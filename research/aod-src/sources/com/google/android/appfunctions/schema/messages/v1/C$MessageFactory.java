package com.google.android.appfunctions.schema.messages.v1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionData;
import androidx.appfunctions.internal.AppFunctionSerializableFactory;
import com.google.android.appfunctions.schema.types.v1.C$DateTimeFactory;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: renamed from: com.google.android.appfunctions.schema.messages.v1.$MessageFactory, reason: invalid class name */
/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
public final class C$MessageFactory implements AppFunctionSerializableFactory<Message> {
    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public Message fromAppFunctionData(@NonNull AppFunctionData appFunctionData) {
        Intrinsics.checkNotNullParameter(appFunctionData, "appFunctionData");
        AppFunctionData appFunctionDataWithSpec = getAppFunctionDataWithSpec(appFunctionData, "com.google.android.appfunctions.schema.messages.v1.Message");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$AttachmentFactory c$AttachmentFactory = new C$AttachmentFactory();
        String stringOrNull = appFunctionDataWithSpec.getStringOrNull("id");
        if (stringOrNull == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull2 = appFunctionDataWithSpec.getStringOrNull("messageType");
        if (stringOrNull2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull3 = appFunctionDataWithSpec.getStringOrNull("body");
        if (stringOrNull3 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String stringOrNull4 = appFunctionDataWithSpec.getStringOrNull("senderId");
        if (stringOrNull4 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        List stringList = appFunctionDataWithSpec.getStringList("recipientsIds");
        if (stringList == null) {
            stringList = CollectionsKt.emptyList();
        }
        List list = stringList;
        AppFunctionData appFunctionData2 = appFunctionDataWithSpec.getAppFunctionData("dateSent");
        DateTime dateTimeFromAppFunctionData = appFunctionData2 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData2) : null;
        AppFunctionData appFunctionData3 = appFunctionDataWithSpec.getAppFunctionData("dateReceived");
        DateTime dateTimeFromAppFunctionData2 = appFunctionData3 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData3) : null;
        AppFunctionData appFunctionData4 = appFunctionDataWithSpec.getAppFunctionData("dateRead");
        DateTime dateTimeFromAppFunctionData3 = appFunctionData4 != null ? c$DateTimeFactory.fromAppFunctionData(appFunctionData4) : null;
        List appFunctionDataList = appFunctionDataWithSpec.getAppFunctionDataList("messageAttachments");
        if (appFunctionDataList == null) {
            appFunctionDataList = CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(appFunctionDataList, 10));
        Iterator it = appFunctionDataList.iterator();
        while (it.hasNext()) {
            arrayList.add(c$AttachmentFactory.fromAppFunctionData((AppFunctionData) it.next()));
        }
        return new Message(stringOrNull, stringOrNull2, stringOrNull3, stringOrNull4, list, dateTimeFromAppFunctionData, dateTimeFromAppFunctionData2, dateTimeFromAppFunctionData3, arrayList);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public /* bridge */ /* synthetic */ AppFunctionData.Builder getAppFunctionDataBuilder(@NotNull String str) {
        return super.getAppFunctionDataBuilder(str);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public /* bridge */ /* synthetic */ AppFunctionData getAppFunctionDataWithSpec(@NotNull AppFunctionData appFunctionData, @NotNull String str) {
        return super.getAppFunctionDataWithSpec(appFunctionData, str);
    }

    @Override // androidx.appfunctions.internal.AppFunctionSerializableFactory
    @NotNull
    public AppFunctionData toAppFunctionData(@NonNull Message appFunctionSerializable) {
        Intrinsics.checkNotNullParameter(appFunctionSerializable, "appFunctionSerializable");
        C$DateTimeFactory c$DateTimeFactory = new C$DateTimeFactory();
        C$AttachmentFactory c$AttachmentFactory = new C$AttachmentFactory();
        AppFunctionData.Builder appFunctionDataBuilder = getAppFunctionDataBuilder("com.google.android.appfunctions.schema.messages.v1.Message");
        appFunctionDataBuilder.setString("id", appFunctionSerializable.getId());
        appFunctionDataBuilder.setString("messageType", appFunctionSerializable.getMessageType());
        appFunctionDataBuilder.setString("body", appFunctionSerializable.getBody());
        appFunctionDataBuilder.setString("senderId", appFunctionSerializable.getSenderId());
        appFunctionDataBuilder.setStringList("recipientsIds", appFunctionSerializable.getRecipientsIds());
        DateTime dateSent = appFunctionSerializable.getDateSent();
        if (dateSent != null) {
            appFunctionDataBuilder.setAppFunctionData("dateSent", c$DateTimeFactory.toAppFunctionData(dateSent));
        }
        DateTime dateReceived = appFunctionSerializable.getDateReceived();
        if (dateReceived != null) {
            appFunctionDataBuilder.setAppFunctionData("dateReceived", c$DateTimeFactory.toAppFunctionData(dateReceived));
        }
        DateTime dateRead = appFunctionSerializable.getDateRead();
        if (dateRead != null) {
            appFunctionDataBuilder.setAppFunctionData("dateRead", c$DateTimeFactory.toAppFunctionData(dateRead));
        }
        List messageAttachments = appFunctionSerializable.getMessageAttachments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(messageAttachments, 10));
        Iterator it = messageAttachments.iterator();
        while (it.hasNext()) {
            arrayList.add(c$AttachmentFactory.toAppFunctionData((Attachment) it.next()));
        }
        appFunctionDataBuilder.setAppFunctionDataList("messageAttachments", arrayList);
        return appFunctionDataBuilder.build();
    }
}
