package edu.sjsu.cohort6.crowdtester.database.dao.mongodb;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import edu.sjsu.cohort6.crowdtester.common.model.entity.Tester;
import edu.sjsu.cohort6.crowdtester.database.dao.BaseDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rwatsh on 10/5/15.
 */
public class TesterDAO extends BasicDAO<Tester, String> implements BaseDAO<Tester> {


    private final Morphia morphia;

    protected TesterDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }

    @Override
    public List<String> add(List<Tester> entityList) {
        List<String> insertedIds = new ArrayList<>();
        if (entityList != null) {
            for (Tester tester : entityList) {
                Key<Tester> key = this.save(tester);
                insertedIds.add(key.getId().toString());
            }
        }
        return insertedIds;
    }

    @Override
    public long remove(List<String> entityIdsList) {
        List<String> objectIds = new ArrayList<>();
        for (String id : entityIdsList) {
            objectIds.add(id);
        }
        Query<Tester> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        return this.deleteByQuery(query).getN();
    }

    @Override
    public void update(List<Tester> entityList) {
        for (Tester tester : entityList) {
            UpdateOperations<Tester> ops = this.createUpdateOperations()
                    .set("creditPoint", tester.getCreditPoint())
                    .set("skills", tester.getSkills())
                    .set("user", tester.getUser());

            Query<Tester> updateQuery = this.createQuery().field(Mapper.ID_KEY).equal(new ObjectId(tester.getId()));
            this.update(updateQuery, ops);
        }
    }

    @Override
    public List<Tester> fetchById(List<String> entityIdsList) {
        List<String> objectIds = new ArrayList<>();
        for (String id : entityIdsList) {
            objectIds.add(id);
        }

        Query<Tester> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        QueryResults<Tester> results = this.find(query);
        return results.asList();
    }

    @Override
    public List<Tester> fetch(String query) {
        List<Tester> courses = new ArrayList<>();
        DBObject dbObjQuery;
        DBCursor cursor;
        if (!(query == null)) {
            dbObjQuery = (DBObject) JSON.parse(query);
            cursor = this.getCollection().find(dbObjQuery);
        } else {
            cursor = this.getCollection().find();
        }

        List<DBObject> dbObjects = cursor.toArray();
        for (DBObject dbObject : dbObjects) {
            Tester course = morphia.fromDBObject(Tester.class, dbObject);
            courses.add(course);
        }
        return courses;
    }
}
