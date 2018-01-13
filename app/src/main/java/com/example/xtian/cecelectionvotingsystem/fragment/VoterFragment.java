package com.example.xtian.cecelectionvotingsystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xtian.cecelectionvotingsystem.R;
import com.example.xtian.cecelectionvotingsystem.VoterFragment.VoterFragment_allVoters;
import com.example.xtian.cecelectionvotingsystem.VoterFragment.VoterFragment_notVoter;
import com.example.xtian.cecelectionvotingsystem.VoterFragment.VoterFragment_yesVoter;

/**
 * Created by Xtian on 11/18/2015.
 */
public class VoterFragment extends android.support.v4.app.Fragment {


    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;



    public VoterFragment(){}


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_vote_tabs, container, false);
        tabLayout = (TabLayout)rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return rootView;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new VoterFragment_allVoters();
                case 1:
                    return new VoterFragment_yesVoter();
                case 2:
                    return new VoterFragment_notVoter();

            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "Voters";
                case 1 :
                    return "Voted";
                case 2 :
                    return "Not-Voted";
            }
            return null;
        }
    }

}
