package it.univaq.examifire.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import it.univaq.examifire.model.audit.EntityAudit;

@Entity
@Table(name = "quiz")
public class Quiz extends EntityAudit<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quiz_id")
	private Long id;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<UserQuiz> userQuiz = new HashSet<>();
	
	
	@ManyToOne (fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "quiz_type_id", nullable = false, updatable = false)
    private QuizType quizType;
	
	@NotBlank(message = "Please enter the title")
	@Size(max = 45, message = "Maximum 45 characters")
	@Column(name = "first_name", nullable = false, length = 45)
	private String title;
}
