package com.miui.keyguard.editor;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.activity.ComponentActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import miuix.appcompat.app.AlertDialog;

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
/* JADX INFO: compiled from: CommonDialogActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CommonDialogActivity extends ComponentActivity {
    public final int POSITION_LOCK_SCREEN;
    public boolean isLight;
    public Dialog mDialog;
    public boolean videoChecked;
    public final int POSITION_DESKTOP = 1;
    public final int POSITION_ALL = 2;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.CommonDialogActivity$showDialog$1, reason: invalid class name */
    /* JADX INFO: compiled from: CommonDialogActivity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.CommonDialogActivity$showDialog$1", f = "CommonDialogActivity.kt", l = {com.miui.maml.R.styleable.AppCompatTheme_homeAsUpIndicator}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public Object L$0;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.CommonDialogActivity$showDialog$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: CommonDialogActivity.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.CommonDialogActivity$showDialog$1$1", f = "CommonDialogActivity.kt", l = {}, m = "invokeSuspend")
        public static final class C00201 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Ref.BooleanRef $needWallpaperTargetAll;
            public int label;
            public final /* synthetic */ CommonDialogActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00201(CommonDialogActivity commonDialogActivity, Ref.BooleanRef booleanRef, Continuation continuation) {
                super(2, continuation);
                this.this$0 = commonDialogActivity;
                this.$needWallpaperTargetAll = booleanRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00201(this.this$0, this.$needWallpaperTargetAll, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00201) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code duplicated, block: B:12:0x0045  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                boolean z;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                WallpaperController companion = WallpaperController.Companion.getInstance(this.this$0, false);
                String lockWallpaperType = companion.getLockWallpaperType();
                String homeWallpaperType = companion.getHomeWallpaperType();
                boolean zIsGalleryInstalledAndOpen = FashionGalleryHelper.INSTANCE.isGalleryInstalledAndOpen(this.this$0);
                Ref.BooleanRef booleanRef = this.$needWallpaperTargetAll;
                if (!zIsGalleryInstalledAndOpen) {
                    Wallpaper.Companion companion2 = Wallpaper.Companion;
                    z = companion2.needWallpaperTargetAll(lockWallpaperType) || companion2.needWallpaperTargetAll(homeWallpaperType) || TemplateApi.DefaultImpls.isThirdPartyWallpaper$default(TemplateApi.Companion.getInstance(this.this$0), null, 1, null);
                }
                booleanRef.element = z;
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommonDialogActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.BooleanRef booleanRef;
            Dialog mDialog;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
                CoroutineDispatcher io2 = Dispatchers.getIO();
                C00201 c00201 = new C00201(CommonDialogActivity.this, booleanRef2, null);
                this.L$0 = booleanRef2;
                this.label = 1;
                if (BuildersKt.withContext(io2, c00201, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                booleanRef = booleanRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                booleanRef = (Ref.BooleanRef) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            String stringExtra = CommonDialogActivity.this.getIntent().getStringExtra("frontDialogType");
            if (stringExtra == null) {
                stringExtra = "show";
            }
            if (booleanRef.element || !TextUtils.equals(stringExtra, "show")) {
                CommonDialogActivity commonDialogActivity = CommonDialogActivity.this;
                Intent intent = commonDialogActivity.getIntent();
                Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
                commonDialogActivity.startCommonEditorActivity(intent, "all");
                CommonDialogActivity.this.finish();
            } else {
                CommonDialogActivity commonDialogActivity2 = CommonDialogActivity.this;
                commonDialogActivity2.setMDialog(CommonDialogActivity.createDialog$default(commonDialogActivity2, 0, 1, null));
                Dialog mDialog2 = CommonDialogActivity.this.getMDialog();
                if (mDialog2 != null && !mDialog2.isShowing() && (mDialog = CommonDialogActivity.this.getMDialog()) != null) {
                    mDialog.show();
                }
            }
            return Unit.INSTANCE;
        }
    }

    public static /* synthetic */ Dialog createDialog$default(CommonDialogActivity commonDialogActivity, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -1;
        }
        return commonDialogActivity.createDialog(i);
    }

    public static final void createDialog$lambda$0(Ref.ObjectRef objectRef, CommonDialogActivity commonDialogActivity, DialogInterface dialogInterface, int i) {
        String str;
        if (i == commonDialogActivity.POSITION_LOCK_SCREEN) {
            str = "lock";
        } else {
            str = i == commonDialogActivity.POSITION_DESKTOP ? "home" : "all";
        }
        objectRef.element = str;
        dialogInterface.dismiss();
    }

    public static final void createDialog$lambda$3(Ref.ObjectRef objectRef, CommonDialogActivity commonDialogActivity, DialogInterface dialogInterface) {
        String str = (String) objectRef.element;
        if (str != null) {
            Intent intent = commonDialogActivity.getIntent();
            Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
            commonDialogActivity.startCommonEditorActivity(intent, str);
        }
        commonDialogActivity.finish();
    }

    public final Dialog createDialog(int i) {
        String[] strArr = {getResources().getString(R.string.kg_template_title_lock_screen), getResources().getString(R.string.kg_template_title_desktop), getResources().getString(R.string.kg_lock_screen_and_desktop)};
        AlertDialog.Builder builder = i == -1 ? new AlertDialog.Builder(this) : new AlertDialog.Builder(this, i);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        builder.setHapticFeedbackEnabled(true);
        builder.setEnableDialogImmersive(true);
        builder.setEnableEnterAnim(true);
        builder.setTitle(getResources().getString(R.string.kg_use_wallpaper_for));
        builder.setItems(strArr, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.CommonDialogActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                CommonDialogActivity.createDialog$lambda$0(objectRef, this, dialogInterface, i2);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.kg_dialog_apply_super_wallpaper_nagetive_button), new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.CommonDialogActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.CommonDialogActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                CommonDialogActivity.createDialog$lambda$3(objectRef, this, dialogInterface);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    public final Dialog getMDialog() {
        return this.mDialog;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        if ((getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32) {
            if (this.isLight) {
                finish();
            }
        } else {
            if ((getApplicationContext().getResources().getConfiguration().uiMode & 48) != 16 || this.isLight) {
                return;
            }
            finish();
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.isLight = (getApplicationContext().getResources().getConfiguration().uiMode & 48) == 16;
        videoCheck(getIntent(), this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showDialog();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        WindowInsetsControllerCompat insetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        Intrinsics.checkNotNullExpressionValue(insetsController, "getInsetsController(...)");
        insetsController.hide(WindowInsetsCompat.Type.statusBars());
        insetsController.setAppearanceLightNavigationBars(false);
    }

    @Override // android.app.Activity
    public void onStop() {
        Dialog dialog;
        super.onStop();
        Dialog dialog2 = this.mDialog;
        if (dialog2 == null || !dialog2.isShowing() || (dialog = this.mDialog) == null) {
            return;
        }
        dialog.dismiss();
    }

    public final void setMDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public final void showDialog() {
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(this), Dispatchers.getMain(), null, new AnonymousClass1(null), 2, null);
    }

    public final void startCommonEditorActivity(Intent intent, String str) {
        intent.putExtra("wallpaperTarget", str);
        intent.putExtra("fromFrontDialog", true);
        intent.setClass(this, CommonEditorActivity.class);
        Object systemService = getSystemService("keyguard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.KeyguardManager");
        if (((KeyguardManager) systemService).isKeyguardLocked()) {
            intent.setFlags(268435456);
        } else {
            intent.setFlags(268468224);
        }
        intent.putExtra("video_resource_checked", this.videoChecked);
        startActivity(intent);
    }

    public final void videoCheck(Intent intent, Context context) {
        this.videoChecked = false;
        if (TextUtils.equals(intent != null ? intent.getStringExtra("resourceType") : null, "video")) {
            new VideoCheckForDepthInterceptor(context, false, false, 6, null).accessCheck(intent != null ? intent.getData() : null, new VideoCheckForDepthInterceptor.Companion.CheckVideoCallback() { // from class: com.miui.keyguard.editor.CommonDialogActivity.videoCheck.1
                @Override // com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor.Companion.CheckVideoCallback
                public void onCheckVideoFail(Integer num) {
                    VideoCheckForDepthInterceptor.Companion.CheckVideoCallback.DefaultImpls.onCheckVideoFail(this, num);
                }

                @Override // com.miui.keyguard.editor.video.VideoCheckForDepthInterceptor.Companion.CheckVideoCallback
                public void onCheckVideoSuccess() {
                    CommonDialogActivity.this.videoChecked = true;
                    CommonDialogActivity.this.showDialog();
                }
            });
        } else {
            showDialog();
        }
    }
}
