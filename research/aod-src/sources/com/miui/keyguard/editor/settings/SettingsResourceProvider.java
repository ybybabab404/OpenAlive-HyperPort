package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.util.CrossListViewUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SettingsResourceProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsResourceProvider {
    public static final SettingsResourceProvider INSTANCE = new SettingsResourceProvider();

    public final float provideApplyButtonHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_template_apply_button_height);
    }

    public final float provideApplyButtonMargin(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_template_apply_button_margin_top);
    }

    public final int provideCustomMarginButtonBottom(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_item_customize_button_margin_bottom);
        }
        if (deviceUtil.isQ18()) {
            return deviceUtil.isLargeDisplay(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_customize_button_margin_bottom) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_customize_button_margin_bottom);
        }
        if (deviceUtil.isFold() && deviceUtil.isLargeDisplay(context)) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_item_customize_button_margin_bottom);
        }
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_customize_button_margin_bottom);
    }

    public final int provideDeskTopIconDrawable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return deviceUtil.isDisplayLandScape(context) ? R.drawable.kg_settings_negative_desktop_placeholder_pad_land : R.drawable.kg_settings_negative_desktop_placeholder_pad_port;
        }
        if (deviceUtil.isQ18()) {
            if (deviceUtil.isDisplayLandScape(context)) {
                return R.drawable.kg_settings_negative_desktop_placeholder_fold_q18_land;
            }
            return deviceUtil.isLargeDisplay(context) ? R.drawable.kg_settings_negative_desktop_placeholder_fold_q18_port : R.drawable.kg_settings_negative_desktop_placeholder_q18;
        }
        if (!deviceUtil.isFold()) {
            return R.drawable.kg_settings_negative_desktop_placeholder;
        }
        if (deviceUtil.isDisplayLandScape(context)) {
            return R.drawable.kg_settings_negative_desktop_placeholder_fold_land;
        }
        return deviceUtil.isLargeDisplay(context) ? R.drawable.kg_settings_negative_desktop_placeholder_fold_port : R.drawable.kg_settings_negative_desktop_placeholder;
    }

    public final int provideSettingsAddButtonSize(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_item_template_add_button_size);
        }
        if (deviceUtil.isQ18()) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_template_add_button_size);
        }
        if (deviceUtil.isFold() && deviceUtil.isLargeDisplay(context)) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_item_template_add_button_size);
        }
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_template_add_button_size);
    }

    public final int provideSettingsTemplateSpacing(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_item_template_spacing);
        }
        if (deviceUtil.isQ18()) {
            return deviceUtil.isLargeDisplay(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_template_spacing) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_template_spacing_outer);
        }
        if (deviceUtil.isFold() && deviceUtil.isLargeDisplay(context)) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_item_template_spacing);
        }
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_template_spacing);
    }

    public final int provideSettingsViewWidth(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            if (deviceUtil.isDisplayLandScape(context)) {
                return deviceUtil.isInFreeform(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_dktmode_view_pager_width_land) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_view_pager_width_land);
            }
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_view_pager_width_port);
        }
        if (deviceUtil.isQ18()) {
            if (deviceUtil.isDisplayLandScape(context)) {
                return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_view_pager_width_land);
            }
            return deviceUtil.isLargeDisplay(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_view_pager_width_port) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_view_pager_width_outer);
        }
        if (!deviceUtil.isFold()) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_view_pager_width);
        }
        if (deviceUtil.isDisplayLandScape(context)) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_view_pager_width_land);
        }
        return deviceUtil.isLargeDisplay(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_view_pager_width_port) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_view_pager_width);
    }

    public final float provideSettingsViewWithRedDotHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return provideTemplateContainerHeight(context) + providerDeleteHoldSpace(context);
    }

    public final int provideTemplateContainerHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (int) CrossListViewUtil.calculateTemplateContainerHeight$default(CrossListViewUtil.INSTANCE, context, provideTemplateContainerWidth(context), null, 0.0f, 4, null);
    }

    public final int provideTemplateContainerWidth(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            if (deviceUtil.isDisplayLandScape(context)) {
                return deviceUtil.isInFreeform(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_dktmode_item_template_width_land) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_item_template_width_land);
            }
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_item_template_width_port);
        }
        if (deviceUtil.isQ18()) {
            if (deviceUtil.isDisplayLandScape(context)) {
                return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_template_width_land);
            }
            return deviceUtil.isLargeDisplay(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_template_width_port) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_template_width_outer);
        }
        if (!deviceUtil.isFold()) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_template_width);
        }
        if (deviceUtil.isDisplayLandScape(context)) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_item_template_width_land);
        }
        return deviceUtil.isLargeDisplay(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_item_template_width_port) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_template_width);
    }

    public final int provideTemplateRadius(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_pad_item_template_radius);
        }
        if (deviceUtil.isQ18()) {
            return deviceUtil.isLargeDisplay(context) ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_q18_item_template_radius) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_template_radius);
        }
        return deviceUtil.isFold() ? context.getResources().getDimensionPixelSize(R.dimen.kg_settings_fold_item_template_radius) : context.getResources().getDimensionPixelSize(R.dimen.kg_settings_item_template_radius);
    }

    public final float provideTemplateScale(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPad()) {
            return deviceUtil.isDisplayLandScape(context) ? context.getResources().getFloat(R.dimen.kg_settings_pad_template_scale_land) : context.getResources().getFloat(R.dimen.kg_settings_pad_template_scale_port);
        }
        if (!deviceUtil.isQ18()) {
            return deviceUtil.isFold() ? context.getResources().getFloat(R.dimen.kg_settings_fold_template_scale) : context.getResources().getFloat(R.dimen.kg_settings_template_scale);
        }
        if (deviceUtil.isDisplayLandScape(context)) {
            return context.getResources().getFloat(R.dimen.kg_settings_q18_template_scale_land);
        }
        return deviceUtil.isLargeDisplay(context) ? context.getResources().getFloat(R.dimen.kg_settings_q18_template_scale_port) : context.getResources().getFloat(R.dimen.kg_settings_q18_template_scale_outer);
    }

    public final float provideUsingTextViewHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_template_apply_button_height);
    }

    public final float provideUsingTextViewTopMargin(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_template_apply_button_margin_top);
    }

    public final float provideWholeSettingsViewHeight(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return provideSettingsViewWithRedDotHeight(context) + provideApplyButtonHeight(context) + provideApplyButtonMargin(context);
    }

    public final float providerDeleteHoldSpace(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.kg_settings_red_dot_hold_space);
    }

    public final Point requireDisplayRealSize(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Point point = new Point();
        Display display = context.getDisplay();
        if (display != null) {
            display.getRealSize(point);
        }
        return point;
    }
}
