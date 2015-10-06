package edu.sjsu.cohort6.crowdtester.common.model;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Bug status.
 *
 * @author rwatsh on 10/4/15.
 */
@Embedded
public enum BugStatus {
    OPEN, CLOSED;
}
