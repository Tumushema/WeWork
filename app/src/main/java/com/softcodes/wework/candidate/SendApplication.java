package com.softcodes.wework.candidate;

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
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softcodes.wework.R;
import com.softcodes.wework.constants.RequestHandler;
import com.softcodes.wework.constants.SharedPrefManager;
import com.softcodes.wework.constants.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static com.softcodes.wework.candidate.CandidateDashboard.JOB_ID;
import static com.softcodes.wework.constants.Links.ADD_APPLICATION;


public class SendApplication extends AppCompatActivity {
TextView dropfiles;
    private static final int REQUEST_CAMERA = 1, SELECT_FILE = 0;
    Uri imageUri;
    Bitmap bitmap;
    private ImageView resume;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_resume);
        dropfiles = findViewById(R.id.txt_drop_files);
        resume = findViewById(R.id.resume_upload);
        progressDialog = new ProgressDialog(this);
    }



    private void SelectImage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SendApplication.this);
        builder.setTitle("Choose Resume");
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
                    startActivityForResult(Intent.createChooser(intent, "Add Resume /CV"), SELECT_FILE);

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
            resume.setImageBitmap(bitmap);
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                resume.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void upload_resume_cv(View view) {
        User user = SharedPrefManager.getInstance(this).getUser();
        final String candidate_id = String.valueOf(user.getlPhone());
        Intent intent = getIntent();
        final String id = intent.getStringExtra(JOB_ID);


        @SuppressWarnings("deprecation")
        @SuppressLint("StaticFieldLeak")
        class ResumeCv extends AsyncTask<Void, Void, String> {



            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
                progressDialog.setMessage("Saving Please Wait....");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(Void... voids) {

             ByteArrayOutputStream byteArrayOutputStreamObject;

                byteArrayOutputStreamObject = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

                byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

                final String cv = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
                RequestHandler requestHandler = new RequestHandler();

                HashMap<String, String> params = new HashMap<>();
                params.put("candidate_id", candidate_id);
                params.put("url", cv);
                params.put("job_id", id);

                return requestHandler.sendPostRequest(ADD_APPLICATION, params);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                Toast.makeText(SendApplication.this, s, Toast.LENGTH_LONG).show();

                if(s.equalsIgnoreCase("Application submitted!")) {
                    Intent intent = new Intent(SendApplication.this, CandidateDashboard.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    Toast.makeText(SendApplication.this, "oops something went wrong!, Try again", Toast.LENGTH_LONG).show();
                }
            }


        }

        ResumeCv resumeCv = new ResumeCv();
        resumeCv.execute();
    }

    public void uploadcv(View view) {
        SelectImage();
        dropfiles.setVisibility(View.GONE);
    }
}