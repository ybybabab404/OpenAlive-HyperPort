package com.miui.aod.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.core.text.TextUtilsCompat;
import com.miui.aod.R;
import java.util.Locale;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.miuixbasewidget.widget.MessageView;

/* JADX INFO: loaded from: classes.dex */
public class MessageArrowView extends MessageView {
    public ImageView imageView;

    public MessageArrowView(Context context) {
        this(context, null);
    }

    public MessageArrowView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MessageArrowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setClosable(false);
        addArrowIcon();
    }

    public final void addArrowIcon() {
        this.imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        this.imageView.setBackgroundResource(R.drawable.miuix_appcompat_arrow_right_light);
        if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
            layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_message_view_text_margin_right);
            this.imageView.setRotation(180.0f);
        } else {
            layoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_message_view_text_margin_right);
        }
        addView(this.imageView, layoutParams);
        Folme.useAt(this.imageView).touch().handleTouchOf(this.imageView, new AnimConfig[0]);
    }

    public void setContentDes(String str) {
        ImageView imageView = this.imageView;
        if (imageView != null) {
            imageView.setContentDescription(str);
        }
    }

    public void setMarginArrowLeft(int i) {
        ((ViewGroup.MarginLayoutParams) this.imageView.getLayoutParams()).leftMargin = i;
    }

    @Override // android.view.View
    public void setOnClickListener(@Nullable View.OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.imageView.setOnClickListener(onClickListener);
    }
}
