package it.univaq.examifire.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("welcome");
		registry.addViewController("/index").setViewName("welcome");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/signin").setViewName("user_account/signin");
		registry.addViewController("/forgotpassword").setViewName("user_account/forgotpassword");
	}
	
}
