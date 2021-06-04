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


import com.example.credeb.Model.Beneficary;
import com.example.credeb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ViewBeneficaryAdapter extends ArrayAdapter{
    private Activity mContext;
    List<Beneficary> beneficaryList;

    public ViewBeneficaryAdapter(Activity mContext, List<Beneficary> beneficaryList){
        super(mContext, R.layout.beneficary_list, beneficaryList);
        this.mContext = mContext;
        this.beneficaryList = beneficaryList;

    }


    @SuppressLint("SetTextI18n")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.beneficary_list, null, true);

        TextView name = listItemView.findViewById(R.id.textView1);
        TextView bank_name = listItemView.findViewById(R.id.textView13);
        TextView account_no = listItemView.findViewById(R.id.textView14);
        TextView ifsc_code = listItemView.findViewById(R.id.textView24);

        Beneficary beneficary = beneficaryList.get(position);

        name.setText("Beneficiary Name: " + beneficary.getName());
        bank_name.setText("Bank Name: " + beneficary.getBank_name());
        account_no.setText("Account No: " + beneficary.getAccount_no());
        ifsc_code.setText("IFSC Code: " + beneficary.getIfsc());

        return listItemView;
    }

}
