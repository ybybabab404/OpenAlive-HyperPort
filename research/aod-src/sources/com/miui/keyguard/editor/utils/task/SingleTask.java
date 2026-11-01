package com.miui.keyguard.editor.utils.task;

import android.util.Log;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes2.dex */
public class SingleTask<T> extends Task {
    public Supplier supplier;

    /* JADX INFO: renamed from: $r8$lambda$YRZ7twu9ISKQB4j-Hf7t4S-qRqY, reason: not valid java name */
    public static /* synthetic */ void m913$r8$lambda$YRZ7twu9ISKQB4jHf7t4SqRqY(Consumer consumer, Object obj, Throwable th) {
        if (th != null) {
            Log.e("Task.SingleTask", "post", th);
        } else {
            consumer.accept(obj);
        }
    }

    public SingleTask(Supplier supplier) {
        this.supplier = supplier;
    }

    public void post(BiConsumer biConsumer) {
        CompletableFuture.supplyAsync(this.supplier, this.executor).whenCompleteAsync(biConsumer, this.mainExecutor);
    }

    public void post(final Consumer consumer) {
        post(new BiConsumer() { // from class: com.miui.keyguard.editor.utils.task.SingleTask$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SingleTask.m913$r8$lambda$YRZ7twu9ISKQB4jHf7t4SqRqY(consumer, obj, (Throwable) obj2);
            }
        });
    }
}
