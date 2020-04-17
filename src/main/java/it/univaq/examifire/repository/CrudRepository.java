package it.univaq.examifire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudRepository<MODEL, PK> extends JpaRepository<MODEL, PK> {
	/**
	 * JPA specific extension of
	 * {@link org.springframework.data.repository.Repository}.
	 * 
	 * public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T,
	 * ID>, QueryByExampleExecutor<T>
	 * 
	 * ---
	 * 
	 * instead of to use org.springframework.data.repository.CrudRepository that is
	 * an Interface for generic CRUD operations on a repository for a specific type.
	 * 
	 * public interface CrudRepository<T, ID> extends Repository<T, ID>
	 * 
	 */
}
