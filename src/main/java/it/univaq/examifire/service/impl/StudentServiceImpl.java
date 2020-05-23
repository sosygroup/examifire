package it.univaq.examifire.service.impl;

import org.springframework.stereotype.Service;

import it.univaq.examifire.model.user.Student;
import it.univaq.examifire.service.StudentService;

@Service
public class StudentServiceImpl extends CrudServiceImpl<Student, Long> implements StudentService {
}
