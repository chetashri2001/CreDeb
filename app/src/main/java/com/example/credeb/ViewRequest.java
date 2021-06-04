package com.example.credeb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.credeb.R;

public class ViewRequest extends AppCompatActivity {

    Button loan;
    Button myloan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        loan = findViewById(R.id.loan);
        myloan = findViewById(R.id.myloan);
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRequest.this, ViewAllNotifications.class);
                startActivity(intent);
            }
        });

        myloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewRequest.this, ViewMyRequest.class);
                startActivity(intent);
            }
        });
    }
}