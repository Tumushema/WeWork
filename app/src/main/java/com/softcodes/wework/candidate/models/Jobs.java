package com.softcodes.wework.candidate.models;

public class Jobs {
    private String job_id;
    private String job_name;
    private String job_image;
    private String job_location;
    private String job_salary;
    private String job_deadline;
    private String  job_category;

    public Jobs(String job_id, String job_name, String job_image, String job_location, String job_salary, String job_deadline, String job_category) {
        this.job_id = job_id;
        this.job_name = job_name;
        this.job_image = job_image;
        this.job_location = job_location;
        this.job_salary = job_salary;
        this.job_deadline = job_deadline;
        this.job_category = job_category;
    }

    public String getJob_id() {
        return job_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public String getJob_image() {
        return job_image;
    }

    public String getJob_location() {
        return job_location;
    }

    public String getJob_salary() {
        return job_salary;
    }

    public String getJob_deadline() {
        return job_deadline;
    }

    public String getJob_category() {
        return job_category;
    }
}
