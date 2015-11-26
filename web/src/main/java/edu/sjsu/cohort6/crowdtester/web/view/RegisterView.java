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

package edu.sjsu.cohort6.crowdtester.web.view;

import edu.sjsu.cohort6.crowdtester.common.util.EndpointUtils;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

/**
 * @author rwatsh on 10/18/15.
 */
public class RegisterView {
    public static final String USERS_PATH = EndpointUtils.ENDPOINT_ROOT + "/register";
    private DBClient dbClient;

    public RegisterView(DBClient dbClient) {
        this.dbClient = dbClient;
        setUpEndpoints();
    }

    private void setUpEndpoints() {
        FreeMarkerEngine templateEngine = Util.getFreeMarkerEngine();

        get(USERS_PATH, (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            /*List<User> users = userDAO.fetchById(null);
            attributes.put("users", users);*/

            return new ModelAndView(attributes, "register.ftl");
        }, templateEngine);
    }


}
