package it.univaq.examifire.model.course;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.univaq.examifire.model.audit.EntityAudit;

@Entity
@Table(name = "part")
public class Part extends EntityAudit<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "part_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "course_id", nullable = false, updatable = false)
	@JsonIgnore // @JsonIgnore is used to solve infinite recursion issue caused by bidirectional
				// relationship
	private Course course;

	@NotBlank(message = "Please enter the name")
	@Size(max = 45, message = "Maximum 45 characters")
	@Column(name = "name", nullable = false, length = 45)
	private String name;

	// @NotBlank(message = "Please enter the description")
	@Lob // the database column type is LONGTEXT
	@Column(name = "description", nullable = true)
	private String description;

	// @OneToMany(mappedBy = "variableName") variableName is the name of the
	// variable annotated with @ManyToOne
	@OneToMany(mappedBy = "part", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Content> contents = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Content> getContents() {
		return contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;

		Part other = (Part) obj;

		if (contents == null) {
			if (other.contents != null) return false;
		} else if (!contents.equals(other.contents)) return false;

		if (course == null) {
			if (other.course != null) return false;
		} else if (!course.equals(other.course)) return false;

		if (description == null) {
			if (other.description != null) return false;
		} else if (!description.equals(other.description)) return false;

		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;

		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;

		return true;
	}

	@Override
	public String toString() {
		return "Part [id=" + id + ", course=" + course + ", name=" + name + ", description=" + description
				+ ", contents=" + contents + "]";
	}

}
