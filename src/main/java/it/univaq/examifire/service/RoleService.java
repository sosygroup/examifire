package it.univaq.examifire.service;


import java.util.Optional;

import it.univaq.examifire.model.user.Role;

public interface RoleService extends CrudService<Role, Long> {
	Optional<Role> findByName(String name);
}
