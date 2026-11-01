package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.history.common.ViewExtKt;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.settings.data.HomeColumnDataSource;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: SettingsTemplateAdapterAndHoldImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsNegativeViewHolder extends BaseViewHolder<TemplateItemBean, CrossViewPagerCallback> implements ISettingsViewHolder {
    public final View addButton;
    public final ImageView addDesktopView;
    public final View addKeyguardView;
    public final View itemView;
    public final int templateContainerHeight;
    public final int templateContainerWidth;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsNegativeViewHolder(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.itemView = itemView;
        this.addButton = itemView.findViewById(R.id.item_template_add_button);
        this.addKeyguardView = itemView.findViewById(R.id.view_template_negative_keyguard_place_holder);
        this.addDesktopView = (ImageView) itemView.findViewById(R.id.view_template_negative_desktop_place_holder);
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.templateContainerWidth = settingsResourceProvider.provideTemplateContainerWidth(context);
        Context context2 = itemView.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        this.templateContainerHeight = settingsResourceProvider.provideTemplateContainerHeight(context2);
    }

    public static final Unit onViewHolderBound$lambda$4(SettingsNegativeViewHolder settingsNegativeViewHolder, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        CrossViewPagerCallback crossViewPagerCallback = (CrossViewPagerCallback) settingsNegativeViewHolder.getCallback();
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onAddTemplate();
        }
        return Unit.INSTANCE;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public View getHomeTemplateView() {
        return this.addDesktopView;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public View getLockTemplateView() {
        return this.addKeyguardView;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public View getSelfItemView() {
        return this.itemView;
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public View getTargetContentView() {
        return this.itemView;
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isHomeDarkColor() {
        return ISettingsViewHolder.DefaultImpls.isHomeDarkColor(this);
    }

    @Override // com.miui.keyguard.editor.settings.ISettingsViewHolder
    public boolean isLockDarkColor() {
        return ISettingsViewHolder.DefaultImpls.isLockDarkColor(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderBound(TemplateItemBean templateItemBean, int i) {
        super.onViewHolderBound((Object) templateItemBean, i);
        if (DeviceUtil.INSTANCE.isPhone()) {
            HomeColumnDataSource homeColumnDataSource = HomeColumnDataSource.INSTANCE;
            Context context = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (homeColumnDataSource.getHomeColumns(context) == 5) {
                this.addDesktopView.setImageResource(R.drawable.kg_settings_negative_desktop_placeholder_five);
            } else {
                this.addDesktopView.setImageResource(R.drawable.kg_settings_negative_desktop_placeholder);
            }
        } else {
            ImageView imageView = this.addDesktopView;
            SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
            Context context2 = imageView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            int iProvideTemplateRadius = settingsResourceProvider.provideTemplateRadius(context2);
            Context context3 = this.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            imageView.setImageResource(settingsResourceProvider.provideDeskTopIconDrawable(context3));
            imageView.setClipToOutline(true);
            imageView.setOutlineProvider(new RoundOutlineProvider(iProvideTemplateRadius));
        }
        View addKeyguardView = this.addKeyguardView;
        Intrinsics.checkNotNullExpressionValue(addKeyguardView, "addKeyguardView");
        ViewExtKt.clickNoRepeat$default(addKeyguardView, 0L, new Function1() { // from class: com.miui.keyguard.editor.settings.SettingsNegativeViewHolder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return SettingsNegativeViewHolder.onViewHolderBound$lambda$4(this.f$0, (View) obj);
            }
        }, 1, null);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder
    public void onViewHolderCreated(View itemView) {
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        View view = this.addButton;
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int iProvideSettingsAddButtonSize = settingsResourceProvider.provideSettingsAddButtonSize(context);
        view.getLayoutParams().width = iProvideSettingsAddButtonSize;
        view.getLayoutParams().height = iProvideSettingsAddButtonSize;
        Folme.useAt(this.addKeyguardView).touch().handleTouchOf(this.addKeyguardView, new AnimConfig[0]);
        View view2 = this.addKeyguardView;
        view2.getLayoutParams().width = this.templateContainerWidth;
        view2.getLayoutParams().height = this.templateContainerHeight;
        ImageView imageView = this.addDesktopView;
        imageView.getLayoutParams().width = this.templateContainerWidth;
        imageView.getLayoutParams().height = this.templateContainerHeight;
    }
}
