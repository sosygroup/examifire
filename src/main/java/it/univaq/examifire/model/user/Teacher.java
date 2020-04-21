package it.univaq.examifire.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import it.univaq.examifire.model.course.TeachingAppointment;

@Entity
@Table(name = "teacher")
@PrimaryKeyJoinColumn(name = "user_id")
public class Teacher extends User {
	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<TeachingAppointment> teachingAppointments = new HashSet<>();

	public Set<TeachingAppointment> getTeachingAppointments() {
		return teachingAppointments;
	}

	public void setTeachingAppointments(Set<TeachingAppointment> teachingAppointments) {
		this.teachingAppointments = teachingAppointments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teachingAppointments == null) ? 0 : teachingAppointments.hashCode());
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
		Teacher other = (Teacher) obj;
		if (teachingAppointments == null) {
			if (other.teachingAppointments != null)
				return false;
		} else if (!teachingAppointments.equals(other.teachingAppointments))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Teacher [teachingAppointments=" + teachingAppointments + "]";
	}

}
