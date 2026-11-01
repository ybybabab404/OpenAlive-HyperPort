package androidx.window.area.reflectionguard;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public interface ExtensionWindowAreaPresentationRequirements {
    @NonNull
    Context getPresentationContext();

    void setPresentationView(@NonNull View view);
}
