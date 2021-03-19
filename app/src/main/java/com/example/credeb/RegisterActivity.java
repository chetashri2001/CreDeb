package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseAuth fAuth;
    private FirebaseUser user;
    TextView doLoginButton;
    EditText fname;
    EditText lname;
    EditText mobile;
    EditText email;
    EditText password;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.register_button);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        if (user != null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        doLoginButton = findViewById(R.id.doLoginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String f_name = fname.getText().toString();
                final String l_name = lname.getText().toString();
                final String mobile_no = mobile.getText().toString();
                final String e_mail = email.getText().toString();
                final String pw = password.getText().toString();

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

                if (TextUtils.isEmpty(pw)) {
                    password.setError("Password is required.");
                    return;
                }

                if (pw.length() < 6) {
                    password.setError("Password must be >= 6 characters.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(e_mail, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            //String user_id = mAuth.getCurrentUser().getUid();
                            mAuth = FirebaseAuth.getInstance();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("fname", f_name);
                            map.put("lname", l_name);
                            map.put("email_ID", e_mail);
                            map.put("mobile", mobile_no);
                            map.put("id", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                            FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(mAuth.getCurrentUser().getUid()).setValue(map);


                        } else {
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        doLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

}