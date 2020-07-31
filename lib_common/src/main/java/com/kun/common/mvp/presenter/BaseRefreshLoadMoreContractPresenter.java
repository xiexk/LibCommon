package com.kun.common.mvp.presenter;

import com.kun.common.data.RefreshLoadDataManagerInterface;
import com.kun.common.domain.searchable.ListWrapper;
import com.kun.common.domain.searchable.Seachable;
import com.kun.common.mvp.constract.BaseRefreshLoadMoreContract;
import com.kun.common.net.ErrorObserver;

/**
 * Created by Administrator on 2019/1/14.
 * 实现刷新加载更多的数据处理类
 * <p>
 * * 商品提供者
 * 通用上下拉获取数据组件
 * <p>
 * 1、presenter 要继承 BaseRefreshLoadMoreContractPresenter<T,S> T为具体searchable类 S为返回的数据Model
 * 2、presenter 实现自己的Presenter 还要实现 BaseRefreshLoadMoreContract.PresenterResult<T> T为返回的数据的Model(解析用)
 * 3、向presenter的继承的实现类添加 super.setRefreshLoadDataManagerInterface(dataManager); //获取数据接口  dataManager要实现RefreshLoadInterface 用于请求网络数据（查询列表）
 * super.setPresenterResult(this);//用于处理返回来的数据
 * 4、activity要实现BaseRefreshLoadMoreContract.View 接口用于控制上下拉组建状态 设置还有更多 刷新完毕等 BaseRecyclerViewHeaderActivity已经实现
 * 5、GoodsContract.View要继承BaseRefreshLoadMoreContract.View接口 并传入BaseRefreshLoadMorePresenter
 */

public abstract class BaseRefreshLoadMoreContractPresenter<M extends RefreshLoadDataManagerInterface, S extends Seachable, T> extends
    BaseContractPresenterImpl implements
    BaseRefreshLoadMoreContract.Presenter<S> {

  private BaseRefreshLoadMoreContract.View baseRLMView;

  private static int DEFAULT_SIZE = 10;

  private static int DEFAULT_PAGE = 1;

  private long total_local;

  private int size = DEFAULT_SIZE;

  private int current = DEFAULT_PAGE;

  public S searchable;

  private M refreshLoadDataManagerInterface;

  public BaseRefreshLoadMoreContractPresenter(BaseRefreshLoadMoreContract.View baseContract) {
    super(baseContract);
    this.baseRLMView = baseContract;
  }

  public void initRefreshPresenter(S searchable, M refreshLoadDataManagerInterface) {
    this.searchable = searchable;
    this.refreshLoadDataManagerInterface = refreshLoadDataManagerInterface;
  }

  @Override
  public void search(S search) {
    this.searchable = search;
    refreshForNet();
  }

  @Override
  public void refreshForNet() {
    size = DEFAULT_SIZE;
    current = DEFAULT_PAGE;
    total_local = 0;
    addDisposabe(refreshLoadDataManagerInterface.getList(size, current, searchable,
        new ErrorObserver<ListWrapper<T>>(baseRLMView) {
          @Override
          public void onNext(ListWrapper<T> listWrapper) {
            total_local=listWrapper.getList().size();
            BaseRefreshLoadMoreContractPresenter.this.refreshForNetResult(listWrapper);
            if (total_local ==listWrapper.getTotal()) {
              baseRLMView.netLoadMoreComplite(false);
            } else {
              baseRLMView.netLoadMoreComplite(true);
            }
            baseRLMView.netRefreshComplite();
          }
        }));
  }

  @Override
  public void loadMoreForNet() {
    current++;
    ErrorObserver<ListWrapper<T>> errorObserver = new ErrorObserver<ListWrapper<T>>(baseRLMView) {
      @Override
      public void onNext(ListWrapper<T> listWrapper) {
        total_local+=listWrapper.getList().size();
        BaseRefreshLoadMoreContractPresenter.this.loadMoreForNetResult(listWrapper);
        if (total_local>=listWrapper.getTotal()) {
          baseRLMView.netLoadMoreComplite(false);
        } else {
          baseRLMView.netLoadMoreComplite(true);
        }
      }
    };
    addDisposabe(refreshLoadDataManagerInterface.getList(size, current, searchable, errorObserver));
  }

  public abstract void refreshForNetResult(ListWrapper<T> listWrapper);

  public abstract void loadMoreForNetResult(ListWrapper<T> listWrapper);
}
