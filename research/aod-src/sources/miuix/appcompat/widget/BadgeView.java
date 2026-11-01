package miuix.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.FolmeObject;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ViewProperty;
import miuix.appcompat.R;

/* JADX INFO: loaded from: classes2.dex */
public class BadgeView extends View {
    public BadgeAnimator mAnimator;
    public Context mContext;
    public boolean mCustomBg;
    public boolean mHasNumber;
    public int mNumber;
    public int mSize;

    public class BadgeAnimator implements FolmeObject {
        public final ViewProperty mBadgeAlphaProperty;
        public Folme.ObjectFolmeImpl mFolmeAnimator;

        public BadgeAnimator() {
            this.mBadgeAlphaProperty = new ViewProperty("badgeAlpha", 1.0f) { // from class: miuix.appcompat.widget.BadgeView.BadgeAnimator.1
                @Override // miuix.animation.property.FloatProperty
                public float getValue(View view) {
                    Drawable background = view.getBackground();
                    if (background != null) {
                        return background.getAlpha();
                    }
                    return 0.0f;
                }

                @Override // miuix.animation.property.FloatProperty
                public void setValue(View view, float f) {
                    Drawable background;
                    if (f < 0.0f || f > 255.0f || (background = view.getBackground()) == null) {
                        return;
                    }
                    background.setAlpha((int) f);
                }
            };
        }

        @Override // miuix.animation.FolmeObject
        public Folme.ObjectFolmeImpl folme() {
            return this.mFolmeAnimator;
        }

        public final void setBackgroundAlpha(int i) {
            BadgeView badgeView = BadgeView.this;
            Float fValueOf = Float.valueOf(1.0f);
            Float fValueOf2 = Float.valueOf(0.5f);
            if (i <= 0) {
                AnimConfig animConfig = new AnimConfig();
                animConfig.setEase(FolmeEase.spring(1.0f, 0.3f));
                Folme.use((View) badgeView).to(this.mBadgeAlphaProperty, Float.valueOf(0.0f), ViewProperty.SCALE_X, fValueOf2, ViewProperty.SCALE_Y, fValueOf2, animConfig);
                BadgeView.this.setImportantForAccessibility(2);
                return;
            }
            if (badgeView.getBackground() != null && BadgeView.this.getBackground().getAlpha() == 0) {
                badgeView.setScaleX(0.5f);
                badgeView.setScaleY(0.5f);
            }
            AnimConfig animConfig2 = new AnimConfig();
            animConfig2.setEase(FolmeEase.spring(0.65f, 0.35f));
            animConfig2.setSpecial(this.mBadgeAlphaProperty, FolmeEase.spring(1.0f, 0.3f), new float[0]);
            Folme.use((View) badgeView).to(this.mBadgeAlphaProperty, Float.valueOf(255.0f), ViewProperty.SCALE_X, fValueOf, ViewProperty.SCALE_Y, fValueOf, animConfig2);
            BadgeView.this.setImportantForAccessibility(1);
        }

        @Override // miuix.animation.FolmeObject
        public void setFolmeImpl(Folme.ObjectFolmeImpl objectFolmeImpl) {
            this.mFolmeAnimator = objectFolmeImpl;
        }
    }

