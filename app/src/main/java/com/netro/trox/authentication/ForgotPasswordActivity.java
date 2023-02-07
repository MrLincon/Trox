package com.netro.trox.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netro.trox.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    ImageView back;

    RelativeLayout email, phone;

    String selected = "";
    CardView btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        back = findViewById(R.id.back);
        btnContinue = findViewById(R.id.btn_continue);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = "Email";
                email.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_outline_bg));
                phone.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_bg));
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = "International";
                email.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_bg));
                phone.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_outline_bg));
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class));
            }
        });
    }
}