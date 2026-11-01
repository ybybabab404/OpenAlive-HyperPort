package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import com.miui.keyguard.editor.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
/* JADX INFO: compiled from: LargeFontLanguageHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class LargeFontLanguageHelper {
    public static final LargeFontLanguageHelper INSTANCE = new LargeFontLanguageHelper();
    public static final List largeFontLanguagesForTitleDescription;
    public static final List largeFontLanguagesForTopButton;

    static {
        ArrayList arrayList = new ArrayList();
        largeFontLanguagesForTitleDescription = arrayList;
        ArrayList arrayList2 = new ArrayList();
        largeFontLanguagesForTopButton = arrayList2;
        arrayList.add("bo-CN");
        arrayList2.add("ml-IN");
        arrayList2.add("ml-rIN");
    }

    public final void adaptTitleAndDescriptionForLargeFontLanguage(TextView titleView, TextView descriptionView, boolean z) {
        Intrinsics.checkNotNullParameter(titleView, "titleView");
        Intrinsics.checkNotNullParameter(descriptionView, "descriptionView");
        try {
            Context context = titleView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            boolean zIsLargeFontLanguage = isLargeFontLanguage(context, largeFontLanguagesForTitleDescription);
            Log.i("Keyguard-Editor-LargeFontLanguageHelper", "adaptLargeFontLanguageForTitleAndDescription -> isLargeFontLanguage = " + zIsLargeFontLanguage);
            if (zIsLargeFontLanguage) {
                setTextSize(titleView, R.dimen.kg_main_item_floor_title_small_text_size);
                setTextSize(descriptionView, R.dimen.kg_main_item_floor_desc_small_text_size);
            } else if (z) {
                setTextSize(titleView, R.dimen.kg_main_item_floor_title_text_size);
                setTextSize(descriptionView, R.dimen.kg_main_item_floor_desc_text_size);
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-LargeFontLanguageHelper", "adaptTitleAndDescriptionForLargeFontLanguage", e);
        }
    }

    public final void adaptTopButtonForLargeFontLanguage(TextView applyButton, TextView cancelButton) {
        Intrinsics.checkNotNullParameter(applyButton, "applyButton");
        Intrinsics.checkNotNullParameter(cancelButton, "cancelButton");
        try {
            Context context = applyButton.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            boolean zIsLargeFontLanguage = isLargeFontLanguage(context, largeFontLanguagesForTopButton);
            Log.i("Keyguard-Editor-LargeFontLanguageHelper", "adaptTopButtonForLargeFontLanguage -> isLargeFontLanguage = " + zIsLargeFontLanguage);
            if (zIsLargeFontLanguage) {
                int i = R.dimen.kg_top_button_small_text_size;
                setTextSize(applyButton, i);
                setTextSize(cancelButton, i);
                int i2 = R.dimen.kg_top_button_height;
                setFixedHeight(applyButton, i2);
                setFixedHeight(cancelButton, i2);
            }
        } catch (Exception e) {
            Log.e("Keyguard-Editor-LargeFontLanguageHelper", "adaptTitleAndDescriptionForLargeFontLanguage", e);
        }
    }

    public final String getLocalDescription(Context context) {
        try {
            Locale locale = context.getResources().getConfiguration().getLocales().get(0);
            return locale.getLanguage() + "-" + locale.getCountry();
        } catch (Exception e) {
            Log.e("Keyguard-Editor-LargeFontLanguageHelper", "getLocalDescription error", e);
            return null;
        }
    }

    public final boolean isLargeFontLanguage(Context context, List list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        String localDescription = getLocalDescription(context);
        Log.i("Keyguard-Editor-LargeFontLanguageHelper", "isLargeFontLanguage -> localeDescription = " + localDescription);
        return CollectionsKt.contains(list, localDescription);
    }

    public final void setFixedHeight(TextView textView, int i) {
        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = textView.getResources().getDimensionPixelSize(i);
            textView.setLayoutParams(layoutParams);
        }
        textView.setPadding(textView.getPaddingLeft(), 0, textView.getPaddingRight(), 0);
        textView.setGravity(17);
    }

    public final void setTextSize(TextView textView, int i) {
        textView.setTextSize(0, textView.getResources().getDimension(i));
    }
}
