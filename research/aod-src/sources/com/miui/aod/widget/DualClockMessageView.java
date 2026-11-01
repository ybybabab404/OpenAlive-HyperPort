package com.miui.aod.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import miuix.device.DeviceUtils;

/* JADX INFO: loaded from: classes.dex */
public class DualClockMessageView extends MessageArrowView {
    public static /* synthetic */ void $r8$lambda$4o9OlMlo1NqT4cE9Yptp5cH7Uww(DualClockMessageView dualClockMessageView, View view) {
        dualClockMessageView.getClass();
        Intent intent = new Intent("android.settings.DATE_SETTINGS");
        intent.addFlags(268435456);
        if (DeviceUtils.isFoldDevice() || DeviceUtils.isTablet(dualClockMessageView.getContext())) {
            intent.removeFlags(268435456);
        }
        dualClockMessageView.getContext().startActivity(intent);
    }

    public DualClockMessageView(Context context) {
        this(context, null);
    }

    public DualClockMessageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DualClockMessageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.imageView.setContentDescription(getContext().getString(R.string.dual_clock_tip));
        Utils.setLineSpacing("DualClockMessageView", this, (TextView) findViewById(android.R.id.text1), Boolean.TRUE);
        setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.widget.DualClockMessageView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DualClockMessageView.$r8$lambda$4o9OlMlo1NqT4cE9Yptp5cH7Uww(this.f$0, view);
            }
        });
    }
}
