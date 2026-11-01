package com.miui.miwallpaper.material.utils.clockdesign;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.R;
import com.miui.miwallpaper.material.utils.PartRectColorUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: classes2.dex */
public class RectCalculable {
    public static final RectCalculable CLASSIC;
    public static final RectCalculable CLASSIC_MAX;
    public static final RectCalculable CLASSIC_PLUS;
    public static final RectCalculable DEPTH_PETS;
    public static final RectCalculable DOODLE;
    public static final RectCalculable EASTERN_A;
    public static final RectCalculable EASTERN_B;
    public static final RectCalculable EASTERN_C;
    public static final RectCalculable MAGAZINE_A;
    public static final RectCalculable MAGAZINE_B;
    public static final RectCalculable MAGAZINE_C;
    public static final RectCalculable OVERSIZE_A;
    public static final RectCalculable OVERSIZE_B;
    public static final RectCalculable PAD_EXCLUSIVE_A;
    public static final RectCalculable PAD_EXCLUSIVE_B;
    public static final RectCalculable PAD_EXCLUSIVE_C;
    public static final RectCalculable RHOMBUS;
    public static final RectCalculable SMART_FRAME;
    public final String type;
    public static final /* synthetic */ RectCalculable[] $VALUES = $values();
    public static final Map TYPE_CACHE = new HashMap(values().length);

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$1, reason: invalid class name */
    public final enum AnonymousClass1 extends RectCalculable {
        public AnonymousClass1(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            float f7 = (int) (f2 - f4);
            ClockDesignInfo.ClassicDesign classicDesign = ClockDesignInfo.CLASSIC_CLOCK_DESIGN;
            int i2 = (int) ((classicDesign.top * f7) / f6);
            int i3 = (int) ((f7 * classicDesign.bottom) / f6);
            int i4 = (int) (((f - f3) * classicDesign.width) / f5);
            Rect rect = new Rect(0, i2, i4, i3);
            if (f3 >= 1.0f) {
                int i5 = (int) (f3 / 2.0f);
                rect.set(i5, i2, (int) (f - i5), i3);
            } else if (f4 > 1.0f) {
                int i6 = (int) (f4 / 2.0f);
                rect.set(0, i2 + i6, i4, i6 + i3);
            }
            PartRectColorUtils.setClockStyleRect(z, rect);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$10, reason: invalid class name */
    public final enum AnonymousClass10 extends RectCalculable {
        public AnonymousClass10(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            int i2 = (int) (f2 - f4);
            ClockDesignInfo.OversizeACapsuleDesign oversizeACapsuleDesign = ClockDesignInfo.OVERSIZE_A_CAPSULE_DESIGN;
            PartRectColorUtils.setOversizeCapsuleRect(z, new Rect((int) (f - ((int) ((((int) (f - f3)) * oversizeACapsuleDesign.width) / f5))), 0, (int) f, (int) ((i2 * oversizeACapsuleDesign.height) / f6)));
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$11, reason: invalid class name */
    public final enum AnonymousClass11 extends RectCalculable {
        public AnonymousClass11(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$12, reason: invalid class name */
    public final enum AnonymousClass12 extends RectCalculable {
        public AnonymousClass12(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            ClockDesignInfo.RhombusDesign rhombusDesign = ClockDesignInfo.RHOMBUS_CLOCK_DESIGN;
            int i2 = (int) (((f - f3) * rhombusDesign.width) / f5);
            float f7 = (int) (((f2 - f4) * rhombusDesign.height) / f6);
            float f8 = i2;
            PartRectColorUtils.setClockStyleRect(z, new Rect(((int) (f - f8)) / 2, ((int) (f2 - f7)) / 2, ((int) (f + f8)) / 2, ((int) (f2 + f7)) / 2));
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$13, reason: invalid class name */
    public final enum AnonymousClass13 extends RectCalculable {
        public AnonymousClass13(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            int i2;
            int i3;
            int i4;
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            int i5 = (int) (f - f3);
            int i6 = (int) (f2 - f4);
            int i7 = iArr[0];
            int i8 = iArr[1];
            if (i7 == -1 || i8 == -1) {
                int[] currentLockScreenInfo = PartRectColorUtils.getCurrentLockScreenInfo(context);
                i2 = currentLockScreenInfo[0];
                int i9 = currentLockScreenInfo[1];
                Log.d("RectCalculable", "MAGAZINE_A computeImageRect: clockTypeInfo" + Arrays.toString(currentLockScreenInfo));
                i3 = i9;
            } else {
                i3 = i8;
                i2 = i7;
            }
            float f7 = i5;
            float f8 = i6;
            int[] iArrComputeMagazineAPosition = RectCalculable.computeMagazineAPosition("clockStyle", z, i, i2, f, f2, f5, f6, f7, f8);
            switch (i3) {
                case 102:
                case R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 105 */:
                    i4 = 3;
                    break;
                case 103:
                default:
                    i4 = 1;
                    break;
                case 104:
                case R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /* 106 */:
                    i4 = 2;
                    break;
            }
            int[] iArrComputeMagazineAPosition2 = RectCalculable.computeMagazineAPosition("", z, i, i4, f, f2, f5, f6, f7, f8);
            Rect rect = new Rect(iArrComputeMagazineAPosition[0], iArrComputeMagazineAPosition[1], iArrComputeMagazineAPosition[2], iArrComputeMagazineAPosition[3]);
            Rect rect2 = new Rect(iArrComputeMagazineAPosition2[0], iArrComputeMagazineAPosition2[1], iArrComputeMagazineAPosition2[2], iArrComputeMagazineAPosition2[3]);
            if (f3 < 1.0f && f4 > 1.0f) {
                int i10 = (int) (f4 / 2.0f);
                rect.set(iArrComputeMagazineAPosition[0], iArrComputeMagazineAPosition[1] + i10, iArrComputeMagazineAPosition[2], iArrComputeMagazineAPosition[3] + i10);
                rect2.set(iArrComputeMagazineAPosition2[0], iArrComputeMagazineAPosition2[1] + i10, iArrComputeMagazineAPosition2[2], iArrComputeMagazineAPosition2[3] + i10);
            }
            PartRectColorUtils.setMagazineScriptRect(z, rect2);
            PartRectColorUtils.setClockStyleRect(z, rect);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$14, reason: invalid class name */
    public final enum AnonymousClass14 extends RectCalculable {
        public AnonymousClass14(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            int i2 = (int) (f - f3);
            float f7 = (int) (f2 - f4);
            ClockDesignInfo.MagazineDesign magazineDesign = ClockDesignInfo.MAGAZINE_B_CLOCK_DESIGN;
            int i3 = (int) ((magazineDesign.top * f7) / f6);
            int i4 = ((int) ((magazineDesign.height * f7) / f6)) + i3;
            ClockDesignInfo.MagazineDesign magazineDesign2 = ClockDesignInfo.MAGAZINE_B_SCRIPT_DESIGN;
            int i5 = (int) ((magazineDesign2.top * f7) / f6);
            if (!z && PartRectColorUtils.isPad()) {
                i5 = (int) ((ClockDesignInfo.MAGAZINE_B_SCRIPT_DESIGN_PAD.top * f7) / f6);
            }
            int i6 = ((int) ((f7 * magazineDesign2.height) / f6)) + i5;
            float f8 = (int) ((i2 * magazineDesign2.width) / f5);
            int i7 = (int) ((f - f8) / 2.0f);
            int i8 = (int) ((f8 + f) / 2.0f);
            int i9 = (int) f;
            Rect rect = new Rect(0, i3, i9, i4);
            Rect rect2 = new Rect(i7, i5, i8, i6);
            if (f3 >= 1.0f) {
                int i10 = (int) (f3 / 2.0f);
                rect.set(i10, i3, (int) (f - i10), i4);
            } else if (f4 > 1.0f) {
                int i11 = (int) (f4 / 2.0f);
                rect.set(0, i3 + i11, i9, i4 + i11);
                rect2.set(i7, i5 + i11, i8, i6 + i11);
            }
            PartRectColorUtils.setMagazineScriptRect(z, rect2);
            PartRectColorUtils.setClockStyleRect(z, rect);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$15, reason: invalid class name */
    public final enum AnonymousClass15 extends RectCalculable {
        public AnonymousClass15(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$16, reason: invalid class name */
    public final enum AnonymousClass16 extends RectCalculable {
        public AnonymousClass16(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            float f7;
            float f8;
            float f9;
            float f10;
            float f11;
            float f12;
            if (PartRectColorUtils.getWallpaperEffectType(context, 2) == 0) {
                PartRectColorUtils.computeCommonStatusBarRect(z, f, f2, f3, f4, f5, f6, ClockDesignInfo.STATUS_BAR_DESIGN);
                f7 = f;
                f8 = f2;
                f9 = f3;
                f10 = f4;
                f11 = f5;
                f12 = f6;
            } else {
                f7 = f;
                f8 = f2;
                f9 = f3;
                f10 = f4;
                f11 = f5;
                f12 = f6;
            }
            PartRectColorUtils.computeIconAndFingerPrintRect(z, f7, f8, f9, f10, f11, f12);
            PartRectColorUtils.computeFullImageRect(f7, f8, f9, f10);
            PartRectColorUtils.computeCommonClockStyleRect(z, f7, f8, f9, f10, f11, f12);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$17, reason: invalid class name */
    public final enum AnonymousClass17 extends RectCalculable {
        public AnonymousClass17(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$18, reason: invalid class name */
    public final enum AnonymousClass18 extends RectCalculable {
        public AnonymousClass18(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$2, reason: invalid class name */
    public final enum AnonymousClass2 extends RectCalculable {
        public AnonymousClass2(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            float f7 = (int) (f2 - f4);
            ClockDesignInfo.ClassicDesign classicDesign = ClockDesignInfo.CLASSIC_PLUS_DESIGN;
            int i2 = (int) ((classicDesign.top * f7) / f6);
            int i3 = (int) ((f7 * classicDesign.bottom) / f6);
            float f8 = (int) (((f - f3) * classicDesign.width) / f5);
            int i4 = ((int) (f - f8)) / 2;
            int i5 = ((int) (f + f8)) / 2;
            Rect rect = new Rect(i4, i2, i5, i3);
            if (f3 < 1.0f && f4 > 1.0f) {
                int i6 = (int) (f4 / 2.0f);
                rect.set(i4, i2 + i6, i5, i3 + i6);
            }
            PartRectColorUtils.setClockStyleRect(z, rect);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$3, reason: invalid class name */
    public final enum AnonymousClass3 extends RectCalculable {
        public AnonymousClass3(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            int[] iArrComputeClassicMaxClockPosition = RectCalculable.computeClassicMaxClockPosition(z, i, f, f5, f6, f3, f4, f - f3, f2 - f4);
            PartRectColorUtils.setClockStyleRect(z, new Rect(iArrComputeClassicMaxClockPosition[0], iArrComputeClassicMaxClockPosition[1], iArrComputeClassicMaxClockPosition[2], iArrComputeClassicMaxClockPosition[3]));
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public ClockDesignInfo.ClockDesign getDesignParams(int i, boolean z, int i2) {
            if (i == 2) {
                return z ? ClockDesignInfo.CLASSIC_MAX_CLOCK_DESIGN_FOLD_LANSCAPE : ClockDesignInfo.CLASSIC_MAX_CLOCK_DESIGN_FOLD;
            }
            if (i != 4) {
                return ClockDesignInfo.CLASSIC_MAX_CLOCK_DESIGN;
            }
            return z ? ClockDesignInfo.CLASSIC_MAX_CLOCK_DESIGN_PAD_LANSCAPE : ClockDesignInfo.CLASSIC_MAX_CLOCK_DESIGN_PAD;
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$4, reason: invalid class name */
    public final enum AnonymousClass4 extends RectCalculable {
        public AnonymousClass4(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            RectCalculable.computeEasternImageRect(z, i, iArr, str, f, f2, f3, f4, f5, f6);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public ClockDesignInfo.ClockDesign getDesignParams(int i, boolean z, int i2) {
            Log.d("RectCalculable", "EASTERN_C getDesignParams: style" + i2);
            if (i2 == 1) {
                if (i == 2) {
                    return z ? ClockDesignInfo.EASTERN_C_CLOCK_DESIGN_FOLD_LANDSCAPE : ClockDesignInfo.EASTERN_C_CLOCK_DESIGN_FOLD;
                }
                if (i != 4) {
                    return ClockDesignInfo.EASTERN_C_CLOCK_DESIGN;
                }
                return z ? ClockDesignInfo.EASTERN_C_CLOCK_DESIGN_PAD_LANDSCAPE : ClockDesignInfo.EASTERN_C_CLOCK_DESIGN_PAD;
            }
            if (i == 2) {
                return z ? ClockDesignInfo.EASTERN_C_CLOCK_DESIGN2_FOLD_LANDSCAPE : ClockDesignInfo.EASTERN_C_CLOCK_DESIGN2_FOLD;
            }
            if (i != 4) {
                return ClockDesignInfo.EASTERN_C_CLOCK_DESIGN2;
            }
            return z ? ClockDesignInfo.EASTERN_C_CLOCK_DESIGN2_PAD_LANDSCAPE : ClockDesignInfo.EASTERN_C_CLOCK_DESIGN2_PAD;
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$5, reason: invalid class name */
    public final enum AnonymousClass5 extends RectCalculable {
        public AnonymousClass5(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            RectCalculable.computeEasternImageRect(z, i, iArr, str, f, f2, f3, f4, f5, f6);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public ClockDesignInfo.ClockDesign getDesignParams(int i, boolean z, int i2) {
            if (i == 2) {
                return z ? ClockDesignInfo.EASTERN_B_CLOCK_DESIGN_FOLD_LANDSCAPE : ClockDesignInfo.EASTERN_B_CLOCK_DESIGN_FOLD;
            }
            if (i != 4) {
                return ClockDesignInfo.EASTERN_B_CLOCK_DESIGN;
            }
            return z ? ClockDesignInfo.EASTERN_B_CLOCK_DESIGN_PAD_LANDSCAPE : ClockDesignInfo.EASTERN_B_CLOCK_DESIGN_PAD;
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$6, reason: invalid class name */
    public final enum AnonymousClass6 extends RectCalculable {
        public AnonymousClass6(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            RectCalculable.computeEasternImageRect(z, i, iArr, str, f, f2, f3, f4, f5, f6);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public ClockDesignInfo.ClockDesign getDesignParams(int i, boolean z, int i2) {
            if (i == 2) {
                return z ? ClockDesignInfo.EASTERN_A_CLOCK_DESIGN_FOLD_LANDSCAPE : ClockDesignInfo.EASTERN_A_CLOCK_DESIGN_FOLD;
            }
            if (i != 4) {
                return ClockDesignInfo.EASTERN_A_CLOCK_DESIGN;
            }
            return z ? ClockDesignInfo.EASTERN_A_CLOCK_DESIGN_PAD_LANDSCAPE : ClockDesignInfo.EASTERN_A_CLOCK_DESIGN_PAD;
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$7, reason: invalid class name */
    public final enum AnonymousClass7 extends RectCalculable {
        public AnonymousClass7(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$8, reason: invalid class name */
    public final enum AnonymousClass8 extends RectCalculable {
        public AnonymousClass8(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.material.utils.clockdesign.RectCalculable$9, reason: invalid class name */
    public final enum AnonymousClass9 extends RectCalculable {
        public AnonymousClass9(String str, int i, String str2) {
            super(str, i, str2);
        }

        @Override // com.miui.miwallpaper.material.utils.clockdesign.RectCalculable
        public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
            super.computeImageRect(z, i, context, str, iArr, f, f2, f3, f4, f5, f6);
            PartRectColorUtils.computeCommonClockStyleRect(z, f, f2, f3, f4, f5, f6);
        }
    }

    public static /* synthetic */ RectCalculable[] $values() {
        return new RectCalculable[]{CLASSIC, CLASSIC_PLUS, CLASSIC_MAX, EASTERN_C, EASTERN_B, EASTERN_A, PAD_EXCLUSIVE_A, PAD_EXCLUSIVE_B, PAD_EXCLUSIVE_C, OVERSIZE_A, OVERSIZE_B, RHOMBUS, MAGAZINE_A, MAGAZINE_B, MAGAZINE_C, DOODLE, SMART_FRAME, DEPTH_PETS};
    }

    static {
        int i = 0;
        CLASSIC = new AnonymousClass1("CLASSIC", i, "classic");
        CLASSIC_PLUS = new AnonymousClass2("CLASSIC_PLUS", 1, "classic_plus");
        CLASSIC_MAX = new AnonymousClass3("CLASSIC_MAX", 2, "classic_max");
        EASTERN_C = new AnonymousClass4("EASTERN_C", 3, "eastern_c");
        EASTERN_B = new AnonymousClass5("EASTERN_B", 4, "eastern_b");
        EASTERN_A = new AnonymousClass6("EASTERN_A", 5, "eastern_a");
        PAD_EXCLUSIVE_A = new AnonymousClass7("PAD_EXCLUSIVE_A", 6, "pad_exclusive_a");
        PAD_EXCLUSIVE_B = new AnonymousClass8("PAD_EXCLUSIVE_B", 7, "pad_exclusive_b");
        PAD_EXCLUSIVE_C = new AnonymousClass9("PAD_EXCLUSIVE_C", 8, "pad_exclusive_c");
        OVERSIZE_A = new AnonymousClass10("OVERSIZE_A", 9, "oversize_a");
        OVERSIZE_B = new AnonymousClass11("OVERSIZE_B", 10, "oversize_b");
        RHOMBUS = new AnonymousClass12("RHOMBUS", 11, "rhombus");
        MAGAZINE_A = new AnonymousClass13("MAGAZINE_A", 12, "magazine_a");
        MAGAZINE_B = new AnonymousClass14("MAGAZINE_B", 13, "magazine_b");
        MAGAZINE_C = new AnonymousClass15("MAGAZINE_C", 14, "magazine_c");
        DOODLE = new AnonymousClass16("DOODLE", 15, "doodle");
        SMART_FRAME = new AnonymousClass17("SMART_FRAME", 16, "smart_frame");
        DEPTH_PETS = new AnonymousClass18("DEPTH_PETS", 17, "depth_pets");
        RectCalculable[] rectCalculableArrValues = values();
        int length = rectCalculableArrValues.length;
        while (i < length) {
            RectCalculable rectCalculable = rectCalculableArrValues[i];
            TYPE_CACHE.put(rectCalculable.getType(), rectCalculable);
            i++;
        }
    }

    public RectCalculable(String str, int i, String str2) {
        super(str, i);
        this.type = str2;
    }

    public static int[] centerHorizontallyCalculable(String str, boolean z, int i, int i2, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        ClockDesignInfo.ClockDesign designParams = fromType(str).getDesignParams(i, z, i2);
        if (designParams == null) {
            return new int[0];
        }
        float f8 = f5 / 2.0f;
        float f9 = ((designParams.top * f7) / f3) + f8;
        float f10 = (int) ((f6 * designParams.width) / f2);
        int i3 = (int) ((f - f10) / 2.0f);
        return new int[]{i3, (int) f9, (int) (i3 + f10), (int) (((designParams.bottom * f7) / f3) + f8)};
    }

    public static int[] computeClassicMaxClockPosition(boolean z, int i, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        return centerHorizontallyCalculable("classic_max", z, i, -1, f, f2, f3, f4, f5, f6, f7);
    }

    public static int[] computeEasternClockPosition(String str, boolean z, int i, int i2, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        return centerHorizontallyCalculable(str, z, i, i2, f, f2, f3, f4, f5, f6, f7);
    }

    public static void computeEasternImageRect(boolean z, int i, int[] iArr, String str, float f, float f2, float f3, float f4, float f5, float f6) {
        int[] iArrComputeEasternClockPosition = computeEasternClockPosition(str, z, i, iArr[0], f, f5, f6, f3, f4, (int) (f - f3), (int) (f2 - f4));
        PartRectColorUtils.setClockStyleRect(z, new Rect(iArrComputeEasternClockPosition[0], iArrComputeEasternClockPosition[1], iArrComputeEasternClockPosition[2], iArrComputeEasternClockPosition[3]));
    }

    public static int[] computeMagazineAPosition(String str, boolean z, int i, int i2, float f, float f2, float f3, float f4, float f5, float f6) {
        int i3;
        ClockDesignInfo.MagazineDesign magazineDesign = ClockDesignInfo.MAGAZINE_A_SCRIPT_DESIGN;
        float f7 = magazineDesign.top;
        float f8 = magazineDesign.height;
        float f9 = magazineDesign.width;
        if ("clockStyle".equals(str)) {
            ClockDesignInfo.MagazineDesign magazineDesign2 = ClockDesignInfo.MAGAZINE_A_CLOCK_DESIGN;
            f8 = magazineDesign2.height;
            f9 = magazineDesign2.width;
            if (i == 2) {
                f7 = z ? ClockDesignInfo.MAGAZINE_A_CLOCK_DESIGN_FOLD_LANDSCAPE.top : ClockDesignInfo.MAGAZINE_A_CLOCK_DESIGN_FOLD.top;
            } else if (i != 4) {
                f7 = magazineDesign2.top;
            } else {
                f7 = z ? ClockDesignInfo.MAGAZINE_A_CLOCK_DESIGN_PAD_LANDSCAPE.top : ClockDesignInfo.MAGAZINE_A_CLOCK_DESIGN_PAD.top;
            }
        }
        float f10 = (f7 * f6) / f4;
        int i4 = (int) (((f6 * f8) / f4) + f10);
        float f11 = (int) ((f5 * f9) / f3);
        int i5 = (int) f;
        if (i2 == 2) {
            i5 = (int) ((f + f11) / 2.0f);
            i3 = (int) ((f - f11) / 2.0f);
        } else if (i2 != 3) {
            i5 = (int) f11;
            i3 = 0;
        } else {
            i3 = (int) (f - f11);
        }
        return new int[]{i3, (int) f10, i5, i4};
    }

    public static RectCalculable fromType(String str) {
        return TextUtils.isEmpty(str) ? CLASSIC : (RectCalculable) TYPE_CACHE.getOrDefault(str, CLASSIC);
    }

    public static RectCalculable valueOf(String str) {
        return (RectCalculable) Enum.valueOf(RectCalculable.class, str);
    }

    public static RectCalculable[] values() {
        return (RectCalculable[]) $VALUES.clone();
    }

    public void computeImageRect(boolean z, int i, Context context, String str, int[] iArr, float f, float f2, float f3, float f4, float f5, float f6) {
        PartRectColorUtils.computeCommonStatusBarRect(z, f, f2, f3, f4, f5, f6, ClockDesignInfo.STATUS_BAR_DESIGN);
        PartRectColorUtils.computeIconAndFingerPrintRect(z, f, f2, f3, f4, f5, f6);
        PartRectColorUtils.computeFullImageRect(f, f2, f3, f4);
    }

    public ClockDesignInfo.ClockDesign getDesignParams(int i, boolean z, int i2) {
        return null;
    }

    public String getType() {
        return this.type;
    }
}
