package android.os;

import java.io.File;
import java.io.FileNotFoundException;

/** Records the resolved file instead of opening a real descriptor. */
public final class ParcelFileDescriptor {
    public static final int MODE_READ_ONLY = 0x10000000;
    private final File file;
    private final int mode;

    private ParcelFileDescriptor(File file, int mode) {
        this.file = file;
        this.mode = mode;
    }

    public static ParcelFileDescriptor open(File file, int mode) throws FileNotFoundException {
        if (!file.isFile()) throw new FileNotFoundException(file.getPath());
        return new ParcelFileDescriptor(file, mode);
    }

    public File getFileForTest() { return file; }
    public int getModeForTest() { return mode; }
}
