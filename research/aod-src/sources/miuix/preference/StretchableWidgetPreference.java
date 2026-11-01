package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.PreferenceViewHolder;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.stretchablewidget.StretchableWidget;
import miuix.stretchablewidget.WidgetContainer;

/* JADX INFO: loaded from: classes2.dex */
public class StretchableWidgetPreference extends BasePreference {
    public View mButtonLine;
    public WidgetContainer mContainer;
    public String mDetailMsgResId;
    public TextView mDetailMsgView;
    public int mHeight;
    public boolean mIsExpand;
    public ImageView mStateImage;
    public StretchableWidget.StretchableWidgetStateChangedListener mStretchableWidgetStateChangedListener;
    public TextView mTitle;
    public View mTopLine;
    public RelativeLayout mTopView;

    public StretchableWidgetPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.stretchableWidgetPreferenceStyle);
    }

    public StretchableWidgetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHeight = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StretchableWidgetPreference, i, 0);
        this.mDetailMsgResId = typedArrayObtainStyledAttributes.getString(R.styleable.StretchableWidgetPreference_detail_message);
        this.mIsExpand = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StretchableWidgetPreference_expand_state, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final String getStateInfo() {
        return this.mIsExpand ? getContext().getString(R.string.miuix_appcompat_accessibility_expand_state) : getContext().getString(R.string.miuix_appcompat_accessibility_collapse_state);
    }

    @Override // miuix.preference.BasePreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        this.mTopView = (RelativeLayout) view.findViewById(R.id.top_view);
        WidgetContainer widgetContainer = (WidgetContainer) view.findViewById(android.R.id.widget_frame);
        this.mContainer = widgetContainer;
        widgetContainer.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        this.mHeight = this.mContainer.getMeasuredHeight();
        this.mTitle = (TextView) view.findViewById(R.id.title);
        this.mDetailMsgView = (TextView) view.findViewById(R.id.detail_msg_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.state_image);
        this.mStateImage = imageView;
        imageView.setBackgroundResource(R.drawable.miuix_stretchable_widget_state_collapse);
        this.mButtonLine = view.findViewById(R.id.button_line);
        this.mTopLine = view.findViewById(R.id.top_line);
        setDetailMsgText(this.mDetailMsgResId);
        setState(this.mIsExpand);
        this.mTopView.setOnClickListener(new View.OnClickListener() { // from class: miuix.preference.StretchableWidgetPreference.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                StretchableWidgetPreference.this.stateChangeAnim(view2);
            }
        });
        if (isAccessibilityEnabled()) {
            ViewCompat.setAccessibilityDelegate(this.mTopView, new AccessibilityDelegateCompat() { // from class: miuix.preference.StretchableWidgetPreference.2
                @Override // androidx.core.view.AccessibilityDelegateCompat
                public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                    accessibilityNodeInfoCompat.setStateDescription(StretchableWidgetPreference.this.getStateInfo());
                    accessibilityNodeInfoCompat.setCheckable(true);
                    accessibilityNodeInfoCompat.setChecked(StretchableWidgetPreference.this.mIsExpand);
                }
            });
        }
    }

    public final void setContainerAmin(boolean z) {
        IStateStyle iStateStyleAdd = Folme.useValue(this.mContainer).setup("start").add("widgetHeight", this.mHeight);
        ViewProperty viewProperty = ViewProperty.ALPHA;
        iStateStyleAdd.add((FloatProperty) viewProperty, 1.0f).setup("end").add("widgetHeight", 0).add((FloatProperty) viewProperty, 0.0f);
        Folme.useValue(this.mContainer).setTo(z ? "start" : "end");
    }

    public void setDetailMsgText(String str) {
        this.mDetailMsgView.setText(str);
    }

    public void setState(boolean z) {
        if (z) {
            this.mStateImage.setBackgroundResource(R.drawable.miuix_stretchable_widget_state_expand);
            this.mButtonLine.setVisibility(0);
            this.mTopLine.setVisibility(0);
        } else {
            this.mStateImage.setBackgroundResource(R.drawable.miuix_stretchable_widget_state_collapse);
            this.mButtonLine.setVisibility(8);
            this.mTopLine.setVisibility(8);
        }
        setContainerAmin(z);
    }

    public final void stateChangeAnim(View view) {
        boolean z = this.mIsExpand;
        this.mIsExpand = !z;
        if (z) {
            Folme.useValue(this.mContainer).to("end", new AnimConfig().setFromSpeed(0.0f).setSpecial(ViewProperty.ALPHA, (AnimSpecialConfig) new AnimSpecialConfig().setEase(-2, 1.0f, 0.2f)));
            this.mStateImage.setBackgroundResource(miuix.stretchablewidget.R.drawable.miuix_stretchable_widget_state_collapse);
            this.mButtonLine.setVisibility(8);
            this.mTopLine.setVisibility(8);
        } else {
            Folme.useValue(this.mContainer).to("start", new AnimConfig().setFromSpeed(0.0f).setSpecial(ViewProperty.ALPHA, (AnimSpecialConfig) new AnimSpecialConfig().setEase(-2, 1.0f, 0.2f)));
            this.mStateImage.setBackgroundResource(miuix.stretchablewidget.R.drawable.miuix_stretchable_widget_state_expand);
            this.mButtonLine.setVisibility(0);
            this.mTopLine.setVisibility(0);
        }
        if (isAccessibilityEnabled()) {
            view.announceForAccessibility(getStateInfo());
        }
        StretchableWidget.StretchableWidgetStateChangedListener stretchableWidgetStateChangedListener = this.mStretchableWidgetStateChangedListener;
        if (stretchableWidgetStateChangedListener != null) {
            stretchableWidgetStateChangedListener.stateChanged(this.mIsExpand);
        }
    }
}
