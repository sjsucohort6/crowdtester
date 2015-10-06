package edu.sjsu.cohort6.crowdtester.database.dao.mongodb;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import edu.sjsu.cohort6.crowdtester.common.model.entity.App;
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
public class AppDAO extends BasicDAO<App, String> implements BaseDAO<App> {
    private final Morphia morphia;

    public AppDAO(Class<App> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super( mongoClient, morphia, dbName);
        this.morphia = morphia;
    }

    @Override
    public List<String> add(List<App> entityList) {
        List<String> insertedIds = new ArrayList<>();
        if (entityList != null) {
            for (App app: entityList) {
                Key<App> key = this.save(app);
                insertedIds.add(key.getId().toString());
            }
        }
        return insertedIds;
    }

    @Override
    public long remove(List<String> entityIdsList) {
        List<ObjectId> objectIds = new ArrayList<>();
        for (String id : entityIdsList) {
            objectIds.add(new ObjectId(id));
        }
        Query<App> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        return this.deleteByQuery(query).getN();
    }

    @Override
    public void update(List<App> entityList) {
        for (App app: entityList) {
            UpdateOperations<App> ops = this.createUpdateOperations()
                    .set("name", app.getName())
                    .set("appFileName", app.getAppFileName())
                    .set("appIncentivePolicy", app.getAppIncentivePolicy())
                    .set("appVendor", app.getAppVendor())
                    .set("description", app.getDescription())
                    .set("projectEndDate", app.getProjectEndDate())
                    .set("projectStartDate", app.getProjectStartDate())
                    .set("supportedPlatforms", app.getSupportedPlatforms())
                    .set("testers", app.getTesters());

            Query<App> updateQuery = this.createQuery().field(Mapper.ID_KEY).equal(new ObjectId(app.getId()));
            this.update(updateQuery, ops);
        }

    }

    @Override
    public List<App> fetchById(List<String> entityIdsList) {
        List<ObjectId> objectIds = new ArrayList<>();
        for (String id : entityIdsList) {
            objectIds.add(new ObjectId(id));
        }

        Query<App> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        QueryResults<App> results = this.find(query);
        return results.asList();
    }

    @Override
    public List<App> fetch(String query) {
        List<App> courses = new ArrayList<>();
        DBObject dbObjQuery;
        DBCursor cursor;
        if (!(query == null)) {
            dbObjQuery = (DBObject) JSON.parse(query);
            cursor = this.getCollection().find(dbObjQuery);
        } else {
            cursor = this.getCollection().find();
        }

        List<DBObject> dbObjects = cursor.toArray();
        for (DBObject dbObject: dbObjects) {
            App course = morphia.fromDBObject(App.class, dbObject);
            courses.add(course);
        }
        return courses;
    }
}