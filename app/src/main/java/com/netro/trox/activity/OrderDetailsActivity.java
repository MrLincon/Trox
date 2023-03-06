package com.netro.trox.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.netro.trox.R;
import com.netro.trox.fragment.orders.FragmentPickUpRequest;
import com.netro.trox.fragment.orders.FragmentPickedUp;
import com.netro.trox.util.Tools;

public class OrderDetailsActivity extends AppCompatActivity {

    ImageView back;

    LinearLayout main;

    CardView status, rating, instructions;

    TextView statusTitle, statusDescription, rateNow, senderName, senderContact, senderAddress, receiverName, receiverContact, receiverAddress, parcelWeight, parcelPrice, orderID;

    String data, order_id;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        back = findViewById(R.id.back);
        main = findViewById(R.id.main);
        status = findViewById(R.id.status);
        rating = findViewById(R.id.rating);
        instructions = findViewById(R.id.instructions);
        statusTitle = findViewById(R.id.status_title);
        statusDescription = findViewById(R.id.status_description);
        rateNow = findViewById(R.id.rate_now);
        senderName = findViewById(R.id.sender_name);
        senderContact = findViewById(R.id.sender_contact);
        senderAddress = findViewById(R.id.sender_address);
        receiverName = findViewById(R.id.receiver_name);
        receiverContact = findViewById(R.id.receiver_contact);
        receiverAddress = findViewById(R.id.receiver_address);
        parcelWeight = findViewById(R.id.parcel_weight);
        parcelPrice = findViewById(R.id.parcel_price);
        orderID = findViewById(R.id.order_id_text);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        data = getIntent().getStringExtra("data");
        order_id = getIntent().getStringExtra("order_id");

        FirebaseFirestore.getInstance().collection("orders").document(order_id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                   if (documentSnapshot.exists()){
                       String SenderName = documentSnapshot.getString("sender_name");
                       String SenderContact = documentSnapshot.getString("sender_contact");
                       String SenderAddress = documentSnapshot.getString("sender_address");
                       String ReceiverName = documentSnapshot.getString("receiver_name");
                       String ReceiverContact = documentSnapshot.getString("receiver_contact");
                       String ReceiverAddress = documentSnapshot.getString("receiver_address");
                       String ParcelWeight = documentSnapshot.getString("parcel_weight");
                       Long Price = documentSnapshot.getLong("price");
                       String OrdeID = documentSnapshot.getString("order_id");


                       senderName.setText(SenderName);
                       senderContact.setText(SenderContact);
                       senderAddress.setText(SenderAddress);
                       receiverName.setText(ReceiverName);
                       receiverContact.setText(ReceiverContact);
                       receiverAddress.setText(ReceiverAddress);
                       parcelWeight.setText(ParcelWeight);
                       parcelPrice.setText("$"+Price);
                       orderID.setText(OrdeID);
                   }
                    }
                });

        if (data.equals("FragmentPickUpRequest")){
            status.setVisibility(View.GONE);
            instructions.setVisibility(View.VISIBLE);
            rating.setVisibility(View.GONE);
        }else if (data.equals("FragmentPickedUp")){
            status.setVisibility(View.VISIBLE);
            instructions.setVisibility(View.VISIBLE);
            rating.setVisibility(View.GONE);

            statusTitle.setTextColor(getResources().getColor(R.color.colorStatusInfo));
            statusTitle.setText(getResources().getString(R.string.order_picked_up));
            statusDescription.setText("Please use proper packaging to keep your item safe. Write down the receiver’s details on the package to ensure delivery.");
        }else if (data.equals("FragmentDelivered")){
            status.setVisibility(View.VISIBLE);
            instructions.setVisibility(View.GONE);
            rating.setVisibility(View.VISIBLE);

            statusTitle.setTextColor(getResources().getColor(R.color.colorStatusSuccess));
            statusTitle.setText(getResources().getString(R.string.delivered));
            statusDescription.setText("Please use proper packaging to keep your item safe. Write down the receiver’s details on the package to ensure delivery.");
        }else if (data.equals("FragmentReturned")){
            status.setVisibility(View.VISIBLE);
            instructions.setVisibility(View.VISIBLE);
            rating.setVisibility(View.GONE);

            statusTitle.setTextColor(getResources().getColor(R.color.colorStatusError));
            statusTitle.setText("Order Returned");
            statusDescription.setText("Please use proper packaging to keep your item safe. Write down the receiver’s details on the package to ensure delivery.");
        }

        rateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(OrderDetailsActivity.this);
                View sheetView = OrderDetailsActivity.this.getLayoutInflater().inflate(R.layout.bottomsheet_rating, null);
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