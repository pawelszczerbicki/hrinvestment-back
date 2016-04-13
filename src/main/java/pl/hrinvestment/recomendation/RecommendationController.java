package pl.hrinvestment.recomendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService service;

    @RequestMapping(method = GET)
    public List<Worker> all() {
        return service.findAll();
    }

    @RequestMapping(value = "/worker", method = POST)
    public Worker recommendWorker(@RequestBody Worker w) {
        return service.save(w);
    }

    @RequestMapping(value = "/company", method = POST)
    public Worker recommendCompany(@RequestBody Worker w) {
        return service.save(w);
    }

    @RequestMapping(value = "/{id}/file", method = POST)
    public Worker uploadFile(MultipartFile file, @PathVariable String id) {
        return service.attachFile(file, id);
    }
}
