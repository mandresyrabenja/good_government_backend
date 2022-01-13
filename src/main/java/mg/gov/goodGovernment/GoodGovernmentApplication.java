package mg.gov.goodGovernment;

import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class GoodGovernmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodGovernmentApplication.class, args);
	}

	/**
	 * Ajout des 22 regions dans la base de données
	 * @param service RegionService
	 */
	@Bean
	public CommandLineRunner runner(RegionService service) {
		return args -> {
			// 22 Regions
			ArrayList<Region> regions = new ArrayList<>();
			regions.add(new Region("Diana", "1234") );
			regions.add(new Region("Sava", "1234") );
			regions.add(new Region("Itasy", "1234") );
			regions.add(new Region("Analamanga", "1234") );
			regions.add(new Region("Vakinakaratra", "1234") );
			regions.add(new Region("Bongolava", "1234") );
			regions.add(new Region("Sofia", "1234") );
			regions.add(new Region("Boeny", "1234") );
			regions.add(new Region("Betsiboka", "1234") );
			regions.add(new Region("Melaky", "1234") );
			regions.add(new Region("Alaotra Mangoro", "1234") );
			regions.add(new Region("Antsinanana", "1234") );
			regions.add(new Region("Analanjorofo", "1234") );
			regions.add(new Region("Amoron'i Mania", "1234") );
			regions.add(new Region("Haute Matsiatra", "1234") );
			regions.add(new Region("Vatovavy", "1234") );
			regions.add(new Region("Fitovinany", "1234") );
			regions.add(new Region("Atsimo Atsinanana", "1234") );
			regions.add(new Region("Ihorombe", "1234") );
			regions.add(new Region("Menabe", "1234") );
			regions.add(new Region("Atsimo Andrefana", "1234") );
			regions.add(new Region("Androy", "1234") );
			regions.add(new Region("Anôsy", "1234") );

			// Ajout des regions aux base de données
			for (Region region: regions) {
				service.addRegion(region);
			}
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
