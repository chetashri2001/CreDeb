package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.credeb.Adapter.ViewTransactionHistoryAdapter;
import com.example.credeb.Model.BankAccount;
import com.example.credeb.Model.BankUserDetails;
import com.example.credeb.Model.TransactionHistory;
import com.example.credeb.Model.User;
import com.example.credeb.Model.UserBankDetails;
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
    int i = 0;
    protected boolean _active = true;
    protected int _splashTime = 1000;
    String userID, sender_acc_no;
    private FirebaseAuth mAuth;
    TextView amountDisp;
    static String id1, id2, title;
    ArrayList itemList;
    Float sender_init, rece_init, sender_fin, receiver_fin;
    TextView receiverDisp;
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
        //  mAuth = FirebaseAuth.getInstance();
        //userID = mAuth.getCurrentUser().getUid();
        Log.d("otp success", "uid  " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        final String[] sender_account_num = new String[1];
        Bundle bundle1 = getIntent().getExtras();
        assert bundle1 != null;
        receiverDisp = findViewById(R.id.textViewReceiver);
        //notificationList = new ArrayList<>();
        senderDisp = findViewById(R.id.textViewSender);
        String amount_no = bundle1.getString("Amount to be transferred");
        String receiver_acc_no = bundle1.getString("Receiver's account number");
        String receiver_ifsc = bundle1.getString("Receiver's IFSC");
        String userID = bundle1.getString("UID");
        Log.d("acc", "get uid in otp success shud work " + userID);
        Log.d("acc", "get uid in otp success " + userID);
        receiverDisp.setText(receiver_acc_no);
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
//                Handler mHandler = new Handler();
//                mHandler.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Intent back = new Intent(OtpSuccessActivity.this, DisplayMenuActivity.class);
//                        startActivity(back);
//                    }
//
//                }, 4000L);

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
                            Log.d("acc", "get val " + val);
                            if (val.equals(userID)) {
                                Log.d("BOHOT HO GAYA", "uid " + val);
                                accountNumber[0] = userBankDetails.getAccount_no();
                                sender_account_num[0] = accountNumber[0];
                                sender_acc_no = accountNumber[0];
                                String key = snap.getKey().toString();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("Type_of_transaction", "Debit");
                                map.put("Amount", amount_no);
                                map.put("Date_and_Time", strDate);
                                map.put("Sender", accountNumber[0]);
                                map.put("Receiver", receiver_acc_no);
                                map.put("Receiver_IFSC", receiver_ifsc);
                                FirebaseDatabase.getInstance().getReference().child("Bank1").child("User Transaction details").push().setValue(map);
                                map.clear();
                                map.put("Type_of_transaction", "Credit");
                                map.put("Amount", amount_no);
                                map.put("Date_and_Time", strDate);
                                map.put("Sender", accountNumber[0]);
                                map.put("Receiver", receiver_acc_no);
                                map.put("Sender_IFSC", ifsc);
                                FirebaseDatabase.getInstance().getReference().child("Bank1").child("User Transaction details").push().setValue(map);
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
                //deduct the money from sender's acc -> Bank1 -> Bank account details
                //add money to receiver's acc -> Bank1 -> Bank account details
                final Float[] sender_float = new Float[2];
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Bank1").child("Bank Account details");
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
                                    cust_ids[0] = bankAccount.getCust_ID();
                                    Log.d("acc", "custid 1 " + cust_ids[0]);
                                    id1 = cust_ids[0];
                                }
                            }
                            if (val.equals(receiver_acc_no) && Float.compare(sender_float[0], sender_float[1]) >= 0) {
                                balance[0] = bankAccount.getAccount_balance();
                                float f3 = Float.parseFloat(balance[0]);
                                float f4 = Float.parseFloat(amount_no);
                                String y = String.valueOf(f3 + f4);
                                String key1 = snap.getKey().toString();
                                //String value = snap.getValue().toString();
                                ref.child(key1).child("account_balance").setValue(y);
                                cust_ids[1] = bankAccount.getCust_ID();
                                id2 = cust_ids[1];
                                Log.d("acc", "custid 2 " + cust_ids[1]);

                                if (Float.compare(f3, f3 + f4) < 0) {
                                    //0123456789101112 => XXXXXXXXXXXXX112
                                    Log.d("float", "bal");
                                    String msg1 = "BANK1\nINR " + amount_no + " have been debited from your account XXXXXXXXXXXXX" + accountNumber[0].substring(accountNumber[0].length() - 3) +
                                            " on " + strDate;
                                    Intent intent = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                                    //Get the SmsManager instance and call the sendTextMessage method to send message
                                    SmsManager sms = SmsManager.getDefault();
                                    sms.sendTextMessage("+919867765361", null, msg1, pi, null);

                                    String msg2 = "BANK1\nINR " + amount_no + " have been credited to your account XXXXXXXXXXXXX" + receiver_acc_no.substring(receiver_acc_no.length() - 3) +
                                            " on " + strDate;
                                    Intent intent1 = new Intent(getApplicationContext(), DisplayMenuActivity.class);
                                    PendingIntent pi1 = PendingIntent.getActivity(getApplicationContext(), 0, intent1, 0);

                                    //Get the SmsManager instance and call the sendTextMessage method to send message
                                    SmsManager sms1 = SmsManager.getDefault();
                                    sms1.sendTextMessage("+919819975899", null, msg2, pi1, null);

                                }
                                showNotification(receiver_acc_no, amount_no);

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
                            String val = userBankDetails.getUserID();
                            if (val.equals(userID)) {
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
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


//                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//                                    NotificationChannel channel = new NotificationChannel("myNotif", "myNotif", NotificationManager.IMPORTANCE_DEFAULT);
//                                    NotificationManager manager = getSystemService(NotificationManager.class);
//                                }
//
//                                NotificationCompat.Builder builder = new NotificationCompat.Builder(OtpSuccessActivity.this, "myNotifs")
//                            .setContentTitle("Transaction successful! ").setSmallIcon(R.drawable.ic_baseline_check_24).setAutoCancel(true)
//                                        .setContentText(receiver_acc_no+ " has been credited INR " +amount_no );
//
//                                NotificationManagerCompat manager = NotificationManagerCompat.from(OtpSuccessActivity.this);
//                                manager.notify(111, builder.build());

//                                NotificationManager notificationManager =
//                                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                                String channelId = "my_channel_id";
//                                CharSequence channelName = "My Channel";
//                                int importance = NotificationManager.IMPORTANCE_DEFAULT;
//                                NotificationChannel notificationChannel = new NotificationChannel(channelId,             channelName, importance);
//                                notificationChannel.enableLights(true);
//                                notificationChannel.setLightColor(Color.RED);
//                                notificationChannel.enableVibration(true);
//                                notificationChannel.setVibrationPattern(new long[]{1000, 2000});
//                                notificationManager.createNotificationChannel(notificationChannel);
//
//                                int notifyId = 1;
//
//                                Notification notification = new Notification.Builder(OtpSuccessActivity.this)
//                                        .setContentTitle("My Message")
//                                        .setContentText("My test message!")
//                                        .setSmallIcon(R.drawable.ic_baseline_check_24)
//                                        .setChannel(channelId)
//                                        .build();
//
//                                notificationManager.notify(0, notification);
