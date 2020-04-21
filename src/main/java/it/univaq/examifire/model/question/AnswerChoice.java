package it.univaq.examifire.model.question;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "answer_choice")
public class AnswerChoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_choice_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "question_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private MultipleAnswerQuestion multipleAnswerQuestion;

	// This is the correct answer
	@Lob // the database column type is LONGTEXT
	@Column(name = "answer", nullable = true)
	private String answer;

	@Digits(integer = 2 /* precision */, fraction = 1 /* scale */)
	@Column(name = "fraction_grade", columnDefinition = "DECIMAL(3,1) default 0.0", nullable = false, precision = 2, scale = 1)
	private BigDecimal fractionGrade = new BigDecimal(0.0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MultipleAnswerQuestion getMultipleAnswerQuestion() {
		return multipleAnswerQuestion;
	}

	public void setMultipleAnswerQuestion(MultipleAnswerQuestion multipleAnswerQuestion) {
		this.multipleAnswerQuestion = multipleAnswerQuestion;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public BigDecimal getFractionGrade() {
		return fractionGrade;
	}

	public void setFractionGrade(BigDecimal fractionGrade) {
		this.fractionGrade = fractionGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((fractionGrade == null) ? 0 : fractionGrade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((multipleAnswerQuestion == null) ? 0 : multipleAnswerQuestion.hashCode());
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
		AnswerChoice other = (AnswerChoice) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (fractionGrade == null) {
			if (other.fractionGrade != null)
				return false;
		} else if (!fractionGrade.equals(other.fractionGrade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (multipleAnswerQuestion == null) {
			if (other.multipleAnswerQuestion != null)
				return false;
		} else if (!multipleAnswerQuestion.equals(other.multipleAnswerQuestion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AnswerChoice [id=" + id + ", multipleAnswerQuestion=" + multipleAnswerQuestion + ", answer=" + answer
				+ ", fractionGrade=" + fractionGrade + "]";
	}

}
