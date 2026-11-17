package org.aliveclean;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 时钟的抽象契约：{@code active} 开关与 {@code refresh}（整分刷新）。
 *
 * <p>目前唯一实现是 {@link AodClockView}，只被 {@link EditorClockView} 用在编辑器与桌面预览。
 * 原有两个"留给注入系统 AODView"的方法（{@code tick}、{@code notificationTop}）一直零调用方，
 * 2026-11-17 删除；将来真要注入时按当时的时序重新加，不要照抄旧签名。</p>
 */
abstract class AodClockFace extends FrameLayout {
    AodClockFace(Context context){super(context);}
    abstract void active(boolean value);
    abstract void refresh(long now);
}
