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

package edu.sjsu.cohort6.crowdtester.web.route;

import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.SessionDAO;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.UserDAO;
import edu.sjsu.cohort6.crowdtester.web.HttpConstants;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.ArrayList;

/**
 * @author rwatsh on 11/29/15.
 */
public class LogoutRoute implements Route {
    private DBClient dbClient;
    private UserDAO userDAO;
    private SessionDAO sessionDAO;

    public LogoutRoute(DBClient dbClient) {
        this.dbClient = dbClient;
        this.userDAO = (UserDAO) dbClient.getDAO(UserDAO.class);
        this.sessionDAO = (SessionDAO) dbClient.getDAO(SessionDAO.class);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        //User user = RouteHelper.getUserFromSession(request, dbClient);
        Session session = request.session(false);
        // deletes the current user's session
        if (session != null) {
            sessionDAO.remove(new ArrayList<String>(){{add(session.id());}});
        }
        // redirects to home page
        response.redirect(HttpConstants.INDEX_PATH);
        return "";
    }
}
