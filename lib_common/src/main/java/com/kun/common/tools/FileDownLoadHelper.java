package com.kun.common.tools;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件下载工具类
 */
public class FileDownLoadHelper {
    /**
     * 下载单个任务
     */
    public static void downLoad(String url, String path, FileDownloadSampleListener fileDownloadSampleListener){
        com.liulishuo.filedownloader.FileDownloader.getImpl().create(url)
                .setPath(path, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(url)
                .setListener(fileDownloadSampleListener).start();
    }

    /**
     * 批量下载文件
     */
    public static void downLoadlist(List<String> urls, String basePath, FileDownloadSampleListener fileDownloadSampleListener){
        //(1) 创建 FileDownloadQueueSet
        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(fileDownloadSampleListener);

        //(2) 创建Task 队列
        final List<BaseDownloadTask> tasks = new ArrayList<>();
        for(String url:urls){
            tasks.add(  FileDownloader.getImpl().create(url).setPath(basePath,true));
        }

        //(3) 设置参数

        // 每个任务的进度 无回调
        //queueSet.disableCallbackProgressTimes();
        // do not want each task's download progress's callback,we just consider which task will completed.

        queueSet.setCallbackProgressTimes(100);
        queueSet.setCallbackProgressMinInterval(100);
        //失败 重试次数
        queueSet.setAutoRetryTimes(3);

        //避免掉帧
        FileDownloader.enableAvoidDropFrame();

        //(4)串行下载
        queueSet.downloadSequentially(tasks);

        //(5)任务启动
        queueSet.start();
    }

    public static void delete(){

    }

    /**
     * 清除所有下载任务
     */
    public static void cleanAllTaskData(){
        //清除所有的下载任务
        FileDownloader.getImpl().clearAllTaskData();
    }
}
