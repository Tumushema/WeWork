package com.softcodes.wework.employeer.jobs.models;

public class Applicants {
    private String applicantId;
    private String applicantName;
    private String applicantJobName;
    private String applicantcv;

    public Applicants(String applicantId, String applicantName, String applicantJobName, String applicantcv) {
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.applicantJobName = applicantJobName;
        this.applicantcv = applicantcv;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantJobName() {
        return applicantJobName;
    }

    public String getApplicantcv() {
        return applicantcv;
    }
}
