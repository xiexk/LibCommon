package com.app.ud361.cropper;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.app.ud361.cropper.util.CropperBitMapUtil;
import com.app.ud361.cropper.util.CropperSdCardHelper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CropTool {

  private static final int GUIDELINES_ON_TOUCH = 1;

  public static String FILE_SAVEPATH = CropperSdCardHelper.getExternalSdCardPath() + "/Cropper/cache/";

  private String url;

  private boolean fixedAspect = false;//false 自由裁减  true 按比例裁减

  private static final int FIXEDASPECTX = 1, FIXEDASPECTY = 1;//默认1:1比例

  private CropToolInterface cropToolInterface;

  /**
   *按比例裁减 X轴比例
   */
  private int fixedAspectX = FIXEDASPECTX;

  /**
   *按比例裁减 Y轴比例
   */
  private int fixedAspectY = FIXEDASPECTY;

  private CropImageView cropImageView;

  private Button cropButton;

  private Activity context;

  public CropTool(Activity context, CropImageView cropImageView, Button cropButton, String imageUrl,
      CropToolInterface cropToolInterface) {
    this.context = context;
    this.cropImageView = cropImageView;
    this.cropButton = cropButton;
    this.url = imageUrl;
    this.cropToolInterface = cropToolInterface;
    initData();
    initListener();
  }

  private void initData() {
    setAspect(fixedAspect, fixedAspectX, fixedAspectY);
    //设置显示网格线方式
    cropImageView.setGuidelines(GUIDELINES_ON_TOUCH);
    Bitmap bitmap = CropperBitMapUtil.getBitmap(url, context); //从本地取图片(在cdcard中获取)  //
    cropImageView.setImageBitmap(bitmap);
  }

  public void setAspect(boolean fixedAspect, int fixedAspectX, int fixedAspectY) {
    //设置是按照裁减比例裁减 和裁减比例
    cropImageView.setFixedAspectRatio(fixedAspect);
    cropImageView.setAspectRatio(fixedAspectX, fixedAspectY);
  }

  private void initListener() {
    // Initialize the Crop button.
    cropButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {
          Bitmap croppedImage = cropImageView.getCroppedImage();
          String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
          String cropFileName = "cropper_" + timeStamp + ".jpeg"; //裁剪的图片地址
          String path = FILE_SAVEPATH + cropFileName;
          CropperBitMapUtil.saveBitmap(croppedImage, path);
          if (cropToolInterface != null) {
            cropToolInterface.cropResult(path);
          }
        } catch (Exception e) {
          Toast.makeText(context, "请调整裁减尺寸", Toast.LENGTH_LONG).show();
        }
      }
    });
  }

  public interface CropToolInterface {
    void cropResult(String cropResultPath);
  }
}
