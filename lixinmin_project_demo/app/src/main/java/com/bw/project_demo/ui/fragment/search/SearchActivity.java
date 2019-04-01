package com.bw.project_demo.ui.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.search.SearchPresenter.SearchPresenter;
import com.bw.project_demo.ui.fragment.search.adapter.MySearchAdapter;
import com.bw.project_demo.ui.fragment.search.searchContrant.SearchContrants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchContrants.SearchView {

    @BindView(R.id.search_rcy)
    RecyclerView searchRcy;
//    @BindView(R.id.edit_search)
//    TextView editSearch;
    private SearchContrants.SearchPresenter presenter;
    private String search_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        search_name = getIntent().getStringExtra("search_name");
        presenter = new SearchPresenter();
        presenter.attahView(this);
        presenter.requestModel(search_name);

    }

    @Override
    public void showData(SearchBeans beans) {

        List<SearchBeans.ResultBean> result = beans.getResult();
        if (result.size()==0){
            Intent in = new Intent(SearchActivity.this,SearchErrorActivity.class);
            in.putExtra("shop",search_name);
            startActivity(in);

            finish();
        }else if (result.size()>0){
            EventBus.getDefault().postSticky(search_name);
            MySearchAdapter adapter = new MySearchAdapter(SearchActivity.this, result);
            GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 2);
            searchRcy.setLayoutManager(manager);
            searchRcy.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(search_name);
    }
}
