package com.miui.keyguard.editor.settings.util;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
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
    */
/* JADX INFO: compiled from: ViewUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ViewUtilKt {
    public static final EaseManager.EaseStyle hideEase;
    public static final EaseManager.EaseStyle showEase;

    static {
        EaseManager.EaseStyle style = EaseManager.getStyle(-2, 1.0f, 0.35f);
        Intrinsics.checkNotNullExpressionValue(style, "getStyle(...)");
        showEase = style;
        EaseManager.EaseStyle style2 = EaseManager.getStyle(-2, 1.0f, 0.15f);
        Intrinsics.checkNotNullExpressionValue(style2, "getStyle(...)");
        hideEase = style2;
    }

    public static final void alphaAnim(final View view, final boolean z, final Function0 callback) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(callback, "callback");
        AnimConfig animConfigAddListeners = new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.settings.util.ViewUtilKt$alphaAnim$translateConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                if (z) {
                    view.setVisibility(0);
                } else {
                    view.setTag("isHideAnimating");
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                callback.invoke();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (z) {
                    view.setVisibility(0);
                } else {
                    view.setVisibility(8);
                    view.setAlpha(1.0f);
                }
                callback.invoke();
            }
        });
        Intrinsics.checkNotNullExpressionValue(animConfigAddListeners, "addListeners(...)");
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useAt(view).state().setEase(z ? showEase : hideEase, new FloatProperty[0]).setTo(animState.add(viewProperty, z ? 0.0f : 1.0f, new long[0])).to(new AnimState().add(viewProperty, z ? 1.0f : 0.0f, new long[0]), animConfigAddListeners);
    }

    public static final void fakeHide(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(0);
        if (view.getAlpha() > 0.0f) {
            view.setAlpha(0.0f);
        }
    }

    public static final void hide(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(8);
    }

    public static final void hideByAlphaAnim(View view, Function0 callback) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (view.getVisibility() == 4 || view.getVisibility() == 8) {
            callback.invoke();
        } else {
            alphaAnim(view, false, callback);
        }
    }

    public static /* synthetic */ void hideByAlphaAnim$default(View view, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = new Function0() { // from class: com.miui.keyguard.editor.settings.util.ViewUtilKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Unit.INSTANCE;
                }
            };
        }
        hideByAlphaAnim(view, function0);
    }

    public static final boolean isNoTransparent(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getVisibility() == 0 && view.getAlpha() > 0.0f;
    }

    public static final void show(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(0);
        if (view.getAlpha() < 1.0f) {
            view.setAlpha(1.0f);
        }
    }

    public static final void showByAlphaAnim(View view, Function0 callback) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(callback, "callback");
        view.setVisibility(0);
        if (view.getAlpha() < 1.0f) {
            alphaAnim(view, true, callback);
        }
    }

    public static final void switchApplyBtnWithAnim(final View view, final boolean z) {
        String str;
        Intrinsics.checkNotNullParameter(view, "<this>");
        TextView textView = view instanceof TextView ? (TextView) view : null;
        CharSequence text = textView != null ? textView.getText() : null;
        float alpha = view.getAlpha();
        boolean z2 = view.getVisibility() == 0;
        Log.d("ViewUtil", ((Object) text) + " is show=" + z + " alpha=" + alpha + ", isVisible=" + z2 + ", w=" + view.getWidth() + " h=" + view.getHeight() + " x=" + view.getX() + " y=" + view.getY());
        if (z) {
            if (view.getVisibility() == 0 && view.getAlpha() == 1.0f) {
                return;
            }
            Object tag = view.getTag();
            String str2 = tag instanceof String ? (String) tag : null;
            if (str2 != null && str2.equals("isHideAnimating") && view.getVisibility() == 0) {
                view.setTag(null);
            }
            Object tag2 = view.getTag();
            str = tag2 instanceof String ? (String) tag2 : null;
            if (str != null && str.equals("isShowAnimating")) {
                return;
            }
        } else {
            if (view.getVisibility() == 8) {
                return;
            }
            Object tag3 = view.getTag();
            String str3 = tag3 instanceof String ? (String) tag3 : null;
            if (str3 != null && str3.equals("isShowAnimating") && view.getVisibility() == 8) {
                view.setTag(null);
            }
            Object tag4 = view.getTag();
            str = tag4 instanceof String ? (String) tag4 : null;
            if (str != null && str.equals("isHideAnimating")) {
                return;
            }
        }
        AnimConfig animConfigAddListeners = new AnimConfig().addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.settings.util.ViewUtilKt$switchApplyBtnWithAnim$translateConfig$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                if (z) {
                    view.setVisibility(0);
                    view.setTag("isShowAnimating");
                } else {
                    view.setTag("isHideAnimating");
                }
                View view2 = view;
                TextView textView2 = view2 instanceof TextView ? (TextView) view2 : null;
                CharSequence text2 = textView2 != null ? textView2.getText() : null;
                float alpha2 = view.getAlpha();
                boolean z3 = view.getVisibility() == 0;
                Log.d("ViewUtil", "onBegin " + ((Object) text2) + "  alpha=" + alpha2 + ", isVisible=" + z3 + ", tag=" + view.getTag());
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                view.setTag(null);
                View view2 = view;
                TextView textView2 = view2 instanceof TextView ? (TextView) view2 : null;
                CharSequence text2 = textView2 != null ? textView2.getText() : null;
                float alpha2 = view.getAlpha();
                boolean z3 = view.getVisibility() == 0;
                Log.d("ViewUtil", "onCancel " + ((Object) text2) + "  alpha=" + alpha2 + ", isVisible=" + z3 + ", tag=" + view.getTag());
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (z) {
                    view.setVisibility(0);
                } else {
                    view.setVisibility(8);
                    view.setAlpha(1.0f);
                }
                view.setTag(null);
                View view2 = view;
                TextView textView2 = view2 instanceof TextView ? (TextView) view2 : null;
                CharSequence text2 = textView2 != null ? textView2.getText() : null;
                float alpha2 = view.getAlpha();
                boolean z3 = view.getVisibility() == 0;
                Log.d("ViewUtil", "onComplete " + ((Object) text2) + " alpha=" + alpha2 + ", isVisible=" + z3 + ", tag=" + view.getTag() + ", w=" + view.getWidth() + " h=" + view.getHeight() + " x=" + view.getX() + " y=" + view.getY());
            }
        });
        Intrinsics.checkNotNullExpressionValue(animConfigAddListeners, "addListeners(...)");
        AnimState animState = new AnimState();
        ViewProperty viewProperty = ViewProperty.ALPHA;
        Folme.useAt(view).state().setEase(z ? showEase : hideEase, new FloatProperty[0]).setTo(animState.add(viewProperty, z ? 0.0f : 1.0f, new long[0])).to(new AnimState().add(viewProperty, z ? 1.0f : 0.0f, new long[0]), animConfigAddListeners);
    }
}
