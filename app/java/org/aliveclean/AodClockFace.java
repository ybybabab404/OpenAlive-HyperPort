package org.aliveclean;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * AOD 时钟的抽象基类，统一生命周期契约（激活、每秒 tick、整分刷新、通知区域高度）。
 *
 * <p>注入到系统 AODView 的时钟必须继承它，这样 {@code HyperOsAod} 只认一种接口。</p>
 */
abstract class AodClockFace extends FrameLayout {
    AodClockFace(Context context){super(context);}
    abstract void active(boolean value);
    abstract void tick(long now);
    abstract void refresh(long now);
    abstract int notificationTop();
}
