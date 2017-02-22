package com.ydays.toc_eat.Fragment.Navigation;

/**
 * Created by clemb on 22/02/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ydays.toc_eat.toc_eat.R;


public class MyAdvertFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MyAdvertFragment() {
        // Required empty public constructor
    }

    public static MyAdvertFragment newInstance(int sectionNumber) {
        MyAdvertFragment fragment = new MyAdvertFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myadvert, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

    }
}