package com.miui.aod.util;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.core.content.res.ResourcesCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.miui.aod.Utils;
import com.miui.aod.resource.DrawableCache;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ImageUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ImageUtils {
    public static final boolean DEBUG = false;
    public static final ImageUtils INSTANCE = new ImageUtils();

    public static final void loadAsBackground(View view, int i, int i2) {
        loadAsBackground(view, i, true, i2);
    }

    public static final void loadAsBackground(final View view, int i, boolean z, int i2) {
        if (view != null) {
            loadDrawable(view, i, z, i2, new CustomTarget<Drawable>() { // from class: com.miui.aod.util.ImageUtils$loadAsBackground$1$1
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(Drawable drawable) {
                    view.setBackground(null);
                }

                @Override // com.bumptech.glide.request.target.CustomTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(Drawable drawable) {
                    view.setBackground(null);
                }

                @Override // com.bumptech.glide.request.target.Target
                public void onResourceReady(Drawable resource, Transition transition) {
                    Intrinsics.checkNotNullParameter(resource, "resource");
                    view.setBackground(resource);
                }
            });
        }
    }

    public static final void loadDrawable(View view, int i, boolean z, int i2, CustomTarget callBack) {
        View view2;
        int i3;
        boolean z2;
        int i4;
        CustomTarget customTarget;
        Intrinsics.checkNotNullParameter(callBack, "callBack");
        if (view == null) {
            return;
        }
        try {
            if (Utils.isAodProcess()) {
                Intrinsics.checkNotNull(Glide.with(view.getContext()).load(Integer.valueOf(i)).into(callBack));
            } else {
                view2 = view;
                i3 = i;
                z2 = z;
                i4 = i2;
                customTarget = callBack;
                try {
                    INSTANCE.loadDrawableFallBack(view2, i3, z2, i4, customTarget);
                } catch (Exception e) {
                    e = e;
                    Log.e("ImageUtils", "loadDrawable error for " + view2, e);
                    CustomTarget customTarget2 = customTarget;
                    int i5 = i4;
                    boolean z3 = z2;
                    INSTANCE.loadDrawableFallBack(view2, i3, z3, i5, customTarget2);
                }
            }
        } catch (Exception e2) {
            e = e2;
            view2 = view;
            i3 = i;
            z2 = z;
            i4 = i2;
            customTarget = callBack;
        }
    }

    public static final void loadIntoImage(ImageView imageView, int i) {
        loadIntoImage(imageView, i, -1, -1, true, 1);
    }

    public static final void loadIntoImage(ImageView imageView, int i, int i2) {
        loadIntoImage(imageView, i, -1, -1, true, i2);
    }

    public static final void loadIntoImage(ImageView imageView, int i, int i2, int i3, boolean z, int i4) {
        if (imageView != null) {
            if (i == 0) {
                if (DEBUG) {
                    Log.w("ImageUtils", "loadImage error for " + imageView + ", resId is 0");
                }
                imageView.setImageDrawable(null);
                return;
            }
            try {
                if (Utils.isAodProcess()) {
                    Intrinsics.checkNotNull(((RequestBuilder) Glide.with(imageView.getContext()).load(Integer.valueOf(i)).override(i2, i3)).into(imageView));
                } else {
                    INSTANCE.loadIntoImageFallBack(imageView, i, z, i4);
                }
            } catch (Exception e) {
                Log.e("ImageUtils", "loadImage error for " + imageView, e);
                INSTANCE.loadIntoImageFallBack(imageView, i, z, i4);
            }
        }
    }

    public final void loadDrawableFallBack(View view, int i, boolean z, int i2, CustomTarget customTarget) {
        if (view != null) {
            customTarget.onLoadStarted(null);
            try {
                Drawable drawable = z ? DrawableCache.getInstance(view.getContext()).getDrawable(i, i2) : ResourcesCompat.getDrawable(view.getContext().getResources(), i, null);
                if (drawable != null) {
                    customTarget.onResourceReady(drawable, null);
                } else {
                    customTarget.onLoadFailed(null);
                }
            } catch (Exception e) {
                Log.e("ImageUtils", "loadDrawableFallBack error for " + view, e);
                customTarget.onLoadFailed(null);
            }
        }
    }

    public final void loadIntoImageFallBack(ImageView imageView, int i, boolean z, int i2) {
        if (imageView != null) {
            try {
                imageView.setImageDrawable(z ? DrawableCache.getInstance(imageView.getContext()).getDrawable(i, i2) : ResourcesCompat.getDrawable(imageView.getContext().getResources(), i, null));
            } catch (Exception e) {
                Log.e("ImageUtils", "loadIntoImageFallBack error for " + imageView, e);
                imageView.setImageDrawable(null);
            }
        }
    }
}
