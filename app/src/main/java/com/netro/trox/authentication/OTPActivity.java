package com.netro.trox.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.netro.trox.R;
import com.netro.trox.activity.AccountSetupActivity;

public class OTPActivity extends AppCompatActivity {

    TextView time;
    CardView verify;

    Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activity);

        time = findViewById(R.id.time);
        verify = findViewById(R.id.verify);

        popup = new Dialog(this);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText(String.valueOf(millisUntilFinished / 1000 + " s"));
                // logic to set the EditText could go here
            }

            public void onFinish() {
                time.setText("0");
            }

        }.start();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setContentView(R.layout.popup_successful);
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView message = popup.findViewById(R.id.message);
                TextView actionText = popup.findViewById(R.id.action_text);
                CardView btnContinue = popup.findViewById(R.id.btn_continue);
                popup.show();
                popup.setCancelable(false);

                message.setText(getResources().getString(R.string.sign_up_completed));
                actionText.setText(getResources().getString(R.string.continue_));

                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(OTPActivity.this, AccountSetupActivity.class));
                    }
                });
            }
        });
    }
}