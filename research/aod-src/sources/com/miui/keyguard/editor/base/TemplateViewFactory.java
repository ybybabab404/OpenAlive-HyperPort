package com.miui.keyguard.editor.base;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.edit.allinone.AllInOneTemplateView;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.EffectsTemplateView;
import com.miui.keyguard.editor.edit.base.HomeTemplateView;
import com.miui.keyguard.editor.edit.classicclock.ClassicMaxTemplateView;
import com.miui.keyguard.editor.edit.classicclock.ClassicPlusTemplateView;
import com.miui.keyguard.editor.edit.classicclock.ClassicTemplateView;
import com.miui.keyguard.editor.edit.doodleclock.DoodleTemplateView;
import com.miui.keyguard.editor.edit.easternartclock.EasternArtATemplateView;
import com.miui.keyguard.editor.edit.easternartclock.EasternArtBTemplateView;
import com.miui.keyguard.editor.edit.easternartclock.TraditionalChinaTemplateView;
import com.miui.keyguard.editor.edit.frameclock.FrameTemplateView;
import com.miui.keyguard.editor.edit.magazineclock.MagazineATemplateView;
import com.miui.keyguard.editor.edit.magazineclock.MagazineBTemplateView;
import com.miui.keyguard.editor.edit.magazineclock.MagazineCTemplateView;
import com.miui.keyguard.editor.edit.oversizeclock.OversizeATemplateView;
import com.miui.keyguard.editor.edit.oversizeclock.OversizeBTemplateView;
import com.miui.keyguard.editor.edit.padclock.PadATemplateView;
import com.miui.keyguard.editor.edit.padclock.PadBTemplateView;
import com.miui.keyguard.editor.edit.padclock.PadCTemplateView;
import com.miui.keyguard.editor.edit.petsclock.PetsTemplateView;
import com.miui.keyguard.editor.edit.rhombusclock.RhombusTemplateView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
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
    */
