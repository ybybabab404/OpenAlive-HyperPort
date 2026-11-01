package com.miui.clock.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.WeatherBean;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class DataUtils {
    public static final HashMap mAppInstalledForUserSystemUI = new HashMap(4, 1.0f);
    public static final HashMap mAppInstalledForUserNoSystemUI = new HashMap(4, 1.0f);
    public static String[] packageArray = {"com.miui.weather2", "com.mi.health"};

    public static void clearInstallInfoCache() {
        mAppInstalledForUserSystemUI.clear();
        mAppInstalledForUserNoSystemUI.clear();
    }

    public static String getAppForUserKey(String str, int i) {
        return str + "_" + i;
    }

    public static HealthBean getCaloriesData(Cursor cursor, HealthBean healthBean) {
        if (cursor != null && cursor.getCount() != 0) {
            if (healthBean == null) {
                healthBean = new HealthBean();
            }
            cursor.moveToNext();
            int columnIndex = cursor.getColumnIndex("finished");
            if (columnIndex >= 0) {
                healthBean.setStandCountNow(cursor.getInt(columnIndex));
            }
            int columnIndex2 = cursor.getColumnIndex("goal");
            if (columnIndex2 >= 0) {
                healthBean.setStandCountTarget(cursor.getInt(columnIndex2));
            }
            int columnIndex3 = cursor.getColumnIndex("last_24");
            if (columnIndex3 >= 0) {
                healthBean.setCaloriesList(cursor.getString(columnIndex3));
            }
        }
        return healthBean;
    }

    public static HealthBean getHealthBean(WeakReference weakReference, int i, HealthBean healthBean) {
        HealthBean stepCountData;
        Log.i("ClockDataUtils", "before getHealthBean");
        String str = "content://com.mi.health.provider.main/activity/steps/brief";
        String str2 = "content://com.mi.health.provider.main/widget/stand/simple";
        String str3 = "content://com.mi.health.provider.main/sleep/report";
        String str4 = "content://com.mi.health.provider.main/";
        String str5 = "content://com.mi.health.provider.main/";
        Cursor cursorQuery = null;
        try {
            try {
                ContentResolver contentResolver = ((Context) weakReference.get()).getContentResolver();
                switch (i) {
                    case 500:
                    case 501:
                        cursorQuery = contentResolver.query(Uri.parse(str), null, null, null, null);
                        StringBuilder sb = new StringBuilder();
                        sb.append("getHealthBean, cursor = ");
                        sb.append(cursorQuery);
                        sb.append(", count = ");
                        sb.append(cursorQuery != null ? cursorQuery.getCount() : 0);
                        sb.append(", type = ");
                        sb.append(i);
                        Log.i("ClockDataUtils", sb.toString());
                        stepCountData = getStepCountData(cursorQuery, healthBean);
                        break;
                    case 502:
                    case 503:
                        cursorQuery = contentResolver.query(Uri.parse(str5), null, null, null, null);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("getHealthBean, cursor = ");
                        sb2.append(cursorQuery);
                        sb2.append(", count = ");
                        sb2.append(cursorQuery != null ? cursorQuery.getCount() : 0);
                        sb2.append(", type = ");
                        sb2.append(i);
                        Log.i("ClockDataUtils", sb2.toString());
                        stepCountData = getCaloriesData(cursorQuery, healthBean);
                        break;
                    case 504:
                        cursorQuery = contentResolver.query(Uri.parse(str2), null, null, null, null);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("getHealthBean, cursor = ");
                        sb3.append(cursorQuery);
                        sb3.append(", count = ");
                        sb3.append(cursorQuery != null ? cursorQuery.getCount() : 0);
                        sb3.append(", type = ");
                        sb3.append(i);
                        Log.i("ClockDataUtils", sb3.toString());
                        stepCountData = getStandCountData(cursorQuery, healthBean);
                        break;
                    case 505:
                    case 508:
                        cursorQuery = contentResolver.query(Uri.parse(str4), null, null, null, null);
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("getHealthBean, cursor = ");
                        sb4.append(cursorQuery);
                        sb4.append(", count = ");
                        sb4.append(cursorQuery != null ? cursorQuery.getCount() : 0);
                        sb4.append(", type = ");
                        sb4.append(i);
                        Log.i("ClockDataUtils", sb4.toString());
                        stepCountData = getSportTimeData(cursorQuery, healthBean);
                        break;
                    case 506:
                        cursorQuery = contentResolver.query(Uri.parse(str3), null, null, null, null);
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("getHealthBean, cursor = ");
                        sb5.append(cursorQuery);
                        sb5.append(", count = ");
                        sb5.append(cursorQuery != null ? cursorQuery.getCount() : 0);
                        sb5.append(", type = ");
                        sb5.append(i);
                        Log.i("ClockDataUtils", sb5.toString());
                        stepCountData = getSleepData(cursorQuery, healthBean);
                        break;
                    case 507:
                    default:
                        stepCountData = healthBean;
                        break;
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Exception e) {
                Log.e("DataUtils", "get health data fail", e);
                if (0 != 0) {
                    cursorQuery.close();
                }
                stepCountData = healthBean;
            }
            Log.i("ClockDataUtils", "getHealthBean end, bean = " + stepCountData);
            return stepCountData;
        } catch (Throwable th) {
            if (0 != 0) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public static HealthBean getSleepData(Cursor cursor, HealthBean healthBean) {
        if (cursor != null && cursor.getCount() != 0) {
            if (healthBean == null) {
                healthBean = new HealthBean();
            }
            cursor.moveToNext();
            int columnIndex = cursor.getColumnIndex("duration");
            if (columnIndex >= 0) {
                healthBean.setSleepDuration(cursor.getLong(columnIndex));
            }
        }
        return healthBean;
    }

    public static HealthBean getSportTimeData(Cursor cursor, HealthBean healthBean) {
        if (cursor != null && cursor.getCount() != 0) {
            if (healthBean == null) {
                healthBean = new HealthBean();
            }
            cursor.moveToNext();
            int columnIndex = cursor.getColumnIndex("finished");
            if (columnIndex >= 0) {
                healthBean.setSportTimeNow(cursor.getInt(columnIndex));
            }
            int columnIndex2 = cursor.getColumnIndex("goal");
            if (columnIndex2 >= 0) {
                healthBean.setSportTimeTarget(cursor.getInt(columnIndex2));
            }
            int columnIndex3 = cursor.getColumnIndex("last_24");
            if (columnIndex3 >= 0) {
                healthBean.setSportTimeList(cursor.getString(columnIndex3));
            }
        }
        return healthBean;
    }

    public static HealthBean getStandCountData(Cursor cursor, HealthBean healthBean) {
        if (cursor != null && cursor.getCount() != 0) {
            if (healthBean == null) {
                healthBean = new HealthBean();
            }
            cursor.moveToNext();
            int columnIndex = cursor.getColumnIndex("finished");
            if (columnIndex >= 0) {
                healthBean.setStandCountNow(cursor.getInt(columnIndex));
            }
            int columnIndex2 = cursor.getColumnIndex("goal");
            if (columnIndex2 >= 0) {
                healthBean.setStandCountTarget(cursor.getInt(columnIndex2));
            }
        }
        return healthBean;
    }

    public static HealthBean getStepCountData(Cursor cursor, HealthBean healthBean) {
        if (cursor != null && cursor.getCount() != 0) {
            if (healthBean == null) {
                healthBean = new HealthBean();
            }
            cursor.moveToNext();
            int columnIndex = cursor.getColumnIndex("steps");
            if (columnIndex >= 0) {
                healthBean.setStepCountNow(cursor.getInt(columnIndex));
            }
            int columnIndex2 = cursor.getColumnIndex("goal");
            if (columnIndex2 >= 0) {
                healthBean.setStepCountTarget(cursor.getInt(columnIndex2));
            }
            int columnIndex3 = cursor.getColumnIndex("last_24");
            if (columnIndex3 >= 0) {
                healthBean.setStepCountList(cursor.getString(columnIndex3));
            }
        }
        return healthBean;
    }

    /* JADX WARN: Code duplicated, block: B:118:0x02e2  */
    /* JADX WARN: Code duplicated, block: B:120:0x02e7  */
    public static WeatherBean getWeatherBean(WeakReference weakReference, String str) throws Throwable {
        Cursor cursorQuery;
        WeatherBean weatherBean;
        Log.i("ClockDataUtils", "getWeatherBean, type = " + str);
        Cursor cursor = null;
        String string = null;
        weatherBean = null;
        WeatherBean weatherBean2 = null;
        cursor = null;
        try {
            Cursor cursorQuery2 = ((Context) weakReference.get()).getContentResolver().query(Uri.parse("content://weather/actualWeatherData").buildUpon().appendPath(str).build(), null, null, null, null);
            try {
                cursorQuery = ((Context) weakReference.get()).getContentResolver().query(Uri.parse("content://weather/hourlyData").buildUpon().appendPath(str).build(), null, null, null, null);
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append("getWeatherBean, cursor = ");
                        sb.append(cursorQuery2);
                        sb.append(", count = ");
                        sb.append(cursorQuery2 == null ? 0 : cursorQuery2.getCount());
                        sb.append(" cursorSunMoveInfo count= ");
                        sb.append(cursorQuery == null ? 0 : cursorQuery.getCount());
                        Log.i("ClockDataUtils", sb.toString());
                        if (cursorQuery2 != null && cursorQuery2.getCount() > 0) {
                            cursorQuery2.moveToNext();
                            weatherBean = new WeatherBean();
                            try {
                                int columnIndex = cursorQuery2.getColumnIndex("weather_type");
                                if (columnIndex >= 0) {
                                    weatherBean.setWeatherType(cursorQuery2.getInt(columnIndex));
                                }
                                int columnIndex2 = cursorQuery2.getColumnIndex("temperature_unit");
                                if (columnIndex2 >= 0) {
                                    weatherBean.setTemperatureUnit(cursorQuery2.getInt(columnIndex2));
                                }
                                int columnIndex3 = cursorQuery2.getColumnIndex("temperature");
                                if (columnIndex3 >= 0) {
                                    String string2 = cursorQuery2.getString(columnIndex3);
                                    Log.d("ClockDataUtils", "current temperature is=" + string2);
                                    weatherBean.setTemperatureValid(true);
                                    weatherBean.setTemperature(string2);
                                } else {
                                    Log.d("ClockDataUtils", "get current temperature fail");
                                    weatherBean.setTemperatureValid(false);
                                }
                                int columnIndex4 = cursorQuery2.getColumnIndex("tmphighs");
                                if (columnIndex4 > 0) {
                                    weatherBean.setHighestTemperature(cursorQuery2.getInt(columnIndex4));
                                }
                                int columnIndex5 = cursorQuery2.getColumnIndex("tmplows");
                                if (columnIndex5 > 0) {
                                    weatherBean.setLowestTemperature(cursorQuery2.getInt(columnIndex5));
                                }
                                int columnIndex6 = cursorQuery2.getColumnIndex("aqilevel");
                                if (columnIndex6 >= 0) {
                                    int i = cursorQuery2.getInt(columnIndex6);
                                    Log.d("ClockDataUtils", "weatherbean set AQI level = " + i);
                                    weatherBean.setAqiLevel(i);
                                }
                                int columnIndex7 = cursorQuery2.getColumnIndex("city_name");
                                if (columnIndex7 >= 0) {
                                    String string3 = cursorQuery2.getString(columnIndex7);
                                    Log.d("ClockDataUtils", "weatherbean set city name = " + string3);
                                    weatherBean.setCityName(string3);
                                }
                                int columnIndex8 = cursorQuery2.getColumnIndex("humidity");
                                if (columnIndex8 >= 0) {
                                    weatherBean.setHumidity(cursorQuery2.getInt(columnIndex8));
                                }
                                int columnIndex9 = cursorQuery2.getColumnIndex("wind_direction");
                                if (columnIndex9 >= 0) {
                                    weatherBean.setWindAngel(cursorQuery2.getString(columnIndex9));
                                }
                                int columnIndex10 = cursorQuery2.getColumnIndex("wind_power");
                                if (columnIndex10 >= 0) {
                                    weatherBean.setWindSpeed(cursorQuery2.getString(columnIndex10));
                                }
                                int columnIndex11 = cursorQuery2.getColumnIndex("ultraviolet");
                                if (columnIndex11 >= 0) {
                                    weatherBean.setUVILevel(cursorQuery2.getString(columnIndex11));
                                }
                                int columnIndex12 = cursorQuery2.getColumnIndex("feel_temperature");
                                if (columnIndex12 >= 0) {
                                    weatherBean.setFeelTemperatureValid(true);
                                    weatherBean.setSomatosensoryTemperature(cursorQuery2.getString(columnIndex12));
                                } else {
                                    Log.d("ClockDataUtils", "get current feel temperature fail");
                                    weatherBean.setFeelTemperatureValid(false);
                                }
                                int columnIndex13 = cursorQuery2.getColumnIndex("next_hour_rain_probability");
                                if (columnIndex13 >= 0) {
                                    String string4 = cursorQuery2.getString(columnIndex13);
                                    Log.d("ClockDataUtils", "get rain probability: is=" + string4);
                                    weatherBean.setRainProbability(string4);
                                }
                                int columnIndex14 = cursorQuery2.getColumnIndex("pressure");
                                if (columnIndex14 >= 0) {
                                    weatherBean.setPressure(cursorQuery2.getInt(columnIndex14));
                                }
                                int columnIndex15 = cursorQuery2.getColumnIndex("description");
                                if (columnIndex15 >= 0) {
                                    weatherBean.setDescription(cursorQuery2.getString(columnIndex15));
                                }
                                boolean z = cursorQuery != null;
                                Cursor cursor2 = z ? cursorQuery : cursorQuery2;
                                Log.d("ClockDataUtils", "useHourlyData? " + z);
                                if (z) {
                                    cursor2.moveToNext();
                                }
                                int columnIndex16 = cursor2.getColumnIndex("sunrise");
                                long j = columnIndex16 >= 0 ? cursor2.getLong(columnIndex16) : 0L;
                                int columnIndex17 = cursor2.getColumnIndex("sunset");
                                long j2 = columnIndex17 >= 0 ? cursor2.getLong(columnIndex17) : 0L;
                                int columnIndex18 = cursor2.getColumnIndex("city_id");
                                if (columnIndex18 >= 0) {
                                    string = cursor2.getString(columnIndex18);
                                    weatherBean.setCityId(string);
                                }
                                cursor2.moveToNext();
                                int columnIndex19 = cursor2.getColumnIndex("sunrise");
                                long j3 = columnIndex19 >= 0 ? cursor2.getLong(columnIndex19) : 0L;
                                Log.d("ClockDataUtils", "sunrise=" + j + " sunset=" + j2 + " sunriseTomorrow=" + j3 + " cityId=" + string + " useHourlyData=" + z);
                                weatherBean.setSunTime(j, j2, j3, z);
                                weatherBean2 = weatherBean;
                            } catch (Exception e) {
                                e = e;
                                cursor = cursorQuery2;
                                try {
                                    Log.e("ClockDataUtils", "get weather data fail", e);
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                    weatherBean2 = weatherBean;
                                } catch (Throwable th) {
                                    th = th;
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                    throw th;
                                }
                            }
                        }
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    } catch (Exception e2) {
                        e = e2;
                        weatherBean = null;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = cursorQuery2;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                cursorQuery = null;
                weatherBean = null;
            } catch (Throwable th3) {
                th = th3;
                cursorQuery = null;
            }
        } catch (Exception e4) {
            e = e4;
            cursorQuery = null;
            weatherBean = null;
        } catch (Throwable th4) {
            th = th4;
            cursorQuery = null;
        }
        Log.i("ClockDataUtils", "getWeatherBean end, bean = " + weatherBean2);
        return weatherBean2;
    }

    public static void initAppInstalledInfo(Context context) {
        int iUpdateCurrentUserId = DateFormatUtils.updateCurrentUserId();
        for (String str : packageArray) {
            isAppInstalled(context, str, iUpdateCurrentUserId, true);
            isAppInstalled(context, str, iUpdateCurrentUserId, false);
        }
    }

    public static boolean isAppInstalled(Context context, String str, int i, boolean z) {
        String appForUserKey = getAppForUserKey(str, i);
        boolean z2 = true;
        if (!z) {
            try {
                context.getPackageManager().getPackageInfo(str, 1);
                mAppInstalledForUserNoSystemUI.put(appForUserKey, Boolean.TRUE);
                return true;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("ClockDataUtils", "name not found pkg=" + str);
                mAppInstalledForUserNoSystemUI.put(appForUserKey, Boolean.FALSE);
                return false;
            }
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            Class cls = Integer.TYPE;
            if (((PackageInfo) ReflectUtils.invokeObject(PackageManager.class, packageManager, "getPackageInfoAsUser", PackageInfo.class, new Class[]{String.class, cls, cls}, str, 1, Integer.valueOf(i))) == null) {
                z2 = false;
            }
            mAppInstalledForUserSystemUI.put(appForUserKey, Boolean.valueOf(z2));
            return z2;
        } catch (Exception unused2) {
            Log.e("ClockDataUtils", "name not found pkg=" + str);
            mAppInstalledForUserSystemUI.put(appForUserKey, Boolean.FALSE);
            return false;
        }
    }

    public static boolean isAppInstalledForUser(Context context, String str, int i) {
        String appForUserKey = getAppForUserKey(str, i);
        if (DateFormatUtils.isSystemUI(context)) {
            HashMap map = mAppInstalledForUserSystemUI;
            return map.containsKey(appForUserKey) ? Boolean.TRUE.equals(map.get(appForUserKey)) : isAppInstalled(context, str, i, true);
        }
        HashMap map2 = mAppInstalledForUserNoSystemUI;
        return map2.containsKey(appForUserKey) ? Boolean.TRUE.equals(map2.get(appForUserKey)) : isAppInstalled(context, str, i, false);
    }

    public static boolean isHealthAppInstalled(Context context) {
        return isAppInstalledForUser(context, "com.mi.health", DateFormatUtils.updateCurrentUserId());
    }

    public static boolean isWeatherAppInstalled(Context context) {
        return isAppInstalledForUser(context, "com.miui.weather2", DateFormatUtils.updateCurrentUserId());
    }

    public static boolean isWeatherAppInstalled(Context context, int i) {
        return isAppInstalledForUser(context, "com.miui.weather2", i);
    }
}
