package com.miui.maml;

import com.miui.maml.util.MamlLog;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
public class FramerateTokenList {
    private static final String LOG_TAG = "FramerateTokenList";
    private float mCurFramerate;
    private FramerateChangeListener mFramerateChangeListener;
    private ArrayList<FramerateToken> mList = new ArrayList<>();

    public interface FramerateChangeListener {
        void onFrameRateChage(float f, float f2);
    }

    public class FramerateToken {
        public float mFramerate;
        public String mName;

        public FramerateToken(String str) {
            this.mName = str;
        }

        public float getFramerate() {
            return this.mFramerate;
        }

        public void requestFramerate(float f) {
            if (this.mFramerate != f) {
                if (FramerateTokenList.this.mFramerateChangeListener != null) {
                    FramerateTokenList.this.mFramerateChangeListener.onFrameRateChage(this.mFramerate, f);
                }
                this.mFramerate = f;
                FramerateTokenList.this.onChange();
            }
        }
    }

    public FramerateTokenList() {
    }

    public FramerateTokenList(FramerateChangeListener framerateChangeListener) {
        this.mFramerateChangeListener = framerateChangeListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChange() {
        float f;
        synchronized (this.mList) {
            try {
                Iterator<FramerateToken> it = this.mList.iterator();
                f = 0.0f;
                while (it.hasNext()) {
                    float f2 = it.next().mFramerate;
                    if (f2 > f) {
                        f = f2;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mCurFramerate = f;
        MamlLog.d(LOG_TAG, "Frame rate changed, current frame rate is " + this.mCurFramerate);
    }

    public void clear() {
        synchronized (this.mList) {
            this.mList.clear();
        }
    }

    public FramerateToken createToken(String str) {
        MamlLog.d(LOG_TAG, "createToken: " + str);
        FramerateToken framerateToken = new FramerateToken(str);
        synchronized (this.mList) {
            this.mList.add(framerateToken);
        }
        return framerateToken;
    }

    public float getFramerate() {
        return this.mCurFramerate;
    }

    public void removeToken(FramerateToken framerateToken) {
        synchronized (this.mList) {
            this.mList.remove(framerateToken);
        }
    }
}
