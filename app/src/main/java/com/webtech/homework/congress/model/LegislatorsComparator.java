package com.webtech.homework.congress.model;

import java.util.Comparator;

/**
 * Created by ferrari on 11/29/2016.
 */

public class LegislatorsComparator implements Comparator {

    @Override
    public int compare(Object obj1, Object obj2) {
        Legislator o1 = (Legislator) obj1;
        Legislator o2 = (Legislator) obj2;
        return o1.getLast_name().compareTo(o2.getLast_name());
    }
}
