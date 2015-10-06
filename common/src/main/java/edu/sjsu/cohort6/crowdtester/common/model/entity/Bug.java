package edu.sjsu.cohort6.crowdtester.common.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cohort6.crowdtester.common.model.BaseModel;
import edu.sjsu.cohort6.crowdtester.common.model.BugStatus;
import edu.sjsu.cohort6.crowdtester.common.model.Severity;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

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
                '}';
    }
}
