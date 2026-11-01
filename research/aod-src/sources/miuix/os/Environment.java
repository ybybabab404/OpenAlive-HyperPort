package miuix.os;

import com.miui.maml.util.LargeIconsHelper;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class Environment extends android.os.Environment {
    public static final File MIUI_DATA_DIRECTORY = new File("/data/miui/");
    public static final File MIUI_APP_DIRECTORY = new File(getMiuiDataDirectory(), LargeIconsHelper.LARGE_ICONS_APP_FOLD);
    public static final File MIUI_PRESET_APP_DIRECTORY = new File(getMiuiDataDirectory(), "preset_apps");
    public static final File MIUI_CUSTOMIZED_DIRECTORY = new File(getMiuiDataDirectory(), "current");
    public static int sCpuCount = 0;

    public static File getMiuiDataDirectory() {
        return MIUI_DATA_DIRECTORY;
    }
}
