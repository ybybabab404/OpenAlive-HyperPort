package miuix.appcompat.app.floatingactivity.multiapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes2.dex */
public class FloatingService extends Service {
    public final RemoteCallbackList mServiceNotify = new RemoteCallbackList();
    public final SparseArray mNotifyIdentity = new SparseArray();
    public final ConcurrentHashMap mServiceNotifyMap = new ConcurrentHashMap();
    public final IFloatingService mBinder = new IFloatingService.Stub() { // from class: miuix.appcompat.app.floatingactivity.multiapp.FloatingService.1
        @Override // miuix.appcompat.app.floatingactivity.multiapp.IFloatingService
        public Bundle callServiceMethod(int i, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            int i2 = 0;
            if (i == 6) {
                bundle2.putInt(String.valueOf(6), FloatingService.this.getPageCount(bundle != null ? bundle.getInt("key_task_id", 0) : 0));
                return bundle2;
            }
            if (i == 7) {
                String strFindPreviousIdentity = FloatingService.this.findPreviousIdentity(bundle.getString("key_request_identity"), bundle.getInt("key_task_id", 0));
                int iBeginBroadcast = FloatingService.this.mServiceNotify.beginBroadcast();
                while (i2 < iBeginBroadcast) {
                    if (TextUtils.equals(strFindPreviousIdentity, FloatingService.this.mServiceNotify.getBroadcastCookie(i2).toString())) {
                        ((IServiceNotify) FloatingService.this.mServiceNotify.getBroadcastItem(i2)).notifyFromService(8, bundle);
                        break;
                    }
                    i2++;
                }
                FloatingService.this.mServiceNotify.finishBroadcast();
                return bundle2;
            }
            String string = null;
            if (i == 9) {
                if (bundle != null) {
                    i2 = bundle.getInt("key_task_id", 0);
                    string = bundle.getString("key_request_identity");
                }
                bundle2.putBoolean("check_finishing", FloatingService.this.checkFinishing(i, string, i2));
                return bundle2;
            }
            if (i != 10) {
                FloatingService.this.onMethodCall(i);
                return bundle2;
            }
            if (bundle != null) {
                i2 = bundle.getInt("key_task_id", 0);
                string = bundle.getString("execute_slide");
            }
            FloatingService.this.notifyPreviousSlide(i, string, i2);
            return bundle2;
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0022  */
        public final Pair parseIdentity(String str) {
            String str2;
            int i = 0;
            if (TextUtils.isEmpty(str)) {
                str2 = null;
            } else {
                String[] strArrSplit = str.split(":");
                if (strArrSplit.length == 1) {
                    str2 = strArrSplit[0];
                } else if (strArrSplit.length >= 2) {
                    String str3 = strArrSplit[0];
                    try {
                        i = Integer.parseInt(strArrSplit[1]);
                    } catch (Exception unused) {
                    }
                    str2 = str3;
                } else {
                    str2 = null;
                }
            }
            return new Pair(str2, Integer.valueOf(FloatingService.this.getCompatTaskId(i)));
        }

        @Override // miuix.appcompat.app.floatingactivity.multiapp.IFloatingService
        public int registerServiceNotify(IServiceNotify iServiceNotify, String str) {
            Pair identity = parseIdentity(str);
            String str2 = (String) identity.first;
            int iIntValue = ((Integer) identity.second).intValue();
            LinkedList linkedList = (LinkedList) FloatingService.this.mNotifyIdentity.get(iIntValue);
            if (linkedList == null) {
                linkedList = new LinkedList();
                FloatingService.this.mNotifyIdentity.put(iIntValue, linkedList);
            } else {
                linkedList.remove(str2);
            }
            FloatingService.this.mServiceNotify.unregister(iServiceNotify);
            int registeredCallbackCount = FloatingService.this.mServiceNotify.getRegisteredCallbackCount();
            FloatingService.this.mServiceNotify.register(iServiceNotify, str2);
            linkedList.add(str2);
            return registeredCallbackCount;
        }

        @Override // miuix.appcompat.app.floatingactivity.multiapp.IFloatingService
        public void unregisterServiceNotify(IServiceNotify iServiceNotify, String str) {
            Pair identity = parseIdentity(str);
            String str2 = (String) identity.first;
            int iIntValue = ((Integer) identity.second).intValue();
            LinkedList linkedList = (LinkedList) FloatingService.this.mNotifyIdentity.get(iIntValue);
            if (linkedList != null) {
                linkedList.remove(str2);
                if (linkedList.isEmpty()) {
                    FloatingService.this.mNotifyIdentity.remove(iIntValue);
                }
            }
            FloatingService.this.mServiceNotify.unregister(iServiceNotify);
            FloatingService.this.mServiceNotifyMap.remove(str2);
        }

        @Override // miuix.appcompat.app.floatingactivity.multiapp.IFloatingService
        public void upDateRemoteActivityInfo(String str, int i) {
            FloatingService.this.mServiceNotifyMap.put((String) parseIdentity(str).first, Integer.valueOf(i));
        }
    };

    public final boolean checkFinishing(int i, String str, int i2) {
        boolean z = false;
        try {
            if (str == null) {
                return false;
            }
            try {
                int iBeginBroadcast = this.mServiceNotify.beginBroadcast();
                String strFindNextIdentity = findNextIdentity(str, i2);
                for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
                    if (TextUtils.equals(strFindNextIdentity, this.mServiceNotify.getBroadcastCookie(i3).toString())) {
                        z = ((IServiceNotify) this.mServiceNotify.getBroadcastItem(i3)).notifyFromService(i, null).getBoolean("check_finishing");
                        break;
                    }
                }
                return z;
            } catch (RemoteException e) {
                Log.w("FloatingService", "checkFinishing is faulty", e);
                return false;
            }
        } finally {
            this.mServiceNotify.finishBroadcast();
        }
    }

