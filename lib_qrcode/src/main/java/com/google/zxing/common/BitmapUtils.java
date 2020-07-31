package com.google.zxing.common;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class BitmapUtils {

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap getCompressedBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    /**
     * 用字符串生成二维码
     */
    public static Bitmap create2DCode(String str) throws WriterException {
        return create2DCodeWH(str, 450, 450);
    }

    /**
     * 用字符串生成二维码
     */
    public static Bitmap create2DCodeHasColor(String str, int color, int bgColor) throws WriterException {
        return create2DCode(str, 450, 450, color, bgColor);
    }


    /**
     * 用字符串生成二维码
     */
    public static Bitmap create2DCodeWH(String str, int codeWidth, int codeHeight) throws WriterException {
        return create2DCode(str, codeWidth, codeHeight, Color.BLACK, Color.WHITE);
    }


    /**
     * 用字符串生成二维码
     */
    public static Bitmap create2DCode(String str, int codeWidth, int codeHeight, int color, int bgColor) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, codeWidth, codeHeight, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = color;
                    //pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = bgColor;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 生成带logo的二维码，logo默认为二维码的1/5
     */
    public static Bitmap createQRCodeWithLogo(String text, int size, Bitmap mBitmap) {
        return createQRCodeWithLogo(text, size, mBitmap, BLACK, WHITE);
    }

    /**
     * 生成带logo的二维码，logo默认为二维码的1/5
     *
     * @param text    需要生成二维码的文字、网址等
     * @param size    需要生成二维码的大小（）
     * @param mBitmap logo文件
     * @return bitmap
     */
    public static Bitmap createQRCodeWithLogo(String text, int size, Bitmap mBitmap, int color, int bgColor) {
        try {
            int IMAGE_HALFWIDTH = size / 10;
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            /*
             * 设置容错级别，默认为ErrorCorrectionLevel.L
             * 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
             */
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            //default is 4
            hints.put(EncodeHintType.MARGIN, 1);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints);
//矩阵高度
            int width = bitMatrix.getWidth();
            //矩阵宽度
            int height = bitMatrix.getHeight();
            int halfW = width / 2;
            int halfH = height / 2;

            Matrix m = new Matrix();
            float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
            float sy = (float) 2 * IMAGE_HALFWIDTH
                    / mBitmap.getHeight();
            m.setScale(sx, sy);
            //设置缩放信息
            //将logo图片按martix设置的信息缩放
            mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
                    mBitmap.getWidth(), mBitmap.getHeight(), m, false);

            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                            && y > halfH - IMAGE_HALFWIDTH
                            && y < halfH + IMAGE_HALFWIDTH) {
                        //该位置用于存放图片信息
                        //记录图片每个像素信息
                        pixels[y * width + x] = mBitmap.getPixel(x - halfW
                                + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                    } else {
                        if (bitMatrix.get(x, y)) {
                            pixels[y * size + x] = color;
                        } else {
                            pixels[y * size + x] = bgColor;
                        }
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 在二维码中间添加Logo图案 默认比例
     */
    public static Bitmap addLogo(Bitmap src, Bitmap logo) {
        return addLogo(src, logo,5);
    }
    /**
     *
     * 在二维码中间添加Logo图案  scare为logo占图片的大小比例  5 为1/5   6为1/6
     */
    public static Bitmap addLogo(Bitmap src, Bitmap logo,int scare) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / scare / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            //canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }


    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

	private static BarcodeFormat barcodeFormat= BarcodeFormat.CODE_128;


    /**
     * 创建条形码
     */
    public static Bitmap creatBarcode(String contents, int desiredWidth, int desiredHeight) {
        return creatBarcode(contents, desiredWidth, desiredHeight, BLACK, WHITE);
    }


    /**
     * 创建条形码
     * 使用：
     * 根据字符串生成条形码图片并显示在界面上，第二个参数为图片的大小（120*60）
     * Bitmap qrCodeBitmap=null;
     * qrCodeBitmap= BarcodeCreater.creatBarcode(contentString, 120, 60);
     * bg.setImageBitmap(qrCodeBitmap);
     */
    public static Bitmap creatBarcode(String contents, int desiredWidth, int desiredHeight, int color, int bgColor) {
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, barcodeFormat, desiredWidth,
                    desiredHeight);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];

        //条形码文案宽度为条形码宽度的8/10
        int numberWidth = (desiredWidth * 8) / 10;
        //条形码文案的高度为条形码高度的2/10
        int munberHeight = (desiredHeight * 2) / 10;
        // 条形码文案bitmap
        Bitmap bitmapShowNumber = Bitmap.createBitmap(numberWidth, munberHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmapShowNumber);
            //画条形码文案的底部背景开始
            Paint rectPaint = new Paint();
            rectPaint.setColor(bgColor);
            //画条形码文案的底部背景
            canvas.drawRect(0, 0, (float) numberWidth, (float) munberHeight, rectPaint);
            //画条形码文案文字开始
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setTextSize(munberHeight);
            // 文字宽
            float textWidth = textPaint.measureText(contents);
            // 文字bdescent位置 (为baseline到 底部的距离)
            float descent = textPaint.descent();
            //文字marginLeft（为了居中）
            float marginLeft = (numberWidth - textWidth) / 2;
            if (marginLeft < 0) {
                marginLeft = 0;
            }
            //画条形码文案
            canvas.drawText(contents, marginLeft, munberHeight - descent + 2, textPaint);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmapShowNumber = null;
            e.getStackTrace();
        }

        //条形码文案距离左侧位置为 （条形码宽度-条形码文案宽度）/2
        int numberMarginLeft = (desiredWidth - numberWidth) / 2;
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {

                    if (y < munberHeight / 2) {
                        //条形码顶部留白（颜色为背景色）
                        pixels[offset + x] = bgColor;
                    } else if (y > height - munberHeight && x > numberMarginLeft && x < (numberMarginLeft + numberWidth)) {
                        //条形码底部编写条形码文字内容
                        pixels[offset + x] = bitmapShowNumber.getPixel(x - numberMarginLeft, y - height + munberHeight);
                    } else {
                        //条形码文案内容
                        pixels[offset + x] = result.get(x, y) ? color : bgColor;
                    }

            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


}
