package com.example.sangeet.assignment.repository;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sangeet.assignment.contributor.ContributorModel;
import com.example.sangeet.assignment.R;
import com.google.gson.Gson;

/**
 * Created by Sangeet on 1/6/2018.
 */

public class RepoDetails extends Activity implements View.OnClickListener {

    public TextView name, projectLink;
    private TextView description, contributorName;
    public ImageView avatarImage, backButton, contributorImage;
    private GridView gridView;
    String contributor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repo_details);

        Intent intent = getIntent();
        String avtarUrl = intent.getStringExtra("avatarUrl");
        String nameString = intent.getStringExtra("name");
        String projectLinkString = intent.getStringExtra("projectLink");
        String descriptionString = intent.getStringExtra("description");
        contributor = intent.getStringExtra("contributor");

        name = (TextView) findViewById(R.id.repoName);
        projectLink = (TextView) findViewById(R.id.projectLink);
        description = (TextView) findViewById(R.id.description);
        gridView = (GridView) findViewById(R.id.gridview);
        avatarImage = (ImageView) findViewById(R.id.repoImage);
    //    contributorImage = (ImageView) findViewById(R.id.contributorImage);
        backButton = (ImageView) findViewById(R.id.backRepo);

        projectLink.setOnClickListener(this);
        backButton.setOnClickListener(this);

        Glide.with(getApplicationContext()).load(avtarUrl).into(avatarImage);
        name.setText("" + nameString);
        description.setText("" + descriptionString);
        projectLink.setText("" + projectLinkString);
     //   contributorImage.setOnClickListener(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, contributor , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                progressDialog.hide();
                Gson gson = new Gson();
                ContributorModel[] model =  gson.fromJson(response, ContributorModel[].class);
                if (model != null && model.length>0) {
                    gridView.setAdapter(new RepoDetailGridViewAdapter(getApplicationContext(), model));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        });

        queue.add(stringRequest);

}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.contributorImage:
//                Intent intent = new Intent(this, Contributor.class);
//                Bundle bundle = new Bundle();
//           //     bundle.putString("url",);
//                startActivity(intent);
//                break;
            case R.id.backRepo:
//                Intent intent1 = new Intent(this, HomeActivity.class);
//                startActivity(intent1);
                onBackPressed();
                break;
        }
    }
}
