package it.univaq.examifire.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/users")
	public String findAll(Model model) {
		model.addAttribute("users", userService.findAll());
		return "user/list";
	}
	
	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-user";
		}

		userService.create(user);
		model.addAttribute("users", userService.findAll());
		return "index";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		model.addAttribute("user", user);
		return "update-user";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}

		userService.update(user);
		model.addAttribute("users", userService.findAll());
		return "index";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.delete(user);
		model.addAttribute("users", userService.findAll());
		return "redirect:/users";
	}
}
