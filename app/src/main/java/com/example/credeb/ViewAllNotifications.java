package com.example.credeb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.credeb.Adapter.ViewAllNotificationsAdapter;
import com.example.credeb.Model.Peer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAllNotifications extends AppCompatActivity {

    ListView myListView;
    List<Peer> notifyList;
    DatabaseReference reference, reference1;
    Button accept;
    Button reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_notifications);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();

        myListView = findViewById(R.id.myListView);
        accept = findViewById(R.id.accept);
        reject = findViewById(R.id.reject);
        notifyList = new ArrayList<Peer>();

        

        FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notifyList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Peer peer = snap.getValue(Peer.class);
                    if(UID.equals(peer.getLender_userID())){
                        notifyList.add(peer);
                    }
                }
                ViewAllNotificationsAdapter adapter = new ViewAllNotificationsAdapter(ViewAllNotifications.this, notifyList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}