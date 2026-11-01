package com.miui.aod.stylelist;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.miui.aod.AODApplication;
import com.miui.aod.Utils;
import com.miui.aod.template.CurrentTemplateApi;
import com.miui.keyguard.editor.data.bean.ClockInfo;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
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
/* JADX INFO: compiled from: FsAodViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FsAodViewModel extends ContentObserver implements DefaultLifecycleObserver {
    public final Context context;
    public final MutableLiveData fsAodPreview;
    public final MutableLiveData fsSameToLockFlag;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FsAodViewModel(Context context) {
        super(new Handler(Looper.getMainLooper()));
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.fsAodPreview = new MutableLiveData();
        this.fsSameToLockFlag = new MutableLiveData();
    }

    public final void loadFsAodStyle() {
        ClockInfo clockInfo;
        Context aodSettingDensityContext = AODApplication.aodSettingDensityContext;
        Intrinsics.checkNotNullExpressionValue(aodSettingDensityContext, "aodSettingDensityContext");
        TemplateConfig currentTemplateConfig = new CurrentTemplateApi(aodSettingDensityContext).getCurrentTemplateConfig();
        Log.i("FsAodViewModel", "loadFsAodStyle config=" + currentTemplateConfig);
        String templateId = (currentTemplateConfig == null || (clockInfo = currentTemplateConfig.getClockInfo()) == null) ? null : clockInfo.getTemplateId();
        if (templateId == null || templateId.length() == 0) {
            return;
        }
        this.fsAodPreview.setValue(Boolean.TRUE);
    }

    public final void observe(LifecycleOwner lifecycleOwner, Observer preViewObserver) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        Intrinsics.checkNotNullParameter(preViewObserver, "preViewObserver");
        this.fsSameToLockFlag.setValue(Boolean.valueOf(Utils.isFsAodSameToLock(this.context)));
        this.context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("full_screen_aod_on"), false, this);
        lifecycleOwner.getLifecycle().addObserver(this);
        this.fsAodPreview.observe(lifecycleOwner, preViewObserver);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        super.onChange(z, uri);
        if (Utils.isFsAodSameToLock(this.context)) {
            loadFsAodStyle();
        } else {
            this.fsAodPreview.setValue(Boolean.FALSE);
        }
        this.fsSameToLockFlag.setValue(Boolean.valueOf(Utils.isFsAodSameToLock(this.context)));
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onCreate(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        super.onCreate(owner);
        if (Utils.isFsAodSameToLock(this.context)) {
            loadFsAodStyle();
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        super.onDestroy(owner);
        this.context.getContentResolver().unregisterContentObserver(this);
    }
}
