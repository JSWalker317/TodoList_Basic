package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edtRegEmail, edtRegPass;
    private TextView txtRegPQ ;
    private Button btnReg;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        init();
        addListener();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);
    }


    private void init(){
        toolbar = findViewById(R.id.RegisterToolbar);
        txtRegPQ = findViewById(R.id.RegistrationPageQuestion);
        edtRegEmail = findViewById(R.id.RegistrationEmail);
        edtRegPass = findViewById(R.id.RegistrationPassword);
        btnReg = findViewById(R.id.RegistrationButton);
    }
    private void addListener() {
        txtRegPQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginActivity();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =edtRegEmail.getText().toString().trim();
                String password = edtRegPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    edtRegEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtRegPass.setError("Password is required");
                    return;
                }else   {
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }else {
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this,"Registration is failed"+ error, Toast.LENGTH_SHORT ).show();
                                loader.dismiss();
                            }

                        }
                    });

                }



            }
        });
    }
    private void gotoLoginActivity(){
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}