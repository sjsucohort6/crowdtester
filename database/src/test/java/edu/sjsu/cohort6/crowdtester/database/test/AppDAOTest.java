/*
 * Copyright (c) 2015 San Jose State University.    Permission is hereby granted, free of charge, to any person obtaining a copy  of this software and associated documentation files (the "Software"), to deal  in the Software without restriction, including without limitation the rights  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  copies of the Software, and to permit persons to whom the Software is  furnished to do so, subject to the following conditions:    The above copyright notice and this permission notice shall be included in  all copies or substantial portions of the Software.
 */

package edu.sjsu.cohort6.crowdtester.database.test;

import edu.sjsu.cohort6.crowdtester.common.model.entity.App;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.AppDAO;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/6/15.
 */
public class AppDAOTest extends DBTest<AppDAO, App> {
    private static final Logger log = Logger.getLogger(AppDAOTest.class.getName());

    @Override
    public void testAdd() throws Exception {
        App a = getApp();
        log.info("Created app: " + a);
        Assert.assertNotNull(a);
    }

    private App getApp() {
        App a = testCreateApp();
        return a;
    }

    @Override
    public void testRemove() throws Exception {
        App a = getApp();
        dao.remove(new ArrayList<String>() {{add(a.getId());}});
        log.info("Deleted app: " + a);
    }

    @Override
    public void testUpdate() throws Exception {
        App a = getApp();
        a.setDescription("updated description");
        dao.update(new ArrayList<App>() {{
            add(a);
        }});
        List<App> apps = dao.fetchById(new ArrayList<String>() {{
            add(a.getId());
        }});
        Assert.assertNotNull(apps);
        log.info(apps.toString());
    }

    @Override
    public void testFetch() throws Exception {
        App a = getApp();

        List<App> apps = dao.fetchById(new ArrayList<String>() {{
            add(a.getId());
        }});
        Assert.assertNotNull(apps);
        log.info(apps.toString());
    }
}
