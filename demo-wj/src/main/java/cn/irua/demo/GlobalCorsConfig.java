package cn.irua.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {
	private CorsConfiguration corsConfig() {
	    CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.addAllowedOrigin("*");
	    corsConfiguration.addAllowedHeader("*");
	    corsConfiguration.addAllowedMethod("*");
	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.setMaxAge(3600L);
	    return corsConfiguration;
	}
	@Bean
	public CorsFilter corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfig());
	    return new CorsFilter(source);
	}
}
