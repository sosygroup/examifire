package it.univaq.examifire.model.course;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.audit.EntityAudit;
import it.univaq.examifire.model.user.Teacher;

@Entity
@Table(name = "course_teacher")
public class CourseTeacher extends EntityAudit<Long> implements Serializable {
	private static final long serialVersionUID = 3368643330583206422L;

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
	private Teacher teacher;

	@Id
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "course_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Course course;

	@Column(name = "leader", nullable = false)
	private boolean leader;

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	

	@Override
	public String toString() {
		return "CourseTeacher [teacher=" + teacher + ", course=" + course + ", leader=" + leader + "]";
	}

}
