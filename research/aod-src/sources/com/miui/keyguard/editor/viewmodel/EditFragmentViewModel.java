package com.miui.keyguard.editor.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.DoodleInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.SignatureInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.preset.FontFilterData;
import com.miui.keyguard.editor.data.template.TemplateChangeRecorder;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.ToastUtil;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: EditFragmentViewModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditFragmentViewModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    public FontFilterData oldPrimaryFontFilter;
    public FontFilterData oldSecondaryFontFilter;
    public int selectTemplate;
    public TemplateConfig templateConfig;
    public final TemplateChangeRecorder templateChangeRecorder = new TemplateChangeRecorder();
    public MutableLiveData mHomeFilterId = new MutableLiveData();
    public MutableLiveData mFollowStatus = new MutableLiveData();
    public MutableLiveData mHomeWallpaperStatus = new MutableLiveData();
    public MutableLiveData mWallpaperFilterId = new MutableLiveData();
    public MutableLiveData mHierarchyStatus = new MutableLiveData();
    public MutableLiveData mMatteStatus = new MutableLiveData();
    public MutableLiveData mDoodleStatus = new MutableLiveData();
    public MutableLiveData mPrimaryFontFilter = new MutableLiveData();
    public MutableLiveData mSecondaryFontFilter = new MutableLiveData();
    public MutableLiveData mDifferentColorStatus = new MutableLiveData();

    /* JADX INFO: compiled from: EditFragmentViewModel.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int computeSupportedClockEffect(CommonConfig commonConfig, int i) {
            TemplateConfig lockscreenInfo;
            ClockInfo clockInfo;
            if (!isVersionEnable(commonConfig)) {
                return (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null || !clockInfo.isAutoPrimaryColor()) ? 0 : 1;
            }
            if (needFallbackToSolid(i)) {
                Log.d("EditFragmentViewModel", "needFallbackToSolid, clockEffect=" + i + ", fallback to solid");
                return 0;
            }
            if (!shouldDisableGlassEffect(i, commonConfig)) {
                return i;
            }
            Log.d("EditFragmentViewModel", "shouldDisableGlassEffect, clockEffect=" + i + ", fallback to obscure");
            return 1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final CoroutineScope getCoroutineScope(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (context instanceof LifecycleOwner) {
                return LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) context);
            }
            if (context instanceof DeviceScreenshotHelper.ScreenshotContext) {
                return CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
            }
            throw new IllegalArgumentException("error context " + context);
        }

        public final CoroutineScope getCoroutineScope(EditFragment editFragment) {
            if (editFragment != null && !editFragment.isDetached()) {
                return LifecycleOwnerKt.getLifecycleScope(editFragment);
            }
            Log.d("EditFragmentViewModel", "getCoroutineScope: edit fragment invalid: " + (editFragment != null));
            return null;
        }

        public final EditFragmentViewModel getViewModel(EditFragment editFragment) {
            if (editFragment == null || editFragment.isDetached() || !editFragment.isAdded()) {
                return null;
            }
            return (EditFragmentViewModel) new ViewModelProvider(editFragment).get(EditFragmentViewModel.class);
        }

        public final boolean isVersionEnable(CommonConfig commonConfig) {
            return ArraysKt.contains(new Integer[]{2, 3, 4}, commonConfig != null ? Integer.valueOf(commonConfig.getVersion()) : null);
        }

        public final boolean needFallbackToSolid(int i) {
            if ((i == 2 || i == 4) && !DeviceConfig.supportDiffEffectAndGradientEffect()) {
                return true;
            }
            return ClockEffectUtils.needFallbackToSolid(i) && !ViewStylerKt.canUseBlur(EditorApplicationProxy.Companion.getApplication());
        }

        /* JADX WARN: Code duplicated, block: B:36:0x0061  */
        public final boolean shouldDisableGlassEffect(int i, CommonConfig commonConfig) {
            boolean z;
            TemplateConfig lockscreenInfo;
            WallpaperInfo wallpaperInfo;
            TemplateConfig lockscreenInfo2;
            WallpaperInfo wallpaperInfo2;
            TemplateConfig lockscreenInfo3;
            ClockInfo clockInfo;
            if (i != 5) {
                return false;
            }
            String resourceType = null;
            if (!Intrinsics.areEqual((commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo3.getClockInfo()) == null) ? null : clockInfo.getTemplateId(), "all_in_one") || !DeviceConfig.supportGlassEffect()) {
                return true;
            }
            if (!Intrinsics.areEqual((commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getResourceType(), "image")) {
                if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                    resourceType = wallpaperInfo.getResourceType();
                }
                z = Intrinsics.areEqual(resourceType, "gallery");
            }
            return !z;
        }
    }

    public static /* synthetic */ void reloadTemplateConfig$default(EditFragmentViewModel editFragmentViewModel, CommonConfig commonConfig, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        editFragmentViewModel.reloadTemplateConfig(commonConfig, z, z2);
    }

    public static /* synthetic */ void reloadTemplateConfigForFontFilter$default(EditFragmentViewModel editFragmentViewModel, CommonConfig commonConfig, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        editFragmentViewModel.reloadTemplateConfigForFontFilter(commonConfig, z, z2);
    }

    public static /* synthetic */ void reloadTemplateConfigForWallpaperFilter$default(EditFragmentViewModel editFragmentViewModel, CommonConfig commonConfig, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        editFragmentViewModel.reloadTemplateConfigForWallpaperFilter(commonConfig, z);
    }

    public static /* synthetic */ void updateDifferentColorStatus$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        editFragmentViewModel.updateDifferentColorStatus(i, z);
    }

    public static /* synthetic */ void updateDoodleStatus$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        editFragmentViewModel.updateDoodleStatus(i, z);
    }

    public static /* synthetic */ void updateFollowStatus$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        editFragmentViewModel.updateFollowStatus(i, z, z2);
    }

    public static /* synthetic */ void updateHierarchyStatus$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, boolean z2, boolean z3, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        if ((i2 & 8) != 0) {
            z3 = false;
        }
        editFragmentViewModel.updateHierarchyStatus(i, z, z2, z3);
    }

    public static /* synthetic */ void updateHomeFilterId$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        editFragmentViewModel.updateHomeFilterId(i, z, z2);
    }

    public static /* synthetic */ void updateHomeWallpaperStatus$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        editFragmentViewModel.updateHomeWallpaperStatus(i, z, z2);
    }

    public static /* synthetic */ void updateMatteStatus$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        editFragmentViewModel.updateMatteStatus(i, z);
    }

    public static /* synthetic */ void updateWallpaperFilterId$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        editFragmentViewModel.updateWallpaperFilterId(i, z);
    }

    public static /* synthetic */ void updateWallpaperFilterIdSync$default(EditFragmentViewModel editFragmentViewModel, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        editFragmentViewModel.updateWallpaperFilterIdSync(i, z);
    }

    /* JADX WARN: Code duplicated, block: B:109:0x0168 A[PHI: r3 r5 r7
  0x0168: PHI (r3v4 int) = (r3v3 int), (r3v3 int), (r3v3 int), (r3v3 int), (r3v15 int), (r3v15 int), (r3v15 int), (r3v15 int) binds: [B:142:0x021b, B:144:0x0221, B:137:0x0209, B:139:0x020f, B:101:0x0153, B:103:0x0159, B:105:0x015f, B:107:0x0165] A[DONT_GENERATE, DONT_INLINE]
  0x0168: PHI (r5v5 int) = (r5v3 int), (r5v3 int), (r5v3 int), (r5v3 int), (r5v8 int), (r5v8 int), (r5v8 int), (r5v8 int) binds: [B:142:0x021b, B:144:0x0221, B:137:0x0209, B:139:0x020f, B:101:0x0153, B:103:0x0159, B:105:0x015f, B:107:0x0165] A[DONT_GENERATE, DONT_INLINE]
  0x0168: PHI (r7v4 int) = (r7v2 int), (r7v2 int), (r7v2 int), (r7v2 int), (r7v14 int), (r7v14 int), (r7v14 int), (r7v14 int) binds: [B:142:0x021b, B:144:0x0221, B:137:0x0209, B:139:0x020f, B:101:0x0153, B:103:0x0159, B:105:0x015f, B:107:0x0165] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:111:0x016c  */
    /* JADX WARN: Code duplicated, block: B:113:0x017e  */
    /* JADX WARN: Code duplicated, block: B:114:0x018c  */
    /* JADX WARN: Code duplicated, block: B:116:0x019a  */
    /* JADX WARN: Code duplicated, block: B:117:0x019c  */
    /* JADX WARN: Code duplicated, block: B:123:0x01bd  */
    /* JADX WARN: Code duplicated, block: B:125:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:128:0x01dd  */
    /* JADX WARN: Code duplicated, block: B:130:0x01e7  */
    /* JADX WARN: Code duplicated, block: B:133:0x01f1  */
    /* JADX WARN: Code duplicated, block: B:135:0x01fb  */
    /* JADX WARN: Code duplicated, block: B:136:0x0201  */
    /* JADX WARN: Code duplicated, block: B:141:0x0213  */
    /* JADX WARN: Code duplicated, block: B:40:0x0077  */
    /* JADX WARN: Code duplicated, block: B:76:0x00f9  */
    public final Triple getCurrentColorInfo(CommonConfig commonConfig, boolean z) {
        int primaryColor;
        int primaryFontColorPanelId;
        SignatureInfo signatureInfo;
        SignatureInfo signatureInfo2;
        SignatureInfo signatureInfo3;
        SignatureInfo signatureInfo4;
        SignatureInfo signatureInfo5;
        SignatureInfo signatureInfo6;
        int solidColor;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo2;
        ClockInfo clockInfo2;
        TemplateConfig lockscreenInfo3;
        ClockInfo clockInfo3;
        TemplateConfig lockscreenInfo4;
        ClockInfo clockInfo4;
        TemplateConfig lockscreenInfo5;
        ClockInfo clockInfo5;
        TemplateConfig lockscreenInfo6;
        ClockInfo clockInfo6;
        TemplateConfig lockscreenInfo7;
        ClockInfo clockInfo7;
        TemplateConfig lockscreenInfo8;
        ClockInfo clockInfo8;
        TemplateConfig lockscreenInfo9;
        ClockInfo clockInfo9;
        TemplateConfig lockscreenInfo10;
        ClockInfo clockInfo10;
        Companion companion = Companion;
        int primaryFontColorPanelId2 = 0;
        int iComputeSupportedClockEffect = companion.computeSupportedClockEffect(commonConfig, (commonConfig == null || (lockscreenInfo10 = commonConfig.getLockscreenInfo()) == null || (clockInfo10 = lockscreenInfo10.getClockInfo()) == null) ? 0 : clockInfo10.getClockEffect());
        String templateId = null;
        int blendColor = -1;
        if (Intrinsics.areEqual("magazine_a", (commonConfig == null || (lockscreenInfo9 = commonConfig.getLockscreenInfo()) == null || (clockInfo9 = lockscreenInfo9.getClockInfo()) == null) ? null : clockInfo9.getTemplateId())) {
            primaryColor = commonConfig.getLockscreenInfo().getClockInfo().getPrimaryColor();
            if (companion.isVersionEnable(commonConfig)) {
                primaryFontColorPanelId = commonConfig.getLockscreenInfo().getClockInfo().getPrimaryFontColorPanelId();
            } else if (commonConfig.getLockscreenInfo().getClockInfo().isAutoPrimaryColor()) {
                primaryFontColorPanelId = 0;
            } else {
                primaryFontColorPanelId = 13;
            }
            signatureInfo = commonConfig.getLockscreenInfo().getSignatureInfo();
            if (signatureInfo == null && signatureInfo.getClockEffect() == 0) {
                SignatureInfo signatureInfo7 = commonConfig.getLockscreenInfo().getSignatureInfo();
                Intrinsics.checkNotNull(signatureInfo7);
                blendColor = signatureInfo7.getPrimaryColor();
            } else {
                signatureInfo2 = commonConfig.getLockscreenInfo().getSignatureInfo();
                if (signatureInfo2 == null && signatureInfo2.getBlendColor() == 0) {
                    SignatureInfo signatureInfo8 = commonConfig.getLockscreenInfo().getSignatureInfo();
                    Intrinsics.checkNotNull(signatureInfo8);
                    blendColor = signatureInfo8.getPrimaryColor();
                } else {
                    signatureInfo3 = commonConfig.getLockscreenInfo().getSignatureInfo();
                    if (signatureInfo3 != null) {
                        blendColor = signatureInfo3.getBlendColor();
                    }
                }
            }
            if (companion.isVersionEnable(commonConfig)) {
                signatureInfo5 = commonConfig.getLockscreenInfo().getSignatureInfo();
                if (signatureInfo5 != null) {
                    primaryFontColorPanelId2 = signatureInfo5.getPrimaryFontColorPanelId();
                } else {
                    signatureInfo6 = commonConfig.getLockscreenInfo().getSignatureInfo();
                    if (signatureInfo6 != null || !signatureInfo6.isAutoPrimaryColor()) {
                        primaryFontColorPanelId2 = 13;
                    }
                }
            } else {
                signatureInfo4 = commonConfig.getLockscreenInfo().getSignatureInfo();
                if (signatureInfo4 != null || !signatureInfo4.isAutoPrimaryColor()) {
                    primaryFontColorPanelId2 = 13;
                }
            }
            solidColor = blendColor;
        } else {
            if (Intrinsics.areEqual("magazine_b", (commonConfig == null || (lockscreenInfo8 = commonConfig.getLockscreenInfo()) == null || (clockInfo8 = lockscreenInfo8.getClockInfo()) == null) ? null : clockInfo8.getTemplateId())) {
                primaryColor = commonConfig.getLockscreenInfo().getClockInfo().getPrimaryColor();
                if (companion.isVersionEnable(commonConfig)) {
                    primaryFontColorPanelId = commonConfig.getLockscreenInfo().getClockInfo().getPrimaryFontColorPanelId();
                } else if (commonConfig.getLockscreenInfo().getClockInfo().isAutoPrimaryColor()) {
                    primaryFontColorPanelId = 0;
                } else {
                    primaryFontColorPanelId = 13;
                }
                signatureInfo = commonConfig.getLockscreenInfo().getSignatureInfo();
                if (signatureInfo == null) {
                    signatureInfo2 = commonConfig.getLockscreenInfo().getSignatureInfo();
                    if (signatureInfo2 == null) {
                        signatureInfo3 = commonConfig.getLockscreenInfo().getSignatureInfo();
                        if (signatureInfo3 != null) {
                            blendColor = signatureInfo3.getBlendColor();
                        }
                    } else {
                        signatureInfo3 = commonConfig.getLockscreenInfo().getSignatureInfo();
                        if (signatureInfo3 != null) {
                            blendColor = signatureInfo3.getBlendColor();
                        }
                    }
                } else {
                    signatureInfo2 = commonConfig.getLockscreenInfo().getSignatureInfo();
                    if (signatureInfo2 == null) {
                        signatureInfo3 = commonConfig.getLockscreenInfo().getSignatureInfo();
                        if (signatureInfo3 != null) {
                            blendColor = signatureInfo3.getBlendColor();
                        }
                    } else {
                        signatureInfo3 = commonConfig.getLockscreenInfo().getSignatureInfo();
                        if (signatureInfo3 != null) {
                            blendColor = signatureInfo3.getBlendColor();
                        }
                    }
                }
                if (companion.isVersionEnable(commonConfig)) {
                    signatureInfo5 = commonConfig.getLockscreenInfo().getSignatureInfo();
                    if (signatureInfo5 != null) {
                        primaryFontColorPanelId2 = signatureInfo5.getPrimaryFontColorPanelId();
                    } else {
                        signatureInfo6 = commonConfig.getLockscreenInfo().getSignatureInfo();
                        if (signatureInfo6 != null) {
                            primaryFontColorPanelId2 = 13;
                        } else {
                            primaryFontColorPanelId2 = 13;
                        }
                    }
                } else {
                    signatureInfo4 = commonConfig.getLockscreenInfo().getSignatureInfo();
                    if (signatureInfo4 != null) {
                        primaryFontColorPanelId2 = 13;
                    } else {
                        primaryFontColorPanelId2 = 13;
                    }
                }
                solidColor = blendColor;
            } else {
                if (z && ViewStylerKt.canUseBlur() && iComputeSupportedClockEffect != 0) {
                    if (commonConfig == null || (lockscreenInfo7 = commonConfig.getLockscreenInfo()) == null || (clockInfo7 = lockscreenInfo7.getClockInfo()) == null) {
                        primaryColor = -1;
                    } else {
                        primaryColor = clockInfo7.getBlendColor();
                    }
                } else if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) {
                    primaryColor = -1;
                } else {
                    primaryColor = clockInfo.getPrimaryColor();
                }
                if (companion.isVersionEnable(commonConfig)) {
                    Intrinsics.checkNotNull(commonConfig);
                    primaryFontColorPanelId = commonConfig.getLockscreenInfo().getClockInfo().getPrimaryFontColorPanelId();
                } else {
                    primaryFontColorPanelId = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (clockInfo2 = lockscreenInfo2.getClockInfo()) == null || !clockInfo2.isAutoPrimaryColor()) ? 13 : 0;
                }
                if (commonConfig != null && (lockscreenInfo6 = commonConfig.getLockscreenInfo()) != null && (clockInfo6 = lockscreenInfo6.getClockInfo()) != null) {
                    templateId = clockInfo6.getTemplateId();
                }
                if (Intrinsics.areEqual("doodle", templateId)) {
                    DoodleInfo doodle = commonConfig.getLockscreenInfo().getDoodle();
                    solidColor = doodle != null ? doodle.getSolidColor() : -16777216;
                    if (companion.isVersionEnable(commonConfig)) {
                        DoodleInfo doodle2 = commonConfig.getLockscreenInfo().getDoodle();
                        if (doodle2 != null) {
                            primaryFontColorPanelId2 = doodle2.getPrimaryFontColorPanelId();
                        } else {
                            primaryFontColorPanelId2 = 13;
                        }
                    } else {
                        DoodleInfo doodle3 = commonConfig.getLockscreenInfo().getDoodle();
                        if (doodle3 == null || !doodle3.isAutoSolidColor()) {
                            primaryFontColorPanelId2 = 13;
                        }
                    }
                } else {
                    if (z && ViewStylerKt.canUseBlur() && iComputeSupportedClockEffect != 0) {
                        if (commonConfig != null && (lockscreenInfo5 = commonConfig.getLockscreenInfo()) != null && (clockInfo5 = lockscreenInfo5.getClockInfo()) != null) {
                            blendColor = clockInfo5.getSecondaryBlendColor();
                        }
                    } else if (commonConfig != null && (lockscreenInfo3 = commonConfig.getLockscreenInfo()) != null && (clockInfo3 = lockscreenInfo3.getClockInfo()) != null) {
                        blendColor = clockInfo3.getSecondaryColor();
                    }
                    if (companion.isVersionEnable(commonConfig)) {
                        Intrinsics.checkNotNull(commonConfig);
                        primaryFontColorPanelId2 = commonConfig.getLockscreenInfo().getClockInfo().getSecondaryFontColorPanelId();
                    } else if (commonConfig == null || (lockscreenInfo4 = commonConfig.getLockscreenInfo()) == null || (clockInfo4 = lockscreenInfo4.getClockInfo()) == null || !clockInfo4.isAutoSecondaryColor()) {
                        primaryFontColorPanelId2 = 13;
                    }
                    solidColor = blendColor;
                }
            }
        }
        return new Triple(new Pair(Integer.valueOf(primaryColor), Integer.valueOf(primaryFontColorPanelId)), new Pair(Integer.valueOf(solidColor), Integer.valueOf(primaryFontColorPanelId2)), Integer.valueOf(iComputeSupportedClockEffect));
    }

    public final IntWrapper getDifferentColorStatus() {
        IntWrapper intWrapper = (IntWrapper) this.mDifferentColorStatus.getValue();
        return intWrapper == null ? new IntWrapper(2, true) : intWrapper;
    }

    public final IntWrapper getDoodleStatus() {
        IntWrapper intWrapper = (IntWrapper) this.mDoodleStatus.getValue();
        return intWrapper == null ? new IntWrapper(1, true) : intWrapper;
    }

    public final IntWrapper getFollowStatus() {
        IntWrapper intWrapper = (IntWrapper) this.mFollowStatus.getValue();
        return intWrapper == null ? new IntWrapper(0, true) : intWrapper;
    }

    public final int getFontFilterId() {
        FontFilterData fontFilterData = (FontFilterData) this.mPrimaryFontFilter.getValue();
        if (fontFilterData != null) {
            return fontFilterData.getFilterId();
        }
        return 0;
    }

    public final IntWrapper getHierarchyStatus() {
        IntWrapper intWrapper = (IntWrapper) this.mHierarchyStatus.getValue();
        return intWrapper == null ? new IntWrapper(0, true) : intWrapper;
    }

    public final int getHomeBlurStatus(WallpaperInfo wallpaperInfo) {
        if (!Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "image")) {
            if (!Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "gallery")) {
                return 0;
            }
        }
        return wallpaperInfo.getMagicType() == 30000 ? 1 : 2;
    }

    public final IntWrapper getHomeFilterId() {
        IntWrapper intWrapper = (IntWrapper) this.mHomeFilterId.getValue();
        return intWrapper == null ? new IntWrapper(2, true) : intWrapper;
    }

    public final int getHomeWallpaperStatus(int i) {
        return (i == 0 || i == 3) ? 2 : 0;
    }

    public final IntWrapper getHomeWallpaperStatus() {
        IntWrapper intWrapper = (IntWrapper) this.mHomeWallpaperStatus.getValue();
        return intWrapper == null ? new IntWrapper(0, true) : intWrapper;
    }

    public final IntWrapper getMatteStatus() {
        IntWrapper intWrapper = (IntWrapper) this.mMatteStatus.getValue();
        return intWrapper == null ? new IntWrapper(3, true) : intWrapper;
    }

    public final FontFilterData getOldPrimaryFontFilter() {
        return this.oldPrimaryFontFilter;
    }

    public final FontFilterData getOldSecondaryFontFilter() {
        return this.oldSecondaryFontFilter;
    }

    public final FontFilterData getPrimaryFontFilter() {
        FontFilterData fontFilterData = (FontFilterData) this.mPrimaryFontFilter.getValue();
        return fontFilterData == null ? new FontFilterData(0, 0, 0, false, 8, null) : fontFilterData;
    }

    public final FontFilterData getSecondaryFontFilter() {
        FontFilterData fontFilterData = (FontFilterData) this.mSecondaryFontFilter.getValue();
        return fontFilterData == null ? new FontFilterData(0, 0, 0, false, 8, null) : fontFilterData;
    }

    public final int getSelectTemplate() {
        return this.selectTemplate;
    }

    public final TemplateChangeRecorder getTemplateChangeRecorder() {
        return this.templateChangeRecorder;
    }

    public final TemplateConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public final IntWrapper getWallpaperFilterId() {
        IntWrapper intWrapper = (IntWrapper) this.mWallpaperFilterId.getValue();
        return intWrapper == null ? new IntWrapper(0, true) : intWrapper;
    }

    public final void initHomeButtonStatus(CommonConfig commonConfig, boolean z) {
        HomeConfig homeInfo;
        HomeConfig homeInfo2;
        int followStatus = (commonConfig == null || (homeInfo2 = commonConfig.getHomeInfo()) == null) ? 2 : homeInfo2.getFollowStatus();
        updateFollowStatus$default(this, followStatus, z, false, 4, null);
        updateHomeWallpaperStatus$default(this, getHomeWallpaperStatus(followStatus), z, false, 4, null);
        updateHomeFilterId$default(this, getHomeBlurStatus((commonConfig == null || (homeInfo = commonConfig.getHomeInfo()) == null) ? null : homeInfo.getWallpaperInfo()), z, false, 4, null);
    }

    public final void observeDifferentColorStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mDifferentColorStatus.observe(lifecycleOwner, observer);
    }

    public final void observeDoodleStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mDoodleStatus.observe(lifecycleOwner, observer);
    }

    public final void observeFollowStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mFollowStatus.observe(lifecycleOwner, observer);
    }

    public final void observeHierarchyStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mHierarchyStatus.observe(lifecycleOwner, observer);
    }

    public final void observeHomeFilterId(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mHomeFilterId.observe(lifecycleOwner, observer);
    }

    public final void observeHomeWallpaperStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mHomeWallpaperStatus.observe(lifecycleOwner, observer);
    }

    public final void observeMatteStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mMatteStatus.observe(lifecycleOwner, observer);
    }

    public final void observePrimaryFontFilter(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mPrimaryFontFilter.observe(lifecycleOwner, observer);
    }

    public final void observeSecondaryFontFilter(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        Log.d("EditFragmentViewModel", "observeSecondaryFontFilter: ");
        this.mSecondaryFontFilter.observe(lifecycleOwner, observer);
    }

    public final void observeWallpaperFilterStatus(LifecycleOwner lifecycleOwner, Observer observer) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(observer, "observer");
        this.mWallpaperFilterId.observe(lifecycleOwner, observer);
    }

    public final void reloadTemplateConfig(CommonConfig commonConfig, boolean z, boolean z2) {
        Log.d("EditFragmentViewModel", "reloadTemplateConfig: fromInit=" + z2 + ", config=" + commonConfig);
        if (commonConfig != null) {
            try {
                reloadTemplateConfigForWallpaperFilter(commonConfig, z);
                reloadTemplateConfigForFontFilter(commonConfig, z, z2);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                Log.e("EditFragmentViewModel", "reloadTemplateConfig: error", e);
            }
        }
    }

    public final void reloadTemplateConfigForFontFilter(CommonConfig commonConfig, boolean z, boolean z2) {
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        TemplateConfig lockscreenInfo2;
        Triple currentColorInfo = getCurrentColorInfo(commonConfig, z2);
        Pair pair = (Pair) currentColorInfo.component1();
        Pair pair2 = (Pair) currentColorInfo.component2();
        int iIntValue = ((Number) currentColorInfo.component3()).intValue();
        int iIntValue2 = ((Number) pair.getFirst()).intValue();
        int iIntValue3 = ((Number) pair.getSecond()).intValue();
        int iIntValue4 = ((Number) pair2.getFirst()).intValue();
        int iIntValue5 = ((Number) pair2.getSecond()).intValue();
        Log.d("EditFragmentViewModel", "reloadTemplateConfigForFontFilter: fontFilterId=" + iIntValue + " primaryColorId=" + iIntValue3 + " primaryColor=" + iIntValue2 + " secondaryColorId=" + iIntValue5 + " secondaryColor=" + iIntValue4);
        FontFilterData fontFilterData = new FontFilterData(iIntValue, iIntValue3, iIntValue2, z);
        boolean z3 = false;
        updateFontFilter(0, fontFilterData);
        ClockInfo clockInfo2 = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo2.getClockInfo();
        if (Intrinsics.areEqual(clockInfo2 != null ? clockInfo2.getTemplateId() : null, "magazine_c") && (clockInfo2.getStyle() == 6 || clockInfo2.getStyle() == 9)) {
            z3 = true;
        }
        updateFontFilter(1, new FontFilterData(iIntValue, iIntValue5, iIntValue4, z3 ? true : z));
        updateDifferentColorStatus((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null || !clockInfo.isDiffHourMinuteColor()) ? 2 : 1, z);
    }

    public final void reloadTemplateConfigForWallpaperFilter(CommonConfig commonConfig, boolean z) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo3;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo4;
        TemplateConfig lockscreenInfo5;
        WallpaperInfo wallpaperInfo5;
        TemplateConfig lockscreenInfo6;
        WallpaperInfo wallpaperInfo6;
        TemplateConfig lockscreenInfo7;
        WallpaperInfo wallpaperInfo7;
        Integer numValueOf = null;
        Boolean boolValueOf = (commonConfig == null || (lockscreenInfo7 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo7 = lockscreenInfo7.getWallpaperInfo()) == null) ? null : Boolean.valueOf(wallpaperInfo7.getEnableBlur());
        if (commonConfig != null && (lockscreenInfo6 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo6 = lockscreenInfo6.getWallpaperInfo()) != null) {
            numValueOf = Integer.valueOf(wallpaperInfo6.getMagicType());
        }
        Log.d("EditFragmentViewModel", "reloadTemplateConfigForWallpaperFilter: matte=" + boolValueOf + " wallpaperFilter=" + numValueOf + " onlySet=" + z);
        int magicType = 0;
        int i = 3;
        if ((commonConfig != null ? commonConfig.getVersion() : 0) >= 2) {
            i = (commonConfig == null || (lockscreenInfo5 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo5 = lockscreenInfo5.getWallpaperInfo()) == null || !wallpaperInfo5.getEnableBlur()) ? false : true ? 2 : 3;
            if (commonConfig != null && (lockscreenInfo4 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo4 = lockscreenInfo4.getWallpaperInfo()) != null) {
                magicType = wallpaperInfo4.getMagicType();
            }
        } else {
            if ((commonConfig == null || (lockscreenInfo3 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo3.getWallpaperInfo()) == null || wallpaperInfo3.getMagicType() != 0) ? false : true) {
                WallpaperInfo wallpaperInfo8 = commonConfig.getLockscreenInfo().getWallpaperInfo();
                if (wallpaperInfo8 != null && wallpaperInfo8.getEnableBlur()) {
                    magicType = 30000;
                }
            } else {
                i = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo2.getWallpaperInfo()) == null || !wallpaperInfo2.getEnableBlur()) ? false : true ? 2 : 3;
                if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                    magicType = wallpaperInfo.getMagicType();
                }
            }
        }
        updateMatteStatus(i, z);
        updateWallpaperFilterId(magicType, z);
    }

    public final void setOldPrimaryFontFilter(FontFilterData fontFilterData) {
        this.oldPrimaryFontFilter = fontFilterData;
    }

    public final void setOldSecondaryFontFilter(FontFilterData fontFilterData) {
        this.oldSecondaryFontFilter = fontFilterData;
    }

    public final void setSelectTemplate(int i) {
        this.selectTemplate = i;
    }

    public final void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }

    public final void updateDifferentColorStatus(int i, boolean z) {
        Log.d("EditFragmentViewModel", "updateDifferentColorStatus: differentColorStatus=" + i + " onlySet=" + z);
        this.mDifferentColorStatus.postValue(new IntWrapper(i, z));
    }

    public final void updateDoodleStatus(int i, boolean z) {
        Log.d("EditFragmentViewModel", "updateDoodleStatus: mDoodleStatus=" + i + " onlySet=" + z);
        this.mDoodleStatus.postValue(new IntWrapper(i, z));
    }

    public final void updateFollowStatus(int i, boolean z, boolean z2) {
        Log.d("EditFragmentViewModel", "updateFollowStatus: status=" + i);
        IntWrapper intWrapper = new IntWrapper(i, z);
        if (z2) {
            this.mFollowStatus.setValue(intWrapper);
        } else {
            this.mFollowStatus.postValue(intWrapper);
        }
    }

    public final void updateFontFilter(int i, FontFilterData fontFilter) {
        Intrinsics.checkNotNullParameter(fontFilter, "fontFilter");
        if (i == 0) {
            updatePrimaryFontFilter(fontFilter);
        } else if (i == 1) {
            updateSecondaryFontFilter(fontFilter);
        }
        Log.d("EditFragmentViewModel", "updateFontFilter: targetIndex=" + i + ", fontFilter=" + fontFilter);
    }

    public final void updateHierarchyStatus(int i, boolean z, boolean z2, boolean z3) {
        IntWrapper intWrapper;
        IntWrapper intWrapper2;
        Log.d("EditFragmentViewModel", "update Hierarchy=" + i + " onlySet=" + z + " isSync=" + z2);
        if (i == 4 && ((((intWrapper = (IntWrapper) this.mHierarchyStatus.getValue()) != null && intWrapper.getIntValue() == 1) || (((intWrapper2 = (IntWrapper) this.mHierarchyStatus.getValue()) != null && intWrapper2.getIntValue() == 3) || z3)) && this.selectTemplate == 0)) {
            ToastUtil.INSTANCE.showText(R.string.kg_dialog_too_much_time_blocking, 0);
        }
        if (z2) {
            this.mHierarchyStatus.setValue(new IntWrapper(i, z));
        } else {
            this.mHierarchyStatus.postValue(new IntWrapper(i, z));
        }
    }

    public final void updateHomeFilterId(int i, boolean z, boolean z2) {
        IntWrapper intWrapper = new IntWrapper(i, z);
        if (z2) {
            this.mHomeFilterId.setValue(intWrapper);
        } else {
            this.mHomeFilterId.postValue(intWrapper);
        }
    }

    public final void updateHomeWallpaperStatus(int i, boolean z, boolean z2) {
        Log.d("EditFragmentViewModel", "updateHomeWallpaperStatus: status=" + i);
        IntWrapper intWrapper = new IntWrapper(i, z);
        if (z2) {
            this.mHomeWallpaperStatus.setValue(intWrapper);
        } else {
            this.mHomeWallpaperStatus.postValue(intWrapper);
        }
    }

    public final void updateMatteStatus(int i, boolean z) {
        Log.d("EditFragmentViewModel", "updateMatteStatus: matteStatus=" + i + " onlySet=" + z);
        this.mMatteStatus.postValue(new IntWrapper(i, z));
    }

    public final void updatePrimaryFontFilter(FontFilterData fontFilterData) {
        FontFilterData fontFilterData2 = (FontFilterData) this.mSecondaryFontFilter.getValue();
        if (fontFilterData2 != null) {
            fontFilterData2.setFilterId(fontFilterData.getFilterId());
        }
        this.mPrimaryFontFilter.postValue(fontFilterData);
    }

    public final void updateSecondaryFontFilter(FontFilterData fontFilterData) {
        Log.d("EditFragmentViewModel", "updateSecondaryFontFilter: fontFilter=" + fontFilterData);
        FontFilterData fontFilterData2 = (FontFilterData) this.mPrimaryFontFilter.getValue();
        if (fontFilterData2 != null) {
            fontFilterData2.setFilterId(fontFilterData.getFilterId());
        }
        this.mSecondaryFontFilter.postValue(fontFilterData);
    }

    public final void updateWallpaperFilterId(int i, boolean z) {
        Log.d("EditFragmentViewModel", "update wallpaper filter id=" + i + " onlySet=" + z);
        this.mWallpaperFilterId.postValue(new IntWrapper(i, z));
    }

    public final void updateWallpaperFilterIdSync(int i, boolean z) {
        Log.d("EditFragmentViewModel", "sync update wallpaper filter id=" + i + " onlySet=" + z);
        this.mWallpaperFilterId.setValue(new IntWrapper(i, z));
    }
}
