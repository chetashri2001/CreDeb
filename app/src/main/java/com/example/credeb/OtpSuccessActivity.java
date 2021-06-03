package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.credeb.Adapter.ViewTransactionHistoryAdapter;
import com.example.credeb.Model.BankAccount;
import com.example.credeb.Model.BankUserDetails;
import com.example.credeb.Model.TransactionHistory;
import com.example.credeb.Model.User;
import com.example.credeb.Model.UserBankDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class OtpSuccessActivity extends AppCompatActivity {


    Button btnProceed, btnDeny;
    EditText pin;
    int i = 0;
    protected boolean _active = true;
    protected int _splashTime = 1000;
    String userID, sender_acc_no;
    private FirebaseAuth mAuth;
    TextView amountDisp;
    static String id1, id2, title;
    ArrayList itemList;
    Float sender_init, rece_init, sender_fin, receiver_fin;
    TextView receiverDisp1, receiverDisp2;
    TextView senderDisp;
    ArrayList<String> questionArrrayList = new ArrayList<>();
    //List notificationList;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_success);
        questionArrrayList.clear();
        ActivityCompat.requestPermissions(this,new String[]{
                        Manifest.permission.SEND_SMS},
                1);
        //  mAuth = FirebaseAuth.getInstance();
        //userID = mAuth.getCurrentUser().getUid();
        Log.d("otp success", "uid  " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        final String[] sender_account_num = new String[1];
        Bundle bundle1 = getIntent().getExtras();
        assert bundle1 != null;
        receiverDisp1 = findViewById(R.id.textViewReceiverAcc);
        receiverDisp2 = findViewById(R.id.textViewReceiverName);
        pin = findViewById(R.id.textView);
        //notificationList = new ArrayList<>();
//a        senderDisp = findViewById(R.id.textViewSender);
        String amount_no = bundle1.getString("Amount to be transferred");
        String receiver_acc_no = bundle1.getString("Receiver's account number");
        String receiver_ifsc = bundle1.getString("Receiver's IFSC");
        String userID = bundle1.getString("UID");
        String sender = bundle1.getString("Sender's acc num");
        String receiver_bank = bundle1.getString("Receiver's Bank name");
        String sender_bank = bundle1.getString("Sender bank name");
        String receiver_mobile = bundle1.getString("Receiver phone number");
        String receiver_fname = bundle1.getString("Receiver Fname");
        String receiver_lname = bundle1.getString("Receiver Lname");
        String sender_mobile = bundle1.getString("Sender mobile");
        String source = bundle1.getString("Source");
        Log.d("Sender", "sender ka bank name" + sender_bank);

        Log.d("acc", "get uid in otp success shud work " + userID);
        Log.d("acc", "get uid in otp success " + userID);
        String rec =receiver_fname + " "+receiver_lname;
        receiverDisp1.setText(receiver_acc_no);
        receiverDisp2.setText(rec);

//        map.put("transaction_ID","2");
//        map.put("cust_ID","80000002");
//        map.put("cust_ID2","80000001");
//        map.put("date","25-12-2019");
//        map.put("time","08:08:15");
//        map.put("type","Debit");
//        map.put("amount","500");
//        FirebaseDatabase.getInstance().getReference().child("Bank1").child("User Transaction details").push().setValue(map);
//        map.clear();

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("Type_of_transaction", "Debit");
//        map.put("Amount", 1000);
//        map.put("Date_and_Time", strDate);
//        map.put("Sender", accountNumber[0]);
//        map.put("Receiver", receiver_acc_no);
//        map.put("Receiver_IFSC", receiver_ifsc);
//        FirebaseDatabase.getInstance().getReference().child("Bank1").child("User Transaction details").push().setValue(map);
//        map.clear();
        Date c = Calendar.getInstance().getTime();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println("Current time => " + c);
        itemList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        Log.d("acc", "time print " + c);
        Log.d("acc", "date print " + formattedDate);
        btnProceed = findViewById(R.id.button_proceed);
        amountDisp = findViewById(R.id.textViewAmount);
        amountDisp.setText(amount_no);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userpin = pin.getText().toString();
                if(TextUtils.isEmpty(userpin)){
                    pin.setError("Enter Pin");
                    return;
                }
                FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userr = snapshot.getValue(User.class);
                        if(userpin.equals(userr.getPin()))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(OtpSuccessActivity.this);
                            builder.setTitle("Transaction successful")
                                    .setMessage("Rs. " + amount_no + " sent to " + receiver_acc_no + ".")
                                    .setCancelable(false).setCancelable(false)
                                    .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //this for skip dialog
                                            dialog.cancel();
                                        }
                                    });
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (alertDialog.isShowing()) {
                                        alertDialog.dismiss();
                                    }
                                }
                            }, 3500);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
                            final String[] accountNumber = new String[1];
                            final String[] balance = new String[1];
                            final String[] cust_ids = new String[2];
                            final String[] phoneNumbers = new String[2];
                            final String[] names = new String[4];
                            //GET THE SENDER'S ACCOUNT NUMBER
                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
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
                                        String acc = userBankDetails.getAccount_no();
                                        Log.d("acc", "get val " + val);
                                        Log.d("acc no", "get val... " + acc);
                                        if (val.equals(userID) && acc.equals(sender)) {
                                            Log.d("BOHOT HO GAYA", "uid " + val);
                                            accountNumber[0] = userBankDetails.getAccount_no();
                                            sender_account_num[0] = accountNumber[0];
                                            sender_acc_no = accountNumber[0];
                                            accountNumber[0] = sender;
                                            String key = snap.getKey().toString();
                                            HashMap<String, Object> map = new HashMap<>();
                                            map.put("Type_of_transaction", "Debit");
                                            map.put("Amount", amount_no);
                                            map.put("Date_and_Time", strDate);
                                            map.put("user1", accountNumber[0]);
                                            map.put("user2", receiver_acc_no);
                                            map.put("user2_ifsc", receiver_ifsc);
                                            map.put("user1_ifsc", ifsc);
                                            FirebaseDatabase.getInstance().getReference().child(sender_bank).child("User Transaction details").push().setValue(map);
                                            map.clear();

                                            map.put("Type_of_transaction", "Credit");
                                            map.put("Amount", amount_no);
                                            map.put("Date_and_Time", strDate);
                                            map.put("user2", accountNumber[0]);
                                            map.put("user1", receiver_acc_no);
                                            map.put("user2_ifsc", ifsc);
                                            map.put("user1_ifsc", receiver_ifsc);
                                            //FirebaseDatabase.getInstance().getReference().child("Bank"+receiver_ifsc.substring(4,5)).child("User Transaction details").push().setValue(map);
                                            FirebaseDatabase.getInstance().getReference().child(receiver_bank).child("User Transaction details").push().setValue(map);
                                            map.clear();
                                            Log.d("acc", "acc hi title" + title);
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Log.d("sender", "question array list " + questionArrrayList);
                            //deduct the money from sender's acc -> sender_bank -> Bank account details
                            //add money to receiver's acc -> receiver bank -> Bank account details
                            final Float[] sender_float = new Float[2];
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(sender_bank).child("Bank Account details");
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snap : snapshot.getChildren()) {
                                        BankAccount bankAccount = snap.getValue(BankAccount.class);
                                        final String sender_acc = null;
                                        assert bankAccount != null;
                                        Log.d("sender", "acc " + amount_no);
                                        String val = bankAccount.getAccount_No();
                                        if (val.equals(accountNumber[0])) {
                                            Log.d("sender", "account num diff in" + accountNumber[0]);
                                            Log.d("sender", "acc " + amount_no);
                                            balance[0] = bankAccount.getAccount_balance();
                                            float f1 = Float.parseFloat(balance[0]);
                                            assert amount_no != null;
                                            float f2 = Float.parseFloat(amount_no);
                                            sender_float[0] = f1;
                                            sender_float[1] = f2;
                                            if (Float.compare(f1, f2) >= 0) {
                                                String x = String.valueOf(f1 - f2);
                                                String key = snap.getKey().toString();
                                                //String value = snap.getValue().toString();
                                                ref.child(key).child("account_balance").setValue(x);
                                                Log.d("Senderr", "Sender ka balanve change hua kya " + x);
                                                cust_ids[0] = bankAccount.getCust_ID();
                                                Log.d("acc", "custid 1 " + cust_ids[0]);
                                                id1 = cust_ids[0];
                                            }
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
                                        Log.d("sender", "Rec acc " + receiver_acc_no);
                                        String val = bankAccount.getAccount_No();
                                        Log.d("sender", "Sender float "+sender_float[0]+" "+sender_float[1]);
                                        if (val.equals(receiver_acc_no) && Float.compare(sender_float[0], sender_float[1]) >= 0) {
                                            balance[0] = bankAccount.getAccount_balance();
                                            float f3 = Float.parseFloat(balance[0]);
                                            float f4 = Float.parseFloat(amount_no);
                                            String y = String.valueOf(f3 + f4);
                                            String key1 = snap.getKey().toString();
                                            //String value = snap.getValue().toString();
                                            reff.child(key1).child("account_balance").setValue(y).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Log.d("Receiverr", "receiver ka balance change hua kya " + y);
                                                }
                                            });

                                            cust_ids[1] = bankAccount.getCust_ID();
                                            id2 = cust_ids[1];
                                            Log.d("acc", "custid 2 " + cust_ids[1]);

                                            if (Float.compare(f3, f3 + f4) < 0 && source.equals("OtpVerif")) {
                                                //0123456789101112 => XXXXXXXXXXXXX112
                                                Log.d("float", "bal");
                                                String msg1 = sender_bank + "\nINR " + amount_no + " have been debited from your account XXXXXXXXXXXXX" + accountNumber[0].substring(accountNumber[0].length() - 3) +
                                                        " on " + strDate;
                                                Intent intent = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                                                //Get the SmsManager instance and call the sendTextMessage method to send message
                                                SmsManager sms = SmsManager.getDefault();
                                                sms.sendTextMessage("+91"+sender_mobile, null, msg1, pi, null);

                                                String msg2 = receiver_bank + "\nINR " + amount_no + " have been credited to your account XXXXXXXXXXXXX" + receiver_acc_no.substring(receiver_acc_no.length() - 3) +
                                                        " on " + strDate;
                                                Intent intent1 = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                                PendingIntent pi1 = PendingIntent.getActivity(getApplicationContext(), 0, intent1, 0);

                                                //Get the SmsManager instance and call the sendTextMessage method to send message
                                                SmsManager sms1 = SmsManager.getDefault();
                                                sms1.sendTextMessage("+91"+receiver_mobile, null, msg2, pi1, null);
                                                showNotification(receiver_acc_no, amount_no);
                                            }

                                            if (Float.compare(f3, f3 + f4) < 0 && source.equals("PeerOtpVerif")) {
                                                String row_id = bundle1.getString("Row id");
                                                assert row_id != null;
                                                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(row_id).child("status").setValue("Transferred");
                                                //0123456789101112 => XXXXXXXXXXXXX112
                                                Log.d("float", "bal");
                                                String msg1 = "Loan of INR " + amount_no + " has been sent from your account XXXXXXXXXXXXX" + accountNumber[0].substring(accountNumber[0].length() - 3) +
                                                        " to the account XXXXXXXXXXXXX"+ receiver_acc_no.substring(receiver_acc_no.length() - 3)+ " on " + strDate;
                                                Intent intent = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                                                //Get the SmsManager instance and call the sendTextMessage method to send message
                                                SmsManager sms = SmsManager.getDefault();
                                                sms.sendTextMessage("+91"+sender_mobile, null, msg1, pi, null);

                                                String msg2 = "Loan of INR " + amount_no + " has been sent to your account XXXXXXXXXXXXX" + receiver_acc_no.substring(receiver_acc_no.length() - 3) +
                                                        " from the account XXXXXXXXXXXXX"+ accountNumber[0].substring(accountNumber[0].length() - 3) +" on " + strDate;
                                                Intent intent1 = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                                PendingIntent pi1 = PendingIntent.getActivity(getApplicationContext(), 0, intent1, 0);

                                                //Get the SmsManager instance and call the sendTextMessage method to send message
                                                SmsManager sms1 = SmsManager.getDefault();
                                                sms1.sendTextMessage("+91"+receiver_mobile, null, msg2, pi1, null);

                                            }
                                        }




                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Log.d("acc", "sender cha acc num 1 " + sender_init);
                            Log.d("acc", "sender cha acc num 2 " + sender_fin);
                            Log.d("acc", "custid 1 " + cust_ids[0]);
                            Log.d("acc", "custid 2 " + cust_ids[1]);
                            //deduct the money from sender's acc -> App Database -> User bank details
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snap : snapshot.getChildren()) {
                                        UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                                        assert userBankDetails != null;
                                        String val = userBankDetails.getAccount_no();
                                        if (val.equals(sender)) {
                                            balance[0] = userBankDetails.getBalance();
                                            float f1 = Float.parseFloat(balance[0]);
                                            assert amount_no != null;
                                            float f2 = Float.parseFloat(amount_no);
                                            if (Float.compare(f1, f2) >= 0) {
                                                String x = String.valueOf(f1 - f2);
                                                String key = snap.getKey().toString();
                                                //String value = snap.getValue().toString();
                                                reference.child(key).child("Balance").setValue(x);
                                                deduct_receiver(receiver_acc_no, amount_no);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        else {
                            pin.setError("Incorrect Pin");
                            Toast.makeText(OtpSuccessActivity.this, "Pin incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
        btnDeny = findViewById(R.id.button_deny);
        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtpSuccessActivity.this);
                builder.setTitle("Transaction cancelled")
                        .setMessage("Rs. " + amount_no + " not sent to " + receiver_acc_no + ".")
                        .setCancelable(false).setCancelable(false)
                        .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //this for skip dialog
                                dialog.cancel();
                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    }
                }, 3500); //change 5000 with a specific time you want
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent back = new Intent(OtpSuccessActivity.this, DisplayMenuActivity.class);
                        startActivity(back);
                    }

                }, 4000L);
            }

        });


    }

    public void showNotification(String receiver_acc_no, String amount_no) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        //   notificationList.clear();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Transaction successful! ")
                .setContentText(receiver_acc_no + " has been credited INR " + amount_no);
        // notificationList.add(receiver_acc_no+ " has been credited INR " +amount_no);
        //ViewTransactionHistoryAdapter adapter = new ViewTransactionHistoryAdapter(OtpSuccessActivity.this,  notificationList);
        //myListView.setAdapter(adapter);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Intent intent = new Intent(this, MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        assert notificationManager != null;
        notificationManager.notify(notificationId, mBuilder.build());
    }

    public void deduct_receiver(String receiver_acc_no, String amount_no) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details");
        final String[] balance = new String[1];

        //add the money to receiver's acc -> App Database -> User bank details
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                    assert userBankDetails != null;
                    String val = userBankDetails.getAccount_no();
                    if (val.equals(receiver_acc_no)) {
                        balance[0] = userBankDetails.getBalance();
                        float f1 = Float.parseFloat(balance[0]);
                        assert amount_no != null;
                        float f2 = Float.parseFloat(amount_no);
                        String x = String.valueOf(f1 + f2);
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

    }

}

