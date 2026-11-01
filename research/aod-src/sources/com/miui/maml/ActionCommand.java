package com.miui.maml;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.util.Pair;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.animation.BaseAnimation;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.SensorBinder;
import com.miui.maml.data.VariableBinder;
import com.miui.maml.data.VariableNames;
import com.miui.maml.data.VariableType;
import com.miui.maml.data.Variables;
import com.miui.maml.elements.AnimConfigElement;
import com.miui.maml.elements.AnimStateElement;
import com.miui.maml.elements.AnimatedScreenElement;
import com.miui.maml.elements.ElementGroup;
import com.miui.maml.elements.FunctionElement;
import com.miui.maml.elements.GraphicsElement;
import com.miui.maml.elements.MusicControlScreenElement;
import com.miui.maml.elements.ScreenElement;
import com.miui.maml.elements.VariableElement;
import com.miui.maml.elements.filament.PhysicallyBasedRenderingElement;
import com.miui.maml.elements.lottie.LottieScreenElement;
import com.miui.maml.elements.pag.PagElement;
import com.miui.maml.elements.pag.PagImageElement;
import com.miui.maml.elements.pag.PagImageModel;
import com.miui.maml.elements.pag.PagTextModel;
import com.miui.maml.elements.pag.PagUtils;
import com.miui.maml.elements.video.VideoElement;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.util.ColorParser;
import com.miui.maml.util.HideSdkDependencyUtils;
import com.miui.maml.util.IntentInfo;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.MobileDataUtils;
import com.miui.maml.util.ReflectionHelper;
import com.miui.maml.util.SharedPreferenceHelper;
import com.miui.maml.util.Task;
import com.miui.maml.util.Utils;
import com.miui.maml.util.Variable;
import com.xiaomi.onetrack.api.a;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.api.bf;
import com.xiaomi.onetrack.c.ag;
import com.xiaomi.onetrack.util.ac;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ActionCommand {
    public static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    private static final String COMMAND_BLUETOOTH = "Bluetooth";
    private static final String COMMAND_DATA = "Data";
    private static final String COMMAND_RING_MODE = "RingMode";
    private static final String COMMAND_USB_STORAGE = "UsbStorage";
    private static final String COMMAND_WIFI = "Wifi";
    private static final String LOG_TAG = "ActionCommand";
    private static final int STATE_DISABLED = 0;
    private static final int STATE_ENABLED = 1;
    private static final int STATE_INTERMEDIATE = 5;
    private static final int STATE_TURNING_OFF = 3;
    private static final int STATE_TURNING_ON = 2;
    private static final int STATE_UNKNOWN = 4;
    public static final String TAG_NAME = "Command";
    public static final String USB_CONNECTED = "connected";
    private static final Handler mHandler = new Handler(Looper.getMainLooper());
    protected ScreenElement mScreenElement;

    /* JADX INFO: renamed from: com.miui.maml.ActionCommand$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$AnimConfigCommand$Type;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$AnimStateCommand$Type;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$AnimationCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$AnimationProperty$Type;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$FolmeCommand$Type;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$IntentCommand$IntentType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$JsonObjectCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$MusicCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$PbrCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$SensorBinderCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$TickListenerCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$VariableBinderCommand$Command;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$ActionCommand$VideoCommand$CommandType;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$SoundManager$Command;
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$data$VariableType;

        static {
            int[] iArr = new int[PagCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType = iArr;
            try {
                iArr[PagCommand.CommandType.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[PagCommand.CommandType.PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[PagCommand.CommandType.RELEASE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[PagCommand.CommandType.REPLACE_TEXT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[PagCommand.CommandType.REPLACE_IMAGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[PagCommand.CommandType.RESUME.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[PagCommand.CommandType.SET_PROGRESS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[PagCommand.CommandType.SET_LOOP_COUNT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[MusicCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$MusicCommand$CommandType = iArr2;
            try {
                iArr2[MusicCommand.CommandType.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$MusicCommand$CommandType[MusicCommand.CommandType.PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$MusicCommand$CommandType[MusicCommand.CommandType.PREV.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$MusicCommand$CommandType[MusicCommand.CommandType.NEXT.ordinal()] = 4;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$MusicCommand$CommandType[MusicCommand.CommandType.SET_PROGRESS.ordinal()] = 5;
            } catch (NoSuchFieldError unused13) {
            }
            int[] iArr3 = new int[LottieCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType = iArr3;
            try {
                iArr3[LottieCommand.CommandType.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.STOP.ordinal()] = 4;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.SET_SPEED.ordinal()] = 5;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.GOTO_AND_PLAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.GOTO_AND_STOP.ordinal()] = 7;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.PLAY_SEGMENTS.ordinal()] = 8;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[LottieCommand.CommandType.SET_LOOP_COUNT.ordinal()] = 9;
            } catch (NoSuchFieldError unused22) {
            }
            int[] iArr4 = new int[VideoCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$VideoCommand$CommandType = iArr4;
            try {
                iArr4[VideoCommand.CommandType.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$VideoCommand$CommandType[VideoCommand.CommandType.PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$VideoCommand$CommandType[VideoCommand.CommandType.SEEK_TO.ordinal()] = 3;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$VideoCommand$CommandType[VideoCommand.CommandType.SET_VOLUME.ordinal()] = 4;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$VideoCommand$CommandType[VideoCommand.CommandType.CONFIG.ordinal()] = 5;
            } catch (NoSuchFieldError unused27) {
            }
            int[] iArr5 = new int[JsonObjectCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$JsonObjectCommand$CommandType = iArr5;
            try {
                iArr5[JsonObjectCommand.CommandType.PUT_INT.ordinal()] = 1;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$JsonObjectCommand$CommandType[JsonObjectCommand.CommandType.PUT_STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$JsonObjectCommand$CommandType[JsonObjectCommand.CommandType.PUT_JSON_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$JsonObjectCommand$CommandType[JsonObjectCommand.CommandType.PUT_JSON_ARRAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$JsonObjectCommand$CommandType[JsonObjectCommand.CommandType.PUT_BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError unused32) {
            }
            int[] iArr6 = new int[FolmeCommand.Type.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$FolmeCommand$Type = iArr6;
            try {
                iArr6[FolmeCommand.Type.TO.ordinal()] = 1;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$FolmeCommand$Type[FolmeCommand.Type.SET_TO.ordinal()] = 2;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$FolmeCommand$Type[FolmeCommand.Type.FROM_TO.ordinal()] = 3;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$FolmeCommand$Type[FolmeCommand.Type.CANCEL.ordinal()] = 4;
            } catch (NoSuchFieldError unused36) {
            }
            int[] iArr7 = new int[AnimConfigCommand.Type.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$AnimConfigCommand$Type = iArr7;
            try {
                iArr7[AnimConfigCommand.Type.UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimConfigCommand$Type[AnimConfigCommand.Type.REMOVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimConfigCommand$Type[AnimConfigCommand.Type.CLEAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused39) {
            }
            int[] iArr8 = new int[AnimStateCommand.Type.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$AnimStateCommand$Type = iArr8;
            try {
                iArr8[AnimStateCommand.Type.UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimStateCommand$Type[AnimStateCommand.Type.REMOVE.ordinal()] = 2;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimStateCommand$Type[AnimStateCommand.Type.CLEAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused42) {
            }
            int[] iArr9 = new int[AnimationCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$AnimationCommand$CommandType = iArr9;
            try {
                iArr9[AnimationCommand.CommandType.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimationCommand$CommandType[AnimationCommand.CommandType.PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimationCommand$CommandType[AnimationCommand.CommandType.RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimationCommand$CommandType[AnimationCommand.CommandType.PLAY_WITH_PARAMS.ordinal()] = 4;
            } catch (NoSuchFieldError unused46) {
            }
            int[] iArr10 = new int[TickListenerCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$TickListenerCommand$CommandType = iArr10;
            try {
                iArr10[TickListenerCommand.CommandType.SET.ordinal()] = 1;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$TickListenerCommand$CommandType[TickListenerCommand.CommandType.UNSET.ordinal()] = 2;
            } catch (NoSuchFieldError unused48) {
            }
            int[] iArr11 = new int[GraphicsCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType = iArr11;
            try {
                iArr11[GraphicsCommand.CommandType.LINE_TO.ordinal()] = 1;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.MOVE_TO.ordinal()] = 2;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.CURVE_TO.ordinal()] = 3;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.DRAW_RECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.BEGIN_FILL.ordinal()] = 5;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.LINE_STYLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.DRAW_CIRCLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.DRAW_ELLIPSE.ordinal()] = 8;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.CUBIC_CURVE_TO.ordinal()] = 9;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.DRAW_ROUND_RECT.ordinal()] = 10;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.LINE_GRADIENT_STYLE.ordinal()] = 11;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.BEGIN_GRADIENT_FILL.ordinal()] = 12;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.CREATE_GRADIENT_BOX.ordinal()] = 13;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[GraphicsCommand.CommandType.SET_RENDER_LISTENER.ordinal()] = 14;
            } catch (NoSuchFieldError unused62) {
            }
            int[] iArr12 = new int[PbrCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$PbrCommand$CommandType = iArr12;
            try {
                iArr12[PbrCommand.CommandType.UPDATE_UNIFORM.ordinal()] = 1;
            } catch (NoSuchFieldError unused63) {
            }
            int[] iArr13 = new int[SensorBinderCommand.CommandType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$SensorBinderCommand$CommandType = iArr13;
            try {
                iArr13[SensorBinderCommand.CommandType.TURN_ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$SensorBinderCommand$CommandType[SensorBinderCommand.CommandType.TURN_OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused65) {
            }
            int[] iArr14 = new int[TargetCommand.TargetType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType = iArr14;
            try {
                iArr14[TargetCommand.TargetType.SCREEN_ELEMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[TargetCommand.TargetType.VARIABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[TargetCommand.TargetType.ANIMATION_ITEM.ordinal()] = 3;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[TargetCommand.TargetType.VARIABLE_BINDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[TargetCommand.TargetType.CONSTRUCTOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused70) {
            }
            int[] iArr15 = new int[AnimationProperty.Type.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$AnimationProperty$Type = iArr15;
            try {
                iArr15[AnimationProperty.Type.PLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimationProperty$Type[AnimationProperty.Type.PAUSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimationProperty$Type[AnimationProperty.Type.RESUME.ordinal()] = 3;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$AnimationProperty$Type[AnimationProperty.Type.PLAY_WITH_PARAMS.ordinal()] = 4;
            } catch (NoSuchFieldError unused74) {
            }
            int[] iArr16 = new int[SoundManager.Command.values().length];
            $SwitchMap$com$miui$maml$SoundManager$Command = iArr16;
            try {
                iArr16[SoundManager.Command.Play.ordinal()] = 1;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                $SwitchMap$com$miui$maml$SoundManager$Command[SoundManager.Command.Pause.ordinal()] = 2;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                $SwitchMap$com$miui$maml$SoundManager$Command[SoundManager.Command.Resume.ordinal()] = 3;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                $SwitchMap$com$miui$maml$SoundManager$Command[SoundManager.Command.Stop.ordinal()] = 4;
            } catch (NoSuchFieldError unused78) {
            }
            int[] iArr17 = new int[IntentCommand.IntentType.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$IntentCommand$IntentType = iArr17;
            try {
                iArr17[IntentCommand.IntentType.Activity.ordinal()] = 1;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$IntentCommand$IntentType[IntentCommand.IntentType.Broadcast.ordinal()] = 2;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$IntentCommand$IntentType[IntentCommand.IntentType.Service.ordinal()] = 3;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                $SwitchMap$com$miui$maml$ActionCommand$IntentCommand$IntentType[IntentCommand.IntentType.Var.ordinal()] = 4;
            } catch (NoSuchFieldError unused82) {
            }
            int[] iArr18 = new int[VariableBinderCommand.Command.values().length];
            $SwitchMap$com$miui$maml$ActionCommand$VariableBinderCommand$Command = iArr18;
            try {
                iArr18[VariableBinderCommand.Command.Refresh.ordinal()] = 1;
            } catch (NoSuchFieldError unused83) {
            }
            int[] iArr19 = new int[VariableType.values().length];
            $SwitchMap$com$miui$maml$data$VariableType = iArr19;
            try {
                iArr19[VariableType.NUM.ordinal()] = 1;
            } catch (NoSuchFieldError unused84) {
            }
            try {
                $SwitchMap$com$miui$maml$data$VariableType[VariableType.STR.ordinal()] = 2;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                $SwitchMap$com$miui$maml$data$VariableType[VariableType.JSONO.ordinal()] = 3;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                $SwitchMap$com$miui$maml$data$VariableType[VariableType.JSONA.ordinal()] = 4;
            } catch (NoSuchFieldError unused87) {
            }
        }
    }

    public static class ActionPerformCommand extends TargetCommand {
        public static final String TAG_NAME = "ActionCommand";
        private String mAction;
        private Expression mActionExp;

        public ActionPerformCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute(a.a);
            this.mAction = attribute;
            if (TextUtils.isEmpty(attribute)) {
                this.mAction = null;
                this.mActionExp = Expression.build(getVariables(), element.getAttribute("actionExp"));
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            String strEvaluateStr;
            ScreenElement screenElement = (ScreenElement) getTarget();
            if (screenElement == null) {
                return;
            }
            String str = this.mAction;
            if (str != null) {
                screenElement.performAction(str);
                return;
            }
            Expression expression = this.mActionExp;
            if (expression == null || (strEvaluateStr = expression.evaluateStr()) == null) {
                return;
            }
            screenElement.performAction(strEvaluateStr);
        }
    }

    public static class AnimConfigCommand extends TargetCommand {
        public static final String TAG_NAME = "AnimConfigCommand";
        private Expression mAttr;
        private Type mCommand;
        private Expression mSubName;
        private Expression[] mValuesExp;

        public enum Type {
            UPDATE,
            REMOVE,
            CLEAR,
            INVALID
        }

        public AnimConfigCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mSubName = Expression.build(getVariables(), element.getAttribute("subNameExp"));
            this.mAttr = Expression.build(getVariables(), element.getAttribute("attrExp"));
            this.mValuesExp = Expression.buildMultiple(getVariables(), element.getAttribute("valuesExp"));
            String attribute = element.getAttribute("command");
            attribute.getClass();
            switch (attribute) {
                case "remove":
                    this.mCommand = Type.REMOVE;
                    break;
                case "update":
                    this.mCommand = Type.UPDATE;
                    break;
                case "clear":
                    this.mCommand = Type.CLEAR;
                    break;
                default:
                    this.mCommand = Type.INVALID;
                    break;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            String str;
            Object target = getTarget();
            if (target == null || !(target instanceof AnimConfigElement)) {
                return;
            }
            AnimConfigElement animConfigElement = (AnimConfigElement) target;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mTargetName);
            if (this.mSubName != null) {
                str = "." + this.mSubName.evaluateStr();
            } else {
                str = "";
            }
            sb.append(str);
            String string = sb.toString();
            Expression expression = this.mAttr;
            String strEvaluateStr = expression != null ? expression.evaluateStr() : "";
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$AnimConfigCommand$Type[this.mCommand.ordinal()];
            if (i == 1) {
                Expression[] expressionArr = this.mValuesExp;
                if (expressionArr != null) {
                    animConfigElement.updateConfigData(string, strEvaluateStr, expressionArr);
                    return;
                }
                return;
            }
            if (i == 2) {
                animConfigElement.removeConfigData(string, strEvaluateStr);
            } else {
                if (i != 3) {
                    return;
                }
                animConfigElement.clearConfigData();
            }
        }
    }

    public static class AnimStateCommand extends TargetCommand {
        public static final String TAG_NAME = "AnimStateCommand";
        private Expression mAttrArrayName;
        private String[] mAttrs;
        private Expression[] mAttrsExp;
        private Type mCommand;
        private boolean mIsAttrsValid;
        private boolean mIsValuesValid;
        private Expression mValueArrayName;
        private double[] mValues;
        private Expression[] mValuesExp;

        public enum Type {
            UPDATE,
            REMOVE,
            CLEAR,
            INVALID
        }

        public AnimStateCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            Variables variables = getVariables();
            this.mValueArrayName = Expression.build(variables, element.getAttribute("valueArrayNameExp"));
            this.mAttrArrayName = Expression.build(variables, element.getAttribute("attrArrayNameExp"));
            this.mValuesExp = Expression.buildMultiple(variables, element.getAttribute("valuesExp"));
            this.mAttrsExp = Expression.buildMultiple(variables, element.getAttribute("attrsExp"));
            this.mIsValuesValid = isExpressionsValid(this.mValuesExp);
            boolean zIsExpressionsValid = isExpressionsValid(this.mAttrsExp);
            this.mIsAttrsValid = zIsExpressionsValid;
            if (zIsExpressionsValid) {
                this.mAttrs = new String[this.mAttrsExp.length];
            }
            if (this.mIsValuesValid) {
                this.mValues = new double[this.mValuesExp.length];
            }
            String attribute = element.getAttribute("command");
            attribute.getClass();
            switch (attribute) {
                case "remove":
                    this.mCommand = Type.REMOVE;
                    break;
                case "update":
                    this.mCommand = Type.UPDATE;
                    break;
                case "clear":
                    this.mCommand = Type.CLEAR;
                    break;
                default:
                    this.mCommand = Type.INVALID;
                    break;
            }
        }

        private void remove(AnimStateElement animStateElement) {
            Object obj;
            String[] strArr = this.mAttrs;
            if (strArr == null) {
                if (this.mAttrArrayName == null || (obj = getVariables().get(this.mAttrArrayName.evaluateStr())) == null || !(obj instanceof String[])) {
                    return;
                }
                animStateElement.removeAttr((String[]) obj);
                return;
            }
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                this.mAttrs[i] = this.mAttrsExp[i].evaluateStr();
            }
            animStateElement.removeAttr(this.mAttrs);
        }

        private void update(AnimStateElement animStateElement) {
            double[] dArr;
            String[] strArr = this.mAttrs;
            if (strArr != null && (dArr = this.mValues) != null && strArr.length == dArr.length) {
                int length = strArr.length;
                for (int i = 0; i < length; i++) {
                    this.mAttrs[i] = this.mAttrsExp[i].evaluateStr();
                    this.mValues[i] = this.mValuesExp[i].evaluate();
                }
                animStateElement.updateAttr(this.mAttrs, this.mValues);
                return;
            }
            if (this.mAttrArrayName == null || this.mValueArrayName == null) {
                return;
            }
            Object obj = getVariables().get(this.mAttrArrayName.evaluateStr());
            Object obj2 = getVariables().get(this.mValueArrayName.evaluateStr());
            if (obj == null || !(obj instanceof String[]) || obj2 == null || !(obj2 instanceof double[])) {
                return;
            }
            animStateElement.updateAttr((String[]) obj, (double[]) obj2);
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof AnimStateElement)) {
                return;
            }
            AnimStateElement animStateElement = (AnimStateElement) target;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$AnimStateCommand$Type[this.mCommand.ordinal()];
            if (i == 1) {
                update(animStateElement);
            } else if (i == 2) {
                remove(animStateElement);
            } else {
                if (i != 3) {
                    return;
                }
                animStateElement.clear();
            }
        }
    }

    public static class AnimationCommand extends TargetCommand {
        public static final String TAG_NAME = "AnimationCommand";
        private boolean mAllAni;
        private String[] mAniTags;
        private CommandType mCommand;
        private Expression[] mPlayParams;

        public enum CommandType {
            INVALID,
            PLAY,
            PAUSE,
            RESUME,
            PLAY_WITH_PARAMS
        }

        public AnimationCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute("command");
            if (attribute.equalsIgnoreCase("play")) {
                this.mCommand = CommandType.PLAY;
            } else if (attribute.equalsIgnoreCase("pause")) {
                this.mCommand = CommandType.PAUSE;
            } else if (attribute.equalsIgnoreCase("resume")) {
                this.mCommand = CommandType.RESUME;
            } else if (attribute.toLowerCase().startsWith("play(") && attribute.endsWith(")")) {
                this.mCommand = CommandType.PLAY_WITH_PARAMS;
                Expression[] expressionArrBuildMultiple = Expression.buildMultiple(getVariables(), attribute.substring(5, attribute.length() - 1));
                this.mPlayParams = expressionArrBuildMultiple;
                if (expressionArrBuildMultiple != null && expressionArrBuildMultiple.length != 2 && expressionArrBuildMultiple.length != 4) {
                    MamlLog.e("ActionCommand", "bad expression format");
                }
            } else {
                this.mCommand = CommandType.INVALID;
            }
            String attribute2 = element.getAttribute("tags");
            if (".".equals(attribute2)) {
                this.mAllAni = true;
            } else {
                if (TextUtils.isEmpty(attribute2)) {
                    return;
                }
                this.mAniTags = attribute2.split(ac.b);
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            long jEvaluate;
            long jEvaluate2;
            boolean z;
            Expression expression;
            ScreenElement screenElement = (ScreenElement) getTarget();
            if (screenElement == null) {
                return;
            }
            CommandType commandType = this.mCommand;
            if ((commandType == CommandType.PLAY || commandType == CommandType.PLAY_WITH_PARAMS) && (this.mAllAni || this.mAniTags != null)) {
                screenElement.setAnim(this.mAniTags);
            }
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$AnimationCommand$CommandType[this.mCommand.ordinal()];
            if (i == 1) {
                screenElement.playAnim();
                return;
            }
            if (i == 2) {
                screenElement.pauseAnim();
                return;
            }
            if (i == 3) {
                screenElement.resumeAnim();
                return;
            }
            if (i != 4) {
                return;
            }
            Expression[] expressionArr = this.mPlayParams;
            if (expressionArr.length > 0) {
                Expression expression2 = expressionArr[0];
                jEvaluate = (long) (expression2 == null ? 0.0d : expression2.evaluate());
            } else {
                jEvaluate = 0;
            }
            Expression[] expressionArr2 = this.mPlayParams;
            if (expressionArr2.length > 1) {
                Expression expression3 = expressionArr2[1];
                jEvaluate2 = (long) (expression3 == null ? -1.0d : expression3.evaluate());
            } else {
                jEvaluate2 = -1;
            }
            Expression[] expressionArr3 = this.mPlayParams;
            boolean z2 = expressionArr3.length > 2 && (expression = expressionArr3[2]) != null && expression.evaluate() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            Expression[] expressionArr4 = this.mPlayParams;
            if (expressionArr4.length > 3) {
                Expression expression4 = expressionArr4[3];
                z = expression4 != null && expression4.evaluate() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            } else {
                z = false;
            }
            screenElement.playAnim(jEvaluate, jEvaluate2, z2, z);
        }
    }

    @Deprecated
    public static class AnimationProperty extends PropertyCommand {
        public static final String PROPERTY_NAME = "animation";
        private Expression[] mPlayParams;
        private Type mType;

        public enum Type {
            INVALID,
            PLAY,
            PAUSE,
            RESUME,
            PLAY_WITH_PARAMS
        }

        public AnimationProperty(ScreenElement screenElement, Variable variable, String str) {
            super(screenElement, variable, str);
            if (str.equalsIgnoreCase("play")) {
                this.mType = Type.PLAY;
                return;
            }
            if (str.equalsIgnoreCase("pause")) {
                this.mType = Type.PAUSE;
                return;
            }
            if (str.equalsIgnoreCase("resume")) {
                this.mType = Type.RESUME;
                return;
            }
            if (!str.toLowerCase().startsWith("play(") || !str.endsWith(")")) {
                this.mType = Type.INVALID;
                return;
            }
            this.mType = Type.PLAY_WITH_PARAMS;
            Expression[] expressionArrBuildMultiple = Expression.buildMultiple(getVariables(), str.substring(5, str.length() - 1));
            this.mPlayParams = expressionArrBuildMultiple;
            if (expressionArrBuildMultiple == null || expressionArrBuildMultiple.length == 2 || expressionArrBuildMultiple.length == 4) {
                return;
            }
            MamlLog.e("ActionCommand", "bad expression format");
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            long jEvaluate;
            long jEvaluate2;
            boolean z;
            boolean z2;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$AnimationProperty$Type[this.mType.ordinal()];
            if (i == 1) {
                this.mTargetElement.playAnim();
                return;
            }
            if (i == 2) {
                this.mTargetElement.pauseAnim();
                return;
            }
            if (i == 3) {
                this.mTargetElement.resumeAnim();
                return;
            }
            if (i != 4) {
                return;
            }
            Expression[] expressionArr = this.mPlayParams;
            if (expressionArr.length > 0) {
                Expression expression = expressionArr[0];
                jEvaluate = (long) (expression == null ? 0.0d : expression.evaluate());
            } else {
                jEvaluate = 0;
            }
            long j = jEvaluate;
            Expression[] expressionArr2 = this.mPlayParams;
            if (expressionArr2.length > 1) {
                Expression expression2 = expressionArr2[1];
                jEvaluate2 = (long) (expression2 == null ? -1.0d : expression2.evaluate());
            } else {
                jEvaluate2 = -1;
            }
            long j2 = jEvaluate2;
            Expression[] expressionArr3 = this.mPlayParams;
            if (expressionArr3.length > 2) {
                Expression expression3 = expressionArr3[2];
                z = expression3 != null && expression3.evaluate() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            } else {
                z = false;
            }
            Expression[] expressionArr4 = this.mPlayParams;
            if (expressionArr4.length > 3) {
                Expression expression4 = expressionArr4[3];
                z2 = expression4 != null && expression4.evaluate() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            } else {
                z2 = false;
            }
            this.mTargetElement.playAnim(j, j2, z, z2);
        }
    }

    public static abstract class BaseMethodCommand extends TargetCommand {
        protected static final int ERROR_EXCEPTION = -2;
        protected static final int ERROR_NO_METHOD = -1;
        protected static final int ERROR_SUCCESS = 1;
        protected IndexedVariable mErrorCodeVar;
        private ObjVar[] mParamObjVars;
        protected Class<?>[] mParamTypes;
        protected Object[] mParamValues;
        private Expression[] mParams;
        protected IndexedVariable mReturnVar;
        protected Class<?> mTargetClass;
        protected String mTargetClassName;

        public BaseMethodCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute(am.v);
            this.mTargetClassName = attribute;
            if (TextUtils.isEmpty(attribute)) {
                this.mTargetClassName = null;
            }
            this.mParams = Expression.buildMultiple(getVariables(), element.getAttribute("params"));
            String attribute2 = element.getAttribute("paramTypes");
            if (this.mParams != null && !TextUtils.isEmpty(attribute2)) {
                try {
                    Class<?>[] clsArrStrTypesToClass = ReflectionHelper.strTypesToClass(TextUtils.split(attribute2, ac.b));
                    this.mParamTypes = clsArrStrTypesToClass;
                    if (this.mParams.length != clsArrStrTypesToClass.length) {
                        MamlLog.e("ActionCommand", this.mLogStr + "different length of params and paramTypes");
                        this.mParams = null;
                        this.mParamTypes = null;
                    }
                } catch (ClassNotFoundException e) {
                    MamlLog.e("ActionCommand", this.mLogStr + "invalid method paramTypes. " + e.toString());
                    this.mParams = null;
                    this.mParamTypes = null;
                }
            }
            String attribute3 = element.getAttribute("return");
            if (!TextUtils.isEmpty(attribute3)) {
                this.mReturnVar = new IndexedVariable(attribute3, getVariables(), VariableType.parseType(element.getAttribute("returnType")).isNumber());
            }
            String attribute4 = element.getAttribute("errorVar");
            if (!TextUtils.isEmpty(attribute4)) {
                this.mErrorCodeVar = new IndexedVariable(attribute4, getVariables(), true);
            }
            this.mLogStr += ", class=" + this.mTargetClassName + " type=" + this.mTargetType.toString();
        }

        @Override // com.miui.maml.ActionCommand
        public void finish() {
            super.finish();
            this.mParamValues = null;
        }

        @Override // com.miui.maml.ActionCommand.TargetCommand, com.miui.maml.ActionCommand
        public void init() {
            Expression expression;
            super.init();
            Class<?>[] clsArr = this.mParamTypes;
            if (clsArr != null) {
                if (this.mParamObjVars == null) {
                    this.mParamObjVars = new ObjVar[clsArr.length];
                }
                int i = 0;
                while (true) {
                    Class<?>[] clsArr2 = this.mParamTypes;
                    if (i >= clsArr2.length) {
                        break;
                    }
                    this.mParamObjVars[i] = null;
                    Class<?> cls = clsArr2[i];
                    if ((!cls.isPrimitive() || cls.isArray()) && cls != String.class && (expression = this.mParams[i]) != null) {
                        String strEvaluateStr = expression.evaluateStr();
                        if (!TextUtils.isEmpty(strEvaluateStr)) {
                            this.mParamObjVars[i] = new ObjVar(strEvaluateStr, getVariables());
                        }
                    }
                    i++;
                }
            }
            String str = this.mTargetClassName;
            if (str != null) {
                try {
                    this.mTargetClass = Class.forName(str);
                } catch (Exception e) {
                    MamlLog.w("ActionCommand", "target class not found, name: " + this.mTargetClassName + "\n" + e.toString());
                }
            }
        }

        public void prepareParams() {
            Expression[] expressionArr = this.mParams;
            if (expressionArr == null) {
                return;
            }
            if (this.mParamValues == null) {
                this.mParamValues = new Object[expressionArr.length];
            }
            int i = 0;
            while (true) {
                Expression[] expressionArr2 = this.mParams;
                if (i >= expressionArr2.length) {
                    return;
                }
                Object[] objArr = this.mParamValues;
                objArr[i] = null;
                Class<?> cls = this.mParamTypes[i];
                Expression expression = expressionArr2[i];
                if (expression != null) {
                    if (cls == String.class) {
                        objArr[i] = expression.evaluateStr();
                    } else if (cls == Integer.TYPE) {
                        objArr[i] = Integer.valueOf((int) expression.evaluate());
                    } else if (cls == Long.TYPE) {
                        objArr[i] = Long.valueOf((long) expression.evaluate());
                    } else if (cls == Boolean.TYPE) {
                        objArr[i] = Boolean.valueOf(expression.evaluate() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    } else if (cls == Double.TYPE) {
                        objArr[i] = Double.valueOf(expression.evaluate());
                    } else if (cls == Float.TYPE) {
                        objArr[i] = Float.valueOf((float) expression.evaluate());
                    } else if (cls == Byte.TYPE) {
                        objArr[i] = Byte.valueOf((byte) expression.evaluate());
                    } else if (cls == Short.TYPE) {
                        objArr[i] = Short.valueOf((short) expression.evaluate());
                    } else if (cls == Character.TYPE) {
                        objArr[i] = Character.valueOf((char) expression.evaluate());
                    } else {
                        ObjVar objVar = this.mParamObjVars[i];
                        objArr[i] = objVar != null ? objVar.get() : null;
                    }
                }
                i++;
            }
        }
    }

    @SuppressLint({"MissingPermission"})
    public static class BluetoothSwitchCommand extends NotificationReceiver {
        private BluetoothAdapter mBluetoothAdapter;
        private boolean mBluetoothEnable;
        private boolean mBluetoothEnabling;
        private OnOffCommandHelper mOnOffHelper;

        public BluetoothSwitchCommand(ScreenElement screenElement, String str) {
            super(screenElement, VariableNames.BLUETOOTH_STATE, "android.bluetooth.adapter.action.STATE_CHANGED");
            this.mOnOffHelper = new OnOffCommandHelper(str);
        }

        private boolean ensureBluetoothAdapter() {
            if (this.mBluetoothAdapter == null) {
                this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            return this.mBluetoothAdapter != null;
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            if (ensureBluetoothAdapter()) {
                OnOffCommandHelper onOffCommandHelper = this.mOnOffHelper;
                if (onOffCommandHelper.mIsToggle) {
                    if (this.mBluetoothEnable) {
                        this.mBluetoothAdapter.disable();
                        this.mBluetoothEnabling = false;
                    } else {
                        this.mBluetoothAdapter.enable();
                        this.mBluetoothEnabling = true;
                    }
                } else if (!this.mBluetoothEnabling) {
                    boolean z = this.mBluetoothEnable;
                    boolean z2 = onOffCommandHelper.mIsOn;
                    if (z != z2) {
                        if (z2) {
                            this.mBluetoothAdapter.enable();
                            this.mBluetoothEnabling = true;
                        } else {
                            this.mBluetoothAdapter.disable();
                            this.mBluetoothEnabling = false;
                        }
                    }
                }
                update();
            }
        }

        @Override // com.miui.maml.ActionCommand.NotificationReceiver
        public void update() {
            if (ensureBluetoothAdapter()) {
                try {
                    this.mBluetoothEnable = this.mBluetoothAdapter.isEnabled();
                } catch (SecurityException e) {
                    MamlLog.w("ActionCommand", "no Bluetooth permission. " + e);
                }
                if (!this.mBluetoothEnable) {
                    updateState(this.mBluetoothEnabling ? 2 : 0);
                } else {
                    this.mBluetoothEnabling = false;
                    updateState(1);
                }
            }
        }
    }

    public static class ConditionCommand extends ActionCommand {
        private ActionCommand mCommand;
        private Expression mCondition;

        public ConditionCommand(ActionCommand actionCommand, Expression expression) {
            super(actionCommand.getRoot());
            this.mCommand = actionCommand;
            this.mCondition = expression;
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            if (this.mCondition.evaluate() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                this.mCommand.perform();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            this.mCommand.init();
        }
    }

    public static class DataSwitchCommand extends NotificationReceiver {
        private boolean mApnEnable;
        private MobileDataUtils mMobileDataUtils;
        private OnOffCommandHelper mOnOffHelper;

        public DataSwitchCommand(ScreenElement screenElement, String str) {
            super(screenElement, VariableNames.DATA_STATE, NotifierManager.TYPE_MOBILE_DATA);
            this.mOnOffHelper = new OnOffCommandHelper(str);
            this.mMobileDataUtils = MobileDataUtils.getInstance();
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            boolean z = this.mApnEnable;
            OnOffCommandHelper onOffCommandHelper = this.mOnOffHelper;
            boolean z2 = onOffCommandHelper.mIsToggle ? !z : onOffCommandHelper.mIsOn;
            if (z != z2) {
                this.mMobileDataUtils.enableMobileData(this.mScreenElement.getContext().mContext, z2);
            }
        }

        @Override // com.miui.maml.ActionCommand.NotificationReceiver
        public void update() {
            boolean zIsMobileEnable = this.mMobileDataUtils.isMobileEnable(this.mScreenElement.getContext().mContext);
            this.mApnEnable = zIsMobileEnable;
            updateState(zIsMobileEnable ? 1 : 0);
        }
    }

    public static class DelayCommand extends ActionCommand {
        private Runnable mCmd;
        private ActionCommand mCommand;
        private long mDelay;

        public DelayCommand(ActionCommand actionCommand, long j) {
            super(actionCommand.getRoot());
            this.mCommand = actionCommand;
            this.mDelay = j;
            this.mCmd = new Runnable() { // from class: com.miui.maml.ActionCommand.DelayCommand.1
                @Override // java.lang.Runnable
                public void run() {
                    DelayCommand.this.mCommand.perform();
                }
            };
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            getRoot().postDelayed(this.mCmd, this.mDelay);
        }

        @Override // com.miui.maml.ActionCommand
        public void finish() {
            getRoot().removeCallbacks(this.mCmd);
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            this.mCommand.init();
        }
    }

    public static class EaseTypeCommand extends TargetCommand {
        public static final String TAG_NAME = "EaseTypeCommand";
        private String mEaseFun;
        private String mEaseParams;
        private Expression mEaseTypeExp;

        public EaseTypeCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mTargetType = TargetCommand.TargetType.ANIMATION_ITEM;
            this.mEaseTypeExp = Expression.build(getVariables(), element.getAttribute("easeTypeExp"));
            this.mEaseFun = element.getAttribute("easeFunExp");
            this.mEaseParams = element.getAttribute("easeParamsExp");
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            ArrayList arrayList = (ArrayList) getTarget();
            if (arrayList == null) {
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((BaseAnimation.AnimationItem) it.next()).changeInterpolator(this.mEaseTypeExp.evaluateStr(), this.mEaseParams, this.mEaseFun);
            }
        }
    }

    public static class ExternCommand extends ActionCommand {
        public static final String TAG_NAME = "ExternCommand";
        private String mCommand;
        private Expression mNumParaExp;
        private Expression mStrParaExp;

        public ExternCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            this.mCommand = element.getAttribute("command");
            this.mNumParaExp = Expression.build(getVariables(), element.getAttribute("numPara"));
            this.mStrParaExp = Expression.build(getVariables(), element.getAttribute("strPara"));
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            ScreenElementRoot root = getRoot();
            String str = this.mCommand;
            Expression expression = this.mNumParaExp;
            Double dValueOf = expression == null ? null : Double.valueOf(expression.evaluate());
            Expression expression2 = this.mStrParaExp;
            root.issueExternCommand(str, dValueOf, expression2 != null ? expression2.evaluateStr() : null);
        }
    }

    public static class FieldCommand extends BaseMethodCommand {
        public static final String TAG_NAME = "FieldCommand";
        private Field mField;
        private String mFieldName;
        private boolean mIsSet;

        public FieldCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mFieldName = element.getAttribute("field");
            this.mLogStr = "FieldCommand, " + this.mLogStr + ", field=" + this.mFieldName + "\n";
            String attribute = element.getAttribute("method");
            if ("get".equals(attribute)) {
                this.mIsSet = false;
            } else if ("set".equals(attribute)) {
                this.mIsSet = true;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            if (this.mField == null) {
                loadField();
            }
            if (this.mField != null) {
                try {
                    int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[this.mTargetType.ordinal()];
                    if (i == 1 || i == 2) {
                        if (!this.mIsSet) {
                            if (this.mReturnVar != null) {
                                this.mReturnVar.set(this.mField.get(getTarget()));
                                return;
                            }
                            return;
                        }
                        prepareParams();
                        Object[] objArr = this.mParamValues;
                        if (objArr == null || objArr.length != 1) {
                            return;
                        }
                        this.mField.set(getTarget(), this.mParamValues[0]);
                    }
                } catch (IllegalAccessException e) {
                    MamlLog.e("ActionCommand", e.toString());
                } catch (IllegalArgumentException e2) {
                    MamlLog.e("ActionCommand", e2.toString());
                } catch (NullPointerException e3) {
                    MamlLog.e("ActionCommand", this.mLogStr + "Field target is null. " + e3.toString());
                }
            }
        }

        @Override // com.miui.maml.ActionCommand.BaseMethodCommand, com.miui.maml.ActionCommand.TargetCommand, com.miui.maml.ActionCommand
        public void init() {
            super.init();
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[this.mTargetType.ordinal()];
            if ((i == 1 || i == 2) && this.mField != null) {
                loadField();
            }
        }

        public void loadField() {
            Object target;
            if (this.mTargetClass == null && (target = getTarget()) != null) {
                this.mTargetClass = target.getClass();
            }
            Class<?> cls = this.mTargetClass;
            if (cls == null) {
                MamlLog.e("ActionCommand", this.mLogStr + "class is null.");
                return;
            }
            try {
                this.mField = cls.getField(this.mFieldName);
            } catch (NoSuchFieldException e) {
                MamlLog.e("ActionCommand", this.mLogStr + e.toString());
            }
        }
    }

    public static class FolmeCommand extends TargetCommand {
        public static final String TAG_NAME = "FolmeCommand";
        private Type mCommand;
        private Expression mConfig;
        private boolean mIsParamsValid;
        private boolean mIsStatesValid;
        private Expression[] mParams;
        private Expression[] mStates;

        public enum Type {
            TO,
            SET_TO,
            FROM_TO,
            CANCEL,
            ADD_RANGE_BOARD,
            INVALID
        }

        public FolmeCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mParams = Expression.buildMultiple(getVariables(), element.getAttribute("params"));
            this.mStates = Expression.buildMultiple(getVariables(), element.getAttribute("states"));
            this.mConfig = Expression.build(getVariables(), element.getAttribute(ag.a));
            this.mIsParamsValid = isExpressionsValid(this.mParams);
            this.mIsStatesValid = isExpressionsValid(this.mStates);
            String attribute = element.getAttribute("command");
            attribute.getClass();
            switch (attribute) {
                case "cancel":
                    this.mCommand = Type.CANCEL;
                    break;
                case "fromTo":
                    this.mCommand = Type.FROM_TO;
                    break;
                case "to":
                    this.mCommand = Type.TO;
                    break;
                case "setTo":
                    this.mCommand = Type.SET_TO;
                    break;
                default:
                    this.mCommand = Type.INVALID;
                    break;
            }
        }

        private void folmeCancel(AnimatedScreenElement animatedScreenElement) {
            if (this.mIsParamsValid) {
                animatedScreenElement.folmeCancel(this.mParams);
            } else {
                animatedScreenElement.folmeCancel(null);
            }
        }

        private void folmeFromTo(AnimatedScreenElement animatedScreenElement) {
            if (this.mIsStatesValid) {
                Expression[] expressionArr = this.mStates;
                if (expressionArr.length > 1) {
                    String strEvaluateStr = expressionArr[0].evaluateStr();
                    String strEvaluateStr2 = this.mStates[1].evaluateStr();
                    Expression expression = this.mConfig;
                    animatedScreenElement.folmeFromTo(strEvaluateStr, strEvaluateStr2, expression != null ? expression.evaluateStr() : null);
                }
            }
        }

        private void folmeSetTo(AnimatedScreenElement animatedScreenElement) {
            if (this.mIsStatesValid) {
                Expression[] expressionArr = this.mStates;
                if (expressionArr.length > 0) {
                    animatedScreenElement.folmeSetTo(expressionArr[0].evaluateStr());
                }
            }
        }

        private void folmeTo(AnimatedScreenElement animatedScreenElement) {
            if (this.mIsStatesValid) {
                Expression[] expressionArr = this.mStates;
                if (expressionArr.length > 0) {
                    String strEvaluateStr = expressionArr[0].evaluateStr();
                    Expression expression = this.mConfig;
                    animatedScreenElement.folmeTo(strEvaluateStr, expression != null ? expression.evaluateStr() : null);
                }
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof AnimatedScreenElement)) {
                return;
            }
            AnimatedScreenElement animatedScreenElement = (AnimatedScreenElement) target;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$FolmeCommand$Type[this.mCommand.ordinal()];
            if (i == 1) {
                folmeTo(animatedScreenElement);
                return;
            }
            if (i == 2) {
                folmeSetTo(animatedScreenElement);
            } else if (i == 3) {
                folmeFromTo(animatedScreenElement);
            } else {
                if (i != 4) {
                    return;
                }
                folmeCancel(animatedScreenElement);
            }
        }
    }

    public static class FrameRateCommand extends ActionCommand {
        private static final String TAG_NAME = "FrameRateCommand";
        private Expression mRate;

        public FrameRateCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            this.mRate = Expression.build(screenElement.getVariables(), element.getAttribute("rate"));
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            if (this.mRate != null) {
                getRoot().requestFrameRateByCommand((float) this.mRate.evaluate());
            }
        }
    }

    public static class FunctionPerformCommand extends TargetCommand {
        public static final String TAG_NAME = "FunctionCommand";

        public FunctionPerformCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mTargetType = TargetCommand.TargetType.SCREEN_ELEMENT;
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof FunctionElement)) {
                return;
            }
            ((FunctionElement) target).perform();
        }
    }

    public static class GraphicsCommand extends TargetCommand {
        public static final String TAG_NAME = "GraphicsCommand";
        private Expression mColorArrayNameExp;
        private Expression mColorExp;
        private ColorParser[] mColorParsers;
        private int[] mColors;
        private CommandType mCommand;
        private String mCurrentColorArrayName;
        private String mCurrentStopArrayName;
        private boolean mIsParamsValid;
        private boolean mIsStopsValid;
        private Expression[] mParamExps;
        private Expression mStopArrayNameExp;
        private Expression[] mStopExps;
        private float[] mStops;

        public enum CommandType {
            INVALID,
            BEGIN_FILL,
            BEGIN_GRADIENT_FILL,
            CREATE_GRADIENT_BOX,
            CURVE_TO,
            CUBIC_CURVE_TO,
            DRAW_CIRCLE,
            DRAW_ELLIPSE,
            DRAW_RECT,
            DRAW_ROUND_RECT,
            LINE_GRADIENT_STYLE,
            LINE_STYLE,
            LINE_TO,
            MOVE_TO,
            SET_RENDER_LISTENER
        }

        public GraphicsCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            checkExps(element);
            this.mColorArrayNameExp = Expression.build(getVariables(), element.getAttribute("colorArrayNameExp"));
            this.mStopArrayNameExp = Expression.build(getVariables(), element.getAttribute("stopArrayNameExp"));
            this.mColorExp = Expression.build(getVariables(), element.getAttribute("colorExp"));
            parseCommand(element);
        }

        private void beginFill(GraphicsElement graphicsElement) {
            ColorParser[] colorParserArr = this.mColorParsers;
            int color = (colorParserArr == null || colorParserArr.length <= 0) ? -16777216 : colorParserArr[0].getColor();
            Expression expression = this.mColorExp;
            if (expression != null) {
                color = (int) expression.evaluate();
            }
            graphicsElement.beginFill(color);
        }

        private void checkExps(Element element) {
            String[] strArrSplit;
            Expression[] expressionArrBuildMultiple = Expression.buildMultiple(getVariables(), element.getAttribute("paramsExp"));
            this.mParamExps = expressionArrBuildMultiple;
            this.mIsParamsValid = isExpressionsValid(expressionArrBuildMultiple);
            Expression[] expressionArrBuildMultiple2 = Expression.buildMultiple(getVariables(), element.getAttribute("stopsExp"));
            this.mStopExps = expressionArrBuildMultiple2;
            this.mIsStopsValid = isExpressionsValid(expressionArrBuildMultiple2);
            String attribute = element.getAttribute("colors");
            if (TextUtils.isEmpty(attribute) || (strArrSplit = attribute.split(ac.b)) == null || strArrSplit.length <= 0) {
                return;
            }
            this.mColorParsers = new ColorParser[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                this.mColorParsers[i] = new ColorParser(getVariables(), strArrSplit[i]);
            }
        }

        private void createGradientBox(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 4) {
                    graphicsElement.createOrUpdateGradientBox(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()), scale((float) this.mParamExps[2].evaluate()), scale((float) this.mParamExps[3].evaluate()), this.mParamExps[4].evaluateStr());
                }
            }
        }

        private void cubicCurveTo(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 5) {
                    graphicsElement.cubicCurveTo(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()), scale((float) this.mParamExps[2].evaluate()), scale((float) this.mParamExps[3].evaluate()), scale((float) this.mParamExps[4].evaluate()), scale((float) this.mParamExps[5].evaluate()));
                }
            }
        }

        private void curveTo(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 3) {
                    graphicsElement.curveTo(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()), scale((float) this.mParamExps[2].evaluate()), scale((float) this.mParamExps[3].evaluate()));
                }
            }
        }

        private void drawCircle(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 2) {
                    graphicsElement.drawCircle(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()), scale((float) this.mParamExps[2].evaluate()));
                }
            }
        }

        private void drawEllipse(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 3) {
                    graphicsElement.drawEllipse(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()), scale((float) this.mParamExps[2].evaluate()), scale((float) this.mParamExps[3].evaluate()));
                }
            }
        }

        private void drawRect(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 3) {
                    graphicsElement.drawRect(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()), scale((float) this.mParamExps[2].evaluate()), scale((float) this.mParamExps[3].evaluate()));
                }
            }
        }

        private void drawRoundRect(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 4) {
                    float fScale = scale((float) expressionArr[0].evaluate());
                    float fScale2 = scale((float) this.mParamExps[1].evaluate());
                    float fScale3 = scale((float) this.mParamExps[2].evaluate());
                    float fScale4 = scale((float) this.mParamExps[3].evaluate());
                    float fScale5 = scale((float) this.mParamExps[4].evaluate());
                    Expression[] expressionArr2 = this.mParamExps;
                    graphicsElement.drawRoundRect(fScale, fScale2, fScale3, fScale4, fScale5, expressionArr2.length > 5 ? scale((float) expressionArr2[5].evaluate()) : fScale5);
                }
            }
        }

        private void lineStyle(GraphicsElement graphicsElement) {
            int color;
            if (!this.mIsParamsValid || this.mParamExps.length <= 0) {
                return;
            }
            Expression expression = this.mColorExp;
            if (expression != null) {
                color = (int) expression.evaluate();
            } else {
                ColorParser[] colorParserArr = this.mColorParsers;
                color = (colorParserArr == null || colorParserArr.length <= 0) ? -16777216 : colorParserArr[0].getColor();
            }
            int i = color;
            float fScale = scale((float) this.mParamExps[0].evaluate());
            Expression[] expressionArr = this.mParamExps;
            int iEvaluate = expressionArr.length > 1 ? (int) expressionArr[1].evaluate() : 0;
            Expression[] expressionArr2 = this.mParamExps;
            int iEvaluate2 = expressionArr2.length > 2 ? (int) expressionArr2[2].evaluate() : 0;
            Expression[] expressionArr3 = this.mParamExps;
            graphicsElement.lineStyle(fScale, i, iEvaluate, iEvaluate2, expressionArr3.length > 3 ? (int) expressionArr3[3].evaluate() : 0);
        }

        private void lineTo(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 1) {
                    graphicsElement.lineTo(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()));
                }
            }
        }

        private void moveTo(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 1) {
                    graphicsElement.moveTo(scale((float) expressionArr[0].evaluate()), scale((float) this.mParamExps[1].evaluate()));
                }
            }
        }

        private void parseColor() {
            if (this.mColorArrayNameExp != null) {
                parseColorByArrayName();
                return;
            }
            ColorParser[] colorParserArr = this.mColorParsers;
            if (colorParserArr == null || colorParserArr.length <= 1) {
                return;
            }
            parseColorByParsers();
        }

        private void parseColorByArrayName() {
            String strEvaluateStr = this.mColorArrayNameExp.evaluateStr();
            if (TextUtils.isEmpty(strEvaluateStr)) {
                this.mColors = null;
                return;
            }
            if (strEvaluateStr.equals(this.mCurrentColorArrayName)) {
                return;
            }
            this.mCurrentColorArrayName = strEvaluateStr;
            Object obj = new IndexedVariable(strEvaluateStr, getVariables(), false).get();
            if (obj != null && (obj instanceof int[])) {
                int[] iArr = (int[]) obj;
                if (iArr.length > 1) {
                    this.mColors = iArr;
                    return;
                }
            }
            this.mColors = null;
        }

        private void parseColorByParsers() {
            if (this.mColors == null) {
                this.mColors = new int[this.mColorParsers.length];
            }
            int i = 0;
            while (true) {
                ColorParser[] colorParserArr = this.mColorParsers;
                if (i >= colorParserArr.length) {
                    return;
                }
                this.mColors[i] = colorParserArr[i].getColor();
                i++;
            }
        }

        private void parseCommand(Element element) {
            String attribute = element.getAttribute("command");
            attribute.getClass();
            switch (attribute) {
                case "lineStyle":
                    this.mCommand = CommandType.LINE_STYLE;
                    break;
                case "setRenderListener":
                    this.mCommand = CommandType.SET_RENDER_LISTENER;
                    break;
                case "lineTo":
                    this.mCommand = CommandType.LINE_TO;
                    break;
                case "beginFill":
                    this.mCommand = CommandType.BEGIN_FILL;
                    break;
                case "moveTo":
                    this.mCommand = CommandType.MOVE_TO;
                    break;
                case "drawRect":
                    this.mCommand = CommandType.DRAW_RECT;
                    break;
                case "drawCircle":
                    this.mCommand = CommandType.DRAW_CIRCLE;
                    break;
                case "lineGradientStyle":
                    this.mCommand = CommandType.LINE_GRADIENT_STYLE;
                    break;
                case "createGradientBox":
                    this.mCommand = CommandType.CREATE_GRADIENT_BOX;
                    break;
                case "drawRoundRect":
                    this.mCommand = CommandType.DRAW_ROUND_RECT;
                    break;
                case "cubicCurveTo":
                    this.mCommand = CommandType.CUBIC_CURVE_TO;
                    break;
                case "curveTo":
                    this.mCommand = CommandType.CURVE_TO;
                    break;
                case "beginGradientFill":
                    this.mCommand = CommandType.BEGIN_GRADIENT_FILL;
                    break;
                case "drawEllipse":
                    this.mCommand = CommandType.DRAW_ELLIPSE;
                    break;
                default:
                    this.mCommand = CommandType.INVALID;
                    break;
            }
        }

        private void parseStop() {
            if (this.mStopArrayNameExp != null) {
                parseStopByArrayName();
            } else if (this.mIsStopsValid) {
                parseStopByExp();
            }
        }

        private void parseStopByArrayName() {
            String strEvaluateStr = this.mStopArrayNameExp.evaluateStr();
            if (TextUtils.isEmpty(strEvaluateStr)) {
                this.mStops = null;
                return;
            }
            if (strEvaluateStr.equals(this.mCurrentStopArrayName)) {
                return;
            }
            this.mCurrentStopArrayName = strEvaluateStr;
            Object obj = new IndexedVariable(strEvaluateStr, getVariables(), false).get();
            if (obj != null && (obj instanceof float[])) {
                float[] fArr = (float[]) obj;
                if (fArr.length > 1) {
                    this.mStops = fArr;
                    return;
                }
            }
            this.mStops = null;
        }

        private void parseStopByExp() {
            if (this.mStops == null) {
                this.mStops = new float[this.mStopExps.length];
            }
            int i = 0;
            while (true) {
                Expression[] expressionArr = this.mStopExps;
                if (i >= expressionArr.length) {
                    return;
                }
                this.mStops[i] = (float) expressionArr[i].evaluate();
                i++;
            }
        }

        private float scale(float f) {
            return f * getRoot().getScale();
        }

        private void setRenderListener(GraphicsElement graphicsElement) {
            if (this.mIsParamsValid) {
                Expression[] expressionArr = this.mParamExps;
                if (expressionArr.length > 0) {
                    ScreenElement screenElementFindElement = getRoot().findElement(expressionArr[0].evaluateStr());
                    if (screenElementFindElement == null || !(screenElementFindElement instanceof FunctionElement)) {
                        return;
                    }
                    graphicsElement.setRenderListener((FunctionElement) screenElementFindElement);
                }
            }
        }

        private void setShader(GraphicsElement graphicsElement) {
            if (!this.mIsParamsValid || this.mParamExps.length <= 2) {
                return;
            }
            parseColor();
            parseStop();
            int[] iArr = this.mColors;
            if (iArr == null || iArr.length < 2) {
                MamlLog.e(TAG_NAME, "needs >= 2 number of colors");
                return;
            }
            float[] fArr = this.mStops;
            if (fArr != null && fArr.length != iArr.length) {
                MamlLog.e(TAG_NAME, "color and position arrays must be of equal length");
                return;
            }
            int iEvaluate = (int) this.mParamExps[0].evaluate();
            String strEvaluateStr = this.mParamExps[1].evaluateStr();
            String strEvaluateStr2 = this.mParamExps[2].evaluateStr();
            Expression[] expressionArr = this.mParamExps;
            int iEvaluate2 = expressionArr.length > 3 ? (int) expressionArr[3].evaluate() : 0;
            CommandType commandType = this.mCommand;
            if (commandType == CommandType.LINE_GRADIENT_STYLE) {
                graphicsElement.lineGradientStyle(iEvaluate, this.mColors, this.mStops, strEvaluateStr, strEvaluateStr2, iEvaluate2);
            } else if (commandType == CommandType.BEGIN_GRADIENT_FILL) {
                graphicsElement.beginGradientFill(iEvaluate, this.mColors, this.mStops, strEvaluateStr, strEvaluateStr2, iEvaluate2);
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof GraphicsElement)) {
                return;
            }
            GraphicsElement graphicsElement = (GraphicsElement) target;
            switch (AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$GraphicsCommand$CommandType[this.mCommand.ordinal()]) {
                case 1:
                    lineTo(graphicsElement);
                    break;
                case 2:
                    moveTo(graphicsElement);
                    break;
                case 3:
                    curveTo(graphicsElement);
                    break;
                case 4:
                    drawRect(graphicsElement);
                    break;
                case 5:
                    beginFill(graphicsElement);
                    break;
                case 6:
                    lineStyle(graphicsElement);
                    break;
                case 7:
                    drawCircle(graphicsElement);
                    break;
                case 8:
                    drawEllipse(graphicsElement);
                    break;
                case 9:
                    cubicCurveTo(graphicsElement);
                    break;
                case 10:
                    drawRoundRect(graphicsElement);
                    break;
                case 11:
                case 12:
                    setShader(graphicsElement);
                    break;
                case 13:
                    createGradientBox(graphicsElement);
                    break;
                case 14:
                    setRenderListener(graphicsElement);
                    break;
            }
        }
    }

    public static class IfCommand extends ActionCommand {
        private static final String ALTERNATE = "Alternate";
        private static final String CONSEQUENT = "Consequent";
        public static final String TAG_NAME = "IfCommand";
        private MultiCommand mAlternateCommand;
        private Expression mCondition;
        private MultiCommand mConsequentCommand;

        public IfCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            this.mCondition = Expression.build(screenElement.getVariables(), element.getAttribute("ifCondition"));
            NodeList childNodes = element.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i).getNodeType() == 1) {
                    Element element2 = (Element) childNodes.item(i);
                    String tagName = element2.getTagName();
                    if (CONSEQUENT.equalsIgnoreCase(tagName) && this.mConsequentCommand == null) {
                        this.mConsequentCommand = new MultiCommand(screenElement, element2);
                    } else if (ALTERNATE.equalsIgnoreCase(tagName) && this.mAlternateCommand == null) {
                        this.mAlternateCommand = new MultiCommand(screenElement, element2);
                    }
                }
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Expression expression = this.mCondition;
            if (expression != null) {
                if (expression.evaluate() <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                    MultiCommand multiCommand = this.mAlternateCommand;
                    if (multiCommand != null) {
                        multiCommand.perform();
                        return;
                    }
                    return;
                }
                MultiCommand multiCommand2 = this.mConsequentCommand;
                if (multiCommand2 != null) {
                    multiCommand2.perform();
                }
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void finish() {
            MultiCommand multiCommand = this.mAlternateCommand;
            if (multiCommand != null) {
                multiCommand.finish();
            }
            MultiCommand multiCommand2 = this.mConsequentCommand;
            if (multiCommand2 != null) {
                multiCommand2.finish();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            MultiCommand multiCommand = this.mAlternateCommand;
            if (multiCommand != null) {
                multiCommand.init();
            }
            MultiCommand multiCommand2 = this.mConsequentCommand;
            if (multiCommand2 != null) {
                multiCommand2.init();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void pause() {
            MultiCommand multiCommand = this.mAlternateCommand;
            if (multiCommand != null) {
                multiCommand.pause();
            }
            MultiCommand multiCommand2 = this.mConsequentCommand;
            if (multiCommand2 != null) {
                multiCommand2.pause();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void resume() {
            MultiCommand multiCommand = this.mAlternateCommand;
            if (multiCommand != null) {
                multiCommand.resume();
            }
            MultiCommand multiCommand2 = this.mConsequentCommand;
            if (multiCommand2 != null) {
                multiCommand2.resume();
            }
        }
    }

    public static class IntentCommand extends ActionCommand {
        private static final String TAG_FALLBACK = "Fallback";
        public static final String TAG_NAME = "IntentCommand";
        private ObjVar mActivityOptionsBundle;
        private boolean mDisableMotionAnim;
        private CommandTrigger mFallbackTrigger;
        private int mFlags;
        private Intent mIntent;
        private IntentInfo mIntentInfo;
        private IntentType mIntentType;
        private IndexedVariable mIntentVar;
        private boolean mJumptoMain;
        private long mMaxVersion;
        private long mMinVersion;

        public enum IntentType {
            Activity,
            Broadcast,
            Service,
            Var
        }

        public IntentCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            IntentType intentType = IntentType.Activity;
            this.mIntentType = intentType;
            this.mIntentInfo = new IntentInfo(element, getVariables());
            boolean z = Boolean.parseBoolean(element.getAttribute("broadcast"));
            String attribute = element.getAttribute("type");
            if (z || "broadcast".equals(attribute)) {
                this.mIntentType = IntentType.Broadcast;
            } else if ("service".equals(attribute)) {
                this.mIntentType = IntentType.Service;
            } else if ("activity".equals(attribute)) {
                this.mIntentType = intentType;
            } else if ("var".equals(attribute)) {
                this.mIntentType = IntentType.Var;
                String attribute2 = element.getAttribute("intentVar");
                if (!TextUtils.isEmpty(attribute2)) {
                    this.mIntentVar = new IndexedVariable(attribute2, getVariables(), false);
                }
            }
            this.mMinVersion = Utils.getAttrAsLong(element, "minVersion", -1L);
            this.mMaxVersion = Utils.getAttrAsLong(element, "maxVersion", -1L);
            this.mFlags = Utils.getAttrAsInt(element, "flags", -1);
            this.mDisableMotionAnim = Boolean.parseBoolean(element.getAttribute("disableMotionAnim"));
            this.mJumptoMain = Boolean.parseBoolean(element.getAttribute("jumptoMain"));
            String attribute3 = element.getAttribute("activityOption");
            this.mActivityOptionsBundle = TextUtils.isEmpty(attribute3) ? null : new ObjVar(attribute3, getVariables());
            Element child = Utils.getChild(element, TAG_FALLBACK);
            if (child != null) {
                this.mFallbackTrigger = new CommandTrigger(child, screenElement);
            }
        }

        /* JADX WARN: Code duplicated, block: B:108:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:59:0x00d9  */
        /* JADX WARN: Code duplicated, block: B:61:0x00dd  */
        /* JADX WARN: Code duplicated, block: B:63:0x00e1  */
        /* JADX WARN: Code duplicated, block: B:64:0x00e2 A[Catch: Exception -> 0x00d7, TryCatch #2 {Exception -> 0x00d7, blocks: (B:45:0x00af, B:48:0x00c2, B:50:0x00c8, B:68:0x00ec, B:64:0x00e2), top: B:106:0x00af }] */
        /* JADX WARN: Code duplicated, block: B:68:0x00ec A[Catch: Exception -> 0x00d7, TRY_LEAVE, TryCatch #2 {Exception -> 0x00d7, blocks: (B:45:0x00af, B:48:0x00c2, B:50:0x00c8, B:68:0x00ec, B:64:0x00e2), top: B:106:0x00af }] */
        /* JADX WARN: Code duplicated, block: B:77:0x010d A[Catch: Exception -> 0x002d, TryCatch #0 {Exception -> 0x002d, blocks: (B:5:0x000a, B:14:0x0022, B:16:0x0026, B:19:0x0030, B:20:0x003b, B:21:0x0046, B:23:0x004a, B:25:0x0052, B:27:0x0066, B:29:0x0072, B:31:0x0078, B:33:0x0083, B:35:0x008d, B:38:0x0095, B:40:0x009d, B:75:0x00ff, B:77:0x010d, B:79:0x0111, B:80:0x011c, B:81:0x0126, B:42:0x00a3, B:44:0x00ad, B:74:0x00f8, B:82:0x0130, B:88:0x013b, B:90:0x0147, B:94:0x0168, B:91:0x014e, B:93:0x015e), top: B:103:0x000a }] */
        /* JADX WARN: Code duplicated, block: B:79:0x0111 A[Catch: Exception -> 0x002d, TryCatch #0 {Exception -> 0x002d, blocks: (B:5:0x000a, B:14:0x0022, B:16:0x0026, B:19:0x0030, B:20:0x003b, B:21:0x0046, B:23:0x004a, B:25:0x0052, B:27:0x0066, B:29:0x0072, B:31:0x0078, B:33:0x0083, B:35:0x008d, B:38:0x0095, B:40:0x009d, B:75:0x00ff, B:77:0x010d, B:79:0x0111, B:80:0x011c, B:81:0x0126, B:42:0x00a3, B:44:0x00ad, B:74:0x00f8, B:82:0x0130, B:88:0x013b, B:90:0x0147, B:94:0x0168, B:91:0x014e, B:93:0x015e), top: B:103:0x000a }] */
        /* JADX WARN: Code duplicated, block: B:81:0x0126 A[Catch: Exception -> 0x002d, TryCatch #0 {Exception -> 0x002d, blocks: (B:5:0x000a, B:14:0x0022, B:16:0x0026, B:19:0x0030, B:20:0x003b, B:21:0x0046, B:23:0x004a, B:25:0x0052, B:27:0x0066, B:29:0x0072, B:31:0x0078, B:33:0x0083, B:35:0x008d, B:38:0x0095, B:40:0x009d, B:75:0x00ff, B:77:0x010d, B:79:0x0111, B:80:0x011c, B:81:0x0126, B:42:0x00a3, B:44:0x00ad, B:74:0x00f8, B:82:0x0130, B:88:0x013b, B:90:0x0147, B:94:0x0168, B:91:0x014e, B:93:0x015e), top: B:103:0x000a }] */
        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            boolean z;
            String packageName;
            ActivityInfo activityInfo;
            long j;
            CommandTrigger commandTrigger;
            Pair<Context, Rect> contextAndRect;
            Intent launchIntentForPackage;
            IndexedVariable indexedVariable;
            Intent intent = this.mIntent;
            if (intent == null) {
                return;
            }
            this.mIntentInfo.update(intent);
            boolean z2 = false;
            try {
                int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$IntentCommand$IntentType[this.mIntentType.ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        Utils.sendBroadcast(getContext(), this.mIntent);
                        return;
                    }
                    if (i == 3) {
                        Utils.startService(getContext(), this.mIntent);
                        return;
                    } else {
                        if (i == 4 && (indexedVariable = this.mIntentVar) != null) {
                            indexedVariable.set(this.mIntent);
                            return;
                        }
                        return;
                    }
                }
                ObjVar objVar = this.mActivityOptionsBundle;
                Bundle bundle = objVar != null ? (Bundle) objVar.get() : null;
                PackageManager packageManager = getContext().getPackageManager();
                String str = this.mIntent.getPackage();
                if (TextUtils.isEmpty(str)) {
                    z = true;
                } else {
                    z = packageManager.getPackageInfo(str, 0).applicationInfo.enabled;
                    if (this.mJumptoMain && (launchIntentForPackage = packageManager.getLaunchIntentForPackage(str)) != null) {
                        this.mIntent.setComponent(launchIntentForPackage.getComponent());
                    }
                }
                List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(this.mIntent, 65536);
                if (listQueryIntentActivities == null || listQueryIntentActivities.size() <= 0 || !z) {
                    CommandTrigger commandTrigger2 = this.mFallbackTrigger;
                    if (commandTrigger2 == null) {
                        if (!TextUtils.isEmpty(this.mIntent.getPackage())) {
                            packageName = this.mIntent.getPackage();
                        } else if (TextUtils.isEmpty(this.mIntent.getComponent().getPackageName())) {
                            return;
                        } else {
                            packageName = this.mIntent.getComponent().getPackageName();
                        }
                        HideSdkDependencyUtils.PreloadedAppPolicy_installPreloadedDataApp(getContext(), packageName, this.mIntent, bundle);
                        return;
                    }
                    try {
                        commandTrigger2.perform();
                        return;
                    } catch (Exception e) {
                        e = e;
                        z2 = true;
                        if (z2 || this.mFallbackTrigger == null) {
                            MamlLog.e("ActionCommand", e.toString());
                            return;
                        } else {
                            MamlLog.i("ActionCommand", "fail to send Intent, fallback...");
                            this.mFallbackTrigger.perform();
                            return;
                        }
                    }
                }
                if ((this.mMinVersion > 0 || this.mMaxVersion > 0) && (activityInfo = listQueryIntentActivities.get(0).activityInfo) != null) {
                    try {
                        long jLongValue = ((Long) ReflectionHelper.getFieldValue(ApplicationInfo.class, activityInfo.applicationInfo, "longVersionCode")).longValue();
                        if (jLongValue > 0) {
                            long j2 = this.mMinVersion;
                            if (j2 > 0) {
                                long j3 = this.mMaxVersion;
                                if (j3 > 0) {
                                    if (jLongValue < j2 || jLongValue > j3) {
                                        commandTrigger = this.mFallbackTrigger;
                                        if (commandTrigger != null) {
                                            try {
                                                commandTrigger.perform();
                                                return;
                                            } catch (Exception e2) {
                                                e = e2;
                                                MamlLog.e(TAG_NAME, "parse min&max version code error", e);
                                                contextAndRect = getScreenElement().getRoot().getContextAndRect();
                                                if (contextAndRect != null) {
                                                    Utils.startActivity(getContext(), this.mIntent, bundle);
                                                    return;
                                                }
                                                if (!this.mDisableMotionAnim) {
                                                    this.mIntent.putExtra("maml_view_location_on_screen", (Parcelable) contextAndRect.second);
                                                }
                                                Utils.startActivity((Context) contextAndRect.first, this.mIntent, bundle);
                                            }
                                        }
                                    }
                                } else if (j2 > 0) {
                                    j = this.mMaxVersion;
                                    if (j > 0 && jLongValue > j) {
                                        commandTrigger = this.mFallbackTrigger;
                                        if (commandTrigger != null) {
                                            commandTrigger.perform();
                                            return;
                                        }
                                    }
                                } else if (jLongValue < j2) {
                                    commandTrigger = this.mFallbackTrigger;
                                    if (commandTrigger != null) {
                                        commandTrigger.perform();
                                        return;
                                    }
                                }
                            } else if (j2 > 0) {
                                j = this.mMaxVersion;
                                if (j > 0) {
                                    commandTrigger = this.mFallbackTrigger;
                                    if (commandTrigger != null) {
                                        commandTrigger.perform();
                                        return;
                                    }
                                }
                            } else if (jLongValue < j2) {
                                commandTrigger = this.mFallbackTrigger;
                                if (commandTrigger != null) {
                                    commandTrigger.perform();
                                    return;
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                }
                contextAndRect = getScreenElement().getRoot().getContextAndRect();
                if (contextAndRect != null) {
                    Utils.startActivity(getContext(), this.mIntent, bundle);
                    return;
                }
                if (!this.mDisableMotionAnim) {
                    this.mIntent.putExtra("maml_view_location_on_screen", (Parcelable) contextAndRect.second);
                }
                Utils.startActivity((Context) contextAndRect.first, this.mIntent, bundle);
            } catch (Exception e4) {
                e = e4;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void finish() {
            CommandTrigger commandTrigger = this.mFallbackTrigger;
            if (commandTrigger != null) {
                commandTrigger.finish();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            Task taskFindTask = getRoot().findTask(this.mIntentInfo.getId());
            if (taskFindTask != null && !TextUtils.isEmpty(taskFindTask.action)) {
                this.mIntentInfo.set(taskFindTask);
            }
            if (Utils.isProtectedIntent(this.mIntentInfo.getAction())) {
                return;
            }
            Intent intent = new Intent();
            this.mIntent = intent;
            this.mIntentInfo.update(intent);
            int i = this.mFlags;
            if (i != -1) {
                this.mIntent.setFlags(i);
            } else if (this.mIntentType == IntentType.Activity) {
                this.mIntent.setFlags(872415232);
            }
            CommandTrigger commandTrigger = this.mFallbackTrigger;
            if (commandTrigger != null) {
                commandTrigger.init();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void pause() {
            CommandTrigger commandTrigger = this.mFallbackTrigger;
            if (commandTrigger != null) {
                commandTrigger.pause();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void resume() {
            CommandTrigger commandTrigger = this.mFallbackTrigger;
            if (commandTrigger != null) {
                commandTrigger.resume();
            }
        }
    }

    public static class JsonObjectCommand extends TargetCommand {
        public static final String TAG_NAME = "JsonObjectCommand";
        private CommandType mCommand;
        private Expression mNameExp;
        private Expression mValueExp;

        public enum CommandType {
            INVALID,
            PUT_STRING,
            PUT_BOOLEAN,
            PUT_INT,
            PUT_JSON_OBJECT,
            PUT_JSON_ARRAY,
            REMOVE
        }

        public JsonObjectCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute("command");
            this.mNameExp = Expression.build(getVariables(), element.getAttribute("nameExp"));
            this.mValueExp = Expression.build(getVariables(), element.getAttribute("valueExp"));
            attribute.getClass();
            switch (attribute) {
                case "putInt":
                    this.mCommand = CommandType.PUT_INT;
                    break;
                case "remove":
                    this.mCommand = CommandType.REMOVE;
                    break;
                case "putJsonObject":
                    this.mCommand = CommandType.PUT_JSON_OBJECT;
                    break;
                case "putString":
                    this.mCommand = CommandType.PUT_STRING;
                    break;
                case "putBoolean":
                    this.mCommand = CommandType.PUT_BOOLEAN;
                    break;
                case "putJsonArray":
                    this.mCommand = CommandType.PUT_JSON_ARRAY;
                    break;
                default:
                    this.mCommand = CommandType.INVALID;
                    break;
            }
            this.mTargetType = TargetCommand.TargetType.VARIABLE;
        }

        /* JADX WARN: Code duplicated, block: B:34:0x0061  */
        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object objValueOf;
            String strEvaluateStr;
            Expression expression;
            Object target = getTarget();
            if (target == null || !(target instanceof JSONObject)) {
                return;
            }
            JSONObject jSONObject = (JSONObject) target;
            CommandType commandType = this.mCommand;
            if (commandType == CommandType.REMOVE) {
                Expression expression2 = this.mNameExp;
                strEvaluateStr = expression2 != null ? expression2.evaluateStr() : null;
                if (strEvaluateStr != null) {
                    jSONObject.remove(strEvaluateStr);
                    getVariables().put(this.mTargetName, jSONObject);
                    return;
                }
                return;
            }
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$JsonObjectCommand$CommandType[commandType.ordinal()];
            if (i == 1) {
                Expression expression3 = this.mValueExp;
                if (expression3 != null) {
                    objValueOf = Double.valueOf(expression3.evaluate());
                } else {
                    objValueOf = null;
                }
            } else if (i == 2) {
                Expression expression4 = this.mValueExp;
                if (expression4 != null) {
                    objValueOf = expression4.evaluateStr();
                } else {
                    objValueOf = null;
                }
            } else if (i == 3) {
                Expression expression5 = this.mValueExp;
                if (expression5 != null) {
                    objValueOf = expression5.evaluateJsonObject();
                } else {
                    objValueOf = null;
                }
            } else if (i == 4) {
                Expression expression6 = this.mValueExp;
                if (expression6 != null) {
                    objValueOf = expression6.evaluateJsonArray();
                } else {
                    objValueOf = null;
                }
            } else if (i == 5 && (expression = this.mValueExp) != null) {
                String strEvaluateStr2 = expression.evaluateStr();
                objValueOf = Boolean.valueOf(("0".equals(strEvaluateStr2) || "false".equals(strEvaluateStr2)) ? false : true);
            } else {
                objValueOf = null;
            }
            Expression expression7 = this.mNameExp;
            strEvaluateStr = expression7 != null ? expression7.evaluateStr() : null;
            if (strEvaluateStr == null || objValueOf == null) {
                return;
            }
            try {
                jSONObject.put(strEvaluateStr, objValueOf);
                getVariables().put(this.mTargetName, jSONObject);
            } catch (JSONException unused) {
            }
        }
    }

    public static class LoopCommand extends MultiCommand {
        private static final long COUNT_WARNING = 10000;
        public static final String TAG_NAME = "LoopCommand";
        private Expression mBeginExp;
        private Expression mConditionExp;
        private Expression mCountExp;
        private Expression mEndExp;
        private IndexedVariable mIndexVar;

        public LoopCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute("indexName");
            Variables variables = getVariables();
            if (!TextUtils.isEmpty(attribute)) {
                this.mIndexVar = new IndexedVariable(attribute, variables, true);
            }
            this.mBeginExp = Expression.build(variables, element.getAttribute("begin"));
            Expression expressionBuild = Expression.build(variables, element.getAttribute(com.xiaomi.onetrack.h.b.a.h));
            this.mCountExp = expressionBuild;
            if (expressionBuild == null) {
                this.mEndExp = Expression.build(variables, element.getAttribute("end"));
            }
            this.mConditionExp = Expression.build(variables, element.getAttribute("loopCondition"));
        }

        @Override // com.miui.maml.ActionCommand.MultiCommand, com.miui.maml.ActionCommand
        public void doPerform() {
            int iEvaluate;
            Expression expression = this.mBeginExp;
            int iEvaluate2 = expression == null ? 0 : (int) expression.evaluate();
            Expression expression2 = this.mCountExp;
            if (expression2 != null) {
                iEvaluate = (((int) expression2.evaluate()) + iEvaluate2) - 1;
            } else {
                Expression expression3 = this.mEndExp;
                iEvaluate = expression3 == null ? 0 : (int) expression3.evaluate();
            }
            int i = iEvaluate - iEvaluate2;
            if (i > COUNT_WARNING) {
                MamlLog.w("ActionCommand", "count is too large: " + i + ", exceeds WARNING " + COUNT_WARNING);
            }
            while (iEvaluate2 <= iEvaluate) {
                Expression expression4 = this.mConditionExp;
                if (expression4 != null && expression4.evaluate() <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                    return;
                }
                IndexedVariable indexedVariable = this.mIndexVar;
                if (indexedVariable != null) {
                    indexedVariable.set(iEvaluate2);
                }
                int size = this.mCommands.size();
                for (int i2 = 0; i2 < size; i2++) {
                    this.mCommands.get(i2).perform();
                }
                iEvaluate2++;
            }
        }
    }

    public static class LottieCommand extends TargetCommand {
        public static final String TAG_NAME = "LottieCommand";
        private CommandType mCommand;
        private boolean mIsParamsValid;
        private Expression[] mParams;

        public enum CommandType {
            PAUSE,
            PLAY,
            RESUME,
            STOP,
            SET_SPEED,
            GOTO_AND_PLAY,
            GOTO_AND_STOP,
            PLAY_SEGMENTS,
            SET_LOOP_COUNT
        }

        public LottieCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute("params");
            Expression[] expressionArrBuildMultiple = Expression.buildMultiple(getVariables(), attribute);
            this.mParams = expressionArrBuildMultiple;
            boolean zIsExpressionsValid = isExpressionsValid(expressionArrBuildMultiple);
            this.mIsParamsValid = zIsExpressionsValid;
            if (!zIsExpressionsValid) {
                MamlLog.e(TAG_NAME, "Wrong params: " + attribute);
            }
            parseCommand(element);
        }

        private void parseCommand(Element element) {
            String attribute = element.getAttribute("command");
            attribute.getClass();
            switch (attribute) {
                case "setLoopCount":
                    this.mCommand = CommandType.SET_LOOP_COUNT;
                    break;
                case "goToAndPlay":
                    this.mCommand = CommandType.GOTO_AND_PLAY;
                    break;
                case "goToAndStop":
                    this.mCommand = CommandType.GOTO_AND_STOP;
                    break;
                case "resume":
                    this.mCommand = CommandType.RESUME;
                    break;
                case "play":
                    this.mCommand = CommandType.PLAY;
                    break;
                case "stop":
                    this.mCommand = CommandType.STOP;
                    break;
                case "pause":
                    this.mCommand = CommandType.PAUSE;
                    break;
                case "playSegments":
                    this.mCommand = CommandType.PLAY_SEGMENTS;
                    break;
                case "setSpeed":
                    this.mCommand = CommandType.SET_SPEED;
                    break;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof LottieScreenElement)) {
                return;
            }
            LottieScreenElement lottieScreenElement = (LottieScreenElement) target;
            switch (AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$LottieCommand$CommandType[this.mCommand.ordinal()]) {
                case 1:
                    lottieScreenElement.playAnimation();
                    break;
                case 2:
                    lottieScreenElement.pauseAnimation();
                    break;
                case 3:
                    lottieScreenElement.resumeAnimation();
                    break;
                case 4:
                    lottieScreenElement.stopAnimation();
                    break;
                case 5:
                    if (this.mIsParamsValid) {
                        Expression[] expressionArr = this.mParams;
                        if (expressionArr.length == 1) {
                            lottieScreenElement.setSpeed((float) expressionArr[0].evaluate());
                        }
                    }
                    break;
                case 6:
                    if (this.mIsParamsValid) {
                        Expression[] expressionArr2 = this.mParams;
                        if (expressionArr2.length == 1) {
                            lottieScreenElement.goToAndPlay((int) expressionArr2[0].evaluate());
                        }
                    }
                    break;
                case 7:
                    if (this.mIsParamsValid) {
                        Expression[] expressionArr3 = this.mParams;
                        if (expressionArr3.length == 1) {
                            lottieScreenElement.goToAndStop((int) expressionArr3[0].evaluate());
                        }
                    }
                    break;
                case 8:
                    if (this.mIsParamsValid) {
                        Expression[] expressionArr4 = this.mParams;
                        if (expressionArr4.length == 2) {
                            lottieScreenElement.playSegments((int) expressionArr4[0].evaluate(), (int) this.mParams[1].evaluate());
                        }
                    }
                    break;
                case 9:
                    if (this.mIsParamsValid) {
                        Expression[] expressionArr5 = this.mParams;
                        if (expressionArr5.length == 1) {
                            lottieScreenElement.setLoopCount((int) expressionArr5[0].evaluate());
                        }
                    }
                    break;
            }
        }
    }

    public static class MethodCommand extends BaseMethodCommand {
        public static final String TAG_NAME = "MethodCommand";
        private Constructor<?> mCtor;
        private Method mMethod;
        private String mMethodName;

        public MethodCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mMethodName = element.getAttribute("method");
            this.mLogStr = "MethodCommand, " + this.mLogStr + ", method=" + this.mMethodName + "\n    ";
        }

        /* JADX WARN: Code duplicated, block: B:18:0x002b  */
        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            String str;
            prepareParams();
            int i = 0;
            try {
                try {
                    int i2 = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[this.mTargetType.ordinal()];
                    Object objInvoke = null;
                    if (i2 == 1 || i2 == 2) {
                        if (this.mMethod == null) {
                            loadMethod();
                        }
                        if (this.mMethod != null) {
                            objInvoke = this.mMethod.invoke(getTarget(), this.mParamValues);
                            i = 1;
                        } else {
                            i = -1;
                        }
                    } else if (i2 == 5) {
                        Constructor<?> constructor = this.mCtor;
                        if (constructor != null) {
                            objInvoke = constructor.newInstance(this.mParamValues);
                            i = 1;
                        } else {
                            i = -1;
                        }
                    }
                    IndexedVariable indexedVariable = this.mReturnVar;
                    if (indexedVariable != null) {
                        indexedVariable.set(objInvoke);
                    }
                    IndexedVariable indexedVariable2 = this.mErrorCodeVar;
                    if (indexedVariable2 != null) {
                        indexedVariable2.set(i);
                    }
                } catch (Exception e) {
                    Throwable cause = e.getCause();
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mLogStr);
                    sb.append(e.toString());
                    if (cause != null) {
                        str = "\n cause: " + cause.toString();
                    } else {
                        str = "";
                    }
                    sb.append(str);
                    MamlLog.e("ActionCommand", sb.toString());
                    IndexedVariable indexedVariable3 = this.mErrorCodeVar;
                    if (indexedVariable3 != null) {
                        indexedVariable3.set(-2);
                    }
                }
            } catch (Throwable th) {
                IndexedVariable indexedVariable4 = this.mErrorCodeVar;
                if (indexedVariable4 != null) {
                    indexedVariable4.set(i);
                }
                throw th;
            }
        }

        @Override // com.miui.maml.ActionCommand.BaseMethodCommand, com.miui.maml.ActionCommand.TargetCommand, com.miui.maml.ActionCommand
        public void init() {
            super.init();
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[this.mTargetType.ordinal()];
            if (i == 1 || i == 2) {
                if (this.mMethod == null) {
                    loadMethod();
                    return;
                }
                return;
            }
            if (i != 5) {
                return;
            }
            if (!getRoot().getCapability(4)) {
                this.mCtor = null;
                return;
            }
            if (this.mCtor == null) {
                Class<?> cls = this.mTargetClass;
                if (cls == null) {
                    MamlLog.e("ActionCommand", this.mLogStr + "init, class is null.");
                    return;
                }
                try {
                    this.mCtor = cls.getConstructor(this.mParamTypes);
                } catch (NoSuchMethodException e) {
                    MamlLog.e("ActionCommand", this.mLogStr + "init, fail to find method. " + e.toString());
                }
            }
        }

        public void loadMethod() {
            Object target;
            if (this.mTargetClass == null && (target = getTarget()) != null) {
                this.mTargetClass = target.getClass();
            }
            Class<?> cls = this.mTargetClass;
            if (cls == null) {
                MamlLog.e("ActionCommand", this.mLogStr + "loadMethod(), class is null.");
                return;
            }
            try {
                this.mMethod = cls.getMethod(this.mMethodName, this.mParamTypes);
            } catch (NoSuchMethodException e) {
                MamlLog.e("ActionCommand", this.mLogStr + "loadMethod(). " + e.toString());
            }
            MamlLog.d("ActionCommand", this.mLogStr + "loadMethod(), successful.  " + this.mMethod.toString());
        }
    }

    public static class ModeToggleHelper {
        private int mCurModeIndex;
        private int mCurToggleIndex;
        private ArrayList<Integer> mModeIds;
        private ArrayList<String> mModeNames;
        private boolean mToggle;
        private boolean mToggleAll;
        private ArrayList<Integer> mToggleModes;

        private ModeToggleHelper() {
            this.mModeNames = new ArrayList<>();
            this.mModeIds = new ArrayList<>();
            this.mToggleModes = new ArrayList<>();
        }

        public /* synthetic */ ModeToggleHelper(AnonymousClass1 anonymousClass1) {
            this();
        }

        private int findMode(String str) {
            for (int i = 0; i < this.mModeNames.size(); i++) {
                if (this.mModeNames.get(i).equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        public void addMode(String str, int i) {
            this.mModeNames.add(str);
            this.mModeIds.add(Integer.valueOf(i));
        }

        public boolean build(String str) {
            int iFindMode = findMode(str);
            if (iFindMode >= 0) {
                this.mCurModeIndex = iFindMode;
                return true;
            }
            if ("toggle".equals(str)) {
                this.mToggleAll = true;
                return true;
            }
            for (String str2 : str.split(ac.b)) {
                int iFindMode2 = findMode(str2);
                if (iFindMode2 < 0) {
                    return false;
                }
                this.mToggleModes.add(Integer.valueOf(iFindMode2));
            }
            this.mToggle = true;
            return true;
        }

        public void click() {
            if (this.mToggle) {
                int i = this.mCurToggleIndex + 1;
                this.mCurToggleIndex = i;
                int size = i % this.mToggleModes.size();
                this.mCurToggleIndex = size;
                this.mCurModeIndex = this.mToggleModes.get(size).intValue();
                return;
            }
            if (this.mToggleAll) {
                int i2 = this.mCurModeIndex + 1;
                this.mCurModeIndex = i2;
                this.mCurModeIndex = i2 % this.mModeNames.size();
            }
        }

        public int getModeId() {
            return this.mModeIds.get(this.mCurModeIndex).intValue();
        }

        public String getModeName() {
            return this.mModeNames.get(this.mCurModeIndex);
        }
    }

    public static class MultiCommand extends ActionCommand {
        public static final String TAG_NAME = "MultiCommand";
        public static final String TAG_NAME1 = "GroupCommand";
        protected ArrayList<ActionCommand> mCommands;

        public MultiCommand(final ScreenElement screenElement, Element element) {
            super(screenElement);
            this.mCommands = new ArrayList<>();
            Utils.traverseXmlElementChildren(element, null, new Utils.XmlTraverseListener() { // from class: com.miui.maml.ActionCommand.MultiCommand.1
                @Override // com.miui.maml.util.Utils.XmlTraverseListener
                public void onChild(Element element2) {
                    ActionCommand actionCommandCreate = ActionCommand.create(element2, screenElement);
                    if (actionCommandCreate != null) {
                        MultiCommand.this.mCommands.add(actionCommandCreate);
                    }
                }
            });
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Iterator<ActionCommand> it = this.mCommands.iterator();
            while (it.hasNext()) {
                it.next().perform();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void finish() {
            Iterator<ActionCommand> it = this.mCommands.iterator();
            while (it.hasNext()) {
                it.next().finish();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            Iterator<ActionCommand> it = this.mCommands.iterator();
            while (it.hasNext()) {
                it.next().init();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void pause() {
            Iterator<ActionCommand> it = this.mCommands.iterator();
            while (it.hasNext()) {
                it.next().pause();
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void resume() {
            Iterator<ActionCommand> it = this.mCommands.iterator();
            while (it.hasNext()) {
                it.next().resume();
            }
        }
    }

    public static class MusicCommand extends TargetCommand {
        public static final String TAG_NAME = "MusicCommand";
        private CommandType mCommand;
        private boolean mIsParamsValid;
        private Expression[] mParams;

        public enum CommandType {
            PAUSE,
            PLAY,
            PREV,
            NEXT,
            SET_PROGRESS
        }

        public MusicCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute("params");
            Expression[] expressionArrBuildMultiple = Expression.buildMultiple(getVariables(), attribute);
            this.mParams = expressionArrBuildMultiple;
            boolean zIsExpressionsValid = isExpressionsValid(expressionArrBuildMultiple);
            this.mIsParamsValid = zIsExpressionsValid;
            if (!zIsExpressionsValid) {
                MamlLog.e(TAG_NAME, "Wrong params: " + attribute);
            }
            parseCommand(element);
        }

        private void parseCommand(Element element) {
            String attribute = element.getAttribute("command");
            attribute.getClass();
            switch (attribute) {
                case "next":
                    this.mCommand = CommandType.NEXT;
                    break;
                case "play":
                    this.mCommand = CommandType.PLAY;
                    break;
                case "prev":
                    this.mCommand = CommandType.PREV;
                    break;
                case "pause":
                    this.mCommand = CommandType.PAUSE;
                    break;
                case "setProgress":
                    this.mCommand = CommandType.SET_PROGRESS;
                    break;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof MusicControlScreenElement)) {
                return;
            }
            MusicControlScreenElement musicControlScreenElement = (MusicControlScreenElement) target;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$MusicCommand$CommandType[this.mCommand.ordinal()];
            if (i == 1) {
                musicControlScreenElement.doCommand("music_play");
                return;
            }
            if (i == 2) {
                musicControlScreenElement.doCommand("music_pause");
                return;
            }
            if (i == 3) {
                musicControlScreenElement.doCommand("music_prev");
                return;
            }
            if (i == 4) {
                musicControlScreenElement.doCommand("music_next");
                return;
            }
            if (i == 5 && this.mIsParamsValid) {
                Expression[] expressionArr = this.mParams;
                if (expressionArr.length == 1) {
                    musicControlScreenElement.seekTo((float) expressionArr[0].evaluate());
                }
            }
        }
    }

    public static abstract class NotificationReceiver extends StatefulActionCommand implements NotifierManager.OnNotifyListener {
        private NotifierManager mNotifierManager;
        private String mType;

        public NotificationReceiver(ScreenElement screenElement, String str, String str2) {
            super(screenElement, str);
            this.mType = str2;
            this.mNotifierManager = NotifierManager.getInstance(getContext());
        }

        public void asyncUpdate() {
            ActionCommand.mHandler.post(new Runnable() { // from class: com.miui.maml.ActionCommand.NotificationReceiver.1
                @Override // java.lang.Runnable
                public void run() {
                    NotificationReceiver.this.update();
                }
            });
        }

        @Override // com.miui.maml.ActionCommand
        public void finish() {
            this.mNotifierManager.releaseNotifier(this.mType, this);
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            update();
            this.mNotifierManager.acquireNotifier(this.mType, this);
        }

        @Override // com.miui.maml.NotifierManager.OnNotifyListener
        public void onNotify(Context context, Intent intent, Object obj) {
            asyncUpdate();
        }

        @Override // com.miui.maml.ActionCommand
        public void pause() {
            this.mNotifierManager.pause(this.mType, this);
        }

        @Override // com.miui.maml.ActionCommand
        public void resume() {
            update();
            this.mNotifierManager.resume(this.mType, this);
        }

        public abstract void update();
    }

    public static class ObjVar {
        private int mIndex;
        private Expression mIndexArr;
        private Variables mVars;

        public ObjVar(String str, Variables variables) {
            this.mVars = variables;
            int iIndexOf = str.indexOf(91);
            if (iIndexOf > 0) {
                try {
                    String strSubstring = str.substring(0, iIndexOf);
                    try {
                        this.mIndexArr = Expression.build(variables, str.substring(iIndexOf + 1, str.length() - 1));
                    } catch (IndexOutOfBoundsException unused) {
                    }
                    str = strSubstring;
                } catch (IndexOutOfBoundsException unused2) {
                }
            }
            this.mIndex = variables.registerVariable(str);
        }

        public Object get() {
            Expression expression;
            Object obj = this.mVars.get(this.mIndex);
            if (obj == null || (expression = this.mIndexArr) == null || !(obj instanceof Object[])) {
                return obj;
            }
            try {
                return ((Object[]) obj)[(int) expression.evaluate()];
            } catch (IndexOutOfBoundsException unused) {
                return null;
            }
        }
    }

    public static class OnOffCommandHelper {
        protected boolean mIsOn;
        protected boolean mIsToggle;

        public OnOffCommandHelper(String str) {
            if (str.equalsIgnoreCase("toggle")) {
                this.mIsToggle = true;
            } else if (str.equalsIgnoreCase("on")) {
                this.mIsOn = true;
            } else if (str.equalsIgnoreCase("off")) {
                this.mIsOn = false;
            }
        }
    }

    public static class PagCommand extends TargetCommand {
        public static final String TAG_NAME = "PagCommand";
        private Expression mBackgroundColorExp;
        private ColorParser mBackgroundColorParser;
        private CommandType mCommand;
        private Expression mExpH;
        private Expression mExpW;
        private Expression mFauxBoldExp;
        private Expression mFauxItalicExp;
        private Expression mFillColorExp;
        private ColorParser mFillColorParser;
        private Expression mFontFamilyExp;
        private Expression mFontSizeExp;
        private Expression mIndexExp;
        private Expression mParamsExp;
        private String mSrcType;
        private Expression mStrokeColorExp;
        private ColorParser mStrokeColorParser;
        private Expression mStrokeWidthExp;
        private Expression mTextExp;

        public enum CommandType {
            PAUSE,
            RESUME,
            PLAY,
            SET_PROGRESS,
            SET_LOOP_COUNT,
            REPLACE_TEXT,
            REPLACE_IMAGE,
            RELEASE
        }

        public PagCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            Variables variables = getVariables();
            String attribute = element.getAttribute("command");
            this.mIndexExp = Expression.build(variables, element.getAttribute("index"));
            this.mParamsExp = Expression.build(variables, element.getAttribute("params"));
            this.mTextExp = Expression.build(variables, element.getAttribute("text"));
            this.mFontSizeExp = Expression.build(variables, element.getAttribute("fontSize"));
            this.mFontFamilyExp = Expression.build(variables, element.getAttribute("fontFamily"));
            this.mFillColorExp = Expression.build(variables, element.getAttribute("fillColor"));
            this.mStrokeColorExp = Expression.build(variables, element.getAttribute("strokeColor"));
            this.mStrokeWidthExp = Expression.build(variables, element.getAttribute("strokeWidth"));
            this.mBackgroundColorExp = Expression.build(variables, element.getAttribute("backgroundColor"));
            this.mFauxBoldExp = Expression.build(variables, element.getAttribute("fauxBold"));
            this.mFauxItalicExp = Expression.build(variables, element.getAttribute("fauxItalic"));
            this.mSrcType = element.getAttribute("srcType");
            this.mExpW = Expression.build(variables, element.getAttribute(AnimatedProperty.PROPERTY_NAME_W));
            this.mExpH = Expression.build(variables, element.getAttribute(AnimatedProperty.PROPERTY_NAME_H));
            attribute.getClass();
            switch (attribute) {
                case "setLoopCount":
                    this.mCommand = CommandType.SET_LOOP_COUNT;
                    break;
                case "resume":
                    this.mCommand = CommandType.RESUME;
                    break;
                case "play":
                    this.mCommand = CommandType.PLAY;
                    break;
                case "pause":
                    this.mCommand = CommandType.PAUSE;
                    break;
                case "replaceText":
                    this.mCommand = CommandType.REPLACE_TEXT;
                    break;
                case "replaceImage":
                    this.mCommand = CommandType.REPLACE_IMAGE;
                    break;
                case "setProgress":
                    this.mCommand = CommandType.SET_PROGRESS;
                    break;
                case "release":
                    this.mCommand = CommandType.RELEASE;
                    break;
            }
        }

        private void replaceImage(PagElement pagElement) {
            if (this.mIndexExp == null) {
                return;
            }
            PagImageModel pagImageModel = new PagImageModel();
            Expression expression = this.mIndexExp;
            if (expression != null) {
                pagImageModel.index = (int) expression.evaluate();
            } else {
                pagImageModel.index = -1;
            }
            Expression expression2 = this.mParamsExp;
            if (expression2 != null) {
                pagImageModel.src = expression2.evaluateStr();
            }
            if (!TextUtils.isEmpty(this.mSrcType)) {
                pagImageModel.srcType = this.mSrcType;
            }
            Expression expression3 = this.mExpW;
            if (expression3 != null) {
                pagImageModel.width = expression3.evaluate();
            } else {
                pagImageModel.width = -1.0d;
            }
            Expression expression4 = this.mExpH;
            if (expression4 != null) {
                pagImageModel.height = expression4.evaluate();
            } else {
                pagImageModel.height = -1.0d;
            }
            pagElement.replaceImage(pagImageModel);
        }

        private void replaceImage(PagImageElement pagImageElement) {
            if (this.mIndexExp == null) {
                return;
            }
            PagImageModel pagImageModel = new PagImageModel();
            Expression expression = this.mIndexExp;
            if (expression != null) {
                pagImageModel.index = (int) expression.evaluate();
            } else {
                pagImageModel.index = -1;
            }
            Expression expression2 = this.mParamsExp;
            if (expression2 != null) {
                pagImageModel.src = expression2.evaluateStr();
            }
            if (!TextUtils.isEmpty(this.mSrcType)) {
                pagImageModel.srcType = this.mSrcType;
            }
            Expression expression3 = this.mExpW;
            if (expression3 != null) {
                pagImageModel.width = expression3.evaluate();
            } else {
                pagImageModel.width = -1.0d;
            }
            Expression expression4 = this.mExpH;
            if (expression4 != null) {
                pagImageModel.height = expression4.evaluate();
            } else {
                pagImageModel.height = -1.0d;
            }
            pagImageElement.replaceImage(pagImageModel);
        }

        private void replaceText(PagElement pagElement) {
            if (this.mIndexExp == null) {
                return;
            }
            PagTextModel pagTextModel = new PagTextModel();
            pagTextModel.index = (int) this.mIndexExp.evaluate();
            Expression expression = this.mTextExp;
            if (expression != null) {
                pagTextModel.newText = expression.evaluateStr();
            }
            Expression expression2 = this.mFontSizeExp;
            if (expression2 != null) {
                pagTextModel.fontSize = (float) expression2.evaluate();
            } else {
                pagTextModel.strokeWidth = -1.0f;
            }
            Expression expression3 = this.mFontFamilyExp;
            if (expression3 != null) {
                pagTextModel.fontFamily = expression3.evaluateStr();
            }
            Expression expression4 = this.mFillColorExp;
            if (expression4 != null) {
                String strEvaluateStr = expression4.evaluateStr();
                ColorParser colorParser = new ColorParser(getVariables(), strEvaluateStr);
                this.mFillColorParser = colorParser;
                pagTextModel.fillColorExp = strEvaluateStr;
                pagTextModel.fillColor = colorParser.getColor();
            }
            Expression expression5 = this.mStrokeColorExp;
            if (expression5 != null) {
                String strEvaluateStr2 = expression5.evaluateStr();
                ColorParser colorParser2 = new ColorParser(getVariables(), strEvaluateStr2);
                this.mStrokeColorParser = colorParser2;
                pagTextModel.strokeColorExp = strEvaluateStr2;
                pagTextModel.strokeColor = colorParser2.getColor();
            }
            Expression expression6 = this.mStrokeWidthExp;
            if (expression6 != null) {
                pagTextModel.strokeWidth = (float) expression6.evaluate();
            } else {
                pagTextModel.strokeWidth = -1.0f;
            }
            Expression expression7 = this.mBackgroundColorExp;
            if (expression7 != null) {
                String strEvaluateStr3 = expression7.evaluateStr();
                ColorParser colorParser3 = new ColorParser(getVariables(), strEvaluateStr3);
                this.mBackgroundColorParser = colorParser3;
                pagTextModel.backgroundColorExp = strEvaluateStr3;
                pagTextModel.backgroundColor = colorParser3.getColor();
            }
            Expression expression8 = this.mFauxBoldExp;
            if (expression8 != null) {
                pagTextModel.fauxBold = expression8.evaluateStr();
            }
            Expression expression9 = this.mFauxItalicExp;
            if (expression9 != null) {
                pagTextModel.fauxItalic = expression9.evaluateStr();
            }
            pagElement.replaceText(pagTextModel);
        }

        private void replaceText(PagImageElement pagImageElement) {
            if (this.mIndexExp == null) {
                return;
            }
            PagTextModel pagTextModel = new PagTextModel();
            pagTextModel.index = (int) this.mIndexExp.evaluate();
            Expression expression = this.mTextExp;
            if (expression != null) {
                pagTextModel.newText = expression.evaluateStr();
            }
            Expression expression2 = this.mFontSizeExp;
            if (expression2 != null) {
                pagTextModel.fontSize = (float) expression2.evaluate();
            } else {
                pagTextModel.strokeWidth = -1.0f;
            }
            Expression expression3 = this.mFontFamilyExp;
            if (expression3 != null) {
                pagTextModel.fontFamily = expression3.evaluateStr();
            }
            Expression expression4 = this.mFillColorExp;
            if (expression4 != null) {
                String strEvaluateStr = expression4.evaluateStr();
                ColorParser colorParser = new ColorParser(getVariables(), strEvaluateStr);
                this.mFillColorParser = colorParser;
                pagTextModel.fillColorExp = strEvaluateStr;
                pagTextModel.fillColor = colorParser.getColor();
            }
            Expression expression5 = this.mStrokeColorExp;
            if (expression5 != null) {
                String strEvaluateStr2 = expression5.evaluateStr();
                ColorParser colorParser2 = new ColorParser(getVariables(), strEvaluateStr2);
                this.mStrokeColorParser = colorParser2;
                pagTextModel.strokeColorExp = strEvaluateStr2;
                pagTextModel.strokeColor = colorParser2.getColor();
            }
            Expression expression6 = this.mStrokeWidthExp;
            if (expression6 != null) {
                pagTextModel.strokeWidth = (float) expression6.evaluate();
            } else {
                pagTextModel.strokeWidth = -1.0f;
            }
            Expression expression7 = this.mBackgroundColorExp;
            if (expression7 != null) {
                String strEvaluateStr3 = expression7.evaluateStr();
                ColorParser colorParser3 = new ColorParser(getVariables(), strEvaluateStr3);
                this.mBackgroundColorParser = colorParser3;
                pagTextModel.backgroundColorExp = strEvaluateStr3;
                pagTextModel.backgroundColor = colorParser3.getColor();
            }
            Expression expression8 = this.mFauxBoldExp;
            if (expression8 != null) {
                pagTextModel.fauxBold = expression8.evaluateStr();
            }
            Expression expression9 = this.mFauxItalicExp;
            if (expression9 != null) {
                pagTextModel.fauxItalic = expression9.evaluateStr();
            }
            pagImageElement.replaceText(pagTextModel);
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null) {
                return;
            }
            if (target instanceof PagElement) {
                PagElement pagElement = (PagElement) target;
                switch (AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[this.mCommand.ordinal()]) {
                    case 1:
                        pagElement.playPagAnimation();
                        break;
                    case 2:
                        pagElement.pausePagAnimation();
                        break;
                    case 3:
                        pagElement.releasePagAnimation();
                        break;
                    case 4:
                        replaceText(pagElement);
                        break;
                    case 5:
                        replaceImage(pagElement);
                        break;
                    case 6:
                        pagElement.resumePagAnimation();
                        break;
                    case 7:
                        pagElement.setProgress((float) this.mParamsExp.evaluate());
                        break;
                    case 8:
                        pagElement.setLoopCount((int) this.mParamsExp.evaluate());
                        break;
                }
            }
            if (target instanceof PagImageElement) {
                PagImageElement pagImageElement = (PagImageElement) target;
                switch (AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$PagCommand$CommandType[this.mCommand.ordinal()]) {
                    case 1:
                        pagImageElement.playPagAnimation();
                        break;
                    case 2:
                        pagImageElement.pausePagAnimation();
                        break;
                    case 3:
                        pagImageElement.releasePagAnimation();
                        break;
                    case 4:
                        replaceText(pagImageElement);
                        break;
                    case 5:
                        replaceImage(pagImageElement);
                        break;
                    case 6:
                        pagImageElement.resumePagAnimation();
                        break;
                    case 7:
                        pagImageElement.setProgress((float) this.mParamsExp.evaluate());
                        break;
                    case 8:
                        pagImageElement.setLoopCount((int) this.mParamsExp.evaluate());
                        break;
                }
            }
        }
    }

    public static class PbrCommand extends TargetCommand {
        public static final String TAG_NAME = "PbrCommand";
        private CommandType mCommand;
        private String mCustElementName;
        private boolean mIsParamsValid;
        private Expression[] mParams;
        private boolean mUniformAutoRefresh;
        private String mUniformName;

        public enum CommandType {
            INVALID,
            UPDATE_UNIFORM
        }

        public PbrCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            String attribute = element.getAttribute("params");
            Expression[] expressionArrBuildMultiple = Expression.buildMultiple(getVariables(), attribute);
            this.mParams = expressionArrBuildMultiple;
            boolean zIsExpressionsValid = isExpressionsValid(expressionArrBuildMultiple);
            this.mIsParamsValid = zIsExpressionsValid;
            if (!zIsExpressionsValid) {
                MamlLog.e(TAG_NAME, "Wrong params: " + attribute);
            }
            this.mUniformName = element.getAttribute("uniformName");
            this.mCustElementName = element.getAttribute("custElementName");
            this.mUniformAutoRefresh = Boolean.parseBoolean(element.getAttribute("uniformRefresh"));
            parseCommand(element);
        }

        private void parseCommand(Element element) {
            String attribute = element.getAttribute("command");
            attribute.getClass();
            if (attribute.equals("updateUniform")) {
                this.mCommand = CommandType.UPDATE_UNIFORM;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof PhysicallyBasedRenderingElement)) {
                return;
            }
            PhysicallyBasedRenderingElement physicallyBasedRenderingElement = (PhysicallyBasedRenderingElement) target;
            if (AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$PbrCommand$CommandType[this.mCommand.ordinal()] != 1) {
                return;
            }
            physicallyBasedRenderingElement.updateUniform(this.mCustElementName, this.mUniformName, this.mUniformAutoRefresh, this.mParams);
        }
    }

    public static class PermanenceCommand extends ActionCommand {
        public static final String TAG_NAME = "PermanenceCommand";
        private Expression mExpression;
        private String mName;
        private Expression mNameExp;
        private boolean mRequestUpdate;
        private VariableType mType;
        private IndexedVariable mVar;

        public PermanenceCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            Variables variables = screenElement.getVariables();
            Expression expressionBuild = Expression.build(variables, element.getAttribute("nameExp"));
            this.mNameExp = expressionBuild;
            if (expressionBuild != null) {
                this.mName = expressionBuild.evaluateStr();
            } else {
                this.mName = element.getAttribute(bf.a);
            }
            this.mRequestUpdate = Boolean.parseBoolean(element.getAttribute("requestUpdate"));
            this.mType = VariableType.parseType(element.getAttribute("type"));
            if (TextUtils.isEmpty(this.mName)) {
                MamlLog.e("ActionCommand", "empty name in PermanenceCommand");
            } else {
                this.mVar = new IndexedVariable(this.mName, variables, this.mType.isNumber());
            }
            this.mExpression = Expression.build(variables, element.getAttribute("expression"));
        }

        private SharedPreferenceHelper getSharePreferenceHelper() {
            String str = (String) getVariables().get("customEditLocalId");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return SharedPreferenceHelper.getInstance(getContext().getApplicationContext(), "sp_" + str);
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            SharedPreferenceHelper sharePreferenceHelper = getSharePreferenceHelper();
            if (sharePreferenceHelper == null) {
                return;
            }
            ScreenElementRoot root = getRoot();
            Variables variables = getVariables();
            Expression expression = this.mNameExp;
            if (expression != null) {
                String strEvaluateStr = expression.evaluateStr();
                if (TextUtils.isEmpty(strEvaluateStr)) {
                    this.mName = null;
                    return;
                } else if (!strEvaluateStr.equals(this.mName)) {
                    this.mName = strEvaluateStr;
                    this.mVar = new IndexedVariable(strEvaluateStr, variables, this.mType.isNumber());
                }
            }
            if (this.mVar == null) {
                return;
            }
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$VariableType[this.mType.ordinal()];
            if (i == 1) {
                Expression expression2 = this.mExpression;
                if (expression2 != null) {
                    double dEvaluate = expression2.evaluate();
                    this.mVar.set(dEvaluate);
                    sharePreferenceHelper.save(this.mName, (float) dEvaluate);
                }
            } else if (i == 2) {
                String strEvaluateStr2 = this.mExpression.evaluateStr();
                this.mVar.set(strEvaluateStr2);
                sharePreferenceHelper.save(this.mName, strEvaluateStr2);
            }
            if (this.mRequestUpdate) {
                root.requestUpdate();
            }
        }
    }

    @Deprecated
    public static abstract class PropertyCommand extends ActionCommand {
        protected ScreenElement mTargetElement;
        private Variable mTargetObj;

        public PropertyCommand(ScreenElement screenElement, Variable variable, String str) {
            super(screenElement);
            this.mTargetObj = variable;
        }

        public static PropertyCommand create(ScreenElement screenElement, String str, String str2) {
            Variable variable = new Variable(str);
            if ("visibility".equals(variable.getPropertyName())) {
                return new VisibilityProperty(screenElement, variable, str2);
            }
            if (AnimationProperty.PROPERTY_NAME.equals(variable.getPropertyName())) {
                return new AnimationProperty(screenElement, variable, str2);
            }
            return null;
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            super.init();
            if (this.mTargetObj != null && this.mTargetElement == null) {
                ScreenElement screenElementFindElement = getRoot().findElement(this.mTargetObj.getObjName());
                this.mTargetElement = screenElementFindElement;
                if (screenElementFindElement == null) {
                    MamlLog.w("ActionCommand", "could not find PropertyCommand target, name: " + this.mTargetObj.getObjName());
                    this.mTargetObj = null;
                }
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void perform() {
            if (this.mTargetElement == null) {
                return;
            }
            doPerform();
        }
    }

    public static class RingModeCommand extends NotificationReceiver {
        private AudioManager mAudioManager;
        private ModeToggleHelper mToggleHelper;

        public RingModeCommand(ScreenElement screenElement, String str) {
            super(screenElement, VariableNames.RING_MODE, "android.media.RINGER_MODE_CHANGED");
            ModeToggleHelper modeToggleHelper = new ModeToggleHelper(null);
            this.mToggleHelper = modeToggleHelper;
            modeToggleHelper.addMode("normal", 2);
            this.mToggleHelper.addMode("silent", 0);
            this.mToggleHelper.addMode("vibrate", 1);
            if (this.mToggleHelper.build(str)) {
                return;
            }
            MamlLog.e("ActionCommand", "invalid ring mode command value: " + str);
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            if (this.mAudioManager == null) {
                return;
            }
            this.mToggleHelper.click();
            int modeId = this.mToggleHelper.getModeId();
            this.mAudioManager.setRingerMode(modeId);
            updateState(modeId);
        }

        @Override // com.miui.maml.ActionCommand.NotificationReceiver
        public void update() {
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) this.mScreenElement.getContext().mContext.getSystemService("audio");
            }
            AudioManager audioManager = this.mAudioManager;
            if (audioManager == null) {
                return;
            }
            updateState(audioManager.getRingerMode());
        }
    }

    public static class SensorBinderCommand extends TargetCommand {
        public static final String TAG_NAME = "SensorCommand";
        private CommandType mCommand;

        public enum CommandType {
            INVALID,
            TURN_ON,
            TURN_OFF
        }

        public SensorBinderCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mTargetType = TargetCommand.TargetType.VARIABLE_BINDER;
            String attribute = element.getAttribute("command");
            attribute.getClass();
            if (attribute.equals("turnOff")) {
                this.mCommand = CommandType.TURN_OFF;
            } else if (attribute.equals("turnOn")) {
                this.mCommand = CommandType.TURN_ON;
            } else {
                this.mCommand = CommandType.INVALID;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof SensorBinder)) {
                return;
            }
            SensorBinder sensorBinder = (SensorBinder) target;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$SensorBinderCommand$CommandType[this.mCommand.ordinal()];
            if (i == 1) {
                sensorBinder.turnOnSensorBinder();
            } else {
                if (i != 2) {
                    return;
                }
                sensorBinder.turnOffSensorBinder();
            }
        }
    }

    public static class SoundCommand extends ActionCommand {
        public static final String TAG_NAME = "SoundCommand";
        private SoundManager.Command mCommand;
        private boolean mKeepCur;
        private boolean mLoop;
        private String mSound;
        private Expression mStreamIdExp;
        private IndexedVariable mStreamIdVar;
        private Expression mVolumeExp;

        public SoundCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            this.mSound = element.getAttribute("sound");
            this.mKeepCur = Boolean.parseBoolean(element.getAttribute("keepCur"));
            this.mLoop = Boolean.parseBoolean(element.getAttribute("loop"));
            this.mCommand = SoundManager.Command.parse(element.getAttribute("command"));
            Expression expressionBuild = Expression.build(getVariables(), element.getAttribute("volume"));
            this.mVolumeExp = expressionBuild;
            if (expressionBuild == null) {
                MamlLog.e("ActionCommand", "invalid expression in SoundCommand");
            }
            this.mStreamIdExp = Expression.build(getVariables(), element.getAttribute("streamId"));
            String attribute = element.getAttribute("streamIdVar");
            if (TextUtils.isEmpty(attribute)) {
                return;
            }
            this.mStreamIdVar = new IndexedVariable(attribute, getVariables(), true);
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Expression expression;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$SoundManager$Command[this.mCommand.ordinal()];
            if (i != 1) {
                if ((i == 2 || i == 3 || i == 4) && (expression = this.mStreamIdExp) != null) {
                    getRoot().playSound((int) expression.evaluate(), this.mCommand);
                    return;
                }
                return;
            }
            Expression expression2 = this.mVolumeExp;
            int iPlaySound = getRoot().playSound(this.mSound, new SoundManager.SoundOptions(this.mKeepCur, this.mLoop, expression2 != null ? (float) expression2.evaluate() : 0.0f));
            IndexedVariable indexedVariable = this.mStreamIdVar;
            if (indexedVariable != null) {
                indexedVariable.set(iPlaySound);
            }
        }
    }

    public static abstract class StateTracker {
        private boolean mInTransition = false;
        private Boolean mActualState = null;
        private Boolean mIntendedState = null;
        private boolean mDeferredStateChangeRequestNeeded = false;

        public abstract int getActualState(Context context);

        public final int getTriState(Context context) {
            if (this.mInTransition) {
                return 5;
            }
            int actualState = getActualState(context);
            if (actualState != 0) {
                return actualState != 1 ? 5 : 1;
            }
            return 0;
        }

        public final boolean isTurningOn() {
            Boolean bool = this.mIntendedState;
            return bool != null && bool.booleanValue();
        }

        public abstract void onActualStateChange(Context context, Intent intent);

        public abstract void requestStateChange(Context context, boolean z);

        public final void setCurrentState(Context context, int i) {
            Boolean bool;
            boolean z = this.mInTransition;
            if (i == 0) {
                this.mInTransition = false;
                this.mActualState = Boolean.FALSE;
            } else if (i == 1) {
                this.mInTransition = false;
                this.mActualState = Boolean.TRUE;
            } else if (i == 2) {
                this.mInTransition = true;
                this.mActualState = Boolean.FALSE;
            } else if (i == 3) {
                this.mInTransition = true;
                this.mActualState = Boolean.TRUE;
            }
            if (z && !this.mInTransition && this.mDeferredStateChangeRequestNeeded) {
                MamlLog.d("ActionCommand", "processing deferred state change");
                Boolean bool2 = this.mActualState;
                if (bool2 == null || (bool = this.mIntendedState) == null || !bool.equals(bool2)) {
                    Boolean bool3 = this.mIntendedState;
                    if (bool3 != null) {
                        this.mInTransition = true;
                        requestStateChange(context, bool3.booleanValue());
                    }
                } else {
                    MamlLog.d("ActionCommand", "... but intended state matches, so no changes.");
                }
                this.mDeferredStateChangeRequestNeeded = false;
            }
        }

        public final void toggleState(Context context) {
            boolean z;
            Boolean bool;
            int triState = getTriState(context);
            if (triState != 0) {
                z = false;
                if (triState != 1 && triState == 5 && (bool = this.mIntendedState) != null) {
                    z = !bool.booleanValue();
                }
            } else {
                z = true;
            }
            this.mIntendedState = Boolean.valueOf(z);
            if (this.mInTransition) {
                this.mDeferredStateChangeRequestNeeded = true;
            } else {
                this.mInTransition = true;
                requestStateChange(context, z);
            }
        }
    }

    public static abstract class StatefulActionCommand extends ActionCommand {
        private IndexedVariable mVar;

        public StatefulActionCommand(ScreenElement screenElement, String str) {
            super(screenElement);
            this.mVar = new IndexedVariable(str, getVariables(), true);
        }

        public final void updateState(int i) {
            IndexedVariable indexedVariable = this.mVar;
            if (indexedVariable == null) {
                return;
            }
            indexedVariable.set(i);
            getRoot().requestUpdate();
        }
    }

    public static abstract class TargetCommand extends ActionCommand {
        protected String mLogStr;
        private Object mTarget;
        protected Expression mTargetIndex;
        protected String mTargetName;
        protected Expression mTargetNameExp;
        protected TargetType mTargetType;

        public enum TargetType {
            SCREEN_ELEMENT,
            VARIABLE,
            CONSTRUCTOR,
            ANIMATION_ITEM,
            VARIABLE_BINDER
        }

        public TargetCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            Expression expressionBuild = Expression.build(getVariables(), element.getAttribute("targetExp"));
            this.mTargetNameExp = expressionBuild;
            if (expressionBuild != null) {
                this.mTargetName = expressionBuild.evaluateStr();
            } else {
                this.mTargetName = element.getAttribute("target");
            }
            if (TextUtils.isEmpty(this.mTargetName)) {
                this.mTargetName = null;
            }
            this.mTargetIndex = Expression.build(getVariables(), element.getAttribute("targetIndex"));
            String attribute = element.getAttribute("targetType");
            TargetType targetType = TargetType.SCREEN_ELEMENT;
            this.mTargetType = targetType;
            if ("element".equals(attribute)) {
                this.mTargetType = targetType;
            } else if ("var".equals(attribute)) {
                this.mTargetType = TargetType.VARIABLE;
            } else if ("ctor".equals(attribute)) {
                this.mTargetType = TargetType.CONSTRUCTOR;
            }
            this.mLogStr = "target=" + this.mTargetName + " type=" + this.mTargetType.toString();
        }

        private void findTarget() {
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[this.mTargetType.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    if (this.mTargetName != null) {
                        this.mTarget = Integer.valueOf(getVariables().registerVariable(this.mTargetName));
                        return;
                    } else {
                        MamlLog.e("ActionCommand", "MethodCommand, type=var, empty target name");
                        return;
                    }
                }
                if (i == 3) {
                    this.mTarget = getRoot().getAnimationItems(this.mTargetName);
                    return;
                } else {
                    if (i != 4) {
                        return;
                    }
                    this.mTarget = getRoot().findBinder(this.mTargetName);
                    return;
                }
            }
            ScreenElement screenElementFindElement = getRoot().findElement(this.mTargetName);
            this.mTarget = screenElementFindElement;
            if (screenElementFindElement == null) {
                MamlLog.e("ActionCommand", "could not find ScreenElement target, name: " + this.mTargetName);
                return;
            }
            if (this.mTargetIndex == null || ElementGroup.isArrayGroup(screenElementFindElement)) {
                return;
            }
            MamlLog.e("ActionCommand", "target with index is not an ArrayGroup, name: " + this.mTargetName);
            this.mTargetIndex = null;
        }

        public Object getTarget() {
            Expression expression;
            Expression expression2 = this.mTargetNameExp;
            if (expression2 != null) {
                String strEvaluateStr = expression2.evaluateStr();
                if (strEvaluateStr == null) {
                    this.mTargetName = null;
                    this.mTarget = null;
                    return null;
                }
                if (!strEvaluateStr.equals(this.mTargetName)) {
                    this.mTargetName = strEvaluateStr;
                    findTarget();
                }
            }
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$TargetCommand$TargetType[this.mTargetType.ordinal()];
            if (i == 1) {
                Object obj = this.mTarget;
                return (obj == null || (expression = this.mTargetIndex) == null) ? obj : ((ElementGroup) obj).getChild((int) expression.evaluate());
            }
            if (i != 2) {
                if (i == 3 || i == 4) {
                    return this.mTarget;
                }
                return null;
            }
            if (this.mTarget != null) {
                Object obj2 = getVariables().get(((Integer) this.mTarget).intValue());
                if (this.mTargetIndex == null) {
                    return obj2;
                }
                if (obj2.getClass().isArray()) {
                    return Array.get(obj2, (int) this.mTargetIndex.evaluate());
                }
                MamlLog.e("ActionCommand", "target with index is not an Array variable, name: " + this.mTargetName);
                this.mTargetIndex = null;
            }
            return null;
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            super.init();
            findTarget();
        }
    }

    public static class TickListenerCommand extends TargetCommand {
        public static final String TAG_NAME = "TickListenerCommand";
        private CommandType mCommand;
        private Expression mFunNameExp;

        public enum CommandType {
            INVALID,
            SET,
            UNSET
        }

        public TickListenerCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            this.mFunNameExp = Expression.build(getVariables(), element.getAttribute("function"));
            String attribute = element.getAttribute("command");
            attribute.getClass();
            if (attribute.equals("set")) {
                this.mCommand = CommandType.SET;
            } else if (attribute.equals("unset")) {
                this.mCommand = CommandType.UNSET;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof AnimatedScreenElement)) {
                return;
            }
            AnimatedScreenElement animatedScreenElement = (AnimatedScreenElement) target;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$TickListenerCommand$CommandType[this.mCommand.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                animatedScreenElement.unsetOnTickListener();
            } else {
                ScreenElement screenElementFindElement = getRoot().findElement(this.mFunNameExp.evaluateStr());
                if (screenElementFindElement == null || !(screenElementFindElement instanceof FunctionElement)) {
                    return;
                }
                animatedScreenElement.setOnTickListener((FunctionElement) screenElementFindElement);
            }
        }
    }

    public static class UsbStorageSwitchCommand extends NotificationReceiver {
        private boolean mConnected;
        private OnOffCommandHelper mOnOffHelper;
        private StorageManager mStorageManager;

        public UsbStorageSwitchCommand(ScreenElement screenElement, String str) {
            super(screenElement, VariableNames.USB_MODE, ActionCommand.ACTION_USB_STATE);
            this.mOnOffHelper = new OnOffCommandHelper(str);
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            final boolean z;
            StorageManager storageManager = this.mStorageManager;
            if (storageManager == null) {
                return;
            }
            boolean zStorageManager_isUsbMassStorageEnabled = HideSdkDependencyUtils.StorageManager_isUsbMassStorageEnabled(storageManager);
            OnOffCommandHelper onOffCommandHelper = this.mOnOffHelper;
            if (onOffCommandHelper.mIsToggle) {
                z = !zStorageManager_isUsbMassStorageEnabled;
            } else {
                boolean z2 = onOffCommandHelper.mIsOn;
                if (z2 == zStorageManager_isUsbMassStorageEnabled) {
                    return;
                } else {
                    z = z2;
                }
            }
            updateState(3);
            new Thread("StorageSwitchThread") { // from class: com.miui.maml.ActionCommand.UsbStorageSwitchCommand.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    if (z) {
                        HideSdkDependencyUtils.StorageManager_enableUsbMassStorage(UsbStorageSwitchCommand.this.mStorageManager);
                    } else {
                        HideSdkDependencyUtils.StorageManager_disableUsbMassStorage(UsbStorageSwitchCommand.this.mStorageManager);
                    }
                    UsbStorageSwitchCommand.this.updateState(z ? 2 : 1);
                }
            }.start();
        }

        @Override // com.miui.maml.ActionCommand.NotificationReceiver, com.miui.maml.NotifierManager.OnNotifyListener
        public void onNotify(Context context, Intent intent, Object obj) {
            if (intent != null) {
                this.mConnected = intent.getExtras().getBoolean(ActionCommand.USB_CONNECTED);
            }
            super.onNotify(context, intent, obj);
        }

        @Override // com.miui.maml.ActionCommand.NotificationReceiver
        public void update() {
            int i;
            if (this.mStorageManager == null) {
                StorageManager storageManager = (StorageManager) getContext().getSystemService("storage");
                this.mStorageManager = storageManager;
                if (storageManager == null) {
                    MamlLog.w("ActionCommand", "Failed to get StorageManager");
                    return;
                }
            }
            boolean zStorageManager_isUsbMassStorageEnabled = HideSdkDependencyUtils.StorageManager_isUsbMassStorageEnabled(this.mStorageManager);
            if (this.mConnected) {
                i = zStorageManager_isUsbMassStorageEnabled ? 2 : 1;
            } else {
                i = 0;
            }
            updateState(i);
        }
    }

    public static class VariableAssignmentCommand extends ActionCommand {
        public static final String TAG_NAME = "VariableCommand";
        private Expression[] mArrayValues;
        private Expression mExpression;
        private Expression mIndexExpression;
        private IndexedVariable mLengthVar;
        private String mName;
        private Expression mNameExp;
        private boolean mPersist;
        private boolean mRequestUpdate;
        private VariableType mType;
        private IndexedVariable mVar;

        public VariableAssignmentCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            Variables variables = screenElement.getVariables();
            Expression expressionBuild = Expression.build(variables, element.getAttribute("nameExp"));
            this.mNameExp = expressionBuild;
            if (expressionBuild != null) {
                this.mName = expressionBuild.evaluateStr();
            } else {
                this.mName = element.getAttribute(bf.a);
            }
            this.mPersist = Boolean.parseBoolean(element.getAttribute("persist"));
            this.mRequestUpdate = Boolean.parseBoolean(element.getAttribute("requestUpdate"));
            this.mType = VariableType.parseType(element.getAttribute("type"));
            if (TextUtils.isEmpty(this.mName)) {
                MamlLog.e("ActionCommand", "empty name in VariableAssignmentCommand");
            } else {
                this.mVar = new IndexedVariable(this.mName, variables, this.mType.isNumber());
                if (this.mType.isArray()) {
                    this.mLengthVar = new IndexedVariable(this.mName + VariableElement.LENGTH_SUFFIX, variables, true);
                }
            }
            this.mExpression = Expression.build(variables, element.getAttribute("expression"));
            if (this.mType.isArray()) {
                this.mIndexExpression = Expression.build(variables, element.getAttribute("index"));
                this.mArrayValues = Expression.buildMultiple(variables, element.getAttribute("values"));
            }
        }

        /* JADX WARN: Code duplicated, block: B:57:0x0123  */
        /* JADX WARN: Code duplicated, block: B:59:0x0127  */
        /* JADX WARN: Code duplicated, block: B:60:0x012c  */
        /* JADX WARN: Code duplicated, block: B:68:0x0145  */
        /* JADX WARN: Code duplicated, block: B:84:0x018d  */
        /* JADX WARN: Code duplicated, block: B:86:0x0191  */
        /* JADX WARN: Code duplicated, block: B:87:0x0195  */
        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Expression expression;
            String strEvaluateStr;
            Variables variables;
            Expression expression2;
            Object obj;
            IndexedVariable indexedVariable;
            ScreenElementRoot root = getRoot();
            Variables variables2 = getVariables();
            Expression expression3 = this.mNameExp;
            Object obj2 = null;
            if (expression3 != null) {
                String strEvaluateStr2 = expression3.evaluateStr();
                if (TextUtils.isEmpty(strEvaluateStr2)) {
                    this.mName = null;
                    return;
                }
                if (!strEvaluateStr2.equals(this.mName)) {
                    this.mName = strEvaluateStr2;
                    this.mVar = new IndexedVariable(strEvaluateStr2, variables2, this.mType.isNumber());
                    if (this.mType.isArray()) {
                        this.mLengthVar = new IndexedVariable(this.mName + VariableElement.LENGTH_SUFFIX, variables2, true);
                    }
                }
            }
            if (this.mVar == null) {
                return;
            }
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$VariableType[this.mType.ordinal()];
            if (i == 1) {
                Expression expression4 = this.mExpression;
                if (expression4 != null) {
                    double dEvaluate = expression4.evaluate();
                    this.mVar.set(dEvaluate);
                    if (this.mPersist && root.getCapability(2)) {
                        root.saveVar(this.mName, Double.valueOf(dEvaluate));
                    }
                }
            } else if (i == 2) {
                Expression expression5 = this.mExpression;
                if (expression5 != null) {
                    String strEvaluateStr3 = expression5.evaluateStr();
                    this.mVar.set(strEvaluateStr3);
                    if (this.mPersist && root.getCapability(2)) {
                        root.saveVar(this.mName, strEvaluateStr3);
                    }
                }
            } else if (i == 3) {
                Expression expression6 = this.mExpression;
                if (expression6 != null) {
                    this.mVar.set(expression6.evaluateJsonObject());
                }
            } else if (i != 4) {
                int i2 = 0;
                if (!this.mType.isNumberOrStringArray()) {
                    expression = this.mExpression;
                    if (expression != null) {
                        strEvaluateStr = expression.evaluateStr();
                    } else {
                        strEvaluateStr = null;
                    }
                    variables = getVariables();
                    if (!TextUtils.isEmpty(strEvaluateStr) && variables.existsObj(strEvaluateStr)) {
                        obj2 = variables.get(strEvaluateStr);
                    }
                    expression2 = this.mIndexExpression;
                    if (expression2 == null) {
                        obj = this.mVar.get();
                        this.mVar.set(obj2);
                        if ((obj == null && (obj instanceof Array)) || (obj2 != null && (obj2 instanceof Array))) {
                        }
                        if (this.mLengthVar == null && i2 != 0) {
                            this.mLengthVar = new IndexedVariable(this.mName + VariableElement.LENGTH_SUFFIX, variables2, true);
                        }
                        if (obj2 == null && (obj2 instanceof Array)) {
                            this.mLengthVar.set(Array.getLength(obj2));
                        } else {
                            indexedVariable = this.mLengthVar;
                            if (indexedVariable != null) {
                                indexedVariable.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                            }
                        }
                    } else {
                        this.mVar.setArr((int) expression2.evaluate(), obj2);
                    }
                } else if (this.mIndexExpression == null || this.mExpression == null) {
                    if (this.mArrayValues != null) {
                        Object obj3 = this.mVar.get();
                        int length = this.mArrayValues.length;
                        if (obj3 == null || Array.getLength(obj3) != length || obj3.getClass().getComponentType() != this.mType.mTypeClass) {
                            variables2.createArray(this.mName, length, this.mType.mTypeClass);
                            this.mLengthVar.set(length);
                            obj3 = this.mVar.get();
                        }
                        if (this.mType.isNumberArray()) {
                            while (i2 < length) {
                                Expression expression7 = this.mArrayValues[i2];
                                Variables.putValueToArr(obj3, i2, expression7 == null ? 0.0d : expression7.evaluate());
                                i2++;
                            }
                        } else {
                            while (i2 < length) {
                                Expression expression8 = this.mArrayValues[i2];
                                ((String[]) obj3)[i2] = expression8 == null ? null : expression8.evaluateStr();
                                i2++;
                            }
                        }
                        this.mVar.set(obj3);
                    } else {
                        expression = this.mExpression;
                        if (expression != null) {
                            strEvaluateStr = expression.evaluateStr();
                        } else {
                            strEvaluateStr = null;
                        }
                        variables = getVariables();
                        if (!TextUtils.isEmpty(strEvaluateStr)) {
                            obj2 = variables.get(strEvaluateStr);
                        }
                        expression2 = this.mIndexExpression;
                        if (expression2 == null) {
                            obj = this.mVar.get();
                            this.mVar.set(obj2);
                            i2 = obj == null ? 1 : 1;
                            if (this.mLengthVar == null) {
                                this.mLengthVar = new IndexedVariable(this.mName + VariableElement.LENGTH_SUFFIX, variables2, true);
                            }
                            if (obj2 == null) {
                                indexedVariable = this.mLengthVar;
                                if (indexedVariable != null) {
                                    indexedVariable.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                                }
                            } else {
                                indexedVariable = this.mLengthVar;
                                if (indexedVariable != null) {
                                    indexedVariable.set(BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                                }
                            }
                        } else {
                            this.mVar.setArr((int) expression2.evaluate(), obj2);
                        }
                    }
                } else if (this.mType.isNumberArray()) {
                    this.mVar.setArr((int) this.mIndexExpression.evaluate(), this.mExpression.evaluate());
                } else {
                    this.mVar.setArr((int) this.mIndexExpression.evaluate(), this.mExpression.evaluateStr());
                }
            } else {
                Expression expression9 = this.mExpression;
                if (expression9 != null) {
                    this.mVar.set(expression9.evaluateJsonArray());
                }
            }
            if (this.mRequestUpdate) {
                root.requestUpdate();
            }
        }
    }

    public static class VariableBinderCommand extends ActionCommand {
        public static final String TAG_NAME = "BinderCommand";
        private VariableBinder mBinder;
        private Command mCommand;
        private String mName;

        public enum Command {
            Refresh,
            Invalid
        }

        public VariableBinderCommand(ScreenElement screenElement, Element element) {
            super(screenElement);
            this.mCommand = Command.Invalid;
            this.mName = element.getAttribute(bf.a);
            if (element.getAttribute("command").equals("refresh")) {
                this.mCommand = Command.Refresh;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            if (this.mBinder == null || AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$VariableBinderCommand$Command[this.mCommand.ordinal()] != 1) {
                return;
            }
            this.mBinder.refresh();
        }

        @Override // com.miui.maml.ActionCommand
        public void init() {
            this.mBinder = getRoot().findBinder(this.mName);
        }
    }

    public static class VideoCommand extends TargetCommand {
        public static final String TAG_NAME = "VideoCommand";
        private CommandType mCommand;
        private Expression mLooping;
        private Expression mPath;
        private Expression mScaleMode;
        private Expression mTime;
        private Expression mVolume;

        public enum CommandType {
            PAUSE,
            PLAY,
            SEEK_TO,
            CONFIG,
            SET_VOLUME,
            INVALID
        }

        public VideoCommand(ScreenElement screenElement, Element element) {
            super(screenElement, element);
            Variables variables = getVariables();
            String attribute = element.getAttribute("command");
            this.mPath = Expression.build(variables, element.getAttribute("path"));
            this.mVolume = Expression.build(variables, element.getAttribute("volume"));
            this.mScaleMode = Expression.build(variables, element.getAttribute("scaleMode"));
            this.mLooping = Expression.build(variables, element.getAttribute("loop"));
            this.mTime = Expression.build(variables, element.getAttribute(VariableNames.VAR_TIME));
            attribute.getClass();
            switch (attribute) {
                case "config":
                    this.mCommand = CommandType.CONFIG;
                    break;
                case "seekTo":
                    this.mCommand = CommandType.SEEK_TO;
                    break;
                case "play":
                    this.mCommand = CommandType.PLAY;
                    break;
                case "pause":
                    this.mCommand = CommandType.PAUSE;
                    break;
                case "setVolume":
                    this.mCommand = CommandType.SET_VOLUME;
                    break;
                default:
                    this.mCommand = CommandType.INVALID;
                    break;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Object target = getTarget();
            if (target == null || !(target instanceof VideoElement)) {
                return;
            }
            VideoElement videoElement = (VideoElement) target;
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$ActionCommand$VideoCommand$CommandType[this.mCommand.ordinal()];
            if (i == 1) {
                videoElement.play();
                return;
            }
            if (i == 2) {
                videoElement.pause();
                return;
            }
            if (i == 3) {
                Expression expression = this.mTime;
                if (expression != null) {
                    videoElement.seekTo((int) expression.evaluate());
                    return;
                }
                return;
            }
            if (i == 4) {
                Expression expression2 = this.mVolume;
                if (expression2 != null) {
                    videoElement.setVolume((float) expression2.evaluate());
                    return;
                }
                return;
            }
            if (i != 5) {
                return;
            }
            Expression expression3 = this.mLooping;
            boolean z = false;
            if (expression3 != null && expression3.evaluate() > BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                z = true;
            }
            Expression expression4 = this.mScaleMode;
            int iEvaluate = expression4 != null ? (int) expression4.evaluate() : 1;
            Expression expression5 = this.mPath;
            videoElement.config(z, iEvaluate, expression5 != null ? expression5.evaluateStr() : "");
        }
    }

    @Deprecated
    public static class VisibilityProperty extends PropertyCommand {
        public static final String PROPERTY_NAME = "visibility";
        private boolean mIsShow;
        private boolean mIsToggle;

        public VisibilityProperty(ScreenElement screenElement, Variable variable, String str) {
            super(screenElement, variable, str);
            if (str.equalsIgnoreCase("toggle")) {
                this.mIsToggle = true;
            } else if (str.equalsIgnoreCase(com.xiaomi.onetrack.util.a.i)) {
                this.mIsShow = true;
            } else if (str.equalsIgnoreCase("false")) {
                this.mIsShow = false;
            }
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            if (!this.mIsToggle) {
                this.mTargetElement.show(this.mIsShow);
            } else {
                ScreenElement screenElement = this.mTargetElement;
                screenElement.show(!screenElement.isVisible());
            }
        }
    }

    public static final class WifiStateTracker extends StateTracker {
        private static final int MAX_SCAN_ATTEMPT = 3;
        public boolean zConnected;
        private int zScanAttempt;

        public static /* synthetic */ void $r8$lambda$uFO7fNnt5xl65ovhl0O1_v31UpY(Context context, boolean z) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                MamlLog.d("ActionCommand", "No wifiManager.");
                return;
            }
            if (z && HideSdkDependencyUtils.WifiManager_isWifiApEnabled(wifiManager)) {
                HideSdkDependencyUtils.setWifiApEnabled(context, false);
            }
            wifiManager.setWifiEnabled(z);
        }

        private WifiStateTracker() {
            this.zConnected = false;
            this.zScanAttempt = 0;
        }

        public /* synthetic */ WifiStateTracker(AnonymousClass1 anonymousClass1) {
            this();
        }

        private static int wifiStateToFiveState(int i) {
            if (i == 0) {
                return 3;
            }
            if (i == 1) {
                return 0;
            }
            if (i != 2) {
                return i != 3 ? 4 : 1;
            }
            return 2;
        }

        @Override // com.miui.maml.ActionCommand.StateTracker
        public int getActualState(Context context) {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null) {
                return wifiStateToFiveState(wifiManager.getWifiState());
            }
            return 4;
        }

        @Override // com.miui.maml.ActionCommand.StateTracker
        public void onActualStateChange(Context context, Intent intent) {
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra(VariableNames.WIFI_STATE, -1);
                setCurrentState(context, wifiStateToFiveState(intExtra));
                if (3 == intExtra) {
                    this.zConnected = true;
                    this.zScanAttempt = 0;
                    return;
                }
                return;
            }
            if ("android.net.wifi.SCAN_RESULTS".equals(intent.getAction())) {
                int i = this.zScanAttempt;
                if (i < 3) {
                    int i2 = i + 1;
                    this.zScanAttempt = i2;
                    if (i2 == 3) {
                        this.zConnected = false;
                        return;
                    }
                    return;
                }
                return;
            }
            if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction())) {
                this.zScanAttempt = 3;
                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                if (networkInfo == null) {
                    return;
                }
                NetworkInfo.DetailedState detailedState = networkInfo.getDetailedState();
                this.zConnected = NetworkInfo.DetailedState.SCANNING == detailedState || NetworkInfo.DetailedState.CONNECTING == detailedState || NetworkInfo.DetailedState.AUTHENTICATING == detailedState || NetworkInfo.DetailedState.OBTAINING_IPADDR == detailedState || NetworkInfo.DetailedState.CONNECTED == detailedState;
            }
        }

        @Override // com.miui.maml.ActionCommand.StateTracker
        public void requestStateChange(final Context context, final boolean z) {
            AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() { // from class: com.miui.maml.ActionCommand$WifiStateTracker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ActionCommand.WifiStateTracker.$r8$lambda$uFO7fNnt5xl65ovhl0O1_v31UpY(context, z);
                }
            });
        }
    }

    public static class WifiSwitchCommand extends NotificationReceiver {
        private final OnOffCommandHelper mOnOffHelper;
        private final StateTracker mWifiState;

        public WifiSwitchCommand(ScreenElement screenElement, String str) {
            super(screenElement, VariableNames.WIFI_STATE, NotifierManager.TYPE_WIFI_STATE);
            this.mWifiState = new WifiStateTracker(null);
            update();
            this.mOnOffHelper = new OnOffCommandHelper(str);
        }

        @Override // com.miui.maml.ActionCommand
        public void doPerform() {
            Context context = getContext();
            if (this.mOnOffHelper.mIsToggle) {
                this.mWifiState.toggleState(context);
            } else {
                int triState = this.mWifiState.getTriState(context);
                if (triState == 0 ? this.mOnOffHelper.mIsOn : !(triState != 1 || this.mOnOffHelper.mIsOn)) {
                    this.mWifiState.requestStateChange(context, this.mOnOffHelper.mIsOn);
                }
            }
            update();
        }

        @Override // com.miui.maml.ActionCommand.NotificationReceiver, com.miui.maml.NotifierManager.OnNotifyListener
        public void onNotify(Context context, Intent intent, Object obj) {
            this.mWifiState.onActualStateChange(context, intent);
            super.onNotify(context, intent, obj);
        }

        @Override // com.miui.maml.ActionCommand.NotificationReceiver
        public void update() {
            int triState = this.mWifiState.getTriState(getContext());
            if (triState == 0) {
                updateState(0);
                return;
            }
            if (triState == 1) {
                updateState(((WifiStateTracker) this.mWifiState).zConnected ? 1 : 2);
            } else {
                if (triState != 5) {
                    return;
                }
                updateState(this.mWifiState.isTurningOn() ? 3 : 0);
            }
        }
    }

    public ActionCommand(ScreenElement screenElement) {
        this.mScreenElement = screenElement;
    }

    public static ActionCommand create(ScreenElement screenElement, String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            Variable variable = new Variable(str);
            if (variable.getObjName() != null) {
                return PropertyCommand.create(screenElement, str, str2);
            }
            String propertyName = variable.getPropertyName();
            if (COMMAND_RING_MODE.equals(propertyName)) {
                return new RingModeCommand(screenElement, str2);
            }
            if (COMMAND_WIFI.equals(propertyName)) {
                return new WifiSwitchCommand(screenElement, str2);
            }
            if (COMMAND_BLUETOOTH.equals(propertyName)) {
                return new BluetoothSwitchCommand(screenElement, str2);
            }
            if (COMMAND_DATA.equals(propertyName)) {
                return new DataSwitchCommand(screenElement, str2);
            }
            if (COMMAND_USB_STORAGE.equals(propertyName)) {
                return new UsbStorageSwitchCommand(screenElement, str2);
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:156:0x0255  */
    public static ActionCommand create(Element element, ScreenElement screenElement) {
        ActionCommand graphicsCommand;
        ActionCommand actionCommandCreate;
        if (element == null) {
            return null;
        }
        Expression expressionBuild = Expression.build(screenElement.getVariables(), element.getAttribute("condition"));
        Expression expressionBuild2 = Expression.build(screenElement.getVariables(), element.getAttribute("delayCondition"));
        long attrAsLong = Utils.getAttrAsLong(element, "delay", 0L);
        String nodeName = element.getNodeName();
        nodeName.getClass();
        switch (nodeName) {
            case "GraphicsCommand":
                graphicsCommand = new GraphicsCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "IntentCommand":
                graphicsCommand = new IntentCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "SoundCommand":
                graphicsCommand = new SoundCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "TickListenerCommand":
                graphicsCommand = new TickListenerCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "Command":
                actionCommandCreate = create(screenElement, element.getAttribute("target"), element.getAttribute(am.t));
                break;
            case "MusicCommand":
                graphicsCommand = new MusicCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "PermanenceCommand":
                graphicsCommand = new PermanenceCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "LottieCommand":
                graphicsCommand = new LottieCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "AnimConfigCommand":
                graphicsCommand = new AnimConfigCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "ActionCommand":
                graphicsCommand = new ActionPerformCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "EaseTypeCommand":
                graphicsCommand = new EaseTypeCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "MultiCommand":
            case "GroupCommand":
                graphicsCommand = new MultiCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "FrameRateCommand":
                graphicsCommand = new FrameRateCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "PagCommand":
                if (!PagUtils.supportPagFeature()) {
                    actionCommandCreate = null;
                    break;
                } else {
                    graphicsCommand = new PagCommand(screenElement, element);
                    actionCommandCreate = graphicsCommand;
                    break;
                }
                break;
            case "MethodCommand":
                graphicsCommand = new MethodCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "PbrCommand":
                graphicsCommand = new PbrCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "ExternCommand":
                graphicsCommand = new ExternCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "AnimationCommand":
                graphicsCommand = new AnimationCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "VariableCommand":
                graphicsCommand = new VariableAssignmentCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "VideoCommand":
                graphicsCommand = new VideoCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "SensorCommand":
                graphicsCommand = new SensorBinderCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "JsonObjectCommand":
                graphicsCommand = new JsonObjectCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "IfCommand":
                graphicsCommand = new IfCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "FieldCommand":
                graphicsCommand = new FieldCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "VibrateCommand":
                graphicsCommand = new VibrateCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "AnimStateCommand":
                graphicsCommand = new AnimStateCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "BinderCommand":
                graphicsCommand = new VariableBinderCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "FolmeCommand":
                graphicsCommand = new FolmeCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "FunctionCommand":
                graphicsCommand = new FunctionPerformCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            case "LoopCommand":
                graphicsCommand = new LoopCommand(screenElement, element);
                actionCommandCreate = graphicsCommand;
                break;
            default:
                ObjectFactory.ActionCommandFactory actionCommandFactory = (ObjectFactory.ActionCommandFactory) screenElement.getContext().getObjectFactory("ActionCommand");
                if (actionCommandFactory == null) {
                    actionCommandCreate = null;
                    break;
                } else {
                    actionCommandCreate = actionCommandFactory.create(screenElement, element);
                    break;
                }
                break;
        }
        if (actionCommandCreate == null) {
            return null;
        }
        if (expressionBuild2 != null) {
            actionCommandCreate = new ConditionCommand(actionCommandCreate, expressionBuild2);
        }
        if (attrAsLong > 0) {
            actionCommandCreate = new DelayCommand(actionCommandCreate, attrAsLong);
        }
        return expressionBuild != null ? new ConditionCommand(actionCommandCreate, expressionBuild) : actionCommandCreate;
    }

    public abstract void doPerform();

    public void finish() {
    }

    public final Context getContext() {
        return getScreenContext().mContext;
    }

    public ScreenElementRoot getRoot() {
        return this.mScreenElement.getRoot();
    }

    public final ScreenContext getScreenContext() {
        return this.mScreenElement.getContext();
    }

    public ScreenElement getScreenElement() {
        return this.mScreenElement;
    }

    public final Variables getVariables() {
        return this.mScreenElement.getVariables();
    }

    public void init() {
    }

    public boolean isExpressionsValid(Expression[] expressionArr) {
        if (expressionArr == null) {
            return false;
        }
        int i = 0;
        while (i < expressionArr.length && expressionArr[i] != null) {
            i++;
        }
        return i == expressionArr.length;
    }

    public void pause() {
    }

    public void perform() {
        doPerform();
    }

    public void resume() {
    }
}
