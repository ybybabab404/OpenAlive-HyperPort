package com.miui.miwallpaper.material.palette;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseBooleanArray;
import androidx.core.graphics.ColorUtils;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class Palette {
    public static final Filter DEFAULT_FILTER = new Filter() { // from class: com.miui.miwallpaper.material.palette.Palette.1
        @Override // com.miui.miwallpaper.material.palette.Palette.Filter
        public boolean isAllowed(int i, float[] fArr) {
            return (isWhite(fArr) || isBlack(fArr) || isNearRedILine(fArr)) ? false : true;
        }

        public final boolean isBlack(float[] fArr) {
            return fArr[2] <= 0.05f;
        }

        public final boolean isNearRedILine(float[] fArr) {
            float f = fArr[0];
            return f >= 10.0f && f <= 37.0f && fArr[1] <= 0.82f;
        }

        public final boolean isWhite(float[] fArr) {
            return fArr[2] >= 0.95f;
        }
    };
    public final List mSwatches;
    public final List mTargets;
    public final SparseBooleanArray mUsedColors = new SparseBooleanArray();
    public final Map mSelectedSwatches = new ArrayMap();
    public final Swatch mDominantSwatch = findDominantSwatch();

    public static final class Builder {
        public final Bitmap mBitmap;
        public final List mFilters;
        public int mMaxColors;
        public Rect mRegion;
        public int mResizeArea;
        public int mResizeMaxDimension;
        public final List mSwatches;
        public final List mTargets;

        /* JADX INFO: renamed from: com.miui.miwallpaper.material.palette.Palette$Builder$1, reason: invalid class name */
        class AnonymousClass1 extends AsyncTask<Bitmap, Void, Palette> {
            public final /* synthetic */ Builder this$0;
            public final /* synthetic */ PaletteAsyncListener val$listener;

            @Override // android.os.AsyncTask
            public Palette doInBackground(Bitmap... bitmapArr) {
                try {
                    return this.this$0.generate();
                } catch (Exception e) {
                    Log.e("Palette", "Exception thrown during async generate", e);
                    return null;
                }
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(Palette palette) {
                this.val$listener.onGenerated(palette);
            }
        }

        public Palette generate() {
            List arrayList;
            Filter[] filterArr;
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                Bitmap bitmapScaleBitmapDown = scaleBitmapDown(bitmap);
                Rect rect = this.mRegion;
                if (bitmapScaleBitmapDown != this.mBitmap && rect != null) {
                    double width = ((double) bitmapScaleBitmapDown.getWidth()) / ((double) this.mBitmap.getWidth());
                    rect.left = (int) Math.floor(((double) rect.left) * width);
                    rect.top = (int) Math.floor(((double) rect.top) * width);
                    rect.right = Math.min((int) Math.ceil(((double) rect.right) * width), bitmapScaleBitmapDown.getWidth());
                    rect.bottom = Math.min((int) Math.ceil(((double) rect.bottom) * width), bitmapScaleBitmapDown.getHeight());
                }
                int[] pixelsFromBitmap = getPixelsFromBitmap(bitmapScaleBitmapDown);
                int i = this.mMaxColors;
                if (this.mFilters.isEmpty()) {
                    filterArr = null;
                } else {
                    List list = this.mFilters;
                    filterArr = (Filter[]) list.toArray(new Filter[list.size()]);
                }
                ColorCutQuantizer colorCutQuantizer = new ColorCutQuantizer(pixelsFromBitmap, i, filterArr);
                if (bitmapScaleBitmapDown != this.mBitmap) {
                    bitmapScaleBitmapDown.recycle();
                }
                arrayList = new ArrayList(colorCutQuantizer.getQuantizedColors());
            } else {
                arrayList = this.mSwatches;
            }
            Palette palette = new Palette(arrayList, this.mTargets);
            palette.generate();
            return palette;
        }

        public final int[] getPixelsFromBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[width * height];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            Rect rect = this.mRegion;
            if (rect == null) {
                return iArr;
            }
            int iWidth = rect.width();
            int iHeight = this.mRegion.height();
            int[] iArr2 = new int[iWidth * iHeight];
            for (int i = 0; i < iHeight; i++) {
                Rect rect2 = this.mRegion;
                System.arraycopy(iArr, ((rect2.top + i) * width) + rect2.left, iArr2, i * iWidth, iWidth);
            }
            return iArr2;
        }

        public final Bitmap scaleBitmapDown(Bitmap bitmap) {
            int iMax;
            int i;
            double dSqrt = -1.0d;
            if (this.mResizeArea > 0) {
                int width = bitmap.getWidth() * bitmap.getHeight();
                int i2 = this.mResizeArea;
                if (width > i2) {
                    dSqrt = Math.sqrt(((double) i2) / ((double) width));
                }
            } else if (this.mResizeMaxDimension > 0 && (iMax = Math.max(bitmap.getWidth(), bitmap.getHeight())) > (i = this.mResizeMaxDimension)) {
                dSqrt = ((double) i) / ((double) iMax);
            }
            return dSqrt <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE ? bitmap : Bitmap.createScaledBitmap(bitmap, (int) Math.ceil(((double) bitmap.getWidth()) * dSqrt), (int) Math.ceil(((double) bitmap.getHeight()) * dSqrt), false);
        }
    }

    public interface Filter {
        boolean isAllowed(int i, float[] fArr);
    }

    public interface PaletteAsyncListener {
        void onGenerated(Palette palette);
    }

    public static final class Swatch {
        public final int mBlue;
        public int mBodyTextColor;
        public boolean mGeneratedTextColors;
        public final int mGreen;
        public float[] mHsl;
        public final int mPopulation;
        public final int mRed;
        public final int mRgb;
        public int mTitleTextColor;

        public Swatch(int i, int i2) {
            this.mRed = Color.red(i);
            this.mGreen = Color.green(i);
            this.mBlue = Color.blue(i);
            this.mRgb = i;
            this.mPopulation = i2;
        }

        public final void ensureTextColorsGenerated() {
            if (this.mGeneratedTextColors) {
                return;
            }
            int iCalculateMinimumAlpha = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 4.5f);
            int iCalculateMinimumAlpha2 = ColorUtils.calculateMinimumAlpha(-1, this.mRgb, 3.0f);
            if (iCalculateMinimumAlpha != -1 && iCalculateMinimumAlpha2 != -1) {
                this.mBodyTextColor = ColorUtils.setAlphaComponent(-1, iCalculateMinimumAlpha);
                this.mTitleTextColor = ColorUtils.setAlphaComponent(-1, iCalculateMinimumAlpha2);
                this.mGeneratedTextColors = true;
                return;
            }
            int iCalculateMinimumAlpha3 = ColorUtils.calculateMinimumAlpha(-16777216, this.mRgb, 4.5f);
            int iCalculateMinimumAlpha4 = ColorUtils.calculateMinimumAlpha(-16777216, this.mRgb, 3.0f);
            if (iCalculateMinimumAlpha3 == -1 || iCalculateMinimumAlpha4 == -1) {
                this.mBodyTextColor = iCalculateMinimumAlpha != -1 ? ColorUtils.setAlphaComponent(-1, iCalculateMinimumAlpha) : ColorUtils.setAlphaComponent(-16777216, iCalculateMinimumAlpha3);
                this.mTitleTextColor = iCalculateMinimumAlpha2 != -1 ? ColorUtils.setAlphaComponent(-1, iCalculateMinimumAlpha2) : ColorUtils.setAlphaComponent(-16777216, iCalculateMinimumAlpha4);
                this.mGeneratedTextColors = true;
            } else {
                this.mBodyTextColor = ColorUtils.setAlphaComponent(-16777216, iCalculateMinimumAlpha3);
                this.mTitleTextColor = ColorUtils.setAlphaComponent(-16777216, iCalculateMinimumAlpha4);
                this.mGeneratedTextColors = true;
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && Swatch.class == obj.getClass()) {
                Swatch swatch = (Swatch) obj;
                if (this.mPopulation == swatch.mPopulation && this.mRgb == swatch.mRgb) {
                    return true;
                }
            }
            return false;
        }

        public int getBodyTextColor() {
            ensureTextColorsGenerated();
            return this.mBodyTextColor;
        }

        public float[] getHsl() {
            if (this.mHsl == null) {
                this.mHsl = new float[3];
            }
            ColorUtils.RGBToHSL(this.mRed, this.mGreen, this.mBlue, this.mHsl);
            return this.mHsl;
        }

        public int getPopulation() {
            return this.mPopulation;
        }

        public int getRgb() {
            return this.mRgb;
        }

        public int getTitleTextColor() {
            ensureTextColorsGenerated();
            return this.mTitleTextColor;
        }

        public int hashCode() {
            return (this.mRgb * 31) + this.mPopulation;
        }

        public String toString() {
            return Swatch.class.getSimpleName() + " [RGB: #" + Integer.toHexString(getRgb()) + "] [HSL: " + Arrays.toString(getHsl()) + "] [Population: " + this.mPopulation + "] [Title Text: #" + Integer.toHexString(getTitleTextColor()) + "] [Body Text: #" + Integer.toHexString(getBodyTextColor()) + ']';
        }
    }

    public Palette(List list, List list2) {
        this.mSwatches = list;
        this.mTargets = list2;
    }

    public final Swatch findDominantSwatch() {
        int size = this.mSwatches.size();
        int population = Integer.MIN_VALUE;
        Swatch swatch = null;
        for (int i = 0; i < size; i++) {
            Swatch swatch2 = (Swatch) this.mSwatches.get(i);
            if (swatch2.getPopulation() > population) {
                population = swatch2.getPopulation();
                swatch = swatch2;
            }
        }
        return swatch;
    }

    public void generate() {
        int size = this.mTargets.size();
        for (int i = 0; i < size; i++) {
            Target target = (Target) this.mTargets.get(i);
            target.normalizeWeights();
            this.mSelectedSwatches.put(target, generateScoredTarget(target));
        }
        this.mUsedColors.clear();
    }

    public final float generateScore(Swatch swatch, Target target) {
        float[] hsl = swatch.getHsl();
        Swatch swatch2 = this.mDominantSwatch;
        return (target.getSaturationWeight() > 0.0f ? target.getSaturationWeight() * (1.0f - Math.abs(hsl[1] - target.getTargetSaturation())) : 0.0f) + (target.getLightnessWeight() > 0.0f ? target.getLightnessWeight() * (1.0f - Math.abs(hsl[2] - target.getTargetLightness())) : 0.0f) + (target.getPopulationWeight() > 0.0f ? target.getPopulationWeight() * (swatch.getPopulation() / (swatch2 != null ? swatch2.getPopulation() : 1)) : 0.0f);
    }

    public final Swatch generateScoredTarget(Target target) {
        Swatch maxScoredSwatchForTarget = getMaxScoredSwatchForTarget(target);
        if (maxScoredSwatchForTarget != null && target.isExclusive()) {
            this.mUsedColors.append(maxScoredSwatchForTarget.getRgb(), true);
        }
        return maxScoredSwatchForTarget;
    }

    public final Swatch getMaxScoredSwatchForTarget(Target target) {
        int size = this.mSwatches.size();
        float f = 0.0f;
        Swatch swatch = null;
        for (int i = 0; i < size; i++) {
            Swatch swatch2 = (Swatch) this.mSwatches.get(i);
            if (shouldBeScoredForTarget(swatch2, target)) {
                float fGenerateScore = generateScore(swatch2, target);
                if (swatch == null || fGenerateScore > f) {
                    swatch = swatch2;
                    f = fGenerateScore;
                }
            }
        }
        return swatch;
    }

    public final boolean shouldBeScoredForTarget(Swatch swatch, Target target) {
        float[] hsl = swatch.getHsl();
        return hsl[1] >= target.getMinimumSaturation() && hsl[1] <= target.getMaximumSaturation() && hsl[2] >= target.getMinimumLightness() && hsl[2] <= target.getMaximumLightness() && !this.mUsedColors.get(swatch.getRgb());
    }
}
