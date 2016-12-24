package com.webtech.homework.congress.model;

import java.util.Comparator;

/**
 * Created by ferrari on 12/1/2016.
 */

public class CommitteesComparator implements Comparator {
    @Override
    public int compare(Object obj1, Object obj2) {
        Committee c1 = (Committee)obj1;
        Committee c2 = (Committee)obj2;
        return c1.getCommittee_id().compareTo(c2.getCommittee_id());
    }
}
