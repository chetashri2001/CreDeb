package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.credeb.Adapter.ViewBeneficaryAdapter;
import com.example.credeb.Model.Beneficary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBeneficiary extends AppCompatActivity {
    ListView myListView;
    List<Beneficary> beneficaryList;
    DatabaseReference reference, reference1;
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_beneficiary);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();
        myListView = findViewById(R.id.myListView);
        beneficaryList = new ArrayList<Beneficary>();

        FirebaseDatabase.getInstance().getReference().child("App Database").child("Beneficary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                beneficaryList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Beneficary beneficary = snap.getValue(Beneficary.class);
                    if(beneficary.getUserID().equals(UID)){
                        beneficaryList.add(beneficary);
                    }
                }
                ViewBeneficaryAdapter adapter = new ViewBeneficaryAdapter(ViewBeneficiary.this, beneficaryList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}