package it.univaq.examifire.service;

import java.util.List;

public interface CrudService<MODEL, PK> {
	List<MODEL> findAll();

	MODEL findByPK(PK pk);

	void create(MODEL model);

	void update(MODEL model);

	void delete(PK pk);
}