package com.webtech.homework.congress.model;

import com.webtech.homework.congress.utils.Utility;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by ferrari on 12/1/2016.
 */

public class BillsComparator implements Comparator {
    @Override
    public int compare(Object obj1, Object obj2) {
        Bill b1 = (Bill) obj1;
        Bill b2 = (Bill) obj2;
        Date date1 = Utility.getDate(b1.getIntroduced_on());
        Date date2 = Utility.getDate(b2.getIntroduced_on());

        return date2.compareTo(date1);
    }
}
