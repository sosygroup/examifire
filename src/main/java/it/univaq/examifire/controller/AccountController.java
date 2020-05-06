package it.univaq.examifire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.security.UserPrincipal;
import it.univaq.examifire.service.UserService;
import it.univaq.examifire.validation.UserValidator;

@Controller
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator springValidator;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/signup")
	public String signup(Model model) {
		logger.debug("HTTP GET request received at URL /signup");
		model.addAttribute("user", new User());
		return "account/signup";
	}

	@Transactional
	@PostMapping("/signup")
	public String registerUserAccount(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		logger.debug("HTTP POST request received at URL /signup");

		Role role = new Role();
		role.setId(Role.STUDENT_ROLE_ID);
		user.getRoles().add(role);
		
		springValidator.validate(user, bindingResult);
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			return "account/signup";
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.create(user);
		logger.debug("The user with email {} has been added", user.getEmail());

		User createdUser = userService.findByUsername(user.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with username: " + user.getUsername()));
		createdUser.setCreatedBy(createdUser.getId());
		createdUser.setLastModifiedBy(createdUser.getId());
		userService.update(createdUser);
		logger.debug("The created_by and the last_modified_by info of the user with email {} has been updated",
				user.getEmail());

		return "redirect:/signin";
	}

	@GetMapping("/forgotpassword")
	public String showForgotPassword(Model model) {
		logger.debug("HTTP GET request received at URL /forgotpassword");
		return "account/forgotpassword";
	}

	@PostMapping("/forgotpassword")
	public String forgotPassword(@ModelAttribute("username") String username) {
		logger.debug("HTTP POST request received at URL /forgotpassword");
		// TODO implement the forgot password functionality
		return "redirect:/signin";
	}

	@GetMapping("/home/profile")
	public String showProfile(Authentication authentication, Model model) {
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug("HTTP GET request received at URL /home/profile by the user with id {}", authenticatedUserId);
		
		User user = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		model.addAttribute("user", user);
		return "account/profile";
	}
	
	@Transactional
	@PostMapping("/home/profile")
	public String edit(Authentication authentication, @RequestParam(name = "save_and_continue") boolean saveAndContinue,
			@RequestParam(name = "current_password") String currentPassword,
			@RequestParam(name = "new_password") String newPassword,
			@RequestParam(name = "verify_password") String verifyPassword,
		User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug(
				"HTTP POST request received at URL /home/profile by the user with id {} and with a request parameter save_and_continue={}", authenticatedUserId, saveAndContinue);
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		
		user.setId(persistentUser.getId());
		user.setAccountEnabled(persistentUser.isAccountEnabled());
		user.setPasswordNonExpired(persistentUser.isPasswordNonExpired());
		user.setRoles(persistentUser.getRoles());

		
		if (newPassword.isEmpty()) {
			user.setPassword(persistentUser.getPassword());
			springValidator.validate(user, bindingResult);
			userValidator.validate(user, bindingResult);
		}else {
			user.setPassword(newPassword);
			springValidator.validate(user, bindingResult);
			userValidator.validate(user, currentPassword, newPassword, verifyPassword, bindingResult);
		}
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			// restore the roles otherwise they are not retained in the model
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "account/profile";
		}
		
		// if this variable is true, we force the logout 
		boolean expireUser = (!persistentUser.getUsername().equals(user.getUsername()))? true : false;
		
		if (!newPassword.isEmpty()) {
			user.setPassword(passwordEncoder.encode(newPassword));	
		}
		
		userService.update(user);
		logger.debug("The user with id {} has been updated", user.getId());
		
		if(expireUser) {
			logger.debug("Force the logout for the user {}, since the user changed authentication information", user.getId());
			return "redirect:/logout";
		}

		if (saveAndContinue) {
			model.addAttribute("user", userService.findById(user.getId()).get());
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home/profile";
		} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home";
		}
	}
}