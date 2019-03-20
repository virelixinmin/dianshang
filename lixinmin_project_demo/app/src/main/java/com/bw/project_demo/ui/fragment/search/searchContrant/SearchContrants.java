package com.bw.project_demo.ui.fragment.search.searchContrant;

import com.bw.project_demo.ui.fragment.search.SearchBeans;

public interface SearchContrants {
    public interface SearchView{
        public void showData(SearchBeans beans);
    }
    public interface SearchPresenter<SearchView>{
        public void attahView(SearchContrants.SearchView detailsView);
        public void requestModel(String id);

    }
    public interface SearchModel{
        public void responseModel(String name, SearchCallBack callBack);
        public interface SearchCallBack{
            public void getData(SearchBeans beans);
        }
    }
}
