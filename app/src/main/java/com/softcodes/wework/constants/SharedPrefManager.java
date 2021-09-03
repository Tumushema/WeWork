package com.softcodes.wework.constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.softcodes.wework.candidate.models.Job_Details;

public class SharedPrefManager {
    public static final String SHARED_PREF_NAME = "getwork";
    public static final String KEY_EMAIL = "keyemail";
    public static final String KEY_PASSWORD = "keypassword";
    public static final String KEY_ROLE = "keyuser_role";
    public static final String KEY_ID = "keyid";
    public static final String KEY_EMPLOYER_ID = "keyemployer_id";
    public static final String KEY_CANDIDATE_ID = "keycandidate";
    public static final String KEY_PHONE = "keyphone";


    public static final String SHARED_PREF_NAME_2 = "jobs";
    public static final String KEY_TITLE = "keytitle";
    public static final String KEY_DESIGNATION = "keydesignation";
    public static final String KEY_DEADLINE = "keydeadline";
    public static final String KEY_JOB_ID = "keyjobid";
    public static final String KEY_LOCATION = "keylocation";
    public static final String KEY_CATEGORY = "keycategory";
    public static final String KEY_SKILLS = "keyskills";
    public static final String KEY_COMPANY_NAME = "keycompany_name";
    public static final String KEY_COMPANY_PHONE = "keycompphone";
    public static final String KEY_WEBSITE = "keywebsite";
    public static final String KEY_DESCRIPTION = "keydesciption";
    public static final String KEY_EXPERIENCE = "keyexperience";
    public static final String KEY_QUALIFICATION = "keyqualification";
    public static final String KEY_WORK_TIME = "keyworktime";
    public static final String KEY_SALARY = "keysalary";
    public static final String KEY_EMPLOYERID = "keyemployerid";




    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public void keepJobs(Job_Details jobs){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_JOB_ID,jobs.getJbid());
        editor.putString(KEY_TITLE,jobs.getJbname());
        editor.putString(KEY_DEADLINE,jobs.getJbdeadline());
        editor.putString(KEY_LOCATION,jobs.getJblocation());
        editor.putString(KEY_CATEGORY,jobs.getJbcategory());
        editor.putString(KEY_SKILLS,jobs.getJbskills());
        editor.putString(KEY_COMPANY_NAME,jobs.getJbcompanyName());
        editor.putString(KEY_COMPANY_PHONE,jobs.getJbphone());
        editor.putString(KEY_WEBSITE,jobs.getJbwebsite());
        editor.putString(KEY_DESCRIPTION,jobs.getJbdescription());
        editor.putString(KEY_EXPERIENCE,jobs.getJbexperience());
        editor.putString(KEY_QUALIFICATION,jobs.getJbqualification());
        editor.putString(KEY_WORK_TIME,jobs.getJbworktime());
        editor.putString(KEY_SALARY,jobs.getJbsalary());
        editor.apply();
    }

    public Job_Details getjobs(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_2, Context.MODE_PRIVATE);
        return new Job_Details(

                sharedPreferences.getString(KEY_JOB_ID, null),
                sharedPreferences.getString(KEY_TITLE, null),
                sharedPreferences.getString(KEY_LOCATION, null),
                sharedPreferences.getString(KEY_CATEGORY, null),
                sharedPreferences.getString(KEY_SALARY, null),
                sharedPreferences.getString(KEY_DEADLINE, null),
                sharedPreferences.getString(KEY_SKILLS, null),
                sharedPreferences.getString(KEY_COMPANY_NAME, null),
                sharedPreferences.getString(KEY_COMPANY_PHONE, null),
                sharedPreferences.getString(KEY_WEBSITE, null),
                sharedPreferences.getString(KEY_QUALIFICATION, null),
                sharedPreferences.getString(KEY_EXPERIENCE, null),
                sharedPreferences.getString(KEY_WORK_TIME, null),
                sharedPreferences.getString(KEY_DESCRIPTION, null),
                sharedPreferences.getString(KEY_EMPLOYER_ID, null)

        );
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getlId());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PHONE, user.getlPhone());
        editor.putString(KEY_ROLE, user.getUser_role());
        editor.putString(KEY_CANDIDATE_ID, user.getCandidate_id());
        editor.putString(KEY_EMPLOYER_ID, user.getEmployer_id());

        editor.apply();

    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ROLE, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_ROLE, null),
                sharedPreferences.getString(KEY_CANDIDATE_ID, null),
                sharedPreferences.getString(KEY_EMPLOYER_ID, null)


        );
    }



    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}