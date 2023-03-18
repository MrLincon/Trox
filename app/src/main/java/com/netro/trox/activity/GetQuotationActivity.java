package com.netro.trox.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class GetQuotationActivity extends AppCompatActivity {

    ImageView back;

    EditText pickupCountry, pickupState, pickupCity, deliveryCountry, deliveryCity, deliveryState;
    CardView resultLayout, btnGetQuotation;

    RangeSlider weightSlider;

    TextView priceHome, priceMerchant;

    LinearLayout main;

    Tools tools;

    String ParcelType = "Parcel";

    int ParcelWeight;
    long price;

    String locationField = "";

    private static final int REQUEST_CODE = 1011;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_quotation);

        back = findViewById(R.id.back);
        main = findViewById(R.id.main);
        resultLayout = findViewById(R.id.result_layout);
        pickupCountry = findViewById(R.id.pickup_country);
        pickupState = findViewById(R.id.pickup_state);
        pickupCity = findViewById(R.id.pickup_city);
        deliveryCountry = findViewById(R.id.delivery_country);
        deliveryCity = findViewById(R.id.delivery_city);
        deliveryState = findViewById(R.id.delivery_state);
        btnGetQuotation = findViewById(R.id.btn_get_quotation);
        priceHome = findViewById(R.id.price_home);
        priceMerchant = findViewById(R.id.price_merchant);
        weightSlider = findViewById(R.id.weight_slider);


        tools = new Tools();

        tools.setLightStatusBar(main, this);


        ParcelType = getIntent().getStringExtra("data");

        weightSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                ParcelWeight = Math.round(value);
            }
        });

        btnGetQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String PickupCountry = pickupCountry.getText().toString();
                String PickupState = pickupState.getText().toString();
                String PickupCity = pickupCity.getText().toString();
                String DeliveryCountry = deliveryCountry.getText().toString();
                String DeliveryState = deliveryState.getText().toString();
                String DeliveryCity = deliveryCity.getText().toString();

                if (PickupCountry.equals("")) {
                    tools.makeSnack(main, "Country can not be empty");
                } else if (PickupState.equals("")) {
                    tools.makeSnack(main, "State can not be empty");
                } else if (PickupCity.equals("")) {
                    tools.makeSnack(main, "City can not be empty");
                } else if (DeliveryCountry.equals("")) {
                    tools.makeSnack(main, "Country can not be empty");
                } else if (DeliveryState.equals("")) {
                    tools.makeSnack(main, "State can not be empty");
                } else if (DeliveryCity.equals("")) {
                    tools.makeSnack(main, "City can not be empty");
                } else if (!(ParcelWeight == 0)) {

                    if (PickupCountry.equals(DeliveryCountry)) {

                        if (PickupCity.equals(DeliveryCity) && ParcelType.equals("Parcel")){
                            String doc = null;
                            if (ParcelWeight==1){
                                doc = "local_bellow_one";
                            } else if (ParcelWeight>1 && ParcelWeight<6) {
                                doc = "local_one_to_five";
                            }else if (ParcelWeight>5 && ParcelWeight<=10) {
                                doc = "local_five_to_ten";
                            }

                            setPriceDomestic("parcel",doc);

                        }else if (!PickupCity.equals(DeliveryCity) && ParcelType.equals("Parcel")){
                            String doc = null;
                            if (ParcelWeight==1){
                                doc = "domestic_bellow_one";
                            } else if (ParcelWeight>1 && ParcelWeight<6) {
                                doc = "domestic_one_to_five";
                            }else if (ParcelWeight>5 && ParcelWeight<=10) {
                                doc = "domestic_five_to_ten";
                            }

                            setPriceDomestic("parcel",doc);

                        }else if (PickupCity.equals(DeliveryCity) && ParcelType.equals("Document")){
                            String doc = null;
                            if (ParcelWeight==1){
                                doc = "local_bellow_one";
                            } else if (ParcelWeight>1 && ParcelWeight<6) {
                                doc = "local_one_to_five";
                            }else if (ParcelWeight>5 && ParcelWeight<=10) {
                                doc = "local_five_to_ten";
                            }
                            setPriceDomestic("document",doc);

                        }else if (!PickupCity.equals(DeliveryCity) && ParcelType.equals("Document")){
                            String doc = null;
                            if (ParcelWeight==1){
                                doc = "domestic_bellow_one";
                            } else if (ParcelWeight>1 && ParcelWeight<6) {
                                doc = "domestic_one_to_five";
                            }else if (ParcelWeight>5 && ParcelWeight<=10) {
                                doc = "domestic_five_to_ten";
                            }
                            setPriceDomestic("document",doc);
                        }
                    } else {

                        if (ParcelType.equals("Parcel")){
                            String doc = null;
                            if (ParcelWeight==1){
                                doc = "international_bellow_one";
                            } else if (ParcelWeight>1 && ParcelWeight<6) {
                                doc = "international_one_to_five";
                            }else if (ParcelWeight>5 && ParcelWeight<=10) {
                                doc = "international_five_to_ten";
                            }

                            setPriceInternational("parcel",doc);

                        }else if (ParcelType.equals("Document")){
                            String doc = null;
                            if (ParcelWeight==1){
                                doc = "international_bellow_one";
                            } else if (ParcelWeight>1 && ParcelWeight<6) {
                                doc = "international_one_to_five";
                            }else if (ParcelWeight>5 && ParcelWeight<=10) {
                                doc = "international_to_ten";
                            }
                            setPriceInternational("document",doc);

                        }
                    }
                } else {
                    tools.makeSnack(main, "Weight can not be 0 KG");
                }
            }
        });

        pickupCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationField = "PU_Country";


                pickupState.setText("");
                pickupCity.setText("");

                Intent intent = new Intent(GetQuotationActivity.this, QuotationAddressActivity.class);
                intent.putExtra("data", "country");
                intent.putExtra("country", pickupCountry.getText().toString());
                intent.putExtra("state", pickupState.getText().toString());
                intent.putExtra("city", pickupCity.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        pickupState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!pickupCountry.getText().toString().equals("")) {
                    locationField = "PU_State";

                    pickupCity.setText("");

                    Intent intent = new Intent(GetQuotationActivity.this, QuotationAddressActivity.class);
                    intent.putExtra("data", "state");
                    intent.putExtra("country", pickupCountry.getText().toString());
                    intent.putExtra("state", pickupState.getText().toString());
                    intent.putExtra("city", pickupCity.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    tools.makeSnack(main, "Select a country first");
                }
            }
        });

        pickupCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationField = "PU_City";
                if (!pickupState.getText().toString().equals("")) {
                    Intent intent = new Intent(GetQuotationActivity.this, QuotationAddressActivity.class);
                    intent.putExtra("data", "city");
                    intent.putExtra("country", pickupCountry.getText().toString());
                    intent.putExtra("state", pickupState.getText().toString());
                    intent.putExtra("city", pickupCity.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    tools.makeSnack(main, "Select a state first");
                }

            }
        });

        deliveryCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationField = "DA_Country";


                deliveryState.setText("");
                deliveryCity.setText("");

                Intent intent = new Intent(GetQuotationActivity.this, QuotationAddressActivity.class);
                intent.putExtra("data", "country");
                intent.putExtra("country", deliveryCountry.getText().toString());
                intent.putExtra("state", deliveryState.getText().toString());
                intent.putExtra("city", deliveryCity.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        deliveryState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!deliveryCountry.getText().toString().equals("")) {
                    locationField = "DA_State";

                    deliveryCity.setText("");

                    Intent intent = new Intent(GetQuotationActivity.this, QuotationAddressActivity.class);
                    intent.putExtra("data", "state");
                    intent.putExtra("country", deliveryCountry.getText().toString());
                    intent.putExtra("state", deliveryState.getText().toString());
                    intent.putExtra("city", deliveryCity.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    tools.makeSnack(main, "Select a country first");
                }
            }
        });

        deliveryCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationField = "DA_City";
                if (!deliveryState.getText().toString().equals("")) {
                    Intent intent = new Intent(GetQuotationActivity.this, QuotationAddressActivity.class);
                    intent.putExtra("data", "city");
                    intent.putExtra("country", deliveryCountry.getText().toString());
                    intent.putExtra("state", deliveryState.getText().toString());
                    intent.putExtra("city", deliveryCity.getText().toString());
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    tools.makeSnack(main, "Select a state first");
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String result = data.getStringExtra("key");

            if (locationField.equals("DA_Country")) {
                deliveryCountry.setText(result);
            } else if (locationField.equals("DA_State")) {
                deliveryState.setText(result);
            } else if (locationField.equals("DA_City")) {
                deliveryCity.setText(result);
            } else if (locationField.equals("PU_Country")) {
                pickupCountry.setText(result);
            } else if (locationField.equals("PU_State")) {
                pickupState.setText(result);
            } else if (locationField.equals("PU_City")) {
                pickupCity.setText(result);
            }

        }
    }


    private void setPriceDomestic(String document, String value) {
        FirebaseFirestore.getInstance().collection("deliveryCost").document(document).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                price = documentSnapshot.getLong(value);
//                totalPrice.setText("$"+String.valueOf(price));
            }
        });
    }

    private void setPriceInternational(String document, String value) {
        FirebaseFirestore.getInstance().collection("deliveryCost").document(document).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                price = documentSnapshot.getLong(value);
//                totalPrice.setText("$"+String.valueOf(price));
            }
        });
    }

}