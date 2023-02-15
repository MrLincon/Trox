package com.netro.trox.fragment.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.netro.trox.R;
import com.netro.trox.activity.OrderDetailsActivity;

public class FragmentPickUpRequest extends Fragment {

    View view;

    RelativeLayout cardLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_picked_up_request, container, false);

        cardLayout = view.findViewById(R.id.card_layout);

        cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("data","FragmentPickUpRequest");
                startActivity(intent);
            }
        });

        return view;
    }
}
