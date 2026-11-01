package com.miui.aod.template;

import android.content.Context;
import android.widget.FrameLayout;
import com.miui.aod.R;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FullAodTemplateView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FullAodTemplateViewKt {
    public static final String TAG = "FullAodTemplateView";

    public static final FullAodTemplateView createScaledFullAodTemplateView(Context context, String templateId, FrameLayout.LayoutParams layoutParams, float f, float f2, TemplateConfig templateConfig, float[] fArr) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        if (templateConfig == null) {
            return null;
        }
        FullAodTemplateView fullAodTemplateView = new FullAodTemplateView(context, templateConfig);
        if (layoutParams.gravity == -1) {
            layoutParams.gravity = DeviceUtil.INSTANCE.isRtl(context) ? 8388613 : 8388611;
        }
        fullAodTemplateView.setLayoutParams(layoutParams);
        fullAodTemplateView.setPivotX(layoutParams.width / 2.0f);
        fullAodTemplateView.setPivotY(0.0f);
        fullAodTemplateView.setScaleX(f);
        fullAodTemplateView.setScaleY(f2);
        if (f == 0.0f) {
            f = 1.0f;
        }
        fullAodTemplateView.setClipToOutline(true);
        fullAodTemplateView.setClipChildren(true);
        fullAodTemplateView.setClipToPadding(true);
        ViewUtil.INSTANCE.setSmoothCornerEnabled(fullAodTemplateView, true);
        if (fArr != null) {
            fullAodTemplateView.setOutlineProvider(new RoundOutlineProvider(fArr));
            fullAodTemplateView.updateRadiiAndColor(context.getColor(R.color.aod_preview_border_color), fArr);
            return fullAodTemplateView;
        }
        float dimension = fullAodTemplateView.getResources().getDimension(R.dimen.aod_content_bg_radius) / f;
        fullAodTemplateView.setOutlineProvider(new RoundOutlineProvider(dimension));
        fullAodTemplateView.updateRadiusAndColor(context.getColor(R.color.aod_preview_border_color), (int) dimension);
        return fullAodTemplateView;
    }
}
