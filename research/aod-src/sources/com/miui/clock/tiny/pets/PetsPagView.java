package com.miui.clock.tiny.pets;

import android.content.Context;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
public class PetsPagView extends PagViewBase {
    public volatile boolean mMirrorPag;
    public final Runnable updateMirrorTask;

    public PetsPagView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMirrorPag = false;
        this.updateMirrorTask = new Runnable() { // from class: com.miui.clock.tiny.pets.PetsPagView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
    }

    public final /* synthetic */ void lambda$new$0() {
        setRotationY(this.mMirrorPag ? 180.0f : 0.0f);
    }
}
