package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationAttributes;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import miuix.animation.Folme;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.miuixbasewidget.R;
import miuix.theme.Typography;
import miuix.util.HapticFeedbackCompat;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes2.dex */
public class AlphabetIndexer extends LinearLayout {
    public final int INVALID_INDEX;
    public AccessibilityManager mAccessibilityManager;
    public Adapter mAdapter;
    public boolean mCancelOverlayTextColorAnim;
    public float mClickDownY;
    public boolean mDrawOverlay;
    public boolean mEnableAutoDismiss;
    public ImageView mFirstOmitItem;
    public int mFirstVisibleItemPos;
    public boolean mForceUpdate;
    public boolean mForceUpdateVisibleIndexes;
    public int mGroupCount;
    public int mGroupItemCount;
    public boolean mHandleWindowInsetsEnabled;
    public Handler mHandler;
    public HapticFeedbackCompat mHapticFeedbackCompat;
    public int mIndexMinWidth;
    public int mIndexWidth;
    public SectionIndexer mIndexer;
    public int mItemHeight;
    public int mItemMargin;
    public int mLastAlphabetIndex;
    public View mLastSelectedItem;
    public int mLeftCount;
    public int mListScrollState;
    public int mMaxItemMargin;
    public int mMinItemMargin;
    public int mOmitItemHeight;
    public int mOriginalMarginEnd;
    public TextView mOverlay;
    public Drawable mOverlayBackground;
    public int mOverlayHeight;
    public AnimConfig mOverlayHideAnimConfig;
    public AnimConfig mOverlayShowAnimConfig;
    public int mOverlayTextAppearanceRes;
    public int mOverlayTextColor;
    public TextPaint mOverlayTextPaint;
    public int mOverlayTextSize;
    public int mOverlayWidth;
    public final View.OnLayoutChangeListener mParentLayoutChangeListener;
    public View mParentView;
    public int mScreenHeightDp;
    public HashMap mSectionMap;
    public int mSelectedAlphaIndex;
    public TextHighlighter mTextHighlighter;
    public float mTransFormY;
    public VibrationAttributes mUsageAlarmVibrationAttributes;
    public boolean mUseOmit;
    public int mVerticalPosition;

    public interface Adapter {
        int getFirstVisibleItemPosition();

        int getListHeaderCount();

        void scrollToPosition(int i);

        void stopScroll();
    }

    public class ScrollTargetInfo {
        public int targetItemPos;
        public int targetSectionIndex;

        public ScrollTargetInfo() {
        }
    }

    public static class TextHighlighter {
        public int mActivatedColor;
        public int mHighlightColor;
        public int mIndexerTextSize;
        public String[] mIndexes;
        public String[] mMinVisibleIndexes;
        public int mNormalColor;
        public boolean mSectionsAsIndexesEnabled = false;
        public int mStarHighLightColor;
        public int mStarNormalColor;
        public String[] mVisibleIndexes;

        public TextHighlighter(Context context, TypedArray typedArray) {
            Resources resources = context.getResources();
            CharSequence[] textArray = typedArray.getTextArray(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppcompatIndexerTable);
            if (textArray != null) {
                this.mIndexes = new String[textArray.length];
                int length = textArray.length;
                int i = 0;
                int i2 = 0;
                while (i < length) {
                    this.mIndexes[i2] = textArray[i].toString();
                    i++;
                    i2++;
                }
            } else {
                this.mIndexes = resources.getStringArray(R.array.alphabet_table);
            }
            this.mMinVisibleIndexes = new String[]{"!", "#"};
            ColorStateList colorStateList = AppCompatResources.getColorStateList(context, typedArray.getResourceId(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppcompatIndexerTextColorList, R.color.miuix_appcompat_alphabet_indexer_text_light));
            this.mHighlightColor = colorStateList.getColorForState(new int[]{android.R.attr.state_selected}, resources.getColor(R.color.miuix_appcompat_alphabet_indexer_highlight_text_color));
            this.mActivatedColor = colorStateList.getColorForState(new int[]{android.R.attr.state_activated}, resources.getColor(R.color.miuix_appcompat_alphabet_indexer_activated_text_color));
            this.mNormalColor = colorStateList.getColorForState(new int[0], resources.getColor(R.color.miuix_appcompat_alphabet_indexer_text_color));
            boolean zIsNightMode = ViewUtils.isNightMode(context);
            this.mStarNormalColor = resources.getColor(zIsNightMode ? R.color.miuix_appcompat_alphabet_indexer_star_normal_color_dark : R.color.miuix_appcompat_alphabet_indexer_star_normal_color_light);
            this.mStarHighLightColor = resources.getColor(zIsNightMode ? R.color.miuix_appcompat_alphabet_indexer_star_high_light_dark_color : R.color.miuix_appcompat_alphabet_indexer_star_high_light_light_color);
            this.mIndexerTextSize = typedArray.getDimensionPixelSize(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppcompatIndexerTextSize, resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_indexer_text_size));
        }