    public final String findNextIdentity(String str, int i) {
        Integer num = (Integer) this.mServiceNotifyMap.get(str);
        int iIntValue = num == null ? -1 : num.intValue() + 1;
        for (String str2 : this.mServiceNotifyMap.keySet()) {
            Integer num2 = (Integer) this.mServiceNotifyMap.get(str2);
            if (num2 != null && num2.intValue() == iIntValue) {
                return str2;
            }
        }
        LinkedList<String> linkedList = (LinkedList) this.mNotifyIdentity.get(getCompatTaskId(i));
        if (linkedList == null) {
            return null;
        }
        boolean zEquals = false;
        for (String str3 : linkedList) {
            if (zEquals) {
                return str3;
            }
            zEquals = TextUtils.equals(str, str3);
        }
        return null;
    }

    public final String findPreviousIdentity(String str, int i) {
        LinkedList<String> linkedList = (LinkedList) this.mNotifyIdentity.get(getCompatTaskId(i));
        String str2 = null;
        if (linkedList != null) {
            for (String str3 : linkedList) {
                if (TextUtils.equals(str, str3)) {
                    break;
                }
                str2 = str3;
            }
        }
        return str2;
    }

    public final int getCompatTaskId(int i) {
        return (i != 0 || this.mNotifyIdentity.size() <= 0) ? i : this.mNotifyIdentity.keyAt(0);
    }

    public final int getPageCount(int i) {
        LinkedList linkedList = (LinkedList) this.mNotifyIdentity.get(getCompatTaskId(i));
        if (linkedList != null) {
            return linkedList.size();
        }
        return 0;
    }

    public final void notifyPreviousSlide(int i, String str, int i2) {
        if (str == null) {
            return;
        }
        int iBeginBroadcast = this.mServiceNotify.beginBroadcast();
        String strFindPreviousIdentity = findPreviousIdentity(str, getCompatTaskId(i2));
        for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
            if (TextUtils.equals(strFindPreviousIdentity, this.mServiceNotify.getBroadcastCookie(i3).toString())) {
                ((IServiceNotify) this.mServiceNotify.getBroadcastItem(i3)).notifyFromService(i, null);
                break;
            }
        }
        this.mServiceNotify.finishBroadcast();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder.asBinder();
    }

    public final void onMethodCall(int i) {
        int iBeginBroadcast = this.mServiceNotify.beginBroadcast();
        for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
            ((IServiceNotify) this.mServiceNotify.getBroadcastItem(i2)).notifyFromService(i, null);
        }
        this.mServiceNotify.finishBroadcast();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        stopSelf();
        return super.onUnbind(intent);
    }
}
