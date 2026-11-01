package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.edit.wallpaper.ObserverClickListener;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.TalkBackUtil;
import com.miui.keyguard.editor.view.KgFrame;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MultiTouchFrameLayout.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class MultiTouchFrameLayout extends FrameLayout implements ObserverClickListener {
    public AccessibilityManager am;
    public boolean needDispatch;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiTouchFrameLayout(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = getContext().getSystemService("accessibility");
        this.am = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiTouchFrameLayout(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = getContext().getSystemService("accessibility");
        this.am = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.needDispatch) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.ObserverClickListener
    public boolean onObserveTouchClick(MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        return performChildClick((int) ev.getX(), (int) ev.getY());
    }

    public final boolean performChildClick(int i, int i2) {
        int i3;
        int i4;
        int i5 = 0;
        if (getChildCount() <= 0 || TalkBackUtil.INSTANCE.isTalkBackEnabled(this.am)) {
            Log.w("Keyguard-Editor-MultiTouch", "performChildClick: childCount=" + getChildCount() + " talkBack=" + TalkBackUtil.INSTANCE.isTalkBackEnabled(this.am));
            return false;
        }
        int width = getWidth();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsRtl = deviceUtil.isRtl(context);
        int childCount = getChildCount();
        boolean z = false;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if ((childAt instanceof KgFrame) && childAt.getVisibility() == 0) {
                KgFrame kgFrame = (KgFrame) childAt;
                i3 = i;
                i4 = i2;
                if (kgFrame.inRect(i3, i4, width, zIsRtl, kgFrame.getLayoutDirection())) {
                    Log.i("Keyguard-Editor-MultiTouch", "performChildClick: parentWidth:" + width + " ,isRTL" + zIsRtl);
                    kgFrame.performClick();
                    z = true;
                }
            } else {
                i3 = i;
                i4 = i2;
            }
            i5++;
            i = i3;
            i2 = i4;
        }
        return z;
    }

    public final void updateNeedDispatch(boolean z) {
        this.needDispatch = z;
    }
}