        public String[] getVisibleIndexes(SectionIndexer sectionIndexer, boolean z) {
            String[] strArr;
            if (sectionIndexer == null || !this.mSectionsAsIndexesEnabled) {
                return this.mIndexes;
            }
            Object[] sections = sectionIndexer.getSections();
            if (sections != null && sections.length == 0 && (strArr = this.mMinVisibleIndexes) != null && strArr.length > 0) {
                String[] strArr2 = new String[strArr.length];
                this.mVisibleIndexes = strArr2;
                System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                return this.mVisibleIndexes;
            }
            if (sections == null || sections.length == 0) {
                return this.mIndexes;
            }
            if (this.mVisibleIndexes == null || z) {
                this.mVisibleIndexes = new String[sections.length];
                for (int i = 0; i < sections.length; i++) {
                    this.mVisibleIndexes[i] = (String) sections[i];
                }
            }
            return this.mVisibleIndexes;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$8q5TFmvjl6qGc7xln3Mxm-avktg, reason: not valid java name */
    public static /* synthetic */ WindowInsetsCompat m1339$r8$lambda$8q5TFmvjl6qGc7xln3Mxmavktg(AlphabetIndexer alphabetIndexer, View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
        WindowInsetsCompat rootWindowInsets;
        if (alphabetIndexer.mHandleWindowInsetsEnabled && (rootWindowInsets = ViewCompat.getRootWindowInsets(view)) != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof FrameLayout.LayoutParams) {
                Insets insets = rootWindowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                Insets insets2 = rootWindowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                int i = insets.left;
                int i2 = insets.right;
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                if (alphabetIndexer.mOriginalMarginEnd < 0) {
                    alphabetIndexer.mOriginalMarginEnd = layoutParams2.getMarginEnd();
                }
                if (!MiuixUIUtils.isLayoutHideNavigation(alphabetIndexer)) {
                    if (insets2.left > 0) {
                        i = 0;
                    }
                    if (insets2.right > 0) {
                        i2 = 0;
                    }
                }
                int i3 = ViewUtils.isLayoutRtl(view) ? alphabetIndexer.mOriginalMarginEnd + i : alphabetIndexer.mOriginalMarginEnd + i2;
                if (layoutParams2.getMarginEnd() != i3) {
                    layoutParams2.setMarginEnd(i3);
                    view.setLayoutParams(layoutParams2);
                }
            }
        }
        return windowInsetsCompat;
    }

    public AlphabetIndexer(Context context) {
        this(context, null);
    }

