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
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_NOT_AUTHORIZED = 401;
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_ACCEPTED = 202;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_INTERNAL_ERR = 500;

    // REST API paths
    String CROWDTESTER_API_V1_0 = EndpointUtils.ENDPOINT_ROOT + "/api/v1.0";
    String CROWDTESTER_API_V1_0_APP = CROWDTESTER_API_V1_0 + "/app";
    String CROWDTESTER_API_V1_0_BUG = CROWDTESTER_API_V1_0 + "/bug";
    String CROWDTESTER_API_V1_0_VENDOR = CROWDTESTER_API_V1_0 + "/vendor";
    String CROWDTESTER_API_V1_0_VENDOR_ID = CROWDTESTER_API_V1_0_VENDOR + "/:vendorId";
    String WWW_DIR = "/public";

    //view paths
    public static final String INDEX_PATH = EndpointUtils.ENDPOINT_ROOT + "/index.ftl";
}

