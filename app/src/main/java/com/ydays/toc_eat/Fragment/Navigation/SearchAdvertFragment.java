package com.ydays.toc_eat.Fragment.Navigation;

/**
 * Created by clemb on 22/02/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ydays.toc_eat.toc_eat.R;


public class SearchAdvertFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    MapView m;
    public SearchAdvertFragment() {
        // Required empty public constructor
    }

    public static SearchAdvertFragment newInstance(int sectionNumber) {
        SearchAdvertFragment fragment = new SearchAdvertFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searchadvert, container, false);
        m = (MapView) v.findViewById(R.id.mapView);

        return v;

        // Gets the MapView from the XML layout and creates it

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

    }
}
