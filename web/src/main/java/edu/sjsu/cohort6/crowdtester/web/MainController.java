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
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.DBFactory;
import edu.sjsu.cohort6.crowdtester.database.dao.DatabaseModule;
import edu.sjsu.cohort6.crowdtester.web.rest.UserResource;

import static spark.Spark.*;

/**
 * @author rwatsh on 10/13/15.
 */
public class MainController {
    @Inject
    private DBFactory dbFactory;

    public DBFactory getDbFactory() {
        return dbFactory;
    }

    public static DBClient client = null;

    /**
     * Main method for the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        appInit();

        // Register resources
        UserResource userResource = new UserResource(client);


    }

    /**
     * Initialize application.
     *
     */
    private static void appInit() {
        port(9090);
        // Configure that static files directory.
        staticFileLocation("/");
        Module module = new DatabaseModule();
        MainController mainController = new MainController();
        Guice.createInjector(module).injectMembers(mainController);
        // TODO externalize DB config params
        MainController.client = mainController.getDbFactory().create("localhost", 27017, "crowd_tester_testdb");

        init();
    }
}
