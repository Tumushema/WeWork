package com.softcodes.wework.candidate;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.softcodes.wework.BuildConfig;
import com.softcodes.wework.R;
import com.softcodes.wework.activities.Login;
import com.softcodes.wework.candidate.adapters.JobAdapter;
import com.softcodes.wework.candidate.models.Jobs;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;
import com.softcodes.wework.employeer.jobs.dashboard.PostJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.softcodes.wework.constants.Links.LOAD_JOBS;
import static com.softcodes.wework.constants.Links.POST_JOBS;
import static com.softcodes.wework.constants.Links.SAVE_TO_FAVORITES;

public class CandidateDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,JobAdapter.OnItemClickListenerJobs {

    public final static String JOB_ID = "";

    List<Jobs> jobsList;

    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    TextView no_jobs,welcome,candidateNo,candidEmail;
    EditText search_jobs;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_dashboard);

        recyclerView = findViewById(R.id.displayed_job_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_jobs = findViewById(R.id.no_jobs_yet);
        welcome = findViewById(R.id.welcome_toolbar_title);
        candidEmail = findViewById(R.id.cand_txt_nav_header);
        candidateNo = findViewById(R.id.cand_txt_nav_email);
        search_jobs = findViewById(R.id.search_jobs);
        User user = SharedPrefManager.getInstance(this).getUser();
        final String welc = String.valueOf(user.getEmail());
        final String idNo = String.valueOf(user.getlPhone());
        welcome.setText("Welcome"+" "+welc);

        //candidateNo.setText("CANDIDATE NO: "+" "+idNo);
        //candidEmail.setText(welc);
        jobsList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading Jobs Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        load_jobs();

        //handle search from here................#softcodes


        //
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent v = new Intent(CandidateDashboard.this, CandidateDashboard.class);
            startActivity(v);
        } else if (id == R.id.nav_fullprofile) {
            Intent v = new Intent(CandidateDashboard.this, Myprofile.class);
            startActivity(v);
        } else if (id == R.id.nav_resume) {
            Intent applicants = new Intent(CandidateDashboard.this, SendApplication.class);
            startActivity(applicants);
        }
        else if(id ==R.id.nav_all_jobs){
            Intent applicants = new Intent(CandidateDashboard.this, AllJobs.class);
            startActivity(applicants);
        }
        else if(id ==R.id.nav_job_applied){

            Intent applicants = new Intent(CandidateDashboard.this, MyAppliedJobs.class);
            startActivity(applicants);
        }
        else if(id==R.id.nav_saved_jobs){

            Intent saved_jobs = new Intent(CandidateDashboard.this, MySavedJobs.class);
            startActivity(saved_jobs);
        }
        else if(id==R.id.nav_exit_user){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            Intent saved_jobs = new Intent(CandidateDashboard.this, Login.class);
            startActivity(saved_jobs);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void load_jobs() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOAD_JOBS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray array = new JSONArray(response);
                            if(array.length()>0){
                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject s = array.getJSONObject(i);

                                    jobsList.add(new Jobs(
                                            s.getString("id"),
                                            s.getString("title"),
                                            s.getString("profile_photo"),
                                            s.getString("location"),
                                            s.getString("salary"),
                                            s.getString("deadline"),
                                            s.getString("category")


                                    ));
                                    JobAdapter adapter = new JobAdapter(CandidateDashboard.this, jobsList);
                                    recyclerView.setAdapter(adapter);
                                    progressDialog.dismiss();
                                    adapter.setOnClickListenerJobs(CandidateDashboard.this);
                                }


                            }
                            else {
                                no_jobs.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                recyclerView.setVisibility(View.INVISIBLE);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public void onItemclick(int position) {
        Intent intent = new Intent(CandidateDashboard.this, SendApplication.class);
        Jobs clickedItem = jobsList.get(position);
        intent.putExtra(JOB_ID, clickedItem.getJob_id());
        startActivity(intent);
    }

    @Override
    public void onViewdetails(int position) {

        Intent intent = new Intent(CandidateDashboard.this, JobDetails.class);
        Jobs clickedItem = jobsList.get(position);
        intent.putExtra(JOB_ID, clickedItem.getJob_id());
        startActivity(intent);
        //Toast.makeText(getApplicationContext(), clickedItem.getJob_id(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddToFavorites(int position) {

        Jobs clickedItem = jobsList.get(position);
        final String jb_id = clickedItem.getJob_id();

        User user = SharedPrefManager.getInstance(this).getUser();
        final String candidate_id = String.valueOf(user.getlPhone());


        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class Add_job_Favorites extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Adding Job to Favorites....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("job_id", jb_id);
                params.put("candidate_id", candidate_id);

                return requestHandler.sendPostRequest(SAVE_TO_FAVORITES, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(CandidateDashboard.this, s, Toast.LENGTH_LONG).show();


            }


        }

        Add_job_Favorites add_job_favorites = new Add_job_Favorites();
        add_job_favorites.execute();


    }

    @Override
    public void onShareJob(int position) {
        Jobs clickedItem = jobsList.get(position);
         String jb_name = clickedItem.getJob_name();
         String jb_ref = clickedItem.getJob_id();
        try {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String shareMessage = jb_name;
            shareMessage = shareMessage + "Job Reference" +" "+jb_ref + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}