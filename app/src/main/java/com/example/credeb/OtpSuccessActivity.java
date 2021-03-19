package com.example.credeb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;


public class OtpSuccessActivity extends AppCompatActivity {
        Button btnProceed, btnDeny;
        int i=0;
    protected boolean _active = true;
    protected int _splashTime = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_success);

        btnProceed = findViewById(R.id.button_proceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtpSuccessActivity.this);
                builder.setTitle("Money sent!")
                        .setMessage("Rs. 5000.00 is sent to Chetashri Mahajan.")
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
        btnDeny = findViewById(R.id.button_deny);
        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtpSuccessActivity.this);
                builder.setTitle("Transaction cancelled")
                        .setMessage("Rs. 5000.00 was not transferred to Chetashri Mahajan.")
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
}
