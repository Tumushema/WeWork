package com.softcodes.wework.candidate.models;

public class Job_Details {
    private String jbid;
    private String jbname;
    private String jblocation;
    private String jbcategory;
    private String jbsalary;
    private String jbdeadline;
    private String jbskills;
    private String jbcompanyName;
    private String jbphone;
    private String jbwebsite;
    private String jbqualification;
    private String jbexperience;
    private String jbworktime;
    private String jbdescription;
    private String jbcompanyImage;

    public Job_Details(String jbid, String jbname, String jblocation, String jbcategory, String jbsalary, String jbdeadline, String jbskills, String jbcompanyName, String jbphone, String jbwebsite, String jbqualification, String jbexperience, String jbworktime, String jbdescription,String jbcompanyImage) {
        this.jbid = jbid;
        this.jbname = jbname;
        this.jblocation = jblocation;
        this.jbcategory = jbcategory;
        this.jbsalary = jbsalary;
        this.jbdeadline = jbdeadline;
        this.jbskills = jbskills;
        this.jbcompanyName = jbcompanyName;
        this.jbphone = jbphone;
        this.jbwebsite = jbwebsite;
        this.jbqualification = jbqualification;
        this.jbexperience = jbexperience;
        this.jbworktime = jbworktime;
        this.jbdescription = jbdescription;
        this.jbcompanyImage =jbcompanyImage;
    }

    public String getJbid() {
        return jbid;
    }

    public String getJbname() {
        return jbname;
    }

    public String getJblocation() {
        return jblocation;
    }

    public String getJbcategory() {
        return jbcategory;
    }

    public String getJbsalary() {
        return jbsalary;
    }

    public String getJbdeadline() {
        return jbdeadline;
    }

    public String getJbskills() {
        return jbskills;
    }

    public String getJbcompanyName() {
        return jbcompanyName;
    }

    public String getJbphone() {
        return jbphone;
    }

    public String getJbwebsite() {
        return jbwebsite;
    }

    public String getJbqualification() {
        return jbqualification;
    }

    public String getJbexperience() {
        return jbexperience;
    }

    public String getJbworktime() {
        return jbworktime;
    }

    public String getJbdescription() {
        return jbdescription;
    }

    public String getJbcompanyImage() {
        return jbcompanyImage;
    }
}
