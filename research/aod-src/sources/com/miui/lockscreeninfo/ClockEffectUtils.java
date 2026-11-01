package com.miui.lockscreeninfo;

import android.view.View;
import com.miui.lockscreeninfo.model.SignatureInfo;

/* JADX INFO: loaded from: classes2.dex */
public class ClockEffectUtils {
    public static void clearClockEffectsContainer(View view, SignatureInfo signatureInfo, boolean z) {
        if (signatureInfo == null) {
            return;
        }
        int clockEffect = signatureInfo.getClockEffect();
        if (z || !isBlurMixType(clockEffect)) {
            VerticalTextViewUtils.clearContainerPassBlur(view);
        }
        if (z || !isDifferenceType(clockEffect)) {
            VerticalTextViewUtils.clearDiffEffectContainer(view);
        }
        if (z || !isGlowType(clockEffect)) {
            VerticalTextViewUtils.clearGlowEffectContainer(view);
        }
    }

    public static void clearClockEffectsView(View view, SignatureInfo signatureInfo, boolean z) {
        if (signatureInfo == null) {
            return;
        }
        int clockEffect = signatureInfo.getClockEffect();
        if (z || !isBlurMixType(clockEffect)) {
            VerticalTextViewUtils.clearMemberBlendColor(view);
        }
        if (z || !isDifferenceType(clockEffect)) {
            VerticalTextViewUtils.clearDiffEffectMethod(view);
        }
        if (z || !isGlowType(clockEffect)) {
            VerticalTextViewUtils.clearGlowEffectMethod(view);
        }
    }

    public static boolean isBlurMixType(int i) {
        return i == 1;
    }

    public static boolean isDifferenceType(int i) {
        return i == 2 && VerticalTextViewUtils.supportAdvanceVisualEffect();
    }

    public static boolean isGlowType(int i) {
        return false;
    }

    public static boolean isGradualType(int i) {
        return i == 4 && VerticalTextViewUtils.supportAdvanceVisualEffect();
    }

    public static boolean isSolidType(int i) {
        return i == 0;
    }

    public static void setClockEffectsContainer(View view, int i, SignatureInfo signatureInfo, boolean z) {
        if (signatureInfo == null || view == null) {
            return;
        }
        int clockEffect = signatureInfo.getClockEffect();
        if (isBlurMixType(clockEffect) && VerticalTextViewUtils.deviceBlurEnabled()) {
            VerticalTextViewUtils.setContainerPassBlur(view, i, signatureInfo.isMultiWindowBlur());
            return;
        }
        if (isDifferenceType(clockEffect) && VerticalTextViewUtils.supportAdvanceVisualEffect()) {
            if (z) {
                return;
            }
            VerticalTextViewUtils.setDiffEffectContainer(view, signatureInfo.isMultiWindowBlur());
        } else if (!(isGradualType(clockEffect) && VerticalTextViewUtils.supportAdvanceVisualEffect()) && isGlowType(clockEffect) && VerticalTextViewUtils.supportAdvanceVisualEffect()) {
            VerticalTextViewUtils.setGlowEffectContainer(view);
        }
    }
}
