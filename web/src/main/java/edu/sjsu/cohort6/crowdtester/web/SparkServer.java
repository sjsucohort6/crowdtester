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
import edu.sjsu.cohort6.crowdtester.common.model.IncentivePolicy;
import edu.sjsu.cohort6.crowdtester.common.model.VendorChargeBackPolicy;
import edu.sjsu.cohort6.crowdtester.common.model.entity.User;
import edu.sjsu.cohort6.crowdtester.common.util.EndpointUtils;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.DBFactory;
import edu.sjsu.cohort6.crowdtester.database.dao.DatabaseModule;
import edu.sjsu.cohort6.crowdtester.web.route.*;
import edu.sjsu.cohort6.crowdtester.web.view.MainView;
import edu.sjsu.cohort6.crowdtester.web.view.TesterDashboardView;
import edu.sjsu.cohort6.crowdtester.web.view.VendorDashboardView;
import lombok.extern.java.Log;
import spark.Filter;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
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
            double incentivePerHighBug = Double.parseDouble(props.getProperty("incentivePerHighBug"));
            double incentivePerMediumBug = Double.parseDouble(props.getProperty("incentivePerMediumBug"));
            double incentivePerLowBug = Double.parseDouble(props.getProperty("incentivePerLowBug"));
            IncentivePolicy incentivePolicy = new IncentivePolicy();
            incentivePolicy.setIncentivePerHighBugFiled(incentivePerHighBug);
            incentivePolicy.setIncentivePerMediumBugFiled(incentivePerMediumBug);
            incentivePolicy.setIncentivePerLowBugFiled(incentivePerLowBug);
            double costPerDay = Double.parseDouble(props.getProperty("costPerDay"));
            VendorChargeBackPolicy vendorChargeBackPolicy = new VendorChargeBackPolicy();
            vendorChargeBackPolicy.setCostPerDay(costPerDay);

            DBClient dbClient = app.initDB(dbServer, dbPort, dbName);
            port(serverPort);
            // Configure the static files directory.
            staticFileLocation(HttpConstants.WWW_DIR);

            // Setup Views for the web application
            MainView mainView = new MainView(HttpConstants.INDEX_PATH, dbClient);
            TesterDashboardView testerDashboardView = new TesterDashboardView(HttpConstants.TESTER_DASHBOARD_PATH, dbClient);
            VendorDashboardView vendorDashboardView = new VendorDashboardView(HttpConstants.VENDOR_DASHBOARD_PATH, dbClient);

            // Setup Routes for the web application (these are the REST endpoints or resources or just route handlers/redirectors)
            get("/", new RootRoute());
            get(EndpointUtils.ENDPOINT_ROOT, new RootRoute());
            get(EndpointUtils.ENDPOINT_ROOT + "/", new RootRoute());
            post(HttpConstants.CROWDTESTER_API_V1_0_LOGIN, new LoginRoute(dbClient));
            post(HttpConstants.CROWDTESTER_API_V1_0_REGISTER, new RegisterRoute(dbClient, incentivePolicy, vendorChargeBackPolicy));
            get(HttpConstants.CROWDTESTER_API_V1_0_LOGOUT, new LogoutRoute(dbClient));
            post(HttpConstants.CROWDTESTER_API_V1_0_UPLOAD, new UploadRoute(dbClient));

            // Setup Filters
            enableCORS("*", "*", "*");
            authenticateFilter(HttpConstants.PROTECTED_PATH + "/*", dbClient);
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

    private static void debugFilter(String urlPattern) {
        after(urlPattern, (request, response) -> {
            HttpServletResponse httpServletResponse = response.raw();
            log.info(httpServletResponse.toString());
        });
    }

    private static void authenticateFilter(String protectedUrlPattern, DBClient dbClient) {
        before(protectedUrlPattern, (request, response) -> {
            // ... check if authenticated
            User user = RouteHelper.getUserFromSession(request, dbClient);
            if (user != null) {
                log.info("Authenticating from session id for user: " + user.getUserName());
                // check authorization...
                RouteHelper helper = new RouteHelper();
                if (helper.isAdmin(user)) {
                    return;
                } else if (helper.isVendor(user)) {
                    if (request.url().contains(HttpConstants.BUG_TRACKER_PATH) ||
                            request.url().contains(HttpConstants.VENDOR_DASHBOARD_PATH)) {
                        return;
                    }
                } else if (helper.isTester(user)) {
                    if (request.url().contains(HttpConstants.BUG_TRACKER_PATH) ||
                            request.url().contains(HttpConstants.TESTER_DASHBOARD_PATH)) {
                        return;
                    }
                }
                //halt(HttpConstants.HTTP_FORBIDDEN, "User not authorized to view this page.");

            }
            //halt(HttpConstants.HTTP_NOT_AUTHORIZED, "User failed to authenticate.");
            response.redirect(HttpConstants.INDEX_PATH);
        });
    }




}
