package com.miui.keyguard.editor.edit.wallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.FrameLayout;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.miwallpaper.opengl.GlassRenderer;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: IWallpaperLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface IWallpaperLayer {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: IWallpaperLayer.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public static final void createMyLockShaderWallpaper$lambda$9$lambda$8(boolean z, GLTextureView gLTextureView, GlassRenderer glassRenderer) {
            glassRenderer.setGlassWithBlur(z);
            gLTextureView.requestRender();
        }

        public static /* synthetic */ CombinedWallpaperView createWallpaperView$default(Companion companion, Context context, WallpaperInfo wallpaperInfo, boolean z, boolean z2, boolean z3, EditFragment editFragment, String str, boolean z4, int i, Object obj) {
            if ((i & 32) != 0) {
                editFragment = null;
            }
            if ((i & 64) != 0) {
                str = null;
            }
            if ((i & 128) != 0) {
                z4 = true;
            }
            return companion.createWallpaperView(context, wallpaperInfo, z, z2, z3, editFragment, str, z4);
        }

        public final VideoTextureView createDepthVideoForegroundView(Context context, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, String str) {
            Intrinsics.checkNotNullParameter(context, "context");
            VideoTextureView videoTextureView = new VideoTextureView(context);
            DefaultImpls.setImageAndPosition$default(videoTextureView, bitmap, wallpaperPositionInfo, null, str + "-DepthVideoForegroundView", 4, null);
            videoTextureView.setOpaque(false);
            return videoTextureView;
        }

        public final GLTextureView createMyLockShaderWallpaper(WeakReference contextReference, Bitmap bitmap, Bitmap bitmap2, int i, WallpaperPositionInfo wallpaperPositionInfo, boolean z, final boolean z2, GlassRenderer.IRenderListener iRenderListener, String str, WallpaperInfoType wallpaperInfoType) {
            Intrinsics.checkNotNullParameter(contextReference, "contextReference");
            Log.i("Keyguard-Editor-IWallpaperLayer", "createMyLockShaderWallpaper: ");
            Context context = (Context) contextReference.get();
            if (context == null) {
                Log.w("Keyguard-Editor-IWallpaperLayer", "contextReference is null when createMyLockShaderWallpaper");
                try {
                    KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                    Bundle bundle = new Bundle();
                    bundle.putString("fromWhere", str);
                    Unit unit = Unit.INSTANCE;
                    keyguardEditorMiSightUtil.reportWallpaperNotDisplayed("IWallpaperLayer.createMyLockShaderWallpaper_contextReference_null", bundle);
                } catch (Exception unused) {
                }
                return null;
            }
            if (bitmap == null) {
                Log.w("Keyguard-Editor-IWallpaperLayer", "wallpaperBitmap is null when createMyLockShaderWallpaper");
                try {
                    KeyguardEditorMiSightUtil keyguardEditorMiSightUtil2 = KeyguardEditorMiSightUtil.INSTANCE;
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("fromWhere", str);
                    Unit unit2 = Unit.INSTANCE;
                    keyguardEditorMiSightUtil2.reportWallpaperNotDisplayed("IWallpaperLayer.createMyLockShaderWallpaper_wallpaperBitmap_null", bundle2);
                } catch (Exception unused2) {
                }
                return null;
            }
            GLES20ContextFactory gLES20ContextFactory = new GLES20ContextFactory();
            GLES20ConfigChooser gLES20ConfigChooser = new GLES20ConfigChooser(8, 8, 8, 8, 16, 0);
            final GLTextureView gLTextureView = new GLTextureView(context);
            gLTextureView.setImageAndPosition(bitmap, wallpaperPositionInfo, null, str + "-createMyLockShaderWallpaper");
            gLTextureView.setEGLContextFactory(gLES20ContextFactory);
            gLTextureView.setEGLConfigChooser(gLES20ConfigChooser);
            final GlassRenderer glassRenderer = new GlassRenderer(context, bitmap, i);
            gLTextureView.setCurrentMagicType(i);
            if (iRenderListener != null) {
                glassRenderer.registerRenderListener(iRenderListener);
            }
            gLTextureView.glassRender = glassRenderer;
            glassRenderer.updateIsDarken(z);
            gLTextureView.setRenderer(glassRenderer);
            gLTextureView.queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer$Companion$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IWallpaperLayer.Companion.createMyLockShaderWallpaper$lambda$9$lambda$8(z2, gLTextureView, glassRenderer);
                }
            });
            gLTextureView.setRenderMode(0);
            gLTextureView.setOpaque(false);
            gLTextureView.setNeedHideAnim(wallpaperInfoType == WallpaperInfoType.HOME_WALLPAPER);
            return gLTextureView;
        }

        public final VideoTextureView createVideoWallpaper(Context context, VideoTextureView.SurfaceCallback surfaceCallback, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, String str) {
            Intrinsics.checkNotNullParameter(context, "context");
            Log.i("Keyguard-Editor-IWallpaperLayer", "createVideoWallpaper: surfaceCallback=" + surfaceCallback);
            VideoTextureView videoTextureView = new VideoTextureView(context);
            DefaultImpls.setImageAndPosition$default(videoTextureView, bitmap, wallpaperPositionInfo, null, str + "-createVideoWallpaper", 4, null);
            videoTextureView.setSurfaceCallback(surfaceCallback);
            videoTextureView.setOpaque(false);
            return videoTextureView;
        }

        public final WallpaperImageView createWallpaperImageView(Context context, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, String str, String str2) {
            Intrinsics.checkNotNullParameter(context, "context");
            Log.i("Keyguard-Editor-IWallpaperLayer", "createWallpaperImageView: ");
            WallpaperImageView wallpaperImageView = new WallpaperImageView(context, null, 0, 6, null);
            wallpaperImageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            wallpaperImageView.setImageAndPosition(bitmap, wallpaperPositionInfo, str, str2 + "-createWallpaperImageView");
            return wallpaperImageView;
        }

        public final View createWallpaperLayer(Context context, WallpaperInfo wallpaperInfo, boolean z, boolean z2, GlassRenderer.IRenderListener iRenderListener, boolean z3, String str) {
            Bitmap currentWallpaper;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
            Wallpaper.Companion companion = Wallpaper.Companion;
            boolean z4 = false;
            if (companion.needSmallSensorWallpaper(wallpaperInfo.getResourceType(), context)) {
                currentWallpaper = wallpaperInfo.getCurrentSmallSensorWallpaper();
            } else {
                currentWallpaper = wallpaperInfo.getCurrentWallpaper();
                if (currentWallpaper == null) {
                    String resourceType = wallpaperInfo.getResourceType();
                    if (!Intrinsics.areEqual(resourceType, "video") && !Intrinsics.areEqual(resourceType, "sensor")) {
                        String source = wallpaperInfo.getSource();
                        if (source != null && source.length() != 0 && new File(source).exists()) {
                            z4 = true;
                        }
                        try {
                            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                            Bundle bundle = new Bundle();
                            bundle.putString("resourceType", resourceType);
                            if (source == null) {
                                source = "null";
                            }
                            bundle.putString("source", source);
                            bundle.putBoolean("fileExists", z4);
                            Unit unit = Unit.INSTANCE;
                            keyguardEditorMiSightUtil.reportWallpaperNotDisplayed("IWallpaperLayer.createWallpaperLayer_currentWallpaper_null", bundle);
                        } catch (Exception unused) {
                        }
                    }
                    return null;
                }
            }
            Bitmap bitmap = currentWallpaper;
            WallpaperPositionInfo positionInfo = companion.isSensorWallpaper(wallpaperInfo.getResourceType()) ? null : wallpaperInfo.getPositionInfo();
            boolean zIsNeedDark = wallpaperInfo.isNeedDark();
            int magicType = wallpaperInfo.getMagicType();
            boolean z5 = companion.isSupportBlurWallpaperType(wallpaperInfo.getResourceType(), magicType) && wallpaperInfo.getEnableBlur();
            if (z || !z2) {
                return createMyLockShaderWallpaper(new WeakReference(context), bitmap, z ? wallpaperInfo.getFrontBackBitmap() : wallpaperInfo.getCroppedFrontBackBitmap(), magicType, positionInfo, zIsNeedDark, z5, iRenderListener, str, wallpaperInfo.getInfoType());
            }
            return createWallpaperImageView(context, bitmap, positionInfo, wallpaperInfo.getResourceType(), str);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final CombinedWallpaperView createWallpaperView(Context context, WallpaperInfo wallpaperInfo, boolean z, boolean z2, boolean z3, EditFragment editFragment, String str, boolean z4) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
            CombinedWallpaperView combinedWallpaperView = new CombinedWallpaperView(context, null, 2, 0 == true ? 1 : 0);
            combinedWallpaperView.init(wallpaperInfo, z, z2, z3, editFragment, str, z4);
            return combinedWallpaperView;
        }
    }

    /* JADX INFO: compiled from: IWallpaperLayer.kt */
    @Metadata
    public static final class DefaultImpls {
        public static void addGestureListener(IWallpaperLayer iWallpaperLayer, WallpaperChangedListener wallpaperChangedListener) {
            if (wallpaperChangedListener == null || iWallpaperLayer.getWallpaperChangedListenerSet() == null) {
                return;
            }
            HashSet wallpaperChangedListenerSet = iWallpaperLayer.getWallpaperChangedListenerSet();
            Intrinsics.checkNotNull(wallpaperChangedListenerSet);
            wallpaperChangedListenerSet.add(wallpaperChangedListener);
        }

        public static boolean canScale(IWallpaperLayer iWallpaperLayer) {
            if (iWallpaperLayer.getScaleable() == null) {
                return false;
            }
            Boolean scaleable = iWallpaperLayer.getScaleable();
            Intrinsics.checkNotNull(scaleable);
            return scaleable.booleanValue();
        }

        public static int currentMagicType(IWallpaperLayer iWallpaperLayer) {
            return 0;
        }

        public static WallpaperPositionInfo getPosition(IWallpaperLayer iWallpaperLayer, Bitmap bitmap) {
            GestureManager gestureManager = iWallpaperLayer.getGestureManager();
            if (gestureManager != null) {
                return gestureManager.getPosition(bitmap);
            }
            return null;
        }

        public static /* synthetic */ WallpaperPositionInfo getPosition$default(IWallpaperLayer iWallpaperLayer, Bitmap bitmap, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getPosition");
            }
            if ((i & 1) != 0) {
                bitmap = iWallpaperLayer.getOriginBitmap();
            }
            return iWallpaperLayer.getPosition(bitmap);
        }

        public static Matrix getWallpaperMatrix(IWallpaperLayer iWallpaperLayer) {
            GestureManager gestureManager = iWallpaperLayer.getGestureManager();
            if (gestureManager != null) {
                return gestureManager.getMatrix();
            }
            return null;
        }

        public static boolean isCut(IWallpaperLayer iWallpaperLayer) {
            GestureManager gestureManager = iWallpaperLayer.getGestureManager();
            if (gestureManager != null) {
                return gestureManager.isCut();
            }
            return false;
        }

        public static /* synthetic */ void setImageAndPosition$default(IWallpaperLayer iWallpaperLayer, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, String str, String str2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setImageAndPosition");
            }
            if ((i & 4) != 0) {
                str = null;
            }
            iWallpaperLayer.setImageAndPosition(bitmap, wallpaperPositionInfo, str, str2);
        }

        public static void switchGlassShader(IWallpaperLayer iWallpaperLayer, int i, WallpaperEditParam wallpaperEditParam) {
        }
    }

    void addGestureListener(WallpaperChangedListener wallpaperChangedListener);

    int currentMagicType();

    GestureManager getGestureManager();

    float getGlassInitScaleX();

    boolean getInTemplate();

    Bitmap getOriginBitmap();

    WallpaperPositionInfo getPosition(Bitmap bitmap);

    Boolean getScaleable();

    Surface getVideoSurface();

    Bitmap getWallpaperBitmap(Bitmap bitmap);

    WallpaperCallback getWallpaperCallback();

    HashSet getWallpaperChangedListenerSet();

    Matrix getWallpaperMatrix();

    String getWallpaperType();

    boolean isCut();

    void onWallpaperSelected(WallpaperTypeInfo wallpaperTypeInfo, String str);

    void setImageAndPosition(Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, String str, String str2);

    void setInTemplate(boolean z);

    void setOriginBitmap(Bitmap bitmap);

    void setScaleable(Boolean bool);

    void setWallpaperCallback(WallpaperCallback wallpaperCallback);

    void setWallpaperType(String str);

    void switchGlassShader(int i, WallpaperEditParam wallpaperEditParam);

    View wallpaperLayerView();
}
