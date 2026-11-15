package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;

import java.lang.reflect.Field;

/**
 * Offline tests for the reversible full_screen_aod_on state machine.
 *
 * <p>The dangerous failures here are silent: a stale restore marker can force a
 * custom AOD style after the user switched back to MIUI, and a too-eager restore
 * can drop full_screen_aod_on during the normal wallpaper rebind window.
 */
public final class AodWallpaperModeTest {
    private static int checks;

    private static void check(boolean value, String message) {
        checks++;
        if (!value) throw new AssertionError(message);
    }

    private static final String MODE = AodWallpaperMode.KEY_MODE;
    private static final String RESTORE = AodWallpaperMode.KEY_RESTORE;
    private static final String HAS = AodWallpaperMode.KEY_HAS_RESTORE;
    private static final String ACTIVE = AodWallpaperMode.KEY_ACTIVE;

    private static void put(String key, String value) {
        Settings.Secure.values.put(key, value);
    }

    private static String get(String key) {
        return Settings.Secure.values.get(key);
    }

    private static boolean has(String key) {
        return Settings.Secure.values.containsKey(key);
    }

    private static void reset() throws Exception {
        Handler.reset();
        Settings.Secure.values.clear();
        Settings.Secure.failPlainGet = false;
        Settings.Secure.failPlainPut = false;
        Settings.Secure.failAllGet = false;
        Settings.Secure.failAllPut = false;
        Settings.Secure.writes = 0;
        WallpaperManager.info = null;
        WallpaperManager.systemId = -1;
        WallpaperManager.throwOnInfo = false;
        WallpaperManager.throwOnId = false;
        Field field = AodWallpaperMode.class.getDeclaredField("restoreInFlight");
        field.setAccessible(true);
        field.setBoolean(null, false);
    }

    private static WallpaperInfo ours() {
        return new WallpaperInfo(new ComponentName("org.aliveclean", "org.aliveclean.CleanWallpaper"));
    }

    private static WallpaperInfo foreign() {
        return new WallpaperInfo(new ComponentName("com.miui.wallpaper", "com.miui.wallpaper.Live"));
    }

    private static boolean inFlight() throws Exception {
        Field field = AodWallpaperMode.class.getDeclaredField("restoreInFlight");
        field.setAccessible(true);
        return field.getBoolean(null);
    }

    private static void probeUntilDone() {
        Handler.runReady();
        for (int i = 0; i < 8 && Handler.queued() > 0; i++) Handler.advance(1000);
    }

    private static void testAlive() throws Exception {
        reset();
        Context context = new Context();
        check(!AodWallpaperMode.alive(context), "no wallpaper info is not ours");
        WallpaperManager.info = ours();
        check(AodWallpaperMode.alive(context), "our live wallpaper is alive");
        WallpaperManager.info = foreign();
        check(!AodWallpaperMode.alive(context), "foreign live wallpaper is not ours");
        WallpaperManager.info = new WallpaperInfo(null);
        check(!AodWallpaperMode.alive(context), "null component is not ours");
        WallpaperManager.throwOnInfo = true;
        check(!AodWallpaperMode.alive(context), "lookup failure is fail-closed");
    }

    private static void testUiAlive() throws Exception {
        reset();
        Context context = new Context();
        WallpaperManager.info = ours();
        check(AodWallpaperMode.uiAlive(context), "ui: our live wallpaper");
        WallpaperManager.info = foreign();
        check(!AodWallpaperMode.uiAlive(context), "ui: foreign live wallpaper");

        WallpaperManager.info = null;
        WallpaperManager.systemId = 7;
        put(ACTIVE, "1");
        check(!AodWallpaperMode.uiAlive(context), "ui: static system wallpaper beats stale active marker");

        WallpaperManager.systemId = -1;
        check(AodWallpaperMode.uiAlive(context), "ui: unknown slot uses active marker");
        Settings.Secure.values.remove(ACTIVE);
        check(!AodWallpaperMode.uiAlive(context), "ui: no marker means not alive");

        WallpaperManager.throwOnInfo = true;
        put(ACTIVE, "1");
        check(AodWallpaperMode.uiAlive(context), "ui: query failure falls back to marker");
        Settings.Secure.failAllGet = true;
        check(!AodWallpaperMode.uiAlive(context), "ui: both queries failing is fail-closed");
    }

