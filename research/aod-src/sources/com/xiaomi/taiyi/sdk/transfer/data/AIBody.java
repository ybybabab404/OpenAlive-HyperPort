package com.xiaomi.taiyi.sdk.transfer.data;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.common.DataFormat;
import com.xiaomi.taiyi.sdk.transfer.core.IAICallback;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIBody extends AIPackage {
    public static final CREATOR CREATOR = new CREATOR(null);
    private static final long MAX_BYTE_LENGTH = 524288;
    private static final long MAX_JSON_LENGTH = 131072;
    private static final long MAX_TEXT_LENGTH = 262144;
    private static final String TAG = "AIBody";
    private HashMap<String, AIFrame> frameMap;
    private String jsonResult;
    private String jsonSource;

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<AIBody> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIBody createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new AIBody(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIBody[] newArray(int i) {
            return new AIBody[i];
        }
    }

    public AIBody() {
        this.jsonSource = "";
        this.jsonResult = "";
        this.frameMap = new HashMap<>();
    }

    private AIBody(Parcel parcel) {
        super(parcel);
        this.jsonSource = "";
        this.jsonResult = "";
    }

    public /* synthetic */ AIBody(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    public static /* synthetic */ Pair collect$default(AIBody aIBody, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return aIBody.collect(z);
    }

    public static /* synthetic */ AIFrame getFrame$default(AIBody aIBody, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return aIBody.getFrame(str, z);
    }

    public static /* synthetic */ int putFrame$default(AIBody aIBody, String str, AIFrame aIFrame, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return aIBody.putFrame(str, aIFrame, z);
    }

    private final String readJson(String str) {
        HashMap<String, AIFrame> map = this.frameMap;
        if (map == null) {
            Intrinsics.throwUninitializedPropertyAccessException("frameMap");
            map = null;
        }
        AIFrame aIFrame = map.get(str);
        if ((aIFrame != null ? aIFrame.getFormat() : null) != DataFormat.ONE_BYTE_ARRAY) {
            SdkLog.i(TAG, "readJson " + str + " from bundle");
            String string = getString(str, "");
            return string == null ? "" : string;
        }
        SdkLog.i(TAG, "readJson " + str + " from frame. " + aIFrame);
        Object value = aIFrame.getValue();
        byte[] bArr = value instanceof byte[] ? (byte[]) value : null;
        return bArr != null ? new String(bArr, Charsets.UTF_8) : "";
    }

    private final void writeJson(String str, String str2) {
        if (str2.length() == 0 || str2.length() < MAX_JSON_LENGTH) {
            SdkLog.i(TAG, "writeJson " + str + " to bundle. " + str2.length());
            putString(str, str2);
            return;
        }
        HashMap<String, AIFrame> map = this.frameMap;
        HashMap<String, AIFrame> map2 = null;
        if (map == null) {
            Intrinsics.throwUninitializedPropertyAccessException("frameMap");
            map = null;
        }
        if (map.get(str) != null) {
            return;
        }
        HashMap<String, AIFrame> map3 = this.frameMap;
        if (map3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("frameMap");
        } else {
            map2 = map3;
        }
        AIFrame aIFrameCreateLongTextFrame = AIFrame.CREATOR.createLongTextFrame(str, str2);
        SdkLog.i(TAG, "writeJson " + str + " to frame. " + aIFrameCreateLongTextFrame);
        map2.put(str, aIFrameCreateLongTextFrame);
    }

    public final Pair<Integer, List<AIFrame>> collect() {
        return collect$default(this, false, 1, null);
    }

    public final synchronized Pair<Integer, List<AIFrame>> collect(boolean z) {
        int i = 0;
        if (!isShareMemoryEnable()) {
            return new Pair<>(0, CollectionsKt.emptyList());
        }
        ArrayList arrayList = new ArrayList();
        HashMap<String, AIFrame> map = this.frameMap;
        if (map == null) {
            Intrinsics.throwUninitializedPropertyAccessException("frameMap");
            map = null;
        }
        Iterator<Map.Entry<String, AIFrame>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            AIFrame value = it.next().getValue();
            SdkLog.i(TAG, "collect " + value);
            if (value.hasRead() || value.isHighRead()) {
                arrayList.add(value);
            } else {
                ShareMemoryPool memoryPool = getMemoryPool();
                int i2 = memoryPool != null ? memoryPool.read(value, z) : ErrorCode.ERROR_SHARE_MEMORY_NOT_FOUND;
                if (i2 != 0) {
                    i = i2;
                } else {
                    arrayList.add(value);
                }
            }
        }
        this.jsonSource = readJson(AIKeys.KEY_JSON_SOURCE);
        this.jsonResult = readJson(AIKeys.KEY_JSON_RESULT);
        return new Pair<>(Integer.valueOf(i), arrayList);
    }

    public final synchronized Pair<Integer, List<AIFrame>> distribute() {
        int i = 0;
        if (!isShareMemoryEnable()) {
            return new Pair<>(0, CollectionsKt.emptyList());
        }
        ArrayList arrayList = new ArrayList();
        writeJson(AIKeys.KEY_JSON_SOURCE, this.jsonSource);
        writeJson(AIKeys.KEY_JSON_RESULT, this.jsonResult);
        HashMap<String, AIFrame> map = this.frameMap;
        if (map == null) {
            Intrinsics.throwUninitializedPropertyAccessException("frameMap");
            map = null;
        }
        Iterator<Map.Entry<String, AIFrame>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            AIFrame value = it.next().getValue();
            SdkLog.i(TAG, "distribute " + value);
            if (value.hasWrite() || value.isHighWrite()) {
                arrayList.add(value);
            } else {
                ShareMemoryPool memoryPool = getMemoryPool();
                int iWrite = memoryPool != null ? memoryPool.write(value) : ErrorCode.ERROR_SHARE_MEMORY_NOT_FOUND;
                if (iWrite != 0) {
                    i = iWrite;
                } else {
                    arrayList.add(value);
                }
            }
        }
        return new Pair<>(Integer.valueOf(i), arrayList);
    }

    public final List<AIFrame> free() {
        ArrayList arrayList = new ArrayList();
        HashMap<String, AIFrame> map = this.frameMap;
        if (map == null) {
            Intrinsics.throwUninitializedPropertyAccessException("frameMap");
            map = null;
        }
        Collection<AIFrame> collectionValues = map.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        for (AIFrame aIFrame : collectionValues) {
            ShareMemoryPool memoryPool = getMemoryPool();
            if (memoryPool != null) {
                Intrinsics.checkNotNull(aIFrame);
                memoryPool.free(aIFrame);
            }
            arrayList.add(aIFrame);
        }
        return arrayList;
    }

    public final void freeFrame(AIFrame frame) {
        Intrinsics.checkNotNullParameter(frame, "frame");
        ShareMemoryPool memoryPool = getMemoryPool();
        if (memoryPool != null) {
            memoryPool.free(frame);
        }
    }

    public final IAICallback getCallback() {
        IBinder binder = getBinder(AIKeys.KEY_AI_CALLBACK, null);
        if (binder == null) {
            return null;
        }
        return IAICallback.Stub.asInterface(binder);
    }

    public final byte[] getData(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        byte[] byteArray = getByteArray(key, null);
        if (byteArray != null) {
            return byteArray;
        }
        AIFrame frame$default = getFrame$default(this, key, false, 2, null);
        Object value = frame$default != null ? frame$default.getValue() : null;
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        return null;
    }

    public final int getErrorCode() {
        return getInt(AIKeys.KEY_ERROR_CODE, 0);
    }

    public final String getErrorMsg() {
        String string = getString(AIKeys.KEY_ERROR_MSG, "");
        return string == null ? "" : string;
    }

    public final synchronized AIFrame getFrame(String key, boolean z) {
        ShareMemoryPool memoryPool;
        try {
            Intrinsics.checkNotNullParameter(key, "key");
            HashMap<String, AIFrame> map = null;
            if (!z) {
                HashMap<String, AIFrame> map2 = this.frameMap;
                if (map2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("frameMap");
                } else {
                    map = map2;
                }
                return map.get(key);
            }
            HashMap<String, AIFrame> map3 = this.frameMap;
            if (map3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("frameMap");
                map3 = null;
            }
            AIFrame aIFrame = map3.get(key);
            if (aIFrame == null) {
                return null;
            }
            if (!aIFrame.hasRead()) {
                if (aIFrame.getReadAsBuffer()) {
                    SdkLog.w(TAG, "getFrame " + key + " readAsBuffer, you should read by openFrame!");
                    return aIFrame;
                }
                if (isShareMemoryEnable() && (memoryPool = getMemoryPool()) != null) {
                    ShareMemoryPool.read$default(memoryPool, aIFrame, false, 2, null);
                    return aIFrame;
                }
            }
            return aIFrame;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final String getJsonResult() {
        return this.jsonResult;
    }

    public final String getJsonSource() {
        return this.jsonSource;
    }

    public final ShareMemoryPool getMemoryPool() {
        return (ShareMemoryPool) getParcelable(AIKeys.KEY_SHARE_MEMORY_POOL, ShareMemoryPool.class, null);
    }

    public final int getSpecCode() {
        return getInt(AIKeys.KEY_SPEC_CODE, 0);
    }

    public final String getSpecInfo() {
        String string = getString(AIKeys.KEY_SPEC_INFO, "");
        return string == null ? "" : string;
    }

    public final String getText(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        String string = getString(key, null);
        if (string != null) {
            return string;
        }
        AIFrame frame$default = getFrame$default(this, key, false, 2, null);
        Object value = frame$default != null ? frame$default.getValue() : null;
        byte[] bArr = value instanceof byte[] ? (byte[]) value : null;
        if (bArr != null) {
            return new String(bArr, Charsets.UTF_8);
        }
        return null;
    }

    public final boolean isShareMemoryEnable() {
        return getBoolean(AIKeys.KEY_SHARE_MEMORY_ENABLE, true);
    }

    public final ByteBuffer mapFrame(AIFrame frame) {
        Intrinsics.checkNotNullParameter(frame, "frame");
        if (!isShareMemoryEnable()) {
            SdkLog.i(TAG, "openBuffer for " + frame + " with array.");
            return frame.openArray$base_release();
        }
        ShareMemoryPool memoryPool = getMemoryPool();
        if (memoryPool == null) {
            return null;
        }
        Pair<ByteBuffer, Integer> map = memoryPool.map(frame);
        ByteBuffer byteBuffer = (ByteBuffer) map.component1();
        SdkLog.i(TAG, "openBuffer for " + frame + " with sm. code: " + ((Number) map.component2()).intValue());
        return byteBuffer;
    }

    public final void putCallback(final Function1<? super AIResponse, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        putBinder(AIKeys.KEY_AI_CALLBACK, new IAICallback.Stub() { // from class: com.xiaomi.taiyi.sdk.transfer.data.AIBody.putCallback.1
            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICallback
            public void onResponse(AIResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                callback.invoke(response);
            }
        });
    }

    public final void putData(String key, byte[] value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        if (value.length <= MAX_BYTE_LENGTH) {
            putByteArray(key, value);
        } else {
            putFrame$default(this, key, AIFrame.CREATOR.createBigDataFrame(key, value), false, 4, null);
        }
    }

    public final int putFrame(String key, AIFrame frame) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(frame, "frame");
        return putFrame$default(this, key, frame, false, 4, null);
    }

    public final synchronized int putFrame(String key, AIFrame frame, boolean z) {
        try {
            Intrinsics.checkNotNullParameter(key, "key");
            Intrinsics.checkNotNullParameter(frame, "frame");
            HashMap<String, AIFrame> map = this.frameMap;
            HashMap<String, AIFrame> map2 = null;
            if (map == null) {
                Intrinsics.throwUninitializedPropertyAccessException("frameMap");
                map = null;
            }
            AIFrame aIFrame = map.get(key);
            if (aIFrame != null && aIFrame.hasWrite()) {
                return ErrorCode.ERROR_FRAME_REPEAT;
            }
            HashMap<String, AIFrame> map3 = this.frameMap;
            if (map3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("frameMap");
            } else {
                map2 = map3;
            }
            map2.put(key, frame);
            if (frame.getWriteInBuffer()) {
                SdkLog.w(TAG, "putFrame " + key + " writeInBuffer, you should write by openFrame!");
                return 0;
            }
            if (!z || frame.hasWrite() || !isShareMemoryEnable()) {
                return 0;
            }
            ShareMemoryPool memoryPool = getMemoryPool();
            if (memoryPool == null) {
                return ErrorCode.ERROR_SHARE_MEMORY_NOT_FOUND;
            }
            return memoryPool.write(frame);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void putText(String key, String value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        if (value.length() <= MAX_TEXT_LENGTH) {
            putString(key, value);
        } else {
            putFrame$default(this, key, AIFrame.CREATOR.createLongTextFrame(key, value), false, 4, null);
        }
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.data.AIPackage
    public void readFromParcel(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        super.readFromParcel(parcel);
        this.frameMap = new HashMap<>();
        Bundle bundle = parcel.readBundle(AIPackage.class.getClassLoader());
        if (bundle == null) {
            bundle = new Bundle();
        }
        Set<String> setKeySet = bundle.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "keySet(...)");
        for (String str : setKeySet) {
            AIFrame aIFrame = (AIFrame) bundle.getParcelable(str, AIFrame.class);
            if (aIFrame != null) {
                HashMap<String, AIFrame> map = this.frameMap;
                if (map == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("frameMap");
                    map = null;
                }
                map.put(str, aIFrame);
            }
        }
    }

    public final void setErrorCode(int i) {
        putInt(AIKeys.KEY_ERROR_CODE, i);
    }

    public final void setErrorMsg(String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        putString(AIKeys.KEY_ERROR_MSG, msg);
    }

    public final void setJsonResult(String json) {
        Intrinsics.checkNotNullParameter(json, "json");
        this.jsonResult = json;
    }

    public final void setJsonSource(String json) {
        Intrinsics.checkNotNullParameter(json, "json");
        this.jsonSource = json;
    }

    public final void setMemoryPool(ShareMemoryPool pool) {
        Intrinsics.checkNotNullParameter(pool, "pool");
        putParcelable(AIKeys.KEY_SHARE_MEMORY_POOL, pool);
    }

    public final void setShareMemoryState(boolean z) {
        putBoolean(AIKeys.KEY_SHARE_MEMORY_ENABLE, z);
    }

    public final void setSpecCode(int i) {
        putInt(AIKeys.KEY_SPEC_CODE, i);
    }

    public final void setSpecInfo(String info) {
        Intrinsics.checkNotNullParameter(info, "info");
        putString(AIKeys.KEY_SPEC_INFO, info);
    }

    public final void unmapFrame(AIFrame frame) {
        ShareMemoryPool memoryPool;
        Intrinsics.checkNotNullParameter(frame, "frame");
        if (!isShareMemoryEnable() || (memoryPool = getMemoryPool()) == null) {
            return;
        }
        memoryPool.unmap(frame);
        SdkLog.i(TAG, "unmapBuffer for " + frame + " with sm.");
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.data.AIPackage, android.os.Parcelable
    public void writeToParcel(Parcel dest, int i) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        super.writeToParcel(dest, i);
        Bundle bundle = new Bundle();
        HashMap<String, AIFrame> map = this.frameMap;
        if (map == null) {
            Intrinsics.throwUninitializedPropertyAccessException("frameMap");
            map = null;
        }
        for (Map.Entry<String, AIFrame> entry : map.entrySet()) {
            bundle.putParcelable(entry.getKey(), entry.getValue());
        }
        dest.writeBundle(bundle);
    }
}
