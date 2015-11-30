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

import edu.sjsu.cohort6.crowdtester.common.model.RoleType;
import edu.sjsu.cohort6.crowdtester.common.model.entity.User;
import edu.sjsu.cohort6.crowdtester.common.model.entity.UserSession;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.SessionDAO;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.UserDAO;
import edu.sjsu.cohort6.crowdtester.web.HttpConstants;
import spark.Request;
import spark.Response;
import spark.Session;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class with common methods across routes.
 *
 * @author rwatsh on 11/27/15.
 */
public class RouteHelper {
    public static boolean isVendor(User user) {
        return user.getRole().getRole() == RoleType.VENDOR;
    }

    public static boolean isTester(User user) {
        return user.getRole().getRole() == RoleType.TESTER;
    }

    public static boolean isAdmin(User user) {
        return user.getRole().getRole() == RoleType.ADMIN;
    }

    public Session redirectUserByRole(Request request, Response response, User user, DBClient dbClient) {
        Session session = getSession(request, response, user, dbClient);
        if (session != null) {
            redirectUser(response, user);
        }
        return session;
    }

    public String getRedirectUrl(Request request, Response response, User user, DBClient dbClient) {
        Session session = getSession(request, response, user, dbClient);
        String redirectUrl = null;
        if (session != null) {
            redirectUrl = getRedirectUrl(user);
        }
        return redirectUrl;
    }

    private Session getSession(Request request, Response response, User user, DBClient dbClient) {
        Session session = request.session(true); // create if does not exist
        response.raw().addCookie(new Cookie("session", session.id())); //add session cookie

        // Create an entry for the user and its session in the DB.
        SessionDAO sessionDAO = (SessionDAO) dbClient.getDAO(SessionDAO.class);
        UserSession userSession = new UserSession(session.id(), user.getUserName());
        List<String> insertedIds = sessionDAO.add(new ArrayList<UserSession>() {{
            add(userSession);
        }});
        if( insertedIds != null && !insertedIds.isEmpty()) {
            return session;
        }

        return null;
    }

    private void redirectUser(Response response, User user) {
        String redirectPath = getRedirectUrl(user);

        response.redirect(redirectPath);
    }

    private String getRedirectUrl(User user) {
        String redirectPath = null;
        if (isTester(user)) {
            redirectPath = (HttpConstants.TESTER_DASHBOARD_PATH);
        } else if (isVendor(user)) {
            redirectPath = (HttpConstants.VENDOR_DASHBOARD_PATH);
        } else {
            redirectPath = (HttpConstants.INDEX_PATH);
        }
        return redirectPath;
    }


    public static User getUserFromSession(final Request request, DBClient dbClient) {
        Session session = request.session(false);
        if (session != null) {
            SessionDAO sessionDAO = (SessionDAO) dbClient.getDAO(SessionDAO.class);
            List<UserSession> sessionsList = sessionDAO.fetchById(new ArrayList<String>() {{
                add(session.id());
            }});
            if (sessionsList != null && !sessionsList.isEmpty()) {
                UserSession userSession = sessionsList.get(0);
                UserDAO userDAO = (UserDAO) dbClient.getDAO(UserDAO.class);
                List<User> users = userDAO.fetchByName(userSession.getUserName());
                if (users != null && !users.isEmpty()) {
                    return users.get(0);
                }
            }
        }

        return null;
    }
}
