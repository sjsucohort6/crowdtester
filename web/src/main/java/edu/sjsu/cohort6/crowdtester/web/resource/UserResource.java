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

package edu.sjsu.cohort6.crowdtester.web.resource;

import edu.sjsu.cohort6.crowdtester.common.model.entity.User;
import edu.sjsu.cohort6.crowdtester.common.util.CommonUtils;
import edu.sjsu.cohort6.crowdtester.common.util.EndpointUtils;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.UserDAO;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * @author rwatsh on 10/14/15.
 */
public class UserResource {
    public static final String USERS_PATH = EndpointUtils.ENDPOINT_ROOT + "/users";
    private final UserDAO userDAO;
    private final DBClient dbClient;


    public UserResource(DBClient dbClient) {
        this.dbClient = dbClient;
        this.userDAO = (UserDAO) dbClient.getDAO(UserDAO.class);
        setupEndpoints();
    }

    private void setupEndpoints() {

        FreeMarkerEngine templateEngine = ResourceUtils.getFreeMarkerEngine();

        post(USERS_PATH, "application/json", (request, response) -> {
            String jsonPayload = request.body();

            User user = (User) CommonUtils.convertJsonToObject(jsonPayload, User.class);
            List<User> users = new ArrayList<User>() {{
                add(user);
            }};
            List<String> insertedIds = userDAO.add(users);
            List<User> addedUsers = userDAO.fetchById(insertedIds);
            User addedUser = addedUsers.get(0);
            response.status(201);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("user", addedUser);
            return new ModelAndView(attributes, "users.ftl");
        }, templateEngine);

        get(USERS_PATH, (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<User> users = userDAO.fetchById(null);
            attributes.put("users", users);

            return new ModelAndView(attributes, "users.ftl");
        }, templateEngine);
    }



}
