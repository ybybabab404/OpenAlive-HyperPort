package com.miui.keyguard.editor.history;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.util.Consumer;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import com.miui.keyguard.editor.CommonEditorActivity;
import com.miui.keyguard.editor.EditorActivityForSettings;
import com.miui.keyguard.editor.FashionGalleryCompat;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.BaseFragment;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.databinding.KgEditHistoryFragmentBinding;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.edit.color.picker.ColorPickerCache;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.history.bean.HistoryItemBean;
import com.miui.keyguard.editor.history.common.ViewExtKt;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ApplyCallback;
import com.miui.keyguard.editor.homepage.util.SelectItemProvider;
import com.miui.keyguard.editor.homepage.util.TemplateApplyController;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.settings.util.SettingsTrackHelper;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewStylerKt;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.viewmodel.EditHistoryViewModel;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.Dispatchers;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.internal.app.widget.actionbar.CollapseTitle;
import miuix.appcompat.internal.app.widget.actionbar.ExpandTitle;
import miuix.appcompat.internal.view.ColorTransitionTextView;
import miuix.appcompat.widget.Button;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.recyclerview.widget.RecyclerView;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: HistoryFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class HistoryFragment extends BaseFragment implements CrossViewPagerCallback, SelectItemProvider {
    public static final Companion Companion = new Companion(null);
    public MaterialDayNightToken Pured_Regular;
    public MaterialToken Pured_Regular_Dark;
    public MaterialToken Pured_Regular_Light;
    public KgEditHistoryFragmentBinding binding;
    public HistoryItemBean currentItemData;
    public FashionGalleryCompat fashionGalleryCompat;
    public ConstraintLayout historyView;
    public boolean isApplyLoading;
    public boolean isLocalUpdate;
    public boolean isResumeFromFashiongallery;
    public ActionBar mActionBar;
    public int mClickPosition;
    public int mColumnWidth;
    public AppCompatImageView mEndView;
    public AppCompatImageView mStartView;
    public TemplateApplyController mTemplateApplyController;
    public WeakReference myTemplateView;
    public boolean shouldCaptureCurrentTemplate;
    public EditHistoryViewModel vm;
    public WallpaperEditor.WallpaperPickerLauncher wallpaperPickerLauncher;
    public final Lazy mHistoryAdapter$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda7
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return HistoryFragment.mHistoryAdapter_delegate$lambda$0(this.f$0);
        }
    });
    public final Runnable mApplyRunnable = new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda8
        @Override // java.lang.Runnable
        public final void run() {
            HistoryFragment.mApplyRunnable$lambda$1(this.f$0);
        }
    };
    public final Runnable mScreenSizeChangedRunnable = new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda9
        @Override // java.lang.Runnable
        public final void run() {
            HistoryFragment.mScreenSizeChangedRunnable$lambda$4(this.f$0);
        }
    };

    /* JADX INFO: compiled from: HistoryFragment.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public HistoryFragment() {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(10, "frosted-pured-regular", "light").setColorBlend(ColorBlendToken.Pured_Regular_Light).setMaskBlur(60).build();
        Intrinsics.checkNotNullExpressionValue(materialTokenBuild, "build(...)");
        this.Pured_Regular_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(10, "frosted-pured-regular", "dark").setColorBlend(ColorBlendToken.Pured_Extra_Thin_Dark).setMaskBlur(60).build();
        Intrinsics.checkNotNullExpressionValue(materialTokenBuild2, "build(...)");
        this.Pured_Regular_Dark = materialTokenBuild2;
        this.Pured_Regular = new MaterialDayNightToken(this.Pured_Regular_Light, this.Pured_Regular_Dark);
    }

    public static final void initControllers$lambda$10(HistoryFragment historyFragment, Pair it) {
        Intrinsics.checkNotNullParameter(it, "it");
        historyFragment.onApplyMyTemplateCompleted((TemplateItemBean) it.getFirst(), ((Boolean) it.getSecond()).booleanValue());
    }

    private final void initView() {
        RecyclerView recyclerView;
        RecyclerView recyclerView2;
        RecyclerView recyclerView3;
        View view;
        ViewGroup.LayoutParams layoutParams;
        Button button;
        ViewGroup.LayoutParams layoutParams2;
        Button button2;
        Button button3;
        Resources resources;
        Resources resources2;
        CollapseTitle collapseTitle;
        ExpandTitle expandTitle;
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            booleanRef.element = (activity.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32;
        }
        Context context = getContext();
        if (context != null) {
            this.mStartView = new AppCompatImageView(context);
            this.mEndView = new AppCompatImageView(context);
        }
        if (getActivity() instanceof AppCompatActivity) {
            FragmentActivity activity2 = getActivity();
            Intrinsics.checkNotNull(activity2, "null cannot be cast to non-null type miuix.appcompat.app.AppCompatActivity");
            this.mActionBar = ((AppCompatActivity) activity2).getAppCompatActionBar();
        }
        String string = getResources().getString(R.string.kg_settings_editor_history_mine);
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null && (expandTitle = actionBar.getExpandTitle()) != null) {
            expandTitle.setTitle(string);
        }
        ActionBar actionBar2 = this.mActionBar;
        if (actionBar2 != null && (collapseTitle = actionBar2.getCollapseTitle()) != null) {
            collapseTitle.setTitle(string);
        }
        ActionBar actionBar3 = this.mActionBar;
        if (actionBar3 != null) {
            actionBar3.setStartView(this.mStartView);
        }
        ActionBar actionBar4 = this.mActionBar;
        if (actionBar4 != null) {
            actionBar4.setEndView(this.mEndView);
        }
        updateTheme(booleanRef.element);
        AppCompatImageView appCompatImageView = this.mStartView;
        String string2 = null;
        if (appCompatImageView != null) {
            Context context2 = getContext();
            appCompatImageView.setContentDescription((context2 == null || (resources2 = context2.getResources()) == null) ? null : resources2.getString(R.string.kg_talkback_back));
        }
        AppCompatImageView appCompatImageView2 = this.mEndView;
        if (appCompatImageView2 != null) {
            Context context3 = getContext();
            if (context3 != null && (resources = context3.getResources()) != null) {
                string2 = resources.getString(R.string.kg_settings_template_new_match);
            }
            appCompatImageView2.setContentDescription(string2);
        }
        AppCompatImageView appCompatImageView3 = this.mStartView;
        if (appCompatImageView3 != null) {
            ViewExtKt.clickNoRepeat$default(appCompatImageView3, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryFragment.initView$lambda$14(this.f$0, booleanRef, (View) obj);
                }
            }, 1, null);
        }
        AppCompatImageView appCompatImageView4 = this.mEndView;
        if (appCompatImageView4 != null) {
            ViewExtKt.clickNoRepeat$default(appCompatImageView4, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryFragment.initView$lambda$18(this.f$0, (View) obj);
                }
            }, 1, null);
        }
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = this.binding;
        if (kgEditHistoryFragmentBinding != null && (button3 = kgEditHistoryFragmentBinding.historyMore) != null) {
            ViewExtKt.clickNoRepeat$default(button3, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryFragment.initView$lambda$20(this.f$0, (View) obj);
                }
            }, 1, null);
        }
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding2 = this.binding;
        if (kgEditHistoryFragmentBinding2 != null && (button2 = kgEditHistoryFragmentBinding2.historyDelete) != null) {
            ViewExtKt.clickNoRepeat$default(button2, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryFragment.initView$lambda$21(this.f$0, (View) obj);
                }
            }, 1, null);
        }
        Context context4 = getContext();
        boolean z = context4 != null ? !DeviceUtil.INSTANCE.isFullScreenGestureNav(context4) : false;
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding3 = this.binding;
        if (kgEditHistoryFragmentBinding3 != null && (button = kgEditHistoryFragmentBinding3.historyDelete) != null && (layoutParams2 = button.getLayoutParams()) != null) {
            layoutParams2.height = getResources().getDimensionPixelSize(z ? R.dimen.kg_editor_history_delete_height_has_bottom_nav : R.dimen.kg_editor_history_delete_height);
        }
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding4 = this.binding;
        if (kgEditHistoryFragmentBinding4 != null && (view = kgEditHistoryFragmentBinding4.historyFootView) != null && (layoutParams = view.getLayoutParams()) != null) {
            layoutParams.height = getResources().getDimensionPixelSize(z ? R.dimen.kg_editor_history_list_padding_bottom_has_bottom_nav : R.dimen.kg_editor_history_list_padding_bottom);
        }
        updateMore();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding5 = this.binding;
        if (kgEditHistoryFragmentBinding5 != null && (recyclerView3 = kgEditHistoryFragmentBinding5.historyList) != null) {
            recyclerView3.setLayoutManager(gridLayoutManager);
        }
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding6 = this.binding;
        if (kgEditHistoryFragmentBinding6 != null && (recyclerView2 = kgEditHistoryFragmentBinding6.historyList) != null) {
            recyclerView2.addItemDecoration(new GridSpaceItemDecoration(2, getResources().getDimensionPixelSize(R.dimen.kg_editor_history_item_space), true));
        }
        final HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
        if (mHistoryAdapter != null) {
            mHistoryAdapter.setCallBack(this);
            mHistoryAdapter.setHasStableIds(true);
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding7 = this.binding;
            if (kgEditHistoryFragmentBinding7 != null && (recyclerView = kgEditHistoryFragmentBinding7.historyList) != null) {
                recyclerView.setAdapter(mHistoryAdapter);
            }
            mHistoryAdapter.setOnItemClickListener(new Function2() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return HistoryFragment.initView$lambda$30$lambda$25(mHistoryAdapter, this, ((Integer) obj).intValue(), (View) obj2);
                }
            });
            mHistoryAdapter.setOnItemLongClickListener(new Function2() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return Boolean.valueOf(HistoryFragment.initView$lambda$30$lambda$26(this.f$0, ((Integer) obj).intValue(), (View) obj2));
                }
            });
            mHistoryAdapter.setOnItemChildClickListener(new Function2() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return HistoryFragment.initView$lambda$30$lambda$29(mHistoryAdapter, this, ((Integer) obj).intValue(), (View) obj2);
                }
            });
        }
        Context context5 = getContext();
        if (context5 != null) {
            this.mColumnWidth = DeviceUtil.INSTANCE.currentDisplaySize(context5).width();
        }
    }

    public static final Unit initView$lambda$14(HistoryFragment historyFragment, Ref.BooleanRef booleanRef, View it) {
        Resources resources;
        Resources resources2;
        LiveData deleteMode;
        Intrinsics.checkNotNullParameter(it, "it");
        EditHistoryViewModel editHistoryViewModel = historyFragment.vm;
        if ((editHistoryViewModel == null || (deleteMode = editHistoryViewModel.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE)) {
            AppCompatImageView appCompatImageView = historyFragment.mStartView;
            if (appCompatImageView != null) {
                appCompatImageView.setImageResource(booleanRef.element ? R.drawable.kg_icon_history_back_dark : R.drawable.kg_icon_history_back_light);
            }
            AppCompatImageView appCompatImageView2 = historyFragment.mEndView;
            if (appCompatImageView2 != null) {
                appCompatImageView2.setImageResource(booleanRef.element ? R.drawable.kg_icon_history_add_dark : R.drawable.kg_icon_history_add_light);
            }
            historyFragment.exitDeleteMode();
            AppCompatImageView appCompatImageView3 = historyFragment.mStartView;
            String string = null;
            if (appCompatImageView3 != null) {
                Context context = historyFragment.getContext();
                appCompatImageView3.setContentDescription((context == null || (resources2 = context.getResources()) == null) ? null : resources2.getString(R.string.kg_talkback_back));
            }
            AppCompatImageView appCompatImageView4 = historyFragment.mEndView;
            if (appCompatImageView4 != null) {
                Context context2 = historyFragment.getContext();
                if (context2 != null && (resources = context2.getResources()) != null) {
                    string = resources.getString(R.string.kg_settings_template_new_match);
                }
                appCompatImageView4.setContentDescription(string);
            }
        } else {
            FragmentActivity activity = historyFragment.getActivity();
            if (activity != null) {
                activity.finish();
            }
        }
        return Unit.INSTANCE;
    }

    public static final Unit initView$lambda$18(HistoryFragment historyFragment, View it) {
        ComponentName componentName;
        Intent intent;
        LiveData selectAll;
        LiveData deleteMode;
        Intrinsics.checkNotNullParameter(it, "it");
        EditHistoryViewModel editHistoryViewModel = historyFragment.vm;
        if ((editHistoryViewModel == null || (deleteMode = editHistoryViewModel.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE)) {
            List currentList = historyFragment.getMHistoryAdapter().getCurrentList();
            Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
            ArrayList arrayList = new ArrayList();
            int size = currentList.size();
            for (int i = 0; i < size; i++) {
                HistoryItemBean historyItemBean = (HistoryItemBean) currentList.get(i);
                if (historyItemBean.getStatus() == 0) {
                    TemplateItemBean templateItemBean = historyItemBean.getTemplateItemBean();
                    if (templateItemBean != null && templateItemBean.getItemType() == 2) {
                        EditHistoryViewModel editHistoryViewModel2 = historyFragment.vm;
                        if ((editHistoryViewModel2 == null || (selectAll = editHistoryViewModel2.getSelectAll()) == null) ? false : Intrinsics.areEqual(selectAll.getValue(), Boolean.FALSE)) {
                            Intrinsics.checkNotNull(historyItemBean);
                            arrayList.add(historyItemBean);
                            historyItemBean.setSelected(true);
                        } else {
                            historyItemBean.setSelected(false);
                        }
                        HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
                        if (mHistoryAdapter != null) {
                            mHistoryAdapter.notifyItemChanged(i, "");
                        }
                    }
                }
            }
            historyFragment.updateTitle(arrayList);
        } else {
            Context context = historyFragment.getContext();
            if (context != null) {
                SettingsTrackHelper.trackUsedTemplate$default(SettingsTrackHelper.INSTANCE, context, "我的搭配页", "新建搭配", null, 8, null);
            }
            WallpaperEditor.WallpaperPickerLauncher wallpaperPickerLauncher = historyFragment.wallpaperPickerLauncher;
            if (wallpaperPickerLauncher != null) {
                wallpaperPickerLauncher.launch();
            } else {
                FragmentActivity activity = historyFragment.getActivity();
                if (activity != null && (intent = activity.getIntent()) != null) {
                    intent.putExtra("openSource", 4);
                }
                DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                boolean zIsInternationalBuild = deviceUtil.isInternationalBuild();
                if (!zIsInternationalBuild && deviceUtil.isPad()) {
                    componentName = new ComponentName("com.android.thememanager", "com.android.thememanager.settings.ThemeAndWallpaperSettingDialogActivity");
                } else if (zIsInternationalBuild || !deviceUtil.isFold()) {
                    componentName = zIsInternationalBuild ? new ComponentName("com.android.thememanager", "com.android.thememanager.settings.WallpaperSettingsPickerActivity") : null;
                } else {
                    componentName = new ComponentName("com.android.thememanager", "com.android.thememanager.settings.ThemeAndWallpaperPickerSettingActivity");
                }
                WallpaperEditor.Companion.launchThemePicker(historyFragment, "", true, false, componentName);
            }
        }
        return Unit.INSTANCE;
    }

    public static final Unit initView$lambda$20(HistoryFragment historyFragment, View it) {
        Button button;
        Intrinsics.checkNotNullParameter(it, "it");
        Intent intent = new Intent(historyFragment.getActivity(), (Class<?>) EditorActivityForSettings.class);
        FragmentActivity activity = historyFragment.getActivity();
        if (activity != null) {
            activity.startActivity(intent);
        }
        Context context = historyFragment.getContext();
        if (context != null) {
            SettingsTrackHelper settingsTrackHelper = SettingsTrackHelper.INSTANCE;
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = historyFragment.binding;
            SettingsTrackHelper.trackUsedTemplate$default(settingsTrackHelper, context, "我的搭配页", String.valueOf((kgEditHistoryFragmentBinding == null || (button = kgEditHistoryFragmentBinding.historyMore) == null) ? null : button.getText()), null, 8, null);
        }
        return Unit.INSTANCE;
    }

    public static final Unit initView$lambda$21(HistoryFragment historyFragment, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        historyFragment.showDeleteDialog();
        return Unit.INSTANCE;
    }

    public static final Unit initView$lambda$30$lambda$25(HistoryAdapter historyAdapter, HistoryFragment historyFragment, int i, View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        List currentList = historyAdapter.getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        historyFragment.updateItemSelected(i, (HistoryItemBean) CollectionsKt.getOrNull(currentList, i));
        return Unit.INSTANCE;
    }

    public static final boolean initView$lambda$30$lambda$26(HistoryFragment historyFragment, int i, View view) {
        LiveData nightMode;
        LiveData nightMode2;
        Resources resources;
        Resources resources2;
        LiveData deleteMode;
        Intrinsics.checkNotNullParameter(view, "view");
        EditHistoryViewModel editHistoryViewModel = historyFragment.vm;
        if (!((editHistoryViewModel == null || (deleteMode = editHistoryViewModel.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE))) {
            EditHistoryViewModel editHistoryViewModel2 = historyFragment.vm;
            if (editHistoryViewModel2 != null) {
                editHistoryViewModel2.updateDeleteMode(true);
            }
            AppCompatImageView appCompatImageView = historyFragment.mStartView;
            if (appCompatImageView != null) {
                Context context = historyFragment.getContext();
                appCompatImageView.setContentDescription((context == null || (resources2 = context.getResources()) == null) ? null : resources2.getString(R.string.kg_dialog_apply_super_wallpaper_nagetive_button));
            }
            AppCompatImageView appCompatImageView2 = historyFragment.mEndView;
            if (appCompatImageView2 != null) {
                Context context2 = historyFragment.getContext();
                appCompatImageView2.setContentDescription((context2 == null || (resources = context2.getResources()) == null) ? null : resources.getString(R.string.kg_talkback_select_all));
            }
            AppCompatImageView appCompatImageView3 = historyFragment.mStartView;
            if (appCompatImageView3 != null) {
                EditHistoryViewModel editHistoryViewModel3 = historyFragment.vm;
                appCompatImageView3.setImageResource((editHistoryViewModel3 == null || (nightMode2 = editHistoryViewModel3.getNightMode()) == null) ? false : Intrinsics.areEqual(nightMode2.getValue(), Boolean.TRUE) ? R.drawable.kg_icon_history_cancel_dark : R.drawable.kg_icon_history_cancel_light);
            }
            AppCompatImageView appCompatImageView4 = historyFragment.mEndView;
            if (appCompatImageView4 != null) {
                EditHistoryViewModel editHistoryViewModel4 = historyFragment.vm;
                appCompatImageView4.setImageResource((editHistoryViewModel4 == null || (nightMode = editHistoryViewModel4.getNightMode()) == null) ? false : Intrinsics.areEqual(nightMode.getValue(), Boolean.TRUE) ? R.drawable.kg_icon_history_select_all_dark : R.drawable.kg_icon_history_select_all_light);
            }
            List currentList = historyFragment.getMHistoryAdapter().getCurrentList();
            Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
            int size = currentList.size();
            int i2 = 0;
            while (i2 < size) {
                HistoryItemBean historyItemBean = (HistoryItemBean) currentList.get(i2);
                if (historyItemBean != null && historyItemBean.getStatus() == 0) {
                    historyItemBean.setSelected(i2 == i);
                    HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
                    if (mHistoryAdapter != null) {
                        mHistoryAdapter.notifyItemChanged(i2, "");
                    }
                }
                i2++;
            }
            updateTitle$default(historyFragment, null, 1, null);
        }
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x014d  */
    /* JADX WARN: Code duplicated, block: B:108:0x015e  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static final Unit initView$lambda$30$lambda$29(HistoryAdapter historyAdapter, HistoryFragment historyFragment, int i, View view) throws Throwable {
        LiveData deleteMode;
        TemplateItemBean templateItemBean;
        CommonConfig current;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateItemBean templateItemBean2;
        CommonConfig current2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        LiveData deleteMode2;
        TemplateItemBean templateItemBean3;
        CommonConfig current3;
        HomeConfig homeInfo2;
        WallpaperInfo wallpaperInfo3;
        TemplateItemBean templateItemBean4;
        CommonConfig current4;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo4;
        TemplateItemBean templateItemBean5;
        EditorActivityViewModel viewModel;
        View root;
        LiveData deleteMode3;
        LiveData deleteMode4;
        LiveData deleteMode5;
        TemplateItemBean templateItemBean6;
        Intrinsics.checkNotNullParameter(view, "view");
        List currentList = historyAdapter.getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        HistoryItemBean historyItemBean = (HistoryItemBean) CollectionsKt.getOrNull(currentList, i);
        historyFragment.currentItemData = historyItemBean;
        CommonConfig selectedItemConfig = null;
        Integer numValueOf = (historyItemBean == null || (templateItemBean6 = historyItemBean.getTemplateItemBean()) == null) ? null : Integer.valueOf(templateItemBean6.getItemType());
        int id = view.getId();
        boolean zAreEqual = false;
        if (id == R.id.iv_lockscreen) {
            EditHistoryViewModel editHistoryViewModel = historyFragment.vm;
            if (editHistoryViewModel != null && (deleteMode5 = editHistoryViewModel.getDeleteMode()) != null) {
                zAreEqual = Intrinsics.areEqual(deleteMode5.getValue(), Boolean.TRUE);
            }
            if (zAreEqual || numValueOf == null || numValueOf.intValue() != 2) {
                historyFragment.updateItemSelected(i, historyItemBean);
            } else {
                historyFragment.jumpToTemplateEdit("lock", historyItemBean != null ? historyItemBean.getTemplateItemBean() : null);
            }
        } else if (id == R.id.iv_desk) {
            EditHistoryViewModel editHistoryViewModel2 = historyFragment.vm;
            if (editHistoryViewModel2 != null && (deleteMode4 = editHistoryViewModel2.getDeleteMode()) != null) {
                zAreEqual = Intrinsics.areEqual(deleteMode4.getValue(), Boolean.TRUE);
            }
            if (zAreEqual || numValueOf == null || numValueOf.intValue() != 2) {
                historyFragment.updateItemSelected(i, historyItemBean);
            } else {
                historyFragment.jumpToTemplateEdit("desktop", historyItemBean != null ? historyItemBean.getTemplateItemBean() : null);
            }
        } else if (id == R.id.tv_template_state) {
            if ((historyItemBean != null && historyItemBean.getStatus() == 0) == true) {
                EditHistoryViewModel editHistoryViewModel3 = historyFragment.vm;
                if (((editHistoryViewModel3 == null || (deleteMode3 = editHistoryViewModel3.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode3.getValue(), Boolean.TRUE)) || historyFragment.isApplyLoading) {
                    if (historyItemBean != null) {
                        zAreEqual = true;
                    }
                    if (zAreEqual) {
                        historyFragment.updateItemSelected(i, historyItemBean);
                    }
                } else {
                    historyFragment.mClickPosition = i;
                    KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = historyFragment.binding;
                    if (kgEditHistoryFragmentBinding != null && (root = kgEditHistoryFragmentBinding.getRoot()) != null) {
                        root.postDelayed(historyFragment.mApplyRunnable, 20000L);
                    }
                    historyFragment.isApplyLoading = true;
                    historyItemBean.setStatus(1);
                    HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
                    if (mHistoryAdapter != null) {
                        mHistoryAdapter.notifyItemChanged(i, "");
                    }
                    if (numValueOf != null && numValueOf.intValue() == 2) {
                        TemplateApplyController templateApplyController = historyFragment.mTemplateApplyController;
                        if (templateApplyController != null) {
                            HistoryItemBean historyItemBean2 = historyFragment.currentItemData;
                            selectedItemConfig = templateApplyController.getSelectedItemConfig(false, historyItemBean2 != null ? historyItemBean2.getTemplateItemBean() : null);
                        }
                        FragmentActivity activity = historyFragment.getActivity();
                        if (activity != null && (viewModel = EditorActivityViewModel.Companion.getViewModel(activity)) != null) {
                            viewModel.setApplyTemplateConfig(selectedItemConfig);
                        }
                        if (DeviceUtil.INSTANCE.isInternationalBuild()) {
                            FashionGalleryCompat fashionGalleryCompat = historyFragment.fashionGalleryCompat;
                            if (fashionGalleryCompat != null && fashionGalleryCompat.shouldContinue()) {
                                zAreEqual = true;
                            }
                            if (!zAreEqual) {
                                historyFragment.onApply();
                            }
                        } else {
                            historyFragment.onApply();
                        }
                    } else if (numValueOf != null && numValueOf.intValue() == 1 && historyItemBean.getTemplateItemBean() != null) {
                        historyFragment.onApply();
                    }
                    EditHistoryViewModel editHistoryViewModel4 = historyFragment.vm;
                    if (editHistoryViewModel4 != null) {
                        editHistoryViewModel4.updateCurrentTemplatePosition(i);
                    }
                }
            } else {
                if (historyItemBean != null && (templateItemBean5 = historyItemBean.getTemplateItemBean()) != null && templateItemBean5.getItemType() == 2) {
                    zAreEqual = true;
                }
                if (zAreEqual) {
                    historyFragment.updateItemSelected(i, historyItemBean);
                }
            }
        } else if (id == R.id.keyguard_container) {
            String resourceType = (historyItemBean == null || (templateItemBean4 = historyItemBean.getTemplateItemBean()) == null || (current4 = templateItemBean4.getCurrent()) == null || (lockscreenInfo2 = current4.getLockscreenInfo()) == null || (wallpaperInfo4 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo4.getResourceType();
            String resourceType2 = (historyItemBean == null || (templateItemBean3 = historyItemBean.getTemplateItemBean()) == null || (current3 = templateItemBean3.getCurrent()) == null || (homeInfo2 = current3.getHomeInfo()) == null || (wallpaperInfo3 = homeInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType();
            EditHistoryViewModel editHistoryViewModel5 = historyFragment.vm;
            if (editHistoryViewModel5 != null && (deleteMode2 = editHistoryViewModel5.getDeleteMode()) != null) {
                zAreEqual = Intrinsics.areEqual(deleteMode2.getValue(), Boolean.TRUE);
            }
            if (!zAreEqual && !Wallpaper.Companion.unSupportToEdit(resourceType, resourceType2)) {
                historyFragment.jumpToTemplateEdit("lock", historyItemBean != null ? historyItemBean.getTemplateItemBean() : null);
            }
        } else if (id == R.id.desk_click_view) {
            String resourceType3 = (historyItemBean == null || (templateItemBean2 = historyItemBean.getTemplateItemBean()) == null || (current2 = templateItemBean2.getCurrent()) == null || (lockscreenInfo = current2.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getResourceType();
            String resourceType4 = (historyItemBean == null || (templateItemBean = historyItemBean.getTemplateItemBean()) == null || (current = templateItemBean.getCurrent()) == null || (homeInfo = current.getHomeInfo()) == null || (wallpaperInfo = homeInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo.getResourceType();
            EditHistoryViewModel editHistoryViewModel6 = historyFragment.vm;
            if (editHistoryViewModel6 != null && (deleteMode = editHistoryViewModel6.getDeleteMode()) != null) {
                zAreEqual = Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE);
            }
            if (!zAreEqual && !Wallpaper.Companion.unSupportToEdit(resourceType3, resourceType4)) {
                historyFragment.jumpToTemplateEdit("desktop", historyItemBean != null ? historyItemBean.getTemplateItemBean() : null);
            }
        }
        return Unit.INSTANCE;
    }

    public static final void mApplyRunnable$lambda$1(HistoryFragment historyFragment) {
        Log.i("HistoryFragment", "ApplyRunnable：restore the status of the apply button");
        historyFragment.isApplyLoading = false;
        TemplateApplyController templateApplyController = historyFragment.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(false);
        }
        List currentList = historyFragment.getMHistoryAdapter().getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        int size = currentList.size();
        for (int i = 0; i < size; i++) {
            HistoryItemBean historyItemBean = (HistoryItemBean) currentList.get(i);
            if (historyItemBean.getStatus() == 1) {
                historyItemBean.setStatus(0);
                HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
                if (mHistoryAdapter != null) {
                    mHistoryAdapter.notifyItemChanged(i, "");
                }
            }
        }
    }

    public static final HistoryAdapter mHistoryAdapter_delegate$lambda$0(HistoryFragment historyFragment) {
        return new HistoryAdapter(historyFragment.vm);
    }

    public static final void mScreenSizeChangedRunnable$lambda$4(HistoryFragment historyFragment) {
        int iWidth;
        Context context = historyFragment.getContext();
        if (context == null || (iWidth = DeviceUtil.INSTANCE.currentDisplaySize(context).width()) == historyFragment.mColumnWidth) {
            return;
        }
        historyFragment.mColumnWidth = iWidth;
        HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
        if (mHistoryAdapter != null) {
            mHistoryAdapter.setForceUpdate(true);
            mHistoryAdapter.notifyItemRangeChanged(0, mHistoryAdapter.getCurrentList().size(), "");
        }
    }

    public static final Unit observe$lambda$6(HistoryFragment historyFragment, List list) {
        List items;
        TemplateItemBean templateItemBean;
        historyFragment.shouldCaptureCurrentTemplate = true;
        ColorPickerCache.INSTANCE.clearMyColorData();
        if (historyFragment.isLocalUpdate) {
            Intrinsics.checkNotNull(list);
            historyFragment.updateTemplateData(list);
        } else {
            ArrayList arrayList = new ArrayList();
            Intrinsics.checkNotNull(list);
            FloorItemBean floorItemBean = (FloorItemBean) CollectionsKt.getOrNull(list, 0);
            boolean z = (floorItemBean == null || (items = floorItemBean.getItems()) == null || (templateItemBean = (TemplateItemBean) CollectionsKt.getOrNull(items, 0)) == null || templateItemBean.getItemType() != 4) ? false : true;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                for (TemplateItemBean templateItemBean2 : ((FloorItemBean) it.next()).getItems()) {
                    CommonConfig current = templateItemBean2.getCurrent();
                    if (current != null) {
                        current.setUpdated(false);
                    }
                    int i = 2;
                    if (templateItemBean2.getItemType() != 4 && (z || templateItemBean2.getItemType() != 1)) {
                        i = 0;
                    }
                    arrayList.add(new HistoryItemBean(i, templateItemBean2, false));
                }
            }
            HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
            if (mHistoryAdapter != null) {
                mHistoryAdapter.submitList(arrayList);
            }
        }
        return Unit.INSTANCE;
    }

    public static final Unit observe$lambda$7(HistoryFragment historyFragment, List list) {
        TemplateItemBean templateItemBean;
        HistoryAdapter mHistoryAdapter;
        if (list.size() == 0) {
            List currentList = historyFragment.getMHistoryAdapter().getCurrentList();
            Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
            int size = currentList.size();
            for (int i = 0; i < size; i++) {
                HistoryItemBean historyItemBean = (HistoryItemBean) CollectionsKt.getOrNull(currentList, i);
                if (historyItemBean != null && historyItemBean.getStatus() == 0 && historyItemBean != null && (templateItemBean = historyItemBean.getTemplateItemBean()) != null && templateItemBean.getItemType() == 2 && (mHistoryAdapter = historyFragment.getMHistoryAdapter()) != null) {
                    mHistoryAdapter.notifyItemChanged(i, "");
                }
            }
        }
        return Unit.INSTANCE;
    }

    public static final Unit observe$lambda$8(HistoryFragment historyFragment, Boolean bool) {
        int i;
        Resources resources;
        String string;
        Resources resources2;
        String string2;
        Resources resources3;
        String string3;
        Resources resources4;
        String string4;
        Resources resources5;
        String string5;
        LiveData deleteMode;
        LiveData nightMode;
        EditHistoryViewModel editHistoryViewModel = historyFragment.vm;
        boolean zAreEqual = false;
        boolean zAreEqual2 = (editHistoryViewModel == null || (nightMode = editHistoryViewModel.getNightMode()) == null) ? false : Intrinsics.areEqual(nightMode.getValue(), Boolean.TRUE);
        EditHistoryViewModel editHistoryViewModel2 = historyFragment.vm;
        if (editHistoryViewModel2 != null && (deleteMode = editHistoryViewModel2.getDeleteMode()) != null) {
            zAreEqual = Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE);
        }
        String str = "";
        if (zAreEqual && Intrinsics.areEqual(bool, Boolean.TRUE)) {
            Context context = historyFragment.getContext();
            if (context != null && (resources5 = context.getResources()) != null && (string5 = resources5.getString(R.string.kg_talkback_unselect_all)) != null) {
                str = string5;
            }
            i = R.drawable.kg_icon_history_select_all_blue;
        } else if (zAreEqual2 && zAreEqual) {
            Context context2 = historyFragment.getContext();
            if (context2 != null && (resources4 = context2.getResources()) != null && (string4 = resources4.getString(R.string.kg_talkback_select_all)) != null) {
                str = string4;
            }
            i = R.drawable.kg_icon_history_select_all_dark;
        } else if (!zAreEqual2 && zAreEqual) {
            Context context3 = historyFragment.getContext();
            if (context3 != null && (resources3 = context3.getResources()) != null && (string3 = resources3.getString(R.string.kg_talkback_select_all)) != null) {
                str = string3;
            }
            i = R.drawable.kg_icon_history_select_all_light;
        } else if (!zAreEqual2 || zAreEqual) {
            Context context4 = historyFragment.getContext();
            if (context4 != null && (resources = context4.getResources()) != null && (string = resources.getString(R.string.kg_settings_template_new_match)) != null) {
                str = string;
            }
            i = R.drawable.kg_icon_history_add_light;
        } else {
            Context context5 = historyFragment.getContext();
            if (context5 != null && (resources2 = context5.getResources()) != null && (string2 = resources2.getString(R.string.kg_settings_template_new_match)) != null) {
                str = string2;
            }
            i = R.drawable.kg_icon_history_add_dark;
        }
        AppCompatImageView appCompatImageView = historyFragment.mEndView;
        if (appCompatImageView != null) {
            appCompatImageView.setImageResource(i);
        }
        AppCompatImageView appCompatImageView2 = historyFragment.mEndView;
        if (appCompatImageView2 != null) {
            appCompatImageView2.setContentDescription(str);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onCurrentTemplateScreenshot$lambda$53$lambda$51(HistoryFragment historyFragment) {
        Log.w("HistoryFragment", "takeCaptureForMultiScreenMode success: onCurrentTemplateScreenshotsSaveComplete");
        TemplateApplyController templateApplyController = historyFragment.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(false);
        }
        TemplateApplyController templateApplyController2 = historyFragment.mTemplateApplyController;
        if (templateApplyController2 != null) {
            templateApplyController2.onCurrentTemplateScreenshotsSaveComplete(true);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onCurrentTemplateScreenshot$lambda$53$lambda$52(HistoryFragment historyFragment, Throwable it) {
        Intrinsics.checkNotNullParameter(it, "it");
        TemplateApplyController templateApplyController = historyFragment.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(false);
        }
        return Unit.INSTANCE;
    }

    public static final void onResume$lambda$43$lambda$42(Context context) {
        FashionGalleryHelper.INSTANCE.getGalleryStatus(context.getApplicationContext(), true);
    }

    public static final void showDeleteDialog$lambda$37(HistoryFragment historyFragment, DialogInterface dialogInterface, int i) {
        if (i == -1) {
            historyFragment.deleteTemplates();
        }
    }

    public static final void updateTemplateData$lambda$9(HistoryFragment historyFragment, List list) {
        HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
        if (mHistoryAdapter != null) {
            mHistoryAdapter.notifyItemRangeChanged(0, list.size(), "");
        }
    }

    public static /* synthetic */ void updateTitle$default(HistoryFragment historyFragment, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = new ArrayList();
        }
        historyFragment.updateTitle(list);
    }

    public static final void updateUIafterDelete$lambda$40(HistoryFragment historyFragment, List list) {
        HistoryAdapter mHistoryAdapter = historyFragment.getMHistoryAdapter();
        if (mHistoryAdapter != null) {
            mHistoryAdapter.notifyItemRangeChanged(0, list.size(), "");
        }
    }

    public final void deleteTemplates() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(activity), Dispatchers.getMain(), null, new HistoryFragment$deleteTemplates$1$1(this, null), 2, null);
        }
    }

    public final void exitDeleteMode() {
        LiveData deleteMode;
        LiveData nightMode;
        Boolean bool;
        EditHistoryViewModel editHistoryViewModel = this.vm;
        boolean zBooleanValue = (editHistoryViewModel == null || (nightMode = editHistoryViewModel.getNightMode()) == null || (bool = (Boolean) nightMode.getValue()) == null) ? false : bool.booleanValue();
        EditHistoryViewModel editHistoryViewModel2 = this.vm;
        if (editHistoryViewModel2 != null) {
            editHistoryViewModel2.updateSelectedList(new ArrayList());
        }
        EditHistoryViewModel editHistoryViewModel3 = this.vm;
        if (editHistoryViewModel3 != null) {
            editHistoryViewModel3.updateDeleteMode(false);
        }
        updateTitle$default(this, null, 1, null);
        AppCompatImageView appCompatImageView = this.mStartView;
        if (appCompatImageView != null) {
            appCompatImageView.setImageResource(zBooleanValue ? R.drawable.kg_icon_history_back_dark : R.drawable.kg_icon_history_back_light);
        }
        AppCompatImageView appCompatImageView2 = this.mEndView;
        if (appCompatImageView2 != null) {
            appCompatImageView2.setImageResource(zBooleanValue ? R.drawable.kg_icon_history_add_dark : R.drawable.kg_icon_history_add_light);
        }
        List currentList = getMHistoryAdapter().getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        int size = currentList.size();
        for (int i = 0; i < size; i++) {
            HistoryItemBean historyItemBean = (HistoryItemBean) currentList.get(i);
            if (historyItemBean.getStatus() == 0) {
                EditHistoryViewModel editHistoryViewModel4 = this.vm;
                if ((editHistoryViewModel4 == null || (deleteMode = editHistoryViewModel4.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE)) {
                    historyItemBean.setSelected(false);
                    HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
                    if (mHistoryAdapter != null) {
                        mHistoryAdapter.notifyItemChanged(i, "");
                    }
                }
            }
        }
    }

    public final HistoryAdapter getMHistoryAdapter() {
        return (HistoryAdapter) this.mHistoryAdapter$delegate.getValue();
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    public TemplateItemBean getSelectedItem() {
        List currentList;
        HistoryItemBean historyItemBean;
        HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
        if (mHistoryAdapter == null || (currentList = mHistoryAdapter.getCurrentList()) == null || (historyItemBean = (HistoryItemBean) currentList.get(this.mClickPosition)) == null) {
            return null;
        }
        return historyItemBean.getTemplateItemBean();
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    public androidx.recyclerview.widget.RecyclerView.ViewHolder getSelectedViewHolder() {
        return null;
    }

    public final void initControllers() {
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        TemplateApplyController templateApplyController = new TemplateApplyController(fragmentActivityRequireActivity, this);
        this.mTemplateApplyController = templateApplyController;
        templateApplyController.setApplyCallback(new ApplyCallback() { // from class: com.miui.keyguard.editor.history.HistoryFragment.initControllers.1
            @Override // com.miui.keyguard.editor.homepage.util.ApplyCallback
            public void onApplyCanceled() {
                View root;
                Log.d("HistoryFragment", "onApplyCanceled: history fragment");
                HistoryFragment.this.isApplyLoading = false;
                KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = HistoryFragment.this.binding;
                if (kgEditHistoryFragmentBinding != null && (root = kgEditHistoryFragmentBinding.getRoot()) != null) {
                    root.removeCallbacks(HistoryFragment.this.mApplyRunnable);
                }
                HistoryItemBean historyItemBean = HistoryFragment.this.currentItemData;
                if (historyItemBean != null) {
                    historyItemBean.setStatus(0);
                }
                HistoryFragment.this.getMHistoryAdapter().notifyItemChanged(HistoryFragment.this.mClickPosition, "");
            }

            @Override // com.miui.keyguard.editor.homepage.util.ApplyCallback
            public void onApplyComplete(boolean z, Bitmap bitmap) {
                EditHistoryViewModel editHistoryViewModel;
                Log.d("HistoryFragment", "onApplyComplete: history fragment");
                HistoryFragment.this.isLocalUpdate = true;
                Context context = HistoryFragment.this.getContext();
                if (context == null || (editHistoryViewModel = HistoryFragment.this.vm) == null) {
                    return;
                }
                editHistoryViewModel.loadHistoryData(context);
            }
        });
        TemplateApplyController templateApplyController2 = this.mTemplateApplyController;
        if (templateApplyController2 != null) {
            templateApplyController2.setApplyMyTemplateCallback(new Consumer() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda10
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    HistoryFragment.initControllers$lambda$10(this.f$0, (Pair) obj);
                }
            });
        }
    }

    public final void initFashionGalleryCompat() {
        if (DeviceUtil.INSTANCE.isInternationalBuild() && this.fashionGalleryCompat == null) {
            this.fashionGalleryCompat = new FashionGalleryCompat(this);
        }
    }

    public final void jumpToTemplateEdit(String str, TemplateItemBean templateItemBean) {
        String configUri;
        TemplateHistoryConfig history;
        TemplateHistoryConfig history2;
        Bundle bundle;
        PackageManager packageManager;
        try {
            FragmentActivity activity = getActivity();
            Long lValueOf = null;
            ApplicationInfo applicationInfo = (activity == null || (packageManager = activity.getPackageManager()) == null) ? null : packageManager.getApplicationInfo("com.miui.aod", 128);
            if ((((applicationInfo == null || (bundle = applicationInfo.metaData) == null) ? 0 : bundle.getInt("editor_os_version")) & 240) == 48) {
                Integer numValueOf = templateItemBean != null ? Integer.valueOf(templateItemBean.getItemType()) : null;
                if (numValueOf != null && numValueOf.intValue() == 1) {
                    configUri = "@MINE";
                } else {
                    configUri = (templateItemBean == null || (history2 = templateItemBean.getHistory()) == null) ? null : history2.getConfigUri();
                }
                if (numValueOf != null && numValueOf.intValue() == 1) {
                    lValueOf = -1L;
                } else if (templateItemBean != null && (history = templateItemBean.getHistory()) != null) {
                    lValueOf = Long.valueOf(history.getId());
                }
                Context context = getContext();
                if (context != null) {
                    Intent intent = new Intent(context, (Class<?>) CommonEditorActivity.class);
                    intent.setAction("miui.keyguard.editor.common");
                    intent.putExtra("caller", str);
                    intent.putExtra("argConfigPath", configUri);
                    intent.putExtra("argTemplateSource", lValueOf);
                    intent.putExtra("whereFrom", "my_match");
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            Log.e("HistoryFragment", "jumpToTemplateEdit:", e);
        }
    }

    public final void observe() {
        LiveData selectAll;
        LiveData selectedListLiveData;
        LiveData historyListLiveData;
        EditHistoryViewModel editHistoryViewModel = this.vm;
        if (editHistoryViewModel != null && (historyListLiveData = editHistoryViewModel.getHistoryListLiveData()) != null) {
            historyListLiveData.observe(getViewLifecycleOwner(), new HistoryFragmentKt$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda12
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryFragment.observe$lambda$6(this.f$0, (List) obj);
                }
            }));
        }
        EditHistoryViewModel editHistoryViewModel2 = this.vm;
        if (editHistoryViewModel2 != null && (selectedListLiveData = editHistoryViewModel2.getSelectedListLiveData()) != null) {
            selectedListLiveData.observe(getViewLifecycleOwner(), new HistoryFragmentKt$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryFragment.observe$lambda$7(this.f$0, (List) obj);
                }
            }));
        }
        EditHistoryViewModel editHistoryViewModel3 = this.vm;
        if (editHistoryViewModel3 == null || (selectAll = editHistoryViewModel3.getSelectAll()) == null) {
            return;
        }
        selectAll.observe(getViewLifecycleOwner(), new HistoryFragmentKt$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HistoryFragment.observe$lambda$8(this.f$0, (Boolean) obj);
            }
        }));
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 117) {
            onApply();
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onAddTemplate() {
        CrossViewPagerCallback.DefaultImpls.onAddTemplate(this);
    }

    public final void onApply() {
        TemplateItemBean templateItemBean;
        TemplateHistoryConfig history;
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.applyTemplate(true);
        }
        if (getContext() != null) {
            SettingsTrackHelper settingsTrackHelper = SettingsTrackHelper.INSTANCE;
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            String string = getResources().getString(R.string.kg_apply);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            HistoryItemBean historyItemBean = this.currentItemData;
            settingsTrackHelper.trackUsedTemplate(contextRequireContext, "我的搭配页", string, (historyItemBean == null || (templateItemBean = historyItemBean.getTemplateItemBean()) == null || (history = templateItemBean.getHistory()) == null) ? null : history.getTemplateId());
        }
    }

    public final void onApplyMyTemplateCompleted(TemplateItemBean templateItemBean, boolean z) {
        EditHistoryViewModel editHistoryViewModel;
        LiveData currentTemplatePosition;
        Integer num;
        this.isLocalUpdate = true;
        EditHistoryViewModel editHistoryViewModel2 = this.vm;
        int iIntValue = (editHistoryViewModel2 == null || (currentTemplatePosition = editHistoryViewModel2.getCurrentTemplatePosition()) == null || (num = (Integer) currentTemplatePosition.getValue()) == null) ? 0 : num.intValue();
        List currentList = getMHistoryAdapter().getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        HistoryItemBean historyItemBean = (HistoryItemBean) CollectionsKt.getOrNull(currentList, iIntValue);
        if (historyItemBean != null) {
            historyItemBean.setStatus(2);
        }
        getMHistoryAdapter().notifyItemRangeChanged(iIntValue, 1, "");
        Context context = getContext();
        if (context == null || (editHistoryViewModel = this.vm) == null) {
            return;
        }
        editHistoryViewModel.loadHistoryData(context);
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this.binding = (KgEditHistoryFragmentBinding) DataBindingUtil.inflate(inflater, R.layout.kg_edit_history_fragment, viewGroup, false);
        Context context = getContext();
        EditHistoryViewModel viewModel = context != null ? EditHistoryViewModel.Companion.getViewModel(context) : null;
        this.vm = viewModel;
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = this.binding;
        if (kgEditHistoryFragmentBinding != null) {
            kgEditHistoryFragmentBinding.setVm(viewModel);
        }
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding2 = this.binding;
        if (kgEditHistoryFragmentBinding2 != null) {
            kgEditHistoryFragmentBinding2.setLifecycleOwner(getViewLifecycleOwner());
        }
        observe();
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding3 = this.binding;
        this.historyView = kgEditHistoryFragmentBinding3 != null ? kgEditHistoryFragmentBinding3.historyRoot : null;
        if (kgEditHistoryFragmentBinding3 != null) {
            return kgEditHistoryFragmentBinding3.getRoot();
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onCurrentTemplateScreenshot(BaseTemplateView lockTemplateView, BaseTemplateView baseTemplateView, CommonConfig templateConfig) {
        Intrinsics.checkNotNullParameter(lockTemplateView, "lockTemplateView");
        Intrinsics.checkNotNullParameter(templateConfig, "templateConfig");
        Log.i("HistoryFragment", "onCurrentTemplateScreenshot -> shouldCaptureCurrentTemplate = " + this.shouldCaptureCurrentTemplate);
        this.myTemplateView = new WeakReference(lockTemplateView);
        if (this.shouldCaptureCurrentTemplate) {
            TemplateApplyController templateApplyController = this.mTemplateApplyController;
            if (templateApplyController != null) {
                templateApplyController.setCurrentTemplateScreenshotsSaving(true);
            }
            this.shouldCaptureCurrentTemplate = false;
            if (!checkFragmentInactive()) {
                Context context = getContext();
                if (context != null) {
                    DeviceScreenshotHelper.INSTANCE.screenshot(context, this.historyView, lockTemplateView, baseTemplateView, templateConfig, "wallpaper_for_screenshot_history", new Function0() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda18
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return HistoryFragment.onCurrentTemplateScreenshot$lambda$53$lambda$51(this.f$0);
                        }
                    }, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda19
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return HistoryFragment.onCurrentTemplateScreenshot$lambda$53$lambda$52(this.f$0, (Throwable) obj);
                        }
                    });
                    return;
                }
                return;
            }
            Log.w("HistoryFragment", "takeCaptureForMultiScreenMode failed: fragment not attached to a context.");
            TemplateApplyController templateApplyController2 = this.mTemplateApplyController;
            if (templateApplyController2 != null) {
                templateApplyController2.setCurrentTemplateScreenshotsSaving(false);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        View root;
        View root2;
        super.onDestroy();
        this.myTemplateView = null;
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = this.binding;
        if (kgEditHistoryFragmentBinding != null && (root2 = kgEditHistoryFragmentBinding.getRoot()) != null) {
            root2.removeCallbacks(this.mApplyRunnable);
        }
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding2 = this.binding;
        if (kgEditHistoryFragmentBinding2 != null && (root = kgEditHistoryFragmentBinding2.getRoot()) != null) {
            root.removeCallbacks(this.mScreenSizeChangedRunnable);
        }
        this.mTemplateApplyController = null;
        DeviceScreenshotHelper.INSTANCE.setFirstScreenshotFinished(false);
        BaseItemViewFactoryImpl.Companion.clearCache();
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHistoryTemplateReady() {
        CrossViewPagerCallback.DefaultImpls.onHistoryTemplateReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onHomeWallpaperDarkResultReady() {
        CrossViewPagerCallback.DefaultImpls.onHomeWallpaperDarkResultReady(this);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyHomeTemplateCreated(BaseTemplateView baseTemplateView) {
        CrossViewPagerCallback.DefaultImpls.onMyHomeTemplateCreated(this, baseTemplateView);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onMyLockTemplateCreated(BaseTemplateView baseTemplateView, CommonConfig commonConfig) {
        CrossViewPagerCallback.DefaultImpls.onMyLockTemplateCreated(this, baseTemplateView, commonConfig);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageDeleteCompleted(int i, int i2, TemplateItemBean templateItemBean) {
        CrossViewPagerCallback.DefaultImpls.onPageDeleteCompleted(this, i, i2, templateItemBean);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public boolean onPageDeleted(int i, int i2, TemplateItemBean templateItemBean) {
        return CrossViewPagerCallback.DefaultImpls.onPageDeleted(this, i, i2, templateItemBean);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageEnterDeleteMode(int i, int i2, View view, boolean z) {
        CrossViewPagerCallback.DefaultImpls.onPageEnterDeleteMode(this, i, i2, view, z);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageScrollStateChanged(int i, int i2, int i3, int i4) {
        CrossViewPagerCallback.DefaultImpls.onPageScrollStateChanged(this, i, i2, i3, i4);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageSelected(int i, int i2, int i3, int i4) {
        CrossViewPagerCallback.DefaultImpls.onPageSelected(this, i, i2, i3, i4);
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onPageTransformed(int i, BaseViewHolder baseViewHolder, View view, float f, float f2, int i2) {
        CrossViewPagerCallback.DefaultImpls.onPageTransformed(this, i, baseViewHolder, view, f, f2, i2);
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Context context;
        EditHistoryViewModel editHistoryViewModel;
        LiveData deleteMode;
        Boolean bool;
        super.onResume();
        boolean zBooleanValue = false;
        this.isLocalUpdate = false;
        EditHistoryViewModel editHistoryViewModel2 = this.vm;
        if (editHistoryViewModel2 != null && (deleteMode = editHistoryViewModel2.getDeleteMode()) != null && (bool = (Boolean) deleteMode.getValue()) != null) {
            zBooleanValue = bool.booleanValue();
        }
        if (!zBooleanValue && !this.isResumeFromFashiongallery && (context = getContext()) != null && (editHistoryViewModel = this.vm) != null) {
            editHistoryViewModel.loadHistoryData(context);
        }
        final Context context2 = getContext();
        if (context2 != null) {
            Task.run(new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    HistoryFragment.onResume$lambda$43$lambda$42(context2);
                }
            });
        }
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment
    public void onScreenConfigurationChanged(boolean z, boolean z2) {
        Context context;
        View root;
        View root2;
        super.onScreenConfigurationChanged(z, z2);
        if (z2) {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if ((deviceUtil.isFold() || deviceUtil.isPad()) && (context = getContext()) != null) {
                int iWidth = deviceUtil.currentDisplaySize(context).width();
                if (iWidth == this.mColumnWidth) {
                    KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = this.binding;
                    if (kgEditHistoryFragmentBinding == null || (root = kgEditHistoryFragmentBinding.getRoot()) == null) {
                        return;
                    }
                    root.postDelayed(this.mScreenSizeChangedRunnable, 800L);
                    return;
                }
                KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding2 = this.binding;
                if (kgEditHistoryFragmentBinding2 != null && (root2 = kgEditHistoryFragmentBinding2.getRoot()) != null) {
                    root2.removeCallbacks(this.mScreenSizeChangedRunnable);
                }
                this.mColumnWidth = iWidth;
                HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
                if (mHistoryAdapter != null) {
                    mHistoryAdapter.setForceUpdate(true);
                    mHistoryAdapter.notifyItemRangeChanged(0, mHistoryAdapter.getCurrentList().size(), "");
                }
                updateMore();
            }
        }
    }

    @Override // com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback
    public void onTemplateCustomized(int i, int i2, TemplateItemBean templateItemBean, int i3) {
        CrossViewPagerCallback.DefaultImpls.onTemplateCustomized(this, i, i2, templateItemBean, i3);
    }

    @Override // com.miui.keyguard.editor.base.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        initControllers();
        initView();
        this.isResumeFromFashiongallery = false;
        initFashionGalleryCompat();
    }

    @Override // com.miui.keyguard.editor.homepage.util.SelectItemProvider
    public Pair requireScreenshotPreview() {
        return null;
    }

    public final void setBottomButtonBlur(boolean z) {
        Button button;
        Button button2;
        Button button3;
        Button button4;
        Button button5;
        Button button6;
        Button button7;
        Button button8;
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_history_more_shadow_elevation);
        if (!ViewStylerKt.canUseBlur()) {
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = this.binding;
            if (kgEditHistoryFragmentBinding != null && (button3 = kgEditHistoryFragmentBinding.historyDelete) != null) {
                button3.setBackgroundColor(z ? -16777216 : -1);
            }
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding2 = this.binding;
            if (kgEditHistoryFragmentBinding2 != null && (button2 = kgEditHistoryFragmentBinding2.historyMore) != null) {
                button2.setBackgroundResource(z ? R.drawable.kg_editor_history_more_bg_dark : R.drawable.kg_editor_history_more_bg_light);
            }
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding3 = this.binding;
            if (kgEditHistoryFragmentBinding3 == null || (button = kgEditHistoryFragmentBinding3.historyMore) == null) {
                return;
            }
            ViewCompat.setElevation(button, dimensionPixelSize);
            return;
        }
        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(this.Pured_Regular);
        MaterialConfig materialConfig = materialDayNightConfigCreate != null ? materialDayNightConfigCreate.get(!z) : null;
        if (materialConfig == null) {
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding4 = this.binding;
            if (kgEditHistoryFragmentBinding4 != null && (button6 = kgEditHistoryFragmentBinding4.historyDelete) != null) {
                button6.setBackgroundColor(z ? -16777216 : -1);
            }
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding5 = this.binding;
            if (kgEditHistoryFragmentBinding5 != null && (button5 = kgEditHistoryFragmentBinding5.historyMore) != null) {
                button5.setBackgroundResource(z ? R.drawable.kg_editor_history_more_bg_dark : R.drawable.kg_editor_history_more_bg_light);
            }
            KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding6 = this.binding;
            if (kgEditHistoryFragmentBinding6 == null || (button4 = kgEditHistoryFragmentBinding6.historyMore) == null) {
                return;
            }
            ViewCompat.setElevation(button4, dimensionPixelSize);
            return;
        }
        int color = getResources().getColor(R.color.kg_more_btn_shadow);
        float dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_history_more_shadow_offsety);
        float dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_history_more_shadow_radius);
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding7 = this.binding;
        if (kgEditHistoryFragmentBinding7 != null && (button8 = kgEditHistoryFragmentBinding7.historyDelete) != null) {
            MiuiBlurUtils.setBackgroundBlurMode(button8, 1);
            button8.setMaterial(materialConfig);
        }
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding8 = this.binding;
        if (kgEditHistoryFragmentBinding8 == null || (button7 = kgEditHistoryFragmentBinding8.historyMore) == null) {
            return;
        }
        MiuiBlurUtils.setBackgroundBlurMode(button7, 1);
        button7.setMaterial(materialConfig);
        MiShadowUtils.setShadowConfig(button7, new MaterialConfig.ShadowConfig(color, 0.0f, dimensionPixelSize2, dimensionPixelSize3, 1.0f));
    }

    public final void showDeleteDialog() {
        LiveData selectedListLiveData;
        List list;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HistoryFragment.showDeleteDialog$lambda$37(this.f$0, dialogInterface, i);
            }
        };
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            EditHistoryViewModel editHistoryViewModel = this.vm;
            templateApplyController.showHistoryDeleteDialog(onClickListener, onClickListener, true, (editHistoryViewModel == null || (selectedListLiveData = editHistoryViewModel.getSelectedListLiveData()) == null || (list = (List) selectedListLiveData.getValue()) == null) ? 1 : list.size());
        }
    }

    public final void updateItemSelected(int i, HistoryItemBean historyItemBean) {
        LiveData deleteMode;
        if (historyItemBean == null || historyItemBean.getStatus() != 0) {
            return;
        }
        EditHistoryViewModel editHistoryViewModel = this.vm;
        if ((editHistoryViewModel == null || (deleteMode = editHistoryViewModel.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE)) {
            if (historyItemBean != null && historyItemBean.getSelected()) {
                if (historyItemBean != null) {
                    historyItemBean.setSelected(false);
                }
            } else if (historyItemBean != null) {
                historyItemBean.setSelected(true);
            }
            HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
            if (mHistoryAdapter != null) {
                mHistoryAdapter.notifyItemChanged(i, "");
            }
            updateTitle$default(this, null, 1, null);
        }
    }

    public final void updateMore() {
        Context context = getContext();
        boolean z = context != null ? !DeviceUtil.INSTANCE.isFullScreenGestureNav(context) : false;
        Context context2 = getContext();
        int iWidth = context2 != null ? DeviceUtil.INSTANCE.currentDisplaySize(context2).width() : 0;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_editor_history_more_width);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_editor_history_more_height);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_editor_history_more_margin_left);
        int dimensionPixelSize4 = getResources().getDimensionPixelSize(z ? R.dimen.kg_editor_history_more_margin_bottom_has_bottom_nav : R.dimen.kg_editor_history_more_margin_bottom);
        ConstraintSet constraintSet = new ConstraintSet();
        if (iWidth >= (dimensionPixelSize3 * 2) + dimensionPixelSize) {
            constraintSet.constrainWidth(R.id.history_more, dimensionPixelSize);
        } else {
            int i = R.id.history_more;
            constraintSet.constrainWidth(i, 0);
            constraintSet.setMargin(i, 1, dimensionPixelSize3);
            constraintSet.setMargin(i, 2, dimensionPixelSize3);
        }
        int i2 = R.id.history_more;
        constraintSet.constrainHeight(i2, -2);
        constraintSet.constrainMinHeight(i2, dimensionPixelSize2);
        constraintSet.connect(i2, 1, 0, 1);
        constraintSet.connect(i2, 2, 0, 2);
        constraintSet.connect(i2, 4, 0, 4);
        constraintSet.setMargin(i2, 4, dimensionPixelSize4);
        constraintSet.applyTo(this.historyView);
    }

    public final void updateTemplateData(List list) {
        TemplateItemBean templateItemBean;
        CommonConfig current;
        View root;
        TemplateItemBean templateItemBean2;
        CommonConfig current2;
        CommonConfig current3;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        CommonConfig current4;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        TemplateItemBean templateItemBean3;
        LiveData currentTemplatePosition;
        Integer num;
        TemplateApplyController templateApplyController = this.mTemplateApplyController;
        if (templateApplyController != null) {
            templateApplyController.setCurrentTemplateScreenshotsSaving(true);
        }
        List currentList = getMHistoryAdapter().getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        EditHistoryViewModel editHistoryViewModel = this.vm;
        int iIntValue = (editHistoryViewModel == null || (currentTemplatePosition = editHistoryViewModel.getCurrentTemplatePosition()) == null || (num = (Integer) currentTemplatePosition.getValue()) == null) ? 0 : num.intValue();
        int size = currentList.size();
        int i = 0;
        while (true) {
            if (i < size) {
                HistoryItemBean historyItemBean = (HistoryItemBean) CollectionsKt.getOrNull(currentList, i);
                if (historyItemBean != null && (templateItemBean3 = historyItemBean.getTemplateItemBean()) != null && templateItemBean3.getItemType() == 1) {
                    break;
                } else {
                    i++;
                }
            } else {
                i = 0;
                break;
            }
        }
        HistoryItemBean historyItemBean2 = (HistoryItemBean) CollectionsKt.getOrNull(currentList, 0);
        String resourceType = null;
        TemplateItemBean templateItemBean4 = historyItemBean2 != null ? historyItemBean2.getTemplateItemBean() : null;
        Integer numValueOf = templateItemBean4 != null ? Integer.valueOf(templateItemBean4.getItemType()) : null;
        String resourceType2 = (templateItemBean4 == null || (current4 = templateItemBean4.getCurrent()) == null || (lockscreenInfo = current4.getLockscreenInfo()) == null || (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getResourceType();
        if (templateItemBean4 != null && (current3 = templateItemBean4.getCurrent()) != null && (homeInfo = current3.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
            resourceType = wallpaperInfo.getResourceType();
        }
        if ((numValueOf != null && numValueOf.intValue() == 4) || Wallpaper.Companion.unSupportToEdit(resourceType2, resourceType)) {
            final ArrayList arrayList = new ArrayList();
            arrayList.addAll(currentList);
            arrayList.remove(0);
            int i2 = iIntValue - 1;
            int i3 = i - 1;
            EditHistoryViewModel editHistoryViewModel2 = this.vm;
            if (editHistoryViewModel2 != null) {
                editHistoryViewModel2.updateCurrentTemplatePosition(i2);
            }
            HistoryItemBean historyItemBean3 = (HistoryItemBean) CollectionsKt.getOrNull(arrayList, i3);
            if (historyItemBean3 != null) {
                historyItemBean3.setTemplateItemBean((TemplateItemBean) CollectionsKt.getOrNull(((FloorItemBean) list.get(0)).getItems(), 1));
            }
            if (historyItemBean3 != null) {
                historyItemBean3.setStatus(0);
            }
            if (historyItemBean3 != null) {
                historyItemBean3.setSelected(false);
            }
            HistoryItemBean historyItemBean4 = (HistoryItemBean) CollectionsKt.getOrNull(arrayList, i2);
            if (historyItemBean4 != null) {
                historyItemBean4.setTemplateItemBean((TemplateItemBean) CollectionsKt.getOrNull(((FloorItemBean) list.get(0)).getItems(), 0));
            }
            if (historyItemBean4 != null) {
                historyItemBean4.setSelected(false);
            }
            if (historyItemBean4 != null) {
                historyItemBean4.setStatus(2);
            }
            if (historyItemBean4 != null && (templateItemBean2 = historyItemBean4.getTemplateItemBean()) != null && (current2 = templateItemBean2.getCurrent()) != null) {
                current2.setUpdated(false);
            }
            getMHistoryAdapter().submitList(arrayList, new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    HistoryFragment.updateTemplateData$lambda$9(this.f$0, arrayList);
                }
            });
        } else {
            HistoryItemBean historyItemBean5 = (HistoryItemBean) CollectionsKt.getOrNull(currentList, i);
            if (historyItemBean5 != null) {
                historyItemBean5.setTemplateItemBean((TemplateItemBean) CollectionsKt.getOrNull(((FloorItemBean) list.get(0)).getItems(), 1));
            }
            if (historyItemBean5 != null) {
                historyItemBean5.setStatus(0);
            }
            if (historyItemBean5 != null) {
                historyItemBean5.setSelected(false);
            }
            HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
            if (mHistoryAdapter != null) {
                mHistoryAdapter.notifyItemChanged(i);
            }
            HistoryItemBean historyItemBean6 = (HistoryItemBean) CollectionsKt.getOrNull(currentList, iIntValue);
            if (historyItemBean6 != null) {
                historyItemBean6.setTemplateItemBean((TemplateItemBean) CollectionsKt.getOrNull(((FloorItemBean) list.get(0)).getItems(), 0));
            }
            if (historyItemBean6 != null && (templateItemBean = historyItemBean6.getTemplateItemBean()) != null && (current = templateItemBean.getCurrent()) != null) {
                current.setUpdated(false);
            }
            if (historyItemBean6 != null) {
                historyItemBean6.setSelected(false);
            }
            if (historyItemBean6 != null) {
                historyItemBean6.setStatus(2);
            }
            HistoryAdapter mHistoryAdapter2 = getMHistoryAdapter();
            if (mHistoryAdapter2 != null) {
                mHistoryAdapter2.notifyItemChanged(iIntValue);
            }
        }
        this.isApplyLoading = false;
        KgEditHistoryFragmentBinding kgEditHistoryFragmentBinding = this.binding;
        if (kgEditHistoryFragmentBinding == null || (root = kgEditHistoryFragmentBinding.getRoot()) == null) {
            return;
        }
        root.removeCallbacks(this.mApplyRunnable);
    }

    public final void updateTheme(boolean z) {
        int i;
        int i2;
        View actionBarView;
        View actionBarView2;
        Resources resources;
        ActionBar actionBar;
        LiveData selectAll;
        LiveData deleteMode;
        setBottomButtonBlur(z);
        EditHistoryViewModel editHistoryViewModel = this.vm;
        if (editHistoryViewModel != null) {
            editHistoryViewModel.updateNightMode(z);
        }
        EditHistoryViewModel editHistoryViewModel2 = this.vm;
        boolean zAreEqual = (editHistoryViewModel2 == null || (deleteMode = editHistoryViewModel2.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE);
        EditHistoryViewModel editHistoryViewModel3 = this.vm;
        boolean zAreEqual2 = (editHistoryViewModel3 == null || (selectAll = editHistoryViewModel3.getSelectAll()) == null) ? false : Intrinsics.areEqual(selectAll.getValue(), Boolean.TRUE);
        if (z && zAreEqual) {
            i = R.drawable.kg_icon_history_cancel_dark;
        } else if (z || !zAreEqual) {
            i = (!z || zAreEqual) ? R.drawable.kg_icon_history_back_light : R.drawable.kg_icon_history_back_dark;
        } else {
            i = R.drawable.kg_icon_history_cancel_light;
        }
        AppCompatImageView appCompatImageView = this.mStartView;
        if (appCompatImageView != null) {
            appCompatImageView.setImageResource(i);
        }
        if (zAreEqual && zAreEqual2) {
            i2 = R.drawable.kg_icon_history_select_all_blue;
        } else if (z && zAreEqual) {
            i2 = R.drawable.kg_icon_history_select_all_dark;
        } else if (z || !zAreEqual) {
            i2 = (!z || zAreEqual) ? R.drawable.kg_icon_history_add_light : R.drawable.kg_icon_history_add_dark;
        } else {
            i2 = R.drawable.kg_icon_history_select_all_light;
        }
        AppCompatImageView appCompatImageView2 = this.mEndView;
        if (appCompatImageView2 != null) {
            appCompatImageView2.setImageResource(i2);
        }
        Context context = getContext();
        if (context != null && (resources = context.getResources()) != null && (actionBar = this.mActionBar) != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(resources.getColor(z ? R.color.kg_black_100 : R.color.kg_editor_history_bg_color_light)));
        }
        ActionBar actionBar2 = this.mActionBar;
        ColorTransitionTextView colorTransitionTextView = null;
        ColorTransitionTextView colorTransitionTextView2 = (actionBar2 == null || (actionBarView2 = actionBar2.getActionBarView()) == null) ? null : (ColorTransitionTextView) actionBarView2.findViewById(R.id.action_bar_title_expand);
        ActionBar actionBar3 = this.mActionBar;
        if (actionBar3 != null && (actionBarView = actionBar3.getActionBarView()) != null) {
            colorTransitionTextView = (ColorTransitionTextView) actionBarView.findViewById(R.id.action_bar_title);
        }
        if (colorTransitionTextView2 != null) {
            colorTransitionTextView2.setTextColor(getResources().getColor(z ? R.color.kg_white_95 : R.color.kg_black_100));
        }
        if (colorTransitionTextView != null) {
            colorTransitionTextView.setTextColor(getResources().getColor(z ? R.color.kg_white_95 : R.color.kg_black_100));
        }
        List currentList = getMHistoryAdapter().getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
        if (mHistoryAdapter != null) {
            mHistoryAdapter.notifyItemRangeChanged(0, currentList.size(), "");
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x004f  */
    public final void updateTitle(List list) {
        CollapseTitle collapseTitle;
        ExpandTitle expandTitle;
        int size;
        boolean z;
        LiveData deleteMode;
        ArrayList arrayList = new ArrayList();
        String string = getResources().getString(R.string.kg_editor_history_select_option);
        EditHistoryViewModel editHistoryViewModel = this.vm;
        if ((editHistoryViewModel == null || (deleteMode = editHistoryViewModel.getDeleteMode()) == null) ? false : Intrinsics.areEqual(deleteMode.getValue(), Boolean.TRUE)) {
            List<HistoryItemBean> currentList = getMHistoryAdapter().getCurrentList();
            Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
            for (HistoryItemBean historyItemBean : currentList) {
                if (historyItemBean != null) {
                    z = historyItemBean.getSelected();
                }
                if (z) {
                    arrayList.add(historyItemBean);
                }
            }
            if (list.isEmpty()) {
                size = arrayList.size();
            } else {
                arrayList.clear();
                arrayList.addAll(list);
                size = list.size();
            }
            EditHistoryViewModel editHistoryViewModel2 = this.vm;
            if (editHistoryViewModel2 != null) {
                editHistoryViewModel2.updateSelectedList(arrayList);
            }
            if (size > 0) {
                string = getResources().getQuantityString(R.plurals.kg_history_count_selected, size, Integer.valueOf(size));
            }
        } else {
            string = getResources().getString(R.string.kg_settings_editor_history_mine);
        }
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null && (expandTitle = actionBar.getExpandTitle()) != null) {
            expandTitle.setTitle(string);
        }
        ActionBar actionBar2 = this.mActionBar;
        if (actionBar2 == null || (collapseTitle = actionBar2.getCollapseTitle()) == null) {
            return;
        }
        collapseTitle.setTitle(string);
    }

    public final void updateUIafterDelete() {
        Collection<?> arrayList;
        List currentList;
        LiveData selectedListLiveData;
        EditHistoryViewModel editHistoryViewModel = this.vm;
        if (editHistoryViewModel == null || (selectedListLiveData = editHistoryViewModel.getSelectedListLiveData()) == null || (arrayList = (List) selectedListLiveData.getValue()) == null) {
            arrayList = new ArrayList<>();
        }
        EditHistoryViewModel editHistoryViewModel2 = this.vm;
        if (editHistoryViewModel2 != null) {
            editHistoryViewModel2.updateHistoryCount();
        }
        final ArrayList arrayList2 = new ArrayList();
        HistoryAdapter mHistoryAdapter = getMHistoryAdapter();
        if (mHistoryAdapter != null && (currentList = mHistoryAdapter.getCurrentList()) != null) {
            arrayList2.addAll(currentList);
        }
        arrayList2.removeAll(arrayList);
        HistoryAdapter mHistoryAdapter2 = getMHistoryAdapter();
        if (mHistoryAdapter2 != null) {
            mHistoryAdapter2.submitList(arrayList2, new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryFragment$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    HistoryFragment.updateUIafterDelete$lambda$40(this.f$0, arrayList2);
                }
            });
        }
        exitDeleteMode();
    }
}
