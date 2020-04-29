package it.univaq.examifire.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<MODEL, ID> {
	List<MODEL> findAll();
	
	Page<MODEL> findAll(Pageable pageable);

	Optional<MODEL> findById(ID id);

	void create(MODEL model);

	void update(MODEL model);

	void deleteById(ID id);
	
	void delete(MODEL model);
}