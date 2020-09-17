package com.ipixel.qrscannerapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ipixel.qrscannerapp.fragment.GenerateFragment;
import com.ipixel.qrscannerapp.fragment.HistoryFragment;
import com.ipixel.qrscannerapp.fragment.ScanFragment;

import java.util.ArrayList;


/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mFragmentItems;

    public MainPagerAdapter(FragmentManager fm, ArrayList<String> fragmentItems) {
        super(fm);
        this.mFragmentItems = fragmentItems;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        if(i == 0) {
            fragment = new ScanFragment();
        } else if(i == 1){
            fragment = new GenerateFragment();
        } else if(i == 2){
            fragment = new HistoryFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentItems.size();
    }

}
