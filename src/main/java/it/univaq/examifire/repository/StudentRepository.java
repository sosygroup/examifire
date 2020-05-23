package it.univaq.examifire.repository;

import org.springframework.stereotype.Repository;

import it.univaq.examifire.model.user.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {}