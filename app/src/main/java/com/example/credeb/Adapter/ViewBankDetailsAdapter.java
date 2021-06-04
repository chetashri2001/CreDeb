package com.example.credeb.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.credeb.Model.UserBankDetails;
import com.example.credeb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ViewBankDetailsAdapter<firebaseUser> extends ArrayAdapter {

    List<UserBankDetails> bankList;
    private Activity mContext;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userID = firebaseUser.getUid();




    public ViewBankDetailsAdapter(Activity mContext, List<UserBankDetails> bankList) {
        super(mContext, R.layout.list_item, bankList);
        this.mContext = mContext;
        this.bankList = bankList;

    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item, null, true);

        TextView bank_name = listItemView.findViewById(R.id.textView13);
        TextView account_no = listItemView.findViewById(R.id.textView14);
        TextView ifsc_code = listItemView.findViewById(R.id.textView24);
        TextView balance = listItemView.findViewById(R.id.textView25);

        UserBankDetails appBankAccount = bankList.get(position);

        bank_name.setText("Bank Name: " + appBankAccount.getB_name());
        account_no.setText("Account No: " + appBankAccount.getAccount_no());
        ifsc_code.setText("IFSC Code: " + appBankAccount.getIfsc());
        balance.setText("Balance: " + appBankAccount.getBalance());
        return listItemView;
    }
}