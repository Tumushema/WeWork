package com.softcodes.wework.employeer.jobs.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.softcodes.wework.R;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;

import java.util.Calendar;
import java.util.HashMap;

import static com.softcodes.wework.candidate.CandidateDashboard.JOB_ID;
import static com.softcodes.wework.constants.Links.POST_JOBS;
import static com.softcodes.wework.constants.Links.UPDATE_JOBS;

public class EditMyJob extends AppCompatActivity {
    EditText jname,desination,deadline,location,salary,phone,compName, website,skills,description,experience,qualification,
            work_time;
    Spinner category;
    ProgressDialog progressDialog;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_job);
        jname = findViewById(R.id.editJobTitle);
        desination = findViewById(R.id.editdesignation);
        deadline = findViewById(R.id.editDeadline);
        location = findViewById(R.id.editLocation);
        salary = findViewById(R.id.editSalary);
        compName = findViewById(R.id.editcompanyName);
        phone = findViewById(R.id.editcompanyphone);
        website = findViewById(R.id.editcompanyWebsite);
        description = findViewById(R.id.editJobDescription);
        experience = findViewById(R.id.editcompexperince);
        qualification = findViewById(R.id.editcompqualification);
        work_time = findViewById(R.id.editcompWorktime);
        skills = findViewById(R.id.editcompanyskills);
        category = findViewById(R.id.editpjob_category);
        progressDialog =new ProgressDialog(this);



        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(EditMyJob.this,
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

    public void update_my_job(View view) {

        Intent intent = getIntent();
        final String id = intent.getStringExtra(JOB_ID);
        job_to_be_updated(id);




    }
    public void job_to_be_updated( final String email){
        User user = SharedPrefManager.getInstance(this).getUser();
        final String employer_id = String.valueOf(user.getUser_role());
        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class UpdateJob_job extends AsyncTask<Void, Void, String> {


            final String name = jname.getText().toString();
            final String designation = desination.getText().toString();
            final String jodeadline = deadline.getText().toString();
            final String loca = location.getText().toString();
            final String sala = salary.getText().toString();
            final String company_name = compName.getText().toString();
            final String pohen = phone.getText().toString();
            final String web = website.getText().toString();
            final String decs = description.getText().toString();
            final String exper = experience.getText().toString();
            final String qual = qualification.getText().toString();
            final String wTime = work_time.getText().toString();
            final String ksills = skills.getText().toString();
            final String jobCategory = category.getSelectedItem().toString();

            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Update Job Please Wait....");
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
                params.put("phone", pohen);
                params.put("website", web);
                params.put("description", decs);
                params.put("experience", exper);
                params.put("qualification", qual);
                params.put("work_time", wTime);
                params.put("salary", sala);
                params.put("employer_id", employer_id);

                return requestHandler.sendPostRequest(UPDATE_JOBS, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(EditMyJob.this, s, Toast.LENGTH_LONG).show();

            }


        }

        UpdateJob_job post_job = new UpdateJob_job();
        post_job.execute();

    }
}