package it.univaq.examifire.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

@Controller
public class UserAccountController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "user_account/signup";
	}

	@Transactional
	@PostMapping("/signup")
	public String registerUserAccount(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user_account/signup";
		}

		Role role = new Role();
		role.setId(Role.STUDENT_ROLE_ID);
		user.getRoles().add(role);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.create(user);

		User createdUser = userService.findByUsername(user.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with username: " + user.getUsername()));

		createdUser.setCreatedBy(createdUser.getId());
		createdUser.setLastModifiedBy(createdUser.getId());
		userService.update(createdUser);
		return "redirect:/signin";
	}
	
	@GetMapping("/forgotpassword")
	public String showForgotPassword(Model model) {
		return "/user_account/forgotpassword";
	}
	
	@PostMapping("/forgotpassword")
	public String forgotPassword(@ModelAttribute("username") String username) {
		// TODO implement the forgot password functionality
		return "redirect:/signin";
	}

	
}
