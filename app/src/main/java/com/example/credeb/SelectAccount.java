package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
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

import android.os.Bundle;

public class SelectAccount extends AppCompatActivity {

    Spinner spinner;
    TextView text;
    Button proceed;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID = firebaseUser.getUid();

        String custID;
        spinner = findViewById(R.id.spinner);
        text = findViewById(R.id.textView10);
        proceed = findViewById(R.id.proceed);
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
                adapter = new ArrayAdapter<String>(SelectAccount.this, android.R.layout.simple_spinner_item, spinnerData);
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
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(SelectAccount.this, ViewTransactionHistoryActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("Account no",acc);
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
                Intent intent = new Intent(SelectAccount.this, ViewTransactionHistoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Account no",acc);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}