package it.univaq.examifire.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.repository.UserRepository;
import it.univaq.examifire.service.UserService;

@Service
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements UserService {

	@Override
	public Optional<User> findByUsername(String username) {
		return ((UserRepository) repository).findByUsername(username);
	}
	
	@Override
	public Optional<User> findByEmail(String email) {
		return ((UserRepository) repository).findByEmail(email);
	}

}
