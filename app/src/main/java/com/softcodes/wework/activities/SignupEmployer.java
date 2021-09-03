package com.softcodes.wework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.softcodes.wework.R;
import com.softcodes.wework.constants.RequestHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static com.softcodes.wework.constants.Links.ADD_CANDIDATE;
import static com.softcodes.wework.constants.Links.ADD_EMPLOYER;

public class SignupEmployer extends AppCompatActivity {
    EditText full_name,email,phone,password;
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Uri imageUri;
    Bitmap bitmap;
    private ImageView certificate;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_employer);
        full_name = findViewById(R.id.reg_emp_name);
        email = findViewById(R.id.reg_emp_email);
        phone = findViewById(R.id.reg_emp_phone);
        password = findViewById(R.id.reg_emp_password);
        certificate = findViewById(R.id.registration_certificate);
        progressDialog = new ProgressDialog(this);
    }

    public void signin(View view) {
        Intent i = new Intent(SignupEmployer.this, Login.class);
        startActivity(i);
        finish();
    }

    public void signupas_employer(View view) {

        final String EName = full_name.getText().toString();
        final String EEmail = email.getText().toString().trim();
        final String EPhone = phone.getText().toString();
        final String EPassword = password.getText().toString();
        if(TextUtils.isEmpty(EName)){
            full_name.requestFocus();
            full_name.setError("Enter Full Name");
            return;
        }
        if(TextUtils.isEmpty(EEmail)){
            email.requestFocus();
            email.setError("Enter an email");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(EEmail).matches()) {
            email.setError("Please Enter a Valid Email!");
            email.requestFocus();
            return;
        }
        if (EPhone.isEmpty() || EPhone.length() < 9) {
            phone.setError("Valid  Phone number is required");
            phone.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(EPassword)){
            password.requestFocus();
            password.setError("Enter a prefered Password!");
            return;
        }

        //noinspection deprecation
        @SuppressLint("StaticFieldLeak")
        class EmployerSignUp extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Saving Please Wait....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                // pick selected certificate image and convert it o bytes for storing in db;

                ByteArrayOutputStream byteArrayOutputStreamObject;

                byteArrayOutputStreamObject = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

                byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

                final String Ecertificate = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("name", EName);
                params.put("email", EEmail);
                params.put("phone", EPhone);
                params.put("password", EPassword);
                params.put("profile_photo", Ecertificate);

                return requestHandler.sendPostRequest(ADD_EMPLOYER, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(SignupEmployer.this, s, Toast.LENGTH_LONG).show();

                if(s.equalsIgnoreCase("User registered successfully!")) {
                    Intent intent = new Intent(SignupEmployer.this, Login.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    Toast.makeText(SignupEmployer.this, "Something went wrong!, Try again", Toast.LENGTH_LONG).show();
                }
            }


        }

        EmployerSignUp employerSignUp = new EmployerSignUp();
        employerSignUp.execute();

        full_name.setText("");
        email.setText("");
        phone.setText("");
        password.setText("");
    }


    public void get_employer_certificate(View view) {
        SelectImage();
    }

    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupEmployer.this);
        builder.setTitle("Choose Certificate");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);


                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Add Certificate"), SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }

        });

        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA) {

            Bundle bundle = data.getExtras();
            assert bundle != null;
            bitmap = (Bitmap) bundle.get("data");
            certificate.setImageBitmap(bitmap);
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                certificate.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}