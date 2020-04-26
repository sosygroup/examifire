package it.univaq.examifire.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.univaq.examifire.security.UserDetailsServiceImpl;

/*
 * TODO check if use the annotation @EnableGlobalMethodSecurity(securedEnabled =
 * true, proxyTargetClass = true)
 */

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String ROLE_ADMIN = "ADMIN";
	
	@Autowired
    private UserDetailsServiceImpl customUserDetailsServiceImpl;

	/*
	 * needed by the token repository to create, search, update and delete the
	 * persistent token from the database
	 */
	@Autowired
	private DataSource dataSource;

	/*
	 * TODO instead of to use this bean, check if you can use new
	 * BCryptPasswordEncoder() in the configureGlobal method
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
			/*
			 * Our recommendation is to use CSRF protection for any request that could be
			 * processed by a browser by normal users. If you are only creating a service
			 * that is used by non-browser clients, you will likely want to disable CSRF
			 * protection. What is the reason to disable csrf in a Spring Boot application?
			 * (1) You are using another token mechanism. (2) You want to simplify
			 * interactions between a client and the server. NOTE: the matchers are
			 * considered in order
			 */
	        http         
	         .headers()
	          .frameOptions().sameOrigin()
	          .and()
	            .authorizeRequests()
	             .antMatchers("/resources/**", "/assets/**","/my-assets/**").permitAll()
	                .antMatchers("/").permitAll()
	                .antMatchers("/index").permitAll()
	                .antMatchers("/signup").permitAll()
	                .antMatchers("/forgotpassword").permitAll()
	                .antMatchers("/admin/**").hasRole(ROLE_ADMIN)
	                .antMatchers("/users/**").hasRole(ROLE_ADMIN)
	                .anyRequest().authenticated()
	                .and()
	            .formLogin()
	                .loginPage("/signin")
	                .defaultSuccessUrl("/home")
	                .failureUrl("/signin?error")
	                .permitAll()
	                .and()
	            .logout()
	             .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	             .logoutSuccessUrl("/signin?logout")
	             .deleteCookies("my-remember-me-cookie")
	                .permitAll()
	                .and()
	             .rememberMe()
	              //.key("my-secure-key")
	              .rememberMeCookieName("my-remember-me-cookie")
	              .tokenRepository(persistentTokenRepository())
	              .tokenValiditySeconds(24 * 60 * 60) //24 hours
	              .and()
	            .exceptionHandling();
	    }
	    
		PersistentTokenRepository persistentTokenRepository() {
			JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
			tokenRepositoryImpl.setDataSource(dataSource);
			return tokenRepositoryImpl;
		}

}
