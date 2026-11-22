package org.aliveclean;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import java.io.InputStream;

/**
 * 场景配置的读写层：applied 存 {@code scene}，预览/编辑存 {@code scene.preview}，
 * 负责把键值翻译成字段并给出默认值。注意：个别键名（如 {@code photo}、{@code frame_*}）目前仍以字面量
 * 散落在调用方，新增键时请优先在这里补常量，避免拼错。
 */
final class SceneOptions {
    static final String APPLIED="scene", DRAFT="scene.preview";

    static final String WALLPAPER="wallpaper.png";
    final int aod,lock,home,color,cosmic;
    final VivoOptions vivo;
    final String photo,homePhoto,framePhoto;
    final boolean followLock,framePair,cosmicKeepLock;
    final boolean cosmicContinuousAod,cosmicContinuousHome,sailContinuousAod;
    final int stereoSensitivity;
    final boolean stereoAodAnimation;
    final float frameX,frameY,frameSize,frameAngle;

    SceneOptions(SharedPreferences prefs) {
        int a=prefs.getInt("aod",0),l=prefs.getInt("lock",2),h=prefs.getInt("home",6);
        vivo=VivoOptions.parse(prefs.getString("vivo",""));
        int c=prefs.getInt("cosmic",0);cosmic=supportedCosmic(c)?c:0;
        cosmicKeepLock=prefs.getBoolean("cosmic_keep_lock",false);
        stereoSensitivity=Math.max(-50,Math.min(50,prefs.getInt("stereo_sensitivity",0)));
        stereoAodAnimation=prefs.getBoolean("stereo_aod_animation",true);
        cosmicContinuousAod=prefs.getBoolean("cosmic_continuous_aod",true);
        cosmicContinuousHome=prefs.getBoolean("cosmic_continuous_home",true);
        sailContinuousAod=prefs.getBoolean("sail_continuous_aod",true);
        aod=cosmic!=0?0:a==-1||a==0||PhotoStyle.supported(a)||a==101?a:0;
        lock=l>=0&&l<=5?l:2;
        home=h>=6&&h<=9?h:6;
        color=prefs.getInt("color",0xff264552);
        String name=prefs.getString("photo","photo");
        photo=name!=null&&name.matches("[A-Za-z0-9._-]+")&&!name.equals(".")&&!name.equals("..")?name:"photo";
        name=prefs.getString("home_photo",photo);
        homePhoto=name!=null&&name.matches("[A-Za-z0-9._-]+")&&!name.equals(".")&&!name.equals("..")?name:photo;
        followLock=prefs.getBoolean("home_follow_lock",true);
        framePair=prefs.getBoolean("frame_pair",true);
        name=prefs.getString("frame_photo",photo);
        framePhoto=name!=null&&name.matches("[A-Za-z0-9._-]+")&&!name.equals(".")&&!name.equals("..")?name:photo;
        frameX=finite(prefs.getFloat("frame_x",.5f),0,1,.5f);
        frameY=finite(prefs.getFloat("frame_y",.5f),0,1,.5f);
        frameSize=finite(prefs.getFloat("frame_size",1),.05f,1,1);
        frameAngle=finite(prefs.getFloat("frame_angle",0),-45,45,0);
    }

    private static float finite(float v,float min,float max,float fallback){return Float.isNaN(v)||Float.isInfinite(v)?fallback:Math.max(min,Math.min(max,v));}
    static boolean supportedCosmic(int c){return c==1||c==3||c==4||(c>=6&&c<=19)||(c>=101&&c<=105)||(c>=201&&c<=205)||(c>=301&&c<=303)||(c>=401&&c<=403)||(c>=501&&c<=503);}

