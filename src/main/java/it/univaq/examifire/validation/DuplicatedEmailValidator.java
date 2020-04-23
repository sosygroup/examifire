package it.univaq.examifire.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.examifire.service.UserService;


public class DuplicatedEmailValidator implements ConstraintValidator<DuplicatedEmail, String> {

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (userService.findByEmail(value).isPresent()) {
			return false;
		}

		return true;
	}

}
