package com.netro.trox.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netro.trox.R;
import com.netro.trox.util.Tools;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class OrderConfirmationActivity extends AppCompatActivity {

    LinearLayout main;
    ImageView back;

    TextView senderName, senderContact, senderAddress, receiverName, receiverContact, receiverAddress, parcelWeight, totalPrice;
    CardView btnConfirm;

    String name, contact, address;


    private FirebaseFirestore db;
    private String userID;
    private FirebaseAuth mAuth;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        senderName = findViewById(R.id.sender_name);
        senderContact = findViewById(R.id.sender_contact);
        senderAddress = findViewById(R.id.sender_address);
        receiverName = findViewById(R.id.receiver_name);
        receiverContact = findViewById(R.id.receiver_contact);
        receiverAddress = findViewById(R.id.receiver_address);
        parcelWeight = findViewById(R.id.weight);
        totalPrice = findViewById(R.id.total_price);
        btnConfirm = findViewById(R.id.btn_confirm);

        tools = new Tools();

        tools.setLightStatusBar(main,this);

        //firebase init
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getUid();

        String ReceiverContact = getIntent().getStringExtra("receiverContact");
        String ReceiverName = getIntent().getStringExtra("receiverName");
        String PickupCountry = getIntent().getStringExtra("pickupCountry");
        String PickupState = getIntent().getStringExtra("pickupState");
        String PickupCity = getIntent().getStringExtra("pickupCity");
        String PickupAddress = getIntent().getStringExtra("pickupAddress");
        String DeliveryCountry = getIntent().getStringExtra("deliveryCountry");
        String DeliveryState = getIntent().getStringExtra("deliveryState");
        String DeliveryCity = getIntent().getStringExtra("deliveryCity");
        String DeliveryAddress = getIntent().getStringExtra("deliveryAddress");
        String ParcelWeight = getIntent().getStringExtra("parcelWeight");
        String ParcelType = getIntent().getStringExtra("parcelType");
        String OrderType = getIntent().getStringExtra("orderType");
        Double PickupLattitude = getIntent().getDoubleExtra("pickupLat",0);
        Double PickupLongtitude = getIntent().getDoubleExtra("pickupLong",0);
        Double DeliveryLattitude = getIntent().getDoubleExtra("deliveryLat",0);
        Double DeliveryLongitude = getIntent().getDoubleExtra("deliveryLong",0);

        db.collection("userDetails").document(userID).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                name = documentSnapshot.getString("user_name");
                                contact = documentSnapshot.getString("user_contact");

                                senderName.setText(name);
                                senderContact.setText(contact);

                            }
                        });

        senderAddress.setText(PickupAddress);
        receiverName.setText(ReceiverName);
        receiverContact.setText(ReceiverContact);
        receiverAddress.setText(DeliveryAddress);
        parcelWeight.setText(ParcelWeight);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnConfirm.setFocusable(false);

                String currentTimeInMillies  = String.valueOf(System.currentTimeMillis());

                Map<String, Object> userMap = new HashMap<>();

                userMap.put("sender_name", senderName.getText().toString());
                userMap.put("sender_contact", senderContact.getText().toString());
                userMap.put("sender_country", PickupCountry);
                userMap.put("sender_state", PickupState);
                userMap.put("sender_city", PickupCity);
                userMap.put("sender_address", PickupAddress);
                userMap.put("receiver_name", ReceiverName);
                userMap.put("receiver_contact", ReceiverContact);
                userMap.put("receiver_country", DeliveryCountry);
                userMap.put("receiver_state", DeliveryState);
                userMap.put("receiver_city", DeliveryCity);
                userMap.put("receiver_address", DeliveryAddress);
                userMap.put("pickupLat", PickupLattitude);
                userMap.put("pickupLong", PickupLongtitude);
                userMap.put("deliveryLat", DeliveryLattitude);
                userMap.put("deliveryLong", DeliveryLongitude);
                userMap.put("parcel_weight", ParcelWeight);
                userMap.put("parcel_type", ParcelType);
                userMap.put("order_id", currentTimeInMillies);
                userMap.put("order_status", "Pending");
                userMap.put("order_type", OrderType);
                userMap.put("user_id", userID);
                userMap.put("timestamp", FieldValue.serverTimestamp());


                db.collection("orders").document(currentTimeInMillies)
                        .set(userMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Dialog popup = new Dialog(OrderConfirmationActivity.this);
                        popup.setContentView(R.layout.popup_successful);
                        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView message = popup.findViewById(R.id.message);
                        TextView actionText = popup.findViewById(R.id.action_text);
                        CardView btnContinue = popup.findViewById(R.id.btn_continue);
                        popup.setCancelable(false);
                        popup.show();

                        message.setText("Your order has been successfully placed. Your order will be picked up within 24 hours");
                        actionText.setText("Go To Home");

                        btnContinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popup.dismiss();
                                startActivity(new Intent(OrderConfirmationActivity.this,MainActivity.class));
                                finish();
                            }
                        });
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