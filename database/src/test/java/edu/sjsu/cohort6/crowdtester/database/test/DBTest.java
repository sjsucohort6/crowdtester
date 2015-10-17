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

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import edu.sjsu.cohort6.crowdtester.common.model.*;
import edu.sjsu.cohort6.crowdtester.common.model.entity.*;
import edu.sjsu.cohort6.crowdtester.database.dao.BaseDAO;
import edu.sjsu.cohort6.crowdtester.database.dao.DBClient;
import edu.sjsu.cohort6.crowdtester.database.dao.DBFactory;
import edu.sjsu.cohort6.crowdtester.database.dao.DatabaseModule;
import edu.sjsu.cohort6.crowdtester.database.dao.mongodb.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Optional;

import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class is meant to be inherited from by all DB tests.
 * It has methods for test setup and tear down.
 *
 * @author rwatsh on 9/24/15.
 */
public abstract class DBTest<T extends BaseDAO, S> {
    @Inject
    private DBFactory dbFactory;
    protected T dao;
    private Class<T> tClass;

    public String dbName = "crowd_tester_testdb";
    private static final Logger log = Logger.getLogger(DBTest.class.getName());
    protected static DBClient client;
    private long startTime;

    public DBTest() {
        Module module = new DatabaseModule();
        Guice.createInjector(module).injectMembers(this);

    }

    @BeforeClass
    @Parameters({"server", "port", "dbName"})
    public void setUp(@Optional("localhost") String server,
                      @Optional("27017") String port,
                      @Optional("crowd_tester_testdb") String dbName) throws Exception {
        client = dbFactory.create(server, Integer.parseInt(port), dbName);

        this.dbName = dbName;
        /*
         * Use reflection to infer the class for T type.
         */
        this.tClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        dao = (T) client.getDAO(tClass);
        client.dropDB(this.dbName);
    }


    @AfterClass
    public void tearDown() throws Exception {
        client.close();
    }

    @BeforeMethod
    public void createDB() {
        client.useDB(dbName);
        log.info("********************");
        startTime = System.currentTimeMillis();
    }

    @AfterMethod
    public void dropDB() {
        //client.dropDB(dbName);
        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        log.info(MessageFormat.format("********* Time taken: {0} ms", diff));
    }

    /*
     * Abstract test methods to be implemented by concrete test classes.
     */
    @Test
    abstract public void testAdd() throws Exception;

    @Test
    abstract public void testRemove() throws Exception;

    @Test
    abstract public void testUpdate() throws Exception;

    @Test
    abstract public void testFetch() throws Exception;

    /**
     * Common test methods shared across test sub classes.
     */


    public static User testCreateUser() {
        User user = getTestUser();
        UserDAO userDAO = (UserDAO) client.getDAO(UserDAO.class);
        List<User> usersList = new ArrayList<>();
        usersList.add(user);
        List<String> insertedIds = userDAO.add(usersList);
        log.info("User: " + user);
        List<User> users = userDAO.fetchById(insertedIds);
        Assert.assertNotNull(users);
        Assert.assertTrue(!users.isEmpty());
        log.info("User created: " + users);
        return users.get(0);
    }

    public static User getTestUser() {
        String randomStr = getRandomStr();
        return new User("john.doe@sjsu.edu" + randomStr, "john.doe@sjsu.edu" + randomStr, "John" + randomStr, "Doe", new Role(RoleType.TESTER));
    }

    private static String getRandomStr() {
        Random rand = new Random();
        return Integer.toString(rand.nextInt());
    }

    public static Tester testCreateTester() {
        Tester tester = new Tester();
        tester.setCreditPoint(0);
        tester.setSkills(new ArrayList<Skill>() {
            {
                add(new Skill("IOS"));
                add(new Skill("Android"));
                add(new Skill("Java"));
            }});
        tester.setUser(testCreateUser());
        TesterDAO testerDAO = (TesterDAO) client.getDAO(TesterDAO.class);
        List<String> insertedIds = testerDAO.add(new ArrayList<Tester>() {{
            add(tester);
        }});
        List<Tester> testers = testerDAO.fetchById(insertedIds);
        Assert.assertTrue(testers != null && !testers.isEmpty());
        return testers.get(0);
    }


