package com.miui.keyguard.editor.edit.clock;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import com.miui.clock.module.ClockBean;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.DataUtils;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.AbstractPopupEditor;
import com.miui.keyguard.editor.edit.base.EditCallback;
import com.miui.keyguard.editor.edit.classicclock.AbstractClassicTemplateView;
import com.miui.keyguard.editor.edit.style.FontStyleEditCallback;
import com.miui.keyguard.editor.edit.style.RealtimeStyleEditor;
import com.miui.keyguard.editor.edit.view.ClassicTextStyleSelectorAdapter;
import com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView;
import com.miui.keyguard.editor.edit.view.OnClassicTextStyleSelectedListener;
import com.miui.keyguard.editor.edit.view.StyleSelectorAdapter;
import com.miui.keyguard.editor.edit.view.StyleSelectorView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.recyclerview.widget.RecyclerView;

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
/* JADX INFO: compiled from: ClassicTextStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ClassicTextStyleEditor extends AbstractPopupEditor implements OnClassicTextStyleSelectedListener, RealtimeStyleEditor {
    public final StyleSelectorAdapter adapter;
    public StyleSelectorAdapter adapter1;
    public StyleSelectorAdapter adapter2;
    public final int color;
    public EditorDialogNoTitleBarView container;
    public final ClockBean currentClockBean;
    public boolean isClassic;
    public boolean isHealthAppInstalled;
    public final boolean isTextArea;
    public boolean isWeatherAppInstalled;
    public StyleSelectorView selectorView;
    public StyleSelectorView selectorView1;
    public StyleSelectorView selectorView2;
    public boolean textAreaIsEmpty;

    /* JADX INFO: compiled from: ClassicTextStyleEditor.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

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
            int[] iArr2 = new int[ClassicDataType.values().length];
            try {
                iArr2[ClassicDataType.WEATHER.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ClassicDataType.HEALTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ClassicDataType.TEXT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassicTextStyleEditor(AbstractClassicTemplateView classicTemplate, EditCallback editCallback, ClockViewType type, ClockBean currentClockBean, int i, boolean z, boolean z2, boolean z3, FontStyleEditCallback fontStyleEditCallback) {
        super(classicTemplate, editCallback, fontStyleEditCallback);
        Intrinsics.checkNotNullParameter(classicTemplate, "classicTemplate");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(currentClockBean, "currentClockBean");
        this.currentClockBean = currentClockBean;
        this.color = i;
        this.isTextArea = z;
        this.textAreaIsEmpty = z2;
        this.isClassic = z3;
        this.isWeatherAppInstalled = true;
        this.isHealthAppInstalled = true;
        View viewInflate = LayoutInflater.from(getContext()).inflate(getEditorLayout(), (ViewGroup) getTemplateView(), false);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView");
        EditorDialogNoTitleBarView editorDialogNoTitleBarView = (EditorDialogNoTitleBarView) viewInflate;
        this.container = editorDialogNoTitleBarView;
        if (editorDialogNoTitleBarView != null) {
            String string = getContext().getString(R.string.kg_title_content);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            editorDialogNoTitleBarView.setTitle(string);
        }
        this.isHealthAppInstalled = DataUtils.isHealthAppInstalled(getContext());
        this.isWeatherAppInstalled = DataUtils.isWeatherAppInstalled(getContext());
        EditorDialogNoTitleBarView editorDialogNoTitleBarView2 = this.container;
        this.selectorView = editorDialogNoTitleBarView2 != null ? (StyleSelectorView) editorDialogNoTitleBarView2.findViewById(R.id.text_area_editor_selector_view) : null;
        if (z) {
            StyleSelectorAdapter styleSelectorAdapterCreateAdapter = createAdapter(type, currentClockBean.getClassicLine1());
            this.adapter = styleSelectorAdapterCreateAdapter;
            StyleSelectorView styleSelectorView = this.selectorView;
            if (styleSelectorView != null) {
                RecyclerView itemList = styleSelectorView.getItemList();
                Resources resources = styleSelectorView.getResources();
                int i2 = R.dimen.kg_classic_clock_font_margin_left;
                itemList.setPadding(resources.getDimensionPixelSize(i2), 0, styleSelectorView.getResources().getDimensionPixelSize(i2), styleSelectorView.getResources().getDimensionPixelSize(i2));
                styleSelectorView.getItemList().setClipToPadding(false);
                styleSelectorView.setGridLayoutManagerSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.miui.keyguard.editor.edit.clock.ClassicTextStyleEditor$1$1
                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public int getSpanSize(int i3) {
                        if (((ClassicSelectorBean) this.this$0.adapter.getData().get(i3)).getBeanType() == ClassicSelectorBean.Companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL()) {
                            return 1;
                        }
                        StyleSelectorView styleSelectorView2 = this.this$0.selectorView;
                        Integer numValueOf = styleSelectorView2 != null ? Integer.valueOf(styleSelectorView2.getGridLayoutManagerSpanCount()) : null;
                        Intrinsics.checkNotNull(numValueOf);
                        return numValueOf.intValue();
                    }
                });
                styleSelectorView.setAdapter(styleSelectorAdapterCreateAdapter);
                styleSelectorView.setItemViewCacheSize(styleSelectorAdapterCreateAdapter.getData().size());
                styleSelectorView.scrollToPositionWithOffset(styleSelectorAdapterCreateAdapter.getSelectedIndex(), styleSelectorView.getContext().getResources().getDimensionPixelOffset(getItemTopAndBottomOffsetResId()));
                return;
            }
            return;
        }
        StyleSelectorAdapter styleSelectorAdapterCreateAdapter2 = createAdapter(ClockViewType.TIME_AREA, currentClockBean.getClassicLine2());
        this.adapter = styleSelectorAdapterCreateAdapter2;
        StyleSelectorView styleSelectorView2 = this.selectorView;
        if (styleSelectorView2 != null) {
            styleSelectorView2.setGridLayoutManagerSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.miui.keyguard.editor.edit.clock.ClassicTextStyleEditor$2$1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i3) {
                    if (((ClassicSelectorBean) this.this$0.adapter.getData().get(i3)).getBeanType() == ClassicSelectorBean.Companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL()) {
                        return 1;
                    }
                    StyleSelectorView styleSelectorView3 = this.this$0.selectorView;
                    Integer numValueOf = styleSelectorView3 != null ? Integer.valueOf(styleSelectorView3.getGridLayoutManagerSpanCount()) : null;
                    Intrinsics.checkNotNull(numValueOf);
                    return numValueOf.intValue();
                }
            });
            styleSelectorView2.setAdapter(styleSelectorAdapterCreateAdapter2);
            styleSelectorView2.scrollToPositionWithOffset(styleSelectorAdapterCreateAdapter2.getSelectedIndex(), styleSelectorView2.getContext().getResources().getDimensionPixelOffset(getItemTopAndBottomOffsetResId()));
        }
        EditorDialogNoTitleBarView editorDialogNoTitleBarView3 = this.container;
        this.selectorView1 = editorDialogNoTitleBarView3 != null ? (StyleSelectorView) editorDialogNoTitleBarView3.findViewById(R.id.text_area_editor_selector_view1) : null;
        EditorDialogNoTitleBarView editorDialogNoTitleBarView4 = this.container;
        this.selectorView2 = editorDialogNoTitleBarView4 != null ? (StyleSelectorView) editorDialogNoTitleBarView4.findViewById(R.id.text_area_editor_selector_view2) : null;
        this.adapter1 = createAdapter(ClockViewType.CONTENT_AREA1, currentClockBean.getClassicLine3());
        this.adapter2 = createAdapter(ClockViewType.CONTENT_AREA2, currentClockBean.getClassicLine4());
        StyleSelectorView styleSelectorView3 = this.selectorView1;
        if (styleSelectorView3 != null) {
            styleSelectorView3.setGridLayoutManagerSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.miui.keyguard.editor.edit.clock.ClassicTextStyleEditor.3
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i3) {
                    List data;
                    ClassicSelectorBean classicSelectorBean;
                    StyleSelectorAdapter styleSelectorAdapter = ClassicTextStyleEditor.this.adapter1;
                    Integer numValueOf = (styleSelectorAdapter == null || (data = styleSelectorAdapter.getData()) == null || (classicSelectorBean = (ClassicSelectorBean) data.get(i3)) == null) ? null : Integer.valueOf(classicSelectorBean.getBeanType());
                    int classic_selector_bean_type_normal = ClassicSelectorBean.Companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL();
                    if (numValueOf != null && numValueOf.intValue() == classic_selector_bean_type_normal) {
                        return 1;
                    }
                    StyleSelectorView styleSelectorView4 = ClassicTextStyleEditor.this.selectorView1;
                    Intrinsics.checkNotNull(styleSelectorView4);
                    return styleSelectorView4.getGridLayoutManagerSpanCount();
                }
            });
        }
        StyleSelectorView styleSelectorView4 = this.selectorView1;
        if (styleSelectorView4 != null) {
            styleSelectorView4.setAdapter(this.adapter1);
        }
        StyleSelectorAdapter styleSelectorAdapter = this.adapter1;
        if (styleSelectorAdapter != null) {
            int selectedIndex = styleSelectorAdapter.getSelectedIndex();
            StyleSelectorView styleSelectorView5 = this.selectorView1;
            if (styleSelectorView5 != null) {
                styleSelectorView5.scrollToPositionWithOffset(selectedIndex, getContext().getResources().getDimensionPixelOffset(getItemTopAndBottomOffsetResId()));
            }
        }
        StyleSelectorView styleSelectorView6 = this.selectorView2;
        if (styleSelectorView6 != null) {
            styleSelectorView6.setGridLayoutManagerSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.miui.keyguard.editor.edit.clock.ClassicTextStyleEditor.5
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i3) {
                    StyleSelectorAdapter styleSelectorAdapter2 = ClassicTextStyleEditor.this.adapter2;
                    Intrinsics.checkNotNull(styleSelectorAdapter2);
                    if (((ClassicSelectorBean) styleSelectorAdapter2.getData().get(i3)).getBeanType() == ClassicSelectorBean.Companion.getCLASSIC_SELECTOR_BEAN_TYPE_NORMAL()) {
                        return 1;
                    }
                    StyleSelectorView styleSelectorView7 = ClassicTextStyleEditor.this.selectorView2;
                    Intrinsics.checkNotNull(styleSelectorView7);
                    return styleSelectorView7.getGridLayoutManagerSpanCount();
                }
            });
        }
        StyleSelectorView styleSelectorView7 = this.selectorView2;
        if (styleSelectorView7 != null) {
            styleSelectorView7.setAdapter(this.adapter2);
        }
        StyleSelectorAdapter styleSelectorAdapter2 = this.adapter2;
        if (styleSelectorAdapter2 != null) {
            int selectedIndex2 = styleSelectorAdapter2.getSelectedIndex();
            StyleSelectorView styleSelectorView8 = this.selectorView2;
            if (styleSelectorView8 != null) {
                styleSelectorView8.scrollToPositionWithOffset(selectedIndex2, getContext().getResources().getDimensionPixelOffset(getItemTopAndBottomOffsetResId()));
            }
        }
    }

    private final int getItemTopAndBottomOffsetResId() {
        return R.dimen.kg_classic_text_editor_top_and_bottom_offset;
    }

    public final StyleSelectorAdapter createAdapter(ClockViewType clockViewType, int i) {
        List listEmptyList;
        boolean zIsHealthAppInstalled = DataUtils.isHealthAppInstalled(getContext());
        boolean zIsWeatherAppInstalled = DataUtils.isWeatherAppInstalled(getContext());
        boolean zIsInternationalBuild = DeviceUtil.INSTANCE.isInternationalBuild();
        int i2 = WhenMappings.$EnumSwitchMapping$0[clockViewType.ordinal()];
        if (i2 == 1) {
            List list = ClassicTextStyleEditorKt.TEXT_AREA_DATA;
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (((ClassicSelectorBean) obj).getStyle() == 0 ? this.isClassic : true) {
                    arrayList.add(obj);
                }
            }
            listEmptyList = arrayList;
        } else if (i2 != 2) {
            listEmptyList = (i2 == 3 || i2 == 4) ? ClassicTextStyleEditorKt.CONTENT_AREA_DATA : CollectionsKt.emptyList();
        } else {
            listEmptyList = ClassicTextStyleEditorKt.CLOCK_AREA_DATA;
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : listEmptyList) {
            ClassicSelectorBean classicSelectorBean = (ClassicSelectorBean) obj2;
            int i3 = WhenMappings.$EnumSwitchMapping$1[classicSelectorBean.getDateType().ordinal()];
            boolean z = false;
            if (i3 == 1 ? !(!zIsWeatherAppInstalled || ((!zIsInternationalBuild || !classicSelectorBean.supportGlobal()) && zIsInternationalBuild)) : !(i3 == 2 ? !zIsHealthAppInstalled || ((!zIsInternationalBuild || !classicSelectorBean.supportGlobal()) && zIsInternationalBuild) : i3 == 3 && !DeviceUtil.INSTANCE.isChineseLanguage() && !classicSelectorBean.supportGlobal())) {
                z = true;
            }
            if (z) {
                arrayList2.add(obj2);
            }
        }
        return new ClassicTextStyleSelectorAdapter(getContext(), getItemLayout(), clockViewType, i, arrayList2, this.color, this.currentClockBean.getStyle(), false, this, this.textAreaIsEmpty);
    }

    public final EditorDialogNoTitleBarView getContainer() {
        return this.container;
    }

    public final int getEditorLayout() {
        return this.isTextArea ? R.layout.kg_layout_classic_text_style_bottom_editor : R.layout.kg_layout_classic_text_style_editor;
    }

    public final int getItemLayout() {
        return this.isTextArea ? R.layout.kg_layout_classic_text_style_editor_item_view_single_row : R.layout.kg_layout_classic_text_style_editor_item_view;
    }

    @Override // com.miui.keyguard.editor.edit.view.OnClassicTextStyleSelectedListener
    public void onClassicTextStyleSelected(ClassicSelectorBean bean, ClockViewType type) {
        Intrinsics.checkNotNullParameter(bean, "bean");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i == 1) {
            getEditCallback().onEdited(100, bean);
            return;
        }
        if (i == 2) {
            getEditCallback().onEdited(101, bean);
            return;
        }
        if (i == 3) {
            getEditCallback().onEdited(102, bean);
            return;
        }
        if (i == 4) {
            getEditCallback().onEdited(103, bean);
            return;
        }
        Log.w("ClassicTextStyleEditor", "onClassicTextStyleSelected: unsupported ClockViewType: " + type.ordinal());
    }

    @Override // com.miui.keyguard.editor.edit.base.AbstractPopupEditor
    public void onTemplateRemoved() {
        super.onTemplateRemoved();
        StyleSelectorView styleSelectorView = this.selectorView;
        if (styleSelectorView != null) {
            styleSelectorView.setAdapter(null);
        }
        StyleSelectorView styleSelectorView2 = this.selectorView1;
        if (styleSelectorView2 != null) {
            styleSelectorView2.setAdapter(null);
        }
        StyleSelectorView styleSelectorView3 = this.selectorView2;
        if (styleSelectorView3 != null) {
            styleSelectorView3.setAdapter(null);
        }
        StyleSelectorView styleSelectorView4 = this.selectorView;
        if (styleSelectorView4 != null) {
            styleSelectorView4.setGridLayoutManagerSpanSizeLookup(null);
        }
        StyleSelectorView styleSelectorView5 = this.selectorView1;
        if (styleSelectorView5 != null) {
            styleSelectorView5.setGridLayoutManagerSpanSizeLookup(null);
        }
        StyleSelectorView styleSelectorView6 = this.selectorView2;
        if (styleSelectorView6 != null) {
            styleSelectorView6.setGridLayoutManagerSpanSizeLookup(null);
        }
        EditorDialogNoTitleBarView editorDialogNoTitleBarView = this.container;
        if (editorDialogNoTitleBarView != null) {
            editorDialogNoTitleBarView.onDestroy();
        }
    }

    @Override // com.miui.keyguard.editor.edit.style.RealtimeStyleEditor
    public void onTimeUpdate() {
        StyleSelectorAdapter styleSelectorAdapter = this.adapter;
        if (styleSelectorAdapter != null) {
            styleSelectorAdapter.notifyDataSetChanged();
        }
        StyleSelectorAdapter styleSelectorAdapter2 = this.adapter1;
        if (styleSelectorAdapter2 != null) {
            styleSelectorAdapter2.notifyDataSetChanged();
        }
        StyleSelectorAdapter styleSelectorAdapter3 = this.adapter2;
        if (styleSelectorAdapter3 != null) {
            styleSelectorAdapter3.notifyDataSetChanged();
        }
    }

    public final void updateAdapter(Object obj) {
        ClassicTextStyleSelectorAdapter classicTextStyleSelectorAdapter = obj instanceof ClassicTextStyleSelectorAdapter ? (ClassicTextStyleSelectorAdapter) obj : null;
        if (classicTextStyleSelectorAdapter != null) {
            classicTextStyleSelectorAdapter.setFontStyle(this.currentClockBean.getStyle());
            classicTextStyleSelectorAdapter.notifyDataSetChanged();
        }
    }

    public void updateClockStyle() {
        updateAdapter(this.adapter);
        updateAdapter(this.adapter1);
        updateAdapter(this.adapter2);
    }
}
