package mg.gov.goodGovernment.region;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/regions")
@AllArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public List<Region> getRegions() {
        return regionService.getRegions();
    }

    @GetMapping(path = "{regionId}")
    public ResponseEntity<Object> find(@PathVariable("regionId") Integer id) {
        Region region = regionService.findById(id);
        if(region != null) {
            return new ResponseEntity<>(region, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(
                    new HttpResponse(HttpStatus.NOT_FOUND.value(), true, "Aucune région n'a " + id + " comme ID"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping
    public ResponseEntity<HttpResponse> addRegion(@RequestBody Region region) {
        try{
            regionService.addRegion(region);
        } catch (IllegalStateException e) {
            // Si une région à déjà le même nom
            return new ResponseEntity<>(
                    new HttpResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), true, e.getMessage()),
                    HttpStatus.CONFLICT
            );
        }
        return new ResponseEntity<>(
                new HttpResponse(LocalDateTime.now(), HttpStatus.CREATED.value(), false, "Nouvelle region crée")
                , HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "{regionId}")
    public ResponseEntity<HttpResponse> deleteRegion(@PathVariable("regionId") Integer id) {
        try {
            regionService.deleteRegion(id);
        }catch (IllegalStateException e) {
            return new ResponseEntity<>(
                    // Si aucune région ne correspond à l'ID
                    new HttpResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), true, e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new HttpResponse(LocalDateTime.now(), HttpStatus.OK.value(), false, "Region effacée"),
                HttpStatus.OK
        );
    }

    @PutMapping(path = "{regionId}")
    public ResponseEntity<HttpResponse> updateRegion(@PathVariable("regionId") Integer id,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String password)
    {
        try {
            regionService.updateRegion(id, name, password);
        } catch (IllegalStateException e) {
            // Si aucune région ne correspond à l'ID
            return new ResponseEntity<>(
                    new HttpResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), true, e.getMessage()),
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                new HttpResponse(LocalDateTime.now(), HttpStatus.OK.value(), false, "Region modifié avec success"),
                HttpStatus.OK
        );
    }
}
