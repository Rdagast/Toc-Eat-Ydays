package com.ydays.toc_eat.Fragment.Navigation;

/**
 * Created by clemb on 22/02/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ydays.toc_eat.toc_eat.HomeActivity;
import com.ydays.toc_eat.toc_eat.R;

import static com.ydays.toc_eat.toc_eat.R.*;
import static com.ydays.toc_eat.toc_eat.R.layout.fragment_modify_profile;
import static com.ydays.toc_eat.toc_eat.R.layout.fragment_profile;


public class ProfileFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(int sectionNumber) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
       /* Button btnModify;

        btnModify = (Button) view.findViewById(id.buttonmodify);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }); */
    }
}