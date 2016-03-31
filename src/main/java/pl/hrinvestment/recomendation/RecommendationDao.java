package pl.hrinvestment.recomendation;

import org.springframework.stereotype.Repository;
import pl.hrinvestment.generic.GenericDao;

@Repository
public class RecommendationDao extends GenericDao<Worker>{

    public RecommendationDao() {
        super(Worker.class);
    }
}
