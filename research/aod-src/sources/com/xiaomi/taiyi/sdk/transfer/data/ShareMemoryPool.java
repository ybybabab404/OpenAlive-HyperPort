package com.xiaomi.taiyi.sdk.transfer.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.system.ErrnoException;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
public final class ShareMemoryPool implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    private static final String TAG = "ShareMemoryPool";
    private String clientId;
    private ArrayList<ShareMemoryHolder> memoryList;
    private int[] pool;

    @Metadata
    public static final class CREATOR implements Parcelable.Creator<ShareMemoryPool> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMemoryPool createFromParcel(Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new ShareMemoryPool(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShareMemoryPool[] newArray(int i) {
            return new ShareMemoryPool[i];
        }
    }

    private ShareMemoryPool() {
        this.clientId = "";
        this.pool = new int[0];
        this.memoryList = new ArrayList<>();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ShareMemoryPool(Parcel parcel) {
        this();
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        readFromParcel(parcel);
    }

    public ShareMemoryPool(String id, int[] pool) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(pool, "pool");
        this.clientId = "";
        this.pool = new int[0];
        this.memoryList = new ArrayList<>();
        this.clientId = id;
        this.pool = pool;
    }

    private final Pair<ShareMemoryHolder, Integer> create(AIFrame aIFrame) {
        Object next;
        int byteLength = aIFrame.getByteLength();
        if (byteLength == 0) {
            SdkLog.e(TAG, "create " + aIFrame + " size is 0!");
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_SIZE_EMPTY));
        }
        if (isEmpty()) {
            init();
        }
        Iterator<T> it = this.memoryList.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            ShareMemoryHolder shareMemoryHolder = (ShareMemoryHolder) next;
            if (shareMemoryHolder.getSize() >= byteLength && shareMemoryHolder.isVacant()) {
                break;
            }
        }
        ShareMemoryHolder shareMemoryHolder2 = (ShareMemoryHolder) next;
        if (shareMemoryHolder2 != null) {
            SdkLog.i(TAG, "create " + aIFrame + " use exist sm: " + shareMemoryHolder2);
            return new Pair<>(shareMemoryHolder2, 0);
        }
        try {
            Pair<ShareMemoryHolder, Integer> pairCreateAndAdd = createAndAdd(byteLength);
            ShareMemoryHolder shareMemoryHolder3 = (ShareMemoryHolder) pairCreateAndAdd.component1();
            int iIntValue = ((Number) pairCreateAndAdd.component2()).intValue();
            SdkLog.i(TAG, "create " + aIFrame + " create new sm: " + shareMemoryHolder3 + " code: " + iIntValue);
            return new Pair<>(shareMemoryHolder3, Integer.valueOf(iIntValue));
        } catch (Exception e) {
            SdkLog.e(TAG, "create " + aIFrame + " Exception: " + e.getMessage());
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_FRAME_WRITE_FAILED));
        }
    }

    private final Pair<ShareMemoryHolder, Integer> createAndAdd(int i) {
        try {
            ShareMemoryHolder shareMemoryHolder = new ShareMemoryHolder(i);
            this.memoryList.add(shareMemoryHolder);
            SdkLog.i(TAG, "create " + i + " success: " + shareMemoryHolder);
            return new Pair<>(shareMemoryHolder, 0);
        } catch (ErrnoException e) {
            SdkLog.e(TAG, "create " + i + " ErrnoException: " + e.getMessage());
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_SHARE_MEMORY_REQUEST_ALLOCATION_FAIL));
        } catch (IllegalArgumentException e2) {
            SdkLog.e(TAG, "create " + i + " IllegalArgumentException: " + e2.getMessage());
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_SHARE_MEMORY_FD_INVALID));
        } catch (Exception e3) {
            SdkLog.e(TAG, "create " + i + " Exception: " + e3.getMessage());
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_SHARE_MEMORY_CREATE_FAILED));
        }
    }

    private final synchronized void init() {
        try {
            SdkLog.i(TAG, "init " + this.pool);
            for (int i : this.pool) {
                if (i > 0) {
                    if (((long) i) * 1048576 > 2147483647L) {
                        SdkLog.e(TAG, "init " + i + " MB too large!");
                    } else {
                        createAndAdd(i * 1048576);
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static /* synthetic */ int read$default(ShareMemoryPool shareMemoryPool, AIFrame aIFrame, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return shareMemoryPool.read(aIFrame, z);
    }

    public final synchronized List<String> add(List<ShareMemoryHolder> memoryList) {
        ArrayList arrayList;
        Object next;
        try {
            Intrinsics.checkNotNullParameter(memoryList, "memoryList");
            arrayList = new ArrayList();
            for (ShareMemoryHolder shareMemoryHolder : memoryList) {
                Iterator<T> it = this.memoryList.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((ShareMemoryHolder) next).getId(), shareMemoryHolder.getId()));
                ShareMemoryHolder shareMemoryHolder2 = (ShareMemoryHolder) next;
                if (shareMemoryHolder2 == null) {
                    this.memoryList.add(shareMemoryHolder);
                    arrayList.add(shareMemoryHolder.getId());
                    SdkLog.i(TAG, "add " + shareMemoryHolder);
                } else {
                    SdkLog.w(TAG, "add " + shareMemoryHolder + " skip by " + shareMemoryHolder2);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    public final List<ShareMemoryHolder> all() {
        return this.memoryList;
    }

    public final synchronized void close() {
        Iterator<T> it = this.memoryList.iterator();
        while (it.hasNext()) {
            ((ShareMemoryHolder) it.next()).close();
        }
    }

    public final synchronized void closeAndClear() {
        try {
            Iterator<T> it = this.memoryList.iterator();
            while (it.hasNext()) {
                ((ShareMemoryHolder) it.next()).close();
            }
            this.memoryList.clear();
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final synchronized int free(AIFrame frame) {
        Object next;
        try {
            Intrinsics.checkNotNullParameter(frame, "frame");
            Iterator<T> it = this.memoryList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((ShareMemoryHolder) next).getId(), frame.getShareMemId()));
            ShareMemoryHolder shareMemoryHolder = (ShareMemoryHolder) next;
            if (shareMemoryHolder == null) {
                SdkLog.e(TAG, "free " + frame + " shareMem not found!");
                return ErrorCode.ERROR_SHARE_MEMORY_NOT_FOUND;
            }
            shareMemoryHolder.free();
            SdkLog.i(TAG, "free " + frame + " for " + shareMemoryHolder);
            return 0;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized List<String> free(List<String> memoryIdList) {
        ArrayList arrayList;
        Object next;
        try {
            Intrinsics.checkNotNullParameter(memoryIdList, "memoryIdList");
            arrayList = new ArrayList();
            for (String str : memoryIdList) {
                Iterator<T> it = this.memoryList.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((ShareMemoryHolder) next).getId(), str));
                ShareMemoryHolder shareMemoryHolder = (ShareMemoryHolder) next;
                if (shareMemoryHolder == null) {
                    SdkLog.w(TAG, "free " + str + " not existed!");
                } else {
                    SdkLog.w(TAG, "free " + str + " for " + shareMemoryHolder);
                    shareMemoryHolder.free();
                    arrayList.add(str);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final synchronized List<String> getMemoryIdList() {
        ArrayList arrayList;
        ArrayList<ShareMemoryHolder> arrayList2 = this.memoryList;
        arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator<T> it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList.add(((ShareMemoryHolder) it.next()).getId());
        }
        return arrayList;
    }

    public final synchronized boolean isEmpty() {
        return this.memoryList.isEmpty();
    }

    public final Pair<ByteBuffer, Integer> map(AIFrame frame) {
        Object next;
        Intrinsics.checkNotNullParameter(frame, "frame");
        if (frame.getShareMemId().length() == 0) {
            Pair<ShareMemoryHolder, Integer> pairCreate = create(frame);
            ShareMemoryHolder shareMemoryHolder = (ShareMemoryHolder) pairCreate.component1();
            return shareMemoryHolder == null ? new Pair<>(null, Integer.valueOf(((Number) pairCreate.component2()).intValue())) : frame.map(shareMemoryHolder);
        }
        Iterator<T> it = this.memoryList.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((ShareMemoryHolder) next).getId(), frame.getShareMemId()));
        ShareMemoryHolder shareMemoryHolder2 = (ShareMemoryHolder) next;
        if (shareMemoryHolder2 == null) {
            SdkLog.e(TAG, "map " + frame + " shareMem not found!");
            return new Pair<>(null, Integer.valueOf(ErrorCode.ERROR_SHARE_MEMORY_NOT_FOUND));
        }
        SdkLog.i(TAG, "map " + frame + " from " + shareMemoryHolder2);
        return frame.map(shareMemoryHolder2);
    }

    public final synchronized int read(AIFrame frame, boolean z) {
        Object next;
        try {
            Intrinsics.checkNotNullParameter(frame, "frame");
            Iterator<T> it = this.memoryList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((ShareMemoryHolder) next).getId(), frame.getShareMemId()));
            ShareMemoryHolder shareMemoryHolder = (ShareMemoryHolder) next;
            if (shareMemoryHolder == null) {
                SdkLog.e(TAG, "read " + frame + " shareMem not found!");
                return ErrorCode.ERROR_SHARE_MEMORY_NOT_FOUND;
            }
            SdkLog.i(TAG, "read " + frame + " from " + shareMemoryHolder);
            return frame.read(shareMemoryHolder, z);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void readFromParcel(Parcel parcel) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        String string = parcel.readString();
        if (string == null) {
            string = "";
        }
        this.clientId = string;
        parcel.readTypedList(this.memoryList, ShareMemoryHolder.CREATOR);
    }

    public final synchronized int size() {
        return this.memoryList.size();
    }

    public String toString() {
        return "SMP(" + this.clientId + "): " + this.memoryList;
    }

    public final void unmap() {
        Iterator<T> it = this.memoryList.iterator();
        while (it.hasNext()) {
            ((ShareMemoryHolder) it.next()).unmap();
        }
    }

    public final void unmap(AIFrame frame) {
        Object next;
        Intrinsics.checkNotNullParameter(frame, "frame");
        Iterator<T> it = this.memoryList.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((ShareMemoryHolder) next).getId(), frame.getShareMemId()));
        ShareMemoryHolder shareMemoryHolder = (ShareMemoryHolder) next;
        if (shareMemoryHolder == null) {
            SdkLog.w(TAG, "unmap " + frame + " shareMem not found!");
            new Pair(null, Integer.valueOf(ErrorCode.ERROR_SHARE_MEMORY_NOT_FOUND));
            return;
        }
        SdkLog.i(TAG, "unmap " + frame + " from " + shareMemoryHolder);
        frame.unmap(shareMemoryHolder);
        Unit unit = Unit.INSTANCE;
    }

    public final synchronized int write(AIFrame frame) {
        Intrinsics.checkNotNullParameter(frame, "frame");
        try {
            Pair<ShareMemoryHolder, Integer> pairCreate = create(frame);
            ShareMemoryHolder shareMemoryHolder = (ShareMemoryHolder) pairCreate.component1();
            int iIntValue = ((Number) pairCreate.component2()).intValue();
            SdkLog.i(TAG, "write " + frame + " sm: " + shareMemoryHolder + " code: " + iIntValue);
            if (shareMemoryHolder == null) {
                return iIntValue;
            }
            return frame.write(shareMemoryHolder);
        } catch (Exception e) {
            SdkLog.e(TAG, "write " + frame + " Exception: " + e.getMessage());
            return ErrorCode.ERROR_FRAME_WRITE_FAILED;
        }
        throw th;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int i) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        dest.writeString(this.clientId);
        dest.writeTypedList(this.memoryList);
    }
}
