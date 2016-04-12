package pl.hrinvestment.recomendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.hrinvestment.amazon.S3Service;
import pl.hrinvestment.auth.SecurityService;
import pl.hrinvestment.config.Config;
import pl.hrinvestment.ip.IpInfo;
import pl.hrinvestment.ip.IpService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.springframework.util.StringUtils.isEmpty;
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

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IpService ipService;

    @RequestMapping(method = GET)
    public List<Worker> all() {
        return dao.findAll();
    }

    @RequestMapping(value = "/worker", method = POST)
    public Worker recommendWorker(@RequestBody Worker w) {
        w.setRecommendedBy(securityService.currentUser().get().getEmail());
        Optional<IpInfo> ip = ipService.getInfo(request.getRemoteAddr());
        if (ip.isPresent())
            w.setIp(ip.get());
        return dao.save(w);
    }

    @RequestMapping(value = "/company", method = POST)
    public Worker recommendCompany(@RequestBody Worker w) {
        return recommendWorker(w);
    }

    @RequestMapping(value = "/{id}/file", method = POST)
    public Worker uploadFile(MultipartFile file, @PathVariable String id) {
        Worker worker = dao.findOne(id).get();
        worker.setFileUrl(s3.upload(file, filename(id, file.getOriginalFilename()), config.get(FILES_BUCKET)));
        return dao.save(worker);
    }

    private String filename(String id, String name) {
        return isEmpty(name) ? id : format("%s.%s", id, getExtension(name));
    }
}
