package com.miui.keyguard.editor.settings;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.settings.util.ViewUtilKt;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
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
/* JADX INFO: compiled from: SettingsTemplateDeleteLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class SettingsTemplateDeleteLayer extends AbstractSettingsFakeTemplateLayer {
    public static final Companion Companion = new Companion(null);
    public final Handler handler;
    public ConstraintLayout leftContainer;
    public View leftItemView;
    public ConstraintLayout leftNextContainer;
    public View leftNextItemView;
    public ConstraintLayout rightContainer;
    public View rightItemView;
    public ConstraintLayout rightNextContainer;
    public View rightNextItemView;
    public ConstraintLayout targetContainer;
    public float targetContainerScaleX;
    public float targetContainerScaleY;
    public float targetContainerX;
    public float targetContainerY;
    public View targetItemView;

    /* JADX INFO: compiled from: SettingsTemplateDeleteLayer.kt */
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
    public SettingsTemplateDeleteLayer(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public SettingsTemplateDeleteLayer(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.targetContainerScaleX = 1.0f;
        this.targetContainerScaleY = 1.0f;
        this.handler = new Handler(Looper.getMainLooper());
        LayoutInflater.from(context).inflate(R.layout.kg_settings_template_delete_layer, this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.parent_container);
        frameLayout.getLayoutParams().width = getContainerWidth();
        frameLayout.getLayoutParams().height = getContainerHeight();
        this.targetContainer = (ConstraintLayout) findViewById(R.id.target_container);
        this.leftContainer = (ConstraintLayout) findViewById(R.id.left_container);
        this.leftNextContainer = (ConstraintLayout) findViewById(R.id.left_next_container);
        this.rightContainer = (ConstraintLayout) findViewById(R.id.right_container);
        this.rightNextContainer = (ConstraintLayout) findViewById(R.id.right_next_container);
    }

    public /* synthetic */ SettingsTemplateDeleteLayer(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static /* synthetic */ AnimConfig createCommonAnimConfig$default(SettingsTemplateDeleteLayer settingsTemplateDeleteLayer, TransitionListener transitionListener, float f, float f2, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 0.95f;
        }
        if ((i & 4) != 0) {
            f2 = 0.35f;
        }
        return settingsTemplateDeleteLayer.createCommonAnimConfig(transitionListener, f, f2);
    }

    public static final void startDeleteTransformAnim$lambda$12(SettingsTemplateDeleteLayer settingsTemplateDeleteLayer, Function0 function0) {
        View view = settingsTemplateDeleteLayer.rightItemView;
        if (view == null) {
            if (settingsTemplateDeleteLayer.leftItemView != null) {
                settingsTemplateDeleteLayer.startNeighbouringAnim(settingsTemplateDeleteLayer.leftContainer, function0);
            }
            if (settingsTemplateDeleteLayer.leftNextItemView != null) {
                settingsTemplateDeleteLayer.startNeighbouringNextAnim(settingsTemplateDeleteLayer.leftContainer, settingsTemplateDeleteLayer.leftNextContainer);
                return;
            }
            return;
        }
        if (view != null) {
            settingsTemplateDeleteLayer.startNeighbouringAnim(settingsTemplateDeleteLayer.rightContainer, function0);
        }
        if (settingsTemplateDeleteLayer.rightNextItemView != null) {
            settingsTemplateDeleteLayer.startNeighbouringNextAnim(settingsTemplateDeleteLayer.rightContainer, settingsTemplateDeleteLayer.rightNextContainer);
        }
    }

    public static final Unit startDeleteTransformAnim$lambda$7(Function0 function0, ISettingsViewHolder iSettingsViewHolder, ISettingsViewHolder iSettingsViewHolder2, ISettingsViewHolder iSettingsViewHolder3) {
        View selfItemView;
        View selfItemView2;
        View selfItemView3;
        function0.invoke();
        if (iSettingsViewHolder != null && (selfItemView3 = iSettingsViewHolder.getSelfItemView()) != null) {
            ViewUtilKt.show(selfItemView3);
        }
        if (iSettingsViewHolder2 != null && (selfItemView2 = iSettingsViewHolder2.getSelfItemView()) != null) {
            ViewUtilKt.show(selfItemView2);
        }
        if (iSettingsViewHolder3 != null && (selfItemView = iSettingsViewHolder3.getSelfItemView()) != null) {
            ViewUtilKt.show(selfItemView);
        }
        return Unit.INSTANCE;
    }

    public final AnimConfig createCommonAnimConfig(TransitionListener transitionListener, float f, float f2) {
        AnimConfig animConfig = new AnimConfig();
        animConfig.setEase(EaseManager.getStyle(-2, f, f2));
        if (transitionListener != null) {
            animConfig.addListeners(transitionListener);
        }
        return animConfig;
    }

    @Override // com.miui.keyguard.editor.settings.AbstractSettingsFakeTemplateLayer
    public void initLayout(int i, ViewPager2 viewPager2, boolean z) {
        Intrinsics.checkNotNullParameter(viewPager2, "viewPager2");
        super.initLayout(i, viewPager2, z);
        ISettingsViewHolder iSettingsViewHolderFindViewHolder = findViewHolder(getRecyclerView(), i);
        if (iSettingsViewHolderFindViewHolder == null) {
            Log.e("Keyguard-Editor-SettingsTemplateDeleteLayer", "initLayout targetViewHolder == null");
            return;
        }
        this.targetItemView = iSettingsViewHolderFindViewHolder.getSelfItemView();
        ISettingsViewHolder iSettingsViewHolderFindViewHolder2 = findViewHolder(getRecyclerView(), i - 1);
        this.leftItemView = iSettingsViewHolderFindViewHolder2 != null ? iSettingsViewHolderFindViewHolder2.getSelfItemView() : null;
        ISettingsViewHolder iSettingsViewHolderFindViewHolder3 = findViewHolder(getRecyclerView(), i - 2);
        this.leftNextItemView = iSettingsViewHolderFindViewHolder3 != null ? iSettingsViewHolderFindViewHolder3.getSelfItemView() : null;
        ISettingsViewHolder iSettingsViewHolderFindViewHolder4 = findViewHolder(getRecyclerView(), i + 1);
        this.rightItemView = iSettingsViewHolderFindViewHolder4 != null ? iSettingsViewHolderFindViewHolder4.getSelfItemView() : null;
        ISettingsViewHolder iSettingsViewHolderFindViewHolder5 = findViewHolder(getRecyclerView(), i + 2);
        this.rightNextItemView = iSettingsViewHolderFindViewHolder5 != null ? iSettingsViewHolderFindViewHolder5.getSelfItemView() : null;
        View view = this.targetItemView;
        if (view != null) {
            setTargetContainerLayout(this.targetContainer, view);
            fillContent(this.targetContainer, iSettingsViewHolderFindViewHolder);
            ((ImageView) this.targetContainer.findViewById(R.id.template_delete_button)).setVisibility(0);
        }
        View view2 = this.rightItemView;
        if (view2 != null) {
            setNeighboringContainerLayout(this.rightContainer, view2);
            fillContent(this.rightContainer, iSettingsViewHolderFindViewHolder4);
        }
        View view3 = this.rightNextItemView;
        if (view3 != null) {
            setNeighboringContainerLayout(this.rightNextContainer, view3);
            fillContent(this.rightNextContainer, iSettingsViewHolderFindViewHolder5);
        }
        View view4 = this.leftItemView;
        if (view4 != null) {
            setNeighboringContainerLayout(this.leftContainer, view4);
            fillContent(this.leftContainer, iSettingsViewHolderFindViewHolder2);
        }
        View view5 = this.leftNextItemView;
        if (view5 != null) {
            setNeighboringContainerLayout(this.leftNextContainer, view5);
            fillContent(this.leftNextContainer, iSettingsViewHolderFindViewHolder3);
        }
        View view6 = this.targetItemView;
        if (view6 != null) {
            view6.setAlpha(0.0f);
        }
    }

    public final void onAnimComplete() {
        View view = this.targetItemView;
        if (view != null) {
            view.setAlpha(1.0f);
        }
    }

    public final void startDeleteTransformAnim(int i, final Function0 callback) {
        View selfItemView;
        View selfItemView2;
        Intrinsics.checkNotNullParameter(callback, "callback");
        final ISettingsViewHolder iSettingsViewHolderFindViewHolder = findViewHolder(getRecyclerView(), i);
        final ISettingsViewHolder iSettingsViewHolderFindViewHolder2 = findViewHolder(getRecyclerView(), i - 1);
        final ISettingsViewHolder iSettingsViewHolderFindViewHolder3 = findViewHolder(getRecyclerView(), i + 1);
        if (iSettingsViewHolderFindViewHolder != null && (selfItemView2 = iSettingsViewHolderFindViewHolder.getSelfItemView()) != null) {
            ViewUtilKt.fakeHide(selfItemView2);
        }
        if (iSettingsViewHolderFindViewHolder2 != null && !(iSettingsViewHolderFindViewHolder2 instanceof SettingsNegativeViewHolder)) {
            ViewUtilKt.fakeHide(iSettingsViewHolderFindViewHolder2.getSelfItemView());
        }
        if (iSettingsViewHolderFindViewHolder3 != null && (selfItemView = iSettingsViewHolderFindViewHolder3.getSelfItemView()) != null) {
            ViewUtilKt.fakeHide(selfItemView);
        }
        final Function0 function0 = new Function0() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateDeleteLayer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SettingsTemplateDeleteLayer.startDeleteTransformAnim$lambda$7(callback, iSettingsViewHolderFindViewHolder, iSettingsViewHolderFindViewHolder2, iSettingsViewHolderFindViewHolder3);
            }
        };
        startTargetAnim();
        this.handler.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateDeleteLayer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SettingsTemplateDeleteLayer.startDeleteTransformAnim$lambda$12(this.f$0, function0);
            }
        }, 100L);
    }

    public final void startNeighbouringAnim(ViewGroup viewGroup, final Function0 function0) {
        Log.i("Keyguard-Editor-SettingsTemplateDeleteLayer", "startNeighboringAnim");
        Folme.useAt(viewGroup).state().add(ViewProperty.X, this.targetContainerX).add(ViewProperty.Y, this.targetContainerY).to("neighbor.translate", createCommonAnimConfig$default(this, new TransitionListener() { // from class: com.miui.keyguard.editor.settings.SettingsTemplateDeleteLayer.startNeighbouringAnim.1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                SettingsTemplateDeleteLayer.this.onAnimComplete();
                function0.invoke();
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                Log.i("Keyguard-Editor-SettingsTemplateDeleteLayer", "startLeftAnim,left.translate");
                SettingsTemplateDeleteLayer.this.onAnimComplete();
                function0.invoke();
            }
        }, 0.0f, 0.0f, 6, null));
        Folme.useAt(viewGroup).state().add(ViewProperty.SCALE_X, this.targetContainerScaleX).add(ViewProperty.SCALE_Y, this.targetContainerScaleY).to("neighbor.scale", createCommonAnimConfig(null, 0.95f, 0.4f));
    }

    public final void startNeighbouringNextAnim(ViewGroup viewGroup, ViewGroup viewGroup2) {
        Log.i("Keyguard-Editor-SettingsTemplateDeleteLayer", "startNeighbouringNextAnim");
        float x = viewGroup.getX();
        float y = viewGroup.getY();
        float scaleX = viewGroup.getScaleX();
        float scaleY = viewGroup.getScaleY();
        Folme.useAt(viewGroup2).state().add(ViewProperty.X, x).add(ViewProperty.Y, y).to("NeighbouringNext.translate", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
        Folme.useAt(viewGroup2).state().add(ViewProperty.SCALE_X, scaleX).add(ViewProperty.SCALE_Y, scaleY).to("NeighbouringNext.scale", createCommonAnimConfig(null, 0.95f, 0.4f));
    }

    public final void startTargetAnim() {
        Log.i("Keyguard-Editor-SettingsTemplateDeleteLayer", "startTargetAnim");
        Folme.useAt(this.targetContainer).state().add((FloatProperty) ViewProperty.SCALE_X, 0.2f).add((FloatProperty) ViewProperty.SCALE_Y, 0.2f).add((FloatProperty) ViewProperty.ALPHA, 0.0f).to("target", createCommonAnimConfig$default(this, null, 0.0f, 0.0f, 6, null));
    }
}
