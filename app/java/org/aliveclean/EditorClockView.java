package org.aliveclean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.*;
import android.widget.FrameLayout;

/**
 * 编辑器预览里的时钟容器：AOD 场景被选中时才显示，内部复用 {@link AodClockView}。
 */
public final class EditorClockView extends FrameLayout {
    private final AodClockView face;
    public EditorClockView(Context context,AttributeSet attrs){
        super(context,attrs);setClipChildren(false);
        face=new AodClockView(context,true);addView(face,new LayoutParams(-1,-1));setVisibility(INVISIBLE);
    }
    void scene(boolean selected){
        setVisibility(selected?VISIBLE:INVISIBLE);face.active(selected);
    }
}
