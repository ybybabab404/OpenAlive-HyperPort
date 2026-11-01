package com.miui.aod.components;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.linkagestyle.LinkageStyleClockView;
import com.miui.aod.settings.RouterActivity;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.IAodClock;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class BaseStyleSelectPresenter implements StyleSelectContract.StyleSelectPresenter<BaseStyleSelectView> {
    public static final String KEY_CATE = "category";
    public static final String KEY_PARAMETER = "param";
    public static final String KEY_SOURCE_TYPE = "sourceType";
    private static final String TAG = "BaseStyleSelectPresenter";
    private ApplyTask mApplyTask;
    protected String mCategory;
    protected Context mContext;
    private IAodClock mIAodClock;
    protected String mSelectStyleParameter;
    protected BaseStyleSelectView mStyleSelectView;
    private final Runnable mUpdateRunnable = new Runnable() { // from class: com.miui.aod.components.BaseStyleSelectPresenter.1
        @Override // java.lang.Runnable
        public void run() {
            BaseStyleSelectView baseStyleSelectView = BaseStyleSelectPresenter.this.mStyleSelectView;
            if (baseStyleSelectView == null) {
                return;
            }
            baseStyleSelectView.updateTime();
            BaseStyleSelectPresenter baseStyleSelectPresenter = BaseStyleSelectPresenter.this;
            baseStyleSelectPresenter.mHandler.postDelayed(baseStyleSelectPresenter.mUpdateRunnable, 60000L);
        }
    };
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    public static final class ApplyStyleEvent {
    }

    public static class ApplyTask implements Runnable {
        private final String mCate;
        private final Context mContext;
        private final boolean mNeedBtn;
        private final String mParam;

        public ApplyTask(Context context, String str, String str2, boolean z) {
            this.mContext = context;
            this.mParam = str2;
            this.mCate = str;
            this.mNeedBtn = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            Utils.setAodUsingCategory(this.mContext, this.mCate, this.mParam);
            if (!CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(this.mCate)) {
                Utils.setUsingSuperWallpaperStyleState(this.mContext, false);
                if (CategoryConstants.CATEGORY_LINKAGE.equals(this.mCate)) {
                    Utils.setAodUsingStyleState(this.mContext, 1);
                } else {
                    Utils.setAodUsingStyleState(this.mContext, 2);
                    HistoryProviderHelper.getInstance(this.mContext).insert(this.mCate, this.mParam);
                }
            }
            BackgroundThread.getHandler().post(new Runnable() { // from class: com.miui.aod.components.BaseStyleSelectPresenter$ApplyTask$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ThemeUtils.notifyWithBroadcast(this.f$0.mContext);
                }
            });
            BackgroundThread.getHandler().postDelayed(new Runnable() { // from class: com.miui.aod.components.BaseStyleSelectPresenter$ApplyTask$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RouterActivity.tryToEnableAod(this.f$0.mContext, BaseStyleSelectPresenter.TAG);
                }
            }, 500L);
            if (this.mNeedBtn && Utils.isAodEnable(this.mContext)) {
                Utils.showSuccessToast(this.mContext);
            }
        }
    }

    public BaseStyleSelectPresenter(Context context, BaseStyleSelectView baseStyleSelectView, Intent intent) {
        this.mContext = context;
        this.mStyleSelectView = baseStyleSelectView;
        this.mSelectStyleParameter = intent.getStringExtra(KEY_PARAMETER);
        this.mCategory = intent.getStringExtra(KEY_CATE);
        this.mStyleSelectView.setPresenter(this);
    }

    private void updateLinkeageViewAndLunar(Boolean bool) {
        LinkageStyleClockView linkageStyleClockView;
        BaseStyleSelectView baseStyleSelectView = this.mStyleSelectView;
        if (baseStyleSelectView == null || !(baseStyleSelectView.getStyleInfo() instanceof LinkageClockCategoryInfo) || (linkageStyleClockView = (LinkageStyleClockView) this.mStyleSelectView.getAodContainerContainerView().findViewById(R.id.linkage_clock_container)) == null) {
            return;
        }
        if (linkageStyleClockView.getMClockIsChange() && bool.booleanValue()) {
            this.mStyleSelectView.updateStyleInfoForPreview();
            linkageStyleClockView = (LinkageStyleClockView) this.mStyleSelectView.getAodContainerContainerView().findViewById(R.id.linkage_clock_container);
        }
        if (!AODSettings.supportShowLunarDate(this.mContext) || linkageStyleClockView == null || linkageStyleClockView.notSupportLunarTime()) {
            this.mStyleSelectView.setLunarSlingVisibility(8);
        } else {
            this.mStyleSelectView.setLunarSlingVisibility(0);
        }
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter
    public void applyStyleParameter(String str) {
        this.mApplyTask = new ApplyTask(this.mContext.getApplicationContext(), this.mCategory, str, needApplyBtn());
        BackgroundThread.getHandler().post(this.mApplyTask);
    }

    public IAodClock getIAodClock() {
        return this.mIAodClock;
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter
    public String getStyleCategory() {
        return this.mCategory;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter
    public BaseStyleSelectView getView() {
        return this.mStyleSelectView;
    }

    public void initViewInfo(Bundle bundle) {
        BaseStyleSelectView baseStyleSelectView = this.mStyleSelectView;
        if (baseStyleSelectView != null) {
            this.mIAodClock = baseStyleSelectView.initStyleInfoSelected(this.mCategory, this.mSelectStyleParameter, bundle);
        }
    }

    public boolean needApplyBtn() {
        return true;
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public void onCreate(@NonNull LifecycleOwner lifecycleOwner) {
        updateLinkeageViewAndLunar(Boolean.FALSE);
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(@NonNull LifecycleOwner lifecycleOwner) {
        this.mHandler.removeCallbacksAndMessages(null);
        BaseStyleSelectView baseStyleSelectView = this.mStyleSelectView;
        if (baseStyleSelectView != null) {
            baseStyleSelectView.onDestroy();
        }
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public void onPause(@NonNull LifecycleOwner lifecycleOwner) {
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public void onResume(@NonNull LifecycleOwner lifecycleOwner) {
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
        Calendar calendar = new Calendar(TimeZone.getDefault());
        this.mHandler.postDelayed(this.mUpdateRunnable, ((60 - calendar.get(21)) * 1000) + (1000 - calendar.get(22)));
        this.mStyleSelectView.freshStatusIfNeeded();
        updateLinkeageViewAndLunar(Boolean.TRUE);
    }

    public void onSaveInstanceState(Bundle bundle) {
        BaseStyleSelectView baseStyleSelectView = this.mStyleSelectView;
        if (baseStyleSelectView != null) {
            baseStyleSelectView.onSaveInstanceState(bundle);
        }
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStart(@NotNull LifecycleOwner lifecycleOwner) {
        super.onStart(lifecycleOwner);
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectPresenter, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStop(@NotNull LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
    }

    public void setIAodClock(IAodClock iAodClock) {
        this.mIAodClock = iAodClock;
    }
}
