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

import com.google.common.base.Optional;
import edu.sjsu.cohort6.crowdtester.common.model.IncentivePolicy;
import edu.sjsu.cohort6.crowdtester.common.model.Role;
import edu.sjsu.cohort6.crowdtester.common.model.RoleType;
import edu.sjsu.cohort6.crowdtester.common.model.VendorChargeBackPolicy;
import edu.sjsu.cohort6.crowdtester.common.model.entity.App;
import edu.sjsu.cohort6.crowdtester.common.model.entity.Tester;
import edu.sjsu.cohort6.crowdtester.common.model.entity.User;
import edu.sjsu.cohort6.crowdtester.common.model.entity.Vendor;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.TesterDAO;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.UserDAO;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.VendorDAO;
import edu.sjsu.cohort6.crowdtester.web.HttpConstants;
import edu.sjsu.cohort6.crowdtester.web.payload.RegisterPayload;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author rwatsh on 11/26/15.
 */
public class RegisterRoute implements Route {
    public static final String VENDOR = "VENDOR";
    public static final String TESTER = "TESTER";
    private DBClient dbClient;
    private UserDAO userDAO;
    private IncentivePolicy incentivePolicy;
    private VendorChargeBackPolicy vendorChargeBackPolicy;
    private RouteHelper helper = new RouteHelper();

    public RegisterRoute(DBClient dbClient, IncentivePolicy incentivePolicy, VendorChargeBackPolicy vendorChargeBackPolicy) {
        this.dbClient = dbClient;
        this.userDAO = (UserDAO) dbClient.getDAO(UserDAO.class);
        this.incentivePolicy = incentivePolicy;
        this.vendorChargeBackPolicy = vendorChargeBackPolicy;
    }

    private List<String> convertCSVToList(String csvStr) {
        List<String> list = new ArrayList<>();
        if (csvStr != null) {
            StringTokenizer tokenizer = new StringTokenizer(csvStr, " ");
            while (tokenizer.hasMoreTokens()) {
                list.add(tokenizer.nextToken());
            }
        }
        return list;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            /*ObjectMapper mapper = new ObjectMapper();
            RegisterPayload registerPayload = CommonUtils.convertJsonToObject(request.body(), RegisterPayload.class);
            if (!registerPayload.isValid()) {
                response.status(HttpConstants.HTTP_BAD_REQUEST);
                return "Registeration payload is not valid";
            }*/
            RegisterPayload registerPayload = new RegisterPayload();
            registerPayload.setPassword(request.queryParams("pass"));
            registerPayload.setLastName(request.queryParams("lastName"));
            registerPayload.setFirstName(request.queryParams("firstName"));
            registerPayload.setEmailId(request.queryParams("emailId"));
            registerPayload.setRole(request.queryParams("role"));
            registerPayload.setTesterSkills(convertCSVToList(request.queryParams("testerSkills")));
            registerPayload.setVendorName(request.queryParams("vendorName"));

            Optional<User> optional = userDAO.getUserByCredentials(registerPayload.getEmailId(), registerPayload.getPassword());
            if (optional.isPresent()) {
                response.status(HttpConstants.HTTP_BAD_REQUEST);
                return "User already exists with email ID: " + registerPayload.getEmailId();
            } else {
                // create a new user TODO create a vendor or tester
                User user = new User();
                user.setEmailId(registerPayload.getEmailId());
                user.setFirstName(registerPayload.getFirstName());
                user.setLastName(registerPayload.getLastName());
                switch (registerPayload.getRole().toUpperCase()) {
                    case VENDOR:
                        user.setRole(new Role(RoleType.VENDOR));
                        break;
                    case TESTER:
                        user.setRole(new Role(RoleType.TESTER));
                        break;
                    default:
                        user.setRole(new Role(RoleType.ADMIN));
                        break;
                }
                user.setToken(registerPayload.getPassword());
                user.setUserName(registerPayload.getEmailId());

                List<String> insertedIds = userDAO.add(new ArrayList<User>() {{
                    add(user);
                }});
                if (insertedIds != null && !insertedIds.isEmpty()) {

                    switch (registerPayload.getRole().toUpperCase()) {
                        case VENDOR:
                            Vendor vendor = new Vendor();
                            // Will be set after app is created for this vendor.
                            // Caller needs to tie in this.
                            vendor.setApps(new ArrayList<App>() {{}});
                            VendorChargeBackPolicy vendorChargeBackPolicy = new VendorChargeBackPolicy();
                            vendorChargeBackPolicy.setCostPerDay(vendorChargeBackPolicy.getCostPerDay()); // config.props
                            vendor.setChargeBackPolicy(vendorChargeBackPolicy);
                            vendor.setDateJoined(new Date());
                            IncentivePolicy incentivePolicy = new IncentivePolicy(); // config.props
                            incentivePolicy.setIncentivePerHighBugFiled(incentivePolicy.getIncentivePerHighBugFiled());
                            incentivePolicy.setIncentivePerMediumBugFiled(incentivePolicy.getIncentivePerMediumBugFiled());
                            incentivePolicy.setIncentivePerLowBugFiled(incentivePolicy.getIncentivePerLowBugFiled());
                            vendor.setIncentivePolicy(incentivePolicy);
                            vendor.setName(registerPayload.getVendorName()); // name
                            vendor.setUser(user);
                            VendorDAO vendorDAO = (VendorDAO) dbClient.getDAO(VendorDAO.class);
                            vendorDAO.add(new ArrayList<Vendor>(){{add(vendor);}});
                            break;
                        case TESTER:
                            Tester tester = new Tester();
                            tester.setCreditPoint(0);
                            List<String> testerSkills = registerPayload.getTesterSkills();
                            if(testerSkills != null && !testerSkills.isEmpty()) {
                                tester.setSkills(testerSkills);
                            } else {
                                tester.setSkills(new ArrayList<String>());
                            }
                            tester.setUser(user);
                            TesterDAO testerDAO = (TesterDAO) dbClient.getDAO(TesterDAO.class);
                            testerDAO.add(new ArrayList<Tester>() {{
                                add(tester);
                            }});
                            break;
                        default:
                            break;
                    }

                    Session session = helper.redirectUserByRole(request, response, user, dbClient);

                    if(session == null) {
                        response.status(HttpConstants.HTTP_INTERNAL_ERR);
                        return "Unable to add user session for user: " + registerPayload.getEmailId();
                    }
                    return "";
                } else {
                    response.status(HttpConstants.HTTP_INTERNAL_ERR);
                    return "Unable to add user: " + registerPayload.getEmailId();
                }
            }

        } catch (Exception e) {
            response.status(HttpConstants.HTTP_NOT_AUTHORIZED);
            return e.toString();
        }
    }


}
