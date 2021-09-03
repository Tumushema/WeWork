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
import com.softcodes.wework.constants.RequestHandler;

import java.util.HashMap;

import static com.softcodes.wework.constants.Links.ADD_CANDIDATE;

public class SignUpCandidate extends AppCompatActivity {
    EditText name,email,phone,password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        name=findViewById(R.id.seeker_fullname);
        email = findViewById(R.id.seeker_email);
        phone = findViewById(R.id.seeker_phone);
        password = findViewById(R.id.seeker_password);

        progressDialog =new ProgressDialog(this);
    }

    public void signin(View view) {
        Intent dsp = new Intent(SignUpCandidate.this,Login.class);
        startActivity(dsp);
        finish();
    }

    public void candidate_signup(View view) {

        final String full_name = name.getText().toString().trim();
        final String entered_email = email.getText().toString();
        final String entered_phone = phone.getText().toString();
        final String  entered_password = password.getText().toString();

        if(TextUtils.isEmpty(full_name)){
            name.setError("Name is Required");
            name.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(entered_email)){
            email.requestFocus();
            email.setError("Enter an Email");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(entered_email).matches()) {
            email.setError("Please Enter a Valid Email!");
            email.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(entered_phone)){
            phone.setError("Entered a mobile Number");
            phone.requestFocus();
        }
        if(entered_phone.length()<10||entered_phone.length()>13){
            phone.requestFocus();
            phone.setError("Enter a Valid Phone Number!");
            return;
        }
        if(TextUtils.isEmpty(entered_password)){
            password.setError("Enter a memorable Password");
            password.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(entered_password)){
            password.requestFocus();
            password.setError("Password should contain letters and be more than 6 characters");
            return;
        }

        //noinspection deprecation
        @SuppressLint("StaticFieldLeak")
        class CandidateSignUp extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Saving Please Wait....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", full_name);
                params.put("email", entered_email);
                params.put("phone", entered_phone);
                params.put("password", entered_password);


                return requestHandler.sendPostRequest(ADD_CANDIDATE, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(SignUpCandidate.this, s, Toast.LENGTH_LONG).show();

                if(s.equalsIgnoreCase("User registered successfully!")) {
                    Intent intent = new Intent(SignUpCandidate.this, Login.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    Toast.makeText(SignUpCandidate.this, "Something went wrong!, Try again", Toast.LENGTH_LONG).show();
                }
            }


        }

        CandidateSignUp candidateSignUp = new CandidateSignUp();
        candidateSignUp.execute();

        name.setText("");
        email.setText("");
        phone.setText("");
        password.setText("");
    }
}