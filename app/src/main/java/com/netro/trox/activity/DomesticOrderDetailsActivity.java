package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.WindowCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class DomesticOrderDetailsActivity extends AppCompatActivity {

    ImageView back;
    CoordinatorLayout main;

    EditText receiverContact, receiverName, deliveryCountry, deliveryCity, deliveryState, deliveryAddress;
    RangeSlider weightSlider;

    TextView parcelTypeText;
    ImageView parcelTypeImage;

    RelativeLayout itemType, getQuotation;

    CardView btnContinue;

    String parcelType = "Parcel";
    String type;
    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_order_details);


        back = findViewById(R.id.back);
        main = findViewById(R.id.main);
        receiverName = findViewById(R.id.receiver_name);
        receiverContact = findViewById(R.id.receiver_contact);
        deliveryCountry = findViewById(R.id.delivery_country);
        deliveryCity = findViewById(R.id.delivery_city);
        deliveryState = findViewById(R.id.delivery_state);
        deliveryAddress = findViewById(R.id.delivery_address);
        itemType = findViewById(R.id.item_type);
        parcelTypeText = findViewById(R.id.item_type_text);
        parcelTypeImage = findViewById(R.id.item_type_image);
        getQuotation = findViewById(R.id.get_quotation);
        btnContinue = findViewById(R.id.btn_continue);


        Log.d("sdadadadada", "onCreate: "+"im in on DomesticOrderDetailsActivity");

        type = getIntent().getStringExtra("type");

        tools = new Tools();

        tools.setLightStatusBar(main, this);


        getQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DomesticOrderDetailsActivity.this, GetQuotationActivity.class));
            }
        });

        deliveryCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DomesticOrderDetailsActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("data", "country");
                startActivity(intent);
            }
        });

        deliveryCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DomesticOrderDetailsActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("data", "city");
                startActivity(intent);
            }
        });

        deliveryState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DomesticOrderDetailsActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("data", "state");
                startActivity(intent);
            }
        });

        itemType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(DomesticOrderDetailsActivity.this);
                View sheetView = DomesticOrderDetailsActivity.this.getLayoutInflater().inflate(R.layout.bottomsheet_item_type, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();

                RelativeLayout parcel = sheetView.findViewById(R.id.parcel);
                RelativeLayout document = sheetView.findViewById(R.id.document);

                parcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parcelTypeText.setText("Parcel");
                        parcelTypeImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_parcel_2));
                        parcelType = "Parcel";
                        mBottomSheetDialog.cancel();
                    }
                });

                document.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parcelTypeText.setText("Document");
                        parcelTypeImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_doc_2));
                        parcelType = "Document";
                        mBottomSheetDialog.cancel();
                    }
                });
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DomesticOrderDetailsActivity.this,OrderPickUpLocationActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}