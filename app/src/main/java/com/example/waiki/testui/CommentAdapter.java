package com.example.waiki.testui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by waiki on 10/19/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    TextView comment_content,comment_date,comment_user;


    public CommentAdapter(Context context, int resource, List<Comment> comment) {
        super(context, resource, comment);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_custome, parent, false);
        }

        comment_content=(TextView)convertView.findViewById(R.id.comment_comment);
        comment_date=(TextView)convertView.findViewById(R.id.comment_date);
        comment_user=(TextView)convertView.findViewById(R.id.comment_user);

        comment_content.setText("Date     :"+comment.getComment());
        comment_date.setText("User    :"+comment.getDate());
        comment_user.setText("Comment :"+comment.getUserid());

        return convertView;

    }
}
