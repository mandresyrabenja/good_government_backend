package mg.gov.goodGovernment.region;

import lombok.AllArgsConstructor;
import mg.gov.goodGovernment.http.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlleur HTTP de l'entité Region
 * @author Mandresy
 */
@RestController
@RequestMapping("api/v1/regions")
@AllArgsConstructor
@CrossOrigin
public class RegionController {
    private final RegionService regionService;

    /**
     * Avoir le Region correspondant à l'utilisateur authentifié
     * @param authentication Authentication de l'utilisateur actuel
     * @return Le Region correspondant à l'authentication
     */
    private Region getAuthenticatedRegion(Authentication authentication) {
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_REGION")))
            throw new IllegalStateException("Veuillez vous connecter avec une compte région");

        String regionName = (String) authentication.getPrincipal();
        return regionService.findByName(regionName);
    }

    /**
     * Avoir la liste des régions corresponant au numéro de page entré.<br>
     * Une page contient 10 régions.
     * @param authentication Authentification de l'utilisateur qui fait la requete
     * @param page Numero de page demandé celui qui fait la requete
     * @return Liste des régions correspondant au numéro de page entré
     */
    @GetMapping
    @PreAuthorize("hasAuthority('region:read')")
    public List<Region> getRegions(Authentication authentication, @RequestParam(required = false) Integer page) {
        try {
            // Si l'utilisateur est une région alors on ne donne que son compte région
            List<Region> regions = new ArrayList<>();
            regions.add( getAuthenticatedRegion(authentication) );
            return regions;
        } catch (IllegalStateException e) {
            // Si l'utilisateur est connecté en tant que gouvernment alors on donne la liste des régions
            return regionService.findAllRegions(page);
        }
    }

    @GetMapping(path = "{regionId}")
    @PreAuthorize("hasAuthority('region:read')")
    public ResponseEntity<Object> findRegion(@PathVariable("regionId") Integer id) {
        Region region = regionService.findByIdRegion(id);
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
    @PreAuthorize("hasAuthority('region:create')")
    public ResponseEntity<HttpResponse> addRegion(@RequestBody Region region) {
        try{
            regionService.createRegion(region);
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
    @PreAuthorize("hasAuthority('region:delete')")
    public ResponseEntity<HttpResponse> deleteRegion(Authentication authentication,
                                                     @PathVariable(value = "regionId", required = false) Integer id) {
        try {
            // Si aucun id n'est precisé par la requête alors on efface l'utilisateur connecté
            if(id == null) {
                regionService.deleteRegion(
                        getAuthenticatedRegion(authentication).getId()
                );
            } else {
                regionService.deleteRegion(id);
            }
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
    @PreAuthorize("hasAuthority('region:update')")
    public ResponseEntity<HttpResponse> updateRegion(Authentication authentication,
                                                     @PathVariable(value = "regionId", required = false) Integer id,
                                                     @RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String password)
    {
        try {
            // Si aucun id n'est precisé par la requête alors on modifie l'utilisateur connecté
            if(id == null) {
                regionService.updateRegion(
                        getAuthenticatedRegion(authentication).getId(),
                        name,
                        password
                );
            } else {
                regionService.updateRegion(id, name, password);
            }
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
