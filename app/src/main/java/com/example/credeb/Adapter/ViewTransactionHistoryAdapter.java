package com.example.credeb.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.credeb.Model.TransactionHistory;
import com.example.credeb.Model.UserBankDetails;
import com.example.credeb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewTransactionHistoryAdapter<firebaseUser> extends ArrayAdapter {

    List<TransactionHistory> transactionHistoryActivity;
    String account;
    private Activity mContext;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    public ViewTransactionHistoryAdapter(Activity mContext, List<TransactionHistory> transactionHistoryActivity) {
        super(mContext, R.layout.trans_list_view, transactionHistoryActivity);
        this.mContext = mContext;
        this.transactionHistoryActivity = transactionHistoryActivity;

    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.trans_list_view, null, true);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        TextView bank_name = listItemView.findViewById(R.id.textView13);
        TextView account_no = listItemView.findViewById(R.id.textView14);
        TextView ifsc_code = listItemView.findViewById(R.id.textView24);
        TextView balance = listItemView.findViewById(R.id.textView25);
        TextView balance1 = listItemView.findViewById(R.id.textView112);

       // TransactionHistory transactionHistory = transactionHistoryActivity.get(position);

//        bank_name.setText("User2" + transactionHistory.getUser2());
//        balance.setText("Receiver IFSC: " + transactionHistory.getUser2_ifsc());
//        account_no.setText("Amount: " + transactionHistory.getAmount());
//        ifsc_code.setText("Date and time: " + transactionHistory.getDate_and_Time());
//        balance1.setText("Type of transaction: " + transactionHistory.getType_of_transaction());



            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
    final String[] accountNumber = new String[1];
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {
        Log.d("sender", "hi " + accountNumber[0]);
        for (DataSnapshot snap : snapshot.getChildren()) {
        Log.d("sender", "hi in " + accountNumber[0]);
        UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
        assert userBankDetails != null;
        String val = userBankDetails.getUserID();
        String ifsc = userBankDetails.getIfsc();
        Log.d("acc", "get val " + val);
        if (val.equals(userID)) {
        accountNumber[0] = userBankDetails.getAccount_no();

        TransactionHistory transactionHistory = transactionHistoryActivity.get(position);
        if (accountNumber[0].equals(transactionHistory.getUser1()) && (transactionHistory.getType_of_transaction()).equals("Debit")) {
        Log.d("ADAPTER", "acc sender" + accountNumber[0]);
        bank_name.setText("Receiver: " + transactionHistory.getUser2());
        balance.setText("Receiver IFSC: " + transactionHistory.getUser2_ifsc());
        account_no.setText("Amount: " + transactionHistory.getAmount());
        ifsc_code.setText("Date and time: " + transactionHistory.getDate_and_Time());
        balance1.setText("Type of transaction: " + transactionHistory.getType_of_transaction());
        }
        if (accountNumber[0].equals(transactionHistory.getUser1()) && (transactionHistory.getType_of_transaction()).equals("Credit")) {
        Log.d("ADAPTER", "acc receiver" + accountNumber[0]);
        bank_name.setText("Sender: " + transactionHistory.getUser2());
        balance.setText("Sender IFSC: " + transactionHistory.getUser2_ifsc());
        account_no.setText("Amount: " + transactionHistory.getAmount());
        ifsc_code.setText("Date and time: " + transactionHistory.getDate_and_Time());
        balance1.setText("Type of transaction: " + transactionHistory.getType_of_transaction());

        }
        }

        }
        }

@Override
public void onCancelled(@NonNull DatabaseError error) {

        }
        });



        return listItemView;

    }
}

