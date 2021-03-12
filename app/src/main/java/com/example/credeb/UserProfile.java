package com.example.credeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserProfile extends RecyclerView.Adapter<UserProfile.UserDetails>{

    ArrayList<User> users;
    Context context;

    public UserProfile(ArrayList<User> arrayList, Context context){
        users= arrayList;
        this.context= context;
    }
    @NonNull
    @Override
    public UserDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_profile, parent, false);
        return new UserDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserDetails holder, int position) {
        holder.fname.setText(users.get(position).getFname());
        holder.lname.setText(users.get(position).getLname());
        holder.mobile.setText(users.get(position).getMobile());
        holder.email.setText(users.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class UserDetails extends RecyclerView.ViewHolder{
        TextView fname,lname,mobile, email;
        public UserDetails(@NonNull View itemView){
            super(itemView);
            fname=itemView.findViewById(R.id.fname);
            lname=itemView.findViewById(R.id.lname);
            mobile=itemView.findViewById(R.id.mobile);
            email=itemView.findViewById(R.id.email);
        }
    }
}
