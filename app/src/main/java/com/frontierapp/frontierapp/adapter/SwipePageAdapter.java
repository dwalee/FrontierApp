package com.frontierapp.frontierapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.frontierapp.frontierapp.model.Skill;
import com.frontierapp.frontierapp.model.User;
import com.frontierapp.frontierapp.register_image_upload;
import com.frontierapp.frontierapp.register_skills;
import com.frontierapp.frontierapp.view.register_fragment;
import com.frontierapp.frontierapp.viewmodel.RegisterBasicInfoFragment;

public class SwipePageAdapter extends FragmentStatePagerAdapter {
    Fragment pageFragment;
    User userInfo;
    Skill skill;
    RegisterBasicInfoFragment registerBasicInfo;


    public SwipePageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                pageFragment = new register_fragment();

                //if(pageFragment)
                break;

            case 1:
                pageFragment = new register_skills();
                //userInfo = new User();
                break;

            case 2:
                pageFragment = new register_image_upload();
                //skill = new Skill();

                break;

        }


        return pageFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }


}