    private static void testEnterAndRestore() throws Exception {
        reset();
        Context context = new Context();
        AodWallpaperMode.enter(context);
        check("1".equals(get(MODE)), "fresh: mode ->1");
        check("1".equals(get(ACTIVE)), "fresh: active marker");
        check("1".equals(get(HAS)), "fresh: restore marker");
        check(!has(RESTORE), "fresh: absent original stays absent");
        AodWallpaperMode.restore(context);
        check(!has(MODE), "fresh restore deletes mode");
        check(!has(ACTIVE) && !has(HAS) && !has(RESTORE), "fresh restore clears markers");

        reset();
        put(MODE, "0");
        AodWallpaperMode.enter(context);
        check("0".equals(get(RESTORE)), "original 0 saved");
        check("1".equals(get(MODE)), "mode forced to 1");
        AodWallpaperMode.enter(context);
        check("0".equals(get(RESTORE)), "second enter does not overwrite original");
        AodWallpaperMode.restore(context);
        check("0".equals(get(MODE)), "restore writes original 0 back");
        check(!has(HAS) && !has(ACTIVE), "restore clears marker and active");

        reset();
        put(MODE, "5");
        AodWallpaperMode.enter(context);
        check("1".equals(get(RESTORE)), "out-of-range original is clamped to 1");
        AodWallpaperMode.restore(context);
        check("1".equals(get(MODE)), "clamped original is restored safely");

        reset();
        put(MODE, "0");
        Settings.Secure.failAllGet = true;
        AodWallpaperMode.enter(context);
        check("0".equals(get(MODE)) && !has(HAS), "read failure leaves settings untouched");
        Settings.Secure.failAllGet = false;

        reset();
        put(MODE, "0");
        Settings.Secure.failAllPut = true;
        AodWallpaperMode.enter(context);
        check("0".equals(get(MODE)) && !has(HAS), "write failure leaves settings untouched");
        Settings.Secure.failAllPut = false;

        reset();
        put(MODE, "0");
        Settings.Secure.failPlainGet = true;
        AodWallpaperMode.enter(context);
        check("1".equals(get(MODE)), "reflective getIntForUser fallback works");

        reset();
        put(MODE, "0");
        Settings.Secure.failPlainPut = true;
        AodWallpaperMode.enter(context);
        check("1".equals(get(MODE)), "reflective putIntForUser fallback works");
    }

    private static void testEnsureEnabled() throws Exception {
        reset();
        Context context = new Context();
        WallpaperManager.info = foreign();
        put(MODE, "0");
        AodWallpaperMode.ensureEnabled(context);
        check("0".equals(get(MODE)) && !has(HAS), "ensure: foreign wallpaper is untouched");

        reset();
        WallpaperManager.info = ours();
        put(MODE, "0");
        AodWallpaperMode.ensureEnabled(context);
        check("1".equals(get(MODE)) && "0".equals(get(RESTORE)) && "1".equals(get(HAS)),
                "ensure: missing marker bootstraps a reversible enter");
        check("1".equals(get(ACTIVE)), "ensure: bootstrap sets active");

        reset();
        WallpaperManager.info = ours();
        put(MODE, "0");
        put(HAS, "1");
        put(RESTORE, "0");
        AodWallpaperMode.ensureEnabled(context);
        check("1".equals(get(MODE)) && "0".equals(get(RESTORE)), "ensure: repairs mode only");
        check(!has(ACTIVE), "ensure: repair does not invent active marker");

        reset();
        WallpaperManager.info = ours();
        put(MODE, "1");
        put(HAS, "1");
        put(RESTORE, "0");
        int before = Settings.Secure.writes;
        AodWallpaperMode.ensureEnabled(context);
        check(Settings.Secure.writes == before, "ensure: already enabled is a no-op");

        reset();
        WallpaperManager.info = ours();
        put(MODE, "5");
        put(HAS, "1");
        put(RESTORE, "0");
        AodWallpaperMode.ensureEnabled(context);
        check("1".equals(get(MODE)), "ensure: out-of-range mode is repaired to 1");
    }

