package xxx.test.com.flexibletoast;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author BMR
 * @ClassName: Tools
 * @Description: TODO:
 * @date 2015/9/15 20:42
 */
public class Tools {
    private static long lastClickTime;

    public static void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static boolean isStrEmpty(String str) {
        if (null == str || "".equals(str.trim()) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     *
     * @param activity
     * @return px
     */

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * 获取屏幕的高度
     *
     * @param activity
     * @return px
     */

    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }


    public static String encodeText(String text) {
        if (isStrEmpty(text)) {
            return "";
        }
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (Exception e) {
            //Logger.beginInfo().p("the content of the comment encode exception:").p(e.getMessage()).end();
        }
        return text;
    }

    public static String decodeText(String text) {
        if (isStrEmpty(text)) {
            return "";
        }
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (Exception e) {
            //Logger.beginInfo().p("the content of the comment decode exception:").p(e.getMessage()).end();
        }
        return text;
    }


    /**
     * 判断当前网络是否为wifi
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 为图像添加环形外框
     *
     * @param srcBitmap
     * @param broderThickness
     * @param color
     * @return Bitmap
     */
    public static Bitmap drawCircleBorder(Bitmap srcBitmap, int broderThickness, int color) {

        int width = srcBitmap.getWidth() + 2 * broderThickness;
        int height = srcBitmap.getHeight() + 2 * broderThickness;
        int radius = (width < height ? width : height) / 2;
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        output.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();

        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
       /* 设置paint的style为STROKE：空心 */
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
       /* 设置paint的外框宽度 */
        paint.setStrokeWidth(broderThickness);

        // 画圆
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        canvas.drawBitmap(srcBitmap, broderThickness, broderThickness, paint);
        return output;

    }


    /**
     * 将图像处理为背景透明的圆形头像
     *
     * @param bitmap
     * @return output
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /**
     * 判断连续点击方法
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static int getFileSize(File file) throws Exception {
        int size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }


    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (Tools.isStrEmpty(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        boolean isNormalCharacter = (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
        return !isNormalCharacter;
    }


    /**
     * string类型转换为long类型
     *
     * @param strTime 要转换的String类型的时间
     * @return
     * @throws ParseException
     */
    public static long stringToLong(String strTime) {
        // formatType时间格式
        String formatType = "yyyy-MM-dd HH:mm:ss";
        // strTime的时间格式和formatType的时间格式必须相同
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }


    /**
     * string类型转换为date类型
     *
     * @param strTime    要转换的string类型的时间，formatType要转换的格式
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * date类型转换为long类型
     *
     * @param date 要转换的date类型的时间
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 针对wrapContent和固定数值两种方式测量View的高宽
     * @param view 要测量的view
     * @param mode wrapContent：0； 固定值：1
     * @param width 固定宽度px
     * @param height 固定高度px
     * @return 测量后的View的宽高
     */
    public static int[] measureView(View view, int mode, int width, int height) {
        if (mode == 0) {
            // wrap_content
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            view.measure(widthMeasureSpec, heightMeasureSpec);
            return new int[]{view.getWidth(), view.getHeight()};
        } else if (mode == 1) {
            // fix value
            int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
            int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
            view.measure(widthMeasureSpec, heightMeasureSpec);
            return new int[]{view.getWidth(), view.getHeight()};
        }
        return new int[]{0, 0};

    }


    /**
     * 开启软键盘
     */
    public static void openSoftKeyboard(EditText et) {
        InputMethodManager inputManager = (InputMethodManager)et.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et, 0);
    }

    /**
     * 关闭软键盘
     */
    public static void closeSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity)context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
