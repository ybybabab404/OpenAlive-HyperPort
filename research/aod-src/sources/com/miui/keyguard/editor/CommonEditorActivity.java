package com.miui.keyguard.editor;

import android.app.KeyguardManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.activity.OnBackPressedCallback;
import androidx.core.os.BundleKt;
import androidx.fragment.app.FragmentTransaction;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.template.CurrentTemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.settings.SettingsResourceProvider;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
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
/* JADX INFO: compiled from: CommonEditorActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CommonEditorActivity extends EditorActivity {
    public static final Companion Companion = new Companion(null);
    public final CommonEditorActivity$commonOnBackCallback$1 commonOnBackCallback = new OnBackPressedCallback() { // from class: com.miui.keyguard.editor.CommonEditorActivity$commonOnBackCallback$1
        {
            super(true);
        }

        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            EditFragment editFragment;
            if (this.this$0.isShowApplyLoading()) {
                Log.i("Keyguard-Editor-CommonEditorActivity", "onBack: intercept applyLoading");
                return;
            }
            EditFragment editFragment2 = this.this$0.editFragment;
            if (editFragment2 != null && editFragment2.isInPreviewAnimation()) {
                Log.i("Keyguard-Editor-CommonEditorActivity", "onBack: intercept previewAnimation");
                return;
            }
            EditFragment editFragment3 = this.this$0.editFragment;
            if (editFragment3 != null && editFragment3.getTemplateStatus() == 0 && ((editFragment = this.this$0.editFragment) == null || !editFragment.isEdited())) {
                Log.i("Keyguard-Editor-CommonEditorActivity", "onBack: finishActivity (let system pop back stack)");
                this.this$0.finishActivity();
            } else {
                Log.i("Keyguard-Editor-CommonEditorActivity", "onBack: dispatch to parent");
                setEnabled(false);
                this.this$0.getOnBackPressedDispatcher().onBackPressed();
            }
        }
    };
    public CurrentTemplateApi currentTemplateApi;
    public EditFragment editFragment;
    public boolean fromRecentLaunch;
    public FrameLayout screenshotContainer;

    /* JADX INFO: compiled from: CommonEditorActivity.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.CommonEditorActivity$onResume$1, reason: invalid class name */
    /* JADX INFO: compiled from: CommonEditorActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.CommonEditorActivity$onResume$1", f = "CommonEditorActivity.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommonEditorActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            WallpaperController companion = WallpaperController.Companion.getInstance();
            if (companion != null) {
                companion.refreshMiuiWallpaperInfo(CommonEditorActivity.this);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.CommonEditorActivity$refreshTemplate$2, reason: invalid class name */
    /* JADX INFO: compiled from: CommonEditorActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.CommonEditorActivity$refreshTemplate$2", f = "CommonEditorActivity.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super CommonConfig>, Object> {
        public int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommonEditorActivity.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CommonEditorActivity.this.currentTemplateApi = new CurrentTemplateApi(CommonEditorActivity.this);
            CurrentTemplateApi currentTemplateApi = CommonEditorActivity.this.currentTemplateApi;
            if (currentTemplateApi != null) {
                return CurrentTemplateApi.requireCurrentTemplate$default(currentTemplateApi, null, 1, null);
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.CommonEditorActivity$refreshTemplateIfModByThirdApp$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CommonEditorActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.CommonEditorActivity$refreshTemplateIfModByThirdApp$1", f = "CommonEditorActivity.kt", l = {154}, m = "invokeSuspend")
    public static final class C00851 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public long J$0;
        public int label;

        public C00851(Continuation continuation) {
            super(2, continuation);
        }

        public static final void invokeSuspend$lambda$4(CommonEditorActivity commonEditorActivity, final CommonEditorActivity commonEditorActivity2, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, CommonConfig commonConfig) {
            DeviceScreenshotHelper deviceScreenshotHelper = DeviceScreenshotHelper.INSTANCE;
            FrameLayout frameLayout = commonEditorActivity2.screenshotContainer;
            Intrinsics.checkNotNull(baseTemplateView);
            deviceScreenshotHelper.screenshot(commonEditorActivity, frameLayout, baseTemplateView, baseTemplateView2, commonConfig, (32 & 32) != 0 ? "wallpaper_for_screenshot" : null, new Function0() { // from class: com.miui.keyguard.editor.CommonEditorActivity$refreshTemplateIfModByThirdApp$1$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return CommonEditorActivity.C00851.invokeSuspend$lambda$4$lambda$2(commonEditorActivity2);
                }
            }, (32 & 128) != 0 ? null : new Function1() { // from class: com.miui.keyguard.editor.CommonEditorActivity$refreshTemplateIfModByThirdApp$1$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return CommonEditorActivity.C00851.invokeSuspend$lambda$4$lambda$3(commonEditorActivity2, (Throwable) obj);
                }
            });
        }

        public static final Unit invokeSuspend$lambda$4$lambda$2(CommonEditorActivity commonEditorActivity) {
            Log.w("Keyguard-Editor-CommonEditorActivity", "takeCaptureForMultiScreenMode success from thirdApp: onCurrentTemplateScreenshotsSaveComplete");
            commonEditorActivity.toEditFragment();
            return Unit.INSTANCE;
        }

        public static final Unit invokeSuspend$lambda$4$lambda$3(CommonEditorActivity commonEditorActivity, Throwable th) {
            Log.e("Keyguard-Editor-CommonEditorActivity", "takeCaptureForMultiScreenMode failed from thirdApp, finish the CommonEditorActivity");
            commonEditorActivity.finish();
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommonEditorActivity.this.new C00851(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00851) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            long jCurrentTimeMillis;
            Object objRefreshTemplate;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                jCurrentTimeMillis = System.currentTimeMillis();
                CommonEditorActivity commonEditorActivity = CommonEditorActivity.this;
                this.J$0 = jCurrentTimeMillis;
                this.label = 1;
                objRefreshTemplate = commonEditorActivity.refreshTemplate(this);
                if (objRefreshTemplate == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                long j = this.J$0;
                ResultKt.throwOnFailure(obj);
                jCurrentTimeMillis = j;
                objRefreshTemplate = obj;
            }
            final CommonConfig commonConfig = (CommonConfig) objRefreshTemplate;
            if (commonConfig == null) {
                return Unit.INSTANCE;
            }
            Log.i("Keyguard-Editor-CommonEditorActivity", "refreshTemplateIfModByThirdApp: cost " + (System.currentTimeMillis() - jCurrentTimeMillis));
            final CommonEditorActivity commonEditorActivity2 = CommonEditorActivity.this;
            boolean zIsDisplayLandScape = DeviceUtil.INSTANCE.isDisplayLandScape(commonEditorActivity2);
            Point pointRequireDisplayRealSize = SettingsResourceProvider.INSTANCE.requireDisplayRealSize(commonEditorActivity2);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(zIsDisplayLandScape ? Math.max(pointRequireDisplayRealSize.x, pointRequireDisplayRealSize.y) : Math.min(pointRequireDisplayRealSize.x, pointRequireDisplayRealSize.y), zIsDisplayLandScape ? Math.min(pointRequireDisplayRealSize.x, pointRequireDisplayRealSize.y) : Math.max(pointRequireDisplayRealSize.x, pointRequireDisplayRealSize.y));
            TemplateViewFactory templateViewFactory = TemplateViewFactory.INSTANCE;
            BaseTemplateView baseTemplateViewCreateScaledPreviewTemplateView$default = TemplateViewFactory.createScaledPreviewTemplateView$default(templateViewFactory, commonEditorActivity2, commonConfig.getLockscreenInfo().getClockInfo().getTemplateId(), layoutParams, 1.0E-4f, 1.0E-4f, false, 32, null);
            if (baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
                CommonEditorActivity commonEditorActivity3 = CommonEditorActivity.this;
                baseTemplateViewCreateScaledPreviewTemplateView$default.setTemplateSource(-1L);
                baseTemplateViewCreateScaledPreviewTemplateView$default.loadTemplate(commonConfig);
                FrameLayout frameLayout = commonEditorActivity3.screenshotContainer;
                if (frameLayout != null) {
                    frameLayout.addView(baseTemplateViewCreateScaledPreviewTemplateView$default);
                }
            } else {
                baseTemplateViewCreateScaledPreviewTemplateView$default = null;
            }
            final BaseTemplateView baseTemplateViewCreateScaledPreviewHomeTemplateView$default = TemplateViewFactory.createScaledPreviewHomeTemplateView$default(templateViewFactory, commonEditorActivity2, layoutParams, 1.0E-4f, 1.0E-4f, false, 16, null);
            CommonEditorActivity commonEditorActivity4 = CommonEditorActivity.this;
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setTemplateSource(-1L);
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.loadTemplate(commonConfig);
            FrameLayout frameLayout2 = commonEditorActivity4.screenshotContainer;
            if (frameLayout2 != null) {
                frameLayout2.addView(baseTemplateViewCreateScaledPreviewHomeTemplateView$default);
            }
            final CommonEditorActivity commonEditorActivity5 = CommonEditorActivity.this;
            final BaseTemplateView baseTemplateView = baseTemplateViewCreateScaledPreviewTemplateView$default;
            baseTemplateViewCreateScaledPreviewHomeTemplateView$default.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.CommonEditorActivity$refreshTemplateIfModByThirdApp$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CommonEditorActivity.C00851.invokeSuspend$lambda$4(commonEditorActivity2, commonEditorActivity5, baseTemplateView, baseTemplateViewCreateScaledPreviewHomeTemplateView$default, commonConfig);
                }
            }, 150L);
            return Unit.INSTANCE;
        }
    }

    @Override // com.miui.keyguard.editor.EditorActivity
    public int getTheme(boolean z) {
        Log.d("Keyguard-Editor-CommonEditorActivity", "getTheme: isNight=" + z);
        return z ? R.style.KgEditorActivityForSettingsThemeNight : R.style.KgEditorActivityForSettingsThemeLight;
    }

    @Override // com.miui.keyguard.editor.EditorActivity
    public void navigateToFirstFragment() {
        if (needUpdateCurrent()) {
            refreshTemplateIfModByThirdApp();
        } else {
            toEditFragment();
        }
    }

    public final boolean needUpdateCurrent() {
        String launchedFromPackage = getLaunchedFromPackage();
        TemplateApi companion = TemplateApi.Companion.getInstance(this);
        Log.i("Keyguard-Editor-CommonEditorActivity", "needUpdateCurrent: callerPackage=" + launchedFromPackage);
        if (companion.isLockSetPkgSupportDepth() && companion.isHomeSetPkgSupportDepth()) {
            return false;
        }
        return Intrinsics.areEqual(launchedFromPackage, "com.miui.gallery") || Intrinsics.areEqual(launchedFromPackage, "com.android.fileexplorer");
    }

    @Override // com.miui.keyguard.editor.EditorActivity, miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.fromRecentLaunch = (getIntent().getFlags() & 1048576) != 0;
        super.onCreate(bundle);
        Object systemService = getSystemService("keyguard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
        KeyguardManager keyguardManager = (KeyguardManager) systemService;
        if (!callingFromLockCross() && !keyguardManager.isKeyguardLocked()) {
            setShowWhenLocked(false);
        }
        this.screenshotContainer = getFragmentContainer();
        videoCheck();
        finishEditorTask();
        getOnBackPressedDispatcher().addCallback(this, this.commonOnBackCallback);
    }

    @Override // com.miui.keyguard.editor.EditorActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this), Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
    }

    public final Object refreshTemplate(Continuation continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(null), continuation);
    }

    public final void refreshTemplateIfModByThirdApp() {
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this), null, null, new C00851(null), 3, null);
    }

    @Override // com.miui.keyguard.editor.EditorActivity
    public void saveCallingSource(Intent intent) {
        setLaunchFromPackage(intent != null ? intent.getStringExtra("launched_from_package") : null);
        String stringExtra = intent != null ? intent.getStringExtra("caller") : null;
        Log.i("Keyguard-Editor-CommonEditorActivity", "common saveCallingSource -> launchFromPackage: " + getLaunchFromPackage() + " caller=" + stringExtra);
        if (callingFromSystemUI() || Intrinsics.areEqual(stringExtra, "lock_negative_one")) {
            EditorServiceManager.Companion.getInstance().setActivity(this);
            if (intent != null) {
                intent.putExtra("openSource", 0);
            }
            Log.i("Keyguard-Editor-CommonEditorActivity", "common callingFromSystemUI");
            return;
        }
        if (!isMiuiCall() && !isAppFunctionCall()) {
            finish();
            return;
        }
        SettingsEditorServiceManager.INSTANCE.setActivity(this);
        if (intent != null) {
            intent.putExtra("openSource", 1);
        }
        Log.i("Keyguard-Editor-CommonEditorActivity", "common callingFromTheme");
    }

    public final void toEditFragment() {
        this.editFragment = new EditFragment();
        String stringExtra = getIntent().getStringExtra("argConfigPath");
        if (stringExtra == null) {
            stringExtra = "@MINE";
        }
        long longExtra = getIntent().getLongExtra("argTemplateSource", -1L);
        Log.d("Keyguard-Editor-CommonEditorActivity", "edit: configPath=" + stringExtra + " templateSource=" + longExtra);
        EditFragment editFragment = this.editFragment;
        if (editFragment != null) {
            Pair pair = TuplesKt.to("argConfigPath", stringExtra);
            Pair pair2 = TuplesKt.to("argTemplateSource", Long.valueOf(longExtra));
            Boolean bool = Boolean.FALSE;
            editFragment.setArguments(BundleKt.bundleOf(pair, pair2, TuplesKt.to("argThirdPartyWallpaper", bool), TuplesKt.to("argAutoDownload", bool), TuplesKt.to("argFromRecentLaunch", Boolean.valueOf(this.fromRecentLaunch))));
        }
        Log.d("Keyguard-Editor-CommonEditorActivity", "navigateToFirstFragment: configPath=" + stringExtra + " templateSource=" + longExtra);
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        int i = R.id.kg_fragment_container;
        EditFragment editFragment2 = this.editFragment;
        Intrinsics.checkNotNull(editFragment2);
        fragmentTransactionBeginTransaction.add(i, editFragment2, "Keyguard-Editor-EditFragment").addToBackStack("Keyguard-Editor-EditFragment").commitAllowingStateLoss();
    }
}
