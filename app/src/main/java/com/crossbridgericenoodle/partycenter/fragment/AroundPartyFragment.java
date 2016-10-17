package com.crossbridgericenoodle.partycenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.model.Party;

import java.util.List;

/**
 * 这个是附近晚会的页面
 * TODO: 附近晚会
 */
public class AroundPartyFragment extends Fragment {
    List<Party> partyList;


    public AroundPartyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_around_party, container, false);
    }


}
