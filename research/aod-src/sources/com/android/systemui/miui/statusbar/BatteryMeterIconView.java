package com.android.systemui.miui.statusbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.android.systemui.miui.R;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"AppCompatCustomView"})
public class BatteryMeterIconView extends ImageView {
    public Drawable mBgDrawable;
    public boolean mCharging;
    public Drawable mChargingDrawable;
    public Drawable mChargingMask;
    public Path mChargingMaskPath;
    public Paint mClearPaint;
    public boolean mDark;
    public Drawable mDarkBg;
    public boolean mForThirdPart;
    public boolean mIsDigit;
    public LayerDrawable mLayerDrawable;
    public int mLevel;
    public boolean mLow;
    public boolean mMaskCharging;
    public boolean mMaskProgress;
    public boolean mNeedRoundProgress;
    public boolean mPowerSave;
    public float mProgressCenterLeftOffset;
    public ClipDrawable mProgressClipDrawable;
    public ArrayMap mProgressDarkDrawables;
    public ArrayMap mProgressDrawables;
    public boolean mProgressGravityStart;
    public int mProgressHeight;
    public int mProgressMaskMax;
    public int mProgressMaskMin;
    public Path mProgressMaskPath;
    public boolean mProgressOrientationPortrait;
    public Rect mProgressRect;
    public int mProgressWidth;
    public boolean mQuickCharging;
    public Drawable mQuickChargingDrawable;
    public Drawable mQuickChargingMask;
    public Path mQuickChargingMaskPath;
    public Drawable mRoundProgress;
    public BatteryStatus mStatus;
    public boolean mUseLegacyDrawable;
    public Drawable mWhiteBg;

    public enum BatteryStatus {
        UNKNOWN,
        LOW,
        LOW_DIGIT,
        NORMAL,
        NORMAL_DIGIT,
        CHARGING,
        CHARGING_DIGIT,
        POWER_SAVE,
        POWER_SAVE_DIGIT
    }

    public BatteryMeterIconView(Context context) {
        this(context, null);
    }

    public BatteryMeterIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BatteryMeterIconView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BatteryMeterIconView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mProgressRect = new Rect();
        this.mProgressDrawables = new ArrayMap();
        this.mProgressDarkDrawables = new ArrayMap();
        this.mClearPaint = new Paint(1);
        this.mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void clearTint() {
        for (int i = 0; i < this.mProgressDrawables.size(); i++) {
            Drawable drawable = (Drawable) this.mProgressDrawables.valueAt(i);
            if (drawable != null) {
                drawable.setTintList(null);
            }
        }
        for (int i2 = 0; i2 < this.mProgressDarkDrawables.size(); i2++) {
            Drawable drawable2 = (Drawable) this.mProgressDarkDrawables.valueAt(i2);
            if (drawable2 != null) {
                drawable2.setTintList(null);
            }
        }
        Drawable drawable3 = this.mWhiteBg;
        if (drawable3 != null) {
            drawable3.setTintList(null);
        }
        Drawable drawable4 = this.mDarkBg;
        if (drawable4 != null) {
            drawable4.setTintList(null);
        }
    }

