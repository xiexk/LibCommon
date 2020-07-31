package com.kun.common.activity;


import com.kun.common.R;
import com.kun.common.domain.ArgumentData;


import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;


/**
 * Created by Administrator on 2019/1/29.
 * 视频播放界面
 */

public class ActivityVideo extends Activity {
  private VideoView videoView;

  private String url;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mainlib_activity_video);
    initView();
    initData();
    initListener();
  }

  private void initView() {
    videoView = findViewById(R.id.mainlib_layout_videoplayer);
  }

  private void initData() {
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      ArgumentData argumentData = (ArgumentData) bundle.getSerializable("data");
      url = argumentData.url;
    }
    Uri uri = null;
    if (url != null) {
      uri = Uri.parse(url);
    }
    if (uri != null) {
      videoView.setVideoURI(uri);//为视频播放器设置视频路径
      videoView.setMediaController(new MediaController(this));//显示控制栏
      videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
          videoView.start();//开始播放视频
        }
      });
    }
  }

  private void initListener() {

  }
}
