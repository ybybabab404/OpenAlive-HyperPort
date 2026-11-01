package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.view.StyleSelectorContainer;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.slidingwidget.widget.SlidingSwitch;
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
/* JADX INFO: compiled from: SwitchView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SwitchView extends StyleSelectorContainer {

    /* JADX INFO: renamed from: switch, reason: not valid java name */
    public final SlidingSwitch f0switch;
    public final TextView switchLabel;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public SwitchView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SwitchView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        setOrientation(0);
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        layoutParams.gravity = 16;
        Resources resources = textView.getResources();
        int i = R.dimen.kg_switch_view_padding_horizontal;
        layoutParams.setMarginEnd(resources.getDimensionPixelSize(i));
        textView.setLayoutParams(layoutParams);
        textView.setTextAppearance(R.style.KgSwitchViewTextStyle);
        textView.setLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextDirection(5);
        this.switchLabel = textView;
        addView(textView);
        SlidingSwitch slidingSwitch = new SlidingSwitch(context);
        slidingSwitch.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        slidingSwitch.setTextColor(ContextCompat.getColor(context, R.color.kg_white_100));
        this.f0switch = slidingSwitch;
        addView(slidingSwitch);
        slidingSwitch.setFocusable(true);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(i);
        int dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.kg_switch_view_padding_vertical);
        setPadding(dimensionPixelOffset, dimensionPixelOffset2, dimensionPixelOffset, dimensionPixelOffset2);
    }

    public /* synthetic */ SwitchView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (isClickable()) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    public final void setChecked(boolean z) {
        this.f0switch.setChecked(z);
    }

    public final void setLabel(@StringRes int i) {
        this.switchLabel.setText(i);
    }

    public final void setOnPerformCheckedChangeListener(@NotNull CompoundButton.OnCheckedChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.f0switch.setOnPerformCheckedChangeListener(listener);
    }
}
