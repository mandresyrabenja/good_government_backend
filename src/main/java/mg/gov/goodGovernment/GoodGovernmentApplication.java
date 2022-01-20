package mg.gov.goodGovernment;

import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.citizen.CitizenService;
import mg.gov.goodGovernment.government.Government;
import mg.gov.goodGovernment.government.GovernmentService;
import mg.gov.goodGovernment.notification.CitizenNotification;
import mg.gov.goodGovernment.notification.CitizenNotificationService;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import mg.gov.goodGovernment.report.Report;
import mg.gov.goodGovernment.report.ReportService;
import mg.gov.goodGovernment.report.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GoodGovernmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodGovernmentApplication.class, args);
	}

//	/**
//	 * Insertion des données de test dans la base de données
//	 */
//	@Bean
//	public CommandLineRunner runner(RegionService regionService,
//									GovernmentService governmentService,
//									CitizenService citizenService,
//									ReportService reportService,
//									CitizenNotificationService citizenNotificationService)
//	{
//		return args -> {
//			// Les 22 regions de Madagascar
//			ArrayList<Region> regions = new ArrayList<>();
//			regions.add(new Region("Diana", "1234") );
//			regions.add(new Region("Sava", "1234") );
//			regions.add(new Region("Itasy", "1234") );
//			regions.add(new Region("Analamanga", "1234") );
//			regions.add(new Region("Vakinakaratra", "1234") );
//			regions.add(new Region("Bongolava", "1234") );
//			regions.add(new Region("Sofia", "1234") );
//			regions.add(new Region("Boeny", "1234") );
//			regions.add(new Region("Betsiboka", "1234") );
//			regions.add(new Region("Melaky", "1234") );
//			regions.add(new Region("Alaotra Mangoro", "1234") );
//			regions.add(new Region("Antsinanana", "1234") );
//			regions.add(new Region("Analanjorofo", "1234") );
//			regions.add(new Region("Amoron'i Mania", "1234") );
//			regions.add(new Region("Haute Matsiatra", "1234") );
//			regions.add(new Region("Vatovavy", "1234") );
//			regions.add(new Region("Fitovinany", "1234") );
//			regions.add(new Region("Atsimo Atsinanana", "1234") );
//			regions.add(new Region("Ihorombe", "1234") );
//			regions.add(new Region("Menabe", "1234") );
//			regions.add(new Region("Atsimo Andrefana", "1234") );
//			regions.add(new Region("Androy", "1234") );
//			regions.add(new Region("Anôsy", "1234") );
//
//			// Ajout des regions aux base de données
//			for (Region region: regions) {
//				regionService.createRegion(region);
//			}
//
//			// Ajout d'un compte gouvernement
//			governmentService.createGovernment(new Government("admin", "admin"));
//
//			// Citoyens test
//			List<Citizen> citizens = new ArrayList<>();
//			Citizen citizen1 = new Citizen(
//					153_121_042_101L,
//					"Finoana",
//					"Rabenjatiana",
//					LocalDate.of(1960, Month.JUNE, 26),
//					"finoon@gmail.com",
//					"abcd"
//			);
//			citizens.add(citizen1);
//
//			Citizen citizen2 = new Citizen(
//					102_121_042_102L,
//					"Josoa",
//					"Andrianandrasana",
//					LocalDate.of(2002, Month.NOVEMBER, 25),
//					"josoa@gmail.com",
//					"abcd"
//			);
//			citizens.add(citizen2);
//			Citizen citizen3 = new Citizen(
//					153_121_042_101L,
//					"Mandresy",
//					"Rabenja",
//					LocalDate.of(1947, Month.MARCH, 29),
//					"mandresy@gmail.com",
//					"abcd"
//			);
//			citizens.add(citizen3);
//			for (Citizen citizen: citizens) {
//				citizenService.createCitizen(citizen);
//				citizenNotificationService.addNotification(
//						new CitizenNotification(citizen)
//				);
//			}
//
//			List<Report> reports = new ArrayList<>();
//			reports.add(
//					new Report(
//							citizen1, LocalDate.now(), "Fako", "Be dia be fako", 12.1658498463, -15.16458498,
//							regions.get(3), Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen1, LocalDate.now(), "Tapaka ny jiro", "Tapaka ny jiro", 12.1658498463, -15.16458498,
//							regions.get(15), Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen1, LocalDate.now(), "Tapaka ny rano", "JIRAMA t", 12.1658498463, -15.16458498,
//							null, Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen2, LocalDate.now(), "Vidina menaka", "Lasa 12000", 12.1658498463, -15.16458498,
//							regions.get(3), Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen2, LocalDate.now(), "Vidimpiainana", "Miakatra", 12.1658498463, -15.16458498,
//							regions.get(11), Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen2, LocalDate.now(), "Vidina mofogasy", "200ar", 12.1658498463, -15.16458498,
//							null, Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen3, LocalDate.now(), "Rano", "Miakatra ny rano", 12.1658498463, -15.16458498,
//							regions.get(11), Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen3, LocalDate.now(), "Lalana", "Simba ny lalana", 12.1658498463, -15.16458498,
//							regions.get(3), Status.NEW.getStatus()
//					)
//			);
//			reports.add(
//					new Report(
//							citizen3, LocalDate.now(), "Fako", "Be dia be", 12.1658498463, -15.16458498,
//							null, Status.NEW.getStatus()
//					)
//			);
//
//			for (Report report : reports) { reportService.insert(report); }
//
//		};
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
