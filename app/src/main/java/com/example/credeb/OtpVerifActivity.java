//package com.example.credeb;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.credeb.databinding.ActivityMainBinding;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//import com.google.android.gms.tasks.OnSuccessListener;
//import java.util.concurrent.TimeUnit;
//
//public class OtpVerifActivity extends AppCompatActivity {
//
//    private ActivityMainBinding binding;
//    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks onCallback;
//    private String verifId;
//    private static final String TAG = "MAIN_TAG";
//    private FirebaseAuth auth;
//    private ProgressDialog progressDialog;
//    EditText phone_num, code_num;
//    Button continueButton, codeSubmitButton;
//    TextView resendCode;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //binding = ActivityMainBinding.inflate(getLayoutInflater());
//        //binding.getRoot()
//        setContentView(R.layout.activity_otp_verif);
//
//        auth = FirebaseAuth.getInstance();
//        phone_num = findViewById(R.id.user_phone_no);
//        code_num = findViewById(R.id.code);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Please wait");
//        progressDialog.setCanceledOnTouchOutside(false);
//
//        onCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                //signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                progressDialog.dismiss();
//                Toast.makeText(OtpVerifActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String verif_Id, @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                super.onCodeSent(verif_Id, forceResendingToken);
//                Log.d(TAG, "onCodeSent: "+verif_Id);
//                verifId = verif_Id ;
//                forceResendingToken = token;
//                progressDialog.dismiss();
//
//                //hide show
//                Toast.makeText(OtpVerifActivity.this, "Verification code sent", Toast.LENGTH_SHORT).show();
//            }
//        };
//
//
//        continueButton = findViewById(R.id.phoneContinueBtn);
//        continueButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String phoneNumber = phone_num.getText().toString().trim();
//                if(TextUtils.isEmpty(phoneNumber)){
//                    Toast.makeText(OtpVerifActivity.this, "Please enter phone number.", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                   startPhoneNumberVerification(phoneNumber);
//                }
//            }
//        });
//
//        resendCode = findViewById(R.id.resend_code);
//        resendCode.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                String phoneNumber = phone_num.getText().toString().trim();
//                if(TextUtils.isEmpty(phoneNumber)){
//                    Toast.makeText(OtpVerifActivity.this, "Please enter phone number.", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    resendVerificationCode(phoneNumber, forceResendingToken);
//                }
//            }
//
//
//        });
//
//        codeSubmitButton = findViewById(R.id.codeSubmitBtn);
//        codeSubmitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String codeNumber = code_num.getText().toString().trim();
//                if(TextUtils.isEmpty(codeNumber)){
//                    Toast.makeText(OtpVerifActivity.this, "Please enter OTP.", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    verifyPhoneNumberWithCode(verifId, codeNumber);
//                }
//            }
//        });
//    }
//
//
//
//    private void startPhoneNumberVerification(String phoneNumber){
//
//        progressDialog.setMessage("Verifying phone number");
//        progressDialog.show();
//
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber)
//                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(onCallback).build();
//
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//
//    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token){
//        progressDialog.setMessage("Resending verification code");
//        progressDialog.show();
//
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber)
//                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(onCallback).setForceResendingToken(token)
//                .build();
//
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//
//
//    private void verifyPhoneNumberWithCode(String verifId, String verif_code) {
//        progressDialog.setMessage("Verifying code");
//        progressDialog.show();
//
//        //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifId, verif_code);
//        //signInWithPhoneAuthCredential(credential);
//    }
//
//
//    }
//
//


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
    EditText verif_num, phone_num;
    TextView resend_code;
    private ProgressDialog pd;
    //String phone_num;
    //uncomment this for db mobile
    //String phone_num;
    String ac_num;
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


        phone_num = findViewById(R.id.phoneNumber);
        //uncomment this for db mobile
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("App Database").child("User details");
//        final String[] phoneNumber = new String[1];
//        final String[] accNum = new String[1];
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    User userDetails = snap.getValue(User.class);
//                    assert userDetails != null;
//                    String val = userDetails.getId();
//                    if (val.equals(user_id)) {
//                        phoneNumber[0] = userDetails.getMobile();
//                        phone_num = phoneNumber[0];
//                        Log.d("phone", "mobile no " + phone_num);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
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
                String phone = phone_num.getText().toString().trim();
                //uncomment this for db mobile
                //String phone = "+91" + phone_num;
                Log.d("phone", "mobile " + phone);
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(OtpVerifActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    sendCode(phone);
                }
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
                String phone = phone_num.getText().toString().trim();
                //uncomment this for db mobile
                // String phone = "+91" + phone_num;
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(OtpVerifActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    resendVerificationCode(phone, forceResendingToken);
                }
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

                    Bundle bundle = getIntent().getExtras();
                    assert bundle != null;
                    String amount_to_be_transferred = bundle.getString("Amount to be transferred");
                    String receivers_acc_num = bundle.getString("Receiver's account number");
                    String receiver_ifsc = bundle.getString("Receiver's IFSC");
                    String sender_acc_num = bundle.getString("Sender's acc num");
                    String user_id = bundle.getString("UID");


                    Log.d("verif", "rece acc " + receivers_acc_num);
                    Log.d("verif", "rece ifsc " + receiver_ifsc);
                    Log.d("verif", "amount " + amount_to_be_transferred);
                    Log.d("verif", "uid  " + user_id);


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
                    bundle1.putString("Sender's acc num", accountnumber);
                    bundle1.putString("UID", user_id);
                    Log.d("verif", "sender acc chalna chahiye " + sender_acc_num);
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

        Toast.makeText(OtpVerifActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
        pd.setMessage("Resending verification code");
        pd.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallbacks).setForceResendingToken(forceResendingToken)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}
