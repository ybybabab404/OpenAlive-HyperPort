package androidx.window.embedding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.WindowMetrics;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.PredicateAdapter;
import androidx.window.extensions.core.util.function.Predicate;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: EmbeddingAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class EmbeddingAdapter {
    public final VendorApiLevel1Impl api1Impl;
    public final VendorApiLevel2Impl api2Impl;
    public final PredicateAdapter predicateAdapter;
    public static final Companion Companion = new Companion(null);
    public static final Binder INVALID_SPLIT_INFO_TOKEN = new Binder();
    public static final Binder INVALID_ACTIVITY_STACK_TOKEN = new Binder();

    /* JADX INFO: compiled from: EmbeddingAdapter.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Binder getINVALID_SPLIT_INFO_TOKEN() {
            return EmbeddingAdapter.INVALID_SPLIT_INFO_TOKEN;
        }
    }

    /* JADX INFO: compiled from: EmbeddingAdapter.kt */
    @Metadata
    public final class VendorApiLevel1Impl {
        public final PredicateAdapter predicateAdapter;
        public final /* synthetic */ EmbeddingAdapter this$0;

        public VendorApiLevel1Impl(EmbeddingAdapter embeddingAdapter, PredicateAdapter predicateAdapter) {
            Intrinsics.checkNotNullParameter(predicateAdapter, "predicateAdapter");
            this.this$0 = embeddingAdapter;
            this.predicateAdapter = predicateAdapter;
        }

        public final SplitAttributes getSplitAttributesCompat(androidx.window.extensions.embedding.SplitInfo splitInfo) {
            Intrinsics.checkNotNullParameter(splitInfo, "splitInfo");
            return new SplitAttributes.Builder().setSplitType(SplitAttributes.SplitType.Companion.buildSplitTypeFromValue$window_release(splitInfo.getSplitRatio())).setLayoutDirection(SplitAttributes.LayoutDirection.LOCALE).build();
        }

        public final boolean isSplitAttributesSupported(SplitAttributes splitAttributes) {
            double value$window_release = splitAttributes.getSplitType().getValue$window_release();
            return BaseStyleSelectView.COLLAPSE_SLIDE_VALUE <= value$window_release && value$window_release <= 1.0d && splitAttributes.getSplitType().getValue$window_release() != 1.0f && ArraysKt.contains(new SplitAttributes.LayoutDirection[]{SplitAttributes.LayoutDirection.LEFT_TO_RIGHT, SplitAttributes.LayoutDirection.RIGHT_TO_LEFT, SplitAttributes.LayoutDirection.LOCALE}, splitAttributes.getLayoutDirection());
        }

        public final androidx.window.extensions.embedding.SplitPairRule.Builder setDefaultSplitAttributesCompat(androidx.window.extensions.embedding.SplitPairRule.Builder builder, SplitAttributes splitAttributes) {
            Pair pairTranslateSplitAttributesCompatInternal = translateSplitAttributesCompatInternal(splitAttributes);
            float fFloatValue = ((Number) pairTranslateSplitAttributesCompatInternal.component1()).floatValue();
            int iIntValue = ((Number) pairTranslateSplitAttributesCompatInternal.component2()).intValue();
            builder.setSplitRatio(fFloatValue);
            builder.setLayoutDirection(iIntValue);
            return builder;
        }

        public final androidx.window.extensions.embedding.SplitPlaceholderRule.Builder setDefaultSplitAttributesCompat(androidx.window.extensions.embedding.SplitPlaceholderRule.Builder builder, SplitAttributes splitAttributes) {
            Pair pairTranslateSplitAttributesCompatInternal = translateSplitAttributesCompatInternal(splitAttributes);
            float fFloatValue = ((Number) pairTranslateSplitAttributesCompatInternal.component1()).floatValue();
            int iIntValue = ((Number) pairTranslateSplitAttributesCompatInternal.component2()).intValue();
            builder.setSplitRatio(fFloatValue);
            builder.setLayoutDirection(iIntValue);
            return builder;
        }

        public final Object translateActivityIntentPredicates(final Set set) {
            return this.predicateAdapter.buildPairPredicate(Reflection.getOrCreateKotlinClass(Activity.class), Reflection.getOrCreateKotlinClass(Intent.class), new Function2<Activity, Intent, Boolean>() { // from class: androidx.window.embedding.EmbeddingAdapter$VendorApiLevel1Impl$translateActivityIntentPredicates$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(Activity first, Intent second) {
                    Intrinsics.checkNotNullParameter(first, "first");
                    Intrinsics.checkNotNullParameter(second, "second");
                    Set set2 = set;
                    boolean z = false;
                    if (set2 == null || !set2.isEmpty()) {
                        Iterator it = set2.iterator();
                        while (it.hasNext()) {
                            if (((SplitPairFilter) it.next()).matchesActivityIntentPair(first, second)) {
                                z = true;
                                break;
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
        }

        public final Object translateActivityPairPredicates(final Set set) {
            return this.predicateAdapter.buildPairPredicate(Reflection.getOrCreateKotlinClass(Activity.class), Reflection.getOrCreateKotlinClass(Activity.class), new Function2<Activity, Activity, Boolean>() { // from class: androidx.window.embedding.EmbeddingAdapter$VendorApiLevel1Impl$translateActivityPairPredicates$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Boolean invoke(Activity first, Activity second) {
                    Intrinsics.checkNotNullParameter(first, "first");
                    Intrinsics.checkNotNullParameter(second, "second");
                    Set set2 = set;
                    boolean z = false;
                    if (set2 == null || !set2.isEmpty()) {
                        Iterator it = set2.iterator();
                        while (it.hasNext()) {
                            if (((SplitPairFilter) it.next()).matchesActivityPair(first, second)) {
                                z = true;
                                break;
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
        }

        public final Object translateActivityPredicates(final Set set) {
            return this.predicateAdapter.buildPredicate(Reflection.getOrCreateKotlinClass(Activity.class), new Function1<Activity, Boolean>() { // from class: androidx.window.embedding.EmbeddingAdapter$VendorApiLevel1Impl$translateActivityPredicates$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Activity activity) {
                    Intrinsics.checkNotNullParameter(activity, "activity");
                    Set set2 = set;
                    boolean z = false;
                    if (set2 == null || !set2.isEmpty()) {
                        Iterator it = set2.iterator();
                        while (it.hasNext()) {
                            if (((ActivityFilter) it.next()).matchesActivity(activity)) {
                                z = true;
                                break;
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
        }

        public final androidx.window.extensions.embedding.ActivityRule translateActivityRuleCompat(ActivityRule rule, Class predicateClass) {
            Intrinsics.checkNotNullParameter(rule, "rule");
            Intrinsics.checkNotNullParameter(predicateClass, "predicateClass");
            androidx.window.extensions.embedding.ActivityRule activityRuleBuild = ((androidx.window.extensions.embedding.ActivityRule.Builder) androidx.window.extensions.embedding.ActivityRule.Builder.class.getConstructor(predicateClass, predicateClass).newInstance(translateActivityPredicates(rule.getFilters()), translateIntentPredicates(rule.getFilters()))).setShouldAlwaysExpand(rule.getAlwaysExpand()).build();
            Intrinsics.checkNotNullExpressionValue(activityRuleBuild, "ActivityRuleBuilder::cla…\n                .build()");
            return activityRuleBuild;
        }

        public final SplitInfo translateCompat(androidx.window.extensions.embedding.SplitInfo splitInfo) {
            Intrinsics.checkNotNullParameter(splitInfo, "splitInfo");
            List activities = splitInfo.getPrimaryActivityStack().getActivities();
            Intrinsics.checkNotNullExpressionValue(activities, "splitInfo.primaryActivityStack.activities");
            ActivityStack activityStack = new ActivityStack(activities, splitInfo.getPrimaryActivityStack().isEmpty());
            List activities2 = splitInfo.getSecondaryActivityStack().getActivities();
            Intrinsics.checkNotNullExpressionValue(activities2, "splitInfo.secondaryActivityStack.activities");
            return new SplitInfo(activityStack, new ActivityStack(activities2, splitInfo.getSecondaryActivityStack().isEmpty()), getSplitAttributesCompat(splitInfo), EmbeddingAdapter.Companion.getINVALID_SPLIT_INFO_TOKEN());
        }

        public final Object translateIntentPredicates(final Set set) {
            return this.predicateAdapter.buildPredicate(Reflection.getOrCreateKotlinClass(Intent.class), new Function1<Intent, Boolean>() { // from class: androidx.window.embedding.EmbeddingAdapter$VendorApiLevel1Impl$translateIntentPredicates$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Intent intent) {
                    Intrinsics.checkNotNullParameter(intent, "intent");
                    Set set2 = set;
                    boolean z = false;
                    if (set2 == null || !set2.isEmpty()) {
                        Iterator it = set2.iterator();
                        while (it.hasNext()) {
                            if (((ActivityFilter) it.next()).matchesIntent(intent)) {
                                z = true;
                                break;
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
        }

        public final Object translateParentMetricsPredicate(final Context context, final SplitRule splitRule) {
            return this.predicateAdapter.buildPredicate(Reflection.getOrCreateKotlinClass(WindowMetrics.class), new Function1<WindowMetrics, Boolean>() { // from class: androidx.window.embedding.EmbeddingAdapter$VendorApiLevel1Impl$translateParentMetricsPredicate$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(WindowMetrics windowMetrics) {
                    Intrinsics.checkNotNullParameter(windowMetrics, "windowMetrics");
                    return Boolean.valueOf(splitRule.checkParentMetrics$window_release(context, windowMetrics));
                }
            });
        }

        public final Pair translateSplitAttributesCompatInternal(SplitAttributes splitAttributes) {
            int i = 3;
            if (!isSplitAttributesSupported(splitAttributes)) {
                return new Pair(Float.valueOf(0.0f), 3);
            }
            Float fValueOf = Float.valueOf(splitAttributes.getSplitType().getValue$window_release());
            SplitAttributes.LayoutDirection layoutDirection = splitAttributes.getLayoutDirection();
            if (!Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.LOCALE)) {
                if (Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.LEFT_TO_RIGHT)) {
                    i = 0;
                } else {
                    if (!Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.RIGHT_TO_LEFT)) {
                        throw new IllegalStateException("Unsupported layout direction must be covered in @isSplitAttributesSupported!");
                    }
                    i = 1;
                }
            }
            return new Pair(fValueOf, Integer.valueOf(i));
        }

        public final androidx.window.extensions.embedding.SplitPairRule translateSplitPairRuleCompat(Context context, SplitPairRule rule, Class predicateClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(rule, "rule");
            Intrinsics.checkNotNullParameter(predicateClass, "predicateClass");
            Object objNewInstance = androidx.window.extensions.embedding.SplitPairRule.Builder.class.getConstructor(predicateClass, predicateClass, predicateClass).newInstance(translateActivityPairPredicates(rule.getFilters()), translateActivityIntentPredicates(rule.getFilters()), translateParentMetricsPredicate(context, rule));
            Intrinsics.checkNotNullExpressionValue(objNewInstance, "SplitPairRuleBuilder::cl…text, rule)\n            )");
            androidx.window.extensions.embedding.SplitPairRule splitPairRuleBuild = setDefaultSplitAttributesCompat((androidx.window.extensions.embedding.SplitPairRule.Builder) objNewInstance, rule.getDefaultSplitAttributes()).setShouldClearTop(rule.getClearTop()).setFinishPrimaryWithSecondary(this.this$0.translateFinishBehavior(rule.getFinishPrimaryWithSecondary())).setFinishSecondaryWithPrimary(this.this$0.translateFinishBehavior(rule.getFinishSecondaryWithPrimary())).build();
            Intrinsics.checkNotNullExpressionValue(splitPairRuleBuild, "SplitPairRuleBuilder::cl…                ).build()");
            return splitPairRuleBuild;
        }

        public final androidx.window.extensions.embedding.SplitPlaceholderRule translateSplitPlaceholderRuleCompat(Context context, SplitPlaceholderRule rule, Class predicateClass) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(rule, "rule");
            Intrinsics.checkNotNullParameter(predicateClass, "predicateClass");
            androidx.window.extensions.embedding.SplitPlaceholderRule.Builder finishPrimaryWithSecondary = ((androidx.window.extensions.embedding.SplitPlaceholderRule.Builder) androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class.getConstructor(Intent.class, predicateClass, predicateClass, predicateClass).newInstance(rule.getPlaceholderIntent(), translateActivityPredicates(rule.getFilters()), translateIntentPredicates(rule.getFilters()), translateParentMetricsPredicate(context, rule))).setSticky(rule.isSticky()).setFinishPrimaryWithSecondary(this.this$0.translateFinishBehavior(rule.getFinishPrimaryWithPlaceholder()));
            Intrinsics.checkNotNullExpressionValue(finishPrimaryWithSecondary, "SplitPlaceholderRuleBuil…holder)\n                )");
            androidx.window.extensions.embedding.SplitPlaceholderRule splitPlaceholderRuleBuild = setDefaultSplitAttributesCompat(finishPrimaryWithSecondary, rule.getDefaultSplitAttributes()).build();
            Intrinsics.checkNotNullExpressionValue(splitPlaceholderRuleBuild, "SplitPlaceholderRuleBuil…\n                .build()");
            return splitPlaceholderRuleBuild;
        }
    }

    /* JADX INFO: compiled from: EmbeddingAdapter.kt */
    @Metadata
    public final class VendorApiLevel2Impl {
        public VendorApiLevel2Impl() {
        }

        public final SplitInfo translateCompat(androidx.window.extensions.embedding.SplitInfo splitInfo) {
            Intrinsics.checkNotNullParameter(splitInfo, "splitInfo");
            androidx.window.extensions.embedding.ActivityStack primaryActivityStack = splitInfo.getPrimaryActivityStack();
            Intrinsics.checkNotNullExpressionValue(primaryActivityStack, "splitInfo.primaryActivityStack");
            List activities = primaryActivityStack.getActivities();
            Intrinsics.checkNotNullExpressionValue(activities, "primaryActivityStack.activities");
            ActivityStack activityStack = new ActivityStack(activities, primaryActivityStack.isEmpty());
            androidx.window.extensions.embedding.ActivityStack secondaryActivityStack = splitInfo.getSecondaryActivityStack();
            Intrinsics.checkNotNullExpressionValue(secondaryActivityStack, "splitInfo.secondaryActivityStack");
            List activities2 = secondaryActivityStack.getActivities();
            Intrinsics.checkNotNullExpressionValue(activities2, "secondaryActivityStack.activities");
            ActivityStack activityStack2 = new ActivityStack(activities2, secondaryActivityStack.isEmpty());
            EmbeddingAdapter embeddingAdapter = EmbeddingAdapter.this;
            androidx.window.extensions.embedding.SplitAttributes splitAttributes = splitInfo.getSplitAttributes();
            Intrinsics.checkNotNullExpressionValue(splitAttributes, "splitInfo.splitAttributes");
            return new SplitInfo(activityStack, activityStack2, embeddingAdapter.translate$window_release(splitAttributes), EmbeddingAdapter.Companion.getINVALID_SPLIT_INFO_TOKEN());
        }
    }

    public EmbeddingAdapter(PredicateAdapter predicateAdapter) {
        Intrinsics.checkNotNullParameter(predicateAdapter, "predicateAdapter");
        this.predicateAdapter = predicateAdapter;
        this.api1Impl = new VendorApiLevel1Impl(this, predicateAdapter);
        this.api2Impl = new VendorApiLevel2Impl();
    }

    public static final boolean translateActivityRule$lambda$13(ActivityRule rule, Activity activity) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Set<ActivityFilter> filters = rule.getFilters();
        if (filters != null && filters.isEmpty()) {
            return false;
        }
        for (ActivityFilter activityFilter : filters) {
            Intrinsics.checkNotNullExpressionValue(activity, "activity");
            if (activityFilter.matchesActivity(activity)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean translateActivityRule$lambda$15(ActivityRule rule, Intent intent) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Set<ActivityFilter> filters = rule.getFilters();
        if (filters != null && filters.isEmpty()) {
            return false;
        }
        for (ActivityFilter activityFilter : filters) {
            Intrinsics.checkNotNullExpressionValue(intent, "intent");
            if (activityFilter.matchesIntent(intent)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean translateSplitPairRule$lambda$3(SplitPairRule rule, android.util.Pair pair) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Set<SplitPairFilter> filters = rule.getFilters();
        if (filters != null && filters.isEmpty()) {
            return false;
        }
        for (SplitPairFilter splitPairFilter : filters) {
            Object obj = pair.first;
            Intrinsics.checkNotNullExpressionValue(obj, "activitiesPair.first");
            Object obj2 = pair.second;
            Intrinsics.checkNotNullExpressionValue(obj2, "activitiesPair.second");
            if (splitPairFilter.matchesActivityPair((Activity) obj, (Activity) obj2)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean translateSplitPairRule$lambda$5(SplitPairRule rule, android.util.Pair pair) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Set<SplitPairFilter> filters = rule.getFilters();
        if (filters != null && filters.isEmpty()) {
            return false;
        }
        for (SplitPairFilter splitPairFilter : filters) {
            Object obj = pair.first;
            Intrinsics.checkNotNullExpressionValue(obj, "activityIntentPair.first");
            Object obj2 = pair.second;
            Intrinsics.checkNotNullExpressionValue(obj2, "activityIntentPair.second");
            if (splitPairFilter.matchesActivityIntentPair((Activity) obj, (Intent) obj2)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean translateSplitPairRule$lambda$6(SplitPairRule rule, Context context, WindowMetrics windowMetrics) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullExpressionValue(windowMetrics, "windowMetrics");
        return rule.checkParentMetrics$window_release(context, windowMetrics);
    }

    public static final boolean translateSplitPlaceholderRule$lambda$10(SplitPlaceholderRule rule, Intent intent) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Set<ActivityFilter> filters = rule.getFilters();
        if (filters != null && filters.isEmpty()) {
            return false;
        }
        for (ActivityFilter activityFilter : filters) {
            Intrinsics.checkNotNullExpressionValue(intent, "intent");
            if (activityFilter.matchesIntent(intent)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean translateSplitPlaceholderRule$lambda$11(SplitPlaceholderRule rule, Context context, WindowMetrics windowMetrics) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullExpressionValue(windowMetrics, "windowMetrics");
        return rule.checkParentMetrics$window_release(context, windowMetrics);
    }

    public static final boolean translateSplitPlaceholderRule$lambda$8(SplitPlaceholderRule rule, Activity activity) {
        Intrinsics.checkNotNullParameter(rule, "$rule");
        Set<ActivityFilter> filters = rule.getFilters();
        if (filters != null && filters.isEmpty()) {
            return false;
        }
        for (ActivityFilter activityFilter : filters) {
            Intrinsics.checkNotNullExpressionValue(activity, "activity");
            if (activityFilter.matchesActivity(activity)) {
                return true;
            }
        }
        return false;
    }

    public final int getVendorApiLevel() {
        return WindowSdkExtensions.Companion.getInstance().getExtensionVersion();
    }

    public final SplitInfo translate(androidx.window.extensions.embedding.SplitInfo splitInfo) {
        int vendorApiLevel = getVendorApiLevel();
        if (vendorApiLevel == 1) {
            return this.api1Impl.translateCompat(splitInfo);
        }
        if (vendorApiLevel == 2) {
            return this.api2Impl.translateCompat(splitInfo);
        }
        androidx.window.extensions.embedding.ActivityStack primaryActivityStack = splitInfo.getPrimaryActivityStack();
        Intrinsics.checkNotNullExpressionValue(primaryActivityStack, "splitInfo.primaryActivityStack");
        androidx.window.extensions.embedding.ActivityStack secondaryActivityStack = splitInfo.getSecondaryActivityStack();
        Intrinsics.checkNotNullExpressionValue(secondaryActivityStack, "splitInfo.secondaryActivityStack");
        List activities = primaryActivityStack.getActivities();
        Intrinsics.checkNotNullExpressionValue(activities, "primaryActivityStack.activities");
        ActivityStack activityStack = new ActivityStack(activities, primaryActivityStack.isEmpty());
        List activities2 = secondaryActivityStack.getActivities();
        Intrinsics.checkNotNullExpressionValue(activities2, "secondaryActivityStack.activities");
        ActivityStack activityStack2 = new ActivityStack(activities2, secondaryActivityStack.isEmpty());
        androidx.window.extensions.embedding.SplitAttributes splitAttributes = splitInfo.getSplitAttributes();
        Intrinsics.checkNotNullExpressionValue(splitAttributes, "splitInfo.splitAttributes");
        SplitAttributes splitAttributesTranslate$window_release = translate$window_release(splitAttributes);
        IBinder token = splitInfo.getToken();
        Intrinsics.checkNotNullExpressionValue(token, "splitInfo.token");
        return new SplitInfo(activityStack, activityStack2, splitAttributesTranslate$window_release, token);
    }

    public final List translate(List splitInfoList) {
        Intrinsics.checkNotNullParameter(splitInfoList, "splitInfoList");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(splitInfoList, 10));
        Iterator it = splitInfoList.iterator();
        while (it.hasNext()) {
            arrayList.add(translate((androidx.window.extensions.embedding.SplitInfo) it.next()));
        }
        return arrayList;
    }

    public final Set translate(Context context, Set rules) {
        androidx.window.extensions.embedding.SplitPairRule splitPairRuleTranslateActivityRule;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(rules, "rules");
        Class clsPredicateClassOrNull$window_release = this.predicateAdapter.predicateClassOrNull$window_release();
        if (clsPredicateClassOrNull$window_release == null) {
            return SetsKt.emptySet();
        }
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(rules, 10));
        Iterator it = rules.iterator();
        while (it.hasNext()) {
            EmbeddingRule embeddingRule = (EmbeddingRule) it.next();
            if (embeddingRule instanceof SplitPairRule) {
                splitPairRuleTranslateActivityRule = translateSplitPairRule(context, (SplitPairRule) embeddingRule, clsPredicateClassOrNull$window_release);
            } else if (embeddingRule instanceof SplitPlaceholderRule) {
                splitPairRuleTranslateActivityRule = translateSplitPlaceholderRule(context, (SplitPlaceholderRule) embeddingRule, clsPredicateClassOrNull$window_release);
            } else {
                if (!(embeddingRule instanceof ActivityRule)) {
                    throw new IllegalArgumentException("Unsupported rule type");
                }
                splitPairRuleTranslateActivityRule = translateActivityRule((ActivityRule) embeddingRule, clsPredicateClassOrNull$window_release);
            }
            arrayList.add((androidx.window.extensions.embedding.EmbeddingRule) splitPairRuleTranslateActivityRule);
        }
        return CollectionsKt.toSet(arrayList);
    }

    public final SplitAttributes translate$window_release(androidx.window.extensions.embedding.SplitAttributes splitAttributes) {
        SplitAttributes.SplitType splitTypeRatio;
        SplitAttributes.LayoutDirection layoutDirection;
        Intrinsics.checkNotNullParameter(splitAttributes, "splitAttributes");
        SplitAttributes.Builder builder = new SplitAttributes.Builder();
        androidx.window.extensions.embedding.SplitAttributes.SplitType.RatioSplitType splitType = splitAttributes.getSplitType();
        Intrinsics.checkNotNullExpressionValue(splitType, "splitAttributes.splitType");
        if (splitType instanceof androidx.window.extensions.embedding.SplitAttributes.SplitType.HingeSplitType) {
            splitTypeRatio = SplitAttributes.SplitType.SPLIT_TYPE_HINGE;
        } else if (splitType instanceof androidx.window.extensions.embedding.SplitAttributes.SplitType.ExpandContainersSplitType) {
            splitTypeRatio = SplitAttributes.SplitType.SPLIT_TYPE_EXPAND;
        } else {
            if (!(splitType instanceof androidx.window.extensions.embedding.SplitAttributes.SplitType.RatioSplitType)) {
                throw new IllegalArgumentException("Unknown split type: " + splitType);
            }
            splitTypeRatio = SplitAttributes.SplitType.Companion.ratio(splitType.getRatio());
        }
        SplitAttributes.Builder splitType2 = builder.setSplitType(splitTypeRatio);
        int layoutDirection2 = splitAttributes.getLayoutDirection();
        if (layoutDirection2 == 0) {
            layoutDirection = SplitAttributes.LayoutDirection.LEFT_TO_RIGHT;
        } else if (layoutDirection2 == 1) {
            layoutDirection = SplitAttributes.LayoutDirection.RIGHT_TO_LEFT;
        } else if (layoutDirection2 == 3) {
            layoutDirection = SplitAttributes.LayoutDirection.LOCALE;
        } else if (layoutDirection2 == 4) {
            layoutDirection = SplitAttributes.LayoutDirection.TOP_TO_BOTTOM;
        } else {
            if (layoutDirection2 != 5) {
                throw new IllegalArgumentException("Unknown layout direction: " + layoutDirection2);
            }
            layoutDirection = SplitAttributes.LayoutDirection.BOTTOM_TO_TOP;
        }
        return splitType2.setLayoutDirection(layoutDirection).build();
    }

    public final androidx.window.extensions.embedding.ActivityRule translateActivityRule(final ActivityRule activityRule, Class cls) {
        if (getVendorApiLevel() < 2) {
            return this.api1Impl.translateActivityRuleCompat(activityRule, cls);
        }
        androidx.window.extensions.embedding.ActivityRule.Builder shouldAlwaysExpand = new androidx.window.extensions.embedding.ActivityRule.Builder(new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda6
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateActivityRule$lambda$13(activityRule, (Activity) obj);
            }
        }, new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda7
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateActivityRule$lambda$15(activityRule, (Intent) obj);
            }
        }).setShouldAlwaysExpand(activityRule.getAlwaysExpand());
        Intrinsics.checkNotNullExpressionValue(shouldAlwaysExpand, "ActivityRuleBuilder(acti…Expand(rule.alwaysExpand)");
        String tag = activityRule.getTag();
        if (tag != null) {
            shouldAlwaysExpand.setTag(tag);
        }
        androidx.window.extensions.embedding.ActivityRule activityRuleBuild = shouldAlwaysExpand.build();
        Intrinsics.checkNotNullExpressionValue(activityRuleBuild, "builder.build()");
        return activityRuleBuild;
    }

    public final int translateFinishBehavior(SplitRule.FinishBehavior behavior) {
        Intrinsics.checkNotNullParameter(behavior, "behavior");
        if (Intrinsics.areEqual(behavior, SplitRule.FinishBehavior.NEVER)) {
            return 0;
        }
        if (Intrinsics.areEqual(behavior, SplitRule.FinishBehavior.ALWAYS)) {
            return 1;
        }
        if (Intrinsics.areEqual(behavior, SplitRule.FinishBehavior.ADJACENT)) {
            return 2;
        }
        throw new IllegalArgumentException("Unknown finish behavior:" + behavior);
    }

    public final androidx.window.extensions.embedding.SplitAttributes translateSplitAttributes(SplitAttributes splitAttributes) {
        int i;
        Intrinsics.checkNotNullParameter(splitAttributes, "splitAttributes");
        if (getVendorApiLevel() < 2) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        androidx.window.extensions.embedding.SplitAttributes.Builder splitType = new androidx.window.extensions.embedding.SplitAttributes.Builder().setSplitType(translateSplitType(splitAttributes.getSplitType()));
        SplitAttributes.LayoutDirection layoutDirection = splitAttributes.getLayoutDirection();
        if (Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.LOCALE)) {
            i = 3;
        } else if (Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.LEFT_TO_RIGHT)) {
            i = 0;
        } else if (Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.RIGHT_TO_LEFT)) {
            i = 1;
        } else if (Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.TOP_TO_BOTTOM)) {
            i = 4;
        } else {
            if (!Intrinsics.areEqual(layoutDirection, SplitAttributes.LayoutDirection.BOTTOM_TO_TOP)) {
                throw new IllegalArgumentException("Unsupported layoutDirection:" + splitAttributes + ".layoutDirection");
            }
            i = 5;
        }
        androidx.window.extensions.embedding.SplitAttributes splitAttributesBuild = splitType.setLayoutDirection(i).build();
        Intrinsics.checkNotNullExpressionValue(splitAttributesBuild, "Builder()\n            .s…   )\n            .build()");
        return splitAttributesBuild;
    }

    public final androidx.window.extensions.embedding.SplitPairRule translateSplitPairRule(final Context context, final SplitPairRule splitPairRule, Class cls) {
        if (getVendorApiLevel() < 2) {
            return this.api1Impl.translateSplitPairRuleCompat(context, splitPairRule, cls);
        }
        Predicate predicate = new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda3
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateSplitPairRule$lambda$3(splitPairRule, (android.util.Pair) obj);
            }
        };
        Predicate predicate2 = new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda4
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateSplitPairRule$lambda$5(splitPairRule, (android.util.Pair) obj);
            }
        };
        Predicate predicate3 = new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda5
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateSplitPairRule$lambda$6(splitPairRule, context, (WindowMetrics) obj);
            }
        };
        String tag = splitPairRule.getTag();
        androidx.window.extensions.embedding.SplitPairRule.Builder shouldClearTop = new androidx.window.extensions.embedding.SplitPairRule.Builder(predicate, predicate2, predicate3).setDefaultSplitAttributes(translateSplitAttributes(splitPairRule.getDefaultSplitAttributes())).setFinishPrimaryWithSecondary(translateFinishBehavior(splitPairRule.getFinishPrimaryWithSecondary())).setFinishSecondaryWithPrimary(translateFinishBehavior(splitPairRule.getFinishSecondaryWithPrimary())).setShouldClearTop(splitPairRule.getClearTop());
        Intrinsics.checkNotNullExpressionValue(shouldClearTop, "SplitPairRuleBuilder(\n  …ldClearTop(rule.clearTop)");
        if (tag != null) {
            shouldClearTop.setTag(tag);
        }
        androidx.window.extensions.embedding.SplitPairRule splitPairRuleBuild = shouldClearTop.build();
        Intrinsics.checkNotNullExpressionValue(splitPairRuleBuild, "builder.build()");
        return splitPairRuleBuild;
    }

    public final androidx.window.extensions.embedding.SplitPlaceholderRule translateSplitPlaceholderRule(final Context context, final SplitPlaceholderRule splitPlaceholderRule, Class cls) {
        if (getVendorApiLevel() < 2) {
            return this.api1Impl.translateSplitPlaceholderRuleCompat(context, splitPlaceholderRule, cls);
        }
        Predicate predicate = new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda0
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateSplitPlaceholderRule$lambda$8(splitPlaceholderRule, (Activity) obj);
            }
        };
        Predicate predicate2 = new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda1
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateSplitPlaceholderRule$lambda$10(splitPlaceholderRule, (Intent) obj);
            }
        };
        Predicate predicate3 = new Predicate() { // from class: androidx.window.embedding.EmbeddingAdapter$$ExternalSyntheticLambda2
            @Override // androidx.window.extensions.core.util.function.Predicate
            public final boolean test(Object obj) {
                return EmbeddingAdapter.translateSplitPlaceholderRule$lambda$11(splitPlaceholderRule, context, (WindowMetrics) obj);
            }
        };
        String tag = splitPlaceholderRule.getTag();
        androidx.window.extensions.embedding.SplitPlaceholderRule.Builder finishPrimaryWithPlaceholder = new androidx.window.extensions.embedding.SplitPlaceholderRule.Builder(splitPlaceholderRule.getPlaceholderIntent(), predicate, predicate2, predicate3).setSticky(splitPlaceholderRule.isSticky()).setDefaultSplitAttributes(translateSplitAttributes(splitPlaceholderRule.getDefaultSplitAttributes())).setFinishPrimaryWithPlaceholder(translateFinishBehavior(splitPlaceholderRule.getFinishPrimaryWithPlaceholder()));
        Intrinsics.checkNotNullExpressionValue(finishPrimaryWithPlaceholder, "SplitPlaceholderRuleBuil…holder)\n                )");
        if (tag != null) {
            finishPrimaryWithPlaceholder.setTag(tag);
        }
        androidx.window.extensions.embedding.SplitPlaceholderRule splitPlaceholderRuleBuild = finishPrimaryWithPlaceholder.build();
        Intrinsics.checkNotNullExpressionValue(splitPlaceholderRuleBuild, "builder.build()");
        return splitPlaceholderRuleBuild;
    }

    public final androidx.window.extensions.embedding.SplitAttributes.SplitType translateSplitType(SplitAttributes.SplitType splitType) {
        if (getVendorApiLevel() < 2) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (Intrinsics.areEqual(splitType, SplitAttributes.SplitType.SPLIT_TYPE_HINGE)) {
            return new androidx.window.extensions.embedding.SplitAttributes.SplitType.HingeSplitType(translateSplitType(SplitAttributes.SplitType.SPLIT_TYPE_EQUAL));
        }
        if (Intrinsics.areEqual(splitType, SplitAttributes.SplitType.SPLIT_TYPE_EXPAND)) {
            return new androidx.window.extensions.embedding.SplitAttributes.SplitType.ExpandContainersSplitType();
        }
        float value$window_release = splitType.getValue$window_release();
        double d = value$window_release;
        if (d > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && d < 1.0d) {
            return new androidx.window.extensions.embedding.SplitAttributes.SplitType.RatioSplitType(value$window_release);
        }
        throw new IllegalArgumentException("Unsupported SplitType: " + splitType + " with value: " + splitType.getValue$window_release());
    }
}
