package miuix.appcompat.app.floatingactivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import miuix.appcompat.R;
import miuix.appcompat.app.floatingactivity.helper.BaseFloatingActivityHelper;
import miuix.core.util.WindowUtils;
import miuix.internal.util.AttributeResolver;
import miuix.reflect.Reflects;

/* JADX INFO: loaded from: classes2.dex */
public class FloatingABOLayoutSpec {
    public Context mContext;
    public DisplayMetrics mDisplayMetrics;
    public TypedValue mFixedHeightMajor;
    public TypedValue mFixedHeightMinor;
    public TypedValue mFixedWidthMajor;
    public TypedValue mFixedWidthMinor;
    public boolean mIsInDialogMode;
    public TypedValue mMaxHeightMajor;
    public TypedValue mMaxHeightMinor;
    public TypedValue mMaxWidthMajor;
    public TypedValue mMaxWidthMinor;
    public boolean mFloatingTheme = false;
    public boolean mFloatingWindow = false;
    public Point mPhysicalSize = new Point();

    public FloatingABOLayoutSpec(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        updatePhysicalSize(context);
        parseWindowSize(context, attributeSet);
    }

    public final TypedValue getFixedHeightMajor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mFixedHeightMajor;
        }
        return null;
    }

    public final TypedValue getFixedHeightMinor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mFixedHeightMinor;
        }
        return null;
    }

    public final TypedValue getFixedWidthMajor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mFixedWidthMajor;
        }
        return null;
    }

    public final TypedValue getFixedWidthMinor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mFixedWidthMinor;
        }
        return null;
    }

    public int getHeightMeasureSpec(int i) {
        return getMeasureSpec(i, false, getFixedHeightMinor(), getFixedHeightMajor(), getMaxHeightMinor(), getMaxHeightMajor());
    }

    public int getHeightMeasureSpecForDialog(int i) {
        return getMeasureSpec(i, false, this.mFixedHeightMinor, this.mFixedHeightMajor, this.mMaxHeightMinor, this.mMaxHeightMajor);
    }

    public final TypedValue getMaxHeightMajor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mMaxHeightMajor;
        }
        return null;
    }

    public final TypedValue getMaxHeightMinor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mMaxHeightMinor;
        }
        return null;
    }

    public final TypedValue getMaxWidthMajor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mMaxWidthMajor;
        }
        return null;
    }

    public final TypedValue getMaxWidthMinor() {
        if (this.mFloatingTheme && this.mFloatingWindow) {
            return this.mMaxWidthMinor;
        }
        return null;
    }

    public final int getMeasureSpec(int i, boolean z, TypedValue typedValue, TypedValue typedValue2, TypedValue typedValue3, TypedValue typedValue4) {
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            boolean zIsPortrait = isPortrait();
            if (!zIsPortrait) {
                typedValue = typedValue2;
            }
            int iResolveDimension = resolveDimension(typedValue, z);
            if (iResolveDimension > 0) {
                return View.MeasureSpec.makeMeasureSpec(iResolveDimension, 1073741824);
            }
            if (!zIsPortrait) {
                typedValue3 = typedValue4;
            }
            int iResolveDimension2 = resolveDimension(typedValue3, z);
            if (iResolveDimension2 > 0) {
                return View.MeasureSpec.makeMeasureSpec(Math.min(iResolveDimension2, View.MeasureSpec.getSize(i)), Integer.MIN_VALUE);
            }
        }
        return i;
    }

    public final int getThemeResourceId(ContextThemeWrapper contextThemeWrapper) {
        try {
            return ((Integer) Reflects.invoke(contextThemeWrapper, Reflects.getMethod(contextThemeWrapper.getClass(), "getThemeResId", null), null)).intValue();
        } catch (RuntimeException e) {
            Log.w("FloatingABOLayoutSpec", "catch theme resource get exception", e);
            return 0;
        }
    }

    public int getWidthMeasureSpec(int i) {
        return getMeasureSpec(i, true, getFixedWidthMinor(), getFixedWidthMajor(), getMaxWidthMinor(), getMaxWidthMajor());
    }

    public int getWidthMeasureSpecForDialog(int i) {
        return getMeasureSpec(i, true, this.mFixedWidthMinor, this.mFixedWidthMajor, this.mMaxWidthMinor, this.mMaxWidthMajor);
    }

    public final boolean isPortrait() {
        return WindowUtils.isPortrait(this.mContext);
    }

    public void onConfigurationChanged() {
        int themeResourceId;
        Context contextThemeWrapper = this.mContext;
        if (this.mIsInDialogMode && (contextThemeWrapper instanceof ContextThemeWrapper) && (themeResourceId = getThemeResourceId((ContextThemeWrapper) contextThemeWrapper)) > 0) {
            contextThemeWrapper = new ContextThemeWrapper(this.mContext.getApplicationContext(), themeResourceId);
        }
        this.mFixedWidthMinor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowFixedWidthMinor);
        this.mFixedHeightMajor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowFixedHeightMajor);
        this.mFixedWidthMajor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowFixedWidthMajor);
        this.mFixedHeightMinor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowFixedHeightMinor);
        this.mMaxWidthMinor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowMaxWidthMinor);
        this.mMaxWidthMajor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowMaxWidthMajor);
        this.mMaxHeightMinor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowMaxHeightMinor);
        this.mMaxHeightMajor = AttributeResolver.resolveTypedValue(contextThemeWrapper, R.attr.windowMaxHeightMajor);
        updatePhysicalSize(contextThemeWrapper);
    }

    public void onFloatingModeChanged(boolean z) {
        if (this.mFloatingTheme) {
            this.mFloatingWindow = z;
        }
    }

    public final void parseWindowSize(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Window);
        int i = R.styleable.Window_windowFixedWidthMinor;
        if (typedArrayObtainStyledAttributes.hasValue(i)) {
            TypedValue typedValue = new TypedValue();
            this.mFixedWidthMinor = typedValue;
            typedArrayObtainStyledAttributes.getValue(i, typedValue);
        }
        int i2 = R.styleable.Window_windowFixedHeightMajor;
        if (typedArrayObtainStyledAttributes.hasValue(i2)) {
            TypedValue typedValue2 = new TypedValue();
            this.mFixedHeightMajor = typedValue2;
            typedArrayObtainStyledAttributes.getValue(i2, typedValue2);
        }
        int i3 = R.styleable.Window_windowFixedWidthMajor;
        if (typedArrayObtainStyledAttributes.hasValue(i3)) {
            TypedValue typedValue3 = new TypedValue();
            this.mFixedWidthMajor = typedValue3;
            typedArrayObtainStyledAttributes.getValue(i3, typedValue3);
        }
        int i4 = R.styleable.Window_windowFixedHeightMinor;
        if (typedArrayObtainStyledAttributes.hasValue(i4)) {
            TypedValue typedValue4 = new TypedValue();
            this.mFixedHeightMinor = typedValue4;
            typedArrayObtainStyledAttributes.getValue(i4, typedValue4);
        }
        int i5 = R.styleable.Window_windowMaxWidthMinor;
        if (typedArrayObtainStyledAttributes.hasValue(i5)) {
            TypedValue typedValue5 = new TypedValue();
            this.mMaxWidthMinor = typedValue5;
            typedArrayObtainStyledAttributes.getValue(i5, typedValue5);
        }
        int i6 = R.styleable.Window_windowMaxWidthMajor;
        if (typedArrayObtainStyledAttributes.hasValue(i6)) {
            TypedValue typedValue6 = new TypedValue();
            this.mMaxWidthMajor = typedValue6;
            typedArrayObtainStyledAttributes.getValue(i6, typedValue6);
        }
        int i7 = R.styleable.Window_windowMaxHeightMajor;
        if (typedArrayObtainStyledAttributes.hasValue(i7)) {
            TypedValue typedValue7 = new TypedValue();
            this.mMaxHeightMajor = typedValue7;
            typedArrayObtainStyledAttributes.getValue(i7, typedValue7);
        }
        int i8 = R.styleable.Window_windowMaxHeightMinor;
        if (typedArrayObtainStyledAttributes.hasValue(i8)) {
            TypedValue typedValue8 = new TypedValue();
            this.mMaxHeightMinor = typedValue8;
            typedArrayObtainStyledAttributes.getValue(i8, typedValue8);
        }
        this.mFloatingTheme = typedArrayObtainStyledAttributes.getBoolean(R.styleable.Window_isMiuixFloatingTheme, false);
        this.mFloatingWindow = BaseFloatingActivityHelper.isFloatingWindow(context);
        typedArrayObtainStyledAttributes.recycle();
    }

    public final int resolveDimension(TypedValue typedValue, boolean z) {
        int i;
        float fraction;
        if (typedValue == null || (i = typedValue.type) == 0) {
            return 0;
        }
        if (i == 5) {
            fraction = typedValue.getDimension(this.mDisplayMetrics);
        } else {
            if (i != 6) {
                return 0;
            }
            Point point = this.mPhysicalSize;
            float f = z ? point.x : point.y;
            fraction = typedValue.getFraction(f, f);
        }
        return (int) fraction;
    }

    public void setIsInDialogMode(boolean z) {
        this.mIsInDialogMode = z;
    }

    public void updatePhysicalSize(Context context) {
        this.mDisplayMetrics = context.getResources().getDisplayMetrics();
        this.mPhysicalSize = WindowUtils.getWindowSize(context);
    }
}
