package edu.sjsu.cohort6.crowdtester.common.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.sjsu.cohort6.crowdtester.common.model.BaseModel;
import edu.sjsu.cohort6.crowdtester.common.model.Skill;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

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
    private List<Skill> skills;

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
    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
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
