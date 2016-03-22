package pl.hrinvestment.recomendation;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommend")
public class RecomendationController {

    @RequestMapping("/worker")
    public Worker recommendWorker(@RequestBody Worker w){
        return w;
    }

    @RequestMapping("/company")
    public Worker recommendCompany(@RequestBody Worker w){
        return w;
    }
}
