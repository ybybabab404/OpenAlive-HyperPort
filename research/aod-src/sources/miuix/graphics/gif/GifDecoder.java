package miuix.graphics.gif;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Vector;
import miuix.animation.internal.TransitionInfo;

/* JADX INFO: loaded from: classes2.dex */
public class GifDecoder {
    public int[] act;
    public int bgColor;
    public int bgIndex;
    public int[] dest;
    public Vector frames;
    public int[] gct;
    public boolean gctFlag;
    public int gctSize;
    public int height;
    public int ih;
    public Bitmap image;
    public BufferedInputStream in;
    public boolean interlace;
    public int iw;
    public int ix;
    public int iy;
    public int lastBgColor;
    public Bitmap lastBitmap;
    public int[] lct;
    public boolean lctFlag;
    public int lctSize;
    public int lrh;
    public int lrw;
    public int lrx;
    public int lry;
    public long mDecodeBmSize;
    public boolean mDecodeToTheEnd;
    public int mDecodedFrames;
    public int mStartFrame;
    public int pixelAspect;
    public byte[] pixelStack;
    public byte[] pixels;
    public short[] prefix;
    public int status;
    public byte[] suffix;
    public int transIndex;
    public int width;
    public long mMaxDecodeSize = 1048576;
    public int loopCount = 1;
    public byte[] block = new byte[AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL];
    public int blockSize = 0;
    public int dispose = 0;
    public int lastDispose = 0;
    public boolean transparency = false;
    public int delay = 0;
    public boolean mCancel = false;
    public boolean calledOnce = false;

    public static class GifFrame {
        public int delay;
        public Bitmap image;

        public GifFrame(Bitmap bitmap, int i) {
            this.image = bitmap;
            this.delay = i;
        }

