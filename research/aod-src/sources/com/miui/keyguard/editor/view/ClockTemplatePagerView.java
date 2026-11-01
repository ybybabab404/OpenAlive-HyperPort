package com.miui.keyguard.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.edit.base.EditCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: ClockTemplatePagerView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockTemplatePagerView extends ConstraintLayout {
    public static final Companion Companion = new Companion(null);
    public final Integer classicLine2;
    public ClockTemplatePagerAdapter clockTemplatePagerAdapter;
    public final Lazy clockVerticalRecyclerView$delegate;
    public final String initTemplateId;
    public final Integer style;
    public final Lazy templateRowList$delegate;

    /* JADX INFO: compiled from: ClockTemplatePagerView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockTemplatePagerView(final Context context, EditCallback editCallback, AttributeSet attributeSet, int i, String str, Integer num, Integer num2) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
        this.initTemplateId = str;
        this.style = num;
        this.classicLine2 = num2;
        this.clockVerticalRecyclerView$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.view.ClockTemplatePagerView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ClockTemplatePagerView.clockVerticalRecyclerView_delegate$lambda$0(this.f$0);
            }
        });
        this.templateRowList$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.view.ClockTemplatePagerView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TemplateDataUtil.getClockTemplate(context);
            }
        });
        ViewGroup.inflate(context, R.layout.kg_clock_template_pager_view, this);
        final ClockTemplateItem clockTemplateItemInitSelectedInfo = initSelectedInfo();
        this.clockTemplatePagerAdapter = new ClockTemplatePagerAdapter(editCallback, clockTemplateItemInitSelectedInfo);
        RecyclerView clockVerticalRecyclerView = getClockVerticalRecyclerView();
        clockVerticalRecyclerView.setAdapter(this.clockTemplatePagerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(1);
        clockVerticalRecyclerView.setLayoutManager(linearLayoutManager);
        this.clockTemplatePagerAdapter.submitList(getTemplateRowList());
        getClockVerticalRecyclerView().post(new Runnable() { // from class: com.miui.keyguard.editor.view.ClockTemplatePagerView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ClockTemplatePagerView._init_$lambda$5(this.f$0, clockTemplateItemInitSelectedInfo);
            }
        });
    }

    public /* synthetic */ ClockTemplatePagerView(Context context, EditCallback editCallback, AttributeSet attributeSet, int i, String str, Integer num, Integer num2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, editCallback, (i2 & 4) != 0 ? null : attributeSet, (i2 & 8) != 0 ? 0 : i, str, num, (i2 & 64) != 0 ? null : num2);
    }

    public static final void _init_$lambda$5(ClockTemplatePagerView clockTemplatePagerView, ClockTemplateItem clockTemplateItem) {
        androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = clockTemplatePagerView.getClockVerticalRecyclerView().getLayoutManager();
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(clockTemplateItem != null ? clockTemplateItem.getRowIndex() : 0, 0);
        }
    }

    public static final RecyclerView clockVerticalRecyclerView_delegate$lambda$0(ClockTemplatePagerView clockTemplatePagerView) {
        return (RecyclerView) clockTemplatePagerView.findViewById(R.id.clock_vertical_recycler_view);
    }

    private final RecyclerView getClockVerticalRecyclerView() {
        Object value = this.clockVerticalRecyclerView$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (RecyclerView) value;
    }

    private final List<ClockTemplateRow> getTemplateRowList() {
        return (List) this.templateRowList$delegate.getValue();
    }

    public final ClockTemplateItem initSelectedInfo() {
        TemplateConfig templateConfig;
        ClockInfo clockInfo;
        TemplateConfig templateConfig2;
        ClockInfo clockInfo2;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = getTemplateRowList().iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            int i3 = 0;
            for (ClockTemplateItem clockTemplateItem : ((ClockTemplateRow) it.next()).getTemplateList()) {
                int i4 = i3 + 1;
                clockTemplateItem.setIndex(i, i3);
                if (Intrinsics.areEqual(this.initTemplateId, clockTemplateItem.getTemplateConfig().getClockInfo().getTemplateId())) {
                    arrayList.add(clockTemplateItem);
                    clockTemplateItem.setSelected(true);
                }
                i3 = i4;
            }
            i = i2;
        }
        Integer num = this.style;
        if (Intrinsics.areEqual(this.initTemplateId, "classic_plus") && this.classicLine2 != null) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ClockTemplateItem clockTemplateItem2 = (ClockTemplateItem) it2.next();
                if (!Intrinsics.areEqual((clockTemplateItem2 == null || (templateConfig2 = clockTemplateItem2.getTemplateConfig()) == null || (clockInfo2 = templateConfig2.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo2.getClassicLine2()), this.classicLine2)) {
                    if (clockTemplateItem2 != null) {
                        clockTemplateItem2.setSelected(false);
                    }
                    it2.remove();
                }
            }
        } else if (arrayList.size() > 1) {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                ClockTemplateItem clockTemplateItem3 = (ClockTemplateItem) it3.next();
                if (!Intrinsics.areEqual((clockTemplateItem3 == null || (templateConfig = clockTemplateItem3.getTemplateConfig()) == null || (clockInfo = templateConfig.getClockInfo()) == null) ? null : Integer.valueOf(clockInfo.getStyle()), num)) {
                    if (clockTemplateItem3 != null) {
                        clockTemplateItem3.setSelected(false);
                    }
                    it3.remove();
                }
            }
        }
        return (ClockTemplateItem) CollectionsKt.getOrNull(arrayList, 0);
    }
}
