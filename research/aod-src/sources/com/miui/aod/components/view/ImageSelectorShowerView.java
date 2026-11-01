package com.miui.aod.components.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.notification.NotificationUtils;
import com.miui.aod.part.PartProvider;
import com.miui.aod.settings.AodStyleSelectActivity;
import com.miui.aod.util.AodUtils;
import com.miui.aod.util.BitmapFactoryUtils;
import com.miui.aod.util.BitmapUtils;
import com.miui.aod.util.FileUtils;
import com.miui.aod.util.InvertBitmapTransformation;
import com.miui.aod.util.ReflectUtil;
import com.miui.aod.util.WindowUtils;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.ContentProviderUtils;
import com.miui.aod.utils.UserHandleUtils;
import com.miui.aod.widget.AODSettings;
import com.miui.aod.widget.GradientLinearLayout;
import com.miui.getalpha.GetAlpha;
import com.miui.keyguard.editor.utils.DeviceUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/* JADX INFO: loaded from: classes.dex */
public class ImageSelectorShowerView extends LinearLayout implements IPreAodView, AodStyleSelectActivity.PhotoAlbumPickerListener {
    public static final String TAG = "ImageSelectorShowerView";
    private Bitmap bitmap;
    private File file;
    private boolean fileExist;
    private View mAddIcon;
    private View mBatteryContainer;
    public TextView mCity2;
    public TextView mClockHorizontal;
    private View mClockHorizontalContainer;
    public TextView mDateDual;
    public View mDateDualContainer;
    public TextView mDateLunar;
    public TextView mDateView;
    public GradientLinearLayout mGradientLayout;
    public LinearLayout mHeaderLL;
    private ImageLoadListener mImageLoadListener;
    public SquareFrameLayout mImageShowerContainer;
    private ImageView mImageShowerIv;
    private boolean mInPreview;
    private View mInflatedNotificationIcons;
    private ImageView mInvertBtn;
    private ImageSelectorCategoryInfo mPicSelectCategoryInfo;
    private String mSelectedImageUri;
    private int mShowStyle;
    private int mSize;
    private float mSmallWidthLess;

    /* JADX INFO: renamed from: com.miui.aod.components.view.ImageSelectorShowerView$3, reason: invalid class name */
    public class AnonymousClass3 implements RequestListener<Drawable> {
        final /* synthetic */ String val$imageUri;

