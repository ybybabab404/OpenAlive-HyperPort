package com.miui.keyguard.editor.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: BaseFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public abstract class BaseFragment extends Fragment {
    public View contentLayoutView;
    public OnBackPressedCallback onBackPressedCallback;

    public BaseFragment() {
    }

    public BaseFragment(int i) {
        super(i);
    }

    public final void addOnBackPressedCallback(OnBackPressedCallback onBackPressedCallback) {
        FragmentActivity activity;
        OnBackPressedDispatcher onBackPressedDispatcher;
        if (onBackPressedCallback == null || (activity = getActivity()) == null || (onBackPressedDispatcher = activity.getOnBackPressedDispatcher()) == null) {
            return;
        }
        onBackPressedDispatcher.addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    public final boolean checkFragmentInactive() {
        return !isAdded() || isDetached();
    }

    public final OnBackPressedCallback getOnBackPressedCallback() {
        return this.onBackPressedCallback;
    }

    public final OnBackPressedCallback initializeOnBackPressedCallback() {
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        if (onBackPressedCallback != null) {
            return onBackPressedCallback;
        }
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return null;
        }
        OnBackPressedCallback onBackPressedCallbackInstanceOnBackPressedCallback = instanceOnBackPressedCallback(activity);
        this.onBackPressedCallback = onBackPressedCallbackInstanceOnBackPressedCallback;
        return onBackPressedCallbackInstanceOnBackPressedCallback;
    }

    public OnBackPressedCallback instanceOnBackPressedCallback(FragmentActivity fragmentActivity) {
        Intrinsics.checkNotNullParameter(fragmentActivity, "fragmentActivity");
        return new BaseOnBackPressedCallback(fragmentActivity);
    }

    public final void navigateTo(Fragment fragment, String tag, Bundle args) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(args, "args");
        fragment.setArguments(args);
        getParentFragmentManager().beginTransaction().add(R.id.kg_fragment_container, fragment, tag).addToBackStack(tag).commit();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View view = this.contentLayoutView;
        return view == null ? super.onCreateView(inflater, viewGroup, bundle) : view;
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(!z);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        OnBackPressedCallback onBackPressedCallback = this.onBackPressedCallback;
        if (onBackPressedCallback != null) {
            onBackPressedCallback.setEnabled(true);
        }
    }

    public void onScreenConfigurationChanged(boolean z, boolean z2) {
        Log.d("BaseFragment", "onScreenConfigurationChanged: " + this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        addOnBackPressedCallback(initializeOnBackPressedCallback());
    }

    public final void setInterceptAllEventsOnApply(boolean z) {
        if (!checkFragmentInactive()) {
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            if (fragmentActivityRequireActivity instanceof EditorActivity) {
                ((EditorActivity) fragmentActivityRequireActivity).setInterceptAllEventsOnApplying(z);
                return;
            }
            return;
        }
        Log.w("Keyguard-Editor-BaseFragment", "setInterceptAllEventsOnApply: " + z + " failed. fragment(" + this + ") is not attached.");
    }
}
