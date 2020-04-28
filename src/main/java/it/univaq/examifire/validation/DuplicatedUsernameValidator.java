package it.univaq.examifire.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

public class DuplicatedUsernameValidator implements ConstraintValidator<DuplicatedUsername, User> {

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		// NOTE: this validation is called by during both user creation and modification.
		// see comment in the it.univaq.examifire.validation.DuplicatedEmailValidator class 

		User persistentUser = userService.findByUsername(value.getUsername()).orElse(null);
		
		if (persistentUser == null) {
			return true;
		}
		if (value.getId() != null && value.getId().equals(persistentUser.getId())) {
			return true;
		}

		return false;

	}
}