    public final Path createPathFromPathData(String str) {
        try {
            Method declaredMethod = Class.forName("android.util.PathParser").getDeclaredMethod("createPathFromPathData", String.class);
            declaredMethod.setAccessible(true);
            return (Path) declaredMethod.invoke(null, str);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public BatteryStatus getStatus() {
        int iOrdinal;
        BatteryStatus[] batteryStatusArrValues = BatteryStatus.values();
        if (this.mCharging) {
            iOrdinal = BatteryStatus.CHARGING.ordinal();
        } else if (this.mPowerSave) {
            iOrdinal = BatteryStatus.POWER_SAVE.ordinal();
        } else {
            iOrdinal = this.mLow ? BatteryStatus.LOW.ordinal() : BatteryStatus.NORMAL.ordinal();
        }
        return batteryStatusArrValues[iOrdinal + (this.mIsDigit ? 1 : 0)];
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Path path;
        Drawable drawable;
        float width;
        float height;
        if (this.mUseLegacyDrawable) {
            super.onDraw(canvas);
            return;
        }
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null, 31);
        super.onDraw(canvas);
        if (this.mMaskProgress && this.mNeedRoundProgress && this.mRoundProgress != null) {
            if (this.mProgressOrientationPortrait) {
                height = ((canvas.getHeight() - this.mProgressClipDrawable.getIntrinsicHeight()) / 2.0f) + (((100.0f - this.mLevel) * this.mProgressHeight) / 100.0f);
                width = (canvas.getWidth() - this.mRoundProgress.getIntrinsicWidth()) / 2.0f;
            } else {
                width = ((((canvas.getWidth() - this.mProgressClipDrawable.getIntrinsicWidth()) / 2.0f) - this.mProgressCenterLeftOffset) + ((this.mLevel * this.mProgressWidth) / 100.0f)) - this.mRoundProgress.getIntrinsicWidth();
                height = (canvas.getHeight() - this.mRoundProgress.getIntrinsicHeight()) / 2.0f;
            }
            canvas.save();
            canvas.translate(width, height);
            canvas.drawPath(this.mProgressMaskPath, this.mClearPaint);
            canvas.restore();
        }
        if (this.mMaskCharging && (path = this.mChargingMaskPath) != null && this.mCharging && !this.mIsDigit) {
            if (this.mQuickCharging) {
                path = this.mQuickChargingMaskPath;
                drawable = this.mQuickChargingDrawable;
            } else {
                drawable = this.mChargingDrawable;
            }
            canvas.save();
            canvas.drawPath(path, this.mClearPaint);
            if (this.mForThirdPart) {
                canvas.save();
                drawable.setBounds(new Rect(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
                drawable.draw(canvas);
                canvas.restore();
            }
            canvas.restore();
        }
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        if (this.mUseLegacyDrawable) {
            return;
        }
        LayerDrawable layerDrawable = (LayerDrawable) getContext().getResources().getDrawable(R.drawable.battery_meter);
        this.mLayerDrawable = layerDrawable;
        this.mBgDrawable = layerDrawable.findDrawableByLayerId(R.id.background);
        setup();
    }

    public void setForThirdPart(boolean z) {
        this.mForThirdPart = z;
    }

    public void setUseLegacyDrawable(boolean z) {
        this.mUseLegacyDrawable = z;
    }

    public void setup() {
        updateResources();
        updateProgressDrawable();
        update(this.mLevel, this.mDark, this.mIsDigit, this.mQuickCharging, this.mCharging, this.mPowerSave);
    }

    public void update(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.mLevel = i;
        this.mIsDigit = z2;
        this.mCharging = z4;
        this.mQuickCharging = z3;
        this.mPowerSave = z5;
        this.mLow = i <= 19;
        updateRoundProgressFlag(i);
        Drawable drawable = z ? this.mDarkBg : this.mWhiteBg;
        this.mBgDrawable = drawable;
        this.mLayerDrawable.setDrawableByLayerId(R.id.background, drawable);
        BatteryStatus status = getStatus();
        if (status != this.mStatus || this.mDark != z) {
            this.mDark = z;
            this.mStatus = status;
            if (this.mProgressClipDrawable != null) {
                this.mProgressClipDrawable.setDrawable((Drawable) (z ? this.mProgressDarkDrawables : this.mProgressDrawables).get(status));
            }
        }
        ClipDrawable clipDrawable = this.mProgressClipDrawable;
        if (clipDrawable != null) {
            clipDrawable.setLevel(i * 100);
            this.mProgressClipDrawable.invalidateSelf();
        }
    }

    public final void updateProgressDrawable() {
        int i;
        Drawable drawable = (Drawable) this.mProgressDrawables.get(BatteryStatus.NORMAL);
        if (this.mProgressOrientationPortrait) {
            i = 80;
        } else {
            i = this.mProgressGravityStart ? 8388611 : 8388613;
        }
        ClipDrawable clipDrawable = new ClipDrawable(drawable, i, this.mProgressOrientationPortrait ? 2 : 1);
        this.mProgressClipDrawable = clipDrawable;
        this.mStatus = BatteryStatus.UNKNOWN;
        LayerDrawable layerDrawable = this.mLayerDrawable;
        int i2 = R.id.progress;
        layerDrawable.setDrawableByLayerId(i2, clipDrawable);
        LayerDrawable layerDrawable2 = this.mLayerDrawable;
        layerDrawable2.setLayerGravity(layerDrawable2.findIndexByLayerId(i2), 17);
        LayerDrawable layerDrawable3 = this.mLayerDrawable;
        layerDrawable3.setLayerInset(layerDrawable3.findIndexByLayerId(i2), 0, 0, (int) (this.mProgressCenterLeftOffset * 2.0f), 0);
        int intrinsicWidth = this.mLayerDrawable.getIntrinsicWidth();
        int intrinsicHeight = this.mLayerDrawable.getIntrinsicHeight();
        this.mProgressWidth = this.mProgressClipDrawable.getIntrinsicWidth();
        int intrinsicHeight2 = this.mProgressClipDrawable.getIntrinsicHeight();
        this.mProgressHeight = intrinsicHeight2;
        Rect rect = this.mProgressRect;
        int i3 = this.mProgressWidth;
        rect.left = (intrinsicWidth - i3) / 2;
        rect.right = intrinsicWidth - ((intrinsicWidth - i3) / 2);
        rect.top = (intrinsicHeight - intrinsicHeight2) / 2;
        rect.bottom = intrinsicHeight - ((intrinsicHeight - intrinsicHeight2) / 2);
        setImageDrawable(this.mLayerDrawable);
    }

    public void updateResources() {
        Resources resources = getContext().getResources();
        this.mProgressCenterLeftOffset = resources.getDimensionPixelSize(R.dimen.battery_meter_progress_center_left_offset);
        this.mProgressOrientationPortrait = resources.getBoolean(R.bool.battery_meter_progress_oriention_portrait);
        this.mProgressGravityStart = resources.getBoolean(R.bool.battery_meter_progress_gravity_start);
        this.mWhiteBg = resources.getDrawable(R.drawable.battery_meter_bg);
        this.mDarkBg = resources.getDrawable(R.drawable.battery_meter_bg_dark);
        Matrix matrix = new Matrix();
        float dimension = resources.getDimension(R.dimen.battery_meter_path_to_dp);
        matrix.setScale(dimension, dimension);
        this.mMaskCharging = resources.getBoolean(R.bool.battery_meter_mask_charging);
        this.mMaskProgress = resources.getBoolean(R.bool.battery_meter_mask_progress);
        if (this.mMaskCharging) {
            try {
                this.mChargingMaskPath = createPathFromPathData(resources.getString(R.string.battery_meter_charging_mask_path));
                this.mChargingMask = resources.getDrawable(R.drawable.battery_meter_charging_mask);
                this.mChargingDrawable = resources.getDrawable(R.drawable.battery_meter_charging);
                this.mChargingMaskPath.transform(matrix);
            } catch (Exception e) {
                this.mChargingMaskPath = null;
                Log.e("BatteryMeterIconView", "create ChargingMaskPath Exception=" + e);
            }
            try {
                this.mQuickChargingMaskPath = createPathFromPathData(resources.getString(R.string.battery_meter_quick_charging_mask_path));
                this.mQuickChargingMask = resources.getDrawable(R.drawable.battery_meter_quick_charging_mask);
                this.mQuickChargingDrawable = resources.getDrawable(R.drawable.battery_meter_quick_charging);
                this.mQuickChargingMaskPath.transform(matrix);
            } catch (Exception e2) {
                this.mQuickChargingMaskPath = null;
                Log.e("BatteryMeterIconView", "create ChargingMaskPath Exception=" + e2);
            }
        }
        if (this.mMaskProgress) {
            try {
                this.mProgressMaskPath = createPathFromPathData(resources.getString(R.string.battery_meter_progress_mask_path));
                this.mRoundProgress = resources.getDrawable(R.drawable.battery_meter_progress_mask);
                Matrix matrix2 = new Matrix();
                float dimension2 = resources.getDimension(R.dimen.battery_meter_progress_mask_scale_path_to_dp);
                matrix2.setScale(dimension2, dimension2);
                this.mProgressMaskPath.transform(matrix2);
            } catch (Exception e3) {
                this.mProgressMaskPath = null;
                Log.e("BatteryMeterIconView", "create ProgressMaskPath Exception=" + e3);
            }
        }
        this.mProgressMaskMin = resources.getInteger(R.integer.battery_meter_progress_mask_min);
        this.mProgressMaskMax = resources.getInteger(R.integer.battery_meter_progress_mask_max);
        ArrayMap arrayMap = this.mProgressDrawables;
        BatteryStatus batteryStatus = BatteryStatus.LOW;
        arrayMap.put(batteryStatus, resources.getDrawable(R.drawable.battery_meter_progress_low));
        this.mProgressDarkDrawables.put(batteryStatus, resources.getDrawable(R.drawable.battery_meter_progress_low_dark));
        ArrayMap arrayMap2 = this.mProgressDrawables;
        BatteryStatus batteryStatus2 = BatteryStatus.LOW_DIGIT;
        arrayMap2.put(batteryStatus2, resources.getDrawable(R.drawable.battery_meter_progress_low_digit));
        this.mProgressDarkDrawables.put(batteryStatus2, resources.getDrawable(R.drawable.battery_meter_progress_low_digit_dark));
        ArrayMap arrayMap3 = this.mProgressDrawables;
        BatteryStatus batteryStatus3 = BatteryStatus.NORMAL;
        arrayMap3.put(batteryStatus3, resources.getDrawable(R.drawable.battery_meter_progress_normal));
        this.mProgressDarkDrawables.put(batteryStatus3, resources.getDrawable(R.drawable.battery_meter_progress_normal_dark));
        ArrayMap arrayMap4 = this.mProgressDrawables;
        BatteryStatus batteryStatus4 = BatteryStatus.NORMAL_DIGIT;
        arrayMap4.put(batteryStatus4, resources.getDrawable(R.drawable.battery_meter_progress_normal_digit));
        this.mProgressDarkDrawables.put(batteryStatus4, resources.getDrawable(R.drawable.battery_meter_progress_normal_digit_dark));
        ArrayMap arrayMap5 = this.mProgressDrawables;
        BatteryStatus batteryStatus5 = BatteryStatus.CHARGING;
        arrayMap5.put(batteryStatus5, resources.getDrawable(R.drawable.battery_meter_progress_charging));
        this.mProgressDarkDrawables.put(batteryStatus5, resources.getDrawable(R.drawable.battery_meter_progress_charging_dark));
        ArrayMap arrayMap6 = this.mProgressDrawables;
        BatteryStatus batteryStatus6 = BatteryStatus.CHARGING_DIGIT;
        arrayMap6.put(batteryStatus6, resources.getDrawable(R.drawable.battery_meter_progress_charging_digit));
        this.mProgressDarkDrawables.put(batteryStatus6, resources.getDrawable(R.drawable.battery_meter_progress_charging_digit_dark));
        ArrayMap arrayMap7 = this.mProgressDrawables;
        BatteryStatus batteryStatus7 = BatteryStatus.POWER_SAVE;
        arrayMap7.put(batteryStatus7, resources.getDrawable(R.drawable.battery_meter_progress_power_save));
        this.mProgressDarkDrawables.put(batteryStatus7, resources.getDrawable(R.drawable.battery_meter_progress_power_save_dark));
        ArrayMap arrayMap8 = this.mProgressDrawables;
        BatteryStatus batteryStatus8 = BatteryStatus.POWER_SAVE_DIGIT;
        arrayMap8.put(batteryStatus8, resources.getDrawable(R.drawable.battery_meter_progress_power_save_digit));
        this.mProgressDarkDrawables.put(batteryStatus8, resources.getDrawable(R.drawable.battery_meter_progress_power_save_digit_dark));
    }

    public final void updateRoundProgressFlag(int i) {
        this.mNeedRoundProgress = i >= this.mProgressMaskMin && i <= this.mProgressMaskMax;
    }
}
