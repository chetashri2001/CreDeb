package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.credeb.Model.BankAccount;
import com.example.credeb.Model.BankUserDetails;
import com.example.credeb.Model.UserBankDetails;
import com.example.credeb.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.example.credeb.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class OtpVerifActivity extends AppCompatActivity {


    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    Button getotp, verifyotp, abort;
    EditText verif_num; //, phone_num;
    TextView resend_code;
    private ProgressDialog pd;
    //String phone_num;
    //uncomment this for db mobile
    String phone_num;
    String ac_num;

    String receiver_bank, sender_bank, reciever_uid, receiver_mobile, receiver_fname, receiver_lname;

    //String userID; //phone_num;
    private FirebaseUser firebaseUser;
    @Override
    public void onBackPressed() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verif);

        firebaseAuth = FirebaseAuth.getInstance();
        assert firebaseUser != null;
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        userID = firebaseUser.getUid();
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait");
        abort = findViewById(R.id.abortBt);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String user_id = bundle.getString("UID");
        String receivers_acc_num = bundle.getString("Receiver's account number");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("App Database").child("User details");
        FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                    String acc = userBankDetails.getAccount_no();
                    if(receivers_acc_num.equals(acc)){
                        receiver_bank = userBankDetails.getB_name();
                        reciever_uid = userBankDetails.getUserID();
                        ref.child(reciever_uid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                assert user != null;
                                receiver_mobile = user.getMobile();
                                receiver_fname = user.getFname();
                                receiver_lname = user.getLname();
                                Log.d("receiver", "Mobile no receiver inside " + receiver_mobile);
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

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                dothenextThing();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(OtpVerifActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "onCodeSent :" + s);
                mVerificationId = s;
                forceResendingToken = token;
                pd.dismiss();

                Toast.makeText(OtpVerifActivity.this, "Verification Code Sent", Toast.LENGTH_SHORT).show();

            }
        };


        //phone_num = findViewById(R.id.phoneNumber);
        //uncomment this for db mobile
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User details");
        final String[] phoneNumber = new String[1];
        final String[] accNum = new String[1];
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User userDetails = snap.getValue(User.class);
                    assert userDetails != null;
                    String val = userDetails.getId();
                    if (val.equals(user_id)) {
                        phoneNumber[0] = userDetails.getMobile();
                        phone_num = phoneNumber[0];
                        Log.d("phone", "mobile no " + phone_num);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OtpVerifActivity.this, DisplayMenuActivity.class);
                startActivity(i);
            }
        });
        getotp = findViewById(R.id.GETOTP);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String phone = phone_num.getText().toString().trim();
                //uncomment this for db mobile
                String phone = "+91" + phone_num;
                Log.d("phone", "mobile " + phone);
                sendCode(phone);
//                if (TextUtils.isEmpty(phone)) {
//                    Toast.makeText(OtpVerifActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
//                } else {
//                    sendCode(phone);
//                }
            }
        });
        verifyotp = findViewById(R.id.verifyBt);
        verif_num = findViewById(R.id.Verification);
        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = verif_num.getText().toString().trim();
                String accountnumber = "" + ac_num;
                Log.d("VERIF ID", "dekhu toh " + accountnumber);
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(OtpVerifActivity.this, "Enter Code Number", Toast.LENGTH_SHORT).show();
                } else {

                    verifyTheCode(mVerificationId, code, accountnumber);
                }
            }
        });
        resend_code = findViewById(R.id.resend_code);
        resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String phone = phone_num.getText().toString().trim();
                //uncomment this for db mobile
                String phone = "+91" + phone_num;
                resendVerificationCode(phone, forceResendingToken);
//                if (TextUtils.isEmpty(phone)) {
//                    Toast.makeText(OtpVerifActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
//                } else {
//                    resendVerificationCode(phone, forceResendingToken);
//                }
            }
        });
    }

    private void verifyTheCode(String mVerificationId, String code, String accountnumber) {

        Log.d("VERIF ID", "verif id " + mVerificationId + " code " + code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(OtpVerifActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d("Reciever", "Reciever bank name is inside "+receiver_bank);
                    Bundle bundle = getIntent().getExtras();
                    assert bundle != null;
                    String amount_to_be_transferred = bundle.getString("Amount to be transferred");
                    String receivers_acc_num = bundle.getString("Receiver's account number");
                    String receiver_ifsc = bundle.getString("Receiver's IFSC");
                    String sender_acc_num = bundle.getString("Sender's account number");
                    String sender_bank = bundle.getString("Sender bank name");
                    String user_id = bundle.getString("UID");
                    String sender_mobile = bundle.getString("Sender mobile");


                    Log.d("verif", "rece acc " + receivers_acc_num);
                    Log.d("verif", "rece ifsc " + receiver_ifsc);
                    Log.d("verif", "amount " + amount_to_be_transferred);
                    Log.d("verif", "uid  " + user_id);
                    Log.d("verif", "send acc  " + sender_acc_num);
                    Log.d("verif", "send mobile  " + sender_mobile);


                    // String user_id = bundle.getString("UID");

//                    //verification successful we will start the profile activity
////                    Intent intent = new Intent(OtpVerifActivity.this, OtpSuccessActivity.class);
////                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                    startActivity(intent);
                    Intent i = new Intent(OtpVerifActivity.this, OtpSuccessActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("Amount to be transferred", amount_to_be_transferred);
                    bundle1.putString("Receiver's account number", receivers_acc_num);
                    bundle1.putString("Receiver's IFSC", receiver_ifsc);
                    bundle1.putString("Sender's acc num", sender_acc_num);
                    bundle1.putString("Receiver's Bank name", receiver_bank);
                    bundle1.putString("Sender bank name", sender_bank);
                    bundle1.putString("Receiver phone number", receiver_mobile);
                    bundle1.putString("Receiver Fname", receiver_fname);
                    bundle1.putString("Receiver Lname", receiver_lname);
                    bundle1.putString("Sender mobile", sender_mobile);
                    bundle1.putString("Source", "OtpVerif");
                    bundle1.putString("UID", user_id);



                    Log.d("verif", "sender acc chalna chahiye " + sender_acc_num);
                    Log.d("receiver", "Receiver mobile no outside " + receiver_mobile);
                    i.putExtras(bundle1);
                    startActivity(i);

                } else {

                    //verification unsuccessful.. display an error message
                    Toast.makeText(OtpVerifActivity.this, "Verification failed.", Toast.LENGTH_SHORT).show();
                    String message = "Something is wrong, we will fix it soon.";

//                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                        Toast.makeText(OtpVerifActivity.this, "Verification failed.", Toast.LENGTH_SHORT).show();
//                    }

                }
            }
        });


    }


    private void dothenextThing() {

        Toast.makeText(OtpVerifActivity.this, "Verification Code Received", Toast.LENGTH_SHORT).show();
        pd.dismiss();
    }

    private void sendCode(String phone) {
        pd.setMessage("Verifying Phone Number");
        pd.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        pd.setMessage("Resending Verification Code");
        pd.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallbacks).setForceResendingToken(forceResendingToken)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}
