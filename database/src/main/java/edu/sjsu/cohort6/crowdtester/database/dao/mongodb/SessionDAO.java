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

import com.mongodb.MongoClient;
import edu.sjsu.cohort6.crowdtester.common.model.entity.UserSession;
import edu.sjsu.cohort6.crowdtester.database.dao.BaseDAO;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rwatsh on 11/28/15.
 */
public class SessionDAO extends BasicDAO<UserSession, String> implements BaseDAO<UserSession> {

    private final Morphia morphia;

    protected SessionDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }

    @Override
    public List<String> add(List<UserSession> entityList) {
        List<String> insertedIds = new ArrayList<>();
        if (entityList != null) {
            for (UserSession session : entityList) {
                Key<UserSession> key = this.save(session);
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
        Query<UserSession> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
        return this.deleteByQuery(query).getN();
    }

    @Override
    public void update(List<UserSession> entityList) {

    }

    @Override
    public List<UserSession> fetchById(List<String> entityIdsList) {
        List<String> objectIds = new ArrayList<>();
        if (entityIdsList != null) {
            for (String id : entityIdsList) {
                objectIds.add(id);
            }
        }

        if (!objectIds.isEmpty()) {
            Query<UserSession> query = this.createQuery().field(Mapper.ID_KEY).in(objectIds);
            QueryResults<UserSession> results = this.find(query);
            return results.asList();
        } else {
            Query<UserSession> query = this.createQuery();
            QueryResults<UserSession> results = this.find(query);
            return results.asList();
        }
    }

    @Override
    public List<UserSession> fetch(String jsonQueryString) {
        return null;
    }
}
