package com.miui.keyguard.editor.edit.wallpaper;

import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.util.DataUtilKt;
import com.miui.keyguard.editor.utils.BitmapCreator;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.WideColorGamutManager;
import com.miui.keyguard.editor.utils.segment.ImageSegmentHandler;
import com.miui.maml.folme.AnimatedProperty;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: GLRenderHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GLRenderHelper {
    public static final GLRenderHelper INSTANCE = new GLRenderHelper();

    public static /* synthetic */ Bitmap getCachedAlphaDepth$default(GLRenderHelper gLRenderHelper, String str, Integer num, Integer num2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            num = 0;
        }
        if ((i & 4) != 0) {
            num2 = 0;
        }
        return gLRenderHelper.getCachedAlphaDepth(str, num, num2, str2);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0081  */
    public final Bitmap getCachedAlphaDepth(String str, Integer num, Integer num2, String header) throws Throwable {
        Integer numValueOf;
        Integer numValueOf2;
        FileInputStream fileInputStream;
        Intrinsics.checkNotNullParameter(header, "header");
        FileChannel channel = null;
        if (str == null || !new File(str).exists()) {
            DataUtilKt.throwIfDebug("not exist path " + str);
            return null;
        }
        if (num == null || num2 == null || num.intValue() <= 0 || num2.intValue() <= 0) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str2 = String.format("^(%1s)_(?<width>\\d+)x(?<height>\\d+).(alpha)$", Arrays.copyOf(new Object[]{header}, 1));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            Matcher matcher = Pattern.compile(str2).matcher(new File(str).getName());
            if (matcher.find()) {
                String strGroup = matcher.group("width");
                numValueOf = strGroup != null ? Integer.valueOf(Integer.parseInt(strGroup)) : num;
                String strGroup2 = matcher.group("height");
                if (strGroup2 != null) {
                    numValueOf2 = Integer.valueOf(Integer.parseInt(strGroup2));
                }
            } else {
                numValueOf = num;
            }
            numValueOf2 = num2;
        } else {
            numValueOf = num;
            numValueOf2 = num2;
        }
        if (numValueOf == null || numValueOf.intValue() <= 0 || numValueOf2 == null || numValueOf2.intValue() <= 0) {
            DataUtilKt.throwIfDebug("error file name " + str);
            return null;
        }
        try {
            fileInputStream = new FileInputStream(new File(str));
            try {
                channel = fileInputStream.getChannel();
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) channel.size());
                while (channel.read(byteBufferAllocate) > 0) {
                }
                byte[] bArrArray = byteBufferAllocate.array();
                Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, numValueOf.intValue(), numValueOf2.intValue(), Bitmap.Config.ALPHA_8, WideColorGamutManager.INSTANCE.isP3File(str), false, 16, null);
                bitmapCreateBitmap$default.copyPixelsFromBuffer(ByteBuffer.wrap(bArrArray));
                FileUtil.close(channel, fileInputStream);
                Log.i("GLRenderHelper", "getCachedAlphaDepth: end " + bitmapCreateBitmap$default);
                return bitmapCreateBitmap$default;
            } catch (Throwable th) {
                th = th;
                FileUtil.close(channel, fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
        }
    }

    public final Bitmap getDepthBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Bitmap bitmapDepthSegment = ImageSegmentHandler.INSTANCE.depthSegment(bitmap);
        return bitmapDepthSegment == null ? bitmap : bitmapDepthSegment;
    }

    public final String getDepthBitmapName(Bitmap bitmap, String str) {
        String str2 = str + "_" + bitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmap.getHeight() + ".alpha";
        Intrinsics.checkNotNullExpressionValue(str2, "toString(...)");
        return str2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final String saveAlphaBitmap(Bitmap bitmap, String str, Boolean bool, String header) throws Throwable {
        FileOutputStream fileOutputStream;
        Intrinsics.checkNotNullParameter(header, "header");
        Object obj = null;
        if (str == null || str.length() == 0 || bitmap == null) {
            DataUtilKt.throwIfDebug("save depth bitmap error " + bitmap + " " + str);
            return null;
        }
        if (Intrinsics.areEqual(bool, Boolean.TRUE)) {
            str = new File(str).getAbsolutePath() + File.separator + getDepthBitmapName(bitmap, header);
        }
        FileUtil.makeFileExists(str);
        try {
            try {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmap.getWidth() * bitmap.getHeight());
                bitmap.copyPixelsToBuffer(byteBufferAllocate);
                byte[] bArrArray = byteBufferAllocate.array();
                fileOutputStream = new FileOutputStream(str);
                try {
                    fileOutputStream.write(bArrArray);
                    FileUtil.close(fileOutputStream);
                    return str;
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    Log.e("GLRenderHelper", "saveAlphaBitmap: ", e);
                    FileUtil.close(fileOutputStream);
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                obj = bitmap;
                FileUtil.close(obj);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            FileUtil.close(obj);
            throw th;
        }
    }
}
