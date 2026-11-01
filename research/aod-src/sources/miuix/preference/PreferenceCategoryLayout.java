package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import miuix.core.util.RomUtils;

/* JADX INFO: loaded from: classes2.dex */
public class PreferenceCategoryLayout extends FrameLayout {
    public PreferenceCategoryLayout(@NonNull Context context) {
        super(context);
    }

    public PreferenceCategoryLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PreferenceCategoryLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.preferenceCardStyleEnable, R.attr.preferenceTraditionalCategoryBackground});
        int i = typedArrayObtainStyledAttributes.getInt(0, 1);
        if (i != 2 && (RomUtils.getHyperOsVersion() <= 1 || i != 1)) {
            setBackground(typedArrayObtainStyledAttributes.getDrawable(1));
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
