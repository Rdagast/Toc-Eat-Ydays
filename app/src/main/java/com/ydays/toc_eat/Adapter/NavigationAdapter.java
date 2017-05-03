package com.ydays.toc_eat.Adapter;

/**
 * Created by clemb on 22/02/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ydays.toc_eat.Fragment.Navigation.*;



public class NavigationAdapter extends FragmentPagerAdapter {
    public NavigationAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyAdvertFragment().newInstance(position + 1);
                break;
            case 1:
                fragment = new SearchAdvertFragment().newInstance(position + 1);
                break;
            case 2:
                fragment = new MessagingFragment().newInstance(position +1);
                break;
            case 3:
                fragment = new ProfileFragment().newInstance(position +1);
                break;
            default:
                fragment = new SearchAdvertFragment().newInstance(position + 1);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "Mes Annonces";
                break;
            case 1:
                title = "Recherche";
                break;
            case 2:
                title = "Messagerie";
                break;
            case 3:
                title = "Profil";
                break;
        }
        return null;
    }
}