package com.miui.aod.components.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.systemui.miui.statusbar.BatteryMeterIconView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.category.MamlCategoryInfo;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.category.SunCategoryInfo;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.widget.ITimeUpdate;
import com.miui.aod.linkagestyle.LinkageStyleClockView;
import com.miui.aod.notification.DiffDispatch;
import com.miui.aod.notification.NotificationUtils;
import com.miui.aod.other.step.StepBean;
import com.miui.aod.other.step.StepLiveData;
import com.miui.aod.other.step.StepUtils;
import com.miui.aod.other.step.StepWidget;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.theme.MamlViewContainer;
import com.miui.aod.util.ClockColorManager;
import com.miui.aod.util.ImageUtils;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.GradientLinearLayout;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.ILinkageClock;
import com.miui.aod.widget.OnClockColorChangeListener;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class AodContainerView extends FrameLayout implements ITimeUpdate, StepLiveData.IStepComponent, OnClockColorChangeListener {
    IAodClock mAodClock;
    private ViewGroup mAodContent;
    private ViewGroup mBatteryAndStepContainer;
    private View mBatteryWidget;
    private ViewGroup mClockContent;
    private View mFocusNotificationWidget;
    private ImageView mLabelIv;
    private LayoutInflater mLayoutInflater;
    private boolean mNeedDrawEdge;
    private View mNotificationWidget;
    private Paint mPaint;
    private final RectF mRectF;
    public int mSize;
    private StepWidget mStepWidget;
    public StyleInfo mStyleInfo;
    private final float sCornerRadius;

    public AodContainerView(Context context) {
        super(context);
        this.mAodClock = null;
        this.sCornerRadius = getResources().getDimensionPixelSize(R.dimen.aod_style_bg_radius);
        this.mRectF = new RectF();
    }

    public AodContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAodClock = null;
        this.sCornerRadius = getResources().getDimensionPixelSize(R.dimen.aod_style_bg_radius);
        this.mRectF = new RectF();
    }

    private void clearWidget() {
        this.mBatteryAndStepContainer = null;
        this.mBatteryWidget = null;
        this.mStepWidget = null;
        this.mNotificationWidget = null;
    }

    private int getGravityForLayout(boolean z, boolean z2, int i) {
        if (z2) {
            if (z) {
                return 21;
            }
        } else if (z) {
            return 19;
        }
        return i;
    }

    private int getMarginForLayout(boolean z) {
        if (z) {
            return getResources().getDimensionPixelOffset(R.dimen.icon_container_margin_start);
        }
        return 0;
    }

    private void hideViewById(View view, int i) {
        View viewFindViewById;
        if (view == null || (viewFindViewById = view.findViewById(i)) == null) {
            return;
        }
        viewFindViewById.setVisibility(8);
    }

    private void inflateBattery(StyleInfo styleInfo) {
        if (styleInfo instanceof MamlCategoryInfo) {
            return;
        }
        updateBatteryWidget();
        if (this.mBatteryWidget == null) {
            inflateBatteryAndStepContainerIfNeeded(styleInfo);
            this.mBatteryWidget = this.mBatteryAndStepContainer.findViewById(R.id.battery_container);
        }
    }

    private void inflateBatteryAndStepContainerIfNeeded(StyleInfo styleInfo) {
        int i;
        View viewFindViewById;
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.battery_step_container);
        this.mBatteryAndStepContainer = viewGroup;
        if (viewGroup == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.battery_step_container_vs);
            if (viewStub != null) {
                viewStub.inflate();
            }
            this.mBatteryAndStepContainer = (ViewGroup) findViewById(R.id.battery_step_container);
            if (!styleInfo.isBatterySwitchOn() && (viewFindViewById = findViewById(R.id.battery_container)) != null) {
                viewFindViewById.setVisibility(8);
            }
        }
        ViewGroup viewGroup2 = this.mBatteryAndStepContainer;
        if (viewGroup2 != null) {
            if (viewGroup2 instanceof GradientLinearLayout) {
                if (styleInfo.getBatteryMask() > 0) {
                    ((GradientLinearLayout) this.mBatteryAndStepContainer).setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), styleInfo.getBatteryMask()));
                } else {
                    ((GradientLinearLayout) this.mBatteryAndStepContainer).setGradientOverlayDrawable(null);
                }
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBatteryAndStepContainer.getLayoutParams();
            if (styleInfo.isLeftGravity()) {
                i = 3;
            } else {
                i = styleInfo.isRightGravity() ? 5 : 1;
            }
            if (marginLayoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) marginLayoutParams).gravity = i;
            } else if (marginLayoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) marginLayoutParams).gravity = i;
            }
            this.mBatteryAndStepContainer.setLayoutParams(marginLayoutParams);
        }
    }

    private void inflateNotificationIconsIfNeeded() {
        View view;
        if (!this.mStyleInfo.needInflateNotification()) {
            this.mNotificationWidget = this.mClockContent.findViewById(R.id.icons);
            this.mFocusNotificationWidget = this.mClockContent.findViewById(R.id.fn_container);
            if (this.mNotificationWidget == null) {
                this.mNotificationWidget = this.mClockContent.findViewById(R.id.icons_container);
                return;
            }
            return;
        }
        View viewFindViewById = this.mClockContent.findViewById(R.id.icons_container);
        this.mNotificationWidget = viewFindViewById;
        if (viewFindViewById == null) {
            ViewStub viewStub = (ViewStub) findViewById(R.id.notification_icons_vs);
            this.mNotificationWidget = viewStub != null ? viewStub.inflate().findViewById(R.id.icons) : findViewById(R.id.icons);
        }
        this.mFocusNotificationWidget = this.mClockContent.findViewById(R.id.fn_container);
        if (Utils.isAodProcess() || (view = this.mFocusNotificationWidget) == null) {
            return;
        }
        DiffDispatch.initAnimation((LinearLayout) view);
    }

    private void inflateStepWidget() {
        inflateBatteryAndStepContainerIfNeeded(this.mStyleInfo);
        ViewStub viewStub = (ViewStub) findViewById(R.id.step_component_view_stub);
        if (viewStub != null) {
            this.mStepWidget = (StepWidget) viewStub.inflate();
        }
    }

    private IAodClock inflateView(boolean z, StyleInfo styleInfo) {
        this.mClockContent.removeAllViews();
        styleInfo.dealContentLayoutParams(this.mClockContent.getLayoutParams(), this.mSize);
        IAodClock iAodClockCreateAodClock = styleInfo.createAodClock(z, this.mSize);
        this.mAodClock = iAodClockCreateAodClock;
        iAodClockCreateAodClock.setTimeZone(TimeZone.getDefault());
        if (z) {
            String string = MiuiSettingsUtils.System.getString(getContext().getContentResolver(), "resident_timezone");
            Log.d("AodContainerView", "timeZoneId:" + string);
            if (string != null) {
                this.mAodClock.setTimeZone2(TimeZone.getTimeZone(string));
            }
        }
        this.mLayoutInflater.inflate(this.mAodClock.getLayoutResource(styleInfo), this.mClockContent, true);
        this.mAodClock.bindView(this.mClockContent);
        this.mAodClock.update(styleInfo, this.mSize);
        return this.mAodClock;
    }

    private void setBg(StyleInfo styleInfo) {
        this.mAodClock.setClockMask(styleInfo.getClockColor(), styleInfo.getClockMask());
        styleInfo.setBg(this, this.mSize);
    }

    private void setNotificationIconDrawable(ImageView imageView, Drawable drawable) {
        if (imageView == null) {
            return;
        }
        if (drawable == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageDrawable(drawable);
        }
    }

    private void setupBatteryViews(StyleInfo styleInfo) {
        ImageView imageView = (ImageView) findViewById(R.id.aod_battery_icon);
        if (imageView instanceof BatteryMeterIconView) {
            ((BatteryMeterIconView) imageView).clearTint();
        }
        boolean zIsBatterySwitchOn = styleInfo.isBatterySwitchOn();
        boolean zNeedInflateBattery = styleInfo.needInflateBattery(getContext());
        if (zIsBatterySwitchOn && zNeedInflateBattery) {
            inflateBattery(styleInfo);
            showBatteryIcon(true);
            return;
        }
        int i = 8;
        if (styleInfo instanceof TextStyleCategoryInfo) {
            View viewFindViewById = findViewById(R.id.aod_battery_icon);
            if (viewFindViewById != null) {
                viewFindViewById.setAlpha(0.6f);
            }
            View viewFindViewById2 = findViewById(R.id.aod_battery_digital);
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(8);
            }
        }
        updateBatteryWidget();
        View view = this.mBatteryWidget;
        if (view != null) {
            if (zIsBatterySwitchOn) {
                i = 0;
            } else if (this.mStyleInfo instanceof SunCategoryInfo) {
                i = 4;
            }
            view.setVisibility(i);
        }
    }

    private void setupLabel() {
        int i = this.mSize;
        if (i == 2 || i == 6) {
            if (TextUtils.isEmpty(this.mStyleInfo.getLabel()) || this.mLabelIv == null || !AodDrawables.sLabelDrawables.containsKey(this.mStyleInfo.getLabel())) {
                ImageView imageView = this.mLabelIv;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    return;
                }
                return;
            }
            int iIntValue = ((Integer) AodDrawables.sLabelDrawables.get(this.mStyleInfo.getLabel())).intValue();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mLabelIv.getLayoutParams();
            layoutParams.width = (int) (getResources().getDimensionPixelSize(R.dimen.label_width) * CategoryConstants.SSMALL_RADIO);
            layoutParams.height = (int) (getResources().getDimensionPixelSize(R.dimen.label_height) * CategoryConstants.SSMALL_RADIO);
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.label_margin);
            layoutParams.topMargin = dimensionPixelSize;
            layoutParams.rightMargin = dimensionPixelSize;
            this.mLabelIv.setImageDrawable(null);
            this.mLabelIv.setVisibility(0);
            ImageUtils.loadIntoImage(this.mLabelIv, iIntValue);
        }
    }

    private void setupNotificationIcons(StyleInfo styleInfo) {
        int i;
        boolean zIsNotificationSwitchOn = styleInfo.isNotificationSwitchOn();
        inflateNotificationIconsIfNeeded();
        if (!zIsNotificationSwitchOn) {
            View view = this.mNotificationWidget;
            if (view != null) {
                view.setVisibility(8);
            } else {
                View viewFindViewById = findViewById(R.id.icons_container);
                this.mNotificationWidget = viewFindViewById;
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(8);
                }
            }
            View view2 = this.mFocusNotificationWidget;
            if (view2 != null) {
                view2.setVisibility(8);
                return;
            }
            return;
        }
        View view3 = this.mNotificationWidget;
        if (view3 == null) {
            return;
        }
        view3.setVisibility(0);
        if (!styleInfo.dealNotificationWidgetLayoutParams()) {
            int i2 = 17;
            if ((this.mNotificationWidget.getLayoutParams() instanceof LinearLayout.LayoutParams) && !(styleInfo instanceof LinkageClockCategoryInfo)) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mNotificationWidget.getLayoutParams();
                layoutParams.topMargin = ((styleInfo instanceof SmartCoverStyleInfo) || layoutParams.topMargin != 0) ? layoutParams.topMargin : styleInfo.getTopMargin(getContext());
                layoutParams.leftMargin = 0;
                layoutParams.rightMargin = 0;
                ViewParent parent = this.mNotificationWidget.getParent();
                if (parent instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) parent;
                    int paddingTop = viewGroup.getPaddingTop();
                    if (styleInfo.isLeftGravity()) {
                        viewGroup.setPadding(0, paddingTop, paddingTop, paddingTop);
                        i = 8388611;
                    } else if (styleInfo.isRightGravity()) {
                        viewGroup.setPadding(paddingTop, paddingTop, 0, paddingTop);
                        i = 8388613;
                    } else {
                        viewGroup.setPadding(paddingTop, paddingTop, paddingTop, paddingTop);
                        i = 17;
                    }
                    layoutParams.gravity = i;
                    if (viewGroup.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                        layoutParams2.gravity = i;
                        viewGroup.setLayoutParams(layoutParams2);
                    }
                }
                this.mNotificationWidget.setLayoutParams(layoutParams);
            }
            if ((this.mNotificationWidget.getLayoutParams() instanceof FrameLayout.LayoutParams) && !(styleInfo instanceof LinkageClockCategoryInfo)) {
                ViewParent parent2 = this.mNotificationWidget.getParent();
                if (parent2 instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) parent2;
                    int paddingLeft = viewGroup2.getPaddingLeft();
                    int paddingRight = viewGroup2.getPaddingRight();
                    int paddingTop2 = viewGroup2.getPaddingTop();
                    int paddingBottom = viewGroup2.getPaddingBottom();
                    if (styleInfo.isLeftGravity()) {
                        viewGroup2.setPadding(0, paddingTop2, paddingRight, paddingBottom);
                        i2 = 8388611;
                    } else if (styleInfo.isRightGravity()) {
                        viewGroup2.setPadding(paddingLeft, paddingTop2, 0, paddingBottom);
                        i2 = 8388613;
                    }
                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) this.mNotificationWidget.getLayoutParams();
                    layoutParams3.gravity = i2;
                    this.mNotificationWidget.setLayoutParams(layoutParams3);
                    if (viewGroup2.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) viewGroup2.getLayoutParams();
                        layoutParams4.gravity = i2;
                        viewGroup2.setLayoutParams(layoutParams4);
                    }
                }
            }
        }
        int i3 = this.mSize;
        if (i3 == 3 || i3 == 2 || i3 == 4) {
            return;
        }
        if (AODSettings.notificationOneIconMode(this.mStyleInfo)) {
            ((ImageView) this.mNotificationWidget.findViewById(R.id.first)).setVisibility(0);
            return;
        }
        setNotificationIconDrawable((ImageView) this.mNotificationWidget.findViewById(R.id.first), NotificationUtils.getAppIconByPackageName(getContext(), "com.android.contacts", "com.android.contacts.activities.TwelveKeyDialer"));
        setNotificationIconDrawable((ImageView) this.mNotificationWidget.findViewById(R.id.second), NotificationUtils.getAppIconByPackageName(getContext(), "com.android.mms", ""));
        setNotificationIconDrawable((ImageView) this.mNotificationWidget.findViewById(R.id.third), NotificationUtils.getAppIconByPackageName(getContext(), NotificationUtils.MAIL_ICON_PKG, ""));
        ((ImageView) this.mNotificationWidget.findViewById(R.id.forth)).setVisibility(8);
        ((ImageView) this.mNotificationWidget.findViewById(R.id.more_notification_icon)).setVisibility(8);
    }

    private void setupStepWidget(StyleInfo styleInfo) {
        if (styleInfo instanceof TextStyleCategoryInfo) {
            return;
        }
        showStepWidget(styleInfo.isStepComponentSwitchOn());
    }

    private void updateAodContentLayoutParams(StyleInfo styleInfo) {
        ViewGroup viewGroup = this.mAodContent;
        if (viewGroup != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParams.gravity = styleInfo.getClockGravity() | 48;
            styleInfo.dealAodContentContainerLayoutParams(layoutParams, this.mSize);
            this.mAodContent.setLayoutParams(layoutParams);
        }
    }

    private void updateBatteryWidget() {
        if (this.mBatteryWidget == null) {
            StyleInfo styleInfo = this.mStyleInfo;
            if ((styleInfo instanceof LinkageClockCategoryInfo) && !(styleInfo instanceof FlipLinkageClockCategoryInfo)) {
                this.mBatteryWidget = ((LinkageStyleClockView) findViewById(R.id.linkage_clock_container)).getSmartFrameBatterView();
            }
        }
        if (this.mBatteryWidget == null) {
            this.mBatteryWidget = findViewById(R.id.battery_container);
        }
    }

    private void updateClockContentLayoutParams(StyleInfo styleInfo) {
        int clockGravity = styleInfo.getClockGravity();
        boolean z = clockGravity == 3;
        boolean zIsRtl = DeviceUtil.INSTANCE.isRtl(getContext());
        int marginForLayout = getMarginForLayout(z);
        int gravityForLayout = getGravityForLayout(z, zIsRtl, clockGravity);
        ViewGroup viewGroup = this.mClockContent;
        if (viewGroup != null) {
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                updateForLinearLayout((LinearLayout.LayoutParams) layoutParams, zIsRtl, gravityForLayout, marginForLayout);
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                updateForFrameLayout((FrameLayout.LayoutParams) layoutParams, zIsRtl, gravityForLayout, marginForLayout);
            }
            styleInfo.dealClockContentContainerLayoutParams(layoutParams, this.mSize);
            this.mClockContent.setLayoutParams(layoutParams);
            StyleInfo styleInfo2 = this.mStyleInfo;
            if (styleInfo2 == null || !styleInfo2.isForAodNotification()) {
                return;
            }
            ((LinearLayout) this.mClockContent).setGravity(17);
        }
    }

    private void updateForFrameLayout(FrameLayout.LayoutParams layoutParams, boolean z, int i, int i2) {
        layoutParams.gravity = i;
        if (z) {
            layoutParams.rightMargin = i2;
        }
    }

    private void updateForLinearLayout(LinearLayout.LayoutParams layoutParams, boolean z, int i, int i2) {
        layoutParams.gravity = i;
        if (z) {
            layoutParams.rightMargin = i2;
        }
    }

    private void updateTextStylePreAodView() {
        IAodClock iAodClock;
        StyleInfo styleInfo = this.mStyleInfo;
        if (!(styleInfo instanceof TextStyleCategoryInfo) || (iAodClock = this.mAodClock) == null) {
            return;
        }
        iAodClock.update(styleInfo, this.mSize);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mNeedDrawEdge) {
            if (this.mPaint == null) {
                Paint paint = new Paint(1);
                this.mPaint = paint;
                paint.setColor(Color.parseColor("#2effffff"));
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setStrokeWidth(Utils.getImageShowStrokeWidth());
            }
            float strokeWidth = this.mPaint.getStrokeWidth();
            Rect clipBounds = canvas.getClipBounds();
            float f = strokeWidth / 2.0f;
            this.mRectF.set(clipBounds.left + f, clipBounds.top + f, clipBounds.right - f, clipBounds.bottom - f);
            RectF rectF = this.mRectF;
            float f2 = this.sCornerRadius;
            canvas.drawRoundRect(rectF, f2, f2, this.mPaint);
        }
    }

    @Override // com.miui.aod.components.widget.ITimeUpdate
    public void handleUpdateTime(boolean z) {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock != null) {
            iAodClock.updateTime(z);
        }
        updateStep((StepBean) StepLiveData.getInstance().getValue());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        ClockColorManager.INSTANCE.registerClockColorChangeListener(this);
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        ClockColorManager.INSTANCE.unregisterClockColorChangeListener(this);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        setForceDarkAllowed(false);
        this.mLayoutInflater = LayoutInflater.from(getContext());
        this.mAodContent = (ViewGroup) findViewById(R.id.aod_content_container);
        this.mClockContent = (ViewGroup) findViewById(R.id.content);
        this.mLabelIv = (ImageView) findViewById(R.id.label);
    }

    public void releaseDrawable() {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock != null) {
            iAodClock.releaseDrawable();
        }
        ImageView imageView = (ImageView) findViewById(R.id.aod_bg);
        if (imageView != null) {
            imageView.setImageBitmap(null);
            imageView.setImageDrawable(null);
        }
    }

    public void setClockViewVisible(boolean z) {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock instanceof ILinkageClock) {
            ((ILinkageClock) iAodClock).setClockViewVisible(z);
        }
    }

    public void setNeedDrawEdge(boolean z) {
        this.mNeedDrawEdge = z;
        invalidate();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
    }

    public void showBatteryIcon(boolean z) {
        View viewFindViewById;
        int i;
        if (z && this.mStyleInfo.needInflateBattery(getContext())) {
            inflateBattery(this.mStyleInfo);
        }
        updateBatteryWidget();
        View view = this.mBatteryWidget;
        if (view != null) {
            if (z) {
                i = 0;
            } else {
                i = this.mStyleInfo instanceof SunCategoryInfo ? 4 : 8;
            }
            view.setVisibility(i);
        }
        ViewGroup viewGroup = this.mClockContent;
        if (viewGroup != null && (viewFindViewById = viewGroup.findViewById(R.id.maml_view_container)) != null) {
            ((MamlViewContainer) viewFindViewById).setBatteryEnable(z);
        }
        updateTextStylePreAodView();
    }

    public void showDateAndTime(boolean z) {
        if (this.mStyleInfo instanceof TextStyleCategoryInfo) {
            findViewById(R.id.clock_horizontal).setVisibility(z ? 0 : 8);
            findViewById(R.id.date).setVisibility(z ? 0 : 8);
            updateTextStylePreAodView();
        }
    }

    public void showLunarTime(boolean z) {
        View viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.date_lunar);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(z ? 0 : 8);
            IAodClock iAodClock = this.mAodClock;
            if (iAodClock != null) {
                iAodClock.updateTime(DateFormat.is24HourFormat(getContext()));
            }
        }
        ViewGroup viewGroup = this.mClockContent;
        if (viewGroup == null || (viewFindViewById = viewGroup.findViewById(R.id.maml_view_container)) == null) {
            return;
        }
        ((MamlViewContainer) viewFindViewById).setLunarCalenderEnable(z);
    }

    public void showNotificationIcon(boolean z) {
        View viewFindViewById;
        ViewGroup viewGroup = this.mClockContent;
        if (viewGroup != null && (viewFindViewById = viewGroup.findViewById(R.id.maml_view_container)) != null) {
            ((MamlViewContainer) viewFindViewById).setNotificationEnable(z);
        }
        inflateNotificationIconsIfNeeded();
        setupNotificationIcons(this.mStyleInfo);
        View view = this.mNotificationWidget;
        if (view != null) {
            view.setVisibility(z ? 0 : 8);
        } else {
            View viewFindViewById2 = findViewById(R.id.icons_container);
            this.mNotificationWidget = viewFindViewById2;
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(8);
            }
        }
        View viewFindViewById3 = findViewById(R.id.fn_container);
        this.mFocusNotificationWidget = viewFindViewById3;
        if (viewFindViewById3 != null) {
            viewFindViewById3.setVisibility(8);
        }
        updateTextStylePreAodView();
    }

    public void showStepWidget(boolean z) {
        if (!z || !StepUtils.supportStepComponent()) {
            StepWidget stepWidget = this.mStepWidget;
            if (stepWidget != null) {
                stepWidget.setVisibility(8);
                return;
            }
            return;
        }
        inflateStepWidget();
        StepWidget stepWidget2 = this.mStepWidget;
        if (stepWidget2 != null) {
            stepWidget2.setVisibility(0);
            this.mStepWidget.updateStep((StepBean) StepLiveData.getInstance().getValue());
        }
    }

    public IAodClock update(StyleInfo styleInfo, int i) {
        clearWidget();
        this.mSize = i;
        this.mStyleInfo = styleInfo;
        boolean zIsDualClock = AODSettings.isDualClock(getContext());
        IAodClock iAodClockInflateView = inflateView(zIsDualClock, styleInfo);
        styleInfo.dealAodContainerLayoutParam(getLayoutParams(), i, getContext(), zIsDualClock);
        int i2 = this.mSize;
        if (i2 == 2 || i2 == 4 || i2 == 6) {
            showBatteryIcon(false);
            showStepWidget(false);
            showNotificationIcon(false);
            hideViewById(this.mClockContent, R.id.date);
            hideViewById(this.mClockContent, R.id.date_lunar);
        } else {
            setupBatteryViews(styleInfo);
            setupStepWidget(styleInfo);
            setupNotificationIcons(styleInfo);
            IAodClock iAodClock = this.mAodClock;
            if (iAodClock != null) {
                iAodClock.onInflateBatteryComplete();
            }
        }
        if (!this.mStyleInfo.isLunarSwitchOn(getContext())) {
            hideViewById(this.mClockContent, R.id.date_lunar);
        }
        setBg(styleInfo);
        updateAodContentLayoutParams(styleInfo);
        updateClockContentLayoutParams(styleInfo);
        setupLabel();
        iAodClockInflateView.onInflateComplete();
        return iAodClockInflateView;
    }

    public void updateAodView() {
        StyleInfo styleInfo;
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock == null || (styleInfo = this.mStyleInfo) == null) {
            return;
        }
        iAodClock.update(styleInfo, this.mSize);
    }

    @Override // com.miui.aod.widget.OnClockColorChangeListener
    public void updateClockColor(int i) {
        IAodClock iAodClock = this.mAodClock;
        if (iAodClock == null || !(iAodClock instanceof OnClockColorChangeListener)) {
            return;
        }
        ((OnClockColorChangeListener) iAodClock).updateClockColor(this.mSize);
    }

    public void updateStep(StepBean stepBean) {
        StepWidget stepWidget = this.mStepWidget;
        if (stepWidget != null) {
            stepWidget.updateStep(stepBean);
        }
    }
}
