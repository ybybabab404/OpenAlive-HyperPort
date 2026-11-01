package com.miui.maml;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.Utils;

/* JADX INFO: loaded from: classes2.dex */
public class FancyDrawable extends MamlDrawable implements RendererController.IRenderable {
    private static final String LOG_TAG = "FancyDrawable";
    private static final String QUIET_IMAGE_NAME = "quietImage.png";
    private static final int RENDER_TIMEOUT = 100;
    private static final String START_IMAGE_NAME = "startImage.png";
    private static final String USE_QUIET_IMAGE_TAG = "useQuietImage";
    private boolean mPaused;
    private Drawable mQuietDrawable;
    private RendererCore mRendererCore;
    private Drawable mStartDrawable;
    private boolean mTimeOut;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Object mPauseLock = new Object();
    private final Runnable mRenderTimeout = new Runnable() { // from class: com.miui.maml.FancyDrawable.1
        @Override // java.lang.Runnable
        public void run() {
            FancyDrawable.this.mTimeOut = true;
            FancyDrawable.this.doPause();
        }
    };

    public static final class FancyDrawableState extends MamlDrawable.MamlDrawableState {
        RendererCore mRendererCore;

        public FancyDrawableState(RendererCore rendererCore) {
            this.mRendererCore = rendererCore;
        }

        @Override // com.miui.maml.MamlDrawable.MamlDrawableState
        public MamlDrawable createDrawable() {
            return new FancyDrawable(this.mRendererCore);
        }
    }

    public FancyDrawable(RendererCore rendererCore) {
        init(rendererCore);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doPause() {
        synchronized (this.mPauseLock) {
            try {
                if (this.mPaused) {
                    return;
                }
                logd("doPause: ");
                this.mPaused = true;
                this.mRendererCore.pauseRenderable(this);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void doResume() {
        synchronized (this.mPauseLock) {
            try {
                if (this.mPaused) {
                    logd("doResume: ");
                    this.mPaused = false;
                    this.mRendererCore.resumeRenderable(this);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void init(RendererCore rendererCore) {
        rendererCore.getClass();
        this.mState = new FancyDrawableState(rendererCore);
        this.mRendererCore = rendererCore;
        rendererCore.addRenderable(this);
        setIntrinsicSize((int) this.mRendererCore.getRoot().getWidth(), (int) this.mRendererCore.getRoot().getHeight());
        ScreenContext context = this.mRendererCore.getRoot().getContext();
        Drawable drawable = context.mResourceManager.getDrawable(context.mContext.getResources(), QUIET_IMAGE_NAME);
        this.mQuietDrawable = drawable;
        if (drawable != null) {
            Drawable drawableMutate = drawable.mutate();
            this.mQuietDrawable = drawableMutate;
            drawableMutate.setBounds(0, 0, drawableMutate.getIntrinsicWidth(), this.mQuietDrawable.getIntrinsicHeight());
        }
        Drawable drawable2 = context.mResourceManager.getDrawable(context.mContext.getResources(), START_IMAGE_NAME);
        this.mStartDrawable = drawable2;
        if (drawable2 != null) {
            Drawable drawableMutate2 = drawable2.mutate();
            this.mStartDrawable = drawableMutate2;
            drawableMutate2.setBounds(0, 0, drawableMutate2.getIntrinsicWidth(), this.mStartDrawable.getIntrinsicHeight());
        }
    }

    private void logd(CharSequence charSequence) {
        MamlLog.d(LOG_TAG, ((Object) charSequence) + "  [" + toString() + "]");
    }

    @Override // com.miui.maml.MamlDrawable
    public void cleanUp() {
        logd("cleanUp: ");
        this.mRendererCore.removeRenderable(this);
    }

    @Override // com.miui.maml.RendererController.IRenderable
    public void doRender() {
        this.mHandler.removeCallbacks(this.mRenderTimeout);
        this.mHandler.postDelayed(this.mRenderTimeout, 100L);
        this.mHandler.post(this.mInvalidateSelf);
    }

    @Override // com.miui.maml.MamlDrawable
    public void drawIcon(Canvas canvas) {
        Drawable drawable;
        this.mHandler.removeCallbacks(this.mRenderTimeout);
        if (this.mTimeOut) {
            doResume();
            this.mTimeOut = false;
        }
        try {
            int iSave = canvas.save();
            canvas.translate(getBounds().left, getBounds().top);
            canvas.scale(this.mWidth / getRoot().getWidth(), this.mHeight / getRoot().getHeight(), 0.0f, 0.0f);
            if (Utils.getVariableNumber(USE_QUIET_IMAGE_TAG, this.mRendererCore.getRoot().getVariables()) <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE || (drawable = this.mQuietDrawable) == null) {
                this.mRendererCore.render(canvas);
            } else {
                drawable.setBounds(0, 0, (int) getRoot().getWidth(), (int) getRoot().getHeight());
                this.mQuietDrawable.draw(canvas);
            }
            canvas.restoreToCount(iSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.miui.maml.MamlDrawable
    public void finalize() throws Throwable {
        cleanUp();
        super.finalize();
    }

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    public Drawable getQuietDrawable() {
        return this.mQuietDrawable;
    }

    public ScreenElementRoot getRoot() {
        return this.mRendererCore.getRoot();
    }

    public Drawable getStartDrawable() {
        return this.mStartDrawable;
    }

    public void onPause() {
        getRoot().onCommand("pause");
        doPause();
        this.mHandler.removeCallbacks(this.mRenderTimeout);
    }

    public void onResume() {
        getRoot().onCommand("resume");
        doResume();
    }

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        Drawable drawable = this.mQuietDrawable;
        if (drawable != null) {
            drawable.setAlpha(i);
        }
        Drawable drawable2 = this.mStartDrawable;
        if (drawable2 != null) {
            drawable2.setAlpha(i);
        }
    }

    @Override // com.miui.maml.MamlDrawable
    public void setBadgeInfo(Drawable drawable, Rect rect) {
        if (rect == null || (rect.left >= 0 && rect.top >= 0 && rect.width() <= this.mIntrinsicWidth && rect.height() <= this.mIntrinsicHeight)) {
            this.mBadgeDrawable = drawable;
            this.mBadgeLocation = rect;
            MamlDrawable.MamlDrawableState mamlDrawableState = this.mState;
            mamlDrawableState.mStateBadgeDrawable = drawable;
            mamlDrawableState.mStateBadgeLocation = rect;
            return;
        }
        throw new IllegalArgumentException("Badge location " + rect + " not in badged drawable bounds " + new Rect(0, 0, this.mIntrinsicWidth, this.mIntrinsicHeight));
    }

    @Override // com.miui.maml.MamlDrawable, android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        MamlLog.d(LOG_TAG, "setColorFilter");
        Drawable drawable = this.mQuietDrawable;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        Drawable drawable2 = this.mStartDrawable;
        if (drawable2 != null) {
            drawable2.setColorFilter(colorFilter);
        }
        Drawable drawable3 = this.mBadgeDrawable;
        if (drawable3 != null) {
            drawable3.setColorFilter(colorFilter);
        }
        RendererCore rendererCore = this.mRendererCore;
        if (rendererCore != null) {
            rendererCore.setColorFilter(colorFilter);
        }
    }
}
