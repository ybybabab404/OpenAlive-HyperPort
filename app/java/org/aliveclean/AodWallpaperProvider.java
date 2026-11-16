package org.aliveclean;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 给系统 AOD / 主题管理器读取"当前 AOD 图片"的 ContentProvider。
 *
 * <p>只服务"当前生效"的那张图：按 fixed 顺序取 applied 偏好里的照片 → draft 偏好里的照片
 * → 从 MIUI 模板缓存出来的一份世界可读副本；三处都没有就抛 {@code FileNotFoundException}，
 * 由 AOD 侧决定回退到渐变占位（query/insert 在无数据时返回 null）。</p>
 */
public final class AodWallpaperProvider extends ContentProvider {
    static final String AUTHORITY = "org.aliveclean.aodwallpaper";
    static final Uri PHOTO = Uri.parse("content://" + AUTHORITY + "/photo");
    private static final String TEMPLATE_DIR =
            "/data/user_de/0/com.miui.aod/files/templates/current/";

    @Override public boolean onCreate() { return true; }
    @Override public Cursor query(Uri uri, String[] projection, String selection,
                                  String[] arguments, String order) { return null; }
    @Override public String getType(Uri uri) { return "image/png"; }
    @Override public Uri insert(Uri uri, ContentValues values) { return null; }
    @Override public int delete(Uri uri, String selection, String[] arguments) { return 0; }
    @Override public int update(Uri uri, ContentValues values, String selection, String[] arguments) { return 0; }

    @Override public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        Context context = getContext();
        String photo = prefs(context, SceneOptions.APPLIED).getString("photo", "");
        File file = photoFile(context, SceneOptions.APPLIED, photo);
        if (file == null) file = photoFile(context, SceneOptions.DRAFT,
                prefs(context, SceneOptions.DRAFT).getString("photo", ""));
        if (file == null) file = templateCache(context);
        if (file == null) throw new FileNotFoundException(photo);
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
    }

    private static SharedPreferences prefs(Context context, String name) {
        return context.getSharedPreferences(name, 0);
    }

    private static File photoFile(Context context, String prefs, String photo) {
        if (photo == null || photo.isEmpty()) return null;
        File file = new File(context.getFilesDir(), photo);
        return file.isFile() ? file : null;
    }

    /**
     * The AOD process may start after the wallpaper process has already gone away
     * (or before commitDraft has written APPLIED).  In that window MIUI's own
     * template is the only bitmap that matches what is on screen.  Read it as root
     * and hand the provider a private, world-readable copy.
     */
    private static synchronized File templateCache(Context context) {
        File out = new File(context.getCacheDir(), "aod-wallpaper.png");
        File temp = new File(context.getCacheDir(), "aod-wallpaper.png.tmp");
        // Never hand the AOD a file left by an older build.  A failed/timed-out
        // root copy must fall through to the gradient placeholder, not resurrect
        // the old black-and-white moon cache.
        if (out.exists() && !out.delete()) {
            AliveLog.w("AliveClean", "aod template cache delete failed: " + out);
            return null;
        }
        if (temp.exists() && !temp.delete()) {
            AliveLog.w("AliveClean", "aod template temp delete failed: " + temp);
            return null;
        }
        String[] names = {"wallpaper_home", "wallpaper", "small_wallpaper_screenshot"};
        for (String name : names) {
            String source = TEMPLATE_DIR + name;
            try {
                Process process = new ProcessBuilder("su", "0", "-c",
                        "cp '" + source + "' '" + temp.getAbsolutePath() + "' && chmod 644 '"
                                + temp.getAbsolutePath() + "'")
                        .redirectErrorStream(true).start();
                if (!process.waitFor(3, java.util.concurrent.TimeUnit.SECONDS)) {
                    process.destroyForcibly();
                    continue;
                }
                if (process.exitValue() == 0 && temp.isFile() && temp.length() > 0
                        && temp.renameTo(out) && out.isFile()) return out;
            } catch (Throwable ignored) {
            } finally {
                if (temp.exists()) temp.delete();
            }
        }
        return null;
    }
}
