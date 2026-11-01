package com.miui.keyguard.editor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.ClickUtil;
import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.IFolme;
import miuix.animation.ITouchStyle;
import miuix.visual.check.BorderLayout;
import miuix.visual.check.VisualCheckBox;
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
/* JADX INFO: compiled from: KgVisualCheckBox.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class KgVisualCheckBox extends VisualCheckBox {
    public static final Companion Companion = new Companion(null);
    public BorderLayout borderLayout;
    public boolean checked;
    public View.OnClickListener clickCallBack;
    public View.OnClickListener disableClickListener;
    public boolean doubleClickEnable;
    public float downX;
    public float downY;
    public ImageView extraIcon;
    public IFolme iFolme;
    public ImageView icon;
    public View iconBox;
    public boolean isScale;
    public TextView label;
    public long lastClickTime;
    public ImageView maskView;
    public OnCheckListener onCheckListener;
    public OnUncheckListener onUncheckListener;
    public ImageView preview;

    /* JADX INFO: compiled from: KgVisualCheckBox.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: KgVisualCheckBox.kt */
    @Metadata
    public interface OnCheckListener {
        void onChecked(KgVisualCheckBox kgVisualCheckBox);
    }

    /* JADX INFO: compiled from: KgVisualCheckBox.kt */
    @Metadata
    public interface OnUncheckListener {
        void onUncheck(KgVisualCheckBox kgVisualCheckBox);
    }

    public KgVisualCheckBox(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setClickable(true);
        setOrientation(1);
        setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.keyguard.editor.view.KgVisualCheckBox.1
            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View host, int i, Bundle bundle) {
                Intrinsics.checkNotNullParameter(host, "host");
                if (i != 16) {
                    return super.performAccessibilityAction(host, i, bundle);
                }
                if (KgVisualCheckBox.this.isClickable()) {
                    KgVisualCheckBox kgVisualCheckBox = KgVisualCheckBox.this;
                    kgVisualCheckBox.setChecked(!kgVisualCheckBox.isChecked());
                } else {
                    View.OnClickListener onClickListener = KgVisualCheckBox.this.disableClickListener;
                    if (onClickListener != null) {
                        onClickListener.onClick(KgVisualCheckBox.this);
                    }
                }
                return true;
            }
        });
    }

    public static final boolean onAttachedToWindow$lambda$0(View view, MotionEvent motionEvent) {
        return true;
    }

    public final void disable() {
        setClickable(false);
        setEnabled(false);
        ImageView imageView = this.icon;
        if (imageView != null) {
            imageView.setAlpha(0.3f);
        }
        TextView textView = this.label;
        if (textView != null) {
            textView.setAlpha(0.5f);
        }
        if (isChecked()) {
            setCheckedOnly(false);
        }
    }

    public final void enable() {
        ImageView imageView = this.icon;
        if (imageView != null) {
            imageView.setAlpha(1.0f);
        }
        TextView textView = this.label;
        if (textView != null) {
            textView.setAlpha(1.0f);
        }
        setClickable(true);
        setEnabled(true);
    }

    public final void enableDoubleClick(boolean z) {
        this.doubleClickEnable = z;
    }

    @NotNull
    public final BorderLayout getBorderLayout() {
        BorderLayout borderLayout = this.borderLayout;
        if (borderLayout != null) {
            return borderLayout;
        }
        Intrinsics.throwUninitializedPropertyAccessException("borderLayout");
        return null;
    }

    @Nullable
    public final View.OnClickListener getClickCallBack() {
        return this.clickCallBack;
    }

    public final boolean getDoubleClickEnable() {
        return this.doubleClickEnable;
    }

    @Nullable
    public final ImageView getExtraIcon() {
        return this.extraIcon;
    }

    @Nullable
    public final IFolme getIFolme() {
        return this.iFolme;
    }

    @Nullable
    public final ImageView getIcon() {
        return this.icon;
    }

    @Nullable
    public final View getIconBox() {
        return this.iconBox;
    }

    @Nullable
    public final TextView getLabel() {
        return this.label;
    }

    @Nullable
    public final ImageView getMaskView() {
        return this.maskView;
    }

    @Nullable
    public final ImageView getPreview() {
        return this.preview;
    }

    public final boolean handleTouchEventWhenDisabled(MotionEvent event) {
        View.OnClickListener onClickListener;
        Intrinsics.checkNotNullParameter(event, "event");
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            this.downX = event.getX();
            this.downY = event.getY();
        } else if (actionMasked == 1) {
            float scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            if (Math.abs(this.downX - event.getX()) < scaledTouchSlop && Math.abs(this.downY - event.getY()) < scaledTouchSlop && (onClickListener = this.disableClickListener) != null) {
                onClickListener.onClick(this);
            }
        }
        return true;
    }

    @Override // miuix.visual.check.VisualCheckBox
    public boolean isChecked() {
        return this.checked;
    }

    public final boolean isFastDoubleClick() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        long j = this.lastClickTime;
        if (jUptimeMillis - j >= 500) {
            this.lastClickTime = jUptimeMillis;
            return false;
        }
        Log.d("KgVisualCheckBox", "isFastDoubleClick true, lastClickTime=" + j);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getBorderLayout().setOnHoverListener(new View.OnHoverListener() { // from class: com.miui.keyguard.editor.view.KgVisualCheckBox$$ExternalSyntheticLambda0
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                return KgVisualCheckBox.onAttachedToWindow$lambda$0(view, motionEvent);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getBorderLayout().setOnHoverListener(null);
    }

    @Override // miuix.visual.check.VisualCheckBox, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        setBorderLayout((BorderLayout) findViewById(R.id.func_button_border));
    }

    @Override // miuix.visual.check.VisualCheckBox, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        ITouchStyle iTouchStyle;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.isScale) {
            Log.d("KgVisualCheckBox", "Wallpaper is scaling, action=" + event.getActionMasked());
            return true;
        }
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
        IFolme iFolme = this.iFolme;
        if (iFolme != null && (iTouchStyle = iFolme.touch()) != null) {
            iTouchStyle.onMotionEvent(event);
        }
        getBorderLayout().onVisualCheckBoxTouchEvent(this, event);
        if (event.getAction() == 1 && !isFastDoubleClick()) {
            setChecked(!this.checked);
            View.OnClickListener onClickListener = this.clickCallBack;
            if (onClickListener != null) {
                onClickListener.onClick(this);
            }
        }
        return true;
    }

    public final void setBorderLayout(@NotNull BorderLayout borderLayout) {
        Intrinsics.checkNotNullParameter(borderLayout, "<set-?>");
        this.borderLayout = borderLayout;
    }

    @Override // miuix.visual.check.VisualCheckBox
    @CallSuper
    public void setChecked(boolean z) {
        setCheckedOnly(z);
        if (z) {
            OnCheckListener onCheckListener = this.onCheckListener;
            if (onCheckListener != null) {
                onCheckListener.onChecked(this);
                return;
            }
            return;
        }
        OnUncheckListener onUncheckListener = this.onUncheckListener;
        if (onUncheckListener != null) {
            onUncheckListener.onUncheck(this);
        }
    }

    public final void setCheckedOnly(boolean z) {
        updateIfCheckedChanged(z);
        this.checked = z;
        getBorderLayout().onChecked(z);
    }

    public final void setClickCallBack(@Nullable View.OnClickListener onClickListener) {
        this.clickCallBack = onClickListener;
    }

    public final void setClickListener(@NotNull View.OnClickListener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        this.clickCallBack = l;
    }

    public final void setDisableClickListener(@NotNull View.OnClickListener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        this.disableClickListener = l;
    }

    public final void setDoubleClickEnable(boolean z) {
        this.doubleClickEnable = z;
    }

    public final void setExtraIcon(@Nullable ImageView imageView) {
        this.extraIcon = imageView;
    }

    public final void setIFolme(@Nullable IFolme iFolme) {
        this.iFolme = iFolme;
    }

    public final void setIcon(@Nullable ImageView imageView) {
        this.icon = imageView;
    }

    public final void setIconBox(@Nullable View view) {
        this.iconBox = view;
    }

    public final void setLabel(@Nullable TextView textView) {
        this.label = textView;
    }

    public final void setMaskView(@Nullable ImageView imageView) {
        this.maskView = imageView;
    }

    public final void setOnCheckListener(@NotNull OnCheckListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onCheckListener = listener;
    }

    public final void setOnUncheckListener(@NotNull OnUncheckListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onUncheckListener = listener;
    }

    public final void setPreview(@Nullable ImageView imageView) {
        this.preview = imageView;
    }

    public final void setPreviewThumbnail(@NotNull Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        ImageView imageView = this.preview;
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public final void setScale(boolean z) {
        this.isScale = z;
    }

    public final void updateIfCheckedChanged(boolean z) {
        if (this.checked != z) {
            updateWhenCheckedChanged(z);
        }
    }

    public final void updateVisualCheckBoxChecked(boolean z) {
        try {
            Field declaredField = VisualCheckBox.class.getDeclaredField("mChecked");
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type miuix.visual.check.VisualCheckBox");
            declaredField.setAccessible(true);
            declaredField.setBoolean(this, z);
        } catch (Exception e) {
            Log.e("KgVisualCheckBox", "updateVisualCheckBoxChecked error", e);
        }
    }

    public void updateWhenCheckedChanged(boolean z) {
    }
}
