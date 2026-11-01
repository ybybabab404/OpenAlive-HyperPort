package androidx.window.area;

import android.app.Activity;
import android.os.Binder;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.window.area.utils.DeviceUtils;
import androidx.window.core.BuildConfig;
import androidx.window.core.ExperimentalWindowApi;
import androidx.window.core.VerificationMode;
import androidx.window.extensions.area.ExtensionWindowAreaPresentation;
import androidx.window.extensions.area.ExtensionWindowAreaStatus;
import androidx.window.extensions.area.WindowAreaComponent;
import androidx.window.extensions.core.util.function.Consumer;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;
import java.util.HashMap;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

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
/* JADX INFO: compiled from: WindowAreaControllerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@ExperimentalWindowApi
@Metadata
public final class WindowAreaControllerImpl implements WindowAreaController {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = Reflection.getOrCreateKotlinClass(WindowAreaControllerImpl.class).getSimpleName();
    public WindowAreaCapability.Status currentRearDisplayModeStatus;
    public WindowAreaCapability.Status currentRearDisplayPresentationStatus;
    public final HashMap currentWindowAreaInfoMap;
    public Consumer rearDisplaySessionConsumer;
    public final int vendorApiLevel;
    public final WindowAreaComponent windowAreaComponent;

    /* JADX INFO: compiled from: WindowAreaControllerImpl.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: WindowAreaControllerImpl.kt */
    @Metadata
    public static final class RearDisplayPresentationSessionConsumer implements Consumer<Integer> {
        public final Executor executor;
        public int lastReportedSessionStatus;
        public final WindowAreaComponent windowAreaComponent;
        public final WindowAreaPresentationSessionCallback windowAreaPresentationSessionCallback;

        public RearDisplayPresentationSessionConsumer(Executor executor, WindowAreaPresentationSessionCallback windowAreaPresentationSessionCallback, WindowAreaComponent windowAreaComponent) {
            Intrinsics.checkNotNullParameter(executor, "executor");
            Intrinsics.checkNotNullParameter(windowAreaPresentationSessionCallback, "windowAreaPresentationSessionCallback");
            Intrinsics.checkNotNullParameter(windowAreaComponent, "windowAreaComponent");
            this.executor = executor;
            this.windowAreaPresentationSessionCallback = windowAreaPresentationSessionCallback;
            this.windowAreaComponent = windowAreaComponent;
        }

        public static final void accept$lambda$0(int i, int i2, RearDisplayPresentationSessionConsumer this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (i == 0) {
                this$0.windowAreaPresentationSessionCallback.onSessionEnded(null);
                return;
            }
            if (i != 1) {
                if (i == 2) {
                    this$0.windowAreaPresentationSessionCallback.onContainerVisibilityChanged(true);
                    return;
                }
                Log.e(WindowAreaControllerImpl.TAG, "Invalid session state value received: " + i);
                return;
            }
            if (i2 == 2) {
                this$0.windowAreaPresentationSessionCallback.onContainerVisibilityChanged(false);
                return;
            }
            WindowAreaPresentationSessionCallback windowAreaPresentationSessionCallback = this$0.windowAreaPresentationSessionCallback;
            WindowAreaComponent windowAreaComponent = this$0.windowAreaComponent;
            ExtensionWindowAreaPresentation rearDisplayPresentation = windowAreaComponent.getRearDisplayPresentation();
            Intrinsics.checkNotNull(rearDisplayPresentation);
            windowAreaPresentationSessionCallback.onSessionStarted(new RearDisplayPresentationSessionPresenterImpl(windowAreaComponent, rearDisplayPresentation));
        }

        public void accept(final int i) {
            final int i2 = this.lastReportedSessionStatus;
            this.lastReportedSessionStatus = i;
            this.executor.execute(new Runnable() { // from class: androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowAreaControllerImpl.RearDisplayPresentationSessionConsumer.accept$lambda$0(i, i2, this);
                }
            });
        }

        @Override // androidx.window.extensions.core.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Integer num) {
            accept(num.intValue());
        }
    }

    /* JADX INFO: compiled from: WindowAreaControllerImpl.kt */
    @Metadata
    @SourceDebugExtension
    public static final class RearDisplaySessionConsumer implements Consumer<Integer> {
        public final WindowAreaSessionCallback appCallback;
        public final Executor executor;
        public final WindowAreaComponent extensionsComponent;
        public WindowAreaSession session;

        public RearDisplaySessionConsumer(Executor executor, WindowAreaSessionCallback appCallback, WindowAreaComponent extensionsComponent) {
            Intrinsics.checkNotNullParameter(executor, "executor");
            Intrinsics.checkNotNullParameter(appCallback, "appCallback");
            Intrinsics.checkNotNullParameter(extensionsComponent, "extensionsComponent");
            this.executor = executor;
            this.appCallback = appCallback;
            this.extensionsComponent = extensionsComponent;
        }

        public static final void onSessionFinished$lambda$2(RearDisplaySessionConsumer this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.appCallback.onSessionEnded(null);
        }

        public static final void onSessionStarted$lambda$1$lambda$0(RearDisplaySessionConsumer this$0, WindowAreaSession it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(it, "$it");
            this$0.appCallback.onSessionStarted(it);
        }

        public void accept(int i) {
            if (i == 0) {
                onSessionFinished();
                return;
            }
            if (i == 1) {
                onSessionStarted();
                return;
            }
            if (BuildConfig.INSTANCE.getVerificationMode() == VerificationMode.STRICT) {
                Log.d(WindowAreaControllerImpl.TAG, "Received an unknown session status value: " + i);
            }
            onSessionFinished();
        }

        @Override // androidx.window.extensions.core.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Integer num) {
            accept(num.intValue());
        }

        public final void onSessionFinished() {
            this.session = null;
            this.executor.execute(new Runnable() { // from class: androidx.window.area.WindowAreaControllerImpl$RearDisplaySessionConsumer$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    WindowAreaControllerImpl.RearDisplaySessionConsumer.onSessionFinished$lambda$2(this.f$0);
                }
            });
        }

        public final void onSessionStarted() {
            final RearDisplaySessionImpl rearDisplaySessionImpl = new RearDisplaySessionImpl(this.extensionsComponent);
            this.session = rearDisplaySessionImpl;
            this.executor.execute(new Runnable() { // from class: androidx.window.area.WindowAreaControllerImpl$RearDisplaySessionConsumer$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowAreaControllerImpl.RearDisplaySessionConsumer.onSessionStarted$lambda$1$lambda$0(this.f$0, rearDisplaySessionImpl);
                }
            });
        }
    }

    public Flow getWindowAreaInfos() {
        return FlowKt.callbackFlow(new WindowAreaControllerImpl$windowAreaInfos$1(this, null));
    }

    public final boolean shouldRemoveWindowAreaInfo(WindowAreaInfo windowAreaInfo) {
        for (Object obj : windowAreaInfo.getCapabilityMap$window_release().values()) {
            Intrinsics.checkNotNullExpressionValue(obj, "windowAreaInfo.capabilityMap.values");
            if (!Intrinsics.areEqual(((WindowAreaCapability) obj).getStatus(), WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNSUPPORTED)) {
                return false;
            }
        }
        return true;
    }

    public final void startRearDisplayMode(Activity activity, Executor executor, WindowAreaSessionCallback windowAreaSessionCallback) {
        if (Intrinsics.areEqual(this.currentRearDisplayModeStatus, WindowAreaCapability.Status.WINDOW_AREA_STATUS_ACTIVE)) {
            windowAreaSessionCallback.onSessionEnded(new IllegalStateException("The WindowArea feature is currently active, WindowAreaInfo#getActiveSessioncan be used to get an instance of the current active session"));
        } else {
            if (!Intrinsics.areEqual(this.currentRearDisplayModeStatus, WindowAreaCapability.Status.WINDOW_AREA_STATUS_AVAILABLE)) {
                windowAreaSessionCallback.onSessionEnded(new IllegalStateException("The WindowArea feature is currently not available to be entered"));
                return;
            }
            RearDisplaySessionConsumer rearDisplaySessionConsumer = new RearDisplaySessionConsumer(executor, windowAreaSessionCallback, this.windowAreaComponent);
            this.rearDisplaySessionConsumer = rearDisplaySessionConsumer;
            this.windowAreaComponent.startRearDisplaySession(activity, rearDisplaySessionConsumer);
        }
    }

    public final void startRearDisplayPresentationMode(Activity activity, Executor executor, WindowAreaPresentationSessionCallback windowAreaPresentationSessionCallback) {
        if (!Intrinsics.areEqual(this.currentRearDisplayPresentationStatus, WindowAreaCapability.Status.WINDOW_AREA_STATUS_AVAILABLE)) {
            windowAreaPresentationSessionCallback.onSessionEnded(new IllegalStateException("The WindowArea feature is currently not available to be entered"));
        } else {
            WindowAreaComponent windowAreaComponent = this.windowAreaComponent;
            windowAreaComponent.startRearDisplayPresentationSession(activity, new RearDisplayPresentationSessionConsumer(executor, windowAreaPresentationSessionCallback, windowAreaComponent));
        }
    }

    public final void updateRearDisplayAvailability(int i) {
        WindowMetrics windowMetricsFromDisplayMetrics$window_release;
        if (this.vendorApiLevel >= 3) {
            WindowMetricsCalculator.Companion companion = WindowMetricsCalculator.Companion;
            DisplayMetrics rearDisplayMetrics = this.windowAreaComponent.getRearDisplayMetrics();
            Intrinsics.checkNotNullExpressionValue(rearDisplayMetrics, "windowAreaComponent.rearDisplayMetrics");
            windowMetricsFromDisplayMetrics$window_release = companion.fromDisplayMetrics$window_release(rearDisplayMetrics);
        } else {
            DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
            String MANUFACTURER = Build.MANUFACTURER;
            Intrinsics.checkNotNullExpressionValue(MANUFACTURER, "MANUFACTURER");
            String MODEL = Build.MODEL;
            Intrinsics.checkNotNullExpressionValue(MODEL, "MODEL");
            DisplayMetrics rearDisplayMetrics$window_release = deviceUtils.getRearDisplayMetrics$window_release(MANUFACTURER, MODEL);
            if (rearDisplayMetrics$window_release == null) {
                throw new IllegalArgumentException("DeviceUtils rear display metrics entry should not be null");
            }
            windowMetricsFromDisplayMetrics$window_release = WindowMetricsCalculator.Companion.fromDisplayMetrics$window_release(rearDisplayMetrics$window_release);
        }
        WindowAreaCapability.Status statusTranslate$window_release = WindowAreaAdapter.INSTANCE.translate$window_release(i);
        this.currentRearDisplayModeStatus = statusTranslate$window_release;
        updateRearDisplayWindowArea(WindowAreaCapability.Operation.OPERATION_TRANSFER_ACTIVITY_TO_AREA, statusTranslate$window_release, windowMetricsFromDisplayMetrics$window_release);
    }

    public final void updateRearDisplayPresentationAvailability(ExtensionWindowAreaStatus extensionWindowAreaStatus) {
        this.currentRearDisplayPresentationStatus = WindowAreaAdapter.INSTANCE.translate$window_release(extensionWindowAreaStatus.getWindowAreaStatus());
        WindowMetricsCalculator.Companion companion = WindowMetricsCalculator.Companion;
        DisplayMetrics windowAreaDisplayMetrics = extensionWindowAreaStatus.getWindowAreaDisplayMetrics();
        Intrinsics.checkNotNullExpressionValue(windowAreaDisplayMetrics, "extensionWindowAreaStatus.windowAreaDisplayMetrics");
        updateRearDisplayWindowArea(WindowAreaCapability.Operation.OPERATION_PRESENT_ON_AREA, this.currentRearDisplayPresentationStatus, companion.fromDisplayMetrics$window_release(windowAreaDisplayMetrics));
    }

    public final void updateRearDisplayWindowArea(WindowAreaCapability.Operation operation, WindowAreaCapability.Status status, WindowMetrics windowMetrics) {
        WindowAreaInfo windowAreaInfo = (WindowAreaInfo) this.currentWindowAreaInfoMap.get("WINDOW_AREA_REAR_DISPLAY");
        if (!Intrinsics.areEqual(status, WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNSUPPORTED)) {
            if (windowAreaInfo == null) {
                windowAreaInfo = new WindowAreaInfo(windowMetrics, WindowAreaInfo.Type.TYPE_REAR_FACING, new Binder("WINDOW_AREA_REAR_DISPLAY"), this.windowAreaComponent);
            }
            windowAreaInfo.getCapabilityMap$window_release().put(operation, new WindowAreaCapability(operation, status));
            windowAreaInfo.setMetrics(windowMetrics);
            this.currentWindowAreaInfoMap.put("WINDOW_AREA_REAR_DISPLAY", windowAreaInfo);
            return;
        }
        if (windowAreaInfo != null) {
            if (shouldRemoveWindowAreaInfo(windowAreaInfo)) {
                this.currentWindowAreaInfoMap.remove("WINDOW_AREA_REAR_DISPLAY");
            } else {
                windowAreaInfo.getCapabilityMap$window_release().put(operation, new WindowAreaCapability(operation, status));
            }
        }
    }
}
