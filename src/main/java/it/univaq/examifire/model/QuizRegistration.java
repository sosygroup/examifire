package it.univaq.examifire.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.audit.EntityAudit;
import it.univaq.examifire.model.user.Student;

// represent the subscription and the 
@Entity
@Table(name = "quiz_registration")
public class QuizRegistration extends EntityAudit<Long> implements Serializable {
	private static final long serialVersionUID = 239667744691558120L;

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
	@JoinColumn(name = "quiz_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Quiz quiz;

	// timestamp that specify when the user book the quiz
	@Column(name = "quiz_booking_time", nullable = true)
	private LocalDateTime quizBookingTime;

	@Column(name = "quiz_start_time", nullable = true)
	private LocalDateTime quizStartTime;

	@Column(name = "quiz_end_time", nullable = true)
	private LocalDateTime quizEndTime;

	@Column(name = "grade_registration_time", nullable = true)
	private LocalDateTime gradeRegistrationTime;
	/*
	 * in the database the resulting column will be DECIMAL (3,1), i.e., a total of
	 * 3 digits, 1 of which after the decimal point (and 14 before the decimal
	 * point).
	 */
	@Digits(integer = 2 /* precision */, fraction = 1 /* scale */)
	@Column(name = "grade", nullable = true, precision = 2, scale = 1)
	private BigDecimal grade;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public LocalDateTime getQuizBookingTime() {
		return quizBookingTime;
	}

	public void setQuizBookingTime(LocalDateTime quizBookingTime) {
		this.quizBookingTime = quizBookingTime;
	}

	public LocalDateTime getQuizStartTime() {
		return quizStartTime;
	}

	public void setQuizStartTime(LocalDateTime quizStartTime) {
		this.quizStartTime = quizStartTime;
	}

	public LocalDateTime getQuizEndTime() {
		return quizEndTime;
	}

	public void setQuizEndTime(LocalDateTime quizEndTime) {
		this.quizEndTime = quizEndTime;
	}

	public LocalDateTime getGradeRegistrationTime() {
		return gradeRegistrationTime;
	}

	public void setGradeRegistrationTime(LocalDateTime gradeRegistrationTime) {
		this.gradeRegistrationTime = gradeRegistrationTime;
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(BigDecimal grade) {
		this.grade = grade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((gradeRegistrationTime == null) ? 0 : gradeRegistrationTime.hashCode());
		result = prime * result + ((quiz == null) ? 0 : quiz.hashCode());
		result = prime * result + ((quizBookingTime == null) ? 0 : quizBookingTime.hashCode());
		result = prime * result + ((quizEndTime == null) ? 0 : quizEndTime.hashCode());
		result = prime * result + ((quizStartTime == null) ? 0 : quizStartTime.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		QuizRegistration other = (QuizRegistration) obj;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (gradeRegistrationTime == null) {
			if (other.gradeRegistrationTime != null)
				return false;
		} else if (!gradeRegistrationTime.equals(other.gradeRegistrationTime))
			return false;
		if (quiz == null) {
			if (other.quiz != null)
				return false;
		} else if (!quiz.equals(other.quiz))
			return false;
		if (quizBookingTime == null) {
			if (other.quizBookingTime != null)
				return false;
		} else if (!quizBookingTime.equals(other.quizBookingTime))
			return false;
		if (quizEndTime == null) {
			if (other.quizEndTime != null)
				return false;
		} else if (!quizEndTime.equals(other.quizEndTime))
			return false;
		if (quizStartTime == null) {
			if (other.quizStartTime != null)
				return false;
		} else if (!quizStartTime.equals(other.quizStartTime))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuizRegistration [student=" + student + ", quiz=" + quiz + ", quizBookingTime=" + quizBookingTime
				+ ", quizStartTime=" + quizStartTime + ", quizEndTime=" + quizEndTime + ", gradeRegistrationTime="
				+ gradeRegistrationTime + ", grade=" + grade + "]";
	}

}
