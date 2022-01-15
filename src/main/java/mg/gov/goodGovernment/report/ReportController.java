package mg.gov.goodGovernment.report;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.citizen.CitizenService;
import mg.gov.goodGovernment.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/reports")
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final CitizenService citizenService;

    @PostMapping
    @PreAuthorize("hasAuthority('report:create')")
    public ResponseEntity<Object> create(Authentication authentication, @RequestBody Report report) {
        // Récuperation du citoyen
        String email = (String) authentication.getPrincipal();
        Citizen citizen = citizenService.findByEmail(email);
        if(null == citizen)
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.NOT_FOUND.value(),
                            true,
                            "Aucun citoyen n' a" + email + "comme email"
                    ),
                    HttpStatus.NOT_FOUND
            );

        // Affectation des données nécessaire au signalement
        report.setDate(LocalDate.now());
        report.setCitizen(citizen);
        report.setStatus(Status.NEW.name());

        // Insertion à la base de données
        reportService.insert(report);
        return new ResponseEntity<>(
                new HttpResponse(
                        HttpStatus.OK.value(), false, "Signalement crée avec succès"
                ),
                HttpStatus.OK
        );
    }

}
