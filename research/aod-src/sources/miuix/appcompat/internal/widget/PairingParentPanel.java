package miuix.appcompat.internal.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.xiaomi.onetrack.util.a;
import miuix.appcompat.R;
import miuix.core.util.SystemProperties;
import miuix.core.widget.NestedScrollView;
import miuix.internal.util.ViewUtils;

/* JADX INFO: loaded from: classes2.dex */
public class PairingParentPanel extends FrameLayout {
    public AppCompatImageView mClosableIcon;
    public int mClosableIconPositionHorizontal;
    public int mClosableIconPositionTop;
    public Context mContext;
    public boolean mCustomViewVerticalCenterEnabled;
    public boolean mIsButtonPanelVisible;
    public boolean mIsDebugEnabled;
    public boolean mIsFeedbackVisible;
    public NestedScrollView mPairingScrollView;
    public int mScrollExpectedHeight;

    public PairingParentPanel(@NonNull Context context) {
        this(context, null);
    }

    public PairingParentPanel(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PairingParentPanel(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsDebugEnabled = false;
        this.mCustomViewVerticalCenterEnabled = true;
        this.mIsButtonPanelVisible = true;
        this.mIsFeedbackVisible = false;
        this.mScrollExpectedHeight = 0;
        this.mContext = context;
        init(context);
    }

    private int getScrollableAvailableHeight() {
        int measuredHeight;
        ViewGroup viewGroup = (ViewGroup) getParent();
        ViewGroup viewGroup2 = viewGroup != null ? (ViewGroup) viewGroup.getParent() : null;
        ViewGroup viewGroup3 = viewGroup2 instanceof NestedScrollViewExpander ? (ViewGroup) viewGroup2.getParent() : null;
        ViewGroup viewGroup4 = viewGroup3 instanceof NestedScrollViewExpandContainer ? (ViewGroup) viewGroup3.getParent() : null;
        if (viewGroup4 == null) {
            return -1;
        }
        ViewGroup viewGroup5 = (ViewGroup) viewGroup4.findViewById(R.id.pairingCheckboxContainer);
        int measuredHeight2 = 0;
        int measuredHeight3 = (viewGroup5 == null || viewGroup5.getVisibility() != 0) ? 0 : viewGroup5.getMeasuredHeight();
        int paddingBottom = viewGroup4.getPaddingBottom();
        DialogButtonPanel dialogButtonPanel = (DialogButtonPanel) viewGroup4.findViewById(R.id.buttonPanel);
        if (dialogButtonPanel == null || dialogButtonPanel.getVisibility() != 0) {
            this.mIsButtonPanelVisible = false;
            measuredHeight = 0;
        } else {
            measuredHeight = dialogButtonPanel.getMeasuredHeight();
            this.mIsButtonPanelVisible = true;
        }
        TextView textView = (TextView) viewGroup4.findViewById(R.id.pairingDialogFeedback);
        if (textView == null || textView.getVisibility() != 0) {
            this.mIsFeedbackVisible = false;
        } else {
            measuredHeight2 = textView.getMeasuredHeight();
            this.mIsFeedbackVisible = true;
        }
        if (this.mIsDebugEnabled) {
            Log.e("PairingParentPanel", "getScrollableAvailableHeight: dialogParentPanel.height = " + viewGroup4.getMeasuredHeight() + ", dialogParentPanelPaddingBottom = " + paddingBottom + ", buttonPanelHeight = " + measuredHeight + ", feedbackViewHeight = " + measuredHeight2);
        }
        return (((viewGroup4.getMeasuredHeight() - paddingBottom) - measuredHeight3) - measuredHeight) - measuredHeight2;
    }

    public final void applyCustomViewLayoutVerticalCenterIfNeeded() {
        if (this.mCustomViewVerticalCenterEnabled) {
            int scrollableAvailableHeight = getScrollableAvailableHeight();
            NestedScrollView nestedScrollView = this.mPairingScrollView;
            if (nestedScrollView != null) {
                this.mScrollExpectedHeight = nestedScrollView.getMeasuredHeight();
            }
            if (this.mIsDebugEnabled) {
                Log.d("PairingParentPanel", "onMeasure: ==> height = " + getMeasuredHeight() + ", scrollableAvailableHeight = " + scrollableAvailableHeight);
            }
            if (scrollableAvailableHeight <= 0 || getMeasuredHeight() >= scrollableAvailableHeight) {
                return;
            }
            int paddingBottom = scrollableAvailableHeight - ((this.mIsButtonPanelVisible || this.mIsFeedbackVisible) ? getPaddingBottom() : 0);
            setMeasuredDimension(getMeasuredWidth(), scrollableAvailableHeight);
            this.mScrollExpectedHeight = paddingBottom;
            if (this.mIsDebugEnabled) {
                Log.d("PairingParentPanel", "onMeasure: reMeasure pairingParentPanel height = " + scrollableAvailableHeight + ", scrollViewExpectedHeight = " + paddingBottom + ", paddingBottom = " + getPaddingBottom() + ", paddingTop = " + getPaddingTop());
            }
        }
    }

    public int getScrollExpectedHeight() {
        return this.mScrollExpectedHeight;
    }

    public final void init(Context context) {
        this.mClosableIconPositionTop = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_pairing_dialog_icon_position_margin_top);
        this.mClosableIconPositionHorizontal = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_pairing_dialog_icon_position_margin_horizontal);
        isDebugEnabled();
    }

    public final boolean isDebugEnabled() {
        String str = "";
        try {
            String str2 = SystemProperties.get("log.tag.alertdialog.ime.debug.enable");
            if (str2 != null) {
                str = str2;
            }
        } catch (Exception e) {
            Log.i("PairingParentPanel", "can not access property log.tag.alertdialog.ime.enable, undebugable", e);
        }
        Log.d("PairingParentPanel", "Alert dialog ime debugEnable = " + str);
        boolean zEquals = TextUtils.equals(a.i, str);
        this.mIsDebugEnabled = zEquals;
        return zEquals;
    }

    public final void layoutClosableIcon() {
        if (this.mClosableIcon == null) {
            return;
        }
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i = this.mClosableIconPositionTop;
        int measuredWidth = zIsLayoutRtl ? this.mClosableIconPositionHorizontal : (getMeasuredWidth() - this.mClosableIcon.getMeasuredWidth()) - this.mClosableIconPositionHorizontal;
        AppCompatImageView appCompatImageView = this.mClosableIcon;
        appCompatImageView.layout(measuredWidth, i, appCompatImageView.getMeasuredWidth() + measuredWidth, this.mClosableIcon.getMeasuredHeight() + i);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClosableIcon = (AppCompatImageView) findViewById(R.id.pairingClosable);
        this.mPairingScrollView = (NestedScrollView) findViewById(R.id.pairingScrollView);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        layoutClosableIcon();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        applyCustomViewLayoutVerticalCenterIfNeeded();
    }

    public void setCustomViewVerticalCenterEnabled(boolean z) {
        this.mCustomViewVerticalCenterEnabled = z;
    }
}
