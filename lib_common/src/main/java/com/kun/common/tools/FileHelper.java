package com.kun.common.tools;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.kun.common.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import static com.kun.common.constans.CommonConfig.imagePath;

/**
 * Created by yemao on 2017/3/15.
 * 关于文件工具类
 */

public class FileHelper {

    /**
     * 获取文件大小
     *
     * @param filePath 文件路径
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return 0;
    }

    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public String compressImage(String filePath) {

        //原文件
        File oldFile = new File(filePath);

        //压缩文件路径 照片路径/
        String targetPath = imagePath + oldFile.getName();
        int quality = 50;//压缩比例0-100
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm);
        }

        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }


    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public Bitmap setRotateAngle(int angle, Bitmap bitmap) {

        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }


    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {

        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(BaseApplication.application)
                    .asBitmap()
                    .load(filePath)
                    .submit(500,300)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return myBitmap;
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap Bitmap
     */
    public static File saveBitmap(Bitmap bitmap,String path) {
        String savePath;
        File filePic=null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = path;
        } else {
            Log.e("tag", "saveBitmap failure : sdcard not mounted");
            return null;
        }
        try {
            filePic = new File(savePath);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e("tag", "saveBitmap: " + e.getMessage());
            return filePic;
        }
        Log.i("tag", "saveBitmap success: " + filePic.getAbsolutePath());
        return filePic;
    }


    /**
     * 读取inputStream内容
     *
     * @param in
     */
    public static String readstr(InputStream in) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String read = "";
            while ((read = bufferedReader.readLine()) != null) {
                stringBuffer.append(read);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }
    /**
     *追加文件：使用FileWriter
     */
    public static void writeFileAppend(String fileName,String content){
        try{
          //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer=new FileWriter(fileName,true);
            writer.write(content);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 从文件路径中获取文件名
     */
    public static String getFileNameFromPath(String path) {
        String pathChanage = path.replaceAll("\\\\", "/");
        String temp[] = pathChanage.split("/");/**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
        String fileName = temp[temp.length - 1];
        return fileName;
    }

    /**
     * 获取不带后缀名的文件名
     */
    public static String getFileNameWithoutSuffix(File file) {
        String fileName = file.getName();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    /**
     * 获取不带后缀名的文件名
     */
    public static String getFileNameWithoutSuffix(String url) {
        String fileName = url;
        fileName = url.substring(url.lastIndexOf("/") + 1);
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 获取url连接中的文件名
     */
    public static String getFileName(String url) {
        if(TextUtils.isEmpty(url)){
            LogHelper.e("获取文件名错误：","url为空");
            return null;
        }
        String fileName = "";
        fileName = url.substring(url.lastIndexOf("/") + 1);
        return fileName;
    }

    /**
     * 检查文件是否在本地目录中
     *
     * @param fileName
     * @return
     */
    public static boolean isExistInLocal(String fileName, String localPath) {
        boolean flag = false;
        File file = new File(localPath + fileName);
        if (file.exists()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 删除指定路径，指定天数前的的媒体数据
     *
     * @param context 上下文
     * @param path    指定路径
     * @param d       指定天数
     */
    public static void deleteMediaFile(Context context, String path, int d) {
        // 判断是否初始化或者初始化是否成功
        if (null == context) {
            Log.e("deleteMediaFile", "Initialization failure !!!");
            return;
        }
        // 获取path文件夹
        File filepath = new File(path);
        if (!filepath.exists()) {
            return;
        }
        File[] files = filepath.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
            } else {
                long lastDate = file.lastModified();
                long diff = System.currentTimeMillis() - lastDate;
                long days = diff / (1000 * 60 * 60 * 24);
                if (days > d) {
                    try {
                        file.delete();
                        //删除下载工具DB里的url
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    /**
     *  读取指定目录文件的内容
     */
    public static String readFileToString(String filePath){
        StringBuilder sb = new StringBuilder();
        File file = new File(filePath);
        if(file.exists()) {
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                String strline;
                while ((strline = reader.readLine()) != null){
                    sb.append(strline).append("\n");
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 读取一个缩放后的图片，限定图片大小，避免OOM
     * http://blog.sina.com.cn/s/blog_5de73d0b0100zfm8.html
     * @param uri       图片uri，支持“file://”、“content://”
     * @param maxWidth  最大允许宽度
     * @param maxHeight 最大允许高度
     * @return  返回一个缩放后的Bitmap，失败则返回null
     */
    public static Bitmap decodeUri(Context context, Uri uri, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //只读取图片尺寸
        resolveUri(context, uri, options);

        //计算实际缩放比例
        int scale = 1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if ((options.outWidth / scale > maxWidth &&
                    options.outWidth / scale > maxWidth * 1.4) ||
                    (options.outHeight / scale > maxHeight &&
                            options.outHeight / scale > maxHeight * 1.4)) {
                scale++;
            } else {
                break;
            }
        }

        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;//读取图片内容
        options.inPreferredConfig = Bitmap.Config.RGB_565; //根据情况进行修改
        Bitmap bitmap = null;
        try {
            bitmap = resolveUriForBitmap(context, uri, options);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // http://blog.sina.com.cn/s/blog_5de73d0b0100zfm8.html
    private static void resolveUri(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return;
        }

        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.w("resolveUri", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.w("resolveUri", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.w("resolveUri", "Unable to close content: " + uri);
        } else {
            Log.w("resolveUri", "Unable to close content: " + uri);
        }
    }

    // http://blog.sina.com.cn/s/blog_5de73d0b0100zfm8.html
    private static Bitmap resolveUriForBitmap(Context context, Uri uri, BitmapFactory.Options options) {
        if (uri == null) {
            return null;
        }

        Bitmap bitmap = null;
        String scheme = uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme) ||
                ContentResolver.SCHEME_FILE.equals(scheme)) {
            InputStream stream = null;
            try {
                stream = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                Log.w("resolveUriForBitmap", "Unable to open content: " + uri, e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Log.w("resolveUriForBitmap", "Unable to close content: " + uri, e);
                    }
                }
            }
        } else if (ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme)) {
            Log.w("resolveUriForBitmap", "Unable to close content: " + uri);
        } else {
            Log.w("resolveUriForBitmap", "Unable to close content: " + uri);
        }

        return bitmap;
    }


}
