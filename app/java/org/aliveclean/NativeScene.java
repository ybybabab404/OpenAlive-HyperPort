package org.aliveclean;

final class NativeScene {
    static {
        try { System.loadLibrary("alive_clean"); }
        catch (UnsatisfiedLinkError | SecurityException error) {
            AliveLog.e(AliveLog.TAG, "native library alive_clean failed to load", error);
            throw error;
        }
    }
    static native long create(int width,int height,int mode,int aod,int lock,int home);
    static native void texture(long handle,int slot,int texture,int width,int height);
    static native void tint(long handle,int color);
    static native void frameCrop(long handle,float x,float y,float size,float angle);
    static native boolean frame(long handle,java.nio.ByteBuffer frame);
    static native boolean effects(long handle,java.nio.ByteBuffer effects);
    static native boolean render(long handle,int photo,int decorator,float photoAspect,java.nio.ByteBuffer frame);
    static native void destroy(long handle);
}
