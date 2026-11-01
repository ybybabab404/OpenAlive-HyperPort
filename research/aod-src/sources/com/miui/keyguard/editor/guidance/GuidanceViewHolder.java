package com.miui.keyguard.editor.guidance;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import java.io.IOException;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: GuidanceAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GuidanceViewHolder extends RecyclerView.ViewHolder {
    public MediaPlayer mediaPlayer;
    public boolean pendingStart;
    public boolean prepared;
    public Surface surface;
    public final String tag;
    public final TextView textView;
    public final TextureView videoView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuidanceViewHolder(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        this.tag = "GuidanceViewHolder";
        View viewFindViewById = itemView.findViewById(R.id.text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.textView = (TextView) viewFindViewById;
        View viewFindViewById2 = itemView.findViewById(R.id.video);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        TextureView textureView = (TextureView) viewFindViewById2;
        this.videoView = textureView;
        this.mediaPlayer = new MediaPlayer();
        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.miui.keyguard.editor.guidance.GuidanceViewHolder.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                Intrinsics.checkNotNullParameter(view, "view");
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                Intrinsics.checkNotNullParameter(view, "view");
                GuidanceViewHolder.this.releaseMediaResources();
            }
        });
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() { // from class: com.miui.keyguard.editor.guidance.GuidanceViewHolder.2
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                Intrinsics.checkNotNullParameter(surfaceTexture, "surfaceTexture");
                GuidanceViewHolder.this.surface = new Surface(surfaceTexture);
                MediaPlayer mediaPlayer = GuidanceViewHolder.this.mediaPlayer;
                if (mediaPlayer != null) {
                    mediaPlayer.setSurface(GuidanceViewHolder.this.surface);
                }
                if (GuidanceViewHolder.this.pendingStart && GuidanceViewHolder.this.prepared) {
                    GuidanceViewHolder.this.playInternal();
                }
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
                GuidanceViewHolder.this.releaseMediaResources();
                return true;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture p0, int i, int i2) {
                Intrinsics.checkNotNullParameter(p0, "p0");
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture p0) {
                Intrinsics.checkNotNullParameter(p0, "p0");
            }
        });
    }

    public static final void onBind$lambda$0(GuidanceViewHolder guidanceViewHolder, MediaPlayer mediaPlayer) {
        guidanceViewHolder.prepared = true;
        Log.i(guidanceViewHolder.tag, "mediaPlayer prepared");
        guidanceViewHolder.dumpMediaPlayerState(mediaPlayer, true, guidanceViewHolder.pendingStart, guidanceViewHolder.surface);
        if (guidanceViewHolder.surface != null) {
            guidanceViewHolder.playInternal();
        }
    }

    public final void dumpMediaPlayerState(MediaPlayer mediaPlayer, boolean z, boolean z2, Surface surface) {
        Log.i(this.tag, "prepared " + z + " pendingStart " + z2 + " surface is null " + (surface == null) + " playing " + (mediaPlayer != null ? Boolean.valueOf(mediaPlayer.isPlaying()) : null));
    }

    public final void onBind(GuidanceItem itemData) throws IOException {
        Intrinsics.checkNotNullParameter(itemData, "itemData");
        Log.i(this.tag, "onBind " + itemData);
        this.pendingStart = false;
        this.textView.setText(itemData.getText());
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setDataSource(this.itemView.getContext(), itemData.getUri());
        }
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 != null) {
            mediaPlayer2.setLooping(true);
        }
        MediaPlayer mediaPlayer3 = this.mediaPlayer;
        if (mediaPlayer3 != null) {
            mediaPlayer3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.miui.keyguard.editor.guidance.GuidanceViewHolder$$ExternalSyntheticLambda0
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer4) {
                    GuidanceViewHolder.onBind$lambda$0(this.f$0, mediaPlayer4);
                }
            });
        }
        MediaPlayer mediaPlayer4 = this.mediaPlayer;
        if (mediaPlayer4 != null) {
            mediaPlayer4.prepare();
        }
    }

    public final void play() {
        Log.i(this.tag, "play");
        dumpMediaPlayerState(this.mediaPlayer, this.prepared, this.pendingStart, this.surface);
        if (this.surface == null || !this.prepared) {
            this.pendingStart = true;
            return;
        }
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            playInternal();
        }
    }

    public final void playInternal() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        this.pendingStart = false;
    }

    public final void releaseMediaResources() {
        MediaPlayer mediaPlayer;
        this.surface = null;
        this.videoView.setSurfaceTextureListener(null);
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if (mediaPlayer2 == null) {
            return;
        }
        if (mediaPlayer2 != null && mediaPlayer2.isPlaying() && (mediaPlayer = this.mediaPlayer) != null) {
            mediaPlayer.stop();
        }
        MediaPlayer mediaPlayer3 = this.mediaPlayer;
        if (mediaPlayer3 != null) {
            mediaPlayer3.reset();
        }
        MediaPlayer mediaPlayer4 = this.mediaPlayer;
        if (mediaPlayer4 != null) {
            mediaPlayer4.release();
        }
        this.mediaPlayer = null;
    }

    public final void stop() {
        MediaPlayer mediaPlayer;
        if (this.pendingStart) {
            this.pendingStart = false;
            return;
        }
        MediaPlayer mediaPlayer2 = this.mediaPlayer;
        if ((mediaPlayer2 == null || mediaPlayer2.isPlaying()) && (mediaPlayer = this.mediaPlayer) != null) {
            mediaPlayer.pause();
        }
    }
}
