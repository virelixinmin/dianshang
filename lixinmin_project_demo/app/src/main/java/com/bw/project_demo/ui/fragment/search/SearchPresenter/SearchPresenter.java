package com.bw.project_demo.ui.fragment.search.SearchPresenter;

import com.bw.project_demo.ui.fragment.search.SearchBeans;
import com.bw.project_demo.ui.fragment.search.SearchModel.SearchModelImpl;
import com.bw.project_demo.ui.fragment.search.searchContrant.SearchContrants;

public class SearchPresenter implements SearchContrants.SearchPresenter {
    SearchContrants.SearchView detailsView;
    private SearchContrants.SearchModel model;

    @Override
    public void attahView(SearchContrants.SearchView detailsView) {
            this.detailsView=detailsView;
            model = new SearchModelImpl();
    }

    @Override
    public void requestModel(String name) {
            model.responseModel(name, new SearchContrants.SearchModel.SearchCallBack() {
                @Override
                public void getData(SearchBeans beans) {
                    detailsView.showData(beans);
                }
            });
    }
}
