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
import it.univaq.examifire.model.user.TeacherUser;

@Entity
@Table(name = "course_teacher_user")
public class CourseTeacherUser extends EntityAudit<Long> implements Serializable {
	private static final long serialVersionUID = 8719823681870895521L;

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
	 * protest because it expects a value that can be translated to a User object
	 * to always be there, as specified by @ManyToOne(optional = false).
	 */
	@Id
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private TeacherUser teacherUser;

	@Id
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "course_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Course course;
	
	@Column(name = "leader", nullable = false)
	private boolean leader;

	public TeacherUser getTeacherUser() {
		return teacherUser;
	}

	public void setTeacherUser(TeacherUser teacherUser) {
		this.teacherUser = teacherUser;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + (leader ? 1231 : 1237);
		result = prime * result + ((teacherUser == null) ? 0 : teacherUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseTeacherUser other = (CourseTeacherUser) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (leader != other.leader)
			return false;
		if (teacherUser == null) {
			if (other.teacherUser != null)
				return false;
		} else if (!teacherUser.equals(other.teacherUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseTeacherUser [teacherUser=" + teacherUser + ", course=" + course + ", leader=" + leader + "]";
	}
	
}
