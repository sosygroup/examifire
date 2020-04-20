package it.univaq.examifire.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import it.univaq.examifire.model.course.CourseStudentUser;

@Entity
@Table(name = "student_user")
@PrimaryKeyJoinColumn(name = "user_id")
public class StudentUser extends User {

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "studentUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<CourseStudentUser> courseStudentUser = new HashSet<>();

	public Set<CourseStudentUser> getCourseStudentUser() {
		return courseStudentUser;
	}

	public void setCourseStudentUser(Set<CourseStudentUser> courseStudentUser) {
		this.courseStudentUser = courseStudentUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courseStudentUser == null) ? 0 : courseStudentUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentUser other = (StudentUser) obj;
		if (courseStudentUser == null) {
			if (other.courseStudentUser != null)
				return false;
		} else if (!courseStudentUser.equals(other.courseStudentUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StudentUser [courseStudentUser=" + courseStudentUser + "]";
	}

}
