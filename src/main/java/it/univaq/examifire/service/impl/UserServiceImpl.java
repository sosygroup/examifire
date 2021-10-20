package it.univaq.examifire.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.repository.UserRepository;
import it.univaq.examifire.service.UserService;

@Service
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements UserService {

	@Override
	public Optional<User> findByEmail(String email) {
		logger.debug("The method findByEmail has been invoked for the table {}, with parameter email={}", tableName, email);
		return ((UserRepository) repository).findByEmail(email);
	}
}
