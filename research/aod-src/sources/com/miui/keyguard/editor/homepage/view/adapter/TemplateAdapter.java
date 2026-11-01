package com.miui.keyguard.editor.homepage.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.homepage.view.generator.FactoryParams;
import com.miui.keyguard.editor.utils.CommonUtils;
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
    */
/* JADX INFO: compiled from: AdapterAndHolderImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class TemplateAdapter extends BaseAdapter<TemplateItemBean, CrossViewPagerCallback> {
    public final String TAG;
    public BaseItemViewFactoryImpl itemViewFactory;
    public final boolean myFloor;

    public TemplateAdapter(boolean z) {
        super(0);
        this.myFloor = z;
        this.TAG = "Keyguard-Editor-TemplateAdapter";
    }

    public /* synthetic */ TemplateAdapter(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    @Override // com.miui.keyguard.editor.homepage.view.adapter.BaseAdapter
    public BaseViewHolder createMyViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Context context = parent.getContext();
        Intrinsics.checkNotNull(context);
        LayoutInflater layoutInflater = getLayoutInflater(context);
        if (this.itemViewFactory == null) {
            this.itemViewFactory = new BaseItemViewFactoryImpl(context, this.myFloor);
        }
        FactoryParams factoryParams = new FactoryParams(isPreset(), false, 2, null);
        if (i != -1) {
            if (i == 1) {
                BaseItemViewFactoryImpl baseItemViewFactoryImpl = this.itemViewFactory;
                Intrinsics.checkNotNull(baseItemViewFactoryImpl);
                return new MyTemplateViewHolder(layoutInflater, parent, baseItemViewFactoryImpl, factoryParams);
            }
            if (i != 2 && i != 3 && i != 4) {
                Log.w(this.TAG, "Unsupported viewType: " + i);
                return new BlankViewHolder(context);
            }
        }
        BaseItemViewFactoryImpl baseItemViewFactoryImpl2 = this.itemViewFactory;
        Intrinsics.checkNotNull(baseItemViewFactoryImpl2);
        return new TemplatePreviewViewHolder(layoutInflater, parent, baseItemViewFactoryImpl2, factoryParams, CommonUtils.INSTANCE.checkThemeSupportDownload(context));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItem(i);
        if (templateItemBean != null) {
            return templateItemBean.hashCode();
        }
        return 0L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        TemplateItemBean templateItemBean = (TemplateItemBean) getItem(i);
        if (templateItemBean != null) {
            return templateItemBean.getItemType();
        }
        return -1;
    }

    public boolean isPreset() {
        return true;
    }
}
