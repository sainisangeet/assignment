package com.example.sangeet.assignment.contributor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sangeet.assignment.R;

/**
 * Created by Sangeet on 1/7/2018.
 */

public class ContributorArrayAdapter extends BaseAdapter {

    ContributorRepoListModel[] model;
    Context mContext;


    public ContributorArrayAdapter(Context applicationContext, ContributorRepoListModel[] model) {
        mContext = applicationContext;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.length;
    }

    @Override
    public Object getItem(int i) {
        return model[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contributor_repo_list, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.repoName.setText("" + model[position].getName());
        return convertView;

    }

    private class MyViewHolder {
        TextView repoName;

        public MyViewHolder(View item) {
            repoName = (TextView) item.findViewById(R.id.text);

        }
    }

}

