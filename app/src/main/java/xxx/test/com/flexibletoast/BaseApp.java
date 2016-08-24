package xxx.test.com.flexibletoast;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by abc on 2016/8/23.
 */
public class BaseApp extends Application {
    // 全局的 handler 对象
    private final Handler appHandler = new Handler();
    // 全局的 Toast 对象
    private FlexibleToast flexibleToast;
    private static BaseApp instance;
    public static void setInstance(BaseApp instance) {
        BaseApp.instance = instance;
    }
    public static BaseApp getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        flexibleToast = new FlexibleToast(this);
        Log.i("TAG","主线程"+Thread.currentThread().getId());

    }
    public Handler getAppHandler() {
        return appHandler;
    }
    public void toastShowByBuilder(final FlexibleToast.Builder builder) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            getAppHandler().post(new Runnable() {
                @Override
                public void run() {
                    flexibleToast.toastShow(builder);
                }
            });
        } else {
            flexibleToast.toastShow(builder);
        }
    }
}
