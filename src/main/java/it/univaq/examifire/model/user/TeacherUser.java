package it.univaq.examifire.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import it.univaq.examifire.model.course.CourseTeacherUser;

@Entity
@Table(name = "teacher_user")
@PrimaryKeyJoinColumn(name = "user_id")
public class TeacherUser extends User {
	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "teacherUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<CourseTeacherUser> courseTeacherUsers = new HashSet<>();

	public Set<CourseTeacherUser> getCourseTeacherUsers() {
		return courseTeacherUsers;
	}

	public void setCourseTeacherUsers(Set<CourseTeacherUser> courseTeacherUsers) {
		this.courseTeacherUsers = courseTeacherUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courseTeacherUsers == null) ? 0 : courseTeacherUsers.hashCode());
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
		TeacherUser other = (TeacherUser) obj;
		if (courseTeacherUsers == null) {
			if (other.courseTeacherUsers != null)
				return false;
		} else if (!courseTeacherUsers.equals(other.courseTeacherUsers))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TeacherUser [courseTeacherUsers=" + courseTeacherUsers + "]";
	}
	

}
