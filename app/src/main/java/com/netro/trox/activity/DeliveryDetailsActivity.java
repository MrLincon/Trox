package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.netro.trox.R;
import com.netro.trox.authentication.OTPActivity;
import com.netro.trox.util.Tools;

public class DeliveryDetailsActivity extends AppCompatActivity {

    LinearLayout btnDeliveryDetails;
    CoordinatorLayout main;
    ImageView back;
    TextView rateNow, action;

    Dialog popup;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        btnDeliveryDetails = findViewById(R.id.btn_delivery_details);
        rateNow = findViewById(R.id.rate_now);
        action = findViewById(R.id.action);

        popup = new Dialog(this);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        int value = getIntent().getIntExtra("value",1);

        if (value==5){
            rateNow.setVisibility(View.VISIBLE);
            action.setText(getResources().getString(R.string.go_to_home));
        }



        rateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(DeliveryDetailsActivity.this);
                View sheetView = DeliveryDetailsActivity.this.getLayoutInflater().inflate(R.layout.bottomsheet_rating, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();

                TextView skip = sheetView.findViewById(R.id.skip);
                CardView submit = sheetView.findViewById(R.id.btn_submit);

                skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.cancel();
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.cancel();
                        popup.setContentView(R.layout.popup_successful);
                        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView message = popup.findViewById(R.id.message);
                        TextView actionText = popup.findViewById(R.id.action_text);
                        CardView btnContinue = popup.findViewById(R.id.btn_continue);
                        popup.show();
                        popup.setCancelable(false);

                        message.setText(getResources().getString(R.string.your_rating_successfully_done_thanks_for_giving_your_feedback));
                        actionText.setText(getResources().getString(R.string.explore_now));

                        btnContinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(DeliveryDetailsActivity.this, MainActivity.class));
                            }
                        });

                    }
                });

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliveryDetailsActivity.this, TrackOrdersActivity.class));
                finish();
            }
        });

        btnDeliveryDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action.getText().equals("Go to Home")){
                    startActivity(new Intent(DeliveryDetailsActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(DeliveryDetailsActivity.this, "Call Support", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliveryDetailsActivity.this, TrackOrdersActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DeliveryDetailsActivity.this, TrackOrdersActivity.class));
        finish();
    }

}