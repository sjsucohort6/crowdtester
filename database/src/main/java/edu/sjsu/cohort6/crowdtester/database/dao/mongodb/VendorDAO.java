package edu.sjsu.cohort6.crowdtester.database.dao.mongodb;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import edu.sjsu.cohort6.crowdtester.common.model.entity.Vendor;
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
 * @author rwatsh on 10/6/15.
 */
public class VendorDAO extends BasicDAO<Vendor, String> implements BaseDAO<Vendor> {
    private final Morphia morphia;

    public VendorDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }

    @Override
    public List<String> add(List<Vendor> entityList) {
        List<String> insertedIds = new ArrayList<>();

        if (entityList != null) {
            for (Vendor vendor: entityList) {
                Key<Vendor> key = this.save(vendor);
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
        Query<Vendor> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        return this.deleteByQuery(query).getN();
    }

    @Override
    public void update(List<Vendor> entityList) {
        for (Vendor vendor : entityList) {
            UpdateOperations<Vendor> ops = this.createUpdateOperations()
                    .set("user", vendor.getUser())
                    .set("apps", vendor.getApps())
                    .set("chargeBackPolicy", vendor.getChargeBackPolicy())
                    .set("dateJoined", vendor.getDateJoined())
                    .set("incentivePolicy", vendor.getIncentivePolicy())
                    .set("name", vendor.getName());

            Query<Vendor> updateQuery = this.createQuery().field(Mapper.ID_KEY).equal(new ObjectId(vendor.getId()));
            this.update(updateQuery, ops);
        }
    }

    @Override
    public List<Vendor> fetchById(List<String> entityIdsList) {
        List<ObjectId> objectIds = new ArrayList<>();
        Query<Vendor> query =  null;

        if (entityIdsList != null) {
            for (String id : entityIdsList) {
                if (id != null) {
                    //id = CommonUtils.sanitizeIdString(id);
                    objectIds.add(new ObjectId(id));
                }
            }
        }
        query = objectIds != null && !objectIds.isEmpty()
                ? this.createQuery().field(Mapper.ID_KEY).in(objectIds)
                : this.createQuery();
        QueryResults<Vendor> results = this.find(query);
        return results.asList();
    }

    @Override
    public List<Vendor> fetch(String query) {
        List<Vendor> users = new ArrayList<>();
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
            Vendor user = morphia.fromDBObject(Vendor.class, dbObject);
            users.add(user);
        }
        return users;
    }
}
