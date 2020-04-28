package it.univaq.examifire.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * A username cannot be duplicated in the User table
 * 
 * @author Marco Autili
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuplicatedUsernameValidator.class)
// IF YOU CHANGE THE NAME OF THIS INTERFACE, YOU MUST CHANGE THE SAME INTO THE HTML FILES THAT USES IT FOR SHOWING GLOBAL ERRORS (e.g., edit.html and signup.html)
public @interface DuplicatedUsername {
	/*
	 * 
	 * The annotation Target({ ElementType.TYPE }) Specify that this custom
	 * annotation is a *class-level annotation*, hence it is applicable to any
	 * class, which is WatchlistItem in our case. Having access to the whole class,
	 * class-level annotations permit to realize *cross-field* validation rules
	 * 
	 * The annotation Retention(RetentionPolicy.RUNTIME) Specify that this custom
	 * annotation is effective at runtime, as opposed to compile-time annotation.
	 * 
	 * The annotation Constraint(validatedBy = PriorityValidator.class) Specify that
	 * the actual logic of the validation annotation is implemented in the
	 * PriorityValidator class. This annotation defines a correlation between the
	 * two.
	 * 
	 */
	String message() default "The username already exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
