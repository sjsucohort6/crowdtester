/*
 * Copyright (c) 2015 San Jose State University.   
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
        if (entityIdsList != null) {
            for (String id : entityIdsList) {
                objectIds.add(id);
            }
        }

        if (!objectIds.isEmpty()) {
            Query<Tester> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
            QueryResults<Tester> results = this.find(query);
            return results.asList();
        } else {
            Query<Tester> query = this.createQuery();
            QueryResults<Tester> results = this.find(query);
            return results.asList();
        }
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

    public Tester fetchByUserId(String userId) {
        List<Tester> testers = fetch("{\"user.$id\" : \"" + userId + "\"}");
        if (testers != null && !testers.isEmpty()) {
            return testers.get(0);
        }
        return null;
    }
}
