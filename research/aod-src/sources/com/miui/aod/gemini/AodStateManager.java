package com.miui.aod.gemini;

import android.app.KeyguardManager;
import android.app.appfunctions.AppFunctionException;
import android.content.Context;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionException$$ExternalSyntheticApiModelOutline1;
import androidx.appfunctions.service.AppFunction;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateResponse;
import com.google.android.appfunctions.schema.devicestate.v1.GetUncategorizedDeviceState;
import com.google.android.appfunctions.schema.devicestate.v1.GetUncategorizedDeviceStateParams;
import com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates;
import com.miui.keyguard.editor.gemini.GetEditorState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: AodStateManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AodStateManager implements GetUncategorizedDeviceState {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    public static final String TAG = "GetAodState";

    /* JADX INFO: compiled from: AodStateManager.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final DeviceStateResponse buildResponse(Context context, Context context2) {
        List<GeminiScreenConfig> listCreateScreens = AodConfigKt.createScreens(context, context2);
        List listBuildEditorScreens = new GetEditorState().buildEditorScreens(context2);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listCreateScreens.iterator();
        while (it.hasNext()) {
            PerScreenDeviceStates perScreenDeviceStatesMakeUpDeviceStates = ((GeminiScreenConfig) it.next()).makeUpDeviceStates(context, AodStateCategory.UNCATEGORIZED);
            if (perScreenDeviceStatesMakeUpDeviceStates != null) {
                arrayList.add(perScreenDeviceStatesMakeUpDeviceStates);
            }
        }
        List listPlus = CollectionsKt.plus((Collection) arrayList, (Iterable) listBuildEditorScreens);
        String string = context.getResources().getConfiguration().getLocales().get(0).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return new DeviceStateResponse(listPlus, string, null, 4, null);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: android.app.appfunctions.AppFunctionException */
    @AppFunction
    @Nullable
    public Object getUncategorizedDeviceState(@NotNull AppFunctionContext appFunctionContext, @NotNull GetUncategorizedDeviceStateParams getUncategorizedDeviceStateParams, @NotNull Continuation<? super DeviceStateResponse> continuation) throws AppFunctionException {
        Context context = appFunctionContext.getContext();
        if (!AodConfigKt.isSupportAppfunction()) {
            return new DeviceStateResponse(CollectionsKt.emptyList(), "", null, 4, null);
        }
        if (!((KeyguardManager) context.getSystemService(KeyguardManager.class)).isDeviceLocked()) {
            return buildResponse(context, AodConfigKt.createEnglishContext(context));
        }
        AppFunctionException$$ExternalSyntheticApiModelOutline1.m();
        throw AodDeviceStateSetter$$ExternalSyntheticApiModelOutline0.m(1000, "Attempting to execute a device state app function while the device is locked");
    }
}
