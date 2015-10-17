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

package edu.sjsu.cohort6.crowdtester.database.test;

import edu.sjsu.cohort6.crowdtester.common.model.entity.Bug;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.BugDAO;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/6/15.
 */
public class BugDAOTest extends DBTest<BugDAO, Bug> {
    private static final Logger log = Logger.getLogger(BugDAOTest.class.getName());

    @Override
    public void testAdd() throws Exception {
        Bug b = getBug();
        log.info(b.toString());
        Assert.assertNotNull(b);
    }

    private Bug getBug() {
        Bug a = testCreateBug();
        return a;
    }

    @Override
    public void testRemove() throws Exception {
        Bug b = getBug();
        long n = dao.remove(new ArrayList<String>() {{
            add(b.getId());
        }});
        Assert.assertTrue(n > 0);
    }

    @Override
    public void testUpdate() throws Exception {
        Bug b = getBug();
        b.setValidBug(true);
        dao.update(new ArrayList<Bug>(){{add(b);}});
    }

    @Override
    public void testFetch() throws Exception {
        Bug b = getBug();
        List<Bug> bugs = dao.fetchById(new ArrayList<String>() {{
            add(b.getId());
        }});
        Assert.assertNotNull(bugs);
        Assert.assertNotNull(bugs.get(0));
    }
}