    /**
     * 从磁盘读一份配置快照。
     *
     * <p>给"壁纸服务在独立进程"用：{@code SharedPreferences} 的实例缓存是按进程的，
     * 主进程写了 applied 之后，{@code :wallpaper} 进程里那个实例永远读到旧值
     * （MODE_MULTI_PROCESS 早就废弃且不再生效）。所以那边不碰 SharedPreferences，
     * 每次直接解析 {@code shared_prefs/<name>.xml}——文件总是最新的。</p>
     */
    static SceneOptions load(Context context,String name){
        MemoryPreferences prefs=new MemoryPreferences();
        File file=new File(context.getApplicationInfo().dataDir,"shared_prefs/"+name+".xml");
        if(file.isFile()){
            try(InputStream in=new java.io.FileInputStream(file)){
                org.xmlpull.v1.XmlPullParser parser=android.util.Xml.newPullParser();
                parser.setInput(in,"utf-8");
                for(int event=parser.getEventType();event!=org.xmlpull.v1.XmlPullParser.END_DOCUMENT;event=parser.next()){
                    if(event!=org.xmlpull.v1.XmlPullParser.START_TAG)continue;
                    String name_=parser.getAttributeValue(null,"name");
                    if(name_==null)continue;
                    String value=parser.getAttributeValue(null,"value");
                    switch(parser.getName()){
                        case "string": prefs.strings.put(name_,parser.nextText());break;
                        case "int": prefs.ints.put(name_,Integer.parseInt(value));break;
                        case "long": prefs.longs.put(name_,Long.parseLong(value));break;
                        case "float": prefs.floats.put(name_,Float.parseFloat(value));break;
                        case "boolean": prefs.booleans.put(name_,"true".equals(value));break;
                        default: break;
                    }
                }
            }catch(Throwable error){AliveLog.w("AliveClean","read "+name+" preferences failed",error);}
        }
        return new SceneOptions(prefs);
    }

    /** 只读的 SharedPreferences：把解析出来的那份 XML 伪装成偏好表，复用现有的构造逻辑。 */
    private static final class MemoryPreferences implements SharedPreferences {
        final java.util.Map<String,String> strings=new java.util.HashMap<>();
        final java.util.Map<String,Integer> ints=new java.util.HashMap<>();
        final java.util.Map<String,Long> longs=new java.util.HashMap<>();
        final java.util.Map<String,Float> floats=new java.util.HashMap<>();
        final java.util.Map<String,Boolean> booleans=new java.util.HashMap<>();
        public java.util.Map<String,?> getAll(){java.util.Map<String,Object> all=new java.util.HashMap<>();all.putAll(strings);all.putAll(ints);all.putAll(longs);all.putAll(floats);all.putAll(booleans);return all;}
        public String getString(String key,String fallback){String value=strings.get(key);return value==null?fallback:value;}
        public java.util.Set<String> getStringSet(String key,java.util.Set<String> fallback){return fallback;}
        public int getInt(String key,int fallback){Integer value=ints.get(key);return value==null?fallback:value;}
        public long getLong(String key,long fallback){Long value=longs.get(key);return value==null?fallback:value;}
        public float getFloat(String key,float fallback){Float value=floats.get(key);return value==null?fallback:value;}
        public boolean getBoolean(String key,boolean fallback){Boolean value=booleans.get(key);return value==null?fallback:value;}
        public boolean contains(String key){return getAll().containsKey(key);}
        public Editor edit(){throw new UnsupportedOperationException("read only");}
        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener){}
        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener){}
    }
    boolean pairedFrame(){return aod==1&&framePair;}

    boolean save(SharedPreferences target) {
        return target.edit().putString("vivo",vivo==null?"":vivo.json()).putInt("aod",aod).putInt("cosmic",cosmic).putInt("lock",lock)
            .putBoolean("cosmic_keep_lock",cosmicKeepLock)
            .putInt("stereo_sensitivity",stereoSensitivity).putBoolean("stereo_aod_animation",stereoAodAnimation)
            .putBoolean("cosmic_continuous_aod",cosmicContinuousAod)
            .putBoolean("cosmic_continuous_home",cosmicContinuousHome)
            .putBoolean("sail_continuous_aod",sailContinuousAod)
            .putInt("home",home).putInt("color",color).putString("photo",photo)
            .putString("home_photo",homePhoto).putBoolean("home_follow_lock",followLock)
            .putBoolean("frame_pair",framePair).putString("frame_photo",framePhoto)
            .putFloat("frame_x",frameX).putFloat("frame_y",frameY).putFloat("frame_size",frameSize).putFloat("frame_angle",frameAngle).commit();
    }
}
