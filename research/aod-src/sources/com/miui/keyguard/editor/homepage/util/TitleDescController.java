package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TitleDescController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TitleDescController {
    public final String TAG;
    public final View contentView;
    public final Context context;
    public final TextView descriptionView;
    public boolean isCanPerformSettlingTransition;
    public boolean isFlingTransition;
    public float maxScrollDownIsolatedThreshold;
    public float minAlphaScrollThreshold;
    public final Resources resources;
    public float scrollDownThreshold;
    public final ScrollHelper scrollHelper;
    public float scrollUpThreshold;
    public final RelativeLayout titleModule;
    public final TextView titleView;

    public TitleDescController(View contentView) {
        Intrinsics.checkNotNullParameter(contentView, "contentView");
        this.contentView = contentView;
        this.TAG = "Keyguard-Editor-TitleDescController";
        this.context = contentView.getContext();
        this.resources = contentView.getResources();
        View viewFindViewById = contentView.findViewById(R.id.kg_title_module);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.titleModule = (RelativeLayout) viewFindViewById;
        View viewFindViewById2 = contentView.findViewById(R.id.kg_tv_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.titleView = (TextView) viewFindViewById2;
        View viewFindViewById3 = contentView.findViewById(R.id.kg_tv_description);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.descriptionView = (TextView) viewFindViewById3;
        this.scrollUpThreshold = getDimen(R.dimen.kg_main_item_scroll_up_alpha_threshold);
        float dimen = getDimen(R.dimen.kg_main_item_scroll_down_alpha_threshold);
        this.scrollDownThreshold = dimen;
        this.minAlphaScrollThreshold = this.scrollUpThreshold;
        this.maxScrollDownIsolatedThreshold = dimen;
        this.scrollHelper = new ScrollHelper();
    }

    public final float getDimen(int i) {
        return this.resources.getDimension(i);
    }

    public final View getTitleModule() {
        return this.titleModule;
    }

    public final void hideTitleModule(boolean z) {
        Folme.clean(this.titleModule);
        if (this.titleModule.getVisibility() == 4) {
            return;
        }
        if (!z) {
            this.titleModule.setVisibility(4);
            return;
        }
        AnimState animState = new AnimState("hide_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 1.0f, new long[0]);
        Folme.use((View) this.titleModule).state().setTo(animStateAdd).to(new AnimState("hide_end").add(viewProperty, 0.0f, new long[0]), TransitionKt.getAnimConfig(0.95f, 0.35f, new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.util.TitleDescController$hideTitleModule$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                this.this$0.titleModule.setVisibility(4);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                this.this$0.titleModule.setVisibility(4);
            }
        }));
    }

    public final void performBackwardTransition() {
        Folme.useAt(this.titleModule).state().setup("performBackwardTransition.title").add((FloatProperty) ViewProperty.ALPHA, 1.0f).add((FloatProperty) ViewProperty.TRANSLATION_Y, 0).to("performBackwardTransition.title", TransitionKt.getAnimConfig(0.95f, 0.3f));
    }

    public final void performForwardTransition() {
        float fProvideCrossTitleTopMargin;
        Folme.clean(this.titleModule);
        ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
        Float provideTemplateTitleTopMargin = resourcesProvider.getProvideTemplateTitleTopMargin();
        if (provideTemplateTitleTopMargin != null) {
            float fFloatValue = provideTemplateTitleTopMargin.floatValue();
            Context context = this.context;
            Intrinsics.checkNotNullExpressionValue(context, "context");
            fProvideCrossTitleTopMargin = fFloatValue - resourcesProvider.provideCrossTitleTopMargin(context);
        } else {
            fProvideCrossTitleTopMargin = 0.0f;
        }
        Folme.useAt(this.titleModule).state().setup("performForwardTransition.title.alpha").add((FloatProperty) ViewProperty.ALPHA, 0.0f).to("performForwardTransition.title.alpha", TransitionKt.getAnimConfig(0.95f, 0.18f));
        Folme.useAt(this.titleModule).state().setup("performForwardTransition.title.transY").add(ViewProperty.TRANSLATION_Y, fProvideCrossTitleTopMargin).to("performForwardTransition.title.transY", TransitionKt.getAnimConfig(0.95f, 0.3f));
    }

    public final void setTitle(int i) {
        this.titleView.setText(i);
    }

    public final void showTitleModule(boolean z) {
        Folme.clean(this.titleModule);
        if (this.titleModule.getVisibility() == 0) {
            return;
        }
        if (!z) {
            this.titleModule.setVisibility(0);
            return;
        }
        AnimState animState = new AnimState("show_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 0.0f, new long[0]);
        Folme.use((View) this.titleModule).state().setTo(animStateAdd).to(new AnimState("show_end").add(viewProperty, 1.0f, new long[0]), TransitionKt.getAnimConfig(0.95f, 0.35f, new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.util.TitleDescController$showTitleModule$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                this.this$0.titleModule.setVisibility(0);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                this.this$0.titleModule.setVisibility(0);
            }
        }));
    }
}
