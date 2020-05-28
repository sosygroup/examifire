package it.univaq.examifire.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	/*@Bean
	public HttpTraceRepository htttpTraceRepository(){
	  return new InMemoryHttpTraceRepository();
	}*/

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("welcome");
		registry.addViewController("/index").setViewName("welcome");
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/signin").setViewName("account/signin");
		
		// Theme Features
		registry.addViewController("/home/admin/theme-features/bootstrap/typography").setViewName("theme-features/bootstrap/typography");
		registry.addViewController("/home/admin/theme-features/bootstrap/buttons").setViewName("theme-features/bootstrap/buttons");
		registry.addViewController("/home/admin/theme-features/bootstrap/button-groups").setViewName("theme-features/bootstrap/button-groups");
		registry.addViewController("/home/admin/theme-features/custom/utilities").setViewName("theme-features/custom/utilities");
	}

}
