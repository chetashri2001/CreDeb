package com.example.credeb;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.credeb.Adapter.ViewBankDetailsAdapter;
import com.example.credeb.Model.UserBankDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBank<userID> extends AppCompatActivity {
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    ListView myListView;
    List<UserBankDetails> bankList;
    DatabaseReference reference;
    String userID;

    {
        assert firebaseUser != null;
        userID = firebaseUser.getUid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bank);

        myListView = findViewById(R.id.myListView);
        bankList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bankList.clear();
                for(DataSnapshot bank : snapshot.getChildren()){
                    UserBankDetails appBankAccount = bank.getValue(UserBankDetails.class);
                    assert appBankAccount != null;
                    if(appBankAccount.getUserID().equals(userID)) {
                        bankList.add(appBankAccount);
                    }
                }
                ViewBankDetailsAdapter adapter = new ViewBankDetailsAdapter(ViewBank.this, bankList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}