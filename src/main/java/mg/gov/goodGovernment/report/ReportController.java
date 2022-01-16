package mg.gov.goodGovernment.report;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.citizen.Citizen;
import mg.gov.goodGovernment.citizen.CitizenService;
import mg.gov.goodGovernment.http.HttpResponse;
import mg.gov.goodGovernment.region.Region;
import mg.gov.goodGovernment.region.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/reports")
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final CitizenService citizenService;
    private final RegionService regionService;

    /**
     * Mettre à jour un signalement<br>
     * @param id ID du signalement
     * @param regionId ID de la nouvelle région du signalement
     * @param status Nouvelle status de la signalement
     * @return Une JSON décrivant si l'opération est effectué ou a un erreur
     */
    @PutMapping(path = "{id}")
    @PreAuthorize("hasAuthority('report:update')")
    public ResponseEntity<HttpResponse> updateReport(Authentication authentication,
                                                     @PathVariable Long id,
                                                     @RequestParam(required = false) Integer regionId,
                                                     @RequestParam(required = false) String status) {
        try{
            reportService.update(authentication, id, regionId, status);
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.OK.value(), false, "Signalement mis à jour avec succès"
                    ),
                    HttpStatus.OK
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    new HttpResponse(
                            HttpStatus.NOT_ACCEPTABLE.value(), true, e.getMessage()
                    ),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    /**
     * GetById report
     * @param id ID
     * @return Report
     */
    @GetMapping(path = "{id}")
    @PreAuthorize("hasAuthority('report:read')")
    public ResponseEntity<Object> findReport(@PathVariable Long id) {
        try {
            Report report = reportService.findById(id);
            return new ResponseEntity<>(report, HttpStatus.FOUND);
        } catch (IllegalStateException e) {
            // Si aucun signalement ne correspond à l'ID envoyé par le requête Http
          return new ResponseEntity<>(
                  new HttpResponse(
                          HttpStatus.NOT_FOUND.value(), true, e.getMessage()
                  ),
                  HttpStatus.NOT_FOUND
          );
        }
    }

    /**
     * Avoir la liste des signalements pas encore affecté à une région
     * @return La liste des signalements pas encore affecté à une région
     */
    private List<Report> findNotAssignedReport() {
        // Récuperation de la liste des signalements pas encore affecté à une région
        return reportService.findByRegionIsNull();
    }

    /**
     * Avoir la liste des signalements de problèmes d'une région, d'un citoyen
     * ou ceux qui ne sont pas encore attribué à une région particulière
     * @param regionId ID du région
     * @return La liste des signalements de problèmes du région
     */
    @GetMapping
    @PreAuthorize("hasAuthority('report:read')")
    public List<Report> findReport(Authentication authentication,
            @RequestParam(value = "region", required = false) String regionId)
    {
        // Récuperer la liste des signalements pas encore affecté
        // si regionId est null
        if ("null".equalsIgnoreCase(regionId)) {
            return findNotAssignedReport();
        }

        // Si l'utilisateur connecté est de type région
        // Alors on récupere la liste des signalements affectés à elle
        if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_REGION")) ) {
            String regionName = (String) authentication.getPrincipal();
            Region region =  regionService.findByName(regionName);
            return reportService.findByRegion(region);
        };

        // Si l'utilisateur connecté est de type citoyen
        // Alors on récupere la liste des signalements effectués par lui
        if( authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CITIZEN")) ) {
            String citizenEmail = (String) authentication.getPrincipal();
            Citizen citizen =  citizenService.findByEmail(citizenEmail);
            return reportService.findByCitizen(citizen);
        };

        return null;
    }

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
        report.setStatus(Status.NEW.getStatus());

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
