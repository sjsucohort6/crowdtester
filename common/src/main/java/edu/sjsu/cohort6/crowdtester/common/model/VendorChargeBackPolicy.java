package edu.sjsu.cohort6.crowdtester.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Pay-as-you-go policy for the vendor.
 *
 * This may vary per Vendor.
 *
 * @author rwatsh on 10/4/15.
 */
@Embedded
public class VendorChargeBackPolicy {
    /**
     * Vendors will be charged based on the number of days the app testing is required.
     */
    private double costPerDay;

    @JsonProperty
    public double getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }

    @Override
    public String toString() {
        return "VendorChargeBackPolicy{" +
                "costPerDay=" + costPerDay +
                '}';
    }
}
