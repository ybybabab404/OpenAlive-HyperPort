package org.aliveclean;

final class XiaomiDesktopMotion {
    static int clamp(int value){return Math.max(-100,Math.min(100,value));}
    static float offset(float value,int amplitude){
        if(!Float.isFinite(value))return 50f;
        return Math.max(0f,Math.min(100f,50f+(value-50f)*(1f+clamp(amplitude)/100f)));
    }

    static long delay(int responsiveness){return 100L-clamp(responsiveness);}
}
