package com.app.ud361.cropper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Administrator on 2019/6/5.
 * 裁减activity  目前只能本地图片
 */

public class CropActivity extends Activity implements CropTool.CropToolInterface{

  public static final String IMAGE_URL = "IMAGE_URL";//图片地址

  public static final String FIXED_AS_PECT = "FIXED_AS_PECT";//固定比例裁减

  public static final String FIXED_AS_PECT_X = "FIXED_AS_PECT_X";//固定比例裁减x

  public static final String FIXED_AS_PECT_Y = "FIXED_AS_PECT_Y";//固定比例裁减y

  private String url;

  private boolean fixedAspect = false;//false 自由裁减  true 按比例裁减

  private static final int FIXEDASPECTX = 1, FIXEDASPECTY = 1;//默认1:1比例

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

  private CropTool cropTool;

  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.cropper_layout_crop);

    initView();
    initData();
  }

  private void initView() {
    cropImageView = findViewById(R.id.cropper_layout_crop_civ);
    cropButton = findViewById(R.id.cropper_layout_crop_bt_crop);
  }

  private void initData() {
    url = getIntent().getStringExtra(IMAGE_URL);
    fixedAspect = getIntent().getBooleanExtra(FIXED_AS_PECT, false);
    fixedAspectX = getIntent().getIntExtra(FIXED_AS_PECT_X, FIXEDASPECTX);
    fixedAspectY = getIntent().getIntExtra(FIXED_AS_PECT_Y, FIXEDASPECTY);
    if (url == null) {
      Toast.makeText(this, "图片地址错误", Toast.LENGTH_LONG).show();
      finish();
      return;
    }
    cropTool=new CropTool(this,cropImageView,cropButton,url,this);
    cropTool.setAspect(fixedAspect,fixedAspectX,fixedAspectY);
  }


  @Override
  public void cropResult(String cropResultPath) {
    Intent intent = getIntent();
    intent.putExtra("url", cropResultPath);
    setResult(RESULT_OK, intent);
    finish();
  }
}
