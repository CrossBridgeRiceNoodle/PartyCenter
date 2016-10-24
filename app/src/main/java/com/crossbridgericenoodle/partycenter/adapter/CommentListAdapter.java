package com.crossbridgericenoodle.partycenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.crossbridgericenoodle.partycenter.R;
import com.crossbridgericenoodle.partycenter.model.Comment;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    //TODO 需要真正的评论实体类
    private List<Comment> commentList;
    private Context context;

    public CommentListAdapter(Context context, List<Comment> commentList) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = (Comment) getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, null);
            viewHolder = new ViewHolder();
            //TODO ViewHolder.域.findViewById()
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //TODO ViewHolder.域.setText()

        return view;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

    }
}
