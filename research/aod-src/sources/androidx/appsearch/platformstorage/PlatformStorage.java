package androidx.appsearch.platformstorage;

import android.app.appsearch.AppSearchManager;
import android.app.appsearch.AppSearchResult;
import android.app.appsearch.EnterpriseGlobalSearchSession;
import android.app.appsearch.GlobalSearchSession;
import android.content.Context;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.appsearch.app.AppSearchEnvironmentFactory;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi
public final class PlatformStorage {
    public static final Executor EXECUTOR = AppSearchEnvironmentFactory.getEnvironmentInstance().createCachedThreadPoolExecutor();

    @RequiresApi
    public static class ApiHelperForV {
        @DoNotInline
        public static void createEnterpriseGlobalSearchSession(AppSearchManager appSearchManager, Executor executor, Consumer<AppSearchResult<EnterpriseGlobalSearchSession>> consumer) {
            appSearchManager.createEnterpriseGlobalSearchSession(executor, consumer);
        }
    }

    public static final class GlobalSearchContext {
        public final Context mContext;
        public final Executor mExecutor;

        public static final class Builder {
            public final Context mContext;
            public Executor mExecutor;

            public Builder(Context context) {
                this.mContext = (Context) Preconditions.checkNotNull(context);
            }

            public GlobalSearchContext build() {
                if (this.mExecutor == null) {
                    this.mExecutor = PlatformStorage.EXECUTOR;
                }
                return new GlobalSearchContext(this.mContext, this.mExecutor);
            }

            public Builder setWorkerExecutor(Executor executor) {
                Preconditions.checkNotNull(executor);
                this.mExecutor = executor;
                return this;
            }
        }

        public GlobalSearchContext(Context context, Executor executor) {
            this.mContext = (Context) Preconditions.checkNotNull(context);
            this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        }
    }

    public static final class SearchContext {

        public static final class Builder {
        }
    }

    public static /* synthetic */ void $r8$lambda$HofHyZ714NL3fyf61NPv29UCSe0(ResolvableFuture resolvableFuture, GlobalSearchContext globalSearchContext, AppSearchResult appSearchResult) {
        if (appSearchResult.isSuccess()) {
            resolvableFuture.set(new GlobalSearchSessionImpl((GlobalSearchSession) appSearchResult.getResultValue(), globalSearchContext.mExecutor, globalSearchContext.mContext));
        } else {
            resolvableFuture.setException(new AppSearchException(appSearchResult.getResultCode(), appSearchResult.getErrorMessage()));
        }
    }

    public static ListenableFuture createGlobalSearchSessionAsync(final GlobalSearchContext globalSearchContext) {
        Preconditions.checkNotNull(globalSearchContext);
        AppSearchManager appSearchManager = (AppSearchManager) globalSearchContext.mContext.getSystemService(AppSearchManager.class);
        final ResolvableFuture resolvableFutureCreate = ResolvableFuture.create();
        appSearchManager.createGlobalSearchSession(globalSearchContext.mExecutor, new Consumer() { // from class: androidx.appsearch.platformstorage.PlatformStorage$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PlatformStorage.$r8$lambda$HofHyZ714NL3fyf61NPv29UCSe0(resolvableFutureCreate, globalSearchContext, (AppSearchResult) obj);
            }
        });
        return resolvableFutureCreate;
    }
}
