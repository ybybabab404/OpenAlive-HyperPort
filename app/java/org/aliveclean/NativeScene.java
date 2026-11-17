package org.aliveclean;

/**
 * native 渲染器的 JNI 出口：创建场景、上传纹理、推帧参数、渲染与销毁。
 *
 * <p>方法签名与 {@code native/renderer.rs} 的 {@code Java_org_aliveclean_NativeScene_*} 一一对应，
 * 改名或改参数必须两边同时改。三个直接缓冲还有容量契约：{@code frame}=36 float/144 B、
 * {@code effects}=20 float/80 B、{@code render}=60 float/240 B，超了 Rust 侧会拒绝。</p>
 */
final class NativeScene {
    /**
     * 库能否加载。模块被覆盖安装后，旧进程里的 ClassLoader 仍指向已被替换掉的 APK 目录，
     * {@code System.loadLibrary} 会抛 {@code UnsatisfiedLinkError}（是 Error，不是 Exception）。
     * 这时候只能放弃渲染——绝不能让一个渲染线程把 SystemUI 带走。
     */
    static final boolean available;
    static {
        boolean loaded;
        try { System.loadLibrary("alive_clean"); loaded = true; }
        catch (Throwable error) {
            AliveLog.e(AliveLog.TAG, "native library alive_clean failed to load; rendering disabled", error);
            loaded = false;
        }
        available = loaded;
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
