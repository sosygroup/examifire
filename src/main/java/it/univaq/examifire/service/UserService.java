package it.univaq.examifire.service;

import java.util.Optional;

import it.univaq.examifire.model.User;

public interface UserService extends CrudService<User, Long> {
	Optional<User> findByUsername(String username);
}