    public static App testCreateApp(Vendor vendor, Tester tester) {
        App app = new App();
        app.setName("CoolApp" + getRandomStr());
        app.setAppFileName("/files/coolapp.zip");
        app.setAppIncentivePolicy(getIncentivePolicy());
        app.setAppVendor(vendor);
        app.setDescription("Very cool app");
        app.setProjectEndDate(getDateNDaysFromNow(20));
        app.setProjectStartDate(getDateNDaysFromNow(2));
        app.setSupportedPlatforms(new ArrayList<Platform>() {{
            add(Platform.ANDROID);
            add(Platform.IOS);
        }});
        app.setTesters(new ArrayList<Tester>() {{
            add(tester);
        }});
        AppDAO appDAO = (AppDAO) client.getDAO(AppDAO.class);
        List<String> insertedIds = appDAO.add(new ArrayList<App>() {{
            add(app);
        }});
        List<App> apps = appDAO.fetchById(insertedIds);
        Assert.assertTrue(apps != null && !apps.isEmpty());
        return apps.get(0);
    }

    public static Date getDateNDaysFromNow(int x) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.add( Calendar.DAY_OF_YEAR, x);
        Date nDaysFromNow = cal.getTime();
        return nDaysFromNow;
    }

    public static Vendor testCreateVendor() {
        Vendor vendor = new Vendor();
        // Will be set after app is created for this vendor.
        // Caller needs to tie in this.
        vendor.setApps(new ArrayList<App>() {{}});
        vendor.setChargeBackPolicy(getVendorChargeBackPolicy());
        vendor.setDateJoined(new Date());
        vendor.setIncentivePolicy(getIncentivePolicy());
        vendor.setName("Acme Corp" + getRandomStr());
        vendor.setUser(testCreateUser());
        VendorDAO vendorDAO = (VendorDAO) client.getDAO(VendorDAO.class);
        List<String> insertedIds = vendorDAO.add(new ArrayList<Vendor>(){{add(vendor);}});
        Assert.assertNotNull(insertedIds);
        List<Vendor> vendors = vendorDAO.fetchById(insertedIds);
        Assert.assertNotNull(vendors);
        return vendors.get(0);
    }

    public static Bug testCreateBug(App a, Tester t) {
        Bug b = new Bug();
        b.setApp(a);
        b.setBugSeverity(Severity.HIGH);
        b.setBugStatus(BugStatus.OPEN);
        b.setTester(t);
        b.setValidBug(false);
        b.setCreatedOn(new Date());
        BugDAO bugDAO = (BugDAO) client.getDAO(BugDAO.class);
        List<String> insertedIds = bugDAO.add(new ArrayList<Bug>() {{
            add(b);
        }});
        List<Bug> bugs = bugDAO.fetchById(insertedIds);
        Assert.assertTrue(bugs != null && !bugs.isEmpty());
        return bugs.get(0);
    }

    public static Bug testCreateBug() {
        Tester t = testCreateTester();
        Vendor v = testCreateVendor();
        App a = testCreateApp(v, t);
        return testCreateBug(a, t);
    }

    public static App testCreateApp() {
        Vendor v = testCreateVendor();
        Tester t = testCreateTester();
        App a = testCreateApp(v, t);
        v.setApps(new ArrayList<App>() {{add(a);}} );
        VendorDAO vendorDAO = (VendorDAO) client.getDAO(VendorDAO.class);
        vendorDAO.update(new ArrayList<Vendor>() {{
            add(v);
        }});
        return a;
    }

    private static IncentivePolicy getIncentivePolicy() {

        IncentivePolicy incentivePolicy = new IncentivePolicy();
        incentivePolicy.setIncentivePerHighBugFiled(100.0);
        incentivePolicy.setIncentivePerMediumBugFiled(50.0);
        incentivePolicy.setIncentivePerLowBugFiled(20.0);
        return incentivePolicy;
    }

    private static VendorChargeBackPolicy getVendorChargeBackPolicy() {
        VendorChargeBackPolicy vendorChargeBackPolicy = new VendorChargeBackPolicy();
        vendorChargeBackPolicy.setCostPerDay(20.0);
        return vendorChargeBackPolicy;
    }
}
