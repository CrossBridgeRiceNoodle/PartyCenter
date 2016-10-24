package com.crossbridgericenoodle.partycenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.base.BaseActivity;
import com.crossbridgericenoodle.partycenter.model.Party;

public class UpdatePartyActivity extends BaseActivity implements View.OnClickListener {
    EditText name;
    EditText time;
    EditText location;
    EditText type;
    EditText host;
    EditText publisher;
    Button updateButton;
    Party party;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_party);
        //TODO 在进入这个活动前传入party数据
        party = (Party) getIntent().getSerializableExtra("party_selected");
        initView();
        initViewInfo();

    }

    private void initView() {
        name = (EditText) findViewById(R.id.et_name);
        time = (EditText) findViewById(R.id.et_time);
        location = (EditText) findViewById(R.id.et_location);
        type = (EditText) findViewById(R.id.et_type);
        host = (EditText) findViewById(R.id.et_host);
        publisher = (EditText) findViewById(R.id.et_publisher);
        updateButton = (Button) findViewById(R.id.bt_update);
    }

    private void initViewInfo() {
        name.setText(party.name);
        time.setText(party.time);
        location.setText(party.location);
        type.setText(party.type);
        publisher.setText(party.publisher);
        host.setText(party.host);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update: {
                //TODO 执行更新晚会信息的后台操作哦以及前台跳转操作
                break;
            }
        }
    }
}
