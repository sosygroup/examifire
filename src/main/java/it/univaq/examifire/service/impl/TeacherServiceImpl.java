package it.univaq.examifire.service.impl;

import org.springframework.stereotype.Service;

import it.univaq.examifire.model.user.Teacher;
import it.univaq.examifire.service.TeacherService;

@Service
public class TeacherServiceImpl extends CrudServiceImpl<Teacher, Long> implements TeacherService {
}
