package com.example.credeb;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.credeb.Model.Beneficary;
import com.example.credeb.Model.User;
import com.example.credeb.Model.UserBankDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PeertoPeerForm extends AppCompatActivity {
    TextView lender_name;
    TextView lender_ifsc;
    EditText amount;
    TextView editdate;
    DatePicker picker;
    //TextView time;
    Button request;
    Button get;
    CheckBox checkBox;
    RadioGroup radioGroup;
    //    RadioButton one, two;
    Spinner lender_account;
    Spinner my_account;
    ArrayAdapter<String> adapter;
    ArrayList<String> lenderlist;
    ArrayAdapter<String> adapter1;
    ArrayList<String> mylist;
    DatePickerDialog.OnDateSetListener setListener;
    DatabaseReference reference, ref;
    String receiver_mobile;
    // int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peerto_peer_form);
        ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.SEND_SMS},
                1);
        lender_name = findViewById(R.id.len_name);
        lender_ifsc = findViewById(R.id.ifsccode);
        editdate = findViewById(R.id.editTextDate);
        //  time = findViewById(R.id.editTextTime);
        amount = findViewById(R.id.amount);
        request = findViewById(R.id.request);
        get = findViewById(R.id.get);
        //radioGroup = findViewById(R.id.radioGroup);
//        one = findViewById(R.id.one);
//        two = findViewById(R.id.two);
        lender_account = findViewById(R.id.lend_acc);
        my_account = findViewById(R.id.my_acc);
        checkBox = findViewById(R.id.checkBox);
        picker = findViewById(R.id.picker);
        final String[] lender_userID = new String[1];

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int[] month = {calendar.get(Calendar.MONTH)};
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();

        lenderlist = new ArrayList<>();
        mylist = new ArrayList<>();
        final String[] lender_details = new String[2];
        FirebaseDatabase.getInstance().getReference().child("App Database").child("Beneficary").orderByChild("userID").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot bene : snapshot.getChildren()){
                    Beneficary beneficary = bene.getValue(Beneficary.class);
                    lenderlist.add(beneficary.getAccount_no());
                }
                adapter = new ArrayAdapter<String>(PeertoPeerForm.this, android.R.layout.simple_spinner_item, lenderlist);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lender_account.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").orderByChild("userID").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot bank : snapshot.getChildren()){
                    UserBankDetails userBankDetails = bank.getValue(UserBankDetails.class);
                    mylist.add(userBankDetails.getAccount_no());
                }
                adapter1 = new ArrayAdapter<String>(PeertoPeerForm.this, android.R.layout.simple_spinner_item, mylist);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                my_account.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc = lender_account.getSelectedItem().toString();
                FirebaseDatabase.getInstance().getReference().child("App Database").child("Beneficary").orderByChild("userID").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Beneficary beneficary = snap.getValue(Beneficary.class);
                            if(acc.equals(beneficary.getAccount_no())){
                                lender_name.setText(beneficary.getName());
                                lender_details[1] = beneficary.getName();
                                lender_details[0] = acc;
                                lender_ifsc.setText(beneficary.getIfsc());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

//        editdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(PeertoPeerForm.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month[0], day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//            }
//        });
//        setListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                month[0] = month[0] +1+1;
//                String date = day + "/" + month[0] + "/" + year;
//                editdate.setText(date);
//
//            }
//        };

//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar calender1 = Calendar.getInstance();
//                hour = calender1.get(Calendar.HOUR_OF_DAY);
//                minute = calender1.get(Calendar.MINUTE);
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(PeertoPeerForm.this, new TimePickerDialog.OnTimeSetListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
//                        time.setText(i + ":" + i1 + ":00");
//                    }
//                },hour, minute, false);
//                timePickerDialog.show();
//            }
//        });
//        picker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = picker.getDayOfMonth();
                int month = (picker.getMonth() + 1);
                int year = picker.getYear();
                String mnth, curmnth;
                Date promised_date, today;
                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                int currentYear = calendar.get(android.icu.util.Calendar.YEAR);
                int currentMonth = calendar.get(android.icu.util.Calendar.MONTH) + 1;
                int currentDate = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);

                SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
                if(month<10){
                    mnth = "0" + month;
                    curmnth = "0" + currentMonth;
                }
                else{
                    mnth = ""+month;
                    curmnth = "" + currentMonth;
                }
                // Get the two dates to be compared
                try {
                    promised_date = sdfo.parse(year+"-"+mnth+"-"+day);
                    today = sdfo.parse(currentYear+"-"+curmnth+"-"+currentDate);
                    if(promised_date.before(today)){
                        editdate.setError("Invalid option.");
                        Toast.makeText(PeertoPeerForm.this, "Date not set correctly.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                String date = day + "/" + month + "/" + year;
                editdate.setText(date);
                String len_acc = lender_account.getSelectedItem().toString();
                String len_name = lender_name.getText().toString();
                String len_ifsc = lender_ifsc.getText().toString();
                String myacc = my_account.getSelectedItem().toString();
                String amt = amount.getText().toString();
                String return_date = editdate.getText().toString();
                if (TextUtils.isEmpty(amt)) {
                    amount.setError("Amount is required.");
                    return;
                }

//                String return_time = time.getText().toString();
//                int radio = radioGroup.getCheckedRadioButtonId();
                //RadioButton one = radioGroup.findViewById(radio);
                ref = FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer");
                String id = ref.push().getKey();
                FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").orderByChild("account_no").equalTo(len_acc).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapp : snapshot.getChildren()){
                            UserBankDetails user = snapp.getValue(UserBankDetails.class);
                            //ref.child(id).child("lender_userID").setValue(user.getUserID());
                            lender_userID[0] = user.getUserID();
                            Log.d("lender", "Lender userID in loop: " + lender_userID[0]);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // String method = one.getText().toString();
                Log.d("Request", "Details are: " + len_acc + len_name + len_ifsc + amt + return_date);
                HashMap<String, Object> map = new HashMap<>();

                map.clear();
                if(checkBox.isChecked()){
                    reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren()){
                                UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                                if(userBankDetails.getAccount_no().equals(len_acc)){
                                    String len_bank = userBankDetails.getB_name();
                                    ref.child(id).child("lender_bank").setValue(len_bank);
                                    Log.d("lender", "Lender userID out loop1: " + lender_userID[0]);
                                }
                                if(userBankDetails.getAccount_no().equals(myacc)){
                                    map.put("rec_bank", userBankDetails.getB_name());
                                    map.put("rec_ifsc", userBankDetails.getIfsc());
                                    ref.child(id).setValue(map);
                                    ref.child(id).child("lender_accountNo").setValue(len_acc);
                                    ref.child(id).child("lender_ifsc").setValue(len_ifsc);
                                    ref.child(id).child("lender_name").setValue(len_name);
                                    ref.child(id).child("rec_accountNo").setValue(myacc);
                                    ref.child(id).child("amount").setValue(amt);
                                    ref.child(id).child("return_date").setValue(return_date);
//                                    ref.child(id).child("return_time").setValue(return_time);
                                    //ref.child(id).child("pay_method").setValue(method);
                                    ref.child(id).child("userID").setValue(userID);
                                    ref.child(id).child("id").setValue(id);
                                    ref.child(id).child("status").setValue("Pending");
                                    ref.child(id).child("lender_userID").setValue(lender_userID[0]);
                                    ref.child(id).child("bounce").setValue("0");
                                    if(len_ifsc.startsWith("BANK1")) {
                                        ref.child(id).child("lender_bank").setValue("Bank1");
                                    }
                                    if(len_ifsc.startsWith("BANK2")) {
                                        ref.child(id).child("lender_bank").setValue("Bank2");
                                    }
                                    map.clear();
                                    Log.d("lender", "Lender userID out loop2: " + lender_userID[0]);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                else{
                    checkBox.setError("Agree the conditions");
                    return;
                }
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("App Database").child("User details");
                FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                            String acc = userBankDetails.getAccount_no();
                            if(lender_details[0].equals(acc)){
                                String receiver_bank = userBankDetails.getB_name();
                                String reciever_uid = userBankDetails.getUserID();
                                ref.child(reciever_uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User user = snapshot.getValue(User.class);
                                        assert user != null;
                                        receiver_mobile = user.getMobile();
                                        Log.d("receiver", "Mobile no receiver inside peer to peer" + receiver_mobile);
                                        String msg1 = "Dear " + lender_details[1] + ", you have a peer to peer loan request. Please check the notifications" +
                                                " page in your app to know more about this.";

                                        Intent intent = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                                        //Get the SmsManager instance and call the sendTextMessage method to send message
                                        SmsManager sms = SmsManager.getDefault();
                                        sms.sendTextMessage("+91"+receiver_mobile, null, msg1, pi, null);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Log.d("Reciever", "Reciever bank name is "+receiver_bank);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(PeertoPeerForm.this, "We will let you know if the lender accepts or rejects your request.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PeertoPeerForm.this, DisplayMenuActivity.class);
                startActivity(intent);
            }
        });

    }
}

