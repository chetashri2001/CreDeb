package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.credeb.Model.BankAccount;
import com.example.credeb.Model.Peer;
import com.example.credeb.Model.User;
import com.example.credeb.Model.UserBankDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import android.icu.util.Calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DisplayMenuActivity extends AppCompatActivity {
    private FlowingDrawer mDrawer;
    private ImageView menu;
    private LinearLayout ll_view_prof, ll_update_prof, ll_view_bank_acc, notif, logout, about, viewben;
    Button transfer, peertopeer, transaction_history, multiple_acc, beneficarybtn;
    TextView user_name, user_mobile;
    private FirebaseUser firebaseUser;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayMenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);
        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userID = user.getUid();
        menu = findViewById(R.id.menu);
        ll_view_prof = findViewById(R.id.ll_view_prof);
        ll_update_prof = findViewById(R.id.ll_update_prof);
        ll_view_bank_acc = findViewById(R.id.ll_view_bank_acc);
        // ll_multiple_bank_acc = findViewById(R.id.ll_multiple_bank_acc);
        notif = findViewById(R.id.notif);
        viewben = findViewById(R.id.viewbene);
        logout = findViewById(R.id.logout);
        about = findViewById(R.id.about);
        transfer = findViewById(R.id.tranfer);
        user_name = findViewById(R.id.user_name);
        user_mobile = findViewById(R.id.user_mobile);
        beneficarybtn = findViewById(R.id.beneficary);
        transaction_history = findViewById(R.id.transaction_history);
        peertopeer = findViewById(R.id.peer_to_peer);
        multiple_acc = findViewById(R.id.multiple_bank_acc);


        viewben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeMenu(true);
                Intent intent = new Intent(DisplayMenuActivity.this, ViewBeneficiary.class);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // userinfo();
                mDrawer.openMenu(true);
            }
        });

        ll_view_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeMenu(true);
                Intent intent = new Intent(DisplayMenuActivity.this, UserProfileView.class);
                startActivity(intent);
            }
        });
        ll_update_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeMenu(true);
                Intent intent = new Intent(DisplayMenuActivity.this, UserProfileUpdateActivity.class);
                startActivity(intent);
            }
        });
        ll_view_bank_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeMenu(true);
                Intent intent = new Intent(DisplayMenuActivity.this, ViewBank.class);
                startActivity(intent);
            }
        });
