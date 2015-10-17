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
import edu.sjsu.cohort6.crowdtester.common.model.entity.Bug;
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
public class BugDAO extends BasicDAO<Bug, String> implements BaseDAO<Bug> {
    private final Morphia morphia;

    public BugDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }

    @Override
    public List<String> add(List<Bug> entityList) {
        List<String> insertedIds = new ArrayList<>();
        if (entityList != null) {
            for (Bug bug : entityList) {
                Key<Bug> key = this.save(bug);
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
        Query<Bug> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        return this.deleteByQuery(query).getN();
    }

    @Override
    public void update(List<Bug> entityList) {
        for (Bug bug : entityList) {
            UpdateOperations<Bug> ops = this.createUpdateOperations()
                    .set("app", bug.getApp())
                    .set("bugSeverity", bug.getBugSeverity())
                    .set("bugStatus", bug.getBugStatus())
                    .set("createdOn", bug.getCreatedOn())
                    .set("tester", bug.getTester());

            Query<Bug> updateQuery = this.createQuery().field(Mapper.ID_KEY).equal(new ObjectId(bug.getId()));
            this.update(updateQuery, ops);
        }
    }

    @Override
    public List<Bug> fetchById(List<String> entityIdsList) {
        List<String> objectIds = new ArrayList<>();
        for (String id : entityIdsList) {
            objectIds.add(id);
        }

        Query<Bug> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        QueryResults<Bug> results = this.find(query);
        return results.asList();
    }

    @Override
    public List<Bug> fetch(String query) {
        List<Bug> courses = new ArrayList<>();
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
            Bug course = morphia.fromDBObject(Bug.class, dbObject);
            courses.add(course);
        }
        return courses;
    }
}
