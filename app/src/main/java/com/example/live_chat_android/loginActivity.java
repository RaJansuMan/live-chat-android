package com.example.live_chat_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    private EditText userView;
    private EditText passwordView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userView=(EditText) findViewById(R.id.emailTI);
        passwordView=(EditText) findViewById(R.id.passwordTI);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


    }

    public void signINExistingUser(View v){
        attempSignin();
    }
    public void registernewUser(View v){
        Intent toregis=new Intent(loginActivity.this,registerActivity.class);
        startActivity(toregis);
    }
    private void attempSignin(){
        String Email=userView.getText().toString();
        String password=passwordView.getText().toString();

        mAuth.signInWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("livechat", "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent tochat=new Intent(loginActivity.this,chatActivity.class);
                            finish();
                            startActivity(tochat);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("livechat", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            showErroeDialog("user doesnot exist");                            //updateUI(null);
                        }
                    }
                });
    }

    private void showErroeDialog(String message){
        new AlertDialog.Builder(this)
                .setTitle("oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}