package it.univaq.examifire.model.question;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.QuizQuestion;
import it.univaq.examifire.model.StudentAnswer;
import it.univaq.examifire.model.audit.EntityAudit;
import it.univaq.examifire.model.course.Content;

/*
 * in order to detect the dynamic instance of a question in the presentation
 * layer, use e.g.,
 * <span th:if="${question instanceof T(it.univaq.examifire.model.question.OpenAnswerQuestion)}"
 * th:text="this is an open answer question"></span>
 */
@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question extends EntityAudit<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long id;

	@NotBlank(message = "Please enter the text")
	@Lob // the database column type is LONGTEXT
	@Column(name = "text", nullable = false)
	private String text;

	@Digits(integer = 2 /* precision */, fraction = 1 /* scale */)
	@Column(name = "penalty", columnDefinition = "DECIMAL(3,1) default 0.0", nullable = false, precision = 2, scale = 1)
	private BigDecimal penalty = new BigDecimal(0.0);

	@Lob // the database column type is LONGTEXT
	@Column(name = "private_notes", nullable = true)
	private String privateNotes;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "content_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Content courseContent;

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<QuizQuestion> quizQuestions = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<StudentAnswer> studentAnswers = new HashSet<>();

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<QuestionVersion> questionVersions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public String getPrivateNotes() {
		return privateNotes;
	}

	public void setPrivateNotes(String privateNotes) {
		this.privateNotes = privateNotes;
	}

	public Content getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(Content courseContent) {
		this.courseContent = courseContent;
	}

	public Set<QuizQuestion> getQuizQuestions() {
		return quizQuestions;
	}

	public void setQuizQuestions(Set<QuizQuestion> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}

	public Set<StudentAnswer> getStudentAnswers() {
		return studentAnswers;
	}

	public void setStudentAnswers(Set<StudentAnswer> studentAnswers) {
		this.studentAnswers = studentAnswers;
	}

	public Set<QuestionVersion> getQuestionVersions() {
		return questionVersions;
	}

	public void setQuestionVersions(Set<QuestionVersion> questionVersions) {
		this.questionVersions = questionVersions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseContent == null) ? 0 : courseContent.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((penalty == null) ? 0 : penalty.hashCode());
		result = prime * result + ((privateNotes == null) ? 0 : privateNotes.hashCode());
		result = prime * result + ((questionVersions == null) ? 0 : questionVersions.hashCode());
		result = prime * result + ((quizQuestions == null) ? 0 : quizQuestions.hashCode());
		result = prime * result + ((studentAnswers == null) ? 0 : studentAnswers.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Question other = (Question) obj;
		if (courseContent == null) {
			if (other.courseContent != null)
				return false;
		} else if (!courseContent.equals(other.courseContent))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (penalty == null) {
			if (other.penalty != null)
				return false;
		} else if (!penalty.equals(other.penalty))
			return false;
		if (privateNotes == null) {
			if (other.privateNotes != null)
				return false;
		} else if (!privateNotes.equals(other.privateNotes))
			return false;
		if (questionVersions == null) {
			if (other.questionVersions != null)
				return false;
		} else if (!questionVersions.equals(other.questionVersions))
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
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", penalty=" + penalty + ", privateNotes=" + privateNotes
				+ ", courseContent=" + courseContent + ", quizQuestions=" + quizQuestions + ", studentAnswers="
				+ studentAnswers + ", questionVersions=" + questionVersions + "]";
	}

}
