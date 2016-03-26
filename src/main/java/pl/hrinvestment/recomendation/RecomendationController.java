package pl.hrinvestment.recomendation;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/recommend")
public class RecomendationController {

    @RequestMapping(value = "/worker", method = POST)
    public Worker recommendWorker(@RequestBody Worker w){
        return w;
    }

    @RequestMapping(value = "/company", method = POST)
    public Worker recommendCompany(@RequestBody Worker w){
        return w;
    }
}
