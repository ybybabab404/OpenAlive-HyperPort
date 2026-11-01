package miuix.preference;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.activity.result.ActivityResultCaller;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.IFragment;
import miuix.appcompat.internal.app.widget.ActionBarImpl;
import miuix.appcompat.internal.app.widget.ActionBarOverlayLayout;
import miuix.container.ExtraPaddingObserver;
import miuix.container.ExtraPaddingPolicy;
import miuix.core.util.EnvStateManager;
import miuix.core.util.MiuixUIUtils;
import miuix.core.util.RomUtils;
import miuix.core.util.WindowBaseInfo;
import miuix.flexible.template.TemplateFactory;
import miuix.internal.util.AttributeResolver;
import miuix.miuixbasewidget.widget.HyperScrollBar;
import miuix.miuixbasewidget.widget.RecyclerViewScrollBarAdapter;
import miuix.os.DeviceHelper;
import miuix.preference.adapter.PreferenceFragmentScrollBarAdapter;
import miuix.preference.flexible.DropdownPreferenceTemplate;
import miuix.preference.flexible.MiuixPreferenceTemplate;
import miuix.preference.flexible.RadioButtonPreferenceTemplate;
import miuix.preference.flexible.TextPreferenceTemplate;
import miuix.preference.utils.PreferenceLayoutUtils;
import miuix.recyclerview.card.CardDefaultItemAnimator;
import miuix.recyclerview.card.base.BaseDecoration;
import miuix.smooth.SmoothCornerHelper;
import miuix.springback.view.SpringBackLayout;
import miuix.theme.token.ContainerToken;

/* JADX INFO: loaded from: classes2.dex */
public abstract class PreferenceFragment extends PreferenceFragmentCompat implements IFragment {
    public Rect mCacheListContainerMargin;
    public int mCardStyle;
    public boolean mConfigChangeUpdateViewEnable;
    public Rect mContentInset;
    public int mDeviceType;
    public ExtraPaddingPolicy mExtraPaddingPolicy;
    public FrameDecoration mFrameDecoration;
    public PreferenceGroupAdapter mGroupAdapter;
    public HyperScrollBar mHyperScrollBar;
    public boolean mIsEnableCardStyle;
    public View mListContainer;
    public int mListViewPaddingBottom;
    public PreferenceFragmentScrollBarAdapter mPreferenceFragmentScrollBarAdapter;
    public RecyclerViewScrollBarAdapter mRecyclerVIewScrollBarAdapter;
    public Insets mTempNavigationBarInsets;
    public boolean mUserExtraPaddingPolicy;
    public boolean mIsOverlayMode = false;
    public boolean mItemSelectable = false;
    public int mCurSelectedItem = -1;
    public boolean mExtraPaddingEnable = true;
    public boolean mExtraPaddingInitEnable = false;
    public List mExtraPaddingObserver = null;
    public int mExtraHorizontalPadding = 0;
    public boolean mEnableNavigationBarInsets = true;
    public boolean mEnableWindowInsets = true;
    public boolean mEnableHyperMaterial = false;
    public boolean mScrollBarVisible = true;
    public boolean mIsFloatingPanelMode = false;

