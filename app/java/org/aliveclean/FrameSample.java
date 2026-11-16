package org.aliveclean;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 单帧参数快照：把当帧的 FrameMotion / SceneMotion / TextureMotion uniform 打包进直接缓冲，
 * 供渲染线程在异步阶段复用（避免边算边改）。
 */
final class FrameSample {
    final ByteBuffer effect=ByteBuffer.allocateDirect(36*4).order(ByteOrder.nativeOrder());
    final ByteBuffer base=ByteBuffer.allocateDirect(SceneMotion.COUNT*4).order(ByteOrder.nativeOrder());
    final ByteBuffer textures=ByteBuffer.allocateDirect(20*4).order(ByteOrder.nativeOrder());
    int mask;
    boolean pending,expanded;
    long capturedAt;
    void capture(FrameMotion frame,SceneMotion common,long now){
        if(pending)return;
        frame.ratios(common);
        effect.clear();effect.put(frame.packet());effect.flip();
        base.clear();base.put(common.packet());base.flip();
        mask=frame.style.index((long)frame.values[6]);capturedAt=now;expanded=frame.expanded();pending=true;
    }
    void clear(){pending=false;}
    void captureTextures(TextureMotion motion){textures.clear();textures.put(motion.packet());textures.flip();}
}
