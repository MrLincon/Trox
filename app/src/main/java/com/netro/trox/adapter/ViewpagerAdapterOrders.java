package com.netro.trox.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.netro.trox.fragment.orders.FragmentDelivered;
import com.netro.trox.fragment.orders.FragmentPickUpRequest;
import com.netro.trox.fragment.orders.FragmentPickedUp;
import com.netro.trox.fragment.orders.FragmentReturned;

public class ViewpagerAdapterOrders extends FragmentStatePagerAdapter {

    public ViewpagerAdapterOrders(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment[] fragment = {null};

        if (position == 0) {
            fragment[0] = new FragmentPickUpRequest();
        } else if (position == 1) {
            fragment[0] = new FragmentPickedUp();
        }else if (position == 2) {
            fragment[0] = new FragmentDelivered();
        }else if (position == 3) {
            fragment[0] = new FragmentReturned();
        }
        return fragment[0];
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Pickup Requested";
        }  else if (position == 1) {
            return "Picked Up";
        } else if (position == 2) {
            return "Delivered";
        }else if (position == 3) {
            return "Returned";
        }
        return null;
    }

}