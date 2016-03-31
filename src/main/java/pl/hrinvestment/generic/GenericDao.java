package pl.hrinvestment.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public abstract class GenericDao<T> {

    @Autowired
    protected MongoTemplate mongo;

    protected Class<T> clazz;

    public GenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T save(T e) {
        mongo.save(e);
        return e;
    }

    public T insert(T e) {
        mongo.insert(e);
        return e;
    }

    public Optional<T> findOne(String id) {
        return ofNullable(mongo.findOne(query(where("id").is(id)), clazz));
    }

    public Optional<T> findOne(Criteria c) {
        return ofNullable(mongo.findOne(query(c), clazz));
    }

    public List<T> find(Criteria c) {
        return mongo.find(query(c), clazz);
    }

    public List<T> findAll() {
        return mongo.findAll(clazz);
    }
}
