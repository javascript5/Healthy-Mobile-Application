package com.pleng.healthy.healthy.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.pleng.healthy.healthy.AlarmClock.SleepForm;
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

public class PostFragment extends Fragment{
    private ArrayList<Post> post = new ArrayList<>();
    private ListView postListView;
    private PostAdapter postAdapter;
    private Bundle bundle;
    private CommentFragment obj;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postListView = (ListView) getView().findViewById(R.id.post_listview);
        Button backButton = (Button) getView().findViewById(R.id.back_button_post);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack("Menu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://jsonplaceholder.typicode.com/posts")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        post.add(new Post(object.getInt("userId"),
                                object.getInt("id"),
                                object.getString("title"),
                                object.getString("body")
                        ));
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
                        postAdapter = new PostAdapter(
                                getActivity(),
                                R.layout.adapter_post,
                                post
                        );
                    }
                    postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            bundle = new Bundle();
                            bundle.putInt("postId", postAdapter.getItem(position).id);
                            fm = getActivity().getSupportFragmentManager();
                            ft = fm.beginTransaction();
                            obj = new CommentFragment();
                            obj.setArguments(bundle);
                            ft.replace(R.id.main_view, obj).addToBackStack("PostFragment");
                            ft.commit();
                        }
                    });
                    postAdapter.notifyDataSetChanged();
                    postListView.setAdapter(postAdapter);

            }


        }.execute();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }
}
