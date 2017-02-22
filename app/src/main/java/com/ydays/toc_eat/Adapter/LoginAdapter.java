package com.ydays.toc_eat.Adapter;

/**
 * Created by clemb on 22/02/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ydays.toc_eat.Fragment.LoginFragment.*;


public class LoginAdapter extends FragmentPagerAdapter {
    public LoginAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new LoginFragment().newInstance(position + 1);
                break;
            case 1:
                fragment = new RegisterFragment().newInstance(position + 1);
                break;
            default:
                fragment = new LoginFragment().newInstance(position + 1);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "Login";
                break;
            case 1:
                title = "Register";
                break;
        }
        return title;
    }
}