package com.kun.common.mvp.constract;

/**
 * Created by Administrator on 2018/12/17.
 * 刷新 加载更多 契约
 */
public interface BaseRefreshLoadMoreContract  {

  interface View extends BaseViewContract {

    void netLoadMoreComplite(boolean hasMore);

    void netRefreshComplite();
  }

  interface Presenter<T> extends BasePresenter {

    void search(T search);

    void refreshForNet();

    void loadMoreForNet();
  }
}
