package com.app.ud361.cropper.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.WindowManager;

public class CropperBitMapUtil {
  public static Bitmap getBitmap(String url, Activity context) {
    try {
      FileInputStream fis = new FileInputStream(url);
      Bitmap bitmap= BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
      WindowManager wm = (WindowManager) context.getBaseContext().getSystemService(Context.WINDOW_SERVICE);
      //先获取屏幕宽高
      int width = wm.getDefaultDisplay().getWidth();
      int height = wm.getDefaultDisplay().getHeight();

      float scaleWidth = 1, scaleHeight = 1;
      if (bitmap.getWidth() < width) {
        //强转为float类型，
        scaleWidth = (float)width / (float)bitmap.getWidth();
      }

      if (bitmap.getHeight() < height) {
        scaleHeight = (float)height / (float)bitmap.getHeight();
      }

      if (scaleWidth > scaleHeight)
        scaleHeight = scaleWidth;
      else
        scaleWidth = scaleHeight;

      Matrix matrix = new Matrix();
      //根据屏幕大小选择bitmap放大比例。
      matrix.postScale(scaleWidth, scaleHeight);
      bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()
          , matrix, true);
      return bitmap;
    }catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }



  public static boolean  saveBitmap(Bitmap bmp, String path) {
    Bitmap.CompressFormat format = CompressFormat.JPEG;
    OutputStream stream = null;
    createFile(path);
    try {
      stream = new FileOutputStream(path);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return bmp.compress(format, 100, stream);

  }


  /**
   * 创建文件
   */
  public static File createFile(String file_path) {
    try {
      File destDir_portrait = new File(file_path);
      File parent = destDir_portrait.getParentFile();
      if (!parent.exists()) {
        parent.mkdirs();
      }
      if (!destDir_portrait.exists()) {
        destDir_portrait.createNewFile();
      }
      return destDir_portrait;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}
