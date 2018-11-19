package com.pleng.healthy.healthy.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pleng.healthy.healthy.R;

import java.util.List;

public class PostAdapter extends ArrayAdapter<Post>{
    private Context context;
    private List<Post> posts;
    public PostAdapter(@NonNull Context context, int resource, List<Post> posts) {
        super(context, resource, posts);
        this.posts = posts;
        this.context = context;
    }


    @Nullable
    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View postItem = LayoutInflater.from(context).inflate(
                R.layout.adapter_post,
                parent,
                false
        );

        TextView postId = (TextView) postItem.findViewById(R.id.post_id);
        TextView postTitle = (TextView) postItem.findViewById(R.id.post_title);
        TextView postBody = (TextView) postItem.findViewById(R.id.post_body);

        postId.setText(posts.get(position).id+"");
        postTitle.setText(posts.get(position).title);
        postBody.setText(posts.get(position).body);


        return postItem;
    }
}
