package it.univaq.examifire.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		return "user/list";
	}
	
	@RequestMapping("/datatable.jquery")
	public @ResponseBody Map<String, Object> findAllPaginated(Model model, Pageable pageable, @RequestParam("draw") Integer draw , @RequestParam(value = "search", defaultValue = "") String search){
		Map<String, Object> data = new HashMap<String, Object>();
        Page<User> page = userService.findAll(pageable);
        data.put("data",page.getContent());
        data.put("draw",draw);
        data.put("recordsTotal",page.getTotalElements());
        data.put("recordsFiltered",page.getTotalElements());
        return data;
	}
	
	@GetMapping("/add")
	public String showAdd(Model model) {
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("user", new User());
		return "user/add";
	}
	
	@PostMapping("/add")
	public String add(@RequestParam(name = "save_and_add_new") boolean saveAndAddNew, User user, BindingResult result, Model model,RedirectAttributes redirectAttributes) {
		user.setPassword(PasswordGeneratorUtils.generateCommonLangPassword());
		validator.validate(user, result);
		if (result.hasErrors()) {
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("confirm_crud_operation", "add_failed");
			return "user/add";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.create(user);
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
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("user", user);
		model.addAttribute("roles", roleService.findAll());
		return "user/edit";
	}
	
	@Transactional
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, @RequestParam boolean saveAndContinue, User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) throws Exception {
		User persistentUser = userService.findById(id).orElseThrow(() -> new Exception("User Not Found with id: " + id));
		
		user.setPassword(persistentUser.getPassword());
		validator.validate(user, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach((error) -> {System.out.println(error.getDefaultMessage());});
			//restore the roles otherwise they are not retained in the model
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("confirm_crud_operation", "update_failed");
			return "user/edit";
		}
		
		userService.update(user);
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
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes atts) throws Exception {
		User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userService.delete(user);
		model.addAttribute("users", userService.findAll());
		atts.addFlashAttribute("confirm_crud_operation", "delete_succeeded");
		return "redirect:/home/admin/users";
	}
}
