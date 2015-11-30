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

import edu.sjsu.cohort6.crowdtester.common.model.entity.Tester;
import edu.sjsu.cohort6.crowdtester.common.model.entity.User;
import edu.sjsu.cohort6.crowdtester.common.model.entity.Vendor;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.TesterDAO;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.VendorDAO;
import edu.sjsu.cohort6.crowdtester.web.route.RouteHelper;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

/**
 * @author rwatsh on 10/18/15.
 */
public class VendorDashboardView extends BaseView {
    public VendorDashboardView(String viewPath, DBClient dbClient) {
        super(viewPath, dbClient);
        get(viewPath, (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();
            /*List<User> users = userDAO.fetchById(null);
            attributes.put("users", users);*/
            VendorDAO vendorDAO = (VendorDAO) dbClient.getDAO(VendorDAO.class);
            User user = RouteHelper.getUserFromSession(req, dbClient);
            attributes.put("user", user);
            Vendor vendor = vendorDAO.fetchByUserId(user.getId());
            attributes.put("vendor", vendor);
            TesterDAO testerDAO = (TesterDAO) dbClient.getDAO(TesterDAO.class);
            List<Tester> testers = testerDAO.fetchById(null);
            attributes.put("testers", testers);
            return new ModelAndView(attributes, "vendordashboard.ftl");
        }, templateEngine);
    }
}
