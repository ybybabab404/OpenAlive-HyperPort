package androidx.window.area;

import android.content.Context;
import androidx.window.core.ExperimentalWindowApi;
import androidx.window.extensions.area.ExtensionWindowAreaPresentation;
import androidx.window.extensions.area.WindowAreaComponent;
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
/* JADX INFO: compiled from: RearDisplayPresentationSessionPresenterImpl.kt */
/* JADX INFO: loaded from: classes.dex */
@ExperimentalWindowApi
@Metadata
public final class RearDisplayPresentationSessionPresenterImpl implements WindowAreaSessionPresenter {
    public final Context context;
    public final ExtensionWindowAreaPresentation presentation;
    public final WindowAreaComponent windowAreaComponent;

    public RearDisplayPresentationSessionPresenterImpl(WindowAreaComponent windowAreaComponent, ExtensionWindowAreaPresentation presentation) {
        Intrinsics.checkNotNullParameter(windowAreaComponent, "windowAreaComponent");
        Intrinsics.checkNotNullParameter(presentation, "presentation");
        this.windowAreaComponent = windowAreaComponent;
        this.presentation = presentation;
        Context presentationContext = presentation.getPresentationContext();
        Intrinsics.checkNotNullExpressionValue(presentationContext, "presentation.presentationContext");
        this.context = presentationContext;
    }
}
