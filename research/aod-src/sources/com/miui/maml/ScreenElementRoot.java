package com.miui.maml;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.animation.BaseAnimation;
import com.miui.maml.compat.AwesomeClockCompat;
import com.miui.maml.component.MamlSurface;
import com.miui.maml.component.MamlView;
import com.miui.maml.data.ConfigChangeVariableUpdater;
import com.miui.maml.data.DarkModeVariableUpdater;
import com.miui.maml.data.DateTimeVariableUpdater;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.VariableBinder;
import com.miui.maml.data.VariableBinderManager;
import com.miui.maml.data.VariableNames;
import com.miui.maml.data.VariableUpdaterManager;
import com.miui.maml.data.Variables;
import com.miui.maml.elements.AnimatedScreenElement;
import com.miui.maml.elements.ElementGroup;
import com.miui.maml.elements.ElementGroupRC;
import com.miui.maml.elements.FramerateController;
import com.miui.maml.elements.ITicker;
import com.miui.maml.elements.ScreenElement;
import com.miui.maml.elements.ScreenElementVisitor;
import com.miui.maml.util.ConfigFile;
import com.miui.maml.util.DeviceUtils;
import com.miui.maml.util.HideSdkDependencyUtils;
import com.miui.maml.util.MamlAccessHelper;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.MamlViewManager;
import com.miui.maml.util.SystemProperties;
import com.miui.maml.util.Task;
import com.miui.maml.util.Utils;
import com.xiaomi.onetrack.util.ac;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes2.dex */
public class ScreenElementRoot extends ScreenElement implements InteractiveListener {
    private static final boolean CALCULATE_FRAME_RATE = true;
    public static final int CAPABILITY_ALL = -1;
    public static final int CAPABILITY_CREATE_OBJ = 4;
    public static final int CAPABILITY_VAR_PERSISTENCE = 2;
    public static final int CAPABILITY_WEBSERVICE = 1;
    private static final int DARK_MODE_COLOR = 419430400;
    private static final int DEFAULT_RES_DENSITY = 240;
    private static final int DEFAULT_SCREEN_WIDTH = 480;
    private static final String EXTERNAL_COMMANDS_TAG_NAME = "ExternalCommands";
    private static final String HYPER_OS_VERSION_CODE = "ro.mi.os.version.code";
    private static final String HYPER_OS_VERSION_NAME = "ro.mi.os.version.name";
    private static final String LOG_TAG = "ScreenElementRoot";
    private static final int MAML_INTERNAL_VERSION = Integer.parseInt(BuildConfig.mamlVersionCode);
    private static final String MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String RAW_DENSITY = "__raw_density";
    private static final String ROOT_NAME = "__root";
    private static final String SCALE_FACTOR = "__scale_factor";
    private static final String THEMEMANAGER_PACKAGE_NAME = "com.android.thememanager";
    private static final String VARIABLE_VIEW_HEIGHT = "view_height";
    private static final String VARIABLE_VIEW_WIDTH = "view_width";
    private static final String VAR_MAML_VERSION = "__maml_version";
    protected float DEFAULT_FRAME_RATE;
    private boolean isIconLoad;
    private List<AnimatedScreenElement> mAccessibleElements;
    private boolean mAllowScreenRotation;
    private ArrayMap<String, ArrayList<BaseAnimation.AnimationItem>> mAnimationItems;
    private boolean mAutoDarkenWallpaper;
    private boolean mBlurWindow;
    private String mCacheDir;
    private int mCapability;
    private long mCheckPoint;
    private boolean mClearCanvas;
    private ConfigFile mConfig;
    private String mConfigPath;
    private Configuration mConfiguration;

    @NonNull
    protected final ScreenContext mContext;
    protected RendererController mController;
    private String mCornerCutType;
    private boolean mDarkWallpaperMode;
    private int mDefaultResourceDensity;
    private int mDefaultScreenWidth;
    private boolean mDisableCutRoundCorner;
    private ArrayMap<String, WeakReference<ScreenElement>> mElements;
    private WeakReference<OnExternCommandListener> mExternCommandListener;
    private CommandTriggers mExternalCommandManager;
    private boolean mFinished;
    private float mFontScale;
    protected float mFrameRate;
    private IndexedVariable mFrameRateVar;
    private FramerateHelper mFramerateHelper;
    private int mFrames;
    private float mHeight;
    private WeakReference<OnHoverChangeListener> mHoverChangeListenerRef;
    private AnimatedScreenElement mHoverElement;
    private Matrix mHoverMatrix;
    protected ElementGroup mInnerGroup;
    private boolean mKeepResource;
    private boolean mLoaded;
    private MamlAccessHelper mMamlAccessHelper;
    private WeakReference<OnExternCommandListener> mMamlSurfaceExternCommandListener;
    private WeakReference<MamlSurface> mMamlSurfaceRef;
    private MamlView.Config mMamlViewConfig;
    private WeakReference<OnExternCommandListener> mMamlViewExternCommandListener;
    private boolean mNeedDisallowInterceptTouchEvent;
    private IndexedVariable mNeedDisallowInterceptTouchEventVar;
    private boolean mNeedFolmeClean;
    private boolean mNeedReset;
    private ArrayList<ITicker> mPreTickers;
    protected HashMap<String, String> mRawAttrs;
    private int mRawDefaultResourceDensity;
    private int mRawHeight;
    private int mRawTargetDensity;
    private int mRawWidth;
    private final Object mRenderMutex;
    private ArrayList<RendererController> mRendererControllers;
    private float mResizeScale;
    private String mRootTag;
    private boolean mSaveConfigOnlyInPause;
    private boolean mSaveConfigViaProvider;
    private float mScale;
    private boolean mScaleByDensity;
    private boolean mSelfRender;
    public boolean mShowDebugLayout;
    private boolean mShowFramerate;
    private SoundManager mSoundManager;
    private StylesManager mStylesManager;
    private boolean mSupportAccessibilityService;
    private boolean mSupportBackgroundBlur;
    private OnExternCommandListener mSystemExternCommandListener;
    private int mTargetDensity;
    protected int mTargetScreenHeight;
    protected int mTargetScreenWidth;
    private IndexedVariable mTouchBeginTime;
    private IndexedVariable mTouchBeginX;
    private IndexedVariable mTouchBeginY;
    private IndexedVariable mTouchX;
    private IndexedVariable mTouchY;
    private boolean mTouchable;
    private boolean mTransparentSurface;
    private boolean mUseCustomizedDarkModeWallpaper;
    private boolean mUseHardwareCanvas;
    protected VariableBinderManager mVariableBinderManager;
    private VariableUpdaterManager mVariableUpdaterManager;
    private int mVersion;
    private WeakReference<MamlViewManager> mViewManagerRef;
    private String mWidgetType;
    private float mWidth;

