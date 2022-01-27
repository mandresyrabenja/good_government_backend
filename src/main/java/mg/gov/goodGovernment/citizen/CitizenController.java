package mg.gov.goodGovernment.citizen;

import lombok.RequiredArgsConstructor;
import mg.gov.goodGovernment.http.HttpResponse;
import mg.gov.goodGovernment.notification.CitizenNotification;
import mg.gov.goodGovernment.notification.CitizenNotificationService;
import mg.gov.goodGovernment.report.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controlleur HTTP de l'entité Citizen
 */
@RestController
@RequestMapping("api/v1/citizens")
@RequiredArgsConstructor
@CrossOrigin
public class CitizenController {
    private final CitizenService citizenService;
    private final ReportService reportService;
    private final CitizenNotificationService citizenNotificationService;

    @GetMapping
    @PreAuthorize("hasAuthority('citizen:read')")
    public List<Citizen> findAllCitizen() {
        return citizenService.findAllCitizens();
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAuthority('citizen:read')")
    public ResponseEntity<Object> findCitizen(@PathVariable Long id) {
        Citizen citizen = citizenService.findCitizen(id);
        if(citizen != null) {
            return new ResponseEntity<>(citizen, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(
                    new HttpResponse(HttpStatus.NOT_FOUND.value(), true, "Aucun citoyen n'a " + id + " comme ID"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createCitizen(@RequestBody Citizen citizen) {
        try{
            citizenService.createCitizen(citizen);
            citizenNotificationService.addNotification(
                    new CitizenNotification(citizen)
            );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    // Aux cas ou une règle a été violée
                    new HttpResponse(LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE.value(), true, e.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        return new ResponseEntity<>(
                new HttpResponse(LocalDateTime.now(), HttpStatus.CREATED.value(), false, "Nouvelle citoyen crée")
                , HttpStatus.CREATED
        );
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAuthority('citizen:update')")
    public ResponseEntity<HttpResponse> updateCitizen(@PathVariable("id") Long id,
                                                      @RequestParam(required = false) Long cin,
                                                      @RequestParam(required = false) String firstName,
                                                      @RequestParam(required = false) String lastName,
                                                      @RequestParam(required = false) LocalDate dob,
                                                      @RequestParam(required = false) String email,
                                                      @RequestParam(required = false) String password)
    {
        try {
            citizenService.updateCitizen(id, new Citizen(cin, firstName, lastName, dob, email, password) );
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    new HttpResponse(LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE.value(), true, e.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        return new ResponseEntity<>(
                new HttpResponse(LocalDateTime.now(), HttpStatus.OK.value(), false, "Citoyen modifié avec succèss"),
                HttpStatus.OK
        );
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('citizen:delete')")
    public ResponseEntity<HttpResponse> deleteCitizen(@PathVariable("id") Long id) {
        try {
            citizenService.deleteCitizen(id);
        }catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    // Si aucun citoyen ne correspond à l'ID
                    new HttpResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), true, e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new HttpResponse(LocalDateTime.now(), HttpStatus.OK.value(), false, "Citoyen effacée"),
                HttpStatus.OK
        );
    }

}
