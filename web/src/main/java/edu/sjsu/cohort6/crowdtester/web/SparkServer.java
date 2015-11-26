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

package edu.sjsu.cohort6.crowdtester.web;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import edu.sjsu.cohort6.crowdtester.common.util.EndpointUtils;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.DBFactory;
import edu.sjsu.cohort6.crowdtester.database.dao.DatabaseModule;
import edu.sjsu.cohort6.crowdtester.web.route.RootRoute;
import edu.sjsu.cohort6.crowdtester.web.view.MainView;
import edu.sjsu.cohort6.crowdtester.web.view.RegisterView;
import edu.sjsu.cohort6.crowdtester.web.view.UserView;
import lombok.extern.java.Log;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Properties;

import static spark.Spark.*;

/**
 * @author rwatsh on 10/13/15.
 */
@Log
public class SparkServer {
    @Inject
    private DBFactory dbFactory;

    /**
     * Main method for the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config.properties"));
            SparkServer app = new SparkServer();
            int serverPort = Integer.parseInt(props.getProperty("port", "9090"));
            String dbServer = props.getProperty("dbHost", "localhost");
            int dbPort = Integer.parseInt(props.getProperty("dbPort", "27017"));
            String dbName = props.getProperty("dbName", "crowdtester_db");
            DBClient dbClient = app.initDB(dbServer, dbPort, dbName);
            port(serverPort);
            // Configure that static files directory.
            staticFileLocation(HttpConstants.WWW_DIR);

            // Setup Views for the web application
            UserView userView = new UserView(dbClient);
            MainView mainView = new MainView(HttpConstants.INDEX_PATH, dbClient);
            RegisterView registerView = new RegisterView(dbClient);


            // Setup Routes for the web application (these are the REST endpoints or resources or just route handlers/redirectors)
            get("/", new RootRoute());
            get(EndpointUtils.ENDPOINT_ROOT, new RootRoute());

            // Setup Filters
            enableCORS("*", "*", "*");
            init();
        } catch (Exception e) {
            halt(HttpConstants.HTTP_INTERNAL_ERR, "Internal error occurred on server, exception is: " + e.toString());
        }


    }

    private DBClient initDB(String server, int port, String dbName) {
        log.info(MessageFormat.format("Initializing DB {0}, {1} name {2}", server, port, dbName));
        Module module = new DatabaseModule();
        Guice.createInjector(module).injectMembers(this);
        return dbFactory.create(server, port, dbName);
    }

    // Cross origin resource sharing filter.
    private static void enableCORS(final String origin, final String methods, final String headers) {
        before(new Filter() {
            @Override
            public void handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", origin);
                response.header("Access-Control-Request-Method", methods);
                response.header("Access-Control-Allow-Headers", headers);
            }
        });
    }

}
