package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.load.engine.Initializable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public abstract class DrawableResource<T extends Drawable> implements Resource<T>, Initializable {
    public final Drawable drawable;

    public DrawableResource(Drawable drawable) {
        this.drawable = (Drawable) Preconditions.checkNotNull(drawable);
    }

    @Override // com.bumptech.glide.load.engine.Resource
    public final Drawable get() {
        Drawable.ConstantState constantState = this.drawable.getConstantState();
        return constantState == null ? this.drawable : constantState.newDrawable();
    }

    public void initialize() {
        Drawable drawable = this.drawable;
        if (drawable instanceof BitmapDrawable) {
            ((BitmapDrawable) drawable).getBitmap().prepareToDraw();
        } else if (drawable instanceof GifDrawable) {
            ((GifDrawable) drawable).getFirstFrame().prepareToDraw();
        }
    }
}
