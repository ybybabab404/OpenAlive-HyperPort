package org.aliveclean;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 时钟的抽象契约（激活、每秒 tick、整分刷新、通知区域高度）。
 *
 * <p>目前唯一实现是 {@link AodClockView}，且只有编辑器预览在用；{@code tick} 与
 * {@code notificationTop} 还没有调用方——留着是为了以后把自绘时钟再注入系统 AODView 时有落点。</p>
 */
abstract class AodClockFace extends FrameLayout {
    AodClockFace(Context context){super(context);}
    abstract void active(boolean value);
    abstract void tick(long now);
    abstract void refresh(long now);
    abstract int notificationTop();
}
