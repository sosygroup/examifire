package it.univaq.examifire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

@Controller
public class UserAccountController {
	private static final Logger logger = LoggerFactory.getLogger(UserAccountController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/signup")
	public String signup(Model model) {
		logger.debug("HTTP GET request received at URL /signup");
		model.addAttribute("user", new User());
		return "user_account/signup";
	}

	@Transactional
	@PostMapping("/signup")
	public String registerUserAccount(@ModelAttribute("user") User user, BindingResult result, Model model) {
		logger.debug("HTTP POST request received at URL /signup");
		
		Role role = new Role();
		role.setId(Role.STUDENT_ROLE_ID);
		user.getRoles().add(role);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		validator.validate(user, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			return "user_account/signup";
		}

		
		userService.create(user);
		logger.debug("The user with email {} has been added", user.getEmail());

		User createdUser = userService.findByUsername(user.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with username: " + user.getUsername()));
		createdUser.setCreatedBy(createdUser.getId());
		createdUser.setLastModifiedBy(createdUser.getId());
		userService.update(createdUser);
		logger.debug("The created_by and the last_modified_by info of the user with email {} has been updated", user.getEmail());
		
		return "redirect:/signin";
	}
	
	@GetMapping("/forgotpassword")
	public String showForgotPassword(Model model) {
		logger.debug("HTTP GET request received at URL /forgotpassword");
		return "/user_account/forgotpassword";
	}
	
	@PostMapping("/forgotpassword")
	public String forgotPassword(@ModelAttribute("username") String username) {
		logger.debug("HTTP POST request received at URL /forgotpassword");
		// TODO implement the forgot password functionality
		return "redirect:/signin";
	}

	
}
