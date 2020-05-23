package it.univaq.examifire.repository;

import org.springframework.stereotype.Repository;

import it.univaq.examifire.model.user.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {}