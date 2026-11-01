package com.miui.aod.gemini;

import android.app.appfunctions.AppFunctionException;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.service.internal.AppFunctionInvoker;
import androidx.appfunctions.service.internal.ConfigurableAppFunctionFactory;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse;
import com.google.android.appfunctions.schema.devicestate.v1.GetDeviceStateMetadataParams;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: renamed from: com.miui.aod.gemini.$AodMetadataManager_AppFunctionInvoker, reason: invalid class name */
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
/* JADX INFO: compiled from: $AodMetadataManager_AppFunctionInvoker.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class C$AodMetadataManager_AppFunctionInvoker implements AppFunctionInvoker {

    @NotNull
    private final Set<String> supportedFunctionIds = SetsKt.setOf(AodMetadataManagerIds.GET_DEVICE_STATE_METADATA_ID);

    /* JADX INFO: renamed from: com.miui.aod.gemini.$AodMetadataManager_AppFunctionInvoker$unsafeInvoke$1, reason: invalid class name */
    /* JADX INFO: compiled from: $AodMetadataManager_AppFunctionInvoker.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.aod.gemini.$AodMetadataManager_AppFunctionInvoker", f = "$AodMetadataManager_AppFunctionInvoker.kt", l = {30}, m = "unsafeInvoke")
    public static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return C$AodMetadataManager_AppFunctionInvoker.this.unsafeInvoke(null, null, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final AodMetadataManager unsafeInvoke$lambda$0() {
        return new AodMetadataManager();
    }

    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    @NotNull
    public Set<String> getSupportedFunctionIds() {
        return this.supportedFunctionIds;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: android.app.appfunctions.AppFunctionException */
    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    @Nullable
    public Object unsafeInvoke(@NotNull AppFunctionContext appFunctionContext, @NotNull String str, @NotNull Map<String, ? extends Object> map, @NotNull Continuation<Object> continuation) throws AppFunctionFunctionNotFoundException, AppFunctionException {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object deviceStateMetadata = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(deviceStateMetadata);
            if (!Intrinsics.areEqual(str, AodMetadataManagerIds.GET_DEVICE_STATE_METADATA_ID)) {
                throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
            }
            AodMetadataManager aodMetadataManager = (AodMetadataManager) new ConfigurableAppFunctionFactory(appFunctionContext.getContext(), new Function0() { // from class: com.miui.aod.gemini.$AodMetadataManager_AppFunctionInvoker$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return C$AodMetadataManager_AppFunctionInvoker.unsafeInvoke$lambda$0();
                }
            }).createEnclosingClass(AodMetadataManager.class);
            Object obj = map.get("getDeviceStateMetadataParams");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.google.android.appfunctions.schema.devicestate.v1.GetDeviceStateMetadataParams");
            anonymousClass1.label = 1;
            deviceStateMetadata = aodMetadataManager.getDeviceStateMetadata(appFunctionContext, (GetDeviceStateMetadataParams) obj, anonymousClass1);
            if (deviceStateMetadata == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(deviceStateMetadata);
        }
        return (DeviceStateMetadataResponse) deviceStateMetadata;
    }
}
