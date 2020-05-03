package it.univaq.examifire.service;

import java.util.Optional;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public interface CrudService<MODEL, ID> {
	Iterable<MODEL> findAll();

	DataTablesOutput<MODEL> findAll(DataTablesInput dataTablesInput);

	/*
	 ***************************************************************************
	 * Additional Data Tables methods are provided by the DataTablesRepository *
	 ***************************************************************************
	 * DataTablesOutput<R> findAll(DataTablesInput input, Function<T, R> converter);
	 * 
	 * DataTablesOutput<T> findAll(DataTablesInput input, Specification<T>
	 * additionalSpecification); DataTablesOutput<T> findAll(DataTablesInput input,
	 * 
	 * Specification<T> additionalSpecification, Specification<T>
	 * preFilteringSpecification);
	 * 
	 * DataTablesOutput<R> findAll(DataTablesInput input, Specification<T>
	 * additionalSpecification, Specification<T> preFilteringSpecification,
	 * Function<T, R> converter);
	 */

	Optional<MODEL> findById(ID id);

	void create(MODEL model);

	void update(MODEL model);

	void delete(MODEL model);

}