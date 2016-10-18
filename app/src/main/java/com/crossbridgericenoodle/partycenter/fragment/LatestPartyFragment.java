package com.crossbridgericenoodle.partycenter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crossbridgericenoodle.partycenter.Api;
import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.activity.PartyActivity;
import com.crossbridgericenoodle.partycenter.adapter.BriefPartyAdapter;
import com.crossbridgericenoodle.partycenter.model.Party;

import java.util.List;


/**
 * TODO: 最新的晚会
 */
public class LatestPartyFragment extends Fragment {

    ListView listView;
    List<Party> latestPartyList;

    public LatestPartyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest_party, container, false);
        listView = (ListView) view.findViewById(R.id.lv_latest_party);

        Api.getInstance().getNewParties(10, new Api.OnResultListener<List<Party>>() {
            @Override
            public void getResult(List<Party> parties) {
                latestPartyList = parties;
                BriefPartyAdapter adapter = new BriefPartyAdapter(latestPartyList, getContext());
                listView.setAdapter(adapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Party party = latestPartyList.get(position);
                Intent intent = new Intent(getContext(), PartyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("party_selected", party);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }


}