    private static void testRestoreProbe() throws Exception {
        // Five foreign probes restore the original value and clear every marker.
        reset();
        Context context = new Context();
        put(MODE, "1");
        put(HAS, "1");
        put(RESTORE, "0");
        put(ACTIVE, "1");
        AodWallpaperMode.restoreWhenForeign(context);
        check(inFlight(), "probe: in flight after start");
        check(Handler.queued() == 1, "probe: initial step queued");
        AodWallpaperMode.restoreWhenForeign(context);
        check(Handler.queued() == 1, "probe: concurrent call is ignored");
        probeUntilDone();
        check("0".equals(get(MODE)), "probe: five foreign observations restore 0");
        check(!has(HAS) && !has(ACTIVE) && !has(RESTORE), "probe: all markers cleared");
        check(!inFlight(), "probe: flag cleared after restore");

        // Five "ours" observations cancel without touching settings.
        reset();
        WallpaperManager.info = ours();
        put(MODE, "1");
        put(HAS, "1");
        put(RESTORE, "0");
        AodWallpaperMode.restoreWhenForeign(context);
        probeUntilDone();
        check("1".equals(get(MODE)) && "1".equals(get(HAS)), "probe: five ours cancels");
        check(!inFlight(), "probe: ours cancellation clears flag");

        // A single miss followed by our wallpaper cancels immediately; this is the
        // wallpaper-rebind grace window that must not restore.
        reset();
        WallpaperManager.info = null;
        put(MODE, "1");
        put(HAS, "1");
        put(RESTORE, "0");
        AodWallpaperMode.restoreWhenForeign(context);
        Handler.runReady();
        WallpaperManager.info = ours();
        Handler.advance(1000);
        check("1".equals(get(MODE)) && "1".equals(get(HAS)), "probe: miss then ours cancels");
        check(Handler.queued() == 0 && !inFlight(), "probe: miss then ours stops");

        // Four ours then one miss then ours also cancels (no accumulated-miss restore).
        reset();
        WallpaperManager.info = ours();
        put(MODE, "1");
        put(HAS, "1");
        put(RESTORE, "0");
        AodWallpaperMode.restoreWhenForeign(context);
        Handler.runReady();
        Handler.advance(1000);
        Handler.advance(1000);
        Handler.advance(1000);
        WallpaperManager.info = null;
        Handler.advance(1000);
        WallpaperManager.info = ours();
        Handler.advance(1000);
        check("1".equals(get(MODE)) && "1".equals(get(HAS)), "probe: late miss then ours cancels");
        check(Handler.queued() == 0 && !inFlight(), "probe: late-miss cancellation stops");

        // Characterization: alive() swallows lookup failures and reports false, so a
        // persistently unavailable WallpaperManager looks like a foreign wallpaper
        // and the probe restores after five seconds.  This is intentional
        // fail-closed behaviour, but it can also undo our mode during a service
        // outage; see the risk note in the report.
        reset();
        WallpaperManager.throwOnInfo = true;
        put(MODE, "1");
        put(HAS, "1");
        put(RESTORE, "0");
        AodWallpaperMode.restoreWhenForeign(context);
        probeUntilDone();
        check("0".equals(get(MODE)) && !has(HAS), "probe: lookup failure counts as foreign");
        check(!inFlight(), "probe: lookup-failure path clears flag");

        // restore() itself swallowing a write failure must still release the flag.
        reset();
        put(MODE, "1");
        put(HAS, "1");
        put(RESTORE, "0");
        Settings.Secure.failAllPut = true;
        AodWallpaperMode.restoreWhenForeign(context);
        probeUntilDone();
        check(!inFlight(), "probe: restore write failure still clears flag");
        check("1".equals(get(MODE)), "probe: failed restore leaves mode intact");

        reset();
        AodWallpaperMode.restoreWhenForeign(null);
        check(!inFlight() && Handler.queued() == 0, "probe: null context is a no-op");
    }

    private static void testRestoreIdempotence() throws Exception {
        reset();
        Context context = new Context();
        put(MODE, "0");
        AodWallpaperMode.restore(context);
        check("0".equals(get(MODE)), "restore: no marker leaves mode alone");

        put(HAS, "1");
        put(RESTORE, "0");
        AodWallpaperMode.restore(context);
        check("0".equals(get(MODE)), "restore: writes original");
        AodWallpaperMode.restore(context);
        check("0".equals(get(MODE)) && !has(HAS), "restore: second call is a no-op");

        reset();
        put(MODE, "0");
        put(HAS, "1");
        Settings.Secure.failAllGet = true;
        AodWallpaperMode.restore(context);
        check("0".equals(get(MODE)), "restore: read failure leaves mode alone");
    }

    public static void main(String[] args) throws Exception {
        testAlive();
        testUiAlive();
        testEnterAndRestore();
        testEnsureEnabled();
        testRestoreProbe();
        testRestoreIdempotence();
        System.out.println("AOD_MODE_OK: alive/uiAlive, reversible enter, enable repair, "
                + "five-probe restore, rebind grace, failure paths (" + checks + " checks)");
    }
}
