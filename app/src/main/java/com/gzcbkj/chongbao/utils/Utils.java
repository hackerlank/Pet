package com.gzcbkj.chongbao.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.gzcbkj.chongbao.BaseApplication;
import com.gzcbkj.chongbao.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public final static String GIF_EXTENSION = ".gif";
    public final static String IMAGE_EXTENSION = ".jpg";

    /**
     * @param context
     * @param permission
     * @return
     */
    public static boolean isGrantPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    public static String createImagePath(Context context) {
        String fileName = UUID.randomUUID().toString() + IMAGE_EXTENSION;
        String dirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/download";
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory())
            file.mkdirs();
        String filePath = dirPath + "/" + fileName;
        return filePath;
    }

    public static String getSaveFilePath(Context context, String fileName) {
        String dirPath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/download";
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory())
            file.mkdirs();
        String filePath = dirPath + "/" + fileName;
        return filePath;
    }

    /**
     * 保存JPG图片
     *
     * @param bmp
     */
    public static String saveJpegByFileName(Bitmap bmp, String fileName, Context context) {
        String folder = getSaveFilePath(context, fileName);
        FileOutputStream fout = null;
        BufferedOutputStream bos = null;
        try {
            fout = new FileOutputStream(folder);
            bos = new BufferedOutputStream(fout);
            bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return folder;
    }

    public static String getMoneyValue(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(value);
    }

    /**
     * 保存JPG图片
     *
     * @param bmp
     */
    public static String saveJpeg(Bitmap bmp, Context context) {
        String folder = createImagePath(context);
        FileOutputStream fout = null;
        BufferedOutputStream bos = null;
        try {
            fout = new FileOutputStream(folder);
            bos = new BufferedOutputStream(fout);
            bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return folder;
    }


    /**
     * 复制文件
     *
     * @param oldPath
     * @param newPath
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean isSuccessful = false;
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                inStream = new FileInputStream(oldPath); //读入原文件
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                isSuccessful = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccessful = false;
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                    inStream = null;
                }
                if (fs != null) {
                    fs.close();
                    fs = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                isSuccessful = false;
            }
        }
        return isSuccessful;
    }


    /**
     * dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    @SuppressLint("SimpleDateFormat")
    private static String getTimeStrOnlyHour(long time) {
        SimpleDateFormat mSdf = new SimpleDateFormat("HH:mm");
        Date dt = new Date(time);
        return mSdf.format(dt);
    }

    public static String getDateString(int time, String keyString) {
        return String.format(keyString, time);
    }


    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * @param defaultId
     * @param path
     * @param iv
     * @Param File file
     */
    public static void loadImage(File file, int defaultId, String path, ImageView iv) {
        if (file != null && file.exists()) {
            loadImage(defaultId, Uri.fromFile(file), iv);
        } else {
            loadImage(defaultId, path, iv);
        }
    }


    /**
     * @param defaultId
     * @param path
     * @param iv
     * @Param File file
     */
    public static void loadImage(File file, int defaultId, String path, ImageView iv, String fileName) {
        if (file != null && file.exists()) {
            if (fileName.endsWith(GIF_EXTENSION)) {
                Glide.with(BaseApplication.getAppContext())
                        .load(Uri.fromFile(file))
                        .asGif()
                        .placeholder(defaultId)
                        .error(defaultId)//load失敗的Drawable
                        .centerCrop()//中心切圖, 會填滿
                        .fitCenter()//中心fit, 以原本圖片的長寬為主
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontAnimate()
                        .into(iv);
            } else {
                Glide.with(BaseApplication.getAppContext())
                        .load(Uri.fromFile(file))
                        .placeholder(defaultId)
                        .error(defaultId)//load失敗的Drawable
                        .centerCrop()//中心切圖, 會填滿
                        .fitCenter()//中心fit, 以原本圖片的長寬為主
                        .dontAnimate()
                        .into(iv);
            }
        } else {
            if (fileName.endsWith(GIF_EXTENSION)) {
                Glide.with(BaseApplication.getAppContext())
                        .load(path)
                        .asGif()
                        .placeholder(defaultId)
                        .error(defaultId)//load失敗的Drawable
                        .centerCrop()//中心切圖, 會填滿
                        .fitCenter()//中心fit, 以原本圖片的長寬為主
                        .dontAnimate()
                        .into(iv);
            } else {
                Glide.with(BaseApplication.getAppContext())
                        .load(path)
                        .placeholder(defaultId)
                        .error(defaultId)//load失敗的Drawable
                        .centerCrop()//中心切圖, 會填滿
                        .fitCenter()//中心fit, 以原本圖片的長寬為主
                        .dontAnimate()
                        .into(iv);
            }
        }
    }

    /**
     * @param defaultId
     * @param path
     * @param iv
     */
    public static void loadImages(int defaultId, String path, ImageView iv) {
        ArrayList<String> list = Utils.getUrlList(path);
        if (list.size() > 0) {
            Utils.loadImage(defaultId, list.get(0), iv);
        } else {
            iv.setImageResource(defaultId);
        }
    }

    /**
     * @param defaultId
     * @param path
     * @param iv
     */
    public static void loadImage(int defaultId, String path, ImageView iv) {
        if (iv.getMeasuredWidth() > 0 && iv.getMeasuredHeight() > 0) {
            Glide.with(BaseApplication.getAppContext())
                    .load(path)
                    .override(iv.getMeasuredWidth(), iv.getMeasuredHeight())
                    .error(defaultId)//load失敗的Drawable
                    .placeholder(defaultId)
                    .centerCrop()//中心切圖, 會填滿
                    .dontAnimate()
                    .fitCenter()//中心fit, 以原本圖片的長寬為主
                    .into(iv);
        } else {
            Glide.with(BaseApplication.getAppContext())
                    .load(path)
                    .error(defaultId)//load失敗的Drawable
                    .placeholder(defaultId)
                    .centerCrop()//中心切圖, 會填滿
                    .fitCenter()//中心fit, 以原本圖片的長寬為主
                    .dontAnimate()
                    .into(iv);
        }

    }

    /**
     * @param defaultId
     * @param uri
     * @param iv
     */
    public static void loadImage(int defaultId, Uri uri, ImageView iv) {
        if (iv.getMeasuredWidth() > 0 && iv.getMeasuredHeight() > 0) {
            Glide.with(BaseApplication.getAppContext())
                    .load(uri)
                    .placeholder(defaultId)
                    .override(iv.getMeasuredWidth(), iv.getMeasuredHeight())
                    .error(defaultId)//load失敗的Drawable
                    .centerCrop()//中心切圖, 會填滿
                    .fitCenter()//中心fit, 以原本圖片的長寬為主
                    .dontAnimate()
                    .into(iv);
        } else {
            Glide.with(BaseApplication.getAppContext())
                    .load(uri)
                    .placeholder(defaultId)
                    .error(defaultId)//load失敗的Drawable
                    .centerCrop()//中心切圖, 會填滿
                    .fitCenter()//中心fit, 以原本圖片的長寬為主
                    .dontAnimate()
                    .into(iv);
        }
    }


    /**
     * 从Assets中读取图片
     *
     * @param fileName
     * @return
     */
    public static Bitmap getImageFromAssetsFile(Resources resources, String fileName) {
        Bitmap image = null;
        InputStream is = null;
        AssetManager am = resources.getAssets();
        try {
            is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }


    public static Bitmap rotateBmp(Bitmap bmp, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap newBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, false);
        bmp.recycle();
        bmp = null;
        return newBmp;
    }

    /**
     * 判断两个时间是不是同一天时间
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameDayTime(long time1, long time2) {
        if (Math.abs(time1 - time2) > 24 * 3600 * 1000) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(time2);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        return day1 == day2;
    }

    public static void setSubText(TextView tv, String text, String subText, int textColor, int subTextColor) {
        int index = text.indexOf(subText);
        if (index >= 0) {
            SpannableString ss = new SpannableString(text);
            ss.setSpan(new ForegroundColorSpan(subTextColor), index, index + subText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.setText(ss);
            tv.setTextColor(textColor);
        } else {
            tv.setText(text);
            tv.setTextColor(textColor);
        }
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断设备上是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isPhoneCorrect(String phone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    // 获取Glide磁盘缓存大小
    public static String getCacheSize() {
        try {
            return getFormatSize(getFolderSize(new File(BaseApplication.getAppContext().getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
            return "0M";
        }
    }

    // 获取指定文件夹内所有文件大小的和
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    // 格式化单位
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


    // 获取Glide磁盘缓存大小
    public static String replaceHtmlText(String text) {
        return text.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&gt;", ">")
                .replaceAll("&#39;", "'")
                .replaceAll("&quot;", "\"");
    }

    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(strTime);
        return date;
    }

    public static String transformTime(Context context, String strTime) {
        if (TextUtils.isEmpty(strTime)) {
            return "";
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(strTime);
            long time = date.getTime();
            long currentTime = System.currentTimeMillis();
            if (currentTime < time) {
                return strTime;
            }
            if (currentTime - time < 60 * 1000) {
                return ((currentTime - time) / 1000) + context.getString(R.string.second_ago);
            }
            if (currentTime - time < 60 * 60 * 1000) {
                return ((currentTime - time) / (60 * 1000)) + context.getString(R.string.minute_ago);
            }
            if (currentTime - time < 24 * 60 * 60 * 1000) {
                return ((currentTime - time) / (60 * 60 * 1000)) + context.getString(R.string.hour_ago);
            }
            return ((currentTime - time) / (24 * 60 * 60 * 1000)) + context.getString(R.string.day_ago);
        } catch (Exception e) {

        }
        return strTime;
    }


    /**
     *
     */
    public static String getNewText(int num) {
        return num < 10 ? ("0" + num) : String.valueOf(num);
    }


    public static ArrayList<String> getUrlList(String orginUrl) {
        ArrayList<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(orginUrl)) {
            return list;
        }
        String[] urls = orginUrl.split(",");
        if (urls != null && urls.length > 0) {
            for (String url : urls) {
                if (url.startsWith("http")) {
                    list.add(url);
                } else if (list.size() > 0) {
                    String lastUrl = list.get(list.size() - 1) + "," + url;
                    list.set(list.size() - 1, lastUrl);
                }
            }
        } else {
            list.add(orginUrl);
        }
        return list;
    }

    public static String replaceHtmlImage(String html) {
        String regxpForImgTag = "<img\\s[^>]+/>";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String temp = matcher.group();
            html = html.replace(temp, "");
        }
        return html;
    }
}


