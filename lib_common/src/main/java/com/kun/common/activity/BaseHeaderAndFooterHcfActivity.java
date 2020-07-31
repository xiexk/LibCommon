package com.kun.common.activity;

import java.util.List;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.kun.common.R;
import com.kun.common.support.bottombar.BottomNavigationFixHelper;

import android.os.Bundle;

import androidx.annotation.Nullable;


/**
 * Created by Administrator on 2018/8/30.
 * 默认头部和底部activity
 * 继承头部Activity(已经实现了头部了)
 */

public class BaseHeaderAndFooterHcfActivity extends BaseHeaderHCFActivity implements
    BottomNavigationBar.OnTabSelectedListener {
  public BottomNavigationBar bottomNavigationBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void initView() {
    super.initView();

    bottomNavigationBar = new BottomNavigationBar(this, null);
    //   bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);//动画模式
    bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//
    bottomNavigationBar.setBarBackgroundColor(R.color.white_shallow);
    bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);//背景样式
    baseActivityFragmentController.addFooter(bottomNavigationBar);
  }

  @Override
  public void initData() {
    super.initData();
  }

  @Override
  public void initListener() {
    super.initListener();
    bottomNavigationBar.setTabSelectedListener(this);
  }

  //设置底部导航item
  public void setBottomNavigationBarItem(List<BottomNavigationItem> items) {
    bottomNavigationBar.setInActiveColor(R.color.gray_medium);
    for (BottomNavigationItem b : items) {
      bottomNavigationBar.addItem(b);
    }
    bottomNavigationBar.setFirstSelectedPosition(0);
    bottomNavigationBar.initialise();
    BottomNavigationFixHelper.setItem(bottomNavigationBar, 14);
  }

  @Override
  public void onTabSelected(int position) {

  }

  @Override
  public void onTabUnselected(int position) {
  }

  @Override
  public void onTabReselected(int position) {
  }
}
