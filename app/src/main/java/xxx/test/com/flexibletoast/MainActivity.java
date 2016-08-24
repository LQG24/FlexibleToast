package xxx.test.com.flexibletoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view= LayoutInflater.from(this).inflate(R.layout.item,null,false);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","子线程");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG","子线程");
                        FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_CENTER).setFirstText("底部的").setSecondText("提醒");
                        BaseApp.getInstance().toastShowByBuilder(builder);
                    }
                }).start();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlexibleToast.Builder builder = new FlexibleToast.Builder(MainActivity.this).setGravity(FlexibleToast.GRAVITY_BOTTOM).setFirstText("你妹的").setSecondText("哈哈").setImageResource(R.mipmap.ic_launcher).setCustomerView(view);
                BaseApp.getInstance().toastShowByBuilder(builder);
            }
        });
    }
}
