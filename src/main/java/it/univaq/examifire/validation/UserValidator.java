package it.univaq.examifire.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

@Validated
@Component
public class UserValidator {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void validatePassword(String persistentPassword, String oldPassword, String newPassword,
			String confirmPassword, BindingResult bindingResult) {

		if (!passwordEncoder.matches(oldPassword, persistentPassword)) {
			bindingResult.rejectValue("", "PasswordMatchError", "The password is wrong");
		}

		validateConfirmPassword(newPassword, confirmPassword, bindingResult);
	}
	
	public void validateConfirmPassword(String newPassword,
			String confirmPassword, BindingResult bindingResult) {
		
		if (!confirmPassword.equals(newPassword)) {
			bindingResult.rejectValue("", "ConfirmPasswordError",
					"Please, retype the new password and confirm it correctly");

		}

	}

	public void validateDuplicatedEmail (User user, BindingResult bindingResult) {
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

		bindingResult.rejectValue("email", "DuplicatedEmail", "The email already exists");
	}
	
}
