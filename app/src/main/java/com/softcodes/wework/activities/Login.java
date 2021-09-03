package com.softcodes.wework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.softcodes.wework.R;
import com.softcodes.wework.candidate.CandidateDashboard;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;
import com.softcodes.wework.employeer.jobs.dashboard.EmployerDashboard;
import com.softcodes.wework.employeer.jobs.dashboard.PostJob;
import com.softcodes.wework.guest.GuestDashboard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.softcodes.wework.constants.Links.URL_LOGIN;

public class Login extends AppCompatActivity {
    EditText phone_email,password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        phone_email = findViewById(R.id.login_phone_or_email);
        password = findViewById(R.id.login_password);
        progressDialog = new ProgressDialog(this);
    }

    public void for_gotpassword(View view) {
        Intent dsp = new Intent(Login.this,ForgotPassword.class);
        startActivity(dsp);
        finish();
    }

    public void signup(View view) {

        Intent dsp = new Intent(Login.this,SignupAs.class);
        startActivity(dsp);

    }

    public void login(View view) {
        final String phone = phone_email.getText().toString();
        final  String pass = password.getText().toString();
        if(TextUtils.isEmpty(phone)){
            phone_email.setError("Enter Email");
            phone_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            password.requestFocus();
            password.setError("Password is Required");
        }
        UserLoginFunction(phone,pass);

    }



    @SuppressLint("StaticFieldLeak")
    public void UserLoginFunction(final String email, final String password) {


/**
 *
 */
        @SuppressWarnings("deprecation")
        class UserLoginClass extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return requestHandler.sendPostRequest(URL_LOGIN, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


                progressDialog.show();
                progressDialog.setMessage("Checking Please Wait....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                // progressDialog = ProgressDialog.show(Login.this, "Checking...", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(httpResponseMsg);

                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


                        JSONObject userJson = obj.getJSONObject("user");

                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getString("email"),
                                userJson.getString("password"),
                                userJson.getString("candidate_id"),
                                userJson.getString("phone"),
                                userJson.getString("user_role"),
                                userJson.getString("employer_id")



                        );


                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                       // Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_LONG).show();
                        if (user.getPassword().equals("Candidate")) {
                            startActivity(new Intent(getApplicationContext(), CandidateDashboard.class));
                            finish();

                        } else if (user.getPassword().equals("Employer")) {
                            startActivity(new Intent(getApplicationContext(), EmployerDashboard.class));
                            finish();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email, password);
    }

}