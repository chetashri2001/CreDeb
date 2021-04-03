package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.credeb.Adapter.ViewTransactionHistoryAdapter;
import com.example.credeb.Model.TransactionHistory;
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

public class ViewTransactionHistoryActivity<userID> extends AppCompatActivity {
    ListView myListView;
    List<TransactionHistory> transactionHistoryActivity;
    DatabaseReference reference, reference1;
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction_history);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();
        Log.d("Sender cha uid ", "uid bagha "+UID);
        myListView = findViewById(R.id.myListView);
        transactionHistoryActivity = new ArrayList<TransactionHistory>();

        reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
        final String[] user_account = new String[1];
        reference1 = FirebaseDatabase.getInstance().getReference().child("Bank1").child("User Transaction details");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //transactionHistoryActivity.clear();
                for (DataSnapshot bank : snapshot.getChildren()) {
                    UserBankDetails userBankDetails = bank.getValue(UserBankDetails.class);
                    assert userBankDetails != null;
                    String val = userBankDetails.getUserID();
                    if (val.equals(UID)) {
                        user_account[0] = userBankDetails.getAccount_no();
                        Log.d("sender cha acc num", "hi"+user_account[0]);

                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                transactionHistoryActivity.clear();
                                for (DataSnapshot bank : snapshot.getChildren()) {
                                    TransactionHistory transactionHistory = bank.getValue(TransactionHistory.class);
                                    assert transactionHistory != null;
                                    Log.d("sender cha acc num", "hiii  "+ user_account[0]);
                                    String val1 = transactionHistory.getSender(); //sender
                                    String val2 = transactionHistory.getReceiver(); //receiver
                                    Log.d("sender cha acc num", "hiii"+val);
                                    if (val1.equals(user_account[0])) {
                                        transactionHistoryActivity.add(transactionHistory);
                                    }
                                    if (val2.equals( user_account[0])) {
                                        transactionHistoryActivity.add(transactionHistory);
                                    }

                                }
                                ViewTransactionHistoryAdapter adapter = new ViewTransactionHistoryAdapter(ViewTransactionHistoryActivity.this, transactionHistoryActivity);
                                myListView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
//                ViewTransactionHistoryAdapter adapter = new ViewTransactionHistoryAdapter(ViewTransactionHistoryActivity.this, transactionHistoryActivity);
//                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}



//package com.example.credeb;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class ViewTransactionHistoryActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_transaction_history);
//    }
//}