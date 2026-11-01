package androidx.profileinstaller;

/* JADX INFO: loaded from: classes.dex */
class WritableFileSection {
    public final byte[] mContents;
    public final int mExpectedInflateSize;
    public final boolean mNeedsCompression;
    public final FileSectionType mType;

    public WritableFileSection(FileSectionType fileSectionType, int i, byte[] bArr, boolean z) {
        this.mType = fileSectionType;
        this.mExpectedInflateSize = i;
        this.mContents = bArr;
        this.mNeedsCompression = z;
    }
}
