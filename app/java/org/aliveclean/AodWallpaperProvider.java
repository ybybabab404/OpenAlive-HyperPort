package org.aliveclean;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;

/** Serves the wallpaper copy to SystemUI. The AOD view runs in SystemUI's process and
 * cannot open files in our private directory, so it asks us over a content provider
 * instead: same data, no shared paths on disk. */
public final class AodWallpaperProvider extends ContentProvider {
    static final String AUTHORITY = "org.aliveclean.aodwallpaper";
    static final String NAME = "aod-photo.png";
    static final Uri PHOTO = Uri.parse("content://" + AUTHORITY + "/photo");

    @Override public boolean onCreate() { return true; }
    @Override public Cursor query(Uri uri, String[] projection, String selection,
                                  String[] arguments, String order) { return null; }
    @Override public String getType(Uri uri) { return "image/png"; }
    @Override public Uri insert(Uri uri, ContentValues values) { return null; }
    @Override public int delete(Uri uri, String selection, String[] arguments) { return 0; }
    @Override public int update(Uri uri, ContentValues values, String selection, String[] arguments) { return 0; }

    @Override public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        String photo = getContext().getSharedPreferences(SceneOptions.APPLIED, 0)
                .getString("photo", "");
        File file = new File(getContext().getFilesDir(), photo);
        if (photo.isEmpty() || !file.isFile()) throw new FileNotFoundException(photo);
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
    }
}
