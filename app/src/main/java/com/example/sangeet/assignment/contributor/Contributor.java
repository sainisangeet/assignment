package com.example.sangeet.assignment.contributor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sangeet.assignment.R;
import com.example.sangeet.assignment.repository.RepoDetails;
import com.google.gson.Gson;

/**
 * Created by Sangeet on 1/6/2018.
 */

public class Contributor extends Activity implements View.OnClickListener {

    ImageView backButton, contributorImage;
    ListView repoList;
    ContributorRepoListModel[] model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contributor);

        backButton = (ImageView) findViewById(R.id.back);
        contributorImage = (ImageView) findViewById(R.id.imageContributor);
        repoList = (ListView) findViewById(R.id.repoList);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String imageUrl = intent.getStringExtra("image");
        String repoUrl = intent.getStringExtra("repoUrl");

        Glide.with(getApplicationContext()).load(imageUrl).into(contributorImage);

        backButton.setOnClickListener(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, repoUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Gson gson = new Gson();
                model =  gson.fromJson(response, ContributorRepoListModel[].class);
             //   ContributorArrayAdapter<Item> repoListArray = new ContributorArrayAdapter<Item>(this, R.layout.support_simple_spinner_dropdown_item,model.getItems());
                repoList.setAdapter(new ContributorArrayAdapter(getApplicationContext(),model));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        });

        queue.add(stringRequest);


        repoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(view.getContext(), RepoDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("avatarUrl",model[position].getOwner().getAvatar_url());
                bundle.putString("name",model[position].getName());
                bundle.putString("description",model[position].getDescription());
                bundle.putString("projectLink",model[position].getHtml_url());
                bundle.putString("contributor",model[position].getContributors_url());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
//                Intent intent = new Intent(this, RepoDetails.class);
//                startActivity(intent);
                onBackPressed();
                break;
        }
    }
}
