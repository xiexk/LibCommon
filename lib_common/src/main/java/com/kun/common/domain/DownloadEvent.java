package com.kun.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Ryan on 2018/3/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadEvent {
    /**
     * 下载完成状态  true 为成功
     */
    private boolean state;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 消息
     */
    private String MSG;


}
