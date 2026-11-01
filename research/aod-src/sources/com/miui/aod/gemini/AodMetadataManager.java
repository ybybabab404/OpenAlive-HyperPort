package com.miui.aod.gemini;

import android.app.KeyguardManager;
import android.app.appfunctions.AppFunctionException;
import android.content.Context;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionException$$ExternalSyntheticApiModelOutline1;
import androidx.appfunctions.service.AppFunction;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItem;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateItemMetadata;
import com.google.android.appfunctions.schema.devicestate.v1.DeviceStateMetadataResponse;
import com.google.android.appfunctions.schema.devicestate.v1.GetDeviceStateMetadata;
import com.google.android.appfunctions.schema.devicestate.v1.GetDeviceStateMetadataParams;
import com.google.android.appfunctions.schema.devicestate.v1.LocalizedString;
import com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates;
import com.google.android.appfunctions.schema.devicestate.v1.PerScreenMetadata;
import com.google.gson.Gson;
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
/* JADX INFO: compiled from: AodMetadataManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AodMetadataManager implements GetDeviceStateMetadata {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final String EDITOR_SCREEN_AVAILABLE_VALUE = "available";

    @NotNull
    private static final String TAG = "GetAodStateMetadata";

    /* JADX INFO: compiled from: AodMetadataManager.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final DeviceStateMetadataResponse buildResponse(Context context, Context context2) {
        List<GeminiScreenConfig> listCreateScreens = AodConfigKt.createScreens(context, context2);
        List<PerScreenDeviceStates> listBuildEditorScreens = new GetEditorState().buildEditorScreens(context2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listBuildEditorScreens, 10));
        for (PerScreenDeviceStates perScreenDeviceStates : listBuildEditorScreens) {
            String description = perScreenDeviceStates.getDescription();
            List<DeviceStateItem> deviceStateItems = perScreenDeviceStates.getDeviceStateItems();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(deviceStateItems, 10));
            for (DeviceStateItem deviceStateItem : deviceStateItems) {
                String key = deviceStateItem.getKey();
                if (key == null) {
                    key = "";
                }
                String str = key;
                String purpose = deviceStateItem.getPurpose();
                if (purpose == null) {
                    purpose = perScreenDeviceStates.getDescription();
                }
                String str2 = purpose;
                LocalizedString name = deviceStateItem.getName();
                Boolean bool = Boolean.TRUE;
                String strEditorScreenPossibleValue = editorScreenPossibleValue();
                String hintText = deviceStateItem.getHintText();
                if (hintText == null) {
                    hintText = perScreenDeviceStates.getDescription();
                }
                arrayList2.add(new DeviceStateItemMetadata(str, str2, name, null, bool, strEditorScreenPossibleValue, hintText));
            }
            arrayList.add(new PerScreenMetadata(description, perScreenDeviceStates.getIntentUri(), null, arrayList2, 4, null));
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator<T> it = listCreateScreens.iterator();
        while (it.hasNext()) {
            PerScreenMetadata perScreenMetadataMakeUpMetaData = ((GeminiScreenConfig) it.next()).makeUpMetaData(context);
            if (perScreenMetadataMakeUpMetaData != null) {
                arrayList3.add(perScreenMetadataMakeUpMetaData);
            }
        }
        List listPlus = CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayList);
        String string = context.getResources().getConfiguration().getLocales().get(0).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return new DeviceStateMetadataResponse(listPlus, string, null, null, null, 28, null);
    }

    private final String editorScreenPossibleValue() {
        String json = new Gson().toJson(new PredeterminedFinitePossibleValue(new PredeterminedFinitePossibleValue.PredeterminedFiniteDetail(null, CollectionsKt.listOf(EDITOR_SCREEN_AVAILABLE_VALUE), 1, null)));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        return json;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: android.app.appfunctions.AppFunctionException */
    @AppFunction
    @Nullable
    public Object getDeviceStateMetadata(@NotNull AppFunctionContext appFunctionContext, @NotNull GetDeviceStateMetadataParams getDeviceStateMetadataParams, @NotNull Continuation<? super DeviceStateMetadataResponse> continuation) throws AppFunctionException {
        Context context = appFunctionContext.getContext();
        if (!AodConfigKt.isSupportAppfunction()) {
            return new DeviceStateMetadataResponse(CollectionsKt.emptyList(), "", null, null, null, 28, null);
        }
        if (!((KeyguardManager) context.getSystemService(KeyguardManager.class)).isDeviceLocked()) {
            return buildResponse(context, AodConfigKt.createEnglishContext(context));
        }
        AppFunctionException$$ExternalSyntheticApiModelOutline1.m();
        throw AodDeviceStateSetter$$ExternalSyntheticApiModelOutline0.m(1000, "Attempting to execute a device state app function while the device is locked");
    }
}
