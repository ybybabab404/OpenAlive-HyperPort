package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.classic.ClassicContentAreaView;
import com.miui.clock.classic.ClassicTextAreaView;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.ClockViewType;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.clock.ClassicSelectorBean;
import com.miui.keyguard.editor.utils.ClockUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: ClassicTextStyleSelectorAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClassicTextStyleSelectorAdapter extends StyleSelectorAdapter<ClassicSelectorBean> {
    public final int color;
    public int fontStyle;
    public final boolean isPlus;
    public final int itemLayout;
    public final OnClassicTextStyleSelectedListener listener;
    public Context scaleContext;
    public final int sourceDensityApi;
    public final boolean textAreaIsEmpty;
    public final ClockViewType type;

    /* JADX INFO: compiled from: ClassicTextStyleSelectorAdapter.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            try {
                iArr[ClockViewType.TEXT_AREA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockViewType.TIME_AREA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClockViewType.CONTENT_AREA1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ClockViewType.CONTENT_AREA2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicTextStyleSelectorAdapter(Context context, int i, ClockViewType type, int i2, List data, int i3, int i4, boolean z, OnClassicTextStyleSelectedListener onClassicTextStyleSelectedListener, boolean z2) {
        super(data);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(data, "data");
        this.itemLayout = i;
        this.type = type;
        this.color = i3;
        this.fontStyle = i4;
        this.isPlus = z;
        this.listener = onClassicTextStyleSelectedListener;
        this.textAreaIsEmpty = z2;
        this.sourceDensityApi = context.getResources().getConfiguration().densityDpi;
        setSelectedIndex(initSelectIndex(i2));
    }

    public final View createStyleView(ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        ClockViewType clockViewType = this.type;
        if (clockViewType == ClockViewType.CONTENT_AREA1 || clockViewType == ClockViewType.CONTENT_AREA2 || clockViewType == ClockViewType.TIME_AREA) {
            Context context2 = this.scaleContext;
            if (context2 == null) {
                Configuration configuration = new Configuration();
                configuration.setTo(context.getResources().getConfiguration());
                configuration.densityDpi = (int) (this.sourceDensityApi * 0.375f);
                context = context.createConfigurationContext(configuration);
                this.scaleContext = context;
            } else {
                context = context2;
            }
        }
        View viewInflate = LayoutInflater.from(context).inflate(getStyleTextViewLayoutId(), viewGroup, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        return viewInflate;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return ((ClassicSelectorBean) getData().get(i)).getBeanType();
    }

    public final int getStyleTextViewLayoutId() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.type.ordinal()];
        if (i == 1) {
            return R.layout.kg_layout_classic_text_area_item;
        }
        if (i == 2) {
            return R.layout.kg_layout_classic_clock_area_item;
        }
        if (i == 3 || i == 4) {
            return com.miui.clock.R.layout.miui_classic_clock_content_area;
        }
        throw new RuntimeException("unsupported ClockViewType:" + this.type.ordinal());
    }

    public final int initSelectIndex(int i) {
        int i2 = 0;
        if (this.textAreaIsEmpty) {
            return 0;
        }
        int actualRenderStyle = ClassicContentStyle.getActualRenderStyle(i);
        Iterator it = getData().iterator();
        while (it.hasNext()) {
            if (((ClassicSelectorBean) it.next()).getStyle() == actualRenderStyle) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public int itemLayoutId() {
        return 0;
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public void onBind(View view, int i) {
        View viewFindViewById;
        super.onBind(view, i);
        ClassicSelectorBean classicSelectorBean = (ClassicSelectorBean) getData().get(i);
        int beanType = classicSelectorBean.getBeanType();
        ClassicSelectorBean.Companion companion = ClassicSelectorBean.Companion;
        if (beanType != companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL()) {
            if (beanType == companion.getCLASSIC_SELECTOR_BEAN_TYPE_LABEL()) {
                updateTypeGroup(view, classicSelectorBean.getLabelResId(), true, false);
                return;
            }
            if (beanType == companion.getCLASSIC_SELECTOR_BEAN_TYPE_ALL()) {
                updateTypeGroup(view, classicSelectorBean.getLabelResId(), true, true);
                return;
            }
            updateTypeGroup(view, classicSelectorBean.getLabelResId(), false, false);
            Log.d("ClassicTextStyleSelectorAdapter", "wrong bean type=" + beanType);
            return;
        }
        View viewFindViewById2 = view != null ? view.findViewById(R.id.kg_classic_text_style_view) : null;
        if (viewFindViewById2 instanceof ClassicTextAreaView) {
            ClassicTextAreaView classicTextAreaView = (ClassicTextAreaView) viewFindViewById2;
            ClockUtil.setClassicClockTextAreaStyle$default(ClockUtil.INSTANCE, classicTextAreaView, this.color, classicSelectorBean, false, 8, null);
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            Context context = classicTextAreaView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            classicTextAreaView.setClockDarkMode(viewUtil.getDarkMode(context));
        } else {
            View view2 = viewFindViewById2;
            if (view2 instanceof MiuiTextGlassView) {
                MiuiTextGlassView miuiTextGlassView = (MiuiTextGlassView) view2;
                miuiTextGlassView.setTimeLayoutDirection(3);
                ClockUtil.INSTANCE.setClassicClockTimeAreaStyle(miuiTextGlassView, this.color, classicSelectorBean, this.fontStyle, this.isPlus);
                ViewUtil viewUtil2 = ViewUtil.INSTANCE;
                Context context2 = miuiTextGlassView.getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                miuiTextGlassView.setClockDarkMode(viewUtil2.getDarkMode(context2));
            } else if (view2 instanceof ClassicContentAreaView) {
                ClassicContentAreaView classicContentAreaView = (ClassicContentAreaView) view2;
                ClockUtil.INSTANCE.setClassicContentAreaStyle(classicContentAreaView, this.color, classicSelectorBean, this.fontStyle);
                ViewUtil viewUtil3 = ViewUtil.INSTANCE;
                Context context3 = classicContentAreaView.getContext();
                Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                classicContentAreaView.setClockDarkMode(viewUtil3.getDarkMode(context3));
            }
        }
        View viewFindViewById3 = view != null ? view.findViewById(R.id.classic_text_style_select_view_place_holder) : null;
        if (viewFindViewById3 != null) {
            viewFindViewById3.setVisibility(classicSelectorBean.getStyle() != 0 ? 8 : 0);
        }
        if (view != null && (viewFindViewById = view.findViewById(R.id.classic_text_style_select_view_container)) != null) {
            viewFindViewById.setBackgroundResource(R.drawable.kg_background_date_clock_nomal_view);
        }
        TextView textView = view != null ? (TextView) view.findViewById(R.id.classic_text_style_select_view_label) : null;
        if (textView != null) {
            textView.setText(classicSelectorBean.getLabelResId());
            ViewUtil viewUtil4 = ViewUtil.INSTANCE;
            Context context4 = textView.getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            if (viewUtil4.getDarkMode(context4)) {
                textView.setTextColor(textView.getContext().getColor(R.color.kg_white_60));
            } else {
                textView.setTextColor(textView.getContext().getColor(R.color.kg_black_60));
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public StyleSelectorAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (i != ClassicSelectorBean.Companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL()) {
            View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.kg_layout_classic_text_style_editor_item_type, parent, false);
            Intrinsics.checkNotNull(viewInflate);
            return new StyleSelectorAdapter.ItemViewHolder(viewInflate);
        }
        View viewInflate2 = LayoutInflater.from(parent.getContext()).inflate(this.itemLayout, parent, false);
        FrameLayout frameLayout = (FrameLayout) viewInflate2.findViewById(R.id.classic_text_style_select_view_container);
        int dimensionPixelSize = frameLayout.getResources().getDimensionPixelSize(R.dimen.kg_classic_clock_font_padding_left_or_right);
        View viewCreateStyleView = createStyleView(parent);
        onStyleViewCreate(viewCreateStyleView);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        layoutParams.setMarginStart(dimensionPixelSize);
        layoutParams.setMarginEnd(dimensionPixelSize);
        Unit unit = Unit.INSTANCE;
        frameLayout.addView(viewCreateStyleView, layoutParams);
        Intrinsics.checkNotNull(viewInflate2);
        return new StyleSelectorAdapter.ItemViewHolder(viewInflate2);
    }

    @Override // com.miui.keyguard.editor.edit.view.StyleSelectorAdapter
    public void onItemSelect(View view, int i) {
        super.onItemSelect(view, i);
        OnClassicTextStyleSelectedListener onClassicTextStyleSelectedListener = this.listener;
        if (onClassicTextStyleSelectedListener != null) {
            onClassicTextStyleSelectedListener.onClassicTextStyleSelected((ClassicSelectorBean) getData().get(i), this.type);
        }
    }

    public final void onStyleViewCreate(View view) {
        view.setId(R.id.kg_classic_text_style_view);
    }

    public void setFontStyle(int i) {
        this.fontStyle = i;
    }

    public final void updateTypeGroup(View view, int i, boolean z, boolean z2) {
        TextView textView = view != null ? (TextView) view.findViewById(R.id.type_label) : null;
        View viewFindViewById = view != null ? view.findViewById(R.id.type_divider) : null;
        if (textView != null) {
            textView.setText(i);
        }
        if (textView != null) {
            textView.setVisibility(z ? 0 : 8);
        }
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(z2 ? 0 : 8);
        }
    }
}
