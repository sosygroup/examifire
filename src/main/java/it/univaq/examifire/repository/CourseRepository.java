package it.univaq.examifire.repository;

import org.springframework.stereotype.Repository;

import it.univaq.examifire.model.course.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}
