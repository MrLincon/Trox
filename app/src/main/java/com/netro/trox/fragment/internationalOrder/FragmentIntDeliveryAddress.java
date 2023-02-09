package com.netro.trox.fragment.internationalOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.netro.trox.R;
import com.netro.trox.activity.GetQuotationActivity;

public class FragmentIntDeliveryAddress extends Fragment {

    ImageView back;
    RelativeLayout getQuotation;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_int_delivery_address, container, false);

        back = view.findViewById(R.id.back);
        getQuotation = view.findViewById(R.id.get_quotation);

        getQuotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), GetQuotationActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
