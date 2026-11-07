package org.aliveclean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.*;
import android.widget.FrameLayout;

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
