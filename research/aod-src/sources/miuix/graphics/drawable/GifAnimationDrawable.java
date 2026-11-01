package miuix.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.SystemClock;
import java.util.ArrayList;
import miuix.graphics.gif.DecodeGifImageHelper;

/* JADX INFO: loaded from: classes2.dex */
public class GifAnimationDrawable extends AnimationDrawable {
    public int mCurrentFrame;
    public DrawableContainer.DrawableContainerState mDrawableContainerState;
    public ArrayList mDurations;
    public final DecodeGifImageHelper mHelper;
    public ArrayList mOriginalDurations;
    public Resources mResources;

    @Override // android.graphics.drawable.AnimationDrawable
    public final void addFrame(Drawable drawable, int i) {
        super.addFrame(drawable, i);
        this.mDurations.add(Integer.valueOf(i));
        this.mOriginalDurations.add(Integer.valueOf(i));
    }

    @Override // android.graphics.drawable.AnimationDrawable
    public final int getDuration(int i) {
        return ((Integer) this.mDurations.get(i)).intValue();
    }

    public final void preSelectDrawable(int i) {
        if (this.mHelper.mFrames.isEmpty()) {
            return;
        }
        DecodeGifImageHelper.GifFrame gifFrame = (DecodeGifImageHelper.GifFrame) this.mHelper.mFrames.get(0);
        if (this.mHelper.mFrames.size() > 1) {
            this.mHelper.mFrames.remove(0);
        }
        this.mHelper.decodeNextFrames();
        this.mDrawableContainerState.getChildren()[i] = new BitmapDrawable(this.mResources, gifFrame.mImage);
        this.mDurations.add(i, Integer.valueOf(gifFrame.mDuration));
    }

    @Override // android.graphics.drawable.Drawable
    public final void scheduleSelf(Runnable runnable, long j) {
        if (j == SystemClock.uptimeMillis() + ((long) ((Integer) this.mOriginalDurations.get(this.mCurrentFrame)).intValue())) {
            j = SystemClock.uptimeMillis() + ((long) ((Integer) this.mDurations.get(this.mCurrentFrame)).intValue());
        }
        super.scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.DrawableContainer
    public final boolean selectDrawable(int i) {
        preSelectDrawable(i);
        this.mCurrentFrame = i;
        return super.selectDrawable(i);
    }

    @Override // android.graphics.drawable.AnimationDrawable, android.graphics.drawable.DrawableContainer
    public final void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        this.mDrawableContainerState = drawableContainerState;
    }
}
