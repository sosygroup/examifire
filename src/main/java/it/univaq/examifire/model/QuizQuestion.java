package it.univaq.examifire.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
import it.univaq.examifire.model.question.Question;

@Entity
@Table(name = "quiz_question")
public class QuizQuestion extends EntityAudit<Long> implements Serializable {
	private static final long serialVersionUID = -852059341405165211L;

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

	/*
	 * in the database the resulting column will be DECIMAL (3,1), i.e., a total of
	 * 3 digits, 1 of which after the decimal point (and 14 before the decimal
	 * point).
	 */
	@Digits(integer = 2 /* precision */, fraction = 1 /* scale */)
	@Column(name = "grade", nullable = true, precision = 2, scale = 1)
	private BigDecimal grade;

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
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((quiz == null) ? 0 : quiz.hashCode());
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
		QuizQuestion other = (QuizQuestion) obj;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
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
		return true;
	}

	@Override
	public String toString() {
		return "QuizQuestion [quiz=" + quiz + ", question=" + question + ", grade=" + grade + "]";
	}

}
