package it.univaq.examifire.repository;

import org.springframework.stereotype.Repository;

import it.univaq.examifire.model.user.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
