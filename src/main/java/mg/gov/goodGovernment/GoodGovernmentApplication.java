package mg.gov.goodGovernment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GoodGovernmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodGovernmentApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(
				Arrays.asList(
						"http://localhost:4200",
						"http://localhost:8080",
						"https://admin-gg.herokuapp.com",
						"https://region-gg.herokuapp.com",
						"http://localhost:8100"
				)
		);
		corsConfiguration.setAllowedHeaders(
				Arrays.asList(
						"Origin",
						"Access-Control-Allow-Origin",
						"Content-Type",
						"Accept",
						"Authorization",
						"Origin, Accept",
						"X-Requested-With",
						"Access-Control-Request-Method",
						"Access-Control-Request-Headers"
				)
		);
		corsConfiguration.setExposedHeaders(
				Arrays.asList(
						"Origin",
						"Content-Type",
						"Accept",
						"Authorization",
						"Access-Control-Allow-Origin",
						"Access-Control-Allow-Origin",
						"Access-Control-Allow-Credentials"
				)
		);
		corsConfiguration.setAllowedMethods(
				Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")
		);
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