/* JADX INFO: compiled from: TemplateViewFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateViewFactory {

    @NotNull
    public static final TemplateViewFactory INSTANCE = new TemplateViewFactory();
    public static final Map idToView = MapsKt.mapOf(TuplesKt.to("rhombus", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$0((Context) obj);
        }
    }), TuplesKt.to("classic", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda10
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$1((Context) obj);
        }
    }), TuplesKt.to("classic_plus", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda11
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$2((Context) obj);
        }
    }), TuplesKt.to("classic_max", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda12
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$3((Context) obj);
        }
    }), TuplesKt.to("magazine_a", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda13
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$4((Context) obj);
        }
    }), TuplesKt.to("magazine_b", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda14
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$5((Context) obj);
        }
    }), TuplesKt.to("magazine_c", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda15
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$6((Context) obj);
        }
    }), TuplesKt.to("doodle", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda16
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$7((Context) obj);
        }
    }), TuplesKt.to("smart_frame", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda17
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$8((Context) obj);
        }
    }), TuplesKt.to("eastern_a", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda18
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$9((Context) obj);
        }
    }), TuplesKt.to("eastern_b", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$10((Context) obj);
        }
    }), TuplesKt.to("eastern_c", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$11((Context) obj);
        }
    }), TuplesKt.to("oversize_a", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$12((Context) obj);
        }
    }), TuplesKt.to("oversize_b", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$13((Context) obj);
        }
    }), TuplesKt.to("pad_exclusive_a", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$14((Context) obj);
        }
    }), TuplesKt.to("pad_exclusive_b", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda6
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$15((Context) obj);
        }
    }), TuplesKt.to("pad_exclusive_c", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda7
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$16((Context) obj);
        }
    }), TuplesKt.to("depth_pets", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda8
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$17((Context) obj);
        }
    }), TuplesKt.to("all_in_one", new Function1() { // from class: com.miui.keyguard.editor.base.TemplateViewFactory$$ExternalSyntheticLambda9
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return TemplateViewFactory.idToView$lambda$18((Context) obj);
        }
    }));

    public static /* synthetic */ BaseTemplateView createScaledPreviewHomeTemplateView$default(TemplateViewFactory templateViewFactory, Context context, FrameLayout.LayoutParams layoutParams, float f, float f2, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = false;
        }
        return templateViewFactory.createScaledPreviewHomeTemplateView(context, layoutParams, f, f2, z);
    }

    public static /* synthetic */ BaseTemplateView createScaledPreviewTemplateView$default(TemplateViewFactory templateViewFactory, Context context, String str, FrameLayout.LayoutParams layoutParams, float f, float f2, boolean z, int i, Object obj) {
        if ((i & 32) != 0) {
            z = false;
        }
        return templateViewFactory.createScaledPreviewTemplateView(context, str, layoutParams, f, f2, z);
    }

    public static final BaseClockView idToView$lambda$0(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new RhombusTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$1(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new ClassicTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$10(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new EasternArtBTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$11(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new TraditionalChinaTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$12(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new OversizeATemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$13(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new OversizeBTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$14(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new PadATemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$15(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new PadBTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$16(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new PadCTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$17(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new PetsTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$18(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new AllInOneTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$2(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new ClassicPlusTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$3(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new ClassicMaxTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$4(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new MagazineATemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$5(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new MagazineBTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$6(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new MagazineCTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$7(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new DoodleTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$8(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new FrameTemplateView(ctx);
    }

    public static final BaseClockView idToView$lambda$9(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return new EasternArtATemplateView(ctx);
    }

    @Nullable
    public final BaseClockView create(@NotNull Context context, @Nullable String str) {
        Intrinsics.checkNotNullParameter(context, "context");
        Function1 function1 = (Function1) idToView.get(str);
        if (function1 != null) {
            return (BaseClockView) function1.invoke(context);
        }
        Log.w("TemplateViewFactory", "no templateViewClass found, templateId " + str);
        return null;
    }

    @Nullable
    public final BaseTemplateView create(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new EffectsTemplateView(context);
    }

    @NotNull
    public final BaseTemplateView createScaledPreviewHomeTemplateView(@NotNull Context context, @NotNull FrameLayout.LayoutParams layoutParams, float f, float f2, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        HomeTemplateView homeTemplateView = new HomeTemplateView(context);
        homeTemplateView.setAnimLayer(z);
        if (layoutParams.gravity == -1) {
            layoutParams.gravity = DeviceUtil.INSTANCE.isRtl(context) ? 8388613 : 8388611;
        }
        homeTemplateView.setLayoutParams(layoutParams);
        homeTemplateView.setPivotX(0.0f);
        homeTemplateView.setPivotY(0.0f);
        homeTemplateView.setScaleX(f);
        homeTemplateView.setScaleY(f2);
        homeTemplateView.disableTouch();
        return homeTemplateView;
    }

    @Nullable
    public final BaseTemplateView createScaledPreviewTemplateView(@NotNull Context context, @NotNull String templateId, @NotNull FrameLayout.LayoutParams layoutParams, float f, float f2, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(templateId, "templateId");
        Intrinsics.checkNotNullParameter(layoutParams, "layoutParams");
        BaseTemplateView baseTemplateViewCreate = create(context);
        if (baseTemplateViewCreate == null) {
            return null;
        }
        baseTemplateViewCreate.setAnimLayer(z);
        if (layoutParams.gravity == -1) {
            layoutParams.gravity = DeviceUtil.INSTANCE.isRtl(context) ? 8388613 : 8388611;
        }
        baseTemplateViewCreate.setLayoutParams(layoutParams);
        baseTemplateViewCreate.setPivotX(0.0f);
        baseTemplateViewCreate.setPivotY(0.0f);
        baseTemplateViewCreate.setScaleX(f);
        baseTemplateViewCreate.setScaleY(f2);
        baseTemplateViewCreate.disableTouch();
        return baseTemplateViewCreate;
    }
}
