package com.kun.common.data;


import android.graphics.Bitmap;

import com.kun.common.tools.HelperBitMap;
import com.kun.common.tools.ImageHelper;
import com.kun.common.tools.SdCardHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * bitmap控制器
 */

public class DataManagerBitmap extends BaseDataManager {


    public DataManagerBitmap(DataManager mDataManager) {
        super(mDataManager);
    }

    /**
     * 保存bitmap
     */
    public DisposableObserver saveBitmap(Bitmap bitmap, String path, DisposableObserver<String> observer) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                ImageHelper.saveBitmap(bitmap,path);
                e.onNext(path);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }


}
