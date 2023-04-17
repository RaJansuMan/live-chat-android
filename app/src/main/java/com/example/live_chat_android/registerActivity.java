package com.example.live_chat_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {

   static final public String PREF_KEY="ker456884";

    static final public String USER_ID="user113456";

    private EditText userNameView;
    private EditText memailView;
    private EditText passwordView;
    private EditText confirmPasswordView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameView =(EditText) findViewById(R.id.usernameIT);
        memailView =(EditText) findViewById(R.id.emailregisIT);
        passwordView =(EditText) findViewById(R.id.passwordRegisIT);
        confirmPasswordView =(EditText) findViewById(R.id.confirmpasswordIT);

        //confirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.register_form_finished || id == EditorInfo.IME_NULL) {
//                    attemptRegistration();
//                    return true;
//                }
//                return false;
//            }
//        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


    }
    public void signUPNewUser(View v){
        attemptRegistration();
    }

    public void UseralreadyExist(View v){
        toLoginPage();
    }

    private void attemptRegistration(){
        // Reset errors displayed in the form.
        memailView.setError(null);
        passwordView.setError(null);

        String email=memailView.getText().toString();
        String password=passwordView.getText().toString();

        if(!(isEmailValid(email)|| isPasswordEqual(password)))
        {
            showErroeDialog("Enter Correct Email or passoword");}
       else {
            createNewUser(email, password);
        }
    }

    private boolean isEmailValid(String memail){
        return memail.contains("@");
    }
    private boolean isPasswordEqual(String password ){
        String confirmpassword= confirmPasswordView.getText().toString();
        return password.equals(confirmpassword) && password.length()>4;
    }
    private void createNewUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("livechat", "createUserWithEmail:success");
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    updateUI(user);
                    saveDisplayname();
                    finish();
                    toLoginPage();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("livechat", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
//                    updateUI(null);
                }
            }
        });
    }

    private void saveDisplayname(){
        String username=userNameView.getText().toString();
        SharedPreferences prefs=getSharedPreferences(PREF_KEY ,0);
        prefs.edit().putString(USER_ID,username);
        Log.d("livechat",username);
    }
    private void toLoginPage(){
        Intent loginIntent=new Intent(registerActivity.this,loginActivity.class);
        startActivity(loginIntent);
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