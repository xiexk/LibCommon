package com.kun.common.activity;

import com.kun.common.widge.layout.LayoutMainHeader;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/8/30.
 * BaseActivity默认头部Activity
 */

public class BaseHeaderHCFActivity extends BaseHCFActivity {
  public LayoutMainHeader baseRelativeLayoutMainHeader;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void initView() {
    super.initView();
    baseRelativeLayoutMainHeader = new LayoutMainHeader(this, null);
    baseActivityFragmentController.setHeader(baseRelativeLayoutMainHeader);
  }

  @Override
  public void initData() {
    super.initData();
  }

  @Override
  public void initListener() {
    super.initListener();
    baseRelativeLayoutMainHeader.setClickLeftImageViewListener(new LayoutMainHeader.ClickLeftImageViewListener() {
      @Override
      public void clickBack() {
        finish();
      }
    });
  }

  @Override
  public void onClick(View v) {
    super.onClick(v);
    int id = v.getId();
  }
}
