package com.miui.clock.tiny.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.utils.BatteryController;
import com.miui.clock.tiny.utils.BatteryStatus;
import com.miui.clock.tiny.utils.DeviceConfig;
import com.miui.clock.tiny.utils.MiuiBlurUtils;

/* JADX INFO: loaded from: classes.dex */
public class BatteryView extends FrameLayout implements BatteryController.BatteryStateChangeCallback {
    public ImageView mBatteryCharging;
    public VectorDrawable mBatteryChargingDrawable;
    public int mBatteryColor;
    public BatteryController mBatteryController;
    public ImageView mBatteryInner;
    public ClipDrawable mBatteryInnerClipDrawable;
    public VectorDrawable mBatteryInnerDrawable;
    public ImageView mBatteryNormalOuter;
    public VectorDrawable mBatteryOuterDrawable;
    public ImageView mBatteryQuickCharging;
    public VectorDrawable mBatteryQuickChargingDrawable;
    public int mBlendColor;
    public boolean mCharging;
    public int mLevel;
    public boolean mLow;
    public boolean mNeedShowNormal;
    public int mOriginColor;
    public boolean mQuickCharging;
    public float mScale;
    public TextView mTextBatteryLevel;
    public boolean mTextDark;
    public boolean mUseBlur;

    public static /* synthetic */ void $r8$lambda$8KIlrk7aiI8W0TmWd_W_YPfcYww(BatteryView batteryView, float f) {
        batteryView.setPivotX(batteryView.getWidth());
        batteryView.setPivotY(0.0f);
        batteryView.setScaleX(f);
        batteryView.setScaleY(f);
    }

    public BatteryView(Context context) {
        super(context);
        this.mBatteryColor = -16777216;
        this.mUseBlur = false;
        this.mNeedShowNormal = false;
        this.mScale = 1.0f;
    }

