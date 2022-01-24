package mg.gov.goodGovernment.government;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/governments")
@AllArgsConstructor
@CrossOrigin
public class GovernmentController {
    private final GovernmentService governmentService;

    @GetMapping
    public List<Government> findAllGovernment() {
        return governmentService.findAll();
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createGovernment(@RequestBody Government government) {
        try{
            governmentService.createGovernment(government);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    new HttpResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), true, e.getMessage()),
                    HttpStatus.CONFLICT
            );
        }
        return new ResponseEntity<>(
                new HttpResponse(LocalDateTime.now(), HttpStatus.CREATED.value(), false, "New government account created")
                , HttpStatus.CREATED
        );
    }
}
