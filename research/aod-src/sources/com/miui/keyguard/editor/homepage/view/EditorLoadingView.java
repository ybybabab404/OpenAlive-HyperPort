package com.miui.keyguard.editor.homepage.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.AnimatorKt;
import com.miui.keyguard.editor.utils.DensityUtils;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.utils.EaseManager;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: EditorLoadingView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class EditorLoadingView extends RelativeLayout {
    public int backgroundColorId;
    public ProgressBar progressBar;
    public TextView titleView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorLoadingView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.backgroundColorId = R.color.kg_black_30;
        initView$default(this, context, false, 2, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorLoadingView(Context context, int i) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.backgroundColorId = i;
        initView(context, false);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorLoadingView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.backgroundColorId = R.color.kg_black_30;
        initView$default(this, context, false, 2, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditorLoadingView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.backgroundColorId = R.color.kg_black_30;
        initView$default(this, context, false, 2, null);
    }

    public static /* synthetic */ void initView$default(EditorLoadingView editorLoadingView, Context context, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        editorLoadingView.initView(context, z);
    }

    public static final void initView$lambda$0(View view) {
    }

    public final void hideLoading(final Function0 function0) {
        Folme.useValue(new Object[0]).setTo("loading_alpha", Float.valueOf(1.0f)).to("loading_alpha", Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(-2, 0.95f, 0.35f)).addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.homepage.view.EditorLoadingView.hideLoading.1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                AnimatorKt.alphaTo$default(EditorLoadingView.this, 0.0f, 0.0f, 0.0f, function0, 6, null);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                AnimatorKt.alphaTo$default(EditorLoadingView.this, 0.0f, 0.0f, 0.0f, function0, 6, null);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                float fCoerceAtMost = RangesKt.coerceAtMost(RangesKt.coerceAtLeast(UpdateInfo.findByName(collection, "loading_alpha").getFloatValue(), 0.0f), 1.0f);
                ProgressBar progressBar = EditorLoadingView.this.progressBar;
                if (progressBar != null) {
                    progressBar.setAlpha(fCoerceAtMost);
                }
                TextView textView = EditorLoadingView.this.titleView;
                if (textView != null) {
                    textView.setAlpha(fCoerceAtMost);
                }
            }
        }));
    }

    public final void initView(Context context, boolean z) {
        setGravity(16);
        setBackgroundColor(context.getResources().getColor(this.backgroundColorId));
        ProgressBar progressBar = new ProgressBar(context);
        this.progressBar = progressBar;
        Intrinsics.checkNotNull(progressBar);
        progressBar.setId(R.id.kg_hierarchy_loading);
        Resources resources = context.getResources();
        int i = R.dimen.kg_hierarchy_loading_icon_size;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(resources.getDimensionPixelSize(i), context.getResources().getDimensionPixelSize(i));
        layoutParams.addRule(13);
        layoutParams.addRule(1);
        ProgressBar progressBar2 = this.progressBar;
        Intrinsics.checkNotNull(progressBar2);
        progressBar2.setLayoutParams(layoutParams);
        TextView textView = new TextView(context);
        this.titleView = textView;
        textView.setId(R.id.kg_hierarchy_loading_text);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        ProgressBar progressBar3 = this.progressBar;
        Intrinsics.checkNotNull(progressBar3);
        layoutParams2.addRule(3, progressBar3.getId());
        layoutParams2.topMargin = DensityUtils.dp2Px(8);
        layoutParams2.addRule(13);
        layoutParams2.addRule(1);
        TextView textView2 = this.titleView;
        if (textView2 != null) {
            textView2.setLayoutParams(layoutParams2);
        }
        TextView textView3 = this.titleView;
        if (textView3 != null) {
            textView3.setText(context.getResources().getString(R.string.kg_hierarchy_loading_tip));
        }
        TextView textView4 = this.titleView;
        if (textView4 != null) {
            textView4.setTextColor(context.getResources().getColor(R.color.kg_white_100));
        }
        Typeface typefaceCreate = Typeface.create("mipro-regular", 0);
        TextView textView5 = this.titleView;
        if (textView5 != null) {
            textView5.setTypeface(typefaceCreate);
        }
        TextView textView6 = this.titleView;
        if (textView6 != null) {
            textView6.setTextSize(0, DensityUtils.dp2Px(14));
        }
        if (z) {
            ProgressBar progressBar4 = this.progressBar;
            if (progressBar4 != null) {
                progressBar4.setVisibility(0);
            }
            TextView textView7 = this.titleView;
            if (textView7 != null) {
                textView7.setVisibility(0);
            }
        } else {
            ProgressBar progressBar5 = this.progressBar;
            if (progressBar5 != null) {
                progressBar5.setVisibility(8);
            }
            TextView textView8 = this.titleView;
            if (textView8 != null) {
                textView8.setVisibility(8);
            }
        }
        addView(this.progressBar);
        addView(this.titleView);
        setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.homepage.view.EditorLoadingView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditorLoadingView.initView$lambda$0(view);
            }
        });
        setKeepScreenOn(true);
    }

    @Override // android.view.View
    public void onVisibilityChanged(View changedView, int i) {
        Intrinsics.checkNotNullParameter(changedView, "changedView");
        super.onVisibilityChanged(changedView, i);
        setKeepScreenOn(i == 0);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        setKeepScreenOn(i == 0);
    }

    public final void showLoading() {
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        TextView textView = this.titleView;
        if (textView != null) {
            textView.setVisibility(0);
        }
    }
}
