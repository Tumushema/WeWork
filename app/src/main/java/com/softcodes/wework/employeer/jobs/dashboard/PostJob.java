package com.softcodes.wework.employeer.jobs.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.softcodes.wework.R;
import com.softcodes.wework.activities.Login;
import com.softcodes.wework.activities.SignUpCandidate;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;

import java.util.Calendar;
import java.util.HashMap;

import static com.softcodes.wework.constants.Links.ADD_CANDIDATE;
import static com.softcodes.wework.constants.Links.POST_JOBS;

public class PostJob extends AppCompatActivity {

    EditText jname,desination,deadline,location,salary,phone,compName, website,skills,description,experience,qualification,
    work_time;
    Spinner category;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_job);
        jname = findViewById(R.id.etJobTitle);
        desination = findViewById(R.id.etdesignation);
        deadline = findViewById(R.id.etDeadline);
        location = findViewById(R.id.etLocation);
        salary = findViewById(R.id.etSalary);
        compName = findViewById(R.id.companyName);
        phone = findViewById(R.id.companyphone);
        website = findViewById(R.id.companyWebsite);
        description = findViewById(R.id.etJobDescription);
        experience = findViewById(R.id.compexperince);
        qualification = findViewById(R.id.compqualification);
        work_time = findViewById(R.id.compWorktime);
        skills = findViewById(R.id.etEnterSkills);
        category = findViewById(R.id.empjob_category);
        progressDialog =new ProgressDialog(this);


        // pick current date....

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(PostJob.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                deadline.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    public void post_jobs(View view) {

        final String name = jname.getText().toString();
        final String designation = desination.getText().toString();
        final String jodeadline = deadline.getText().toString();
        final String  loca = location.getText().toString();
        final String sala = salary.getText().toString();
        final String company_name = compName.getText().toString();
        final String pohen = phone.getText().toString();
        final String web = website.getText().toString();
        final String decs = description.getText().toString();
        final String exper = experience.getText().toString();
        final String  qual = qualification.getText().toString();
        final String wTime = work_time.getText().toString();
        final  String ksills = skills.getText().toString();
        final String jobCategory = category.getSelectedItem().toString();

        User user = SharedPrefManager.getInstance(this).getUser();
        final String employer_id = String.valueOf(user.getUser_role());

        if(TextUtils.isEmpty(name)){
            jname.setError("Required field!");
            jname.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(designation)){
            desination.setError("Required field!");
            desination.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(jodeadline)){
            deadline.setError("Required field!");
            deadline.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(loca)){
            location.setError("Required field!");
            location.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(sala)){
            salary.setError("Required field!");
            salary.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(company_name)){
            compName.setError("Required field!");
            compName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pohen)){
            phone.setError("Required field!");
            phone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(web)){
            website.setError("Required field!");
            website.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(decs)){
            description.setError("Required field!");
            description.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(exper)){
            experience.setError("Required field!");
            experience.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(qual)){
            qualification.setError("Required field!");
            qualification.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(wTime)){
            work_time.setError("Required field!");
            work_time.requestFocus();
            return;
        }

        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class Post_job extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Posting Job Please Wait....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("title", name);
                params.put("designation", designation);
                params.put("deadline", jodeadline);
                params.put("location", loca);
                params.put("category", jobCategory);
                params.put("skills", ksills);
                params.put("company_name", company_name);
                params.put("phone",pohen);
                params.put("website", web);
                params.put("description", decs);
                params.put("experience", exper);
                params.put("qualification", qual);
                params.put("work_time", wTime);
                params.put("salary", sala);
                params.put("employer_id", employer_id);

                return requestHandler.sendPostRequest(POST_JOBS, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(PostJob.this, s, Toast.LENGTH_LONG).show();

            }


        }

        Post_job post_job = new Post_job();
        post_job.execute();

    }
}