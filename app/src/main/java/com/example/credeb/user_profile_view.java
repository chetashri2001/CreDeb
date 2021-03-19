package com.example.credeb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.credeb.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_profile_view extends AppCompatActivity {
    String userID;
    private DatabaseReference reference;
    TextView fnameTextView;
    TextView lnameTextView;
    TextView mobileTextView;
    TextView emailTextView;
    TextView usernameTextView;
    private FirebaseUser firebaseUser;
    Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view);

        usernameTextView = findViewById(R.id.username);
        fnameTextView = findViewById(R.id.fname);
        lnameTextView = findViewById(R.id.lname);
        mobileTextView = findViewById(R.id.mobile);
        emailTextView = findViewById(R.id.email);
        back_button=findViewById(R.id.back_button);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        userinfo();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_profile_view.this, DisplayMenuActivity.class);
                startActivity(intent);
            }
        });
        
        update_button=findViewById(R.id.update_button);
          update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_profile_view.this, UserProfileUpdateActivity.class);
                startActivity(intent);
            }
        });


    }

    private void userinfo() {
        FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                usernameTextView.setText(user.getFname());
                //String txt = user.getFname() + " " + user.getLname();
                fnameTextView.setText(user.getFname());
                lnameTextView.setText(user.getLname());
                mobileTextView.setText(user.getMobile());
                emailTextView.setText(user.getEmail_ID());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
