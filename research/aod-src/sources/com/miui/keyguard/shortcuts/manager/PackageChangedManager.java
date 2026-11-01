package com.miui.keyguard.shortcuts.manager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.UserHandle;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.PackageUtils;
import com.miui.maml.R;
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
/* JADX INFO: compiled from: PackageChangedManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SuppressLint({"StaticFieldLeak"})
@SourceDebugExtension
public final class PackageChangedManager {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final List packageChangedCallbacks;
    public final PackageChangedReceiver packageChangedReceiver;

    /* JADX INFO: compiled from: PackageChangedManager.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: PackageChangedManager.kt */
    @Metadata
    public interface PackageChangedCallback {

        /* JADX INFO: compiled from: PackageChangedManager.kt */
        @Metadata
        public static final class DefaultImpls {
        }

        void onPackageAdded(String str, List list);

        void onPackageDataCleared(String str);

        void onPackageRemoved(String str);

        void onPackageUpdate(String str, List list);
    }

    /* JADX INFO: compiled from: PackageChangedManager.kt */
    @Metadata
    public final class PackageChangedReceiver extends BroadcastReceiver {
        public PackageChangedReceiver() {
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Uri data;
            String schemeSpecificPart;
            Uri data2;
            String schemeSpecificPart2;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            String action = intent.getAction();
            if (action != null) {
                switch (action.hashCode()) {
                    case -810471698:
                        if (action.equals("android.intent.action.PACKAGE_REPLACED") && (data = intent.getData()) != null && (schemeSpecificPart = data.getSchemeSpecificPart()) != null) {
                            PackageChangedManager.this.handlePackageAddedOrUpdated(context, schemeSpecificPart, true);
                        }
                        break;
                    case 267468725:
                        if (action.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                            PackageChangedManager.this.handlePackageDataCleared(intent);
                            break;
                        }
                        break;
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED") && !PackageChangedManager.this.isReplacing(intent)) {
                            PackageChangedManager.this.handlePackageRemoved(intent);
                        }
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED") && !PackageChangedManager.this.isReplacing(intent) && (data2 = intent.getData()) != null && (schemeSpecificPart2 = data2.getSchemeSpecificPart()) != null) {
                            PackageChangedManager.this.handlePackageAddedOrUpdated(context, schemeSpecificPart2, false);
                        }
                        break;
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.manager.PackageChangedManager$handlePackageAddedOrUpdated$1, reason: invalid class name */
    /* JADX INFO: compiled from: PackageChangedManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.manager.PackageChangedManager$handlePackageAddedOrUpdated$1", f = "PackageChangedManager.kt", l = {R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public final /* synthetic */ boolean $isUpdate;
        public final /* synthetic */ String $packageName;
        public int label;
        public final /* synthetic */ PackageChangedManager this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, String str, Context context, PackageChangedManager packageChangedManager, Continuation continuation) {
            super(2, continuation);
            this.$isUpdate = z;
            this.$packageName = str;
            this.$context = context;
            this.this$0 = packageChangedManager;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$isUpdate, this.$packageName, this.$context, this.this$0, continuation);
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
                LogUtils logUtils = LogUtils.INSTANCE;
                String str = this.$isUpdate ? "update" : "install";
                LogUtils.logD$default(logUtils, "PackageChangedManager", str + " success : " + this.$packageName, null, 4, null);
                ApplicationInfo applicationInfo = PackageUtils.INSTANCE.getApplicationInfo(this.$context, this.$packageName);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    String str2 = this.$packageName;
                    Context context = this.$context;
                    boolean z = this.$isUpdate;
                    PackageChangedManager packageChangedManager = this.this$0;
                    List shortcutDataFromApplication$default = DataUtils.getShortcutDataFromApplication$default(DataUtils.INSTANCE, applicationInfo, str2, context, null, false, 24, null);
                    MainCoroutineDispatcher main = Dispatchers.getMain();
                    PackageChangedManager$handlePackageAddedOrUpdated$1$1$1 packageChangedManager$handlePackageAddedOrUpdated$1$1$1 = new PackageChangedManager$handlePackageAddedOrUpdated$1$1$1(z, packageChangedManager, str2, shortcutDataFromApplication$default, null);
                    this.label = 1;
                    if (BuildersKt.withContext(main, packageChangedManager$handlePackageAddedOrUpdated$1$1$1, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
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

    public PackageChangedManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.packageChangedReceiver = new PackageChangedReceiver();
        this.packageChangedCallbacks = new ArrayList();
        registerReceiver(context);
    }

    public final void addCallback(PackageChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.packageChangedCallbacks.contains(callback)) {
            return;
        }
        this.packageChangedCallbacks.add(callback);
    }

    public final void handlePackageAddedOrUpdated(Context context, String str, boolean z) {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new AnonymousClass1(z, str, context, this, null), 3, null);
    }

    public final void handlePackageDataCleared(Intent intent) {
        String schemeSpecificPart;
        Uri data = intent.getData();
        if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
            return;
        }
        LogUtils.logD$default(LogUtils.INSTANCE, "PackageChangedManager", "clear data success : " + schemeSpecificPart, null, 4, null);
        Iterator it = this.packageChangedCallbacks.iterator();
        while (it.hasNext()) {
            ((PackageChangedCallback) it.next()).onPackageDataCleared(schemeSpecificPart);
        }
    }

    public final void handlePackageRemoved(Intent intent) {
        String schemeSpecificPart;
        Uri data = intent.getData();
        if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
            return;
        }
        LogUtils.logD$default(LogUtils.INSTANCE, "PackageChangedManager", "uninstall success : " + schemeSpecificPart, null, 4, null);
        Iterator it = this.packageChangedCallbacks.iterator();
        while (it.hasNext()) {
            ((PackageChangedCallback) it.next()).onPackageRemoved(schemeSpecificPart);
        }
    }

    public final boolean isReplacing(Intent intent) {
        return intent.getBooleanExtra("android.intent.extra.REPLACING", false);
    }

    public final void registerReceiver(Context context) {
        PackageChangedReceiver packageChangedReceiver = this.packageChangedReceiver;
        UserHandle userHandle = UserHandle.CURRENT;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        Unit unit = Unit.INSTANCE;
        context.registerReceiverAsUser(packageChangedReceiver, userHandle, intentFilter, null, null);
    }

    public final void release() {
        this.context.unregisterReceiver(this.packageChangedReceiver);
    }

    public final void removeCallback(PackageChangedCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.packageChangedCallbacks.remove(callback);
    }
}
