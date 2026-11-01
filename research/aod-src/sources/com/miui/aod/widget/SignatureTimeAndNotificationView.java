package com.miui.aod.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.miui.aod.R;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
public class SignatureTimeAndNotificationView extends RotateHorizontalLinearLayout {
    public ImageView mNotificationView;

    public SignatureTimeAndNotificationView(Context context) {
        super(context);
    }

    public SignatureTimeAndNotificationView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mNotificationView = (ImageView) findViewById(R.id.first);
        findViewById(R.id.icons_container).setPadding(Utils.dp2Px(5), Utils.dp2Px(2), 0, 0);
    }

    @Override // com.miui.aod.widget.RotateHorizontalLinearLayout
    public void setV(boolean z) {
        super.setV(z);
        if (z) {
            this.mNotificationView.setImageResource(R.drawable.ic_notificon_rotate);
        } else {
            this.mNotificationView.setImageResource(R.drawable.ic_notification);
        }
    }
}
