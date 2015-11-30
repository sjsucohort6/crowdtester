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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;
import java.util.List;

import static spark.Spark.halt;

/**
 * @author rwatsh on 11/29/15.
 */
public class UploadRoute implements Route {

    public UploadRoute(DBClient dbClient) {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        final File upload = new File("upload");
        if (!upload.exists() && !upload.mkdirs()) {
            throw new RuntimeException("Failed to create directory " + upload.getAbsolutePath());
        }

        // apache commons-fileupload to handle file upload
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(upload);
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        List<FileItem> items = fileUpload.parseRequest(request.raw());

        // image is the field name that we want to save
        FileItem item = items.stream()
                .filter(e -> "appFile".equals(e.getFieldName()))
                .findFirst().get();
        String fileName = item.getName();
        item.write(new File(upload, fileName));
        halt(200);
        return null;

    }


}
