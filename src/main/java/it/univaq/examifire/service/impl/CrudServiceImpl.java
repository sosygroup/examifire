package it.univaq.examifire.service.impl;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import it.univaq.examifire.repository.CrudRepository;
import it.univaq.examifire.service.CrudService;

public class CrudServiceImpl<MODEL, ID extends Serializable> implements CrudService<MODEL, ID> {
	@Autowired
	protected CrudRepository<MODEL, ID> repository;

	@Override
	public Iterable<MODEL> findAll() {
		return repository.findAll();
	}
	
	@Override
	public DataTablesOutput<MODEL> findAll(DataTablesInput dataTablesInput) {
		return repository.findAll(dataTablesInput);
	}

	@Override
	public Optional<MODEL> findById(ID id) {
		return repository.findById(id);
	}

	@Override
	public void create(MODEL model){
		repository.save(model);
	}

	@Override
	public void update(MODEL model){
		repository.save(model);
	}

	@Override
	public void delete(MODEL model) {
		repository.delete(model);
	}

}
