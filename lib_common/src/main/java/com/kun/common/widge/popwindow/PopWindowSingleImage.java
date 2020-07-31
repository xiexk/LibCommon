package com.kun.common.widge.popwindow;


import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kun.app.popwin.BaseAnchorPopWindow;
import com.kun.common.BaseApplication;
import com.kun.common.R;

/**
 * 展示单张图片弹窗
 */
public class PopWindowSingleImage extends BaseAnchorPopWindow implements View.OnClickListener {
    /**
     * log
     */
    private String tag = "单图片展示弹窗";
    /**
     * 图片
     */
    private ImageView iv;
    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 构造函数
     */
    public PopWindowSingleImage(Activity context, String imageUrl) {
        this(context, null, imageUrl);
    }

    public PopWindowSingleImage(Activity context, View anchotView, String imageUrl) {
        super(context, LayoutInflater.from(context).inflate(R.layout.mainlib_layout_single_image, null), anchotView, true);
        this.context = context;
        this.imageUrl = imageUrl;
        setAnim(ANIM_Alpha);
        setLocation(LOCATION_CENTER);
        initView();
        initData();
        initListener();

    }

    /**
     * 处理界面
     */
    private void initView() {
        iv = contentView.findViewById(R.id.mainlib_item_single_image_iv);

    }

    /**
     * 处理数据
     */
    private void initData() {
        Glide.with(BaseApplication.application).load(imageUrl).into(iv);
    }

    /**
     * 处理监听
     */
    private void initListener() {
        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.mainlib_item_single_image_iv) {
            dismiss();
        }
    }


}
