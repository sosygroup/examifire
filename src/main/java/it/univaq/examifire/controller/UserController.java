package it.univaq.examifire.controller;

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
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.RoleService;
import it.univaq.examifire.service.UserService;
import it.univaq.examifire.util.PasswordGeneratorUtils;

@Controller()
@RequestMapping("/home/admin/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping()
	public String findAll(Model model) {
		logger.debug("HTTP GET request received at URL /home/admin/users");
		return "user/list";
	}
	
	@RequestMapping("/datatable.jquery")
    public @ResponseBody DataTablesOutput<User> listPOST(Model model, @Valid DataTablesInput input) {
		logger.debug("HTTP GET request received at URL /home/admin/users/datatable.jquery");
		return userService.findAll(input);
    }
	
	@GetMapping("/add")
	public String showAdd(Model model) {
		logger.debug("HTTP GET request received at URL /home/admin/users/add");
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("user", new User());
		return "user/add";
	}
	
	@PostMapping("/add")
	public String add(@RequestParam(name = "save_and_add_new") boolean saveAndAddNew, User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		logger.debug("HTTP POST request received at URL /home/admin/users/add with a request parameter save_and_add_new={}", saveAndAddNew);
		user.setPassword(PasswordGeneratorUtils.generateCommonLangPassword());
		validator.validate(user, result);
	
		if (result.hasErrors()) {
			result.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("confirm_crud_operation", "add_failed");
			return "user/add";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.create(user);
		logger.debug("The user with email {} has been added", user.getEmail());
		
		if (saveAndAddNew) {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
			return "redirect:/home/admin/users/add";
		} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
			return "redirect:/home/admin/users";
		}
	}

	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable("id") Long id, Model model) {
		logger.debug("HTTP GET request received at URL /home/admin/users/edit/{}", id);
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + id));
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.findAll());
		return "user/edit";
	}
	
	@Transactional
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, @RequestParam(name = "save_and_continue") boolean saveAndContinue, User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("HTTP POST request received at URL /home/admin/users/edit/{} with a request parameter save_and_continue={}", id, saveAndContinue);
		User persistentUser = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found with id: " + id));

		user.setPassword(persistentUser.getPassword());
		validator.validate(user, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			//restore the roles otherwise they are not retained in the model
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "user/edit";
		}
		
		userService.update(user);
		logger.debug("The user with id {} has been updated", id);
		
		if (saveAndContinue) {
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("user", userService.findById(id).get());
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home/admin/users/edit/"+id;
		} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home/admin/users";
		}		
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("HTTP GET request received at URL /home/admin/users/delete/{}", id);
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + id));
		userService.delete(user);
		logger.debug("The user with id {} has been deleted", id);
		model.addAttribute("users", userService.findAll());
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "delete_succeeded");
		return "redirect:/home/admin/users";
	}
	
}
