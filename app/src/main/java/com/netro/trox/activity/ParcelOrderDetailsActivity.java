package com.netro.trox.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

public class ParcelOrderDetailsActivity extends AppCompatActivity {

    ImageView back;
    CoordinatorLayout main;

    EditText receiverContact, receiverName, pickupCountry, pickupState, pickupCity, pickupAddress, pickupLocation,
            deliveryCountry, deliveryCity, deliveryState, deliveryAddress, deliveryLocation;
    RangeSlider weightSlider;
    int weight;

    TextView parcelTypeText;
    ImageView parcelTypeImage;

    RelativeLayout itemType, getQuotation;

    CardView btnContinue;

    String locationField = "";

    String parcelType = "Parcel";
    String type;
    Double deliveryLattitude, deliveryLongitude, pickupLattitude, pickupLongtitude;

    Tools tools;

    private static final int REQUEST_CODE = 1011;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcel_order_details);


        back = findViewById(R.id.back);
        main = findViewById(R.id.main);
        receiverName = findViewById(R.id.receiver_name);
        receiverContact = findViewById(R.id.receiver_contact);
        pickupCountry = findViewById(R.id.pickup_country);
        pickupState = findViewById(R.id.pickup_state);
        pickupCity = findViewById(R.id.pickup_city);
        pickupAddress = findViewById(R.id.pickup_address);
        pickupLocation = findViewById(R.id.pickup_location);
        deliveryCountry = findViewById(R.id.delivery_country);
        deliveryCity = findViewById(R.id.delivery_city);
        deliveryState = findViewById(R.id.delivery_state);
        deliveryAddress = findViewById(R.id.delivery_address);
        deliveryLocation = findViewById(R.id.delivery_location);
        itemType = findViewById(R.id.item_type);
        parcelTypeText = findViewById(R.id.item_type_text);
        parcelTypeImage = findViewById(R.id.item_type_image);
        weightSlider = findViewById(R.id.weight_slider);
        getQuotation = findViewById(R.id.get_quotation);
        btnContinue = findViewById(R.id.btn_continue);


        type = getIntent().getStringExtra("type");

        tools = new Tools();

        tools.setLightStatusBar(main, this);


        getQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParcelOrderDetailsActivity.this, GetQuotationActivity.class));
            }
        });

        if (type.equals("Domestic")) {
            FirebaseFirestore.getInstance().collection("userDetails").document(FirebaseAuth.getInstance().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String country = documentSnapshot.getString("user_country");
                            pickupCountry.setText(country);
                            deliveryCountry.setText(country);

                            pickupCountry.setEnabled(false);
                            pickupCountry.setFocusable(false);
                            deliveryCountry.setEnabled(false);
                            deliveryCountry.setFocusable(false);
                        }
                    });
        } else if (type.equals("International")) {
            FirebaseFirestore.getInstance().collection("userDetails").document(FirebaseAuth.getInstance().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String country = documentSnapshot.getString("user_country");
                            pickupCountry.setText(country);

                            pickupCountry.setEnabled(false);
                            pickupCountry.setFocusable(false);
                        }
                    });
        }

        pickupCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationField = "PU_Country";


                pickupState.setText("");
                pickupCity.setText("");

                Intent intent = new Intent(ParcelOrderDetailsActivity.this, DeliveryAddressActivity.class);
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

                    Intent intent = new Intent(ParcelOrderDetailsActivity.this, DeliveryAddressActivity.class);
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
                    Intent intent = new Intent(ParcelOrderDetailsActivity.this, DeliveryAddressActivity.class);
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

        pickupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!pickupCity.getText().toString().equals("")){

                    if (ContextCompat.checkSelfPermission(ParcelOrderDetailsActivity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locationPermissionGranted = true;
                        locationField = "PickupLocation";
                        Intent intent = new Intent(ParcelOrderDetailsActivity.this, MapActivity.class);
                        intent.putExtra("city", pickupCity.getText().toString());
                        intent.putExtra("data", "pickup");
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        ActivityCompat.requestPermissions(ParcelOrderDetailsActivity.this,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                }else{
                    tools.makeSnack(main,"Select a city first");
                }

            }
        });

        deliveryLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!deliveryCity.getText().toString().equals("")){
                    if (ContextCompat.checkSelfPermission(ParcelOrderDetailsActivity.this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        locationPermissionGranted = true;
                        locationField = "DeliveryLocation";
                        Intent intent = new Intent(ParcelOrderDetailsActivity.this, MapActivity.class);
                        intent.putExtra("city", deliveryCity.getText().toString());
                        intent.putExtra("data", "delivery");
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        ActivityCompat.requestPermissions(ParcelOrderDetailsActivity.this,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                }else{
                    tools.makeSnack(main,"Select a city first");
                }

            }
        });


        deliveryCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationField = "DA_Country";


                deliveryState.setText("");
                deliveryCity.setText("");

                Intent intent = new Intent(ParcelOrderDetailsActivity.this, DeliveryAddressActivity.class);
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

                    Intent intent = new Intent(ParcelOrderDetailsActivity.this, DeliveryAddressActivity.class);
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
                    Intent intent = new Intent(ParcelOrderDetailsActivity.this, DeliveryAddressActivity.class);
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


        weightSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                weight = Math.round(value);
            }
        });


        itemType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(ParcelOrderDetailsActivity.this);
                View sheetView = ParcelOrderDetailsActivity.this.getLayoutInflater().inflate(R.layout.bottomsheet_item_type, null);
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

                String ReceiverContact = receiverContact.getText().toString();
                String ReceiverName = receiverName.getText().toString();
                String PickupCountry = pickupCountry.getText().toString();
                String PickupState = pickupState.getText().toString();
                String PickupCity = pickupCity.getText().toString();
                String PickupAddress = pickupAddress.getText().toString();
                String DeliveryCountry = deliveryCountry.getText().toString();
                String DeliveryState = deliveryState.getText().toString();
                String DeliveryCity = deliveryCity.getText().toString();
                String DeliveryAddress = deliveryAddress.getText().toString();
                String ParcelWeight = weight + " KG";
                String ParcelType = parcelTypeText.getText().toString();
                String PickupLocation = pickupLocation.getText().toString();
                String DeliveryLocation = deliveryLocation.getText().toString();


                if (PickupCountry.equals("")) {
                    tools.makeSnack(main, "Country can not be empty");
                } else if (PickupState.equals("")) {
                    tools.makeSnack(main, "State can not be empty");
                } else if (PickupCity.equals("")) {
                    tools.makeSnack(main, "City can not be empty");
                } else if (PickupAddress.equals("")) {
                    tools.makeSnack(main, "Address can not be empty");
                } else if (PickupLocation.equals("")) {
                    tools.makeSnack(main, "Set a pickup location on the map");
                } else if (ReceiverName.equals("")) {
                    tools.makeSnack(main, "Receiver name can not be empty");
                } else if (ReceiverContact.equals("")) {
                    tools.makeSnack(main, "Receiver contact can not be empty");
                } else if (DeliveryCountry.equals("")) {
                    tools.makeSnack(main, "Country can not be empty");
                } else if (DeliveryState.equals("")) {
                    tools.makeSnack(main, "State can not be empty");
                } else if (DeliveryCity.equals("")) {
                    tools.makeSnack(main, "City can not be empty");
                } else if (DeliveryAddress.equals("")) {
                    tools.makeSnack(main, "Address can not be empty");
                } else if (DeliveryLocation.equals("")) {
                    tools.makeSnack(main, "Set a delivery location on the map");
                } else if (ParcelWeight.equals("0 KG")) {
                    tools.makeSnack(main, "Select your parcel's weight");
                } else if (ParcelType.equals("")) {
                    tools.makeSnack(main, "Parcel type can not be empty");
                } else {

                    Intent intent = new Intent(ParcelOrderDetailsActivity.this, OrderConfirmationActivity.class);
                    intent.putExtra("receiverContact", ReceiverContact);
                    intent.putExtra("receiverName", ReceiverName);
                    intent.putExtra("pickupCountry", PickupCountry);
                    intent.putExtra("pickupState", PickupState);
                    intent.putExtra("pickupCity", PickupCity);
                    intent.putExtra("pickupAddress", PickupAddress);
                    intent.putExtra("deliveryCountry", DeliveryCountry);
                    intent.putExtra("deliveryState", DeliveryState);
                    intent.putExtra("deliveryCity", DeliveryCity);
                    intent.putExtra("deliveryAddress", DeliveryAddress);
                    intent.putExtra("parcelWeight", ParcelWeight);
                    intent.putExtra("parcelType", ParcelType);
                    intent.putExtra("orderType", type);
                    intent.putExtra("pickupLat", pickupLattitude);
                    intent.putExtra("pickupLong", pickupLongtitude);
                    intent.putExtra("deliveryLat", deliveryLattitude);
                    intent.putExtra("deliveryLong", deliveryLongitude);
                    startActivity(intent);

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




            if (locationField.equals("DeliveryLocation")) {

                deliveryLattitude = data.getDoubleExtra("deliveryLat",0);
                deliveryLongitude = data.getDoubleExtra("deliveryLong",0);
                String deliveryAddress = data.getStringExtra("deliveryAddress");
                deliveryLocation.setText(deliveryAddress);

            } else if (locationField.equals("PickupLocation")) {

                pickupLattitude = data.getDoubleExtra("pickupLat",0);
                pickupLongtitude = data.getDoubleExtra("pickupLong",0);
                String pickupAddress = data.getStringExtra("pickupAddress");
                pickupLocation.setText(pickupAddress);

            }

        }
    }
}