package com.miui.aod.components.view;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.icu.text.BreakIterator;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.aod.Utils;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.util.FrameworkReflectUtils;
import com.miui.aod.util.MiuiPreDrawListener;
import com.miui.aod.util.ShaderUtils;
import com.miui.aod.util.ViewUtilsKt;
import com.miui.aod.utils.CommonUtils;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.Function1;

/* JADX INFO: loaded from: classes.dex */
public class VerticalTextView extends AppCompatTextView {
    private static final String TAG = "com.miui.aod.components.view.VerticalTextView";
    private int[] mAutoSizeTextSizesInPx;
    private float mCJKspacingAdd;
    private float mCJKspacingMult;
    private final Rect mContentRect;

    @DrawableRes
    private int mDrawableMask;
    private final char mEllipsisHint;
    private boolean mIsVerticalMode;
    private Drawable mMaskDrawable;
    private boolean mNeedsAutoFormatText;
    private boolean mNeedsAutoSizeText;
    private MaskInitLayoutChangeListener mOnLayoutChangeListener;
    private float mScale;

    @ShaderUtils.IShaderMode
    private int mShaderMode;
    private float mTMeasuredHeight;
    private float mTMeasuredWidth;
    private TextPaint mTempTextPaint;
    private TextFormat mTextFormat;
    private VerticalStaticLayout mVerticalStaticLayout;
    private static final Map<String, Method> sTextViewMethodByNameCache = new ConcurrentHashMap();
    public static final Rect CONTENT_INITIAL_RECT = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);

    public static class MaskInitLayoutChangeListener implements View.OnLayoutChangeListener {
        private Rect mRect;
        private final WeakReference<VerticalTextView> mRefs;

        public MaskInitLayoutChangeListener(VerticalTextView verticalTextView) {
            this.mRefs = new WeakReference<>(verticalTextView);
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            VerticalTextView verticalTextView = this.mRefs.get();
            if (verticalTextView != null) {
                verticalTextView.initDrawMask(this.mRect);
            }
        }

        public void setRect(Rect rect) {
            this.mRect = rect;
        }
    }

    public static class TextFormat implements Comparable<TextFormat> {
        public int lineCount;
        public int[] lineEnds;

        @Override // java.lang.Comparable
        public int compareTo(TextFormat textFormat) {
            int i = this.lineCount;
            int i2 = textFormat.lineCount;
            if (i < i2) {
                return -1;
            }
            if (i > i2) {
                return 1;
            }
            int i3 = 0;
            while (true) {
                int[] iArr = this.lineEnds;
                if (i3 >= iArr.length) {
                    return 0;
                }
                int i4 = iArr[i3];
                int i5 = textFormat.lineEnds[i3];
                if (i4 < i5) {
                    return 1;
                }
                if (i4 > i5) {
                    return -1;
                }
                i3++;
            }
        }
    }

    public static /* synthetic */ Boolean $r8$lambda$18_jt8iX4exXCAHGVwf_Ufp2MWo(VerticalTextView verticalTextView, MiuiPreDrawListener miuiPreDrawListener) {
        verticalTextView.initMaskDrawable();
        miuiPreDrawListener.removeListener();
        return Boolean.TRUE;
    }

    /* JADX INFO: renamed from: $r8$lambda$_X-y-0YNlk2Tgb35YQe2_HMBDJo, reason: not valid java name */
    public static /* synthetic */ Boolean m282$r8$lambda$_Xy0YNlk2Tgb35YQe2_HMBDJo(VerticalTextView verticalTextView, MiuiPreDrawListener miuiPreDrawListener) {
        verticalTextView.mContentRect.set(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
        verticalTextView.initDrawMask(verticalTextView.mContentRect);
        miuiPreDrawListener.removeListener();
        return Boolean.TRUE;
    }

    public VerticalTextView(Context context) {
        this(context, null);
    }

    public VerticalTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.textViewStyle);
    }

    public VerticalTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsVerticalMode = true;
        this.mEllipsisHint = (char) 65049;
        this.mCJKspacingAdd = 0.0f;
        this.mCJKspacingMult = 1.0f;
        this.mDrawableMask = -1;
        this.mNeedsAutoSizeText = false;
        this.mNeedsAutoFormatText = false;
        this.mScale = -1.0f;
        this.mContentRect = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
        this.mShaderMode = 0;
        ViewUtilsKt.doOnMiuiPreDraw(this, new Function1() { // from class: com.miui.aod.components.view.VerticalTextView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VerticalTextView.m282$r8$lambda$_Xy0YNlk2Tgb35YQe2_HMBDJo(this.f$0, (MiuiPreDrawListener) obj);
            }
        });
        FrameworkReflectUtils.setUseBoundsForWidthCompat(this, false);
    }

    private int autoJustTextFormat(int i, int i2) {
        if (!isNeedsAutoFormatText() || this.mTextFormat == null) {
            return i;
        }
        while (i >= i2) {
            int iFitForTextFormat = fitForTextFormat(this.mAutoSizeTextSizesInPx[i]);
            if (iFitForTextFormat >= 0) {
                return iFitForTextFormat == 0 ? i : i + 1;
            }
            i--;
        }
        return i;
    }

    private void autoJustTextSize() {
        if (!this.mNeedsAutoSizeText || this.mAutoSizeTextSizesInPx.length <= 0 || getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
            return;
        }
        int length = this.mAutoSizeTextSizesInPx.length - 1;
        RectF rectF = new RectF(0.0f, 0.0f, (getMeasuredWidth() - getTotalPaddingLeft()) - getTotalPaddingRight(), (getMeasuredHeight() - getExtendedPaddingBottom()) - getExtendedPaddingTop());
        if (rectF.right <= 0.0f || rectF.bottom <= 0.0f) {
            return;
        }
        int i = 0;
        int i2 = 0;
        while (i <= length) {
            int i3 = (i + length) / 2;
            if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[i3], rectF)) {
                int i4 = i3 + 1;
                i2 = i;
                i = i4;
            } else {
                i2 = i3 - 1;
                length = i2;
            }
        }
        int iMax = Math.max(0, Math.min(autoJustTextFormat(i2, 0), i2));
        if (iMax < this.mAutoSizeTextSizesInPx.length) {
            float textSize = getTextSize();
            int i5 = this.mAutoSizeTextSizesInPx[iMax];
            if (textSize != i5) {
                setRawTextSize(TypedValue.applyDimension(0, i5, getContext().getResources().getDisplayMetrics()));
            }
        }
        this.mNeedsAutoSizeText = true;
    }

    private int fitForTextFormat(int i) {
        if (isVerticalMode()) {
            TextPaint textPaint = this.mTempTextPaint;
            if (textPaint == null) {
                this.mTempTextPaint = new TextPaint();
            } else {
                textPaint.reset();
            }
            this.mTempTextPaint.set(getPaint());
            this.mTempTextPaint.setTextSize(i);
            this.mVerticalStaticLayout = new VerticalStaticLayout(getText(), this.mTempTextPaint, (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), getLineSpacingMultiplier(), getLineSpacingExtra(), getCJKVerticalSpacingMultiplier(), getCJKVerticalLineSpacingExtra());
        } else {
            getPaint().setTextSize(i);
            Class cls = Boolean.TYPE;
            Class cls2 = Integer.TYPE;
            Method textViewMethod = getTextViewMethod("makeNewLayout", new Class[]{cls2, cls2, BoringLayout.Metrics.class, BoringLayout.Metrics.class, cls2, cls});
            if (textViewMethod != null) {
                try {
                    textViewMethod.invoke(this, Integer.valueOf((getWidth() - getPaddingLeft()) - getPaddingRight()), 0, null, null, Integer.valueOf((getWidth() - getPaddingLeft()) - getPaddingRight()), Boolean.FALSE);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Log.e(TAG, "error...", e);
                }
            }
        }
        return Integer.compare(0, getTextFormat().compareTo(this.mTextFormat));
    }

    private float getCJKVerticalLineSpacingExtra() {
        return this.mCJKspacingAdd;
    }

    private float getCJKVerticalSpacingMultiplier() {
        return this.mCJKspacingMult;
    }

    private int getClusterStep(@NonNull BreakIterator breakIterator, int i, int i2) {
        int iFollowing = breakIterator.following(i);
        if (iFollowing == -1 || iFollowing <= i) {
            iFollowing = Math.min(i + 1, i2);
        }
        return Math.max(1, iFollowing - i);
    }

    private float getCurLineBeginY(int i) {
        if ((getGravity() & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) == 48) {
            return getPaddingTop();
        }
        if ((getGravity() & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) == 80) {
            return (getHeight() - getPaddingBottom()) - this.mVerticalStaticLayout.getHeight(i);
        }
        if ((getGravity() & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) == 16) {
            return (((getHeight() - getPaddingBottom()) + getPaddingTop()) - this.mVerticalStaticLayout.getHeight(i)) / 2.0f;
        }
        return 0.0f;
    }

    @Nullable
    private Method getTextViewMethod(@NonNull String str, Class<?>[] clsArr) {
        try {
            Map<String, Method> map = sTextViewMethodByNameCache;
            Method method = map.get(str);
            if (method != null) {
                return method;
            }
            Method declaredMethod = TextView.class.getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            map.put(str, declaredMethod);
            return declaredMethod;
        } catch (Exception unused) {
            return null;
        }
    }

    private void initDimentionsOnMeasure(int i, int i2) {
        float fMin;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        VerticalStaticLayout verticalStaticLayout = new VerticalStaticLayout(getText(), getPaint(), ((mode == 0 ? Integer.MAX_VALUE : size) - getPaddingLeft()) - getPaddingRight(), ((mode2 != 0 ? size2 : Integer.MAX_VALUE) - getPaddingTop()) - getPaddingBottom(), getLineSpacingMultiplier(), getLineSpacingExtra(), getCJKVerticalSpacingMultiplier(), getCJKVerticalLineSpacingExtra());
        this.mVerticalStaticLayout = verticalStaticLayout;
        float width = verticalStaticLayout.getWidth();
        float height = this.mVerticalStaticLayout.getHeight();
        float paddingLeft = width + getPaddingLeft() + getPaddingRight();
        float fMin2 = 2.1474836E9f;
        if (mode2 == 1073741824) {
            fMin = size2;
        } else {
            fMin = mode2 == Integer.MIN_VALUE ? Math.min(height, size2) : 2.1474836E9f;
        }
        if (mode == 1073741824) {
            fMin2 = size;
        } else if (mode == Integer.MIN_VALUE) {
            fMin2 = Math.min(paddingLeft, size);
        }
        this.mTMeasuredHeight = fMin;
        this.mTMeasuredWidth = fMin2;
    }

    @SuppressLint({"ResourceType"})
    private void initMaskDrawable() {
        if (getDrawableMask() != -1) {
            if (this.mContentRect.equals(CONTENT_INITIAL_RECT)) {
                this.mContentRect.set(0, 0, getWidth(), getHeight());
            }
            int i = this.mShaderMode;
            if (i == 1 && this.mMaskDrawable == null) {
                Drawable drawableForMask = AodDrawables.getDrawableForMask(getContext(), getDrawableMask());
                this.mMaskDrawable = drawableForMask;
                CommonUtils.setXfermodeForDrawable(drawableForMask, new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                this.mMaskDrawable.setBounds(this.mContentRect);
                return;
            }
            if (i == 0) {
                this.mMaskDrawable = null;
                if (AodDrawables.isPureColor(this.mDrawableMask)) {
                    setTextColor(getContext().getColor(this.mDrawableMask));
                } else {
                    getPaint().setShader(ShaderUtils.getShader(getContext(), this.mDrawableMask, getContentRect()));
                }
            }
        }
    }

    private void setRawTextSize(float f) {
        if (f != getPaint().getTextSize()) {
            getPaint().setTextSize(f);
            boolean zIsInLayout = isInLayout();
            reMeasureText(getMeasuredWidth(), getMeasuredHeight());
            if (zIsInLayout) {
                forceLayout();
            } else {
                requestLayout();
            }
            invalidate();
        }
    }

    private boolean shouldDrawCharacter(char c) {
        return c != '\n';
    }

    private boolean suggestedSizeFitsInSpace(int i, RectF rectF) {
        TextPaint textPaint = this.mTempTextPaint;
        if (textPaint == null) {
            this.mTempTextPaint = new TextPaint();
        } else {
            textPaint.reset();
        }
        this.mTempTextPaint.set(getPaint());
        this.mTempTextPaint.setTextSize(i);
        VerticalStaticLayout verticalStaticLayout = new VerticalStaticLayout(getText(), this.mTempTextPaint, (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), getLineSpacingMultiplier(), getLineSpacingExtra(), getCJKVerticalSpacingMultiplier(), getCJKVerticalLineSpacingExtra());
        this.mVerticalStaticLayout = verticalStaticLayout;
        return verticalStaticLayout.getShowedLineCount() >= this.mVerticalStaticLayout.getLineCount();
    }

    public Rect getContentRect() {
        return this.mContentRect;
    }

    public int getDrawableMask() {
        return this.mDrawableMask;
    }

    public Drawable getGradientOverlayDrawable() {
        return this.mMaskDrawable;
    }

    public String getShowText() {
        if (!isVerticalMode()) {
            return getLayout() != null ? getShowText(getLayout().getLineCount()) : "";
        }
        VerticalStaticLayout verticalStaticLayout = this.mVerticalStaticLayout;
        if (verticalStaticLayout == null) {
            return "";
        }
        int showedLineCount = verticalStaticLayout.getShowedLineCount();
        int lineCount = this.mVerticalStaticLayout.getLineCount();
        if (showedLineCount <= 0) {
            return String.valueOf((char) 65049);
        }
        return showedLineCount < lineCount ? getShowText(showedLineCount) : getText().toString();
    }

    public String getShowText(int i) {
        if (isVerticalMode()) {
            StringBuilder sb = new StringBuilder();
            sb.append(getText().subSequence(0, getText().length() - this.mVerticalStaticLayout.getEllipsisCount()).toString());
            sb.append(this.mVerticalStaticLayout.getEllipsisCount() > 0 ? (char) 65049 : "");
            return sb.toString();
        }
        Layout layout = getLayout();
        if (layout != null) {
            int lineCount = layout.getLineCount();
            if (lineCount <= 0) {
                return getText().toString();
            }
            int i2 = lineCount - 1;
            int ellipsisCount = layout.getEllipsisCount(i2);
            if (ellipsisCount > 0) {
                int lineEnd = layout.getLineEnd(i2) - ellipsisCount;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(lineEnd > 0 ? getText().subSequence(0, lineEnd) : "");
                sb2.append("…");
                return sb2.toString();
            }
        }
        return getText().toString();
    }

    public TextFormat getTextFormat() {
        int i;
        TextFormat textFormat = new TextFormat();
        if (isVerticalMode()) {
            int showedLineCount = this.mVerticalStaticLayout.getShowedLineCount();
            textFormat.lineCount = showedLineCount;
            if (showedLineCount > 0) {
                textFormat.lineEnds = new int[showedLineCount];
                int i2 = 0;
                while (true) {
                    i = textFormat.lineCount;
                    if (i2 >= i) {
                        break;
                    }
                    textFormat.lineEnds[i2] = this.mVerticalStaticLayout.getLineBreak(i2);
                    i2++;
                }
                textFormat.lineEnds[i - 1] = getText().length() - (this.mVerticalStaticLayout.getEllipsisCount() > 0 ? 1 : 0);
            }
        } else {
            Layout layout = getLayout();
            if (layout != null) {
                int lineCount = layout.getLineCount();
                textFormat.lineEnds = new int[lineCount];
                int i3 = lineCount - 1;
                int ellipsisCount = layout.getEllipsisCount(i3);
                layout.getLineEnd(i3);
                for (int i4 = 0; i4 < lineCount; i4++) {
                    textFormat.lineEnds[i4] = layout.getLineEnd(i4);
                }
                if (ellipsisCount > 0) {
                    textFormat.lineEnds[i3] = layout.getLineStart(i3) + layout.getEllipsisStart(i3) + 1;
                }
                textFormat.lineCount = lineCount;
                return textFormat;
            }
        }
        return textFormat;
    }

    public Rect initDrawMask(@NonNull Rect rect) {
        int i = 0;
        rect.set(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
        if (this.mIsVerticalMode) {
            VerticalStaticLayout verticalStaticLayout = this.mVerticalStaticLayout;
            if (verticalStaticLayout == null) {
                return rect;
            }
            float width = verticalStaticLayout.getWidth(verticalStaticLayout.getShowedLineCount());
            rect.top = getPaddingTop();
            if ((getGravity() & 7) == 5) {
                rect.right = (int) Math.ceil(getWidth() - getPaddingRight());
            } else if ((getGravity() & 7) == 3) {
                rect.right = (int) Math.ceil(getPaddingLeft() + width);
            } else if ((getGravity() & 7) == 1) {
                rect.right = (int) Math.ceil((((getWidth() + getPaddingLeft()) - getPaddingRight()) + width) / 2.0f);
            }
            rect.left = (int) Math.max(rect.right - width, 0.0f);
            while (i < this.mVerticalStaticLayout.getShowedLineCount()) {
                rect.bottom = Math.max((int) Math.ceil(this.mVerticalStaticLayout.getHeight(i)), rect.bottom);
                i++;
            }
            rect.bottom += rect.top;
            Log.d(TAG, "vrect " + rect);
            return rect;
        }
        Layout layout = getLayout();
        if (layout == null) {
            rect.set(0, 0, getWidth(), getHeight());
            if (this.mOnLayoutChangeListener == null) {
                this.mOnLayoutChangeListener = new MaskInitLayoutChangeListener(this);
            }
            this.mOnLayoutChangeListener.setRect(rect);
            addOnLayoutChangeListener(this.mOnLayoutChangeListener);
            invalidate();
            return rect;
        }
        Rect rect2 = new Rect();
        getLineBounds(0, rect2);
        int i2 = rect2.top;
        rect.top = i2;
        rect.bottom = Math.min(i2 + layout.getLineBottom(layout.getLineCount() - 1), getMeasuredHeight());
        while (i < layout.getLineCount()) {
            float paragraphLeft = layout.getParagraphLeft(i);
            float paragraphRight = layout.getParagraphRight(i);
            if (paragraphLeft > paragraphRight) {
                paragraphRight = paragraphLeft;
                paragraphLeft = paragraphRight;
            }
            rect.left = Math.min((int) Math.floor(paragraphLeft), rect.left);
            rect.right = Math.max((int) Math.ceil(paragraphRight), rect.right);
            i++;
        }
        Log.d(TAG, "hrect " + rect);
        return rect;
    }

    public boolean isNeedsAutoFormatText() {
        return this.mNeedsAutoFormatText && this.mTextFormat != null;
    }

    public boolean isVerticalMode() {
        return this.mIsVerticalMode;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x010a  */
    /* JADX WARN: Code duplicated, block: B:32:0x0121  */
    /* JADX WARN: Code duplicated, block: B:35:0x0132  */
    /* JADX WARN: Code duplicated, block: B:36:0x0139  */
    /* JADX WARN: Code duplicated, block: B:38:0x014b  */
    /* JADX WARN: Code duplicated, block: B:40:0x0151  */
    /* JADX WARN: Code duplicated, block: B:43:0x015c  */
    /* JADX WARN: Code duplicated, block: B:44:0x017a  */
    /* JADX WARN: Code duplicated, block: B:47:0x0197  */
    /* JADX WARN: Code duplicated, block: B:54:0x01b4  */
    /* JADX WARN: Code duplicated, block: B:57:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:59:0x01d8  */
    /* JADX WARN: Code duplicated, block: B:71:0x01e6 A[SYNTHETIC] */
    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        Canvas canvas2;
        int i;
        float width;
        float paddingLeft;
        float curLineBeginY;
        BreakIterator characterInstance;
        int i2;
        int clusterStep;
        char[] cArr;
        boolean zNeedRotate;
        float f;
        float f2;
        float fMax;
        int i3;
        float f3;
        int i4;
        int i5;
        char[] cArr2;
        int i6;
        int clusterStep2;
        float charSpacingMultiplier;
        int i7;
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null);
        int i8 = 0;
        if (this.mScale != -1.0f) {
            canvas2 = canvas;
            int iSaveLayer2 = canvas2.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null);
            if (Utils.isLayoutRtl()) {
                float f4 = this.mScale;
                canvas2.scale(f4, f4, getWidth(), 0.0f);
            } else {
                float f5 = this.mScale;
                canvas2.scale(f5, f5);
            }
            i = iSaveLayer2;
        } else {
            canvas2 = canvas;
            i = 0;
        }
        initMaskDrawable();
        TextPaint paint = getPaint();
        if (this.mIsVerticalMode) {
            int showedLineCount = this.mVerticalStaticLayout.getShowedLineCount();
            int lineCount = this.mVerticalStaticLayout.getLineCount();
            if (lineCount == 0) {
                return;
            }
            paint.setColor(getCurrentTextColor());
            paint.drawableState = getDrawableState();
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            String showText = getShowText();
            char[] charArray = showText.toCharArray();
            canvas2.save();
            VerticalStaticLayout verticalStaticLayout = this.mVerticalStaticLayout;
            float width2 = verticalStaticLayout.getWidth(verticalStaticLayout.getShowedLineCount());
            float lineMaxWidth = this.mVerticalStaticLayout.getLineMaxWidth();
            if ((getGravity() & 7) == 5) {
                paddingLeft = getWidth() - getPaddingRight();
            } else {
                if ((getGravity() & 7) == 3) {
                    paddingLeft = width2 + getPaddingLeft();
                } else {
                    width = (getGravity() & 7) == 1 ? ((((getWidth() + getPaddingLeft()) - getPaddingRight()) + width2) / 2.0f) - lineMaxWidth : 0.0f;
                }
                curLineBeginY = getCurLineBeginY(0);
                float lineSpacingMultiplier = ((getLineSpacingMultiplier() - 1.0f) * lineMaxWidth) + getLineSpacingExtra();
                characterInstance = BreakIterator.getCharacterInstance();
                characterInstance.setText(showText);
                float f6 = width;
                i2 = 0;
                while (true) {
                    if (i2 < charArray.length) {
                        iSaveLayer = iSaveLayer;
                        break;
                    }
                    clusterStep = getClusterStep(characterInstance, i2, charArray.length);
                    cArr = charArray;
                    zNeedRotate = this.mVerticalStaticLayout.needRotate(showText, i2, clusterStep);
                    int iSave = canvas2.save();
                    if (zNeedRotate) {
                        canvas2.rotate(90.0f, width, curLineBeginY);
                    }
                    float[] charWH = this.mVerticalStaticLayout.getCharWH(showText, i2);
                    f = charWH[0];
                    f2 = charWH[1];
                    if (zNeedRotate) {
                        fMax = Math.max(0.0f, width);
                    } else {
                        fMax = Math.max(0.0f, ((this.mVerticalStaticLayout.getLineMaxWidth() - f) / 2.0f) + width);
                    }
                    if (zNeedRotate) {
                        i3 = fontMetricsInt.descent;
                    } else {
                        i3 = fontMetricsInt.ascent;
                    }
                    f3 = curLineBeginY - i3;
                    if (shouldDrawCharacter(cArr[i2])) {
                        cArr2 = cArr;
                        i5 = i2;
                        i4 = clusterStep;
                        canvas2 = canvas;
                        canvas2.drawText(cArr2, i5, i4, fMax, f3, paint);
                    } else {
                        i4 = clusterStep;
                        canvas2 = canvas;
                        i5 = i2;
                        cArr2 = cArr;
                    }
                    canvas2.restoreToCount(iSave);
                    i6 = i4 + i5;
                    if (i6 < cArr2.length) {
                        curLineBeginY = curLineBeginY;
                    } else if (i5 + 1 > this.mVerticalStaticLayout.getLineBreak(i8) || (i7 = i8 + 1) >= lineCount) {
                        clusterStep2 = getClusterStep(characterInstance, i6, cArr2.length);
                        charSpacingMultiplier = ((this.mVerticalStaticLayout.getCharSpacingMultiplier() - 1.0f) * f2) + this.mVerticalStaticLayout.getCharSpacingExtra(r27);
                        if (zNeedRotate == this.mVerticalStaticLayout.needRotate(showText, i6, clusterStep2)) {
                            f2 = charSpacingMultiplier + f2;
                        }
                        curLineBeginY += f2;
                    } else {
                        if (i7 >= showedLineCount) {
                            break;
                        }
                        f6 -= lineSpacingMultiplier + lineMaxWidth;
                        curLineBeginY = getCurLineBeginY(i7);
                        i8 = i7;
                        width = f6;
                    }
                    charArray = cArr2;
                    showText = showText;
                    characterInstance = characterInstance;
                    fontMetricsInt = fontMetricsInt;
                    iSaveLayer = iSaveLayer;
                    i2 = i6;
                    width = width;
                }
                canvas2.restore();
            }
            width = paddingLeft - lineMaxWidth;
            curLineBeginY = getCurLineBeginY(0);
            float lineSpacingMultiplier2 = ((getLineSpacingMultiplier() - 1.0f) * lineMaxWidth) + getLineSpacingExtra();
            characterInstance = BreakIterator.getCharacterInstance();
            characterInstance.setText(showText);
            float f7 = width;
            i2 = 0;
            while (true) {
                if (i2 < charArray.length) {
                    iSaveLayer = iSaveLayer;
                    break;
                }
                clusterStep = getClusterStep(characterInstance, i2, charArray.length);
                cArr = charArray;
                zNeedRotate = this.mVerticalStaticLayout.needRotate(showText, i2, clusterStep);
                int iSave2 = canvas2.save();
                if (zNeedRotate) {
                    canvas2.rotate(90.0f, width, curLineBeginY);
                }
                float[] charWH2 = this.mVerticalStaticLayout.getCharWH(showText, i2);
                f = charWH2[0];
                f2 = charWH2[1];
                if (zNeedRotate) {
                    fMax = Math.max(0.0f, width);
                } else {
                    fMax = Math.max(0.0f, ((this.mVerticalStaticLayout.getLineMaxWidth() - f) / 2.0f) + width);
                }
                if (zNeedRotate) {
                    i3 = fontMetricsInt.descent;
                } else {
                    i3 = fontMetricsInt.ascent;
                }
                f3 = curLineBeginY - i3;
                if (shouldDrawCharacter(cArr[i2])) {
                    cArr2 = cArr;
                    i5 = i2;
                    i4 = clusterStep;
                    canvas2 = canvas;
                    canvas2.drawText(cArr2, i5, i4, fMax, f3, paint);
                } else {
                    i4 = clusterStep;
                    canvas2 = canvas;
                    i5 = i2;
                    cArr2 = cArr;
                }
                canvas2.restoreToCount(iSave2);
                i6 = i4 + i5;
                if (i6 < cArr2.length) {
                    curLineBeginY = curLineBeginY;
                } else if (i5 + 1 > this.mVerticalStaticLayout.getLineBreak(i8)) {
                    clusterStep2 = getClusterStep(characterInstance, i6, cArr2.length);
                    charSpacingMultiplier = ((this.mVerticalStaticLayout.getCharSpacingMultiplier() - 1.0f) * f2) + this.mVerticalStaticLayout.getCharSpacingExtra(r27);
                    if (zNeedRotate == this.mVerticalStaticLayout.needRotate(showText, i6, clusterStep2)) {
                        f2 = charSpacingMultiplier + f2;
                    }
                    curLineBeginY += f2;
                } else {
                    clusterStep2 = getClusterStep(characterInstance, i6, cArr2.length);
                    charSpacingMultiplier = ((this.mVerticalStaticLayout.getCharSpacingMultiplier() - 1.0f) * f2) + this.mVerticalStaticLayout.getCharSpacingExtra(r27);
                    if (zNeedRotate == this.mVerticalStaticLayout.needRotate(showText, i6, clusterStep2)) {
                        f2 = charSpacingMultiplier + f2;
                    }
                    curLineBeginY += f2;
                }
                charArray = cArr2;
                showText = showText;
                characterInstance = characterInstance;
                fontMetricsInt = fontMetricsInt;
                iSaveLayer = iSaveLayer;
                i2 = i6;
                width = width;
            }
            canvas2.restore();
        } else {
            super.onDraw(canvas);
            iSaveLayer = iSaveLayer;
        }
        Drawable drawable = this.mMaskDrawable;
        if (drawable != null) {
            drawable.draw(canvas2);
        }
        if (this.mScale != -1082130432) {
            canvas2.restoreToCount(i);
        }
        canvas2.restoreToCount(iSaveLayer);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (isVerticalMode()) {
            autoJustTextSize();
        }
        if (isVerticalMode() || !isNeedsAutoFormatText()) {
            return;
        }
        TextFormat textFormat = getTextFormat();
        if (textFormat.compareTo(this.mTextFormat) <= 0) {
            if (textFormat.compareTo(this.mTextFormat) < 0) {
                setTextSize(0, getTextSize() + 1.0f);
                return;
            }
            return;
        }
        int iCeil = ((int) Math.ceil(getTextSize())) + 1;
        this.mAutoSizeTextSizesInPx = new int[iCeil];
        for (int i5 = 0; i5 < iCeil; i5++) {
            this.mAutoSizeTextSizesInPx[i5] = i5;
        }
        int[] iArr = this.mAutoSizeTextSizesInPx;
        setTextSize(0, iArr[Math.max(Math.min(autoJustTextFormat(iArr.length - 1, 0), this.mAutoSizeTextSizesInPx.length - 1), 0)]);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mIsVerticalMode) {
            initDimentionsOnMeasure(i, i2);
            setMeasuredDimension((int) this.mTMeasuredWidth, (int) this.mTMeasuredHeight);
        }
    }

    public void reMeasureText(int i, int i2) {
        this.mVerticalStaticLayout = new VerticalStaticLayout(getText(), getPaint(), (i - getPaddingLeft()) - getPaddingRight(), (i2 - getPaddingTop()) - getPaddingBottom(), getLineSpacingMultiplier(), getLineSpacingExtra(), getCJKVerticalSpacingMultiplier(), getCJKVerticalLineSpacingExtra());
    }

    public void setAutoFormatText(boolean z, TextFormat textFormat) {
        this.mNeedsAutoFormatText = z;
        this.mTextFormat = textFormat;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    @SuppressLint({"RestrictedApi"})
    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        if (!isVerticalMode()) {
            this.mNeedsAutoSizeText = true;
            super.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
            return;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float fApplyDimension = TypedValue.applyDimension(i4, i, displayMetrics);
        float fApplyDimension2 = TypedValue.applyDimension(i4, i2, displayMetrics);
        float fApplyDimension3 = TypedValue.applyDimension(i4, i3, displayMetrics);
        int iFloor = ((int) Math.floor((fApplyDimension2 - fApplyDimension) / fApplyDimension3)) + 1;
        int[] iArr = new int[iFloor];
        for (int i5 = 0; i5 < iFloor; i5++) {
            iArr[i5] = Math.round((i5 * fApplyDimension3) + fApplyDimension);
        }
        this.mNeedsAutoSizeText = true;
        this.mAutoSizeTextSizesInPx = iArr;
        autoJustTextSize();
    }

    public void setCJKVerticalSpace(float f, float f2) {
        this.mCJKspacingAdd = f;
        this.mCJKspacingMult = f2;
        requestLayout();
    }

    public void setDrawableMask(@DrawableRes int i) {
        setDrawableMask(i, 1);
    }

    public void setDrawableMask(@DrawableRes int i, @ShaderUtils.IShaderMode int i2) {
        if (this.mDrawableMask == i && this.mShaderMode == i2) {
            return;
        }
        this.mShaderMode = i2;
        this.mDrawableMask = i;
        this.mMaskDrawable = null;
        ViewUtilsKt.doOnMiuiPreDraw(this, new Function1() { // from class: com.miui.aod.components.view.VerticalTextView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return VerticalTextView.$r8$lambda$18_jt8iX4exXCAHGVwf_Ufp2MWo(this.f$0, (MiuiPreDrawListener) obj);
            }
        });
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        this.mNeedsAutoSizeText = false;
    }

    public void setTextSize(int i, float f, boolean z) {
        super.setTextSize(i, f);
        if (z) {
            this.mNeedsAutoSizeText = false;
        }
    }

    public void setVerticalMode(boolean z) {
        this.mIsVerticalMode = z;
        requestLayout();
    }
}
