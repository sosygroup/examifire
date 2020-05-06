package it.univaq.examifire.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

@Component
public class UserValidator {
	@Autowired
	private UserService userService;


	@Autowired
	private PasswordEncoder passwordEncoder;

	public void validate(User user, BindingResult bindingResult) {
		duplicatedEmailValidation(user, bindingResult);
		duplicatedUsernameValidation(user, bindingResult);

	}
	
	public void validate(User user, String currentPassword, String newPassword, String verifyPassword, BindingResult bindingResult) {
		validate (user, bindingResult);
		passwordUpdateValidation(user, currentPassword, newPassword, verifyPassword, bindingResult);

	}

	private void duplicatedEmailValidation(User user, BindingResult bindingResult) {
		// NOTE: this validation is called by during both user creation and modification

		User persistentUser = userService.findByEmail(user.getEmail()).orElse(null);

		// The DB does not contains any users for the given email, hence the chosen
		// email is valid
		if (persistentUser == null) {
			return;
		}

		// (i) if user.getId() is null, it means that a new user is being created. Now,
		// since a persistent user with the given email already exists this validation
		// check fails.
		//
		// (ii) if user.getid() is not null, it means that the user with the given id
		// is being modified. Now, if user.getId().equals(persistentUser.getId()) is
		// true, it means that the user with the given id already exists and has the
		// same email this validation check succeeds
		//
		// (iii) if user.getid() is not null, it means that the user with the given id
		// is being modified. Now, if user.getId().equals(persistentUser.getId()) is
		// false, it means that the user with the given id is different by the one
		// already present in the DB (i.e., persistentUser). This means that the
		// modified email is duplicated (i.e., the chosen email is the same has the
		// persistenUser). Hence, this validation check fails.
		if (user.getId() != null && user.getId().equals(persistentUser.getId())) {
			return;
		}

		bindingResult.rejectValue("email", "", "The email already exists");
	}

	private void duplicatedUsernameValidation(User user, BindingResult bindingResult) {
		User persistentUser = userService.findByUsername(user.getUsername()).orElse(null);

		if (persistentUser == null) {
			return;
		}
		if (user.getId() != null && user.getId().equals(persistentUser.getId())) {
			return;
		}

		bindingResult.rejectValue("username", "", "The username already exists");
	}

	
	private void passwordUpdateValidation(User user, String currentPassword, String newPassword, String verifyPassword, BindingResult bindingResult) {
		User persistentUser = userService.findById(user.getId()).orElse(null);

		if (passwordEncoder.matches(currentPassword, persistentUser.getPassword()) && verifyPassword.equals(newPassword)) {
			return;
		}
		
		bindingResult.rejectValue("password", "", "Please, retype the new password and verify it: make sure your passwords match");
		
	}
}
