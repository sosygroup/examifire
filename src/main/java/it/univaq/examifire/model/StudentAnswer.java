package it.univaq.examifire.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.audit.EntityAudit;
import it.univaq.examifire.model.question.Question;
import it.univaq.examifire.model.user.Student;

@Entity
@Table(name = "student_answer")
public class StudentAnswer extends EntityAudit<Long> implements Serializable {
	private static final long serialVersionUID = -8059956406404349357L;

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

	@Id
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "question_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Question question;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "answer_type_id", nullable = false, updatable = false)
	private AnswerType type;

	// This is the student answer
	@Lob // the database column type is LONGTEXT
	@Column(name = "answer", nullable = true)
	private String answer;

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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public AnswerType getType() {
		return type;
	}

	public void setType(AnswerType type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((quiz == null) ? 0 : quiz.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		StudentAnswer other = (StudentAnswer) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (quiz == null) {
			if (other.quiz != null)
				return false;
		} else if (!quiz.equals(other.quiz))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
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
		return "StudentAnswer [student=" + student + ", quiz=" + quiz + ", question=" + question + ", type=" + type
				+ ", answer=" + answer + "]";
	}

}
