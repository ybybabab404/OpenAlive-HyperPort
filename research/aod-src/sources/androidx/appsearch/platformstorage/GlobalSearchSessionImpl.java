package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.BatchResultCallback;
import android.app.appsearch.GenericDocument;
import android.app.appsearch.GetByDocumentIdRequest;
import android.app.appsearch.GetSchemaResponse;
import android.app.appsearch.exceptions.AppSearchException;
import android.app.appsearch.observer.DocumentChangeInfo;
import android.app.appsearch.observer.ObserverCallback;
import android.app.appsearch.observer.ObserverSpec;
import android.app.appsearch.observer.SchemaChangeInfo;
import android.content.Context;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appsearch.app.Features;
import androidx.appsearch.app.GlobalSearchSession;
import androidx.appsearch.app.SearchResults;
import androidx.appsearch.app.SearchSpec;
import androidx.appsearch.platformstorage.converter.GenericDocumentToPlatformConverter;
import androidx.appsearch.platformstorage.converter.ObserverSpecToPlatformConverter;
import androidx.appsearch.platformstorage.converter.RequestToPlatformConverter;
import androidx.appsearch.platformstorage.converter.SearchSpecToPlatformConverter;
import androidx.appsearch.platformstorage.util.BatchResultCallbackAdapter;
import androidx.collection.ArrayMap;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@RestrictTo
class GlobalSearchSessionImpl implements GlobalSearchSession {
    public final Context mContext;
    public final Executor mExecutor;
    public final Features mFeatures;
    public final Map mObserverCallbacksLocked = new ArrayMap();
    public final android.app.appsearch.GlobalSearchSession mPlatformSession;

    @RequiresApi
    public static class ApiHelperForT {
        @DoNotInline
        public static void getByDocumentId(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, String str2, GetByDocumentIdRequest getByDocumentIdRequest, Executor executor, BatchResultCallback<String, GenericDocument> batchResultCallback) {
            globalSearchSession.getByDocumentId(str, str2, getByDocumentIdRequest, executor, batchResultCallback);
        }

        @DoNotInline
        public static void getSchema(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, String str2, Executor executor, Consumer<AppSearchResult<GetSchemaResponse>> consumer) {
            globalSearchSession.getSchema(str, str2, executor, consumer);
        }

        @DoNotInline
        public static void registerObserverCallback(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, ObserverSpec observerSpec, Executor executor, ObserverCallback observerCallback) throws AppSearchException {
            globalSearchSession.registerObserverCallback(str, observerSpec, executor, observerCallback);
        }

        @DoNotInline
        public static void unregisterObserverCallback(android.app.appsearch.GlobalSearchSession globalSearchSession, String str, ObserverCallback observerCallback) throws AppSearchException {
            globalSearchSession.unregisterObserverCallback(str, observerCallback);
        }
    }

    public GlobalSearchSessionImpl(android.app.appsearch.GlobalSearchSession globalSearchSession, Executor executor, Context context) {
        this.mPlatformSession = (android.app.appsearch.GlobalSearchSession) Preconditions.checkNotNull(globalSearchSession);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        Context context2 = (Context) Preconditions.checkNotNull(context);
        this.mContext = context2;
        this.mFeatures = new FeaturesImpl(context2);
    }

    @Override // androidx.appsearch.app.GlobalSearchSession, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mPlatformSession.close();
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public ListenableFuture getByDocumentIdAsync(String str, String str2, androidx.appsearch.app.GetByDocumentIdRequest getByDocumentIdRequest) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(getByDocumentIdRequest);
        ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        ApiHelperForT.getByDocumentId(this.mPlatformSession, str, str2, RequestToPlatformConverter.toPlatformGetByDocumentIdRequest(getByDocumentIdRequest), this.mExecutor, new BatchResultCallbackAdapter(resolvableFutureCreate, new Function() { // from class: androidx.appsearch.platformstorage.GlobalSearchSessionImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return GenericDocumentToPlatformConverter.toJetpackGenericDocument((GenericDocument) obj);
            }
        }));
        return resolvableFutureCreate;
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public void registerObserverCallback(String str, androidx.appsearch.observer.ObserverSpec observerSpec, Executor executor, final androidx.appsearch.observer.ObserverCallback observerCallback) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(observerSpec);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(observerCallback);
        synchronized (this.mObserverCallbacksLocked) {
            ObserverCallback observerCallback2 = (ObserverCallback) this.mObserverCallbacksLocked.get(observerCallback);
            if (observerCallback2 == null) {
                observerCallback2 = new ObserverCallback() { // from class: androidx.appsearch.platformstorage.GlobalSearchSessionImpl.1
                    @Override // android.app.appsearch.observer.ObserverCallback
                    public void onDocumentChanged(DocumentChangeInfo documentChangeInfo) {
                        observerCallback.onDocumentChanged(ObserverSpecToPlatformConverter.toJetpackDocumentChangeInfo(documentChangeInfo));
                    }

                    @Override // android.app.appsearch.observer.ObserverCallback
                    public void onSchemaChanged(SchemaChangeInfo schemaChangeInfo) {
                        observerCallback.onSchemaChanged(ObserverSpecToPlatformConverter.toJetpackSchemaChangeInfo(schemaChangeInfo));
                    }
                };
            }
            try {
                ApiHelperForT.registerObserverCallback(this.mPlatformSession, str, ObserverSpecToPlatformConverter.toPlatformObserverSpec(observerSpec), executor, observerCallback2);
                this.mObserverCallbacksLocked.put(observerCallback, observerCallback2);
            } catch (AppSearchException e) {
                throw new androidx.appsearch.exceptions.AppSearchException(e.getResultCode(), e.getMessage(), e.getCause());
            }
        }
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public SearchResults search(String str, SearchSpec searchSpec) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(searchSpec);
        return new SearchResultsImpl(this.mPlatformSession.search(str, SearchSpecToPlatformConverter.toPlatformSearchSpec(this.mContext, searchSpec)), searchSpec, this.mExecutor, this.mContext);
    }

    @Override // androidx.appsearch.app.GlobalSearchSession
    public void unregisterObserverCallback(String str, androidx.appsearch.observer.ObserverCallback observerCallback) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(observerCallback);
        synchronized (this.mObserverCallbacksLocked) {
            ObserverCallback observerCallback2 = (ObserverCallback) this.mObserverCallbacksLocked.get(observerCallback);
            if (observerCallback2 == null) {
                return;
            }
            try {
                ApiHelperForT.unregisterObserverCallback(this.mPlatformSession, str, observerCallback2);
                this.mObserverCallbacksLocked.remove(observerCallback);
            } catch (AppSearchException e) {
                throw new androidx.appsearch.exceptions.AppSearchException(e.getResultCode(), e.getMessage(), e.getCause());
            }
        }
    }
}
