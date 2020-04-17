package it.univaq.examifire.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.examifire.repository.CrudRepository;
import it.univaq.examifire.service.CrudService;

public class CrudServiceImpl<MODEL, PK> implements CrudService<MODEL, PK> {
	@Autowired
	protected CrudRepository<MODEL, PK> repository;

	@Override
	public List<MODEL> findAll() {
		return repository.findAll();
	}

	@Override
	public MODEL findByPK(PK pk) {
		return repository.findById(pk).orElse(null);
	}

	@Override
	public void create(MODEL model) {
		repository.save(model);
	}

	@Override
	public void update(MODEL model) {
		repository.save(model);
	}

	@Override
	public void delete(PK pk) {
		repository.deleteById(pk);
	}

}
