package it.univaq.examifire.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import it.univaq.examifire.model.user.User;
import it.univaq.examifire.service.UserService;

@Component
public class UserAuthenticationUpdater {
	// TODO check if it is a solution:
	// - can I change the user information directly from an authentication object ?
	// - what are the best practices and security concern about this solution ?
	// Note that, we need to add a public void update(User user) {...} method inside UserPrincipal class  
	@Autowired
	private UserService userService;
	
	public void update(Authentication authentication) {
		Long authenticatedUserId = ((UserPrincipal)authentication.getPrincipal()).getId();
		User user = userService.findById(authenticatedUserId).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + authenticatedUserId));;
		((UserPrincipal)authentication.getPrincipal()).update(user);
		return;
	}
}
