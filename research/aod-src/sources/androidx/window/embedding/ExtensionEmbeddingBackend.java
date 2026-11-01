package androidx.window.embedding;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.collection.ArraySet;
import androidx.core.util.Consumer;
import androidx.window.core.BuildConfig;
import androidx.window.core.ConsumerAdapter;
import androidx.window.core.ExtensionsUtil;
import androidx.window.core.PredicateAdapter;
import androidx.window.core.VerificationMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

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
/* JADX INFO: compiled from: ExtensionEmbeddingBackend.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class ExtensionEmbeddingBackend implements EmbeddingBackend {
    public static volatile ExtensionEmbeddingBackend globalInstance;
    public final Context applicationContext;
    public EmbeddingInterfaceCompat embeddingExtension;
    public final RuleTracker ruleTracker;
    public final CopyOnWriteArrayList splitChangeCallbacks;
    public final EmbeddingCallbackImpl splitInfoEmbeddingCallback;
    public final Lazy splitSupportStatus$delegate;
    public static final Companion Companion = new Companion(null);
    public static final ReentrantLock globalLock = new ReentrantLock();

    /* JADX INFO: compiled from: ExtensionEmbeddingBackend.kt */
    @RequiresApi
    @Metadata
    public static final class Api31Impl {
        public static final Api31Impl INSTANCE = new Api31Impl();

        @DoNotInline
        @NotNull
        public final SplitController.SplitSupportStatus isSplitPropertyEnabled(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            try {
                PackageManager.Property property = context.getPackageManager().getProperty("android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED", context.getPackageName());
                Intrinsics.checkNotNullExpressionValue(property, "try {\n                co…OT_DECLARED\n            }");
                if (property.isBoolean()) {
                    return property.getBoolean() ? SplitController.SplitSupportStatus.SPLIT_AVAILABLE : SplitController.SplitSupportStatus.SPLIT_UNAVAILABLE;
                }
                if (BuildConfig.INSTANCE.getVerificationMode() == VerificationMode.LOG) {
                    Log.w("EmbeddingBackend", "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED must have a boolean value");
                }
                return SplitController.SplitSupportStatus.SPLIT_ERROR_PROPERTY_NOT_DECLARED;
            } catch (PackageManager.NameNotFoundException unused) {
                if (BuildConfig.INSTANCE.getVerificationMode() == VerificationMode.LOG) {
                    Log.w("EmbeddingBackend", "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED must be set and enabled in AndroidManifest.xml to use splits APIs.");
                }
                return SplitController.SplitSupportStatus.SPLIT_ERROR_PROPERTY_NOT_DECLARED;
            } catch (Exception e) {
                if (BuildConfig.INSTANCE.getVerificationMode() == VerificationMode.LOG) {
                    Log.e("EmbeddingBackend", "PackageManager.getProperty is not supported", e);
                }
                return SplitController.SplitSupportStatus.SPLIT_ERROR_PROPERTY_NOT_DECLARED;
            }
        }
    }

    /* JADX INFO: compiled from: ExtensionEmbeddingBackend.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final EmbeddingBackend getInstance(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (ExtensionEmbeddingBackend.globalInstance == null) {
                ReentrantLock reentrantLock = ExtensionEmbeddingBackend.globalLock;
                reentrantLock.lock();
                try {
                    if (ExtensionEmbeddingBackend.globalInstance == null) {
                        Context applicationContext = context.getApplicationContext();
                        Companion companion = ExtensionEmbeddingBackend.Companion;
                        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
                        ExtensionEmbeddingBackend.globalInstance = new ExtensionEmbeddingBackend(applicationContext, companion.initAndVerifyEmbeddingExtension(applicationContext));
                    }
                    Unit unit = Unit.INSTANCE;
                    reentrantLock.unlock();
                } catch (Throwable th) {
                    reentrantLock.unlock();
                    throw th;
                }
            }
            ExtensionEmbeddingBackend extensionEmbeddingBackend = ExtensionEmbeddingBackend.globalInstance;
            Intrinsics.checkNotNull(extensionEmbeddingBackend);
            return extensionEmbeddingBackend;
        }

        public final EmbeddingInterfaceCompat initAndVerifyEmbeddingExtension(Context context) {
            ClassLoader classLoader;
            EmbeddingCompat embeddingCompat = null;
            try {
                if (isExtensionVersionSupported(Integer.valueOf(ExtensionsUtil.INSTANCE.getSafeVendorApiLevel()))) {
                    EmbeddingCompat.Companion companion = EmbeddingCompat.Companion;
                    if (companion.isEmbeddingAvailable() && (classLoader = EmbeddingBackend.class.getClassLoader()) != null) {
                        embeddingCompat = new EmbeddingCompat(companion.embeddingComponent(), new EmbeddingAdapter(new PredicateAdapter(classLoader)), new ConsumerAdapter(classLoader), context);
                    }
                }
            } catch (Throwable th) {
                Log.d("EmbeddingBackend", "Failed to load embedding extension: " + th);
            }
            if (embeddingCompat == null) {
                Log.d("EmbeddingBackend", "No supported embedding extension found");
            }
            return embeddingCompat;
        }

        public final boolean isExtensionVersionSupported(Integer num) {
            return num != null && num.intValue() >= 1;
        }
    }

    /* JADX INFO: compiled from: ExtensionEmbeddingBackend.kt */
    @Metadata
    public final class EmbeddingCallbackImpl implements EmbeddingInterfaceCompat.EmbeddingCallbackInterface {
        public List lastInfo;

        public EmbeddingCallbackImpl() {
        }

        public final List getLastInfo() {
            return this.lastInfo;
        }

        @Override // androidx.window.embedding.EmbeddingInterfaceCompat.EmbeddingCallbackInterface
        public void onSplitInfoChanged(List splitInfo) {
            Intrinsics.checkNotNullParameter(splitInfo, "splitInfo");
            this.lastInfo = splitInfo;
            Iterator it = ExtensionEmbeddingBackend.this.getSplitChangeCallbacks().iterator();
            while (it.hasNext()) {
                ((SplitListenerWrapper) it.next()).accept(splitInfo);
            }
        }
    }

    /* JADX INFO: compiled from: ExtensionEmbeddingBackend.kt */
    @Metadata
    @SourceDebugExtension
    public static final class RuleTracker {
        public final ArraySet splitRules = new ArraySet();
        public final HashMap tagRuleMap = new HashMap();

        public static /* synthetic */ void addOrUpdateRule$default(RuleTracker ruleTracker, EmbeddingRule embeddingRule, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            ruleTracker.addOrUpdateRule(embeddingRule, z);
        }

        public final void addOrUpdateRule(EmbeddingRule rule, boolean z) {
            Intrinsics.checkNotNullParameter(rule, "rule");
            if (this.splitRules.contains(rule)) {
                return;
            }
            String tag = rule.getTag();
            if (tag == null) {
                this.splitRules.add(rule);
                return;
            }
            if (!this.tagRuleMap.containsKey(tag)) {
                this.tagRuleMap.put(tag, rule);
                this.splitRules.add(rule);
            } else {
                if (z) {
                    throw new IllegalArgumentException("Duplicated tag: " + tag + ". Tag must be unique among all registered rules");
                }
                this.splitRules.remove((EmbeddingRule) this.tagRuleMap.get(tag));
                this.tagRuleMap.put(tag, rule);
                this.splitRules.add(rule);
            }
        }

        public final boolean contains(EmbeddingRule rule) {
            Intrinsics.checkNotNullParameter(rule, "rule");
            return this.splitRules.contains(rule);
        }

        public final ArraySet getSplitRules() {
            return this.splitRules;
        }
    }

    /* JADX INFO: compiled from: ExtensionEmbeddingBackend.kt */
    @Metadata
    @SourceDebugExtension
    public static final class SplitListenerWrapper {
        public final Activity activity;
        public final Consumer callback;
        public final Executor executor;
        public List lastValue;

        public SplitListenerWrapper(Activity activity, Executor executor, Consumer callback) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(executor, "executor");
            Intrinsics.checkNotNullParameter(callback, "callback");
            this.activity = activity;
            this.executor = executor;
            this.callback = callback;
        }

        public static final void accept$lambda$1(SplitListenerWrapper this$0, List splitsWithActivity) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(splitsWithActivity, "$splitsWithActivity");
            this$0.callback.accept(splitsWithActivity);
        }

        public final void accept(List splitInfoList) {
            Intrinsics.checkNotNullParameter(splitInfoList, "splitInfoList");
            final ArrayList arrayList = new ArrayList();
            for (Object obj : splitInfoList) {
                if (((SplitInfo) obj).contains(this.activity)) {
                    arrayList.add(obj);
                }
            }
            if (Intrinsics.areEqual(arrayList, this.lastValue)) {
                return;
            }
            this.lastValue = arrayList;
            this.executor.execute(new Runnable() { // from class: androidx.window.embedding.ExtensionEmbeddingBackend$SplitListenerWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ExtensionEmbeddingBackend.SplitListenerWrapper.accept$lambda$1(this.f$0, arrayList);
                }
            });
        }

        public final Consumer getCallback() {
            return this.callback;
        }
    }

    public ExtensionEmbeddingBackend(Context applicationContext, EmbeddingInterfaceCompat embeddingInterfaceCompat) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        this.applicationContext = applicationContext;
        this.embeddingExtension = embeddingInterfaceCompat;
        EmbeddingCallbackImpl embeddingCallbackImpl = new EmbeddingCallbackImpl();
        this.splitInfoEmbeddingCallback = embeddingCallbackImpl;
        this.splitChangeCallbacks = new CopyOnWriteArrayList();
        EmbeddingInterfaceCompat embeddingInterfaceCompat2 = this.embeddingExtension;
        if (embeddingInterfaceCompat2 != null) {
            embeddingInterfaceCompat2.setEmbeddingCallback(embeddingCallbackImpl);
        }
        this.ruleTracker = new RuleTracker();
        this.splitSupportStatus$delegate = LazyKt.lazy(new Function0<SplitController.SplitSupportStatus>() { // from class: androidx.window.embedding.ExtensionEmbeddingBackend$splitSupportStatus$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SplitController.SplitSupportStatus invoke() {
                return !this.this$0.areExtensionsAvailable() ? SplitController.SplitSupportStatus.SPLIT_UNAVAILABLE : ExtensionEmbeddingBackend.Api31Impl.INSTANCE.isSplitPropertyEnabled(this.this$0.applicationContext);
            }
        });
    }

    @Override // androidx.window.embedding.EmbeddingBackend
    public void addRule(EmbeddingRule rule) {
        Intrinsics.checkNotNullParameter(rule, "rule");
        ReentrantLock reentrantLock = globalLock;
        reentrantLock.lock();
        try {
            if (!this.ruleTracker.contains(rule)) {
                RuleTracker.addOrUpdateRule$default(this.ruleTracker, rule, false, 2, null);
                EmbeddingInterfaceCompat embeddingInterfaceCompat = this.embeddingExtension;
                if (embeddingInterfaceCompat != null) {
                    embeddingInterfaceCompat.setRules(getRules());
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // androidx.window.embedding.EmbeddingBackend
    public void addSplitListenerForActivity(Activity activity, Executor executor, Consumer callback) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(executor, "executor");
        Intrinsics.checkNotNullParameter(callback, "callback");
        ReentrantLock reentrantLock = globalLock;
        reentrantLock.lock();
        try {
            if (this.embeddingExtension == null) {
                Log.v("EmbeddingBackend", "Extension not loaded, skipping callback registration.");
                callback.accept(CollectionsKt.emptyList());
                return;
            }
            SplitListenerWrapper splitListenerWrapper = new SplitListenerWrapper(activity, executor, callback);
            this.splitChangeCallbacks.add(splitListenerWrapper);
            if (this.splitInfoEmbeddingCallback.getLastInfo() != null) {
                List lastInfo = this.splitInfoEmbeddingCallback.getLastInfo();
                Intrinsics.checkNotNull(lastInfo);
                splitListenerWrapper.accept(lastInfo);
            } else {
                splitListenerWrapper.accept(CollectionsKt.emptyList());
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }

    public final boolean areExtensionsAvailable() {
        return this.embeddingExtension != null;
    }

    public Set getRules() {
        ReentrantLock reentrantLock = globalLock;
        reentrantLock.lock();
        try {
            return CollectionsKt.toSet(this.ruleTracker.getSplitRules());
        } finally {
            reentrantLock.unlock();
        }
    }

    public final CopyOnWriteArrayList getSplitChangeCallbacks() {
        return this.splitChangeCallbacks;
    }

    @Override // androidx.window.embedding.EmbeddingBackend
    public SplitController.SplitSupportStatus getSplitSupportStatus() {
        return (SplitController.SplitSupportStatus) this.splitSupportStatus$delegate.getValue();
    }

    @Override // androidx.window.embedding.EmbeddingBackend
    public boolean isActivityEmbedded(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        EmbeddingInterfaceCompat embeddingInterfaceCompat = this.embeddingExtension;
        if (embeddingInterfaceCompat != null) {
            return embeddingInterfaceCompat.isActivityEmbedded(activity);
        }
        return false;
    }

    @Override // androidx.window.embedding.EmbeddingBackend
    public void removeSplitListenerForActivity(Consumer consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        ReentrantLock reentrantLock = globalLock;
        reentrantLock.lock();
        try {
            for (SplitListenerWrapper splitListenerWrapper : this.splitChangeCallbacks) {
                if (Intrinsics.areEqual(splitListenerWrapper.getCallback(), consumer)) {
                    this.splitChangeCallbacks.remove(splitListenerWrapper);
                    break;
                }
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            reentrantLock.unlock();
        }
    }
}
