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
import edu.sjsu.cohort6.crowdtester.common.model.BugStatus;
import edu.sjsu.cohort6.crowdtester.common.model.Severity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

/**
 * Represents the bugs filed per app.
 *
 * @author rwatsh on 10/4/15.
 */
@Entity(value = "bugs", noClassnameStored = true, concern = "SAFE")
public class Bug extends BaseModel {
    @Id
    private String id = new ObjectId().toHexString();

    @Reference
    private App app;

    @Reference
    private Tester tester;

    private Date createdOn;

    private Severity bugSeverity;

    private BugStatus bugStatus;

    /**
     * Bug will be validated by the vendor.
     * Only approved bugs will fetch the tester an incentive for that bug.
     */
    private boolean validBug = false;

    private String bugSummary;
    private String bugDescription;


    @JsonProperty
    public String getBugSummary() {
        return bugSummary;
    }

    public void setBugSummary(String bugSummary) {
        this.bugSummary = bugSummary;
    }

    @JsonProperty
    public String getBugDescription() {
        return bugDescription;
    }

    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }




    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @JsonProperty
    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    @JsonProperty
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @JsonProperty
    public Severity getBugSeverity() {
        return bugSeverity;
    }

    public void setBugSeverity(Severity bugSeverity) {
        this.bugSeverity = bugSeverity;
    }

    @JsonProperty
    public BugStatus getBugStatus() {
        return bugStatus;
    }

    public void setBugStatus(BugStatus bugStatus) {
        this.bugStatus = bugStatus;
    }

    @JsonProperty
    public boolean isValidBug() {
        return validBug;
    }

    public void setValidBug(boolean validBug) {
        this.validBug = validBug;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id='" + id + '\'' +
                ", app=" + app +
                ", tester=" + tester +
                ", createdOn=" + createdOn +
                ", bugSeverity=" + bugSeverity +
                ", bugStatus=" + bugStatus +
                ", validBug=" + validBug +
                ", bugSummary='" + bugSummary + '\'' +
                ", bugDescription='" + bugDescription + '\'' +
                '}';
    }
}
