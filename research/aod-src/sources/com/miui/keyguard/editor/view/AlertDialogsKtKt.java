package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.AodClockViews;
import com.miui.keyguard.editor.base.AodViewFactory;
import com.miui.keyguard.editor.base.MiuiHomeViewFactory;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
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
/* JADX INFO: compiled from: AlertDialogsKt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AlertDialogsKtKt {

    /* JADX INFO: renamed from: com.miui.keyguard.editor.view.AlertDialogsKtKt$buildDialogViewManager$3, reason: invalid class name */
    /* JADX INFO: compiled from: AlertDialogsKt.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.view.AlertDialogsKtKt$buildDialogViewManager$3", f = "AlertDialogsKt.kt", l = {141}, m = "invokeSuspend")
    public static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function2 $addToDialog;
        public final /* synthetic */ Context $context;
        public final /* synthetic */ boolean $needOriginHome;
        public final /* synthetic */ ScreenshotSource $screenshotSource;
        public final /* synthetic */ CommonConfig $templateConfig;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Context context, ScreenshotSource screenshotSource, boolean z, CommonConfig commonConfig, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.$screenshotSource = screenshotSource;
            this.$needOriginHome = z;
            this.$templateConfig = commonConfig;
            this.$addToDialog = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$context, this.$screenshotSource, this.$needOriginHome, this.$templateConfig, this.$addToDialog, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TemplateApi companion = TemplateApi.Companion.getInstance(this.$context);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                AlertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1 alertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1 = new AlertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1(this.$templateConfig, companion, null);
                this.label = 1;
                obj = BuildersKt.withContext(io2, alertDialogsKtKt$buildDialogViewManager$3$configWithWallpaper$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            View viewCreate = MiuiHomeViewFactory.INSTANCE.create(this.$context, (CommonConfig) obj, this.$screenshotSource, this.$needOriginHome);
            if (viewCreate != null) {
                this.$addToDialog.invoke(viewCreate, Boxing.boxBoolean(true));
            }
            return Unit.INSTANCE;
        }
    }

    public static final void adaptN8AlertDialog(AlertDialog alertDialog, boolean z, boolean z2, boolean z3) {
        if (alertDialog == null || !DeviceUtil.INSTANCE.isN8()) {
            return;
        }
        Log.d("AlertDialogsKt", "adapt AlertDialog: " + z + " " + z2 + " " + z3);
        TextView textView = (TextView) alertDialog.findViewById(R.id.alertTitle);
        TextView textView2 = (TextView) alertDialog.findViewById(R.id.message);
        LinearLayout linearLayout = (LinearLayout) alertDialog.findViewById(R.id.buttonPanel);
        Button button = alertDialog.getButton(-1);
        Button button2 = alertDialog.getButton(-2);
        Resources resources = alertDialog.getContext().getResources();
        if (z && linearLayout != null) {
            ViewUtil.setMarginTop(linearLayout, resources.getDimensionPixelSize(R.dimen.kg_miuix_appcompat_dialog_button_vertical_margin));
        }
        if (z3 && linearLayout != null) {
            ViewUtil.setMarginTop(linearLayout, resources.getDimensionPixelSize(R.dimen.kg_miuix_appcompat_guidance_dialog_button_vertical_margin));
        }
        float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_miuix_appcompat_dialog_title_text_size);
        if (textView != null) {
            textView.setTextSize(0, dimensionPixelSize);
        }
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.kg_miuix_appcompat_dialog_message_text_size);
        if (textView2 != null) {
            textView2.setTextSize(0, dimensionPixelSize2);
        }
        float dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.kg_miuix_appcompat_button_text_size);
        button.setTextSize(0, dimensionPixelSize3);
        button2.setTextSize(0, dimensionPixelSize3);
    }

    public static /* synthetic */ void adaptN8AlertDialog$default(AlertDialog alertDialog, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            z3 = false;
        }
        adaptN8AlertDialog(alertDialog, z, z2, z3);
    }

    public static final AlertDialog applyToAodAlertDialog(final Context context, CommonConfig commonConfig, ScreenshotSource screenshotSource, boolean z, final DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener, final DialogInterface.OnDismissListener onDismissListener, DialogInterface.OnCancelListener onCancelListener) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(screenshotSource, "screenshotSource");
        Intrinsics.checkNotNullParameter(positiveListener, "positiveListener");
        Intrinsics.checkNotNullParameter(negativeListener, "negativeListener");
        final AodDialogViewManager aodDialogViewManagerBuildDialogViewManager$default = buildDialogViewManager$default(context, commonConfig, screenshotSource, z, DialogType.TYPE_AOD, false, 32, null);
        AlertDialog alertDialogCreate = new AlertDialogBuilder(context, 0, 2, null).setTitle(R.string.kg_apply_to_aod_alert_dialog_title).setView(aodDialogViewManagerBuildDialogViewManager$default.getRootView()).setNegativeButton(R.string.kg_apply_to_aod_alert_dialog_negative_text, negativeListener).setPositiveButton(R.string.kg_apply_to_aod_alert_dialog_positive_text, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.view.AlertDialogsKtKt$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AlertDialogsKtKt.applyToAodAlertDialog$lambda$0(context, positiveListener, dialogInterface, i);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.miui.keyguard.editor.view.AlertDialogsKtKt$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertDialogsKtKt.applyToAodAlertDialog$lambda$1(onDismissListener, aodDialogViewManagerBuildDialogViewManager$default, dialogInterface);
            }
        }).setOnCancelListener(onCancelListener).setButtonForceVertical(true).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        return alertDialogCreate;
    }

    public static /* synthetic */ AlertDialog applyToAodAlertDialog$default(Context context, CommonConfig commonConfig, ScreenshotSource screenshotSource, boolean z, DialogInterface.OnClickListener onClickListener, DialogInterface.OnClickListener onClickListener2, DialogInterface.OnDismissListener onDismissListener, DialogInterface.OnCancelListener onCancelListener, int i, Object obj) {
        if ((i & 64) != 0) {
            onDismissListener = null;
        }
        if ((i & 128) != 0) {
            onCancelListener = null;
        }
        return applyToAodAlertDialog(context, commonConfig, screenshotSource, z, onClickListener, onClickListener2, onDismissListener, onCancelListener);
    }

    public static final void applyToAodAlertDialog$lambda$0(Context context, DialogInterface.OnClickListener onClickListener, DialogInterface dialogInterface, int i) {
        DeviceUtil.INSTANCE.enableAod(context);
        onClickListener.onClick(dialogInterface, i);
    }

    public static final void applyToAodAlertDialog$lambda$1(DialogInterface.OnDismissListener onDismissListener, AodDialogViewManager aodDialogViewManager, DialogInterface dialogInterface) {
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
        aodDialogViewManager.onDismiss();
    }

    public static final AodDialogViewManager buildDialogViewManager(final Context context, CommonConfig commonConfig, ScreenshotSource screenshotSource, boolean z, DialogType dialogType, boolean z2) {
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.kg_aod_alert_dialog_content_view, (ViewGroup) null);
        Intrinsics.checkNotNull(viewInflate, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) viewInflate;
        final AodDialogViewManager aodDialogViewManager = new AodDialogViewManager();
        aodDialogViewManager.setRootView(viewGroup);
        final Point point = new Point();
        Display display = context.getDisplay();
        if (display != null) {
            display.getRealSize(point);
            float f = context.getResources().getFloat(R.dimen.kg_template_view_aod_dialog_preview_scale);
            final Ref.FloatRef floatRef = new Ref.FloatRef();
            floatRef.element = f;
            final Ref.FloatRef floatRef2 = new Ref.FloatRef();
            floatRef2.element = f;
            int i = point.x;
            int i2 = (int) (i * floatRef.element);
            int i3 = point.y;
            int i4 = (int) (i3 * f);
            if (i2 > i4) {
                i2 = (int) (i3 * f);
                float f2 = i2;
                int i5 = (int) ((i3 / i) * f2);
                floatRef.element = f2 / i;
                floatRef2.element = i5 / i3;
                i4 = i5;
            }
            ImageView imageView = (ImageView) viewGroup.findViewById(R.id.template_container);
            Intrinsics.checkNotNull(imageView);
            initAodDialogPreviewContainer(imageView, i2, i4);
            Bitmap currentScreenshot = ScreenshotSourceKt.getCurrentScreenshot(screenshotSource, context);
            if (currentScreenshot != null) {
                imageView.setImageBitmap(currentScreenshot);
            } else {
                imageView.setBackgroundResource(R.drawable.kg_apply_dialog_item_placeholder);
            }
            final FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.aod_container);
            Intrinsics.checkNotNull(frameLayout);
            initAodDialogPreviewContainer(frameLayout, i2, i4);
            if (dialogType == DialogType.TYPE_AOD) {
                FrameLayout frameLayout2 = new FrameLayout(context);
                initAodDialogPreview(frameLayout2, floatRef.element, floatRef2.element, point.x, point.y);
                AodClockViews aodClockViewsCreate = AodViewFactory.INSTANCE.create(context, commonConfig);
                if (aodClockViewsCreate != null) {
                    frameLayout2.addView(aodClockViewsCreate.getRootView());
                    aodDialogViewManager.setAodPreview(aodClockViewsCreate.getRootView());
                    aodDialogViewManager.getAodClockViews().addAll(aodClockViewsCreate.getClockViews());
                    aodClockViewsCreate.setRootView(null);
                    aodClockViewsCreate.getClockViews().clear();
                }
                frameLayout2.setBackgroundColor(-16777216);
                frameLayout.addView(frameLayout2);
                return aodDialogViewManager;
            }
            boolean z3 = ScreenshotSourceKt.getCurrentWallpaperScreenshot(screenshotSource, context) == null;
            Function2 function2 = new Function2() { // from class: com.miui.keyguard.editor.view.AlertDialogsKtKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return AlertDialogsKtKt.buildDialogViewManager$lambda$8(floatRef, floatRef2, point, frameLayout, aodDialogViewManager, context, (View) obj, ((Boolean) obj2).booleanValue());
                }
            };
            if (z3) {
                BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), Dispatchers.getMain(), null, new AnonymousClass3(context, screenshotSource, z2, commonConfig, function2, null), 2, null);
                return aodDialogViewManager;
            }
            View viewCreate = MiuiHomeViewFactory.INSTANCE.create(context, commonConfig, screenshotSource, z2);
            if (viewCreate != null) {
                if (viewCreate.findViewById(R.id.kg_home_preview_wallpaper_layer) instanceof CombinedWallpaperView) {
                    Log.d("AlertDialogsKt", "buildDialogViewManager: init scale for real wallpaper view");
                    initAodDialogPreview(viewCreate, floatRef.element, floatRef2.element, point.x, point.y);
                }
                function2.invoke(viewCreate, Boolean.FALSE);
            }
        }
        return aodDialogViewManager;
    }

    public static /* synthetic */ AodDialogViewManager buildDialogViewManager$default(Context context, CommonConfig commonConfig, ScreenshotSource screenshotSource, boolean z, DialogType dialogType, boolean z2, int i, Object obj) {
        if ((i & 32) != 0) {
            z2 = false;
        }
        return buildDialogViewManager(context, commonConfig, screenshotSource, z, dialogType, z2);
    }

    public static final Unit buildDialogViewManager$lambda$8(Ref.FloatRef floatRef, Ref.FloatRef floatRef2, Point point, FrameLayout frameLayout, AodDialogViewManager aodDialogViewManager, Context context, View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "view");
        if (z) {
            initAodDialogPreview(view, floatRef.element, floatRef2.element, point.x, point.y);
        }
        frameLayout.addView(view);
        aodDialogViewManager.setAodPreview(view);
        frameLayout.addView(createFrameView(context));
        return Unit.INSTANCE;
    }

    public static final View createFrameView(Context context) {
        View view = new View(context);
        view.setBackgroundResource(R.drawable.kg_alert_dialog_background_item);
        return view;
    }

    public static final void initAodDialogPreview(View view, float f, float f2, int i, int i2) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        layoutParams.gravity = deviceUtil.isRtl(context) ? 8388613 : 8388611;
        view.setLayoutParams(layoutParams);
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        view.setScaleX(f);
        view.setScaleY(f2);
    }

    public static final void initAodDialogPreviewContainer(View view, int i, int i2) {
        ViewUtil.INSTANCE.setSmoothCornerEnabled(view, true);
        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(view.getResources().getDimension(R.dimen.kg_template_view_alert_dialog_preview_radius));
        view.setClipToOutline(true);
        view.setOutlineProvider(roundOutlineProvider);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        view.setLayoutParams(layoutParams);
    }

    public static final boolean isDarkMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getApplicationContext().getResources().getConfiguration().isNightModeActive();
    }
}
