package it.univaq.examifire.model.course;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import it.univaq.examifire.model.Quiz;
import it.univaq.examifire.model.audit.EntityAudit;

@Entity
@Table(name = "course")
public class Course extends EntityAudit<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long id;

	@NotBlank(message = "Please enter the name")
	@Size(max = 45, message = "Maximum 45 characters")
	@Column(name = "name", nullable = false, length = 45)
	private String name;

	// @NotBlank(message = "Please enter the description")
	@Lob // the database column type is LONGTEXT
	@Column(name = "description", nullable = true)
	private String description;

	@NotBlank(message = "Please enter the cfu/ects")
	@Digits(integer = 2 /* precision */, fraction = 0 /* scale */)
	@PositiveOrZero(message = "Please enter a positive number or zero")
	@Column(name = "cfu_ects", nullable = false, precision = 2)
	private int cfu_ects;

	@NotBlank(message = "Please enter the academicYear")
	@Size(max = 45, message = "Maximum 45 characters")
	@Column(name = "academic_year", nullable = false, length = 9)
	private String academicYear;

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Part> parts = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<CourseRegistration> studentRegistrations = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<TeachingAppointment> teachingAppointments = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Quiz> quizzes = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCfu_ects() {
		return cfu_ects;
	}

	public void setCfu_ects(int cfu_ects) {
		this.cfu_ects = cfu_ects;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public Set<Part> getParts() {
		return parts;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}

	public Set<CourseRegistration> getStudentRegistrations() {
		return studentRegistrations;
	}

	public void setStudentRegistrations(Set<CourseRegistration> studentRegistrations) {
		this.studentRegistrations = studentRegistrations;
	}

	public Set<TeachingAppointment> getTeachingAppointments() {
		return teachingAppointments;
	}

	public void setTeachingAppointments(Set<TeachingAppointment> teachingAppointments) {
		this.teachingAppointments = teachingAppointments;
	}

	public Set<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(Set<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((academicYear == null) ? 0 : academicYear.hashCode());
		result = prime * result + cfu_ects;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parts == null) ? 0 : parts.hashCode());
		result = prime * result + ((quizzes == null) ? 0 : quizzes.hashCode());
		result = prime * result + ((studentRegistrations == null) ? 0 : studentRegistrations.hashCode());
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
		Course other = (Course) obj;
		if (academicYear == null) {
			if (other.academicYear != null)
				return false;
		} else if (!academicYear.equals(other.academicYear))
			return false;
		if (cfu_ects != other.cfu_ects)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parts == null) {
			if (other.parts != null)
				return false;
		} else if (!parts.equals(other.parts))
			return false;
		if (quizzes == null) {
			if (other.quizzes != null)
				return false;
		} else if (!quizzes.equals(other.quizzes))
			return false;
		if (studentRegistrations == null) {
			if (other.studentRegistrations != null)
				return false;
		} else if (!studentRegistrations.equals(other.studentRegistrations))
			return false;
		if (teachingAppointments == null) {
			if (other.teachingAppointments != null)
				return false;
		} else if (!teachingAppointments.equals(other.teachingAppointments))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", description=" + description + ", cfu_ects=" + cfu_ects
				+ ", academicYear=" + academicYear + ", parts=" + parts + ", studentRegistrations="
				+ studentRegistrations + ", teachingAppointments=" + teachingAppointments + ", quizzes=" + quizzes
				+ "]";
	}

}
