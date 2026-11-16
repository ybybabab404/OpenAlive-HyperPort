package org.aliveclean;

/**
 * 非 native 渲染器（Cosmic/Bubble/Flyme/Gradient/Stereo）的动画接口。
 *
 * <p>由 {@link RenderLoop} 在每帧统一驱动：{@code change} 切换场景，
 * {@code advance} 推进时间，{@code follow} 跟随触摸，{@code pause} 停表。</p>
 */
interface WallpaperMotion {
    void change(int scene,boolean animate);
    void advance(long time,float speed);
    void follow(float x,float y);
    void pause();
    boolean active();
}
