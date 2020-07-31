package com.kun.app.popwin;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PopMainActivity extends AppCompatActivity {

  private BasePopWindow basePopWindows;

  private BaseAnchorPopWindow baseAnchorPopWindow;

  private TextView tv_anchor;//锚点

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.popwin_activity_main);
    tv_anchor = findViewById(R.id.popwindow_layout_main_tv);
  }

  public void click(View v) {
    basePopWindows = new BasePopWindow(this, R.layout.popwin_layout_text);
    basePopWindows.setAnim(BasePopWindow.ANIM_LEFT);
    basePopWindows.setLocation(BasePopWindow.LOCATION_LEFT_CENTER);
    basePopWindows.show();
  }

  public void showanchorpopwindow(View v) {
    baseAnchorPopWindow = new BaseAnchorPopWindow(this, R.layout.popwin_layout_text, tv_anchor);
    baseAnchorPopWindow.setAnim(BasePopWindow.ANIM_LEFT);
    baseAnchorPopWindow.setLocation(BasePopWindow.LOCATION_LEFT_CENTER);
    baseAnchorPopWindow.setHoriGravity(BaseAnchorPopWindow.LayoutGravity.CENTER_HORI);
    baseAnchorPopWindow.setVertGravity(BaseAnchorPopWindow.LayoutGravity.TO_BOTTOM);
    baseAnchorPopWindow.show();
  }
}
