package it.univaq.examifire.service.impl;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import it.univaq.examifire.model.course.Course;
import it.univaq.examifire.service.CourseService;

@Service
public class CourseServiceImpl extends CrudServiceImpl<Course,Long> implements CourseService {

	@Override
	public DataTablesOutput<Course> findAllByTeacherId(DataTablesInput dataTablesInput, Long teacherId) {
		return repository.findAll(dataTablesInput,(root, query, criteriaBuilder) -> {
	        return criteriaBuilder.equal(root.join("teachers", JoinType.INNER).get("teacher").get("id"), teacherId);
	    });
	}
}
