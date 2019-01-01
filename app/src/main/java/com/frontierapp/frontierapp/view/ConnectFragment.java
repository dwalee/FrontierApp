package com.frontierapp.frontierapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frontierapp.frontierapp.R;
import com.frontierapp.frontierapp.databinding.FragmentConnectBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectFragment extends Fragment {
    private FragmentConnectBinding connectBinding;

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        connectBinding = FragmentConnectBinding.inflate(inflater, container, false);
        init();
        // Inflate the layout for this fragment
        return connectBinding.getRoot();
    }

    private void init() {

    }


}
