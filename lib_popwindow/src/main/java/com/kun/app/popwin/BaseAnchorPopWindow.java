package com.kun.app.popwin;

import android.app.Activity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2018/12/10.
 * 有参照View的popwindows
 * 下面组合1和组合2 交叉选择来确定显示位置
 */

public class BaseAnchorPopWindow extends BasePopWindow {
  private View anchotView;

  private LayoutGravity layoutGravity;

  private int anchWidth;

  private int anchHeight;

  public BaseAnchorPopWindow(Activity context, int viewContentId, View anchotView) {
    super(context, viewContentId);
    this.anchotView = anchotView;
    this.layoutGravity = new LayoutGravity(LayoutGravity.ALIGN_LEFT | LayoutGravity.TO_BOTTOM);
    initAnchorViewHeightWidth();
  }

  public BaseAnchorPopWindow(Activity context, View viewContent, View anchotView) {
    super(context, viewContent);
    this.anchotView = anchotView;
    this.layoutGravity = new LayoutGravity(LayoutGravity.ALIGN_LEFT | LayoutGravity.TO_BOTTOM);
    initAnchorViewHeightWidth();
  }

  public BaseAnchorPopWindow(Activity context, View viewContent, View anchotView, boolean marchWidth) {
    super(context, viewContent, marchWidth);
    this.anchotView = anchotView;
    this.layoutGravity = new LayoutGravity(LayoutGravity.ALIGN_LEFT | LayoutGravity.TO_BOTTOM);
    initAnchorViewHeightWidth();
  }

  //初始化anchorView宽高
  private void initAnchorViewHeightWidth() {
    if(anchotView!=null){
    anchWidth = anchotView.getWidth();
    anchHeight = anchotView.getHeight();}
  }

  public BaseAnchorPopWindow setHoriGravity(int gravity) {
    layoutGravity.setHoriGravity(gravity);
    return this;
  }

  public void setVertGravity(int gravity) {
    layoutGravity.setVertGravity(gravity);
  }
@Override
  public void show() {
    if(anchotView!=null) {
      int[] offset = layoutGravity.getOffset(anchotView, popupWindow);
      popupWindow.showAsDropDown(anchotView, offset[0], offset[1]);
    }else {
      super.show();
    }
  }


  public static class LayoutGravity {
    private int layoutGravity;
    // waring, don't change the order of these constants!

    public static final int ALIGN_LEFT = 0x1;

    public static final int ALIGN_ABOVE = 0x2;

    public static final int ALIGN_RIGHT = 0x4;

    public static final int ALIGN_BOTTOM = 0x8;

    public static final int TO_LEFT = 0x10;

    public static final int TO_ABOVE = 0x20;

    public static final int TO_RIGHT = 0x40;

    public static final int TO_BOTTOM = 0x80;

    //中间
    public static final int CENTER_HORI = 0x100;

    public static final int CENTER_VERT = 0x200;

    public LayoutGravity(int gravity) {
      layoutGravity = gravity;
    }

    public int getLayoutGravity() {
      return layoutGravity;
    }

    public void setLayoutGravity(int gravity) {
      layoutGravity = gravity;
    }

    public void setHoriGravity(int gravity) {
      layoutGravity &= (0x2 + 0x8 + 0x20 + 0x80 + 0x200);
      layoutGravity |= gravity;
    }

    public void setVertGravity(int gravity) {
      layoutGravity &= (0x1 + 0x4 + 0x10 + 0x40 + 0x100);
      layoutGravity |= gravity;
    }


    public boolean isParamFit(int param) {
      return (layoutGravity & param) > 0;
    }

    public int getHoriParam() {
        for (int i = 0x1; i <= 0x100; i = i << 2) {
            if (isParamFit(i)) {
                return i;
            }
        }
      return ALIGN_LEFT;
    }

    public int getVertParam() {
        for (int i = 0x2; i <= 0x200; i = i << 2) {
            if (isParamFit(i)) {
                return i;
            }
        }
      return TO_BOTTOM;
    }

    public int[] getOffset(View anchor, PopupWindow window) {
      int anchWidth = anchor.getWidth();
      int anchHeight = anchor.getHeight();

      int winWidth = window.getWidth();
      int winHeight = window.getHeight();
      View view = window.getContentView();
        if (winWidth <= 0) {
            winWidth = view.getWidth();
        }
        if (winHeight <= 0) {
            winHeight = view.getHeight();
        }

      int xoff = 0;
      int yoff = 0;
//组合1
      switch (getHoriParam()) {
        case ALIGN_LEFT:
          xoff = 0;
          break;
        case ALIGN_RIGHT:
          xoff = anchWidth - winWidth;
          break;
        case TO_LEFT:
          xoff = -winWidth;
          break;
        case TO_RIGHT:
          xoff = anchWidth;
          break;
        case CENTER_HORI:
          xoff = (anchWidth - winWidth) / 2;
          break;
        default:
          break;
      }
      //组合2
      switch (getVertParam()) {
        case ALIGN_ABOVE:
          yoff = -anchHeight;
          break;
        case ALIGN_BOTTOM:
          yoff = -winHeight;
          break;
        case TO_ABOVE:
          yoff = -anchHeight - winHeight;
          break;
        case TO_BOTTOM:
          yoff = 0;
          break;
        case CENTER_VERT:
          yoff = (-winHeight - anchHeight) / 2;
          break;
        default:
          break;
      }
      return new int[] {xoff, yoff};
    }
  }
}
