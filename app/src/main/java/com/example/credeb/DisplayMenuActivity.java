package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.credeb.Model.User;
import com.example.credeb.Model.UserBankDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.HashMap;

public class DisplayMenuActivity extends AppCompatActivity {
    private FlowingDrawer mDrawer;
    private ImageView menu;
    private LinearLayout ll_view_prof, ll_update_prof, ll_view_bank_acc, notif, logout, about;
    Button transfer, peertopeer, transaction_history, multiple_acc;
    TextView user_name, user_mobile;
    private FirebaseUser firebaseUser;

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
        logout = findViewById(R.id.logout);
        about = findViewById(R.id.about);
        transfer = findViewById(R.id.tranfer);
        user_name = findViewById(R.id.user_name);
        user_mobile = findViewById(R.id.user_mobile);

        transaction_history = findViewById(R.id.transaction_history);
        peertopeer = findViewById(R.id.peer_to_peer);
        multiple_acc = findViewById(R.id.multiple_bank_acc);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();


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
                Intent intent = new Intent(DisplayMenuActivity.this, TransactionActivity.class);
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
                Intent intent = new Intent(DisplayMenuActivity.this, ViewTransactionHistoryActivity.class);
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


//        goToOtpVerif = findViewById(R.id.otpButton);
//        goToOtpVerif.setOnClickListener(new View.OnClickListener()
//
//
//        user_prof_view = findViewById(R.id.user_prof_view);
//
//        user_prof_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DisplayMenuActivity.this, user_profile_view.class);
//                startActivity(intent);
//            }
//        });
//        payment = findViewById(R.id.payment);
//
//        payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DisplayMenuActivity.this, TransactionActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        bankacc = findViewById(R.id.bankacc);
//
//        bankacc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DisplayMenuActivity.this, AddBankActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        viewbank = findViewById(R.id.viewbank);
//        viewbank .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DisplayMenuActivity.this, ViewBank.class);
//                startActivity(intent);
//            }
//        });
//
//
//        peertopeer = findViewById(R.id.peertopeer);
//        peertopeer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DisplayMenuActivity.this, PeertoPeerForm.class);
//                startActivity(intent);
//            }
//        });
    }

//    private void userinfo() {
//        FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        String userID = user.getUid();
//        FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(userID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                assert user != null;
//                if(user.getFname()!=null && user.getLname()!=null) {
//                    String name = user.getFname() + " " + user.getLname();
//                    user_name.setText(name);
//                    user_mobile.setText(user.getMobile());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
