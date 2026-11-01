package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceViewHolder;
import miuix.core.util.RomUtils;

/* JADX INFO: loaded from: classes2.dex */
public class CommentPreference extends BasePreference {
    public int mLargeVerticalMargin;
    public int mSmallVerticalMargin;
    public CharSequence mText;
    public int mVerticalMarginChoice;

    public CommentPreference(@NonNull Context context) {
        this(context, null);
    }

    public CommentPreference(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.commentPreferenceStyle);
    }

    public CommentPreference(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Miuix_Preference_CommentPreference);
    }

    public CommentPreference(@NonNull Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mText = "";
        this.mVerticalMarginChoice = -1;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CommentPreference, i, i2);
        this.mVerticalMarginChoice = typedArrayObtainStyledAttributes.getInt(R.styleable.CommentPreference_verticalMarginChoice, -1);
        int i3 = R.styleable.CommentPreference_android_text;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(i3, 0);
        if (resourceId != 0) {
            this.mText = context.getString(resourceId);
        } else {
            this.mText = typedArrayObtainStyledAttributes.getText(i3);
        }
        setClickable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_clickable, false));
        setCardStyleEnable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_cardEnable, false));
        setTouchAnimationEnable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_touchAnimationEnable, false));
        this.mLargeVerticalMargin = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_comment_margin_vertical_traditional);
        this.mSmallVerticalMargin = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_comment_margin_vertical_card);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final int[] getVerticalMargin(boolean z) {
        int i;
        int i2;
        int i3 = this.mVerticalMarginChoice;
        if (i3 != -1) {
            if (i3 == 1) {
                int i4 = this.mSmallVerticalMargin;
                i2 = this.mLargeVerticalMargin;
                i = i4;
            } else {
                i = this.mLargeVerticalMargin;
            }
            return new int[]{i, i2};
        }
        i = z ? this.mSmallVerticalMargin : this.mLargeVerticalMargin;
        i2 = i;
        return new int[]{i, i2};
    }

    @Override // miuix.preference.BasePreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        TextView textView = (TextView) view.findViewById(R.id.content);
        LinearLayout linearLayout = view instanceof LinearLayout ? (LinearLayout) view : null;
        if (textView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{R.attr.preferenceCardStyleEnable});
            try {
                int i = typedArrayObtainStyledAttributes.getInt(0, 1);
                typedArrayObtainStyledAttributes.close();
                int[] verticalMargin = getVerticalMargin(i == 2 || (RomUtils.getHyperOsVersion() > 1 && i == 1));
                if (linearLayout != null && verticalMargin[0] != verticalMargin[1]) {
                    linearLayout.setGravity(48);
                } else if (linearLayout != null) {
                    linearLayout.setGravity(16);
                }
                layoutParams.setMargins(0, verticalMargin[0], 0, verticalMargin[1]);
                textView.setText(this.mText);
            } catch (Throwable th) {
                if (typedArrayObtainStyledAttributes != null) {
                    try {
                        typedArrayObtainStyledAttributes.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }
}
