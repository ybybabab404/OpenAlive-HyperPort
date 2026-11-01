package com.miui.maml.elements.pag;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.miui.maml.ResourceLoader;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.elements.BitmapProvider;
import com.miui.maml.elements.ViewHolderScreenElement;
import com.miui.maml.util.AssetsResourceLoader;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.TextFormatter;
import com.xiaomi.onetrack.util.a;
import java.io.File;
import miuix.core.util.IOUtils;
import org.libpag.PAGFile;
import org.libpag.PAGImage;
import org.libpag.PAGImageView;
import org.libpag.PAGText;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class PagImageElement extends ViewHolderScreenElement {
    public static final String LOG_TAG = "PagImageElement";
    public static final String TAG_NAME = "PagImageView";
    private boolean mAutoPlay;
    protected BitmapProvider.VersionedBitmap mBitmap;
    private BitmapProvider mBitmapProvider;
    protected BitmapProvider.VersionedBitmap mCurrentBitmap;
    private int mLoop;
    private PAGFile mPagFile;
    private MamlPagImageView mPagView;
    private String mPath;
    private IndexedVariable mProgressProperty;
    private int mScaleMode;
    private boolean mSetPath;
    private TextFormatter mSrcFormatter;

    public class PagAsyncTask extends AsyncTask<Void, Void, String> {
        public PagAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Void... voidArr) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                byte[] byteArray = IOUtils.toByteArray(PagImageElement.this.getContext().mResourceManager.getInputStream(PagImageElement.this.mPath));
                PagImageElement.this.mPagFile = PAGFile.Load(byteArray);
                MamlLog.w(PagImageElement.LOG_TAG, "file size: " + byteArray.length + " loading timed " + (System.currentTimeMillis() - jCurrentTimeMillis) + " path: " + PagImageElement.this.mPath);
                return null;
            } catch (Exception e) {
                MamlLog.w(PagImageElement.LOG_TAG, "READ ERROR: " + e.getMessage());
                return null;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (PagImageElement.this.mPagFile != null) {
                PagImageElement.this.mPagView.setComposition(PagImageElement.this.mPagFile);
                if (PagImageElement.this.mAutoPlay) {
                    PagImageElement.this.mPagView.play();
                }
            }
        }
    }

    public PagImageElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mPath = "";
        this.mBitmap = new BitmapProvider.VersionedBitmap(null);
        this.mCurrentBitmap = new BitmapProvider.VersionedBitmap(null);
        load(element);
    }

    private BitmapProvider.VersionedBitmap getBitmap(boolean z, String str, int i, int i2) {
        if (this.mBitmap.getBitmap() != null) {
            return this.mBitmap;
        }
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            return bitmapProvider.getBitmap(str, z, i, i2);
        }
        return null;
    }

    private /* synthetic */ void lambda$loadPagFile$0(PAGFile pAGFile) {
        if (this.mAutoPlay) {
            this.mPagView.play();
        }
    }

    private void load(Element element) {
        if (!TextUtils.isEmpty(this.mName)) {
            this.mProgressProperty = new IndexedVariable(this.mName + ".progress", getVariables(), true);
        }
        MamlPagImageView mamlPagImageView = new MamlPagImageView(this.mRoot.getContext().mContext);
        this.mPagView = mamlPagImageView;
        mamlPagImageView.setScreenElementRoot(this.mRoot);
        this.mPagView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mPagView.setBackground(null);
        this.mLoop = getAttrAsInt(element, "loop", -1);
        this.mAutoPlay = Boolean.parseBoolean(getAttr(element, "autoPlay"));
        this.mSetPath = Boolean.parseBoolean(getAttr(element, "setPath"));
        int attrAsInt = getAttrAsInt(element, "scaleMode", 2);
        this.mScaleMode = attrAsInt;
        this.mPagView.setScaleMode(attrAsInt);
        Variables variables = getVariables();
        this.mSrcFormatter = new TextFormatter(variables, element.getAttribute("src"), Expression.build(variables, element.getAttribute("srcExp")));
        this.mPagView.addListener(new PAGImageView.PAGImageViewListener() { // from class: com.miui.maml.elements.pag.PagImageElement.1
            public void onAnimationCancel(PAGImageView pAGImageView) {
            }

            public void onAnimationEnd(PAGImageView pAGImageView) {
                if (PagImageElement.this.mProgressProperty != null) {
                    PagImageElement.this.mProgressProperty.set(1.0d);
                }
                PagImageElement.this.performAction("complete");
            }

            public void onAnimationRepeat(PAGImageView pAGImageView) {
                if (PagImageElement.this.mProgressProperty != null) {
                    PagImageElement.this.mProgressProperty.set(1.0d);
                }
                PagImageElement.this.performAction("loopComplete");
            }

            public void onAnimationStart(PAGImageView pAGImageView) {
            }

            public void onAnimationUpdate(PAGImageView pAGImageView) {
                if (PagImageElement.this.mProgressProperty != null && PagImageElement.this.mPagFile != null) {
                    PagImageElement.this.mProgressProperty.set(PagImageElement.this.mPagFile.getProgress());
                }
                PagImageElement.this.performAction("update");
            }
        });
        this.mPagView.setRepeatCount(this.mLoop);
    }

    private void loadPagFile() {
        String path;
        if (this.mSetPath) {
            ResourceLoader resourceLoader = getContext().mResourceManager.getResourceLoader();
            if (resourceLoader instanceof AssetsResourceLoader) {
                path = PAGFile.Load(getContext().mContext.getAssets(), ((AssetsResourceLoader) resourceLoader).getAssetsPath(this.mPath)).path();
            } else {
                File extraFile = getContext().mResourceManager.getExtraFile(this.mPath);
                path = (extraFile == null || !extraFile.exists() || extraFile.length() <= 0) ? "" : extraFile.getPath();
            }
            this.mPagView.setPathAsync(path, new PAGFile.LoadListener() { // from class: com.miui.maml.elements.pag.PagImageElement$$ExternalSyntheticLambda0
            });
        } else {
            new PagAsyncTask().execute(new Void[0]);
        }
        MamlLog.w(LOG_TAG, "mSetPath " + this.mSetPath + " path " + this.mPath);
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void doTick(long j) {
        super.doTick(j);
        TextFormatter textFormatter = this.mSrcFormatter;
        String text = textFormatter != null ? textFormatter.getText() : null;
        if (TextUtils.isEmpty(text) || text.equals(this.mPath)) {
            return;
        }
        this.mPath = text;
        loadPagFile();
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            bitmapProvider.finish();
        }
        this.mBitmap.reset();
        this.mCurrentBitmap.reset();
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement
    public View getView() {
        return this.mPagView;
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement, com.miui.maml.elements.ElementGroupRC, com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement
    public void onViewAdded(View view) {
        super.onViewAdded(view);
    }

    @Override // com.miui.maml.elements.ViewHolderScreenElement
    public void onViewRemoved(View view) {
        PAGFile pAGFile;
        super.onViewRemoved(view);
        if (this.mPagView == null || (pAGFile = this.mPagFile) == null) {
            return;
        }
        pAGFile.removeAllLayers();
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void pause() {
        MamlLog.d(LOG_TAG, "pause");
        pausePagAnimation();
    }

    public void pausePagAnimation() {
        MamlPagImageView mamlPagImageView = this.mPagView;
        if (mamlPagImageView != null) {
            mamlPagImageView.pause();
        }
    }

    public void playPagAnimation() {
        MamlPagImageView mamlPagImageView = this.mPagView;
        if (mamlPagImageView != null) {
            mamlPagImageView.play();
        }
    }

    public void releasePagAnimation() {
    }

    public void replaceImage(PagImageModel pagImageModel) {
        PAGFile pAGFile;
        if (pagImageModel == null || (pAGFile = this.mPagFile) == null) {
            return;
        }
        int i = pagImageModel.index;
        int iNumImages = pAGFile.numImages();
        if (i < 0 || iNumImages <= 0 || i >= iNumImages) {
            return;
        }
        String str = pagImageModel.src;
        String str2 = pagImageModel.srcType;
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        BitmapProvider bitmapProviderCreate = BitmapProvider.create(this.mRoot, str2);
        this.mBitmapProvider = bitmapProviderCreate;
        bitmapProviderCreate.init(str);
        double d = pagImageModel.width;
        int iScale = d > -1.0d ? (int) scale(d) : -1;
        double d2 = pagImageModel.height;
        int iScale2 = d2 > -1.0d ? (int) scale(d2) : -1;
        this.mCurrentBitmap.set(getBitmap(true, str, iScale, iScale2));
        Bitmap bitmap = this.mCurrentBitmap.getBitmap();
        if (bitmap == null) {
            return;
        }
        if (iScale > 0 && iScale2 > 0) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, iScale, iScale2);
        }
        this.mPagFile.replaceImage(i, PAGImage.FromBitmap(bitmap));
    }

    public void replaceText(PagTextModel pagTextModel) {
        PAGFile pAGFile;
        if (pagTextModel == null || (pAGFile = this.mPagFile) == null) {
            return;
        }
        int i = pagTextModel.index;
        int iNumTexts = pAGFile.numTexts();
        if (i < 0 || iNumTexts <= 0 || i >= iNumTexts) {
            return;
        }
        PAGText textData = this.mPagFile.getTextData(i);
        String str = pagTextModel.newText;
        if (str != null) {
            textData.text = str;
        }
        float f = pagTextModel.fontSize;
        if (f > -1.0f) {
            textData.fontSize = f;
        }
        String str2 = pagTextModel.fontFamily;
        if (str2 != null) {
            textData.fontFamily = str2;
        }
        if (pagTextModel.fillColorExp != null) {
            textData.fillColor = pagTextModel.fillColor;
        }
        if (pagTextModel.strokeColorExp != null) {
            textData.strokeColor = pagTextModel.strokeColor;
        }
        float f2 = pagTextModel.strokeWidth;
        if (f2 > -1.0f) {
            textData.strokeWidth = f2;
        }
        if (pagTextModel.backgroundColorExp != null) {
            textData.backgroundColor = pagTextModel.backgroundColor;
        }
        String str3 = pagTextModel.fauxBold;
        if (str3 != null) {
            textData.fauxBold = a.i.equalsIgnoreCase(str3);
        }
        String str4 = pagTextModel.fauxItalic;
        if (str4 != null) {
            textData.fauxItalic = a.i.equalsIgnoreCase(str4);
        }
        this.mPagFile.replaceText(i, textData);
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        BitmapProvider bitmapProvider = this.mBitmapProvider;
        if (bitmapProvider != null) {
            bitmapProvider.reset();
        }
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.ScreenElement
    public void resume() {
        super.resume();
        if (this.mAutoPlay) {
            playPagAnimation();
        }
    }

    public void resumePagAnimation() {
        MamlPagImageView mamlPagImageView = this.mPagView;
        if (mamlPagImageView != null) {
            mamlPagImageView.play();
        }
    }

    public void setLoopCount(int i) {
        MamlPagImageView mamlPagImageView = this.mPagView;
        if (mamlPagImageView != null) {
            mamlPagImageView.setRepeatCount(i);
        }
    }

    public void setProgress(float f) {
        MamlPagImageView mamlPagImageView = this.mPagView;
        if (mamlPagImageView != null) {
            this.mPagView.setCurrentFrame(PagUtils.ProgressToFrame(f, mamlPagImageView.numFrames()));
        }
    }
}
