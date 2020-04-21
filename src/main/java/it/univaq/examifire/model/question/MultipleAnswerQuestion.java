package it.univaq.examifire.model.question;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "multiple_answer_question")
@PrimaryKeyJoinColumn(name = "question_id")
public class MultipleAnswerQuestion extends Question {

	// TODO create a cross field custom validator that checks that the sum of
	// answerChoiceFractionGrade for each MultipleAnswerChoice is 100. Not sure if
	// it is
	// class-level validation or a field-level one

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "multipleAnswerQuestion", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<AnswerChoice> answerChoices;

	public Set<AnswerChoice> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(Set<AnswerChoice> answerChoices) {
		this.answerChoices = answerChoices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answerChoices == null) ? 0 : answerChoices.hashCode());
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
		MultipleAnswerQuestion other = (MultipleAnswerQuestion) obj;
		if (answerChoices == null) {
			if (other.answerChoices != null)
				return false;
		} else if (!answerChoices.equals(other.answerChoices))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MultipleAnswerQuestion [answerChoices=" + answerChoices + "]";
	}

}
