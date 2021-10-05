package it.univaq.examifire.model.course;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.audit.EntityAudit;
import it.univaq.examifire.model.user.Student;

@Entity
@Table(name = "course_registration")
public class CourseRegistration extends EntityAudit<Long> implements Serializable {
	private static final long serialVersionUID = 81345456055113254L;

	/*
	 * @ManyToOne operates on the so called logical model, i.e. the object-oriented
	 * side of the object-relational mapping. The semantics of optional=false here
	 * are:
	 * 
	 * Whether the association is optional. If set to false then a non-null
	 * relationship must always exist.
	 * 
	 * So the JPA engine expects that the underlying storage will always provide a
	 * value that can be translated to a User object.
	 * 
	 * @JoinColumn operates on the physical model, i.e. how things are actually laid
	 * down in the datastore (database). Specifying nullable = false will make the
	 * DB column non-nullable.
	 * 
	 * If @JoinColumn(nullable = false) was omitted, the column would be nullable.
	 * One could insert a null value there and the DB would happily accept it.
	 * However if someone tried to read that value through JPA, the JPA engine would
	 * protest because it expects a value that can be translated to a User object to
	 * always be there, as specified by @ManyToOne(optional = false).
	 */
	@Id
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Student student;

	@Id
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "course_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Course course;

	@NotBlank(message = "Please enter the cfu/ects")
	@Digits(integer = 2 /* precision */, fraction = 0 /* scale */)
	@PositiveOrZero(message = "Please enter a positive number or zero")
	@Column(name = "cfu_ects", nullable = false, precision = 2)
	private int cfu_ects;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getCfu_ects() {
		return cfu_ects;
	}

	public void setCfu_ects(int cfu_ects) {
		this.cfu_ects = cfu_ects;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cfu_ects;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		CourseRegistration other = (CourseRegistration) obj;

		if (cfu_ects != other.cfu_ects) return false;

		if (course == null) {
			if (other.course != null) return false;
		} else if (!course.equals(other.course)) return false;

		if (student == null) {
			if (other.student != null) return false;
		} else if (!student.equals(other.student)) return false;

		return true;
	}

	@Override
	public String toString() {
		return "CourseRegistration [student=" + student + ", course=" + course + ", cfu_ects=" + cfu_ects + "]";
	}

}
