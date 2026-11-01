package com.miui.aod.components;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.widget.IAodClock;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public interface StyleSelectContract {

    public interface StyleSelectPresenter<StyleSelectView> extends DefaultLifecycleObserver {
        default void applyStyleParameter(String str) {
        }

        String getStyleCategory();

        StyleSelectView getView();

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        default void onCreate(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        default void onDestroy(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        default void onPause(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        default void onResume(@NonNull LifecycleOwner lifecycleOwner) {
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        /* bridge */ /* synthetic */ default void onStart(@NotNull LifecycleOwner lifecycleOwner) {
            super.onStart(lifecycleOwner);
        }

        @Override // androidx.lifecycle.DefaultLifecycleObserver
        /* bridge */ /* synthetic */ default void onStop(@NotNull LifecycleOwner lifecycleOwner) {
            super.onStop(lifecycleOwner);
        }
    }

    public interface StyleSelectView<StyleSelectPresenter> {
        default void freshStatusIfNeeded() {
        }

        BaseStyleSelectPresenter getPresenter();

        default StyleInfo getStyleInfo() {
            return null;
        }

        default IAodClock initStyleInfoSelected(String str, String str2, Bundle bundle) {
            return null;
        }

        default void notifyConfigurationChanged() {
        }

        default void setPresenter(StyleSelectPresenter styleselectpresenter) {
        }

        default void updateTime() {
        }
    }
}
