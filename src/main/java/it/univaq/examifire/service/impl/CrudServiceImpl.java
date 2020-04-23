package it.univaq.examifire.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.examifire.repository.CrudRepository;
import it.univaq.examifire.service.CrudService;

public class CrudServiceImpl<MODEL, ID> implements CrudService<MODEL, ID> {
	@Autowired
	protected CrudRepository<MODEL, ID> repository;

	@Override
	public List<MODEL> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<MODEL> findById(ID id) {
		return repository.findById(id);
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
	public void deleteById(ID id) {
		repository.deleteById(id);
	}
	@Override
	public void delete(MODEL model) {
		repository.delete(model);
	}

}
