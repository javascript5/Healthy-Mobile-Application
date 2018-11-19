package com.pleng.healthy.healthy.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.pleng.healthy.healthy.AlarmClock.SleepStore;
import com.pleng.healthy.healthy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentFragment extends Fragment {
    private Bundle bundle;
    private int postId;
    private ArrayList<Comment> comments = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private ListView commentListView;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            bundle = getArguments();
            postId = bundle.getInt("postId");

        }

        commentListView = (ListView) getView().findViewById(R.id.comment_listview);
        Button backButton = (Button) getView().findViewById(R.id.back_button_comment);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack("PostFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });
        readApi();

    }


    private void readApi(){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://jsonplaceholder.typicode.com/posts/"+ postId +"/comments")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    comments.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        if(object.getInt("postId") == postId) {
                            comments.add(new Comment(
                                    object.getInt("postId"),
                                    object.getInt("id"),
                                    object.getString("name"),
                                    object.getString("email"),
                                    object.getString("body")
                            ));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if(getActivity() != null) {
                    commentAdapter = new CommentAdapter(
                            getActivity(),
                            R.layout.adapter_post,
                            comments
                    );
                }
                    commentAdapter.notifyDataSetChanged();
                    commentListView.setAdapter(commentAdapter);

            }


        }.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }
}
