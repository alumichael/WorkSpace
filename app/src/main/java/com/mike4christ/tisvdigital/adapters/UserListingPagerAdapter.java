package com.mike4christ.tisvdigital.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mike4christ.tisvdigital.fragment.ChatFragment;

public class UserListingPagerAdapter extends FragmentPagerAdapter {
    private static final Fragment[] sFragments = new Fragment[]{ChatFragment.newInstance(ChatFragment.TYPE_ALL)};
    private static final String[] sTitles = new String[]{"All Users"};

    public UserListingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int position) {
        return sFragments[position];
    }

    public int getCount() {
        return sFragments.length;
    }

    public String getPageTitle(int position) {
        return sTitles[position];
    }
}
