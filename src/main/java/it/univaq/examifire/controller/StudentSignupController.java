package it.univaq.examifire.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.model.user.Student;
import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.StudentService;
import it.univaq.examifire.validation.UserValidator;

@Controller
public class StudentSignupController {
	/*
	 * org.springframework.validation.Errors /
	 * org.springframework.validation.BindingResult validation results for a
	 * preceding command or form object (the immediately preceding method argument).
	 */
	private static final Logger logger = LoggerFactory.getLogger(StudentSignupController.class);

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/signup")
	public String showSignup(Model model) {
		logger.info("HTTP GET request received at URL /signup");
		model.addAttribute("user", new Student());
		return "account/signup";
	}

	@Transactional
	@PostMapping("/signup")
	public String signup(
			@RequestParam(name = "confirm_password") String confirmPassword,
			@ModelAttribute (name = "user") @Validated(User.Registration.class) Student student,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		logger.info("HTTP POST request received at URL /signup");

		Role role = new Role();
		role.setId(Role.STUDENT_ROLE_ID);
		student.getRoles().add(role);

		userValidator.validateDuplicatedEmail(student, bindingResult);
		userValidator.validateConfirmPassword(student.getPassword(), confirmPassword, bindingResult);

		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error ->
				logger.warn("Validation error: {}", error.getDefaultMessage())
			);

			model.addAttribute("confirm_crud_operation", "add_failed");
			return "account/signup";
		}

		student.setPassword(passwordEncoder.encode(student.getPassword()));

		studentService.create(student);
		logger.info("The {} has been added", student.toString());

		//set the creationBy and the lastmodifiedBy
		student.setCreatedBy(student.getId());
		student.setLastUpdatedBy(student.getId());
		studentService.update(student);

		logger.info("The created_by and the last_updated_by info of the student with email {} has been updated", student.getEmail());

		redirectAttributes.addFlashAttribute("confirm_crud_operation", "add_succeeded");
		return "redirect:/signin";
	}

	@GetMapping("/forgotpassword")
	public String showForgotPassword(Model model) {
		logger.info("HTTP GET request received at URL /forgotpassword");
		return "account/forgotpassword";
	}

	@PostMapping("/forgotpassword")
	public String forgotPassword(@ModelAttribute("username") String username) {
		logger.info("HTTP POST request received at URL /forgotpassword");
		// TODO implement the forgot password functionality 
		// https://www.baeldung.com/spring-security-registration-i-forgot-my-password
		return "redirect:/signin";
	}

}
