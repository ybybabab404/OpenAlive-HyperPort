package android.os;

import java.io.FileDescriptor;
import java.io.IOException;

public class ParcelFileDescriptor {
    private final FileDescriptor descriptor = new FileDescriptor();
    public boolean closed;

    public FileDescriptor getFileDescriptor() { return descriptor; }

    public void close() throws IOException { closed = true; }
}
