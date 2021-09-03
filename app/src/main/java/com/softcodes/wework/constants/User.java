package com.softcodes.wework.constants;


public class User {
    private int id;
    private String email;
    private String password;
    private String phone;
    private String user_role;
    private String candidate_id;
    private String employer_id;
    public User(int id, String email, String password, String phone, String user_role, String candidate_id, String employer_id) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.user_role = user_role;
        this.candidate_id = candidate_id;
        this.employer_id = employer_id;
    }

    public String getUser_role() {
        return user_role;
    }

    public String getCandidate_id() {
        return candidate_id;
    }

    public String getEmployer_id() {
        return employer_id;
    }

    public int getlId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getlPhone() {
        return phone;
    }
}