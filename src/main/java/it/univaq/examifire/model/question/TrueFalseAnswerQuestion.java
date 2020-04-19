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
	private Boolean answer;

	public Boolean getAnswer() {
		return answer;
	}

	public void setAnswer(Boolean answer) {
		this.answer = answer;
	}
	
}
