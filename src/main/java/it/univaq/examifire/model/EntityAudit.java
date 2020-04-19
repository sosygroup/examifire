package it.univaq.examifire.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * In any business application, auditing simply means tracking and logging every
 * change we do in our persisted records, which simply means tracking every
 * insert, update, and delete operation and storing it.
 * 
 * Auditing helps us in maintaining history records, which can later help us in
 * tracking user activities. If implemented properly, auditing can also provide
 * us similar functionality to version control systems.
 */

/*
 * if you need to manage auditing to log (versioning) all changes use Hibernate Envers
 * https://www.baeldung.com/database-auditing-jpa. With Hibernate, we could make
 * use of Interceptors and EventListeners as well as database triggers to
 * accomplish auditing. But the ORM framework offers Envers, a module
 * implementing auditing and versioning of persistent classes.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class EntityAudit<U>  {
    @CreatedBy
    protected U createdBy;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@LastModifiedBy
    protected U lastModifiedBy;
	
	@LastModifiedDate
	@Column(nullable = false, updatable = true)
	private Instant updatedAt;

	public U getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public U getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(U lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
}
