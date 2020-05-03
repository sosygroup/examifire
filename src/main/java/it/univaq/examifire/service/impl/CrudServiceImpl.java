package it.univaq.examifire.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import it.univaq.examifire.repository.CrudRepository;
import it.univaq.examifire.service.CrudService;

public class CrudServiceImpl<MODEL, ID extends Serializable> implements CrudService<MODEL, ID> {
	protected static final Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);
	
	@Autowired
	protected CrudRepository<MODEL, ID> repository;
	
	protected String tableName;

	public CrudServiceImpl(){
		super();
		try {
			this.tableName = ((Class<?>)((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0]).getAnnotation(Table.class).name();
		} catch (Exception e) {
			this.tableName = "%TABLE_NAME_NOT_FOUND%";
		}
    }

	@Override
	public Iterable<MODEL> findAll() {
		logger.debug("The method findAll has been invoked for the table {}", tableName);
		return repository.findAll();
	}
	
	@Override
	public DataTablesOutput<MODEL> findAll(DataTablesInput dataTablesInput) {
		logger.debug("The method findAll has been invoked for the table {}, with parameter dataTablesInput={}", tableName, dataTablesInput.toString());
		return repository.findAll(dataTablesInput);
	}

	@Override
	public Optional<MODEL> findById(ID id) {
		logger.debug("The method findById has been invoked for the table {}, with parameter id={}", tableName, id);
		return repository.findById(id);
	}

	@Override
	public void create(MODEL model){
		logger.debug("The method create has been invoked for the table {}, with parameter model={}", tableName, model.toString());
		repository.save(model);
	}

	@Override
	public void update(MODEL model){
		logger.debug("The method update has been invoked for the table {}, with parameter model={}", tableName, model.toString());
		repository.save(model);
	}

	@Override
	public void delete(MODEL model) {
		logger.debug("The method delete has been invoked for the table {}, with parameter model={}", tableName, model.toString());
		repository.delete(model);
	}

}
