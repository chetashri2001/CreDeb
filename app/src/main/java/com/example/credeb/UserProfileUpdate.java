package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.credeb.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileUpdateActivity extends AppCompatActivity {

    EditText fname;
    EditText lname;
    EditText mobile;
    EditText email;
    Button update_button;
    String userID;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        update_button = findViewById(R.id.update_button);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        display_user_info();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f_name = fname.getText().toString();
                String l_name = lname.getText().toString();
                String mobile_no = mobile.getText().toString();
                String e_mail = email.getText().toString();

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

                FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).child("fname").setValue(f_name);
                FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).child("lname").setValue(l_name);
                FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).child("email_ID").setValue(e_mail);
                FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).child("mobile").setValue(mobile_no);


                Toast.makeText(UserProfileUpdateActivity.this, "Update successful", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void display_user_info() {
        FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                fname.setText(user.getFname());
                lname.setText(user.getLname());
                mobile.setText(user.getMobile());
                email.setText(user.getEmail_ID());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
