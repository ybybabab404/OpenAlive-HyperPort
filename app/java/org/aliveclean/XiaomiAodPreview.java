package org.aliveclean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.lang.reflect.*;

/**
 * 离屏渲染一张小米 AOD 预览图：把 com.miui.aod 的样式控制器与缩略图布局装出来后测量、
 * 布局、画进 Bitmap；内部的 Renderer 也被实时预览视图复用来刷新时钟。
 *
 * <p>原版类与资源 id 都通过小米 UI 包的 ClassLoader 反射取得，并且必须先把
 * AODApplication.sInstance / sysuiContext 指向这个 ContextWrapper，原版代码才肯继续初始化；
 * 日期/城市文字用 assets/native-clock/runtime/hyperos/fonts/MiSansVF.ttf 覆盖，
 * 因为宿主里没有小米的字体资源。</p>
 */
final class XiaomiAodPreview {
    static Bitmap render(Context host,Bundle metadata,int width,int height)throws Exception {
        Renderer renderer=new Renderer(host,metadata);
        View view=renderer.view;
        view.measure(View.MeasureSpec.makeMeasureSpec(width,View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(height,View.MeasureSpec.EXACTLY));
        view.layout(0,0,width,height);Bitmap image=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);view.draw(new Canvas(image));return image;
    }
    static final class Renderer {
        final View view,content;
        private final Object renderer;
        private final Context host;
        private final XiaomiAodBattery battery;
        Renderer(Context host,Bundle metadata)throws Exception {
        this.host=host;
        OfficialHyperOsUi ui=OfficialHyperOsUi.openSuperWallpaper(host);ClassLoader loader=ui.getClassLoader();

        loader.loadClass("com.miui.aod.AODApplication").getField("sInstance").set(null,ui);
        loader.loadClass("com.miui.aod.AODApplication").getField("sysuiContext").set(null,ui);
        Class<?> style=loader.loadClass("com.miui.aod.common.StyleInfo");
        Object info=loader.loadClass("com.miui.aod.category.SuperWallpaperCategoryInfo").getConstructor().newInstance();
        XiaomiUi.call(info,"setData",new Class<?>[]{float.class,float.class,float.class,float.class,int.class},
                metadata.getFloat("clock_position_x"),metadata.getFloat("clock_position_y"),
                metadata.getFloat("dual_clock_position_x_anchor_right"),metadata.getFloat("dual_clock_position_y"),metadata.getInt("support_change_with_time",0));
        XiaomiUi.call(info,"switchOnDateAndTime",new Class<?>[]{boolean.class},false);
        XiaomiUi.call(info,"switchOnNotificationIcon",new Class<?>[]{boolean.class},false);
        XiaomiUi.call(info,"switchOnBatteryIcon",new Class<?>[]{boolean.class},true);
        XiaomiUi.call(info,"setLunarSwitchOn",new Class<?>[]{boolean.class},false);
        renderer=loader.loadClass("com.miui.aod.AODStyleController").getConstructor(int.class).newInstance(5);
        int layout=ui.getResources().getIdentifier("thumbnail_real_aod_layout","layout","com.miui.aod");
        view=LayoutInflater.from(ui).inflate(layout,null,false);
        View container=view.findViewById(ui.getResources().getIdentifier("clock_container","id","com.miui.aod"));
        XiaomiUi.call(renderer,"inflateView",new Class<?>[]{View.class,style},container,info);

        android.graphics.Typeface auxiliary=new android.graphics.Typeface.Builder(host.getAssets(),
                "native-clock/runtime/hyperos/fonts/MiSansVF.ttf").setWeight(400).build();
        if(auxiliary==null)throw new IllegalStateException("Original Xiaomi date font missing");
        for(String id:new String[]{"date","date_lunar","city","city2"}){
            View text=view.findViewById(ui.getResources().getIdentifier(id,"id","com.miui.aod"));
            if(text instanceof android.widget.TextView)((android.widget.TextView)text).setTypeface(auxiliary);
        }
        battery=XiaomiAodBattery.install(host,view,auxiliary);
        update();
        View bg=view.findViewById(ui.getResources().getIdentifier("aod_bg","id","com.miui.aod"));if(bg!=null)bg.setVisibility(View.INVISIBLE);
        content=view.findViewById(ui.getResources().getIdentifier("single_clock_container","id","com.miui.aod"));
        if(content==null)throw new IllegalStateException("Original Xiaomi AOD clock content missing");

        for(String id:new String[]{"aod_icons_container"}){
            View unused=view.findViewById(ui.getResources().getIdentifier(id,"id","com.miui.aod"));
            if(unused!=null&&unused.getParent() instanceof android.view.ViewGroup)((android.view.ViewGroup)unused.getParent()).removeView(unused);
        }
        }
        void update()throws Exception {XiaomiUi.call(renderer,"handleUpdateTime",new Class<?>[]{boolean.class},android.text.format.DateFormat.is24HourFormat(host));if(battery!=null)battery.refreshLabels();}
        void active(boolean value){if(battery!=null)battery.active(value);}
        void whenBatteryChanged(Runnable callback){if(battery!=null)battery.whenChanged(callback);}
    }
}
