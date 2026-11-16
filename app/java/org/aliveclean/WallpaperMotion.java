package org.aliveclean;

/**
 * 非 native 渲染器（Cosmic/Bubble/Flyme/Gradient/Stereo）的动画接口。
 *
 * <p>由 {@link RenderLoop} 在每帧统一驱动：{@code change} 切换场景，{@code advance} 推进时间，
 * {@code pause} 停表。{@code follow} 传的是系统上报的 AOD 区域坐标（{@code AodRegion.sceneX/Y}，即时钟位置），
 * <b>不是触摸</b>——只有 CosmicMotion 真正使用它，其余实现对它是空方法；触摸走
 * {@code WallpaperRenderer.interaction()} / {@code VivoEngineScene.touch()}。</p>
 */
interface WallpaperMotion {
    void change(int scene,boolean animate);
    void advance(long time,float speed);
    void follow(float x,float y);
    void pause();
    boolean active();
}
