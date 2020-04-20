package it.univaq.examifire.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "true_false_answer_question")
@PrimaryKeyJoinColumn(name = "question_id")
public class TrueFalseAnswerQuestion extends Question{
	@Column(name = "answer")
	private boolean answer;

	public boolean getAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	
}
