package com.demo.amber.com.testdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.FloatRange;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.support.v8.renderscript.Type;

/**
 * Created by Amber on 2017/4/6. 模糊图片工具类
 */
public class BlurBitmapUtil {

    /**
     * 对图片进行高斯模糊
     *
     * @param context
     *            上下文对象
     * @param bitmap
     *            需要模糊的图片
     * @param blurRadius
     *            模糊半径，由于性能限制，这个值的取值区间为(0至25f)
     * @return 模糊处理后的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, @FloatRange(from = 1, to = 25) float blurRadius) {

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象，第二个参数Element相当于一种像素处理的算法，高斯模糊的话用这个就好
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        // 创建相同类型的Allocation对象用来输出
        Type type = input.getType();
        Allocation output = Allocation.createTyped(rs, type);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(input);
        // 将输出数据保存到输出内存中
        blurScript.forEach(output);
        // 将数据填充到bitmap中
        output.copyTo(bitmap);

        // 销毁它们释放内存
        input.destroy();
        output.destroy();
        blurScript.destroy();
        rs.destroy();
        type.destroy();

        return bitmap;
    }

}