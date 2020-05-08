package it.univaq.examifire.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;

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

	public void validate(User user, BindingResult bindingResult) {
		duplicatedEmailValidation(user, bindingResult);
		duplicatedUsernameValidation(user, bindingResult);

	}

	public void validatePassword(String persistentPassword, String currentPassword, String newPassword,
			String confirmPassword, BindingResult bindingResult) {

		if (!passwordEncoder.matches(currentPassword, persistentPassword)) {
			bindingResult.rejectValue("password", "current_password", "The password is wrong");
		}

		if (!confirmPassword.equals(newPassword)) {
			bindingResult.rejectValue("password", "new_password",
					"Please, retype the new password and confirm it correctly");

		}

		this.validatePassword(newPassword, bindingResult);

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

	public void validatePassword(String password, BindingResult bindingResult) {

		try {
			assertThat(password, not(blankOrNullString()));
		} catch (AssertionError e) {
			bindingResult.rejectValue("password", "new_password", "Please, enter the password");
		}

		try {
			assertThat(password.length(), is(both(greaterThanOrEqualTo(5)).and(lessThanOrEqualTo(255))));
		} catch (AssertionError e) {
			bindingResult.rejectValue("password", "new_password", "Minimum 5 characters and maximum 255 characters");
		}

		try {
			assertThat(password, matchesPattern("^[a-zA-Z0-9]+[_\\.\\-]?[a-zA-Z0-9]+$"));
		} catch (AssertionError e) {
			bindingResult.rejectValue("password", "new_password",
					"Please use only alpha numeric characters, possibly with either '_', '-' or '.' in between");
		}

	}
}
