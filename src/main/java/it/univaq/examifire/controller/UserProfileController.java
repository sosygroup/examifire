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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
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

		Long authenticatedUserId = ((UserPrincipal) authentication.getPrincipal()).getId();
		logger.debug("HTTP POST request received at URL /home/profile/avatar/change by the user with id {}",
				authenticatedUserId);

		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));

		try {
			byte[] encodeBase64 = Base64.getEncoder().encode(file.getBytes());
			persistentUser.setAvatar(encodeBase64);
		} catch (IOException e) {
		}

		userService.update(persistentUser);

		return "/home/profile/account-info";
	}

	@RequestMapping(value = "/avatar/remove", method = RequestMethod.POST)
	public @ResponseBody String removeUserAvatar(Authentication authentication) {

		Long authenticatedUserId = ((UserPrincipal) authentication.getPrincipal()).getId();
		logger.debug("HTTP POST request received at URL /home/profile/avatar/remove by the user with id {}",
				authenticatedUserId);

		User persistentUser = userService.findById(authenticatedUserId)
				.orElseThrow(() -> new IllegalArgumentException("User Not Found with id:" + authenticatedUserId));
		persistentUser.setAvatar(null);

		userService.update(persistentUser);

		return "/home/profile/account-info";
	}
	
	
	
	@GetMapping("/account-info")
	public String showAccountInfo(Authentication authentication, Model model) {
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug("HTTP GET request received at URL /home/profile/account-info by the user with id {}", authenticatedUserId);
		
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
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		logger.debug(
				"HTTP POST request received at URL /home/profile/account-info by the user with id {}", authenticatedUserId);
		
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
			return "account/profile/account-info";
		}
		
		persistentUser.setFirstname(user.getFirstname());
		persistentUser.setLastname(user.getLastname());
		persistentUser.setUsername(user.getUsername());
		persistentUser.setEmail(user.getEmail());
			
		userService.update(persistentUser);
		logger.debug("The user with id {} has been updated", persistentUser.getId());
	
		userAuthenticationUpdater.update(authentication);
		
	//	if (saveAndContinue) {
			model.addAttribute("user", userService.findById(persistentUser.getId()).get());
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home/profile/account-info";
		/*} else {
			redirectAttributes.addFlashAttribute("confirm_crud_operation", "update_succeeded");
			return "redirect:/home";
		}*/
	}
		
	
}
