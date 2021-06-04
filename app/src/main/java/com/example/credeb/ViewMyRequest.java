package com.example.credeb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.credeb.Adapter.ViewAllNotificationsAdapter;
import com.example.credeb.Adapter.ViewMyRequestAdapter;
import com.example.credeb.Model.Peer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMyRequest extends AppCompatActivity {

    ListView myListView;
    List<Peer> notifyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_request);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String UID = user.getUid();
        Log.d("UIIID",UID);

        myListView = findViewById(R.id.myListView);
        notifyList = new ArrayList<Peer>();

        FirebaseDatabase.getInstance().getReference().child("App Database").child("Peer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notifyList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Peer peer = snap.getValue(Peer.class);
                    Log.d("UIIIIIID","UID: "+peer.getUserID() + " " + "UIID: " + UID);
                    if(peer.getUserID().equals(UID)){
                        Log.d("UID", "Yooo");
                        notifyList.add(peer);
                    }
                }
                ViewMyRequestAdapter adapter = new ViewMyRequestAdapter(ViewMyRequest.this, notifyList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}