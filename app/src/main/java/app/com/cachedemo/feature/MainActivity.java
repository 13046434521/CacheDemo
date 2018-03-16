package app.com.cachedemo.feature;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import app.com.cachedemo.AppConstants;
import app.com.cachedemo.http.api.NewsApi;
import app.com.cachedemo.http.domain.NewsEntity;
import app.com.cachedemo.helper.OkHttpClientHelper;
import app.com.cachedemo.R;
import app.com.cachedemo.adapter.NewsApapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private NewsApapter myAdapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();
    }

    //Android 6.0以上的权限申请
    private void initPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        else{
            init();
        }
    }

    private void init() {
        refreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(this);
        myAdapter = new NewsApapter(this,null);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(myAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();
    }

    private void getData() {
        if (!refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(true);
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.HTTP.BASE_URL)
                .client(OkHttpClientHelper.getInstance(this).getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApi newsApi = retrofit.create(NewsApi.class);
        newsApi.getNews().enqueue(new Callback<NewsEntity>() {
            @Override
            public void onResponse(Call<NewsEntity> call, Response<NewsEntity> response) {
                //请求成功
                getDataSuccess(response.body().getStories());
            }

            @Override
            public void onFailure(Call<NewsEntity> call, Throwable t) {
                //请求失败
                getDataFailure(t.getMessage());
            }
        });
    }

    private void getDataSuccess(List<NewsEntity.StoriesBean> list){
        myAdapter.setNewData(list);

        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

    private void getDataFailure(String msg) {
        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        Toast.makeText(MainActivity.this,"加载失败，失败原因："+msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
            init();
        }
        else{
            finish();
        }
    }
}
