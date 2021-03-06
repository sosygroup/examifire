package it.univaq.examifire.service;

import java.util.Optional;

import it.univaq.examifire.model.user.User;

public interface UserService extends CrudService<User, Long> {
	
	Optional<User> findByEmail(String email);

}
