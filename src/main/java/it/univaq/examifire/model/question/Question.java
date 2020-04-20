package it.univaq.examifire.model.question;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import it.univaq.examifire.model.audit.EntityAudit;

/*
 * in order to detect the dynamic instance of a question in the presentation
 * layer, use e.g.,
 * <span th:if="${question instanceof T(it.univaq.examifire.model.question.OpenAnswerQuestion)}"
 * th:text="this is an open answer question"></span>
 */
@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Question extends EntityAudit<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long id;

	@NotBlank(message = "Please enter the text")
	@Lob // the database column type is LONGTEXT
	@Column(name = "text", nullable = false)
	private String text;

	@Digits(integer = 2 /* precision */, fraction = 1 /* scale */)
	@Column(name = "penalty", columnDefinition = "DECIMAL(3,1) default 0.0", nullable = false, precision = 2, scale = 1)
	private BigDecimal penalty = new BigDecimal(0.0);

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<QuestionVersion> questionVersions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public Set<QuestionVersion> getQuestionVersions() {
		return questionVersions;
	}

	public void setQuestionVersions(Set<QuestionVersion> questionVersions) {
		this.questionVersions = questionVersions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((penalty == null) ? 0 : penalty.hashCode());
		result = prime * result + ((questionVersions == null) ? 0 : questionVersions.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		Question other = (Question) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (penalty == null) {
			if (other.penalty != null)
				return false;
		} else if (!penalty.equals(other.penalty))
			return false;
		if (questionVersions == null) {
			if (other.questionVersions != null)
				return false;
		} else if (!questionVersions.equals(other.questionVersions))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", penalty=" + penalty + ", questionVersions="
				+ questionVersions + "]";
	}

}
