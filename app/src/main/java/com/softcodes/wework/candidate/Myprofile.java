package com.softcodes.wework.candidate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.softcodes.wework.R;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;

import java.util.HashMap;

import static com.softcodes.wework.constants.Links.SAVE_TO_FAVORITES;
import static com.softcodes.wework.constants.Links.UPDATE_USER_PROFILE;

public class Myprofile extends AppCompatActivity {
    EditText email,phone,password;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);

        email = findViewById(R.id.changeemail);
        phone = findViewById(R.id.changephone);
        password = findViewById(R.id.changepassword);

        progressDialog = new ProgressDialog(this);


        User user = SharedPrefManager.getInstance(this).getUser();

        final  String rphone = String.valueOf(user.getCandidate_id());
        final String remail = String.valueOf(user.getEmail());
        final String rpass = String.valueOf(user.getEmployer_id());

        phone.setText(rphone);
        email.setText(remail);
        password.setText(rpass);

    }

    public void update_my_info(View view) {

        final String entered_phone  = phone.getText().toString();
        final String entered_email = email.getText().toString();
        final String entered_password = password.getText().toString();
        User user = SharedPrefManager.getInstance(this).getUser();
        final String candidate_id = String.valueOf(user.getlPhone());


        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class Update_Info extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Updating....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(Void... voids) {

                RequestHandler requestHandler = new RequestHandler();
                HashMap<String, String> params = new HashMap<>();

                params.put("candidate_id", candidate_id);
                params.put("email", entered_email);
                params.put("phone", entered_phone);
                params.put("password", entered_password);
                return requestHandler.sendPostRequest(UPDATE_USER_PROFILE, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(Myprofile.this, s, Toast.LENGTH_LONG).show();


            }


        }

        Update_Info update_info = new Update_Info();
        update_info.execute();



    }
}