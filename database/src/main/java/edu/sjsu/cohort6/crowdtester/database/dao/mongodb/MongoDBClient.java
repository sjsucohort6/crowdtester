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

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.mongodb.MongoClient;
import edu.sjsu.cohort6.crowdtester.common.model.entity.App;
import edu.sjsu.cohort6.crowdtester.database.dao.BaseDAO;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.text.MessageFormat;

/**
 * @author rwatsh on 10/5/15.
 */
public class MongoDBClient implements DBClient {
    private final String server;
    private final int port;
    private final String dbName;
    private Morphia morphia = null;
    private MongoClient mongoClient;
    private Datastore morphiaDatastore;
    private AppDAO appDAO;
    private BugDAO bugDAO;
    private UserDAO userDAO;
    private VendorDAO vendorDAO;
    private TesterDAO testerDAO;
    private SessionDAO sessionDAO;

    /**
     * Constructs a MongoDB client instance.
     * <p>
     * This is private so it can only be instantiated via DI (using Guice).
     *
     * @param server server hostname or ip
     * @param port   port number for mongodb service
     * @param dbName name of db to use
     */
    @Inject
    private MongoDBClient(@Assisted("server") String server, @Assisted("port") int port, @Assisted("dbName") String dbName) {
        this.server = server;
        this.port = port;
        this.dbName = dbName;
        mongoClient = new MongoClient(server, port);
        morphia = new Morphia();
        morphia.mapPackageFromClass(App.class);
        morphiaDatastore = morphia.createDatastore(mongoClient, dbName);
        morphiaDatastore.ensureIndexes();
        appDAO = new AppDAO(mongoClient, morphia, dbName);
        bugDAO = new BugDAO(mongoClient, morphia, dbName);
        userDAO = new UserDAO(mongoClient, morphia, dbName);
        vendorDAO = new VendorDAO(mongoClient, morphia, dbName);
        testerDAO = new TesterDAO(mongoClient, morphia, dbName);
        sessionDAO = new SessionDAO(mongoClient, morphia, dbName);
    }

    @Override
    public void dropDB(String dbName) {
        morphiaDatastore.getDB().dropDatabase();
    }

    @Override
    public void useDB(String dbName) {
        morphiaDatastore = morphia.createDatastore(mongoClient, dbName);
        morphiaDatastore.ensureIndexes();
    }

    @Override
    public boolean checkHealth() {
        try {
            mongoClient.listDatabaseNames();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getConnectString() {
        return MessageFormat.format("DB Connect Info: server [{0}], port [{1}], dbName [{2}]", server, port, dbName);

    }

    @Override
    public Object getDAO(Class<? extends BaseDAO> clazz) {
        if (clazz != null) {
            if (clazz.getTypeName().equalsIgnoreCase(AppDAO.class.getTypeName())) {
                return appDAO;
            } else if (clazz.getTypeName().equalsIgnoreCase(BugDAO.class.getTypeName())) {
                return bugDAO;
            } else if (clazz.getTypeName().equalsIgnoreCase(UserDAO.class.getTypeName())) {
                return userDAO;
            } else if (clazz.getTypeName().equalsIgnoreCase(VendorDAO.class.getTypeName())) {
                return vendorDAO;
            } else if (clazz.getTypeName().equalsIgnoreCase(TesterDAO.class.getTypeName())) {
                return testerDAO;
            } else if (clazz.getTypeName().equalsIgnoreCase(SessionDAO.class.getTypeName())) {
                return sessionDAO;
            }
        }
        return null;
    }

    @Override
    public Morphia getMorphia() {
        return morphia;
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }
}
