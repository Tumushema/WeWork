package com.softcodes.wework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.softcodes.wework.R;

public class SignupAs extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_as);

        View card1 = findViewById(R.id.go_to_employer);
        View card2 = findViewById(R.id.go_to_jobseek);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.go_to_employer:
                Intent intent = new Intent(SignupAs.this, SignupEmployer.class);
                startActivity(intent);
                finish();
                break;
            case R.id.go_to_jobseek:
                Intent i = new Intent(SignupAs.this, SignUpCandidate.class);
                startActivity(i);
                finish();
                break;



        }
}
}