    /* JADX INFO: renamed from: com.miui.maml.ScreenElementRoot$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ScreenElementRoot$ExtraResource$MetricsType;

        static {
            int[] iArr = new int[ExtraResource.MetricsType.values().length];
            $SwitchMap$com$miui$maml$ScreenElementRoot$ExtraResource$MetricsType = iArr;
            try {
                iArr[ExtraResource.MetricsType.DEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$ScreenElementRoot$ExtraResource$MetricsType[ExtraResource.MetricsType.SW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$ScreenElementRoot$ExtraResource$MetricsType[ExtraResource.MetricsType.SW_DEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static class ExtraResource {
        private ArrayList<ScaleMetrics> mResources = new ArrayList<>();
        private ArrayList<ScaleMetrics> mScales = new ArrayList<>();

        public enum MetricsType {
            DEN,
            SW,
            SW_DEN
        }

        public class Resource extends ScaleMetrics {
            String mPath;

            public Resource() {
                super();
            }

            public Resource(String str, MetricsType metricsType) {
                super(str, metricsType);
                int i = AnonymousClass4.$SwitchMap$com$miui$maml$ScreenElementRoot$ExtraResource$MetricsType[metricsType.ordinal()];
                if (i == 1) {
                    this.mPath = "den" + this.mDensity;
                    return;
                }
                if (i != 2) {
                    return;
                }
                this.mPath = "sw" + this.mScreenWidth;
            }

            @Override // com.miui.maml.ScreenElementRoot.ExtraResource.ScaleMetrics
            public void onParseInfo(String[] strArr) {
                this.mPath = strArr[strArr.length <= 2 ? (char) 0 : (char) 1];
            }

            @Override // com.miui.maml.ScreenElementRoot.ExtraResource.ScaleMetrics
            public String toString() {
                return super.toString() + " path:" + this.mPath;
            }
        }

        public class ScaleMetrics {
            int mDensity;
            int mScreenWidth;
            int mSizeType;
            float scale;

            public ScaleMetrics() {
                this.scale = 1.0f;
            }

            public ScaleMetrics(String str, MetricsType metricsType) {
                this.scale = 1.0f;
                try {
                    String[] strArrSplit = str.split(":");
                    int i = AnonymousClass4.$SwitchMap$com$miui$maml$ScreenElementRoot$ExtraResource$MetricsType[metricsType.ordinal()];
                    char c = 1;
                    if (i == 1) {
                        int i2 = Integer.parseInt(strArrSplit[0]);
                        this.mDensity = i2;
                        this.mScreenWidth = (ResourceManager.translateDensity(i2) * ScreenElementRoot.DEFAULT_SCREEN_WIDTH) / ScreenElementRoot.DEFAULT_RES_DENSITY;
                        if (strArrSplit.length > 1) {
                            this.scale = Float.parseFloat(strArrSplit[1]);
                            return;
                        }
                        return;
                    }
                    if (i == 2) {
                        int i3 = Integer.parseInt(strArrSplit[0]);
                        this.mScreenWidth = i3;
                        this.mDensity = ResourceManager.retranslateDensity((i3 * ScreenElementRoot.DEFAULT_RES_DENSITY) / ScreenElementRoot.DEFAULT_SCREEN_WIDTH);
                        if (strArrSplit.length > 1) {
                            this.scale = Float.parseFloat(strArrSplit[1]);
                            return;
                        }
                        return;
                    }
                    if (i != 3) {
                        return;
                    }
                    String[] strArrSplit2 = strArrSplit[0].split("-");
                    this.mSizeType = 0;
                    if (strArrSplit2.length == 1) {
                        if (strArrSplit2[0].startsWith("sw")) {
                            int i4 = Integer.parseInt(strArrSplit2[0].substring(2));
                            this.mScreenWidth = i4;
                            this.mDensity = ResourceManager.retranslateDensity((i4 * ScreenElementRoot.DEFAULT_RES_DENSITY) / ScreenElementRoot.DEFAULT_SCREEN_WIDTH);
                        } else {
                            if (!strArrSplit2[0].startsWith("den")) {
                                throw new IllegalArgumentException("invalid format: " + str);
                            }
                            int i5 = Integer.parseInt(strArrSplit2[0].substring(3));
                            this.mDensity = i5;
                            this.mScreenWidth = (ResourceManager.translateDensity(i5) * ScreenElementRoot.DEFAULT_SCREEN_WIDTH) / ScreenElementRoot.DEFAULT_RES_DENSITY;
                        }
                    } else {
                        if (strArrSplit2.length < 2) {
                            throw new IllegalArgumentException("invalid format: " + str);
                        }
                        this.mScreenWidth = Integer.parseInt(strArrSplit2[0].substring(2));
                        this.mDensity = Integer.parseInt(strArrSplit2[1].substring(3));
                        if (strArrSplit2.length == 3) {
                            this.mSizeType = ExtraResource.parseSizeType(strArrSplit2[2]);
                        }
                    }
                    if (strArrSplit.length > 1) {
                        if (strArrSplit.length != 2) {
                            c = 2;
                        }
                        this.scale = Float.parseFloat(strArrSplit[c]);
                    }
                    onParseInfo(strArrSplit);
                } catch (NumberFormatException unused) {
                    MamlLog.w(ScreenElementRoot.LOG_TAG, "format error of string: " + str);
                    throw new IllegalArgumentException("invalid format");
                }
            }

            public void onParseInfo(String[] strArr) {
            }

            public String toString() {
                return "ScaleMetrics sw:" + this.mScreenWidth + " den:" + this.mDensity + " sizeType:" + this.mSizeType + " scale:" + this.scale;
            }
        }

        public ExtraResource(Element element, int i) {
            Resource resource = new Resource();
            resource.mDensity = i;
            resource.mScreenWidth = (ResourceManager.translateDensity(i) * ScreenElementRoot.DEFAULT_SCREEN_WIDTH) / ScreenElementRoot.DEFAULT_RES_DENSITY;
            resource.mSizeType = 0;
            resource.mPath = null;
            resource.scale = 1.0f;
            this.mResources.add(resource);
            ArrayList<ScaleMetrics> arrayList = this.mResources;
            String attribute = element.getAttribute("extraResourcesDensity");
            MetricsType metricsType = MetricsType.DEN;
            inflateMetrics(arrayList, attribute, metricsType);
            ArrayList<ScaleMetrics> arrayList2 = this.mResources;
            String attribute2 = element.getAttribute("extraResourcesScreenWidth");
            MetricsType metricsType2 = MetricsType.SW;
            inflateMetrics(arrayList2, attribute2, metricsType2);
            ArrayList<ScaleMetrics> arrayList3 = this.mResources;
            String attribute3 = element.getAttribute("extraResources");
            MetricsType metricsType3 = MetricsType.SW_DEN;
            inflateMetrics(arrayList3, attribute3, metricsType3);
            ScaleMetrics scaleMetrics = new ScaleMetrics();
            scaleMetrics.mDensity = i;
            scaleMetrics.mScreenWidth = (ResourceManager.translateDensity(i) * ScreenElementRoot.DEFAULT_SCREEN_WIDTH) / ScreenElementRoot.DEFAULT_RES_DENSITY;
            scaleMetrics.mSizeType = 0;
            scaleMetrics.scale = -1.0f;
            this.mScales.add(scaleMetrics);
            inflateMetrics(this.mScales, element.getAttribute("extraScaleByDensity"), metricsType);
            inflateMetrics(this.mScales, element.getAttribute("extraScaleByScreenWidth"), metricsType2);
            inflateMetrics(this.mScales, element.getAttribute("extraScales"), metricsType3);
        }

        private void inflateMetrics(ArrayList<ScaleMetrics> arrayList, String str, MetricsType metricsType) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            for (String str2 : str.split(ac.b)) {
                try {
                    if (arrayList == this.mResources) {
                        arrayList.add(new Resource(str2.trim(), metricsType));
                    } else if (arrayList == this.mScales) {
                        arrayList.add(new ScaleMetrics(str2.trim(), metricsType));
                    }
                } catch (IllegalArgumentException unused) {
                    MamlLog.w(ScreenElementRoot.LOG_TAG, "format error of attribute: " + str);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int parseSizeType(String str) {
            if (CategoryConstants.CATEGORY_MAML_SMALL.equals(str)) {
                return 1;
            }
            if ("normal".equals(str)) {
                return 2;
            }
            if ("large".equals(str)) {
                return 3;
            }
            return "xlarge".equals(str) ? 4 : 0;
        }

        public ScaleMetrics findMetrics(int i, int i2, int i3, ArrayList<ScaleMetrics> arrayList) {
            ArrayList<ScaleMetrics> arrayList2 = new ArrayList();
            int i4 = Integer.MAX_VALUE;
            int i5 = Integer.MAX_VALUE;
            for (ScaleMetrics scaleMetrics : arrayList) {
                int i6 = scaleMetrics.mSizeType;
                if (i6 == 0 || i6 == i3) {
                    int iAbs = Math.abs(i - scaleMetrics.mDensity);
                    if (iAbs < i4) {
                        int iAbs2 = Math.abs(i2 - scaleMetrics.mScreenWidth);
                        arrayList2.clear();
                        arrayList2.add(scaleMetrics);
                        i5 = iAbs2;
                        i4 = iAbs;
                    } else if (iAbs == i4) {
                        int iAbs3 = Math.abs(i2 - scaleMetrics.mScreenWidth);
                        if (iAbs3 < i5) {
                            arrayList2.clear();
                            arrayList2.add(scaleMetrics);
                            i5 = iAbs3;
                        } else if (iAbs3 == i5) {
                            arrayList2.add(scaleMetrics);
                        }
                    }
                }
            }
            ScaleMetrics scaleMetrics2 = null;
            for (ScaleMetrics scaleMetrics3 : arrayList2) {
                int i7 = scaleMetrics3.mSizeType;
                if (i7 == i3) {
                    return scaleMetrics3;
                }
                if (i7 == 0) {
                    scaleMetrics2 = scaleMetrics3;
                }
            }
            return scaleMetrics2;
        }

        public Resource findResource(int i, int i2, int i3) {
            return (Resource) findMetrics(i, i2, i3, this.mResources);
        }

        public ScaleMetrics findScale(int i, int i2, int i3) {
            return findMetrics(i, i2, i3, this.mScales);
        }
    }

    public static class FramerateHelper {
        private String mFramerateText;
        private TextPaint mPaint;
        private int mRealFrameRate;
        private int mShowingFramerate;
        private int mTextX;
        private int mTextY;

        public FramerateHelper() {
            this(-65536, 14, 10, 10);
        }

        public FramerateHelper(int i, int i2, int i3, int i4) {
            TextPaint textPaint = new TextPaint();
            this.mPaint = textPaint;
            textPaint.setColor(i);
            this.mPaint.setTextSize(i2);
            this.mTextX = i3;
            this.mTextY = i4;
        }

        public void draw(Canvas canvas) {
            if (this.mFramerateText == null || this.mShowingFramerate != this.mRealFrameRate) {
                int i = this.mRealFrameRate;
                this.mShowingFramerate = i;
                this.mFramerateText = String.format("FPS %d", Integer.valueOf(i));
            }
            canvas.drawText(this.mFramerateText, this.mTextX, this.mTextY, this.mPaint);
        }

        public void set(int i) {
            this.mRealFrameRate = i;
        }
    }

    public static class InnerGroup extends ElementGroup {
        public InnerGroup(Element element, ScreenElementRoot screenElementRoot) {
            super(element, screenElementRoot);
        }

        @Override // com.miui.maml.elements.ScreenElement
        public final RendererController getRendererController() {
            return this.mRoot.getRendererController();
        }
    }

    public interface OnExternCommandListener {
        void onCommand(String str, Double d, String str2);
    }

    public interface OnHoverChangeListener {
        void onHoverChange(String str);
    }

    public ScreenElementRoot(@NonNull ScreenContext screenContext) {
        super(null, null);
        this.DEFAULT_FRAME_RATE = 30.0f;
        this.mPreTickers = new ArrayList<>();
        this.mRawAttrs = new HashMap<>();
        this.mSupportAccessibilityService = false;
        this.mBlurWindow = false;
        this.mTouchable = CALCULATE_FRAME_RATE;
        this.mDisableCutRoundCorner = false;
        this.mSupportBackgroundBlur = false;
        this.mHoverMatrix = new Matrix();
        this.mAnimationItems = new ArrayMap<>();
        this.mElements = new ArrayMap<>();
        this.isIconLoad = false;
        this.mFramerateHelper = new FramerateHelper();
        this.mRendererControllers = new ArrayList<>();
        this.mCapability = -1;
        this.mAccessibleElements = new ArrayList();
        this.mRenderMutex = new Object();
        this.mRoot = this;
        this.mContext = screenContext;
        this.mVariableUpdaterManager = new VariableUpdaterManager(this);
        this.mTouchX = new IndexedVariable(VariableNames.VAR_TOUCH_X, getContext().mVariables, CALCULATE_FRAME_RATE);
        this.mTouchY = new IndexedVariable(VariableNames.VAR_TOUCH_Y, getContext().mVariables, CALCULATE_FRAME_RATE);
        this.mTouchBeginX = new IndexedVariable(VariableNames.VAR_TOUCH_BEGIN_X, getContext().mVariables, CALCULATE_FRAME_RATE);
        this.mTouchBeginY = new IndexedVariable(VariableNames.VAR_TOUCH_BEGIN_Y, getContext().mVariables, CALCULATE_FRAME_RATE);
        this.mTouchBeginTime = new IndexedVariable(VariableNames.VAR_TOUCH_BEGIN_TIME, getContext().mVariables, CALCULATE_FRAME_RATE);
        this.mNeedDisallowInterceptTouchEventVar = new IndexedVariable(VariableNames.VAR_INTECEPT_SYS_TOUCH, getContext().mVariables, CALCULATE_FRAME_RATE);
        this.mSoundManager = new SoundManager(screenContext);
        this.mSystemExternCommandListener = new SystemCommandListener(this);
        this.mConfiguration = new Configuration(screenContext.mContext.getResources().getConfiguration());
    }

    private void loadConfig(String str) {
        if (str == null) {
            return;
        }
        ConfigFile configFile = new ConfigFile();
        this.mConfig = configFile;
        configFile.setSaveViaProvider(this.mSaveConfigViaProvider);
        if (!this.mConfig.load(str)) {
            this.mConfig.loadDefaultSettings(this.mContext.mResourceManager.getConfigRoot());
        }
        for (ConfigFile.Variable variable : this.mConfig.getVariables()) {
            if (TextUtils.equals(variable.type, "string")) {
                Utils.putVariableString(variable.name, this.mContext.mVariables, variable.value);
            } else if (TextUtils.equals(variable.type, "number")) {
                try {
                    Utils.putVariableNumber(variable.name, this.mContext.mVariables, Double.parseDouble(variable.value));
                } catch (NumberFormatException unused) {
                }
            }
        }
        for (Task task : this.mConfig.getTasks()) {
            this.mContext.mVariables.put(task.id + ".name", task.name);
            this.mContext.mVariables.put(task.id + ".package", task.packageName);
            this.mContext.mVariables.put(task.id + ".class", task.className);
        }
    }

    private void loadRawAttrs(Element element) {
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node nodeItem = attributes.item(i);
            this.mRawAttrs.put(nodeItem.getNodeName(), nodeItem.getNodeValue());
        }
    }

    private void processUseVariableUpdater(Element element) {
        String attribute = element.getAttribute("useVariableUpdater");
        if (TextUtils.isEmpty(attribute)) {
            onAddVariableUpdater(this.mVariableUpdaterManager);
        } else {
            this.mVariableUpdaterManager.addFromTag(attribute);
        }
        this.mVariableUpdaterManager.add(new DarkModeVariableUpdater(this.mVariableUpdaterManager));
        this.mVariableUpdaterManager.add(new ConfigChangeVariableUpdater(this.mVariableUpdaterManager));
    }

    private void setupScale(Element element) {
        String attribute = element.getAttribute("scaleByDensity");
        if (AwesomeClockCompat.isAwesomeClock(element, this.mRootTag)) {
            AwesomeClockCompat.compatClock(element, this);
        } else if (!TextUtils.isEmpty(attribute)) {
            this.mScaleByDensity = Boolean.parseBoolean(attribute);
        }
        int attrAsInt = Utils.getAttrAsInt(element, "defaultScreenWidth", 0);
        this.mDefaultScreenWidth = attrAsInt;
        if (attrAsInt == 0) {
            this.mDefaultScreenWidth = Utils.getAttrAsInt(element, "screenWidth", 0);
        }
        int attrAsInt2 = Utils.getAttrAsInt(element, "defaultResourceDensity", 0);
        this.mRawDefaultResourceDensity = attrAsInt2;
        if (attrAsInt2 == 0) {
            this.mRawDefaultResourceDensity = Utils.getAttrAsInt(element, "resDensity", 0);
        }
        int iTranslateDensity = ResourceManager.translateDensity(this.mRawDefaultResourceDensity);
        this.mDefaultResourceDensity = iTranslateDensity;
        int i = this.mDefaultScreenWidth;
        if (i == 0 && iTranslateDensity == 0) {
            this.mDefaultScreenWidth = DEFAULT_SCREEN_WIDTH;
            this.mDefaultResourceDensity = DEFAULT_RES_DENSITY;
        } else if (iTranslateDensity == 0) {
            this.mDefaultResourceDensity = (i * DEFAULT_RES_DENSITY) / DEFAULT_SCREEN_WIDTH;
        } else if (i == 0) {
            this.mDefaultScreenWidth = (iTranslateDensity * DEFAULT_SCREEN_WIDTH) / DEFAULT_RES_DENSITY;
        }
        this.mContext.mResourceManager.setDefaultResourceDensity(this.mDefaultResourceDensity);
        WindowManager windowManager = (WindowManager) this.mContext.mContext.getSystemService("window");
        Display defaultDisplay = windowManager.getDefaultDisplay();
        int[] targetScreenWH = DeviceUtils.getTargetScreenWH(windowManager, this.mResizeScale, this.mWidgetType);
        this.mTargetScreenWidth = Math.min(targetScreenWH[0], targetScreenWH[1]);
        this.mTargetScreenHeight = Math.max(targetScreenWH[0], targetScreenWH[1]);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        this.mRawTargetDensity = displayMetrics.densityDpi;
        int i2 = this.mContext.mContext.getResources().getConfiguration().screenLayout & 15;
        int i3 = this.mRawDefaultResourceDensity;
        if (i3 == 0) {
            i3 = (this.mDefaultScreenWidth * DEFAULT_RES_DENSITY) / DEFAULT_SCREEN_WIDTH;
        }
        ExtraResource extraResource = new ExtraResource(element, i3);
        ExtraResource.Resource resourceFindResource = extraResource.findResource(this.mRawTargetDensity, this.mTargetScreenWidth, i2);
        MamlLog.d(LOG_TAG, "findResource: " + resourceFindResource.toString());
        this.mContext.mResourceManager.setExtraResource(resourceFindResource.mPath, (int) (((float) ResourceManager.translateDensity(resourceFindResource.mDensity)) / resourceFindResource.scale));
        ExtraResource.ScaleMetrics scaleMetricsFindScale = extraResource.findScale(this.mRawTargetDensity, this.mTargetScreenWidth, i2);
        MamlLog.d(LOG_TAG, "findScale: " + scaleMetricsFindScale.toString());
        if (this.mScaleByDensity) {
            int iTranslateDensity2 = ResourceManager.translateDensity(this.mRawTargetDensity);
            this.mTargetDensity = iTranslateDensity2;
            float f = this.mResizeScale;
            if (f > 0.0f) {
                this.mTargetDensity = (int) (iTranslateDensity2 * f);
            }
            float f2 = scaleMetricsFindScale.scale;
            if (f2 <= 0.0f) {
                this.mScale = this.mTargetDensity / this.mDefaultResourceDensity;
            } else {
                float f3 = f2 * ((this.mRawTargetDensity * 1.0f) / scaleMetricsFindScale.mDensity);
                this.mScale = f3;
                if (f > 0.0f) {
                    this.mScale = f3 * f;
                }
            }
        } else {
            int i4 = this.mTargetScreenWidth;
            float f4 = i4 / this.mDefaultScreenWidth;
            this.mScale = f4;
            this.mTargetDensity = (int) (this.mDefaultResourceDensity * f4);
            float f5 = scaleMetricsFindScale.scale;
            if (f5 > 0.0f) {
                this.mScale = f5 * ((i4 * 1.0f) / scaleMetricsFindScale.mScreenWidth);
            }
        }
        MamlLog.w(LOG_TAG, "set scale: " + this.mScale);
        this.mContext.mResourceManager.setTargetDensity(this.mTargetDensity);
        this.mRawWidth = Utils.getAttrAsInt(element, "width", 0);
        this.mRawHeight = Utils.getAttrAsInt(element, "height", 0);
        this.mWidth = Math.round(this.mRawWidth * this.mScale);
        this.mHeight = Math.round(this.mRawHeight * this.mScale);
    }

    private void traverseElements() {
        this.mRendererControllers.clear();
        acceptVisitor(new ScreenElementVisitor() { // from class: com.miui.maml.ScreenElementRoot.2
            @Override // com.miui.maml.elements.ScreenElementVisitor
            public void visit(ScreenElement screenElement) {
                RendererController rendererController;
                if ((screenElement instanceof FramerateController) && (rendererController = screenElement.getRendererController()) != null) {
                    rendererController.addFramerateController((FramerateController) screenElement);
                }
                if ((screenElement instanceof ElementGroupRC) || (screenElement instanceof ScreenElementRoot)) {
                    ScreenElementRoot.this.mRendererControllers.add(screenElement.getRendererController());
                }
            }
        });
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void acceptVisitor(ScreenElementVisitor screenElementVisitor) {
        super.acceptVisitor(screenElementVisitor);
        this.mInnerGroup.acceptVisitor(screenElementVisitor);
    }

    public void addAccessibleElements(AnimatedScreenElement animatedScreenElement) {
        animatedScreenElement.setVirtualViewId(this.mAccessibleElements.size());
        this.mAccessibleElements.add(animatedScreenElement);
    }

    public void addAccessibleList(List<AnimatedScreenElement> list) {
        this.mAccessibleElements.addAll(list);
        for (AnimatedScreenElement animatedScreenElement : list) {
            animatedScreenElement.setVirtualViewId(this.mAccessibleElements.indexOf(animatedScreenElement));
        }
    }

    public void addAnimationItem(String str, BaseAnimation.AnimationItem animationItem) {
        if (this.mAnimationItems.containsKey(str)) {
            this.mAnimationItems.get(str).add(animationItem);
            return;
        }
        ArrayList<BaseAnimation.AnimationItem> arrayList = new ArrayList<>();
        arrayList.add(animationItem);
        this.mAnimationItems.put(str, arrayList);
    }

    public void addElement(String str, WeakReference weakReference) {
        this.mElements.put(str, weakReference);
    }

    public void addPreTicker(ITicker iTicker) {
        this.mPreTickers.add(iTicker);
    }

    public boolean allowScreenRotation() {
        return this.mAllowScreenRotation;
    }

    public void attachToVsync() {
        if (this.mController == null) {
            throw new NullPointerException("VsyncUpdater or controller is null, MUST load before attaching");
        }
        int size = this.mRendererControllers.size();
        for (int i = 0; i < size; i++) {
            RenderVsyncUpdater.getInstance().addRendererController(this.mRendererControllers.get(i));
        }
    }

    public FramerateTokenList.FramerateToken createFramerateToken(String str) {
        return createToken(str);
    }

    public void detachFromVsync() {
        if (this.mController == null) {
            throw new NullPointerException("VsyncUpdater or controller is null, MUST load before detaching");
        }
        int size = this.mRendererControllers.size();
        for (int i = 0; i < size; i++) {
            RenderVsyncUpdater.getInstance().removeRendererController(this.mRendererControllers.get(i));
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doRender(Canvas canvas) {
        if (this.mFinished || !this.mLoaded) {
            return;
        }
        if (this.mClearCanvas) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        try {
            this.mInnerGroup.render(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e2) {
            MamlLog.e(LOG_TAG, e2.toString(), e2);
        }
        if (this.mShowFramerate) {
            this.mFramerateHelper.draw(canvas);
        }
        this.mFrames++;
        this.mController.doneRender();
        if (this.mDarkWallpaperMode && !this.mUseCustomizedDarkModeWallpaper && this.mAutoDarkenWallpaper) {
            canvas.drawColor(DARK_MODE_COLOR);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doTick(long j) {
        if (this.mFinished || !this.mLoaded) {
            return;
        }
        VariableBinderManager variableBinderManager = this.mVariableBinderManager;
        if (variableBinderManager != null) {
            variableBinderManager.tick();
        }
        this.mVariableUpdaterManager.tick(j);
        int size = this.mPreTickers.size();
        for (int i = 0; i < size; i++) {
            this.mPreTickers.get(i).tick(j);
        }
        this.mInnerGroup.tick(j);
        this.mNeedDisallowInterceptTouchEvent = this.mNeedDisallowInterceptTouchEventVar.getDouble() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
        if (this.mFrameRateVar == null) {
            this.mFrameRateVar = new IndexedVariable(VariableNames.FRAME_RATE, this.mContext.mVariables, CALCULATE_FRAME_RATE);
            this.mCheckPoint = 0L;
        }
        long j2 = this.mCheckPoint;
        if (j2 == 0) {
            this.mCheckPoint = j;
            return;
        }
        long j3 = j - j2;
        if (j3 >= 1000) {
            int i2 = (int) (((long) (this.mFrames * 1000)) / j3);
            this.mFramerateHelper.set(i2);
            this.mFrameRateVar.set(i2);
            this.mFrames = 0;
            this.mCheckPoint = j;
        }
    }

    public void doneRender() {
        this.mController.doneRender();
    }

    public VariableBinder findBinder(String str) {
        VariableBinderManager variableBinderManager = this.mVariableBinderManager;
        if (variableBinderManager != null) {
            return variableBinderManager.findBinder(str);
        }
        return null;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public ScreenElement findElement(String str) {
        return ROOT_NAME.equals(str) ? this : getElement(str);
    }

    public Task findTask(String str) {
        ConfigFile configFile = this.mConfig;
        if (configFile == null) {
            return null;
        }
        return configFile.getTask(str);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void finish() {
        if (this.mFinished || !this.mLoaded) {
            return;
        }
        super.finish();
        MamlLog.d(LOG_TAG, "finish");
        this.mInnerGroup.performAction("preFinish");
        this.mInnerGroup.finish();
        this.mInnerGroup.performAction("finish");
        ConfigFile configFile = this.mConfig;
        if (configFile != null && !this.mSaveConfigOnlyInPause) {
            configFile.save(this.mContext.mContext.getApplicationContext());
        }
        VariableBinderManager variableBinderManager = this.mVariableBinderManager;
        if (variableBinderManager != null) {
            variableBinderManager.finish();
        }
        CommandTriggers commandTriggers = this.mExternalCommandManager;
        if (commandTriggers != null) {
            commandTriggers.finish();
        }
        VariableUpdaterManager variableUpdaterManager = this.mVariableUpdaterManager;
        if (variableUpdaterManager != null) {
            variableUpdaterManager.finish();
        }
        this.mSoundManager.release();
        this.mContext.mResourceManager.finish(this.mKeepResource);
        this.mFinished = CALCULATE_FRAME_RATE;
        this.mKeepResource = false;
        Expression.FunctionExpression.resetFunctions();
    }

    public List<AnimatedScreenElement> getAccessibleElements() {
        return this.mAccessibleElements;
    }

    public ArrayList<BaseAnimation.AnimationItem> getAnimationItems(String str) {
        return this.mAnimationItems.get(str);
    }

    public String getCacheDir() {
        return this.mCacheDir;
    }

    public boolean getCapability(int i) {
        if ((this.mCapability & i) != 0) {
            return CALCULATE_FRAME_RATE;
        }
        return false;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public ScreenContext getContext() {
        return this.mContext;
    }

    @Nullable
    public Pair<Context, Rect> getContextAndRect() {
        WeakReference<MamlViewManager> weakReference = this.mViewManagerRef;
        MamlViewManager mamlViewManager = weakReference != null ? weakReference.get() : null;
        if (mamlViewManager != null) {
            return Pair.create(mamlViewManager.getViewContext(), mamlViewManager.getViewLocationOnScreen());
        }
        return null;
    }

    public String getCornerCutType() {
        return this.mCornerCutType;
    }

    public int getDefaultScreenWidth() {
        return this.mDefaultScreenWidth;
    }

    public ScreenElement getElement(String str) {
        WeakReference<ScreenElement> weakReference = this.mElements.get(str);
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public final float getFontScale() {
        return this.mFontScale;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public AnimatedScreenElement getHoverElement() {
        return this.mHoverElement;
    }

    public MamlAccessHelper getMamlAccessHelper() {
        return this.mMamlAccessHelper;
    }

    public MamlSurface getMamlSurface() {
        WeakReference<MamlSurface> weakReference = this.mMamlSurfaceRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public String getRawAttr(String str) {
        return this.mRawAttrs.get(str);
    }

    public Object getRenderMutex() {
        return this.mRenderMutex;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public RendererController getRendererController() {
        return this.mController;
    }

    public int getResourceDensity() {
        return this.mDefaultResourceDensity;
    }

    public String getRootTag() {
        return this.mRootTag;
    }

    public final float getScale() {
        float f = this.mScale;
        if (f != 0.0f) {
            return f;
        }
        MamlLog.w(LOG_TAG, "scale not initialized!");
        return 1.0f;
    }

    public int getScreenHeight() {
        return this.mTargetScreenHeight;
    }

    public int getScreenWidth() {
        return this.mTargetScreenWidth;
    }

    public StylesManager.Style getStyle(String str) {
        StylesManager stylesManager;
        if (TextUtils.isEmpty(str) || (stylesManager = this.mStylesManager) == null) {
            return null;
        }
        return stylesManager.getStyle(str);
    }

    public float getSystemFrameRate() {
        return ((WindowManager) this.mContext.mContext.getSystemService("window")).getDefaultDisplay().getRefreshRate();
    }

    public int getTargetDensity() {
        return this.mTargetDensity;
    }

    public MamlViewManager getViewManager() {
        WeakReference<MamlViewManager> weakReference = this.mViewManagerRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public Configuration getmConfiguration() {
        return this.mConfiguration;
    }

    public void haptic(int i) {
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void init() {
        Context context;
        PackageManager packageManager;
        PackageInfo packageInfo;
        int i;
        if (this.mLoaded) {
            Variables variables = this.mContext.mVariables;
            variables.put("__objRoot", this);
            variables.put("__objContext", this.mContext);
            super.init();
            MamlLog.d(LOG_TAG, "init");
            requestFramerate(this.mFrameRate);
            this.mCapability = -1;
            this.mShowDebugLayout = HideSdkDependencyUtils.isShowDebugLayout();
            int i2 = 0;
            this.mFinished = false;
            this.mContext.mResourceManager.init();
            float f = getContext().mContext.getResources().getConfiguration().fontScale;
            this.mFontScale = f;
            variables.put("__fontScale", f);
            Locale locale = this.mContext.mContext.getResources().getConfiguration().locale;
            ScreenContext screenContext = this.mContext;
            LanguageHelper.load(locale, screenContext.mResourceManager, screenContext.mVariables);
            variables.put(VariableNames.RAW_SCREEN_WIDTH, this.mTargetScreenWidth);
            variables.put(VariableNames.RAW_SCREEN_HEIGHT, this.mTargetScreenHeight);
            variables.put(VariableNames.SCREEN_WIDTH, this.mTargetScreenWidth / this.mScale);
            variables.put(VariableNames.SCREEN_HEIGHT, this.mTargetScreenHeight / this.mScale);
            MamlView.Config config = this.mMamlViewConfig;
            if (config == null || (i = config.viewWidth) <= 0 || config.viewHeight <= 0) {
                variables.put("view_width", this.mTargetScreenWidth / this.mScale);
                variables.put("view_height", this.mTargetScreenHeight / this.mScale);
                int i3 = this.mRawWidth;
                if (i3 > 0) {
                    variables.put("view_width", i3);
                }
                int i4 = this.mRawHeight;
                if (i4 > 0) {
                    variables.put("view_height", i4);
                }
            } else {
                variables.put("view_width", i / this.mScale);
                variables.put("view_height", this.mMamlViewConfig.viewHeight / this.mScale);
            }
            variables.put(RAW_DENSITY, this.mRawTargetDensity);
            variables.put(SCALE_FACTOR, this.mScale);
            variables.put(VAR_MAML_VERSION, MAML_INTERNAL_VERSION);
            try {
                ScreenContext screenContext2 = this.mContext;
                if (screenContext2 != null && (context = screenContext2.mContext) != null && (packageManager = context.getPackageManager()) != null && (packageInfo = packageManager.getPackageInfo(THEMEMANAGER_PACKAGE_NAME, 0)) != null) {
                    i2 = packageInfo.versionCode;
                }
            } catch (Exception unused) {
                MamlLog.e(LOG_TAG, "thememanager not found");
            }
            variables.put(VariableNames.VAR_THEMEMANAGER_VERSION, i2);
            variables.put(VariableNames.VAR_MIUI_VERSION_NAME, SystemProperties.get(MIUI_VERSION_NAME));
            variables.put(VariableNames.VAR_MIUI_VERSION_CODE, SystemProperties.get(MIUI_VERSION_CODE));
            variables.put(VariableNames.VAR_ANDROID_VERSION, Build.VERSION.RELEASE);
            variables.put(VariableNames.VAR_SYSTEM_VERSION, Build.VERSION.INCREMENTAL);
            variables.put(VariableNames.VAR_IS_TABLET, Boolean.valueOf(DeviceUtils.isPad()));
            variables.put(VariableNames.VAR_DEVICE_LEVEL, DeviceUtils.getDeviceLevel());
            variables.put(VariableNames.VAR_HYPER_OS_VERSION_NAME, SystemProperties.get(HYPER_OS_VERSION_NAME));
            variables.put(VariableNames.VAR_HYPER_OS_VERSION_CODE, SystemProperties.get(HYPER_OS_VERSION_CODE));
            loadConfig();
            VariableUpdaterManager variableUpdaterManager = this.mVariableUpdaterManager;
            if (variableUpdaterManager != null) {
                variableUpdaterManager.init();
            }
            VariableBinderManager variableBinderManager = this.mVariableBinderManager;
            if (variableBinderManager != null) {
                variableBinderManager.init();
            }
            CommandTriggers commandTriggers = this.mExternalCommandManager;
            if (commandTriggers != null) {
                commandTriggers.init();
            }
            this.mInnerGroup.performAction("init");
            this.mInnerGroup.init();
            this.mInnerGroup.performAction("postInit");
            this.mRoot.mHoverElement = null;
            this.mNeedReset = CALCULATE_FRAME_RATE;
            this.mController.setNeedReset(CALCULATE_FRAME_RATE);
            requestUpdate();
        }
    }

    public boolean isDisableCutRoundCorner() {
        return this.mDisableCutRoundCorner;
    }

    public boolean isIconLoad() {
        return this.isIconLoad;
    }

    public boolean isMamlBlurWindow() {
        return this.mBlurWindow;
    }

    public boolean isSupportAccessibilityService() {
        return this.mSupportAccessibilityService;
    }

    public boolean isTransparentSurface() {
        return this.mTransparentSurface;
    }

    public boolean isUseHardwareCanvas() {
        return this.mUseHardwareCanvas;
    }

    public void issueExternCommand(String str, Double d, String str2) {
        OnExternCommandListener onExternCommandListener;
        OnExternCommandListener onExternCommandListener2;
        OnExternCommandListener onExternCommandListener3;
        this.mSystemExternCommandListener.onCommand(str, d, str2);
        WeakReference<OnExternCommandListener> weakReference = this.mExternCommandListener;
        if (weakReference != null && (onExternCommandListener3 = weakReference.get()) != null) {
            onExternCommandListener3.onCommand(str, d, str2);
            MamlLog.d(LOG_TAG, "issueExternCommand: " + str + " " + d + " " + str2);
        }
        WeakReference<OnExternCommandListener> weakReference2 = this.mMamlViewExternCommandListener;
        if (weakReference2 != null && (onExternCommandListener2 = weakReference2.get()) != null) {
            onExternCommandListener2.onCommand(str, d, str2);
            MamlLog.d(LOG_TAG, "issueExternCommand to MamlView: " + str + " " + d + " " + str2);
        }
        WeakReference<OnExternCommandListener> weakReference3 = this.mMamlSurfaceExternCommandListener;
        if (weakReference3 == null || (onExternCommandListener = weakReference3.get()) == null) {
            return;
        }
        onExternCommandListener.onCommand(str, d, str2);
        MamlLog.d(LOG_TAG, "issueExternCommand to MamlSurface: " + str + " " + d + " " + str2);
    }

    public final boolean load() {
        try {
            this.mLoaded = false;
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            Element manifestRoot = this.mContext.mResourceManager.getManifestRoot();
            if (manifestRoot == null) {
                MamlLog.e(LOG_TAG, "load error, manifest root is null");
                return false;
            }
            this.mRootTag = manifestRoot.getNodeName();
            loadRawAttrs(manifestRoot);
            processUseVariableUpdater(manifestRoot);
            setupScale(manifestRoot);
            this.mVariableBinderManager = new VariableBinderManager(Utils.getChild(manifestRoot, VariableBinderManager.TAG_NAME), this);
            Element child = Utils.getChild(manifestRoot, EXTERNAL_COMMANDS_TAG_NAME);
            if (child != null) {
                this.mExternalCommandManager = new CommandTriggers(child, this);
            }
            Element child2 = Utils.getChild(manifestRoot, "Styles");
            if (child2 != null) {
                this.mStylesManager = new StylesManager(child2);
            }
            this.mSelfRender = Boolean.parseBoolean(manifestRoot.getAttribute("selfRender"));
            this.mTransparentSurface = Boolean.parseBoolean(manifestRoot.getAttribute("transparentSurface"));
            this.mFrameRate = Utils.getAttrAsFloat(manifestRoot, "frameRate", this.DEFAULT_FRAME_RATE);
            this.mUseCustomizedDarkModeWallpaper = Boolean.parseBoolean(manifestRoot.getAttribute("customizedDarkModeWallpaper"));
            this.mDisableCutRoundCorner = Boolean.parseBoolean(manifestRoot.getAttribute("disableCutRoundCorner"));
            if (manifestRoot.hasAttribute("cornerCutType")) {
                this.mCornerCutType = manifestRoot.getAttribute("cornerCutType");
            }
            this.mClearCanvas = Boolean.parseBoolean(manifestRoot.getAttribute("clearCanvas"));
            this.mSupportAccessibilityService = Boolean.parseBoolean(manifestRoot.getAttribute("supportAccessibityService"));
            this.mAllowScreenRotation = Boolean.parseBoolean(manifestRoot.getAttribute("allowScreenRotation"));
            this.mBlurWindow = Boolean.parseBoolean(manifestRoot.getAttribute("blurWindow"));
            this.mUseHardwareCanvas = Boolean.parseBoolean(manifestRoot.getAttribute("useHardwareCanvas"));
            this.mNeedFolmeClean = Utils.getAttrAsInt(manifestRoot, "needFolmeClean", 1) > 0;
            this.mSupportBackgroundBlur = Boolean.parseBoolean(manifestRoot.getAttribute("supportBackgroundBlur"));
            RendererController rendererController = new RendererController();
            this.mController = rendererController;
            rendererController.setSelfRender(this.mSelfRender);
            InnerGroup innerGroup = new InnerGroup(manifestRoot, this);
            this.mInnerGroup = innerGroup;
            if (innerGroup.getElements().size() <= 0) {
                MamlLog.e(LOG_TAG, "load error, no element loaded");
                return false;
            }
            this.mVersion = Utils.getAttrAsInt(manifestRoot, "version", 1);
            if (!onLoad(manifestRoot)) {
                MamlLog.e(LOG_TAG, "load error, onLoad fail");
                return false;
            }
            traverseElements();
            MamlLog.d(LOG_TAG, "load finished, spent " + (SystemClock.elapsedRealtime() - jElapsedRealtime) + " ms");
            this.mLoaded = CALCULATE_FRAME_RATE;
            return CALCULATE_FRAME_RATE;
        } catch (Exception e) {
            MamlLog.e(LOG_TAG, "load error! ", e);
            return false;
        }
    }

    public void loadConfig() {
        loadConfig(this.mConfigPath);
    }

    public boolean needDisallowInterceptTouchEvent() {
        return this.mNeedDisallowInterceptTouchEvent;
    }

    public boolean needFolmeClean() {
        return this.mNeedFolmeClean;
    }

    public void onAddVariableUpdater(VariableUpdaterManager variableUpdaterManager) {
        variableUpdaterManager.add(new DateTimeVariableUpdater(variableUpdaterManager));
    }

    public void onCommand(final String str) {
        if (this.mExternalCommandManager != null) {
            postRunnable(new Runnable() { // from class: com.miui.maml.ScreenElementRoot.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        ScreenElementRoot.this.mExternalCommandManager.onAction(str);
                    } catch (Exception e) {
                        MamlLog.e(ScreenElementRoot.LOG_TAG, e.toString());
                        e.printStackTrace();
                    }
                }
            });
            requestUpdate();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void onConfigurationChanged(Configuration configuration) {
        if (this.mAllowScreenRotation) {
            setConfiguration(configuration, false);
            onCommand("orientationChange");
            requestUpdate();
        }
        Configuration configuration2 = this.mConfiguration;
        if (configuration2 == null) {
            return;
        }
        if (configuration2.screenWidthDp != configuration.screenWidthDp || configuration2.screenLayout != configuration.screenLayout || configuration2.smallestScreenWidthDp != configuration.smallestScreenWidthDp || configuration2.densityDpi != configuration.densityDpi) {
            ResourceManager.clear();
            Element manifestRoot = this.mContext.mResourceManager.getManifestRoot();
            if (manifestRoot == null) {
                MamlLog.e(LOG_TAG, "load error, manifest root is null");
                return;
            } else {
                setupScale(manifestRoot);
                setConfiguration(configuration, CALCULATE_FRAME_RATE);
                requestUpdate();
            }
        }
        this.mConfiguration.updateFrom(configuration);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public boolean onHover(MotionEvent motionEvent) {
        try {
            if (!this.mFinished && this.mLoaded && this.mTouchable) {
                return this.mInnerGroup.onHover(motionEvent);
            }
            return false;
        } catch (Exception | OutOfMemoryError e) {
            MamlLog.e(LOG_TAG, e.toString(), e);
            return false;
        }
    }

    public void onHoverChange(AnimatedScreenElement animatedScreenElement, String str) {
        this.mHoverElement = animatedScreenElement;
        WeakReference<OnHoverChangeListener> weakReference = this.mHoverChangeListenerRef;
        OnHoverChangeListener onHoverChangeListener = weakReference != null ? weakReference.get() : null;
        if (onHoverChangeListener != null) {
            onHoverChangeListener.onHoverChange(str);
        }
    }

    public boolean onLoad(Element element) {
        return CALCULATE_FRAME_RATE;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public boolean onTouch(MotionEvent motionEvent) {
        try {
            if (!this.mFinished && this.mLoaded && this.mTouchable) {
                AnimatedScreenElement animatedScreenElement = this.mHoverElement;
                if (animatedScreenElement != null) {
                    this.mHoverMatrix.setTranslate((this.mHoverElement.getAbsoluteLeft() + (animatedScreenElement.getWidth() / 2.0f)) - motionEvent.getX(), (this.mHoverElement.getAbsoluteTop() + (this.mHoverElement.getHeight() / 2.0f)) - motionEvent.getY());
                    motionEvent.transform(this.mHoverMatrix);
                    this.mHoverElement.onTouch(motionEvent);
                    if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                        this.mHoverElement = null;
                    }
                    return CALCULATE_FRAME_RATE;
                }
                double dDescale = descale(motionEvent.getX());
                double dDescale2 = descale(motionEvent.getY());
                this.mTouchX.set(dDescale);
                this.mTouchY.set(dDescale2);
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    this.mTouchBeginX.set(dDescale);
                    this.mTouchBeginY.set(dDescale2);
                    this.mTouchBeginTime.set(System.currentTimeMillis());
                    this.mNeedDisallowInterceptTouchEvent = false;
                } else if (actionMasked == 1) {
                    this.mNeedDisallowInterceptTouchEvent = false;
                }
                boolean zOnTouch = this.mInnerGroup.onTouch(motionEvent);
                if (!zOnTouch) {
                    this.mController.requestUpdate();
                }
                return zOnTouch;
            }
            return false;
        } catch (Exception | OutOfMemoryError e) {
            MamlLog.e(LOG_TAG, e.toString());
            return false;
        }
    }

    @Override // com.miui.maml.InteractiveListener
    public void onUIInteractive(ScreenElement screenElement, String str) {
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void pause() {
        if (this.mLoaded) {
            super.pause();
            MamlLog.d(LOG_TAG, "pause");
            this.mInnerGroup.performAction("pause");
            this.mInnerGroup.pause();
            this.mSoundManager.pause();
            VariableBinderManager variableBinderManager = this.mVariableBinderManager;
            if (variableBinderManager != null) {
                variableBinderManager.pause();
            }
            CommandTriggers commandTriggers = this.mExternalCommandManager;
            if (commandTriggers != null) {
                commandTriggers.pause();
            }
            VariableUpdaterManager variableUpdaterManager = this.mVariableUpdaterManager;
            if (variableUpdaterManager != null) {
                variableUpdaterManager.pause();
            }
            this.mContext.mResourceManager.pause();
            onHoverChange(null, null);
            ConfigFile configFile = this.mConfig;
            if (configFile != null) {
                configFile.save(this.mContext.mContext.getApplicationContext());
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void pauseAnim(long j) {
        super.pauseAnim(j);
        this.mInnerGroup.pauseAnim(j);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void playAnim(long j, long j2, long j3, boolean z, boolean z2) {
        super.playAnim(j, j2, j3, z, z2);
        this.mInnerGroup.playAnim(j, j2, j3, z, z2);
    }

    public int playSound(String str) {
        return playSound(str, new SoundManager.SoundOptions(false, false, 1.0f));
    }

    public int playSound(String str, SoundManager.SoundOptions soundOptions) {
        if (!TextUtils.isEmpty(str) && shouldPlaySound()) {
            return this.mSoundManager.playSound(str, soundOptions);
        }
        return 0;
    }

    public void playSound(int i, SoundManager.Command command) {
        try {
            this.mSoundManager.playSound(i, command);
        } catch (Exception e) {
            MamlLog.e(LOG_TAG, e.toString());
        }
    }

    public boolean postDelayed(Runnable runnable, long j) {
        if (this.mFinished || !this.mLoaded) {
            return false;
        }
        return this.mContext.postDelayed(runnable, j);
    }

    public void postMessage(MotionEvent motionEvent) {
        if (this.mLoaded) {
            this.mController.postMessage(motionEvent);
        }
    }

    public String putRawAttr(String str, String str2) {
        return this.mRawAttrs.put(str, str2);
    }

    public void removeAccessibleElement(AnimatedScreenElement animatedScreenElement) {
        this.mAccessibleElements.remove(animatedScreenElement);
        animatedScreenElement.setVirtualViewId(Integer.MIN_VALUE);
    }

    public void removeAllAccessibleElements() {
        Iterator<AnimatedScreenElement> it = this.mAccessibleElements.iterator();
        while (it.hasNext()) {
            it.next().setVirtualViewId(Integer.MIN_VALUE);
        }
        this.mAccessibleElements.clear();
    }

    public void removeCallbacks(Runnable runnable) {
        this.mContext.removeCallbacks(runnable);
    }

    public void removeElement(String str) {
        this.mElements.remove(str);
    }

    public void removePreTicker(ITicker iTicker) {
        this.mPreTickers.remove(iTicker);
    }

    public void requestFrameRateByCommand(float f) {
        this.mFrameRate = f;
        requestFramerate(f);
        if (f > 0.0f) {
            requestUpdate();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void requestUpdate() {
        if (this.mLoaded) {
            int size = this.mRendererControllers.size();
            for (int i = 0; i < size; i++) {
                this.mRendererControllers.get(i).requestUpdate();
            }
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void reset(long j) {
        super.reset(j);
        this.mInnerGroup.reset(j);
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resume() {
        if (this.mLoaded) {
            super.resume();
            MamlLog.d(LOG_TAG, "resume");
            this.mShowDebugLayout = HideSdkDependencyUtils.isShowDebugLayout();
            this.mInnerGroup.performAction("resume");
            this.mInnerGroup.resume();
            VariableUpdaterManager variableUpdaterManager = this.mVariableUpdaterManager;
            if (variableUpdaterManager != null) {
                variableUpdaterManager.resume();
            }
            VariableBinderManager variableBinderManager = this.mVariableBinderManager;
            if (variableBinderManager != null) {
                variableBinderManager.resume();
            }
            CommandTriggers commandTriggers = this.mExternalCommandManager;
            if (commandTriggers != null) {
                commandTriggers.resume();
            }
            this.mContext.mResourceManager.resume();
            setSyncInterval();
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void resumeAnim(long j) {
        super.resumeAnim(j);
        this.mInnerGroup.resumeAnim(j);
    }

    public void saveVar(String str, Double d) {
        ConfigFile configFile = this.mConfig;
        if (configFile == null) {
            MamlLog.w(LOG_TAG, "fail to saveVar, config file is null");
        } else if (d == null) {
            configFile.putNumber(str, "null");
        } else {
            configFile.putNumber(str, d.doubleValue());
        }
    }

    public void saveVar(String str, String str2) {
        ConfigFile configFile = this.mConfig;
        if (configFile == null) {
            MamlLog.w(LOG_TAG, "fail to saveVar, config file is null");
        } else {
            configFile.putString(str, str2);
        }
    }

    public void selfFinish() {
        if (this.mLoaded) {
            this.mController.finish();
        }
    }

    public void selfInit() {
        if (this.mLoaded) {
            this.mController.init();
        }
    }

    public void selfPause() {
        int size = this.mRendererControllers.size();
        for (int i = 0; i < size; i++) {
            this.mRendererControllers.get(i).selfPause();
        }
    }

    public void selfResume() {
        int size = this.mRendererControllers.size();
        for (int i = 0; i < size; i++) {
            this.mRendererControllers.get(i).selfResume();
        }
    }

    public void setAutoDarkenWallpaper(boolean z) {
        this.mAutoDarkenWallpaper = z;
    }

    @Deprecated
    public void setBgColor(int i) {
    }

    public void setCacheDir(String str) {
        this.mCacheDir = str;
    }

    public void setCapability(int i, boolean z) {
        if (z) {
            this.mCapability = i | this.mCapability;
        } else {
            this.mCapability = (~i) & this.mCapability;
        }
    }

    public void setClearCanvas(boolean z) {
        this.mClearCanvas = z;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void setColorFilter(ColorFilter colorFilter) {
        ElementGroup elementGroup = this.mInnerGroup;
        if (elementGroup != null) {
            elementGroup.setColorFilter(colorFilter);
        }
    }

    public void setConfig(String str) {
        this.mConfigPath = str;
    }

    public void setConfiguration(Configuration configuration, boolean z) {
        if (this.mAllowScreenRotation || z) {
            Variables variables = this.mContext.mVariables;
            Utils.putVariableNumber(VariableNames.ORIENTATION, variables, Double.valueOf(configuration.orientation));
            int i = configuration.orientation;
            if (i == 1) {
                variables.put(VariableNames.RAW_SCREEN_WIDTH, this.mTargetScreenWidth);
                variables.put(VariableNames.RAW_SCREEN_HEIGHT, this.mTargetScreenHeight);
                variables.put(VariableNames.SCREEN_WIDTH, this.mTargetScreenWidth / this.mScale);
                variables.put(VariableNames.SCREEN_HEIGHT, this.mTargetScreenHeight / this.mScale);
                return;
            }
            if (i != 2) {
                return;
            }
            variables.put(VariableNames.RAW_SCREEN_WIDTH, this.mTargetScreenHeight);
            variables.put(VariableNames.RAW_SCREEN_HEIGHT, this.mTargetScreenWidth);
            variables.put(VariableNames.SCREEN_WIDTH, this.mTargetScreenHeight / this.mScale);
            variables.put(VariableNames.SCREEN_HEIGHT, this.mTargetScreenWidth / this.mScale);
        }
    }

    public void setDarkWallpaperMode(boolean z) {
        this.mDarkWallpaperMode = z;
    }

    public void setDefaultFramerate(float f) {
        this.DEFAULT_FRAME_RATE = f;
    }

    public void setIconLoad(boolean z) {
        this.isIconLoad = z;
    }

    public final void setKeepResource(boolean z) {
        this.mKeepResource = z;
    }

    public void setMamlAccessHelper(MamlAccessHelper mamlAccessHelper) {
        this.mMamlAccessHelper = mamlAccessHelper;
    }

    public void setMamlSurface(MamlSurface mamlSurface) {
        this.mMamlSurfaceRef = new WeakReference<>(mamlSurface);
    }

    public void setMamlSurfaceOnExternCommandListener(OnExternCommandListener onExternCommandListener) {
        this.mMamlSurfaceExternCommandListener = onExternCommandListener == null ? null : new WeakReference<>(onExternCommandListener);
    }

    public void setMamlViewConfig(MamlView.Config config) {
        this.mMamlViewConfig = config;
    }

    public void setMamlViewOnExternCommandListener(OnExternCommandListener onExternCommandListener) {
        this.mMamlViewExternCommandListener = onExternCommandListener == null ? null : new WeakReference<>(onExternCommandListener);
    }

    public void setOnExternCommandListener(OnExternCommandListener onExternCommandListener) {
        this.mExternCommandListener = onExternCommandListener == null ? null : new WeakReference<>(onExternCommandListener);
    }

    public void setOnHoverChangeListener(OnHoverChangeListener onHoverChangeListener) {
        this.mHoverChangeListenerRef = new WeakReference<>(onHoverChangeListener);
    }

    public void setRenderControllerListener(RendererController.Listener listener) {
        if (this.mLoaded) {
            this.mController.setListener(listener);
        }
    }

    public void setRenderControllerRenderable(RendererController.IRenderable iRenderable) {
        if (this.mLoaded) {
            setRenderControllerListener(new SingleRootListener(this, iRenderable));
        }
    }

    public void setResizeRadio(float f) {
        this.mResizeScale = f;
    }

    public void setSaveConfigOnlyInPause(boolean z) {
        this.mSaveConfigOnlyInPause = z;
    }

    public void setSaveConfigViaProvider(boolean z) {
        this.mSaveConfigViaProvider = z;
        ConfigFile configFile = this.mConfig;
        if (configFile != null) {
            configFile.setSaveViaProvider(z);
        }
    }

    public void setScaleByDensity(boolean z) {
        this.mScaleByDensity = z;
    }

    public void setSyncInterval() {
        int systemFrameRate = (int) getSystemFrameRate();
        int i = systemFrameRate != 0 ? 1000 / systemFrameRate : 16;
        if (i < 1) {
            i = 1;
        }
        RenderVsyncUpdater.getInstance().setSyncInterval(i);
    }

    public void setTouchable(boolean z) {
        this.mTouchable = z;
    }

    public void setViewManager(MamlViewManager mamlViewManager) {
        this.mViewManagerRef = new WeakReference<>(mamlViewManager);
    }

    public void setWidgetType(String str) {
        this.mWidgetType = str;
    }

    public boolean shouldPlaySound() {
        return CALCULATE_FRAME_RATE;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void showCategory(String str, boolean z) {
        this.mInnerGroup.showCategory(str, z);
    }

    public void showFramerate(boolean z) {
        this.mShowFramerate = z;
    }

    public boolean supportBackgroundBlur() {
        return this.mSupportBackgroundBlur;
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void tick(final long j) {
        if (this.mNeedReset) {
            postRunnableAtFrontOfQueue(new Runnable() { // from class: com.miui.maml.ScreenElementRoot.1
                @Override // java.lang.Runnable
                public void run() {
                    ScreenElementRoot.this.reset(j);
                    if (ScreenElementRoot.this.mExternalCommandManager != null) {
                        try {
                            ScreenElementRoot.this.mExternalCommandManager.onAction("init");
                        } catch (Exception e) {
                            MamlLog.w(ScreenElementRoot.LOG_TAG, "onAction init fail." + e);
                        }
                    }
                }
            });
            this.mNeedReset = false;
            this.mController.setNeedReset(false);
        }
        doTick(j);
    }

    public long update(long j) {
        int size = this.mRendererControllers.size();
        long j2 = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            RendererController rendererController = this.mRendererControllers.get(i);
            if (!rendererController.isSelfPaused() || rendererController.hasRunnable()) {
                long jUpdate = rendererController.update(j);
                if (jUpdate < j2) {
                    j2 = jUpdate;
                }
            }
        }
        return j2;
    }

    public void updateDateTimeVariable(String str) {
        if (TextUtils.isEmpty(str) && this.mVariableUpdaterManager == null) {
            return;
        }
        this.mVariableUpdaterManager.updateDateTimeVariable(str);
        this.mVariableUpdaterManager.resume();
    }

    public long updateIfNeeded(long j) {
        int size = this.mRendererControllers.size();
        long j2 = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            RendererController rendererController = this.mRendererControllers.get(i);
            if (!rendererController.isSelfPaused() || rendererController.hasRunnable()) {
                long jUpdateIfNeeded = rendererController.updateIfNeeded(j);
                if (jUpdateIfNeeded < j2) {
                    j2 = jUpdateIfNeeded;
                }
            }
        }
        return j2;
    }

    public final int version() {
        return this.mVersion;
    }
}
