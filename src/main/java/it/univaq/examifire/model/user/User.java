package it.univaq.examifire.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import it.univaq.examifire.model.audit.EntityAudit;


@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends EntityAudit<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	// @NotBlank(message = "{user.firstname.invalid}") externalize validation
	// messages in the ValidationMessages.properties e.g.,
	// user.firstname.invalid='${validatedValue}' is an invalid username. It must be
	// minimum {min} chars and maximum {max} chars.
	@NotBlank(message = "Please enter the firstname")
	@Size(max = 45, message = "Maximum 45 characters")
	@Column(name = "first_name", nullable = false, length = 45)
	private String firstname;

	// TODO Username can only contain letters, numbers and underscores (and no spaces!)
	@NotBlank(message = "Please enter the lastname")
	@Size(max = 45, message = "Maximum 45 characters")
	@Column(name = "last_name", nullable = false, length = 45)
	private String lastname;
	
	@NotBlank(message = "Please enter the username")
	@Size(max = 32, min = 5, message = "Minimum 5 characters and maximum 32 characters")
	@Column(name = "username", nullable = false, unique = true, length = 32)
	@Pattern(regexp = "^[a-zA-Z0-9]+[_\\.\\-]?[a-zA-Z0-9]+$", message = "Please use only alpha numeric characters, possibly with either '_', '-' or '.' in between")
	private String username;

	
	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@NotBlank(message = "Please enter the email")
	@Size(max = 50, message = "Maximum 50 characters")
	@Email(message = "Invalid email")
	@Column(name = "email", unique = true, length = 50)
	private String email;

	// this means that the user is both active and enabled
	@Column(name = "account_enabled", columnDefinition = "boolean default true", nullable = false)
	private boolean accountEnabled = true;

	@Column(name = "password_non_expired", columnDefinition = "boolean default false", nullable = false)
	private boolean passwordNonExpired = true;

	@NotEmpty(message = "Please select at least one role")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAccountEnabled() {
		return accountEnabled;
	}

	public void setAccountEnabled(boolean accountEnabled) {
		this.accountEnabled = accountEnabled;
	}

	public boolean isPasswordNonExpired() {
		return passwordNonExpired;
	}

	public void setPasswordNonExpired(boolean passwordNonExpired) {
		this.passwordNonExpired = passwordNonExpired;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountEnabled ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (passwordNonExpired ? 1231 : 1237);
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (accountEnabled != other.accountEnabled)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordNonExpired != other.passwordNonExpired)
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", accountEnabled=" + accountEnabled
				+ ", passwordNonExpired=" + passwordNonExpired + ", roles=" + roles + "]";
	}

}
