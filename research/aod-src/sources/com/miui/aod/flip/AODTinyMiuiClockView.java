package com.miui.aod.flip;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.aod.widget.Direction;
import com.miui.clock.tiny.TinyMiuiClockView;
import com.miui.clock.tiny.model.TinyClockBean;

/* JADX INFO: loaded from: classes.dex */
public final class AODTinyMiuiClockView extends TinyMiuiClockView {

    /* JADX INFO: renamed from: com.miui.aod.flip.AODTinyMiuiClockView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId;

        static {
            int[] iArr = new int[FlipLinkageTemplateId.values().length];
            $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId = iArr;
            try {
                iArr[FlipLinkageTemplateId.SPLICING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId[FlipLinkageTemplateId.Classic.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId[FlipLinkageTemplateId.Doodle.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId[FlipLinkageTemplateId.Bubble.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId[FlipLinkageTemplateId.Interactive_Pets.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId[FlipLinkageTemplateId.AI_Pets.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId[FlipLinkageTemplateId.Pets.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public AODTinyMiuiClockView(@NonNull Context context) {
        this(context, null);
    }

    public AODTinyMiuiClockView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private int getMargin() {
        return super.getMarginAOD();
    }

    public Point getMargin(FlipLinkageTemplateId flipLinkageTemplateId, Direction direction) {
        Point point = new Point(0, 0);
        switch (AnonymousClass1.$SwitchMap$com$miui$aod$flip$FlipLinkageTemplateId[flipLinkageTemplateId.ordinal()]) {
            case 1:
                return new Point(getMargin(), 0);
            case 2:
                if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
                    return new Point(0, getMargin());
                }
                return direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT ? new Point(getMargin(), 0) : point;
            case 3:
            case 4:
            case 5:
            case 6:
                return new Point(0, 0);
            case 7:
                return new Point(getMargin(), 0);
            default:
                return new Point(0, 0);
        }
    }

    public TinyMiuiClockView init(TinyClockBean tinyClockBean) {
        return super.init(tinyClockBean, 1);
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
    }
}
