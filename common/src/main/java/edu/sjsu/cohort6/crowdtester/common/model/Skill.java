package edu.sjsu.cohort6.crowdtester.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Represents testing skills.
 *
 * @author rwatsh on 10/4/15.
 */
@Embedded
public class Skill {
    private String name;

    public Skill(String name) {
        this.name = name;
    }

    public Skill() {}

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "name='" + name + '\'' +
                '}';
    }
}
