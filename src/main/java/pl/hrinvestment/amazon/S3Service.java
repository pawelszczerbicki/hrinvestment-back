package pl.hrinvestment.amazon;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.hrinvestment.config.Config;

import java.io.InputStream;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;
import static pl.hrinvestment.config.Keys.S3_ENABLED;

@Service
public class S3Service {

    private static final Logger logger = getLogger(S3Service.class);

    @Autowired
    private AmazonS3Client s3;

    @Autowired
    private Config config;

    public String upload(MultipartFile file, String name, String bucket) {
        if (!config.asBoolean(S3_ENABLED)) {
            logger.info("Amazon S3 is disabled on this environment. File with name [{}] will not be uploaded", name);
            return null;
        }
        try (InputStream s = file.getInputStream()) {
            s3.putObject(new PutObjectRequest(bucket, name, s, new ObjectMetadata()).withCannedAcl(PublicRead));
            return getUrl(bucket, name) + "?time=" + currentTimeMillis();
        } catch (Exception e) {
            logger.error(format("Can not upload file [%s] to bucket [%s]", name, bucket), e);
            throw new IllegalStateException(e);
        }
    }

    private String getUrl(String bucket, String name) {
        return s3.getResourceUrl(bucket, name);
    }
}
