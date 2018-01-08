package com.example.sangeet.assignment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sangeet.assignment.R;
import com.example.sangeet.assignment.repository.RepoDetails;

/**
 * Created by Sangeet on 1/6/2018.
 */

public class HomeRecyclerView extends RecyclerView.Adapter<HomeRecyclerView.MyViewHolder> {

    private Context mContext;
    View itemView;
    RepoModelClass model;

    HomeRecyclerView(RepoModelClass model, Context context){
        this.model = model;
        this.mContext = context;
       // Log.i("", "HomeRecyclerView: " + this.model.getTotal_count());
     //   Toast.makeText(mContext,"" + model.getTotal_count(),Toast.LENGTH_LONG).show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, fullName;
        private TextView watcherCount, commitCount;
        public ImageView avatarImage;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            fullName = (TextView) view.findViewById(R.id.fullName);
            watcherCount = (TextView) view.findViewById(R.id.watcherCount);
            commitCount = (TextView) view.findViewById(R.id.commitCount);
            avatarImage = (ImageView) view.findViewById(R.id.imageRepo);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText("" + model.getItems().get(position).getName());
        holder.fullName.setText("" + model.getItems().get(position).getFull_name());
        holder.watcherCount.setText("" + model.getItems().get(position).getWatchers() + " (Watchers)");
        holder.commitCount.setText("" + model.getItems().get(position).getForks() + " (Forks)");
        Glide.with(mContext).load(model.getItems().get(position).getOwner().getAvatar_url()).into(holder.avatarImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(),RepoDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("avatarUrl",model.getItems().get(position).getOwner().getAvatar_url());
                bundle.putString("url",model.getItems().get(position).getUrl());
                bundle.putString("name",model.getItems().get(position).getName());
                bundle.putString("description",model.getItems().get(position).getDescription());
                bundle.putString("projectLink",model.getItems().get(position).getHtml_url());
                bundle.putString("contributor",model.getItems().get(position).getContributors_url());
                intent.putExtras(bundle);
                itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (model.getItems() != null && model.getItems().size() > 0) {
            return this.model.getItems().size() > 10 ? 10 : this.model.getItems().size();
        }else {
            return 0;
        }
    }

}