    /* JADX INFO: renamed from: miuix.preference.PreferenceFragment$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnLayoutChangeListener {
        public static /* synthetic */ void $r8$lambda$8bM5_uXNa17viDFFdDBokM1Z_YU(RecyclerView recyclerView) {
            RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator != null) {
                itemAnimator.endAnimations();
            }
        }

        public AnonymousClass1() {
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            Context context = PreferenceFragment.this.getContext();
            if (context != null) {
                int i9 = i8 - i6;
                int i10 = i3 - i;
                int i11 = i4 - i2;
                if (i10 == i7 - i5 && i11 == i9) {
                    return;
                }
                if (PreferenceFragment.this.mFrameDecoration != null) {
                    PreferenceFragment.this.mFrameDecoration.updateContainerHeight(i11);
                }
                if (PreferenceFragment.this.mExtraPaddingPolicy != null) {
                    PreferenceFragment preferenceFragment = PreferenceFragment.this;
                    if (preferenceFragment.updateExtraPaddingHorizontal(context, preferenceFragment.mExtraPaddingPolicy, i10, i11)) {
                        int extraHorizontalPadding = PreferenceFragment.this.getExtraHorizontalPadding();
                        if (PreferenceFragment.this.mExtraPaddingObserver != null) {
                            for (int i12 = 0; i12 < PreferenceFragment.this.mExtraPaddingObserver.size(); i12++) {
                                ((ExtraPaddingObserver) PreferenceFragment.this.mExtraPaddingObserver.get(i12)).onExtraPaddingChanged(extraHorizontalPadding);
                            }
                        }
                        PreferenceFragment.this.onExtraPaddingChanged(extraHorizontalPadding);
                        final RecyclerView listView = PreferenceFragment.this.getListView();
                        if (listView != null) {
                            if (PreferenceFragment.this.mGroupAdapter != null) {
                                PreferenceFragment.this.mGroupAdapter.onExtraPaddingChanged(extraHorizontalPadding);
                            }
                            listView.post(new Runnable() { // from class: miuix.preference.PreferenceFragment$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PreferenceFragment.AnonymousClass1.$r8$lambda$8bM5_uXNa17viDFFdDBokM1Z_YU(listView);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: miuix.preference.PreferenceFragment$4, reason: invalid class name */
    class AnonymousClass4 implements Runnable {
        public final /* synthetic */ PreferenceFragment this$0;
        public final /* synthetic */ String val$key;

        @Override // java.lang.Runnable
        public void run() {
            if (this.this$0.mGroupAdapter != null) {
                if (this.this$0.isActionBarOverlay()) {
                    ActionBar actionBar = this.this$0.getActionBar();
                    this.this$0.mGroupAdapter.setHighlightSnapToEnd(true, actionBar != null ? actionBar.getHeight() : 0);
                }
                this.this$0.mGroupAdapter.requestHighlight(this.this$0.getListView(), this.val$key);
            }
        }
    }

    public class FrameDecoration extends BaseDecoration {
        public boolean isAnimatorRunning;
        public Drawable mCardGroupBackground;
        public int mCardGroupMarginBottom;
        public final ArrayList mCardGroups;
        public int mCheckableFilterColorChecked;
        public int mCheckableFilterColorNormal;
        public Paint mGroupBgPaint;
        public int mGroupUnCheckedBgColor;
        public int mHeightPixels;
        public boolean mIsLayoutRtl;
        public int mMaskPaddingBottom;
        public int mMaskPaddingEnd;
        public int mMaskPaddingStart;
        public int mMaskPaddingTop;
        public PreferenceGroupRect mPreferenceGroupRect;

        public FrameDecoration(Context context) {
            this.mIsLayoutRtl = false;
            this.mCardGroups = new ArrayList();
            this.mPaint.setAntiAlias(true);
            updateClipPaintColor();
            initMaskPadding(context);
            Paint paint = new Paint();
            this.mGroupBgPaint = paint;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            int iResolveColor = AttributeResolver.resolveColor(context, R.attr.checkablePreferenceItemColorFilterNormal);
            this.mGroupUnCheckedBgColor = iResolveColor;
            this.mGroupBgPaint.setColor(iResolveColor);
            this.mGroupBgPaint.setAntiAlias(true);
        }

        public /* synthetic */ FrameDecoration(PreferenceFragment preferenceFragment, Context context, AnonymousClass1 anonymousClass1) {
            this(context);
        }

        public final void calculateGroupBottom(RecyclerView recyclerView, Preference preference, View view, int i, int i2) {
            if (preference.getParent() == null || view == null) {
                return;
            }
            float fFindNearViewY = findNearViewY(recyclerView, view, i, i2, true);
            if (!PreferenceFragment.this.mGroupAdapter.getAnimatorPreferenceGroups().contains(preference.getParent())) {
                this.mPreferenceGroupRect.rectF.bottom = view.getY() + view.getHeight();
            } else if (fFindNearViewY == -1.0f || getNextPreference(recyclerView, i, i2) == null) {
                this.mPreferenceGroupRect.rectF.bottom = view.getY() + view.getHeight();
            } else {
                this.mPreferenceGroupRect.rectF.bottom = fFindNearViewY - this.mCardGroupMarginBottom;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final boolean calculateGroupRect(Preference preference, int i, int i2, RecyclerView recyclerView, int i3, int i4, View view) {
            if ((preference.getParent() instanceof PreferenceScreen) && PreferenceLayoutUtils.isDynamicGroupItem(preference)) {
                i = ((PreferencedynamicGroupController) preference).getGroupItemType();
            } else if (preference.getParent() instanceof PreferenceScreen) {
                i = 1;
            }
            if (i == 1 || (i == 2 && !checkNextIsAtomicPreference(recyclerView, i2, i3))) {
                this.mPreferenceGroupRect.type |= 1;
                calculateGroupTop(recyclerView, preference, view, i4, i2);
                if (i == 1) {
                    PreferenceGroupRect preferenceGroupRect = this.mPreferenceGroupRect;
                    preferenceGroupRect.type = 4 | preferenceGroupRect.type;
                }
                calculateGroupBottom(recyclerView, preference, view, i2, i3);
                this.mPreferenceGroupRect = null;
                return true;
            }
            if (i == 2) {
                this.mPreferenceGroupRect.type |= 1;
                calculateGroupTop(recyclerView, preference, view, i4, i2);
            }
            if (i == 4 || i == 3) {
                PreferenceGroupRect preferenceGroupRect2 = this.mPreferenceGroupRect;
                preferenceGroupRect2.type = 2 | preferenceGroupRect2.type;
                if (preferenceGroupRect2.rectF.bottom < view.getY() + view.getHeight()) {
                    this.mPreferenceGroupRect.rectF.bottom = view.getY() + view.getHeight();
                }
            }
            PreferenceGroupRect preferenceGroupRect3 = this.mPreferenceGroupRect;
            if (preferenceGroupRect3 == null || i != 4) {
                return false;
            }
            preferenceGroupRect3.type |= 4;
            calculateGroupBottom(recyclerView, preference, view, i2, i3);
            RectF rectF = this.mPreferenceGroupRect.rectF;
            if (rectF.bottom <= rectF.top) {
                rectF.top = view.getY();
            }
            this.mPreferenceGroupRect = null;
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0071  */
        @Override // miuix.recyclerview.card.base.BaseDecoration
        public void calculateGroupRectAndDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state, RecyclerView.Adapter adapter) {
            int i;
            int width;
            int i2;
            int i3;
            int i4;
            PreferenceGroupRect preferenceGroupRect;
            RecyclerView recyclerView2 = recyclerView;
            if (isAdapterEmpty() || PreferenceFragment.this.isDisableAllCardStyle()) {
                return;
            }
            this.mCardGroups.clear();
            int childCount = recyclerView2.getChildCount();
            boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(recyclerView2);
            this.mIsLayoutRtl = zIsLayoutRtl;
            if (zIsLayoutRtl) {
                i = this.mCardMarginEnd + PreferenceFragment.this.mExtraHorizontalPadding;
                width = recyclerView2.getWidth() - this.mCardMarginStart;
                i2 = PreferenceFragment.this.mExtraHorizontalPadding;
            } else {
                i = this.mCardMarginStart + PreferenceFragment.this.mExtraHorizontalPadding;
                width = recyclerView2.getWidth() - this.mCardMarginEnd;
                i2 = PreferenceFragment.this.mExtraHorizontalPadding;
            }
            int i5 = width - i2;
            int i6 = i;
            int i7 = 0;
            int i8 = 0;
            while (i8 < childCount) {
                View childAt = recyclerView2.getChildAt(i8);
                int childAdapterPosition = recyclerView2.getChildAdapterPosition(childAt);
                Preference item = PreferenceFragment.this.mGroupAdapter.getItem(childAdapterPosition);
                if (item == null) {
                    i3 = i7;
                    i4 = childCount;
                    recyclerView2 = recyclerView;
                    childCount = i4;
                    i7 = i3;
                } else {
                    int positionType = PreferenceFragment.this.mGroupAdapter.getPositionType(childAdapterPosition);
                    AnonymousClass1 anonymousClass1 = null;
                    if (i7 >= this.mCardGroups.size()) {
                        PreferenceGroupRect preferenceGroupRect2 = new PreferenceGroupRect(PreferenceFragment.this, anonymousClass1);
                        this.mPreferenceGroupRect = preferenceGroupRect2;
                        this.mCardGroups.add(preferenceGroupRect2);
                    } else if (this.mPreferenceGroupRect == null) {
                        this.mPreferenceGroupRect = (PreferenceGroupRect) this.mCardGroups.get(i7);
                    }
                    boolean z = item instanceof RadioButtonPreference;
                    if (z || (item.getParent() instanceof RadioSetPreferenceCategory)) {
                        i3 = i7;
                        i4 = childCount;
                        RadioSetPreferenceCategory radioSetPreferenceCategory = item.getParent() instanceof RadioSetPreferenceCategory ? (RadioSetPreferenceCategory) item.getParent() : null;
                        if (positionType == 1 || positionType == 2) {
                            PreferenceGroupRect preferenceGroupRect3 = this.mPreferenceGroupRect;
                            preferenceGroupRect3.type |= 1;
                            preferenceGroupRect3.startRadioButtonCategory = true;
                            preferenceGroupRect3.isRadioButton = true;
                            if (item.getParent() != null) {
                                int i9 = i8;
                                calculateGroupTop(recyclerView, item, childAt, childAdapterPosition, i9);
                                i8 = i9;
                            }
                        }
                        if (positionType == 4 || positionType == 3) {
                            this.mPreferenceGroupRect.isRadioButton = true;
                            PreferenceGroupRect preferenceGroupRect4 = this.mPreferenceGroupRect;
                            preferenceGroupRect4.type |= 2;
                            if (preferenceGroupRect4.rectF.bottom < childAt.getY() + childAt.getHeight()) {
                                this.mPreferenceGroupRect.rectF.bottom = childAt.getY() + childAt.getHeight();
                            }
                        }
                        if (radioSetPreferenceCategory != null && radioSetPreferenceCategory.getPrimaryPreference() == item && (preferenceGroupRect = this.mPreferenceGroupRect) != null) {
                            preferenceGroupRect.primeIndex = i8;
                        }
                        if (this.mPreferenceGroupRect != null) {
                            if (item.getParent() instanceof RadioSetPreferenceCategory) {
                                this.mPreferenceGroupRect.isRadioButtonChecked = false;
                                RadioSetPreferenceCategory radioSetPreferenceCategory2 = (RadioSetPreferenceCategory) item.getParent();
                                int preferenceCount = radioSetPreferenceCategory2.getPreferenceCount();
                                for (int i10 = 0; i10 < preferenceCount; i10++) {
                                    Preference preference = radioSetPreferenceCategory2.getPreference(i10);
                                    if ((preference instanceof RadioButtonPreference) && ((RadioButtonPreference) preference).isChecked()) {
                                        this.mPreferenceGroupRect.isRadioButtonChecked = true;
                                        break;
                                    }
                                }
                            } else if (z) {
                                this.mPreferenceGroupRect.isRadioButtonChecked = ((RadioButtonPreference) item).isChecked();
                            }
                        }
                        if (positionType == 1 || positionType == 4) {
                            recyclerView2 = recyclerView;
                            int i11 = i8;
                            childCount = i4;
                            calculateGroupBottom(recyclerView2, item, childAt, i11, childCount);
                            i8 = i11;
                            this.mPreferenceGroupRect.endRadioButtonCategory = checkEndRadioButtonPreferenceCategory(recyclerView2, i8, childCount);
                            this.mPreferenceGroupRect.type |= 4;
                            this.mPreferenceGroupRect = null;
                            i7 = i3 + 1;
                        } else {
                            recyclerView2 = recyclerView;
                            childCount = i4;
                            i7 = i3;
                        }
                    } else {
                        if (isPreferenceCardStyleEnabled(item)) {
                            i3 = i7;
                            boolean zCalculateGroupRect = calculateGroupRect(item, positionType, i8, recyclerView2, childCount, childAdapterPosition, childAt);
                            i4 = childCount;
                            if (zCalculateGroupRect) {
                                i7 = i3 + 1;
                                recyclerView2 = recyclerView;
                                childCount = i4;
                            }
                        } else {
                            i3 = i7;
                            i4 = childCount;
                        }
                        recyclerView2 = recyclerView;
                        childCount = i4;
                        i7 = i3;
                    }
                }
                i8++;
            }
            for (int i12 = 0; i12 < this.mCardGroups.size(); i12++) {
                PreferenceGroupRect preferenceGroupRect5 = (PreferenceGroupRect) this.mCardGroups.get(i12);
                RectF rectF = preferenceGroupRect5.rectF;
                float f = rectF.top;
                float f2 = rectF.bottom;
                RectF rectF2 = new RectF();
                rectF2.set(i6, f, i5, f2);
                if (f2 - f > 0.0f) {
                    int i13 = preferenceGroupRect5.type;
                    boolean z2 = (i13 & 1) != 0;
                    boolean z3 = (i13 & 4) != 0;
                    float f3 = z2 ? this.mCardRadius : 0.0f;
                    float f4 = z3 ? this.mCardRadius : 0.0f;
                    this.mCardPath.reset();
                    this.mCardPath.addRoundRect(rectF2, new float[]{f3, f3, f3, f3, f4, f4, f4, f4}, Path.Direction.CW);
                    if (!preferenceGroupRect5.isRadioButton || PreferenceFragment.this.mIsEnableCardStyle) {
                        Drawable drawable = this.mCardGroupBackground;
                        if (drawable != null) {
                            if (drawable instanceof ColorDrawable) {
                                this.mPaint.setColor(((ColorDrawable) drawable).getColor());
                                canvas.drawPath(this.mCardPath, this.mPaint);
                            } else {
                                clipDrawableRoundRect(canvas, rectF2, this.mCardPath, drawable);
                            }
                        }
                    } else {
                        this.mPaint.setColor(preferenceGroupRect5.isRadioButtonChecked ? this.mCheckableFilterColorChecked : this.mCheckableFilterColorNormal);
                        canvas.drawPath(this.mCardPath, this.mPaint);
                    }
                }
            }
            RecyclerView.ItemAnimator itemAnimator = recyclerView2.getItemAnimator();
            if (itemAnimator == null) {
                return;
            }
            if (itemAnimator.isRunning()) {
                this.isAnimatorRunning = true;
                return;
            }
            if (this.isAnimatorRunning) {
                PreferenceFragment.this.mGroupAdapter.clearPreferenceGroups();
            }
            this.isAnimatorRunning = false;
        }

        public final void calculateGroupTop(RecyclerView recyclerView, Preference preference, View view, int i, int i2) {
            FrameDecoration frameDecoration;
            View view2;
            if (preference.getParent() == null) {
                this.mPreferenceGroupRect.rectF.top = view.getY();
                return;
            }
            if (PreferenceFragment.this.mGroupAdapter.getAnimatorPreferenceGroups().contains(preference.getParent())) {
                boolean zCheckPreIsAtomicPreference = checkPreIsAtomicPreference(i);
                frameDecoration = this;
                view2 = view;
                float fFindNearViewY = frameDecoration.findNearViewY(recyclerView, view2, i2, 0, false);
                if (frameDecoration.getPrePreference(recyclerView, i2) == null) {
                    frameDecoration.mPreferenceGroupRect.rectF.top = view2.getY();
                } else if (zCheckPreIsAtomicPreference) {
                    if (fFindNearViewY == -1.0f) {
                        frameDecoration.mPreferenceGroupRect.rectF.top = view2.getY();
                    } else {
                        frameDecoration.mPreferenceGroupRect.rectF.top = fFindNearViewY + frameDecoration.mCardGroupMarginBottom;
                    }
                } else if (fFindNearViewY == -1.0f) {
                    frameDecoration.mPreferenceGroupRect.rectF.top = view2.getY();
                } else {
                    frameDecoration.mPreferenceGroupRect.rectF.top = fFindNearViewY;
                }
            } else {
                frameDecoration = this;
                view2 = view;
                frameDecoration.mPreferenceGroupRect.rectF.top = view2.getY();
            }
            if (frameDecoration.mPreferenceGroupRect.rectF.bottom < view2.getY() + view2.getHeight()) {
                frameDecoration.mPreferenceGroupRect.rectF.bottom = view2.getY() + view2.getHeight();
            }
        }

        public final void calculateItemOffsets(Rect rect, int i, Preference preference) {
            int positionType = PreferenceFragment.this.mGroupAdapter.getPositionType(i);
            if ((preference.getParent() instanceof PreferenceScreen) && !PreferenceLayoutUtils.isDynamicGroupItem(preference)) {
                positionType = 1;
            }
            if (positionType == 1 || positionType == 4) {
                rect.bottom += this.mCardGroupMarginBottom;
            }
        }

        public final boolean checkEndRadioButtonPreferenceCategory(RecyclerView recyclerView, int i, int i2) {
            int i3 = i + 1;
            if (i3 < i2) {
                return !(PreferenceFragment.this.mGroupAdapter.getItem(recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i3))) instanceof RadioSetPreferenceCategory);
            }
            return false;
        }

        public final boolean checkNextIsAtomicPreference(RecyclerView recyclerView, int i, int i2) {
            return !(getNextPreference(recyclerView, i, i2) instanceof PreferenceGroup);
        }

        public final boolean checkPreIsAtomicPreference(int i) {
            int i2 = i - 1;
            if (i2 >= 0) {
                return !((PreferenceFragment.this.mGroupAdapter != null ? PreferenceFragment.this.mGroupAdapter.getItem(i2) : null) instanceof PreferenceGroup);
            }
            return false;
        }

        public void enableHyperMaterial(boolean z) {
            this.mEnableHyperMaterial = z;
            setCardDrawable();
        }

        public final int findNearViewY(RecyclerView recyclerView, View view, int i, int i2, boolean z) {
            View childAt;
            if (z) {
                if (view == null || view.getTop() >= this.mHeightPixels) {
                    return -1;
                }
                do {
                    i++;
                    if (i < i2) {
                        childAt = recyclerView.getChildAt(i);
                    }
                } while (childAt == null);
                return (int) childAt.getY();
            }
            for (int i3 = i - 1; i3 >= i2; i3--) {
                View childAt2 = recyclerView.getChildAt(i3);
                if (childAt2 != null) {
                    return ((int) childAt2.getY()) + childAt2.getHeight();
                }
            }
            return -1;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition;
            Preference item;
            if (isAdapterEmpty() || PreferenceFragment.this.isDisableAllCardStyle() || (item = PreferenceFragment.this.mGroupAdapter.getItem((childAdapterPosition = recyclerView.getChildAdapterPosition(view)))) == null) {
                return;
            }
            if ((item.getParent() instanceof RadioSetPreferenceCategory) || ((!(item instanceof PreferenceGroup) && (item.getParent() instanceof RadioButtonPreferenceCategory)) || (item instanceof RadioButtonPreference))) {
                setItemOffsets(rect, item, childAdapterPosition, recyclerView);
                return;
            }
            if (isPreferenceCardStyleEnabled(item)) {
                setItemOffsets(rect, item, childAdapterPosition, recyclerView);
            }
            if (recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() != childAdapterPosition + 1) {
                return;
            }
            rect.bottom = 0;
        }

        public final Preference getNextPreference(RecyclerView recyclerView, int i, int i2) {
            int i3 = i + 1;
            if (i3 < i2) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i3));
                if (PreferenceFragment.this.mGroupAdapter != null) {
                    return PreferenceFragment.this.mGroupAdapter.getItem(childAdapterPosition);
                }
            }
            return null;
        }

        public final Preference getPrePreference(RecyclerView recyclerView, int i) {
            int i2 = i - 1;
            if (i2 >= 0) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(i2));
                if (PreferenceFragment.this.mGroupAdapter != null) {
                    return PreferenceFragment.this.mGroupAdapter.getItem(childAdapterPosition);
                }
            }
            return null;
        }

        public void initMaskPadding(Context context) {
            this.mMaskPaddingTop = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_checkable_item_mask_padding_top);
            this.mMaskPaddingBottom = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_checkable_item_mask_padding_bottom);
            this.mMaskPaddingStart = AttributeResolver.resolveDimensionPixelSize(context, R.attr.preferenceCheckableItemMaskPaddingStart);
            this.mMaskPaddingEnd = AttributeResolver.resolveDimensionPixelSize(context, R.attr.preferenceCheckableItemSetMaskPaddingEnd);
            this.mCardRadius = context.getResources().getDimensionPixelSize(R.dimen.miuix_theme_radius_medium);
            this.mCardMarginStart = AttributeResolver.resolveDimensionPixelSize(context, R.attr.preferenceCardGroupMarginStart);
            this.mCardMarginEnd = AttributeResolver.resolveDimensionPixelSize(context, R.attr.preferenceCardGroupMarginEnd);
            this.mCheckableFilterColorChecked = AttributeResolver.resolveColor(context, R.attr.checkablePreferenceItemColorFilterChecked);
            this.mCheckableFilterColorNormal = AttributeResolver.resolveColor(context, R.attr.checkablePreferenceItemColorFilterNormal);
            this.mCardGroupMarginBottom = context.getResources().getDimensionPixelSize(R.dimen.miuix_preference_card_group_margin_bottom);
            if (PreferenceFragment.this.mIsEnableCardStyle) {
                setCardDrawable();
            }
        }

        public final boolean isAdapterEmpty() {
            return PreferenceFragment.this.mGroupAdapter == null || PreferenceFragment.this.mGroupAdapter.getItemCount() == 0;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final boolean isPreferenceCardStyleEnabled(Preference preference) {
            if (!PreferenceFragment.this.mIsEnableCardStyle || (preference instanceof PreferenceGroup)) {
                return false;
            }
            if (preference instanceof PreferenceStyle) {
                return ((PreferenceStyle) preference).enabledCardStyle();
            }
            return true;
        }

        public final void setCardDrawable() {
            Context context = PreferenceFragment.this.getContext();
            if (context != null) {
                Drawable drawableResolveDrawable = this.mEnableHyperMaterial ? AttributeResolver.resolveDrawable(context, R.attr.preferenceHyperMaterialCardGroupBackground) : AttributeResolver.resolveDrawable(context, R.attr.preferenceCardGroupBackground);
                this.mCardGroupBackground = drawableResolveDrawable;
                if (drawableResolveDrawable instanceof ColorDrawable) {
                    this.mPaint.setColor(((ColorDrawable) drawableResolveDrawable).getColor());
                }
            }
        }

        public final void setItemOffsets(Rect rect, Preference preference, int i, RecyclerView recyclerView) {
            boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(recyclerView);
            int i2 = zIsLayoutRtl ? this.mCardMarginEnd : this.mCardMarginStart;
            int i3 = zIsLayoutRtl ? this.mCardMarginStart : this.mCardMarginEnd;
            rect.left = i2 + PreferenceFragment.this.mExtraHorizontalPadding;
            rect.right = i3 + PreferenceFragment.this.mExtraHorizontalPadding;
            calculateItemOffsets(rect, i, preference);
        }

        public void updateClipPaintColor() {
            if (!(PreferenceFragment.this.getActivity() instanceof AppCompatActivity) || ((AppCompatActivity) PreferenceFragment.this.getActivity()).isInFloatingWindowMode()) {
                this.mPaint.setColor(AttributeResolver.resolveColor(PreferenceFragment.this.getContext(), R.attr.preferenceCheckableMaskColor));
            } else {
                this.mPaint.setColor(AttributeResolver.resolveColor(PreferenceFragment.this.getContext(), R.attr.preferenceNormalCheckableMaskColor));
            }
        }

        public void updateContainerHeight(int i) {
            this.mHeightPixels = i;
        }
    }

    public class PreferenceGroupRect {
        public boolean endRadioButtonCategory;
        public boolean isRadioButton;
        public boolean isRadioButtonChecked;
        public int primeIndex;
        public RectF rectF;
        public boolean startRadioButtonCategory;
        public int type;

        public PreferenceGroupRect() {
            this.rectF = new RectF();
            this.primeIndex = -1;
            this.endRadioButtonCategory = false;
            this.startRadioButtonCategory = false;
            this.type = 0;
            this.isRadioButton = false;
            this.isRadioButtonChecked = false;
        }

        public /* synthetic */ PreferenceGroupRect(PreferenceFragment preferenceFragment, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    static {
        TemplateFactory.registerTemplate("dropdownPreference", DropdownPreferenceTemplate.class);
        TemplateFactory.registerTemplate("textPreference", TextPreferenceTemplate.class);
        TemplateFactory.registerTemplate("radioButtonPreference", RadioButtonPreferenceTemplate.class);
        TemplateFactory.registerTemplate("preference", MiuixPreferenceTemplate.class);
    }

    private void initExtraPaddingPolicy() {
        ExtraPaddingPolicy extraPaddingPolicyCreateDefault = ExtraPaddingPolicy.Builder.createDefault(this.mDeviceType, ContainerToken.PADDING_BASE_DP, ContainerToken.PADDING_HORIZONTAL_COMMON);
        this.mExtraPaddingPolicy = extraPaddingPolicyCreateDefault;
        if (extraPaddingPolicyCreateDefault != null) {
            extraPaddingPolicyCreateDefault.setEnable(this.mExtraPaddingEnable);
            float f = getResources().getDisplayMetrics().density;
            if (this.mExtraPaddingPolicy.isEnable()) {
                this.mExtraHorizontalPadding = (int) ((this.mExtraPaddingPolicy.getExtraPaddingDp() * f) + 0.5f);
            } else {
                this.mExtraHorizontalPadding = 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateExtraPaddingHorizontal(Context context, ExtraPaddingPolicy extraPaddingPolicy, int i, int i2) {
        Resources resources = context.getResources();
        WindowBaseInfo windowInfo = EnvStateManager.getWindowInfo(context, resources.getConfiguration());
        if (i == 0) {
            i = windowInfo.windowSize.x;
        }
        int i3 = i;
        if (i2 == 0) {
            i2 = windowInfo.windowSize.y;
        }
        float f = resources.getDisplayMetrics().density;
        Point point = windowInfo.windowSizeDp;
        extraPaddingPolicy.onContainerSizeChanged(point.x, point.y, i3, i2, f, isInFloatingWindowMode());
        return setExtraHorizontalPadding(extraPaddingPolicy.isEnable() ? (int) ((extraPaddingPolicy.getExtraPaddingDp() * f) + 0.5f) : 0);
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean acceptExtraPaddingFromParent() {
        return false;
    }

    public final void addWindowInsetsListener() {
        View view;
        if (!this.mEnableWindowInsets || isActionBarOverlay() || (view = this.mListContainer) == null) {
            return;
        }
        miuix.internal.util.ViewUtils.doOnApplyWindowInsets(view, new miuix.internal.util.ViewUtils.OnApplyWindowInsetsListener() { // from class: miuix.preference.PreferenceFragment.2
            @Override // miuix.internal.util.ViewUtils.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat, miuix.internal.util.ViewUtils.RelativePadding relativePadding) {
                WindowInsetsCompat rootWindowInsets;
                Insets insets;
                if (!PreferenceFragment.this.isLayoutHideNavigation(view2) || (rootWindowInsets = ViewCompat.getRootWindowInsets(view2)) == null || ((insets = rootWindowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())) != null && PreferenceFragment.this.mTempNavigationBarInsets != null && PreferenceFragment.this.mTempNavigationBarInsets.equals(insets))) {
                    return windowInsetsCompat;
                }
                PreferenceFragment.this.mTempNavigationBarInsets = insets;
                PreferenceFragment preferenceFragment = PreferenceFragment.this;
                if (preferenceFragment.mEnableNavigationBarInsets) {
                    preferenceFragment.applyWindowInsets(preferenceFragment.mListContainer, PreferenceFragment.this.mTempNavigationBarInsets);
                }
                PreferenceFragment preferenceFragment2 = PreferenceFragment.this;
                preferenceFragment2.setRecyclerViewPadding(preferenceFragment2.mTempNavigationBarInsets);
                return windowInsetsCompat;
            }
        });
    }

    public final void applyWindowInsets(View view, Insets insets) {
        int i;
        int i2;
        if (view == null) {
            return;
        }
        ViewGroup.MarginLayoutParams layoutParams = getLayoutParams(view);
        Rect rect = this.mCacheListContainerMargin;
        if (rect != null) {
            i = rect.left;
            i2 = rect.right;
        } else {
            i = 0;
            i2 = 0;
        }
        layoutParams.setMargins(i + insets.left, layoutParams.topMargin, i2 + insets.right, layoutParams.bottomMargin);
        view.setLayoutParams(layoutParams);
    }

    @Override // miuix.appcompat.app.IFragment
    public void checkThemeLegality() {
    }

    @Override // miuix.appcompat.app.IFragment
    public ActionBar getActionBar() {
        ActivityResultCaller parentFragment = getParentFragment();
        FragmentActivity activity = getActivity();
        if (parentFragment == null && (activity instanceof AppCompatActivity)) {
            return ((AppCompatActivity) activity).getAppCompatActionBar();
        }
        if (parentFragment instanceof IFragment) {
            return ((IFragment) parentFragment).getActionBar();
        }
        return null;
    }

    @Override // miuix.appcompat.app.IContentInsetState
    public Rect getContentInset() {
        if (this.mIsOverlayMode && this.mContentInset == null) {
            ActivityResultCaller parentFragment = getParentFragment();
            if (parentFragment == null && (getActivity() instanceof AppCompatActivity)) {
                this.mContentInset = ((AppCompatActivity) getActivity()).getContentInset();
            } else if (parentFragment instanceof IFragment) {
                this.mContentInset = ((IFragment) parentFragment).getContentInset();
            }
        }
        return this.mContentInset;
    }

    public final Rect getContentViewMargin(View view) {
        if (view == null) {
            return new Rect();
        }
        ViewGroup.MarginLayoutParams layoutParams = getLayoutParams(view);
        return new Rect(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
    }

    public int getExtraHorizontalPadding() {
        return this.mExtraHorizontalPadding;
    }

    public final ViewGroup.MarginLayoutParams getLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return (ViewGroup.MarginLayoutParams) layoutParams;
        }
        return layoutParams != null ? new ViewGroup.MarginLayoutParams(layoutParams) : new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    }

    public int getListViewPaddingBottom() {
        return -1;
    }

    public int getListViewPaddingTop() {
        return -1;
    }

    @Override // miuix.appcompat.app.IFragment
    public Context getThemedContext() {
        return getContext();
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean hasActionBar() {
        return false;
    }

    public boolean isActionBarOverlay() {
        return this.mIsOverlayMode;
    }

    public boolean isConfigChangeUpdateViewEnable() {
        return true;
    }

    public final boolean isDisableAllCardStyle() {
        return -1 == this.mCardStyle;
    }

    public boolean isEmbeddedFragment() {
        return false;
    }

    @Override // miuix.container.ExtraPaddingProcessor
    public boolean isExtraHorizontalPaddingEnable() {
        return this.mExtraPaddingEnable;
    }

    public boolean isInFloatingWindowMode() {
        FragmentActivity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            return ((AppCompatActivity) activity).isInFloatingWindowMode();
        }
        return false;
    }

    public final boolean isLayoutHideNavigation(View view) {
        return MiuixUIUtils.isTargetSdkVersionAboveV(view.getContext()) || (view.getWindowSystemUiVisibility() & 512) != 0;
    }

    public final boolean isTabletOrFold() {
        int i = this.mDeviceType;
        return i == 2 || i == 3 || i == 5;
    }

    @Override // miuix.appcompat.app.IFragment
    public void onActionModeFinished(ActionMode actionMode) {
    }

    @Override // miuix.appcompat.app.IFragment
    public void onActionModeStarted(ActionMode actionMode) {
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        PreferenceScreen preferenceScreen;
        FrameDecoration frameDecoration;
        super.onConfigurationChanged(configuration);
        stopHighlight();
        if (getActivity() == null) {
            return;
        }
        Context context = getContext();
        if (context != null) {
            setActionBarOverLayoutBg();
            int iDetectType = DeviceHelper.detectType(context);
            if (this.mDeviceType != iDetectType) {
                this.mDeviceType = iDetectType;
                if (!this.mUserExtraPaddingPolicy) {
                    this.mExtraPaddingPolicy = ExtraPaddingPolicy.Builder.createDefault(iDetectType, ContainerToken.PADDING_BASE_DP, ContainerToken.PADDING_HORIZONTAL_COMMON);
                }
                ExtraPaddingPolicy extraPaddingPolicy = this.mExtraPaddingPolicy;
                if (extraPaddingPolicy != null) {
                    extraPaddingPolicy.setEnable(this.mExtraPaddingEnable);
                    if (this.mExtraPaddingInitEnable ? updateExtraPaddingHorizontal(context, this.mExtraPaddingPolicy, -1, -1) : setExtraHorizontalPadding(this.mExtraPaddingPolicy.isEnable() ? (int) (this.mExtraPaddingPolicy.getExtraPaddingDp() * getResources().getDisplayMetrics().density) : 0)) {
                        int extraHorizontalPadding = getExtraHorizontalPadding();
                        PreferenceGroupAdapter preferenceGroupAdapter = this.mGroupAdapter;
                        if (preferenceGroupAdapter != null) {
                            preferenceGroupAdapter.setExtraHorizontalPadding(extraHorizontalPadding);
                        }
                        if (this.mExtraPaddingObserver != null) {
                            for (int i = 0; i < this.mExtraPaddingObserver.size(); i++) {
                                ((ExtraPaddingObserver) this.mExtraPaddingObserver.get(i)).onExtraPaddingChanged(extraHorizontalPadding);
                            }
                        }
                        onExtraPaddingChanged(extraHorizontalPadding);
                    }
                }
            }
        }
        if (this.mHyperScrollBar != null && isActionBarOverlay() && this.mScrollBarVisible) {
            this.mHyperScrollBar.postDelayed(new Runnable() { // from class: miuix.preference.PreferenceFragment.5
                @Override // java.lang.Runnable
                public void run() {
                    ActionBar actionBar = PreferenceFragment.this.getActionBar();
                    if (actionBar != null) {
                        int height = actionBar.getHeight();
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PreferenceFragment.this.mHyperScrollBar.getLayoutParams();
                        layoutParams.setMargins(layoutParams.leftMargin, height, layoutParams.rightMargin, layoutParams.bottomMargin);
                        PreferenceFragment.this.mHyperScrollBar.setLayoutParams(layoutParams);
                    }
                }
            }, 500L);
        }
        if (!isTabletOrFold() || !this.mConfigChangeUpdateViewEnable || (preferenceScreen = getPreferenceScreen()) == null || (frameDecoration = this.mFrameDecoration) == null) {
            return;
        }
        frameDecoration.initMaskPadding(preferenceScreen.getContext());
        this.mFrameDecoration.updateClipPaintColor();
        PreferenceGroupAdapter preferenceGroupAdapter2 = this.mGroupAdapter;
        if (preferenceGroupAdapter2 != null) {
            preferenceGroupAdapter2.initAttr(preferenceScreen.getContext());
            PreferenceGroupAdapter preferenceGroupAdapter3 = this.mGroupAdapter;
            FrameDecoration frameDecoration2 = this.mFrameDecoration;
            preferenceGroupAdapter3.setClipPaint(frameDecoration2.mPaint, frameDecoration2.mMaskPaddingTop, this.mFrameDecoration.mMaskPaddingBottom, this.mFrameDecoration.mMaskPaddingStart, this.mFrameDecoration.mMaskPaddingEnd, this.mFrameDecoration.mCardRadius);
        }
    }

    @Override // miuix.appcompat.app.IContentInsetState
    public void onContentInsetChanged(Rect rect) {
        if (rect == null) {
            return;
        }
        setRecyclerViewPadding(Insets.of(rect));
        applyWindowInsets(this.mListContainer, Insets.of(rect));
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mConfigChangeUpdateViewEnable = isConfigChangeUpdateViewEnable();
        Context themedContext = getThemedContext();
        if (themedContext != null) {
            TypedArray typedArrayObtainStyledAttributes = themedContext.obtainStyledAttributes(miuix.appcompat.R.styleable.Window);
            setExtraHorizontalPaddingEnable(typedArrayObtainStyledAttributes.getBoolean(miuix.appcompat.R.styleable.Window_windowExtraPaddingHorizontalEnable, this.mExtraPaddingEnable));
            setExtraHorizontalPaddingInitEnable(typedArrayObtainStyledAttributes.getBoolean(miuix.appcompat.R.styleable.Window_windowExtraPaddingHorizontalInitEnable, this.mExtraPaddingInitEnable));
            typedArrayObtainStyledAttributes.recycle();
            boolean z = true;
            this.mCardStyle = AttributeResolver.resolveInt(themedContext, R.attr.preferenceCardStyleEnable, 1);
            this.mScrollBarVisible = AttributeResolver.resolveBoolean(themedContext, R.attr.preferenceScrollBarEnable, true);
            if (this.mCardStyle != 2 && (RomUtils.getHyperOsVersion() <= 1 || this.mCardStyle != 1)) {
                z = false;
            }
            this.mIsEnableCardStyle = z;
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public final RecyclerView.Adapter onCreateAdapter(PreferenceScreen preferenceScreen) {
        PreferenceGroupAdapter preferenceGroupAdapter = new PreferenceGroupAdapter(preferenceScreen, this.mIsEnableCardStyle, this.mCardStyle);
        this.mGroupAdapter = preferenceGroupAdapter;
        preferenceGroupAdapter.setItemSelectable(this.mItemSelectable);
        this.mGroupAdapter.setExtraHorizontalPadding(this.mExtraHorizontalPadding);
        FrameDecoration frameDecoration = this.mFrameDecoration;
        if (frameDecoration != null) {
            this.mGroupAdapter.setClipPaint(frameDecoration.mPaint, frameDecoration.mMaskPaddingTop, this.mFrameDecoration.mMaskPaddingBottom, this.mFrameDecoration.mMaskPaddingStart, this.mFrameDecoration.mMaskPaddingEnd, this.mFrameDecoration.mCardRadius);
        }
        return this.mGroupAdapter;
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override // miuix.appcompat.app.IFragment
    public boolean onCreatePanelMenu(int i, Menu menu) {
        return false;
    }

    @Override // androidx.preference.PreferenceFragmentCompat
    public RecyclerView onCreateRecyclerView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ActionBarOverlayLayout actionBarOverlayLayout;
        RecyclerView recyclerView = (RecyclerView) layoutInflater.inflate(R.layout.miuix_preference_recyclerview, viewGroup, false);
        if (recyclerView instanceof miuix.recyclerview.widget.RecyclerView) {
            ((miuix.recyclerview.widget.RecyclerView) recyclerView).setSpringEnabled(false);
        }
        recyclerView.setLayoutManager(onCreateLayoutManager());
        Context context = recyclerView.getContext();
        int listViewPaddingTop = getListViewPaddingTop();
        int listViewPaddingBottom = getListViewPaddingBottom();
        if (listViewPaddingTop == -1) {
            listViewPaddingTop = recyclerView.getPaddingTop();
        }
        if (listViewPaddingBottom == -1) {
            listViewPaddingBottom = recyclerView.getPaddingBottom();
        }
        this.mListViewPaddingBottom = listViewPaddingBottom;
        recyclerView.setPadding(recyclerView.getPaddingLeft(), listViewPaddingTop, recyclerView.getPaddingRight(), this.mListViewPaddingBottom);
        SmoothCornerHelper.setViewSmoothCornerEnable(recyclerView, true);
        FrameDecoration frameDecoration = new FrameDecoration(this, context, null);
        this.mFrameDecoration = frameDecoration;
        frameDecoration.enableHyperMaterial(this.mEnableHyperMaterial);
        recyclerView.addItemDecoration(this.mFrameDecoration);
        recyclerView.setItemAnimator(new CardDefaultItemAnimator());
        this.mListContainer = viewGroup;
        this.mCacheListContainerMargin = getContentViewMargin(viewGroup);
        this.mListContainer.addOnLayoutChangeListener(new AnonymousClass1());
        if (viewGroup instanceof SpringBackLayout) {
            ((SpringBackLayout) viewGroup).setTarget(recyclerView);
        }
        FragmentActivity activity = getActivity();
        if (activity != null && (actionBarOverlayLayout = (ActionBarOverlayLayout) activity.findViewById(miuix.appcompat.R.id.action_bar_overlay_layout)) != null) {
            actionBarOverlayLayout.setRootSubDecor(false);
        }
        addWindowInsetsListener();
        return recyclerView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context themedContext;
        updateActionBarOverlay();
        setActionBarOverLayoutBg();
        this.mDeviceType = DeviceHelper.detectType(getActivity());
        if (!this.mUserExtraPaddingPolicy) {
            initExtraPaddingPolicy();
        }
        if (this.mExtraPaddingInitEnable && this.mExtraPaddingPolicy != null && (themedContext = getThemedContext()) != null) {
            updateExtraPaddingHorizontal(themedContext, this.mExtraPaddingPolicy, viewGroup != null ? viewGroup.getMeasuredWidth() : 0, viewGroup != null ? viewGroup.getMeasuredHeight() : 0);
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        List list = this.mExtraPaddingObserver;
        if (list != null) {
            list.clear();
        }
        unregisterCoordinateScrollView(this.mListContainer);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.preference.PreferenceManager.OnDisplayPreferenceDialogListener
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragmentNewInstance;
        boolean zOnPreferenceDisplayDialog = getCallbackFragment() instanceof PreferenceFragmentCompat.OnPreferenceDisplayDialogCallback ? ((PreferenceFragmentCompat.OnPreferenceDisplayDialogCallback) getCallbackFragment()).onPreferenceDisplayDialog(this, preference) : false;
        if (!zOnPreferenceDisplayDialog && (getActivity() instanceof PreferenceFragmentCompat.OnPreferenceDisplayDialogCallback)) {
            zOnPreferenceDisplayDialog = ((PreferenceFragmentCompat.OnPreferenceDisplayDialogCallback) getActivity()).onPreferenceDisplayDialog(this, preference);
        }
        if (!zOnPreferenceDisplayDialog && getFragmentManager().findFragmentByTag("androidx.preference.PreferenceFragment.DIALOG") == null) {
            if (preference instanceof EditTextPreference) {
                dialogFragmentNewInstance = EditTextPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else if (preference instanceof ListPreference) {
                dialogFragmentNewInstance = ListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            } else {
                if (!(preference instanceof MultiSelectListPreference)) {
                    throw new IllegalArgumentException("Cannot display dialog for an unknown Preference type: " + preference.getClass().getSimpleName() + ". Make sure to implement onPreferenceDisplayDialog() to handle displaying a custom dialog for this Preference.");
                }
                dialogFragmentNewInstance = MultiSelectListPreferenceDialogFragmentCompat.newInstance(preference.getKey());
            }
            dialogFragmentNewInstance.setTargetFragment(this, 0);
            dialogFragmentNewInstance.show(getFragmentManager(), "androidx.preference.PreferenceFragment.DIALOG");
        }
    }

    @Override // miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
    }

    @Override // miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    @Override // miuix.appcompat.app.IFragment
    public void onOptionsMenuViewAdded(Menu menu, Menu menu2) {
    }

    @Override // miuix.appcompat.app.IFragment
    public void onPanelClosed(int i, Menu menu) {
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(Preference preference) {
        PreferenceGroupAdapter preferenceGroupAdapter;
        if (this.mItemSelectable && (preferenceGroupAdapter = this.mGroupAdapter) != null) {
            preferenceGroupAdapter.setSelectedPreference(preference);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // miuix.appcompat.app.IFragment
    public void onPreparePanel(int i, View view, Menu menu) {
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        stopHighlight();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mIsOverlayMode && !isEmbeddedFragment()) {
            registerCoordinateScrollView(this.mListContainer);
            getListView().setClipToPadding(false);
            Rect contentInset = getContentInset();
            if (contentInset != null && !contentInset.isEmpty()) {
                onContentInsetChanged(contentInset);
            }
        }
        RecyclerView listView = getListView();
        this.mHyperScrollBar = (HyperScrollBar) view.findViewById(R.id.scrollBar);
        View viewFindViewById = view.findViewById(android.R.id.list_container);
        if (!(viewFindViewById instanceof SpringBackLayout) || this.mHyperScrollBar == null || listView == null) {
            return;
        }
        this.mRecyclerVIewScrollBarAdapter = new RecyclerViewScrollBarAdapter(listView);
        this.mPreferenceFragmentScrollBarAdapter = new PreferenceFragmentScrollBarAdapter(getActionBar(), listView, (SpringBackLayout) viewFindViewById);
        if (this.mHyperScrollBar != null) {
            if (isActionBarOverlay()) {
                this.mHyperScrollBar.attachToScrollableView(this.mPreferenceFragmentScrollBarAdapter);
            } else {
                this.mHyperScrollBar.attachToScrollableView(this.mRecyclerVIewScrollBarAdapter);
            }
            this.mHyperScrollBar.setScrollVisible(this.mScrollBarVisible);
            if (isActionBarOverlay() && this.mScrollBarVisible) {
                this.mHyperScrollBar.postDelayed(new Runnable() { // from class: miuix.preference.PreferenceFragment.3
                    @Override // java.lang.Runnable
                    public void run() {
                        ActionBar actionBar = PreferenceFragment.this.getActionBar();
                        if (actionBar != null) {
                            int height = actionBar.getHeight();
                            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PreferenceFragment.this.mHyperScrollBar.getLayoutParams();
                            layoutParams.setMargins(layoutParams.leftMargin, height, layoutParams.rightMargin, layoutParams.bottomMargin);
                            PreferenceFragment.this.mHyperScrollBar.setLayoutParams(layoutParams);
                        }
                    }
                }, 500L);
            }
        }
    }

    @Override // miuix.appcompat.app.IFragment
    public void onViewInflated(View view, Bundle bundle) {
    }

    public void registerCoordinateScrollView(View view) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.registerCoordinateScrollView(view);
        }
    }

    public final void setActionBarOverLayoutBg() {
        FragmentActivity activity;
        Drawable drawableResolveDrawable;
        if (!this.mIsEnableCardStyle || (activity = getActivity()) == null) {
            return;
        }
        Window window = activity.getWindow();
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) activity.findViewById(miuix.appcompat.R.id.action_bar_overlay_layout);
        Drawable drawableResolveDrawable2 = AttributeResolver.resolveDrawable(getContext(), R.attr.preferenceCardPageBackground);
        if (!isInFloatingWindowMode() && (drawableResolveDrawable = AttributeResolver.resolveDrawable(getContext(), R.attr.preferenceCardPageNoFloatingBackground)) != null) {
            drawableResolveDrawable2 = drawableResolveDrawable;
        }
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setBackground(drawableResolveDrawable2);
        } else {
            View viewFindViewById = window.getDecorView().findViewById(android.R.id.content);
            if (viewFindViewById == null || viewFindViewById.getParent() == null || !(viewFindViewById.getParent() instanceof View)) {
                window.setBackgroundDrawable(drawableResolveDrawable2);
            } else {
                ((View) viewFindViewById.getParent()).setBackground(drawableResolveDrawable2);
            }
        }
        if (EnvStateManager.isFullScreenGestureMode(getContext())) {
            return;
        }
        int i = window.getAttributes().flags;
        boolean z = (Integer.MIN_VALUE & i) != 0;
        boolean z2 = (i & 134217728) != 0;
        if (z && !z2 && (drawableResolveDrawable2 instanceof ColorDrawable)) {
            window.setNavigationBarColor(((ColorDrawable) drawableResolveDrawable2).getColor());
        }
    }

    @Override // miuix.container.ExtraPaddingObserver
    public boolean setExtraHorizontalPadding(int i) {
        if (this.mExtraHorizontalPadding == i) {
            return false;
        }
        this.mExtraHorizontalPadding = i;
        return true;
    }

    public void setExtraHorizontalPaddingEnable(boolean z) {
        this.mExtraPaddingEnable = z;
        ExtraPaddingPolicy extraPaddingPolicy = this.mExtraPaddingPolicy;
        if (extraPaddingPolicy != null) {
            extraPaddingPolicy.setEnable(z);
        }
    }

    public void setExtraHorizontalPaddingInitEnable(boolean z) {
        this.mExtraPaddingInitEnable = z;
    }

    public final void setRecyclerViewPadding(Insets insets) {
        View view = getView();
        RecyclerView listView = getListView();
        if (view == null || listView == null) {
            return;
        }
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            ActionBarImpl actionBarImpl = (ActionBarImpl) actionBar;
            if (actionBarImpl.getContentView() != null) {
                Rect rect = new Rect();
                Rect rect2 = new Rect();
                actionBarImpl.getContentView().getGlobalVisibleRect(rect);
                view.getGlobalVisibleRect(rect2);
                listView.setPadding(listView.getPaddingLeft(), listView.getPaddingTop(), listView.getPaddingRight(), Math.max(0, insets.bottom - Math.max(0, rect.bottom - rect2.bottom)) + this.mListViewPaddingBottom);
                return;
            }
        }
        listView.setPadding(listView.getPaddingLeft(), listView.getPaddingTop(), listView.getPaddingRight(), insets.bottom + this.mListViewPaddingBottom);
    }

    public void stopHighlight() {
        PreferenceGroupAdapter preferenceGroupAdapter = this.mGroupAdapter;
        if (preferenceGroupAdapter != null) {
            preferenceGroupAdapter.stopHighlight();
        }
    }

    public void unregisterCoordinateScrollView(View view) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.unregisterCoordinateScrollView(view);
        }
    }

    public final void updateActionBarOverlay() {
        IFragment iFragment;
        Fragment parentFragment = getParentFragment();
        while (true) {
            if (parentFragment == null) {
                iFragment = null;
                break;
            }
            if (parentFragment instanceof IFragment) {
                iFragment = (IFragment) parentFragment;
                if (iFragment.hasActionBar()) {
                    break;
                }
            }
            parentFragment = parentFragment.getParentFragment();
        }
        Context themedContext = iFragment != null ? iFragment.getThemedContext() : getActivity();
        if (themedContext != null) {
            this.mIsOverlayMode = AttributeResolver.resolveBoolean(themedContext, R.attr.windowActionBarOverlay, false);
        }
    }
}
