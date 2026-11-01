package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class TextButtonPreference extends BasePreference {
    public View.OnClickListener mClickListener;
    public int mTextColor;

    public TextButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textButtonPreferenceStyle);
    }

    public TextButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Miuix_Preference_TextButtonPreference);
    }

    public TextButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TextButtonPreference, i, i2);
        this.mTextColor = typedArrayObtainStyledAttributes.getInt(R.styleable.TextButtonPreference_android_textColor, AttributeResolver.resolveBoolean(getContext(), android.R.attr.isLightTheme, true) ? context.getResources().getColor(R.color.miuix_color_blue_light_primary_default) : context.getResources().getColor(R.color.miuix_color_blue_dark_primary_default));
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // miuix.preference.BasePreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        TextView textView = (TextView) view.findViewById(android.R.id.title);
        if (textView != null) {
            textView.setTextColor(this.mTextColor);
        }
        View.OnClickListener onClickListener = this.mClickListener;
        if (onClickListener != null) {
            view.setOnClickListener(onClickListener);
        }
    }
}
