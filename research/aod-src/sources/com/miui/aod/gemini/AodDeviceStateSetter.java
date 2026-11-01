package com.miui.aod.gemini;

import android.app.KeyguardManager;
import android.app.appfunctions.AppFunctionException;
import android.content.Context;
import android.content.Intent;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionException$$ExternalSyntheticApiModelOutline1;
import androidx.appfunctions.service.AppFunction;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem;
import com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItem;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemParams;
import com.google.android.appfunctions.schema.devicestate.v1.SetDeviceStateItemResponse;
import com.miui.aod.R;
import com.miui.keyguard.editor.gemini.GetEditorState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
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
/* JADX INFO: compiled from: AodDeviceStateSetter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AodDeviceStateSetter implements SetDeviceStateItem {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final String EDITOR_SCREEN_AVAILABLE_VALUE = "available";

    /* JADX INFO: compiled from: AodDeviceStateSetter.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final SetDeviceStateItemResponse handleEditorScreenState(Context context, Context context2, SetDeviceStateItemParams setDeviceStateItemParams) {
        Object next;
        GetEditorState getEditorState = new GetEditorState();
        if (!CollectionsKt.toSet(getEditorState.getEditorScreenKey()).contains(setDeviceStateItemParams.getKey())) {
            return null;
        }
        if (!Intrinsics.areEqual(setDeviceStateItemParams.getValue(), EDITOR_SCREEN_AVAILABLE_VALUE)) {
            return new SetDeviceStateItemResponse(false, EDITOR_SCREEN_AVAILABLE_VALUE, context2.getString(R.string.aod_failure_reason_invalid_value));
        }
        Iterator it = getEditorState.buildEditorScreens(context2).iterator();
        loop0: while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            List deviceStateItems = ((PerScreenDeviceStates) next).getDeviceStateItems();
            if (deviceStateItems == null || !deviceStateItems.isEmpty()) {
                Iterator it2 = deviceStateItems.iterator();
                while (it2.hasNext()) {
                    if (Intrinsics.areEqual(((DeviceStateItem) it2.next()).getKey(), setDeviceStateItemParams.getKey())) {
                        break loop0;
                    }
                }
            }
        }
        PerScreenDeviceStates perScreenDeviceStates = (PerScreenDeviceStates) next;
        boolean zLaunchEditorScreen = launchEditorScreen(context, perScreenDeviceStates != null ? perScreenDeviceStates.getIntentUri() : null);
        return new SetDeviceStateItemResponse(zLaunchEditorScreen, EDITOR_SCREEN_AVAILABLE_VALUE, zLaunchEditorScreen ? null : context2.getString(R.string.aod_failure_reason_invalid_value));
    }

    private final boolean launchEditorScreen(Context context, String str) {
        Object objM1061constructorimpl;
        if (str == null || StringsKt.isBlank(str)) {
            return false;
        }
        try {
            Result.Companion companion = Result.Companion;
            Intent uri = Intent.parseUri(str, 1);
            uri.addFlags(268435456);
            uri.setPackage(context.getPackageName());
            context.startActivity(uri);
            objM1061constructorimpl = Result.m1061constructorimpl(uri);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        return Result.m1066isSuccessimpl(objM1061constructorimpl);
    }

    private final boolean shouldCheckForDeviceLock(SetDeviceStateItemParams setDeviceStateItemParams) {
        return !Intrinsics.areEqual(setDeviceStateItemParams.getRequestInitiatedWhileUnlocked(), Boolean.TRUE);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: android.app.appfunctions.AppFunctionException */
    @AppFunction
    @Nullable
    public Object setDeviceStateItem(@NotNull AppFunctionContext appFunctionContext, @NotNull SetDeviceStateItemParams setDeviceStateItemParams, @NotNull Continuation<? super SetDeviceStateItemResponse> continuation) throws AppFunctionException {
        Context context = appFunctionContext.getContext();
        if (!AodConfigKt.isSupportAppfunction()) {
            return new SetDeviceStateItemResponse(false, "", "Not supported");
        }
        if (shouldCheckForDeviceLock(setDeviceStateItemParams) && ((KeyguardManager) context.getSystemService(KeyguardManager.class)).isDeviceLocked()) {
            AppFunctionException$$ExternalSyntheticApiModelOutline1.m();
            throw AodDeviceStateSetter$$ExternalSyntheticApiModelOutline0.m(1000, "Attempting to execute a device state app function while the device is locked");
        }
        Context contextCreateEnglishContext = AodConfigKt.createEnglishContext(context);
        List<GeminiScreenConfig> listCreateScreens = AodConfigKt.createScreens(context, contextCreateEnglishContext);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listCreateScreens) {
            if (((GeminiScreenConfig) obj).isScreenEnabled()) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Pair<Boolean, SetDeviceStateItemResponse> pair = ((GeminiScreenConfig) it.next()).set(setDeviceStateItemParams);
            if (((Boolean) pair.getFirst()).booleanValue() && pair.getSecond() != null) {
                Object second = pair.getSecond();
                Intrinsics.checkNotNull(second);
                return second;
            }
        }
        SetDeviceStateItemResponse setDeviceStateItemResponseHandleEditorScreenState = handleEditorScreenState(context, contextCreateEnglishContext, setDeviceStateItemParams);
        return setDeviceStateItemResponseHandleEditorScreenState != null ? setDeviceStateItemResponseHandleEditorScreenState : new SetDeviceStateItemResponse(false, "false", context.getString(R.string.aod_failure_reason_invalid_value));
    }
}
