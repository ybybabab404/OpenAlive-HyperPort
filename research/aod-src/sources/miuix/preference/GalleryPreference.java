package miuix.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.OriginalViewPager2;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import miuix.core.util.MiuixUIUtils;
import miuix.miuixbasewidget.widget.PageIndicator;
import miuix.viewpager2.widget.ViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public class GalleryPreference extends BasePreference {
    public OriginalViewPager2.OnPageChangeCallback mCallback;
    public boolean mCardEnable;
    public String mContentDescription;
    public Context mContext;
    public PageIndicator mIndicator;
    public LayoutInflater mInflater;
    public int[] mLayoutArray;
    public int mLayoutId;
    public OriginalViewPager2.OnPageChangeCallback mOnPageChangeCallback;
    public LinearLayout mRoot;
    public CharSequence mSummary;
    public CharSequence[] mSummaryArray;
    public TextView mSummaryTextView;
    public CharSequence mTitle;
    public CharSequence[] mTitleArray;
    public TextView mTitleTextView;
    public ViewPager2 mViewPager;

    public class GalleryPreferenceAdapter extends RecyclerView.Adapter<LayoutHolder> {
        public GalleryPreferenceAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return GalleryPreference.this.mLayoutArray.length;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(LayoutHolder layoutHolder, int i) {
            ViewGroup viewGroup = (ViewGroup) layoutHolder.itemView;
            if (viewGroup.getChildCount() == 3) {
                viewGroup.removeViewAt(0);
            }
            View viewInflate = GalleryPreference.this.mInflater.inflate(GalleryPreference.this.mLayoutArray[i], (ViewGroup) layoutHolder.itemView, false);
            viewGroup.addView(viewInflate, 0);
            if (GalleryPreference.this.mTitleArray == null || i >= GalleryPreference.this.mTitleArray.length) {
                layoutHolder.mViewPagerTitleTextView.setVisibility(8);
            } else {
                CharSequence charSequence = GalleryPreference.this.mTitleArray[i];
                layoutHolder.mViewPagerTitleTextView.setVisibility(0);
                layoutHolder.mViewPagerTitleTextView.setText(charSequence);
            }
            if (GalleryPreference.this.mSummaryArray == null || i >= GalleryPreference.this.mSummaryArray.length) {
                layoutHolder.mViewPagerSummaryTextView.setVisibility(8);
            } else {
                layoutHolder.mViewPagerSummaryTextView.setText(GalleryPreference.this.mSummaryArray[i]);
            }
            if (GalleryPreference.this.isAccessibilityEnabled() && layoutHolder.mViewPagerTitleTextView.getVisibility() == 0) {
                GalleryPreference.this.mRoot.setImportantForAccessibility(1);
                layoutHolder.mViewPagerTitleTextView.setImportantForAccessibility(2);
                layoutHolder.mViewPagerSummaryTextView.setImportantForAccessibility(2);
                if (viewInflate != null) {
                    viewInflate.setImportantForAccessibility(2);
                }
                if (GalleryPreference.this.mIndicator != null) {
                    GalleryPreference.this.mIndicator.setImportantForAccessibility(2);
                }
                ViewCompat.setAccessibilityDelegate(GalleryPreference.this.mRoot, new AccessibilityDelegateCompat() { // from class: miuix.preference.GalleryPreference.GalleryPreferenceAdapter.1
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                        if (GalleryPreference.this.mIndicator != null && GalleryPreference.this.mIndicator.getVisibility() == 0) {
                            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
                            accessibilityNodeInfoCompat.setRoleDescription("\u200b");
                        }
                        accessibilityNodeInfoCompat.setContentDescription(GalleryPreference.this.mContentDescription);
                        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(AutoBottomSheetKt.ALIGN_SCREEN_TOP, null));
                        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(AutoBottomSheetKt.ALIGN_SCREEN_START, null));
                        accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
                    }

                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                        int itemCount;
                        if (super.performAccessibilityAction(view, i2, bundle)) {
                            return true;
                        }
                        if (i2 == 4096) {
                            if (GalleryPreference.this.mViewPager != null && GalleryPreference.this.mViewPager.getCurrentItem() > 0) {
                                GalleryPreference.this.mViewPager.setCurrentItem(GalleryPreference.this.mViewPager.getCurrentItem() - 1, true);
                            }
                            return true;
                        }
                        if (i2 != 8192) {
                            return false;
                        }
                        if (GalleryPreference.this.mViewPager != null && GalleryPreference.this.mViewPager.getAdapter() != null && (itemCount = GalleryPreference.this.mViewPager.getAdapter().getItemCount()) > 0 && GalleryPreference.this.mViewPager.getCurrentItem() < itemCount - 1) {
                            GalleryPreference.this.mViewPager.setCurrentItem(GalleryPreference.this.mViewPager.getCurrentItem() + 1, true);
                        }
                        return true;
                    }
                });
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public LayoutHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return GalleryPreference.this.new LayoutHolder(GalleryPreference.this.mInflater.inflate(R.layout.miuix_gallery_preference_viewpager_layout, viewGroup, false));
        }
    }

    public class LayoutHolder extends RecyclerView.ViewHolder {
        public TextView mViewPagerSummaryTextView;
        public TextView mViewPagerTitleTextView;

        public LayoutHolder(View view) {
            super(view);
            this.mViewPagerTitleTextView = (TextView) view.findViewById(R.id.miuix_gallery_preference_internal_title);
            TextView textView = (TextView) view.findViewById(R.id.miuix_gallery_preference_internal_summary);
            this.mViewPagerSummaryTextView = textView;
            GalleryPreference.this.autoAdjustTextViewGravity(textView);
            if (GalleryPreference.this.mTitleArray == null && GalleryPreference.this.mSummaryArray == null) {
                this.mViewPagerTitleTextView.setVisibility(8);
                this.mViewPagerSummaryTextView.setVisibility(8);
            }
        }
    }

    public GalleryPreference(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GalleryPreference(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Miuix_Preference_GalleryPreference);
    }

    public GalleryPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GalleryPreference, i, i2);
        this.mLayoutId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.GalleryPreference_galleryLayout, 0);
        this.mCardEnable = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BasePreference_cardEnable, false);
        this.mTitle = getTitle();
        this.mSummary = getSummary();
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.GalleryPreference_galleryLayoutArray, 0);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.GalleryPreference_galleryTitleArray, 0);
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.GalleryPreference_gallerySummaryArray, 0);
        typedArrayObtainStyledAttributes.recycle();
        this.mLayoutArray = createLayoutArray(resourceId);
        this.mTitleArray = createTitleOrSummaryArray(resourceId2);
        this.mSummaryArray = createTitleOrSummaryArray(resourceId3);
    }

    public static /* synthetic */ String access$184(GalleryPreference galleryPreference, Object obj) {
        String str = galleryPreference.mContentDescription + obj;
        galleryPreference.mContentDescription = str;
        return str;
    }

    public final void autoAdjustTextViewGravity(final TextView textView) {
        if (textView != null) {
            textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: miuix.preference.GalleryPreference$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    TextView textView2 = textView;
                    textView2.setGravity(textView2.getLineCount() > 1 ? 8388611 : 17);
                }
            });
        }
    }

    public final String createContentDescription(TextView textView, TextView textView2) {
        StringBuilder sb = new StringBuilder();
        if (textView != null) {
            CharSequence text = textView.getText();
            if (!TextUtils.isEmpty(text)) {
                sb.append(text);
            }
        }
        if (textView2 != null) {
            CharSequence text2 = textView2.getText();
            if (!TextUtils.isEmpty(text2)) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(text2);
            }
        }
        return sb.toString();
    }

    public final int[] createLayoutArray(int i) {
        if (i == 0) {
            return null;
        }
        TypedArray typedArrayObtainTypedArray = this.mContext.getResources().obtainTypedArray(i);
        int length = typedArrayObtainTypedArray.length();
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = typedArrayObtainTypedArray.getResourceId(i2, 0);
        }
        typedArrayObtainTypedArray.recycle();
        return iArr;
    }

    public final CharSequence[] createTitleOrSummaryArray(int i) {
        if (i == 0) {
            return null;
        }
        return this.mContext.getResources().getStringArray(i);
    }

    public final ViewPager2 createViewPager2() {
        ViewPager2 viewPager2 = new ViewPager2(this.mContext);
        viewPager2.setAdapter(new GalleryPreferenceAdapter());
        viewPager2.setOrientation(0);
        return viewPager2;
    }

    @Override // miuix.preference.BasePreference, miuix.preference.PreferenceStyle
    public boolean enabledCardStyle() {
        return this.mCardEnable;
    }

    @Override // miuix.preference.BasePreference, miuix.preference.FolmeAnimationController
    public boolean isTouchAnimationEnable() {
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x009e  */
    /* JADX WARN: Code duplicated, block: B:20:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:23:0x00b3  */
    /* JADX WARN: Code duplicated, block: B:24:0x00b9  */
    @Override // miuix.preference.BasePreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        View viewInflate;
        PageIndicator pageIndicator;
        if (this.mRoot == null) {
            this.mRoot = (LinearLayout) preferenceViewHolder.findViewById(R.id.miuix_gallery_preference_layout_root);
            this.mTitleTextView = (TextView) preferenceViewHolder.findViewById(R.id.miuix_gallery_preference_external_title);
            TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.miuix_gallery_preference_external_summary);
            this.mSummaryTextView = textView;
            autoAdjustTextViewGravity(textView);
            if (this.mLayoutArray == null) {
                int i = this.mLayoutId;
                if (i != 0) {
                    viewInflate = this.mInflater.inflate(i, (ViewGroup) this.mRoot, false);
                    this.mRoot.addView(viewInflate, 0);
                }
                if (TextUtils.isEmpty(this.mTitle)) {
                    this.mTitleTextView.setVisibility(8);
                } else {
                    this.mTitleTextView.setText(this.mTitle);
                }
                if (TextUtils.isEmpty(this.mSummary)) {
                    this.mSummaryTextView.setVisibility(8);
                } else {
                    this.mSummaryTextView.setText(this.mSummary);
                }
                if (this.mSummaryArray == null && TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSummary) && (pageIndicator = this.mIndicator) != null) {
                    setMarginTop(pageIndicator, 4);
                }
                if (isAccessibilityEnabled() || this.mTitleTextView.getVisibility() != 0) {
                }
                this.mRoot.setImportantForAccessibility(1);
                this.mTitleTextView.setImportantForAccessibility(2);
                this.mSummaryTextView.setImportantForAccessibility(2);
                this.mContentDescription = createContentDescription(this.mTitleTextView, this.mSummaryTextView);
                if (viewInflate != null) {
                    viewInflate.setImportantForAccessibility(2);
                }
                PageIndicator pageIndicator2 = this.mIndicator;
                if (pageIndicator2 != null) {
                    pageIndicator2.setImportantForAccessibility(2);
                }
                ViewCompat.setAccessibilityDelegate(this.mRoot, new AccessibilityDelegateCompat() { // from class: miuix.preference.GalleryPreference.4
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                        if (GalleryPreference.this.mIndicator != null && GalleryPreference.this.mIndicator.getVisibility() == 0) {
                            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
                            accessibilityNodeInfoCompat.setRoleDescription("\u200b");
                        }
                        accessibilityNodeInfoCompat.setContentDescription(GalleryPreference.this.mContentDescription);
                        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(AutoBottomSheetKt.ALIGN_SCREEN_TOP, null));
                        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(AutoBottomSheetKt.ALIGN_SCREEN_START, null));
                        accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
                    }

                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                        int itemCount;
                        if (super.performAccessibilityAction(view, i2, bundle)) {
                            return true;
                        }
                        if (i2 == 4096) {
                            if (GalleryPreference.this.mViewPager != null && GalleryPreference.this.mViewPager.getCurrentItem() > 0) {
                                GalleryPreference.this.mViewPager.setCurrentItem(GalleryPreference.this.mViewPager.getCurrentItem() - 1, true);
                            }
                            return true;
                        }
                        if (i2 != 8192) {
                            return false;
                        }
                        if (GalleryPreference.this.mViewPager != null && GalleryPreference.this.mViewPager.getAdapter() != null && (itemCount = GalleryPreference.this.mViewPager.getAdapter().getItemCount()) > 0 && GalleryPreference.this.mViewPager.getCurrentItem() < itemCount - 1) {
                            GalleryPreference.this.mViewPager.setCurrentItem(GalleryPreference.this.mViewPager.getCurrentItem() + 1, true);
                        }
                        return true;
                    }
                });
                return;
            }
            this.mViewPager = createViewPager2();
            if (isAccessibilityEnabled()) {
                OriginalViewPager2.OnPageChangeCallback onPageChangeCallback = new OriginalViewPager2.OnPageChangeCallback() { // from class: miuix.preference.GalleryPreference.1
                    @Override // androidx.viewpager2.widget.OriginalViewPager2.OnPageChangeCallback
                    public void onPageSelected(int i2) {
                        super.onPageSelected(i2);
                        if (GalleryPreference.this.mTitleArray != null && i2 >= 0 && i2 < GalleryPreference.this.mTitleArray.length) {
                            GalleryPreference galleryPreference = GalleryPreference.this;
                            galleryPreference.mContentDescription = (String) galleryPreference.mTitleArray[i2];
                        }
                        if (GalleryPreference.this.mSummaryArray != null && i2 >= 0 && i2 < GalleryPreference.this.mSummaryArray.length) {
                            GalleryPreference.access$184(GalleryPreference.this, " " + ((Object) GalleryPreference.this.mSummaryArray[i2]));
                        }
                        if (GalleryPreference.this.mRoot.isAccessibilityFocused()) {
                            GalleryPreference.this.mRoot.announceForAccessibility(GalleryPreference.this.mContentDescription);
                        }
                    }
                };
                this.mOnPageChangeCallback = onPageChangeCallback;
                this.mViewPager.registerOnPageChangeCallback(onPageChangeCallback);
            }
            this.mRoot.addView(this.mViewPager, 0);
            PageIndicator pageIndicator3 = new PageIndicator(this.mContext);
            this.mIndicator = pageIndicator3;
            pageIndicator3.setIndicatorCount(this.mViewPager.getAdapter().getItemCount());
            this.mIndicator.setOnPageChangeListener(new PageIndicator.OnPageChangeListener() { // from class: miuix.preference.GalleryPreference.2
                @Override // miuix.miuixbasewidget.widget.PageIndicator.OnPageChangeListener
                public void onPageSelected(int i2) {
                    GalleryPreference.this.mViewPager.setCurrentItem(i2);
                }
            });
            if (this.mCallback == null) {
                OriginalViewPager2.OnPageChangeCallback onPageChangeCallback2 = new OriginalViewPager2.OnPageChangeCallback() { // from class: miuix.preference.GalleryPreference.3
                    @Override // androidx.viewpager2.widget.OriginalViewPager2.OnPageChangeCallback
                    public void onPageScrolled(int i2, float f, int i3) {
                        super.onPageScrolled(i2, f, i3);
                        GalleryPreference.this.mIndicator.setCurrentPosition(i2);
                        GalleryPreference.this.mIndicator.setCurrentPositionOffset(f);
                    }
                };
                this.mCallback = onPageChangeCallback2;
                this.mViewPager.registerOnPageChangeCallback(onPageChangeCallback2);
            }
            this.mRoot.addView(this.mIndicator);
            viewInflate = null;
            if (TextUtils.isEmpty(this.mTitle)) {
                this.mTitleTextView.setVisibility(8);
            } else {
                this.mTitleTextView.setText(this.mTitle);
            }
            if (TextUtils.isEmpty(this.mSummary)) {
                this.mSummaryTextView.setVisibility(8);
            } else {
                this.mSummaryTextView.setText(this.mSummary);
            }
            if (this.mSummaryArray == null) {
                setMarginTop(pageIndicator, 4);
            }
            if (isAccessibilityEnabled()) {
            }
        }
    }

    @Override // androidx.preference.Preference
    public void onDetached() {
        OriginalViewPager2.OnPageChangeCallback onPageChangeCallback;
        OriginalViewPager2.OnPageChangeCallback onPageChangeCallback2;
        super.onDetached();
        ViewPager2 viewPager2 = this.mViewPager;
        if (viewPager2 != null && (onPageChangeCallback2 = this.mOnPageChangeCallback) != null) {
            viewPager2.unregisterOnPageChangeCallback(onPageChangeCallback2);
            this.mOnPageChangeCallback = null;
        }
        ViewPager2 viewPager3 = this.mViewPager;
        if (viewPager3 == null || (onPageChangeCallback = this.mCallback) == null) {
            return;
        }
        viewPager3.unregisterOnPageChangeCallback(onPageChangeCallback);
        this.mCallback = null;
    }

    public final void setMarginTop(View view, int i) {
        int iDp2px = MiuixUIUtils.dp2px(this.mContext, i);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).setMargins(0, iDp2px, 0, 0);
            view.setLayoutParams(layoutParams);
        }
    }

    @Override // androidx.preference.Preference
    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mTitleTextView == null) {
            this.mTitleTextView = new TextView(this.mContext);
        }
        this.mTitleTextView.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mTitleTextView.setText(charSequence);
    }
}
