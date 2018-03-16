package app.com.cachedemo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import app.com.cachedemo.GlideApp;
import app.com.cachedemo.http.domain.NewsEntity;
import app.com.cachedemo.R;

/**
 * @作者JTL.
 * @日期2018/3/15.
 * @说明：
 */

public class NewsApapter extends BaseQuickAdapter<NewsEntity.StoriesBean,BaseViewHolder> {
    private Context context;
    public NewsApapter(Context context,@Nullable List<NewsEntity.StoriesBean> data) {
        //注意这里直接把RecyclerView的item的Layout布局写在这里
        super(R.layout.news_item, data);
        this.context=context;
    }

    //设置新的数据源并刷新
    @Override
    public void setNewData(@Nullable List<NewsEntity.StoriesBean> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity.StoriesBean item) {
        helper.setText(R.id.tv_home_title,item.getTitle());
        GlideApp.with(context)
                .load(item.getImages().get(0))
                .centerInside()
                .into((ImageView) helper.getView(R.id.iv_home_img));
    }
}
