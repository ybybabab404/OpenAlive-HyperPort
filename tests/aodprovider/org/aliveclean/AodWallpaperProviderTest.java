package org.aliveclean;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Offline contract tests for the AOD wallpaper provider.  The AOD process can start
 * long after the apply process died, so the provider is the last line of defence
 * against RenderLoop's legacy shared black/white moon fallback.
 */
public final class AodWallpaperProviderTest {
    private static int checks;

    private static void check(boolean value, String message) {
        checks++;
        if (!value) throw new AssertionError(message);
    }

    private static File write(File dir, String name, String content) throws IOException {
        File file = new File(dir, name);
        Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
        return file;
    }

    private static AodWallpaperProvider provider(Context context) {
        AodWallpaperProvider provider = new AodWallpaperProvider();
        provider.attachContextForTest(context);
        return provider;
    }

    private static File open(AodWallpaperProvider provider, Uri uri) throws FileNotFoundException {
        return provider.openFile(uri, "r").getFileForTest();
    }

    private static void expectMissing(AodWallpaperProvider provider, String why)
            throws FileNotFoundException {
        try {
            provider.openFile(AodWallpaperProvider.PHOTO, "r");
            throw new AssertionError("expected FileNotFoundException: " + why);
        } catch (FileNotFoundException expected) {
            checks++;
        }
    }

    public static void main(String[] args) throws Exception {
        File root = Files.createTempDirectory("aod-provider").toFile();
        File files = new File(root, "files");
        File cache = new File(root, "cache");
        check(files.mkdirs() && cache.mkdirs(), "temp dirs created");
        Context context = new Context(files, cache);
        SharedPreferences applied = context.getSharedPreferences(SceneOptions.APPLIED, 0);
        SharedPreferences draft = context.getSharedPreferences(SceneOptions.DRAFT, 0);
        AodWallpaperProvider provider = provider(context);

        check("image/png".equals(provider.getType(AodWallpaperProvider.PHOTO)), "provider type");

        // 1. Applied photo wins over the draft.
        File appliedFile = write(files, "applied.png", "applied");
        File draftFile = write(files, "draft.png", "draft");
        applied.put("photo", appliedFile.getName());
        draft.put("photo", draftFile.getName());
        ParcelFileDescriptor pfd = provider.openFile(AodWallpaperProvider.PHOTO, "r");
        check(appliedFile.equals(pfd.getFileForTest()), "applied photo is authoritative");
        check(pfd.getModeForTest() == ParcelFileDescriptor.MODE_READ_ONLY, "read-only descriptor");

        // 2. Missing applied photo falls back to the draft.
        applied.put("photo", "gone.png");
        check(draftFile.equals(open(provider, AodWallpaperProvider.PHOTO)), "missing applied -> draft");

        // 3. Empty applied path is ignored (not treated as filesDir itself).
        applied.put("photo", "");
        check(draftFile.equals(open(provider, AodWallpaperProvider.PHOTO)), "empty applied -> draft");

        // 4. A directory is not a photo.
        File directory = new File(files, "photo-dir");
        check(directory.mkdirs(), "directory created");
        applied.put("photo", directory.getName());
        check(draftFile.equals(open(provider, AodWallpaperProvider.PHOTO)), "directory -> draft");

        // 5. No photo metadata: an existing non-empty cache is the template fallback.
        applied.put("photo", "");
        draft.put("photo", "");
        File cacheFile = write(cache, "aod-wallpaper.png", "template-cache");
        check(cacheFile.equals(open(provider, AodWallpaperProvider.PHOTO)), "non-empty cache fallback");

        // 6. An empty cache is stale garbage, never a valid photo.
        Files.write(cacheFile.toPath(), new byte[0]);
        expectMissing(provider, "empty cache");

        // 7. No metadata and no cache must fail so RenderLoop logs the failure.
        check(cacheFile.delete(), "cache removed");
        expectMissing(provider, "no photo at all");

        // Characterization: photoFile() only checks isFile(), not length.  A truncated
        // APPLIED file is still handed out; RenderLoop then fails to decode it and can
        // fall back to /data/data/com.android.systemui/files/aod-wallpaper.png.
        File emptyApplied = write(files, "empty-applied.png", "");
        applied.put("photo", emptyApplied.getName());
        check(emptyApplied.equals(open(provider, AodWallpaperProvider.PHOTO)),
                "zero-byte applied photo is currently accepted (risk)");

        // Characterization: a cache left over from a previous wallpaper is reused when
        // the root template copy fails.  This can show an old wallpaper in the AOD ball.
        applied.put("photo", "");
        draft.put("photo", "");
        write(cache, "aod-wallpaper.png", "stale-previous-wallpaper");
        check(open(provider, AodWallpaperProvider.PHOTO).length() > 0, "stale cache is reused (risk)");

        System.out.println("AOD_PROVIDER_OK: applied>draft>cache order, empty path, directory, "
                + "empty cache rejection, fail-closed, zero-byte and stale-cache risks (" + checks + " checks)");
    }
}
