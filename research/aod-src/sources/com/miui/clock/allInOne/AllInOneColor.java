package com.miui.clock.allInOne;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.avoid.DepthAvoidEvaluatorDebug;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.StringCompanionObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AllInOneColor.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneColor {
    public static final Companion Companion = new Companion(null);
    public ClockStyleInfo mClockInfo;
    public final Context mContext;

    /* JADX INFO: compiled from: AllInOneColor.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: AllInOneColor.kt */
    @Metadata
    public static final class FakeClockStyle extends ClockStyleInfo {
        @Override // com.miui.clock.module.ClockStyleInfo
        public int getLayoutId() {
            throw new NotImplementedError("An operation is not implemented: Not yet implemented");
        }

        @Override // com.miui.clock.module.ClockStyleInfo
        public String getType() {
            throw new NotImplementedError("An operation is not implemented: Not yet implemented");
        }
    }

    public AllInOneColor(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        this.mContext = mContext;
    }

    /* JADX WARN: Code duplicated, block: B:156:0x0501  */
    public final void applyClassicMaxPalette(int i, boolean z, Map map, boolean z2) {
        Integer numValueOf;
        int aodSecondaryColor;
        Integer numValueOf2;
        int iIntValue;
        Integer numValueOf3;
        int iIntValue2;
        int color;
        Integer numValueOf4;
        int iIntValue3;
        Integer numValueOf5;
        int iIntValue4;
        int iIntValue5;
        ClockStyleInfo clockStyleInfo = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo);
        int clockEffect = clockStyleInfo.getClockEffect();
        if (!ClockEffectUtils.isGradualType(clockEffect)) {
            if (ClockEffectUtils.isDifferenceType(clockEffect)) {
                ClockStyleInfo clockStyleInfo2 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo2);
                if (clockStyleInfo2.isAutoPrimaryColor()) {
                    Object obj = z ? "secondary15" : "secondary85";
                    if (map != null && map.get(obj) != null) {
                        Object obj2 = map.get(obj);
                        Intrinsics.checkNotNull(obj2);
                        int iIntValue6 = ((Number) obj2).intValue();
                        ClockStyleInfo clockStyleInfo3 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo3);
                        ClockStyleInfo clockStyleInfo4 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo4);
                        clockStyleInfo3.setPrimaryColor(ClockStyleInfo.isAODType(clockStyleInfo4.getDisplayType()) ? GlobalColorUtils.transformAodColor(iIntValue6) : iIntValue6);
                        ClockStyleInfo clockStyleInfo5 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo5);
                        clockStyleInfo5.setBlendColor(iIntValue6);
                        ClockStyleInfo clockStyleInfo6 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo6);
                        clockStyleInfo6.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue6));
                        ClockStyleInfo clockStyleInfo7 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo7);
                        clockStyleInfo7.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue6));
                    }
                } else {
                    ClockStyleInfo clockStyleInfo8 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo8);
                    ClockStyleInfo clockStyleInfo9 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo9);
                    clockStyleInfo8.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo9.getPrimaryColor()));
                    ClockStyleInfo clockStyleInfo10 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo10);
                    ClockStyleInfo clockStyleInfo11 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo11);
                    clockStyleInfo10.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo11.getBlendColor()));
                }
                ClockStyleInfo clockStyleInfo12 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo12);
                if (clockStyleInfo12.isAutoSecondaryColor()) {
                    String str = z ? "secondary15" : "secondary85";
                    if (map != null && map.get(str) != null) {
                        Object obj3 = map.get(str);
                        Intrinsics.checkNotNull(obj3);
                        int iIntValue7 = ((Number) obj3).intValue();
                        ClockStyleInfo clockStyleInfo13 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo13);
                        ClockStyleInfo clockStyleInfo14 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo14);
                        clockStyleInfo13.setSecondaryColor(ClockStyleInfo.isAODType(clockStyleInfo14.getDisplayType()) ? GlobalColorUtils.transformAodColor(iIntValue7) : iIntValue7);
                        ClockStyleInfo clockStyleInfo15 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo15);
                        clockStyleInfo15.setSecondaryBlendColor(iIntValue7);
                        ClockStyleInfo clockStyleInfo16 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo16);
                        clockStyleInfo16.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue7));
                        ClockStyleInfo clockStyleInfo17 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo17);
                        clockStyleInfo17.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue7));
                    }
                } else {
                    ClockStyleInfo clockStyleInfo18 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo18);
                    ClockStyleInfo clockStyleInfo19 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo19);
                    clockStyleInfo18.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo19.getSecondaryColor()));
                    ClockStyleInfo clockStyleInfo20 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo20);
                    ClockStyleInfo clockStyleInfo21 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo21);
                    clockStyleInfo20.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo21.getSecondaryBlendColor()));
                }
                ClockStyleInfo clockStyleInfo22 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo22);
                clockStyleInfo22.convertAnimationColor();
            } else if (!ClockEffectUtils.isGlowType(clockEffect) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                ClockStyleInfo clockStyleInfo23 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo23);
                if (clockStyleInfo23.isAutoPrimaryColor()) {
                    if (ClockEffectUtils.hasBlurBlendRendering(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        int color2 = Color.parseColor("#FFE6E6E6");
                        if (z) {
                            if (map == null || map.get("neutral-variant30") == null) {
                                ClockStyleInfo clockStyleInfo24 = this.mClockInfo;
                                Intrinsics.checkNotNull(clockStyleInfo24);
                                numValueOf5 = Integer.valueOf(clockStyleInfo24.getPrimaryColor());
                            } else {
                                numValueOf5 = (Integer) map.get("neutral-variant30");
                            }
                            Intrinsics.checkNotNull(numValueOf5);
                            iIntValue4 = numValueOf5.intValue();
                        } else {
                            iIntValue4 = Color.parseColor("#FFABABAB");
                        }
                        if (z) {
                            Integer numValueOf6 = (map == null || map.get("secondary70") == null) ? Integer.valueOf(iIntValue4) : (Integer) map.get("secondary70");
                            Intrinsics.checkNotNull(numValueOf6);
                            iIntValue5 = numValueOf6.intValue();
                        } else {
                            iIntValue5 = Color.parseColor("#FFBEBEBE");
                        }
                        ClockStyleInfo clockStyleInfo25 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo25);
                        clockStyleInfo25.setPrimaryColor(color2);
                        ClockStyleInfo clockStyleInfo26 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo26);
                        clockStyleInfo26.setFullAodPrimaryColor(color2);
                        ClockStyleInfo clockStyleInfo27 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo27);
                        clockStyleInfo27.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue4));
                        ClockStyleInfo clockStyleInfo28 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo28);
                        clockStyleInfo28.setBlendColor(iIntValue4);
                        ClockStyleInfo clockStyleInfo29 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo29);
                        clockStyleInfo29.setAodPrimaryColor(iIntValue5);
                        ClockStyleInfo clockStyleInfo30 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo30);
                        clockStyleInfo30.setAodBlendColor(iIntValue5);
                    } else {
                        if (z) {
                            if (map == null || map.get("secondary30") == null) {
                                ClockStyleInfo clockStyleInfo31 = this.mClockInfo;
                                Intrinsics.checkNotNull(clockStyleInfo31);
                                numValueOf4 = Integer.valueOf(clockStyleInfo31.getPrimaryColor());
                            } else {
                                numValueOf4 = (Integer) map.get("secondary30");
                            }
                            Intrinsics.checkNotNull(numValueOf4);
                            iIntValue3 = numValueOf4.intValue();
                        } else {
                            iIntValue3 = -1;
                        }
                        ClockStyleInfo clockStyleInfo32 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo32);
                        clockStyleInfo32.setPrimaryColor(iIntValue3);
                        ClockStyleInfo clockStyleInfo33 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo33);
                        clockStyleInfo33.setAodPrimaryColor(GlobalColorUtils.transformAodColor(iIntValue3));
                        ClockStyleInfo clockStyleInfo34 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo34);
                        clockStyleInfo34.setAodBlendColor(GlobalColorUtils.transformAodColor(iIntValue3));
                        ClockStyleInfo clockStyleInfo35 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo35);
                        clockStyleInfo35.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue3));
                    }
                } else if (ClockEffectUtils.hasBlurBlendRendering(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                    ClockStyleInfo clockStyleInfo36 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo36);
                    int iTransformAodColor = GlobalColorUtils.transformAodColor(clockStyleInfo36.getBlendColor());
                    ClockStyleInfo clockStyleInfo37 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo37);
                    int iTransformFullAodColor = GlobalColorUtils.transformFullAodColor(clockStyleInfo37.getBlendColor());
                    ClockStyleInfo clockStyleInfo38 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo38);
                    clockStyleInfo38.setPrimaryColor(-1);
                    ClockStyleInfo clockStyleInfo39 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo39);
                    clockStyleInfo39.setAodPrimaryColor(iTransformAodColor);
                    ClockStyleInfo clockStyleInfo40 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo40);
                    clockStyleInfo40.setAodBlendColor(iTransformAodColor);
                    ClockStyleInfo clockStyleInfo41 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo41);
                    clockStyleInfo41.setFullAodPrimaryColor(-1);
                    ClockStyleInfo clockStyleInfo42 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo42);
                    clockStyleInfo42.setFullAodBlendColor(iTransformFullAodColor);
                } else {
                    if (ClockEffectUtils.hasBlurBlendRendering(clockEffect) && !DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        ClockStyleInfo clockStyleInfo43 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo43);
                        clockStyleInfo43.convertPrimaryColorByBlendColor(true);
                    }
                    ClockStyleInfo clockStyleInfo44 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo44);
                    int aodPrimaryColor = clockStyleInfo44.getAodPrimaryColor();
                    ClockStyleInfo clockStyleInfo45 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo45);
                    clockStyleInfo45.setAodBlendColor(aodPrimaryColor);
                    ClockStyleInfo clockStyleInfo46 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo46);
                    ClockStyleInfo clockStyleInfo47 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo47);
                    clockStyleInfo46.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo47.getPrimaryColor()));
                }
                ClockStyleInfo clockStyleInfo48 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo48);
                if (!clockStyleInfo48.isAutoSecondaryColor()) {
                    if (ClockEffectUtils.hasBlurBlendRendering(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                        ClockStyleInfo clockStyleInfo49 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo49);
                        aodSecondaryColor = GlobalColorUtils.transformAodColor(clockStyleInfo49.getSecondaryBlendColor());
                        ClockStyleInfo clockStyleInfo50 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo50);
                        clockStyleInfo50.setSecondaryColor(-1);
                        ClockStyleInfo clockStyleInfo51 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo51);
                        clockStyleInfo51.setFullAodSecondaryColor(-1);
                        ClockStyleInfo clockStyleInfo52 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo52);
                        ClockStyleInfo clockStyleInfo53 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo53);
                        clockStyleInfo52.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo53.getSecondaryBlendColor()));
                    } else {
                        if (ClockEffectUtils.hasBlurBlendRendering(clockEffect) && !DeviceConfig.supportBackgroundBlur(this.mContext)) {
                            ClockStyleInfo clockStyleInfo54 = this.mClockInfo;
                            Intrinsics.checkNotNull(clockStyleInfo54);
                            clockStyleInfo54.convertSecondaryColorByBlendColor(true);
                        }
                        ClockStyleInfo clockStyleInfo55 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo55);
                        aodSecondaryColor = clockStyleInfo55.getAodSecondaryColor();
                        ClockStyleInfo clockStyleInfo56 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo56);
                        ClockStyleInfo clockStyleInfo57 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo57);
                        clockStyleInfo56.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(clockStyleInfo57.getSecondaryColor()));
                    }
                    ClockStyleInfo clockStyleInfo58 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo58);
                    clockStyleInfo58.setAodSecondaryColor(aodSecondaryColor);
                    ClockStyleInfo clockStyleInfo59 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo59);
                    clockStyleInfo59.setAodSecondaryBlendColor(aodSecondaryColor);
                } else if (ClockEffectUtils.hasBlurBlendRendering(clockEffect) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                    int color3 = Color.parseColor("#FFE6E6E6");
                    if (z) {
                        if (map == null || map.get("neutral-variant30") == null) {
                            ClockStyleInfo clockStyleInfo60 = this.mClockInfo;
                            Intrinsics.checkNotNull(clockStyleInfo60);
                            numValueOf3 = Integer.valueOf(clockStyleInfo60.getSecondaryColor());
                        } else {
                            numValueOf3 = (Integer) map.get("neutral-variant30");
                        }
                        Intrinsics.checkNotNull(numValueOf3);
                        iIntValue2 = numValueOf3.intValue();
                    } else {
                        iIntValue2 = Color.parseColor("#FFABABAB");
                    }
                    if (z) {
                        Integer numValueOf7 = (map == null || map.get("secondary70") == null) ? Integer.valueOf(iIntValue2) : (Integer) map.get("secondary70");
                        Intrinsics.checkNotNull(numValueOf7);
                        color = numValueOf7.intValue();
                    } else {
                        color = Color.parseColor("#FFBEBEBE");
                    }
                    ClockStyleInfo clockStyleInfo61 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo61);
                    clockStyleInfo61.setSecondaryColor(color3);
                    ClockStyleInfo clockStyleInfo62 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo62);
                    clockStyleInfo62.setFullAodSecondaryColor(color3);
                    ClockStyleInfo clockStyleInfo63 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo63);
                    clockStyleInfo63.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
                    ClockStyleInfo clockStyleInfo64 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo64);
                    clockStyleInfo64.setSecondaryBlendColor(iIntValue2);
                    ClockStyleInfo clockStyleInfo65 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo65);
                    clockStyleInfo65.setAodSecondaryColor(color);
                    ClockStyleInfo clockStyleInfo66 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo66);
                    clockStyleInfo66.setAodSecondaryBlendColor(color);
                } else {
                    if (z) {
                        if (map == null || map.get("secondary30") == null) {
                            ClockStyleInfo clockStyleInfo67 = this.mClockInfo;
                            Intrinsics.checkNotNull(clockStyleInfo67);
                            numValueOf2 = Integer.valueOf(clockStyleInfo67.getSecondaryColor());
                        } else {
                            numValueOf2 = (Integer) map.get("secondary30");
                        }
                        Intrinsics.checkNotNull(numValueOf2);
                        iIntValue = numValueOf2.intValue();
                    } else {
                        iIntValue = -1;
                    }
                    ClockStyleInfo clockStyleInfo68 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo68);
                    clockStyleInfo68.setSecondaryColor(iIntValue);
                    ClockStyleInfo clockStyleInfo69 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo69);
                    clockStyleInfo69.setAodSecondaryColor(GlobalColorUtils.transformAodColor(iIntValue));
                    ClockStyleInfo clockStyleInfo70 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo70);
                    clockStyleInfo70.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iIntValue));
                    ClockStyleInfo clockStyleInfo71 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo71);
                    clockStyleInfo71.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                }
                if (ClockEffectUtils.needFallbackToSolid(clockEffect) && !DeviceConfig.supportBackgroundBlur(this.mContext)) {
                    ClockStyleInfo clockStyleInfo72 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo72);
                    if (!clockStyleInfo72.isAutoPrimaryColor()) {
                        ClockStyleInfo clockStyleInfo73 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo73);
                        clockStyleInfo73.convertPrimaryColorByBlendColor(true);
                    }
                    ClockStyleInfo clockStyleInfo74 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo74);
                    if (!clockStyleInfo74.isAutoSecondaryColor()) {
                        ClockStyleInfo clockStyleInfo75 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo75);
                        clockStyleInfo75.convertSecondaryColorByBlendColor(true);
                    }
                }
            }
        }
        ClockStyleInfo clockStyleInfo76 = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo76);
        if (clockStyleInfo76.isAutoPrimaryColor()) {
            ClockStyleInfo clockStyleInfo77 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo77);
            if (!z) {
                numValueOf = -1;
            } else if (map == null || map.get("secondary30") == null) {
                ClockStyleInfo clockStyleInfo78 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo78);
                numValueOf = Integer.valueOf(clockStyleInfo78.getPrimaryColor());
            } else {
                numValueOf = (Integer) map.get("secondary30");
            }
            Intrinsics.checkNotNull(numValueOf);
            clockStyleInfo77.setOriginMagazineColor(numValueOf.intValue());
        } else {
            ClockStyleInfo clockStyleInfo79 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo79);
            if (ClockEffectUtils.isBlurMixType(clockStyleInfo79.getClockEffect())) {
                ClockStyleInfo clockStyleInfo80 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo80);
                ClockStyleInfo clockStyleInfo81 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo81);
                clockStyleInfo80.setOriginMagazineColor(clockStyleInfo81.getBlendColor());
            } else {
                ClockStyleInfo clockStyleInfo82 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo82);
                if (ClockEffectUtils.isGlassType(clockStyleInfo82.getClockEffect())) {
                    ClockStyleInfo clockStyleInfo83 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo83);
                    ClockStyleInfo clockStyleInfo84 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo84);
                    clockStyleInfo83.setOriginMagazineColor(clockStyleInfo84.getBlendColor());
                } else {
                    ClockStyleInfo clockStyleInfo85 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo85);
                    if (ClockEffectUtils.isDifferenceType(clockStyleInfo85.getClockEffect())) {
                        ClockStyleInfo clockStyleInfo86 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo86);
                        ClockStyleInfo clockStyleInfo87 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo87);
                        clockStyleInfo86.setOriginMagazineColor(clockStyleInfo87.getBlendColor());
                    } else {
                        ClockStyleInfo clockStyleInfo88 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo88);
                        ClockStyleInfo clockStyleInfo89 = this.mClockInfo;
                        Intrinsics.checkNotNull(clockStyleInfo89);
                        clockStyleInfo88.setOriginMagazineColor(clockStyleInfo89.getPrimaryColor());
                    }
                }
            }
        }
        ClockStyleInfo clockStyleInfo90 = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo90);
        ClockStyleInfo clockStyleInfo91 = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo91);
        clockStyleInfo90.setAnimationPrimaryColor(clockStyleInfo91.getPrimaryColor());
        ClockStyleInfo clockStyleInfo92 = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo92);
        ClockStyleInfo clockStyleInfo93 = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo93);
        clockStyleInfo92.setAnimationSecondaryColor(clockStyleInfo93.getSecondaryColor());
    }

    public final void processingClockPalette(ClockStyleInfo clockStyleInfo, int i, boolean z, Map map, boolean z2) {
        ClockStyleInfo clockStyleInfo2;
        int iIntValue;
        String str;
        this.mClockInfo = clockStyleInfo;
        if (clockStyleInfo != null) {
            clockStyleInfo.setTextDark(z);
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + (map != null ? "secondary30=" + map.get("secondary30") : "null"));
        if (DepthAvoidEvaluatorDebug.DEBUG_LOG && map != null) {
            Log.d("ClockPalette", "┌── Full Palette Dump ──");
            for (Map.Entry entry : CollectionsKt.sortedWith(map.entrySet(), new Comparator() { // from class: com.miui.clock.allInOne.AllInOneColor$processingClockPalette$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt.compareValues((String) ((Map.Entry) obj).getKey(), (String) ((Map.Entry) obj2).getKey());
                }
            })) {
                String str2 = (String) entry.getKey();
                Integer num = (Integer) entry.getValue();
                StringBuilder sb = new StringBuilder();
                sb.append("│ ");
                sb.append(str2);
                sb.append(" = ");
                if (num != null) {
                    int iIntValue2 = num.intValue();
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    str = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(iIntValue2)}, 1));
                    Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                } else {
                    str = "null";
                }
                sb.append(str);
                Log.d("ClockPalette", sb.toString());
            }
            Log.d("ClockPalette", "└── end (" + map.size() + " entries) ──");
        }
        updateHighTextColors(this.mClockInfo);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        ClockStyleInfo clockStyleInfo3 = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo3);
        if (clockStyleInfo3.isSuperSaveOpen()) {
            return;
        }
        applyClassicMaxPalette(i, z, map, z2);
        ClockStyleInfo clockStyleInfo4 = this.mClockInfo;
        Intrinsics.checkNotNull(clockStyleInfo4);
        if (ClockEffectUtils.isGlassType(clockStyleInfo4.getClockEffect()) && DeviceConfig.isGlassEffectEnable(this.mContext)) {
            ClockStyleInfo clockStyleInfo5 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo5);
            ClockStyleInfo clockStyleInfo6 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo6);
            clockStyleInfo5.setGlassColor(clockStyleInfo6.getBlendColor());
            ClockStyleInfo clockStyleInfo7 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo7);
            ClockStyleInfo clockStyleInfo8 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo8);
            clockStyleInfo7.setAodGlassColor(clockStyleInfo8.getAodBlendColor());
            ClockStyleInfo clockStyleInfo9 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo9);
            ClockStyleInfo clockStyleInfo10 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo10);
            clockStyleInfo9.setSecondaryGlassColor(clockStyleInfo10.getSecondaryBlendColor());
            ClockStyleInfo clockStyleInfo11 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo11);
            ClockStyleInfo clockStyleInfo12 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo12);
            clockStyleInfo11.setAodSecondaryGlassColor(clockStyleInfo12.getAodSecondaryBlendColor());
            GlobalColorUtils.transformAodColor(-1275068417);
            if (z) {
                Integer num2 = (map == null || map.get("secondary70") == null) ? -1 : (Integer) map.get("secondary70");
                Intrinsics.checkNotNull(num2);
                iIntValue = num2.intValue();
            } else {
                iIntValue = Color.parseColor("#FFBEBEBE");
            }
            ClockStyleInfo clockStyleInfo13 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo13);
            if (clockStyleInfo13.isAutoPrimaryColor()) {
                ClockStyleInfo clockStyleInfo14 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo14);
                clockStyleInfo14.setGlassColor(-1275068417);
                ClockStyleInfo clockStyleInfo15 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo15);
                clockStyleInfo15.setAodGlassColor(iIntValue);
                ClockStyleInfo clockStyleInfo16 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo16);
                clockStyleInfo16.setAodPrimaryColor(iIntValue);
                ClockStyleInfo clockStyleInfo17 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo17);
                clockStyleInfo17.setAodBlendColor(iIntValue);
                ClockStyleInfo clockStyleInfo18 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo18);
                clockStyleInfo18.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                if (!z) {
                    ClockStyleInfo clockStyleInfo19 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo19);
                    clockStyleInfo19.setOriginMagazineColor(-1275068417);
                }
            }
            ClockStyleInfo clockStyleInfo20 = this.mClockInfo;
            Intrinsics.checkNotNull(clockStyleInfo20);
            if (clockStyleInfo20.isAutoSecondaryColor()) {
                ClockStyleInfo clockStyleInfo21 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo21);
                clockStyleInfo21.setSecondaryGlassColor(-1275068417);
                ClockStyleInfo clockStyleInfo22 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo22);
                clockStyleInfo22.setAodSecondaryGlassColor(iIntValue);
                ClockStyleInfo clockStyleInfo23 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo23);
                clockStyleInfo23.setAodSecondaryColor(iIntValue);
                ClockStyleInfo clockStyleInfo24 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo24);
                clockStyleInfo24.setAodSecondaryBlendColor(iIntValue);
                ClockStyleInfo clockStyleInfo25 = this.mClockInfo;
                Intrinsics.checkNotNull(clockStyleInfo25);
                clockStyleInfo25.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(iIntValue));
                if (!z) {
                    ClockStyleInfo clockStyleInfo26 = this.mClockInfo;
                    Intrinsics.checkNotNull(clockStyleInfo26);
                    clockStyleInfo26.setOriginMagazineColor(-1275068417);
                }
            }
        }
        if (!DepthAvoidEvaluatorDebug.DEBUG_LOG || (clockStyleInfo2 = this.mClockInfo) == null) {
            return;
        }
        Intrinsics.checkNotNull(clockStyleInfo2);
        Log.d("ClockPalette", "┌── Color Result (region=" + clockStyleInfo2.getClockDepthType() + ", effect=" + clockStyleInfo2.getClockEffect() + ") ──");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("│ primaryColor     = ");
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String str3 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getPrimaryColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
        sb2.append(str3);
        Log.d("ClockPalette", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("│ secondaryColor   = ");
        String str4 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getSecondaryColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str4, "format(...)");
        sb3.append(str4);
        Log.d("ClockPalette", sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("│ blendColor       = ");
        String str5 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getBlendColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str5, "format(...)");
        sb4.append(str5);
        Log.d("ClockPalette", sb4.toString());
        StringBuilder sb5 = new StringBuilder();
        sb5.append("│ secondBlendColor = ");
        String str6 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getSecondaryBlendColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str6, "format(...)");
        sb5.append(str6);
        Log.d("ClockPalette", sb5.toString());
        StringBuilder sb6 = new StringBuilder();
        sb6.append("│ aodPrimary       = ");
        String str7 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getAodPrimaryColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str7, "format(...)");
        sb6.append(str7);
        Log.d("ClockPalette", sb6.toString());
        StringBuilder sb7 = new StringBuilder();
        sb7.append("│ aodSecondary     = ");
        String str8 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getAodSecondaryColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str8, "format(...)");
        sb7.append(str8);
        Log.d("ClockPalette", sb7.toString());
        StringBuilder sb8 = new StringBuilder();
        sb8.append("│ fullAodPrimary   = ");
        String str9 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getFullAodPrimaryColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str9, "format(...)");
        sb8.append(str9);
        Log.d("ClockPalette", sb8.toString());
        StringBuilder sb9 = new StringBuilder();
        sb9.append("│ fullAodSecondary = ");
        String str10 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getFullAodSecondaryColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str10, "format(...)");
        sb9.append(str10);
        Log.d("ClockPalette", sb9.toString());
        StringBuilder sb10 = new StringBuilder();
        sb10.append("│ magazineColor    = ");
        String str11 = String.format("#%08X", Arrays.copyOf(new Object[]{Integer.valueOf(clockStyleInfo2.getOriginMagazineColor())}, 1));
        Intrinsics.checkNotNullExpressionValue(str11, "format(...)");
        sb10.append(str11);
        Log.d("ClockPalette", sb10.toString());
        Log.d("ClockPalette", "│ autoPrimary=" + clockStyleInfo2.isAutoPrimaryColor() + ", autoSecondary=" + clockStyleInfo2.isAutoSecondaryColor());
        Log.d("ClockPalette", "└── end ──");
    }

    public final void updateHighTextColors(ClockStyleInfo clockStyleInfo) {
        if (!DeviceConfig.isHighTextContrastEnabled(this.mContext) || clockStyleInfo == null) {
            return;
        }
        clockStyleInfo.updateAodHighTextColors();
    }
}
