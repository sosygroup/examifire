package it.univaq.examifire.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

public class DuplicatedEmailValidator implements ConstraintValidator<DuplicatedEmail, User> {

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		// NOTE: this validation is called by during both user creation and modification 
		
		User persistentUser = userService.findByEmail(value.getEmail()).orElse(null);

		// The DB does not contains any users for the given email, hence the chosen
		// email is valid
		if (persistentUser == null) {
			return true;
		}

		// (i) if value.getId() is null, it means that a new user is being created. Now,
		// since a persistent user with the given email already exists this validation
		// check fails.
		//
		// (ii) if value.getid() is not null, it means that the user with the given id
		// is being modified. Now, if value.getId().equals(persistentUser.getId()) is
		// true, it means that the user with the given id already exists and has the
		// same email this validation check succeeds
		//
		// (iii) if value.getid() is not null, it means that the user with the given id
		// is being modified. Now, if value.getId().equals(persistentUser.getId()) is
		// false, it means that the user with the given id is different by the one
		// already present in the DB (i.e., persistentUser). This means that the
		// modified email is duplicated (i.e., the chosen email is the same has the
		// persistenUser). Hence, this validation check fails.
		if (value.getId() != null && value.getId().equals(persistentUser.getId())) {
			return true;
		}

		return false;
	}

}
