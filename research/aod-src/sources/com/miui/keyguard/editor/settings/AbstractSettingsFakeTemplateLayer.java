package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.homepage.view.CustomButtonView;
import com.miui.keyguard.editor.settings.data.HomeColumnDataSource;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
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
/* JADX INFO: compiled from: AbstractSettingsFakeTemplateLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class AbstractSettingsFakeTemplateLayer extends FrameLayout {
    public final int containerHeight;
    public final int containerWidth;
    public boolean isUseBlur;
    public RecyclerView recyclerView;
    public final int templateHeight;
    public final int templateWidth;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public AbstractSettingsFakeTemplateLayer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public AbstractSettingsFakeTemplateLayer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        this.templateWidth = settingsResourceProvider.provideTemplateContainerWidth(context);
        this.templateHeight = settingsResourceProvider.provideTemplateContainerHeight(context);
        this.containerWidth = settingsResourceProvider.provideSettingsViewWidth(context);
        this.containerHeight = (int) settingsResourceProvider.provideSettingsViewWithRedDotHeight(context);
    }

    public /* synthetic */ AbstractSettingsFakeTemplateLayer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final Unit fillContent$lambda$6$lambda$4(CustomButtonView customButtonView, AbstractSettingsFakeTemplateLayer abstractSettingsFakeTemplateLayer, ISettingsViewHolder iSettingsViewHolder) {
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        Intrinsics.checkNotNull(customButtonView);
        viewUtil.decorateCustomButton(customButtonView, abstractSettingsFakeTemplateLayer.isUseBlur, iSettingsViewHolder.isLockDarkColor());
        return Unit.INSTANCE;
    }

    public static final Unit fillContent$lambda$6$lambda$5(AbstractSettingsFakeTemplateLayer abstractSettingsFakeTemplateLayer, ImageView imageView, ISettingsViewHolder iSettingsViewHolder, CustomButtonView customButtonView) {
        Intrinsics.checkNotNull(imageView);
        abstractSettingsFakeTemplateLayer.bindDesktopIconPreview(imageView, iSettingsViewHolder.isHomeDarkColor());
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        Intrinsics.checkNotNull(customButtonView);
        viewUtil.decorateCustomButton(customButtonView, abstractSettingsFakeTemplateLayer.isUseBlur, iSettingsViewHolder.isHomeDarkColor());
        return Unit.INSTANCE;
    }

    public final void bindDesktopIconPreview(ImageView imageView, boolean z) {
        if (!DeviceUtil.INSTANCE.isPhone()) {
            CommonUtils commonUtils = CommonUtils.INSTANCE;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            imageView.setImageResource(commonUtils.getDeskColorBlockForColumn(context, z));
            return;
        }
        HomeColumnDataSource homeColumnDataSource = HomeColumnDataSource.INSTANCE;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        if (homeColumnDataSource.getHomeColumns(context2) == 5) {
            imageView.setImageResource(!z ? R.drawable.kg_miui_home_desktop_five_black : R.drawable.kg_miui_home_desktop_five_white);
        } else {
            imageView.setImageResource(!z ? R.drawable.kg_miui_home_desktop_black : R.drawable.kg_miui_home_desktop_white);
        }
    }

    public final void fillContent(ViewGroup container, final ISettingsViewHolder iSettingsViewHolder) {
        Intrinsics.checkNotNullParameter(container, "container");
        if (iSettingsViewHolder == null || iSettingsViewHolder.getLockTemplateView() == null || iSettingsViewHolder.getHomeTemplateView() == null || (iSettingsViewHolder instanceof SettingsNegativeViewHolder)) {
            return;
        }
        ImageView imageView = (ImageView) container.findViewById(R.id.lock_content);
        ImageView imageView2 = (ImageView) container.findViewById(R.id.home_content);
        final ImageView imageView3 = (ImageView) container.findViewById(R.id.home_preview);
        final CustomButtonView customButtonView = (CustomButtonView) container.findViewById(R.id.customize_keyguard_btn);
        customButtonView.setVisibility(0);
        final CustomButtonView customButtonView2 = (CustomButtonView) container.findViewById(R.id.customize_desktop_btn);
        customButtonView2.setVisibility(0);
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        int iProvideCustomMarginButtonBottom = settingsResourceProvider.provideCustomMarginButtonBottom(context);
        Intrinsics.checkNotNull(customButtonView);
        ViewUtil.setMarginBottom(customButtonView, iProvideCustomMarginButtonBottom);
        Intrinsics.checkNotNull(customButtonView2);
        ViewUtil.setMarginBottom(customButtonView2, iProvideCustomMarginButtonBottom);
        Intrinsics.checkNotNull(imageView);
        setContent(imageView, iSettingsViewHolder.getLockTemplateView(), new Function0() { // from class: com.miui.keyguard.editor.settings.AbstractSettingsFakeTemplateLayer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AbstractSettingsFakeTemplateLayer.fillContent$lambda$6$lambda$4(customButtonView, this, iSettingsViewHolder);
            }
        });
        Intrinsics.checkNotNull(imageView2);
        setContent(imageView2, iSettingsViewHolder.getHomeTemplateView(), new Function0() { // from class: com.miui.keyguard.editor.settings.AbstractSettingsFakeTemplateLayer$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return AbstractSettingsFakeTemplateLayer.fillContent$lambda$6$lambda$5(this.f$0, imageView3, iSettingsViewHolder, customButtonView2);
            }
        });
    }

    public final RecyclerView findRecyclerViewInViewPager(ViewPager2 viewPager2) {
        View childAt = viewPager2.getChildAt(0);
        if (childAt instanceof RecyclerView) {
            return (RecyclerView) childAt;
        }
        return null;
    }

    public final ISettingsViewHolder findViewHolder(RecyclerView recyclerView, int i) {
        Object objFindViewHolderForAdapterPosition = recyclerView != null ? recyclerView.findViewHolderForAdapterPosition(i) : null;
        if (objFindViewHolderForAdapterPosition instanceof ISettingsViewHolder) {
            return (ISettingsViewHolder) objFindViewHolderForAdapterPosition;
        }
        return null;
    }

    public final int getContainerHeight() {
        return this.containerHeight;
    }

    public final int getContainerWidth() {
        return this.containerWidth;
    }

    @Nullable
    public final RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    public void initLayout(int i, ViewPager2 viewPager2, boolean z) {
        Intrinsics.checkNotNullParameter(viewPager2, "viewPager2");
        this.isUseBlur = z;
        this.recyclerView = findRecyclerViewInViewPager(viewPager2);
    }

    public final void setContent(ImageView imageView, View view, Function0 function0) {
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        viewUtil.setWidth(imageView, this.templateWidth);
        viewUtil.setHeight(imageView, this.templateHeight);
        imageView.setClipToOutline(true);
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = imageView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(settingsResourceProvider.provideTemplateRadius(context));
        float f = 1;
        roundOutlineProvider.setRadius(roundOutlineProvider.getRadius() * RangesKt.coerceAtLeast(f / imageView.getScaleX(), f / imageView.getScaleY()));
        imageView.setOutlineProvider(roundOutlineProvider);
        if (view instanceof BaseTemplateView) {
            viewUtil.takeScreenshot((BaseTemplateView) view, new AbstractSettingsFakeTemplateLayer$setContent$2$2(imageView, function0, null));
        } else {
            imageView.setImageBitmap(view != null ? viewUtil.drawToBitmap(view) : null);
            function0.invoke();
        }
    }

    public final void setNeighboringContainerLayout(@NotNull ViewGroup container, @Nullable View view) {
        Intrinsics.checkNotNullParameter(container, "container");
        if (view == null) {
            return;
        }
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        viewUtil.setWidth(container, this.containerWidth);
        viewUtil.setHeight(container, this.containerHeight);
        SettingsResourceProvider settingsResourceProvider = SettingsResourceProvider.INSTANCE;
        Context context = container.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        container.setScaleX(settingsResourceProvider.provideTemplateScale(context));
        Context context2 = container.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        container.setScaleY(settingsResourceProvider.provideTemplateScale(context2));
        container.setTranslationX(view.getTranslationX());
        container.setTranslationY(view.getTranslationY());
        container.setX(view.getX());
        container.setY(view.getY());
    }

    public final void setRecyclerView(@Nullable RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public final void setTargetContainerLayout(@NotNull ViewGroup container, @Nullable View view) {
        Intrinsics.checkNotNullParameter(container, "container");
        if (view == null) {
            return;
        }
        ViewUtil viewUtil = ViewUtil.INSTANCE;
        viewUtil.setWidth(container, this.containerWidth);
        viewUtil.setHeight(container, this.containerHeight);
        container.setTranslationX(view.getTranslationX());
        container.setTranslationY(view.getTranslationY());
        container.setX(view.getX());
        container.setY(view.getY());
    }
}
