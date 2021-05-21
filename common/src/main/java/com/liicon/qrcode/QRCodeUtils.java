package com.liicon.qrcode;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.liicon.utils.log.LLog;

/**
 * 二维码处理工具类
 * @version  v0.1  king  2014-11-19  二维码处理
 */
public class QRCodeUtils {
    
    /**
     * 生成二维码图片
     * @param text 要生成的文本信息
     * @param width 图片宽度
     * @param height 图片高度
     * @return
     */
    public static Bitmap createImage(String text, int width, int height) {
        return createImage(text, width, height, null);
    }
	/**
	 * 生成二维码图片
	 * @param text 要生成的文本信息
	 * @param width 图片宽度
	 * @param height 图片高度
	 * @param logo 绘制到中心的logo
	 * @return
	 */
	public static Bitmap createImage(String text, int width, int height, Bitmap logo) {
	    BitMatrix bitMatrix = null;
		try {
			if (TextUtils.isEmpty(text)) {
				return null;
			}

            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();

			// 把输入的文本转为二维码
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
		    LLog.e(e);
		}
		if (bitMatrix == null) {
		    return null;
		}
		
		
		// 生成二维码
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        
        if (logo != null) {
            // 添加LOGO
            return addQRCodeLogo(bitmap, logo);
        } else {
            return bitmap;
        }
	}
	
	
	/**
	 * 添加LOGO
	 * @param source
	 * @param logo
	 * @return
	 */
    private static Bitmap addQRCodeLogo(Bitmap source, Bitmap logo) {
        int logoWidth = source.getWidth() / 5; // 设置logo图片宽度为二维码图片的五分之一
        int logoHeight = source.getHeight() / 5; // 设置logo图片高度为二维码图片的五分之一
        logo = BitmapHelper.resizeImage(logo, logoWidth, logoHeight);
        int logoX = (source.getWidth() - logoWidth) / 2; // 设置logo图片的位置,这里令其居中

        int logoY = (source.getHeight() - logoHeight) / 2; // 设置logo图片的位置,这里令其居中
        int right = logoX + logoWidth;  
        int bottom = logoY + logoHeight; 
        
        Rect mRect = new Rect(logoX, logoY, right, bottom);
        
        Canvas canvas = new Canvas(source);
        
        Rect mRectLogo = new Rect(0, 0, logoWidth, logoHeight);
        canvas.drawBitmap(logo, mRectLogo, mRect, null);
        
        return source;
    }
}
