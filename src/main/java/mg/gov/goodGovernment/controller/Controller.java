package mg.gov.goodGovernment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    /**
     * Formulaire du login
     */
    @GetMapping("login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("logout")
    public String getLogout() {  return "logout"; }
}
