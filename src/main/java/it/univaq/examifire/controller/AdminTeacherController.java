package it.univaq.examifire.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.Teacher;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.security.UserPrincipal;
import it.univaq.examifire.service.RoleService;
import it.univaq.examifire.service.TeacherService;
import it.univaq.examifire.util.PasswordGeneratorUtils;
import it.univaq.examifire.util.Utils;
import it.univaq.examifire.validation.UserValidator;

@Controller()
@RequestMapping("/home/admin/teacher")
public class AdminTeacherController {
	private static final Logger logger = LoggerFactory.getLogger(AdminTeacherController.class);

	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/list")
	public String listAll(Authentication authentication, Model model) {
		logger.info("HTTP GET request received at URL /home/admin/teacher/list by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		return "admin/user/teacher/list";
	}

	@RequestMapping(value ="/list-datatable", method = RequestMethod.GET)
	public @ResponseBody DataTablesOutput<Teacher> listAllPaginated (Authentication authentication, Model model, @Valid DataTablesInput input) {
		logger.info("Ajax request received at URL /home/admin/teacher/list-datatable by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		return teacherService.findAll(input);
	}
	
	@GetMapping("/add")
	public String showAdd(Authentication authentication, Model model) {
		logger.info("HTTP GET request received at URL /home/admin/teacher/add by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		model.addAttribute("user", new Teacher());
		return "admin/user/teacher/add";
	}
	
	
	@Transactional
	@PostMapping("/add")
	public String add(Authentication authentication,
		@RequestParam(name = "save_option") String saveOption, 
		@RequestParam(name = "is_admin", required = false) boolean isAdmin,
		@Validated(User.CreateByAdmin.class) @ModelAttribute("user") Teacher teacher, 
		BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		logger.info(
				"HTTP POST request received at URL /home/admin/teacher/add by the admin with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		userValidator.validateDuplicatedEmail(teacher, bindingResult);
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.warn("Validation error: {}", error.getDefaultMessage());
			});
			
			model.addAttribute("is_admin", isAdmin);
			model.addAttribute("confirm_crud_operation", "add_failed");
			return "admin/user/teacher/add";
		}
		
		teacher.getRoles().add(roleService.findByName(Role.TEACHER_ROLE_NAME).get());
		
		if (isAdmin) {
			teacher.getRoles().add(roleService.findByName(Role.ADMIN_ROLE_NAME).get());
		}
		
		// automatically generate the password for the user 
		teacher.setPassword(passwordEncoder.encode(PasswordGeneratorUtils.generateCommonLangPassword()));
		teacherService.update(teacher);
		logger.info("The {} has been added by the admin with email {}", teacher.toString(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
			
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
		switch (saveOption) {
         case "edit":
        	 return "redirect:/home/admin/teacher/edit/" + teacher.getId() + "/account-info";
         case "add_new":
        	 return "redirect:/home/admin/teacher/add";
         case "list_all":
        	 return "redirect:/home/admin/teacher/list";
         default:
             throw new IllegalArgumentException("Invalid save option: " + saveOption) ;
		 }
	}
		
	@GetMapping("/edit/{teacherId}/account-info")
	public String showEditAccountInfo(Authentication authentication, @PathVariable("teacherId") Long teacherId, Model model) {
		logger.info("HTTP GET request received at URL /home/admin/teacher/edit/{}/account-info by the admin with email {}", teacherId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
		Teacher persistentUser = teacherService.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher Not Found with id: " + teacherId));
		model.addAttribute("is_admin", Utils.hasAdminRole(persistentUser));
		model.addAttribute("persistentUser", persistentUser);
		model.addAttribute("user", persistentUser);
		return "admin/user/teacher/edit/account-info";
	}
	
	
	@Transactional
	@PostMapping("/edit/{teacherId}/account-info")
	public String editAccountInfo(Authentication authentication,
		@PathVariable("teacherId") Long teacherId,
		@RequestParam(name = "is_admin", required = false) boolean isAdmin,
		@Validated(User.EditByAdmin.class) @ModelAttribute("user") Teacher teacher, BindingResult bindingResult,
		Model model, RedirectAttributes redirectAttributes) {
		
		logger.info("HTTP POST request received at URL /home/admin/teacher/edit/{}/account-info by the admin with email {}", teacherId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		// we need to set the ID here since it is used by the validator
		teacher.setId(teacherId);
		userValidator.validateDuplicatedEmail(teacher, bindingResult);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.warn("Validation error: {}", error.getDefaultMessage());
			});
			
			model.addAttribute("persistentUser", teacherService.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher Not Found with id: " + teacherId)));
			model.addAttribute("is_admin", isAdmin);
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "admin/user/teacher/edit/account-info";
		}
		
		Teacher persistentUser = teacherService.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher Not Found with id: " + teacherId));
		
		persistentUser.setFirstname(teacher.getFirstname());
		persistentUser.setLastname(teacher.getLastname());
		persistentUser.setEmail(teacher.getEmail());
		
		if (isAdmin && !Utils.hasAdminRole(persistentUser)) {
			persistentUser.getRoles().add(roleService.findByName(Role.ADMIN_ROLE_NAME).get());
		}else if (!isAdmin && Utils.hasAdminRole(persistentUser)) {
			persistentUser.getRoles().remove(roleService.findByName(Role.ADMIN_ROLE_NAME).get());
		}
		
		persistentUser.setAccountEnabled(teacher.isAccountEnabled());
		persistentUser.setPasswordNonExpired(teacher.isPasswordNonExpired());
		
		teacherService.update(persistentUser);
		logger.info("The {} has been edited by the admin with email {}", persistentUser.toString(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
		return "redirect:/home/admin/teacher/edit/" + persistentUser.getId() + "/account-info";
	}
	
	@GetMapping("/edit/{teacherId}/change-password")
	public String showChangePassword(Authentication authentication, @PathVariable("teacherId") Long teacherId, Model model) {
		
		logger.info("HTTP GET request received at URL /home/admin/teacher/edit/{}/change-password by the admin with email {}", teacherId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		Teacher persistentUser = teacherService.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher Not Found with id: " + teacherId));
		model.addAttribute("persistentUser", persistentUser);
		model.addAttribute("user", persistentUser);
		return "admin/user/teacher/edit/change-password";
	}
	
	@Transactional
	@PostMapping("/edit/{teacherId}/change-password")
	public String changePassword(
			Authentication authentication,
			@PathVariable("teacherId") Long teacherId,
			@RequestParam(name = "confirm_password") String confirmPassword,
			@Validated(User.ChangePassword.class) @ModelAttribute("user") Teacher teacher, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectAttributes) {
		
		logger.info("HTTP POST request received at URL /home/admin/teacher/edit/{}/change-password by the admin with email {}", teacherId, ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		Teacher persistentUser = teacherService.findById(teacherId)
				.orElseThrow(() -> new IllegalArgumentException("Teacher Not Found with id: " + teacherId));
		
		userValidator.validateConfirmPassword(teacher.getPassword(), confirmPassword, bindingResult);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.warn("Validation error: {}", error.getDefaultMessage());
			});
			
			model.addAttribute("persistentUser", teacherService.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher Not Found with id: " + teacherId)));
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "admin/user/teacher/edit/change-password";
		}
		
		persistentUser.setPassword(passwordEncoder.encode(teacher.getPassword()));	
		teacherService.update(persistentUser);
		logger.debug("The password of the teacher with email {} has been updated by the admin with email {}", persistentUser.getEmail(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
		return "redirect:/home/admin/teacher/edit/" + persistentUser.getId() + "/change-password";
		
	}
	
	@GetMapping("/delete/{teacherId}")
	public String delete(
			Authentication authentication,
			@PathVariable("teacherId") Long teacherId, 
			Model model, RedirectAttributes redirectAttributes){
		logger.info("HTTP GET request received at URL /home/admin/teacher/delete/{} by the admin with email {}", teacherId, ((UserPrincipal)authentication.getPrincipal()).getEmail());

		Teacher persistentUser = teacherService.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("Teacher Not Found with id:" + teacherId));
		teacherService.delete(persistentUser);
		logger.info("The {} has been deleted by the admin with email {}", persistentUser.toString(), ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "delete_succeeded");
		return "redirect:/home/admin/teacher/list";
	}
	
	
}
