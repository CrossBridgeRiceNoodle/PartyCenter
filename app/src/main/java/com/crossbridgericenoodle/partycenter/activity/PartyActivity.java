package com.crossbridgericenoodle.partycenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.crossbridgericenoodle.partycenter.Api;
import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.model.Party;

/**
 * 这个是晚会的详细页面
 * <p>
 * TODO:晚会详细页面
 */
public class PartyActivity extends AppCompatActivity {
    Party party;
    ListView listView;
    TextView name;
    TextView time;
    TextView location;
    TextView type;
    TextView publisher;
    TextView host;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);
        setTitle(party.name);

        party = (Party) getIntent().getSerializableExtra("party_selected");

        listView = (ListView) findViewById(R.id.lv_comments);
        name = (TextView) findViewById(R.id.tv_current_name);
        time = (TextView) findViewById(R.id.tv_current_time);
        location = (TextView) findViewById(R.id.tv_current_location);
        type = (TextView) findViewById(R.id.tv_current_type);
        publisher = (TextView) findViewById(R.id.tv_current_publisher);
        host = (TextView) findViewById(R.id.tv_current_host);
        poster = (ImageView) findViewById(R.id.iv_current_poster);

        Api.getInstance().getPartyInfo(party.ID, new Api.OnResultListener<Party>() {
            @Override
            public void getResult(Party p) {
                party = p;
            }
        });

        name.setText(party.name);
        time.setText(party.time);
        location.setText(party.location);
        type.setText(party.type);
        publisher.setText(party.publisher);
        host.setText(party.host);
        //TODO ImageView源文件设置，以及显示大小的设置

    }

}
