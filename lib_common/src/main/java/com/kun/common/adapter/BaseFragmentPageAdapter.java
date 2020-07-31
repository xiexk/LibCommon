package com.kun.common.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 * 主页Fragment适配
 */

public class BaseFragmentPageAdapter extends FragmentPagerAdapter {
  private List<Fragment> fragments;

  private String[] titles;

  public BaseFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
    super(fm);
    this.fragments = fragments;
    this.titles = titles;
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    if (titles != null) {
      return titles[position];
    }
    return super.getPageTitle(position);
  }
}
