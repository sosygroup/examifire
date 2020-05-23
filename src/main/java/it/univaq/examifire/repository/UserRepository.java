package it.univaq.examifire.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import it.univaq.examifire.model.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	// @Query("select u from User u where u.email = :email")
	// User findByEmail(@Param("email") String email);
	Optional<User> findByEmail(String email);
	

}
