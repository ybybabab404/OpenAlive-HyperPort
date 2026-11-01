package com.miui.keyguard.editor.edit;

import android.content.Context;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.edit.base.BaseClockView;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: ThemeClockView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.edit.ThemeClockView$init$1", f = "ThemeClockView.kt", l = {51}, m = "invokeSuspend")
final class ThemeClockView$init$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ CoroutineScope $coroutineScope;
    public final /* synthetic */ String $wallpaperType;
    public Object L$0;
    public int label;
    public final /* synthetic */ ThemeClockView this$0;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.ThemeClockView$init$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: ThemeClockView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.ThemeClockView$init$1$1", f = "ThemeClockView.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super CommonConfig>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return TemplateApi.Companion.getInstance(this.$context).getCurrentTemplateConfigInternal(true);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ThemeClockView$init$1(ThemeClockView themeClockView, Context context, String str, CoroutineScope coroutineScope, Continuation continuation) {
        super(2, continuation);
        this.this$0 = themeClockView;
        this.$context = context;
        this.$wallpaperType = str;
        this.$coroutineScope = coroutineScope;
    }

    public static final void invokeSuspend$lambda$1$lambda$0(CoroutineScope coroutineScope, ThemeClockView themeClockView) {
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new ThemeClockView$init$1$2$1$1(themeClockView, null), 3, null);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ThemeClockView$init$1(this.this$0, this.$context, this.$wallpaperType, this.$coroutineScope, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((ThemeClockView$init$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ThemeClockView themeClockView;
        TemplateConfig lockscreenInfo;
        ClockInfo clockInfo;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ThemeClockView themeClockView2 = this.this$0;
            CoroutineDispatcher io2 = Dispatchers.getIO();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$context, null);
            this.L$0 = themeClockView2;
            this.label = 1;
            Object objWithContext = BuildersKt.withContext(io2, anonymousClass1, this);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
            themeClockView = themeClockView2;
            obj = objWithContext;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            themeClockView = (ThemeClockView) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        themeClockView.templateConfig = (CommonConfig) obj;
        CommonConfig commonConfig = this.this$0.templateConfig;
        if (commonConfig != null) {
            final ThemeClockView themeClockView3 = this.this$0;
            Context context = this.$context;
            String str = this.$wallpaperType;
            final CoroutineScope coroutineScope = this.$coroutineScope;
            TemplateViewFactory templateViewFactory = TemplateViewFactory.INSTANCE;
            CommonConfig commonConfig2 = themeClockView3.templateConfig;
            themeClockView3.clockView = templateViewFactory.create(context, (commonConfig2 == null || (lockscreenInfo = commonConfig2.getLockscreenInfo()) == null || (clockInfo = lockscreenInfo.getClockInfo()) == null) ? null : clockInfo.getTemplateId());
            BaseClockView baseClockView = themeClockView3.clockView;
            if (baseClockView != null) {
                baseClockView.initForTheme();
            }
            BaseClockView baseClockView2 = themeClockView3.clockView;
            if (baseClockView2 != null) {
                baseClockView2.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            }
            BaseClockView baseClockView3 = themeClockView3.clockView;
            themeClockView3.setAutoColorPicker(baseClockView3 != null ? baseClockView3.createAutoColorPicker() : null);
            themeClockView3.addView(themeClockView3.clockView);
            if (commonConfig.getLockscreenInfo().getClockInfo().getClockEffect() == 5) {
                commonConfig.getLockscreenInfo().getClockInfo().setClockEffect(1);
            }
            BaseClockView baseClockView4 = themeClockView3.clockView;
            if (baseClockView4 != null) {
                baseClockView4.initTemplateBean(commonConfig.getLockscreenInfo(), str);
            }
            BaseClockView baseClockView5 = themeClockView3.clockView;
            if (baseClockView5 != null) {
                baseClockView5.addMiuiClockViewInitListener(new Runnable() { // from class: com.miui.keyguard.editor.edit.ThemeClockView$init$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ThemeClockView$init$1.invokeSuspend$lambda$1$lambda$0(coroutineScope, themeClockView3);
                    }
                });
            }
        }
        return Unit.INSTANCE;
    }
}
