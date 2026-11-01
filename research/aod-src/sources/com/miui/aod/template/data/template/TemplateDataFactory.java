package com.miui.aod.template.data.template;

import android.content.Context;
import android.util.Log;
import com.miui.aod.template.FullAodTemplateView;
import com.miui.aod.template.data.template.base.BaseTemplateConfig;
import com.miui.aod.template.data.template.base.EffectsTemplateConfig;
import com.miui.aod.template.data.template.classicclock.AllInOneTemplateConfig;
import com.miui.aod.template.data.template.classicclock.ClassicTemplateConfig;
import com.miui.aod.template.data.template.easternartclock.EasternArtATemplateConfig;
import com.miui.aod.template.data.template.easternartclock.EasternArtBTemplateConfig;
import com.miui.aod.template.data.template.easternartclock.TraditionalChinaTemplateConfig;
import com.miui.aod.template.data.template.magazineclock.MagazineATemplateConfig;
import com.miui.aod.template.data.template.magazineclock.MagazineBTemplateConfig;
import com.miui.aod.template.data.template.magazineclock.MagazineCTemplateConfig;
import com.miui.aod.template.data.template.padclock.PadATemplateConfig;
import com.miui.aod.template.data.template.padclock.PadBTemplateConfig;
import com.miui.aod.template.data.template.padclock.PadCTemplateConfig;
import com.miui.aod.template.data.template.rhombusclock.RhombusTemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateDataFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class TemplateDataFactory {
    public final String TAG = "TemplateDataFactory";
    public final boolean isDebug = true;
    public final Map idToInit = MapsKt.mapOf(TuplesKt.to("rhombus", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$0((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("classic", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$1((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("magazine_a", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$2((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("magazine_b", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$3((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("magazine_c", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda6
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$4((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("eastern_a", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda7
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$5((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("eastern_b", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda8
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$6((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("eastern_c", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda9
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$7((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("pad_exclusive_a", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda10
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$8((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("pad_exclusive_b", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda11
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$9((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("pad_exclusive_c", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$10((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }), TuplesKt.to("all_in_one", new Function3() { // from class: com.miui.aod.template.data.template.TemplateDataFactory$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return TemplateDataFactory.idToInit$lambda$11((TemplateConfig) obj, (Context) obj2, (FullAodTemplateView) obj3);
        }
    }));

    public static final EffectsTemplateConfig idToInit$lambda$0(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new RhombusTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$1(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new ClassicTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$10(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new PadCTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$11(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new AllInOneTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$2(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new MagazineATemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$3(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new MagazineBTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$4(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new MagazineCTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$5(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new EasternArtATemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$6(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new EasternArtBTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$7(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new TraditionalChinaTemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$8(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new PadATemplateConfig(config, ctx, templateView);
    }

    public static final BaseTemplateConfig idToInit$lambda$9(TemplateConfig config, Context ctx, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        return new PadBTemplateConfig(config, ctx, templateView);
    }

    public final BaseTemplateConfig getTemplateConfig(Context ctx, TemplateConfig config, FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        Function3 function3 = (Function3) this.idToInit.get(config.getClockInfo().getTemplateId());
        BaseTemplateConfig baseTemplateConfig = function3 != null ? (BaseTemplateConfig) function3.invoke(config, ctx, templateView) : null;
        if (this.isDebug) {
            Log.i(this.TAG, "templateId = " + config.getClockInfo().getTemplateId() + ", templateConfig = " + baseTemplateConfig);
        }
        return baseTemplateConfig;
    }
}