//        ll_multiple_bank_acc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDrawer.closeMenu(true);
//                Intent intent = new Intent(DisplayMenuActivity.this, AddBankActivity.class);
//                startActivity(intent);
//            }
//
//        });
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeMenu(true);
                Intent intent = new Intent(DisplayMenuActivity.this, ViewRequest.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeMenu(true);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DisplayMenuActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DisplayMenuActivity.this, LoginActivity.class));
                finish();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeMenu(true);
                Intent intent = new Intent(DisplayMenuActivity.this, AboutCredebInfoActivity.class);
                startActivity(intent);
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMenuActivity.this, SelectAccountForTransaction.class);
                startActivity(intent);
            }
        });
        peertopeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMenuActivity.this, PeertoPeerForm.class);
                startActivity(intent);
            }
        });
        transaction_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMenuActivity.this, SelectAccount.class);
                startActivity(intent);
            }
        });
        multiple_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMenuActivity.this, AddBankActivity.class);
                startActivity(intent);
            }
        });

        beneficarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMenuActivity.this, AddBeneficary.class);
                startActivity(intent);
            }
        });


        FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Peer peerDetails = snap.getValue(Peer.class);
                    assert peerDetails != null;
                    String key1 = snap.getKey().toString();
                    if (peerDetails.getStatus().equals("Pending") || peerDetails.getStatus().equals("Accepted")) {
                        Log.d("Value", "Yesss");
                        String date_of_return = peerDetails.getReturn_date();
                        String[] arrOfDate = date_of_return.split("/", -1);
                        int year = Integer.parseInt(arrOfDate[2]);
                        int month = Integer.parseInt(arrOfDate[1]);
                        int date = Integer.parseInt(arrOfDate[0]);

                        String mnth, curmnth;
                        Date promised_date, today;
                        android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                        int currentYear = calendar.get(android.icu.util.Calendar.YEAR);
                        int currentMonth = calendar.get(android.icu.util.Calendar.MONTH) + 1;
                        int currentDate = calendar.get(android.icu.util.Calendar.DAY_OF_MONTH);

                        SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
                        if (month < 10) {
                            mnth = "0" + month;
                            curmnth = "0" + currentMonth;
                        } else {
                            mnth = "" + month;
                            curmnth = "" + currentMonth;
                        }
                        // Get the two dates to be compared
                        try {
                            promised_date = sdfo.parse(year + "-" + mnth + "-" + date);
                            today = sdfo.parse(currentYear + "-" + curmnth + "-" + currentDate);
                            if (promised_date.before(today)) {
                                String key = snap.getKey().toString();
                                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(key).child("status").setValue("Aborted");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (peerDetails.getStatus().equals("Transferred")) {
                        Calendar calendar = Calendar.getInstance();
                        int currentYear = calendar.get(Calendar.YEAR);
                        int currentMonth = calendar.get(Calendar.MONTH);
                        int currentDate = calendar.get(Calendar.DAY_OF_MONTH);
                        String date_of_return = peerDetails.getReturn_date();
                        String[] arrOfDate = date_of_return.split("/", -1);
                        int year = Integer.parseInt(arrOfDate[2]);
                        int month = Integer.parseInt(arrOfDate[1]);
                        int date = Integer.parseInt(arrOfDate[0]);
                        Log.d("yr mnth date", "" + year + " " + " " + month + " " + date + "   " + currentYear + " " + currentMonth + " " + currentDate);

                        if (year == currentYear && month == (currentMonth + 1) && date == currentDate) {
                            final String[] balance = new String[1];

                            String amount = peerDetails.getAmount();
                            String lender_account = peerDetails.getLender_accountNo();
                            String receiver_account = peerDetails.getRec_accountNo();
                            String lender_bank = peerDetails.getLender_bank();
                            String receiver_bank = peerDetails.getRec_bank();
                            String lender_uid = peerDetails.getLender_userID();
                            String receiver_uid = peerDetails.getUserID();
                            String bounce = peerDetails.getBounce();

                            FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snnap : snapshot.getChildren()) {
                                        UserBankDetails userBankDetails = snnap.getValue(UserBankDetails.class);
                                        if (userBankDetails.getAccount_no().equals(receiver_account) && userBankDetails.getUserID().equals(receiver_uid)) {
                                            String bal = userBankDetails.getBalance();

                                            float bal1 = Float.parseFloat(bal);
                                            float amt = Float.parseFloat(amount);
                                            Log.d("bal and amt", " " + bal1 + " " + amt);
                                            if (Float.compare(bal1, amt) < 0) {
                                                Log.d("namaskar", " " + bal1 + " " + amt);
                                            }
                                            if (Float.compare(bal1, amt) < 0 && bounce.equals("0")) {
                                                FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for (DataSnapshot data : snapshot.getChildren()) {
                                                            User user1 = data.getValue(User.class);
                                                            String id = user1.getId();
                                                            if (id.equals(receiver_uid)) {
                                                                // int bounce = Integer.parseInt(peerDetails.getBounce());
                                                                String name = user1.getFname() + " " + user1.getLname();
                                                                String mobile = user1.getMobile();
                                                                Log.d("Valuess", " " + user1.getId() + " " + receiver_uid);
                                                                Log.d("Valuess", " " + user1.getMobile());
                                                                Log.d("Valuess", "Name is: " + name);
//                                                                String msg1 = "Dear " + name + ", the amount of INR " + amount + " you had borrowed from  XXXXXXXXXXXXX" + lender_account.substring(lender_account.length() - 3) +
//                                                                        " has to be returned to their account today. Your bank balance does not have sufficient amount for this transfer. " +
//                                                                        "Please make sure this account has enough balance as soon as possible.";
                                                                String msg1 = "Dear " + name + ", the amount of INR "+amount+" you had borrowed from XXXXXXXXXXXXX" +lender_account.substring(lender_account.length() - 3)+" has to be returned today but your balance is not sufficient.";
                                                                Intent intent = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                                                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                                                                //Get the SmsManager instance and call the sendTextMessage method to send message
                                                                SmsManager sms = SmsManager.getDefault();
                                                                sms.sendTextMessage("+91"+mobile, null, msg1, pi, null);
                                                                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(key1).child("bounce").setValue("1");
                                                            }

                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            } else {
                                                if(Float.compare(bal1, amt) >= 0) {
                                                    //add the money to sender's acc -> sender_bank -> Bank account details
                                                    //deduct money from receiver's acc -> receiver bank -> Bank account details

                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(lender_bank).child("Bank Account details");
                                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot snap : snapshot.getChildren()) {
                                                                BankAccount bankAccount = snap.getValue(BankAccount.class);
                                                                final String sender_acc = null;
                                                                assert bankAccount != null;
                                                                Log.d("sender", "acc " + amount);
                                                                String val = bankAccount.getAccount_No();
                                                                if (val.equals(lender_account)) {
                                                                    balance[0] = bankAccount.getAccount_balance();
                                                                    float f1 = Float.parseFloat(balance[0]);
                                                                    assert amount != null;
                                                                    float f2 = Float.parseFloat(amount);
                                                                    String x = String.valueOf(f1 + f2);
                                                                    String key = snap.getKey().toString();
                                                                    ref.child(key).child("account_balance").setValue(x);
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {
                                                        }
                                                    });

                                                    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child(receiver_bank).child("Bank Account details");
                                                    reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot snap : snapshot.getChildren()) {
                                                                BankAccount bankAccount = snap.getValue(BankAccount.class);
                                                                final String sender_acc = null;
                                                                assert bankAccount != null;
                                                                String val = bankAccount.getAccount_No();
                                                                if (val.equals(receiver_account)) {
                                                                    balance[0] = bankAccount.getAccount_balance();
                                                                    float f3 = Float.parseFloat(balance[0]);
                                                                    float f4 = Float.parseFloat(amount);
                                                                    String y = String.valueOf(f3 - f4);
                                                                    String key1 = snap.getKey().toString();
                                                                    //String value = snap.getValue().toString();
                                                                    reff.child(key1).child("account_balance").setValue(y).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Log.d("Receiverr", "receiver ka balance change hua kya " + y);
                                                                        }
                                                                    });

                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
                                                    //add the money to sender's acc -> App Database -> User bank details
                                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot snap : snapshot.getChildren()) {
                                                                UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                                                                assert userBankDetails != null;
                                                                String val = userBankDetails.getAccount_no();
                                                                if (val.equals(lender_account)) {
                                                                    balance[0] = userBankDetails.getBalance();
                                                                    float f1 = Float.parseFloat(balance[0]);
                                                                    assert amount != null;
                                                                    float f2 = Float.parseFloat(amount);
                                                                    String x = String.valueOf(f1 + f2);
                                                                    String key = snap.getKey().toString();
                                                                    //String value = snap.getValue().toString();
                                                                    reference.child(key).child("Balance").setValue(x);

                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                    DatabaseReference references = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");

                                                    references.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot snap : snapshot.getChildren()) {
                                                                UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                                                                assert userBankDetails != null;
                                                                String val = userBankDetails.getAccount_no();
                                                                if (val.equals(receiver_account)) {
                                                                    balance[0] = userBankDetails.getBalance();
                                                                    float f1 = Float.parseFloat(balance[0]);
                                                                    assert amount != null;
                                                                    float f2 = Float.parseFloat(amount);
                                                                    String x = String.valueOf(f1 - f2);
                                                                    String key = snap.getKey().toString();
                                                                    //String value = snap.getValue().toString();
                                                                    reference.child(key).child("Balance").setValue(x);
                                                                    break;
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                    String key = snap.getKey().toString();
                                                    FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(key).child("status").setValue("Returned");
                                                    FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(lender_uid).addListenerForSingleValueEvent(
                                                            new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    String phn = snapshot.child("mobile").getValue(String.class);
                                                                    String name = snapshot.child("fname").getValue(String.class) + " " + snapshot.child("lname").getValue(String.class);
                                                                    String msg1 = "Dear " + name + ", the amount of INR " + amount + " you had lent to  XXXXXXXXXXXXX" + receiver_account.substring(receiver_account.length() - 3) +
                                                                            " has been returned to your account.";
                                                                    Intent intent = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                                                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                                                                    SmsManager sms = SmsManager.getDefault();
                                                                    sms.sendTextMessage("+91" + phn, null, msg1, pi, null);
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            }
                                                    );

                                                    FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(receiver_uid).addListenerForSingleValueEvent(
                                                            new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                    String phn = snapshot.child("mobile").getValue(String.class);
                                                                    String name = snapshot.child("fname").getValue(String.class) + " " + snapshot.child("lname").getValue(String.class);
                                                                    String msg1 = "Dear " + name + ", the amount of INR " + amount + " you had borrowed from  XXXXXXXXXXXXX" + lender_account.substring(lender_account.length() - 3) +
                                                                            " has been returned to their account.";
                                                                    Intent intent = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                                                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                                                                    SmsManager sms = SmsManager.getDefault();
                                                                    sms.sendTextMessage("+91" + phn, null, msg1, pi, null);
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            }
                                                    );
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
