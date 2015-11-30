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

import edu.sjsu.cohort6.crowdtester.common.util.EndpointUtils;

/**
 * @author rwatsh on 11/15/15.
 */
public interface HttpConstants {
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";
    public static final int HTTP_BAD_REQUEST = 400; // Request paylaod was invalid
    public static final int HTTP_NOT_AUTHORIZED = 401; // Failed to authenticate
    public static final int HTTP_FORBIDDEN = 403; // Unauthorized
    public static final int HTTP_OK = 200; // Request was ok
    public static final int HTTP_CREATED = 201; // Post request was used to create resource which was successfully created
    public static final int HTTP_ACCEPTED = 202; // Post request was used to create resource which was accepted and scheduled to run in background.
    public static final int HTTP_REDIRECT = 302;
    public static final int HTTP_NOT_FOUND = 404; // Resource was not found on server.
    public static final int HTTP_INTERNAL_ERR = 500; // Server side error.

    // REST API or form post action paths
    String CROWDTESTER_API_V1_0 = EndpointUtils.ENDPOINT_ROOT + "/api/v1.0";
    String CROWDTESTER_API_V1_0_LOGIN = CROWDTESTER_API_V1_0 + "/login";
    String CROWDTESTER_API_V1_0_LOGOUT = CROWDTESTER_API_V1_0 + "/logout";
    String CROWDTESTER_API_V1_0_REGISTER = CROWDTESTER_API_V1_0 + "/register";
    String CROWDTESTER_API_V1_0_APP = CROWDTESTER_API_V1_0 + "/app";
    String CROWDTESTER_API_V1_0_BUG = CROWDTESTER_API_V1_0 + "/bug";
    String CROWDTESTER_API_V1_0_VENDOR = CROWDTESTER_API_V1_0 + "/vendor";
    String CROWDTESTER_API_V1_0_VENDOR_ID = CROWDTESTER_API_V1_0_VENDOR + "/:vendorId";
    String WWW_DIR = "/public";
    String CROWDTESTER_API_V1_0_UPLOAD = CROWDTESTER_API_V1_0 + "/upload";

    // View paths
    // Unprotected views
    public static final String INDEX_PATH = EndpointUtils.ENDPOINT_ROOT + "/index.ftl";

    // Protected views
    public static final String PROTECTED_PATH = EndpointUtils.ENDPOINT_ROOT + "/protected";
    public static final String VENDOR_DASHBOARD_PATH = PROTECTED_PATH + "/vendordashboard.ftl";
    public static final String TESTER_DASHBOARD_PATH = PROTECTED_PATH + "/testerdashboard.ftl";
    public static final String BUG_TRACKER_PATH = PROTECTED_PATH + "/bugtracker.ftl";

}