        /* JADX INFO: renamed from: $r8$lambda$gDpxGV7yqiXpFQpwJj36lq-Qj6w, reason: not valid java name */
        public static /* synthetic */ void m266$r8$lambda$gDpxGV7yqiXpFQpwJj36lqQj6w(AnonymousClass3 anonymousClass3, GifDrawable gifDrawable) {
            anonymousClass3.getClass();
            if (BitmapFactoryUtils.INSTANCE.getGifDuration(gifDrawable) > 5000) {
                final String quantityString = ImageSelectorShowerView.this.getResources().getQuantityString(R.plurals.choose_long_gif_limit_toast_tip, 5, 5);
                AodUtils.getMainHandler().post(new Runnable() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Toast.makeText(AODApplication.sInstance, quantityString, 0).show();
                    }
                });
            }
        }

        public AnonymousClass3(String str) {
            this.val$imageUri = str;
        }

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
            Log.i(ImageSelectorShowerView.TAG, "getImageFutureDrawable load failed: " + this.val$imageUri + ", size: " + ImageSelectorShowerView.this.mSize, glideException);
            ImageSelectorShowerView.this.mPicSelectCategoryInfo.setUriString(null);
            ImageSelectorShowerView.this.mPicSelectCategoryInfo.setCroppedUriString(null);
            return false;
        }

        public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
            Log.i(ImageSelectorShowerView.TAG, "getImageFutureDrawable resource ready: " + this.val$imageUri + ", resource: " + drawable);
            if (!(drawable instanceof GifDrawable)) {
                return false;
            }
            final GifDrawable gifDrawable = (GifDrawable) drawable;
            gifDrawable.setLoopCount(1);
            if (TextUtils.isEmpty(ImageSelectorShowerView.this.mSelectedImageUri) || Utils.getTemporaryStyleDuration() != 5000 || !Utils.isShowTemporary(ImageSelectorShowerView.this.getContext())) {
                return false;
            }
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImageSelectorShowerView.AnonymousClass3.m266$r8$lambda$gDpxGV7yqiXpFQpwJj36lqQj6w(this.f$0, gifDrawable);
                }
            });
            return false;
        }

        @Override // com.bumptech.glide.request.RequestListener
        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, Target target, DataSource dataSource, boolean z) {
            return onResourceReady((Drawable) obj, obj2, (Target<Drawable>) target, dataSource, z);
        }
    }

    public static class DrawableWithAlpha {
        public Float mAlpha;
        public Drawable mDrawable;

        public DrawableWithAlpha(Drawable drawable, Float f) {
            this.mDrawable = drawable;
            this.mAlpha = f;
        }
    }

    public interface ImageLoadListener {
        void onFail();

        void onSucceed(Drawable drawable, boolean z);
    }

    /* JADX INFO: renamed from: $r8$lambda$6viuRK530ggJQZaO-HZ23pl6aGM, reason: not valid java name */
    public static /* synthetic */ void m260$r8$lambda$6viuRK530ggJQZaOHZ23pl6aGM(ImageSelectorShowerView imageSelectorShowerView, View view) {
        ImageSelectorCategoryInfo imageSelectorCategoryInfo = imageSelectorShowerView.mPicSelectCategoryInfo;
        imageSelectorCategoryInfo.setInvert(!imageSelectorCategoryInfo.isInvert());
        imageSelectorShowerView.loadBitmap(imageSelectorShowerView.mPicSelectCategoryInfo.getUriString(), true);
    }

    /* JADX INFO: renamed from: $r8$lambda$i3Rj-7vPbnnMQlus5oDHcPiC3yI, reason: not valid java name */
    public static /* synthetic */ ObservableSource m261$r8$lambda$i3Rj7vPbnnMQlus5oDHcPiC3yI(Observable observable) {
        return !Utils.isUiThread(Thread.currentThread()) ? observable : observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static /* synthetic */ void $r8$lambda$kPE6Iu8BjzYQC6KpsoudDkswhk8(ImageSelectorShowerView imageSelectorShowerView, boolean z, DrawableWithAlpha drawableWithAlpha) {
        imageSelectorShowerView.mSelectedImageUri = null;
        imageSelectorShowerView.mImageShowerContainer.setClickable(false);
        Drawable drawable = drawableWithAlpha.mDrawable;
        imageSelectorShowerView.mImageShowerIv.setAlpha(drawableWithAlpha.mAlpha.floatValue());
        if (imageSelectorShowerView.mSize != 1 || (drawable instanceof GifDrawable)) {
            imageSelectorShowerView.mImageShowerIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageSelectorShowerView.mImageShowerIv.setImageDrawable(drawable);
            boolean z2 = drawable instanceof GifDrawable;
            if (z2) {
                imageSelectorShowerView.mImageShowerIv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                final WakeLock wakeLockWrapForAnimation = WakeLock.wrapForAnimation(imageSelectorShowerView.getContext(), "GifWakeLock");
                wakeLockWrapForAnimation.acquire("GifWakeLock");
                if (((Boolean) ReflectUtil.getObjectField(ReflectUtil.getObjectField(drawable.getConstantState(), "frameLoader"), "isRunning", null)).booleanValue()) {
                    ((GifDrawable) drawable).start();
                } else {
                    ((GifDrawable) drawable).startFromFirstFrame();
                }
                ((GifDrawable) drawable).registerAnimationCallback(new Animatable2Compat.AnimationCallback() { // from class: com.miui.aod.components.view.ImageSelectorShowerView.2
                    @Override // androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
                    public void onAnimationEnd(Drawable drawable2) {
                        wakeLockWrapForAnimation.release("GifWakeLock");
                    }
                });
            }
            imageSelectorShowerView.mImageShowerIv.setVisibility(0);
            if (z2 && imageSelectorShowerView.mSize == 1) {
                imageSelectorShowerView.mImageShowerContainer.setOnClickListener(new ImageSelectorShowerView$$ExternalSyntheticLambda4(imageSelectorShowerView));
            }
        } else {
            imageSelectorShowerView.mImageShowerIv.setVisibility(8);
        }
        imageSelectorShowerView.mAddIcon.setVisibility(8);
        ImageLoadListener imageLoadListener = imageSelectorShowerView.mImageLoadListener;
        if (imageLoadListener != null) {
            imageLoadListener.onSucceed(drawable, z);
        }
        imageSelectorShowerView.updateInvertIcon(true);
    }

    public static /* synthetic */ void $r8$lambda$tA9TKkJPAmqqaeESnYhyGXQYHcg(ImageSelectorShowerView imageSelectorShowerView, Throwable th) {
        imageSelectorShowerView.updateInvertIcon(false);
        Log.i(TAG, "on error:", th);
    }

    public static /* synthetic */ boolean $r8$lambda$yviFCKBkBL1ZLpKKDlfdfgZyRI0(ImageSelectorShowerView imageSelectorShowerView, View view, MotionEvent motionEvent) {
        imageSelectorShowerView.getClass();
        if (motionEvent.getActionMasked() == 0) {
            imageSelectorShowerView.mInvertBtn.setAlpha(0.6f);
            return false;
        }
        if (motionEvent.getActionMasked() != 1) {
            return false;
        }
        imageSelectorShowerView.mInvertBtn.setAlpha(1.0f);
        return false;
    }

    public ImageSelectorShowerView(Context context) {
        super(context);
        this.mShowStyle = -1;
        this.fileExist = false;
    }

    public ImageSelectorShowerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShowStyle = -1;
        this.fileExist = false;
    }

    public ImageSelectorShowerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowStyle = -1;
        this.fileExist = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void choosePhoto(View view) {
        if (this.mInPreview) {
            return;
        }
        Utils.selectPicture(getContext());
    }

    private void getImageFile(String str) {
        int iLastIndexOf = str.lastIndexOf(File.separator) + 1;
        if (iLastIndexOf >= str.length()) {
            return;
        }
        this.file = new File(AODApplication.sysuiContext.getCacheDir().getAbsolutePath() + "/select_image/" + str.substring(iLastIndexOf));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Drawable getImageFutureDrawable(String str) throws ExecutionException, InterruptedException {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        Object objRandomUUID;
        if (this.mSize == 2) {
            dimensionPixelSize = (int) (Utils.getSmallViewWidth(getContext()) - this.mSmallWidthLess);
            dimensionPixelSize2 = dimensionPixelSize;
        } else {
            dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.image_selector_shower_width);
            dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.image_selector_shower_height);
        }
        Context context = AODApplication.sysuiContext;
        if (context == null) {
            context = AODApplication.sInstance;
        }
        RequestBuilder requestBuilderLoad = (Utils.isAodProcess() || DeviceUtil.INSTANCE.isGteAndroidW() || UserHandleUtils.USER_CURRENT == 0) ? Glide.with(context).load(this.file) : Glide.with(context).load(this.bitmap);
        try {
            RequestBuilder requestBuilderListener = requestBuilderLoad.listener(new AnonymousClass3(str));
            RequestOptions requestOptions = (RequestOptions) new RequestOptions().transform(new InvertBitmapTransformation(!TextUtils.equals(str, this.mPicSelectCategoryInfo.getCroppedUriString()) && this.mPicSelectCategoryInfo.isInvert()));
            if (this.file.exists()) {
                objRandomUUID = this.file.getAbsolutePath() + this.file.lastModified();
            } else {
                objRandomUUID = UUID.randomUUID();
            }
            return (Drawable) requestBuilderListener.apply(requestOptions.signature(new ObjectKey(objRandomUUID))).submit(dimensionPixelSize, dimensionPixelSize2).get();
        } catch (Exception unused) {
            ImageLoadListener imageLoadListener = this.mImageLoadListener;
            if (imageLoadListener == null) {
                return null;
            }
            imageLoadListener.onFail();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:26:0x009a  */
    public /* synthetic */ void lambda$loadBitmap$2(String str, Context context, ObservableEmitter observableEmitter) throws Throwable {
        float fGetAlphaFromBitmap;
        if (str.contains(Environment.getExternalStorageDirectory().getAbsolutePath())) {
            StringBuilder sb = new StringBuilder();
            sb.append(ImageSelectorCategoryInfo.getImageCacheDir(getContext()));
            String str2 = File.separator;
            sb.append(str2);
            sb.append("baked_file_");
            sb.append(str.substring(str.lastIndexOf(str2) + 1));
            String string = sb.toString();
            if (UserHandleUtils.USER_CURRENT == 0 || Utils.isAodProcess()) {
                File file = new File(string);
                if (file.exists()) {
                    file.delete();
                }
                FileUtils.copy(this.file, file);
                this.mPicSelectCategoryInfo.setUriString(string);
            }
        }
        Drawable imageFutureDrawable = getImageFutureDrawable(str);
        if (imageFutureDrawable == null) {
            return;
        }
        if (this.mSize == 3 && Utils.isNeedToGrayScale()) {
            Bitmap firstFrame = imageFutureDrawable instanceof GifDrawable ? ((GifDrawable) imageFutureDrawable).getFirstFrame() : BitmapUtils.getBitmapFromDrawable(imageFutureDrawable);
            if (firstFrame != null) {
                fGetAlphaFromBitmap = GetAlpha.GetAlphaFromBitmap(firstFrame);
            } else {
                fGetAlphaFromBitmap = 1.0f;
            }
        } else {
            fGetAlphaFromBitmap = 1.0f;
        }
        observableEmitter.onNext(new DrawableWithAlpha(imageFutureDrawable, Float.valueOf(fGetAlphaFromBitmap)));
        observableEmitter.onComplete();
    }

    private void loadBitmap(String str) {
        loadBitmap(str, false);
    }

    private void loadBitmap(final String str, final boolean z) {
        if (str != null) {
            try {
                final Context context = AODApplication.sysuiContext;
                if (context == null) {
                    context = AODApplication.sInstance;
                }
                if (Utils.isAodProcess()) {
                    this.file = new File(str);
                } else if (DeviceUtil.INSTANCE.isGteAndroidW()) {
                    getImageFile(str);
                } else if (UserHandleUtils.USER_CURRENT != 0) {
                    Bundle bundleCall = context.getContentResolver().call(ContentProviderUtils.maybeAddUserId(PartProvider.URI_PART, UserHandleUtils.USER_CURRENT), "provideFile", str, (Bundle) null);
                    this.file = (File) bundleCall.getSerializable("provideFile", File.class);
                    this.fileExist = bundleCall.getBoolean("fileExist");
                    this.bitmap = (Bitmap) bundleCall.getParcelable("bitmap", Bitmap.class);
                } else {
                    this.file = new File(str);
                }
                File file = this.file;
                if (file == null) {
                    Log.i(TAG, "loadBitmap file is null: " + str);
                    return;
                }
                if (!file.exists() && !this.fileExist) {
                    Log.i(TAG, "loadBitmap file is not exit: " + str);
                    return;
                }
                new CompositeDisposable().add(Observable.create(new ObservableOnSubscribe() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$$ExternalSyntheticLambda0
                    @Override // io.reactivex.rxjava3.core.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) throws Throwable {
                        this.f$0.lambda$loadBitmap$2(str, context, observableEmitter);
                    }
                }).compose(new ObservableTransformer() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$$ExternalSyntheticLambda1
                    @Override // io.reactivex.rxjava3.core.ObservableTransformer
                    public final ObservableSource apply(Observable observable) {
                        return ImageSelectorShowerView.m261$r8$lambda$i3Rj7vPbnnMQlus5oDHcPiC3yI(observable);
                    }
                }).subscribe(new Consumer() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$$ExternalSyntheticLambda2
                    @Override // io.reactivex.rxjava3.functions.Consumer
                    public final void accept(Object obj) {
                        ImageSelectorShowerView.$r8$lambda$kPE6Iu8BjzYQC6KpsoudDkswhk8(this.f$0, z, (ImageSelectorShowerView.DrawableWithAlpha) obj);
                    }
                }, new Consumer() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$$ExternalSyntheticLambda3
                    @Override // io.reactivex.rxjava3.functions.Consumer
                    public final void accept(Object obj) {
                        ImageSelectorShowerView.$r8$lambda$tA9TKkJPAmqqaeESnYhyGXQYHcg(this.f$0, (Throwable) obj);
                    }
                }));
            } catch (Exception e) {
                Log.e(TAG, "error...", e);
                this.mInvertBtn.setVisibility(8);
            }
        }
    }

    private void refreshShowerView(String str) {
        if (str != null) {
            loadBitmap(str);
        }
    }

    private void setNotificationIconDrawable(ImageView imageView, Drawable drawable) {
        if (imageView == null) {
            return;
        }
        if (drawable == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setImageDrawable(drawable);
        }
    }

    private void setupNotificationIcons(StyleInfo styleInfo) {
        if (this.mInflatedNotificationIcons == null) {
            return;
        }
        if (!styleInfo.isNotificationSwitchOn() || this.mSize != 1) {
            this.mInflatedNotificationIcons.setVisibility(8);
            return;
        }
        setNotificationIconDrawable((ImageView) this.mInflatedNotificationIcons.findViewById(R.id.first), NotificationUtils.getAppIconByPackageName(getContext(), "com.android.contacts", "com.android.contacts.activities.TwelveKeyDialer"));
        setNotificationIconDrawable((ImageView) this.mInflatedNotificationIcons.findViewById(R.id.second), NotificationUtils.getAppIconByPackageName(getContext(), "com.android.mms", ""));
        setNotificationIconDrawable((ImageView) this.mInflatedNotificationIcons.findViewById(R.id.third), NotificationUtils.getAppIconByPackageName(getContext(), NotificationUtils.MAIL_ICON_PKG, ""));
        ((ImageView) this.mInflatedNotificationIcons.findViewById(R.id.forth)).setVisibility(8);
        this.mInflatedNotificationIcons.setVisibility(0);
    }

    private void updateInvertIcon(boolean z) {
        if (!z || this.mSize != 1 || this.mInPreview) {
            this.mInvertBtn.setVisibility(8);
            return;
        }
        this.mInvertBtn.setVisibility(0);
        this.mInvertBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), this.mPicSelectCategoryInfo.isInvert() ? R.drawable.ic_invert_on : R.drawable.ic_invert));
    }

    public void enterPreviewMode(boolean z) {
        this.mInPreview = z;
        if (z) {
            this.mImageShowerContainer.setForeground(null);
            this.mInvertBtn.setVisibility(8);
        } else {
            this.mImageShowerContainer.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.image_selector_shower_container_fg));
            updateInvertIcon(true);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mImageShowerContainer = (SquareFrameLayout) findViewById(R.id.image_shower_container);
        this.mImageShowerIv = (ImageView) findViewById(R.id.image_shower);
        this.mImageShowerContainer.setClickable(true);
        this.mImageShowerContainer.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.aod.components.view.ImageSelectorShowerView.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(@NonNull View view, @NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setClickable(true);
                accessibilityNodeInfo.setContentDescription(ImageSelectorShowerView.this.mImageShowerContainer.getContentDescription());
                accessibilityNodeInfo.setCheckable(false);
                accessibilityNodeInfo.addAction(16);
            }
        });
        this.mAddIcon = findViewById(R.id.add_icon);
        ImageView imageView = (ImageView) findViewById(R.id.invert_btn);
        this.mInvertBtn = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ImageSelectorShowerView.m260$r8$lambda$6viuRK530ggJQZaOHZ23pl6aGM(this.f$0, view);
            }
        });
        this.mInvertBtn.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.aod.components.view.ImageSelectorShowerView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return ImageSelectorShowerView.$r8$lambda$yviFCKBkBL1ZLpKKDlfdfgZyRI0(this.f$0, view, motionEvent);
            }
        });
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.image_selector_header_timestyle, (ViewGroup) this, false);
        this.mHeaderLL = linearLayout;
        this.mGradientLayout = (GradientLinearLayout) linearLayout.findViewById(R.id.gradient_layout);
        this.mDateView = (TextView) this.mHeaderLL.findViewById(R.id.date);
        TextView textView = (TextView) this.mHeaderLL.findViewById(R.id.date_lunar);
        this.mDateLunar = textView;
        textView.setAlpha(0.6f);
        this.mClockHorizontal = (TextView) this.mHeaderLL.findViewById(R.id.clock_horizontal);
        this.mClockHorizontalContainer = this.mHeaderLL.findViewById(R.id.clock_horizontal_container);
        this.mHeaderLL.findViewById(R.id.battery_step_container).setAlpha(0.6f);
        this.mInflatedNotificationIcons = this.mHeaderLL.findViewById(R.id.icons);
        this.mBatteryContainer = this.mHeaderLL.findViewById(R.id.battery_container);
        View viewFindViewById = this.mHeaderLL.findViewById(R.id.data_dual_container);
        this.mDateDualContainer = viewFindViewById;
        if (viewFindViewById != null) {
            this.mDateDual = (TextView) viewFindViewById.findViewById(R.id.date_dual);
            this.mCity2 = (TextView) this.mDateDualContainer.findViewById(R.id.city2);
        }
        this.mSmallWidthLess = (int) (Utils.getImageShowStrokeWidth() * 2.0f);
    }

    @Override // com.miui.aod.settings.AodStyleSelectActivity.PhotoAlbumPickerListener
    public /* bridge */ /* synthetic */ void onImageSelectCancel() {
        super.onImageSelectCancel();
    }

    @Override // com.miui.aod.settings.AodStyleSelectActivity.PhotoAlbumPickerListener
    public void onImageSelected(String str) {
        this.mImageShowerIv.setImageBitmap(null);
        this.mImageShowerContainer.setContentDescription(getContext().getString(R.string.change_picture));
        this.mPicSelectCategoryInfo.setInvert(false);
        this.mSelectedImageUri = str;
        this.mImageShowerIv.requestFocus();
        refreshShowerView(str);
    }

    public void setImageLoadListener(ImageLoadListener imageLoadListener) {
        this.mImageLoadListener = imageLoadListener;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        if (styleInfo instanceof ImageSelectorCategoryInfo) {
            this.mSize = i;
            this.mPicSelectCategoryInfo = (ImageSelectorCategoryInfo) styleInfo;
            updatePosition();
            if (TextUtils.isEmpty(this.mPicSelectCategoryInfo.getUriString()) && TextUtils.isEmpty(this.mPicSelectCategoryInfo.getCroppedUriString())) {
                this.mInvertBtn.setVisibility(8);
                this.mAddIcon.setVisibility(0);
                this.mImageShowerContainer.setOnClickListener(new ImageSelectorShowerView$$ExternalSyntheticLambda4(this));
            } else {
                refreshShowerView((this.mSize == 1 || TextUtils.isEmpty(this.mPicSelectCategoryInfo.getCroppedUriString())) ? this.mPicSelectCategoryInfo.getUriString() : this.mPicSelectCategoryInfo.getCroppedUriString());
            }
            if (this.mSize != 1) {
                this.mImageShowerContainer.setForeground(null);
                this.mInvertBtn.setVisibility(8);
            }
        }
    }

    public void updatePosition() {
        if (this.mShowStyle != this.mPicSelectCategoryInfo.getExtraInfoShowStyle()) {
            this.mShowStyle = this.mPicSelectCategoryInfo.getExtraInfoShowStyle();
            removeView(this.mHeaderLL);
            if (this.mSize != 2) {
                new LinearLayout.LayoutParams(-2, -2).gravity = 1;
                if (this.mPicSelectCategoryInfo.getExtraInfoShowStyle() != 0) {
                    addView(this.mHeaderLL, this.mPicSelectCategoryInfo.getExtraInfoShowStyle() - 1);
                } else {
                    addView(this.mHeaderLL, 1);
                }
                this.mDateLunar.setVisibility((this.mShowStyle == 0 || !this.mPicSelectCategoryInfo.isLunarSwitchOn(getContext())) ? 8 : 0);
                this.mClockHorizontalContainer.setVisibility(this.mShowStyle == 0 ? 8 : 0);
                View view = this.mDateDualContainer;
                if (view != null) {
                    view.setVisibility((this.mShowStyle == 0 || !AODSettings.isDualClock(getContext())) ? 8 : 0);
                }
                this.mBatteryContainer.setVisibility(this.mPicSelectCategoryInfo.isBatterySwitchOn() ? 0 : 8);
                setupNotificationIcons(this.mPicSelectCategoryInfo);
                return;
            }
            int smallViewWidth = Utils.getSmallViewWidth(getContext());
            setOrientation(0);
            float f = smallViewWidth;
            float f2 = this.mSmallWidthLess;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (f - f2), (int) (f - f2));
            layoutParams.gravity = 16;
            if (this.mPicSelectCategoryInfo.isForAodNotification()) {
                int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.aod_notification_clock_margin);
                if (Utils.inLargeScreen(getContext())) {
                    dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.aod_notification_clock_margin_full);
                }
                ((ViewGroup) this.mImageShowerContainer.getParent()).setPadding(0, (((WindowUtils.INSTANCE.getWidthPixel(getContext()) - (dimensionPixelSize * 2)) - smallViewWidth) / 2) - getContext().getResources().getDimensionPixelSize(R.dimen.clock_container_margin_top_small), 0, 0);
            }
            this.mImageShowerContainer.setLayoutParams(layoutParams);
            this.mImageShowerContainer.setIsSquare(true);
            this.mImageShowerIv.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        }
    }
}
