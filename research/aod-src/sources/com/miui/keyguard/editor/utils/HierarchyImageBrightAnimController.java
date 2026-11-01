package com.miui.keyguard.editor.utils;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: HierarchyImageBrightAnimController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HierarchyImageBrightAnimController {
    public final HierarchyImageView bindHierarchyImageView;
    public final ColorMatrix changeBrightNessColorMatrix;
    public float currentBitmapBrightNess;
    public BitmapDrawable currentBitmapDrawable;
    public boolean isPerformAnim;
    public IStateStyle performingBrightNessAnim;

    public HierarchyImageBrightAnimController(HierarchyImageView bindHierarchyImageView) {
        Intrinsics.checkNotNullParameter(bindHierarchyImageView, "bindHierarchyImageView");
        this.bindHierarchyImageView = bindHierarchyImageView;
        this.changeBrightNessColorMatrix = new ColorMatrix();
        this.currentBitmapBrightNess = 1.0f;
    }

    public final void bitmapBrightNessAnimEnd() {
        this.isPerformAnim = false;
        this.performingBrightNessAnim = null;
        this.currentBitmapBrightNess = 1.0f;
    }

    public final void changeBitmapBrightNessAnim() {
        if (this.isPerformAnim) {
            IStateStyle iStateStyle = this.performingBrightNessAnim;
            if (iStateStyle != null) {
                iStateStyle.cancel();
            }
            this.isPerformAnim = false;
        }
        Drawable drawable = this.bindHierarchyImageView.getDrawable();
        BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
        this.currentBitmapDrawable = bitmapDrawable;
        if (bitmapDrawable != null) {
            startInCreaseBrightNessAnim();
        }
    }

    public final void startDeCreaseBrightNessAnim() {
        if (this.currentBitmapBrightNess == 1.0f) {
            bitmapBrightNessAnimEnd();
            return;
        }
        AnimConfig animConfig = TransitionKt.getAnimConfig(0.95f, 0.4f);
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.utils.HierarchyImageBrightAnimController$startDeCreaseBrightNessAnim$animConfig$1$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                this.this$0.isPerformAnim = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                this.this$0.bitmapBrightNessAnimEnd();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                this.this$0.bitmapBrightNessAnimEnd();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object toTag, Collection updateList) {
                Intrinsics.checkNotNullParameter(toTag, "toTag");
                Intrinsics.checkNotNullParameter(updateList, "updateList");
                super.onUpdate(toTag, updateList);
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(updateList, "brightness");
                if (updateInfoFindByName != null) {
                    this.this$0.updateBitmapBrightNess(updateInfoFindByName.getFloatValue());
                }
            }
        });
        this.performingBrightNessAnim = Folme.useValue("deCreaseBitmapBrightness").setup("setUp").setTo("brightness", Float.valueOf(this.currentBitmapBrightNess)).to("brightness", Float.valueOf(1.0f), animConfig);
    }

    public final void startInCreaseBrightNessAnim() {
        AnimConfig animConfig = TransitionKt.getAnimConfig(0.95f, 0.22f);
        animConfig.addListeners(new TransitionListener() { // from class: com.miui.keyguard.editor.utils.HierarchyImageBrightAnimController$startInCreaseBrightNessAnim$animConfig$1$1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                this.this$0.isPerformAnim = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                super.onCancel(obj);
                this.this$0.startDeCreaseBrightNessAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                this.this$0.startDeCreaseBrightNessAnim();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object toTag, Collection updateList) {
                Intrinsics.checkNotNullParameter(toTag, "toTag");
                Intrinsics.checkNotNullParameter(updateList, "updateList");
                super.onUpdate(toTag, updateList);
                UpdateInfo updateInfoFindByName = UpdateInfo.findByName(updateList, "brightness");
                if (updateInfoFindByName != null) {
                    this.this$0.updateBitmapBrightNess(updateInfoFindByName.getFloatValue());
                }
            }
        });
        this.performingBrightNessAnim = Folme.useValue("inCreaseBitmapBrightness").setup("setUp").setTo("brightness", Float.valueOf(1.0f)).to("brightness", Float.valueOf(1.15f), animConfig);
    }

    public final void updateBitmapBrightNess(float f) {
        this.currentBitmapBrightNess = f;
        this.changeBrightNessColorMatrix.setScale(f, f, f, 1.0f);
        BitmapDrawable bitmapDrawable = this.currentBitmapDrawable;
        if (bitmapDrawable != null) {
            bitmapDrawable.setColorFilter(new ColorMatrixColorFilter(this.changeBrightNessColorMatrix));
        }
    }
}
