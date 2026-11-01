package miuix.appcompat.app;

import android.text.Editable;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public interface ISearchActionCallback {
    default void afterSearchTextChanged(Editable editable) {
    }

    default void beforeSearchTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    default void onBackIconEndClick(View view) {
    }

    default void onBackIconStartClick(View view) {
    }

    default void onFocusChange(View view, boolean z) {
    }

    default void onSearchButtonClick(View view) {
    }

    default void onSearchTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
