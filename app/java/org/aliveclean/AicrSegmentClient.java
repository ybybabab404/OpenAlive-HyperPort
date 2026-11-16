package org.aliveclean;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import org.aliveclean.aicr.AicrStatus;
import org.aliveclean.aicr.IAiCrCoreService;
import org.aliveclean.aicr.ICoreServiceCallback;
import org.aliveclean.aicr.IImageSegmentService;
import org.aliveclean.aicr.SegmentResult;
import org.aliveclean.aicr.VisionAttribute;

/**
 * 通过 Binder 复用小米自带的图像主体分割（算法与模型都在 com.xiaomi.aicr 进程里，本 APK 不携带）。
 *
 * 链路（与 MIUI 自己完全一致，见 com.miui.keyguard.editor.utils.segment.SegmentTask）：
 *   bindService(com.xiaomi.aicr/.access.AiCrCoreService)
 *   -> IAiCrCoreService.getPluginBinder(cb, address, "com.xiaomi.aicr.plugin.IImageSegmentService", "5.0", status)
 *   -> IImageSegmentService.segment(bitmap, new VisionAttribute(false, maxArea))
 *   -> SegmentResult.getBitmaps().get(0)
 *
 * 注意：本 ROM 的 SegmentPlugin.depthSegmentMainFunc() 直接返回 null（未实现），所以不要依赖它；
 * segment()/segmentMainFunc() 只返回主体蒙版（bitmaps/rects/contours），不含景深数据。
 */
final class AicrSegmentClient implements ServiceConnection, AutoCloseable {
    private static final String TAG = "AliveClean";
    private static final String PACKAGE = "com.xiaomi.aicr";
    private static final String SERVICE = "com.xiaomi.aicr.access.AiCrCoreService";
    private static final String PLUGIN = "com.xiaomi.aicr.plugin.IImageSegmentService";
    private static final String MIN_VERSION = "5.0";
    private static final String ADDRESS = "alivehs/" + Integer.toHexString(System.identityHashCode(AicrSegmentClient.class));

    private final Object lock = new Object();
    private final Context context;
    private IAiCrCoreService core;
    private IImageSegmentService plugin;
    private int status = AicrStatus.SERVICE_NOT_CONNECTED;
    private boolean bound;
    private boolean connected;
    private boolean downloading;

    private AicrSegmentClient(Context context) { this.context = context.getApplicationContext(); }

    /**
     * 在调用线程上同步执行一次主体分割。必须在后台线程调用（Binder 往返）。
     *
     * @param combine 是否合并多个主体（VisionAttribute.extractCombinedSubject）
     * @param maxArea 是否把最大连通区域当唯一主体（MIUI 锁屏编辑器用 true）
     * @return 主体蒙版位图；不可用时返回 null 并写日志
     */
    static Bitmap subjectMask(Context context, Bitmap photo, boolean combine, boolean maxArea, long timeoutMs) {
        if (photo == null || photo.isRecycled()) return null;
        AicrSegmentClient client = new AicrSegmentClient(context);
        try {
            if (!client.connect(timeoutMs)) {
                AliveLog.i(TAG, "aicr segment unavailable: " + AicrStatus.name(client.status));
                return null;
            }
            SegmentResult result = client.segment(photo, combine, maxArea);
            if (result == null) {
                AliveLog.i(TAG, "aicr segment returned null");
                return null;
            }
            Bitmap mask = result.first();
            AliveLog.i(TAG, "aicr segment ok mask=" + (mask == null ? "null" : mask.getWidth() + "x" + mask.getHeight())
                    + " rects=" + (result.rects == null ? 0 : result.rects.size())
                    + " contours=" + (result.contours == null ? 0 : result.contours.size()));
            return mask;
        } catch (Throwable error) {
            AliveLog.w(TAG, "aicr segment failed", error);
            return null;
        } finally {
            client.close();
        }
    }

