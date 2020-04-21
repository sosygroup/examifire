package it.univaq.examifire.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.audit.EntityAudit;
import it.univaq.examifire.model.course.Course;

@Entity
@Table(name = "quiz")
public class Quiz extends EntityAudit<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_id")
	private Long id;

	@NotBlank(message = "Please enter the title")
	@Lob // the database column type is LONGTEXT
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "time_open", nullable = true)
	private LocalDateTime timeOpen;

	@Column(name = "time_close", nullable = true)
	private LocalDateTime timeClose;

	@NotBlank(message = "Please enter the duration in minutes")
	@Digits(integer = 2 /* precision */, fraction = 0 /* scale */)
	@Positive(message = "Please enter a positive number")
	@Column(name = "duration", nullable = false, precision = 2)
	private int duration;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "quiz_type_id", nullable = false, updatable = false)
	private QuizType type;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "course_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Course course;

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<QuizQuestion> quizQuestions = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<QuizRegistration> studentRegistrations = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<StudentAnswer> studentAnswers = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(LocalDateTime timeOpen) {
		this.timeOpen = timeOpen;
	}

	public LocalDateTime getTimeClose() {
		return timeClose;
	}

	public void setTimeClose(LocalDateTime timeClose) {
		this.timeClose = timeClose;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public QuizType getType() {
		return type;
	}

	public void setType(QuizType type) {
		this.type = type;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Set<QuizQuestion> getQuizQuestions() {
		return quizQuestions;
	}

	public void setQuizQuestions(Set<QuizQuestion> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}

	public Set<QuizRegistration> getStudentRegistrations() {
		return studentRegistrations;
	}

	public void setStudentRegistrations(Set<QuizRegistration> studentRegistrations) {
		this.studentRegistrations = studentRegistrations;
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
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + duration;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((quizQuestions == null) ? 0 : quizQuestions.hashCode());
		result = prime * result + ((studentAnswers == null) ? 0 : studentAnswers.hashCode());
		result = prime * result + ((studentRegistrations == null) ? 0 : studentRegistrations.hashCode());
		result = prime * result + ((timeClose == null) ? 0 : timeClose.hashCode());
		result = prime * result + ((timeOpen == null) ? 0 : timeOpen.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Quiz other = (Quiz) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (duration != other.duration)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (quizQuestions == null) {
			if (other.quizQuestions != null)
				return false;
		} else if (!quizQuestions.equals(other.quizQuestions))
			return false;
		if (studentAnswers == null) {
			if (other.studentAnswers != null)
				return false;
		} else if (!studentAnswers.equals(other.studentAnswers))
			return false;
		if (studentRegistrations == null) {
			if (other.studentRegistrations != null)
				return false;
		} else if (!studentRegistrations.equals(other.studentRegistrations))
			return false;
		if (timeClose == null) {
			if (other.timeClose != null)
				return false;
		} else if (!timeClose.equals(other.timeClose))
			return false;
		if (timeOpen == null) {
			if (other.timeOpen != null)
				return false;
		} else if (!timeOpen.equals(other.timeOpen))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", title=" + title + ", timeOpen=" + timeOpen + ", timeClose=" + timeClose
				+ ", duration=" + duration + ", type=" + type + ", course=" + course + ", quizQuestions="
				+ quizQuestions + ", studentRegistrations=" + studentRegistrations + ", studentAnswers="
				+ studentAnswers + "]";
	}

}
