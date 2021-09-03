package com.softcodes.wework.employeer.jobs.dashboard;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.softcodes.wework.R;
import com.softcodes.wework.activities.Login;
import com.softcodes.wework.candidate.adapters.MySavedJobs_Adapter;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;
import com.softcodes.wework.employeer.jobs.adapters.Applicants_Adapter;
import com.softcodes.wework.employeer.jobs.models.Applicants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.softcodes.wework.constants.Links.MY_APPLICANTS;
import static com.softcodes.wework.constants.Links.MY_SAVED_JOBS;
import static com.softcodes.wework.constants.Links.URL_ACCEPT_APPLICANT;

public class EmployerDashboard extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener ,Applicants_Adapter.OnItemClickListenerJobs{
    AlertDialog.Builder builder;
    List<Applicants> jobsList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    TextView no_jobs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_dashboard);
        builder = new AlertDialog.Builder(this);

        DrawerLayout drawer = findViewById(R.id.empdrawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.empnav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = findViewById(R.id.applicants_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_jobs = findViewById(R.id.no_applicants_yet);

        jobsList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        User user = SharedPrefManager.getInstance(this).getUser();
        final String employer_ID = String.valueOf(user.getUser_role());
        ShowDetails(employer_ID);
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.emnav_home) {
            //Return home
        } else if (id == R.id.empnav_post_job) {
            Intent v = new Intent(EmployerDashboard.this, PostJob.class);
            startActivity(v);
        }
        else if(id ==R.id.emnav_followers){
            Intent applicants = new Intent(EmployerDashboard.this, Applicants_Employer.class);
            startActivity(applicants);
        }

        //lecturer_settings
        else if (id == R.id.emp_mynav_jobs) {
            Intent general_settings = new Intent(EmployerDashboard.this, EmployerMyJObs.class);
            startActivity(general_settings);
        } else if (id == R.id.compnav_editprofile) {

        } else if (id == R.id.compnav_dashboard) {

        }

        else if (id == R.id.compnav_exit) {

            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

            //Setting message manually and performing action on button click
            builder.setMessage("Do you want to Logout ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Logging you out!",
                                    Toast.LENGTH_SHORT).show();
                            Intent dsp = new Intent(EmployerDashboard.this, Login.class);
                            startActivity(dsp);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "Submit Jobs",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            //Creating dialog box

            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("Sign Out!");
            alert.show();
        } else if (id == R.id.compnav_candidate_search) {

            Intent help = new Intent(EmployerDashboard.this, SearchCandidates.class);
            startActivity(help);
        }


        DrawerLayout drawer = findViewById(R.id.empdrawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @SuppressLint("StaticFieldLeak")
    public void ShowDetails(final String email) {

        @SuppressWarnings("deprecation")
        class Details extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("employer_id", email);

                return requestHandler.sendPostRequest(MY_APPLICANTS, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Retrieving applicants....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);

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

                            jobsList.add(new Applicants(
                                    s.getString("id"),
                                    s.getString("title"),
                                    s.getString("status"),
                                    s.getString("url")




                            ));
                            Applicants_Adapter adapter = new Applicants_Adapter(EmployerDashboard.this, jobsList);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();
                            adapter.setOnClickListenerJobs(EmployerDashboard.this);

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


    @Override
    public void onItemclick(int position) {

        Applicants clickedItem = jobsList.get(position);
        final String id =clickedItem.getApplicantId();

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Accepting Applicant, Please Wait....");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        //noinspection deprecation
        @SuppressLint("StaticFieldLeak")
        class User extends AsyncTask<Void, Void, String> {

            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);

                return requestHandler.sendPostRequest(URL_ACCEPT_APPLICANT, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(EmployerDashboard.this, s, Toast.LENGTH_LONG).show();
                Intent register = new Intent(getApplicationContext(), EmployerDashboard.class);
                startActivity(register);
            }


        }

        User prod_exec = new User();
        prod_exec.execute();




    }
}