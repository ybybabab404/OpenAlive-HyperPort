package com.miui.clock.tiny.pets.ai;

import android.content.Context;
import android.util.AttributeSet;
import com.miui.clock.tiny.pets.PetsPagView;

/* JADX INFO: loaded from: classes.dex */
public class AiPetsPagView extends PetsPagView {
    public FrameCoordinateAxes mFirstFrameCoordinateAxes;
    public FrameCoordinateAxes mLastFrameCoordinateAxes;

    public static class FrameCoordinateAxes {
        public int leftX;
        public int leftY;
        public int rightX;
        public int rightY;

        public FrameCoordinateAxes(int i, int i2, int i3, int i4) {
            this.leftX = i;
            this.leftY = i2;
            this.rightX = i3;
            this.rightY = i4;
        }

        public String toString() {
            return "FrameCoordinateAxes{leftX=" + this.leftX + ", leftY=" + this.leftY + ", rightX=" + this.rightX + ", rightY=" + this.rightY + '}';
        }
    }

    public AiPetsPagView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastFrameCoordinateAxes = new FrameCoordinateAxes(11, 11, 1440, 1440);
        this.mFirstFrameCoordinateAxes = new FrameCoordinateAxes(10, 10, 1440, 1440);
        this.endStopProgress = 0.0f;
    }
}
