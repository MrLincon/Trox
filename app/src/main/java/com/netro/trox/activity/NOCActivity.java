package com.netro.trox.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.netro.trox.R;
import com.netro.trox.adapter.ImageAdapter;
import com.netro.trox.util.Tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NOCActivity extends AppCompatActivity {

    ImageView back;
    CardView btnContinue;

    RecyclerView recyclerView;

    LinearLayout upload, file;
    CoordinatorLayout main;

    private CollectionReference item;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    String userID, ID;

    Dialog popup;

    private static final int PICK_IMG = 1;
    private ArrayList<Uri> imageList = new ArrayList<Uri>();
    private int uploads = 0;
    private DatabaseReference databaseReference;
    int index = 0;

    ImageAdapter adapter;

    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noc);

        main = findViewById(R.id.main);
        back = findViewById(R.id.back);
        btnContinue = findViewById(R.id.btn_continue);
        upload = findViewById(R.id.upload);
        file = findViewById(R.id.file);

        tools = new Tools();

        tools.setLightStatusBar(main, this);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();
        ID = String.valueOf(System.currentTimeMillis());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        popup = new Dialog(this);
        popup.setContentView(R.layout.popup_loading);
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popup.setCancelable(false);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choose();
//                file.setVisibility(View.VISIBLE);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageList.size()!=0){

                    tools.loading(popup,true);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("user_id", userID);

                    FirebaseFirestore.getInstance().collection("int").document(ID).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            upload();
                        }
                    });

                }else{
                    tools.makeSnack(main,"You need to upload your NOC first");
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

    public void loadSelectedImages() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ImageAdapter(imageList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(NOCActivity.this,RecyclerView.HORIZONTAL, false ));
    }

    public void choose() {
        //we will pick images
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMG);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();

                int CurrentImageSelect = 0;

                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                    imageList.add(imageUri);
                    CurrentImageSelect++;
                }
                loadSelectedImages();
            } else {
                Uri imageUri = data.getData();
                if (imageUri != null) {
                    imageList.add(imageUri);
                }
                loadSelectedImages();
            }
        }


    }


    @SuppressLint("SetTextI18n")
    public void upload() {
        final StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for (uploads = 0; uploads < imageList.size(); uploads++) {

            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageList.get(uploads));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] data = baos.toByteArray();

            final StorageReference imageName = ImageFolder.child("image/" + System.currentTimeMillis());

            imageName.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            String url = String.valueOf(uri);
                            SendLink(url, index);

                        }
                    });

                }
            });


        }


    }

    private void SendLink(String url, int counter) {
        index++;
        Log.d("dara", "SendLink: " + index);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("imageLink", FieldValue.arrayUnion(url));


        FirebaseFirestore.getInstance().collection("int").document(ID).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

        if (index == imageList.size()) {
            popup.dismiss();
            imageList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(NOCActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), ParcelOrderDetailsActivity.class);
            intent.putExtra("type", "International");
            startActivity(intent);
        }


    }

    private void finishActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

}