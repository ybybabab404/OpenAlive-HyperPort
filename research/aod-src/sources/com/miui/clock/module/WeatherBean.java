package com.miui.clock.module;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.miui.clock.R;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class WeatherBean {
    private static final String FEEL_TEMPERATURE = "feel_temperature";
    private static final String GREENWICH_MEAN_TIME = "GMT";
    private static final String TAG = "WeatherBean";
    private static final int TEMPERATURE_UNIT_DEGREE_CELSIUS = 1;
    private static final int TEMPERATURE_UNIT_FAHRENHEIT_SCALE = 0;
    private static final String WEATHER_TEMPERATURE = "weather_temperature";
    private int UVILevel;
    private int aqiLevel;
    private String cityId;
    private String cityName;
    private String description;
    private boolean feelTemperatureValid;
    private int highestTemperature;
    private int humidity;
    private int lowestTemperature;
    private int pressure;
    private String rainProbability = "--";
    private int rainProbabilityInt;
    private int somatosensoryTemperature;
    private int sunriseHour;
    private int sunriseHourTomorrow;
    private int sunriseMin;
    private int sunriseMinTomorrow;
    private int sunsetHour;
    private int sunsetMin;
    private int temperature;
    private int temperatureUnit;
    private boolean temperatureValid;
    private int ultraviolet;
    private long updateTime;
    private int weatherType;
    private float windAngel;
    private String windStrength;

    private static String convertSpeedToLevelDesc(float f) {
        if (f < 0.0f) {
            return "";
        }
        if (f < 6.0f) {
            return "1";
        }
        if (f < 12.0f) {
            return ExifInterface.GPS_MEASUREMENT_2D;
        }
        if (f < 20.0f) {
            return ExifInterface.GPS_MEASUREMENT_3D;
        }
        if (f < 29.0f) {
            return "4";
        }
        if (f < 39.0f) {
            return "5";
        }
        if (f < 50.0f) {
            return "6";
        }
        if (f < 62.0f) {
            return "7";
        }
        if (f < 75.0f) {
            return "8";
        }
        if (f < 89.0f) {
            return "9";
        }
        if (f < 103.0f) {
            return "10";
        }
        return f < 117.0f ? "11" : "12";
    }

    public static int getSomatosensoryEmptyResId(int i) {
        return (i == 24 || i == 25) ? R.drawable.somatosensory_empty_round : R.drawable.somatosensory_empty;
    }

    private int getTemperatureWithoutUnit(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            int iIndexOf = this.temperatureUnit == 0 ? str.indexOf("℉") : str.indexOf("℃");
            if (iIndexOf > 0) {
                String strSubstring = str.substring(0, iIndexOf);
                try {
                    strSubstring = strSubstring.trim();
                    return Integer.parseInt(strSubstring);
                } catch (Exception unused) {
                    Log.e(TAG, "parseInt temperature fail, value =" + strSubstring + ".");
                    setTemperatureDataValid(str2, false);
                    return 0;
                }
            }
        }
        setTemperatureDataValid(str2, false);
        return 0;
    }

    private String getWindStrength(String str) {
        float f;
        try {
            f = Float.parseFloat(str.trim());
        } catch (Exception unused) {
            Log.e(TAG, "format wind speed fail");
            f = 0.0f;
        }
        return convertSpeedToLevelDesc(f);
    }

    private void setTemperatureDataValid(String str, boolean z) {
        if (TextUtils.equals(str, WEATHER_TEMPERATURE)) {
            setTemperatureValid(z);
        } else if (TextUtils.equals(str, FEEL_TEMPERATURE)) {
            setFeelTemperatureValid(z);
        }
    }

    public String getAQILevel() {
        return String.valueOf(this.aqiLevel);
    }

    public int getAqiLevelDescResID() {
        int i = this.aqiLevel;
        if (i > 300) {
            return R.string.miui_lock_aqi_level_description_critical;
        }
        if (i > 201) {
            return R.string.miui_lock_aqi_level_description_serious;
        }
        if (i > 151) {
            return R.string.miui_lock_aqi_level_description_moderate;
        }
        if (i > 101) {
            return R.string.miui_lock_aqi_level_description_mild;
        }
        return i > 51 ? R.string.miui_lock_aqi_level_description_good : R.string.miui_lock_aqi_level_description_excellent;
    }

    public String getCityId() {
        return this.cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getFeelTemperatureValid() {
        return this.feelTemperatureValid;
    }

    public int getHighestTemperature() {
        return this.highestTemperature;
    }

    public int getHumidity() {
        return this.humidity;
    }

    public int getIconResId(boolean z, boolean z2) {
        switch (this.weatherType) {
            case 1:
                if (z) {
                    return z2 ? R.drawable.weather_icon_5_blur : R.drawable.weather_icon_5;
                }
                return z2 ? R.drawable.weather_icon_3_blur : R.drawable.weather_icon_3;
            case 2:
                return R.drawable.weather_icon_6;
            case 3:
                if (z) {
                    return z2 ? R.drawable.weather_icon_18_blur : R.drawable.weather_icon_18;
                }
                return z2 ? R.drawable.weather_icon_0_blur : R.drawable.weather_icon_0;
            case 4:
            case 5:
            case 6:
            case 9:
                return R.drawable.weather_icon_12;
            case 7:
                return z2 ? R.drawable.weather_icon_4_blur : R.drawable.weather_icon_4;
            case 8:
            case 11:
                return R.drawable.weather_icon_14;
            case 10:
                return R.drawable.weather_icon_13;
            case 12:
            case 25:
                return R.drawable.weather_icon_11;
            case 13:
            case 15:
                return z2 ? R.drawable.weather_icon_15_blur : R.drawable.weather_icon_15;
            case 14:
            case 17:
                return R.drawable.weather_icon_17;
            case 16:
                return z2 ? R.drawable.weather_icon_16_blur : R.drawable.weather_icon_16;
            case 18:
            case 19:
            case 20:
            case 21:
                return z2 ? R.drawable.weather_icon_8_blur : R.drawable.weather_icon_8;
            case 22:
                return R.drawable.weather_icon_9;
            case 23:
                return z2 ? R.drawable.weather_icon_7_blur : R.drawable.weather_icon_7;
            case 24:
                return z2 ? R.drawable.weather_icon_10_blur : R.drawable.weather_icon_10;
            default:
                return z ? R.drawable.weather_icon_1 : R.drawable.weather_icon_2;
        }
    }

    public int getLowestTemperature() {
        return this.lowestTemperature;
    }

    public int getPressure() {
        return this.pressure;
    }

    public int getPressureIconResId() {
        int i = this.pressure;
        if (i < 1013) {
            return R.drawable.pressure_down;
        }
        return i > 1013 ? R.drawable.pressure_up : R.drawable.pressure_normal;
    }

    public String getPressureString() {
        return String.valueOf(this.pressure);
    }

    public String getRainProbability() {
        return this.rainProbability;
    }

    public int getRainProbabilityInt() {
        return this.rainProbabilityInt;
    }

    public int getSomatosensoryResId(int i) {
        if (i == 24 || i == 25) {
            int i2 = this.somatosensoryTemperature;
            if (i2 > 25) {
                return R.drawable.somatosensory_hot_round;
            }
            return i2 <= 17 ? R.drawable.somatosensory_cold_round : R.drawable.somatosensory_comfortable_round;
        }
        int i3 = this.somatosensoryTemperature;
        if (i3 > 25) {
            return R.drawable.somatosensory_hot;
        }
        return i3 <= 17 ? R.drawable.somatosensory_cold : R.drawable.somatosensory_comfortable;
    }

    public int getSomatosensoryTemperature() {
        return this.somatosensoryTemperature;
    }

    public String getSomatosensoryTemperatureWithUnit(Context context) {
        return this.somatosensoryTemperature + context.getResources().getString(R.string.temperature_unit);
    }

    public int getSunriseMinuteTime() {
        return (this.sunriseHour * 60) + this.sunriseMin;
    }

    public String getSunriseTimeString() {
        Object objValueOf;
        StringBuilder sb = new StringBuilder();
        sb.append(this.sunriseHour);
        sb.append(":");
        int i = this.sunriseMin;
        if (i < 10) {
            objValueOf = "0" + this.sunriseMin;
        } else {
            objValueOf = Integer.valueOf(i);
        }
        sb.append(objValueOf);
        return sb.toString();
    }

    public int getSunriseTomorrowMinuteTime() {
        return (this.sunriseHour * 60) + this.sunriseMin;
    }

    public String getSunriseTomorrowTimeString() {
        Object objValueOf;
        StringBuilder sb = new StringBuilder();
        sb.append(this.sunriseHourTomorrow);
        sb.append(":");
        int i = this.sunriseMinTomorrow;
        if (i < 10) {
            objValueOf = "0" + this.sunriseMinTomorrow;
        } else {
            objValueOf = Integer.valueOf(i);
        }
        sb.append(objValueOf);
        return sb.toString();
    }

    public int getSunsetMinuteTime() {
        return (this.sunsetHour * 60) + this.sunsetMin;
    }

    public String getSunsetTimeString(boolean z) {
        Object objValueOf;
        StringBuilder sb = new StringBuilder();
        sb.append(z ? this.sunsetHour : this.sunsetHour - 12);
        sb.append(":");
        int i = this.sunsetMin;
        if (i < 10) {
            objValueOf = "0" + this.sunsetMin;
        } else {
            objValueOf = Integer.valueOf(i);
        }
        sb.append(objValueOf);
        return sb.toString();
    }

    public int getTemperature() {
        return this.temperature;
    }

    public boolean getTemperatureValid() {
        return this.temperatureValid;
    }

    public String getTemperatureWithoutUnit() {
        return String.valueOf(this.temperature);
    }

    public String getUVILevel() {
        return String.valueOf(this.UVILevel);
    }

    public int getUVILevelDescResID() {
        int i = this.UVILevel;
        if (i <= 2) {
            return R.string.UVI_level_0;
        }
        if (i <= 5) {
            return R.string.UVI_level_1;
        }
        if (i <= 8) {
            return R.string.UVI_level_2;
        }
        return i <= 10 ? R.string.UVI_level_3 : R.string.UVI_level_4;
    }

    public int getUltraviolet() {
        return this.ultraviolet;
    }

    public long getUpdateTime() {
        return this.updateTime;
    }

    public int getWindDescResId() {
        float f = this.windAngel;
        if (f >= 22.5d && f <= 67.5d) {
            return R.string.wind_direction_1;
        }
        if (f > 67.5d && f < 112.5d) {
            return R.string.wind_direction_2;
        }
        if (f >= 112.5d && f <= 157.5d) {
            return R.string.wind_direction_3;
        }
        if (f > 157.5d && f < 202.5d) {
            return R.string.wind_direction_4;
        }
        if (f >= 202.5d && f <= 247.5d) {
            return R.string.wind_direction_5;
        }
        if (f <= 247.5d || f >= 292.5d) {
            return (((double) f) < 292.5d || ((double) f) > 337.5d) ? R.string.wind_direction_0 : R.string.wind_direction_7;
        }
        return R.string.wind_direction_6;
    }

    public int getWindDescResIdFull() {
        float f = this.windAngel;
        if (f >= 22.5d && f <= 67.5d) {
            return R.string.wind_direction_1_f;
        }
        if (f > 67.5d && f < 112.5d) {
            return R.string.wind_direction_2;
        }
        if (f >= 112.5d && f <= 157.5d) {
            return R.string.wind_direction_3_f;
        }
        if (f > 157.5d && f < 202.5d) {
            return R.string.wind_direction_4;
        }
        if (f >= 202.5d && f <= 247.5d) {
            return R.string.wind_direction_5_f;
        }
        if (f <= 247.5d || f >= 292.5d) {
            return (((double) f) < 292.5d || ((double) f) > 337.5d) ? R.string.wind_direction_0 : R.string.wind_direction_7_f;
        }
        return R.string.wind_direction_6;
    }

    public int getWindIconResId() {
        float f = this.windAngel;
        if (f >= 22.5d && f <= 67.5d) {
            return R.drawable.weather_icon_wind_1;
        }
        if (f > 67.5d && f < 112.5d) {
            return R.drawable.weather_icon_wind_2;
        }
        if (f >= 112.5d && f <= 157.5d) {
            return R.drawable.weather_icon_wind_3;
        }
        if (f > 157.5d && f < 202.5d) {
            return R.drawable.weather_icon_wind_4;
        }
        if (f >= 202.5d && f <= 247.5d) {
            return R.drawable.weather_icon_wind_5;
        }
        if (f <= 247.5d || f >= 292.5d) {
            return (((double) f) < 292.5d || ((double) f) > 337.5d) ? R.drawable.weather_icon_wind_0 : R.drawable.weather_icon_wind_7;
        }
        return R.drawable.weather_icon_wind_6;
    }

    public String getWindStrength() {
        return this.windStrength;
    }

    public boolean isAQIDateValid() {
        return this.aqiLevel >= 0;
    }

    public void setAqiLevel(int i) {
        this.aqiLevel = i;
    }

    public void setCityId(String str) {
        this.cityId = str;
    }

    public void setCityName(String str) {
        this.cityName = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setFeelTemperatureValid(boolean z) {
        this.feelTemperatureValid = z;
    }

    public void setHighestTemperature(int i) {
        this.highestTemperature = i;
    }

    public void setHumidity(int i) {
        this.humidity = i;
    }

    public void setLowestTemperature(int i) {
        this.lowestTemperature = i;
    }

    public void setPressure(int i) {
        this.pressure = i;
    }

    public void setRainProbability(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            this.rainProbabilityInt = Integer.parseInt(str.trim());
        } catch (Exception unused) {
            Log.e(TAG, "format rain probability fail");
        }
        this.rainProbability = str;
    }

    public void setSomatosensoryTemperature(int i) {
        this.somatosensoryTemperature = i;
    }

    public void setSomatosensoryTemperature(String str) {
        this.somatosensoryTemperature = getTemperatureWithoutUnit(str, FEEL_TEMPERATURE);
    }

    public void setSunTime(long j, long j2, long j3, boolean z) {
        if (j == j2 || (j == 0 && j2 == 0 && j3 == 0)) {
            Log.d(TAG, "invalid date! sunrise = [" + j + "], sunset = [" + j2 + "], sunriseTomorrow = [" + j3 + "]");
            return;
        }
        Calendar calendar = new Calendar();
        if (z) {
            calendar.setTimeZone(TimeZone.getTimeZone(GREENWICH_MEAN_TIME));
        }
        calendar.setTimeInMillis(j);
        this.sunriseHour = calendar.get(18);
        this.sunriseMin = calendar.get(20);
        calendar.setTimeInMillis(j2);
        this.sunsetHour = calendar.get(18);
        this.sunsetMin = calendar.get(20);
        calendar.setTimeInMillis(j3);
        this.sunriseHourTomorrow = calendar.get(18);
        this.sunriseMinTomorrow = calendar.get(20);
    }

    public void setTemperature(String str) {
        this.temperature = getTemperatureWithoutUnit(str, WEATHER_TEMPERATURE);
    }

    public void setTemperatureUnit(int i) {
        this.temperatureUnit = i;
    }

    public void setTemperatureValid(boolean z) {
        this.temperatureValid = z;
    }

    public void setUVILevel(int i) {
        this.UVILevel = i;
    }

    public void setUVILevel(String str) {
        try {
            this.UVILevel = Integer.parseInt(str.trim());
        } catch (Exception unused) {
            Log.e(TAG, "setUVILevel format fail, value = " + str);
        }
    }

    public void setUltraviolet(int i) {
        this.ultraviolet = i;
    }

    public void setUpdateTime(long j) {
        this.updateTime = j;
    }

    public void setWeatherType(int i) {
        this.weatherType = i;
    }

    public void setWindAngel(float f) {
        this.windAngel = f;
    }

    public void setWindAngel(String str) {
        float f;
        try {
            f = Float.parseFloat(str);
        } catch (Exception unused) {
            Log.e(TAG, "setWindAngel format fail, value = " + str);
            f = 0.0f;
        }
        setWindAngel(f);
    }

    public void setWindSpeed(String str) {
        this.windStrength = getWindStrength(str);
    }

    public String toJsonString() {
        return "{\"weatherType\":" + this.weatherType + ",\"temperature\":" + this.temperature + ",\"temperatureUnit\":" + this.temperatureUnit + ",\"aqiLevel\":" + this.aqiLevel + ",\"humidity\":" + this.humidity + ",\"sunriseHour\":" + this.sunriseHour + ",\"sunriseMin\":" + this.sunriseMin + ",\"sunriseHourTomorrow\":" + this.sunriseHourTomorrow + ",\"sunriseMinTomorrow\":" + this.sunriseMinTomorrow + ",\"sunsetHour\":" + this.sunsetHour + ",\"sunsetMin\":" + this.sunsetMin + ",\"windAngel\":" + this.windAngel + ",\"windStrength\":\"" + this.windStrength + "\",\"pressure\":" + this.pressure + ",\"description\":\"" + this.description + "\",\"rainProbability\":\"" + this.rainProbability + "\",\"rainProbabilityInt\":" + this.rainProbabilityInt + ",\"somatosensoryTemperature\":" + this.somatosensoryTemperature + ",\"UVILevel\":" + this.UVILevel + ",\"updateTime\":" + this.updateTime + '}';
    }

    public String toString() {
        return "WeatherBean{weatherType=" + this.weatherType + ", temperature='" + this.temperature + "', temperatureUnit=" + this.temperatureUnit + ", aqiLevel=" + this.aqiLevel + ", humidity=" + this.humidity + ", sunriseHour=" + this.sunriseHour + ", sunriseMin=" + this.sunriseMin + ", sunriseHourTomorrow=" + this.sunriseHourTomorrow + ", sunriseMinTomorrow=" + this.sunriseMinTomorrow + ", sunsetHour=" + this.sunsetHour + ", sunsetMin=" + this.sunsetMin + ", windAngel=" + this.windAngel + ", windStrength='" + this.windStrength + "', pressure=" + this.pressure + ", description='" + this.description + "', rainProbability='" + this.rainProbability + "', somatosensoryTemperature=" + this.somatosensoryTemperature + ", UVILevel=" + this.UVILevel + ", updateTime=" + this.updateTime + '}';
    }
}
