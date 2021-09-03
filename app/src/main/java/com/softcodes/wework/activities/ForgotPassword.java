package com.softcodes.wework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.softcodes.wework.R;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
    }

    public void back_to_login(View view) {
        Intent dsp = new Intent(ForgotPassword.this,Login.class);
        startActivity(dsp);
        finish();
    }
}