package com.miui.keyguard.shortcuts.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.miui.keyguard.shortcuts.R;
import miuix.core.widget.NestedScrollView;
import miuix.miuixbasewidget.widget.FilterSortView;
import miuix.recyclerview.widget.RecyclerView;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes2.dex */
public final class ActivityLockscreenShortcutsSettingsBinding implements ViewBinding {
    public final FilterSortView filterSortView;
    public final ImageView leftImage;
    public final FilterSortView.TabView leftTab;
    public final RecyclerView recyclerView;
    public final ImageView rightImage;
    public final FilterSortView.TabView rightTab;
    public final LinearLayout rootView;
    public final NestedScrollView scrollView;
    public final SpringBackLayout springBacklayout;
    public final ImageView wallpaperPreview;

    public ActivityLockscreenShortcutsSettingsBinding(LinearLayout linearLayout, FilterSortView filterSortView, ImageView imageView, FilterSortView.TabView tabView, RecyclerView recyclerView, ImageView imageView2, FilterSortView.TabView tabView2, NestedScrollView nestedScrollView, SpringBackLayout springBackLayout, ImageView imageView3) {
        this.rootView = linearLayout;
        this.filterSortView = filterSortView;
        this.leftImage = imageView;
        this.leftTab = tabView;
        this.recyclerView = recyclerView;
        this.rightImage = imageView2;
        this.rightTab = tabView2;
        this.scrollView = nestedScrollView;
        this.springBacklayout = springBackLayout;
        this.wallpaperPreview = imageView3;
    }

    public static ActivityLockscreenShortcutsSettingsBinding bind(View view) {
        int i = R.id.filterSortView;
        FilterSortView filterSortView = (FilterSortView) ViewBindings.findChildViewById(view, i);
        if (filterSortView != null) {
            i = R.id.leftImage;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
            if (imageView != null) {
                i = R.id.leftTab;
                FilterSortView.TabView tabView = (FilterSortView.TabView) ViewBindings.findChildViewById(view, i);
                if (tabView != null) {
                    i = R.id.recyclerView;
                    RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i);
                    if (recyclerView != null) {
                        i = R.id.rightImage;
                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i);
                        if (imageView2 != null) {
                            i = R.id.rightTab;
                            FilterSortView.TabView tabView2 = (FilterSortView.TabView) ViewBindings.findChildViewById(view, i);
                            if (tabView2 != null) {
                                i = R.id.scrollView;
                                NestedScrollView nestedScrollView = (NestedScrollView) ViewBindings.findChildViewById(view, i);
                                if (nestedScrollView != null) {
                                    i = R.id.springBacklayout;
                                    SpringBackLayout springBackLayout = (SpringBackLayout) ViewBindings.findChildViewById(view, i);
                                    if (springBackLayout != null) {
                                        i = R.id.wallpaperPreview;
                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i);
                                        if (imageView3 != null) {
                                            return new ActivityLockscreenShortcutsSettingsBinding((LinearLayout) view, filterSortView, imageView, tabView, recyclerView, imageView2, tabView2, nestedScrollView, springBackLayout, imageView3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }

    public static ActivityLockscreenShortcutsSettingsBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ActivityLockscreenShortcutsSettingsBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View viewInflate = layoutInflater.inflate(R.layout.activity_lockscreen_shortcuts_settings, viewGroup, false);
        if (z) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public LinearLayout getRoot() {
        return this.rootView;
    }
}
