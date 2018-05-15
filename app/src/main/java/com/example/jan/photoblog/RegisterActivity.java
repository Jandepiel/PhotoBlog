package com.example.jan.photoblog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText regEmailText;
    private EditText regPassText;
    private EditText regConfPassText;
    private Button regBtn;
    private Button regLoginBtn;

    private FirebaseAuth mAuth;

    private ProgressBar regProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        regEmailText = (EditText) findViewById(R.id.reg_email);
        regPassText = (EditText) findViewById(R.id.reg_password);
        regConfPassText = (EditText) findViewById(R.id.reg_conf_password);
        regBtn =(Button) findViewById(R.id.reg_btn);
        regLoginBtn =(Button) findViewById(R.id.reg_login_btn);
        regProgress = (ProgressBar) findViewById(R.id.reg_progress);

        regLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String regEmail = regEmailText.getText().toString();
                String regPass = regPassText.getText().toString();
                String regConfPass = regConfPassText.getText().toString();

                if(!TextUtils.isEmpty(regEmail) && !TextUtils.isEmpty(regPass) && !TextUtils.isEmpty(regConfPass)){
                    if (regPass.equals(regConfPass)){
                        regProgress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(regEmail, regPass).addOnCompleteListener(new OnCompleteListener<AuthResult>()  {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){

                                if(task.isSuccessful()){

                                    startMain();


                                } else{

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                                }

                                regProgress.setVisibility(View.INVISIBLE);
                            }


                        });

                    } else {
                        Toast.makeText(RegisterActivity.this, "Error Password and Confirm Password doesn't match ", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {

            startMain();


        }
    }

    private void startMain() {
        Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(mainIntent);
        finish();


    }
}