    public BatteryView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBatteryColor = -16777216;
        this.mUseBlur = false;
        this.mNeedShowNormal = false;
        this.mScale = 1.0f;
        this.mBatteryController = BatteryController.getInstance(getContext());
    }

    public BatteryView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBatteryColor = -16777216;
        this.mUseBlur = false;
        this.mNeedShowNormal = false;
        this.mScale = 1.0f;
    }

    public void clearBatteryViewBlendColor() {
        MiuiBlurUtils.clearMemberBlendColor(this.mTextBatteryLevel);
        MiuiBlurUtils.clearMemberBlendColor(this.mBatteryNormalOuter);
        MiuiBlurUtils.clearMemberBlendColor(this.mBatteryInner);
        MiuiBlurUtils.clearMemberBlendColor(this.mBatteryCharging);
        MiuiBlurUtils.clearMemberBlendColor(this.mBatteryQuickCharging);
    }

    public void clearBatteryViewPassBlur() {
        MiuiBlurUtils.clearContainerPassBlur(this.mTextBatteryLevel);
        MiuiBlurUtils.clearContainerPassBlur(this.mBatteryNormalOuter);
        MiuiBlurUtils.clearContainerPassBlur(this.mBatteryInner);
        MiuiBlurUtils.clearContainerPassBlur(this.mBatteryCharging);
        MiuiBlurUtils.clearContainerPassBlur(this.mBatteryQuickCharging);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        BatteryController batteryController = this.mBatteryController;
        if (batteryController == null) {
            return;
        }
        batteryController.addCallback(this);
    }

    @Override // com.miui.clock.tiny.utils.BatteryController.BatteryStateChangeCallback
    public void onBatteryLevelChanged(BatteryStatus batteryStatus) {
        if (this.mNeedShowNormal) {
            return;
        }
        int level = batteryStatus.getLevel();
        if (level != this.mLevel) {
            this.mLevel = level;
            this.mLow = level <= 19;
        }
        setBatteryColor(this.mBatteryColor);
        setBatteryLevel(level);
    }

    @Override // com.miui.clock.tiny.utils.BatteryController.BatteryStateChangeCallback
    public void onChargeStateChanged(boolean z, boolean z2) {
        if (this.mNeedShowNormal) {
            setContentDescription(getContext().getString(R.string.tiny_accessibility_kg_battery_level));
            return;
        }
        if (this.mCharging != z || this.mQuickCharging != z2) {
            this.mCharging = z;
            this.mQuickCharging = z2;
        }
        updateBatteryClipDrawable();
        this.mBatteryInnerClipDrawable.setLevel(this.mLevel * 100);
        this.mBatteryInner.setBackground(this.mBatteryInnerClipDrawable);
        setBatteryColor(this.mBatteryColor);
        if (this.mCharging) {
            this.mBatteryNormalOuter.setVisibility(4);
            if (this.mQuickCharging) {
                this.mBatteryCharging.setVisibility(4);
                this.mBatteryQuickCharging.setVisibility(0);
            } else {
                this.mBatteryQuickCharging.setVisibility(4);
                this.mBatteryCharging.setVisibility(0);
            }
        } else {
            this.mBatteryCharging.setVisibility(4);
            this.mBatteryQuickCharging.setVisibility(4);
            this.mBatteryNormalOuter.setVisibility(0);
        }
        setViewBlur(this.mTextDark, this.mBlendColor, this.mOriginColor);
        setContentDescription(getResources().getQuantityString(this.mCharging ? R.plurals.tiny_accessibility_battery_level_charging : R.plurals.tiny_accessibility_battery_level, 1, Integer.valueOf(this.mLevel)));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BatteryController batteryController = this.mBatteryController;
        if (batteryController == null) {
            return;
        }
        batteryController.removeCallback(this);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBatteryNormalOuter = (ImageView) findViewById(R.id.tiny_battery_normal_outer);
        this.mTextBatteryLevel = (TextView) findViewById(R.id.battery_text);
        this.mBatteryInner = (ImageView) findViewById(R.id.tiny_battery_inner);
        this.mBatteryCharging = (ImageView) findViewById(R.id.tiny_battery_charging);
        this.mBatteryQuickCharging = (ImageView) findViewById(R.id.tiny_battery_quick_charging);
        this.mBatteryInnerDrawable = (VectorDrawable) this.mBatteryInner.getBackground();
        this.mBatteryInnerClipDrawable = new ClipDrawable(ContextCompat.getDrawable(getContext(), R.drawable.tiny_battery_inner), 3, 1);
        this.mBatteryOuterDrawable = (VectorDrawable) this.mBatteryNormalOuter.getDrawable();
        this.mBatteryChargingDrawable = (VectorDrawable) this.mBatteryCharging.getDrawable();
        this.mBatteryQuickChargingDrawable = (VectorDrawable) this.mBatteryQuickCharging.getDrawable();
    }

    public void setBatteryColor(int i) {
        this.mBatteryColor = i;
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i, mode);
        this.mBatteryInnerDrawable.setColorFilter(porterDuffColorFilter);
        this.mBatteryInnerClipDrawable.setColorFilter(porterDuffColorFilter);
        this.mBatteryOuterDrawable.setTint(i);
        this.mBatteryChargingDrawable.setTint(i);
        this.mBatteryQuickChargingDrawable.setTint(i);
        this.mTextBatteryLevel.setTextColor(i);
        if (this.mLow && !this.mCharging) {
            this.mBatteryInnerClipDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.tiny_battery_low), mode));
        }
        if (this.mCharging) {
            this.mBatteryInnerClipDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.tiny_battery_charging_color), mode));
        }
    }

    public void setBatteryLevel(int i) {
        this.mTextBatteryLevel.setText(i + "%");
        onChargeStateChanged(this.mCharging, this.mQuickCharging);
    }

    public void setNeedBlur(boolean z) {
        this.mUseBlur = z;
    }

    public void setNeedShowNormal(boolean z) {
        this.mNeedShowNormal = z;
        if (z) {
            this.mBatteryNormalOuter.setVisibility(0);
            this.mBatteryInner.setVisibility(0);
            this.mBatteryInner.setBackground(this.mBatteryInnerDrawable);
            this.mBatteryCharging.setVisibility(8);
            this.mBatteryQuickCharging.setVisibility(8);
        }
    }

    public void setScale(final float f) {
        this.mScale = f;
        if (f == 1.0f) {
            return;
        }
        post(new Runnable() { // from class: com.miui.clock.tiny.widget.BatteryView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BatteryView.$r8$lambda$8KIlrk7aiI8W0TmWd_W_YPfcYww(this.f$0, f);
            }
        });
    }

    public void setViewBlur(boolean z, int i, int i2) {
        if (this.mUseBlur && DeviceConfig.supportBackgroundBlur(getContext())) {
            this.mTextDark = z;
            this.mBlendColor = i;
            this.mOriginColor = i2;
            MiuiBlurUtils.setMemberBlendColors(this.mBatteryNormalOuter, z, i, i2);
            MiuiBlurUtils.setMemberBlendColors(this.mTextBatteryLevel, z, i, i2);
            MiuiBlurUtils.setMemberBlendColors(this.mBatteryInner, z, i, i2);
            MiuiBlurUtils.setMemberBlendColors(this.mBatteryCharging, z, i, i2);
            MiuiBlurUtils.setMemberBlendColors(this.mBatteryQuickCharging, z, i, i2);
            if (this.mCharging || this.mLow) {
                MiuiBlurUtils.setMiViewBlurMode(this.mBatteryInner, 0);
            } else {
                MiuiBlurUtils.setMemberBlendColors(this.mBatteryInner, z, i, i2);
            }
        }
    }

    @Override // android.view.View
    public String toString() {
        return "BatteryView{mBatteryColor=" + this.mBatteryColor + ", mLevel=" + this.mLevel + ", mCharging=" + this.mCharging + ", mQuickCharging=" + this.mQuickCharging + ", mLow=" + this.mLow + ", mUseBlur=" + this.mUseBlur + ", mTextDark=" + this.mTextDark + ", mBlendColor=" + this.mBlendColor + ", mOriginColor=" + this.mOriginColor + ", mNeedShowNormal=" + this.mNeedShowNormal + ", mScale=" + this.mScale + '}';
    }

    public final void updateBatteryClipDrawable() {
        Drawable drawable;
        if (this.mCharging) {
            drawable = this.mQuickCharging ? ContextCompat.getDrawable(getContext(), R.drawable.tiny_battery_quick_charging_inner) : ContextCompat.getDrawable(getContext(), R.drawable.tiny_battery_normal_charging_inner);
        } else {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.tiny_battery_inner);
        }
        this.mBatteryInnerClipDrawable = new ClipDrawable(drawable, 3, 1);
    }
}
