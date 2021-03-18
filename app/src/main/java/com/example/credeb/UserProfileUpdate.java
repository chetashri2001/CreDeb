package com.example.credeb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileUpdate extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText mobile;
    EditText email;
    Button update_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        update_button = findViewById(R.id.update_button);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String f_name = fname.getText().toString();
                final String l_name = lname.getText().toString();
                final String mobile_no = mobile.getText().toString();
                final String e_mail = email.getText().toString();


                if (TextUtils.isEmpty(f_name)) {
                    fname.setError("First name is required.");
                    return;
                }

                if (TextUtils.isEmpty(l_name)) {
                    lname.setError("Last name is required.");
                    return;
                }

                if (TextUtils.isEmpty(mobile_no)) {
                    mobile.setError("Mobile number is required.");
                    return;
                }

                if (TextUtils.isEmpty(e_mail)) {
                    email.setError("Email is required.");
                    return;
                }


            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserProfileUpdate.this, "Update successful", Toast.LENGTH_SHORT).show();

            }
        });

    }
}