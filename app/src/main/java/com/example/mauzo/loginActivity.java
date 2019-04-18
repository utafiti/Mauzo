package com.example.mauzo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends Activity {

    EditText name,password;
    TextView info,register;
    Button login;
    int counter = 5;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.edtname);
        password = findViewById(R.id.edtpass);
        info = findViewById(R.id.tvinfo);
        register = findViewById(R.id.tvregister);
        login = findViewById(R.id.btnlogin);
        forgotPassword = findViewById(R.id.tvForgotPassword);
        info.setText("Number of attempts remaining: "+5);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait...");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            finish();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = name.getText().toString().trim();
                String upass = password.getText().toString().trim();
                if (uname.isEmpty()||upass.isEmpty()){
                    Toast.makeText(loginActivity.this, "Fill in all Inputs", Toast.LENGTH_SHORT).show();
                }else {
                    validate(uname,upass);
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),PasswordActivity.class));
            }
        });
    }

    private void validate(String userName, String userPassword){
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){

                    checkEmailVerification();

                }else {
                    Toast.makeText(loginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    info.setText("Number of attempts remaining: "+counter);
                    if (counter==0){
                        login.setEnabled(false);
                    }
                }
            }
        });
    }

    private void  checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if (emailflag){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}

