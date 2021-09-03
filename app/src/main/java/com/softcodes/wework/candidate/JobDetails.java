package com.softcodes.wework.candidate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softcodes.wework.R;
import com.softcodes.wework.candidate.adapters.JobAdapter;
import com.softcodes.wework.candidate.adapters.Job_Details_Adapter;
import com.softcodes.wework.candidate.models.Job_Details;
import com.softcodes.wework.candidate.models.Jobs;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.employeer.jobs.dashboard.EmployerDashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.softcodes.wework.candidate.CandidateDashboard.JOB_ID;
import static com.softcodes.wework.constants.Links.JOB_DETAIL_ID;
import static com.softcodes.wework.constants.Links.LOAD_JOBS;

public class JobDetails extends AppCompatActivity implements Job_Details_Adapter.OnItemClickListenerJobs{

    public final static String CLICKED_JOB_ID = "";

    List<Job_Details> jobsList;

    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    TextView no_jobs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_job_details);
        recyclerView = findViewById(R.id.displayed_job_detail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_jobs = findViewById(R.id.something_went_wrong);

        jobsList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading Job Details Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Intent intent = getIntent();
        final String id = intent.getStringExtra(JOB_ID);
        ShowDetails(id);
    }

    @SuppressLint("StaticFieldLeak")
    public void ShowDetails(final String email) {


        @SuppressWarnings("deprecation")
        class Details extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", email);

                return requestHandler.sendPostRequest(JOB_DETAIL_ID, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                try {

                    JSONArray array = new JSONArray(httpResponseMsg);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject s = array.getJSONObject(i);

                        //adding the product to product list

                        jobsList.add(new Job_Details(
                                s.getString("id"),
                                s.getString("title"),
                                s.getString("location"),
                                s.getString("category"),
                                s.getString("salary"),
                                s.getString("deadline"),
                                s.getString("skills"),
                                s.getString("company_name"),
                                s.getString("phone"),
                                s.getString("website"),
                                s.getString("qualification"),
                                s.getString("experience"),
                                s.getString("work_time"),
                                s.getString("description"),
                                s.getString("profile_photo")

                        ));
                        Job_Details_Adapter adapter = new Job_Details_Adapter(JobDetails.this, jobsList);
                        recyclerView.setAdapter(adapter);

                        progressDialog.dismiss();
                        adapter.setOnClickListenerJobs(JobDetails.this);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }

        Details details = new Details();

        details.execute(email);
    }


    @Override
    public void onItemclick(int position) {
        Job_Details clickedItem = jobsList.get(position);
        Intent intent = new Intent(JobDetails.this, SendApplication.class);
        intent.putExtra(CLICKED_JOB_ID, clickedItem.getJbid());
        startActivity(intent);
        finish();
    }

    @Override
    public void onbackJobs(int position) {
        startActivity(new Intent(getApplicationContext(), CandidateDashboard.class));
        finish();
    }
}