package android.content;

import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileNotFoundException;

/** Minimal ContentProvider host for offline provider tests. */
public abstract class ContentProvider {
    private Context context;

    public Context getContext() { return context; }
    public void attachContextForTest(Context value) { context = value; }

    public abstract boolean onCreate();
    public abstract Cursor query(Uri uri, String[] projection, String selection,
                                 String[] arguments, String order);
    public abstract String getType(Uri uri);
    public abstract Uri insert(Uri uri, ContentValues values);
    public abstract int delete(Uri uri, String selection, String[] arguments);
    public abstract int update(Uri uri, ContentValues values, String selection, String[] arguments);
    public abstract ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException;
}
