package android.content;

import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileNotFoundException;

/** One-shot resolver: returns the configured descriptor, throws, or returns null. */
public final class ContentResolver {
    public static ParcelFileDescriptor descriptor;
    public static boolean throwOpen;
    public static String lastUri;

    public ParcelFileDescriptor openFileDescriptor(Uri uri, String mode)
            throws FileNotFoundException {
        lastUri = String.valueOf(uri);
        if (throwOpen) throw new SecurityException("provider denied");
        return descriptor;
    }
}
