package edu.sjsu.cohort6.crowdtester.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Incentive policy defined by the vendor at a global level and may even be defined at a per app
 * level.
 *
 * Testers will be given incentives based on these policies.
 *
 * @author rwatsh on 10/4/15.
 */
@Embedded
public class IncentivePolicy {
    private double incentivePerHighBugFiled;
    private double incentivePerMediumBugFiled;
    private double incentivePerLowBugFiled;

    @JsonProperty
    public double getIncentivePerHighBugFiled() {
        return incentivePerHighBugFiled;
    }

    public void setIncentivePerHighBugFiled(double incentivePerHighBugFiled) {
        this.incentivePerHighBugFiled = incentivePerHighBugFiled;
    }

    @JsonProperty
    public double getIncentivePerMediumBugFiled() {
        return incentivePerMediumBugFiled;
    }

    public void setIncentivePerMediumBugFiled(double incentivePerMediumBugFiled) {
        this.incentivePerMediumBugFiled = incentivePerMediumBugFiled;
    }

    @JsonProperty
    public double getIncentivePerLowBugFiled() {
        return incentivePerLowBugFiled;
    }

    public void setIncentivePerLowBugFiled(double incentivePerLowBugFiled) {
        this.incentivePerLowBugFiled = incentivePerLowBugFiled;
    }

    @Override
    public String toString() {
        return "IncentivePolicy{" +
                "incentivePerHighBugFiled=" + incentivePerHighBugFiled +
                ", incentivePerMediumBugFiled=" + incentivePerMediumBugFiled +
                ", incentivePerLowBugFiled=" + incentivePerLowBugFiled +
                '}';
    }
}