    public static class BadgeSize {
        public static int MEDIUM = 1;
        public static int SMALL;
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.appcompat.widget.BadgeView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean hasNumber;
        public boolean isBgVisible;
        public int number;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isBgVisible = parcel.readInt() != 0;
            this.hasNumber = parcel.readInt() != 0;
            this.number = parcel.readInt();
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.isBgVisible = parcel.readInt() != 0;
            this.hasNumber = parcel.readInt() != 0;
            this.number = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isBgVisible ? 1 : 0);
            parcel.writeInt(this.hasNumber ? 1 : 0);
            parcel.writeInt(this.number);
        }
    }

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BadgeView(Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Widget_BadgeView);
    }

    public BadgeView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCustomBg = true;
        init(context, attributeSet, i, i2);
    }

    @NonNull
    private Drawable getBackgroundInternal() {
        BadgeDrawable.BadgeConfig badgeConfig;
        BadgeDrawable badgeDrawable;
        if (this.mHasNumber) {
            badgeDrawable = new BadgeDrawable(this.mContext, 0, this.mSize == BadgeSize.SMALL ? BadgeDrawable.BadgeConfig.EXPAND_OUTSIDE : BadgeDrawable.BadgeConfig.EXPAND_INSIDE, this.mNumber);
        } else {
            int i = this.mSize;
            if (i == BadgeSize.SMALL) {
                badgeConfig = BadgeDrawable.BadgeConfig.SIZE_SMALL;
            } else {
                badgeConfig = i == BadgeSize.MEDIUM ? BadgeDrawable.BadgeConfig.SIZE_MEDIUM : BadgeDrawable.BadgeConfig.SIZE_LARGE;
            }
            badgeDrawable = new BadgeDrawable(this.mContext, badgeConfig);
        }
        return badgeDrawable.getCurrentBadgeDrawable();
    }

    private void setBackgroundAlpha(@IntRange int i) {
        this.mAnimator.setBackgroundAlpha(i);
    }

    public int getNumber() {
        return this.mNumber;
    }

    public final void init(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mContext = context;
        this.mAnimator = new BadgeAnimator();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BadgeView, i, i2);
        this.mHasNumber = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BadgeView_hasNumber, false);
        this.mNumber = typedArrayObtainStyledAttributes.getInt(R.styleable.BadgeView_badgeNumber, 0);
        this.mSize = typedArrayObtainStyledAttributes.getInt(R.styleable.BadgeView_badgeSize, BadgeSize.SMALL);
        typedArrayObtainStyledAttributes.recycle();
        if (getId() == -1) {
            setId(R.id.miuix_appcompat_badge_view);
        }
        if (getBackground() == null) {
            Drawable backgroundInternal = getBackgroundInternal();
            backgroundInternal.setAlpha(0);
            super.setBackground(backgroundInternal);
            this.mCustomBg = false;
        }
        setImportantForAccessibility(2);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        Drawable background = getBackground();
        if (background != null && !this.mCustomBg) {
            int intrinsicWidth = background.getIntrinsicWidth();
            int intrinsicHeight = background.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                setMeasuredDimension(intrinsicWidth, intrinsicHeight);
                return;
            }
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            Log.w("BadgeView", "Wrong state class, expecting SavedState! This usually happens when two views of different type have the same id in the same hierarchy.");
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mCustomBg) {
            setBackgroundAlpha(savedState.isBgVisible ? 255 : 0);
            return;
        }
        this.mHasNumber = savedState.hasNumber;
        this.mNumber = savedState.number;
        if (savedState.isBgVisible) {
            super.setBackground(getBackgroundInternal());
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (!this.mCustomBg) {
            savedState.hasNumber = this.mHasNumber;
            savedState.number = this.mNumber;
        }
        Drawable background = getBackground();
        savedState.isBgVisible = background != null && background.getAlpha() > 0;
        return savedState;
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        super.setBackground(drawable);
        this.mCustomBg = true;
    }

    public void setHasNumberOrNot(boolean z) {
        if (this.mHasNumber != z) {
            this.mHasNumber = z;
        }
    }

    public void setNumber(int i) {
        Drawable background = getBackground();
        if (background == null || !this.mHasNumber || this.mNumber == i) {
            return;
        }
        this.mNumber = i;
        Drawable backgroundInternal = getBackgroundInternal();
        if (background.getAlpha() != 0) {
            super.setBackground(backgroundInternal);
            return;
        }
        backgroundInternal.setAlpha(0);
        super.setBackground(backgroundInternal);
        setBackgroundAlpha(255);
    }
}
