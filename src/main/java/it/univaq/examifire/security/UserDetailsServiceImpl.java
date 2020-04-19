package it.univaq.examifire.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.examifire.model.User;
import it.univaq.examifire.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Let people login with username 
		User user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserPrincipal.create(user);
	}

}
