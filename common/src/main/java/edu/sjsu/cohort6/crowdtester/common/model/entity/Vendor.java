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
import edu.sjsu.cohort6.crowdtester.common.model.VendorChargeBackPolicy;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Mobile Application Vendor.
 *
 * @author rwatsh on 10/4/15.
 */
@Entity(value = "vendors", noClassnameStored = true, concern = "SAFE")
public class Vendor extends BaseModel {
    @Id
    private String id = new ObjectId().toHexString();

    @Indexed(unique = true)
    private String name;

    private Date dateJoined;

    @Reference
    private List<App> apps;

    @Embedded
    private IncentivePolicy incentivePolicy;

    @Reference
    private User user;

    @Embedded
    private VendorChargeBackPolicy chargeBackPolicy;

    Date lastUpdated = new Date();

    private static final Logger log = Logger.getLogger(Vendor.class.getName());
    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

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
    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    @JsonProperty
    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    @JsonProperty
    public IncentivePolicy getIncentivePolicy() {
        return incentivePolicy;
    }

    public void setIncentivePolicy(IncentivePolicy incentivePolicy) {
        this.incentivePolicy = incentivePolicy;
    }

    @JsonProperty
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public VendorChargeBackPolicy getChargeBackPolicy() {
        return chargeBackPolicy;
    }

    public void setChargeBackPolicy(VendorChargeBackPolicy chargeBackPolicy) {
        this.chargeBackPolicy = chargeBackPolicy;
    }

    @JsonProperty
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateJoined=" + dateJoined +
                ", apps=" + apps +
                ", incentivePolicy=" + incentivePolicy +
                ", user=" + user +
                ", chargeBackPolicy=" + chargeBackPolicy +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
