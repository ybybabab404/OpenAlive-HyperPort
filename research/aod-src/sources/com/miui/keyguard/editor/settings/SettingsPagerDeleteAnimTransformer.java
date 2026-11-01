package com.miui.keyguard.editor.settings;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.controller.AnimState;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: SettingsPagerDeleteAnimTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsPagerDeleteAnimTransformer implements ViewPager2.PageTransformer {
    public static final Companion Companion = new Companion(null);
    public static final EaseManager.EaseStyle hideEase;
    public static final EaseManager.EaseStyle showAlphaEase;
    public static final EaseManager.EaseStyle showScaleEase;

    /* JADX INFO: compiled from: SettingsPagerDeleteAnimTransformer.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 1.0f, 0.35f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        showAlphaEase = style;
        EaseManager.EaseStyle style2 = EaseManager.getStyle(-2, 0.8f, 0.35f);
        Intrinsics.checkNotNullExpressionValue(style2, "getStyle(...)");
        showScaleEase = style2;
        EaseManager.EaseStyle style3 = EaseManager.getStyle(-2, 1.0f, 0.35f);
        Intrinsics.checkNotNullExpressionValue(style3, "getStyle(...)");
        hideEase = style3;
    }

    public final void hideDeleteButtonAnim(View view) {
        Log.d("SettingsPagerDeleteAnimTransformer", "Hiding delete : " + view);
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 1.0f, new long[0]);
        ViewProperty viewProperty2 = ViewProperty.SCALE_X;
        AnimState animStateAdd2 = animStateAdd.add(viewProperty2, 1.0f, new long[0]);
        ViewProperty viewProperty3 = ViewProperty.SCALE_Y;
        Folme.useAt(view).state().setEase(hideEase, new FloatProperty[0]).setTo(animStateAdd2.add(viewProperty3, 1.0f, new long[0])).to(new AnimState().add(viewProperty, 0.0f, new long[0]).add(viewProperty2, 0.8f, new long[0]).add(viewProperty3, 0.8f, new long[0]), null);
    }

    public final void showDeleteButtonAnim(View view) {
        Log.d("SettingsPagerDeleteAnimTransformer", "Showing delete : " + view);
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useAt(view).state().setEase(showAlphaEase, new FloatProperty[0]).setTo(animState.add(viewProperty, 0.0f, new long[0])).to(new AnimState().add(viewProperty, 1.0f, new long[0]), null);
        AnimState animState2 = new AnimState();
        ViewProperty viewProperty2 = ViewProperty.SCALE_X;
        AnimState animStateAdd = animState2.add(viewProperty2, 0.8f, new long[0]);
        ViewProperty viewProperty3 = ViewProperty.SCALE_Y;
        Folme.useAt(view).state().setEase(showScaleEase, new FloatProperty[0]).setTo(animStateAdd.add(viewProperty3, 0.8f, new long[0])).to(new AnimState().add(viewProperty2, 1.0f, new long[0]).add(viewProperty3, 1.0f, new long[0]), null);
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.PageTransformer
    public void transformPage(View page, float f) {
        Intrinsics.checkNotNullParameter(page, "page");
        Object tag = page.getTag(R.id.kg_template_holder);
        if (tag instanceof TemplateHistoryViewHolder) {
            ImageView deleteTemplateButton = ((TemplateHistoryViewHolder) tag).getDeleteTemplateButton();
            if (deleteTemplateButton.getVisibility() != 0) {
                return;
            }
            if (f == -1.0f || f == 1.0f) {
                if (deleteTemplateButton.getAlpha() > 0.0f) {
                    hideDeleteButtonAnim(deleteTemplateButton);
                }
            } else {
                if (f != 0.0f || deleteTemplateButton.getAlpha() >= 1.0f) {
                    return;
                }
                showDeleteButtonAnim(deleteTemplateButton);
            }
        }
    }
}
