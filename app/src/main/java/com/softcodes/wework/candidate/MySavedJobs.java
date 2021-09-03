package com.softcodes.wework.candidate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.softcodes.wework.R;
import com.softcodes.wework.candidate.adapters.AppliedJobs_Adapter;
import com.softcodes.wework.candidate.adapters.MySavedJobs_Adapter;
import com.softcodes.wework.candidate.models.AppliedJobs;
import com.softcodes.wework.candidate.models.Jobs;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.softcodes.wework.constants.Links.MY_APPLIED_JOBS;
import static com.softcodes.wework.constants.Links.MY_SAVED_JOBS;

public class MySavedJobs extends AppCompatActivity {
    List<Jobs> jobsList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView no_jobs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_applied_jobs);

        recyclerView = findViewById(R.id.displayed_applied);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_jobs = findViewById(R.id.not_applied);

        jobsList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Retrieving my Jobs....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        User user = SharedPrefManager.getInstance(this).getUser();
        final String candidate_id = String.valueOf(user.getlPhone());
        ShowDetails(candidate_id);
    }


    @SuppressLint("StaticFieldLeak")
    public void ShowDetails(final String email) {

        @SuppressWarnings("deprecation")
        class Details extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("candidate_id", email);

                return requestHandler.sendPostRequest(MY_SAVED_JOBS, params);
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
                    if(array.length()>0){
                        //traversing through all the object
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject s = array.getJSONObject(i);

                            //adding the product to product list

                            jobsList.add(new Jobs(
                                    s.getString("job_id"),
                                    s.getString("title"),
                                    s.getString("profile_photo"),
                                    s.getString("location"),
                                    s.getString("salary"),
                                    s.getString("deadline"),
                                    s.getString("category")


                            ));
                            MySavedJobs_Adapter adapter = new MySavedJobs_Adapter(MySavedJobs.this, jobsList);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();

                        }

                    }
                    else {
                        no_jobs.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        recyclerView.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        Details details = new Details();

        details.execute(email);
    }
}