package it.univaq.examifire.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import it.univaq.examifire.model.user.Role;
import it.univaq.examifire.repository.RoleRepository;
import it.univaq.examifire.service.RoleService;

@Service
public class RoleServiceImpl extends CrudServiceImpl<Role,Long> implements RoleService {

	@Override
	public Optional<Role> findByName(String name) {
		logger.debug("The method findByName has been invoked for the table {}, with parameter name={}", tableName, name);
		return ((RoleRepository) repository).findByName(name);
	}
}
