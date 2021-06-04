package com.example.credeb.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.credeb.Model.Peer;
import com.example.credeb.Model.User;
import com.example.credeb.Model.UserBankDetails;
import com.example.credeb.PeerOtpVerify;
import com.example.credeb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewAllNotificationsAdapter extends ArrayAdapter {
    List<Peer> notifyList;
    private Activity mContext;

    public ViewAllNotificationsAdapter(Activity mContext, List<Peer> notifyList) {
        super(mContext, R.layout.notif_list_view, notifyList);
        this.mContext = mContext;
        this.notifyList = notifyList;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.notif_list_view, null, true);
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
        Button accept = listItemView.findViewById(R.id.accept);
        Button reject = listItemView.findViewById(R.id.reject);
        Button transfer = listItemView.findViewById(R.id.tranfer);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String row_id = id.getText().toString();
                row_id = row_id.substring(4);
                Log.d("IDD", "ID IS: " + row_id);
                String finalRow_id = row_id;
                String finalRow_id1 = row_id;
                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(row_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Peer peerr = snapshot.getValue(Peer.class);
                        String acc = peerr.getLender_accountNo();
                        String receiver_uid = peerr.getUserID();
                        String lender_name = peerr.getLender_name();
                        FirebaseDatabase.getInstance().getReference().child("App Database").child("User bank details").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    UserBankDetails userBankDetails = snap.getValue(UserBankDetails.class);
                                    if (userBankDetails.getUserID().equals(peerr.getLender_userID()) && userBankDetails.getAccount_no().equals(peerr.getLender_accountNo())) {
                                        String balance = userBankDetails.getBalance();
                                        float bal = Float.parseFloat(balance);
                                        float half = (bal * 50) / 100;
                                        float amt = Float.parseFloat(peerr.getAmount());
                                        if (Float.compare(half, amt) > 0) {
                                            FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(finalRow_id).child("status").setValue("Accepted");
                                            //status.setText("Status: Accepted");
                                            FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(finalRow_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String s = snapshot.child("status").getValue(String.class);
                                                    status.setText("Status: " + s);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                            accept.setVisibility(accept.GONE);
                                            reject.setVisibility(reject.GONE);
                                            transfer.setVisibility(transfer.VISIBLE);
                                            FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(receiver_uid).addListenerForSingleValueEvent(
                                                    new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            String phn = snapshot.child("mobile").getValue(String.class);
                                                            String name = snapshot.child("fname").getValue(String.class) + " " + snapshot.child("lname").getValue(String.class);
                                                            String msg1 = "Dear " + name + ", your loan request for INR " + amt + " sent to " + lender_name + " has been accepted.";
                                                            Intent intent = new Intent(mContext.getApplicationContext(), ViewAllNotificationsAdapter.class);
                                                            PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);
                                                            SmsManager sms = SmsManager.getDefault();
                                                            sms.sendTextMessage("+91" + phn, null, msg1, pi, null);
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    }
                                            );

                                        } else {
                                            Toast.makeText(mContext, "Balance insufficient", Toast.LENGTH_SHORT).show();
                                            FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(finalRow_id1).child("status").setValue("Rejected");
                                            //status.setText("Status: Rejected");
                                            FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(finalRow_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String s = snapshot.child("status").getValue(String.class);
                                                    status.setText("Status: " + s);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                            accept.setVisibility(accept.GONE);
                                            reject.setVisibility(reject.GONE);
                                            transfer.setVisibility(transfer.GONE);
                                            FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(receiver_uid).addListenerForSingleValueEvent(
                                                    new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            String phn = snapshot.child("mobile").getValue(String.class);
                                                            String name = snapshot.child("fname").getValue(String.class) + " " + snapshot.child("lname").getValue(String.class);
                                                            String msg1 = "Dear " + name + ", your loan request for INR " + amt + " sent to " + lender_name + " has been rejected.";
                                                            Intent intent = new Intent(mContext.getApplicationContext(), ViewAllNotificationsAdapter.class);
                                                            PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);
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

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String row_id = id.getText().toString();
                row_id = row_id.substring(4);
                Log.d("IDD", "ID IS: " + row_id);
                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(row_id).child("status").setValue("Rejected");
                //status.setText("Status: Rejected");
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
                accept.setVisibility(accept.GONE);
                reject.setVisibility(reject.GONE);
                transfer.setVisibility(transfer.GONE);
                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(row_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Peer peerr = snapshot.getValue(Peer.class);
                        String acc = peerr.getLender_accountNo();
                        String receiver_uid = peerr.getUserID();
                        String amt = peerr.getAmount();
                        String lender_name = peerr.getLender_name();
                        FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(receiver_uid).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String phn = snapshot.child("mobile").getValue(String.class);
                                        String name = snapshot.child("fname").getValue(String.class) + " " + snapshot.child("lname").getValue(String.class);
                                        String msg1 = "Dear " + name + ", your loan request for INR " + amt + " sent to " + lender_name + " has been rejected.";
                                        Intent intent = new Intent(mContext.getApplicationContext(), ViewAllNotificationsAdapter.class);
                                        PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);
                                        SmsManager sms = SmsManager.getDefault();
                                        sms.sendTextMessage("+91" + phn, null, msg1, pi, null);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                }
                        );
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String row_id = id.getText().toString();
                row_id = row_id.substring(4);
                Log.d("IDD", "ID IS: " + row_id);

                FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").child(row_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Peer peer = snapshot.getValue(Peer.class);
                        final String[] rec_mobile = new String[1];
                        final String[] sender_mobile = new String[1];
                        final String[] rec_fname = new String[1];
                        final String[] rec_lname = new String[1];
                        FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(peer.getLender_userID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user1 = snapshot.getValue(User.class);
                                sender_mobile[0] = user1.getMobile();
                                Log.d("LENder", "YO sender");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").child(peer.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user2 = snapshot.getValue(User.class);
                                rec_mobile[0] = user2.getMobile();
                                rec_fname[0] = user2.getFname();
                                rec_lname[0] = user2.getLname();
                                Log.d("LENder", "Yo rec " + sender_mobile[0]);
                                String bank;
                                if (peer.getLender_ifsc().substring(0, 5).equals("BANK1")) {
                                    bank = "Bank1";
                                } else {
                                    bank = "Bank2";
                                }
                                Intent i = new Intent(mContext, PeerOtpVerify.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("Amount to be transferred", peer.getAmount());
                                bundle.putString("Receiver's account number", peer.getRec_accountNo());
                                bundle.putString("Receiver's IFSC", peer.getRec_ifsc());
                                bundle.putString("UID", peer.getLender_userID());
                                bundle.putString("Sender's acc num", peer.getLender_accountNo());
                                bundle.putString("Sender bank name", peer.getLender_bank());
                                bundle.putString("Sender mobile", sender_mobile[0]);
                                bundle.putString("Receiver's Bank name", peer.getRec_bank());
                                bundle.putString("Receiver phone number", rec_mobile[0]);
                                bundle.putString("Receiver Fname", rec_fname[0]);
                                bundle.putString("Receiver Lname", rec_lname[0]);
                                String row_id = id.getText().toString();
                                row_id = row_id.substring(4);
                                bundle.putString("Row id", row_id);
                                Log.d("Lenderr", "Details: " + peer.getAmount() + " " + peer.getRec_accountNo() + " " + peer.getLender_userID() + " " + peer.getLender_accountNo() + " " + bank);

                                i.putExtras(bundle);
                                mContext.startActivity(i);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Log.d("LENder", "outside");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        FirebaseDatabase.getInstance().getReference().child("App Database").child("User details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snaap : snapshot.getChildren()) {
                    User user = snaap.getValue(User.class);
                    Peer peer = notifyList.get(position);
                    if (user.getId().equals(peer.getUserID())) {
                        if (peer.getStatus().equals("Pending")) {
                            lender_name.setText("Requester Name: " + user.getFname() + " " + user.getLname());
                            amount.setText("Amount: " + peer.getAmount());
                            account_no.setText("Account No: " + peer.getRec_accountNo());
                            date.setText("Return Date: " + peer.getReturn_date());
                            id.setText("ID: " + peer.getId());
                            status.setText("Status: " + peer.getStatus());
                            transfer.setVisibility(transfer.GONE);
                        } else if (peer.getStatus().equals("Accepted")) {
                            lender_name.setText("Requester Name: " + user.getFname() + " " + user.getLname());
                            amount.setText("Amount: " + peer.getAmount());
                            account_no.setText("Account No: " + peer.getRec_accountNo());
                            date.setText("Return Date: " + peer.getReturn_date());
                            id.setText("ID: " + peer.getId());
                            status.setText("Status: " + peer.getStatus());
                            accept.setVisibility(accept.GONE);
                            reject.setVisibility(reject.GONE);
                        } else {
                            lender_name.setText("Requester Name: " + user.getFname() + " " + user.getLname());
                            amount.setText("Amount: " + peer.getAmount());
                            account_no.setText("Account No: " + peer.getRec_accountNo());
                            date.setText("Return Date: " + peer.getReturn_date());
                            id.setText("ID: " + peer.getId());
                            status.setText("Status: " + peer.getStatus());
                            accept.setVisibility(accept.GONE);
                            reject.setVisibility(reject.GONE);
                            transfer.setVisibility(transfer.GONE);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return listItemView;
    }


}
