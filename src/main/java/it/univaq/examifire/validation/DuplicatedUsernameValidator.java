package it.univaq.examifire.validation;

import java.util.Optional;

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
		Optional<User> user = userService.findById(value.getId());

		if (user.isPresent() && user.get().getUsername().equals(value.getUsername())) {
			return true;
		}

		if (userService.findByUsername(value.getUsername()).isPresent()) {
			return false;
		}

		return true;
	}
}
