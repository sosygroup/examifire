package it.univaq.examifire.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import it.univaq.examifire.model.QuizRegistration;
import it.univaq.examifire.model.StudentAnswer;
import it.univaq.examifire.model.course.CourseRegistration;

@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {
	

	@Column(name = "identification_number", nullable = true, unique = true)
	private String identificationNumber;

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<CourseRegistration> courseRegistrations = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<QuizRegistration> quizRegistrations = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<StudentAnswer> studentAnswers = new HashSet<>();

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public Set<CourseRegistration> getCourseRegistrations() {
		return courseRegistrations;
	}

	public void setCourseRegistrations(Set<CourseRegistration> courseRegistrations) {
		this.courseRegistrations = courseRegistrations;
	}

	public Set<QuizRegistration> getQuizRegistrations() {
		return quizRegistrations;
	}

	public void setQuizRegistrations(Set<QuizRegistration> quizRegistrations) {
		this.quizRegistrations = quizRegistrations;
	}

	public Set<StudentAnswer> getStudentAnswers() {
		return studentAnswers;
	}

	public void setStudentAnswers(Set<StudentAnswer> studentAnswers) {
		this.studentAnswers = studentAnswers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courseRegistrations == null) ? 0 : courseRegistrations.hashCode());
		result = prime * result + ((identificationNumber == null) ? 0 : identificationNumber.hashCode());
		result = prime * result + ((quizRegistrations == null) ? 0 : quizRegistrations.hashCode());
		result = prime * result + ((studentAnswers == null) ? 0 : studentAnswers.hashCode());
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
		Student other = (Student) obj;
		if (courseRegistrations == null) {
			if (other.courseRegistrations != null)
				return false;
		} else if (!courseRegistrations.equals(other.courseRegistrations))
			return false;
		if (identificationNumber == null) {
			if (other.identificationNumber != null)
				return false;
		} else if (!identificationNumber.equals(other.identificationNumber))
			return false;
		if (quizRegistrations == null) {
			if (other.quizRegistrations != null)
				return false;
		} else if (!quizRegistrations.equals(other.quizRegistrations))
			return false;
		if (studentAnswers == null) {
			if (other.studentAnswers != null)
				return false;
		} else if (!studentAnswers.equals(other.studentAnswers))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Student [identificationNumber=" + identificationNumber + ", courseRegistrations=" + courseRegistrations
				+ ", quizRegistrations=" + quizRegistrations + ", studentAnswers=" + studentAnswers + ", getId()="
				+ getId() + ", getFirstname()=" + getFirstname() + ", getLastname()=" + getLastname() + ", getEmail()="
				+ getEmail() + ", isAccountEnabled()=" + isAccountEnabled() + ", isPasswordNonExpired()="
				+ isPasswordNonExpired() + ", getRoles()=" + getRoles() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getLastUpdatedBy()=" + getLastUpdatedBy()
				+ ", getLastUpdatedAt()=" + getLastUpdatedAt() + "]";
	}

	

	

}
