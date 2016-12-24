package com.webtech.homework.congress.model;

/**
 * Created by ferrari on 11/28/2016.
 */

public class BillLastVersion {
    private BillLastVersionUrls urls;
    private String version_name;

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public BillLastVersionUrls getUrls() {
        return urls;
    }

    public void setUrls(BillLastVersionUrls urls) {
        this.urls = urls;
    }
}
