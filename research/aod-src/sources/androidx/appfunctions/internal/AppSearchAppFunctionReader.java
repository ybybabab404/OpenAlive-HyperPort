package androidx.appfunctions.internal;

import android.content.Context;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionMetadata;
import androidx.appfunctions.metadata.AppFunctionMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionParameterMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionResponseMetadata;
import androidx.appfunctions.metadata.AppFunctionResponseMetadataDocument;
import androidx.appfunctions.metadata.AppFunctionRuntimeMetadata;
import androidx.appfunctions.metadata.AppFunctionSchemaMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import androidx.appsearch.app.AppSearchBatchResult;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.app.GetByDocumentIdRequest;
import androidx.appsearch.app.GlobalSearchSession;
import androidx.appsearch.app.SearchResult;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.observer.DocumentChangeInfo;
import androidx.appsearch.observer.ObserverCallback;
import androidx.appsearch.observer.ObserverSpec;
import androidx.appsearch.observer.SchemaChangeInfo;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.guava.ListenableFutureKt;

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
/* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
@SourceDebugExtension
public final class AppSearchAppFunctionReader implements AppFunctionReader {
    public static final Companion Companion = new Companion(null);
    public static final long OBSERVER_DEBOUNCE_MILLIS;
    public static final SearchSpec RUNTIME_SEARCH_SPEC;
    public final Context context;
    public final SchemaAppFunctionInventory schemaAppFunctionInventory;

    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata
    public static final class AppSearchChannelObserver implements ObserverCallback {
        public final Channel updateChannel = ChannelKt.Channel$default(0, null, null, 6, null);

        public final void close() {
            SendChannel.DefaultImpls.close$default(this.updateChannel, null, 1, null);
        }

        public final Flow observe() {
            return FlowKt.receiveAsFlow(this.updateChannel);
        }

        @Override // androidx.appsearch.observer.ObserverCallback
        public void onDocumentChanged(DocumentChangeInfo changeInfo) {
            Intrinsics.checkNotNullParameter(changeInfo, "changeInfo");
            this.updateChannel.mo1228trySendJP2dKIU(Unit.INSTANCE);
        }

        @Override // androidx.appsearch.observer.ObserverCallback
        public void onSchemaChanged(SchemaChangeInfo changeInfo) {
            Intrinsics.checkNotNullParameter(changeInfo, "changeInfo");
            this.updateChannel.mo1228trySendJP2dKIU(Unit.INSTANCE);
        }
    }

    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getOBSERVER_DEBOUNCE_MILLIS-UwyO8pc, reason: not valid java name */
        public final long m15getOBSERVER_DEBOUNCE_MILLISUwyO8pc() {
            return AppSearchAppFunctionReader.OBSERVER_DEBOUNCE_MILLIS;
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$getAppFunctionMetadata$1, reason: invalid class name */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader", f = "AppSearchAppFunctionReader.kt", l = {355, 367, 377, 403}, m = "getAppFunctionMetadata")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$10;
        public Object L$2;
        public Object L$3;
        public Object L$4;
        public Object L$5;
        public Object L$6;
        public Object L$7;
        public Object L$8;
        public Object L$9;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchAppFunctionReader.this.getAppFunctionMetadata(null, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$performSearch$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader", f = "AppSearchAppFunctionReader.kt", l = {173, 180}, m = "performSearch")
    public static final class C00681 extends ContinuationImpl {
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public C00681(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchAppFunctionReader.this.performSearch(null, null, this);
        }
    }

    /* JADX INFO: renamed from: androidx.appfunctions.internal.AppSearchAppFunctionReader$searchTopLevelComponent$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AppSearchAppFunctionReader.kt */
    @Metadata
    @DebugMetadata(c = "androidx.appfunctions.internal.AppSearchAppFunctionReader", f = "AppSearchAppFunctionReader.kt", l = {213}, m = "searchTopLevelComponent")
    public static final class C00691 extends ContinuationImpl {
        public Object L$0;
        public int label;
        public /* synthetic */ Object result;

        public C00691(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppSearchAppFunctionReader.this.searchTopLevelComponent(null, null, this);
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        OBSERVER_DEBOUNCE_MILLIS = DurationKt.toDuration(1, DurationUnit.SECONDS);
        SearchSpec searchSpecBuild = new SearchSpec.Builder().addFilterNamespaces("app_functions_runtime").addFilterDocumentClasses(AppFunctionRuntimeMetadata.class).addFilterPackageNames("android").setVerbatimSearchEnabled(true).build();
        Intrinsics.checkNotNullExpressionValue(searchSpecBuild, "build(...)");
        RUNTIME_SEARCH_SPEC = searchSpecBuild;
    }

    public AppSearchAppFunctionReader(Context context, SchemaAppFunctionInventory schemaAppFunctionInventory) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.schemaAppFunctionInventory = schemaAppFunctionInventory;
    }

    public static final AppFunctionMetadata performSearch$lambda$1(AppSearchAppFunctionReader appSearchAppFunctionReader, Map map, SearchResult searchResult) {
        Intrinsics.checkNotNullParameter(searchResult, "searchResult");
        return appSearchAppFunctionReader.convertSearchResultToAppFunctionMetadata(searchResult, map);
    }

    public static final Unit searchTopLevelComponent$lambda$5(AppSearchAppFunctionReader appSearchAppFunctionReader, Map map, SearchResult searchResult) {
        Intrinsics.checkNotNullParameter(searchResult, "searchResult");
        appSearchAppFunctionReader.extractAppFunctionComponentsMetadataFromSearchResult(searchResult, map);
        return Unit.INSTANCE;
    }

    public final ObserverSpec buildObserverSpec(Set set) {
        ObserverSpec.Builder builder = new ObserverSpec.Builder();
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            CollectionsKt.addAll(arrayList, CollectionsKt.listOf((Object[]) new String[]{"AppFunctionStaticMetadata-" + str, "AppFunctionRuntimeMetadata-" + str, "AppFunctionComponentMetadataDocument-" + str}));
        }
        ObserverSpec observerSpecBuild = builder.addFilterSchemas(arrayList).build();
        Intrinsics.checkNotNullExpressionValue(observerSpecBuild, "build(...)");
        return observerSpecBuild;
    }

    public final AppFunctionSchemaMetadata buildSchemaMetadataFromGdForLegacyIndexer(GenericDocument genericDocument) {
        String propertyString = genericDocument.getPropertyString("schemaName");
        String propertyString2 = genericDocument.getPropertyString("schemaCategory");
        long propertyLong = genericDocument.getPropertyLong("schemaVersion");
        if (propertyString != null && propertyString2 != null && propertyLong != 0) {
            return new AppFunctionSchemaMetadata(propertyString2, propertyString, propertyLong);
        }
        if (propertyString == null && propertyString2 == null && propertyLong == 0) {
            return null;
        }
        Log.e("AppFunctions", "Unexpected state: schemaName=" + propertyString + ", schemaCategory=" + propertyString2 + ", schemaVersion=" + propertyLong);
        return null;
    }

    public final boolean computeEffectivelyEnabled(AppFunctionMetadataDocument appFunctionMetadataDocument, AppFunctionRuntimeMetadata appFunctionRuntimeMetadata) {
        int enabled = (int) appFunctionRuntimeMetadata.getEnabled();
        if (enabled == 0) {
            return appFunctionMetadataDocument.isEnabledByDefault();
        }
        if (enabled == 1) {
            return true;
        }
        if (enabled == 2) {
            return false;
        }
        throw new IllegalStateException("Unknown AppFunction state: " + appFunctionRuntimeMetadata.getEnabled() + '.');
    }

    public final AppFunctionMetadata convertSearchResultToAppFunctionMetadata(SearchResult searchResult, Map map) {
        Object documentClass;
        Object documentClass2;
        AppFunctionResponseMetadata appFunctionResponseMetadata;
        AppFunctionComponentsMetadata appFunctionComponentsMetadata;
        String propertyString = searchResult.getGenericDocument().getPropertyString("functionId");
        if (propertyString == null) {
            throw new IllegalStateException("Required value was null.");
        }
        String propertyString2 = searchResult.getGenericDocument().getPropertyString("packageName");
        if (propertyString2 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        GenericDocument genericDocument = searchResult.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument, "getGenericDocument(...)");
        try {
            documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
        } catch (Exception e) {
            Log.e("AppFunctions", "Failed to convert search result " + genericDocument.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionMetadataDocument.class).getSimpleName(), e);
            documentClass = null;
        }
        AppFunctionMetadataDocument appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
        if (appFunctionMetadataDocument == null) {
            return null;
        }
        List joinedResults = searchResult.getJoinedResults();
        Intrinsics.checkNotNullExpressionValue(joinedResults, "getJoinedResults(...)");
        SearchResult searchResult2 = (SearchResult) CollectionsKt.singleOrNull(joinedResults);
        if (searchResult2 == null) {
            Log.e("AppFunctions", "Runtime metadata not found for " + appFunctionMetadataDocument.getId());
            return null;
        }
        GenericDocument genericDocument2 = searchResult2.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument2, "getGenericDocument(...)");
        try {
            documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
        } catch (Exception e2) {
            Log.e("AppFunctions", "Failed to convert search result " + genericDocument2.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionRuntimeMetadata.class).getSimpleName(), e2);
            documentClass2 = null;
        }
        AppFunctionRuntimeMetadata appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
        if (appFunctionRuntimeMetadata == null) {
            return null;
        }
        GenericDocument genericDocument3 = searchResult.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument3, "getGenericDocument(...)");
        AppFunctionSchemaMetadata appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer = buildSchemaMetadataFromGdForLegacyIndexer(genericDocument3);
        List appFunctionParameterMetadata = getAppFunctionParameterMetadata(appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer);
        if (appFunctionParameterMetadata == null || (appFunctionResponseMetadata = getAppFunctionResponseMetadata(appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer)) == null || (appFunctionComponentsMetadata = getAppFunctionComponentsMetadata(propertyString2, appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer, map)) == null) {
            return null;
        }
        boolean zComputeEffectivelyEnabled = computeEffectivelyEnabled(appFunctionMetadataDocument, appFunctionRuntimeMetadata);
        String description = appFunctionMetadataDocument.getDescription();
        if (description == null) {
            description = "";
        }
        return new AppFunctionMetadata(propertyString, propertyString2, zComputeEffectivelyEnabled, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer, appFunctionParameterMetadata, appFunctionResponseMetadata, appFunctionComponentsMetadata, description);
    }

    public final void extractAppFunctionComponentsMetadataFromSearchResult(SearchResult searchResult, Map map) {
        Object documentClass;
        AppFunctionComponentsMetadata appFunctionComponentsMetadata;
        String propertyString = searchResult.getGenericDocument().getPropertyString("packageName");
        if (propertyString == null) {
            throw new IllegalStateException("Required value was null.");
        }
        GenericDocument genericDocument = searchResult.getGenericDocument();
        Intrinsics.checkNotNullExpressionValue(genericDocument, "getGenericDocument(...)");
        try {
            documentClass = genericDocument.toDocumentClass(AppFunctionComponentsMetadataDocument.class);
        } catch (Exception e) {
            Log.e("AppFunctions", "Failed to convert search result " + genericDocument.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionComponentsMetadataDocument.class).getSimpleName(), e);
            documentClass = null;
        }
        AppFunctionComponentsMetadataDocument appFunctionComponentsMetadataDocument = (AppFunctionComponentsMetadataDocument) documentClass;
        if (appFunctionComponentsMetadataDocument == null || (appFunctionComponentsMetadata = appFunctionComponentsMetadataDocument.toAppFunctionComponentsMetadata()) == null || appFunctionComponentsMetadata.getDataTypes().isEmpty()) {
            return;
        }
        map.put(propertyString, appFunctionComponentsMetadata);
    }

    public final AppFunctionComponentsMetadata getAppFunctionComponentsMetadata(String str, AppFunctionMetadataDocument appFunctionMetadataDocument, AppFunctionSchemaMetadata appFunctionSchemaMetadata, Map map) {
        SchemaAppFunctionInventory schemaAppFunctionInventory;
        if (isAppFunctionMetadataDocumentFromDynamicIndexer(appFunctionMetadataDocument)) {
            AppFunctionComponentsMetadata appFunctionComponentsMetadata = (AppFunctionComponentsMetadata) map.get(str);
            return appFunctionComponentsMetadata == null ? new AppFunctionComponentsMetadata(null, 1, null) : appFunctionComponentsMetadata;
        }
        if (appFunctionSchemaMetadata == null || (schemaAppFunctionInventory = this.schemaAppFunctionInventory) == null) {
            return null;
        }
        return schemaAppFunctionInventory.getComponentsMetadata();
    }

    public final String getAppFunctionId(String str, String str2) {
        return str + '/' + str2;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x014f  */
    /* JADX WARN: Code duplicated, block: B:34:0x0166  */
    /* JADX WARN: Code duplicated, block: B:42:0x01a4 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:49:0x01d6 A[ADDED_TO_REGION, RETURN] */
    /* JADX WARN: Code duplicated, block: B:50:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:59:0x020f  */
    /* JADX WARN: Code duplicated, block: B:62:0x0229 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:63:0x022a  */
    /* JADX WARN: Code duplicated, block: B:65:0x023a  */
    /* JADX WARN: Code duplicated, block: B:67:0x0255  */
    /* JADX WARN: Code duplicated, block: B:69:0x01a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:0x0173 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:7:0x0021  */
    /* JADX WARN: Instruction removed from duplicated block: B:65:0x023a, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:67:0x0255, please report this as an issue */
    @Override // androidx.appfunctions.internal.AppFunctionReader
    public Object getAppFunctionMetadata(String str, String str2, Continuation continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        String str3;
        String str4;
        String appFunctionId;
        GlobalSearchSession globalSearchSession;
        AppSearchBatchResult appSearchBatchResult;
        Object objAwait;
        String str5;
        GlobalSearchSession globalSearchSession2;
        String str6;
        AppSearchBatchResult appSearchBatchResult2;
        AppSearchBatchResult appSearchBatchResult3;
        GenericDocument genericDocument;
        GenericDocument genericDocument2;
        Object documentClass;
        AppFunctionMetadataDocument appFunctionMetadataDocument;
        Object documentClass2;
        AppFunctionRuntimeMetadata appFunctionRuntimeMetadata;
        AppSearchAppFunctionReader appSearchAppFunctionReader;
        AppFunctionSchemaMetadata appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
        List appFunctionParameterMetadata;
        AppFunctionResponseMetadata appFunctionResponseMetadata;
        Object objSearchTopLevelComponent;
        AppFunctionSchemaMetadata appFunctionSchemaMetadata;
        List list;
        AppSearchAppFunctionReader appSearchAppFunctionReader2;
        AppFunctionResponseMetadata appFunctionResponseMetadata2;
        AppFunctionMetadataDocument appFunctionMetadataDocument2;
        AppFunctionMetadataDocument appFunctionMetadataDocument3;
        String str7;
        String str8;
        AppFunctionSchemaMetadata appFunctionSchemaMetadata2;
        String str9;
        AppFunctionComponentsMetadata appFunctionComponentsMetadata;
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
        Object objCreateSearchSession = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objCreateSearchSession);
            Context context = this.context;
            anonymousClass1.L$0 = str;
            anonymousClass1.L$1 = str2;
            anonymousClass1.label = 1;
            objCreateSearchSession = AppSearchUtilsKt.createSearchSession(context, anonymousClass1);
            if (objCreateSearchSession != coroutine_suspended) {
                str3 = str;
                str4 = str2;
            }
            return coroutine_suspended;
        }
        if (i2 == 1) {
            str4 = (String) anonymousClass1.L$1;
            str3 = (String) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objCreateSearchSession);
        } else {
            if (i2 == 2) {
                String str10 = (String) anonymousClass1.L$3;
                globalSearchSession = (GlobalSearchSession) anonymousClass1.L$2;
                String str11 = (String) anonymousClass1.L$1;
                str3 = (String) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objCreateSearchSession);
                appFunctionId = str10;
                str4 = str11;
                appSearchBatchResult = (AppSearchBatchResult) objCreateSearchSession;
                ListenableFuture byDocumentIdAsync = globalSearchSession.getByDocumentIdAsync("android", "appfunctions-db", new GetByDocumentIdRequest.Builder("app_functions_runtime").addIds(appFunctionId).build());
                Intrinsics.checkNotNullExpressionValue(byDocumentIdAsync, "getByDocumentIdAsync(...)");
                anonymousClass1.L$0 = str3;
                anonymousClass1.L$1 = str4;
                anonymousClass1.L$2 = globalSearchSession;
                anonymousClass1.L$3 = appFunctionId;
                anonymousClass1.L$4 = appSearchBatchResult;
                anonymousClass1.label = 3;
                objAwait = ListenableFutureKt.await(byDocumentIdAsync, anonymousClass1);
                if (objAwait != coroutine_suspended) {
                    str5 = str4;
                    globalSearchSession2 = globalSearchSession;
                    str6 = appFunctionId;
                    appSearchBatchResult2 = appSearchBatchResult;
                    objCreateSearchSession = objAwait;
                    appSearchBatchResult3 = (AppSearchBatchResult) objCreateSearchSession;
                    genericDocument = (GenericDocument) appSearchBatchResult2.getSuccesses().get(str6);
                    if (genericDocument == null) {
                        throw new AppFunctionFunctionNotFoundException("Function " + str3 + " is not available under " + str5);
                    }
                    genericDocument2 = (GenericDocument) appSearchBatchResult3.getSuccesses().get(str6);
                    if (genericDocument2 == null) {
                        throw new AppFunctionFunctionNotFoundException("Function " + str3 + " is not available under " + str5);
                    }
                    documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
                    appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
                    if (appFunctionMetadataDocument == null) {
                        return null;
                    }
                    documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
                    appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
                    if (appFunctionRuntimeMetadata != null) {
                        return null;
                    }
                    Set of = SetsKt.setOf(str5);
                    anonymousClass1.L$0 = str3;
                    anonymousClass1.L$1 = str5;
                    anonymousClass1.L$2 = appFunctionMetadataDocument;
                    anonymousClass1.L$3 = appFunctionRuntimeMetadata;
                    anonymousClass1.L$4 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    anonymousClass1.L$5 = appFunctionParameterMetadata;
                    anonymousClass1.L$6 = appFunctionResponseMetadata;
                    anonymousClass1.L$7 = appSearchAppFunctionReader;
                    anonymousClass1.L$8 = str5;
                    anonymousClass1.L$9 = appFunctionMetadataDocument;
                    anonymousClass1.L$10 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    anonymousClass1.label = 4;
                    objSearchTopLevelComponent = appSearchAppFunctionReader.searchTopLevelComponent(globalSearchSession2, of, anonymousClass1);
                    if (objSearchTopLevelComponent != coroutine_suspended) {
                        appFunctionSchemaMetadata = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                        list = appFunctionParameterMetadata;
                        appSearchAppFunctionReader2 = appSearchAppFunctionReader;
                        appFunctionResponseMetadata2 = appFunctionResponseMetadata;
                        appFunctionMetadataDocument2 = appFunctionMetadataDocument;
                        appFunctionMetadataDocument3 = appFunctionMetadataDocument2;
                        str7 = str3;
                        str8 = str5;
                        appFunctionSchemaMetadata2 = appFunctionSchemaMetadata;
                        objCreateSearchSession = objSearchTopLevelComponent;
                        str9 = str8;
                    }
                }
                return coroutine_suspended;
            }
            if (i2 == 3) {
                appSearchBatchResult2 = (AppSearchBatchResult) anonymousClass1.L$4;
                str6 = (String) anonymousClass1.L$3;
                globalSearchSession2 = (GlobalSearchSession) anonymousClass1.L$2;
                str5 = (String) anonymousClass1.L$1;
                String str12 = (String) anonymousClass1.L$0;
                ResultKt.throwOnFailure(objCreateSearchSession);
                str3 = str12;
                appSearchBatchResult3 = (AppSearchBatchResult) objCreateSearchSession;
                genericDocument = (GenericDocument) appSearchBatchResult2.getSuccesses().get(str6);
                if (genericDocument == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str3 + " is not available under " + str5);
                }
                genericDocument2 = (GenericDocument) appSearchBatchResult3.getSuccesses().get(str6);
                if (genericDocument2 == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str3 + " is not available under " + str5);
                }
                try {
                    documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
                } catch (Exception e) {
                    Log.e("AppFunctions", "Failed to convert search result " + genericDocument.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionMetadataDocument.class).getSimpleName(), e);
                    documentClass = null;
                }
                appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
                if (appFunctionMetadataDocument == null) {
                    return null;
                }
                try {
                    documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
                } catch (Exception e2) {
                    Log.e("AppFunctions", "Failed to convert search result " + genericDocument2.getId() + " to " + Reflection.getOrCreateKotlinClass(AppFunctionRuntimeMetadata.class).getSimpleName(), e2);
                    documentClass2 = null;
                }
                appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
                if (appFunctionRuntimeMetadata != null || (appFunctionParameterMetadata = appSearchAppFunctionReader.getAppFunctionParameterMetadata(appFunctionMetadataDocument, (appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer = (appSearchAppFunctionReader = this).buildSchemaMetadataFromGdForLegacyIndexer(genericDocument)))) == null || (appFunctionResponseMetadata = appSearchAppFunctionReader.getAppFunctionResponseMetadata(appFunctionMetadataDocument, appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer)) == null) {
                    return null;
                }
                Set of2 = SetsKt.setOf(str5);
                anonymousClass1.L$0 = str3;
                anonymousClass1.L$1 = str5;
                anonymousClass1.L$2 = appFunctionMetadataDocument;
                anonymousClass1.L$3 = appFunctionRuntimeMetadata;
                anonymousClass1.L$4 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.L$5 = appFunctionParameterMetadata;
                anonymousClass1.L$6 = appFunctionResponseMetadata;
                anonymousClass1.L$7 = appSearchAppFunctionReader;
                anonymousClass1.L$8 = str5;
                anonymousClass1.L$9 = appFunctionMetadataDocument;
                anonymousClass1.L$10 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.label = 4;
                objSearchTopLevelComponent = appSearchAppFunctionReader.searchTopLevelComponent(globalSearchSession2, of2, anonymousClass1);
                if (objSearchTopLevelComponent != coroutine_suspended) {
                    appFunctionSchemaMetadata = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    list = appFunctionParameterMetadata;
                    appSearchAppFunctionReader2 = appSearchAppFunctionReader;
                    appFunctionResponseMetadata2 = appFunctionResponseMetadata;
                    appFunctionMetadataDocument2 = appFunctionMetadataDocument;
                    appFunctionMetadataDocument3 = appFunctionMetadataDocument2;
                    str7 = str3;
                    str8 = str5;
                    appFunctionSchemaMetadata2 = appFunctionSchemaMetadata;
                    objCreateSearchSession = objSearchTopLevelComponent;
                    str9 = str8;
                }
                return coroutine_suspended;
            }
            if (i2 != 4) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            AppFunctionSchemaMetadata appFunctionSchemaMetadata3 = (AppFunctionSchemaMetadata) anonymousClass1.L$10;
            appFunctionMetadataDocument2 = (AppFunctionMetadataDocument) anonymousClass1.L$9;
            str9 = (String) anonymousClass1.L$8;
            appSearchAppFunctionReader2 = (AppSearchAppFunctionReader) anonymousClass1.L$7;
            AppFunctionResponseMetadata appFunctionResponseMetadata3 = (AppFunctionResponseMetadata) anonymousClass1.L$6;
            List list2 = (List) anonymousClass1.L$5;
            AppFunctionSchemaMetadata appFunctionSchemaMetadata4 = (AppFunctionSchemaMetadata) anonymousClass1.L$4;
            appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) anonymousClass1.L$3;
            appFunctionMetadataDocument3 = (AppFunctionMetadataDocument) anonymousClass1.L$2;
            String str13 = (String) anonymousClass1.L$1;
            String str14 = (String) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objCreateSearchSession);
            appSearchAppFunctionReader = this;
            appFunctionSchemaMetadata2 = appFunctionSchemaMetadata3;
            appFunctionResponseMetadata2 = appFunctionResponseMetadata3;
            str7 = str14;
            list = list2;
            appFunctionSchemaMetadata = appFunctionSchemaMetadata4;
            str8 = str13;
        }
        appFunctionComponentsMetadata = appSearchAppFunctionReader2.getAppFunctionComponentsMetadata(str9, appFunctionMetadataDocument2, appFunctionSchemaMetadata2, (Map) objCreateSearchSession);
        if (appFunctionComponentsMetadata == null) {
            return null;
        }
        return new AppFunctionMetadata(str7, str8, appSearchAppFunctionReader.computeEffectivelyEnabled(appFunctionMetadataDocument3, appFunctionRuntimeMetadata), appFunctionSchemaMetadata, list, appFunctionResponseMetadata2, appFunctionComponentsMetadata, null, 128, null);
        GlobalSearchSession globalSearchSession3 = (GlobalSearchSession) objCreateSearchSession;
        appFunctionId = getAppFunctionId(str4, str3);
        ListenableFuture byDocumentIdAsync2 = globalSearchSession3.getByDocumentIdAsync("android", "apps-db", new GetByDocumentIdRequest.Builder("app_functions").addIds(appFunctionId).build());
        Intrinsics.checkNotNullExpressionValue(byDocumentIdAsync2, "getByDocumentIdAsync(...)");
        anonymousClass1.L$0 = str3;
        anonymousClass1.L$1 = str4;
        anonymousClass1.L$2 = globalSearchSession3;
        anonymousClass1.L$3 = appFunctionId;
        anonymousClass1.label = 2;
        Object objAwait2 = ListenableFutureKt.await(byDocumentIdAsync2, anonymousClass1);
        if (objAwait2 != coroutine_suspended) {
            globalSearchSession = globalSearchSession3;
            objCreateSearchSession = objAwait2;
            appSearchBatchResult = (AppSearchBatchResult) objCreateSearchSession;
            ListenableFuture byDocumentIdAsync3 = globalSearchSession.getByDocumentIdAsync("android", "appfunctions-db", new GetByDocumentIdRequest.Builder("app_functions_runtime").addIds(appFunctionId).build());
            Intrinsics.checkNotNullExpressionValue(byDocumentIdAsync3, "getByDocumentIdAsync(...)");
            anonymousClass1.L$0 = str3;
            anonymousClass1.L$1 = str4;
            anonymousClass1.L$2 = globalSearchSession;
            anonymousClass1.L$3 = appFunctionId;
            anonymousClass1.L$4 = appSearchBatchResult;
            anonymousClass1.label = 3;
            objAwait = ListenableFutureKt.await(byDocumentIdAsync3, anonymousClass1);
            if (objAwait != coroutine_suspended) {
                str5 = str4;
                globalSearchSession2 = globalSearchSession;
                str6 = appFunctionId;
                appSearchBatchResult2 = appSearchBatchResult;
                objCreateSearchSession = objAwait;
                appSearchBatchResult3 = (AppSearchBatchResult) objCreateSearchSession;
                genericDocument = (GenericDocument) appSearchBatchResult2.getSuccesses().get(str6);
                if (genericDocument == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str3 + " is not available under " + str5);
                }
                genericDocument2 = (GenericDocument) appSearchBatchResult3.getSuccesses().get(str6);
                if (genericDocument2 == null) {
                    throw new AppFunctionFunctionNotFoundException("Function " + str3 + " is not available under " + str5);
                }
                documentClass = genericDocument.toDocumentClass(AppFunctionMetadataDocument.class);
                appFunctionMetadataDocument = (AppFunctionMetadataDocument) documentClass;
                if (appFunctionMetadataDocument == null) {
                    return null;
                }
                documentClass2 = genericDocument2.toDocumentClass(AppFunctionRuntimeMetadata.class);
                appFunctionRuntimeMetadata = (AppFunctionRuntimeMetadata) documentClass2;
                if (appFunctionRuntimeMetadata != null) {
                    return null;
                }
                Set of3 = SetsKt.setOf(str5);
                anonymousClass1.L$0 = str3;
                anonymousClass1.L$1 = str5;
                anonymousClass1.L$2 = appFunctionMetadataDocument;
                anonymousClass1.L$3 = appFunctionRuntimeMetadata;
                anonymousClass1.L$4 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.L$5 = appFunctionParameterMetadata;
                anonymousClass1.L$6 = appFunctionResponseMetadata;
                anonymousClass1.L$7 = appSearchAppFunctionReader;
                anonymousClass1.L$8 = str5;
                anonymousClass1.L$9 = appFunctionMetadataDocument;
                anonymousClass1.L$10 = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                anonymousClass1.label = 4;
                objSearchTopLevelComponent = appSearchAppFunctionReader.searchTopLevelComponent(globalSearchSession2, of3, anonymousClass1);
                if (objSearchTopLevelComponent != coroutine_suspended) {
                    appFunctionSchemaMetadata = appFunctionSchemaMetadataBuildSchemaMetadataFromGdForLegacyIndexer;
                    list = appFunctionParameterMetadata;
                    appSearchAppFunctionReader2 = appSearchAppFunctionReader;
                    appFunctionResponseMetadata2 = appFunctionResponseMetadata;
                    appFunctionMetadataDocument2 = appFunctionMetadataDocument;
                    appFunctionMetadataDocument3 = appFunctionMetadataDocument2;
                    str7 = str3;
                    str8 = str5;
                    appFunctionSchemaMetadata2 = appFunctionSchemaMetadata;
                    objCreateSearchSession = objSearchTopLevelComponent;
                    str9 = str8;
                    appFunctionComponentsMetadata = appSearchAppFunctionReader2.getAppFunctionComponentsMetadata(str9, appFunctionMetadataDocument2, appFunctionSchemaMetadata2, (Map) objCreateSearchSession);
                    if (appFunctionComponentsMetadata == null) {
                        return null;
                    }
                    return new AppFunctionMetadata(str7, str8, appSearchAppFunctionReader.computeEffectivelyEnabled(appFunctionMetadataDocument3, appFunctionRuntimeMetadata), appFunctionSchemaMetadata, list, appFunctionResponseMetadata2, appFunctionComponentsMetadata, null, 128, null);
                }
            }
        }
        return coroutine_suspended;
    }

    public final List getAppFunctionParameterMetadata(AppFunctionMetadataDocument appFunctionMetadataDocument, AppFunctionSchemaMetadata appFunctionSchemaMetadata) {
        SchemaAppFunctionInventory schemaAppFunctionInventory;
        Map<AppFunctionSchemaMetadata, CompileTimeAppFunctionMetadata> schemaFunctionsMap;
        CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata;
        if (!isAppFunctionMetadataDocumentFromDynamicIndexer(appFunctionMetadataDocument)) {
            if (appFunctionSchemaMetadata == null || (schemaAppFunctionInventory = this.schemaAppFunctionInventory) == null || (schemaFunctionsMap = schemaAppFunctionInventory.getSchemaFunctionsMap()) == null || (compileTimeAppFunctionMetadata = schemaFunctionsMap.get(appFunctionSchemaMetadata)) == null) {
                return null;
            }
            return compileTimeAppFunctionMetadata.getParameters();
        }
        List parameters = appFunctionMetadataDocument.getParameters();
        if (parameters == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(parameters, 10));
        Iterator it = parameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppFunctionParameterMetadataDocument) it.next()).toAppFunctionParameterMetadata());
        }
        return arrayList;
    }

    public final AppFunctionResponseMetadata getAppFunctionResponseMetadata(AppFunctionMetadataDocument appFunctionMetadataDocument, AppFunctionSchemaMetadata appFunctionSchemaMetadata) {
        SchemaAppFunctionInventory schemaAppFunctionInventory;
        Map<AppFunctionSchemaMetadata, CompileTimeAppFunctionMetadata> schemaFunctionsMap;
        CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata;
        if (isAppFunctionMetadataDocumentFromDynamicIndexer(appFunctionMetadataDocument)) {
            AppFunctionResponseMetadataDocument response = appFunctionMetadataDocument.getResponse();
            if (response != null) {
                return response.toAppFunctionResponseMetadata();
            }
            throw new IllegalStateException("Required value was null.");
        }
        if (appFunctionSchemaMetadata == null || (schemaAppFunctionInventory = this.schemaAppFunctionInventory) == null || (schemaFunctionsMap = schemaAppFunctionInventory.getSchemaFunctionsMap()) == null || (compileTimeAppFunctionMetadata = schemaFunctionsMap.get(appFunctionSchemaMetadata)) == null) {
            return null;
        }
        return compileTimeAppFunctionMetadata.getResponse();
    }

    public final boolean isAppFunctionMetadataDocumentFromDynamicIndexer(AppFunctionMetadataDocument appFunctionMetadataDocument) {
        return appFunctionMetadataDocument.getResponse() != null;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00d2, code lost:
    
        if (r11 == r1) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object performSearch(androidx.appsearch.app.GlobalSearchSession r9, androidx.appfunctions.AppFunctionSearchSpec r10, kotlin.coroutines.Continuation r11) {
        /*
            Method dump skipped, instruction units count: 316
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appfunctions.internal.AppSearchAppFunctionReader.performSearch(androidx.appsearch.app.GlobalSearchSession, androidx.appfunctions.AppFunctionSearchSpec, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public final Object searchTopLevelComponent(GlobalSearchSession globalSearchSession, Set set, Continuation continuation) {
        C00691 c00691;
        if (continuation instanceof C00691) {
            c00691 = (C00691) continuation;
            int i = c00691.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c00691.label = i - Integer.MIN_VALUE;
            } else {
                c00691 = new C00691(continuation);
            }
        } else {
            c00691 = new C00691(continuation);
        }
        Object obj = c00691.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = c00691.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Map map = (Map) c00691.L$0;
            ResultKt.throwOnFailure(obj);
            return map;
        }
        ResultKt.throwOnFailure(obj);
        SearchSpec.Builder builderAddFilterNamespaces = new SearchSpec.Builder().addFilterNamespaces("app_functions");
        if (set == null) {
            set = SetsKt.emptySet();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, CollectionsKt.listOf("AppFunctionComponentMetadataDocument-" + ((String) it.next())));
        }
        SearchSpec searchSpecBuild = builderAddFilterNamespaces.addFilterSchemas(arrayList).addFilterPackageNames("android").setVerbatimSearchEnabled(true).setNumericSearchEnabled(true).setListFilterQueryLanguageEnabled(true).build();
        Intrinsics.checkNotNullExpressionValue(searchSpecBuild, "build(...)");
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        SearchResults searchResultsSearch = globalSearchSession.search("", searchSpecBuild);
        Intrinsics.checkNotNullExpressionValue(searchResultsSearch, "search(...)");
        Function1 function1 = new Function1() { // from class: androidx.appfunctions.internal.AppSearchAppFunctionReader$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return AppSearchAppFunctionReader.searchTopLevelComponent$lambda$5(this.f$0, linkedHashMap, (SearchResult) obj2);
            }
        };
        c00691.L$0 = linkedHashMap;
        c00691.label = 1;
        return AppSearchUtilsKt.readAll(searchResultsSearch, function1, c00691) == coroutine_suspended ? coroutine_suspended : linkedHashMap;
    }
}
