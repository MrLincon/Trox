package com.netro.trox.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netro.trox.R;
import com.netro.trox.activity.AccountSetupCustomerActivity;
import com.netro.trox.activity.AccountSetupMerchantActivity;
import com.netro.trox.util.Tools;

public class SignUpAsActivity extends AppCompatActivity {

    ImageView back;

    RelativeLayout customer, merchant;
    CoordinatorLayout main;

    String selected = "";
    CardView btnContinue;

    Tools tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as);

        btnContinue = findViewById(R.id.btn_continue);
        back = findViewById(R.id.back);
        customer = findViewById(R.id.customer);
        merchant = findViewById(R.id.merchant);
        main = findViewById(R.id.main);

        tools = new Tools();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = "Customer";
                customer.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_outline_bg));
                merchant.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_bg));
            }
        });

        merchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = "Merchant";
                customer.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.outline_bg));
                merchant.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_outline_bg));
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected.equals("Customer")){
                    Intent intent = new Intent(SignUpAsActivity.this, AccountSetupCustomerActivity.class);
                    startActivity(intent);         
                    finish();
                }else if (selected.equals("Merchant")){
                    Intent intent = new Intent(SignUpAsActivity.this, AccountSetupMerchantActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    tools.makeSnack(main,getString(R.string.message_user_type));
                }
            }
        });
    }
}