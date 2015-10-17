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

import edu.sjsu.cohort6.crowdtester.common.model.entity.Tester;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.TesterDAO;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rwatsh on 10/6/15.
 */
public class TesterDAOTest extends DBTest<TesterDAO, Tester> {
    @Override
    public void testAdd() throws Exception {
        Tester t = testCreateTester();
        Assert.assertNotNull(t);
    }

    @Override
    public void testRemove() throws Exception {
        Tester t = testCreateTester();
        Assert.assertNotNull(t);
        long n = dao.remove(new ArrayList<String>() {{
            add(t.getId());
        }});
        Assert.assertTrue(n > 0);
    }

    @Override
    public void testUpdate() throws Exception {
        Tester t = testCreateTester();
        t.setCreditPoint(10);
        dao.update(new ArrayList<Tester>(){{add(t);}});
    }

    @Override
    public void testFetch() throws Exception {
        Tester t = testCreateTester();
        List<Tester> testers = dao.fetchById(new ArrayList<String>() {{
            add(t.getId());
        }});

        Assert.assertTrue(testers != null && !testers.isEmpty());
    }
}
