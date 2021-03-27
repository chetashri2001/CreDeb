package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.credeb.Model.AppBank;
import com.example.credeb.Model.BankAccount;
import com.example.credeb.Model.BankUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddBankActivity extends AppCompatActivity {
    String userID;
    //String balance;
    private DatabaseReference reference, ref, ref1;

    EditText bank_name;
   // EditText branch_name;
    EditText account_no;
    EditText ifsc_code;
    Button add_button;
    private FirebaseUser firebaseUser;
    ListView myListView;
    List<AppBank> bankList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);

        bank_name = findViewById(R.id.bank_name);
       // branch_name = findViewById(R.id.branch_name);
        account_no = findViewById(R.id.account_no);
        ifsc_code = findViewById(R.id.ifsc_code);
        add_button = findViewById(R.id.add_button);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        userID = firebaseUser.getUid();
        bankList = new ArrayList<>();

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bank = bank_name.getText().toString();
                //String branch = branch_name.getText().toString();
                String ifsc = ifsc_code.getText().toString();
                String account = account_no.getText().toString();
                HashMap<String, Object> map = new HashMap<>();


                if (bank.equals("Bank1") || bank.equals("Bank2"))
                {
                    if (TextUtils.isEmpty(bank)) {
                    bank_name.setError("Bank name is required.");
                    return;
                }
                //if (TextUtils.isEmpty(branch)) {
                  //  branch_name.setError("Bank name is required.");
                    //return;
                //}
                if (TextUtils.isEmpty(account)) {
                    account_no.setError("Bank name is required.");
                    return;
                }
                if (TextUtils.isEmpty(ifsc)) {
                    ifsc_code.setError("Bank name is required.");
                    return;
                }


                reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
                String detailID = reference.push().getKey();
                final String[] balance = new String[1];

                

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(bank).child("Bank Account details");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            BankAccount bankAccount = snap.getValue(BankAccount.class);
                            assert bankAccount != null;
                            String val = bankAccount.getAccount_No();
                            if(val.equals(account)){

                                balance[0] = bankAccount.getAccount_balance();
                                reference.child(detailID).child("Balance").setValue(balance[0]);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                    map.put("b_name", bank);
                    map.put("account_no", account);
                    map.put("ifsc", ifsc);
                    map.put("userID", userID);
                    map.put("id", detailID);

                    reference.child(detailID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddBankActivity.this, "Bank Account added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddBankActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




            }
                else{
                    Toast.makeText(AddBankActivity.this, "Bank not Linked", Toast.LENGTH_SHORT).show();
                    System.out.println("Not linked");
                }
                Intent intent = new Intent(AddBankActivity.this, DisplayMenuActivity.class);
                startActivity(intent);


            }
        });


    }
}