    private boolean connect(long timeoutMs) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PACKAGE, SERVICE));
        intent.putExtra("bind_type", "org.aliveclean");
        synchronized (lock) {
            try {
                bound = context.bindService(intent, this, Context.BIND_AUTO_CREATE);
            } catch (SecurityException error) {
                AliveLog.w(TAG, "aicr bindService denied", error);
                status = AicrStatus.PERMISSION_DENIED;
                return false;
            }
            if (!bound) {
                AliveLog.i(TAG, "aicr bindService returned false");
                status = AicrStatus.SERVICE_NOT_CONNECTED;
                return false;
            }
            long deadline = System.currentTimeMillis() + timeoutMs;
            while (!connected) {
                long left = deadline - System.currentTimeMillis();
                if (left <= 0) break;
                try {
                    lock.wait(left);
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            if (!connected || core == null) {
                if (status == AicrStatus.SERVICE_NOT_CONNECTED) status = AicrStatus.SERVICE_ERROR;
                return false;
            }
        }
        try {
            if (!core.isSupport(PLUGIN)) {
                AliveLog.i(TAG, "aicr does not support " + PLUGIN);
                status = AicrStatus.UNSUPPORTED_CAPABILITY;
                return false;
            }
            Bundle bundle = new Bundle();
            IBinder binder = core.getPluginBinder(new ICoreServiceCallback() {
                @Override protected void onDownload(IBinder ignored) {
                    synchronized (lock) { downloading = true; lock.notifyAll(); }
                }
            }, ADDRESS, PLUGIN, MIN_VERSION, bundle);
            int value = bundle.getInt("Status", AicrStatus.NOT_FOUND);
            if (binder == null || value != AicrStatus.OK) {
                status = value;
                AliveLog.i(TAG, "aicr plugin binder status=" + AicrStatus.name(value) + " downloading=" + downloading);
                return false;
            }
            plugin = IImageSegmentService.Proxy.asInterface(binder);
            if (plugin == null) {
                status = AicrStatus.SERVICE_ERROR;
                return false;
            }
            // 初始化是幂等的；失败不影响后续 segment（模型在 segment 时再加载）
            try {
                AliveLog.i(TAG, "aicr init cache=" + plugin.initAlgorithmCachePath()
                        + " person=" + plugin.initPersonModel()
                        + " version=" + plugin.getSegmentMainFuncVersion());
            } catch (Throwable error) {
                AliveLog.w(TAG, "aicr plugin init failed", error);
            }
            return true;
        } catch (Throwable error) {
            AliveLog.w(TAG, "aicr plugin lookup failed", error);
            status = AicrStatus.SERVICE_ERROR;
            return false;
        }
    }

    private SegmentResult segment(Bitmap photo, boolean combine, boolean maxArea) {
        if (plugin == null) return null;
        VisionAttribute attribute = new VisionAttribute(false, maxArea);
        attribute.extractCombinedSubject = combine;
        long start = System.currentTimeMillis();
        SegmentResult result = plugin.segment(photo, attribute);
        AliveLog.i(TAG, "aicr segment took " + (System.currentTimeMillis() - start) + "ms for "
                + photo.getWidth() + "x" + photo.getHeight());
        return result;
    }

    @Override public void onServiceConnected(ComponentName name, IBinder binder) {
        synchronized (lock) {
            core = IAiCrCoreService.Proxy.asInterface(binder);
            connected = binder != null;
            if (connected) status = AicrStatus.OK;
            lock.notifyAll();
        }
        AliveLog.i(TAG, "aicr connected=" + connected);
    }

    @Override public void onServiceDisconnected(ComponentName name) {
        synchronized (lock) {
            core = null;
            plugin = null;
            connected = false;
            status = AicrStatus.SERVICE_NOT_CONNECTED;
            lock.notifyAll();
        }
    }

    @Override public void close() {
        synchronized (lock) {
            if (core != null) {
                try { core.releaseConnect(ADDRESS); } catch (Throwable ignored) { }
            }
            if (bound) {
                try { context.unbindService(this); } catch (Throwable ignored) { }
                bound = false;
            }
            connected = false;
            core = null;
            plugin = null;
        }
    }
}
