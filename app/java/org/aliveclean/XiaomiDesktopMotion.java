package org.aliveclean;

/**
 * 桌面动效的参数换算：把设置里的幅度/响应裁剪到 -100..100，换算成原版 Offset_ 事件的
 * 百分比与原版相机位移的延迟毫秒数。
 *
 * <p>offset 是围绕中点 50 的增益映射并在两端钳到 0..100，NaN/无穷会回落到中心 50 ——
 * 原版相机位置只认这一区间，所以调用方不必再做防御。</p>
 */
final class XiaomiDesktopMotion {
    static int clamp(int value){return Math.max(-100,Math.min(100,value));}
    static float offset(float value,int amplitude){
        if(!Float.isFinite(value))return 50f;
        return Math.max(0f,Math.min(100f,50f+(value-50f)*(1f+clamp(amplitude)/100f)));
    }

    static long delay(int responsiveness){return 100L-clamp(responsiveness);}
}
