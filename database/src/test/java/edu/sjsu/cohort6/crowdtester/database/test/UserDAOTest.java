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

import edu.sjsu.cohort6.crowdtester.common.model.entity.User;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.UserDAO;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 9/24/15.
 */
public class UserDAOTest extends DBTest<UserDAO, User> {

    private static final Logger log = Logger.getLogger(UserDAOTest.class.getName());

    @Override
    public void testAdd() {
        User user = testCreateUser();
        Assert.assertNotNull(user);
    }

    @Override
    public void testRemove() throws Exception {
        User user = testCreateUser();
        Assert.assertNotNull(user);
        long num = dao.remove(new ArrayList<String>() {{
            add(user.getId());
        }});
        Assert.assertTrue(num > 0);
    }

    @Override
    public void testUpdate() throws Exception {
        User user = testCreateUser();
        Assert.assertNotNull(user);
        user.setEmailId("test@test.com");
        dao.update(new ArrayList<User>() {{
            add(user);
        }});
    }

    @Override
    public void testFetch() throws Exception {
        User user = testCreateUser();
        Assert.assertNotNull(user);
        List<User> users = dao.fetchById(null);
        log.info(users.toString());
        Assert.assertNotNull(users);
        Assert.assertTrue(!users.isEmpty(), "Could not fetch users");
    }
}
