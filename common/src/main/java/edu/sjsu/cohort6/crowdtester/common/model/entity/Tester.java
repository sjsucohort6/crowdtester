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
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A crowd tester.
 *
 * @author rwatsh on 10/4/15.
 */
@Entity(value = "testers", noClassnameStored = true, concern = "SAFE")
public class Tester extends BaseModel {
    @Id
    private String id = new ObjectId().toHexString();
    @Reference
    private User user;

    @NotNull
    @NotEmpty
    private List<String> skills;


    /**
     * System will auto assign credit points for every valid bug filed by the tester.
     *
     * 1 point for a valid low severity bug
     * 2 points for a valid medium severity bug
     * 3 points for a valid high severity bug
     *
     * UI can use the creditPoints attained by a tester to rank the tester.
     */
    private long creditPoint = 0;

    @JsonProperty
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @JsonProperty
    public long getCreditPoint() {
        return creditPoint;
    }

    public void setCreditPoint(long creditPoint) {
        this.creditPoint = creditPoint;
    }

    @Override
    public String toString() {
        return "Tester{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", skills=" + skills +
                ", creditPoint=" + creditPoint +
                '}';
    }
}
