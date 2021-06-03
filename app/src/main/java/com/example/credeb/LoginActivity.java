package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth fAuth;
    private FirebaseUser user;
    TextView register_Button;
    EditText email;
    EditText password;
    Button login_Button;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait");
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register_Button = findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();


        login_Button = findViewById(R.id.login_button);

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e_mail = email.getText().toString();
                String pw = password.getText().toString();


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
                pd.setMessage("Logging you in");
                pd.show();

                fAuth.signInWithEmailAndPassword(e_mail, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DisplayMenuActivity.class));
                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

}
