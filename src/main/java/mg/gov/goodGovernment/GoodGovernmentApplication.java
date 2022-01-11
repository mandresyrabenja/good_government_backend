package mg.gov.goodGovernment;

import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GoodGovernmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodGovernmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RegionService service) {
		return args -> {
			// Regions de test
			Region analamanga = new Region("Analamanga", "1234");
			Region itasy = new Region("Itasy", "0000");

			// Ajout des regions aux base de données
			service.addRegion(analamanga);
			service.addRegion(itasy);
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
