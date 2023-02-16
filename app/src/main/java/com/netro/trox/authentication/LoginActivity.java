package com.netro.trox.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.netro.trox.R;
import com.netro.trox.activity.MainActivity;
import com.netro.trox.util.Tools;

public class LoginActivity extends AppCompatActivity {

    TextView signUp, forgotPassword;
    CardView logIn;

    CoordinatorLayout main;

    TextInputEditText et_email, et_password;
    TextInputLayout email_layout, password_layout;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logIn = findViewById(R.id.log_in);
        signUp = findViewById(R.id.sign_up);
        forgotPassword = findViewById(R.id.forgot_password);
        main = findViewById(R.id.main);

        tools = new Tools();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().getCurrentUser().reload();
                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    tools.makeSnack(main, "Email is not verified");
                }

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpAsActivity.class));
            }
        });

    }
}