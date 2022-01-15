package mg.gov.goodGovernment;

import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.citizen.CitizenService;
import mg.gov.goodGovernment.government.Government;
import mg.gov.goodGovernment.government.GovernmentService;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import mg.gov.goodGovernment.report.ReportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GoodGovernmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodGovernmentApplication.class, args);
	}

	/**
	 * Insertion des données de test dans la base de données
	 * @param regionService Service d'accès aux base de données d'un compte région
	 * @param governmentService Service d'accès aux base de données d'un compte gouvernement
	 */
	@Bean
	public CommandLineRunner runner(RegionService regionService,
									GovernmentService governmentService,
									CitizenService citizenService)
	{
		return args -> {
			// Les 22 regions de Madagascar
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
				regionService.createRegion(region);
			}

			// Ajout d'un compte gouvernement
			governmentService.createGovernment(new Government("admin", "admin"));

			// Ajout d'un compte citoyen
			citizenService.createCitizen(
					new Citizen(
							153_121_042_101l,
							"Aina",
							"Rakotoson",
							LocalDate.of(1995, Month.NOVEMBER, 25),
							"rakoto@gmail.com",
							"abcd"
					)
			);
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
