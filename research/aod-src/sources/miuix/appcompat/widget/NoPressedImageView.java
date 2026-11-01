package miuix.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
@SuppressLint({"AppCompatCustomView"})
class NoPressedImageView extends ImageView {
    public NoPressedImageView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public NoPressedImageView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        super.setPressed(false);
    }
}
