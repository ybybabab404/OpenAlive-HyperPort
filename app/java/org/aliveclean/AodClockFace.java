package org.aliveclean;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 时钟的抽象契约：{@code active} 开关、{@code tick}（外部驱动的刷新入口，实现里按整分去重）、
 * {@code refresh}（整分刷新）与 {@code notificationTop}（通知区域高度）。
 *
 * <p>目前唯一实现是 {@link AodClockView}，只被 {@link EditorClockView} 用在编辑器与桌面预览；
 * {@code tick}/{@code notificationTop} 还没有调用方——留着是为了以后把自绘时钟注入系统 AODView 时有落点。</p>
 */
abstract class AodClockFace extends FrameLayout {
    AodClockFace(Context context){super(context);}
    abstract void active(boolean value);
    abstract void tick(long now);
    abstract void refresh(long now);
    abstract int notificationTop();
}
