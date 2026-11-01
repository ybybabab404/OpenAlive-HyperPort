package com.miui.keyguard.editor.edit.wallpaper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.data.template.WallpaperInfoType;
import com.miui.keyguard.editor.edit.EditFragment;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.CombineEditListener;
import com.miui.keyguard.editor.edit.base.CombineEditView;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.viewmodel.EditFragmentViewModel;
import com.miui.keyguard.editor.viewmodel.IntWrapper;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.miwallpaper.opengl.GlassRenderer;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
/* JADX INFO: compiled from: CombinedWallpaperView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class CombinedWallpaperView extends FrameLayout implements IWallpaperLayer, GlassRenderer.IRenderListener {
    public static final Companion Companion = new Companion(null);
    public boolean blockSelfGestures;
    public String createFromWhere;
    public boolean curIsFoldLargeScreen;
    public int curMagicType;
    public IWallpaperLayer currentWallpaperView;
    public float downX;
    public float downY;
    public EditFragment editFragment;
    public boolean enableBlur;
    public boolean inTemplate;
    public boolean isFirstBindWallpaper;
    public boolean isFirstFrameReady;
    public boolean isLongClickFinished;
    public boolean isScrollingOrScaling;
    public Runnable longClickChecker;
    public Surface mVideoSurface;
    public boolean needStartWallpaperChangedAnim;
    public CombineEditListener onceCombineEditListener;
    public View originWallpaperView;
    public boolean resetGlassState;
    public int screenOrientation;
    public int screenWidthDp;
    public ObjectAnimator showAlphaAnim;
    public ObserverClickListener touchClickListener;
    public WallpaperInfo wallpaperInfo;
    public View.OnLongClickListener wallpaperLongClickListener;
    public View wallpaperView;

    /* JADX INFO: compiled from: CombinedWallpaperView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: CombinedWallpaperView.kt */
    @Metadata
    public static final class RenderRunable implements Runnable {
        public final int action;
        public final Object data;
        public final WeakReference mGLTextureViewWeakRef;

        public RenderRunable(GLTextureView glTextView, int i, Object obj) {
            Intrinsics.checkNotNullParameter(glTextView, "glTextView");
            this.mGLTextureViewWeakRef = new WeakReference(glTextView);
            this.action = i;
            this.data = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            GlassRenderer glassRenderer;
            int i = this.action;
            if (i == 0) {
                GLTextureView gLTextureView = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if (gLTextureView != null) {
                    gLTextureView.destroyEglSurfaceAndContext();
                    return;
                }
                return;
            }
            if (i == 1) {
                GLTextureView gLTextureView2 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if (gLTextureView2 != null) {
                    gLTextureView2.requestRender();
                    return;
                }
                return;
            }
            if (i == 2 && this.data != null) {
                GLTextureView gLTextureView3 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if (gLTextureView3 != null && (glassRenderer = gLTextureView3.glassRender) != null) {
                    Object obj = this.data;
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
                    glassRenderer.setGlassWithBlur(((Boolean) obj).booleanValue());
                }
                GLTextureView gLTextureView4 = (GLTextureView) this.mGLTextureViewWeakRef.get();
                if (gLTextureView4 != null) {
                    gLTextureView4.requestRender();
                }
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CombinedWallpaperView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.longClickChecker = new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$special$$inlined$Runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                View.OnLongClickListener onLongClickListener = this.this$0.wallpaperLongClickListener;
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(this.this$0);
                }
                Log.i("Keyguard-Editor-CombinedWallpaperView", "longClickChecker: onLongClick");
                this.this$0.isLongClickFinished = true;
            }
        };
        this.inTemplate = true;
        this.screenWidthDp = context.getResources().getConfiguration().screenWidthDp;
        this.screenOrientation = context.getResources().getConfiguration().orientation;
        this.curIsFoldLargeScreen = DeviceUtil.INSTANCE.isFoldLargeScreen(context);
    }

    public /* synthetic */ CombinedWallpaperView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final Unit animSwitchWallpaperView$lambda$17(ObjectAnimator objectAnimator, ObjectAnimator objectAnimator2) {
        Log.d("Keyguard-Editor-CombinedWallpaperView", "start switch anim");
        objectAnimator.start();
        objectAnimator2.start();
        return Unit.INSTANCE;
    }

    public static final void animSwitchWallpaperView$lambda$18(Job job, Function0 function0) {
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        function0.invoke();
    }

    public static final void animSwitchWallpaperView$lambda$19(Job job, Function0 function0) {
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, null, 1, null);
        }
        function0.invoke();
    }

    private final GLTextureView getCurGLTextureView() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (!(iWallpaperLayer instanceof GLTextureView)) {
            return null;
        }
        Intrinsics.checkNotNull(iWallpaperLayer, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.GLTextureView");
        return (GLTextureView) iWallpaperLayer;
    }

    private final VideoTextureView.SurfaceCallback getSurfaceCallback() {
        return new VideoTextureView.SurfaceCallback() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda3
            @Override // com.miui.keyguard.editor.edit.wallpaper.VideoTextureView.SurfaceCallback
            public final void surfaceCreated(Surface surface) {
                CombinedWallpaperView.getSurfaceCallback$lambda$25(this.f$0, surface);
            }
        };
    }

    public static final void getSurfaceCallback$lambda$25(CombinedWallpaperView combinedWallpaperView, Surface surface) {
        Log.d("Keyguard-Editor-CombinedWallpaperView", "getSurfaceCallback: surface=" + surface);
        combinedWallpaperView.mVideoSurface = surface;
    }

    public static final void init$lambda$4$lambda$3(final CombinedWallpaperView combinedWallpaperView) {
        combinedWallpaperView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setBackground(null);
            }
        });
    }

    public static final void onDetachedFromWindow$lambda$32(GLTextureView gLTextureView) {
        try {
            gLTextureView.glassRender = null;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-CombinedWallpaperView", "Error releasing OpenGL resources", e);
        }
    }

    public static final void onDrawFrame$lambda$31(final CombinedWallpaperView combinedWallpaperView, long j) {
        combinedWallpaperView.onFirstFrameReady();
        combinedWallpaperView.startWallpaperViewShowAnimation(combinedWallpaperView.wallpaperView);
        try {
            KeyguardEditorMiSightUtil.INSTANCE.markFirstFrameArrived(j);
        } catch (Exception unused) {
        }
        combinedWallpaperView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                CombinedWallpaperView.onDrawFrame$lambda$31$lambda$30(this.f$0);
            }
        }, 500L);
    }

    public static final void onDrawFrame$lambda$31$lambda$30(final CombinedWallpaperView combinedWallpaperView) {
        boolean z = combinedWallpaperView.getBackground() == null;
        View view = combinedWallpaperView.wallpaperView;
        boolean z2 = (view != null ? view.getAlpha() : 1.0f) <= 0.0f;
        if (z && z2) {
            combinedWallpaperView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    CombinedWallpaperView.onDrawFrame$lambda$31$lambda$30$lambda$29(this.f$0);
                }
            }, 1000L);
        }
    }

    public static final void onDrawFrame$lambda$31$lambda$30$lambda$29(CombinedWallpaperView combinedWallpaperView) {
        boolean z = combinedWallpaperView.getBackground() == null;
        View view = combinedWallpaperView.wallpaperView;
        boolean z2 = (view != null ? view.getAlpha() : 1.0f) <= 0.0f;
        if (z && z2) {
            try {
                KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                Bundle bundle = new Bundle();
                bundle.putString("fromWhere", combinedWallpaperView.createFromWhere);
                Unit unit = Unit.INSTANCE;
                keyguardEditorMiSightUtil.reportWallpaperBlackScreen("CombinedWallpaperView.onDrawFrame_black_after_delay", bundle);
            } catch (Exception unused) {
            }
        }
    }

    public static /* synthetic */ void resetCurrentMagicType$default(CombinedWallpaperView combinedWallpaperView, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        combinedWallpaperView.resetCurrentMagicType(i);
    }

    public static /* synthetic */ void setEnableBlurStatus$default(CombinedWallpaperView combinedWallpaperView, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = true;
        }
        combinedWallpaperView.setEnableBlurStatus(z, z2);
    }

    public static /* synthetic */ void switchWallpaperView$default(CombinedWallpaperView combinedWallpaperView, WallpaperInfo wallpaperInfo, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, boolean z, boolean z2, WallpaperInfoType wallpaperInfoType, boolean z3, int i, Object obj) {
        if ((i & 4) != 0) {
            wallpaperPositionInfo = null;
        }
        if ((i & 8) != 0) {
            z = false;
        }
        if ((i & 16) != 0) {
            z2 = true;
        }
        if ((i & 32) != 0) {
            wallpaperInfoType = null;
        }
        if ((i & 64) != 0) {
            z3 = true;
        }
        combinedWallpaperView.switchWallpaperView(wallpaperInfo, bitmap, wallpaperPositionInfo, z, z2, wallpaperInfoType, z3);
    }

    public final void adaptSensorPreviewWallpaper() {
        GLTextureView curGLTextureView;
        CombineEditView combineEditView;
        BaseTemplateView lockTemplateView;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsFoldLargeScreen = deviceUtil.isFoldLargeScreen(context);
        if (zIsFoldLargeScreen != this.curIsFoldLargeScreen) {
            this.curIsFoldLargeScreen = zIsFoldLargeScreen;
            EditFragment editFragment = this.editFragment;
            Bitmap currentSmallSensorWallpaper = null;
            WallpaperInfo wallpaperInfo = (editFragment == null || (combineEditView = editFragment.getCombineEditView()) == null || (lockTemplateView = combineEditView.getLockTemplateView()) == null) ? null : lockTemplateView.getWallpaperInfo();
            WallpaperInfo wallpaperInfo2 = this.wallpaperInfo;
            if ((wallpaperInfo2 != null ? wallpaperInfo2.getInfoType() : null) == WallpaperInfoType.HOME_WALLPAPER && getHomeFollowWapper()) {
                if (!Intrinsics.areEqual(wallpaperInfo != null ? wallpaperInfo.getResourceType() : null, "sensor")) {
                    return;
                }
            }
            WallpaperInfo wallpaperInfo3 = this.wallpaperInfo;
            if (!Intrinsics.areEqual(wallpaperInfo3 != null ? wallpaperInfo3.getResourceType() : null, "sensor") || (curGLTextureView = getCurGLTextureView()) == null) {
                return;
            }
            if (zIsFoldLargeScreen) {
                WallpaperInfo wallpaperInfo4 = this.wallpaperInfo;
                if (wallpaperInfo4 != null) {
                    currentSmallSensorWallpaper = wallpaperInfo4.getCurrentWallpaper();
                }
            } else {
                WallpaperInfo wallpaperInfo5 = this.wallpaperInfo;
                if (wallpaperInfo5 != null) {
                    currentSmallSensorWallpaper = wallpaperInfo5.getCurrentSmallSensorWallpaper();
                }
            }
            curGLTextureView.onSwitchWallpaperBitmap(new WallpaperTypeInfo(currentSmallSensorWallpaper, "sensor", null, null, 0, false, null, false, false, null, null, null, null, false, false, null, null, null, null, null, 1048572, null));
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void addGestureListener(WallpaperChangedListener wallpaperChangedListener) {
        IWallpaperLayer.DefaultImpls.addGestureListener(this, wallpaperChangedListener);
    }

    public final void addOriginWallpaperView(WallpaperInfo wallpaperInfo, View view, boolean z) {
        if (wallpaperInfo.getCurrentLockWallpaperSource() == LockWallpaperSource.MIUI_WALLPAPER_MANAGER) {
            if (!z && TextUtils.equals(wallpaperInfo.getResourceType(), "video")) {
                Log.i("Keyguard-Editor-CombinedWallpaperView", "skip origin wallpaper placeholder for video preview");
                return;
            }
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(wallpaperInfo.getCurrentWallpaper());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (view != null) {
                view.setAlpha(0.0f);
            }
            addView(imageView, -1, -1);
            this.wallpaperView = view;
            this.originWallpaperView = imageView;
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0052  */
    /* JADX WARN: Code duplicated, block: B:14:0x007e  */
    /* JADX WARN: Code duplicated, block: B:16:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:18:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:20:0x0103  */
    /* JADX WARN: Code duplicated, block: B:22:0x011f  */
    /* JADX WARN: Code duplicated, block: B:24:0x0122  */
    /* JADX WARN: Code duplicated, block: B:27:0x014d  */
    /* JADX WARN: Code duplicated, block: B:29:0x0198  */
    /* JADX WARN: Code duplicated, block: B:30:0x019c  */
    /* JADX WARN: Code duplicated, block: B:33:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:35:0x01a8  */
    /* JADX WARN: Code duplicated, block: B:36:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:38:0x01af  */
    /* JADX WARN: Code duplicated, block: B:40:0x01b9  */
    /* JADX WARN: Code duplicated, block: B:41:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:44:0x01d2  */
    /* JADX WARN: Code duplicated, block: B:46:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:48:0x01e0  */
    /* JADX WARN: Code duplicated, block: B:49:0x01ef  */
    /* JADX WARN: Instruction removed from duplicated block: B:12:0x0052, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:16:0x00a0, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:18:0x00e5, please report this as an issue */
    public final void animSwitchWallpaperView(WallpaperTypeInfo wallpaperTypeInfo) {
        Bitmap bitmap;
        Bitmap bitmap2;
        WallpaperCallback wallpaperCallback;
        HashSet<WallpaperChangedListener> wallpaperChangedListenerSet;
        Ref.ObjectRef objectRef;
        final Ref.ObjectRef objectRef2;
        String str;
        CharSequence charSequence;
        GLTextureView gLTextureViewCreateMyLockShaderWallpaper;
        View view;
        GLTextureView gLTextureView;
        final Function0 function0;
        CoroutineScope coroutineScope;
        final Job jobLaunch$default;
        VideoTextureView videoTextureView;
        CoroutineScope coroutineScope2;
        final Job jobLaunch$default2;
        VideoTextureView videoTextureViewCreateVideoWallpaper;
        Wallpaper.Companion companion = Wallpaper.Companion;
        String type = wallpaperTypeInfo.getType();
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        if (companion.needSmallSensorWallpaper(type, context)) {
            SensorWallpaperPrams sensorWallpaperPrams = wallpaperTypeInfo.getSensorWallpaperPrams();
            if (sensorWallpaperPrams != null) {
                bitmap = sensorWallpaperPrams.getCurrentSmallSensorWallpaper();
            } else {
                bitmap2 = null;
            }
            wallpaperCallback = getWallpaperCallback();
            wallpaperChangedListenerSet = getWallpaperChangedListenerSet();
            objectRef = new Ref.ObjectRef();
            objectRef.element = this.currentWallpaperView;
            this.enableBlur = wallpaperTypeInfo.getEnableBlur();
            if (TextUtils.equals(wallpaperTypeInfo.getType(), "video")) {
                IWallpaperLayer.Companion companion2 = IWallpaperLayer.Companion;
                Context context2 = getContext();
                Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
                videoTextureViewCreateVideoWallpaper = companion2.createVideoWallpaper(context2, getSurfaceCallback(), wallpaperTypeInfo.getBitmap(), wallpaperTypeInfo.getPositionInfo(), this.createFromWhere + "-animSwitchWallpaperView");
                if (wallpaperChangedListenerSet != null) {
                    videoTextureViewCreateVideoWallpaper.wallpaperChangedListenerSet.addAll(wallpaperChangedListenerSet);
                }
                this.currentWallpaperView = videoTextureViewCreateVideoWallpaper;
                videoTextureViewCreateVideoWallpaper.setWallpaperCallback(wallpaperCallback);
                videoTextureViewCreateVideoWallpaper.setScaleable(Boolean.valueOf(WallpaperEditor.Companion.canScale(wallpaperTypeInfo.getType())));
                view = videoTextureViewCreateVideoWallpaper;
                objectRef2 = objectRef;
                charSequence = "video";
                str = "Keyguard-Editor-CombinedWallpaperView";
            } else {
                objectRef2 = objectRef;
                str = "Keyguard-Editor-CombinedWallpaperView";
                charSequence = "video";
                gLTextureViewCreateMyLockShaderWallpaper = IWallpaperLayer.Companion.createMyLockShaderWallpaper(new WeakReference(getContext()), bitmap2, wallpaperTypeInfo.getDepthBitmap(), this.curMagicType, wallpaperTypeInfo.getPositionInfo(), wallpaperTypeInfo.isNeedDark(), this.enableBlur, null, this.createFromWhere + "-animSwitchWallpaperView", wallpaperTypeInfo.getInfoType());
                if (gLTextureViewCreateMyLockShaderWallpaper != null) {
                    Log.i(str, "animSwitchWallpaperView: enableBlur =" + this.enableBlur);
                    setEnableBlurStatus(this.enableBlur, false);
                    if (wallpaperChangedListenerSet != null) {
                        gLTextureViewCreateMyLockShaderWallpaper.wallpaperChangedListenerSet.addAll(wallpaperChangedListenerSet);
                    }
                    this.currentWallpaperView = gLTextureViewCreateMyLockShaderWallpaper;
                    gLTextureViewCreateMyLockShaderWallpaper.setWallpaperCallback(wallpaperCallback);
                    gLTextureViewCreateMyLockShaderWallpaper.setScaleable(Boolean.valueOf(WallpaperEditor.Companion.canScale(wallpaperTypeInfo.getType())));
                    view = gLTextureViewCreateMyLockShaderWallpaper;
                } else {
                    view = null;
                }
            }
            if (view == null) {
                Log.e(str, "animSwitchWallpaperView: newWallpaperView is null");
                try {
                    KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                    Bundle bundle = new Bundle();
                    bundle.putString("type", wallpaperTypeInfo.getType());
                    bundle.putBoolean("isVideo", TextUtils.equals(wallpaperTypeInfo.getType(), charSequence));
                    Unit unit = Unit.INSTANCE;
                    keyguardEditorMiSightUtil.reportWallpaperNotDisplayed("CombinedWallpaperView.animSwitchWallpaperView_newWallpaperView_null", bundle);
                    return;
                } catch (Exception unused) {
                    return;
                }
            }
            view.setAlpha(0.0f);
            addView(view);
            Object obj = objectRef2.element;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.view.View");
            final ObjectAnimator duration = ObjectAnimator.ofFloat((View) obj, AnimatedProperty.PROPERTY_NAME_ALPHA, 1.0f, 0.0f).setDuration(300L);
            final ObjectAnimator duration2 = ObjectAnimator.ofFloat(view, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, 1.0f).setDuration(300L);
            duration.setInterpolator(new DecelerateInterpolator());
            duration.addListener(new AnimatorListenerAdapter() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView.animSwitchWallpaperView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                    Object obj2 = (IWallpaperLayer) objectRef2.element;
                    if (obj2 != null) {
                        this.removeView((View) obj2);
                    }
                    objectRef2.element = null;
                    super.onAnimationEnd(animation);
                }
            });
            duration2.setInterpolator(new DecelerateInterpolator());
            if (view instanceof GLTextureView) {
                gLTextureView = (GLTextureView) view;
            } else {
                gLTextureView = null;
            }
            function0 = new Function0() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return CombinedWallpaperView.animSwitchWallpaperView$lambda$17(duration2, duration);
                }
            };
            if (gLTextureView == null) {
                coroutineScope = EditFragmentViewModel.Companion.getCoroutineScope(this.editFragment);
                if (coroutineScope != null) {
                    jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new CombinedWallpaperView$animSwitchWallpaperView$animWatchDog$2(function0, null), 3, null);
                } else {
                    jobLaunch$default = null;
                }
                gLTextureView.addTextureUpdatedListener(new GLTextureView.TextureUpdatedListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda6
                    @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.TextureUpdatedListener
                    public final void onTextureUpdated() {
                        CombinedWallpaperView.animSwitchWallpaperView$lambda$19(jobLaunch$default, function0);
                    }
                });
                return;
            }
            if (view instanceof VideoTextureView) {
                videoTextureView = (VideoTextureView) view;
            } else {
                videoTextureView = null;
            }
            if (videoTextureView != null) {
                function0.invoke();
                return;
            }
            coroutineScope2 = EditFragmentViewModel.Companion.getCoroutineScope(this.editFragment);
            if (coroutineScope2 != null) {
                jobLaunch$default2 = BuildersKt__Builders_commonKt.launch$default(coroutineScope2, null, null, new CombinedWallpaperView$animSwitchWallpaperView$animWatchDog$1(function0, null), 3, null);
            } else {
                jobLaunch$default2 = null;
            }
            videoTextureView.addFirstFrameListener(new VideoTextureView.FirstFrameListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda5
                @Override // com.miui.keyguard.editor.edit.wallpaper.VideoTextureView.FirstFrameListener
                public final void onFirstFrame() {
                    CombinedWallpaperView.animSwitchWallpaperView$lambda$18(jobLaunch$default2, function0);
                }
            });
        }
        bitmap = wallpaperTypeInfo.getBitmap();
        bitmap2 = bitmap;
        wallpaperCallback = getWallpaperCallback();
        wallpaperChangedListenerSet = getWallpaperChangedListenerSet();
        objectRef = new Ref.ObjectRef();
        objectRef.element = this.currentWallpaperView;
        this.enableBlur = wallpaperTypeInfo.getEnableBlur();
        if (TextUtils.equals(wallpaperTypeInfo.getType(), "video")) {
            IWallpaperLayer.Companion companion3 = IWallpaperLayer.Companion;
            Context context3 = getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            videoTextureViewCreateVideoWallpaper = companion3.createVideoWallpaper(context3, getSurfaceCallback(), wallpaperTypeInfo.getBitmap(), wallpaperTypeInfo.getPositionInfo(), this.createFromWhere + "-animSwitchWallpaperView");
            if (wallpaperChangedListenerSet != null) {
                videoTextureViewCreateVideoWallpaper.wallpaperChangedListenerSet.addAll(wallpaperChangedListenerSet);
            }
            this.currentWallpaperView = videoTextureViewCreateVideoWallpaper;
            videoTextureViewCreateVideoWallpaper.setWallpaperCallback(wallpaperCallback);
            videoTextureViewCreateVideoWallpaper.setScaleable(Boolean.valueOf(WallpaperEditor.Companion.canScale(wallpaperTypeInfo.getType())));
            view = videoTextureViewCreateVideoWallpaper;
            objectRef2 = objectRef;
            charSequence = "video";
            str = "Keyguard-Editor-CombinedWallpaperView";
        } else {
            objectRef2 = objectRef;
            str = "Keyguard-Editor-CombinedWallpaperView";
            charSequence = "video";
            gLTextureViewCreateMyLockShaderWallpaper = IWallpaperLayer.Companion.createMyLockShaderWallpaper(new WeakReference(getContext()), bitmap2, wallpaperTypeInfo.getDepthBitmap(), this.curMagicType, wallpaperTypeInfo.getPositionInfo(), wallpaperTypeInfo.isNeedDark(), this.enableBlur, null, this.createFromWhere + "-animSwitchWallpaperView", wallpaperTypeInfo.getInfoType());
            if (gLTextureViewCreateMyLockShaderWallpaper != null) {
                Log.i(str, "animSwitchWallpaperView: enableBlur =" + this.enableBlur);
                setEnableBlurStatus(this.enableBlur, false);
                if (wallpaperChangedListenerSet != null) {
                    gLTextureViewCreateMyLockShaderWallpaper.wallpaperChangedListenerSet.addAll(wallpaperChangedListenerSet);
                }
                this.currentWallpaperView = gLTextureViewCreateMyLockShaderWallpaper;
                gLTextureViewCreateMyLockShaderWallpaper.setWallpaperCallback(wallpaperCallback);
                gLTextureViewCreateMyLockShaderWallpaper.setScaleable(Boolean.valueOf(WallpaperEditor.Companion.canScale(wallpaperTypeInfo.getType())));
                view = gLTextureViewCreateMyLockShaderWallpaper;
            } else {
                view = null;
            }
        }
        if (view == null) {
            Log.e(str, "animSwitchWallpaperView: newWallpaperView is null");
            KeyguardEditorMiSightUtil keyguardEditorMiSightUtil2 = KeyguardEditorMiSightUtil.INSTANCE;
            Bundle bundle2 = new Bundle();
            bundle2.putString("type", wallpaperTypeInfo.getType());
            bundle2.putBoolean("isVideo", TextUtils.equals(wallpaperTypeInfo.getType(), charSequence));
            Unit unit2 = Unit.INSTANCE;
            keyguardEditorMiSightUtil2.reportWallpaperNotDisplayed("CombinedWallpaperView.animSwitchWallpaperView_newWallpaperView_null", bundle2);
            return;
        }
        view.setAlpha(0.0f);
        addView(view);
        Object obj2 = objectRef2.element;
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type android.view.View");
        final ObjectAnimator duration3 = ObjectAnimator.ofFloat((View) obj2, AnimatedProperty.PROPERTY_NAME_ALPHA, 1.0f, 0.0f).setDuration(300L);
        final ObjectAnimator duration4 = ObjectAnimator.ofFloat(view, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, 1.0f).setDuration(300L);
        duration3.setInterpolator(new DecelerateInterpolator());
        duration3.addListener(new AnimatorListenerAdapter() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView.animSwitchWallpaperView.5
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                Object obj3 = (IWallpaperLayer) objectRef2.element;
                if (obj3 != null) {
                    this.removeView((View) obj3);
                }
                objectRef2.element = null;
                super.onAnimationEnd(animation);
            }
        });
        duration4.setInterpolator(new DecelerateInterpolator());
        if (view instanceof GLTextureView) {
            gLTextureView = (GLTextureView) view;
        } else {
            gLTextureView = null;
        }
        function0 = new Function0() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CombinedWallpaperView.animSwitchWallpaperView$lambda$17(duration4, duration3);
            }
        };
        if (gLTextureView == null) {
            coroutineScope = EditFragmentViewModel.Companion.getCoroutineScope(this.editFragment);
            if (coroutineScope != null) {
                jobLaunch$default = BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new CombinedWallpaperView$animSwitchWallpaperView$animWatchDog$2(function0, null), 3, null);
            } else {
                jobLaunch$default = null;
            }
            gLTextureView.addTextureUpdatedListener(new GLTextureView.TextureUpdatedListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda6
                @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.TextureUpdatedListener
                public final void onTextureUpdated() {
                    CombinedWallpaperView.animSwitchWallpaperView$lambda$19(jobLaunch$default, function0);
                }
            });
            return;
        }
        if (view instanceof VideoTextureView) {
            videoTextureView = (VideoTextureView) view;
        } else {
            videoTextureView = null;
        }
        if (videoTextureView != null) {
            function0.invoke();
            return;
        }
        coroutineScope2 = EditFragmentViewModel.Companion.getCoroutineScope(this.editFragment);
        if (coroutineScope2 != null) {
            jobLaunch$default2 = BuildersKt__Builders_commonKt.launch$default(coroutineScope2, null, null, new CombinedWallpaperView$animSwitchWallpaperView$animWatchDog$1(function0, null), 3, null);
        } else {
            jobLaunch$default2 = null;
        }
        videoTextureView.addFirstFrameListener(new VideoTextureView.FirstFrameListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda5
            @Override // com.miui.keyguard.editor.edit.wallpaper.VideoTextureView.FirstFrameListener
            public final void onFirstFrame() {
                CombinedWallpaperView.animSwitchWallpaperView$lambda$18(jobLaunch$default2, function0);
            }
        });
    }

    public boolean canScale() {
        return IWallpaperLayer.DefaultImpls.canScale(this);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public int currentMagicType() {
        return this.curMagicType;
    }

    public final void disableBlur() {
        Log.i("Keyguard-Editor-CombinedWallpaperView", "disableBlur: enableBlur =false");
        setEnableBlurStatus(false, true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent != null) {
            ObserverClickListener observerClickListener = this.touchClickListener;
            if (observerClickListener instanceof ObserverGestureListener) {
                Intrinsics.checkNotNull(observerClickListener, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.ObserverGestureListener");
                ((ObserverGestureListener) observerClickListener).onObserveTouchEvent(motionEvent);
            }
        }
        if (motionEvent == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (this.blockSelfGestures) {
            Log.d("Keyguard-Editor-CombinedWallpaperView", "dispatchTouchEvent: blocked action=" + MotionEvent.actionToString(motionEvent.getAction()) + ", pointerCount=" + motionEvent.getPointerCount());
            return true;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downX = motionEvent.getX();
            this.downY = motionEvent.getY();
            GestureManager gestureManager = getGestureManager();
            if (gestureManager != null) {
                gestureManager.setScaleXYRatio(this.curMagicType);
            }
            this.isLongClickFinished = false;
            if (this.wallpaperLongClickListener != null) {
                postDelayed(this.longClickChecker, ViewConfiguration.getLongPressTimeout());
            }
            if (canScale()) {
                return super.dispatchTouchEvent(motionEvent);
            }
            return true;
        }
        if (action == 261) {
            if (!this.isScrollingOrScaling) {
                this.isScrollingOrScaling = true;
                if (canScale() && DeviceUtil.INSTANCE.isLowComputeDevice()) {
                    GLTextureView curGLTextureView = getCurGLTextureView();
                    if (curGLTextureView != null) {
                        curGLTextureView.resetGlassState();
                    }
                    this.resetGlassState = true;
                }
            }
            if (this.wallpaperLongClickListener != null) {
                removeCallbacks(this.longClickChecker);
            }
        } else if (action == 1) {
            this.isScrollingOrScaling = false;
            if (this.isLongClickFinished) {
                return true;
            }
            if (this.wallpaperLongClickListener != null) {
                removeCallbacks(this.longClickChecker);
            }
            int scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
            if (canScale() && DeviceUtil.INSTANCE.isLowComputeDevice() && this.resetGlassState) {
                this.resetGlassState = false;
                GLTextureView curGLTextureView2 = getCurGLTextureView();
                if (curGLTextureView2 != null) {
                    curGLTextureView2.updateGlassEffectAndBlurState(this.curMagicType, this.enableBlur);
                }
            }
            float f = scaledTouchSlop;
            if (Math.abs(this.downX - motionEvent.getX()) < f && Math.abs(this.downY - motionEvent.getY()) < f) {
                return performClick(motionEvent);
            }
        } else if (action == 3) {
            if (this.wallpaperLongClickListener != null) {
                removeCallbacks(this.longClickChecker);
            }
            if (this.isScrollingOrScaling) {
                this.isScrollingOrScaling = false;
                if (canScale() && DeviceUtil.INSTANCE.isLowComputeDevice() && this.resetGlassState) {
                    this.resetGlassState = false;
                    GLTextureView curGLTextureView3 = getCurGLTextureView();
                    if (curGLTextureView3 != null) {
                        curGLTextureView3.updateGlassEffectAndBlurState(this.curMagicType, this.enableBlur);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final boolean getBlockSelfGestures() {
        return this.blockSelfGestures;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public GestureManager getGestureManager() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.getGestureManager();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public float getGlassInitScaleX() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.getGlassInitScaleX();
        }
        return 1.0f;
    }

    public final boolean getHomeFollowWapper() {
        IntWrapper followStatus;
        EditFragmentViewModel viewModel = EditFragmentViewModel.Companion.getViewModel(this.editFragment);
        int intValue = (viewModel == null || (followStatus = viewModel.getFollowStatus()) == null) ? 0 : followStatus.getIntValue();
        return intValue == 2 || intValue == 1;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public boolean getInTemplate() {
        return this.inTemplate;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Bitmap getOriginBitmap() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.getOriginBitmap();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public WallpaperPositionInfo getPosition(Bitmap bitmap) {
        return IWallpaperLayer.DefaultImpls.getPosition(this, bitmap);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Boolean getScaleable() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.getScaleable();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Surface getVideoSurface() {
        return this.mVideoSurface;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public Bitmap getWallpaperBitmap(Bitmap bitmap) {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            iWallpaperLayer.getWallpaperBitmap(bitmap);
        }
        return bitmap;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public WallpaperCallback getWallpaperCallback() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.getWallpaperCallback();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public HashSet<WallpaperChangedListener> getWallpaperChangedListenerSet() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.getWallpaperChangedListenerSet();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Matrix getWallpaperMatrix() {
        return IWallpaperLayer.DefaultImpls.getWallpaperMatrix(this);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public String getWallpaperType() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.getWallpaperType();
        }
        return null;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void init(WallpaperInfo wallpaperInfo, boolean z, boolean z2, boolean z3, EditFragment editFragment, String str, boolean z4) {
        boolean z5;
        View viewCreateWallpaperLayer;
        View view;
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        this.editFragment = editFragment;
        this.createFromWhere = str;
        boolean z6 = wallpaperInfo.getCurrentLockWallpaperSource() == LockWallpaperSource.WALLPAPER_MANAGER;
        CombinedWallpaperView combinedWallpaperView = (z6 || z2) ? null : this;
        if (z && TextUtils.equals(wallpaperInfo.getResourceType(), "video")) {
            Bitmap currentWallpaper = wallpaperInfo.getCurrentWallpaper();
            if (currentWallpaper != null) {
                setBackground(new BitmapDrawable(getResources(), currentWallpaper));
            }
            IWallpaperLayer.Companion companion = IWallpaperLayer.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            VideoTextureView videoTextureViewCreateVideoWallpaper = companion.createVideoWallpaper(context, getSurfaceCallback(), wallpaperInfo.getCurrentWallpaper(), null, this.createFromWhere);
            this.currentWallpaperView = videoTextureViewCreateVideoWallpaper;
            videoTextureViewCreateVideoWallpaper.addFirstFrameListener(new VideoTextureView.FirstFrameListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda0
                @Override // com.miui.keyguard.editor.edit.wallpaper.VideoTextureView.FirstFrameListener
                public final void onFirstFrame() {
                    CombinedWallpaperView.init$lambda$4$lambda$3(this.f$0);
                }
            });
            z5 = z;
            viewCreateWallpaperLayer = videoTextureViewCreateVideoWallpaper;
        } else {
            IWallpaperLayer.Companion companion2 = IWallpaperLayer.Companion;
            Context context2 = getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            z5 = z;
            viewCreateWallpaperLayer = companion2.createWallpaperLayer(context2, wallpaperInfo, z5, z6, combinedWallpaperView, z3, str);
        }
        boolean z7 = z6;
        if (viewCreateWallpaperLayer != null) {
            if (z4 && !z7 && combinedWallpaperView != null) {
                addOriginWallpaperView(wallpaperInfo, viewCreateWallpaperLayer, z5);
            }
            addView(viewCreateWallpaperLayer);
            IWallpaperLayer iWallpaperLayer = (IWallpaperLayer) viewCreateWallpaperLayer;
            this.currentWallpaperView = iWallpaperLayer;
            iWallpaperLayer.setWallpaperType(wallpaperInfo.getResourceType());
            this.wallpaperInfo = wallpaperInfo;
            this.curMagicType = wallpaperInfo.getMagicType();
            boolean z8 = Wallpaper.Companion.isSupportBlurWallpaperType(wallpaperInfo.getResourceType(), this.curMagicType) && wallpaperInfo.getEnableBlur();
            Log.i("Keyguard-Editor-CombinedWallpaperView", "[Init] wallpaperType=" + wallpaperInfo.getResourceType() + ", magicType=" + this.curMagicType + ", enableBlur=" + z8 + ", editable=" + z5 + ", isAnimLayer=" + z2 + ", source=" + wallpaperInfo.getCurrentLockWallpaperSource() + ", fromWhere=" + str);
            setEnableBlurStatus(z8, false);
            if (z5) {
                View view2 = viewCreateWallpaperLayer;
                try {
                    KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                    try {
                        String str2 = this.createFromWhere;
                        String resourceType = wallpaperInfo.getResourceType();
                        int i = this.curMagicType;
                        boolean zEquals = TextUtils.equals(wallpaperInfo.getResourceType(), "video");
                        WallpaperInfoType infoType = wallpaperInfo.getInfoType();
                        String string = infoType != null ? infoType.toString() : null;
                        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                        Context context3 = getContext();
                        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
                        view = view2;
                        try {
                            keyguardEditorMiSightUtil.wallpaperDisplayStart(str2, resourceType, i, z8, zEquals, z, string, deviceUtil.isFoldLargeScreen(context3), deviceUtil.isLiteDevice());
                        } catch (Exception unused) {
                        }
                    } catch (Exception unused2) {
                        view = view2;
                    }
                } catch (Exception unused3) {
                    view = view2;
                }
            } else {
                view = viewCreateWallpaperLayer;
            }
            setFocusable(true);
            if (z || !z7) {
                return;
            }
            view.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.onFirstFrameReady();
                }
            });
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public boolean isCut() {
        return IWallpaperLayer.DefaultImpls.isCut(this);
    }

    public final boolean isScrollingOrScaling() {
        return this.isScrollingOrScaling;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration != null) {
            int i = this.screenWidthDp;
            int i2 = configuration.screenWidthDp;
            if (i != i2 || this.screenOrientation != configuration.orientation) {
                this.screenWidthDp = i2;
                this.screenOrientation = configuration.orientation;
                GestureManager gestureManager = getGestureManager();
                if (gestureManager != null) {
                    gestureManager.onConfigurationChanged();
                }
            }
        }
        adaptSensorPreviewWallpaper();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        final GLTextureView curGLTextureView = getCurGLTextureView();
        if (curGLTextureView != null) {
            curGLTextureView.queueEvent(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    CombinedWallpaperView.onDetachedFromWindow$lambda$32(curGLTextureView);
                }
            });
        }
    }

    @Override // com.miui.miwallpaper.opengl.GlassRenderer.IRenderListener
    public void onDrawFrame(GL10 gl10) {
        Log.i("Keyguard-Editor-CombinedWallpaperView", "onDrawFrame: ");
        final long jCurrentTimeMillis = System.currentTimeMillis();
        post(new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CombinedWallpaperView.onDrawFrame$lambda$31(this.f$0, jCurrentTimeMillis);
            }
        });
    }

    public final void onFirstBindWallpaper() {
        if (!this.needStartWallpaperChangedAnim) {
            this.isFirstBindWallpaper = true;
            return;
        }
        this.needStartWallpaperChangedAnim = false;
        ObjectAnimator objectAnimator = this.showAlphaAnim;
        if (objectAnimator != null) {
            objectAnimator.start();
        }
    }

    public final void onFirstFrameReady() {
        if (this.isFirstFrameReady) {
            return;
        }
        this.isFirstFrameReady = true;
        Log.i("Keyguard-Editor-CombinedWallpaperView", "[FirstFrame] ready: magicType=" + this.curMagicType + ", enableBlur=" + this.enableBlur + ", fromWhere=" + this.createFromWhere);
        CombineEditListener combineEditListener = this.onceCombineEditListener;
        if (combineEditListener != null) {
            combineEditListener.onTemplateReady(1);
        }
        this.onceCombineEditListener = null;
    }

    @Override // com.miui.miwallpaper.opengl.GlassRenderer.IRenderListener
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        GLTextureView gLTextureView = iWallpaperLayer instanceof GLTextureView ? (GLTextureView) iWallpaperLayer : null;
        if (gLTextureView != null) {
            gLTextureView.onSurfaceCreated(gl10, eGLConfig);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void onWallpaperSelected(WallpaperTypeInfo wallpaperTypeInfo, String str) {
        if ((wallpaperTypeInfo != null ? wallpaperTypeInfo.getBitmap() : null) == null) {
            try {
                KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "CombinedWallpaperView.onWallpaperSelected_bitmap_null", null, 2, null);
            } catch (Exception unused) {
            }
        } else {
            if (Intrinsics.areEqual(wallpaperTypeInfo.getType(), CategoryConstants.CATEGORY_SUPER_WALLPAPER)) {
                switchGlassShader(0, null);
            }
            animSwitchWallpaperView(wallpaperTypeInfo);
        }
    }

    public final boolean performClick(MotionEvent motionEvent) {
        int i = R.id.kg_wallpaper_layer_click_point_tag;
        setTag(i, new Point((int) motionEvent.getRawX(), (int) motionEvent.getRawY()));
        ObserverClickListener observerClickListener = this.touchClickListener;
        boolean zOnObserveTouchClick = observerClickListener != null ? observerClickListener.onObserveTouchClick(motionEvent) : false;
        setTag(i, null);
        return zOnObserveTouchClick;
    }

    public final void releaseRender() {
        HashSet hashSet;
        GLTextureView curGLTextureView = getCurGLTextureView();
        if (curGLTextureView != null) {
            curGLTextureView.clearAnimation();
        }
        if (curGLTextureView != null && (hashSet = curGLTextureView.wallpaperChangedListenerSet) != null) {
            hashSet.clear();
        }
        if (curGLTextureView != null) {
            curGLTextureView.setWallpaperCallback(null);
        }
        if (curGLTextureView != null) {
            curGLTextureView.glassRender = null;
        }
        if (curGLTextureView != null) {
            curGLTextureView.queueEvent(new RenderRunable(curGLTextureView, 0, null));
        }
        if (curGLTextureView != null) {
            curGLTextureView.stopGLThread();
        }
    }

    public final void requestRender() {
        GLTextureView curGLTextureView = getCurGLTextureView();
        if (curGLTextureView != null) {
            curGLTextureView.queueEvent(new RenderRunable(curGLTextureView, 1, null));
        }
    }

    public final void resetCurrentMagicType(int i) {
        this.curMagicType = i;
    }

    public final void setBlockSelfGestures(boolean z) {
        this.blockSelfGestures = z;
    }

    public final void setEnableBlurStatus(boolean z, boolean z2) {
        GLTextureView curGLTextureView;
        boolean z3 = this.enableBlur;
        if (z3 == z) {
            return;
        }
        Log.i("Keyguard-Editor-CombinedWallpaperView", "[BlurStatus] changed: " + z3 + " -> " + z + ", isRefresh=" + z2 + ", magicType=" + this.curMagicType);
        this.enableBlur = z;
        if (!z2 || (curGLTextureView = getCurGLTextureView()) == null) {
            return;
        }
        curGLTextureView.queueEvent(new RenderRunable(curGLTextureView, 2, Boolean.valueOf(this.enableBlur)));
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setImageAndPosition(@Nullable Bitmap bitmap, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str, @Nullable String str2) {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            iWallpaperLayer.setImageAndPosition(bitmap, wallpaperPositionInfo, str, str2);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setInTemplate(boolean z) {
        this.inTemplate = z;
    }

    public final void setObserveTouchClickListener(@NotNull ObserverClickListener l) {
        Intrinsics.checkNotNullParameter(l, "l");
        this.touchClickListener = l;
    }

    public final void setOnceCombineEditListener(@NotNull CombineEditListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onceCombineEditListener = listener;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setOriginBitmap(@Nullable Bitmap bitmap) {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            iWallpaperLayer.setOriginBitmap(bitmap);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setScaleable(@Nullable Boolean bool) {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            iWallpaperLayer.setScaleable(bool);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperCallback(@Nullable WallpaperCallback wallpaperCallback) {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            iWallpaperLayer.setWallpaperCallback(wallpaperCallback);
        }
    }

    public final void setWallpaperLongClickListener(@Nullable View.OnLongClickListener onLongClickListener) {
        this.wallpaperLongClickListener = onLongClickListener;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperType(@Nullable String str) {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            iWallpaperLayer.setWallpaperType(str);
        }
    }

    public final void startWallpaperViewShowAnimation(View view) {
        Log.i("Keyguard-Editor-CombinedWallpaperView", "startWallpaperLayerShowAnimation: wallpaperLayer == " + view);
        if (view == null) {
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, 0.0f, 1.0f);
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView$startWallpaperViewShowAnimation$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                View view2 = this.this$0.originWallpaperView;
                if (view2 != null) {
                    this.this$0.removeView(view2);
                }
                this.this$0.wallpaperView = null;
            }
        });
        objectAnimatorOfFloat.setInterpolator(new DecelerateInterpolator(1.0f));
        objectAnimatorOfFloat.setDuration(400L);
        objectAnimatorOfFloat.start();
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void switchGlassShader(int i, WallpaperEditParam wallpaperEditParam) {
        IWallpaperLayer.DefaultImpls.switchGlassShader(this, i, wallpaperEditParam);
        if (this.currentWallpaperView == null) {
            Log.e("Keyguard-Editor-CombinedWallpaperView", "please init currentWallpaperView first");
            try {
                KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "CombinedWallpaperView.switchGlassShader_currentWallpaperView_null", null, 2, null);
                return;
            } catch (Exception unused) {
                return;
            }
        }
        int i2 = this.curMagicType;
        this.curMagicType = i;
        Log.i("Keyguard-Editor-CombinedWallpaperView", "[GlassShader] switch: " + i2 + " -> " + i + ", param=" + wallpaperEditParam);
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        Intrinsics.checkNotNull(iWallpaperLayer);
        iWallpaperLayer.switchGlassShader(i, wallpaperEditParam);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer, com.miui.keyguard.editor.edit.wallpaper.VideoTextureView] */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8, types: [android.view.View, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r23v0, types: [android.widget.FrameLayout, com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView] */
    public final void switchWallpaperView(WallpaperInfo wallpaperInfo, Bitmap bitmap, WallpaperPositionInfo wallpaperPositionInfo, boolean z, boolean z2, WallpaperInfoType wallpaperInfoType, boolean z3) {
        ?? CreateVideoWallpaper;
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        Log.i("Keyguard-Editor-CombinedWallpaperView", "[SwitchWallpaper] type=" + wallpaperInfo.getResourceType() + ", magicType=" + wallpaperInfo.getMagicType() + ", curMagicType=" + this.curMagicType + ", enableBlur=" + wallpaperInfo.getEnableBlur() + ", forceBlurFalse=" + z + ", bitmapSize=" + (bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null) + AnimatedProperty.PROPERTY_NAME_X + (bitmap != null ? Integer.valueOf(bitmap.getHeight()) : null) + ", editable=" + z3 + ", infoType=" + wallpaperInfoType);
        WallpaperCallback wallpaperCallback = getWallpaperCallback();
        HashSet<WallpaperChangedListener> wallpaperChangedListenerSet = getWallpaperChangedListenerSet();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = this.currentWallpaperView;
        this.enableBlur = z ? false : wallpaperInfo.getEnableBlur();
        this.isFirstBindWallpaper = false;
        if (z3 && TextUtils.equals(wallpaperInfo.getResourceType(), "video")) {
            IWallpaperLayer.Companion companion = IWallpaperLayer.Companion;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            CreateVideoWallpaper = companion.createVideoWallpaper(context, getSurfaceCallback(), bitmap, null, this.createFromWhere + "-switchWallpaperView");
            if (wallpaperChangedListenerSet != null) {
                CreateVideoWallpaper.wallpaperChangedListenerSet.addAll(wallpaperChangedListenerSet);
            }
            this.currentWallpaperView = CreateVideoWallpaper;
            CreateVideoWallpaper.setWallpaperCallback(wallpaperCallback);
            CreateVideoWallpaper.setScaleable(Boolean.valueOf(WallpaperEditor.Companion.canScale(wallpaperInfo.getResourceType())));
        } else {
            if (bitmap == null) {
                try {
                    KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                    Bundle bundle = new Bundle();
                    bundle.putString("resourceType", wallpaperInfo.getResourceType());
                    Unit unit = Unit.INSTANCE;
                    keyguardEditorMiSightUtil.reportWallpaperNotDisplayed("CombinedWallpaperView.switchWallpaperView_bitmap_null", bundle);
                } catch (Exception unused) {
                }
            }
            if (wallpaperInfo.getMagicType() != this.curMagicType && wallpaperInfoType != WallpaperInfoType.HOME_WALLPAPER) {
                resetCurrentMagicType(wallpaperInfo.getMagicType());
            }
            GLTextureView gLTextureViewCreateMyLockShaderWallpaper = IWallpaperLayer.Companion.createMyLockShaderWallpaper(new WeakReference(getContext()), bitmap, null, this.curMagicType, wallpaperPositionInfo, wallpaperInfo.isNeedDark(), this.enableBlur, null, this.createFromWhere + "-switchWallpaperView", wallpaperInfoType == null ? wallpaperInfo.getInfoType() : wallpaperInfoType);
            if (gLTextureViewCreateMyLockShaderWallpaper != null) {
                Log.i("Keyguard-Editor-CombinedWallpaperView", "switchWallpaperView: enableBlur =" + this.enableBlur);
                setEnableBlurStatus(this.enableBlur, false);
                if (wallpaperChangedListenerSet != null) {
                    gLTextureViewCreateMyLockShaderWallpaper.wallpaperChangedListenerSet.addAll(wallpaperChangedListenerSet);
                }
                this.currentWallpaperView = gLTextureViewCreateMyLockShaderWallpaper;
                gLTextureViewCreateMyLockShaderWallpaper.setWallpaperCallback(wallpaperCallback);
                gLTextureViewCreateMyLockShaderWallpaper.setScaleable(Boolean.valueOf(WallpaperEditor.Companion.canScale(wallpaperInfo.getResourceType())));
                CreateVideoWallpaper = gLTextureViewCreateMyLockShaderWallpaper;
            } else {
                CreateVideoWallpaper = 0;
            }
            if (CreateVideoWallpaper == 0 && bitmap != null) {
                try {
                    KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "CombinedWallpaperView.switchWallpaperView_newView_null_bitmap_notNull", null, 2, null);
                } catch (Exception unused2) {
                }
            }
        }
        if (!z2) {
            Object obj = objectRef.element;
            if (obj instanceof View) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.view.View");
                removeView((View) obj);
            }
            if (CreateVideoWallpaper != 0) {
                addView(CreateVideoWallpaper);
                return;
            }
            return;
        }
        if (CreateVideoWallpaper != 0) {
            addView(CreateVideoWallpaper);
        }
        if (CreateVideoWallpaper == 0 || objectRef.element == null) {
            return;
        }
        CreateVideoWallpaper.setAlpha(0.0f);
        ObjectAnimator duration = ObjectAnimator.ofFloat((Object) CreateVideoWallpaper, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, 1.0f).setDuration(300L);
        this.showAlphaAnim = duration;
        if (duration != null) {
            duration.setInterpolator(new DecelerateInterpolator());
        }
        ObjectAnimator objectAnimator = this.showAlphaAnim;
        if (objectAnimator != null) {
            objectAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView.switchWallpaperView.6
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    Intrinsics.checkNotNullParameter(animation, "animation");
                    CombinedWallpaperView combinedWallpaperView = CombinedWallpaperView.this;
                    Object obj2 = objectRef.element;
                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type android.view.View");
                    combinedWallpaperView.removeView((View) obj2);
                    objectRef.element = null;
                    super.onAnimationEnd(animation);
                }
            });
        }
        if (!this.isFirstBindWallpaper) {
            this.needStartWallpaperChangedAnim = true;
            return;
        }
        this.isFirstBindWallpaper = false;
        ObjectAnimator objectAnimator2 = this.showAlphaAnim;
        if (objectAnimator2 != null) {
            objectAnimator2.start();
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public View wallpaperLayerView() {
        IWallpaperLayer iWallpaperLayer = this.currentWallpaperView;
        if (iWallpaperLayer != null) {
            return iWallpaperLayer.wallpaperLayerView();
        }
        return null;
    }
}
