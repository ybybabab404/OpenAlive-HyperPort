package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import android.util.Size;
import android.widget.ImageView;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.miwallpaper.material.MiuiColorScheme;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import miuix.core.util.IOUtils;

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
/* JADX INFO: compiled from: BitmapUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class BitmapUtil {
    public static final BitmapUtil INSTANCE = new BitmapUtil();

    /* JADX INFO: compiled from: BitmapUtil.kt */
    @Metadata
    public static final class ThermalGate {
        public static volatile int currentStatus;
        public static volatile boolean initialized;
        public static final ThermalGate INSTANCE = new ThermalGate();
        public static final PowerManager.OnThermalStatusChangedListener listener = new PowerManager.OnThermalStatusChangedListener() { // from class: com.miui.keyguard.editor.utils.BitmapUtil$ThermalGate$$ExternalSyntheticLambda0
            @Override // android.os.PowerManager.OnThermalStatusChangedListener
            public final void onThermalStatusChanged(int i) {
                BitmapUtil.ThermalGate.listener$lambda$0(i);
            }
        };

        public static final void listener$lambda$0(int i) {
            int i2 = currentStatus;
            currentStatus = i;
            if (i2 != i) {
                Log.i("BitmapUtil.ThermalGate", "onThermalStatusChanged: " + i2 + " -> " + i);
            }
        }

        public final synchronized void ensureInitialized() {
            try {
                if (initialized) {
                    return;
                }
                Context contextTryGetContext = tryGetContext();
                if (contextTryGetContext == null) {
                    return;
                }
                try {
                    Object systemService = contextTryGetContext.getSystemService("power");
                    PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
                    if (powerManager == null) {
                        Log.w("BitmapUtil.ThermalGate", "PowerManager not available");
                        initialized = true;
                        return;
                    }
                    currentStatus = powerManager.getCurrentThermalStatus();
                    powerManager.addThermalStatusListener(listener);
                    Log.i("BitmapUtil.ThermalGate", "ThermalGate initialized, currentStatus=" + currentStatus);
                    initialized = true;
                } catch (Exception e) {
                    Log.w("BitmapUtil.ThermalGate", "init failed: " + e);
                    initialized = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final boolean shouldDownsample() {
            if (!initialized) {
                ensureInitialized();
            }
            return currentStatus >= 4;
        }

        public final Context tryGetContext() {
            try {
                return EditorApplicationProxy.Companion.getApplication();
            } catch (Exception e) {
                Log.w("BitmapUtil.ThermalGate", "getApplication failed: " + e);
                return null;
            }
        }
    }

    /* JADX INFO: compiled from: BitmapUtil.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            try {
                iArr[ImageView.ScaleType.CENTER_CROP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ImageView.ScaleType.FIT_XY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ Bitmap fromFile$default(BitmapUtil bitmapUtil, String str, int i, int i2, Context context, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        if ((i3 & 8) != 0) {
            context = null;
        }
        return bitmapUtil.fromFile(str, i, i2, context);
    }

    public static /* synthetic */ Bitmap fromFile2$default(BitmapUtil bitmapUtil, String str, int i, int i2, Bitmap.Config config, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        if ((i3 & 8) != 0) {
            config = null;
        }
        return bitmapUtil.fromFile2(str, i, i2, config);
    }

    public static /* synthetic */ Matrix getBitmapMatrix$default(BitmapUtil bitmapUtil, int i, int i2, int i3, int i4, ImageView.ScaleType scaleType, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            scaleType = ImageView.ScaleType.CENTER_CROP;
        }
        return bitmapUtil.getBitmapMatrix(i, i2, i3, i4, scaleType);
    }

    public static /* synthetic */ Bitmap parcelFileDescriptorToBitmap$default(BitmapUtil bitmapUtil, ParcelFileDescriptor parcelFileDescriptor, int i, int i2, Bitmap.Config config, ColorSpace colorSpace, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = -1;
        }
        if ((i3 & 4) != 0) {
            i2 = -1;
        }
        if ((i3 & 8) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        if ((i3 & 16) != 0) {
            colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        }
        return bitmapUtil.parcelFileDescriptorToBitmap(parcelFileDescriptor, i, i2, config, colorSpace);
    }

    public static /* synthetic */ boolean toFile$default(BitmapUtil bitmapUtil, Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat, int i, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            i = 100;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            z = false;
        }
        return bitmapUtil.toFile(bitmap, str, compressFormat, i3, z);
    }

    public static /* synthetic */ boolean toFileByFileDescriptor$default(BitmapUtil bitmapUtil, Bitmap bitmap, ParcelFileDescriptor parcelFileDescriptor, Bitmap.CompressFormat compressFormat, int i, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            i = 100;
        }
        return bitmapUtil.toFileByFileDescriptor(bitmap, parcelFileDescriptor, compressFormat, i);
    }

    public static /* synthetic */ boolean toWebp$default(BitmapUtil bitmapUtil, Bitmap bitmap, String str, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return bitmapUtil.toWebp(bitmap, str, z);
    }

    public final int calculateInSampleSize(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        BitmapCreator.INSTANCE.decodeFile(str, options);
        int i = options.outWidth;
        if (i >= 3333 || options.outHeight >= 3333) {
            return (int) (((double) ((RangesKt.coerceAtLeast(i, options.outHeight) * 1.0f) / 3333)) + 0.5d);
        }
        return 1;
    }

    public final int calculateInSampleSizeFromFd(FileDescriptor fileDescriptor, int i, int i2) {
        try {
            try {
                fileDescriptor = Os.dup(fileDescriptor);
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                    int i3 = options.outWidth;
                    int i4 = options.outHeight;
                    if (i3 <= 0 || i4 <= 0) {
                        Log.w("BitmapUtil", "calculateInSampleSizeFromFd: invalid bounds " + i3 + AnimatedProperty.PROPERTY_NAME_X + i4);
                        try {
                            Os.close(fileDescriptor);
                        } catch (ErrnoException unused) {
                        }
                        return 1;
                    }
                    int iCoerceAtLeast = RangesKt.coerceAtLeast((int) (i * 1.5f), 1);
                    int iCoerceAtLeast2 = RangesKt.coerceAtLeast((int) (i2 * 1.5f), 1);
                    int i5 = 1;
                    while (true) {
                        int i6 = i5 * 2;
                        if (i3 / i6 < iCoerceAtLeast || i4 / i6 < iCoerceAtLeast2) {
                            break;
                        }
                        i5 = i6;
                    }
                    Log.d("BitmapUtil", "calculateInSampleSizeFromFd: src=" + i3 + AnimatedProperty.PROPERTY_NAME_X + i4 + ", req=" + i + AnimatedProperty.PROPERTY_NAME_X + i2 + ", safeReq=" + iCoerceAtLeast + AnimatedProperty.PROPERTY_NAME_X + iCoerceAtLeast2 + ", sample=" + i5);
                    try {
                        Os.close(fileDescriptor);
                    } catch (ErrnoException unused2) {
                    }
                    return i5;
                } catch (Exception e) {
                    Log.w("BitmapUtil", "calculateInSampleSizeFromFd: fallback to 1 due to " + e);
                    try {
                        Os.close(fileDescriptor);
                    } catch (ErrnoException unused3) {
                    }
                    return 1;
                }
            } catch (Throwable th) {
                try {
                    Os.close(fileDescriptor);
                } catch (ErrnoException unused4) {
                }
                throw th;
            }
        } catch (ErrnoException e2) {
            Log.w("BitmapUtil", "calculateInSampleSizeFromFd: dup failed: " + e2);
            return 1;
        }
    }

    public final Bitmap compress(Bitmap bitmap, int i, int i2) {
        boolean z;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= 0 || height <= 0) {
            Log.w("BitmapUtil", "compress: src size invalid ! reqWidth=" + width + ", reqHeight=" + height);
            return null;
        }
        float f = width;
        float f2 = i / f;
        float f3 = height;
        float f4 = i2 / f3;
        Log.i("BitmapUtil", "compress: src width=" + bitmap.getWidth() + ",height=" + bitmap.getHeight());
        if (f2 > f4) {
            z = true;
        } else {
            z = false;
            f2 = f4;
        }
        if (z) {
            int iRoundToInt = MathKt.roundToInt(f3 * f2);
            int iAbs = Math.abs(iRoundToInt - i2) / 2;
            Log.i("BitmapUtil", "compress: scaleByWidth:scaleHeight=" + iRoundToInt + ", top=" + iAbs);
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, iRoundToInt, true);
            if (bitmap.getWidth() != bitmapCreateScaledBitmap.getWidth() || bitmap.getHeight() != bitmapCreateScaledBitmap.getHeight()) {
                bitmap.recycle();
            }
            BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
            Intrinsics.checkNotNull(bitmapCreateScaledBitmap);
            return bitmapCreator.createBitmap(bitmapCreateScaledBitmap, 0, iAbs, i, i2);
        }
        int iRoundToInt2 = MathKt.roundToInt(f * f2);
        int iAbs2 = Math.abs(iRoundToInt2 - i) / 2;
        Log.i("BitmapUtil", "compress: scaleByHeight:scaleWidth=" + iRoundToInt2 + ", left=" + iAbs2);
        Bitmap bitmapCreateScaledBitmap2 = Bitmap.createScaledBitmap(bitmap, iRoundToInt2, i2, true);
        if (bitmap.getWidth() != bitmapCreateScaledBitmap2.getWidth() || bitmap.getHeight() != bitmapCreateScaledBitmap2.getHeight()) {
            bitmap.recycle();
        }
        BitmapCreator bitmapCreator2 = BitmapCreator.INSTANCE;
        Intrinsics.checkNotNull(bitmapCreateScaledBitmap2);
        return bitmapCreator2.createBitmap(bitmapCreateScaledBitmap2, iAbs2, 0, i, i2);
    }

    public final Bitmap createVideoLastFrameThumbnail(File videoFile, Size size) {
        Long longOrNull;
        Intrinsics.checkNotNullParameter(videoFile, "videoFile");
        Intrinsics.checkNotNullParameter(size, "size");
        Bitmap scaledFrameAtTime = null;
        if (!videoFile.exists()) {
            return null;
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                try {
                    mediaMetadataRetriever.setDataSource(videoFile.getAbsolutePath());
                    String strExtractMetadata = mediaMetadataRetriever.extractMetadata(9);
                    long jLongValue = (strExtractMetadata == null || (longOrNull = StringsKt.toLongOrNull(strExtractMetadata)) == null) ? 0L : longOrNull.longValue();
                    scaledFrameAtTime = mediaMetadataRetriever.getScaledFrameAtTime(jLongValue > 0 ? jLongValue * 1000 : -1L, 3, size.getWidth(), size.getHeight());
                    mediaMetadataRetriever.release();
                } catch (Exception e) {
                    Log.e("BitmapUtil", "createVideoLastFrameThumbnail failed path=" + videoFile.getAbsolutePath(), e);
                    mediaMetadataRetriever.release();
                }
            } catch (Exception e2) {
                Log.e("BitmapUtil", "retriever release failed", e2);
            }
            return scaledFrameAtTime;
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
                throw th;
            } catch (Exception e3) {
                Log.e("BitmapUtil", "retriever release failed", e3);
                throw th;
            }
        }
    }

    public final Bitmap cropBitmap(Bitmap bitmap, int i, int i2, int i3, int i4) {
        if (bitmap == null) {
            return null;
        }
        if (i2 <= i || i4 <= i3) {
            Log.w("BitmapUtil", "cropBitmap: size invalid! left=" + i + ",right=" + i2 + ",top=" + i3 + ",bottom=" + i4);
            return null;
        }
        int iCoerceIn = RangesKt.coerceIn(i, 0, bitmap.getWidth());
        int iCoerceIn2 = RangesKt.coerceIn(i3, 0, bitmap.getHeight());
        int iCoerceIn3 = RangesKt.coerceIn(i2 - i, 0, bitmap.getWidth() - iCoerceIn);
        int iCoerceIn4 = RangesKt.coerceIn(i4 - i3, 0, bitmap.getHeight() - iCoerceIn2);
        if (iCoerceIn3 <= 0 || iCoerceIn4 <= 0) {
            Log.w("BitmapUtil", "cropBitmap: clamped size invalid! left=" + i + ",right=" + i2 + ",top=" + i3 + ",bottom=" + i4 + ",bw=" + bitmap.getWidth() + ",bh=" + bitmap.getHeight());
            return null;
        }
        try {
            return BitmapCreator.INSTANCE.createBitmap(bitmap, iCoerceIn, iCoerceIn2, iCoerceIn3, iCoerceIn4);
        } catch (Exception e) {
            Log.w("BitmapUtil", "cropBitmap: left=" + iCoerceIn + ",top=" + iCoerceIn2 + ",w=" + iCoerceIn3 + ",h=" + iCoerceIn4 + ",bw=" + bitmap.getWidth() + ",bh=" + bitmap.getHeight());
            Log.e("BitmapUtil", "cropBitmap: ", e);
            return null;
        }
    }

    public final Bitmap fromDrawable(Drawable drawable, int i, int i2) {
        if (drawable == null) {
            return null;
        }
        try {
            BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
            if (bitmapDrawable != null) {
                Log.d("BitmapUtil", "fromDrawable: convert with BitmapDrawable");
                return tryCompress(bitmapDrawable.getBitmap(), i, i2);
            }
            Log.d("BitmapUtil", "fromDrawable: convert with canvas");
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888, false, false, 24, null);
            Canvas canvas = new Canvas(bitmapCreateBitmap$default);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(canvas);
            return tryCompress(bitmapCreateBitmap$default, i, i2);
        } catch (Exception e) {
            Log.e("BitmapUtil", "fromDrawable: " + e);
            return null;
        }
    }

    public final Bitmap fromFile(String str, int i, int i2, Context context) {
        BitmapFactory.Options options;
        if (str != null && str.length() != 0) {
            if (context != null) {
                try {
                    int i3 = context.getResources().getDisplayMetrics().densityDpi;
                    options = new BitmapFactory.Options();
                    options.inDensity = i3;
                    options.inTargetDensity = i3;
                } catch (Exception e) {
                    Log.e("BitmapUtil", "fromFile: " + e);
                }
            } else {
                options = null;
            }
            Bitmap bitmapDecodeFile = BitmapCreator.INSTANCE.decodeFile(str, options);
            Log.i("BitmapUtil", "fromFile: filePath=" + str + ", bitmap?.width=" + (bitmapDecodeFile != null ? Integer.valueOf(bitmapDecodeFile.getWidth()) : null) + ",bitmap?.height=" + (bitmapDecodeFile != null ? Integer.valueOf(bitmapDecodeFile.getHeight()) : null));
            return tryCompress(bitmapDecodeFile, i, i2);
        }
        return null;
    }

    public final Bitmap fromFile2(String str, int i, int i2, Bitmap.Config config) {
        if (str == null) {
            return null;
        }
        try {
            Log.d("BitmapUtil", "reqWidth = " + i);
            Log.d("BitmapUtil", "reqHeight = " + i2);
            BitmapFactory.Options options = new BitmapFactory.Options();
            if (config != null) {
                options.inPreferredConfig = config;
            } else {
                options.inPreferredConfig = Bitmap.Config.RGB_565;
            }
            options.inJustDecodeBounds = true;
            BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
            bitmapCreator.decodeFile(str, options);
            Log.d("BitmapUtil", "outHeight = " + options.outHeight);
            Log.d("BitmapUtil", "outWidth = " + options.outWidth);
            float fCoerceAtLeast = RangesKt.coerceAtLeast(((float) options.outHeight) / ((float) i2), ((float) options.outWidth) / ((float) i));
            Log.d("BitmapUtil", "inSampleSize = " + fCoerceAtLeast);
            int iRoundToInt = MathKt.roundToInt(RangesKt.coerceAtLeast(fCoerceAtLeast, 1.0f));
            options.inSampleSize = iRoundToInt;
            Log.d("BitmapUtil", "options.inSampleSize = " + iRoundToInt);
            options.inJustDecodeBounds = false;
            return bitmapCreator.decodeFile(str, options);
        } catch (Exception e) {
            Log.e("BitmapUtil", "fromFile: " + e);
            return null;
        }
    }

    public final Matrix getBitmapMatrix(int i, int i2, int i3, int i4, ImageView.ScaleType scaleType) {
        float f;
        float f2;
        float f3;
        float f4;
        Intrinsics.checkNotNullParameter(scaleType, "scaleType");
        Log.d("BitmapUtil", "getCenterCropMatrix: srcWidth=" + i + " srcHeight=" + i2 + " dstWidth=" + i3 + " dstHeight=" + i4);
        if (i * i2 <= 0) {
            return null;
        }
        Matrix matrix = new Matrix();
        int i5 = WhenMappings.$EnumSwitchMapping$0[scaleType.ordinal()];
        float f5 = 0.0f;
        if (i5 != 1) {
            if (i5 != 2) {
                f2 = 1.0f;
                f4 = 1.0f;
            } else {
                f2 = i3 / i;
                f4 = i4 / i2;
            }
            f3 = 0.0f;
        } else {
            if (i * i4 > i3 * i2) {
                float f6 = i4 / i2;
                f5 = (i3 - (i * f6)) * 0.5f;
                f = 0.0f;
                f2 = f6;
            } else {
                float f7 = i3 / i;
                f = (i4 - (i2 * f7)) * 0.5f;
                f2 = f7;
            }
            f3 = f;
            f4 = f2;
        }
        matrix.postScale(f2, f4);
        matrix.postTranslate(Math.round(f5), Math.round(f3));
        return matrix;
    }

    public final Point getBitmapSize(String str) {
        Point point = new Point();
        if (str != null) {
            if (!FileUtil.isFileExist(str)) {
                String presetWallpaperAbsolutePath = TemplateFilePathGenerator.Companion.getPresetWallpaperAbsolutePath(str);
                if (!FileUtil.isFileExist(presetWallpaperAbsolutePath)) {
                    Log.w("BitmapUtil", "getBitmapSize: file not exist:" + str);
                    return point;
                }
                str = presetWallpaperAbsolutePath;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inJustDecodeBounds = true;
            try {
                BitmapCreator.INSTANCE.decodeFile(str, options);
                point.x = options.outWidth;
                point.y = options.outHeight;
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                Log.w("BitmapUtil", "getBitmapSize failed , filePath = " + ((Object) str) + " ");
                Log.e("BitmapUtil", "getBitmapSize: ", e);
            }
        }
        return point;
    }

    public final int getColorMode(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        return MiuiColorScheme.getIsDeepWallpaper(bitmap, false) ? 0 : 2;
    }

    public final Bitmap parcelFileDescriptorToBitmap(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, Bitmap.Config preferredConfig, ColorSpace preferredColorSpace) throws IOException {
        Intrinsics.checkNotNullParameter(preferredConfig, "preferredConfig");
        Intrinsics.checkNotNullParameter(preferredColorSpace, "preferredColorSpace");
        try {
            if (parcelFileDescriptor == null) {
                Log.i("BitmapUtil", "parcelFileDescriptor is null");
                return null;
            }
            try {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                if (fileDescriptor != null) {
                    if (!fileDescriptor.valid()) {
                        fileDescriptor = null;
                    }
                    if (fileDescriptor != null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = preferredConfig;
                        options.inPreferredColorSpace = preferredColorSpace;
                        options.inSampleSize = (i <= 0 || i2 <= 0 || !ThermalGate.INSTANCE.shouldDownsample()) ? 1 : INSTANCE.calculateInSampleSizeFromFd(fileDescriptor, i, i2);
                        Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                        if (bitmapDecodeFileDescriptor == null) {
                            Log.w("BitmapUtil", "decodeFileDescriptor returned null");
                            parcelFileDescriptor.close();
                            return null;
                        }
                        Bitmap bitmapTryCompress = tryCompress(BitmapCreator.INSTANCE.ensureSoftwareBitmap(bitmapDecodeFileDescriptor), i, i2);
                        try {
                            parcelFileDescriptor.close();
                            return bitmapTryCompress;
                        } catch (IOException e) {
                            Log.e("BitmapUtil", "Failed to close descriptor: " + e);
                            return bitmapTryCompress;
                        }
                    }
                }
                Log.w("BitmapUtil", "Invalid file descriptor");
                parcelFileDescriptor.close();
                return null;
            } catch (IOException e2) {
                Log.e("BitmapUtil", "parcelFileDescriptorToBitmap: " + e2);
                e2.printStackTrace();
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e3) {
                    Log.e("BitmapUtil", "Failed to close descriptor: " + e3);
                }
                return null;
            }
        } catch (Throwable th) {
            try {
                parcelFileDescriptor.close();
            } catch (IOException e4) {
                Log.e("BitmapUtil", "Failed to close descriptor: " + e4);
            }
            throw th;
        }
    }

    public final void recycleBitmap(Bitmap bitmap, String tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        if (bitmap != null) {
            bitmap.recycle();
            Log.w("BitmapUtil", "recycleBitmap: bitmap=" + bitmap + " tag=" + tag);
        }
    }

    public final Bitmap resizeImageWallpaper(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i = options.outWidth;
        if (i < 3333 && options.outHeight < 3333) {
            options.inJustDecodeBounds = false;
            return BitmapCreator.INSTANCE.decodeFile(str, options);
        }
        options.inSampleSize = (int) (((double) ((RangesKt.coerceAtLeast(i, options.outHeight) * 1.0f) / 3333)) + 0.5d);
        options.inJustDecodeBounds = false;
        return BitmapCreator.INSTANCE.decodeFile(str, options);
    }

    public final boolean toFile(Bitmap bitmap, String str, Bitmap.CompressFormat compressFormat, int i, boolean z) throws Throwable {
        Log.i("BitmapUtil", "toFile: filePath=" + str);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                File file = new File(str);
                FileUtil.makeFileExists(file);
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    bitmap.compress(compressFormat, i, fileOutputStream2);
                    if (z) {
                        fileOutputStream2.getFD().sync();
                    }
                    IOUtils.closeQuietly((OutputStream) fileOutputStream2);
                    Log.i("BitmapUtil", "toFile: file " + str + " save completed");
                    return true;
                } catch (Exception e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    Log.e("BitmapUtil", "toFile: " + e);
                    IOUtils.closeQuietly((OutputStream) fileOutputStream);
                    Log.i("BitmapUtil", "toFile: file " + str + " save completed");
                    return false;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    IOUtils.closeQuietly((OutputStream) fileOutputStream);
                    Log.i("BitmapUtil", "toFile: file " + str + " save completed");
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final boolean toFileByFileDescriptor(Bitmap bitmap, ParcelFileDescriptor parcelFileDescriptor, Bitmap.CompressFormat compressFormat, int i) {
        if (parcelFileDescriptor == null) {
            Log.i("BitmapUtil", "toFile: fileDescriptor is null");
            return false;
        }
        try {
            Log.i("BitmapUtil", "toFile: fileDescriptor=" + parcelFileDescriptor);
            bitmap.compress(compressFormat, i, new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor));
            return true;
        } catch (Exception e) {
            Log.e("BitmapUtil", "toFile: " + e);
            return false;
        }
    }

    public final InputStream toInputStream(Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public final boolean toJPEG(Bitmap bitmap, ParcelFileDescriptor parcelFileDescriptor) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        return toFileByFileDescriptor$default(this, bitmap, parcelFileDescriptor, Bitmap.CompressFormat.JPEG, 0, 8, null);
    }

    public final boolean toJPEG(Bitmap bitmap, String filePath) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        return toFile$default(this, bitmap, filePath, Bitmap.CompressFormat.JPEG, 0, false, 24, null);
    }

    public final boolean toPNG(Bitmap bitmap, String filePath) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        return toFile$default(this, bitmap, filePath, Bitmap.CompressFormat.PNG, 0, false, 24, null);
    }

    public final boolean toWebp(Bitmap bitmap, String filePath, boolean z) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        return toFile(bitmap, filePath, Bitmap.CompressFormat.WEBP_LOSSLESS, 0, z);
    }

    public final Bitmap tryCompress(Bitmap bitmap, int i, int i2) {
        if (bitmap == null || bitmap.isRecycled()) {
            Log.w("BitmapUtil", "tryCompress: invalid bitmap");
            return null;
        }
        Log.i("BitmapUtil", "tryCompress: reqWidth=" + i + ", reqHeight=" + i2);
        return (i == -1 || i2 == -1 || (i == bitmap.getWidth() && i2 == bitmap.getHeight())) ? bitmap : compress(bitmap, i, i2);
    }
}
