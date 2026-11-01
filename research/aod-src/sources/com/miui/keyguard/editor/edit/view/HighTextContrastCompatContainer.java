package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: HighTextContrastCompatContainer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HighTextContrastCompatContainer extends LinearLayout {
    public final boolean isHighTextContrast;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public HighTextContrastCompatContainer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HighTextContrastCompatContainer(@NotNull final Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        boolean zIsHighTextContrastEnabled = DeviceUtil.INSTANCE.isHighTextContrastEnabled(context);
        this.isHighTextContrast = zIsHighTextContrastEnabled;
        Log.i("Keyguard-Editor-HighTextContrastCompatContainer", "isHighTextContrast: " + zIsHighTextContrastEnabled);
        if (zIsHighTextContrastEnabled) {
            setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.view.HighTextContrastCompatContainer$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HighTextContrastCompatContainer._init_$lambda$0(context, view);
                }
            });
        }
        ViewUtil.INSTANCE.setEnabledAlpha(this, !zIsHighTextContrastEnabled);
    }

    public /* synthetic */ HighTextContrastCompatContainer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void _init_$lambda$0(Context context, View view) {
        ToastUtil.INSTANCE.showText(context, R.string.kg_color_editor_high_text_contrast_tips);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.isHighTextContrast;
    }
}
