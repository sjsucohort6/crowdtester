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

    /**
     * Constructs a MongoDB client instance.
     *
     * This is private so it can only be instantiated via DI (using Guice).
     *
     * @param server    server hostname or ip
     * @param port      port number for mongodb service
     * @param dbName    name of db to use
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
    }

    @Override
    public void dropDB(String dbName) {
        morphiaDatastore.getDB().dropDatabase();
    }

    @Override
    public void useDB(String dbName) {
        morphiaDatastore = morphia.createDatastore(mongoClient, dbName);

    }

    @Override
    public boolean checkHealth() {
        try {
            mongoClient.listDatabaseNames();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public String getConnectString() {
        return MessageFormat.format("DB Connect Info: server [{0}], port [{1}], dbName [{2}]", server, port, dbName);

    }

    @Override
    public Object getDAO(Class<? extends BaseDAO> clazz) {
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
