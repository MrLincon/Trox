package com.netro.trox.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.netro.trox.fragment.offers.FragmentOngoingOffers;
import com.netro.trox.fragment.offers.FragmentPromos;

public class ViewpagerAdapterOffers extends FragmentStatePagerAdapter {

    public ViewpagerAdapterOffers(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final Fragment[] fragment = {null};

        if (position == 0) {
            fragment[0] = new FragmentPromos();
        } else if (position == 1) {
            fragment[0] = new FragmentOngoingOffers();
        }
        return fragment[0];
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Available Promos";
        }  else if (position == 1) {
            return "Ongoing Offers";
        }
        return null;
    }

}