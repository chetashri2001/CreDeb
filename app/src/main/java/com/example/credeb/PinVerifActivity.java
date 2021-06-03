package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.credeb.Model.BankAccount;
import com.example.credeb.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PinVerifActivity extends AppCompatActivity {

    EditText pin_num;
    Button submit, abort;


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_verif);
        pin_num = findViewById(R.id.pin);
        submit = findViewById(R.id.submit);
        abort = findViewById(R.id.abort);
        String mAuth = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String amount_no = bundle.getString("Amount to be transferred");
        String receiver_acc_no = bundle.getString("Receiver's account number");
        String receiver_ifsc = bundle.getString("Receiver's IFSC");
        String sender_acc_num = bundle.getString("Sender's account number");
        String sender_bank = bundle.getString("Sender bank name");
        String UID = bundle.getString("UID");
        String sender_mobile = bundle.getString("Sender mobile");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pin = pin_num.getText().toString();

                if (TextUtils.isEmpty(pin)) {
                    pin_num.setError("Pin is required.");
                    return;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(mAuth);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                            String val = snapshot.child("pin").getValue(String.class);
                            if (val.equals(pin)) {
                                Intent i = new Intent(PinVerifActivity.this, OtpVerifActivity.class);
                                Bundle bundle1 = getIntent().getExtras();
                                String sender_acc = bundle1.getString("Account no");
                                String sender_bank = bundle1.getString("Sender bank name");
                                Log.d("sender", "Sender mobile outside" + sender_mobile);
                                Bundle bundle = new Bundle();
                                bundle.putString("Amount to be transferred", amount_no);
                                bundle.putString("Receiver's account number", receiver_acc_no);
                                bundle.putString("Receiver's IFSC", receiver_ifsc);
                                bundle.putString("UID", UID);
                                bundle.putString("Sender's account number", sender_acc);
                                bundle.putString("Sender bank name", sender_bank);
                                bundle.putString("Sender mobile", sender_mobile);
                                i.putExtras(bundle);
                                startActivity(i);
                                }

                            else{
                                Toast.makeText(PinVerifActivity.this, "Pin incorrect!", Toast.LENGTH_SHORT).show();
                            }
                            }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            });


        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PinVerifActivity.this, DisplayMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}