package com.api.growin;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class GrowinServletInitializer extends SpringBootServletInitializer {
	 @Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
			return builder.sources(GrowinApplication.class);
	 }
}
