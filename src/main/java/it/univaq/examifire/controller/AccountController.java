package it.univaq.examifire.controller;


import java.io.IOException;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.Student;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.security.UserAuthenticationUpdater;
import it.univaq.examifire.security.UserPrincipal;
import it.univaq.examifire.service.UserService;
import it.univaq.examifire.validation.UserValidator;



@Controller
@SessionAttributes("user")
public class AccountController {
	/*
	 * org.springframework.validation.Errors /
	 * org.springframework.validation.BindingResult validation results for a
	 * preceding command or form object (the immediately preceding method argument).
	 */
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAuthenticationUpdater userAuthenticationUpdater;
	
	@GetMapping("/signup")
	public String showSignup(Model model) {
		logger.debug("HTTP GET request received at URL /signup");
		model.addAttribute("user", new Student());
		return "account/signup";
	}
	

	@Transactional
	@PostMapping("/signup")
	public String signup(
			@RequestParam(name = "confirm_password") String confirmPassword,
			@ModelAttribute (name = "user") @Validated(User.Registration.class) Student student,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		logger.debug("HTTP POST request received at URL /signup");

		Role role = new Role();
		role.setId(Role.STUDENT_ROLE_ID);
		student.getRoles().add(role);
		
		userValidator.validateDuplicatedEmail(student, bindingResult);
		userValidator.validateDuplicatedUsername(student, bindingResult);
		userValidator.validateConfirmPassword(student.getPassword(), confirmPassword, bindingResult);
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});

			model.addAttribute("confirm_crud_operation", "add_failed");
			return "account/signup";
		}
		
		student.setPassword(passwordEncoder.encode(student.getPassword()));
		
		userService.create(student);
		logger.debug("The user with email {} has been added", student.getEmail());

		//set the creationBy and the lastmodifiedBy
		student.setCreatedBy(student.getId());
		student.setLastUpdatedBy(student.getId());
		userService.update(student);
		
		logger.debug("The created_by and the last_updated_by info of the student with email {} has been updated",
				student.getEmail());

		redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
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
		// https://www.baeldung.com/spring-security-registration-i-forgot-my-password
		return "redirect:/signin";
	}

	@GetMapping("/home/profile")
	public String showProfile(Authentication authentication, Model model) {
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug("HTTP GET request received at URL /home/profile by the user with id {}", authenticatedUserId);
		
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		/*
		 * Thanks, to the controller level annotation @SessionAttributes("user"),
		 * in this controller session user can be of type, e.g., Student or Teacher 
		 * depending on the type of persistentuser. Thus, the user type is retained
		 * for the User user parameter of the following @PostMapping profile method   
		 */
		model.addAttribute("user", persistentUser);
		return "account/profile/personal-info";
	}
	
	
	@Transactional
	@PostMapping("/home/profile")
	public String profile(Authentication authentication,
			@RequestParam(name = "save_and_continue") boolean saveAndContinue,
			@RequestParam(name = "navigation_tab_active_link") String navigationTabActiveLink,
			@RequestPart(name = "profile_avatar") MultipartFile profileAvatar,
		@Validated(User.Profile.class) User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		/*
		 * Here, the user type (e.g., Student or Teacher) depends on the corresponding type in this controller section, 
		 * see comment in the @GetMapping profile method above. 
		 */
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug(
				"HTTP POST request received at URL /home/profile by the user with id {} and with a request parameters save_and_continue={}, navigation_tab_active_link={}", authenticatedUserId, saveAndContinue, navigationTabActiveLink);
		
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		
		user.setId(authenticatedUserId);
		userValidator.validateDuplicatedUsername(user, bindingResult);
		userValidator.validateDuplicatedEmail(user, bindingResult);
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			
			// restore the user avatar otherwise it is not retained in the model
			user.setAvatar(userService.findById(authenticatedUserId)
					.orElseThrow(() -> new IllegalArgumentException("User Not Found with id: " + authenticatedUserId))
					.getAvatar());

			// restore the roles otherwise they are not retained in the model
			model.addAttribute("confirm_crud_operation", "update_failed");
			model.addAttribute("navigation_tab_active_link", navigationTabActiveLink);
			return "account/profile";
		}
		
		
		if (profileAvatar != null && profileAvatar.getSize() != 0) {
			try {
				byte[] encodeBase64 = Base64.getEncoder().encode(profileAvatar.getBytes());
				persistentUser.setAvatar(encodeBase64);
			} catch (IOException e) {
			}
		}
		

		persistentUser.setFirstname(user.getFirstname());
		persistentUser.setLastname(user.getLastname());
		persistentUser.setUsername(user.getUsername());
		persistentUser.setEmail(user.getEmail());
			
		userService.update(persistentUser);
		logger.debug("The user with id {} has been updated", persistentUser.getId());
	
		userAuthenticationUpdater.update(authentication);
		
		if (saveAndContinue) {
			model.addAttribute("user", userService.findById(persistentUser.getId()).get());
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			redirectAttributes.addFlashAttribute("navigation_tab_active_link", navigationTabActiveLink);
			return "redirect:/home/profile";
		} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home";
		}
	}
		
	@GetMapping("/home/resetavatar")
	public String resetAvatar(Authentication authentication, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		logger.debug("HTTP GET request received at URL /home/resetavatar");
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		persistentUser.setAvatar(null);
		userService.update(persistentUser);
		logger.debug("The user with id {} has been updated", persistentUser.getId());
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
		return "redirect:/home/profile";
	}
	
	@GetMapping("/home/changepassword")
	public String showChangePassword(Authentication authentication, Model model) {
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug("HTTP GET request received at URL /home/changepassword by the user with id {}", authenticatedUserId);
		
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		model.addAttribute("user", persistentUser);
		return "account/changepassword";
	}
	
	@Transactional
	@PostMapping("/home/changepassword")
	public String changepassword(
			Authentication authentication,
			@RequestParam(name = "save_and_continue") boolean saveAndContinue,
			@RequestParam(name = "old_password") String oldPassword,
			@RequestParam(name = "confirm_password") String confirmPassword,
			@Validated(User.ChangePassword.class) User user, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectAttributes) {
		
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug(
				"HTTP POST request received at URL /home/changepassword by the user with id {} and with a request parameters save_and_continue={}", authenticatedUserId, saveAndContinue);
		
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		
		userValidator.validatePassword(persistentUser.getPassword(), oldPassword, user.getPassword(), confirmPassword, bindingResult);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.debug("Validation error: {}", error.getDefaultMessage());
			});
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "account/changepassword";
		}
		
		persistentUser.setPassword(passwordEncoder.encode(user.getPassword()));	
		userService.update(persistentUser);
		logger.debug("The password of the user with id {} has been updated", persistentUser.getId());
		
		if (saveAndContinue) {
			model.addAttribute("user", userService.findById(persistentUser.getId()).get());
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home/changepassword";
		} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home";
		}
	}
	
}
