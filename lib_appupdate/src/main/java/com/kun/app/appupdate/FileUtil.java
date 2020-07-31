package com.kun.app.appupdate;

import java.io.File;

public class FileUtil {
    /**
     * 创建文件
     */
    public static File createFile(String file_path) {
        try {
            File destDir_portrait = new File(file_path);
            File parent = destDir_portrait.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!destDir_portrait.exists()) {
                destDir_portrait.createNewFile();
            }
            return destDir_portrait;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
