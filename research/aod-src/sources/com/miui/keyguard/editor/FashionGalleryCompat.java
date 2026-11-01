package com.miui.keyguard.editor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.homepage.bean.GalleryStatusBean;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: FashiongalleryCompat.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FashionGalleryCompat {
    public static final Companion Companion = new Companion(null);
    public final Fragment fragment;

    /* JADX INFO: compiled from: FashiongalleryCompat.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FashionGalleryCompat(Fragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        this.fragment = fragment;
    }

    public final boolean needStartGalleryDialog() {
        String activityAction;
        Intent intent;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        Context context = this.fragment.getContext();
        if (context == null) {
            return false;
        }
        FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
        FragmentActivity activity = this.fragment.getActivity();
        Integer numValueOf = null;
        GalleryStatusBean galleryStatus$default = FashionGalleryHelper.getGalleryStatus$default(fashionGalleryHelper, activity != null ? activity.getApplicationContext() : null, false, 2, null);
        if (Intrinsics.areEqual(galleryStatus$default.isCallGallery(), Boolean.FALSE) || (activityAction = galleryStatus$default.getActivityAction()) == null || activityAction.length() == 0) {
            Log.d("FashionGalleryCompat", "No need to call gallery");
            return false;
        }
        EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(context);
        if (viewModel != null) {
            CommonConfig applyTemplateConfig = viewModel.getApplyTemplateConfig();
            String resourceType = (applyTemplateConfig == null || (lockscreenInfo = applyTemplateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType();
            Log.d("FashionGalleryCompat", "resourceType: " + resourceType + ", galleryStatusBean: " + galleryStatus$default);
            if (!Intrinsics.areEqual(resourceType, "gallery") && !Intrinsics.areEqual(resourceType, "image") && resourceType != null) {
                return false;
            }
            Log.d("FashionGalleryCompat", "show International ribbon dialog");
            try {
                Intent intent2 = new Intent(galleryStatus$default.getActivityAction());
                FragmentActivity activity2 = this.fragment.getActivity();
                if (activity2 != null && (intent = activity2.getIntent()) != null) {
                    numValueOf = Integer.valueOf(intent.getIntExtra("openSource", -1));
                }
                intent2.putExtra("openSource", numValueOf);
                this.fragment.startActivityForResult(intent2, com.miui.maml.R.styleable.AppCompatTheme_windowActionBar);
                return true;
            } catch (Exception e) {
                Log.d("FashionGalleryCompat", "Failed to start gallery activity: " + galleryStatus$default.getActivityAction(), e);
            }
        }
        return false;
    }

    public final boolean shouldContinue() {
        return needStartGalleryDialog();
    }
}
