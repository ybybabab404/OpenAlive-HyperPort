package android.graphics;

import java.io.FileDescriptor;

/** Decodes are driven by the test fields; actual bytes are irrelevant offline. */
public final class BitmapFactory {
    public static Bitmap providerBitmap;
    public static Bitmap fileBitmap;
    public static boolean providerDecodeNull;
    public static boolean fileDecodeNull;
    public static String lastDecodedFile;
    public static int fileDecodeCalls;

    private BitmapFactory() {}

    public static Bitmap decodeFileDescriptor(FileDescriptor descriptor) {
        return providerDecodeNull ? null : providerBitmap;
    }

    public static Bitmap decodeFile(String path) {
        fileDecodeCalls++;
        lastDecodedFile = path;
        return fileDecodeNull ? null : fileBitmap;
    }
}
