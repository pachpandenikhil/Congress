package com.webtech.homework.congress.model;

/**
 * Created by ferrari on 11/28/2016.
 */

public class Bill {
    private String bill_id;
    private String bill_type;
    private String chamber;
    private String short_title;
    private String official_title;

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    private BillSponsor sponsor;
    private BillHistory history;
    private BillUrls urls;
    private BillLastVersion last_version;


    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public BillSponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(BillSponsor sponsor) {
        this.sponsor = sponsor;
    }

    public BillHistory getHistory() {
        return history;
    }

    public void setHistory(BillHistory history) {
        this.history = history;
    }

    public BillUrls getUrls() {
        return urls;
    }

    public void setUrls(BillUrls urls) {
        this.urls = urls;
    }

    public BillLastVersion getLast_version() {
        return last_version;
    }

    public void setLast_version(BillLastVersion last_version) {
        this.last_version = last_version;
    }

    private String introduced_on;

    public String getIntroduced_on() {
        return introduced_on;
    }

    public void setIntroduced_on(String introduced_on) {
        this.introduced_on = introduced_on;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getOfficial_title() {
        return official_title;
    }

    public void setOfficial_title(String official_title) {
        this.official_title = official_title;
    }
}
