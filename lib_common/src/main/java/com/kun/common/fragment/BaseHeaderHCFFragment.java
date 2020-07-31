package com.kun.common.fragment;

import com.kun.common.widge.layout.LayoutMainHeader;

import android.view.View;

/**
 * Created by Administrator on 2018/9/9.
 * 默认头部的BaseFragment
 */

public class BaseHeaderHCFFragment extends BaseHCFFragment {
  public LayoutMainHeader baseRelativeLayoutMainHeader;

  @Override
  public void initView() {
    super.initView();
    baseRelativeLayoutMainHeader = new LayoutMainHeader(getActivity().getBaseContext(), null);
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
        getActivity().finish();
      }
    });
  }

  @Override
  public void onClick(View v) {
    super.onClick(v);
  }
}
