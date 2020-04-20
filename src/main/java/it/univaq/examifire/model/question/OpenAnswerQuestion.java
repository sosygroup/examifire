package it.univaq.examifire.model.question;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "open_answer_question")
@PrimaryKeyJoinColumn(name = "question_id")
public class OpenAnswerQuestion extends Question {
	// TODO externalize the property
	@Transient
	private int DEFAULT_ANSWER_SPACE_LENGHT = 160;
	
	// This is the correct answer
	@Lob // the database column type is LONGTEXT
	@Column(name = "correct_answer", nullable = true)
	private String correctAnswer;

	// This is the expected correct answer
	// @NotBlank(message = "Please edit the answer space")
	@Lob // the database column type is LONGTEXT
	@Column(name = "answer_space", nullable = false)
	private String answerSpace = new String(StringUtils.repeat(' ', DEFAULT_ANSWER_SPACE_LENGHT));
	


}
