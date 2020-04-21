package it.univaq.examifire.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "open_answer_question")
@PrimaryKeyJoinColumn(name = "question_id")
public class OpenAnswerQuestion extends Question {

	// This is the correct answer
	@Lob // the database column type is LONGTEXT
	@Column(name = "correct_answer", nullable = true)
	private String correctAnswer;

	// this is the number of rows the student's answer is expected to fit in
	@NotBlank(message = "Please specify the number of rows the student's answer is expected to fit in")
	@Column(name = "expected_answer_number_of_rows", nullable = false)
	private int expectedAnswerNumberOfRows = 4;

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public int getExpectedAnswerNumberOfRows() {
		return expectedAnswerNumberOfRows;
	}

	public void setExpectedAnswerNumberOfRows(int expectedAnswerNumberOfRows) {
		this.expectedAnswerNumberOfRows = expectedAnswerNumberOfRows;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correctAnswer == null) ? 0 : correctAnswer.hashCode());
		result = prime * result + expectedAnswerNumberOfRows;
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
		OpenAnswerQuestion other = (OpenAnswerQuestion) obj;
		if (correctAnswer == null) {
			if (other.correctAnswer != null)
				return false;
		} else if (!correctAnswer.equals(other.correctAnswer))
			return false;
		if (expectedAnswerNumberOfRows != other.expectedAnswerNumberOfRows)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OpenAnswerQuestion [correctAnswer=" + correctAnswer + ", expectedAnswerNumberOfRows="
				+ expectedAnswerNumberOfRows + "]";
	}

}
