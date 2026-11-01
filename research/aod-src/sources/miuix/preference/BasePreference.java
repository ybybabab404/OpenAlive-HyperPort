package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import miuix.core.util.RomUtils;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class BasePreference extends Preference implements PreferenceBehavior {
    public boolean mAccessibilityEnable;
    public boolean mCardEnable;
    public boolean mClickable;
    public int mGroupItemType;
    public int mTitleBreakStrategy;
    public int mTitleHyphenationFrequency;
    public int mTitleLineBreakStyle;
    public int mTitleLineBreakWordStyle;
    public boolean mTouchAnimationEnable;

    public BasePreference(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTitleHyphenationFrequency = -1;
        this.mTitleBreakStrategy = -1;
        this.mTitleLineBreakStyle = -1;
        this.mTitleLineBreakWordStyle = -1;
        init(attributeSet);
    }

    public BasePreference(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTitleHyphenationFrequency = -1;
        this.mTitleBreakStrategy = -1;
        this.mTitleLineBreakStyle = -1;
        this.mTitleLineBreakWordStyle = -1;
        init(attributeSet);
    }

    public BasePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTitleHyphenationFrequency = -1;
        this.mTitleBreakStrategy = -1;
        this.mTitleLineBreakStyle = -1;
        this.mTitleLineBreakWordStyle = -1;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        int iResolveInt = AttributeResolver.resolveInt(getContext(), R.attr.preferenceCardStyleEnable, 1);
        boolean z = iResolveInt == 2 || (RomUtils.getHyperOsVersion() > 1 && iResolveInt == 1);
        if (attributeSet == null) {
            this.mClickable = true;
            this.mTouchAnimationEnable = true;
            this.mCardEnable = z;
            this.mAccessibilityEnable = true;
            this.mGroupItemType = 0;
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.BasePreference);
        this.mClickable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_clickable, true);
        this.mTouchAnimationEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_touchAnimationEnable, true);
        this.mCardEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_cardEnable, z);
        this.mAccessibilityEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_accessibilityEnable, true);
        this.mGroupItemType = typedArrayObtainStyledAttributes.getInteger(R.styleable.BasePreference_groupItemType, 0);
        this.mTitleHyphenationFrequency = typedArrayObtainStyledAttributes.getInt(R.styleable.BasePreference_android_hyphenationFrequency, -1);
        this.mTitleBreakStrategy = typedArrayObtainStyledAttributes.getInt(R.styleable.BasePreference_android_breakStrategy, -1);
        this.mTitleLineBreakStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.BasePreference_android_lineBreakStyle, -1);
        this.mTitleLineBreakWordStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.BasePreference_android_lineBreakWordStyle, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final void applyTitleTextStyle(PreferenceViewHolder preferenceViewHolder) {
        if (this.mTitleHyphenationFrequency == -1 && this.mTitleBreakStrategy == -1 && this.mTitleLineBreakStyle == -1 && this.mTitleLineBreakWordStyle == -1) {
            return;
        }
        View viewFindViewById = preferenceViewHolder.findViewById(android.R.id.title);
        if (viewFindViewById instanceof TextView) {
            TextView textView = (TextView) viewFindViewById;
            int i = this.mTitleHyphenationFrequency;
            if (i != -1) {
                textView.setHyphenationFrequency(i);
            }
            int i2 = this.mTitleBreakStrategy;
            if (i2 != -1) {
                textView.setBreakStrategy(i2);
            }
            int i3 = this.mTitleLineBreakStyle;
            if (i3 != -1) {
                textView.setLineBreakStyle(i3);
            }
            int i4 = this.mTitleLineBreakWordStyle;
            if (i4 != -1) {
                textView.setLineBreakWordStyle(i4);
            }
        }
    }

    @Override // miuix.preference.PreferenceStyle
    public boolean enabledCardStyle() {
        return this.mCardEnable;
    }

    @Override // miuix.preference.PreferencedynamicGroupController
    public int getGroupItemType() {
        return this.mGroupItemType;
    }

    @Override // miuix.preference.PreferenceAccessibility
    public boolean isAccessibilityEnabled() {
        return this.mAccessibilityEnable;
    }

    @Override // miuix.preference.FolmeAnimationController
    public boolean isTouchAnimationEnable() {
        return this.mTouchAnimationEnable;
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setClickable(this.mClickable);
        applyTitleTextStyle(preferenceViewHolder);
    }

    public void setCardStyleEnable(boolean z) {
        this.mCardEnable = z;
    }

    public void setClickable(boolean z) {
        this.mClickable = z;
    }

    public void setTouchAnimationEnable(boolean z) {
        this.mTouchAnimationEnable = z;
    }
}
