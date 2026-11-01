package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.utils.ViewUtil;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.recyclerview.widget.RecyclerView;
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
/* JADX INFO: compiled from: BaseStyleSelectorView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class BaseStyleSelectorView<T> extends LinearLayout {
    public final Lazy itemList$delegate;
    public final TextView label;
    public boolean labelVisible;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public BaseStyleSelectorView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @JvmOverloads
    public BaseStyleSelectorView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.itemList$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.view.BaseStyleSelectorView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.createItemList();
            }
        });
        TextView textView = new TextView(context);
        textView.setVisibility(8);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        Resources resources = textView.getResources();
        int i = R.dimen.kg_editor_container_padding;
        layoutParams.leftMargin = resources.getDimensionPixelSize(i);
        layoutParams.rightMargin = textView.getResources().getDimensionPixelSize(i);
        textView.setLayoutParams(layoutParams);
        textView.setTextAppearance(R.style.KgEditorLabelTextStyle);
        textView.setLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextDirection(5);
        this.label = textView;
        this.labelVisible = true;
        setOrientation(1);
        addView(textView);
        addView(getItemList());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StyleSelectorView);
        this.labelVisible = typedArrayObtainStyledAttributes.getBoolean(R.styleable.StyleSelectorView_labelVisible, true);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.StyleSelectorView_labelMarginBottom, 0);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.StyleSelectorView_layoutManagerType, 0);
        int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.StyleSelectorView_layoutManagerOrientation, i2 == 0 ? 0 : 1);
        int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.StyleSelectorView_gridLayoutManagerSpanCount, 1);
        typedArrayObtainStyledAttributes.recycle();
        if (i2 == 0) {
            RecyclerView itemList = getItemList();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(i3);
            itemList.setLayoutManager(linearLayoutManager);
        } else {
            RecyclerView itemList2 = getItemList();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, integer);
            gridLayoutManager.setOrientation(i3);
            itemList2.setLayoutManager(gridLayoutManager);
        }
        if (!this.labelVisible) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            ViewUtil.INSTANCE.setViewMarginBottom(textView, dimensionPixelOffset);
        }
    }

    public /* synthetic */ BaseStyleSelectorView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public abstract RecyclerView createItemList();

    public final int getGridLayoutManagerSpanCount() {
        androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = getItemList().getLayoutManager();
        GridLayoutManager gridLayoutManager = layoutManager instanceof GridLayoutManager ? (GridLayoutManager) layoutManager : null;
        if (gridLayoutManager == null) {
            return 0;
        }
        return gridLayoutManager.getSpanCount();
    }

    @NotNull
    public final RecyclerView getItemList() {
        return (RecyclerView) this.itemList$delegate.getValue();
    }

    public final void scrollToPositionWithOffset(int i, int i2) {
        androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = getItemList().getLayoutManager();
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        if (linearLayoutManager != null) {
            linearLayoutManager.scrollToPositionWithOffset(i, i2);
        }
    }

    public final void setAdapter(@Nullable StyleSelectorAdapter<T> styleSelectorAdapter) {
        getItemList().setAdapter(styleSelectorAdapter);
    }

    public final int setGridLayoutManagerSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        if (!(getItemList().getLayoutManager() instanceof GridLayoutManager)) {
            return 0;
        }
        androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager = getItemList().getLayoutManager();
        Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager");
        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        return gridLayoutManager.getSpanCount();
    }

    public final void setItemViewCacheSize(int i) {
        getItemList().setItemViewCacheSize(i);
    }

    public final void setLabel(@NotNull String labelStr) {
        Intrinsics.checkNotNullParameter(labelStr, "labelStr");
        this.label.setVisibility(0);
        this.label.setText(labelStr);
    }

    public final void setLabelMarginBottom(int i) {
        if (this.labelVisible) {
            ViewUtil.INSTANCE.setViewMarginBottom(this.label, i);
        }
    }

    public final void setLabelViewGone() {
        this.label.setVisibility(8);
    }
}
