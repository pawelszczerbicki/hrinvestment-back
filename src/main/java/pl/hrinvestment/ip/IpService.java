package pl.hrinvestment.ip;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

@Service
public class IpService {
    //Download source http://dev.maxmind.com/geoip/geoip2/geolite2/
    public static final String IP_DB_FILE = "classpath:GeoLite2-City.mmdb";

    @Autowired
    private ResourceLoader loader;

    private DatabaseReader reader;

    @PostConstruct
    public void initIpData() throws IOException {
        reader = new DatabaseReader.Builder(loader.getResource(IP_DB_FILE).getFile()).build();
    }

    public Optional<IpInfo> getInfo(String ip) {
        try {
            CityResponse response = reader.city(InetAddress.getByName(ip));
            return Optional.of(new IpInfo(ip, response.getCity().getName(), response.getCountry().getName()));
        } catch (IOException | GeoIp2Exception e) {
            return Optional.empty();
        }
    }
}
