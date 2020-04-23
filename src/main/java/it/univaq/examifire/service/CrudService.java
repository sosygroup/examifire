package it.univaq.examifire.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<MODEL, ID> {
	List<MODEL> findAll();

	Optional<MODEL> findById(ID id);

	void create(MODEL model);

	void update(MODEL model);

	void deleteById(ID id);
	
	void delete(MODEL model);
}