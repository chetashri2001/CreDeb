package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.credeb.Model.BankAccount;
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


public class OtpSuccessActivity extends AppCompatActivity {
    Button btnProceed, btnDeny;
    int i = 0;
    protected boolean _active = true;
    protected int _splashTime = 1000;
    String userID;
    private FirebaseAuth mAuth;
    TextView amountDisp, receiverDisp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_success);
      //  mAuth = FirebaseAuth.getInstance();
        //userID = mAuth.getCurrentUser().getUid();
        Log.d("otp success", "uid  "+ FirebaseAuth.getInstance().getCurrentUser().getUid());
        Bundle bundle1 = getIntent().getExtras();
        assert bundle1 != null;
        String amount_no = bundle1.getString("Amount to be transferred");
        String receiver_acc_no = bundle1.getString("Receiver's account number");
        String receiver_ifsc = bundle1.getString("Receiver's IFSC");
        String sender_acc_num = bundle1.getString("Sender's acc num");
        String userID = bundle1.getString("UID");
        Log.d("acc", "get uid in otp success shud work "+ userID);
        Log.d("acc", "get uid in otp success "+ userID);
        HashMap<String, Object> map = new HashMap<>();
        btnProceed = findViewById(R.id.button_proceed);
        amountDisp = findViewById(R.id.textViewAmount);
        receiverDisp = findViewById(R.id.textViewReceiver);
        amountDisp.setText(amount_no);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtpSuccessActivity.this);
                builder.setTitle("Money sent!")
                        .setMessage("Rs. "+ amount_no + " sent." )
                        .setCancelable(false).setCancelable(false)
                        .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //this for skip dialog
                                dialog.cancel();
                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }
                }, 3500); //change 5000 with a specific time you want
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent back = new Intent(OtpSuccessActivity.this, DisplayMenuActivity.class);
                        startActivity(back);
                    }

                }, 4000L);


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
             final String[] accountNumber = new String[1];
                final String[] balance = new String[1];
                final String[] cust_ids = new String[2];
                //GET THE SENDER'S ACCOUNT NUMBER
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("sender", "hi "+ accountNumber[0]);
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Log.d("sender", "hi in "+ accountNumber[0]);
                            UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                            assert userBankDetails != null;
                            String val = userBankDetails.getUserID();
                            Log.d("acc", "get val "+ val);
                            if (val.equals(userID)) {
                                accountNumber[0] = userBankDetails.getAccount_no();
                                Log.d("acc", "acc hi"+ accountNumber[0]);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Log.d("sender", "acc new "+ accountNumber[0]);
                //deduct the money from sender's acc -> Bank1 -> Bank account details
                //add money to receiver's acc -> Bank1 -> Bank account details
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Bank1").child("Bank Account details");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            BankAccount bankAccount = snap.getValue(BankAccount.class);
                            assert bankAccount != null;
                            Log.d("sender", "acc "+ amount_no);
                            String val = bankAccount.getAccount_No();
                            if (val.equals(accountNumber[0])) {
                                Log.d("sender", "acc "+ amount_no);
                                balance[0] = bankAccount.getAccount_balance();
                                float f1 = Float.parseFloat(balance[0]);
                                assert amount_no != null;
                                float f2 = Float.parseFloat(amount_no);
                                String x = String.valueOf(f1 - f2);
                                String key = snap.getKey().toString();
                                //String value = snap.getValue().toString();
                                ref.child(key).child("account_balance").setValue(x);
                                cust_ids[0] = bankAccount.getCust_id();
                                map.put("cust_ID",cust_ids[0]);
                            }

                            if (val.equals(receiver_acc_no)) {
                                balance[0] = bankAccount.getAccount_balance();
                                float f1 = Float.parseFloat(balance[0]);
                                assert amount_no != null;
                                float f2 = Float.parseFloat(amount_no);
                                String x = String.valueOf(f1 + f2);
                                String key = snap.getKey().toString();
                                //String value = snap.getValue().toString();
                                ref.child(key).child("account_balance").setValue(x);
                                cust_ids[1] = bankAccount.getCust_id();
                                map.put("cust_ID2",cust_ids[1]);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //deduct the money from sender's acc -> App Database -> User bank details
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                            assert userBankDetails != null;

                            String val = userBankDetails.getUserID();
                            if (val.equals(userID)) {
                                balance[0] = userBankDetails.getBalance();
                                float f1 = Float.parseFloat(balance[0]);
                                assert amount_no != null;
                                float f2 = Float.parseFloat(amount_no);
                                String x = String.valueOf(f1 - f2);
                                String key = snap.getKey().toString();
                                //String value = snap.getValue().toString();
                                reference.child(key).child("Balance").setValue(x);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //add the money to receiver's acc -> App Database -> User bank details
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                            assert userBankDetails != null;
                            String val = userBankDetails.getAccount_no();
                            if (val.equals(receiver_acc_no)) {
                                balance[0] = userBankDetails.getBalance();
                                float f1 = Float.parseFloat(balance[0]);
                                assert amount_no != null;
                                float f2 = Float.parseFloat(amount_no);
                                String x = String.valueOf(f1 + f2);
                                String key = snap.getKey().toString();
                                //String value = snap.getValue().toString();
                                reference.child(key).child("Balance").setValue(x);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                map.put("transaction_ID","000");

                map.put("timestamp", ServerValue.TIMESTAMP);
                //map.put("time","14:20:30");
                //map.put("type","Credit");
                map.put("amount",amount_no);
                FirebaseDatabase.getInstance().getReference().child("Bank1").child("User Transaction details").push().setValue(map);
                map.clear();
            }

        });
        btnDeny = findViewById(R.id.button_deny);
        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtpSuccessActivity.this);
                builder.setTitle("Transaction cancelled")
                        .setMessage("Rs. 5000.00 was not transferred to Chetashri Mahajan.")
                        .setCancelable(false).setCancelable(false)
                        .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //this for skip dialog
                                dialog.cancel();
                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }
                }, 3500); //change 5000 with a specific time you want
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent back = new Intent(OtpSuccessActivity.this, DisplayMenuActivity.class);
                        startActivity(back);
                    }

                }, 4000L);
            }

        });

    }
}
