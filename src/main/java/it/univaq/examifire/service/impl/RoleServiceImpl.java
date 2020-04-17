package it.univaq.examifire.service.impl;

import org.springframework.stereotype.Service;

import it.univaq.examifire.model.Role;
import it.univaq.examifire.service.RoleService;

@Service
public class RoleServiceImpl extends CrudServiceImpl<Role,Long> implements RoleService {
}
