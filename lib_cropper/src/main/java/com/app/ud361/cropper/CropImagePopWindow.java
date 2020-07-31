package com.app.ud361.cropper;



import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.Button;

import com.kun.app.popwin.BasePopWindow;

/**
 * Created by Administrator on 2018/12/11.
 * 裁剪弹窗
 */

public class CropImagePopWindow extends BasePopWindow implements CropTool.CropToolInterface {
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

  private CropImagePopWindowInterface cropImagePopWindowInterface;

  public CropImagePopWindow(Activity context,String url,boolean fixedAspect,int fixedAspectX,int fixedAspectY) {
    super(context, LayoutInflater.from(context).inflate(R.layout.cropper_layout_crop, null), true);
    setAnim(ANIM_DOWN);
    setLocation(LOCATION_RIGHT_CENTER);
    this.url=url;
    this.fixedAspect=fixedAspect;
    this.fixedAspectX=fixedAspectX;
    this.fixedAspectY=fixedAspectY;
    initView();
    initData();
  }

  private void initView() {
    cropImageView = contentView.findViewById(R.id.cropper_layout_crop_civ);
    cropButton = contentView.findViewById(R.id.cropper_layout_crop_bt_crop);

  }

  private void initData() {
    cropTool=new CropTool(context,cropImageView,cropButton,url,this);
    cropTool.setAspect(fixedAspect,fixedAspectX,fixedAspectY);
  }


  @Override
  public void cropResult(String cropResultPath) {
    if(cropImagePopWindowInterface!=null){
      cropImagePopWindowInterface.cropImageBack(cropResultPath);
    }
      this.dismiss();
  }

  public void setCropImagePopWindowInterface(
      CropImagePopWindowInterface cropImagePopWindowInterface) {
    this.cropImagePopWindowInterface = cropImagePopWindowInterface;
  }

  public interface CropImagePopWindowInterface{
    void cropImageBack(String imageUrl);
  }

}
