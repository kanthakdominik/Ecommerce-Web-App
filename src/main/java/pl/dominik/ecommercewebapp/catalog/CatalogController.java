package pl.dominik.ecommercewebapp.catalog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CatalogController {

    @GetMapping("/catalog")
    public @ResponseBody String index(){
        return "Hello from catalog";
    }
}
