package com.liicon.utils.app;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 尺寸处理工具类
 * @version v0.1 king 2014-11-18 尺寸处理
 */
public class SizeUtils {
    public static final int COMPLEX_UNIT_PX = TypedValue.COMPLEX_UNIT_PX;
    public static final int COMPLEX_UNIT_DIP = TypedValue.COMPLEX_UNIT_DIP;
    public static final int COMPLEX_UNIT_SP = TypedValue.COMPLEX_UNIT_SP;
    public static final int COMPLEX_UNIT_PT = TypedValue.COMPLEX_UNIT_PT;
    public static final int COMPLEX_UNIT_IN = TypedValue.COMPLEX_UNIT_IN;
    public static final int COMPLEX_UNIT_MM = TypedValue.COMPLEX_UNIT_MM;

    private static final float MANTISSA_MULT =
        1.0f / (1<<TypedValue.COMPLEX_MANTISSA_SHIFT);
    private static final float[] RADIX_MULTS = new float[] {
        1.0f*MANTISSA_MULT, 1.0f/(1<<7)*MANTISSA_MULT,
        1.0f/(1<<15)*MANTISSA_MULT, 1.0f/(1<<23)*MANTISSA_MULT
    };
    
    /**
     * 根据指定单位，计算尺寸大小
     * @param unit
     * @param value
     * @param metrics
     * @return
     */
    protected static float applyDimension(int unit, float value,
            DisplayMetrics metrics) {
        switch (unit) {
            case COMPLEX_UNIT_PX:
                return value;
            case COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }
    
    protected static float complexToFloat(int complex) {
        return (complex & (TypedValue.COMPLEX_MANTISSA_MASK << TypedValue.COMPLEX_MANTISSA_SHIFT))
                * RADIX_MULTS[(complex >> TypedValue.COMPLEX_RADIX_SHIFT)
                        & TypedValue.COMPLEX_RADIX_MASK];
    }
    
    
    
    
    
    

    /**
     * 根据手机分辨率从dp转成px
     * @param context
     * @param dipValue
     * @return
     */
    public static  int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
      
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static  int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f)-15;
    }
    
}
