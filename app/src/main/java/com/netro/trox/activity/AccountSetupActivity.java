package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.netro.trox.R;

public class AccountSetupActivity extends AppCompatActivity {

    CardView btnContinue;

    Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup);

        btnContinue = findViewById(R.id.btn_continue);

        popup = new Dialog(this);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setContentView(R.layout.popup_successful);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView message = popup.findViewById(R.id.message);
                TextView actionText = popup.findViewById(R.id.action_text);
                CardView btnContinue = popup.findViewById(R.id.btn_continue);
                popup.show();
                popup.setCancelable(false);

                message.setText(getResources().getString(R.string.account_ready));
                actionText.setText(getResources().getString(R.string.go_to_home));

                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(AccountSetupActivity.this, MainActivity.class));
                    }
                });
            }
        });
    }
}