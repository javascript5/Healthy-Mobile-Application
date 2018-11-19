package com.pleng.healthy.healthy.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pleng.healthy.healthy.R;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment>{
    private Context context;
    private List<Comment> comments;
    public CommentAdapter(@NonNull Context context, int resource, List<Comment> comments) {
        super(context, resource, comments);
        this.comments = comments;
        this.context = context;
    }


    @Nullable
    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View commentItem = LayoutInflater.from(context).inflate(
                R.layout.adapter_comment,
                parent,
                false
        );

        TextView commentId = (TextView) commentItem.findViewById(R.id.comment_post_id);
        TextView commentTitle = (TextView) commentItem.findViewById(R.id.comment_id);
        TextView commentBody = (TextView) commentItem.findViewById(R.id.comment_body);
        TextView commentEmail = (TextView) commentItem.findViewById(R.id.comment_email);
        TextView commentName = (TextView) commentItem.findViewById(R.id.comment_name);
        commentId.setText(comments.get(position).postId+"");
        commentTitle.setText(comments.get(position).id+"");
        commentBody.setText(comments.get(position).body);
        commentEmail.setText(comments.get(position).email);
        commentName.setText(comments.get(position).name);


        return commentItem;
    }
}
