package it.univaq.examifire.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.univaq.examifire.model.User;
import it.univaq.examifire.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
		builder.disabled(!user.isActive());
		builder.accountExpired(user.isPasswordExpired());
		builder.password(user.getPassword());
		builder.authorities(
				user.getRoles().stream().map(
						role -> new SimpleGrantedAuthority("ROLE_"+role.getName())).collect(Collectors.toList()));
		return builder.build();
	}

}