    public AlphabetIndexer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.miuixAppcompatAlphabetIndexerStyle);
    }

    public AlphabetIndexer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIndexWidth = 0;
        this.INVALID_INDEX = -1;
        this.mGroupItemCount = 1;
        this.mLeftCount = 0;
        this.mGroupCount = 0;
        this.mSelectedAlphaIndex = -1;
        this.mSectionMap = new HashMap();
        this.mListScrollState = 0;
        this.mUseOmit = false;
        this.mForceUpdate = false;
        this.mForceUpdateVisibleIndexes = false;
        this.mHandleWindowInsetsEnabled = true;
        this.mParentView = null;
        this.mTransFormY = -1.0f;
        this.mFirstVisibleItemPos = 0;
        this.mOriginalMarginEnd = -1;
        this.mParentLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: miuix.miuixbasewidget.widget.AlphabetIndexer.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                int i10 = i5 - i3;
                if (i9 - i7 != i10 || AlphabetIndexer.this.mForceUpdate) {
                    AlphabetIndexer.this.mForceUpdate = false;
                    AlphabetIndexer.this.updateItemsAfterParentVisibleHeightChanged(i10);
                }
                if (i2 == i6 && i3 == i7 && i4 == i8 && i5 == i9) {
                    return;
                }
                ViewCompat.requestApplyInsets(AlphabetIndexer.this);
            }
        };
        this.mEnableAutoDismiss = true;
        this.mScreenHeightDp = -1;
        this.mHandler = new Handler() { // from class: miuix.miuixbasewidget.widget.AlphabetIndexer.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 1) {
                    return;
                }
                AlphabetIndexer.this.hideOverlay();
            }
        };
        parseAttrs(attributeSet, i);
        init();
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() { // from class: miuix.miuixbasewidget.widget.AlphabetIndexer$$ExternalSyntheticLambda0
            @Override // miuix.internal.util.ViewUtils.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, ViewUtils.RelativePadding relativePadding) {
                return AlphabetIndexer.m1339$r8$lambda$8q5TFmvjl6qGc7xln3Mxmavktg(this.f$0, view, windowInsetsCompat, relativePadding);
            }
        });
    }

    private HapticFeedbackCompat getHapticFeedbackCompat() {
        if (this.mHapticFeedbackCompat == null) {
            this.mHapticFeedbackCompat = new HapticFeedbackCompat(getContext());
        }
        return this.mHapticFeedbackCompat;
    }

    private int getListOffset() {
        Adapter adapter = this.mAdapter;
        if (adapter == null) {
            return 0;
        }
        return adapter.getListHeaderCount();
    }

    private int getMarginBottom() {
        return ((ViewGroup.MarginLayoutParams) getLayoutParams()).bottomMargin;
    }

    private int getMarginTop() {
        return ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin;
    }

    private int getMarinEnd() {
        return ((ViewGroup.MarginLayoutParams) getLayoutParams()).getMarginEnd();
    }

    private int getRealWidthOfIndexer() {
        return Math.max(this.mIndexMinWidth, this.mIndexWidth);
    }

    private SectionIndexer getSectionIndexer() {
        return this.mIndexer;
    }

    @RequiresApi
    private VibrationAttributes getUsageAlarmVibrationAttributes() {
        if (this.mUsageAlarmVibrationAttributes == null) {
            this.mUsageAlarmVibrationAttributes = new VibrationAttributes.Builder().setUsage(17).build();
        }
        return this.mUsageAlarmVibrationAttributes;
    }

    private void setChecked(int i) {
        this.mSelectedAlphaIndex = i;
        View view = this.mLastSelectedItem;
        if (view != null) {
            updateIndexItemColor(view, false);
        }
        View childAt = getChildAt(getChildIndex(i));
        this.mLastSelectedItem = childAt;
        updateIndexItemColor(childAt, true);
        if (this.mLastSelectedItem != null) {
            invalidate();
        }
    }

    public final void announceAccessibilityEvent(String str) {
        if (TextUtils.equals(str, "!")) {
            str = getContext().getString(R.string.miuix_indexer_collect);
        }
        announceForAccessibility(str);
    }

    public final void announceSelectText() {
        SectionIndexer sectionIndexer = getSectionIndexer();
        if (sectionIndexer == null) {
            return;
        }
        String[] visibleIndexes = this.mTextHighlighter.getVisibleIndexes(sectionIndexer, false);
        int i = this.mSelectedAlphaIndex;
        if (i < 0 || i >= visibleIndexes.length) {
            return;
        }
        announceAccessibilityEvent(visibleIndexes[i]);
    }

    public final int calculateIndex(float f) {
        int height = this.mItemHeight + (this.mItemMargin * 2);
        View childAt = getChildAt(0);
        if (childAt != null) {
            height = (((ViewGroup.MarginLayoutParams) childAt.getLayoutParams()).topMargin * 2) + childAt.getHeight();
        }
        int length = this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false).length;
        int childCount = getChildCount();
        float f2 = height;
        if (f <= f2 || (length == childCount && !this.mUseOmit)) {
            return (int) (f / f2);
        }
        int i = height * 2;
        if (f > (getHeight() - getPaddingTop()) - i) {
            return (length - 2) + (((int) (f - ((getHeight() - getPaddingTop()) - i))) / height);
        }
        int height2 = this.mOmitItemHeight + (this.mItemMargin * 2);
        ImageView imageView = this.mFirstOmitItem;
        if (imageView != null) {
            height2 = imageView.getHeight() + (this.mItemMargin * 2);
        }
        int i2 = height2 + height;
        int i3 = (int) (f - f2);
        int i4 = i3 / i2;
        int i5 = i3 % i2 > height ? 1 : 0;
        int i6 = this.mLeftCount;
        if (i4 < i6) {
            return ((this.mGroupItemCount + 1) * i4) + 1 + i5;
        }
        int i7 = this.mGroupItemCount;
        return ((i7 + 1) * i6) + 1 + (i7 * (i4 - i6)) + i5;
    }

    public final int calculateOverlayPosition(int i) {
        int childIndex = getChildIndex(i);
        View childAt = getChildAt(childIndex);
        if (childAt == null) {
            return 0;
        }
        int top = (childAt.getTop() + childAt.getBottom()) / 2;
        if (top <= 0) {
            top = (int) (((((double) (childIndex + 1)) + 0.5d) * ((double) this.mItemHeight)) + ((double) getPaddingTop()));
        }
        return top + getMarginTop();
    }

    public final void checkSelectedItemChanged() {
        int realSectionPosition;
        SectionIndexer sectionIndexer = getSectionIndexer();
        Adapter adapter = this.mAdapter;
        if (adapter == null || sectionIndexer == null || this.mSelectedAlphaIndex == (realSectionPosition = getRealSectionPosition(sectionIndexer.getSectionForPosition(adapter.getFirstVisibleItemPosition()), sectionIndexer))) {
            return;
        }
        setChecked(realSectionPosition);
    }

    public final void clearLastChecked(int i) {
        if (i < 0) {
            return;
        }
        View childAt = getChildAt(getChildIndex(i));
        if (!(childAt instanceof TextView)) {
            if (childAt instanceof ImageView) {
                ((ImageView) childAt).setImageResource(R.drawable.miuix_ic_omit);
            }
        } else {
            TextView textView = (TextView) childAt;
            boolean zIsStarText = isStarText(textView.getText());
            TextHighlighter textHighlighter = this.mTextHighlighter;
            textView.setTextColor(zIsStarText ? textHighlighter.mStarNormalColor : textHighlighter.mNormalColor);
        }
    }

    public final void constructItem(int i) {
        this.mItemMargin = i;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 1;
        layoutParams.bottomMargin = i;
        layoutParams.topMargin = i;
        layoutParams.weight = 1.0f;
        for (CharSequence charSequence : this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false)) {
            TextView textView = new TextView(getContext());
            Typography.applyMiSansMedium(textView);
            textView.setGravity(17);
            textView.setHeight(this.mItemHeight);
            textView.setIncludeFontPadding(false);
            textView.setTextColor(isStarText(charSequence) ? this.mTextHighlighter.mStarNormalColor : this.mTextHighlighter.mNormalColor);
            textView.setTextSize(0, this.mTextHighlighter.mIndexerTextSize);
            if (TextUtils.equals(charSequence, "!")) {
                charSequence = "♥";
            }
            textView.setText(charSequence);
            textView.setHorizontallyScrolling(false);
            textView.setVerticalScrollBarEnabled(false);
            textView.setImportantForAccessibility(2);
            attachViewToParent(textView, -1, layoutParams);
        }
        this.mUseOmit = false;
    }

    public final void constructItemWithOmit(int i) {
        int i2;
        int i3;
        int marginTop = getMarginTop() <= 0 ? getMarginTop() + (this.mOverlayHeight / 2) + 1 : getMarginTop();
        int marginBottom = getMarginBottom() <= 0 ? getMarginBottom() + (this.mOverlayHeight / 2) + 1 : getMarginBottom();
        int paddingTop = (i - getPaddingTop()) - getPaddingBottom();
        if (paddingTop + marginTop + marginBottom >= i) {
            paddingTop -= marginTop + marginBottom;
        }
        String[] visibleIndexes = this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false);
        int length = visibleIndexes.length;
        int i4 = this.mItemHeight;
        int i5 = this.mMinItemMargin;
        int i6 = i4 + (i5 * 2);
        int i7 = this.mOmitItemHeight + i6 + (i5 * 2);
        int i8 = paddingTop - (i6 * 3);
        int i9 = i8 / i7;
        this.mGroupCount = i9;
        if (i9 < 1) {
            this.mGroupCount = 1;
        }
        int i10 = i8 % i7;
        int i11 = length - 3;
        int i12 = this.mGroupCount;
        int i13 = i11 / i12;
        this.mGroupItemCount = i13;
        if (i13 < 2) {
            this.mGroupItemCount = 2;
            int i14 = i11 / 2;
            i10 += i7 * (i12 - i14);
            this.mGroupCount = i14;
        }
        int i15 = this.mGroupItemCount;
        int i16 = this.mGroupCount;
        this.mLeftCount = i11 - (i15 * i16);
        this.mItemMargin = i5;
        if (i10 > 0) {
            this.mItemMargin = i5 + ((i10 / 2) / ((i16 * 2) + 3));
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 1;
        int i17 = this.mItemMargin;
        layoutParams.bottomMargin = i17;
        layoutParams.topMargin = i17;
        layoutParams.weight = 1.0f;
        for (int i18 = 0; i18 < length; i18++) {
            int i19 = this.mGroupItemCount;
            int i20 = this.mLeftCount;
            if (i18 < (i19 + 1) * i20) {
                i19++;
                i2 = i18;
            } else {
                i2 = i18 - ((i19 + 1) * i20);
            }
            if (i18 <= 1 || i18 >= length - 2 || (i3 = (i2 - 1) % i19) == 0) {
                String str = visibleIndexes[i18];
                TextView textView = new TextView(getContext());
                Typography.applyMiSansMedium(textView);
                textView.setGravity(17);
                textView.setHeight(this.mItemHeight);
                textView.setIncludeFontPadding(false);
                textView.setTextColor(TextUtils.equals(str, "!") ? this.mTextHighlighter.mStarNormalColor : this.mTextHighlighter.mNormalColor);
                textView.setTextSize(0, this.mTextHighlighter.mIndexerTextSize);
                if (TextUtils.equals(str, "!")) {
                    str = "♥";
                }
                textView.setText(str);
                textView.setImportantForAccessibility(2);
                attachViewToParent(textView, -1, layoutParams);
            } else if (i3 == 1) {
                ImageView imageView = new ImageView(getContext());
                if (this.mFirstOmitItem == null) {
                    this.mFirstOmitItem = imageView;
                }
                imageView.setMaxHeight(this.mOmitItemHeight);
                imageView.setMaxWidth(this.mOmitItemHeight);
                imageView.setImageResource(R.drawable.miuix_ic_omit);
                imageView.setImportantForAccessibility(2);
                attachViewToParent(imageView, -1, layoutParams);
            }
        }
        this.mUseOmit = true;
    }

    public final void doPerformHapticFeedback(int i) {
        getHapticFeedbackCompat().performHapticFeedback(getUsageAlarmVibrationAttributes(), i);
    }

    public final void drawThumbInternal(CharSequence charSequence, float f) {
        if (this.mAdapter == null || this.mOverlay == null) {
            return;
        }
        this.mCancelOverlayTextColorAnim = true;
        if (TextUtils.equals(charSequence, "!")) {
            charSequence = "♥";
        }
        if (!TextUtils.equals(this.mOverlay.getText(), charSequence)) {
            if (HapticCompat.doesSupportHaptic("2.0")) {
                doPerformHapticFeedback(HapticFeedbackConstants.MIUI_GEAR_LIGHT);
            } else {
                doPerformHapticFeedback(HapticFeedbackConstants.MIUI_MESH_NORMAL);
            }
        }
        this.mOverlay.setTextColor(isStarText(charSequence) ? this.mTextHighlighter.mStarHighLightColor : this.mOverlayTextColor);
        this.mOverlay.setTranslationY(f - getMarginTop());
        updateOverlayTextAlpha(1.0f);
        this.mOverlay.setText(charSequence);
        this.mOverlay.setPaddingRelative((this.mOverlayHeight - ((int) this.mOverlayTextPaint.measureText(charSequence.toString()))) / 2, 0, 0, 0);
        this.mOverlay.setVisibility(0);
        showOverlay();
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0043 A[PHI: r0
  0x0043: PHI (r0v9 int) = (r0v7 int), (r0v12 int) binds: [B:32:0x005f, B:22:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    public final int getChildIndex(int i) {
        int i2;
        int i3;
        int i4 = 0;
        int length = this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false).length;
        int i5 = length - 1;
        int i6 = i > i5 ? i5 : i;
        if (getChildCount() != length && (i2 = this.mGroupItemCount) > 1 && i > 1) {
            if (i >= length - 2) {
                i6 = (this.mGroupCount * 2) + 1 + (i6 == i5 ? 1 : 0);
            } else {
                int i7 = this.mLeftCount;
                if (i7 <= 0) {
                    int i8 = i - 1;
                    i3 = ((i8 / i2) * 2) + 1;
                    if (i8 % i2 != 0) {
                        i4 = 1;
                    }
                    i6 = i3 + i4;
                } else if (i < (i2 + 1) * i7) {
                    int i9 = i2 + 1;
                    int i10 = i - 1;
                    i3 = ((i10 / i9) * 2) + 1;
                    if (i10 % i9 != 0) {
                        i4 = 1;
                    }
                    i6 = i3 + i4;
                } else {
                    i6 = ((((i - i7) - 1) / i2) * 2) + 1 + (((i - i7) - 1) % i2 != 0 ? 1 : 0);
                }
            }
        }
        return normalizeIndex(i6);
    }

    public int getIndexerIntrinsicWidth() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicWidth();
        }
        return 0;
    }

    public final int getLetterIndex(String str) {
        int i = this.mLastAlphabetIndex;
        String[] visibleIndexes = this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false);
        for (int i2 = 0; i2 < visibleIndexes.length; i2++) {
            if (TextUtils.equals(str, visibleIndexes[i2])) {
                i = i2;
            }
        }
        if (i == -1) {
            return 0;
        }
        return i;
    }

    public final int getRealSectionPosition(int i, SectionIndexer sectionIndexer) {
        if (sectionIndexer != null) {
            Object[] sections = sectionIndexer.getSections();
            String str = (sections == null || i < 0 || i >= sections.length) ? null : (String) sections[i];
            if (!TextUtils.isEmpty(str)) {
                return getLetterIndex(str.toUpperCase());
            }
        }
        return i;
    }

    public final int getSafeSectionIndex(int i, SectionIndexer sectionIndexer) {
        Object[] sections = sectionIndexer == null ? null : sectionIndexer.getSections();
        String[] visibleIndexes = this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false);
        if ((sections == null || sections.length == 0) && visibleIndexes.length > 0) {
            return i >= visibleIndexes.length ? visibleIndexes.length - 1 : Math.max(i, 0);
        }
        if (sections == null || sections.length == 0 || (getHeight() - getPaddingTop()) - getPaddingBottom() <= 0 || i < 0) {
            return -1;
        }
        if (i >= visibleIndexes.length) {
            return sections.length - 1;
        }
        this.mSectionMap.clear();
        for (int i2 = 0; i2 < sections.length; i2++) {
            this.mSectionMap.put(sections[i2].toString().toUpperCase(), Integer.valueOf(i2));
        }
        int i3 = 0;
        while (true) {
            int i4 = i3 + i;
            if (i4 >= visibleIndexes.length && i < i3) {
                return 0;
            }
            int i5 = i - i3;
            if (i4 < visibleIndexes.length && this.mSectionMap.containsKey(visibleIndexes[i4])) {
                Integer num = (Integer) this.mSectionMap.get(visibleIndexes[i4]);
                Objects.requireNonNull(num);
                return num.intValue();
            }
            if (i5 >= 0 && this.mSectionMap.containsKey(visibleIndexes[i5])) {
                Integer num2 = (Integer) this.mSectionMap.get(visibleIndexes[i5]);
                Objects.requireNonNull(num2);
                return num2.intValue();
            }
            i3++;
        }
    }

    public final ScrollTargetInfo getScrollTargetInfo(int i, SectionIndexer sectionIndexer) {
        ScrollTargetInfo scrollTargetInfo = new ScrollTargetInfo();
        int listOffset = getListOffset();
        if (sectionIndexer == null || sectionIndexer.getSections() == null || sectionIndexer.getSections().length <= 0) {
            scrollTargetInfo.targetItemPos = listOffset;
        } else {
            scrollTargetInfo.targetItemPos = sectionIndexer.getPositionForSection(i) + listOffset;
        }
        scrollTargetInfo.targetSectionIndex = i;
        return scrollTargetInfo;
    }

    public final void handleAccessibilityAction(int i, int i2, SectionIndexer sectionIndexer) {
        int i3;
        if (sectionIndexer == null) {
            return;
        }
        String[] visibleIndexes = this.mTextHighlighter.getVisibleIndexes(sectionIndexer, false);
        if (i2 == 4096) {
            i3 = i - 1;
            while (i3 >= 0) {
                Object[] sections = sectionIndexer.getSections();
                if (sections == null || sections.length <= 0) {
                    if (sections != null) {
                        i = i3;
                        break;
                    }
                    i3--;
                } else {
                    if (Arrays.asList(sections).contains(visibleIndexes[i3])) {
                        i = i3;
                        break;
                    }
                    i3--;
                }
            }
        } else if (i2 == 8192) {
            i3 = i + 1;
            while (i3 <= visibleIndexes.length - 1) {
                Object[] sections2 = sectionIndexer.getSections();
                if (sections2 == null || sections2.length <= 0) {
                    if (sections2 != null) {
                        i = i3;
                        break;
                    }
                    i3++;
                } else {
                    if (Arrays.asList(sections2).contains(visibleIndexes[i3])) {
                        i = i3;
                        break;
                    }
                    i3++;
                }
            }
        }
        scrollToSelection(i, sectionIndexer);
        setChecked(i);
    }

    public final void handleAccessibilityMotionEvent(boolean z, SectionIndexer sectionIndexer, float f) {
        int iCalculateIndex;
        int i = this.mSelectedAlphaIndex;
        if (this.mAccessibilityManager.isEnabled() && this.mAccessibilityManager.isTouchExplorationEnabled() && i >= 0 && z) {
            View childAt = getChildAt(getChildIndex(i));
            float top = (childAt.getTop() + childAt.getBottom()) / 2.0f;
            float paddingTop = top > ((float) getPaddingTop()) ? top - getPaddingTop() : 0.0f;
            this.mTransFormY = paddingTop;
            iCalculateIndex = calculateIndex(paddingTop);
        } else {
            iCalculateIndex = calculateIndex(f);
        }
        ScrollTargetInfo scrollTargetInfoScrollToSelection = scrollToSelection(iCalculateIndex, sectionIndexer);
        if (scrollTargetInfoScrollToSelection != null) {
            int i2 = this.mSelectedAlphaIndex;
            int i3 = scrollTargetInfoScrollToSelection.targetSectionIndex;
            if (i2 != i3) {
                setChecked(i3);
            }
        }
    }

    public final boolean hasShown() {
        TextView textView = this.mOverlay;
        return textView != null && textView.getVisibility() == 0 && this.mOverlay.getAlpha() == 1.0f;
    }

    public final void hideOverlay() {
        TextView textView = this.mOverlay;
        if (textView != null) {
            Folme.useAt(textView).visible().setFlags(1L).setScale(1.0f, IVisibleStyle.VisibleType.SHOW).setScale(0.0f, IVisibleStyle.VisibleType.HIDE).hide(this.mOverlayHideAnimConfig);
        }
    }

    public final void init() {
        this.mVerticalPosition = 8388613;
        setGravity(1);
        setOrientation(1);
        initAnimConfig();
        constructItem(this.mMaxItemMargin);
        setClickable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.mScreenHeightDp = getResources().getConfiguration().screenHeightDp;
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
    }

    public final void initAnimConfig() {
        AnimConfig animConfig = new AnimConfig();
        this.mOverlayShowAnimConfig = animConfig;
        animConfig.addListeners(new TransitionListener() { // from class: miuix.miuixbasewidget.widget.AlphabetIndexer.2
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (AlphabetIndexer.this.isPressed() || !AlphabetIndexer.this.mEnableAutoDismiss) {
                    return;
                }
                AlphabetIndexer.this.stop(0);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    UpdateInfo updateInfo = (UpdateInfo) it.next();
                    if (updateInfo.property == ViewProperty.SCALE_X) {
                        AlphabetIndexer.this.updateOverlayTranslationX(updateInfo.getFloatValue());
                        return;
                    }
                }
            }
        });
        AnimConfig animConfig2 = new AnimConfig();
        this.mOverlayHideAnimConfig = animConfig2;
        animConfig2.addListeners(new TransitionListener() { // from class: miuix.miuixbasewidget.widget.AlphabetIndexer.3
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, Collection collection) {
                super.onBegin(obj, collection);
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    if (((UpdateInfo) it.next()).property == ViewProperty.AUTO_ALPHA) {
                        AlphabetIndexer.this.mCancelOverlayTextColorAnim = false;
                        return;
                    }
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                Iterator it = collection.iterator();
                while (it.hasNext()) {
                    UpdateInfo updateInfo = (UpdateInfo) it.next();
                    FloatProperty floatProperty = updateInfo.property;
                    if (floatProperty == ViewProperty.SCALE_X) {
                        AlphabetIndexer.this.updateOverlayTranslationX(updateInfo.getFloatValue());
                    } else if (floatProperty == ViewProperty.AUTO_ALPHA && !AlphabetIndexer.this.mCancelOverlayTextColorAnim) {
                        AlphabetIndexer.this.updateOverlayTextAlpha(updateInfo.getFloatValue());
                    }
                }
            }
        });
    }

    public final boolean isStarText(CharSequence charSequence) {
        return TextUtils.equals(charSequence, "!") || TextUtils.equals(charSequence, "♥");
    }

    public final int normalizeIndex(int i) {
        if (i < 0) {
            return 0;
        }
        return i >= getChildCount() ? getChildCount() - 1 : i;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = (View) getParent();
        this.mParentView = view;
        if (view != null) {
            view.addOnLayoutChangeListener(this.mParentLayoutChangeListener);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = configuration.screenHeightDp;
        if (i != this.mScreenHeightDp) {
            this.mScreenHeightDp = i;
            this.mMaxItemMargin = getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_alphabet_indexer_item_margin);
            updateVerticalPadding();
            updateOverlayLayout();
            this.mForceUpdate = true;
            View view = this.mParentView;
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        View view = this.mParentView;
        if (view != null) {
            view.removeOnLayoutChangeListener(this.mParentLayoutChangeListener);
            this.mParentView = null;
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        String[] visibleIndexes = this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false);
        if (!isEnabled() || (i = this.mSelectedAlphaIndex) <= -1 || i >= visibleIndexes.length) {
            return;
        }
        accessibilityNodeInfo.addAction(AutoBottomSheetKt.ALIGN_SCREEN_START);
        if (i < visibleIndexes.length) {
            accessibilityNodeInfo.addAction(AutoBottomSheetKt.ALIGN_SCREEN_TOP);
        }
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        accessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(0, -1.0f, visibleIndexes.length, i));
        String string = visibleIndexes[i];
        if (string != null) {
            if (TextUtils.equals(string, "!")) {
                string = getContext().getString(R.string.miuix_indexer_collect);
            }
            accessibilityNodeInfo.setContentDescription(string);
        }
        accessibilityNodeInfo.setStateDescription(getContext().getString(R.string.miuix_alphabet_indexer_name));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mIndexWidth = getWidth();
        updateOverlayLayout();
    }

    /* JADX WARN: Code duplicated, block: B:23:0x002f  */
    /* JADX WARN: Code duplicated, block: B:26:0x003f  */
    /* JADX WARN: Code duplicated, block: B:28:0x0048  */
    /* JADX WARN: Code duplicated, block: B:29:0x004c  */
    /* JADX WARN: Code duplicated, block: B:32:0x0057  */
    /* JADX WARN: Code duplicated, block: B:35:0x0061  */
    /* JADX WARN: Code duplicated, block: B:37:0x006e  */
    /* JADX WARN: Code duplicated, block: B:39:0x0081  */
    /* JADX WARN: Code duplicated, block: B:41:0x0092  */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        if (this.mAdapter == null || getVisibility() != 0) {
            stop(0);
            return false;
        }
        SectionIndexer sectionIndexer = getSectionIndexer();
        if (sectionIndexer == null) {
            stop(0);
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            if (motionEvent.getPointerId(motionEvent.getActionIndex()) == 0) {
                setPressed(true);
                f = this.mTransFormY;
                if (f == -1.0f) {
                    float y = motionEvent.getY() > ((float) getPaddingTop()) ? motionEvent.getY() - getPaddingTop() : 0.0f;
                    this.mClickDownY = y;
                    this.mTransFormY = y;
                    handleAccessibilityMotionEvent(true, sectionIndexer, y);
                } else {
                    float y2 = (f + motionEvent.getY()) - this.mClickDownY;
                    handleAccessibilityMotionEvent(false, sectionIndexer, y2 > ((float) getPaddingTop()) ? y2 - getPaddingTop() : 0.0f);
                }
            }
        } else if (actionMasked == 1) {
            this.mTransFormY = -1.0f;
            announceSelectText();
            if (motionEvent.getPointerId(motionEvent.getActionIndex()) == 0) {
                setPressed(false);
                if (hasShown()) {
                    stop(0);
                }
            }
        } else if (actionMasked == 2) {
            f = this.mTransFormY;
            if (f == -1.0f) {
                if (motionEvent.getY() > ((float) getPaddingTop())) {
                }
                this.mClickDownY = y;
                this.mTransFormY = y;
                handleAccessibilityMotionEvent(true, sectionIndexer, y);
            } else {
                float y3 = (f + motionEvent.getY()) - this.mClickDownY;
                handleAccessibilityMotionEvent(false, sectionIndexer, y3 > ((float) getPaddingTop()) ? y3 - getPaddingTop() : 0.0f);
            }
        } else if (actionMasked == 3) {
            this.mTransFormY = -1.0f;
            announceSelectText();
            if (motionEvent.getPointerId(motionEvent.getActionIndex()) == 0) {
                setPressed(false);
                if (hasShown()) {
                    stop(0);
                }
            }
        } else if (actionMasked != 5) {
            if (actionMasked == 6) {
                this.mTransFormY = -1.0f;
                announceSelectText();
                if (motionEvent.getPointerId(motionEvent.getActionIndex()) == 0) {
                    setPressed(false);
                    if (hasShown()) {
                        stop(0);
                    }
                }
            }
        } else if (motionEvent.getPointerId(motionEvent.getActionIndex()) == 0) {
            setPressed(true);
            f = this.mTransFormY;
            if (f == -1.0f) {
                if (motionEvent.getY() > ((float) getPaddingTop())) {
                }
                this.mClickDownY = y;
                this.mTransFormY = y;
                handleAccessibilityMotionEvent(true, sectionIndexer, y);
            } else {
                float y4 = (f + motionEvent.getY()) - this.mClickDownY;
                handleAccessibilityMotionEvent(false, sectionIndexer, y4 > ((float) getPaddingTop()) ? y4 - getPaddingTop() : 0.0f);
            }
        }
        return true;
    }

    public final void parseAttrs(AttributeSet attributeSet, int i) {
        Resources resources = getContext().getResources();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.MiuixAppcompatAlphabetIndexer, i, R.style.Widget_AlphabetIndexer_Starred_DayNight);
        this.mTextHighlighter = new TextHighlighter(getContext(), typedArrayObtainStyledAttributes);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppcompatDrawOverlay, true);
        this.mDrawOverlay = z;
        if (z) {
            this.mOverlayTextSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppcompatOverlayTextSize, resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_indexer_overlay_text_size));
            this.mOverlayTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppcompatOverlayTextColor, resources.getColor(R.color.miuix_appcompat_alphabet_indexer_overlay_text_color));
            this.mOverlayTextAppearanceRes = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppCompatOverlayTextAppearance, R.style.Widget_TextAppearance_AlphabetIndexer_Overlay);
            this.mOverlayBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MiuixAppcompatAlphabetIndexer_miuixAppcompatOverlayBackground);
            this.mItemHeight = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_indexer_item_height);
            this.mOmitItemHeight = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_indexer_omit_item_height);
            int i2 = R.dimen.miuix_appcompat_alphabet_indexer_item_margin;
            this.mItemMargin = resources.getDimensionPixelSize(i2);
            this.mMaxItemMargin = resources.getDimensionPixelSize(i2);
            this.mMinItemMargin = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_indexer_min_item_margin);
            this.mOverlayWidth = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_overlay_width);
            this.mOverlayHeight = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_overlay_height);
            this.mIndexMinWidth = resources.getDimensionPixelSize(R.dimen.miuix_appcompat_alphabet_indexer_min_width);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (super.performAccessibilityAction(i, bundle)) {
            return true;
        }
        SectionIndexer sectionIndexer = getSectionIndexer();
        if (!isEnabled() || sectionIndexer == null) {
            return false;
        }
        if (i != 4096 && i != 8192) {
            return false;
        }
        handleAccessibilityAction(this.mSelectedAlphaIndex, i, sectionIndexer);
        announceSelectText();
        return true;
    }

    public final void resetViews() {
        this.mGroupCount = 0;
        this.mGroupItemCount = 0;
        this.mSelectedAlphaIndex = -1;
        this.mLastSelectedItem = null;
        this.mFirstOmitItem = null;
        removeAllViews();
    }

    public final void scrollTo(SectionIndexer sectionIndexer, ScrollTargetInfo scrollTargetInfo) {
        Adapter adapter = this.mAdapter;
        if (adapter == null) {
            return;
        }
        adapter.stopScroll();
        Object[] sections = sectionIndexer.getSections();
        this.mAdapter.scrollToPosition(scrollTargetInfo.targetItemPos);
        updateOverlay(scrollTargetInfo, sections);
    }

    public final ScrollTargetInfo scrollToSelection(int i, SectionIndexer sectionIndexer) {
        if (this.mAdapter == null || sectionIndexer == null) {
            return null;
        }
        int safeSectionIndex = getSafeSectionIndex(i, sectionIndexer);
        if (safeSectionIndex >= 0) {
            ScrollTargetInfo scrollTargetInfo = getScrollTargetInfo(safeSectionIndex, sectionIndexer);
            scrollTo(sectionIndexer, scrollTargetInfo);
            return scrollTargetInfo;
        }
        this.mAdapter.scrollToPosition(0);
        ScrollTargetInfo scrollTargetInfo2 = new ScrollTargetInfo();
        scrollTargetInfo2.targetItemPos = 0;
        scrollTargetInfo2.targetSectionIndex = 0;
        return scrollTargetInfo2;
    }

    public void setHandleWindowInsetsEnabled(boolean z) {
        this.mHandleWindowInsetsEnabled = z;
        if (z) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, null);
        if (this.mOriginalMarginEnd >= 0) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) layoutParams).setMarginEnd(this.mOriginalMarginEnd);
            }
        }
    }

    public void setMinVisibleIndexes(@NonNull String[] strArr) {
        this.mTextHighlighter.mMinVisibleIndexes = strArr;
    }

    public void setSectionIndexer(SectionIndexer sectionIndexer) {
        this.mIndexer = sectionIndexer;
    }

    public void setSectionsAsIndexesEnabled(boolean z) {
        this.mTextHighlighter.mSectionsAsIndexesEnabled = z;
    }

    @Deprecated
    public void setVerticalPosition(boolean z) {
        this.mVerticalPosition = z ? 8388613 : 8388611;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 0) {
            stop(0);
            clearLastChecked(this.mLastAlphabetIndex);
        }
    }

    public final void showOverlay() {
        TextView textView = this.mOverlay;
        if (textView != null) {
            Folme.useAt(textView).visible().setFlags(1L).setScale(0.0f, IVisibleStyle.VisibleType.HIDE).setScale(1.0f, IVisibleStyle.VisibleType.SHOW).show(this.mOverlayShowAnimConfig);
        }
    }

    public final void stop(int i) {
        this.mHandler.removeMessages(1);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), i <= 0 ? 0L : i);
    }

    public final void updateIndexItemColor(View view, boolean z) {
        if (!(view instanceof TextView)) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(z ? R.drawable.miuix_ic_omit_selected : R.drawable.miuix_ic_omit);
                return;
            }
            return;
        }
        TextView textView = (TextView) view;
        boolean zIsStarText = isStarText(textView.getText());
        TextHighlighter textHighlighter = this.mTextHighlighter;
        int i = zIsStarText ? textHighlighter.mStarHighLightColor : textHighlighter.mHighlightColor;
        TextHighlighter textHighlighter2 = this.mTextHighlighter;
        int i2 = zIsStarText ? textHighlighter2.mStarNormalColor : textHighlighter2.mNormalColor;
        if (!z) {
            i = i2;
        }
        textView.setTextColor(i);
    }

    public final void updateItemMargin(int i) {
        View childAt = getChildAt(0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
        layoutParams.bottomMargin = i;
        layoutParams.topMargin = i;
        childAt.setLayoutParams(layoutParams);
        this.mItemMargin = i;
    }

    public final void updateItemsAfterParentVisibleHeightChanged(int i) {
        View childAt = getChildAt(0);
        int height = childAt.getHeight();
        String[] visibleIndexes = this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false);
        int length = (visibleIndexes.length * (this.mItemHeight + (this.mMinItemMargin * 2))) + getPaddingTop() + getPaddingBottom();
        int marginTop = getMarginTop() <= 0 ? getMarginTop() + (this.mOverlayHeight / 2) + 1 : getMarginTop();
        int marginBottom = getMarginBottom() <= 0 ? getMarginBottom() + (this.mOverlayHeight / 2) + 1 : getMarginBottom();
        if (length + marginTop + marginBottom > i) {
            if (getChildCount() > 0) {
                resetViews();
            }
            constructItemWithOmit(i);
            checkSelectedItemChanged();
            return;
        }
        int paddingTop = visibleIndexes.length > 0 ? ((((((i - getPaddingTop()) - getPaddingBottom()) - marginTop) - marginBottom) / visibleIndexes.length) - this.mItemHeight) / 2 : this.mMaxItemMargin;
        if (getChildCount() != visibleIndexes.length || this.mUseOmit || this.mForceUpdateVisibleIndexes) {
            this.mForceUpdateVisibleIndexes = false;
            resetViews();
            constructItem(Math.min(this.mMaxItemMargin, paddingTop));
            checkSelectedItemChanged();
            return;
        }
        if (Math.min(this.mMaxItemMargin, paddingTop) != this.mItemMargin) {
            updateItemMargin(Math.min(this.mMaxItemMargin, paddingTop));
        } else if (height == 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams.height = this.mItemHeight;
            int i2 = this.mItemMargin;
            layoutParams.topMargin = i2;
            layoutParams.bottomMargin = i2;
            childAt.setLayoutParams(layoutParams);
        } else if (height != this.mItemHeight) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams2.height = this.mItemHeight;
            layoutParams2.topMargin = 0;
            layoutParams2.bottomMargin = 0;
            childAt.setLayoutParams(layoutParams2);
        }
        checkSelectedItemChanged();
    }

    public final void updateOverlay(ScrollTargetInfo scrollTargetInfo, Object[] objArr) {
        int i;
        if (scrollTargetInfo == null || (i = scrollTargetInfo.targetSectionIndex) < 0 || objArr == null) {
            return;
        }
        if (i >= objArr.length) {
            if (objArr.length == 0) {
                drawThumbInternal(this.mTextHighlighter.getVisibleIndexes(getSectionIndexer(), false)[i], calculateOverlayPosition(i));
                return;
            }
            return;
        }
        String string = objArr[i].toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        String upperCase = string.toUpperCase();
        CharSequence charSequenceSubSequence = upperCase.subSequence(0, 1);
        int letterIndex = getLetterIndex(upperCase);
        scrollTargetInfo.targetSectionIndex = letterIndex;
        drawThumbInternal(charSequenceSubSequence, calculateOverlayPosition(letterIndex));
    }

    public final void updateOverlayLayout() {
        TextView textView = this.mOverlay;
        if (textView != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
            int realWidthOfIndexer = getRealWidthOfIndexer() + getMarinEnd();
            if (layoutParams.getMarginEnd() != realWidthOfIndexer) {
                layoutParams.setMarginEnd(realWidthOfIndexer);
                this.mOverlay.setLayoutParams(layoutParams);
            }
        }
    }

    public final void updateOverlayTextAlpha(float f) {
        TextView textView = this.mOverlay;
        textView.setTextColor(textView.getTextColors().withAlpha((int) (f * 255.0f)));
    }

    public final void updateOverlayTranslationX(float f) {
        float width = (this.mOverlay.getWidth() / 2) * (1.0f - f);
        if (ViewUtils.isLayoutRtl(this)) {
            width *= -1.0f;
        }
        this.mOverlay.setTranslationX(width);
    }

    public final void updateVerticalPadding() {
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_alphabet_indexer_padding_vertical);
        setPadding(getPaddingStart(), dimensionPixelOffset, getPaddingEnd(), dimensionPixelOffset);
    }
}
