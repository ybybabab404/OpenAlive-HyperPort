package miuix.internal.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class AsyncInflateLayoutManager {
    public static final AsyncInflateLayoutManager MANAGER = new AsyncInflateLayoutManager();
    public final HashMap mListTypeMap = new HashMap();
    public boolean isEnabled = true;
    public boolean isEnabledLog = false;
    public boolean isCachedEnable = false;
    public final AsyncLayoutInflater.OnInflateFinishedListener mListener = new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: miuix.internal.util.AsyncInflateLayoutManager.1
        @Override // miuix.internal.util.AsyncLayoutInflater.OnInflateFinishedListener
        public void onInflateFinished(View view, int i, ViewGroup viewGroup) {
            List arrayList = (List) AsyncInflateLayoutManager.this.mListTypeMap.get(Integer.valueOf(i));
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            if (AsyncInflateLayoutManager.this.isEnabledLog) {
                Log.i("AsyncInflateManager", "async create view is success.");
            }
            arrayList.add(view);
            AsyncInflateLayoutManager.this.mListTypeMap.put(Integer.valueOf(i), arrayList);
        }
    };

    public static class InflateRequest {
    }

    public static AsyncInflateLayoutManager getInstance() {
        return MANAGER;
    }

    public View inflateViewById(Integer num, Context context, ViewGroup viewGroup, boolean z) {
        if (viewGroup == null || context == null) {
            return null;
        }
        List list = (List) this.mListTypeMap.get(num);
        if (list == null || list.isEmpty()) {
            if (this.isEnabledLog) {
                Log.i("AsyncInflateManager", "inflateCacheById is null.");
            }
            return LayoutInflater.from(context).inflate(num.intValue(), viewGroup, z);
        }
        View view = this.isCachedEnable ? (View) list.get(0) : (View) list.remove(0);
        if (list.isEmpty()) {
            this.mListTypeMap.remove(num);
        }
        if (this.isEnabledLog) {
            Log.i("AsyncInflateManager", "inflateCacheById is ok.");
        }
        return view;
    }

    public View inflateViewById(Integer num, ViewGroup viewGroup, boolean z) {
        if (viewGroup == null) {
            return null;
        }
        List list = (List) this.mListTypeMap.get(num);
        if (list == null || list.isEmpty()) {
            if (this.isEnabledLog) {
                Log.i("AsyncInflateManager", "inflateCacheById is null.");
            }
            return LayoutInflater.from(viewGroup.getContext()).inflate(num.intValue(), viewGroup, z);
        }
        View view = (View) list.remove(0);
        if (list.isEmpty()) {
            this.mListTypeMap.remove(num);
        }
        if (this.isEnabledLog) {
            Log.i("AsyncInflateManager", "inflateCacheById is ok.");
        }
        return view;
    }
}
