package androidx.appsearch.app;

import com.google.common.util.concurrent.ListenableFuture;
import java.io.Closeable;

/* JADX INFO: loaded from: classes.dex */
public interface SearchResults extends Closeable {
    ListenableFuture getNextPageAsync();
}
