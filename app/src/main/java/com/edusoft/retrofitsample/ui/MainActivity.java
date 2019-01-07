package com.edusoft.retrofitsample.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edusoft.retrofitsample.R;
import com.edusoft.retrofitsample.model.PostModel;
import com.edusoft.retrofitsample.util.GetDataService;
import com.edusoft.retrofitsample.util.RetrofitClientService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<PostModel> itemsList;
    PostAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemsList = new ArrayList<>();
        RecyclerView recycleview_sample = findViewById(R.id.recycleview_sample);
        mAdapter = new PostAdapter(this, itemsList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycleview_sample.setLayoutManager(mLayoutManager);
        recycleview_sample.setItemAnimator(new DefaultItemAnimator());
        recycleview_sample.setAdapter(mAdapter);

        fetchPosts();


    }

    class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
        private Context context;
        private List<PostModel> postsList;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView title, body;
            private CardView materialCardView;

            MyViewHolder(View view) {
                super(view);
                //title = view.findViewById(R.id.post_title);
                title = view.findViewById(R.id.text_title);
                body = view.findViewById(R.id.text_body);
                materialCardView = view.findViewById(R.id.post_card);

            }
        }


        PostAdapter(Context context, List<PostModel> postsList) {
            this.context = context;
            this.postsList = postsList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.post_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position){
            holder.body.setText(postsList.get(position).getBody());
            holder.title.setText(postsList.get(position).getTitle());

            holder.materialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked on "+(position+1), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return postsList.size();
        }

    }

    private void fetchPosts() {
        GetDataService apiService =
                RetrofitClientService.getRetrofitInstance().create(GetDataService.class);
        Call<List<PostModel>> call = apiService.getPost();

        call.enqueue(new Callback<List<PostModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {

                List<PostModel> items = null;
                try {
                    items = response.body();
                    //itemsList.clear();
                    assert items != null;
                    itemsList.addAll(items);


                    PostModel model = new PostModel();
                    itemsList.add(model);
                    mAdapter.notifyDataSetChanged();

                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {

            }


        });
    }
}
