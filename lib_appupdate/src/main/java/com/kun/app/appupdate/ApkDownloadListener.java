package com.kun.app.appupdate;



import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;



public abstract class ApkDownloadListener extends FileDownloadSampleListener {
    private String log=ApkDownloadListener.class.getName();
    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.pending(task, soFarBytes, totalBytes);
        Log.e(log,"下载期间："+task.getFilename()+" 速度："+task.getSpeed()+" 已下载："+soFarBytes+" 总大小："+totalBytes);
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.progress(task, soFarBytes, totalBytes);
        Log.e(log,"下载中："+task.getFilename()+" 速度："+task.getSpeed()+" 已下载："+soFarBytes+" 总大小："+totalBytes);
            }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        super.error(task, e);
        Log.e(log,"下载失败："+task.getFilename()+" 错误信息："+e.getMessage());

    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
        Log.e(log,"链接成功："+task.getFilename());
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.paused(task, soFarBytes, totalBytes);
        Log.e(log,"暂停下载："+task.getFilename());
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        super.completed(task);
        Log.e(log,"下载完成："+task.getFilename());

    }

    @Override
    protected void warn(BaseDownloadTask task) {
        super.warn(task);
        Log.e(log,"下载警告："+task.getFilename());
    }
}
