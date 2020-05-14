package it.univaq.examifire.controller;

import java.io.IOException;
import java.util.Base64;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.Student;
import it.univaq.examifire.model.user.Teacher;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.RoleService;
import it.univaq.examifire.service.UserService;
import it.univaq.examifire.util.PasswordGeneratorUtils;
import it.univaq.examifire.validation.UserValidator;

@Controller()
@SessionAttributes("user")
@RequestMapping("/home/admin/users")
public class AdminUserController {
	/*
	 * org.springframework.validation.Errors /
	 * org.springframework.validation.BindingResult validation results for a
	 * preceding command or form object (the immediately preceding method argument).
	 */
	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping()
	public String findAll(Model model) {
		logger.debug("HTTP GET request received at URL /home/admin/users");
		return "admin/user/list";
	}

	@RequestMapping("/datatable.jquery")
	public @ResponseBody DataTablesOutput<User> findPaginated (Model model, @Valid DataTablesInput input) {
		logger.debug("HTTP GET request received at URL /home/admin/users/datatable.jquery");
		return userService.findAll(input);
	}
	
	@GetMapping("/add/{role}")
	public String showAdd(@PathVariable("role") String role, Model model) {
		logger.debug("HTTP GET request received at URL /home/admin/users/add");
		model.addAttribute("roles", roleService.findAll());

		User user;
		Role userRole = new Role();
		switch (role) {
		case "student":
			userRole.setId(Role.STUDENT_ROLE_ID);
			user = new Student();
			user.getRoles().add(userRole);
			break;
		case "teacher":
			userRole.setId(Role.TEACHER_ROLE_ID);
			user = new Teacher();
			user.getRoles().add(userRole);
			break;
		case "admin":
			userRole.setId(Role.ADMIN_ROLE_ID);
			user = new User();
			user.getRoles().add(userRole);
			break;
		default:
			throw new IllegalArgumentException("Unknown role " + role);
		}
		model.addAttribute("user", user);
		return "admin/user/add";
	}
	

	private String getViewByUserType (User user) {
		if (user instanceof Student) {
			return "student";
		}
		
		if (user instanceof Teacher) {
			return "teacher";
		}
		
		if (user instanceof User) {
			return "admin";
		}
		throw new IllegalArgumentException("Unknown the user type " + user);
	}
	
	@PostMapping("/add")
	public String add(
			@RequestParam(name = "save_and_add_new") boolean saveAndAddNew, 
			@RequestParam(name = "navigation_tab_active_link") String navigationTabActiveLink,
			@RequestPart(name = "profile_avatar") MultipartFile profileAvatar,
			@Validated(User.CreateEditByAdmin.class) User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		logger.debug(
				"HTTP POST request received at URL /home/admin/users/add with a request parameter save_and_add_new={}", saveAndAddNew);
		
		userValidator.validateDuplicatedEmail(user, bindingResult);
		userValidator.validateDuplicatedUsername(user, bindingResult);
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("confirm_crud_operation", "add_failed");
			model.addAttribute("navigation_tab_active_link", navigationTabActiveLink);
			
			return "admin/user/add";
		}

		// automatically generate the password for the user 
		user.setPassword(passwordEncoder.encode(PasswordGeneratorUtils.generateCommonLangPassword()));
		try {
			if (profileAvatar != null &&
					profileAvatar.getBytes() !=null && profileAvatar.getSize() !=0) {
				byte[] encodeBase64 = Base64.getEncoder().encode(profileAvatar.getBytes());
				user.setAvatar(encodeBase64);
			}
		} catch (IOException e) {
		}
		userService.create(user);
		logger.debug("The user with email {} has been added", user.getEmail());

		if (saveAndAddNew) {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
			return "redirect:/home/admin/users/add/"+getViewByUserType(user);
		} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
			return "redirect:/home/admin/users";
		}
	}

	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable("id") Long id, Model model) {
		logger.debug("HTTP GET request received at URL /home/admin/users/edit/{}", id);
		User persistentUser = userService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + id));
		model.addAttribute("user", persistentUser);
		model.addAttribute("roles", roleService.findAll());
		return "admin/user/edit";
	}

	@Transactional
	@PostMapping("/edit/{id}")
	public String edit(
			@PathVariable("id") Long id, 
			@RequestParam(name = "save_and_continue") boolean saveAndContinue,
			@RequestParam(name = "navigation_tab_active_link") String navigationTabActiveLink,
			@RequestPart(name = "profile_avatar") MultipartFile profileAvatar,
			@Validated(User.CreateEditByAdmin.class) User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		logger.debug(
				"HTTP POST request received at URL /home/admin/users/edit/{} with a request parameter save_and_continue={}",
				id, saveAndContinue);
		
		userValidator.validateDuplicatedEmail(user, bindingResult);
		userValidator.validateDuplicatedUsername(user, bindingResult);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			// restore the roles otherwise they are not retained in the model
			model.addAttribute("roles", roleService.findAll());
			
			// restore the user avatar otherwise it is not retained in the model
			user.setAvatar(userService.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("User Not Found with id: " + id)).getAvatar());
			
			model.addAttribute("confirm_crud_operation", "update_failed");
			model.addAttribute("navigation_tab_active_link", navigationTabActiveLink);
			return "admin/user/edit";
		}
		
		User persistentUser = userService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id: " + id));
		
		try {
			if (profileAvatar != null &&
					profileAvatar.getBytes() !=null && profileAvatar.getSize() !=0) {
				byte[] encodeBase64 = Base64.getEncoder().encode(profileAvatar.getBytes());
				persistentUser.setAvatar(encodeBase64);
			}
		} catch (IOException e) {
		}
		
		persistentUser.setFirstname(user.getFirstname());
		persistentUser.setLastname(user.getLastname());
		persistentUser.setRoles(user.getRoles());
		persistentUser.setUsername(user.getUsername());
		persistentUser.setEmail(user.getEmail());
		persistentUser.setAccountEnabled(user.isAccountEnabled());
		persistentUser.setPasswordNonExpired(user.isPasswordNonExpired());
		
		userService.update(persistentUser);
		logger.debug("The user with id {} has been updated", id);

		if (saveAndContinue) {
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("user", userService.findById(id).get());
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			redirectAttributes.addFlashAttribute("navigation_tab_active_link", navigationTabActiveLink);
			return "redirect:/home/admin/users/edit/" + id;
		} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home/admin/users";
		}
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		logger.debug("HTTP GET request received at URL /home/admin/users/delete/{}", id);
		User persistentUser = userService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + id));
		userService.delete(persistentUser);
		logger.debug("The user with id {} has been deleted", id);
		model.addAttribute("users", userService.findAll());
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "delete_succeeded");
		return "redirect:/home/admin/users";
	}
	
	@GetMapping("/resetavatar/{id}")
	public String resetAvatar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		logger.debug("HTTP GET request received at URL /home/admin/users/delete/{}", id);
		
		User persistentUser = userService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + id));
		persistentUser.setAvatar(null);
		userService.update(persistentUser);
		logger.debug("The user with id {} has been updated", persistentUser.getId());
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
		return "redirect:/home/admin/users/edit/" + id;
	}

}
