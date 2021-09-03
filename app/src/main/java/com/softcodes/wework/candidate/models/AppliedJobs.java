package com.softcodes.wework.candidate.models;

public class AppliedJobs {
    private String AplicID;
    private String AplicName;
    private String AplicLocation;
    private String AplicDeadline;
    private String AplicSalary;
    private String AplicCompanyName;
    private String AplicStatus;



    public String getAplicID() {
        return AplicID;
    }
    public String getAplicStatus() {
        return AplicStatus;
    }
    public String getAplicName() {
        return AplicName;
    }

    public String getAplicLocation() {
        return AplicLocation;
    }

    public String getAplicDeadline() {
        return AplicDeadline;
    }

    public String getAplicSalary() {
        return AplicSalary;
    }

    public String getAplicCompanyName() {
        return AplicCompanyName;
    }

    public AppliedJobs(String aplicID,String AplicStatus, String aplicName, String aplicLocation, String aplicDeadline, String aplicSalary, String aplicCompanyName) {
        AplicID = aplicID;
        AplicName = aplicName;
        AplicLocation = aplicLocation;
        AplicDeadline = aplicDeadline;
        AplicSalary = aplicSalary;
        AplicCompanyName = aplicCompanyName;
        this.AplicStatus = AplicStatus;
    }
}
