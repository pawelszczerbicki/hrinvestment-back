package pl.hrinvestment.recomendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import static java.time.Instant.now;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.springframework.util.StringUtils.isEmpty;
import static pl.hrinvestment.config.Keys.FILES_BUCKET;

@Service
public class RecommendationService {

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

    public List<Worker> findAll() {
        return dao.findAll();
    }

    public Worker save(Worker w) {
        w.setRecommendedBy(securityService.currentUser().get().getEmail());
        w.setCreatedAt(now());
        Optional<IpInfo> ip = ipService.getInfo(request.getRemoteAddr());
        if (ip.isPresent())
            w.setIp(ip.get());
        return dao.save(w);
    }

    public Worker attachFile(MultipartFile file, String id) {
        Worker w = dao.findOne(id).get();
        w.setFileUrl(s3.upload(file, filename(id, file.getOriginalFilename()), config.get(FILES_BUCKET)));
        return dao.save(w);
    }

    private String filename(String id, String name) {
        return isEmpty(name) ? id : format("%s.%s", id, getExtension(name));
    }
}
