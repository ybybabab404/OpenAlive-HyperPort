package com.miui.keyguard.editor.homepage.util;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.core.view.KeyEventDispatcher;
import androidx.fragment.app.FragmentActivity;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.model.CrossListDataModel;
import com.miui.keyguard.editor.homepage.view.CrossViewPager;
import com.miui.keyguard.editor.view.LockScreenTransformerController;
import com.miui.keyguard.editor.view.TransformerInfo;
import com.miui.keyguard.editor.view.TransformerPositionInfo;
import java.util.List;
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
/* JADX INFO: compiled from: TransformerController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TransformerController {
    public final FragmentActivity activity;
    public final CrossViewPager crossViewPager;
    public Bitmap currentHomeTemplateScreenshot;
    public Bitmap currentLockTemplateScreenshot;
    public final CrossListDataModel dataModel;

    public TransformerController(FragmentActivity activity, CrossListDataModel crossListDataModel, CrossViewPager crossViewPager) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(crossViewPager, "crossViewPager");
        this.activity = activity;
        this.dataModel = crossListDataModel;
        this.crossViewPager = crossViewPager;
    }

    public final TransformerInfo generateTransformerInfo(int i, int i2) {
        TemplateItemBean item = this.crossViewPager.getItem(i, i2);
        return new TransformerInfo(item != null ? item.getTitleRes() : null, item != null ? item.getDescriptionRes() : null, generateTransformerPositionInfo(item, true), generateTransformerPositionInfo(this.crossViewPager.getItem(i - 1, i2), true), generateTransformerPositionInfo(this.crossViewPager.getItem(i + 1, i2), true), generateTransformerPositionInfo(this.crossViewPager.getSelectedItemForFloor(i2 + 1), true));
    }

    public final TransformerInfo generateTransformerInfo(List list) {
        if (list.size() < 2) {
            return null;
        }
        FloorItemBean floorItemBean = (FloorItemBean) list.get(0);
        FloorItemBean floorItemBean2 = (FloorItemBean) list.get(1);
        if (floorItemBean.getItems().isEmpty()) {
            return null;
        }
        TemplateItemBean templateItemBean = (TemplateItemBean) floorItemBean.getItems().get(1);
        return new TransformerInfo(templateItemBean.getTitleRes(), templateItemBean.getDescriptionRes(), generateTransformerPositionInfo(templateItemBean, false), generateTransformerPositionInfo((TemplateItemBean) floorItemBean.getItems().get(0), false), floorItemBean.getItems().size() > 2 ? generateTransformerPositionInfo((TemplateItemBean) floorItemBean.getItems().get(2), false) : null, floorItemBean2.getItems().isEmpty() ? null : generateTransformerPositionInfo((TemplateItemBean) floorItemBean2.getItems().get(0), false));
    }

    public final TransformerPositionInfo generateTransformerPositionInfo(TemplateItemBean templateItemBean, boolean z) {
        Integer numValueOf = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
        if (numValueOf != null && numValueOf.intValue() == 1) {
            return new TransformerPositionInfo(numValueOf.intValue(), this.currentLockTemplateScreenshot, this.currentHomeTemplateScreenshot, null, null, templateItemBean != null ? templateItemBean.getCurrent() : null, 24, null);
        }
        if (numValueOf == null || numValueOf.intValue() != 2) {
            if ((numValueOf != null && numValueOf.intValue() == -1) || ((numValueOf != null && numValueOf.intValue() == 3) || (numValueOf != null && numValueOf.intValue() == 4))) {
                return new TransformerPositionInfo(numValueOf.intValue(), null, null, null, templateItemBean, null, 46, null);
            }
            return null;
        }
        CrossListDataModel crossListDataModel = this.dataModel;
        boolean zIsHistoryTemplateExists = crossListDataModel != null ? crossListDataModel.isHistoryTemplateExists(templateItemBean) : false;
        Log.i("Keyguard-Editor-TransformerController", "generateTransformerPositionInfo -> checkHistoryExists = " + z + ", isHistoryTemplateExists = " + zIsHistoryTemplateExists);
        if (zIsHistoryTemplateExists || !z) {
            return new TransformerPositionInfo(numValueOf.intValue(), null, null, null, templateItemBean, templateItemBean.getCurrent(), 14, null);
        }
        return null;
    }

    public final void onDataLoadCompleted(List list, boolean z) {
        Intrinsics.checkNotNullParameter(list, "list");
        KeyEventDispatcher.Component component = this.activity;
        if (component instanceof LockScreenTransformerController) {
            ((LockScreenTransformerController) component).onDataLoadCompleted(generateTransformerInfo(list), z);
        }
    }
}
