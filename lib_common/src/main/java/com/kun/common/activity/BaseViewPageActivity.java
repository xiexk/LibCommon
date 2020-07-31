package com.kun.common.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import com.google.android.material.tabs.TabLayout;
import com.kun.common.R;
import com.kun.common.adapter.BaseFragmentPageAdapter;


/**
 * Created by Administrator on 2018/10/17.
 * BaseViewPageActivity
 */

public class BaseViewPageActivity extends BaseHeaderHCFActivity {

  private TabLayout baseTabLayout;

  private ViewPager baseViewPage;

  private ArrayList<Fragment> baseFragments = new ArrayList<>();

  private String[] titles;

  private BaseFragmentPageAdapter baseFragmentPageAdapter;

  @Override
  public void initView() {
    super.initView();
    baseActivityFragmentController.addContent(R.layout.mainlib_layout_tab_viewpage);
    baseTabLayout = findViewById(R.id.mainlib_layout_tab_viewpage_tl);
    baseViewPage = findViewById(R.id.mainlib_layout_tab_viewpage_vp);
  }

  public void initViewPage(ArrayList<Fragment> fragments, String[] titles) {

    this.baseFragments = fragments;
    this.titles = titles;
    baseFragmentPageAdapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), fragments, titles);
    baseViewPage.setAdapter(baseFragmentPageAdapter);
    baseTabLayout.setupWithViewPager(baseViewPage);
  }


  public void setCurrent(int current) {
    baseViewPage.setCurrentItem(current);
  }


  @Override
  public void initData() {
    super.initData();
  }

  @Override
  public void initListener() {
    super.initListener();
  }
}
