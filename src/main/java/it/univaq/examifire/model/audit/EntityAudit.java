package it.univaq.examifire.model.audit;

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
@JsonIgnoreProperties(value = { "createdBy", "createdAt", "lastUpdatedBy","lastUpdatedAt"}, allowGetters = true)
public abstract class EntityAudit<U> {
	@CreatedBy
	@Column(name = "created_by", nullable = true, updatable = true)
	private U createdBy;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@LastModifiedBy
	@Column(name = "last_updated_by", nullable = true, updatable = true)
	private U lastUpdatedBy;

	@LastModifiedDate
	@Column(name = "last_updated_at", nullable = false, updatable = true)
	private Instant lastUpdatedAt;

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

	public U getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(U lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Instant getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Instant lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}


	
}
