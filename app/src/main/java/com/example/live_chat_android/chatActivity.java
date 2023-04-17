package com.example.live_chat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chatActivity extends AppCompatActivity {

    private EditText message;

    private String displayName;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message=(EditText)findViewById(R.id.chatInput);

        setUpDisplayname();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }
    public void sendMessage(View v){
        instantMessage chats=new instantMessage(displayName,message.getText().toString());
        mDatabase.child("users").child("message").setValue(chats);
        message.setText("");
    }

    private void setUpDisplayname(){

        SharedPreferences prefs=getSharedPreferences(registerActivity.PREF_KEY,MODE_PRIVATE);
         displayName= prefs.getString(registerActivity.USER_ID, null);

         if(displayName==null)displayName="Anomy";
        Log.d("livechat",displayName);
    }

}