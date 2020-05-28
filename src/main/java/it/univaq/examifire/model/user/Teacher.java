package it.univaq.examifire.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import it.univaq.examifire.model.course.CourseTeacher;

@Entity
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {
	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<CourseTeacher> courses = new HashSet<>();

	public Set<CourseTeacher> getCourses() {
		return courses;
	}

	public void setCourses(Set<CourseTeacher> courses) {
		this.courses = courses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
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
		Teacher other = (Teacher) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Teacher [courses=" + courses + ", getId()=" + getId() + ", getFirstname()="
				+ getFirstname() + ", getLastname()=" + getLastname() + ", getEmail()=" + getEmail()
				+ ", isAccountEnabled()=" + isAccountEnabled() + ", isPasswordNonExpired()=" + isPasswordNonExpired()
				+ ", getRoles()=" + getRoles() + ", getCreatedBy()=" + getCreatedBy() + ", getCreatedAt()="
				+ getCreatedAt() + ", getLastUpdatedBy()=" + getLastUpdatedBy() + ", getLastUpdatedAt()="
				+ getLastUpdatedAt() + "]";
	}

	

	

}
