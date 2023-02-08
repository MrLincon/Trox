package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class DomesticOrderDetailsActivity extends AppCompatActivity {

    ImageView back;
    CoordinatorLayout main;

    EditText country, city, state;

    TextView parcelTypeText;
    ImageView parcelTypeImage;

    RelativeLayout itemType, getQuotation;

    String parcelType = "Parcel";
    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_order_details);

        back = findViewById(R.id.back);
        main = findViewById(R.id.main);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        itemType = findViewById(R.id.item_type);
        parcelTypeText = findViewById(R.id.item_type_text);
        parcelTypeImage = findViewById(R.id.item_type_image);
        getQuotation = findViewById(R.id.get_quotation);

        tools = new Tools();

        tools.setLightStatusBar(main, this);


        getQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DomesticOrderDetailsActivity.this, GetQuotationActivity.class));
            }
        });

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DomesticOrderDetailsActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("data", "country");
                startActivity(intent);
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DomesticOrderDetailsActivity.this, DeliveryAddressActivity.class);
                intent.putExtra("data", "city");
                startActivity(intent);
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}