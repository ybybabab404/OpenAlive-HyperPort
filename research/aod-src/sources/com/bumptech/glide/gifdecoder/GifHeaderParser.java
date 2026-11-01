package com.bumptech.glide.gifdecoder;

import android.util.Log;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import miuix.animation.internal.TransitionInfo;

/* JADX INFO: loaded from: classes.dex */
public class GifHeaderParser {
    public final byte[] block = new byte[AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL];
    public int blockSize = 0;
    public GifHeader header;
    public ByteBuffer rawData;

    public void clear() {
        this.rawData = null;
        this.header = null;
    }

    public final boolean err() {
        return this.header.status != 0;
    }

    public GifHeader parseHeader() {
        if (this.rawData == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        }
        if (err()) {
            return this.header;
        }
        readHeader();
        if (!err()) {
            readContents();
            GifHeader gifHeader = this.header;
            if (gifHeader.frameCount < 0) {
                gifHeader.status = 1;
            }
        }
        return this.header;
    }

    public final int read() {
        try {
            return this.rawData.get() & TransitionInfo.INIT;
        } catch (Exception unused) {
            this.header.status = 1;
            return 0;
        }
    }

    public final void readBitmap() {
        this.header.currentFrame.ix = readShort();
        this.header.currentFrame.iy = readShort();
        this.header.currentFrame.iw = readShort();
        this.header.currentFrame.ih = readShort();
        int i = read();
        boolean z = (i & 128) != 0;
        int iPow = (int) Math.pow(2.0d, (i & 7) + 1);
        GifFrame gifFrame = this.header.currentFrame;
        gifFrame.interlace = (i & 64) != 0;
        if (z) {
            gifFrame.lct = readColorTable(iPow);
        } else {
            gifFrame.lct = null;
        }
        this.header.currentFrame.bufferFrameStart = this.rawData.position();
        skipImageData();
        if (err()) {
            return;
        }
        GifHeader gifHeader = this.header;
        gifHeader.frameCount++;
        gifHeader.frames.add(gifHeader.currentFrame);
    }

    public final void readBlock() {
        int i = read();
        this.blockSize = i;
        if (i <= 0) {
            return;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            try {
                int i4 = this.blockSize;
                if (i2 >= i4) {
                    return;
                }
                i3 = i4 - i2;
                this.rawData.get(this.block, i2, i3);
                i2 += i3;
            } catch (Exception e) {
                if (Log.isLoggable("GifHeaderParser", 3)) {
                    Log.d("GifHeaderParser", "Error Reading Block n: " + i2 + " count: " + i3 + " blockSize: " + this.blockSize, e);
                }
                this.header.status = 1;
                return;
            }
        }
    }

    public final int[] readColorTable(int i) {
        byte[] bArr = new byte[i * 3];
        int[] iArr = null;
        try {
            this.rawData.get(bArr);
            iArr = new int[AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL];
            int i2 = 0;
            int i3 = 0;
            while (i2 < i) {
                int i4 = bArr[i3] & TransitionInfo.INIT;
                int i5 = i3 + 2;
                int i6 = bArr[i3 + 1] & TransitionInfo.INIT;
                i3 += 3;
                int i7 = i2 + 1;
                iArr[i2] = (i6 << 8) | (i4 << 16) | (-16777216) | (bArr[i5] & TransitionInfo.INIT);
                i2 = i7;
            }
            return iArr;
        } catch (BufferUnderflowException e) {
            if (Log.isLoggable("GifHeaderParser", 3)) {
                Log.d("GifHeaderParser", "Format Error Reading Color Table", e);
            }
            this.header.status = 1;
            return iArr;
        }
    }

    public final void readContents() {
        readContents(Integer.MAX_VALUE);
    }

    public final void readContents(int i) {
        boolean z = false;
        while (!z && !err() && this.header.frameCount <= i) {
            int i2 = read();
            if (i2 == 33) {
                int i3 = read();
                if (i3 == 1) {
                    skip();
                } else if (i3 == 249) {
                    this.header.currentFrame = new GifFrame();
                    readGraphicControlExt();
                } else if (i3 == 254) {
                    skip();
                } else if (i3 != 255) {
                    skip();
                } else {
                    readBlock();
                    StringBuilder sb = new StringBuilder();
                    for (int i4 = 0; i4 < 11; i4++) {
                        sb.append((char) this.block[i4]);
                    }
                    if (sb.toString().equals("NETSCAPE2.0")) {
                        readNetscapeExt();
                    } else {
                        skip();
                    }
                }
            } else if (i2 == 44) {
                GifHeader gifHeader = this.header;
                if (gifHeader.currentFrame == null) {
                    gifHeader.currentFrame = new GifFrame();
                }
                readBitmap();
            } else if (i2 != 59) {
                this.header.status = 1;
            } else {
                z = true;
            }
        }
    }

    public final void readGraphicControlExt() {
        read();
        int i = read();
        GifFrame gifFrame = this.header.currentFrame;
        int i2 = (i & 28) >> 2;
        gifFrame.dispose = i2;
        if (i2 == 0) {
            gifFrame.dispose = 1;
        }
        gifFrame.transparency = (i & 1) != 0;
        int i3 = readShort();
        if (i3 < 2) {
            i3 = 10;
        }
        GifFrame gifFrame2 = this.header.currentFrame;
        gifFrame2.delay = i3 * 10;
        gifFrame2.transIndex = read();
        read();
    }

    public final void readHeader() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) read());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.header.status = 1;
            return;
        }
        readLSD();
        if (!this.header.gctFlag || err()) {
            return;
        }
        GifHeader gifHeader = this.header;
        gifHeader.gct = readColorTable(gifHeader.gctSize);
        GifHeader gifHeader2 = this.header;
        gifHeader2.bgColor = gifHeader2.gct[gifHeader2.bgIndex];
    }

    public final void readLSD() {
        this.header.width = readShort();
        this.header.height = readShort();
        int i = read();
        GifHeader gifHeader = this.header;
        gifHeader.gctFlag = (i & 128) != 0;
        gifHeader.gctSize = (int) Math.pow(2.0d, (i & 7) + 1);
        this.header.bgIndex = read();
        this.header.pixelAspect = read();
    }

    public final void readNetscapeExt() {
        do {
            readBlock();
            byte[] bArr = this.block;
            if (bArr[0] == 1) {
                this.header.loopCount = ((bArr[2] & TransitionInfo.INIT) << 8) | (bArr[1] & TransitionInfo.INIT);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    public final int readShort() {
        return this.rawData.getShort();
    }

    public final void reset() {
        this.rawData = null;
        Arrays.fill(this.block, (byte) 0);
        this.header = new GifHeader();
        this.blockSize = 0;
    }

    public GifHeaderParser setData(ByteBuffer byteBuffer) {
        reset();
        ByteBuffer byteBufferAsReadOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        this.rawData = byteBufferAsReadOnlyBuffer;
        byteBufferAsReadOnlyBuffer.position(0);
        this.rawData.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    public final void skip() {
        int i;
        do {
            i = read();
            this.rawData.position(Math.min(this.rawData.position() + i, this.rawData.limit()));
        } while (i > 0);
    }

    public final void skipImageData() {
        read();
        skip();
    }
}
