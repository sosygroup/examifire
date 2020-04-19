package it.univaq.examifire.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "multiple_answer_choice")
public class MultipleAnswerChoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "multiple_answer_choice_id")
	private Long id;
	
	@Column(name = "answer_text")
	private String answerText;
}
