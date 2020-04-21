package it.univaq.examifire.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "true_false_answer_question")
@PrimaryKeyJoinColumn(name = "question_id")
public class TrueFalseAnswerQuestion extends Question {

	@NotBlank(message = "Please specify the correct answer")
	@Column(name = "correct_answer", nullable = false)
	private boolean correctAnswer;

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (correctAnswer ? 1231 : 1237);
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
		TrueFalseAnswerQuestion other = (TrueFalseAnswerQuestion) obj;
		if (correctAnswer != other.correctAnswer)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrueFalseAnswerQuestion [correctAnswer=" + correctAnswer + "]";
	}

}