        public void recycle() {
            Bitmap bitmap = this.image;
            if (bitmap == null || bitmap.isRecycled()) {
                return;
            }
            this.image.recycle();
        }
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00bf  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:44:0x00bf -> B:41:0x00b9). Please report as a decompilation issue!!! */
    /*  JADX ERROR: StackOverflowError in pass: RegionMakerVisitor
        java.lang.StackOverflowError
        	at jadx.core.utils.BlockUtils.traverseSuccessorsUntil(BlockUtils.java:749)
        */
    public void decodeBitmapData() {
        /*
            Method dump skipped, instruction units count: 296
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: miuix.graphics.gif.GifDecoder.decodeBitmapData():void");
    }

    public boolean err() {
        return this.status != 0;
    }

    public int getDelay(int i) {
        this.delay = -1;
        int frameCount = getFrameCount();
        if (i >= 0 && i < frameCount) {
            this.delay = ((GifFrame) this.frames.elementAt(i)).delay;
        }
        return this.delay;
    }

    public Bitmap getFrame(int i) {
        int frameCount = getFrameCount();
        if (frameCount <= 0) {
            return null;
        }
        return ((GifFrame) this.frames.elementAt(i % frameCount)).image;
    }

    public int getFrameCount() {
        Vector vector = this.frames;
        if (vector == null) {
            return 0;
        }
        return vector.size();
    }

    public int getRealFrameCount() {
        if (this.mDecodeToTheEnd) {
            return this.mDecodedFrames;
        }
        return 0;
    }

    public void init() {
        this.status = 0;
        this.frames = new Vector();
        this.gct = null;
        this.lct = null;
    }

    public boolean isDecodeToTheEnd() {
        return this.mDecodeToTheEnd;
    }

    public int read() {
        try {
            return this.in.read();
        } catch (Exception unused) {
            this.status = 1;
            return 0;
        }
    }

    public int read(InputStream inputStream) {
        this.mDecodeToTheEnd = false;
        if (this.calledOnce) {
            throw new IllegalStateException("decoder cannot be called more than once");
        }
        this.calledOnce = true;
        init();
        if (inputStream != null) {
            this.in = new BufferedInputStream(inputStream);
            try {
                readHeader();
                if (!this.mCancel && !err()) {
                    readContents();
                    if (getFrameCount() < 0) {
                        this.status = 1;
                    }
                }
            } catch (OutOfMemoryError unused) {
                this.status = 2;
                recycle();
            }
        } else {
            this.status = 2;
        }
        if (this.mCancel) {
            recycle();
            this.status = 3;
        }
        return this.status;
    }

    public void readBitmap() {
        int[] iArr;
        int i;
        this.ix = readShort();
        this.iy = readShort();
        this.iw = readShort();
        this.ih = readShort();
        int i2 = read();
        int i3 = 0;
        boolean z = (i2 & 128) != 0;
        this.lctFlag = z;
        int i4 = 2 << (i2 & 7);
        this.lctSize = i4;
        this.interlace = (i2 & 64) != 0;
        if (z) {
            int[] colorTable = readColorTable(i4);
            this.lct = colorTable;
            this.act = colorTable;
        } else {
            this.act = this.gct;
            if (this.bgIndex == this.transIndex) {
                this.bgColor = 0;
            }
        }
        if (this.transparency && (iArr = this.act) != null && (i = this.transIndex) >= 0 && i < iArr.length) {
            int i5 = iArr[i];
            iArr[i] = 0;
            i3 = i5;
        }
        if (this.act == null) {
            this.status = 1;
        }
        if (err()) {
            return;
        }
        decodeBitmapData();
        skip();
        if (err() || this.mCancel) {
            return;
        }
        setPixels();
        if (this.mDecodedFrames >= this.mStartFrame) {
            this.frames.addElement(new GifFrame(this.image, this.delay));
        }
        this.mDecodedFrames++;
        if (this.transparency) {
            this.act[this.transIndex] = i3;
        }
        resetFrame();
    }

    public int readBlock() {
        int i;
        int i2 = read();
        this.blockSize = i2;
        int i3 = 0;
        if (i2 > 0) {
            while (true) {
                try {
                    int i4 = this.blockSize;
                    if (i3 >= i4 || (i = this.in.read(this.block, i3, i4 - i3)) == -1) {
                        break;
                    }
                    i3 += i;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (i3 < this.blockSize) {
                this.status = 1;
            }
        }
        return i3;
    }

    public int[] readColorTable(int i) {
        int i2;
        int i3 = i * 3;
        byte[] bArr = new byte[i3];
        try {
            i2 = this.in.read(bArr, 0, i3);
        } catch (Exception e) {
            e.printStackTrace();
            i2 = 0;
        }
        if (i2 < i3) {
            this.status = 1;
            return null;
        }
        int[] iArr = new int[AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL];
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            int i6 = bArr[i4] & TransitionInfo.INIT;
            int i7 = i4 + 2;
            int i8 = bArr[i4 + 1] & TransitionInfo.INIT;
            i4 += 3;
            iArr[i5] = (i8 << 8) | (i6 << 16) | (-16777216) | (bArr[i7] & TransitionInfo.INIT);
        }
        return iArr;
    }

    public void readContents() {
        this.mDecodedFrames = 0;
        boolean z = false;
        while (!z && !err() && !this.mCancel) {
            int i = read();
            if (i == 33) {
                int i2 = read();
                if (i2 == 1) {
                    skip();
                } else if (i2 == 249) {
                    readGraphicControlExt();
                } else if (i2 == 254) {
                    skip();
                } else if (i2 != 255) {
                    skip();
                } else {
                    readBlock();
                    String str = "";
                    for (int i3 = 0; i3 < 11; i3++) {
                        str = str + ((char) this.block[i3]);
                    }
                    if (str.equals("NETSCAPE2.0")) {
                        readNetscapeExt();
                    } else {
                        skip();
                    }
                }
            } else if (i == 44) {
                int size = this.frames.size();
                readBitmap();
                if (this.frames.size() > size) {
                    this.mDecodeBmSize += (long) (this.image.getRowBytes() * this.image.getHeight());
                }
                if (this.mDecodeBmSize > this.mMaxDecodeSize) {
                    z = true;
                }
            } else if (i != 59) {
                this.status = 1;
            } else {
                this.mDecodeToTheEnd = true;
                z = true;
            }
        }
    }

    public void readGraphicControlExt() {
        read();
        int i = read();
        int i2 = (i & 28) >> 2;
        this.dispose = i2;
        if (i2 == 0) {
            this.dispose = 1;
        }
        this.transparency = (i & 1) != 0;
        int i3 = readShort() * 10;
        this.delay = i3;
        if (i3 <= 0) {
            this.delay = 100;
        }
        this.transIndex = read();
        read();
    }

    public void readHeader() {
        if (this.mCancel) {
            return;
        }
        String str = "";
        for (int i = 0; i < 6; i++) {
            str = str + ((char) read());
        }
        if (!str.startsWith("GIF")) {
            this.status = 1;
            return;
        }
        readLSD();
        if (!this.gctFlag || err()) {
            return;
        }
        int[] colorTable = readColorTable(this.gctSize);
        this.gct = colorTable;
        this.bgColor = colorTable[this.bgIndex];
    }

    public void readLSD() {
        this.width = readShort();
        this.height = readShort();
        int i = read();
        this.gctFlag = (i & 128) != 0;
        this.gctSize = 2 << (i & 7);
        this.bgIndex = read();
        this.pixelAspect = read();
    }

    public void readNetscapeExt() {
        do {
            readBlock();
            byte[] bArr = this.block;
            if (bArr[0] == 1) {
                this.loopCount = ((bArr[2] & TransitionInfo.INIT) << 8) | (bArr[1] & TransitionInfo.INIT);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    public int readShort() {
        return (read() << 8) | read();
    }

    public void recycle() {
        Vector vector = this.frames;
        if (vector != null) {
            int size = vector.size();
            for (int i = 0; i < size; i++) {
                ((GifFrame) this.frames.elementAt(i)).recycle();
            }
        }
    }

    public void resetFrame() {
        this.lastDispose = this.dispose;
        this.lrx = this.ix;
        this.lry = this.iy;
        this.lrw = this.iw;
        this.lrh = this.ih;
        this.lastBitmap = this.image;
        this.lastBgColor = this.bgColor;
        this.dispose = 0;
        this.transparency = false;
        this.delay = 0;
        this.lct = null;
    }

    public void setMaxDecodeSize(long j) {
        this.mMaxDecodeSize = j;
    }

    public void setPixels() {
        Bitmap bitmap;
        int i;
        if (this.dest == null) {
            this.dest = new int[this.width * this.height];
        }
        int i2 = this.lastDispose;
        int i3 = 0;
        if (i2 > 0) {
            if (i2 == 3) {
                int frameCount = getFrameCount();
                if (frameCount - 2 > 0) {
                    Bitmap frame = getFrame(frameCount - 3);
                    if (!frame.equals(this.lastBitmap)) {
                        this.lastBitmap = frame;
                        int[] iArr = this.dest;
                        int i4 = this.width;
                        frame.getPixels(iArr, 0, i4, 0, 0, i4, this.height);
                    }
                } else {
                    this.lastBitmap = null;
                    this.dest = new int[this.width * this.height];
                }
            }
            if (this.lastBitmap != null && this.lastDispose == 2) {
                int i5 = !this.transparency ? this.lastBgColor : 0;
                int i6 = (this.lry * this.width) + this.lrx;
                for (int i7 = 0; i7 < this.lrh; i7++) {
                    int i8 = this.lrw + i6;
                    for (int i9 = i6; i9 < i8; i9++) {
                        this.dest[i9] = i5;
                    }
                    i6 += this.width;
                }
            }
        }
        int i10 = 1;
        int i11 = 8;
        int i12 = 0;
        while (true) {
            int i13 = this.ih;
            if (i3 >= i13) {
                break;
            }
            if (this.interlace) {
                if (i12 >= i13) {
                    i10++;
                    if (i10 == 2) {
                        i12 = 4;
                    } else if (i10 == 3) {
                        i12 = 2;
                        i11 = 4;
                    } else if (i10 == 4) {
                        i12 = 1;
                        i11 = 2;
                    }
                }
                i = i12 + i11;
            } else {
                i = i12;
                i12 = i3;
            }
            int i14 = i12 + this.iy;
            if (i14 < this.height) {
                int i15 = this.width;
                int i16 = i14 * i15;
                int i17 = this.ix + i16;
                int i18 = this.iw;
                int i19 = i17 + i18;
                if (i16 + i15 < i19) {
                    i19 = i16 + i15;
                }
                int i20 = i18 * i3;
                while (i17 < i19) {
                    int i21 = i20 + 1;
                    int i22 = this.act[this.pixels[i20] & TransitionInfo.INIT];
                    if (i22 != 0) {
                        this.dest[i17] = i22;
                    }
                    i17++;
                    i20 = i21;
                }
            }
            i3++;
            i12 = i;
        }
        if (this.mDecodedFrames <= this.mStartFrame && (bitmap = this.image) != null && !bitmap.isRecycled()) {
            this.image.recycle();
        }
        this.image = Bitmap.createBitmap(this.dest, this.width, this.height, Bitmap.Config.ARGB_8888);
    }

    public void setStartFrame(int i) {
        this.mStartFrame = i;
    }

    public void skip() {
        do {
            readBlock();
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }
}
