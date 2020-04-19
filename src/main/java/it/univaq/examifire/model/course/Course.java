package it.univaq.examifire.model.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.univaq.examifire.model.audit.EntityAudit;

@Entity
@Table(name = "course")
public class Course extends EntityAudit<Long>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long id;

}
