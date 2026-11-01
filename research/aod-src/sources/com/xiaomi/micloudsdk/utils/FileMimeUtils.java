package com.xiaomi.micloudsdk.utils;

/* JADX INFO: loaded from: classes2.dex */
public class FileMimeUtils {
    public static final int MAX_END_LENGTH;
    public static final int MAX_HEAD_LENGTH;
    public static final Mime[] MIMES;

    public static class Mime {
        public final byte[] mEnd;
        public final byte[] mHead;
        public final String mMime;

        public Mime(String str, byte[] bArr, byte[] bArr2) {
            this.mMime = str;
            this.mHead = bArr;
            this.mEnd = bArr2;
        }

        public int getEndLength() {
            return this.mEnd.length;
        }

        public int getHeadLength() {
            return this.mHead.length;
        }

        public boolean hasEnd() {
            return this.mEnd != null;
        }

        public boolean hasHead() {
            return this.mHead != null;
        }
    }

    static {
        Mime[] mimeArr = {new Mime("image/jpeg", new byte[]{-1, -40}, new byte[]{-1, -39}), new Mime("image/jpeg", new byte[]{-1, -40}, new byte[]{0, 0}), new Mime("image/png", new byte[]{-119, 80, 78, 71, 13, 10, 26, 10}, null), new Mime("image/tga", new byte[]{0, 0, 2, 0, 0}, null), new Mime("image/tga", new byte[]{0, 0, 16, 0, 0}, null), new Mime("image/gif", new byte[]{71, 73, 70, 56, 55, 97}, null), new Mime("image/gif", new byte[]{71, 73, 70, 56, 57, 97}, null), new Mime("image/bmp", new byte[]{66, 77}, null), new Mime("image/tiff", new byte[]{77, 77}, null), new Mime("image/tiff", new byte[]{73, 73}, null), new Mime("video/3gpp", new byte[]{0, 0, 0, 0, 105, 115, 111, 109, 51, 103, 112, 52}, null), new Mime("video/mp4", new byte[]{0, 0, 0, 24, 102, 116, 121, 112, 105, 115, 111, 109}, null), new Mime("video/mp4", new byte[]{0, 0, 0, 24, 102, 116, 121, 112, 109, 112, 52, 50}, null), new Mime("video/mp4", new byte[]{0, 0, 0, 24, 102, 116, 121, 112, 51, 103, 112}, null), new Mime("video/3gp", new byte[]{0, 0, 0, 28, 102, 116, 121, 112, 51, 103, 112, 52}, null), new Mime("video/quicktime", new byte[]{0, 0, 0, 20, 102, 116, 121, 112, 113, 116}, null), new Mime("video/mp4", new byte[]{0, 0, 0, 32, 102, 116, 121, 112, 97, 118, 99, 49}, null), new Mime("video/quicktime", new byte[]{0, 0, 0, 28, 102, 116, 121, 112, 109, 112, 52, 50}, null), new Mime("video/mp4", new byte[]{0, 0, 0, 32, 102, 116, 121, 112, 105, 115, 111, 109}, null)};
        MIMES = mimeArr;
        int headLength = 0;
        int endLength = 0;
        for (Mime mime : mimeArr) {
            if (mime.hasHead() && mime.getHeadLength() > headLength) {
                headLength = mime.getHeadLength();
            }
            if (mime.hasEnd() && mime.getEndLength() > endLength) {
                endLength = mime.getEndLength();
            }
        }
        MAX_HEAD_LENGTH = headLength;
        MAX_END_LENGTH = endLength;
    }
}
