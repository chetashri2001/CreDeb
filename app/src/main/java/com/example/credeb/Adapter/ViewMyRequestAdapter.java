package com.example.credeb.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.credeb.Model.Peer;
import com.example.credeb.Model.User;
import com.example.credeb.R;
import com.example.credeb.ViewRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewMyRequestAdapter extends ArrayAdapter {
    private Activity mContext;
    List<Peer> notifyList;

    public ViewMyRequestAdapter(Activity mContext, List<Peer> notifyList){
        super(mContext, R.layout.my_loan_list, notifyList);
        this.mContext = mContext;
        this.notifyList = notifyList;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.my_loan_list, null, true);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        TextView lender_name = listItemView.findViewById(R.id.name);
        TextView account_no = listItemView.findViewById(R.id.account);
        TextView amount = listItemView.findViewById(R.id.amount);
        TextView date = listItemView.findViewById(R.id.date);
        TextView id = listItemView.findViewById(R.id.id);
        TextView status = listItemView.findViewById(R.id.status);
        Button cancel = listItemView.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String row_id = id.getText().toString();
                row_id = row_id.substring(4);
                Log.d("IDD", "ID IS: " + row_id);
                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(row_id).child("status").setValue("Cancelled");
                cancel.setVisibility(cancel.GONE);
                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(row_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String s = snapshot.child("status").getValue(String.class);
                        status.setText("Status: " + s);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Peer peer = notifyList.get(position);
        lender_name.setText("Lender Name: " + peer.getLender_name());
        amount.setText("Amount: " + peer.getAmount());
        account_no.setText("Account No: " + peer.getLender_accountNo());
        date.setText("Return Date: " + peer.getReturn_date());
        id.setText("ID: " + peer.getId());
        status.setText("Status: " + peer.getStatus());
        if(peer.getStatus() != "Pending"){
            cancel.setVisibility(cancel.GONE);

        }
        if(peer.getStatus().equals("Pending")){
            cancel.setVisibility(cancel.VISIBLE);
        }


        return listItemView;
    }
}
