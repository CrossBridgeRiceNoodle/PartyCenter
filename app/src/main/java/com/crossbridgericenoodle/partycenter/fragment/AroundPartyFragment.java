package com.crossbridgericenoodle.partycenter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.activity.PartyActivity;
import com.crossbridgericenoodle.partycenter.adapter.BriefPartyAdapter;
import com.crossbridgericenoodle.partycenter.model.Party;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是附近晚会的页面
 * TODO: 附近晚会
 */
public class AroundPartyFragment extends Fragment {
    List<Party> partyList;
    ListView listView;

    public AroundPartyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_around_party, container, false);
        listView = (ListView) view.findViewById(R.id.lv_around_party);

        partyList = getPartiesByLocation();

        BriefPartyAdapter adapter = new BriefPartyAdapter(partyList, getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Party party = partyList.get(position);
                Intent intent = new Intent(getContext(), PartyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("party_selected", party);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    //TODO 根据当前位置获取周围晚会实例
    private List<Party> getPartiesByLocation() {
        return new ArrayList<>();
    }


}
