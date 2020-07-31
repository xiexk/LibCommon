package com.kun.common.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kun.common.R;
import com.kun.common.mvp.constract.BaseRefreshLoadMoreContract;
import com.kun.common.mvp.presenter.BaseRefreshLoadMoreContractPresenter;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.recyclerview.RecyclerAdapterWithHF;

/**
 * Created by Administrator on 2018/9/10.
 * BaseRecycleVuewHeaderFragment Base上下拉刷新Fragment
 */

public class BaseRecycleViewHeaderFragment extends BaseHeaderHCFFragment implements BaseRefreshLoadMoreContract.View {
  public PtrClassicFrameLayout basePtrClassicFrameLayout;

  public RecyclerView baseRecyclerView;

  public RecyclerAdapterWithHF baseRecycleViewAdapter;

  private BaseRefreshLoadMoreContractPresenter baseRefreshLoadMorePresenter;

  public void setBaseRefreshLoadMorePresenter(BaseRefreshLoadMoreContractPresenter baseRefreshLoadMorePresenter) {
    this.baseRefreshLoadMorePresenter = baseRefreshLoadMorePresenter;
  }

  @Override
  public void initView() {
    super.initView();
    baseActivityFragmentController.addContent(R.layout.mainlib_layout_base_recyclelistview);
    basePtrClassicFrameLayout = findViewById(R.id.mainlib_layout_base_recyclelistview_ptrfl);
    baseRecyclerView = findViewById(R.id.mainlib_layout_base_recyclelistview_recycler_view);
    baseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  }

  @Override
  public void initData() {
    super.initData();
  }

  @Override
  public void initListener() {
    super.initListener();
    basePtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
      @Override
      public void onRefreshBegin(PtrFrameLayout frame) {
        onRefresh(frame);
      }
    });

    basePtrClassicFrameLayout.setOnLoadMoreListener(() -> onloadMore());
  }

  public void setAdapter(RecyclerView.Adapter adapter) {
    baseRecycleViewAdapter = new RecyclerAdapterWithHF(adapter);
    baseRecyclerView.setAdapter(baseRecycleViewAdapter);
  }

  /**
   * 上拉加载更多
   */
  public void onloadMore() {
    if (baseRefreshLoadMorePresenter != null) {
      baseRefreshLoadMorePresenter.loadMoreForNet();
    }
  }

  /**
   * 上拉加载更多完毕
   * pram hasemore 是否还有更多数据
   */
  public void loadMoreComplete(boolean hasemore) {
    basePtrClassicFrameLayout.loadMoreComplete(hasemore);
  }

  /**
   * 下拉刷新
   */
  public void onRefresh(PtrFrameLayout frame) {
    if (baseRefreshLoadMorePresenter != null) {
      baseRefreshLoadMorePresenter.refreshForNet();
    }
  }

  /**
   * 下拉刷新结束
   */
  public void refreshComplete() {
    basePtrClassicFrameLayout.refreshComplete();
  }

  @Override
  public void netLoadMoreComplite(boolean hasMore) {
    loadMoreComplete(hasMore);
  }

  @Override
  public void netRefreshComplite() {
    refreshComplete();
  }
}
