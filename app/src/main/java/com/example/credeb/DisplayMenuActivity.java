package com.example.credeb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayMenuActivity extends AppCompatActivity {
    Button goToOtpVerif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);


        goToOtpVerif = findViewById(R.id.otpButton);
        goToOtpVerif.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMenuActivity.this, OtpVerifActivity.class);
                startActivity(intent);
            }
        });
    }

}