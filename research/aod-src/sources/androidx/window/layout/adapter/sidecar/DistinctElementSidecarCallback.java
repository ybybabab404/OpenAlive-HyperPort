package androidx.window.layout.adapter.sidecar;

import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.window.sidecar.SidecarDeviceState;
import androidx.window.sidecar.SidecarInterface;
import androidx.window.sidecar.SidecarWindowLayoutInfo;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class DistinctElementSidecarCallback implements SidecarInterface.SidecarCallback {
    public final SidecarAdapter mAdapter;
    public final SidecarInterface.SidecarCallback mCallback;
    public SidecarDeviceState mLastDeviceState;
    public final Object mLock = new Object();
    public final Map mActivityWindowLayoutInfo = new WeakHashMap();

    public DistinctElementSidecarCallback(SidecarAdapter sidecarAdapter, SidecarInterface.SidecarCallback sidecarCallback) {
        this.mAdapter = sidecarAdapter;
        this.mCallback = sidecarCallback;
    }

    public void onDeviceStateChanged(@NonNull SidecarDeviceState sidecarDeviceState) {
        if (sidecarDeviceState == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mAdapter.isEqualSidecarDeviceState(this.mLastDeviceState, sidecarDeviceState)) {
                    return;
                }
                this.mLastDeviceState = sidecarDeviceState;
                this.mCallback.onDeviceStateChanged(sidecarDeviceState);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onWindowLayoutChanged(@NonNull IBinder iBinder, @NonNull SidecarWindowLayoutInfo sidecarWindowLayoutInfo) {
        synchronized (this.mLock) {
            try {
                if (this.mAdapter.isEqualSidecarWindowLayoutInfo((SidecarWindowLayoutInfo) this.mActivityWindowLayoutInfo.get(iBinder), sidecarWindowLayoutInfo)) {
                    return;
                }
                this.mActivityWindowLayoutInfo.put(iBinder, sidecarWindowLayoutInfo);
                this.mCallback.onWindowLayoutChanged(iBinder, sidecarWindowLayoutInfo);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
