package com.miui.misight.mievent;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes2.dex */
public class MiEvent {
    public int mEventId;
    public JSONObject mMiEvent = new JSONObject();
    public long mTimestamp = System.currentTimeMillis();

    public MiEvent(int i) {
        this.mEventId = i;
    }

    public MiEvent addBool(String str, boolean z) {
        try {
            this.mMiEvent.put(str, z);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addBoolArray(String str, boolean[] zArr) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (boolean z : zArr) {
                jSONArray.put(z);
            }
            this.mMiEvent.put(str, jSONArray);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addFloat(String str, float f) {
        try {
            this.mMiEvent.put(str, f);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addFloatArray(String str, float[] fArr) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (float f : fArr) {
                jSONArray.put(f);
            }
            this.mMiEvent.put(str, jSONArray);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addInt(String str, int i) {
        try {
            this.mMiEvent.put(str, i);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addIntArray(String str, int[] iArr) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (int i : iArr) {
                jSONArray.put(i);
            }
            this.mMiEvent.put(str, jSONArray);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addLong(String str, long j) {
        try {
            this.mMiEvent.put(str, j);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addLongArray(String str, long[] jArr) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (long j : jArr) {
                jSONArray.put(j);
            }
            this.mMiEvent.put(str, jSONArray);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addShort(String str, short s) {
        try {
            this.mMiEvent.put(str, (int) s);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addShortArray(String str, short[] sArr) {
        try {
            JSONArray jSONArray = new JSONArray();
            for (short s : sArr) {
                jSONArray.put((int) s);
            }
            this.mMiEvent.put(str, jSONArray);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public MiEvent addStr(String str, String str2) {
        try {
            this.mMiEvent.put(str, str2);
            return this;
        } catch (JSONException e) {
            printStackTrace(e);
            return this;
        }
    }

    public int getEventId() {
        return this.mEventId;
    }

    public String getMiEventStr() {
        JSONObject jSONObject = this.mMiEvent;
        if (jSONObject == null) {
            return null;
        }
        return jSONObject.toString();
    }

    public final void printStackTrace(Exception exc) {
        Log.e("MiSightSdkMiSight:", exc.getStackTrace()[1].getMethodName() + " add param Error.");
        exc.printStackTrace();
    }
}
