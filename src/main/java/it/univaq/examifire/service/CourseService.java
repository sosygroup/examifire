package it.univaq.examifire.service;


import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import it.univaq.examifire.model.course.Course;

public interface CourseService extends CrudService<Course, Long> {

	DataTablesOutput<Course> findAllByTeacherId(DataTablesInput dataTablesInput, Long teacherId);

}
