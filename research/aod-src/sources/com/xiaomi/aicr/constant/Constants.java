package com.xiaomi.aicr.constant;

import com.miui.maml.folme.AnimatedPropertyType;
import java.util.HashMap;
import miuix.animation.internal.AnimTask;

/* JADX INFO: loaded from: classes2.dex */
public class Constants {
    public static final HashMap PLUGIN_MAP = new HashMap<Integer, String[]>() { // from class: com.xiaomi.aicr.constant.Constants.1
        {
            put(2000, new String[]{"com.xiaomi.aicr.cognition.ICognitionAudioInterface", "V1", "V1"});
            put(2001, new String[]{"com.xiaomi.aicr.cognition.ICognitionAudioInterface", "V1", "V1"});
            put(2003, new String[]{"com.xiaomi.aicr.cognition.ICognitionAudioInterface", "V1", "V1"});
            put(2004, new String[]{"com.xiaomi.aicr.cognition.ICognitionAudioInterface", "V1", "V1"});
            put(2005, new String[]{"com.xiaomi.aicr.cognition.ICognitionAudioInterface", "V1", "V1"});
            put(Integer.valueOf(AnimatedPropertyType.PIVOT_Y), new String[]{"com.xiaomi.aicr.cognition.ICognitionEngineInterface", "V1", "V1"});
            put(Integer.valueOf(AnimTask.MAX_MAIN_THREAD_TASK_SIZE), new String[]{"com.xiaomi.aicr.cognition.IAppSuggestInterface", "V1", "V1"});
            put(4001, new String[]{"com.xiaomi.aicr.cognition.IAppSuggestInterface", "V1", "V1"});
            put(3000, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3001, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3002, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3003, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3100, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3004, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3005, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3006, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
            put(3007, new String[]{"com.xiaomi.aicr.cognition.ISceneRecognitionInterface", "V1", "V1"});
        }
    };

    public enum STATUS_DEFINE {
        OK,
        DOWNLOADING_FIRST_USE,
        NEED_UPGRADE,
        SERVICE_NOT_CONNECTED,
        SERVICE_ERROR,
        NOT_FOUND,
        SET_IMAGE_FAIL,
        UNSUPPORTED_CAPABILITY,
        PERMISSION_DENIED
    }
}
