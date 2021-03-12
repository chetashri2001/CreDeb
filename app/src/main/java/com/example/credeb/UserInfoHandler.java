package com.example.credeb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import androidx.core.content.ContextCompat;

public class UserInfoHandler extends DatabaseHelper {

    public UserInfoHandler(Context context){
        super(context);
    }

    public boolean create(User user){
        ContentValues values=new ContentValues();

       values.put("fname", user.getFname());
       values.put("lname", user.getLname());
       values.put("email", user.getEmail());
       values.put("mobile", user.getMobile());

       SQLiteDatabase db = this.getWritableDatabase();
       
       boolean isSuccess = db.insert("USER_INFO", null, values)>0;
       db.close();
       return isSuccess;
    }
}


