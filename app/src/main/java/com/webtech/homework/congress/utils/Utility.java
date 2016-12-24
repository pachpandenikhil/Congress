package com.webtech.homework.congress.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.webtech.homework.congress.model.Bill;
import com.webtech.homework.congress.model.BillResults;
import com.webtech.homework.congress.model.Committee;
import com.webtech.homework.congress.model.CommitteeResults;
import com.webtech.homework.congress.model.Legislator;
import com.webtech.homework.congress.model.LegislatorResults;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ferrari on 11/27/2016.
 */

public class Utility {

    public static void writeToPreferences(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_PREF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.APP_PREF, context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }


    public static List<Legislator> getLegislatorsFromJSON(String json) {
        List<Legislator> legislatorsData = new ArrayList<>();
        if (json != null) {
            Gson gson = new Gson();
            LegislatorResults results = gson.fromJson(json, LegislatorResults.class);
            legislatorsData = Arrays.asList(results.getResults());
        }
        return legislatorsData;
    }


    public static List<Bill> getBillsFromJSON(String json) {
        List<Bill> billsData = new ArrayList<>();
        if (json != null) {
            Gson gson = new Gson();
            BillResults results = gson.fromJson(json, BillResults.class);
            billsData = Arrays.asList(results.getResults());
        }
        return billsData;
    }

    public static Date getDate(String strDate) {
        Date dt = null;
        if(strDate != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                dt = format.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dt;
    }


    public static String convertToDateFormat(String strDate, String format) {
       String destFormatDate = "";
        if( (strDate != null) && (format != null) ) {
            try {
                DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Date dt = dtFormat.parse(strDate);
                DateFormat destFormat = new SimpleDateFormat(format);
                destFormatDate = destFormat.format(dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return destFormatDate;
    }


    public static List<Committee> getCommitteesFromJSON(String json) {
        List<Committee> committeesData = new ArrayList<>();
        if (json != null) {
            Gson gson = new Gson();
            CommitteeResults results = gson.fromJson(json, CommitteeResults.class);
            committeesData = Arrays.asList(results.getResults());
        }
        return committeesData;
    }

    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static String getNullableDisplayValue(String value) {
        String displayValue = Constants.NULL;
        if(value != null) {
            if(!((value.trim()).isEmpty())) {
                displayValue = value.trim();
            }
        }
        return displayValue;
    }
}
