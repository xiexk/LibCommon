package com.kun.common.tools;

import android.content.Context;
import android.media.AudioManager;
/**
 * 媒体声音控制器  （控制媒体声音）
 */
public class HelperAudio {

    private final String log = "AudioMngHelper";
    /**
     * 媒体声音
     */
    private AudioManager audioManager;

    /**
     * 初始化，获取音量管理者
     */
    public HelperAudio(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * 获取系统声音最大声音值
     */
    private int getSystemMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 获取当前声音大小
     */
    private int getSystemCurrentVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 以0-100为范围，获取当前的音量值
     * @return  获取当前的音量值
     */
    public int get100CurrentVolume() {
        return 100*getSystemCurrentVolume()/getSystemMaxVolume();
    }

    /**
     * 调整音量，自定义
     * @param num   0-100
     * @return  改完后的音量值
     */
    public int setVoice100(int num) {
        int a = (int) Math.ceil((num)*getSystemMaxVolume()*0.01);
        a = a<=0 ? 0 : a;
        a = a>=100 ? 100 : a;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,a,0);
        return get100CurrentVolume();
    }

}
