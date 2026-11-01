package com.miui.clock.tiny.pets.interactive;

import android.graphics.Color;
import android.util.Log;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.utils.TinyClockUserIdUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsStyle {
    public static final InteractivePetsClockViewColorStyle[] colorStyles = {new InteractivePetsClockViewColorStyle(Color.parseColor("#FFFDE463")), new InteractivePetsClockViewColorStyle(Color.parseColor("#FFFDE463")), new InteractivePetsClockViewColorStyle(Color.parseColor("#FFC4F550")), new InteractivePetsClockViewColorStyle(Color.parseColor("#FFCDCDFF")), new InteractivePetsClockViewColorStyle(Color.parseColor("#FFFFBCD9"))};
    public static final int[] mTimeHourLeftResource = {R.drawable.interactive_pets_hour_left_0, R.drawable.interactive_pets_hour_left_1, R.drawable.interactive_pets_hour_left_2};
    public static final int[] mTimeHourRightResource = {R.drawable.interactive_pets_hour_right_0, R.drawable.interactive_pets_hour_right_1, R.drawable.interactive_pets_hour_right_2, R.drawable.interactive_pets_hour_right_3, R.drawable.interactive_pets_hour_right_4, R.drawable.interactive_pets_hour_right_5, R.drawable.interactive_pets_hour_right_6, R.drawable.interactive_pets_hour_right_7, R.drawable.interactive_pets_hour_right_8, R.drawable.interactive_pets_hour_right_9};
    public static final int[] mTimeMinuteLeftResource = {R.drawable.interactive_pets_minute_left_0, R.drawable.interactive_pets_minute_left_1, R.drawable.interactive_pets_minute_left_2, R.drawable.interactive_pets_minute_left_3, R.drawable.interactive_pets_minute_left_4, R.drawable.interactive_pets_minute_left_5};
    public static final String[][][] mPetsAodStaticAnimationFrameResource = {new String[][]{new String[]{"interactive_pets_bird_ok_animation1.png", "interactive_pets_bird_ok_animation1.png", "interactive_pets_bird_ok_animation1.png", "interactive_pets_bird_ok_animation2.png", "interactive_pets_bird_ok_animation3.png", "interactive_pets_bird_ok_animation4.png", "interactive_pets_bird_ok_animation5.png", "interactive_pets_bird_ok_animation6.png", "interactive_pets_bird_ok_animation6.png", "interactive_pets_bird_ok_animation6.png", "interactive_pets_bird_ok_animation6.png", "interactive_pets_bird_ok_animation6.png", "interactive_pets_bird_ok_animation6.png"}, new String[]{"interactive_pets_bird_sit_animation1.png", "interactive_pets_bird_sit_animation1.png", "interactive_pets_bird_sit_animation1.png", "interactive_pets_bird_sit_animation2.png", "interactive_pets_bird_sit_animation3.png", "interactive_pets_bird_sit_animation4.png", "interactive_pets_bird_sit_animation5.png", "interactive_pets_bird_sit_animation6.png", "interactive_pets_bird_sit_animation7.png", "interactive_pets_bird_sit_animation6.png", "interactive_pets_bird_sit_animation6.png"}, new String[]{"interactive_pets_bird_ass_animation1.png", "interactive_pets_bird_ass_animation1.png", "interactive_pets_bird_ass_animation2.png", "interactive_pets_bird_ass_animation3.png", "interactive_pets_bird_ass_animation4.png", "interactive_pets_bird_ass_animation5.png", "interactive_pets_bird_ass_animation6.png", "interactive_pets_bird_ass_animation6.png"}}, new String[][]{new String[]{"interactive_pets_sheep_boast_animation1.png", "interactive_pets_sheep_boast_animation2.png", "interactive_pets_sheep_boast_animation3.png", "interactive_pets_sheep_boast_animation3.png", "interactive_pets_sheep_boast_animation4.png", "interactive_pets_sheep_boast_animation5.png", "interactive_pets_sheep_boast_animation6.png", "interactive_pets_sheep_boast_animation7.png"}, new String[]{"interactive_pets_sheep_grass_animation1.png", "interactive_pets_sheep_grass_animation2.png", "interactive_pets_sheep_grass_animation3.png", "interactive_pets_sheep_grass_animation4.png", "interactive_pets_sheep_grass_animation4.png", "interactive_pets_sheep_grass_animation5.png", "interactive_pets_sheep_grass_animation6.png", "interactive_pets_sheep_grass_animation7.png", "interactive_pets_sheep_grass_animation8.png"}, new String[]{"interactive_pets_sheep_roll_animation1.png", "interactive_pets_sheep_roll_animation2.png", "interactive_pets_sheep_roll_animation3.png", "interactive_pets_sheep_roll_animation4.png", "interactive_pets_sheep_roll_animation5.png", "interactive_pets_sheep_roll_animation6.png", "interactive_pets_sheep_roll_animation6.png", "interactive_pets_sheep_roll_animation6.png", "interactive_pets_sheep_roll_animation7.png", "interactive_pets_sheep_roll_animation8.png", "interactive_pets_sheep_roll_animation9.png", "interactive_pets_sheep_roll_animation10.png", "interactive_pets_sheep_roll_animation11.png", "interactive_pets_sheep_roll_animation12.png", "interactive_pets_sheep_roll_animation13.png", "interactive_pets_sheep_roll_animation13.png", "interactive_pets_sheep_roll_animation13.png", "interactive_pets_sheep_roll_animation13.png"}}, new String[][]{new String[]{"interactive_pets_seal_roll_animation1.png", "interactive_pets_seal_roll_animation1.png", "interactive_pets_seal_roll_animation1.png", "interactive_pets_seal_roll_animation2.png", "interactive_pets_seal_roll_animation3.png", "interactive_pets_seal_roll_animation4.png", "interactive_pets_seal_roll_animation5.png", "interactive_pets_seal_roll_animation6.png", "interactive_pets_seal_roll_animation7.png", "interactive_pets_seal_roll_animation8.png", "interactive_pets_seal_roll_animation9.png", "interactive_pets_seal_roll_animation10.png", "interactive_pets_seal_roll_animation10.png"}, new String[]{"interactive_pets_seal_shake_animation1.png", "interactive_pets_seal_shake_animation2.png", "interactive_pets_seal_shake_animation3.png", "interactive_pets_seal_shake_animation4.png", "interactive_pets_seal_shake_animation5.png", "interactive_pets_seal_shake_animation6.png", "interactive_pets_seal_shake_animation7.png", "interactive_pets_seal_shake_animation8.png", "interactive_pets_seal_shake_animation9.png", "interactive_pets_seal_shake_animation10.png", "interactive_pets_seal_shake_animation11.png", "interactive_pets_seal_shake_animation12.png"}, new String[]{"interactive_pets_seal_lie_animation1.png", "interactive_pets_seal_lie_animation1.png", "interactive_pets_seal_lie_animation1.png", "interactive_pets_seal_lie_animation1.png", "interactive_pets_seal_lie_animation2.png", "interactive_pets_seal_lie_animation3.png", "interactive_pets_seal_lie_animation4.png", "interactive_pets_seal_lie_animation5.png", "interactive_pets_seal_lie_animation6.png", "interactive_pets_seal_lie_animation7.png", "interactive_pets_seal_lie_animation8.png"}}};
    public static final int[] mTimeMinuteRightResource = {R.drawable.interactive_pets_minute_right_0, R.drawable.interactive_pets_minute_right_1, R.drawable.interactive_pets_minute_right_2, R.drawable.interactive_pets_minute_right_3, R.drawable.interactive_pets_minute_right_4, R.drawable.interactive_pets_minute_right_5, R.drawable.interactive_pets_minute_right_6, R.drawable.interactive_pets_minute_right_7, R.drawable.interactive_pets_minute_right_8, R.drawable.interactive_pets_minute_right_9};

    public static class InteractivePetsClockViewColorStyle {
        public int primaryColor;

        public InteractivePetsClockViewColorStyle(int i) {
            this.primaryColor = i;
        }

        public int getPrimaryColor() {
            return this.primaryColor;
        }
    }

    public static class InteractivePetsResourceUtil {
        public static final HashMap PET_NAMES = new HashMap<Integer, String>() { // from class: com.miui.clock.tiny.pets.interactive.InteractivePetsStyle.InteractivePetsResourceUtil.1
            {
                put(1, "Bird");
                put(2, "Sheep");
                put(3, "Seal");
            }
        };
        public static String DOWNLOAD_DIR_PREFIX = getDownDir();
        public static Map PetsResourcePathCache = new HashMap();
        public static int lastDownloadResourceDirCount = 0;

        public static String buildInteractivePetsClockResourceDir(int i) {
            return i == 1 ? "/system/media/wallpaper/tiny_clock_resources/" : buildInteractivePetsResourceDir(i);
        }

        public static String buildInteractivePetsResourceDir(int i) {
            if (i == 1) {
                return "/system/media/wallpaper/wallpaper_group_animal_flip_outer/";
            }
            String str = (String) PET_NAMES.get(Integer.valueOf(i));
            if (str == null) {
                Log.d("InteractivePetsResourceUtil", "not find petName" + i);
                return "/system/media/wallpaper/wallpaper_group_animal_flip_outer/";
            }
            String str2 = (String) PetsResourcePathCache.get(str);
            if (str2 != null) {
                File file = new File(str2);
                if (file.exists() && file.isDirectory() && getFilesCount(new File(DOWNLOAD_DIR_PREFIX)) == lastDownloadResourceDirCount) {
                    return str2;
                }
            }
            File fileFindLastModifyResourceDir = findLastModifyResourceDir(str);
            if (fileFindLastModifyResourceDir != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(fileFindLastModifyResourceDir.getAbsolutePath());
                String str3 = File.separator;
                sb.append(str3);
                sb.append(str);
                sb.append(str3);
                String string = sb.toString();
                PetsResourcePathCache.put(str, string);
                return string;
            }
            Log.e("InteractivePetsResourceUtil", "not find resource path" + str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(DOWNLOAD_DIR_PREFIX);
            sb2.append(str);
            String str4 = File.separator;
            sb2.append(str4);
            sb2.append(str);
            sb2.append(str4);
            return sb2.toString();
        }

        public static File findLastModifyResourceDir(String str) {
            File[] fileArrListFiles;
            File file = null;
            if (str == null) {
                return null;
            }
            File file2 = new File(DOWNLOAD_DIR_PREFIX);
            if (file2.exists() && file2.isDirectory() && (fileArrListFiles = file2.listFiles()) != null) {
                long j = 0;
                for (File file3 : fileArrListFiles) {
                    if (file3.isDirectory() && file3.getName().startsWith(str)) {
                        long jLastModified = file3.lastModified();
                        if (file == null || jLastModified > j) {
                            file = file3;
                            j = jLastModified;
                        }
                    }
                }
                lastDownloadResourceDirCount = fileArrListFiles.length;
            }
            return file;
        }

        public static String getDownDir() {
            return "/data/system/theme_magic/users/" + TinyClockUserIdUtils.INSTANCE.updateCurrentUserId() + "/wallpaper/tiny_keyguard/.pets_resource_download/";
        }

        public static int getFilesCount(File file) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                return fileArrListFiles.length;
            }
            return 0;
        }

        public static void updateDownDir() {
            DOWNLOAD_DIR_PREFIX = getDownDir();
            Map map = PetsResourcePathCache;
            if (map != null) {
                map.clear();
            }
        }
    }

    public enum InteractivePetsTypeInfo {
        BIRD(1, 3, 5, 5, 130, new int[]{1, 1, 1}),
        Sheep(2, 3, 5, 5, 129, new int[]{2, 2, 2}),
        Seal(3, 3, 4, 4, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, new int[]{1, 2, 1});

        public final int[] aodFrameAnimationTypes;
        public final int belowCount;
        public final int chargeRepeatFrame;
        public final int switchCount;
        public final int topCount;
        public final int type;

        InteractivePetsTypeInfo(int i, int i2, int i3, int i4, int i5, int[] iArr) {
            this.type = i;
            this.topCount = i3;
            this.belowCount = i4;
            this.switchCount = i2;
            this.chargeRepeatFrame = i5;
            this.aodFrameAnimationTypes = iArr;
        }

        public static InteractivePetsTypeInfo getInteractivePetsTypeInfo(int i) {
            if (i == 1) {
                return BIRD;
            }
            if (i == 2) {
                return Sheep;
            }
            return i == 3 ? Seal : BIRD;
        }
    }

    public enum PetsRegion {
        Switch("Switch", 0),
        Top("Top", 1),
        Below("Below", 2);

        public final String name;
        public final int region;

        PetsRegion(String str, int i) {
            this.name = str;
            this.region = i;
        }

        public static PetsRegion getRegion(int i) {
            if (i == 0) {
                return Switch;
            }
            if (i != 1) {
                return i != 2 ? Switch : Below;
            }
            return Top;
        }
    }

    public static InteractivePetsClockViewColorStyle getClockColorStyleInfo(int i) {
        InteractivePetsClockViewColorStyle[] interactivePetsClockViewColorStyleArr = colorStyles;
        return interactivePetsClockViewColorStyleArr[i % interactivePetsClockViewColorStyleArr.length];
    }
}
