package it.univaq.examifire.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.RoleService;
import it.univaq.examifire.service.UserService;

@Controller()
@RequestMapping("/home/admin/users")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private Validator validator;
	
	@GetMapping()
	public String findAll(Model model) {
		model.addAttribute("users", userService.findAll());
		return "user/list";
	}
	
	@PostMapping("/add")
	public String add(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-user";
		}

		userService.create(user);
		model.addAttribute("users", userService.findAll());
		return "index";
	}

	@GetMapping("/edit/{id}")
	public String showEdit(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.findAll());
		return "user/edit";
	}
	
	@Transactional
	@PostMapping("/edit/{id}")
	public String editAndExit(@PathVariable("id") Long id, @RequestParam boolean saveAndContinue, User user, BindingResult result, Model model) throws Exception {
		User persistentUser = userService.findById(id)
				.orElseThrow(() -> new Exception("User Not Found with id: " + id));
		
		user.setPassword(persistentUser.getPassword());
		validator.validate(user, result);
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach((error) -> {System.out.println(error.getDefaultMessage());});
			user.setId(id);
			return "user/edit";
		}
		
		userService.update(user);
		if (saveAndContinue) {
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("user", userService.findById(id).get());
			return "user/edit";	
		} else {
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("users", userService.findAll());
			return "user/list";
		}		
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.delete(user);
		model.addAttribute("users", userService.findAll());
		return "redirect:/users";
	}
}
