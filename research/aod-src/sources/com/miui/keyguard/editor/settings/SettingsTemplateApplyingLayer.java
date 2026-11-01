package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.settings.util.ViewUtilKt;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: SettingsTemplateApplyingLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SettingsTemplateApplyingLayer extends AbstractSettingsFakeTemplateLayer {
    public static final Companion Companion = new Companion(null);
    public ImageView deleteButton;
    public ConstraintLayout leftContainer;
    public View leftItemView;
    public ConstraintLayout rightContainer;
    public View rightItemView;
    public ConstraintLayout targetContainer;
    public View targetItemView;
    public final int version;

    /* JADX INFO: compiled from: SettingsTemplateApplyingLayer.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public SettingsTemplateApplyingLayer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SettingsTemplateApplyingLayer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.version = DeviceUtil.INSTANCE.getEditorVersion(context);
        LayoutInflater.from(context).inflate(R.layout.kg_settings_template_appyling_layer, this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.parent_container);
        frameLayout.getLayoutParams().width = getContainerWidth();
        frameLayout.getLayoutParams().height = getContainerHeight();
        this.targetContainer = (ConstraintLayout) findViewById(R.id.target_container);
        this.leftContainer = (ConstraintLayout) findViewById(R.id.left_container);
        this.rightContainer = (ConstraintLayout) findViewById(R.id.right_container);
    }

    public /* synthetic */ SettingsTemplateApplyingLayer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final void hideLoadingByAnim(Function0 callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.version < 49) {
            callback.invoke();
            return;
        }
        ImageView imageView = this.deleteButton;
        if (imageView == null) {
            callback.invoke();
        } else if (imageView != null) {
            ViewUtilKt.hideByAlphaAnim(imageView, callback);
        }
    }

    @Override // com.miui.keyguard.editor.settings.AbstractSettingsFakeTemplateLayer
    public void initLayout(int i, ViewPager2 viewPager2, boolean z) {
        Intrinsics.checkNotNullParameter(viewPager2, "viewPager2");
        super.initLayout(i, viewPager2, z);
        Log.i("Keyguard-Editor-SettingsTemplateApplyingLayer", "applying currentPosition=" + i);
        ISettingsViewHolder iSettingsViewHolderFindViewHolder = findViewHolder(getRecyclerView(), i);
        if (iSettingsViewHolderFindViewHolder == null) {
            Log.e("Keyguard-Editor-SettingsTemplateApplyingLayer", "initLayout targetViewHolder == null");
            return;
        }
        ISettingsViewHolder iSettingsViewHolderFindViewHolder2 = findViewHolder(getRecyclerView(), i - 1);
        this.leftItemView = iSettingsViewHolderFindViewHolder2 != null ? iSettingsViewHolderFindViewHolder2.getSelfItemView() : null;
        ISettingsViewHolder iSettingsViewHolderFindViewHolder3 = findViewHolder(getRecyclerView(), i + 1);
        this.rightItemView = iSettingsViewHolderFindViewHolder3 != null ? iSettingsViewHolderFindViewHolder3.getSelfItemView() : null;
        View selfItemView = iSettingsViewHolderFindViewHolder.getSelfItemView();
        this.targetItemView = selfItemView;
        if (selfItemView != null) {
            setTargetContainerLayout(this.targetContainer, selfItemView);
            fillContent(this.targetContainer, iSettingsViewHolderFindViewHolder);
            if (iSettingsViewHolderFindViewHolder instanceof TemplateHistoryViewHolder) {
                ImageView imageView = (ImageView) this.targetContainer.findViewById(R.id.template_delete_button);
                imageView.setVisibility(this.version >= 49 ? 0 : 8);
                this.deleteButton = imageView;
            }
        }
        View view = this.rightItemView;
        if (view != null) {
            setNeighboringContainerLayout(this.rightContainer, view);
            fillContent(this.rightContainer, iSettingsViewHolderFindViewHolder3);
        }
        View view2 = this.leftItemView;
        if (view2 != null) {
            setNeighboringContainerLayout(this.leftContainer, view2);
            fillContent(this.leftContainer, iSettingsViewHolderFindViewHolder2);
        }
    }
}
