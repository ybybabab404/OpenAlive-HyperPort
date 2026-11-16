package org.aliveclean;

/**
 * native 渲染器的 JNI 出口：创建场景、上传纹理、推帧参数、渲染与销毁。
 *
 * <p>方法签名与 {@code native/renderer.rs} 的 {@code Java_org_aliveclean_NativeScene_*} 一一对应，
 * 改名或改参数必须两边同时改。三个直接缓冲还有容量契约：{@code frame}=36 float/144 B、
 * {@code effects}=20 float/80 B、{@code render}=60 float/240 B，超了 Rust 侧会拒绝。</p>
 */
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
