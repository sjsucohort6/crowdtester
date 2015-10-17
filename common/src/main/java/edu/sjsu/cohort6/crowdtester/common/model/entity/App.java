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

package edu.sjsu.cohort6.crowdtester.common.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cohort6.crowdtester.common.model.BaseModel;
import edu.sjsu.cohort6.crowdtester.common.model.IncentivePolicy;
import edu.sjsu.cohort6.crowdtester.common.model.Platform;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * A Mobile Application to be tested.
 *
 * @author rwatsh on 10/4/15.
 */
@Entity(value = "apps", noClassnameStored = true, concern = "SAFE")
public class App extends BaseModel {
    @Id
    private String id = new ObjectId().toHexString();
    @Indexed(unique = true)
    private String name;
    private String description;

    /**
     * This will be the filename of the app binary file that will be stored in
     * mongodb GridFS.
     */
    private String appFileName;

    @Embedded
    private List<Platform> supportedPlatforms;

    private Date projectStartDate;

    private Date projectEndDate;

    private List<Tester> testers;

    /**
     * Incentive policy for this app.
     * If not set, then global incentive policy for the vendor will be used to determine
     * the incentives to be offered to testers working on this app.
     */
    @Embedded
    private IncentivePolicy appIncentivePolicy;

    @Reference
    private Vendor appVendor;

    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty
    public String getAppFileName() {
        return appFileName;
    }

    public void setAppFileName(String appFileName) {
        this.appFileName = appFileName;
    }

    @JsonProperty
    public List<Platform> getSupportedPlatforms() {
        return supportedPlatforms;
    }

    public void setSupportedPlatforms(List<Platform> supportedPlatforms) {
        this.supportedPlatforms = supportedPlatforms;
    }

    @JsonProperty
    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    @JsonProperty
    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    @JsonProperty
    public List<Tester> getTesters() {
        return testers;
    }

    public void setTesters(List<Tester> testers) {
        this.testers = testers;
    }

    @JsonProperty
    public IncentivePolicy getAppIncentivePolicy() {
        return appIncentivePolicy;
    }

    public void setAppIncentivePolicy(IncentivePolicy appIncentivePolicy) {
        this.appIncentivePolicy = appIncentivePolicy;
    }

    @JsonProperty
    public Vendor getAppVendor() {
        return appVendor;
    }

    public void setAppVendor(Vendor appVendor) {
        this.appVendor = appVendor;
    }

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", appFileName='" + appFileName + '\'' +
                ", supportedPlatforms=" + supportedPlatforms +
                ", projectStartDate=" + projectStartDate +
                ", projectEndDate=" + projectEndDate +
                ", testers=" + testers +
                ", appIncentivePolicy=" + appIncentivePolicy +
                '}';
    }
}
