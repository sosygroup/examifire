package it.univaq.examifire.model.question;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "multiple_answer_question")
@PrimaryKeyJoinColumn(name = "question_id")
public class MultipleAnswerQuestion extends Question {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "question_answer", joinColumns = { @JoinColumn(name = "question_id") }, inverseJoinColumns = {
			@JoinColumn(name = "answer_id") })
	private Set<MultipleAnswerChoice> multipleAnswerChoices; // nell'associazione aggiungere answer_fraction_grade; 

	
	

}
