package com.kun.common.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.kun.common.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * imageloader
 *
 * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
 * String imageUri = "assets://image.png"; // from assets
 * String imageUri = "drawable://" + R.drawable.image; // from drawables
 * String imageUri = "content://media/external/audio/albumart/13"; //
 */

public class ImageHelper {
  private static String TAG = "ImageUtils";

  public static ImageLoader getImageLoader() {
    return ImageLoader.getInstance();
  }


  public static void setBgImageHeight(Activity mContext, ImageView mImageView,
      int mHeight, int drawableId, boolean type) {
    setBgImageHeight(mContext, mImageView, mHeight, BitmapFactory.decodeResource(mContext.getResources(),
        drawableId), type);
  }

  public static void setBgImageHeight(Context mContext, ImageView mImageView,
      int mHeight, Bitmap bitmap, boolean type) {
    try {
      int width = bitmap.getWidth();// 获取真实宽高
      int height = bitmap.getHeight();
      int layoutWidth = (mHeight * width) / height;// 调整高度
      if (type) {//LinearLayout
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(layoutWidth, mHeight);
        mImageView.setLayoutParams(lp);
        mImageView.setImageBitmap(bitmap);
      } else {
        // 重新设置宽度和高度  RelativeLayout
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(layoutWidth, mHeight);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mImageView.setLayoutParams(lp);
        mImageView.setImageBitmap(bitmap);
      }

      // drawable = null;
      bitmap = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置图片宽度为整个屏幕
   *
   * @param imageView 设置miageview
   * @param bitmap    设置bitmap
   */
  public static void setImageSizeFullwidth(Context context, ImageView imageView, Bitmap bitmap, int width) {
    try {
      int widthReal = bitmap.getWidth();// 获取真实宽高
      int heightReal = bitmap.getHeight();
      if (width == 0) {
        width = PhoneHelper.SCREEN_WIDTH;
      }
      int layoutHeight = (width * heightReal) / widthReal;
      ViewGroup.LayoutParams params = imageView.getLayoutParams();
      params.width = width;
      params.height = layoutHeight;
      imageView.setLayoutParams(params);
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      imageView.setImageBitmap(bitmap);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置图片宽度为整个屏幕
   *
   * @param context   设置上下文
   * @param imageView 设置miageview
   * @param bitmap    设置bitmap
   */
  public static void setImagewidth(Activity context, ImageView imageView, Bitmap bitmap, int paddingid) {
    try {
      int width = bitmap.getWidth();// 获取真实宽高
      int height = bitmap.getHeight();
      int layoutWidth =  PhoneHelper.SCREEN_WIDTH;
      layoutWidth = layoutWidth - (int) (context.getResources().getDimension(paddingid) * 2);
      int layoutHeight = (layoutWidth * height) / width;
      imageView.setImageBitmap(bitmap);
      ViewGroup.LayoutParams params = imageView.getLayoutParams();
      params.width = layoutWidth;
      params.height = layoutHeight;
      imageView.setLayoutParams(params);
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * @param bitmap 设置图片大小使用dp为单位
   */
  public static void setBgImageHeight(Context mContext, ImageView mImageView,
      int mWidthdp, int mHeightdp, Bitmap bitmap) {

    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        mWidthdp, mHeightdp);
    mImageView.setLayoutParams(lp);
    mImageView.setImageBitmap(bitmap);
    bitmap = null;
  }


  /**
   * 获取图片
   */
  public static Bitmap getImage(String imageAdderss) {
    try {
      if (imageAdderss != null && !imageAdderss.equals("null")) {
        URL url;
        url = new URL(imageAdderss);
        HttpURLConnection conn = (HttpURLConnection) url
            .openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(6000);
        // 获取服务器返回回来的流
        InputStream is = conn.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
          bos.write(buffer, 0, len);
        }
        bos.flush();
        byte[] result = bos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0,
            result.length);
        return bitmap;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 设置控件宽高度
   */
  public static void setViewSize(View v, int width, int heigth) {
    ViewGroup.LayoutParams lp = v.getLayoutParams();
    lp.width = width;
    lp.height = heigth;
    v.setLayoutParams(lp);
  }

  public static boolean saveBitmapToCameraPath(Bitmap bmp){
    String fileName="";
    if(Build.BRAND .equals("Xiaomi") ){ // 小米手机
      fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+new Date()+".JPEG" ;
    }else{  // Meizu 、Oppo
      fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/"+new Date()+".JPEG" ;
    }
    return saveBitmap(bmp,fileName);
  }

  public static boolean  saveBitmap(Bitmap bmp, String path) {
    Bitmap.CompressFormat format = CompressFormat.PNG;
    OutputStream stream = null;
    SdCardHelper.createFile(path);
    try {
      stream = new FileOutputStream(path);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return bmp.compress(format, 100, stream);

  }

  /**
   * 获取imageloader配置项
   */
  public static DisplayImageOptions getDisplayImageOptions() {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(true)//设置下载的图片是否缓存在内存中
        .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
        .showImageOnFail(R.drawable.mainlib_photo_default)
        .showImageForEmptyUri(R.drawable.mainlib_photo_default)
        .build();//构建完成
    return options;
  }
  /**
   * 获取imageloader配置项
   */
  public static DisplayImageOptions getDisplayImageOptionsNoCache() {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(false)//设置下载的图片是否缓存在内存中
        .cacheOnDisk(false)//设置下载的图片是否缓存在SD卡中
        .showImageOnFail(R.drawable.mainlib_photo_default)
        .build();//构建完成
    return options;
  }

  /**
   * 获取imageloader配置项
   */
  public static DisplayImageOptions getDisplayImageOptionsScantypeCenter() {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(true)//设置下载的图片是否缓存在内存中
        .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
        .imageScaleType(ImageScaleType.NONE)
        .build();//构建完成
    return options;
  }

  /**
   * 获取imageloader 有圆角
   */
  public static DisplayImageOptions getDisplayImageOptionsConner(int conner) {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(true)//设置下载的图片是否缓存在内存中
        .cacheOnDisk(false)//设置下载的图片是否缓存在SD卡中
        .showImageForEmptyUri(R.drawable.mainlib_photo_default_round)
        .showImageOnFail(R.drawable.mainlib_photo_default_round)
        .displayer(new RoundedBitmapDisplayer(conner))
        .build();//构建完成
    return options;
  }

  /**
   * 获取imageloader 有头像的圆角
   */
  public static DisplayImageOptions getDisplayImageOptionsRoundHeader(int conner) {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(true)//设置下载的图片是否缓存在内存中
        .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
        .showImageOnFail(R.drawable.mainlib_photo_default)
        .displayer(new RoundedBitmapDisplayer(conner))
        .build();//构建完成
    return options;
  }


  /**
   * 获取imageloader 有圆角
   */
  public static DisplayImageOptions getDisplayImageOptionsConnerNoCache(int conner) {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(false)//设置下载的图片是否缓存在内存中
        .cacheOnDisk(false)//设置下载的图片是否缓存在SD卡中
        .displayer(new RoundedBitmapDisplayer(conner))
        .build();//构建完成
    return options;
  }

  /**
   * 下载图片到sd卡
   */
  public static synchronized void loadImageToSdCard(final String imageurl, final String path) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        InputStream is = null;
        FileOutputStream out = null;
        Bitmap bitmap = null;
        try {
          URL url = new URL(imageurl);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setDoInput(true);
          conn.connect();
          is = conn.getInputStream();
          File file = new File(path);
          if (!file.exists()) {
            file.createNewFile();
          }
          bitmap = BitmapFactory.decodeStream(is);
          out = new FileOutputStream(file);
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          try {
            out.close();
            is.close();
            bitmap.recycle();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  /**
   * 质量压缩并存到SD卡中
   * @param bitmap
   * @param reqSize 需要的大小 k
   * @return
   */

  public static String qualityCompress(Bitmap bitmap ,int reqSize,String savePath){

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //这里100表示不压缩，把压缩后的数据存放到baos中
    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
    int options = 95;
    //如果压缩后的大小超出所要求的，继续压缩
    while (baos.toByteArray().length / 1024 > reqSize){
      baos.reset();
      bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos);

      //每次减少5%质量
      if (options>5){//避免出现options<=0
        options -=5;
      } else {
        break;
      }

    }


    File outputFile =SdCardHelper.createFile(savePath);
    outputFile.delete();
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(outputFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    bitmap.compress(Bitmap.CompressFormat.JPEG, options, out);

    return outputFile.getPath();

  }


  /**
   * 将bitmap转成base64
   * @param bitmap
   * @return
   */
  public static String bitmapToBase64(Bitmap bitmap){
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
    //这里的JPEG 如果换成PNG，那么压缩的就有600kB这样
    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
    byte[] b = baos.toByteArray();
    Log.d("d", "压缩后的大小=" + b.length);
    String result=Base64.encodeToString(b, Base64.DEFAULT);
    result = "<img src='data:img/jpg;base64," + result + "<div class=\"xl-chrome-ext-bar\" id=\"xl_chrome_ext_{4DB361DE-01F7-4376-B494-639E489D19ED}\" style=\"display: none;\">\n" +
            "      <div class=\"xl-chrome-ext-bar__logo\"></div>\n" +
            "\n" +
            "      <a id=\"xl_chrome_ext_download\" href=\"javascript:;\" class=\"xl-chrome-ext-bar__option\">下载视频</a>\n" +
            "      <a id=\"xl_chrome_ext_close\" href=\"javascript:;\" class=\"xl-chrome-ext-bar__close\"></a>\n" +
            "    </div>'/>";
    return result;
  }
}
