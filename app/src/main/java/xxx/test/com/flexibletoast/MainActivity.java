package xxx.test.com.flexibletoast;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View customview= LayoutInflater.from(this).inflate(R.layout.item,null,false);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","子线程");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG","子线程");
                        FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_BOTTOM).setFirstText("底部").setSecondText("提醒");
                        BaseApp.getInstance().toastShowByBuilder(builder);
                    }
                }).start();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_BOTTOM).setCustomerView(customview);
                BaseApp.getInstance().toastShowByBuilder(builder);
            }
        });
        final Button button3 = (Button) findViewById(R.id.button3);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(button3,"normalSnackbar", LENGTH_LONG).setActionTextColor(getResources().getColor(R.color.white))
                    /*    .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_CENTER).setFirstText("回调dismiss");
                        BaseApp.getInstance().toastShowByBuilder(builder);
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);
                        FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_CENTER).setFirstText("回调show");
                        BaseApp.getInstance().toastShowByBuilder(builder);
                    }
                })*/
                        .setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_CENTER).setFirstText("中间toast");
                        BaseApp.getInstance().toastShowByBuilder(builder);
                    }
                });
                snackbar.show();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.button4),"自定义",Snackbar.LENGTH_LONG);
                View view = snackbar.getView();
                ViewGroup.LayoutParams Params = view.getLayoutParams();
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(Params.width,Params.height);
                layoutParams.gravity = Gravity.TOP;
//                TODO 为什么设置居中没反应
//                layoutParams.gravity = Gravity.CENTER;为什么设置居中没反应

                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


//                view.setAnimation();
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(100,100));
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.lufei));
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) view;
                snackbarLayout.setOrientation(LinearLayout.HORIZONTAL);
                snackbarLayout.addView(imageView);
                snackbar.setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_CENTER).setFirstText("自定义的snackbar");
                        BaseApp.getInstance().toastShowByBuilder(builder);
                    }
                }).show();

            }
        });
    }
}
