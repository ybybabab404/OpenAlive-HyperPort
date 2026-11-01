package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TransitionKt;
import com.miui.keyguard.editor.data.util.DataUtilKt;
import com.miui.keyguard.editor.utils.ClickUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.KgFrame;
import com.miui.keyguard.editor.view.KgFrameParam;
import com.miui.keyguard.editor.view.OnSelectListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

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
/* JADX INFO: compiled from: TemplateEditFrameController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class TemplateEditFrameController implements View.OnClickListener {
    public static final Companion Companion = new Companion(null);
    public boolean canShowAll;
    public final View.OnClickListener clickListener;
    public FrameLayout foreLayer;
    public final Map items;
    public int lastColorMode;
    public final OnSelectListener selectListener;

    /* JADX INFO: compiled from: TemplateEditFrameController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
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
        public final KgFrame createFrame(Context context, KgFrameParam param) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(param, "param");
            KgFrame kgFrame = new KgFrame(context, null, 2, 0 == true ? 1 : 0);
            kgFrame.setId(param.getId());
            kgFrame.setFrameParam(param);
            kgFrame.setVisibility(8);
            kgFrame.setFocusable(true);
            kgFrame.setFocusableInTouchMode(true);
            kgFrame.setImportantForAccessibility(1);
            kgFrame.setBorderWidth(DeviceConfig.getDimen(R.dimen.kg_clock_frame_border_width, context));
            kgFrame.setBorderRadius(DeviceConfig.getDimen(R.dimen.kg_area_frame_border_radius, context));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(param.getRect().width(), param.getRect().height());
            layoutParams.setMarginStart(param.getRect().left);
            layoutParams.topMargin = param.getRect().top;
            kgFrame.setLayoutParams(layoutParams);
            if (param.getGravity() != 0) {
                ViewUtil.INSTANCE.setGravity(kgFrame, param.getGravity());
            }
            if (param.getLayoutDirection() != -1) {
                kgFrame.setLayoutDirection(param.getLayoutDirection());
            }
            return kgFrame;
        }
    }

    public TemplateEditFrameController(View.OnClickListener clickListener, OnSelectListener selectListener) {
        Intrinsics.checkNotNullParameter(clickListener, "clickListener");
        Intrinsics.checkNotNullParameter(selectListener, "selectListener");
        this.clickListener = clickListener;
        this.selectListener = selectListener;
        this.lastColorMode = Integer.MIN_VALUE;
        this.items = new LinkedHashMap();
        this.canShowAll = true;
    }

    public /* synthetic */ TemplateEditFrameController(View.OnClickListener onClickListener, OnSelectListener onSelectListener, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(onClickListener, (i & 2) != 0 ? new KgFrame.DefaultSelectListener() : onSelectListener);
    }

    public static /* synthetic */ void hideAll$default(TemplateEditFrameController templateEditFrameController, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        templateEditFrameController.hideAll(z);
    }

    public final void addAll(List params) {
        FrameLayout frameLayout;
        Intrinsics.checkNotNullParameter(params, "params");
        if (this.foreLayer == null) {
            DataUtilKt.throwIfDebug("have you init foreLayer");
            return;
        }
        Iterator it = params.iterator();
        while (it.hasNext()) {
            KgFrameParam kgFrameParam = (KgFrameParam) it.next();
            Companion companion = Companion;
            FrameLayout frameLayout2 = this.foreLayer;
            Intrinsics.checkNotNull(frameLayout2);
            Context context = frameLayout2.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            KgFrame kgFrameCreateFrame = companion.createFrame(context, kgFrameParam);
            kgFrameCreateFrame.setVisibility(4);
            kgFrameCreateFrame.setContentDescription(kgFrameCreateFrame.getResources().getString(kgFrameParam.getContentDescriptionResId()));
            kgFrameCreateFrame.setOnClickListener(this);
            kgFrameCreateFrame.setOnSelectListener(this.selectListener);
            this.items.put(Integer.valueOf(kgFrameParam.getId()), kgFrameCreateFrame);
            if (kgFrameCreateFrame.getParent() == null && (frameLayout = this.foreLayer) != null) {
                frameLayout.addView(kgFrameCreateFrame);
            }
        }
        FrameLayout frameLayout3 = this.foreLayer;
        if (frameLayout3 != null) {
            frameLayout3.requestLayout();
        }
    }

    public final boolean canShowAll(boolean z) {
        this.canShowAll = z;
        return z;
    }

    public final void clearSelectState() {
        for (Map.Entry entry : this.items.entrySet()) {
            if (((KgFrame) entry.getValue()).isSelected()) {
                ((KgFrame) entry.getValue()).setSelected(false);
            }
            if (((View) entry.getValue()).getVisibility() != 0) {
                ((View) entry.getValue()).setVisibility(0);
            }
        }
    }

    public final void exit() {
        hideAll(true);
        this.items.clear();
    }

    public final boolean getCanShowAll$keyguardeditor_release() {
        return this.canShowAll;
    }

    public final KgFrame getItem(int i) {
        return (KgFrame) this.items.get(Integer.valueOf(i));
    }

    public final Map getItems() {
        return this.items;
    }

    public final void hideAll(boolean z) {
        IStateStyle iStateStyleState;
        IStateStyle to;
        if (!z || this.items.isEmpty()) {
            Iterator it = this.items.entrySet().iterator();
            while (it.hasNext()) {
                ((KgFrame) ((Map.Entry) it.next()).getValue()).setVisibility(4);
            }
            return;
        }
        AnimState animState = new AnimState("hide_start");
        ViewProperty viewProperty = ViewProperty.ALPHA;
        AnimState animStateAdd = animState.add(viewProperty, 0.3f, new long[0]);
        AnimState animStateAdd2 = new AnimState("hide_end").add(viewProperty, 0.0f, new long[0]);
        AnimConfig animConfig = TransitionKt.getAnimConfig(0.95f, 0.35f, new TransitionListener() { // from class: com.miui.keyguard.editor.edit.base.TemplateEditFrameController$hideAll$config$1
            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                for (Map.Entry entry : this.this$0.getItems().entrySet()) {
                    ((KgFrame) entry.getValue()).setVisibility(4);
                    ((KgFrame) entry.getValue()).setAlpha(0.3f);
                }
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                for (Map.Entry entry : this.this$0.getItems().entrySet()) {
                    ((KgFrame) entry.getValue()).setVisibility(4);
                    ((KgFrame) entry.getValue()).setAlpha(0.3f);
                }
            }
        });
        KgFrame[] kgFrameArr = (KgFrame[]) this.items.values().toArray(new KgFrame[0]);
        IFolme iFolmeUseAt = Folme.useAt((View[]) Arrays.copyOf(kgFrameArr, kgFrameArr.length));
        if (iFolmeUseAt == null || (iStateStyleState = iFolmeUseAt.state()) == null || (to = iStateStyleState.setTo(animStateAdd)) == null) {
            return;
        }
        to.to(animStateAdd2, animConfig);
    }

    public final void initForeLayer(FrameLayout f) {
        Intrinsics.checkNotNullParameter(f, "f");
        this.foreLayer = f;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (ClickUtil.INSTANCE.isFastDoubleClick()) {
            return;
        }
        if (this.items.keySet().contains(Integer.valueOf(v.getId()))) {
            v.setSelected(true);
            synchronized (this) {
                try {
                    for (Map.Entry entry : this.items.entrySet()) {
                        if (!Intrinsics.areEqual(entry.getValue(), v)) {
                            ((View) entry.getValue()).setVisibility(4);
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        this.clickListener.onClick(v);
    }

    public final void setColorMode(int i) {
        if (this.items.isEmpty()) {
            Log.w("Keyguard-Editor-EFController", "setColorMode: enpty items");
            return;
        }
        if (this.lastColorMode == i) {
            Log.i("Keyguard-Editor-EFController", "setColorMode: has updated");
            return;
        }
        Iterator it = this.items.entrySet().iterator();
        while (it.hasNext()) {
            ((KgFrame) ((Map.Entry) it.next()).getValue()).setColorMode(i);
        }
        this.lastColorMode = i;
    }

    public final void showAll() {
        Iterator it = this.items.entrySet().iterator();
        while (it.hasNext()) {
            ((View) ((Map.Entry) it.next()).getValue()).setVisibility(0);
        }
    }

    public final void updateIfNeeded(List params) {
        Intrinsics.checkNotNullParameter(params, "params");
        Iterator it = params.iterator();
        while (it.hasNext()) {
            KgFrameParam kgFrameParam = (KgFrameParam) it.next();
            KgFrame kgFrame = (KgFrame) this.items.get(Integer.valueOf(kgFrameParam.getId()));
            if (kgFrame != null) {
                kgFrame.updateIfNeeded(kgFrameParam);
            }
        }
        FrameLayout frameLayout = this.foreLayer;
        if (frameLayout != null) {
            frameLayout.requestLayout();
        }
    }
}
