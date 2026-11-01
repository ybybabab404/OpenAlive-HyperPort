package com.miui.keyguard.shortcuts.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.provider.MiuiSettings;
import android.provider.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.miui.aod.utils.MiuiSettingsUtils;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.xiaomi.onetrack.api.a;
import com.xiaomi.onetrack.api.bf;
import com.xiaomi.settingsdk.backup.data.DataPackage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.JobKt;
import miuix.os.Build;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
/* JADX INFO: compiled from: DataUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class DataUtils {
    public static final Uri SHORTCUT_DATA_LEFT_URI;
    public static final Uri SHORTCUT_DATA_RIGHT_URI;
    public static final List defaultWhiteItems;
    public static final DataUtils INSTANCE = new DataUtils();
    public static final ShortcutEntity EMPTY_ENTITY = new ShortcutEntity(0, null, null, null, null, null, null, null, null, false, false, null, null, null, false, 32767, null);
    public static final ShortcutEntity UNKNOWN_ENTITY = new ShortcutEntity(0, "unknown", null, null, null, null, "unknown", null, null, false, false, null, null, null, false, 32701, null);

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.utils.DataUtils$fetchInstalledShortcuts$2, reason: invalid class name */
    /* JADX INFO: compiled from: DataUtils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.utils.DataUtils$fetchInstalledShortcuts$2", f = "DataUtils.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<ShortcutEntity>>, Object> {
        public final /* synthetic */ Context $context;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        public static final boolean invokeSuspend$lambda$0(CoroutineScope coroutineScope) {
            return !JobKt.isActive(coroutineScope.getCoroutineContext());
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$context, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            return DataUtils.INSTANCE.getAllShortcutsDataInApps(this.$context, new Function0() { // from class: com.miui.keyguard.shortcuts.utils.DataUtils$fetchInstalledShortcuts$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(DataUtils.AnonymousClass2.invokeSuspend$lambda$0(coroutineScope));
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.utils.DataUtils$fetchShortcutFromAppAsync$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DataUtils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.utils.DataUtils$fetchShortcutFromAppAsync$2", f = "DataUtils.kt", l = {}, m = "invokeSuspend")
    public static final class C01772 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ShortcutEntity>, Object> {
        public final /* synthetic */ boolean $checkAvailable;
        public final /* synthetic */ Context $context;
        public final /* synthetic */ String $packageName;
        public final /* synthetic */ String $tag;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01772(Context context, String str, String str2, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$packageName = str;
            this.$tag = str2;
            this.$checkAvailable = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01772(this.$context, this.$packageName, this.$tag, this.$checkAvailable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01772) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return DataUtils.INSTANCE.fetchShortcutFromApp(this.$context, this.$packageName, this.$tag, this.$checkAvailable);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.utils.DataUtils$isFashionGalleryShortcutAvailable$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DataUtils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.utils.DataUtils$isFashionGalleryShortcutAvailable$2", f = "DataUtils.kt", l = {}, m = "invokeSuspend")
    public static final class C01782 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01782(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01782(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01782) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(DataUtils.INSTANCE.getShortcutAvailable(this.$context));
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.utils.DataUtils$saveShortcutDataToSettingsAsync$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: DataUtils.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.utils.DataUtils$saveShortcutDataToSettingsAsync$2", f = "DataUtils.kt", l = {}, m = "invokeSuspend")
    public static final class C01792 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ ShortcutEntity $data;
        public final /* synthetic */ boolean $isLeft;
        public final /* synthetic */ String $reason;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01792(Context context, boolean z, ShortcutEntity shortcutEntity, String str, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$isLeft = z;
            this.$data = shortcutEntity;
            this.$reason = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01792(this.$context, this.$isLeft, this.$data, this.$reason, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01792) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DataUtils.INSTANCE.saveShortcutDataToSettings(this.$context, this.$isLeft, this.$data, this.$reason);
            return Unit.INSTANCE;
        }
    }

    static {
        Uri uriFor = Settings.Secure.getUriFor("keyguard_shortcut_left");
        Intrinsics.checkNotNullExpressionValue(uriFor, "getUriFor(...)");
        SHORTCUT_DATA_LEFT_URI = uriFor;
        Uri uriFor2 = Settings.Secure.getUriFor("keyguard_shortcut_right");
        Intrinsics.checkNotNullExpressionValue(uriFor2, "getUriFor(...)");
        SHORTCUT_DATA_RIGHT_URI = uriFor2;
        defaultWhiteItems = new ArrayList();
    }

    public static final void backupShortcutSettings(Context context, DataPackage dataPackage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(dataPackage, "dataPackage");
        if (Build.IS_TABLET) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("keyguard_shortcut_left", MiuiSettingsUtils.Secure.getString(context.getContentResolver(), "keyguard_shortcut_left"));
            jSONObject.put("keyguard_shortcut_right", MiuiSettingsUtils.Secure.getString(context.getContentResolver(), "keyguard_shortcut_right"));
            dataPackage.addKeyJson("json_key_shortcut_Settings", jSONObject);
            LogUtils.logI$default(LogUtils.INSTANCE, "DataUtils", "backupShortcutSettings: settingJson = " + jSONObject, null, 4, null);
        } catch (JSONException e) {
            LogUtils.INSTANCE.logE("DataUtils", "backupShortcutSettings ", e);
        }
    }

    public static /* synthetic */ ShortcutEntity fetchShortcutFromApp$default(DataUtils dataUtils, Context context, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return dataUtils.fetchShortcutFromApp(context, str, str2, z);
    }

    public static /* synthetic */ Object fetchShortcutFromAppAsync$default(DataUtils dataUtils, Context context, String str, String str2, boolean z, Continuation continuation, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return dataUtils.fetchShortcutFromAppAsync(context, str, str2, z, continuation);
    }

    public static /* synthetic */ List getShortcutDataFromApplication$default(DataUtils dataUtils, ApplicationInfo applicationInfo, String str, Context context, Function0 function0, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            function0 = null;
        }
        Function0 function1 = function0;
        if ((i & 16) != 0) {
            z = true;
        }
        return dataUtils.getShortcutDataFromApplication(applicationInfo, str, context, function1, z);
    }

    public static final void restoreShortcutData(Context context, DataPackage dataPackage) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(dataPackage, "dataPackage");
        if (Build.IS_TABLET) {
            return;
        }
        try {
            if (dataPackage.get("json_key_shortcut_Settings") != null) {
                Object value = dataPackage.get("json_key_shortcut_Settings").getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.json.JSONObject");
                JSONObject jSONObject = (JSONObject) value;
                LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "restoreShortcutData: data = " + jSONObject, null, 4, null);
                if (jSONObject.has("keyguard_shortcut_left")) {
                    DataUtils dataUtils = INSTANCE;
                    String strOptString = jSONObject.optString("keyguard_shortcut_left");
                    Intrinsics.checkNotNullExpressionValue(strOptString, "optString(...)");
                    String strBuildNewDataJson = dataUtils.buildNewDataJson(context, strOptString);
                    if (strBuildNewDataJson != null) {
                        MiuiSettingsUtils.Secure.putString(context.getContentResolver(), "keyguard_shortcut_left", strBuildNewDataJson);
                    }
                }
                if (jSONObject.has("keyguard_shortcut_right")) {
                    DataUtils dataUtils2 = INSTANCE;
                    String strOptString2 = jSONObject.optString("keyguard_shortcut_right");
                    Intrinsics.checkNotNullExpressionValue(strOptString2, "optString(...)");
                    String strBuildNewDataJson2 = dataUtils2.buildNewDataJson(context, strOptString2);
                    if (strBuildNewDataJson2 != null) {
                        MiuiSettingsUtils.Secure.putString(context.getContentResolver(), "keyguard_shortcut_right", strBuildNewDataJson2);
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.INSTANCE.logE("DataUtils", "restoreShortcutData: ", e);
        }
    }

    public final ShortcutEntity buildDefaultShortcutData(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        return z ? buildShortcutDataLeftDefault(context) : buildShortcutDataRightDefault(context);
    }

    public final String buildNewDataJson(Context context, String str) {
        Object objM1061constructorimpl;
        ShortcutEntity shortcutEntityFetchShortcutFromApp$default;
        try {
            Result.Companion companion = Result.Companion;
            ShortcutEntity shortcutDataFromJson = getShortcutDataFromJson(str);
            objM1061constructorimpl = Result.m1061constructorimpl((shortcutDataFromJson == null || (shortcutEntityFetchShortcutFromApp$default = fetchShortcutFromApp$default(this, context, shortcutDataFromJson.getPackageName(), shortcutDataFromJson.getTag(), false, 8, null)) == null) ? null : new GsonBuilder().setPrettyPrinting().create().toJson(shortcutEntityFetchShortcutFromApp$default));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
        }
        return (String) (Result.m1065isFailureimpl(objM1061constructorimpl) ? null : objM1061constructorimpl);
    }

    public final ShortcutEntity buildShortcutDataDefault(Context context, String str, String str2) {
        LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "buildShortcutData, packageName=" + str + ", tag=" + str2, null, 4, null);
        if (PackageUtils.INSTANCE.getApplicationInfo(context, str) != null) {
            ShortcutEntity shortcutEntityFetchShortcutFromApp$default = fetchShortcutFromApp$default(INSTANCE, context, str, str2, false, 8, null);
            if (shortcutEntityFetchShortcutFromApp$default != null) {
                shortcutEntityFetchShortcutFromApp$default.setSystemDefault(true);
                shortcutEntityFetchShortcutFromApp$default.updateAvailable(context);
            }
            if (shortcutEntityFetchShortcutFromApp$default != null) {
                return shortcutEntityFetchShortcutFromApp$default;
            }
        }
        return EMPTY_ENTITY;
    }

    public final ShortcutEntity buildShortcutDataLeftDefault(Context context) {
        LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "buildShortcutDataLeftDefault", null, 4, null);
        boolean z = Build.IS_INTERNATIONAL_BUILD;
        return buildShortcutDataDefault(context, z ? "com.miui.android.fashiongallery" : "miui.systemui.plugin", z ? "GLOBAL_WC_HOMEPAGE" : "FLASHLIGHT_SET");
    }

    public final ShortcutEntity buildShortcutDataRightDefault(Context context) {
        LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "buildShortcutDataRightDefault", null, 4, null);
        return buildShortcutDataDefault(context, "com.android.camera", "CAMERA_CAPTURE");
    }

    public final String buildUniqueTag(String str, String str2) {
        return str + "-" + str2;
    }

    public final Object fetchInstalledShortcuts(Context context, Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(context, null), continuation);
    }

    public final ShortcutEntity fetchShortcutFromApp(Context context, String str, String str2, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object obj = null;
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return null;
        }
        for (Object obj2 : fetchShortcutsFromApp(context, str, z)) {
            if (Intrinsics.areEqual(((ShortcutEntity) obj2).getTag(), str2)) {
                obj = obj2;
                break;
            }
        }
        return (ShortcutEntity) obj;
    }

    public final Object fetchShortcutFromAppAsync(Context context, String str, String str2, boolean z, Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new C01772(context, str, str2, z, null), continuation);
    }

    public final List fetchShortcutsFromApp(Context context, String str, boolean z) {
        ApplicationInfo applicationInfo = PackageUtils.INSTANCE.getApplicationInfo(context, str);
        return applicationInfo == null ? CollectionsKt.emptyList() : getShortcutDataFromApplication$default(this, applicationInfo, str, context, null, z, 8, null);
    }

    public final List getAllShortcutsDataInApps(Context context, Function0 function0) {
        Intrinsics.checkNotNullParameter(context, "context");
        Trace.beginSection("getAllShortcutsDataInApps");
        long jCurrentTimeMillis = System.currentTimeMillis();
        List<ApplicationInfo> installedApplications = PackageUtils.INSTANCE.getInstalledApplications(context);
        ArrayList arrayList = new ArrayList();
        for (ApplicationInfo applicationInfo : installedApplications) {
            if (function0 != null && ((Boolean) function0.invoke()).booleanValue()) {
                LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "getAllShortcutsDataInApps cancelled, return", null, 4, null);
                Trace.endSection();
                return arrayList;
            }
            if (applicationInfo.metaData != null) {
                DataUtils dataUtils = INSTANCE;
                String packageName = applicationInfo.packageName;
                Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
                Context context2 = context;
                arrayList.addAll(getShortcutDataFromApplication$default(dataUtils, applicationInfo, packageName, context2, function0, false, 16, null));
                context = context2;
            }
        }
        Trace.endSection();
        LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "getAllShortcutsDataInApps size: " + arrayList.size() + ", cost:" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms", null, 4, null);
        return arrayList;
    }

    public final List getDefaultWhiteItems(Context context) {
        List list = defaultWhiteItems;
        if (!list.isEmpty()) {
            return list;
        }
        try {
            String strLoadJsonFromAssets = PackageUtils.INSTANCE.loadJsonFromAssets(context);
            if (strLoadJsonFromAssets.length() == 0) {
                return CollectionsKt.emptyList();
            }
            JSONArray jSONArray = new JSONObject(strLoadJsonFromAssets).getJSONArray("white_list_shortcuts");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    defaultWhiteItems.add(buildUniqueTag(jSONObject.getString("packageName"), jSONObject.getString("tag")));
                } catch (JSONException e) {
                    LogUtils.logE$default(LogUtils.INSTANCE, "DataUtils", "getDefaultWhiteItems json error: " + e, null, 4, null);
                }
            }
        } catch (JSONException e2) {
            LogUtils.logE$default(LogUtils.INSTANCE, "DataUtils", "getDefaultWhiteItems error: " + e2, null, 4, null);
        }
        return defaultWhiteItems;
    }

    public final ShortcutEntity getEMPTY_ENTITY() {
        return EMPTY_ENTITY;
    }

    public final String getJsonFromSettings(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        return MiuiSettingsUtils.Secure.getString(context.getContentResolver(), z ? "keyguard_shortcut_left" : "keyguard_shortcut_right");
    }

    public final Uri getSHORTCUT_DATA_LEFT_URI() {
        return SHORTCUT_DATA_LEFT_URI;
    }

    public final Uri getSHORTCUT_DATA_RIGHT_URI() {
        return SHORTCUT_DATA_RIGHT_URI;
    }

    public final boolean getShortcutAvailable(Context context) {
        Bundle bundleCall;
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            bundleCall = context.getContentResolver().call(Uri.parse("content://com.miui.android.fashiongallery.lockscreen_magazine_provider"), "getTransitionInfo", (String) null, (Bundle) null);
        } catch (Exception e) {
            LogUtils.INSTANCE.logE("DataUtils", "getShortcutAvailable call provider error", e);
            bundleCall = null;
        }
        String string = bundleCall != null ? bundleCall.getString("result_json") : null;
        LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "getShortcutAvailable resultJson = " + string, null, 4, null);
        if (string == null) {
            return false;
        }
        try {
            String strOptString = new JSONObject(string).optString("leftscreen_activity");
            PackageUtils packageUtils = PackageUtils.INSTANCE;
            Intrinsics.checkNotNull(strOptString);
            return packageUtils.checkMagazineLeftScreenActivityExist(context, strOptString);
        } catch (Exception e2) {
            LogUtils.INSTANCE.logE("DataUtils", "getShortcutAvailable parse json error", e2);
            return false;
        }
    }

    public final List getShortcutDataFromApplication(ApplicationInfo appInfo, String packageName, Context context, Function0 function0, boolean z) throws Throwable {
        XmlResourceParser xmlResourceParser;
        ArrayList arrayList;
        Context context2 = context;
        String str = "http://schemas.android.com/apk/res/android";
        Intrinsics.checkNotNullParameter(appInfo, "appInfo");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(context2, "context");
        ArrayList arrayList2 = new ArrayList();
        Bundle bundle = appInfo.metaData;
        int i = bundle != null ? bundle.getInt("miui_keyguard_shortcuts", 0) : 0;
        if (i != 0) {
            XmlResourceParser xml = null;
            try {
                Resources resourcesForApplication = PackageUtils.INSTANCE.getResourcesForApplication(context2, appInfo);
                if (resourcesForApplication != null) {
                    xml = resourcesForApplication.getXml(i);
                }
            } catch (Resources.NotFoundException unused) {
            }
            if (xml != null) {
                while (xml.next() != 1) {
                    try {
                        if (function0 != null && ((Boolean) function0.invoke()).booleanValue()) {
                            LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "getShortcutDataFromApplication cancelled, return", null, 4, null);
                            xml.close();
                            return arrayList2;
                        }
                        if (xml.getEventType() == 2 && Intrinsics.areEqual("intent", xml.getName())) {
                            String attributeValue = xml.getAttributeValue(str, bf.a);
                            String attributeValue2 = xml.getAttributeValue(str, "targetPackage");
                            String attributeValue3 = xml.getAttributeValue(str, "targetClass");
                            String attributeValue4 = xml.getAttributeValue(str, a.a);
                            String attributeValue5 = xml.getAttributeValue(str, "data");
                            String attributeValue6 = xml.getAttributeValue(str, "icon");
                            String attributeValue7 = xml.getAttributeValue(str, "tag");
                            LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "name: " + attributeValue + ", targetPackage: " + attributeValue2 + ", targetClass: " + attributeValue3 + ", action: " + attributeValue4 + ", data : " + attributeValue5 + ", icon: " + attributeValue6 + ", tag = " + attributeValue7, null, 4, null);
                            String str2 = str;
                            ArrayList arrayList3 = arrayList2;
                            xmlResourceParser = xml;
                            try {
                                try {
                                    ShortcutEntity shortcutEntity = new ShortcutEntity(0, packageName, attributeValue, attributeValue4, attributeValue5, attributeValue6, attributeValue7, attributeValue3, attributeValue2, false, false, null, null, null, false, 32257, null);
                                    context2 = context;
                                    shortcutEntity.buildShortcutNameAndDrawable(context2);
                                    arrayList = arrayList3;
                                    try {
                                        arrayList.add(shortcutEntity);
                                        arrayList2 = arrayList;
                                        xml = xmlResourceParser;
                                        str = str2;
                                    } catch (Exception e) {
                                        e = e;
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    arrayList = arrayList3;
                                    LogUtils.INSTANCE.logE("DataUtils", "Error parsing XML for package: " + packageName, e);
                                    xmlResourceParser.close();
                                    return arrayList;
                                }
                            } catch (Throwable th) {
                                th = th;
                                xmlResourceParser.close();
                                throw th;
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        arrayList = arrayList2;
                        xmlResourceParser = xml;
                    } catch (Throwable th2) {
                        th = th2;
                        xmlResourceParser = xml;
                        xmlResourceParser.close();
                        throw th;
                    }
                    LogUtils.INSTANCE.logE("DataUtils", "Error parsing XML for package: " + packageName, e);
                    xmlResourceParser.close();
                    return arrayList;
                }
                arrayList = arrayList2;
                xmlResourceParser = xml;
                if (z) {
                    INSTANCE.updateShortcutAvailableProviderFromApplication(context2, packageName, arrayList);
                }
                xmlResourceParser.close();
                return arrayList;
            }
        }
        return arrayList2;
    }

    public final ShortcutEntity getShortcutDataFromJson(String itemData) {
        Intrinsics.checkNotNullParameter(itemData, "itemData");
        try {
            LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "getShortcutDataFromSettings, itemData=" + itemData, null, 4, null);
            return (ShortcutEntity) new GsonBuilder().setPrettyPrinting().create().fromJson(itemData, ShortcutEntity.class);
        } catch (Exception e) {
            LogUtils.INSTANCE.logE("DataUtils", "Error loading shortcut data from settings", e);
            return null;
        }
    }

    public final ShortcutEntity getShortcutDataFromSettings(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        String jsonFromSettings = getJsonFromSettings(context, z);
        if (jsonFromSettings == null || jsonFromSettings.length() == 0) {
            return null;
        }
        return getShortcutDataFromJson(jsonFromSettings);
    }

    public final ShortcutEntity getUNKNOWN_ENTITY() {
        return UNKNOWN_ENTITY;
    }

    public final Object isFashionGalleryShortcutAvailable(Context context, Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new C01782(context, null), continuation);
    }

    public final List jsonToShortcuts(String json) {
        Intrinsics.checkNotNullParameter(json, "json");
        if (json.length() == 0) {
            return CollectionsKt.emptyList();
        }
        try {
            List list = (List) new Gson().fromJson(json, new TypeToken<List<? extends ShortcutEntity>>() { // from class: com.miui.keyguard.shortcuts.utils.DataUtils$jsonToShortcuts$type$1
            });
            return list == null ? CollectionsKt.emptyList() : list;
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "DataUtils", "jsonToShortcuts error: " + e.getMessage() + ", json=" + json, null, 4, null);
            return CollectionsKt.emptyList();
        }
    }

    public final List loadWhiteItems(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ArrayList arrayList = new ArrayList();
        String cloudDataString = MiuiSettings.SettingsCloudData.getCloudDataString(context.getContentResolver(), "miui_keyguard_shortcuts", "white_list_shortcuts", "");
        if (cloudDataString == null || cloudDataString.length() == 0) {
            arrayList.addAll(getDefaultWhiteItems(context));
            return arrayList;
        }
        LogUtils.logI$default(LogUtils.INSTANCE, "DataUtils", "loadWhiteItems from cloud, jsonString = " + cloudDataString, null, 4, null);
        try {
            JSONArray jSONArray = new JSONArray(cloudDataString);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                arrayList.add(buildUniqueTag(jSONObject.getString("packageName"), jSONObject.getString("tag")));
            }
        } catch (Exception e) {
            LogUtils.logE$default(LogUtils.INSTANCE, "DataUtils", "cloud data update : " + e, null, 4, null);
            arrayList.clear();
            arrayList.addAll(getDefaultWhiteItems(context));
        }
        return arrayList;
    }

    public final void saveShortcutDataToSettings(Context context, boolean z, ShortcutEntity shortcutEntity, String reason) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reason, "reason");
        LogUtils.logD$default(LogUtils.INSTANCE, "DataUtils", "saveShortcutDataToSettings, isLeft=" + z + ", data=" + shortcutEntity + ", reason:" + reason, null, 4, null);
        try {
            MiuiSettingsUtils.Secure.putString(context.getContentResolver(), z ? "keyguard_shortcut_left" : "keyguard_shortcut_right", new GsonBuilder().setPrettyPrinting().create().toJson(shortcutEntity));
        } catch (Exception e) {
            LogUtils.INSTANCE.logE("DataUtils", "Error saving shortcut data to settings", e);
        }
    }

    public final Object saveShortcutDataToSettingsAsync(Context context, boolean z, ShortcutEntity shortcutEntity, String str, Continuation continuation) {
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new C01792(context, z, shortcutEntity, str, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    public final void updateShortcutAvailableProviderFromApplication(Context context, String str, List list) {
        if (!list.isEmpty() && Intrinsics.areEqual(str, "com.miui.android.fashiongallery")) {
            boolean shortcutAvailable = getShortcutAvailable(context);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ShortcutEntity shortcutEntity = (ShortcutEntity) it.next();
                if (shortcutEntity.isAvailable() != shortcutAvailable) {
                    shortcutEntity.setAvailable(shortcutAvailable);
                }
            }
        }
    }
}
