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
