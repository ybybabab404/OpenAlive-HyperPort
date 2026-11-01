package com.miui.keyguard.editor.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.miui.keyguard.editor.utils.ClickUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.IFolme;
import miuix.animation.ITouchStyle;
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
/* JADX INFO: compiled from: KgPreviewVisualCheckBox.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class KgPreviewVisualCheckBox extends KgVisualCheckBox {
    public KgPreviewVisualCheckBox(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setClickable(true);
        setOrientation(1);
        setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.keyguard.editor.view.KgPreviewVisualCheckBox.1
            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View host, int i, Bundle bundle) {
                Intrinsics.checkNotNullParameter(host, "host");
                if (i != 16) {
                    return super.performAccessibilityAction(host, i, bundle);
                }
                View.OnClickListener clickCallBack = KgPreviewVisualCheckBox.this.getClickCallBack();
                if (clickCallBack == null) {
                    return true;
                }
                clickCallBack.onClick(KgPreviewVisualCheckBox.this);
                return true;
            }
        });
    }

    @Override // com.miui.keyguard.editor.view.KgVisualCheckBox, miuix.visual.check.VisualCheckBox, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        View.OnClickListener clickCallBack;
        ITouchStyle iTouchStyle;
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getAction() == 0) {
            ClickUtil clickUtil = ClickUtil.INSTANCE;
            clickUtil.getEditButtonLock().compareAndSet(clickUtil.getBUTTON_FREE(), getId());
        }
        ClickUtil clickUtil2 = ClickUtil.INSTANCE;
        if (clickUtil2.getEditButtonLock().get() != getId()) {
            return false;
        }
        if (event.getAction() == 1 || event.getAction() == 3) {
            clickUtil2.getEditButtonLock().compareAndSet(getId(), clickUtil2.getBUTTON_FREE());
        }
        if (!isClickable()) {
            return handleTouchEventWhenDisabled(event);
        }
        IFolme iFolme = getIFolme();
        if (iFolme != null && (iTouchStyle = iFolme.touch()) != null) {
            iTouchStyle.onMotionEvent(event);
        }
        if (event.getAction() == 1 && !clickUtil2.isFastDoubleClick() && (clickCallBack = getClickCallBack()) != null) {
            clickCallBack.onClick(this);
        }
        return true;
    }

    public final void updateStatus(int i) {
        Log.d("KgPreviewVisualCheckBox", "updateStatus: status=" + i);
        if (i == 1) {
            if (isChecked()) {
                setCheckedOnly(false);
            }
            ImageView preview = getPreview();
            if (preview != null) {
                preview.setVisibility(0);
            }
            ImageView extraIcon = getExtraIcon();
            if (extraIcon != null) {
                extraIcon.setVisibility(8);
            }
            ImageView icon = getIcon();
            if (icon != null) {
                icon.setVisibility(8);
                return;
            }
            return;
        }
        if (i == 2) {
            if (!isChecked()) {
                setCheckedOnly(true);
            }
            ImageView preview2 = getPreview();
            if (preview2 != null) {
                preview2.setVisibility(0);
            }
            ImageView extraIcon2 = getExtraIcon();
            if (extraIcon2 != null) {
                extraIcon2.setVisibility(8);
            }
            ImageView icon2 = getIcon();
            if (icon2 != null) {
                icon2.setVisibility(8);
                return;
            }
            return;
        }
        if (i != 3) {
            if (isChecked()) {
                setCheckedOnly(false);
            }
            ImageView preview3 = getPreview();
            if (preview3 != null) {
                preview3.setVisibility(8);
            }
            ImageView extraIcon3 = getExtraIcon();
            if (extraIcon3 != null) {
                extraIcon3.setVisibility(8);
            }
            ImageView icon3 = getIcon();
            if (icon3 != null) {
                icon3.setVisibility(0);
                return;
            }
            return;
        }
        if (!isChecked()) {
            setCheckedOnly(true);
        }
        ImageView preview4 = getPreview();
        if (preview4 != null) {
            preview4.setVisibility(0);
        }
        ImageView extraIcon4 = getExtraIcon();
        if (extraIcon4 != null) {
            extraIcon4.setVisibility(0);
        }
        ImageView icon4 = getIcon();
        if (icon4 != null) {
            icon4.setVisibility(8);
        }
    }
}
