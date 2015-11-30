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

import edu.sjsu.cohort6.crowdtester.common.model.entity.User;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.UserDAO;
import edu.sjsu.cohort6.crowdtester.web.HttpConstants;
import edu.sjsu.cohort6.crowdtester.web.payload.LoginPayload;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * @author rwatsh on 11/26/15.
 */
public class LoginRoute implements Route {
    private DBClient dbClient;
    private UserDAO userDAO;
    private RouteHelper helper = new RouteHelper();

    public LoginRoute(DBClient dbClient) {
        this.dbClient = dbClient;
        this.userDAO = (UserDAO) dbClient.getDAO(UserDAO.class);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            /*ObjectMapper mapper = new ObjectMapper();
            LoginPayload loginPayload = CommonUtils.convertJsonToObject(request.body(), LoginPayload.class);
            if (!loginPayload.isValid()) {
                response.status(HttpConstants.HTTP_BAD_REQUEST);
                return "Login payload is not valid";
            }*/
            LoginPayload loginPayload = new LoginPayload();
            loginPayload.setPassword(request.queryParams("password"));
            loginPayload.setUser(request.queryParams("user"));
            User user = userDAO.getUserByCredentials(loginPayload.getUser(), loginPayload.getPassword()).get();
            Session session = helper.redirectUserByRole(request, response, user, dbClient);
            if (session == null) {
                response.status(HttpConstants.HTTP_INTERNAL_ERR);
                return "Unable to add user session for user: " + user.getUserName();
            }
            return "";
            /*HttpServletRequest httpServletRequest = request.raw();
            return MessageFormat.format("{0}://{1}:{2}{3}",
                    httpServletRequest.getScheme(),
                    httpServletRequest.getServerName(),
                    Integer.toString(httpServletRequest.getServerPort()),
                    redirectUrl);*/
        } catch (Exception e) {
            response.status(HttpConstants.HTTP_NOT_AUTHORIZED);
            return e.toString();
        }
    }
}
