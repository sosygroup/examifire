package it.univaq.examifire.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.examifire.service.UserService;


public class DuplicatedUsernameValidator implements ConstraintValidator<DuplicatedUsername, String> {

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (userService.findByUsername(value).isPresent()) {
			return false;
		}

		return true;
	}

}
