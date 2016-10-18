package com.crossbridgericenoodle.partycenter.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.crossbridgericenoodle.partycenter.Api;
import com.crossbridgericenoodle.partycenter.App;
import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.adapter.CommentListAdapter;
import com.crossbridgericenoodle.partycenter.base.BaseActivity;
import com.crossbridgericenoodle.partycenter.model.Party;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是晚会的详细页面
 */
public class PartyActivity extends BaseActivity {
    Party party;
    TextView name;
    TextView time;
    TextView location;
    TextView type;
    TextView publisher;
    TextView host;
    ImageView poster;
    ListView listView;//用于对评论信息进行显示
    List<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        party = (Party) getIntent().getSerializableExtra("party_selected");

        initView();
        initViewInfo();
        getCommentList();
        //TODO 晚会评论区的加载
        listView.setAdapter(new CommentListAdapter(this, commentList));
    }

    /**
     * 对控件的初始化
     */
    private void initView() {
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
        listView = (ListView) findViewById(R.id.lv_comments);
    }

    /**
     * 对每个控件信息的初始化
     */
    private void initViewInfo() {
        name.setText(party.name);
        time.setText(party.time);
        location.setText(party.location);
        type.setText(party.type);
        publisher.setText(party.publisher);
        host.setText(party.host);
        Picasso.with(App.getContext()).load(party.poster).into(poster);
    }

    //TODO 获取真正的评论表
    private List<Comment> getCommentList() {
        commentList = new ArrayList<>();
        return commentList;
    }

}
