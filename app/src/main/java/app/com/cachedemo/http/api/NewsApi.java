package app.com.cachedemo.http.api;

import app.com.cachedemo.AppConstants;
import app.com.cachedemo.http.domain.NewsEntity;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @作者JTL.
 * @日期2018/3/15.
 * @说明：
 */

public interface NewsApi {
    @GET(AppConstants.HTTP.NEWS)
    Call<NewsEntity> getNews();
}
