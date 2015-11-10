package com.project.workgroup.piuradivierte.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.workgroup.piuradivierte.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisEventosFragment extends Fragment   {

    public static final String ARG_SECTION_TITLE = "section_number";

    public static MisEventosFragment newInstance(String sectionTitle) {
        Bundle args = new Bundle();
        MisEventosFragment fragment = new MisEventosFragment();
        args.getString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public MisEventosFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View roorView = inflater.inflate(R.layout.fragment_miseventos, container, false);

        return roorView;
    }


}
