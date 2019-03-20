package com.bw.project_demo.ui.fragment.search.widght;



import com.bw.project_demo.ui.fragment.search.SearchBeans;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MySearchServiceApp {
    @GET("small/commodity/v1/findCommodityByKeyword")
    Observable<SearchBeans> getSearchData(@Query("keyword")String name,@Query("page")int page,@Query("count")int count);
}
