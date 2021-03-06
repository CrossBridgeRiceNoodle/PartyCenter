package com.crossbridgericenoodle.partycenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crossbridgericenoodle.partycenter.App;
import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.model.Party;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BriefPartyAdapter extends BaseAdapter {
    private List<Party> partyList;
    private Context context;

    public BriefPartyAdapter(List<Party> partyList, Context context) {
        this.partyList = partyList;
        this.context = context;
    }

    public void updateContent(List<Party> partyList) {
        this.partyList = partyList;
        notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        return partyList.size();
    }

    @Override
    public Object getItem(int position) {
        return partyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Party party = (Party) getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.brief_party_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.tv_latest_name);
            viewHolder.time = (TextView) view.findViewById(R.id.tv_latest_time);
            viewHolder.location = (TextView) view.findViewById(R.id.tv_latest_location);
            viewHolder.type = (TextView) view.findViewById(R.id.tv_latest_type);
            viewHolder.poster = (ImageView) view.findViewById(R.id.iv_latest_poster);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(String.valueOf(party.ID));
        viewHolder.time.setText(party.time);
        viewHolder.location.setText(party.location);
        viewHolder.type.setText(party.type);
        Picasso.with(App.getContext()).load(party.poster).into(viewHolder.poster);
        return view;
    }

    private class ViewHolder {
        TextView name;//
        TextView time;//
        TextView location;//
        TextView type;//
        ImageView poster;//海报
    }
}
