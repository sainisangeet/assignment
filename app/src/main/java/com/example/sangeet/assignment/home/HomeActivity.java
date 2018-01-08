package com.example.sangeet.assignment.home;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sangeet.assignment.R;
import com.google.gson.Gson;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerViewRepo;
    EditText editText;
    ImageView filter;
    String searchText;
    String sortBy;
    String orderBy;
    Dialog dialog;
    private RadioGroup radioGroupSortBy, radioGroupOrderBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editText = (EditText) findViewById(R.id.editText);
        filter = (ImageView) findViewById(R.id.filter);
        recyclerViewRepo = (RecyclerView) findViewById(R.id.recycler_view_repo);

        filter.setOnClickListener(this);
        searchText = "angular";
        showRepoData();

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    searchText = editText.getText().toString();
                    showRepoData();
                    return true;
                }
                return false;
            }
        });

    }

    public void showRepoData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "https://api.github.com/search/repositories?";
        url = url + "q=" + searchText;

        if (sortBy != null){
            url = url + "&sort=" + sortBy;
        }
        if (orderBy != null){
            url = url + "&order=" + orderBy;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Gson gson = new Gson();
                RepoModelClass model = gson.fromJson(response, RepoModelClass.class);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewRepo.setLayoutManager(layoutManager);
                recyclerViewRepo.setAdapter(new HomeRecyclerView(model,getApplicationContext()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        });

        queue.add(stringRequest);

    }

    void showDialog(){
        dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.filter_dialog);
        dialog.setCancelable(false);

        radioGroupSortBy = (RadioGroup) dialog.findViewById(R.id.radioGroupSortBy);
        radioGroupOrderBy = (RadioGroup) dialog.findViewById(R.id.radioGroupOrderBY);

        radioGroupSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checked) {
                switch (checked){
                    case R.id.star:
                        sortBy = "stars";
                        break;
                    case R.id.forks:
                        sortBy = "forks";
                        break;
                    case R.id.updated:
                        sortBy = "updated";
                        break;
                }
            }
        });

        radioGroupOrderBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checked) {
                switch (checked){
                    case R.id.desc:
                        orderBy = "desc";
                        break;
                    case R.id.asc:
                        orderBy = "asc";
                        break;
                }
            }
        });
        Button closeButton = (Button) dialog.findViewById(R.id.close);
        Button filterButton = (Button) dialog.findViewById(R.id.filterDialog);

        closeButton.setOnClickListener(HomeActivity.this);
        filterButton.setOnClickListener(HomeActivity.this);

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter:
                showDialog();
                break;
            case R.id.close:
                dialog.dismiss();
                break;
            case R.id.filterDialog:
                dialog.dismiss();
                showRepoData();
                break;
        }
    }
}
