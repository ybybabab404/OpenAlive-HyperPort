package com.miui.keyguard.shortcuts.manager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.miui.keyguard.shortcuts.data.ShortcutEntity;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.xiaomi.onetrack.api.am;
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
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: ShortcutOverlayRepositoryImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ShortcutOverlayRepositoryImpl implements ShortcutOverlayRepository {
    public final Context context;
    public static final Companion Companion = new Companion(null);
    public static final Uri CONTENT_URI = Uri.parse("content://com.android.keyguard.shortcut.MiuiShortcutProvider");

    /* JADX INFO: compiled from: ShortcutOverlayRepositoryImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Uri getCONTENT_URI() {
            return ShortcutOverlayRepositoryImpl.CONTENT_URI;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl$deleteInner$2, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutOverlayRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl$deleteInner$2", f = "ShortcutOverlayRepositoryImpl.kt", l = {}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        public final /* synthetic */ String $packageName;
        public /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ ShortcutOverlayRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, ShortcutOverlayRepositoryImpl shortcutOverlayRepositoryImpl, Continuation continuation) {
            super(2, continuation);
            this.$packageName = str;
            this.this$0 = shortcutOverlayRepositoryImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$packageName, this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:14:0x0045  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Bundle bundle;
            Object objM1061constructorimpl;
            boolean z;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            String str = this.$packageName;
            if (str != null) {
                bundle = new Bundle();
                bundle.putString("package", str);
            } else {
                bundle = null;
            }
            ShortcutOverlayRepositoryImpl shortcutOverlayRepositoryImpl = this.this$0;
            try {
                Result.Companion companion = Result.Companion;
                Bundle bundleCall = shortcutOverlayRepositoryImpl.context.getContentResolver().call(ShortcutOverlayRepositoryImpl.Companion.getCONTENT_URI(), "DELETE_OVERLAYS", (String) null, bundle);
                if (bundleCall != null) {
                    z = true;
                    if (!bundleCall.getBoolean("success")) {
                        z = false;
                    }
                } else {
                    z = false;
                }
                objM1061constructorimpl = Result.m1061constructorimpl(Boxing.boxBoolean(z));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
            String str2 = this.$packageName;
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl == null) {
                return objM1061constructorimpl;
            }
            LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutOverlayRepositoryImpl", "deleteShortcuts " + str2 + " error, " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
            return Boxing.boxBoolean(false);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl$getShortcut$1, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutOverlayRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl", f = "ShortcutOverlayRepositoryImpl.kt", l = {45}, m = "getShortcut")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ShortcutOverlayRepositoryImpl.this.getShortcut(null, null, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl$getShortcutsInner$4, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutOverlayRepositoryImpl.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepositoryImpl$getShortcutsInner$4", f = "ShortcutOverlayRepositoryImpl.kt", l = {}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends ShortcutEntity>>, Object> {
        public final /* synthetic */ Bundle $bundle;
        public final /* synthetic */ String $packageName;
        public final /* synthetic */ String $tag;
        public /* synthetic */ Object L$0;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(Bundle bundle, String str, String str2, Continuation continuation) {
            super(2, continuation);
            this.$bundle = bundle;
            this.$packageName = str;
            this.$tag = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = ShortcutOverlayRepositoryImpl.this.new AnonymousClass4(this.$bundle, this.$packageName, this.$tag, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object objM1061constructorimpl;
            List listEmptyList;
            String string;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ShortcutOverlayRepositoryImpl shortcutOverlayRepositoryImpl = ShortcutOverlayRepositoryImpl.this;
            Bundle bundle = this.$bundle;
            try {
                Result.Companion companion = Result.Companion;
                Bundle bundleCall = shortcutOverlayRepositoryImpl.context.getContentResolver().call(ShortcutOverlayRepositoryImpl.Companion.getCONTENT_URI(), "GET_OVERLAYS", (String) null, bundle);
                if (bundleCall == null || (string = bundleCall.getString(am.O)) == null) {
                    listEmptyList = CollectionsKt.emptyList();
                } else {
                    listEmptyList = DataUtils.INSTANCE.jsonToShortcuts(string);
                    Iterator it = listEmptyList.iterator();
                    while (it.hasNext()) {
                        ((ShortcutEntity) it.next()).setOverlay(true);
                    }
                }
                objM1061constructorimpl = Result.m1061constructorimpl(listEmptyList);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
            String str = this.$packageName;
            String str2 = this.$tag;
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl == null) {
                return objM1061constructorimpl;
            }
            LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutOverlayRepositoryImpl", "getShortcutsInner " + str + "-" + str2 + " error, " + thM1063exceptionOrNullimpl.getMessage(), null, 4, null);
            return CollectionsKt.emptyList();
        }
    }

    public ShortcutOverlayRepositoryImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final Object deleteInner(String str, Continuation continuation) {
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(str, this, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    public Object deleteShortcuts(String str, Continuation continuation) {
        Object objDeleteInner = deleteInner(str, continuation);
        return objDeleteInner == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDeleteInner : Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0013  */
    public Object getShortcut(String str, String str2, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
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
        Object shortcutsInner = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(shortcutsInner);
            if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
                LogUtils.logE$default(LogUtils.INSTANCE, "ShortcutOverlayRepositoryImpl", "getShortcut " + str + "-" + str2 + " error, packageName or tag is empty", null, 4, null);
                return null;
            }
            anonymousClass1.label = 1;
            shortcutsInner = getShortcutsInner(str, str2, anonymousClass1);
            if (shortcutsInner == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(shortcutsInner);
        }
        List list = (List) shortcutsInner;
        if (list.isEmpty()) {
            return null;
        }
        return (ShortcutEntity) CollectionsKt.first(list);
    }

    @Override // com.miui.keyguard.shortcuts.manager.ShortcutOverlayRepository
    public Object getShortcuts(String str, Continuation continuation) {
        return getShortcutsInner(str, null, continuation);
    }

    public final Object getShortcutsInner(String str, String str2, Continuation continuation) {
        Bundle bundle = new Bundle();
        if (str != null) {
            bundle.putString("package", str);
        }
        if (str2 != null) {
            bundle.putString("tag", str2);
        }
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass4(bundle, str, str2, null), continuation);
    }
}
