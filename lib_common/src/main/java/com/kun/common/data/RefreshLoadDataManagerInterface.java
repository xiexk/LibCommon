package com.kun.common.data;

import com.kun.common.domain.searchable.Seachable;
import com.kun.common.net.ErrorObserver;

import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019/1/14.
 */

public interface RefreshLoadDataManagerInterface {
  public <T extends Seachable> Disposable getList(int size, int current, T seachable, ErrorObserver observer);
}
