package com.miui.aod.flip;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.FlipLinkageClockCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.util.ReflectUtil;
import com.miui.aod.widget.Direction;
import com.miui.clock.tiny.model.TinyClockBean;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.utils.GlobalColorUtils;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FlipLinkageStyleClockView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FlipLinkageStyleClockView extends ConstraintLayout {
    public final Lazy clockFrameLayout$delegate;
    public boolean isBindView;
    public View mClockInfoContainer;
    public final Context mContext;
    public View mIcon;
    public AODTinyMiuiClockView mMiuiClockView;
    public FlipLinkageClockCategoryInfo mStyleInfo;
    public FlipLinkageTemplateId templateId;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public FlipLinkageStyleClockView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public FlipLinkageStyleClockView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public FlipLinkageStyleClockView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mContext = context;
        this.clockFrameLayout$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.flip.FlipLinkageStyleClockView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FlipLinkageStyleClockView.clockFrameLayout_delegate$lambda$0(this.f$0);
            }
        });
    }

    public /* synthetic */ FlipLinkageStyleClockView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public static final FrameLayout clockFrameLayout_delegate$lambda$0(FlipLinkageStyleClockView flipLinkageStyleClockView) {
        return (FrameLayout) flipLinkageStyleClockView.findViewById(R.id.flip_clock_container);
    }

    private final FrameLayout getClockFrameLayout() {
        Object value = this.clockFrameLayout$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (FrameLayout) value;
    }

    public final void bindView() {
        this.mClockInfoContainer = findViewById(R.id.clock_info_container);
        this.mIcon = findViewById(R.id.icons);
        this.isBindView = true;
    }

    public final void changeRotation(Direction direction) {
        Intrinsics.checkNotNullParameter(direction, "direction");
        Log.v("FlipLinkageStyleClockViewType", "changeRotation: " + direction);
        AODTinyMiuiClockView aODTinyMiuiClockView = null;
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_LEFT) {
            AODTinyMiuiClockView aODTinyMiuiClockView2 = this.mMiuiClockView;
            if (aODTinyMiuiClockView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                aODTinyMiuiClockView = aODTinyMiuiClockView2;
            }
            aODTinyMiuiClockView.changeRotation(0);
            return;
        }
        if (direction == Direction.CAMERA_CUTOUT_ON_THE_RIGHT) {
            AODTinyMiuiClockView aODTinyMiuiClockView3 = this.mMiuiClockView;
            if (aODTinyMiuiClockView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            } else {
                aODTinyMiuiClockView = aODTinyMiuiClockView3;
            }
            aODTinyMiuiClockView.changeRotation(2);
        }
    }

    @Nullable
    public final TinyClockBean getClockBean() {
        AODTinyMiuiClockView aODTinyMiuiClockView = this.mMiuiClockView;
        if (aODTinyMiuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            aODTinyMiuiClockView = null;
        }
        return aODTinyMiuiClockView.getClockBean();
    }

    @Nullable
    public final AODTinyMiuiClockView getMiuiClockView() {
        AODTinyMiuiClockView aODTinyMiuiClockView = this.mMiuiClockView;
        if (aODTinyMiuiClockView != null) {
            return aODTinyMiuiClockView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
        return null;
    }

    public final int getRealViewHeight() {
        int measuredHeight = getClockFrameLayout().getMeasuredHeight();
        View view = this.mClockInfoContainer;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockInfoContainer");
            view = null;
        }
        return measuredHeight + view.getMeasuredHeight();
    }

    public final void initClockView(FlipLinkageTemplateId templateId) {
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        this.templateId = templateId;
        AODTinyMiuiClockView aODTinyMiuiClockView = this.mMiuiClockView;
        FlipLinkageClockCategoryInfo flipLinkageClockCategoryInfo = null;
        if (aODTinyMiuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            aODTinyMiuiClockView = null;
        }
        TinyClockBean tinyClockBean = new TinyClockBean();
        String lowerCase = templateId.toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        tinyClockBean.setTemplateId(lowerCase);
        FlipLinkageClockCategoryInfo flipLinkageClockCategoryInfo2 = this.mStyleInfo;
        if (flipLinkageClockCategoryInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
        } else {
            flipLinkageClockCategoryInfo = flipLinkageClockCategoryInfo2;
        }
        FlipClockInfo clockInfo = flipLinkageClockCategoryInfo.getClockInfo();
        tinyClockBean.setPrimaryColor(GlobalColorUtils.transformAodColor(clockInfo.getPrimaryColor()));
        tinyClockBean.setPrimaryColor180(GlobalColorUtils.transformAodColor(clockInfo.getPrimaryColor180()));
        Log.d("FlipLinkageStyleClockViewType", "aodColor: 0:#" + Integer.toHexString(tinyClockBean.getPrimaryColor()) + " 180:#" + Integer.toHexString(tinyClockBean.getPrimaryColor180()) + " , startColor: 0:#" + Integer.toHexString(clockInfo.getPrimaryColor()) + " 180:#" + Integer.toHexString(clockInfo.getPrimaryColor180()));
        tinyClockBean.setWallpaperDark(clockInfo.getWallpaperDark());
        tinyClockBean.setWallpaperDark180(clockInfo.getWallpaperDark180());
        tinyClockBean.setStyle(clockInfo.getStyle());
        tinyClockBean.setBlendColor(clockInfo.getBlendColor());
        tinyClockBean.setBlendColor180(clockInfo.getBlendColor180());
        tinyClockBean.setNeedInverseColor(clockInfo.getNeedInverseColor());
        aODTinyMiuiClockView.init(tinyClockBean);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        Context contextCreateConfigurationContext = this.mContext.createConfigurationContext((Utils.isAodProcess() ? AODApplication.aodSettingDensityContext : AODApplication.sysuiContext).getResources().getConfiguration());
        Intrinsics.checkNotNullExpressionValue(contextCreateConfigurationContext, "createConfigurationContext(...)");
        if (!Utils.isAodProcess()) {
            try {
                ReflectUtil.setObjectField(contextCreateConfigurationContext, "mClassLoader", this.mContext.getClassLoader());
            } catch (Exception e) {
                Log.d("FlipLinkageStyleClockViewType", "error:", e);
            }
        }
        LayoutInflater.from(contextCreateConfigurationContext).inflate(R.layout.aod_content_linkage_flip_clock, (ViewGroup) getClockFrameLayout(), true);
        AODTinyMiuiClockView aODTinyMiuiClockView = (AODTinyMiuiClockView) getClockFrameLayout().findViewById(R.id.flip_clock);
        this.mMiuiClockView = aODTinyMiuiClockView;
        if (aODTinyMiuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            aODTinyMiuiClockView = null;
        }
        aODTinyMiuiClockView.setGlobalContext(this.mContext);
    }

    public final void setClockViewVisible(boolean z) {
        FlipLinkageTemplateId flipLinkageTemplateId = this.templateId;
        AODTinyMiuiClockView aODTinyMiuiClockView = null;
        if (flipLinkageTemplateId == null) {
            Intrinsics.throwUninitializedPropertyAccessException("templateId");
            flipLinkageTemplateId = null;
        }
        if (flipLinkageTemplateId != FlipLinkageTemplateId.SPLICING) {
            FlipLinkageTemplateId flipLinkageTemplateId2 = this.templateId;
            if (flipLinkageTemplateId2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("templateId");
                flipLinkageTemplateId2 = null;
            }
            if (flipLinkageTemplateId2 != FlipLinkageTemplateId.Classic) {
                FlipLinkageTemplateId flipLinkageTemplateId3 = this.templateId;
                if (flipLinkageTemplateId3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("templateId");
                    flipLinkageTemplateId3 = null;
                }
                if (flipLinkageTemplateId3 != FlipLinkageTemplateId.Doodle) {
                    FlipLinkageTemplateId flipLinkageTemplateId4 = this.templateId;
                    if (flipLinkageTemplateId4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("templateId");
                        flipLinkageTemplateId4 = null;
                    }
                    if (flipLinkageTemplateId4 != FlipLinkageTemplateId.Pets) {
                        FlipLinkageTemplateId flipLinkageTemplateId5 = this.templateId;
                        if (flipLinkageTemplateId5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("templateId");
                            flipLinkageTemplateId5 = null;
                        }
                        if (flipLinkageTemplateId5 != FlipLinkageTemplateId.Bubble) {
                            FlipLinkageTemplateId flipLinkageTemplateId6 = this.templateId;
                            if (flipLinkageTemplateId6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("templateId");
                                flipLinkageTemplateId6 = null;
                            }
                            if (flipLinkageTemplateId6 != FlipLinkageTemplateId.Interactive_Pets) {
                                FlipLinkageTemplateId flipLinkageTemplateId7 = this.templateId;
                                if (flipLinkageTemplateId7 == null) {
                                    Intrinsics.throwUninitializedPropertyAccessException("templateId");
                                    flipLinkageTemplateId7 = null;
                                }
                                if (flipLinkageTemplateId7 != FlipLinkageTemplateId.AI_Pets) {
                                    AODTinyMiuiClockView aODTinyMiuiClockView2 = this.mMiuiClockView;
                                    if (aODTinyMiuiClockView2 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                                    } else {
                                        aODTinyMiuiClockView = aODTinyMiuiClockView2;
                                    }
                                    aODTinyMiuiClockView.setVisibility(z ? 0 : 4);
                                    return;
                                }
                            }
                            AODTinyMiuiClockView aODTinyMiuiClockView3 = this.mMiuiClockView;
                            if (aODTinyMiuiClockView3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                            } else {
                                aODTinyMiuiClockView = aODTinyMiuiClockView3;
                            }
                            aODTinyMiuiClockView.getIClockView(TinyClockViewType.THIS).setVisibility(0);
                            return;
                        }
                    }
                }
                AODTinyMiuiClockView aODTinyMiuiClockView4 = this.mMiuiClockView;
                if (aODTinyMiuiClockView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
                } else {
                    aODTinyMiuiClockView = aODTinyMiuiClockView4;
                }
                aODTinyMiuiClockView.getIClockView(TinyClockViewType.TIME).setVisibility(z ? 0 : 4);
                return;
            }
        }
        AODTinyMiuiClockView aODTinyMiuiClockView5 = this.mMiuiClockView;
        if (aODTinyMiuiClockView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            aODTinyMiuiClockView5 = null;
        }
        aODTinyMiuiClockView5.getIClockView(TinyClockViewType.DATE).setVisibility(z ? 0 : 4);
        AODTinyMiuiClockView aODTinyMiuiClockView6 = this.mMiuiClockView;
        if (aODTinyMiuiClockView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
        } else {
            aODTinyMiuiClockView = aODTinyMiuiClockView6;
        }
        aODTinyMiuiClockView.getIClockView(TinyClockViewType.TIME).setVisibility(z ? 0 : 4);
    }

    public final void setStyleInfo(@NotNull StyleInfo styleInfo) {
        Intrinsics.checkNotNullParameter(styleInfo, "styleInfo");
        this.mStyleInfo = (FlipLinkageClockCategoryInfo) styleInfo;
    }

    public final void updateTime() {
        AODTinyMiuiClockView aODTinyMiuiClockView = this.mMiuiClockView;
        if (aODTinyMiuiClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mMiuiClockView");
            aODTinyMiuiClockView = null;
        }
        aODTinyMiuiClockView.updateTime();
    }
}
