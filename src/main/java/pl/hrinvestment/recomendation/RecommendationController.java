package pl.hrinvestment.recomendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.hrinvestment.amazon.S3Service;
import pl.hrinvestment.config.Config;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.hrinvestment.config.Keys.FILES_BUCKET;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationDao dao;

    @Autowired
    private S3Service s3;

    @Autowired
    private Config config;

    @RequestMapping(method = GET)
    public List<Worker> all() {
        return dao.findAll();
    }


    @RequestMapping(value = "/worker", method = POST)
    public Worker recommendWorker(@RequestBody Worker w) {
        return dao.save(w);
    }

    @RequestMapping(value = "/company", method = POST)
    public Worker recommendCompany(@RequestBody Worker w) {
        return dao.save(w);
    }

    @RequestMapping(value = "/{id}/file", method = POST)
    public Worker uploadFile(MultipartFile file, @PathVariable String id) {
        Worker worker = dao.findOne(id).get();
        worker.setFileUrl(s3.upload(file, id, config.get(FILES_BUCKET)));
        return dao.save(worker);
    }
}
