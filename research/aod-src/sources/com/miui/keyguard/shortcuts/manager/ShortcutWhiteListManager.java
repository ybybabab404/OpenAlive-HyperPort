package com.miui.keyguard.shortcuts.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;

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
/* JADX INFO: compiled from: ShortcutWhiteListManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"StaticFieldLeak"})
@SourceDebugExtension
public final class ShortcutWhiteListManager {
    public static final Uri CLOUD_WHITE_LIST_URI;
    public static final Companion Companion = new Companion(null);
    public final CloudWhiteListObserver cloudWhiteListObserver;
    public final Context context;
    public boolean isWhiteListLoaded;
    public final List whiteItems;
    public final List whiteListChangedCallback;

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutWhiteListManager$1, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutWhiteListManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutWhiteListManager$1", f = "ShortcutWhiteListManager.kt", l = {35}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutWhiteListManager$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ShortcutWhiteListManager.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutWhiteListManager$1$1", f = "ShortcutWhiteListManager.kt", l = {}, m = "invokeSuspend")
        public static final class C00421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ List $tempWhiteItems;
            public int label;
            public final /* synthetic */ ShortcutWhiteListManager this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00421(ShortcutWhiteListManager shortcutWhiteListManager, List list, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shortcutWhiteListManager;
                this.$tempWhiteItems = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00421(this.this$0, this.$tempWhiteItems, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.onWhiteListChanged(this.$tempWhiteItems);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutWhiteListManager.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                List listLoadWhiteItems = DataUtils.INSTANCE.loadWhiteItems(ShortcutWhiteListManager.this.getContext());
                MainCoroutineDispatcher main = Dispatchers.getMain();
                C00421 c00421 = new C00421(ShortcutWhiteListManager.this, listLoadWhiteItems, null);
                this.label = 1;
                if (BuildersKt.withContext(main, c00421, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: ShortcutWhiteListManager.kt */
    @Metadata
    public final class CloudWhiteListObserver extends ContentObserver {
        public CloudWhiteListObserver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new ShortcutWhiteListManager$CloudWhiteListObserver$onChange$1(ShortcutWhiteListManager.this, null), 3, null);
        }
    }

    /* JADX INFO: compiled from: ShortcutWhiteListManager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: ShortcutWhiteListManager.kt */
    @Metadata
    public interface WhiteListChangedCallback {

        /* JADX INFO: compiled from: ShortcutWhiteListManager.kt */
        @Metadata
        public static final class DefaultImpls {
        }

        void onWhiteListChanged(List list);
    }

    static {
        Uri uri = Uri.parse("content://com.android.settings.cloud.CloudSettings/cloud_all_data/notify");
        Intrinsics.checkNotNullExpressionValue(uri, "parse(...)");
        CLOUD_WHITE_LIST_URI = uri;
    }

    public ShortcutWhiteListManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.whiteItems = new ArrayList();
        this.cloudWhiteListObserver = new CloudWhiteListObserver();
        this.whiteListChangedCallback = new ArrayList();
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new AnonymousClass1(null), 3, null);
        registerReceiver(context);
    }

    public final void addCallback(WhiteListChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.whiteListChangedCallback.contains(callback)) {
            return;
        }
        this.whiteListChangedCallback.add(callback);
    }

    public final Context getContext() {
        return this.context;
    }

    public final List getWhiteList() {
        return this.whiteItems;
    }

    public final boolean isWhiteListLoaded() {
        return this.isWhiteListLoaded;
    }

    public final void onWhiteListChanged(List list) {
        this.isWhiteListLoaded = true;
        LogUtils.logD$default(LogUtils.INSTANCE, "ShortcutWhiteListManager", "onWhiteListChanged size: " + list.size(), null, 4, null);
        this.whiteItems.clear();
        this.whiteItems.addAll(list);
        Iterator it = this.whiteListChangedCallback.iterator();
        while (it.hasNext()) {
            ((WhiteListChangedCallback) it.next()).onWhiteListChanged(list);
        }
    }

    public final void registerReceiver(Context context) {
        context.getContentResolver().registerContentObserver(CLOUD_WHITE_LIST_URI, false, this.cloudWhiteListObserver);
    }

    public final void release() {
        this.context.getContentResolver().unregisterContentObserver(this.cloudWhiteListObserver);
    }

    public final void removeCallback(WhiteListChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.whiteListChangedCallback.remove(callback);
    }
}
