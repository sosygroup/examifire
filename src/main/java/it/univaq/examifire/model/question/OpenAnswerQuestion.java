package it.univaq.examifire.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "open_answer_question")
@PrimaryKeyJoinColumn(name = "question_id")
public class OpenAnswerQuestion extends Question{
	@Column(name = "answer")
	private String answer;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
