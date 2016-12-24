package com.webtech.homework.congress.utils;

/**
 * Created by ferrari on 11/27/2016.
 */

public class Constants {

    public static final String APP_PREF = "CongressAppPreferences";

    public static final String STATE_LEGISLATORS = "StateLegislators";
    public static final String HOUSE_LEGISLATORS = "HouseLegislators";
    public static final String SENATE_LEGISLATORS = "SenateLegislators";
    public static final String SELECTED_LEGISLATOR = "SelectedLegislator";

    public static final String ACTIVE_BILLS = "ActiveBills";
    public static final String NEW_BILLS = "NewBills";
    public static final String SELECTED_BILL = "SelectedBill";

    public static final String HOUSE_COMMITTEES = "HouseCommittees";
    public static final String SENATE_COMMITTEES = "SenateCommittees";
    public static final String JOINT_COMMITTEES = "JointCommittees";
    public static final String SELECTED_COMMITTEE = "SelectedCommittee";

    public static final String FAVORITES = "Favorites";

    public static final String QUERY_STATE_LEGISLATORS = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=legislators";
    public static final String QUERY_HOUSE_LEGISLATORS = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=houseLegislators";
    public static final String QUERY_SENATE_LEGISLATORS = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=senateLegislators";

    public static final String QUERY_ACTIVE_BILLS = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=activeBills";
    public static final String QUERY_NEW_BILLS = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=newBills";


    public static final String QUERY_HOUSE_COMMITTEES = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=houseCommittees";
    public static final String QUERY_SENATE_COMMITTEES = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=senateCommittees";
    public static final String QUERY_JOINT_COMMITTEES = "http://custom-env.tq8tnpyzsz.us-west-2.elasticbeanstalk.com/u1OP1b6aJbTy.php?operation=getData&entity=jointCommittees";

    public static final String NULL = "None";
    public static final String LEGISLATOR_DISTRICT_NULL = "District 0";

}
