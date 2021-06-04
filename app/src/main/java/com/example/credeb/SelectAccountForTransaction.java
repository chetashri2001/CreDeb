package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import android.os.Bundle;

import com.example.credeb.Model.UserBankDetails;
import com.example.credeb.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectAccountForTransaction<firebaseUser> extends AppCompatActivity {

    Spinner spinner;
    TextView text;
    Button proceed;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerData;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = firebaseUser.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_for_transaction);


        String custID;
        spinner = findViewById(R.id.spinner);
        text = findViewById(R.id.textView10);
        proceed = findViewById(R.id.proceedbtn);
        spinnerData = new ArrayList<>();

        final String[] acc = new String[1];
        FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").orderByChild("userID").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    UserBankDetails userBankDetails = snapshot1.getValue(UserBankDetails.class);
                    acc[0] = userBankDetails.getAccount_no();
                    spinnerData.add(userBankDetails.getAccount_no());

                }
                adapter = new ArrayAdapter<String>(SelectAccountForTransaction.this, android.R.layout.simple_spinner_item, spinnerData);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String acc = spinner.getSelectedItem().toString();
//                final String[] sender_bank = new String[1];
//                FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot snap : snapshot.getChildren()){
//                            UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
//                            if(userBankDetails.getAccount_no().equals(acc)){
//                                sender_bank[0] = userBankDetails.getB_name();
//                                Log.d("Sender", "Sender bank name" + sender_bank[0]);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(SelectAccountForTransaction.this, TransactionActivity.class);
//                        Log.d("Sender", "Sender bank name inside " + sender_bank[0]);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("Account no",acc);
//                        bundle.putString("Sender bank name", sender_bank[0]);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//
//                    }
//                },4000);
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc = spinner.getSelectedItem().toString();
                final String[] sender_bank = new String[1];
                FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                            if(userBankDetails.getAccount_no().equals(acc)){
                                sender_bank[0] = userBankDetails.getB_name();
                                Log.d("Sender", "Sender bank name" + sender_bank[0]);
                                Intent intent = new Intent(SelectAccountForTransaction.this, TransactionActivity.class);
                                Log.d("Sender", "Sender bank name inside " + sender_bank[0]);
                                Bundle bundle = new Bundle();
                                bundle.putString("Account no",acc);
                                bundle.putString("Sender bank name", sender_bank[0]);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }
}