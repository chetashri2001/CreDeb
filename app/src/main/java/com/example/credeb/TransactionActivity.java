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
import com.example.credeb.Model.UserBankDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TransactionActivity extends AppCompatActivity {

    EditText amount, ifsc_rec, receiver;
    Button pay, abort;
   String userID, sender_acc_no;
//    static String acc_num;
    private FirebaseUser firebaseUser;
    private FirebaseCallback callback;
    //ferkerrejhbg

    public interface FirebaseCallback {
        void onResponse(String name);
    }
    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        amount = findViewById(R.id.amt);
        receiver = findViewById(R.id.receiverAcc);
        pay = findViewById(R.id.payButton);
        abort = findViewById(R.id.abortButton);
        ifsc_rec = findViewById(R.id.receiverIFSC);
        HashMap<String, Object> map = new HashMap<>();
        assert firebaseUser != null;
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        userID = firebaseUser.getUid();
        Log.d("transac", "uid  "+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        userID =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();
        Log.d("transac", "uid new "+ FirebaseAuth.getInstance().getCurrentUser().getUid());

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receiver_acc_no = receiver.getText().toString();
                String amount_no = amount.getText().toString();
                String receiver_ifsc = ifsc_rec.getText().toString();


                if (TextUtils.isEmpty(receiver_acc_no)) {
                    receiver.setError("receiver acc no is required.");
                    return;
                }

                if (TextUtils.isEmpty(amount_no)) {
                    amount.setError("amount is required.");
                    return;
                }

                if (TextUtils.isEmpty(receiver_ifsc)) {
                    ifsc_rec.setError("amount is required.");
                    return;
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
                final String[] accountNumber = new String[1];
                final String[] balance = new String[1];
                final String[] cust_ids = new String[2];
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("sender", "hi " + accountNumber[0]);
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Log.d("sender", "hi in " + accountNumber[0]);
                            UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                            assert userBankDetails != null;
                            String val = userBankDetails.getUserID();
                            Log.d("acc", "get val " + val);
                            if (val.equals(UID)) {
                                balance[0] = userBankDetails.getBalance();
                                float f1 = Float.parseFloat(balance[0]);
                                float f2 = Float.parseFloat(amount_no);
                                if (Float.compare(f1, f2) < 0) {
                                    Toast.makeText(TransactionActivity.this, "Balance insufficient", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TransactionActivity.this, DisplayMenuActivity.class);
                                    startActivity(intent);
                                }
                                }
                                Log.d("acc", "acc hi" + accountNumber[0]);
                            }
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                //Log.d("acc", "acc hi" + sender_acc_no);
                Intent i = new Intent(TransactionActivity.this, OtpVerifActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Amount to be transferred", amount_no);
                bundle.putString("Receiver's account number", receiver_acc_no);
                bundle.putString("Receiver's IFSC", receiver_ifsc);
                bundle.putString("UID", UID);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TransactionActivity.this, DisplayMenuActivity.class);
                startActivity(i);
            }
        });
    }

    }
