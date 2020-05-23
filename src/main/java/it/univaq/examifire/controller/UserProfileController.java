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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.security.UserAuthenticationUpdater;
import it.univaq.examifire.security.UserPrincipal;
import it.univaq.examifire.service.UserService;
import it.univaq.examifire.validation.UserValidator;



@Controller
@SessionAttributes("user")
@RequestMapping("/home/profile")
public class UserProfileController {
	/*
	 * org.springframework.validation.Errors /
	 * org.springframework.validation.BindingResult validation results for a
	 * preceding command or form object (the immediately preceding method argument).
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAuthenticationUpdater userAuthenticationUpdater;
	
	@RequestMapping(value = "/avatar/change", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public @ResponseBody String changeUserAvatar(Authentication authentication,
		   @RequestPart("avatar") MultipartFile file) {

		logger.info("HTTP POST request received at URL /home/profile/avatar/change by the user with email {}",
				((UserPrincipal) authentication.getPrincipal()).getEmail());

		Long authenticatedUserId = ((UserPrincipal) authentication.getPrincipal()).getId();
		User persistentUser = userService.findById(authenticatedUserId).orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));

		try {
			byte[] encodeBase64 = Base64.getEncoder().encode(file.getBytes());
			persistentUser.setAvatar(encodeBase64);
		} catch (IOException e) {
		}

		userService.update(persistentUser);
		logger.info("The avatar of the user with email {} has been updated", persistentUser.getEmail());
		userAuthenticationUpdater.update(authentication);

		return "/home/profile/account-info";
	}

	@RequestMapping(value = "/avatar/remove", method = RequestMethod.POST)
	public @ResponseBody String removeUserAvatar(Authentication authentication) {
		
		logger.info("HTTP POST request received at URL /home/profile/avatar/remove by the user with email {}",
				((UserPrincipal) authentication.getPrincipal()).getEmail());

		Long authenticatedUserId = ((UserPrincipal) authentication.getPrincipal()).getId();
		User persistentUser = userService.findById(authenticatedUserId).orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		persistentUser.setAvatar(null);

		userService.update(persistentUser);
		logger.info("The avatar of the user with email {} has been removed", persistentUser.getEmail());
		return "/home/profile/account-info";
	}
	
	@GetMapping("/account-info")
	public String showAccountInfo(Authentication authentication, Model model) {
		
		logger.info("HTTP GET request received at URL /home/profile/account-info by the user with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		/*
		 * Thanks, to the controller level annotation @SessionAttributes("user"),
		 * in this controller session user can be of type, e.g., Student or Teacher 
		 * depending on the type of persistentuser. Thus, the user type is retained
		 * for the User user parameter of the following @PostMapping profile method   
		 */
		model.addAttribute("user", persistentUser);
		return "account/profile/account-info";
	}
	
	
	@Transactional
	@PostMapping("/account-info")
	public String accountInfo(Authentication authentication,
		@Validated(User.Profile.class) User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		/*
		 * Here, the user type (e.g., Student or Teacher) depends on the corresponding type in this controller section, 
		 * see comment in the @GetMapping profile method above. 
		 */
		
		logger.info("HTTP POST request received at URL /home/profile/account-info by the user with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		
		// we need to set the ID here since it is used by the validator
		user.setId(persistentUser.getId());
		userValidator.validateDuplicatedEmail(user, bindingResult);
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.warn("Validation error: {}", error.getDefaultMessage());
			});
			
			// restore the user avatar otherwise it is not retained in the model
			user.setAvatar(persistentUser.getAvatar());

			// restore the roles otherwise they are not retained in the model
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "account/profile/account-info";
		}
		
		persistentUser.setFirstname(user.getFirstname());
		persistentUser.setLastname(user.getLastname());
		persistentUser.setEmail(user.getEmail());
			
		userService.update(persistentUser);
		logger.info("The {} has been updated", persistentUser.toString());
	
		userAuthenticationUpdater.update(authentication);
		
		model.addAttribute("user", persistentUser);
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
		return "redirect:/home/profile/account-info";
	}
	
	@GetMapping("/change-password")
	public String showChangePassword(Authentication authentication, Model model) {
		
		logger.info("HTTP GET request received at URL /home/profile/change-password by the user with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		User persistentUser = userService.findById(authenticatedUserId).orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		model.addAttribute("user", persistentUser);
		return "account/profile/change-password";
	}
	
	@Transactional
	@PostMapping("/change-password")
	public String changePassword(
			Authentication authentication,
			@RequestParam(name = "old_password") String oldPassword,
			@RequestParam(name = "confirm_password") String confirmPassword,
			@Validated(User.ChangePassword.class) User user, BindingResult bindingResult, 
			Model model, RedirectAttributes redirectAttributes) {
		
		logger.info("HTTP POST request received at URL /home/profile/change-password by the user with email {}", ((UserPrincipal)authentication.getPrincipal()).getEmail());
		
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		
		userValidator.validatePassword(persistentUser.getPassword(), oldPassword, user.getPassword(), confirmPassword, bindingResult);
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach((error) -> {
				logger.warn("Validation error: {}", error.getDefaultMessage());
			});
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "account/profile/change-password";
		}
		
		persistentUser.setPassword(passwordEncoder.encode(user.getPassword()));	
		userService.update(persistentUser);
		logger.debug("The password of the user with email {} has been updated", persistentUser.getEmail());
		
		model.addAttribute("user", persistentUser);
		redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
		return "redirect:/home/profile/change-password";
		
	}
	
}
