package app.com.cachedemo;

/**
 * @作者JTL.
 * @日期2018/3/15.
 * @说明：
 */

public class AppConstants {
    public static final String TAG="CacheDemo";
    public static final int READ_TIME=5*1000;
    public static final int WRITE_TIME=5*1000;
    public static final int CONNECT_TIME=5*1000;
    public static class HTTP {
        public static final String BASE_URL = "https://news-at.zhihu.com/api/";
        public static final String NEWS = "4/news/latest";
    }
}
