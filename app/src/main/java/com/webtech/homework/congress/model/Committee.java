package com.webtech.homework.congress.model;

/**
 * Created by ferrari on 11/28/2016.
 */

public class Committee {
    private String committee_id;
    private String name;
    private String chamber;
    private String parent_committee_id;
    private String phone;
    private String office;

    public String getParent_committee_id() {
        return parent_committee_id;
    }

    public void setParent_committee_id(String parent_committee_id) {
        this.parent_committee_id = parent_committee_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getCommittee_id() {
        return committee_id;
    }

    public void setCommittee_id(String committee_id) {
        this.committee_id = committee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }
}
