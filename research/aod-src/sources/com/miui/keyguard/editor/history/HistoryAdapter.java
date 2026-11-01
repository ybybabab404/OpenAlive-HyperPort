package com.miui.keyguard.editor.history;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.base.TemplateViewFactory;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.databinding.KgLayoutHistoryTemplateItemBinding;
import com.miui.keyguard.editor.databinding.KgLayoutMyTemplateItemBinding;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.history.bean.HistoryItemBean;
import com.miui.keyguard.editor.history.common.DiffExtKt;
import com.miui.keyguard.editor.history.common.ViewExtKt;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.CrossViewPagerCallback;
import com.miui.keyguard.editor.settings.util.ContextUtilKt;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.ViewUtil;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.viewmodel.EditHistoryViewModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import miuix.internal.widget.RoundFrameLayout;

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
/* JADX INFO: compiled from: HistoryAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HistoryAdapter extends ListAdapter<HistoryItemBean, RecyclerView.ViewHolder> {
    public final String TAG;
    public boolean forceUpdate;
    public int itemChildRadius;
    public RoundOutlineProvider itemChildRoundoutline;
    public int itemDeskHeight;
    public int itemDeskWidth;
    public float itemRadius;
    public FrameLayout.LayoutParams layoutPramsForTemplate;
    public CrossViewPagerCallback mCallBack;
    public Function2 onItemChildClickListener;
    public Function2 onItemClickListener;
    public Function2 onItemLongClickListener;
    public float templateScaleX;
    public float templateScaleY;
    public final EditHistoryViewModel viewModel;

    /* JADX INFO: compiled from: HistoryAdapter.kt */
    @Metadata
    public static final class ListViewHolder extends RecyclerView.ViewHolder {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ListViewHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
        }
    }

    public HistoryAdapter(EditHistoryViewModel editHistoryViewModel) {
        super(DiffExtKt.getDefaultDiff());
        this.viewModel = editHistoryViewModel;
        this.TAG = "Keyguard-Editor-HistoryAdapter";
        this.templateScaleX = 1.0f;
        this.templateScaleY = 1.0f;
    }

    public static final Unit onBind$lambda$17(HistoryAdapter historyAdapter, int i, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Function2 function2 = historyAdapter.onItemClickListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i), it);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onBind$lambda$18(HistoryAdapter historyAdapter, int i, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Function2 function2 = historyAdapter.onItemChildClickListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i), it);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onBind$lambda$19(HistoryAdapter historyAdapter, int i, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Function2 function2 = historyAdapter.onItemChildClickListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i), it);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onBind$lambda$20(HistoryAdapter historyAdapter, int i, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Function2 function2 = historyAdapter.onItemChildClickListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i), it);
        }
        return Unit.INSTANCE;
    }

    public static final boolean onBind$lambda$21(HistoryAdapter historyAdapter, int i, View view) {
        Function2 function2 = historyAdapter.onItemLongClickListener;
        if (function2 != null) {
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(view);
            if (((Boolean) function2.invoke(numValueOf, view)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean onBind$lambda$22(HistoryAdapter historyAdapter, int i, View view) {
        Function2 function2 = historyAdapter.onItemLongClickListener;
        if (function2 != null) {
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(view);
            if (((Boolean) function2.invoke(numValueOf, view)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean onBind$lambda$23(HistoryAdapter historyAdapter, int i, View view) {
        Function2 function2 = historyAdapter.onItemLongClickListener;
        if (function2 != null) {
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(view);
            if (((Boolean) function2.invoke(numValueOf, view)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean onBind$lambda$24(HistoryAdapter historyAdapter, int i, View view) {
        Function2 function2 = historyAdapter.onItemLongClickListener;
        if (function2 != null) {
            Integer numValueOf = Integer.valueOf(i);
            Intrinsics.checkNotNull(view);
            if (((Boolean) function2.invoke(numValueOf, view)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public static final void onBind$lambda$6$lambda$5(final BaseTemplateView baseTemplateView, final HistoryAdapter historyAdapter, final Context context, final BaseTemplateView baseTemplateView2, final HistoryItemBean historyItemBean) {
        baseTemplateView.postDelayed(new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                HistoryAdapter.onBind$lambda$6$lambda$5$lambda$4(this.f$0, context, baseTemplateView, baseTemplateView2, historyItemBean);
            }
        }, 140L);
    }

    public static final void onBind$lambda$6$lambda$5$lambda$4(HistoryAdapter historyAdapter, Context context, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, HistoryItemBean historyItemBean) {
        TemplateItemBean templateItemBean;
        Intrinsics.checkNotNull(context);
        CommonConfig current = (historyItemBean == null || (templateItemBean = historyItemBean.getTemplateItemBean()) == null) ? null : templateItemBean.getCurrent();
        Intrinsics.checkNotNull(current);
        historyAdapter.takeScreenshotForCurrentTemplate(context, baseTemplateView, baseTemplateView2, current);
    }

    public static final Unit onBind$lambda$7(HistoryAdapter historyAdapter, int i, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Function2 function2 = historyAdapter.onItemChildClickListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i), it);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onBind$lambda$8(HistoryAdapter historyAdapter, int i, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Function2 function2 = historyAdapter.onItemChildClickListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i), it);
        }
        return Unit.INSTANCE;
    }

    public static final Unit onBind$lambda$9(HistoryAdapter historyAdapter, int i, View it) {
        Intrinsics.checkNotNullParameter(it, "it");
        Function2 function2 = historyAdapter.onItemChildClickListener;
        if (function2 != null) {
            function2.invoke(Integer.valueOf(i), it);
        }
        return Unit.INSTANCE;
    }

    public static final void takeScreenshotForCurrentTemplate$lambda$25(HistoryAdapter historyAdapter, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, CommonConfig commonConfig) {
        CrossViewPagerCallback crossViewPagerCallback = historyAdapter.mCallBack;
        if (crossViewPagerCallback != null) {
            crossViewPagerCallback.onCurrentTemplateScreenshot(baseTemplateView, baseTemplateView2, commonConfig);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        List currentList = getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        HistoryItemBean historyItemBean = (HistoryItemBean) CollectionsKt.getOrNull(currentList, i);
        if (historyItemBean != null) {
            return historyItemBean.getId();
        }
        return 0L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        TemplateItemBean templateItemBean;
        List currentList = getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        HistoryItemBean historyItemBean = (HistoryItemBean) CollectionsKt.getOrNull(currentList, i);
        if (historyItemBean == null || (templateItemBean = historyItemBean.getTemplateItemBean()) == null) {
            return 2;
        }
        return templateItemBean.getItemType();
    }

    public final void onBind(RecyclerView.ViewHolder holder, final int i) {
        TemplateItemBean templateItemBean;
        RoundFrameLayout roundFrameLayout;
        RoundFrameLayout roundFrameLayout2;
        TemplateHistoryConfig history;
        TemplateHistoryConfig history2;
        AppCompatTextView appCompatTextView;
        RoundFrameLayout roundFrameLayout3;
        RoundFrameLayout roundFrameLayout4;
        View root;
        AppCompatTextView appCompatTextView2;
        RoundFrameLayout roundFrameLayout5;
        RoundFrameLayout roundFrameLayout6;
        View root2;
        RoundFrameLayout roundFrameLayout7;
        ViewGroup.LayoutParams layoutParams;
        RoundFrameLayout roundFrameLayout8;
        ViewGroup.LayoutParams layoutParams2;
        RoundFrameLayout roundFrameLayout9;
        RoundFrameLayout roundFrameLayout10;
        TemplateItemBean templateItemBean2;
        RoundFrameLayout roundFrameLayout11;
        RoundFrameLayout roundFrameLayout12;
        RoundFrameLayout roundFrameLayout13;
        RoundFrameLayout roundFrameLayout14;
        KgLayoutMyTemplateItemBinding kgLayoutMyTemplateItemBinding;
        AppCompatTextView appCompatTextView3;
        View view;
        RoundFrameLayout roundFrameLayout15;
        RoundFrameLayout roundFrameLayout16;
        RoundFrameLayout roundFrameLayout17;
        FrameLayout.LayoutParams layoutParams3;
        FrameLayout.LayoutParams layoutParams4;
        RoundFrameLayout roundFrameLayout18;
        RoundFrameLayout roundFrameLayout19;
        RoundFrameLayout roundFrameLayout20;
        ViewGroup.LayoutParams layoutParams5;
        RoundFrameLayout roundFrameLayout21;
        ViewGroup.LayoutParams layoutParams6;
        RoundFrameLayout roundFrameLayout22;
        RoundFrameLayout roundFrameLayout23;
        TemplateItemBean templateItemBean3;
        Intrinsics.checkNotNullParameter(holder, "holder");
        final Context context = holder.itemView.getContext();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Intrinsics.checkNotNull(context);
        int iWidth = deviceUtil.currentDisplaySize(context).width();
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        Rect rectCurrentDisplaySize = deviceUtil.currentDisplaySize(applicationContext);
        int dimensionPixelSize = (iWidth / 4) - holder.itemView.getResources().getDimensionPixelSize(R.dimen.kg_editor_history_item_except_wallpaper_width);
        this.itemDeskWidth = dimensionPixelSize;
        this.itemDeskHeight = (dimensionPixelSize * rectCurrentDisplaySize.height()) / rectCurrentDisplaySize.width();
        Log.i(this.TAG, "onBind---position:" + i + " ---columnScreenWidth:" + iWidth + "---screenWidth:" + rectCurrentDisplaySize.width());
        List currentList = getCurrentList();
        Intrinsics.checkNotNullExpressionValue(currentList, "getCurrentList(...)");
        final HistoryItemBean historyItemBean = (HistoryItemBean) CollectionsKt.getOrNull(currentList, i);
        if (holder.getItemViewType() != 1) {
            KgLayoutHistoryTemplateItemBinding kgLayoutHistoryTemplateItemBinding = (KgLayoutHistoryTemplateItemBinding) DataBindingUtil.getBinding(holder.itemView);
            if (kgLayoutHistoryTemplateItemBinding != null) {
                kgLayoutHistoryTemplateItemBinding.setDataBean(historyItemBean);
                kgLayoutHistoryTemplateItemBinding.setVm(this.viewModel);
                kgLayoutHistoryTemplateItemBinding.executePendingBindings();
            } else {
                kgLayoutHistoryTemplateItemBinding = null;
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout14 = kgLayoutHistoryTemplateItemBinding.ivLockscreen) != null) {
                roundFrameLayout14.removeAllViews();
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout13 = kgLayoutHistoryTemplateItemBinding.ivDesk) != null) {
                roundFrameLayout13.removeAllViews();
            }
            if (historyItemBean != null && (templateItemBean2 = historyItemBean.getTemplateItemBean()) != null && templateItemBean2.getItemType() == 4) {
                if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout12 = kgLayoutHistoryTemplateItemBinding.ivLockscreen) != null) {
                    ImageView imageView = new ImageView(holder.itemView.getContext());
                    RequestManager requestManagerWith = Glide.with(imageView);
                    TemplateItemBean templateItemBean4 = historyItemBean.getTemplateItemBean();
                    requestManagerWith.load(templateItemBean4 != null ? templateItemBean4.getThirdPartyThemePreview() : null).into(imageView);
                    roundFrameLayout12.addView(imageView);
                }
                if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout11 = kgLayoutHistoryTemplateItemBinding.ivDesk) != null) {
                    ImageView imageView2 = new ImageView(holder.itemView.getContext());
                    RequestManager requestManagerWith2 = Glide.with(imageView2);
                    TemplateItemBean templateItemBean5 = historyItemBean.getTemplateItemBean();
                    requestManagerWith2.load(templateItemBean5 != null ? templateItemBean5.getThirdPartyThemePreview() : null).into(imageView2);
                    roundFrameLayout11.addView(imageView2);
                }
            } else if (historyItemBean != null && (templateItemBean = historyItemBean.getTemplateItemBean()) != null && templateItemBean.getItemType() == 2) {
                Context applicationContext2 = holder.itemView.getContext().getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
                int screenStatus = deviceUtil.getScreenStatus(applicationContext2);
                TemplateItemBean templateItemBean6 = historyItemBean.getTemplateItemBean();
                String currentScreenshotUri = (templateItemBean6 == null || (history2 = templateItemBean6.getHistory()) == null) ? null : history2.getCurrentScreenshotUri(screenStatus);
                TemplateItemBean templateItemBean7 = historyItemBean.getTemplateItemBean();
                String currentWallpaperScreenshotUri = (templateItemBean7 == null || (history = templateItemBean7.getHistory()) == null) ? null : history.getCurrentWallpaperScreenshotUri(screenStatus);
                if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout2 = kgLayoutHistoryTemplateItemBinding.ivLockscreen) != null) {
                    ImageView imageView3 = new ImageView(holder.itemView.getContext());
                    ((RequestBuilder) Glide.with(imageView3).load(currentScreenshotUri).transform(new RoundedCorners(this.itemChildRadius))).into(imageView3);
                    roundFrameLayout2.addView(imageView3);
                }
                if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout = kgLayoutHistoryTemplateItemBinding.ivDesk) != null) {
                    ImageView imageView4 = new ImageView(holder.itemView.getContext());
                    RequestManager requestManagerWith3 = Glide.with(imageView4);
                    if (currentWallpaperScreenshotUri != null) {
                        currentScreenshotUri = currentWallpaperScreenshotUri;
                    }
                    ((RequestBuilder) requestManagerWith3.load(currentScreenshotUri).transform(new RoundedCorners(this.itemChildRadius))).into(imageView4);
                    roundFrameLayout.addView(imageView4);
                }
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout10 = kgLayoutHistoryTemplateItemBinding.ivLockscreen) != null) {
                roundFrameLayout10.setRadius(this.itemChildRadius);
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout9 = kgLayoutHistoryTemplateItemBinding.ivDesk) != null) {
                roundFrameLayout9.setRadius(this.itemChildRadius);
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout8 = kgLayoutHistoryTemplateItemBinding.ivLockscreen) != null && (layoutParams2 = roundFrameLayout8.getLayoutParams()) != null) {
                layoutParams2.width = this.itemDeskWidth;
                layoutParams2.height = this.itemDeskHeight;
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout7 = kgLayoutHistoryTemplateItemBinding.ivDesk) != null && (layoutParams = roundFrameLayout7.getLayoutParams()) != null) {
                layoutParams.width = this.itemDeskWidth;
                layoutParams.height = this.itemDeskHeight;
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (root2 = kgLayoutHistoryTemplateItemBinding.getRoot()) != null) {
                ViewExtKt.clickNoRepeat$default(root2, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return HistoryAdapter.onBind$lambda$17(this.f$0, i, (View) obj);
                    }
                }, 1, null);
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout6 = kgLayoutHistoryTemplateItemBinding.ivLockscreen) != null) {
                ViewExtKt.clickNoRepeat$default(roundFrameLayout6, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda7
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return HistoryAdapter.onBind$lambda$18(this.f$0, i, (View) obj);
                    }
                }, 1, null);
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout5 = kgLayoutHistoryTemplateItemBinding.ivDesk) != null) {
                ViewExtKt.clickNoRepeat$default(roundFrameLayout5, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda8
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return HistoryAdapter.onBind$lambda$19(this.f$0, i, (View) obj);
                    }
                }, 1, null);
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (appCompatTextView2 = kgLayoutHistoryTemplateItemBinding.tvTemplateState) != null) {
                ViewExtKt.clickNoRepeat$default(appCompatTextView2, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda9
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return HistoryAdapter.onBind$lambda$20(this.f$0, i, (View) obj);
                    }
                }, 1, null);
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (root = kgLayoutHistoryTemplateItemBinding.getRoot()) != null) {
                root.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda10
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        return HistoryAdapter.onBind$lambda$21(this.f$0, i, view2);
                    }
                });
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout4 = kgLayoutHistoryTemplateItemBinding.ivLockscreen) != null) {
                roundFrameLayout4.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda11
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        return HistoryAdapter.onBind$lambda$22(this.f$0, i, view2);
                    }
                });
            }
            if (kgLayoutHistoryTemplateItemBinding != null && (roundFrameLayout3 = kgLayoutHistoryTemplateItemBinding.ivDesk) != null) {
                roundFrameLayout3.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        return HistoryAdapter.onBind$lambda$23(this.f$0, i, view2);
                    }
                });
            }
            if (kgLayoutHistoryTemplateItemBinding == null || (appCompatTextView = kgLayoutHistoryTemplateItemBinding.tvTemplateState) == null) {
                return;
            }
            appCompatTextView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view2) {
                    return HistoryAdapter.onBind$lambda$24(this.f$0, i, view2);
                }
            });
            return;
        }
        KgLayoutMyTemplateItemBinding kgLayoutMyTemplateItemBinding2 = (KgLayoutMyTemplateItemBinding) DataBindingUtil.getBinding(holder.itemView);
        if (kgLayoutMyTemplateItemBinding2 != null) {
            kgLayoutMyTemplateItemBinding2.setDataBean(historyItemBean);
            kgLayoutMyTemplateItemBinding2.setVm(this.viewModel);
            kgLayoutMyTemplateItemBinding2.executePendingBindings();
            kgLayoutMyTemplateItemBinding = kgLayoutMyTemplateItemBinding2;
        } else {
            kgLayoutMyTemplateItemBinding = null;
        }
        CommonConfig current = (historyItemBean == null || (templateItemBean3 = historyItemBean.getTemplateItemBean()) == null) ? null : templateItemBean3.getCurrent();
        if ((current != null && !current.isUpdated()) || this.forceUpdate) {
            if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout23 = kgLayoutMyTemplateItemBinding.keyguardContainer) != null) {
                roundFrameLayout23.removeAllViews();
            }
            if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout22 = kgLayoutMyTemplateItemBinding.deskContainer) != null) {
                roundFrameLayout22.removeAllViews();
            }
            CommonConfig commonConfigDeepCopy = current != null ? current.deepCopy() : null;
            if (commonConfigDeepCopy == null) {
                Log.w(this.TAG, "onViewHolderBound failed: current == null, position = " + i);
                return;
            }
            DataUtil dataUtil = DataUtil.INSTANCE;
            WallpaperInfo wallpaperInfo = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            WallpaperInfo wallpaperInfo2 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            Context context2 = holder.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            dataUtil.resetWallpaperInfoMagicType(wallpaperInfo, wallpaperInfo2, context2);
            WallpaperInfo wallpaperInfo3 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo3 != null) {
                wallpaperInfo3.setPositionInfo(null);
            }
            WallpaperInfo wallpaperInfo4 = commonConfigDeepCopy.getHomeInfo().getWallpaperInfo();
            if (wallpaperInfo4 != null) {
                wallpaperInfo4.setPositionInfo(null);
            }
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context3 = holder.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
            Point pointProvideRealSize = resourcesProvider.provideRealSize(context3);
            int i2 = this.itemDeskWidth;
            int i3 = this.itemDeskHeight;
            DisplayMetrics displayMetrics = holder.itemView.getResources().getDisplayMetrics();
            int i4 = pointProvideRealSize.x;
            if (i4 <= 0) {
                i4 = displayMetrics.widthPixels;
            }
            int i5 = pointProvideRealSize.y;
            if (i5 <= 0) {
                i5 = displayMetrics.heightPixels;
            }
            this.layoutPramsForTemplate = new FrameLayout.LayoutParams(i4, i5);
            this.templateScaleX = (i2 * 1.0f) / i4;
            this.templateScaleY = (i3 * 1.0f) / i5;
            if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout21 = kgLayoutMyTemplateItemBinding.keyguardContainer) != null && (layoutParams6 = roundFrameLayout21.getLayoutParams()) != null) {
                layoutParams6.width = this.itemDeskWidth;
                layoutParams6.height = this.itemDeskHeight;
            }
            if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout20 = kgLayoutMyTemplateItemBinding.deskContainer) != null && (layoutParams5 = roundFrameLayout20.getLayoutParams()) != null) {
                layoutParams5.width = this.itemDeskWidth;
                layoutParams5.height = this.itemDeskHeight;
            }
            TemplateViewFactory templateViewFactory = TemplateViewFactory.INSTANCE;
            Context context4 = holder.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context4, "getContext(...)");
            FrameLayout.LayoutParams layoutParams7 = this.layoutPramsForTemplate;
            if (layoutParams7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams3 = null;
            } else {
                layoutParams3 = layoutParams7;
            }
            final BaseTemplateView baseTemplateViewCreateScaledPreviewHomeTemplateView$default = TemplateViewFactory.createScaledPreviewHomeTemplateView$default(templateViewFactory, context4, layoutParams3, this.templateScaleX, this.templateScaleY, false, 16, null);
            if (baseTemplateViewCreateScaledPreviewHomeTemplateView$default != null) {
                baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setTemplateSource(-1L);
                baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setExtraParameters(commonConfigDeepCopy.getLockscreenInfo().getClockExtras());
                baseTemplateViewCreateScaledPreviewHomeTemplateView$default.loadTemplate(commonConfigDeepCopy);
                baseTemplateViewCreateScaledPreviewHomeTemplateView$default.setClipToOutline(true);
                ViewUtil.INSTANCE.setSmoothCornerEnabled(baseTemplateViewCreateScaledPreviewHomeTemplateView$default, true);
                if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout19 = kgLayoutMyTemplateItemBinding.deskContainer) != null) {
                    roundFrameLayout19.addView(baseTemplateViewCreateScaledPreviewHomeTemplateView$default);
                }
            } else {
                baseTemplateViewCreateScaledPreviewHomeTemplateView$default = null;
            }
            Context context5 = holder.itemView.getContext();
            Intrinsics.checkNotNullExpressionValue(context5, "getContext(...)");
            String templateId = commonConfigDeepCopy.getLockscreenInfo().getClockInfo().getTemplateId();
            FrameLayout.LayoutParams layoutParams8 = this.layoutPramsForTemplate;
            if (layoutParams8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutPramsForTemplate");
                layoutParams4 = null;
            } else {
                layoutParams4 = layoutParams8;
            }
            final BaseTemplateView baseTemplateViewCreateScaledPreviewTemplateView$default = TemplateViewFactory.createScaledPreviewTemplateView$default(templateViewFactory, context5, templateId, layoutParams4, this.templateScaleX, this.templateScaleY, false, 32, null);
            if (baseTemplateViewCreateScaledPreviewTemplateView$default != null) {
                baseTemplateViewCreateScaledPreviewTemplateView$default.setTemplateSource(-1L);
                baseTemplateViewCreateScaledPreviewTemplateView$default.setExtraParameters(commonConfigDeepCopy.getLockscreenInfo().getClockExtras());
                baseTemplateViewCreateScaledPreviewTemplateView$default.loadTemplate(commonConfigDeepCopy);
                baseTemplateViewCreateScaledPreviewTemplateView$default.setClipToOutline(true);
                ViewUtil.INSTANCE.setSmoothCornerEnabled(baseTemplateViewCreateScaledPreviewTemplateView$default, true);
                if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout18 = kgLayoutMyTemplateItemBinding.keyguardContainer) != null) {
                    roundFrameLayout18.addView(baseTemplateViewCreateScaledPreviewTemplateView$default);
                }
                baseTemplateViewCreateScaledPreviewTemplateView$default.addMiuiClockViewInitListener(new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        HistoryAdapter.onBind$lambda$6$lambda$5(baseTemplateViewCreateScaledPreviewTemplateView$default, this, context, baseTemplateViewCreateScaledPreviewHomeTemplateView$default, historyItemBean);
                    }
                });
            }
            if (current != null) {
                current.setUpdated(true);
            }
            this.forceUpdate = false;
        }
        if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout17 = kgLayoutMyTemplateItemBinding.keyguardContainer) != null) {
            roundFrameLayout17.setRadius(this.itemChildRadius);
        }
        if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout16 = kgLayoutMyTemplateItemBinding.deskContainer) != null) {
            roundFrameLayout16.setRadius(this.itemChildRadius);
        }
        if (kgLayoutMyTemplateItemBinding != null && (roundFrameLayout15 = kgLayoutMyTemplateItemBinding.keyguardContainer) != null) {
            ViewExtKt.clickNoRepeat$default(roundFrameLayout15, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryAdapter.onBind$lambda$7(this.f$0, i, (View) obj);
                }
            }, 1, null);
        }
        if (kgLayoutMyTemplateItemBinding != null && (view = kgLayoutMyTemplateItemBinding.deskClickView) != null) {
            ViewExtKt.clickNoRepeat$default(view, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return HistoryAdapter.onBind$lambda$8(this.f$0, i, (View) obj);
                }
            }, 1, null);
        }
        if (kgLayoutMyTemplateItemBinding == null || (appCompatTextView3 = kgLayoutMyTemplateItemBinding.tvTemplateState) == null) {
            return;
        }
        ViewExtKt.clickNoRepeat$default(appCompatTextView3, 0L, new Function1() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HistoryAdapter.onBind$lambda$9(this.f$0, i, (View) obj);
            }
        }, 1, null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        onBind(holder, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.itemRadius = parent.getResources().getDimensionPixelSize(R.dimen.kg_editor_history_item_radius);
        this.itemChildRadius = parent.getResources().getDimensionPixelSize(DeviceUtil.INSTANCE.isPhone() ? R.dimen.kg_editor_history_item_desk_radius : R.dimen.kg_editor_history_pad_and_fold_item_desk_radius);
        this.itemChildRoundoutline = new RoundOutlineProvider(this.itemChildRadius);
        if (i == 1) {
            ViewDataBinding viewDataBindingInflate = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.kg_layout_my_template_item, parent, false);
            Intrinsics.checkNotNullExpressionValue(viewDataBindingInflate, "inflate(...)");
            View root = ((KgLayoutMyTemplateItemBinding) viewDataBindingInflate).getRoot();
            Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
            return new ListViewHolder(root);
        }
        ViewDataBinding viewDataBindingInflate2 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.kg_layout_history_template_item, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewDataBindingInflate2, "inflate(...)");
        View root2 = ((KgLayoutHistoryTemplateItemBinding) viewDataBindingInflate2).getRoot();
        Intrinsics.checkNotNullExpressionValue(root2, "getRoot(...)");
        return new ListViewHolder(root2);
    }

    public final void setCallBack(CrossViewPagerCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.mCallBack = callback;
    }

    public final void setForceUpdate(boolean z) {
        this.forceUpdate = z;
    }

    public final void setOnItemChildClickListener(Function2 function2) {
        this.onItemChildClickListener = function2;
    }

    public final void setOnItemClickListener(Function2 function2) {
        this.onItemClickListener = function2;
    }

    public final void setOnItemLongClickListener(Function2 function2) {
        this.onItemLongClickListener = function2;
    }

    @Override // androidx.recyclerview.widget.ListAdapter
    public void submitList(List list) {
        if (list == null) {
            list = new ArrayList();
        }
        super.submitList(list);
    }

    public final void takeScreenshotForCurrentTemplate(Context context, final BaseTemplateView baseTemplateView, final BaseTemplateView baseTemplateView2, final CommonConfig commonConfig) {
        if (ContextUtilKt.isActivityInvalid(context) || baseTemplateView == null) {
            return;
        }
        baseTemplateView.post(new Runnable() { // from class: com.miui.keyguard.editor.history.HistoryAdapter$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                HistoryAdapter.takeScreenshotForCurrentTemplate$lambda$25(this.f$0, baseTemplateView, baseTemplateView2, commonConfig);
            }
        });
    }
}
