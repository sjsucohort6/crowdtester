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

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * @author rwatsh on 10/15/15.
 */
public class Util {


    public static FreeMarkerEngine getFreeMarkerEngine() {
        Configuration config = new Configuration();
        /**
         * Let the Spark Freemarker integration class load the template.
         */
        config.setClassForTemplateLoading(FreeMarkerEngine.class, "");
        /**
         * Add object wrapper.
         */
        config.setObjectWrapper(new DefaultObjectWrapper());
        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        config.setDefaultEncoding("UTF-8");

        // TODO change it to RETHROW_HANDLER in production.
        config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        return new FreeMarkerEngine(config);
    }
}
