package com.miui.aod.components.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.photo.Edit.Crop;
import com.miui.aod.components.widget.ColorSelectView;
import com.miui.aod.components.widget.ImageSelectView;
import com.miui.aod.db.HistoryProviderHelper;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.settings.AodStyleSelectActivity;
import com.miui.aod.settings.RouterActivity;
import com.miui.aod.util.AodUtils;
import com.miui.aod.widget.IAodClock;
import com.miui.aod.widget.ImageSelectorClock;
import java.io.File;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class ImageSelectorStyleSelectView extends BaseStyleSelectView implements CropFragment.ICropCanvasBoundInitListener, Crop.IOnTabListener, ExportFragment.Callbacks {
    private static final String TAG = "PureColorAndImageStyleS";
    private ImageSelectView mBackgroundSelectView;
    private TextView mBgSelectTitleTv;
    private ColorSelectView mColorSelectView;
    private CropFragment mCropFragment;
    private FragmentManager mFragmentManager;
    private RadioGroup mImageSelectRG;
    private ImageSelectorCategoryInfo mImageSelectorCategoryInfo;
    private ImageSelectorClock mImageSelectorClock;
    private float mTransY;

    /* JADX INFO: renamed from: com.miui.aod.components.view.ImageSelectorStyleSelectView$2, reason: invalid class name */
    public class AnonymousClass2 implements ImageSelectorShowerView.ImageLoadListener {
        /* JADX INFO: renamed from: $r8$lambda$-iMTPHrIaoRvK9Qe7UBzVPLRCP8, reason: not valid java name */
        public static /* synthetic */ void m271$r8$lambda$iMTPHrIaoRvK9Qe7UBzVPLRCP8(View view, int[] iArr, Runnable runnable) {
            view.getLocationInWindow(iArr);
            runnable.run();
        }

        public static /* synthetic */ void $r8$lambda$s6BNUQUGKGsunYUY7jnMsmwI1Qc(AnonymousClass2 anonymousClass2, int[] iArr, Drawable drawable, boolean z) {
            if (ImageSelectorStyleSelectView.this.mImageSelectorCategoryInfo == null || ImageSelectorStyleSelectView.this.mCropFragment == null || !ImageSelectorStyleSelectView.this.mCropFragment.isAdded()) {
                return;
            }
            Matrix matrixFromValues = ImageSelectorStyleSelectView.this.mImageSelectorCategoryInfo.getMatrixFromValues(ImageSelectorStyleSelectView.this.mContext, iArr);
            CropFragment cropFragment = ImageSelectorStyleSelectView.this.mCropFragment;
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (z) {
                matrixFromValues = new Matrix(ImageSelectorStyleSelectView.this.mCropFragment.mEditorView.mBitmapToCanvas);
            }
            cropFragment.setBitmap(bitmap, matrixFromValues);
        }

        public AnonymousClass2() {
        }

        @Override // com.miui.aod.components.view.ImageSelectorShowerView.ImageLoadListener
        public void onFail() {
            ImageSelectorStyleSelectView.this.mApplyButton.setEnabled(false);
            ImageSelectorStyleSelectView.this.mCropFragment.setBitmap(null, null);
        }

        @Override // com.miui.aod.components.view.ImageSelectorShowerView.ImageLoadListener
        public void onSucceed(final Drawable drawable, final boolean z) {
            ImageSelectorStyleSelectView.this.mApplyButton.setEnabled(true);
            if (ImageSelectorStyleSelectView.this.mFragmentManager != null) {
                if (!(drawable instanceof BitmapDrawable)) {
                    ImageSelectorStyleSelectView.this.mFragmentManager.beginTransaction().remove(ImageSelectorStyleSelectView.this.mCropFragment).commitAllowingStateLoss();
                    return;
                }
                if (!ImageSelectorStyleSelectView.this.mCropFragment.isAdded()) {
                    ImageSelectorStyleSelectView.this.mFragmentManager.beginTransaction().add(R.id.aod_content, ImageSelectorStyleSelectView.this.mCropFragment).commitAllowingStateLoss();
                }
                final int[] iArr = new int[2];
                final Runnable runnable = new Runnable() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ImageSelectorStyleSelectView.AnonymousClass2.$r8$lambda$s6BNUQUGKGsunYUY7jnMsmwI1Qc(this.f$0, iArr, drawable, z);
                    }
                };
                final View viewFindViewById = ImageSelectorStyleSelectView.this.mRootView.findViewById(R.id.aod_content_container);
                if (viewFindViewById != null) {
                    viewFindViewById.post(new Runnable() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView$2$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ImageSelectorStyleSelectView.AnonymousClass2.m271$r8$lambda$iMTPHrIaoRvK9Qe7UBzVPLRCP8(viewFindViewById, iArr, runnable);
                        }
                    });
                } else {
                    runnable.run();
                }
            }
        }
    }

    public static class AsyncApplyTask implements Runnable {
        private final int[] content_container_location;
        private final String mCate;
        private final Context mContext;
        private final String mParam;
        private final EditorView.ImageClipTask mTask;

        public AsyncApplyTask(Context context, String str, String str2, EditorView.ImageClipTask imageClipTask, int[] iArr) {
            this.mContext = context.getApplicationContext();
            this.mParam = str2;
            this.mCate = str;
            this.mTask = imageClipTask;
            this.content_container_location = iArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mTask.run();
            ImageSelectorCategoryInfo imageSelectorCategoryInfo = (ImageSelectorCategoryInfo) CategoryFactory.createStyleInfo(this.mContext, this.mCate, this.mParam);
            if (new File(this.mTask.mDir).exists()) {
                float[] fArr = new float[9];
                this.mTask.mBitmapToCanvas.getValues(fArr);
                imageSelectorCategoryInfo.setMatrixValues(fArr, this.mContext, this.content_container_location);
                imageSelectorCategoryInfo.setCroppedUriString(this.mTask.mDir);
                String json = new Gson().toJson(imageSelectorCategoryInfo);
                Utils.setAodUsingCategory(this.mContext, this.mCate, json);
                Utils.setUsingSuperWallpaperStyleState(this.mContext, false);
                RouterActivity.tryToEnableAod(AODApplication.sInstance, "ImageSelectorStyleSelectView");
                HistoryProviderHelper.getInstance(this.mContext).insert(this.mCate, json);
            }
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$MKq-yK4KwXjixAKSLUaHfM9YOLo, reason: not valid java name */
    public static /* synthetic */ void m267$r8$lambda$MKqyK4KwXjixAKSLUaHfM9YOLo(ImageSelectorStyleSelectView imageSelectorStyleSelectView) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageSelectorStyleSelectView.mImageSelectRG.getLayoutParams();
        RadioButton radioButton = (RadioButton) imageSelectorStyleSelectView.mImageSelectRG.findViewById(R.id.above_image);
        RadioButton radioButton2 = (RadioButton) imageSelectorStyleSelectView.mImageSelectRG.findViewById(R.id.below_image);
        RadioButton radioButton3 = (RadioButton) imageSelectorStyleSelectView.mImageSelectRG.findViewById(R.id.not_show);
        if (radioButton.getLineCount() > 1 || radioButton2.getLineCount() > 1 || radioButton3.getLineCount() > 1) {
            layoutParams.height = -1;
            imageSelectorStyleSelectView.mImageSelectRG.setLayoutParams(layoutParams);
        }
    }

    public static /* synthetic */ void $r8$lambda$VIocXp6mk9GGh8H8LZDweXOxG6s(ImageSelectorStyleSelectView imageSelectorStyleSelectView, RadioGroup radioGroup, int i) {
        if (i == R.id.above_image) {
            imageSelectorStyleSelectView.mImageSelectorCategoryInfo.setExtraInfoShowStyle(1);
            imageSelectorStyleSelectView.enableLunarSlingBtn(true);
        } else if (i == R.id.below_image) {
            imageSelectorStyleSelectView.mImageSelectorCategoryInfo.setExtraInfoShowStyle(2);
            imageSelectorStyleSelectView.enableLunarSlingBtn(true);
        } else if (i == R.id.not_show) {
            imageSelectorStyleSelectView.mImageSelectorCategoryInfo.setExtraInfoShowStyle(0);
            imageSelectorStyleSelectView.enableLunarSlingBtn(false);
        }
        ImageSelectorClock imageSelectorClock = imageSelectorStyleSelectView.mImageSelectorClock;
        if (imageSelectorClock == null || imageSelectorClock.getView() == null) {
            return;
        }
        imageSelectorStyleSelectView.mImageSelectorClock.getView().updatePosition();
    }

    public ImageSelectorStyleSelectView(Context context) {
        super(context);
        if (context instanceof FragmentActivity) {
            this.mFragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            CropFragment cropFragment = new CropFragment();
            this.mCropFragment = cropFragment;
            cropFragment.setICropCanvasBoundInitListener(this);
            this.mCropFragment.setIOnTabListener(this);
        }
    }

    private int findDrawableById(List<DrawableData> list, String str) {
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).mName.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private int getDimensionPixelSize(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackgroundSelect(int i, DrawableData drawableData) {
        if (this.mStyleInfo.getClockBgResID() != drawableData.mResId) {
            Log.i(TAG, "onBackgroundSelect: " + drawableData.mName);
            this.mStyleInfo.setClockBgName(drawableData.mName);
            updateStyleInfoForPreview();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onColorSelect(int i, DrawableData drawableData) {
        String colorName = this.mStyleInfo.getColorName();
        if (TextUtils.isEmpty(drawableData.mName) || drawableData.mName.equals(colorName)) {
            return;
        }
        Log.i(TAG, "onColorSelect: " + drawableData.mName);
        this.mStyleInfo.setColorName(drawableData.mName);
        ImageSelectorClock imageSelectorClock = this.mImageSelectorClock;
        if (imageSelectorClock != null) {
            imageSelectorClock.setClockMask(this.mStyleInfo.getClockColor(), this.mStyleInfo.getClockMask());
            this.mImageSelectorClock.getView().mGradientLayout.invalidate();
        }
    }

    private void selectPicture() {
        Utils.selectPicture(this.mContext);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void applyStyle() {
        if (this.mFragmentManager == null || !this.mCropFragment.isAdded()) {
            this.mImageSelectorCategoryInfo.setCroppedUriString(null);
            super.applyStyle();
        } else {
            ExportFragment exportFragment = new ExportFragment();
            exportFragment.setCallbacks(this);
            exportFragment.show(this.mFragmentManager, "export_dialog");
        }
    }

    public void applyStyleAsync() {
        int[] iArr;
        String json = new Gson().toJson(this.mImageSelectorCategoryInfo);
        EditorView editorView = this.mCropFragment.mEditorView;
        if (editorView == null) {
            Log.e(TAG, "applyStyleAsync: mCropFragment.mEditorView == null");
            return;
        }
        EditorView.ImageClipTask imageClipTaskCreateClipTask = editorView.createClipTask(this.mContext);
        View viewFindViewById = this.mRootView.findViewById(R.id.aod_content_container);
        if (viewFindViewById != null) {
            iArr = new int[2];
            viewFindViewById.getLocationInWindow(iArr);
        } else {
            iArr = null;
        }
        BackgroundThread.getHandler().post(new AsyncApplyTask(this.mContext, this.mCateNameSelected, json, imageClipTaskCreateClipTask, iArr));
    }

    @Override // com.miui.aod.components.view.ExportFragment.Callbacks
    public boolean doExport() {
        int[] iArr;
        try {
            File fileClip = this.mCropFragment.clip();
            if (fileClip == null || !fileClip.exists()) {
                return false;
            }
            String absolutePath = fileClip.getAbsolutePath();
            float[] fArr = new float[9];
            this.mCropFragment.mEditorView.mBitmapToCanvas.getValues(fArr);
            View viewFindViewById = this.mRootView.findViewById(R.id.aod_content_container);
            if (viewFindViewById != null) {
                iArr = new int[2];
                viewFindViewById.getLocationInWindow(iArr);
            } else {
                iArr = null;
            }
            this.mImageSelectorCategoryInfo.setMatrixValues(fArr, this.mContext, iArr);
            this.mImageSelectorCategoryInfo.setCroppedUriString(absolutePath);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error...", e);
            return false;
        }
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void inflateStyleSelectPanelView(ViewGroup viewGroup) {
        this.mAodContainerContainer.setClickable(false);
        this.mApplyButton.setEnabled(false);
        ((ViewGroup.MarginLayoutParams) this.mPreview.getLayoutParams()).topMargin = getDimensionPixelSize(R.dimen.image_selector_top_margin);
        this.mLayoutInflater.inflate(R.layout.image_selector_style_select_layout, viewGroup, true);
        ColorSelectView colorSelectView = (ColorSelectView) this.mRootView.findViewById(R.id.color_select_view);
        this.mColorSelectView = colorSelectView;
        colorSelectView.setOnItemClickListener(new ColorSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView$$ExternalSyntheticLambda0
            @Override // com.miui.aod.components.widget.ColorSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onColorSelect(i, drawableData);
            }
        });
        ImageSelectView imageSelectView = (ImageSelectView) this.mRootView.findViewById(R.id.bg_select_view);
        this.mBackgroundSelectView = imageSelectView;
        imageSelectView.setOnItemClickListener(new ImageSelectView.OnItemClickListener() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView$$ExternalSyntheticLambda1
            @Override // com.miui.aod.components.widget.ImageSelectView.OnItemClickListener
            public final void onItemClick(int i, DrawableData drawableData) {
                this.f$0.onBackgroundSelect(i, drawableData);
            }
        });
        this.mBgSelectTitleTv = (TextView) this.mRootView.findViewById(R.id.bg_select_title);
        RadioGroup radioGroup = (RadioGroup) this.mRootView.findViewById(R.id.imagestyle_selector_rg);
        this.mImageSelectRG = radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView$$ExternalSyntheticLambda2
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                ImageSelectorStyleSelectView.$r8$lambda$VIocXp6mk9GGh8H8LZDweXOxG6s(this.f$0, radioGroup2, i);
            }
        });
        this.mImageSelectRG.post(new Runnable() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ImageSelectorStyleSelectView.m267$r8$lambda$MKqyK4KwXjixAKSLUaHfM9YOLo(this.f$0);
            }
        });
        initAnchorView(R.id.battery_widget_switcher, 1, 0);
    }

    @Override // com.miui.aod.components.view.CropFragment.ICropCanvasBoundInitListener
    public RectF initCanvasBound() {
        int[] iArr = new int[2];
        this.mImageSelectorClock.getView().mImageShowerContainer.getLocationInWindow(iArr);
        int i = iArr[1];
        return new RectF(iArr[0], i, getDimensionPixelSize(R.dimen.image_selector_shower_width) + iArr[0], getDimensionPixelSize(R.dimen.image_selector_shower_height) + i);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
        if (styleInfo instanceof ImageSelectorCategoryInfo) {
            this.mImageSelectorCategoryInfo = (ImageSelectorCategoryInfo) styleInfo;
            if (this.mColorSelectView != null) {
                List<DrawableData> pureDrawables = AodDrawables.getPureDrawables();
                this.mColorSelectView.setDrawableData(pureDrawables);
                int iMax = Math.max(findDrawableById(pureDrawables, styleInfo.getColorName()), 0);
                this.mColorSelectView.setItemSelected(iMax);
                ColorSelectView colorSelectView = this.mColorSelectView;
                colorSelectView.smoothScrollToPosition(colorSelectView.getSelection());
                if (!pureDrawables.isEmpty()) {
                    onColorSelect(iMax, pureDrawables.get(iMax));
                }
            }
            if (this.mBackgroundSelectView != null) {
                List<DrawableData> drawableListByCateName = AodDrawables.getDrawableListByCateName(this.mCateNameSelected);
                List clusterData = AodUtils.getClusterData(drawableListByCateName, styleInfo.getBgCluster());
                this.mBackgroundSelectView.setDrawableData(clusterData);
                if (clusterData == null || clusterData.size() == 0) {
                    this.mBgSelectTitleTv.setVisibility(8);
                    this.mBackgroundSelectView.setVisibility(8);
                }
                this.mBackgroundSelectView.setItemSelected(findDrawableById(drawableListByCateName, styleInfo.getClockBgName()));
                ImageSelectView imageSelectView = this.mBackgroundSelectView;
                imageSelectView.smoothScrollToPosition(imageSelectView.getSelection());
            }
            int extraInfoShowStyle = this.mImageSelectorCategoryInfo.getExtraInfoShowStyle();
            if (extraInfoShowStyle == 0) {
                this.mImageSelectRG.check(R.id.not_show);
                return;
            }
            if (extraInfoShowStyle == 1) {
                this.mImageSelectRG.check(R.id.above_image);
            } else if (extraInfoShowStyle != 2) {
                this.mImageSelectRG.check(R.id.below_image);
            } else {
                this.mImageSelectRG.check(R.id.below_image);
            }
        }
    }

    @Override // com.miui.aod.components.view.ExportFragment.Callbacks
    public void onCancelled(boolean z) {
    }

    @Override // com.miui.aod.components.view.ExportFragment.Callbacks
    public void onExported(boolean z) {
        super.applyStyle();
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void onPreViewTranslateY(float f) {
        float f2 = f - this.mTransY;
        this.mTransY = f;
        CropFragment cropFragment = this.mCropFragment;
        if (cropFragment == null || !cropFragment.isAdded()) {
            return;
        }
        this.mCropFragment.CanvasBoundTranslate(f2);
    }

    @Override // com.miui.aod.components.photo.Edit.Crop.IOnTabListener
    public void onTab(boolean z) {
        if (!z) {
            if (this.mPreviewMode) {
                this.mCropFragment.mEditorView.setCropable(true);
            } else {
                this.mCropFragment.mEditorView.setCropable(false);
            }
            showPreviewMode(!this.mPreviewMode);
            return;
        }
        boolean z2 = this.mPreviewMode;
        if (!z2) {
            selectPicture();
        } else {
            showPreviewMode(!z2);
            this.mCropFragment.mEditorView.setCropable(true);
        }
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    public void showPreviewMode(boolean z) {
        super.showPreviewMode(z);
        this.mImageSelectorClock.getView().enterPreviewMode(z);
    }

    @Override // com.miui.aod.components.view.BaseStyleSelectView
    @Deprecated
    public IAodClock updateStyleInfoForPreview() {
        final IAodClock iAodClockUpdateStyleInfoForPreview = super.updateStyleInfoForPreview();
        if (iAodClockUpdateStyleInfoForPreview instanceof ImageSelectorClock) {
            ImageSelectorClock imageSelectorClock = (ImageSelectorClock) iAodClockUpdateStyleInfoForPreview;
            this.mImageSelectorClock = imageSelectorClock;
            imageSelectorClock.getView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView.1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    ImageSelectorStyleSelectView.this.mCropFragment.translateCanvasBounds(ImageSelectorStyleSelectView.this.initCanvasBound());
                }
            });
            this.mImageSelectorClock.getView().setImageLoadListener(new AnonymousClass2());
            ((AodStyleSelectActivity) this.mContext).addPhotoAlbumPicker(new AodStyleSelectActivity.PhotoAlbumPickerListener() { // from class: com.miui.aod.components.view.ImageSelectorStyleSelectView.3
                @Override // com.miui.aod.settings.AodStyleSelectActivity.PhotoAlbumPickerListener
                public void onImageSelectCancel() {
                    if (ImageSelectorStyleSelectView.this.mImageSelectorCategoryInfo.getUriString() == null) {
                        EventBus.getDefault().post(new BaseStyleSelectView.ExitSelectViewEvent());
                    }
                }

                @Override // com.miui.aod.settings.AodStyleSelectActivity.PhotoAlbumPickerListener
                public void onImageSelected(String str) {
                    int[] iArr;
                    ImageSelectorStyleSelectView.this.mApplyButton.setEnabled(false);
                    ImageSelectorStyleSelectView.this.mCropFragment.setBitmap(null, null);
                    ImageSelectorStyleSelectView.this.mImageSelectorCategoryInfo.setUriString(str);
                    if (str == null) {
                        Log.wtf(ImageSelectorStyleSelectView.TAG, "error acquire imageUrl");
                        return;
                    }
                    View viewFindViewById = ImageSelectorStyleSelectView.this.mRootView.findViewById(R.id.aod_content_container);
                    if (viewFindViewById != null) {
                        iArr = new int[2];
                        viewFindViewById.getLocationInWindow(iArr);
                    } else {
                        iArr = null;
                    }
                    ImageSelectorStyleSelectView.this.mImageSelectorCategoryInfo.setMatrixValues(null, ImageSelectorStyleSelectView.this.mContext, iArr);
                    ((ImageSelectorClock) iAodClockUpdateStyleInfoForPreview).getView().onImageSelected(str);
                }
            });
        }
        return iAodClockUpdateStyleInfoForPreview;
    }
}
