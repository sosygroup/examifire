package it.univaq.examifire.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The annotated element is an email cannot be duplicated in the User table
 *  
 * @author Marco Autili
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DuplicatedEmailValidator.class)
public @interface DuplicatedEmail {
	/*
	 * 
	 * The annotation Target({ ElementType.FIELD }) Specify that this custom
	 * annotation is a field-level annotation, hence it is applicable to fields.
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
	String message() default "The email already exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
