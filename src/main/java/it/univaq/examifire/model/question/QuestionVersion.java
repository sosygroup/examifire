package it.univaq.examifire.model.question;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.audit.EntityAudit;

@Entity
@Table(name = "question_version")
public class QuestionVersion extends EntityAudit<Long> implements Serializable{
	private static final long serialVersionUID = 5836461020806281888L;

	@Id
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "question_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Question question;
	
	@Id
	@CreatedDate
	@Column(name = "version_data", nullable = false, updatable = false)
	private LocalDateTime versionData;
	
	@NotBlank(message = "Please enter the description")
	@Lob // the database column type is LONGTEXT
	@Column(name = "comment", nullable = true)
	private String comment;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public LocalDateTime getVersionData() {
		return versionData;
	}

	public void setVersionData(LocalDateTime versionData) {
		this.versionData = versionData;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((versionData == null) ? 0 : versionData.hashCode());
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
		QuestionVersion other = (QuestionVersion) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (versionData == null) {
			if (other.versionData != null)
				return false;
		} else if (!versionData.equals(other.versionData))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuestionVersion [question=" + question + ", versionData=" + versionData + ", comment=" + comment + "]";
	}
	
	

}
