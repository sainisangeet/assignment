package com.example.sangeet.assignment.repository;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sangeet.assignment.contributor.ContributorModel;
import com.example.sangeet.assignment.R;
import com.example.sangeet.assignment.contributor.Contributor;


/**
 * Created by Sangeet on 1/7/2018.
 */

public class RepoDetailGridViewAdapter extends BaseAdapter {

    Context context;
    ContributorModel model;
    ContributorModel contributorModelList[];

    public RepoDetailGridViewAdapter(Context  mContext, ContributorModel[] contributorModelList) {
        context=mContext;
        this.contributorModelList = contributorModelList;
    }

    @Override
    public int getCount() {
        return contributorModelList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        rowView = inflater.inflate(R.layout.repo_grid_view, null);
        holder.tv=(TextView) rowView.findViewById(R.id.contributorName);
        holder.img=(ImageView) rowView.findViewById(R.id.contributorImage);

        if(contributorModelList.length>0) {
            holder.tv.setText(contributorModelList[position].getLogin());
            Glide.with(context).load(contributorModelList[position].getAvatar_url()).into(holder.img);
        }
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Contributor.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",contributorModelList[position].getLogin());
                bundle.putString("image",contributorModelList[position].getAvatar_url());
                bundle.putString("repoUrl",contributorModelList[position].getRepos_url());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
