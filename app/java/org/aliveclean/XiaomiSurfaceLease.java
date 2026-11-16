package org.aliveclean;

import android.view.Surface;

/**
 * 向厂商播放器租借 Surface 的小对象：记录 retain/replace/destroy 时序，避免释放后又被取用。
 */
public final class XiaomiSurfaceLease {
    static { System.loadLibrary("alive_clean"); }
    private long handle;
    public XiaomiSurfaceLease(String frameworkPath) {
        handle=open(frameworkPath);
        if(handle==0)AliveLog.w(AliveLog.TAG,"surface lease open failed path="+frameworkPath);
    }
    public void retain(Surface surface) { retain(handle,surface); }
    public void replaced() { replaced(handle); }
    public void destroyed() { if(handle!=0){destroyed(handle);handle=0;} }
    private static native long open(String frameworkPath);
    private static native void retain(long handle,Surface surface);
    private static native void replaced(long handle);
    private static native void destroyed(long handle);
}
