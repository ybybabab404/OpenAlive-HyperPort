package org.aliveclean;

import android.content.*;
import android.database.Cursor;
import android.graphics.*;
import android.net.Uri;
import android.os.*;
import java.io.*;
import java.util.List;

/** Authenticated Binder bootstrap. No image files or unrestricted operations are exposed. */
public final class SceneProvider extends ContentProvider {
    static final Uri CONFIGURATION=Uri.parse("content://org.aliveclean.scene/configuration");
    static void changed(Context context){context.getContentResolver().notifyChange(CONFIGURATION,null);}
    @Override public boolean onCreate(){return true;}
    @Override public Bundle call(String method,String argument,Bundle extras){
        Context c=getContext();
        if("aod_preview".equals(method)){
            if(!aodCaller(c))throw new SecurityException("AOD preview permission required");
            return aodPreview(c);
        }
        if(c.checkCallingOrSelfPermission("android.permission.STATUS_BAR")!=android.content.pm.PackageManager.PERMISSION_GRANTED)throw new SecurityException("System UI permission required");
        if(!"connect".equals(method)&&!"configuration".equals(method))throw new IllegalArgumentException("Unknown operation");
        try{
            Bundle result=new Bundle();
            NotificationOptions.export(c,result);
            if("connect".equals(method))result.putBinder("channel",SceneChannel.connect(c,extras==null?null:extras.getBinder("owner"),extras==null?0:extras.getInt("clock_api",0),extras==null?null:extras.getString("clock_error"),extras==null?null:extras.getBinder("clock_feedback")));
            SceneOptions options=new SceneOptions(c.getSharedPreferences(SceneOptions.APPLIED,0));
            result.putInt("aod",options.aod);
            result.putBoolean("vivo_wallpaper",options.vivo!=null);
            // The provider lives in our process, so this is the only place where the live wallpaper
            // service's own flag is observable; other processes see their own copy of the static.
            result.putBoolean("wallpaper_running",CleanWallpaper.RUNNING);
            result.putBoolean("vivo_liquid",options.vivo!=null&&options.vivo.family==VivoOptions.LIQUID);
            result.putBoolean("continuous_aod",options.vivo!=null?options.vivo.animateInAod():options.cosmic!=0?options.cosmicContinuousAod:options.aod==0&&options.sailContinuousAod);
            Bundle xiaomiClock=XiaomiState.lockClock(c);
            if(xiaomiClock!=null){
                result.putInt("aod",101);result.putBoolean("vivo_wallpaper",false);
                result.putBoolean("continuous_aod",false);
                result.putBundle("xiaomi_clock",xiaomiClock);
            }
            return result;
        }catch(Exception error){throw new IllegalStateException("Scene channel unavailable",error);}
    }

    /** Read-only preview for the com.miui.aod settings page. Never reads or writes user settings. */
    private boolean aodCaller(Context c){
        if(c.checkCallingOrSelfPermission("android.permission.STATUS_BAR")==android.content.pm.PackageManager.PERMISSION_GRANTED)return true;
        String caller=getCallingPackage();
        if("com.miui.aod".equals(caller))return true;
        try{
            String[] packages=c.getPackageManager().getPackagesForUid(Binder.getCallingUid());
            if(packages!=null)for(String name:packages)if("com.miui.aod".equals(name))return true;
        }catch(Throwable ignored){}
        return false;
    }

    /** The preview panel speaks for our own wallpaper only; with a stock wallpaper it keeps the system preview. */
    private static boolean ourWallpaper(Context c){
        try{
            android.app.WallpaperInfo info=android.app.WallpaperManager.getInstance(c).getWallpaperInfo();
            if(info!=null)return "org.aliveclean".equals(info.getPackageName());
        }catch(Throwable ignored){}
        // Same process as the wallpaper service, so its flag is observable here even when the
        // system refuses to hand WallpaperInfo to a caller.
        return CleanWallpaper.RUNNING;
    }

    private Bundle aodPreview(Context c){
        Bundle result=new Bundle();
        Bitmap bitmap=null;
        try{
            if(!ourWallpaper(c))return result;
            SceneOptions options=new SceneOptions(c.getSharedPreferences(SceneOptions.APPLIED,0));
            VivoOptions vivo=options.vivo;
            if(vivo==null)vivo=currentVivo(c);
            if(vivo!=null)bitmap=vivoPreview(c,vivo);
            if(bitmap==null){
                XiaomiHomePreview.Card[] cards=XiaomiHomePreview.load(c);
                if(cards[0]!=null&&cards[0].image!=null)bitmap=cards[0].image.copy(Bitmap.Config.ARGB_8888,false);
            }
            if(bitmap==null)bitmap=wallpaperSnapshot(c);
            if(bitmap==null)return result;
            Bitmap scaled=scale(bitmap);
            if(scaled!=bitmap)bitmap.recycle();
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            boolean alpha=scaled.hasAlpha();
            scaled.compress(alpha?Bitmap.CompressFormat.PNG:Bitmap.CompressFormat.JPEG,alpha?100:88,out);
            result.putByteArray("image",out.toByteArray());
            result.putBoolean("alpha",alpha);
            result.putInt("width",scaled.getWidth());
            result.putInt("height",scaled.getHeight());
            scaled.recycle();
        }catch(Throwable error){
            android.util.Log.w("OpenAliveScene","AOD preview unavailable",error);
        }
        return result;
    }

    /** LIQUID/FLASH: selected photo itself. RASTER: cover, i.e. the first photo. */
    private Bitmap vivoPreview(Context c,VivoOptions vivo)throws IOException{
        String name=vivo.photos.isEmpty()?null:vivo.photos.get(0);
        Bitmap bitmap=null;
        if(name!=null)bitmap=decode(new File(c.getFilesDir(),name));
        if(bitmap==null){
            String asset=vivo.family==VivoOptions.RASTER?"vivo/default/raster-0.jpg"
                    :vivo.family==VivoOptions.LIQUID?"vivo/default/liquid.jpg":"vivo/default/flash.jpg";
            try(InputStream in=c.getAssets().open(asset)){
                bitmap=BitmapFactory.decodeStream(in);
            }
        }
        if(bitmap==null)return null;
        if(vivo.family==VivoOptions.RASTER&&!vivo.photos.isEmpty()){
            int turns=Math.floorMod(vivo.slots[0].rotation,4);
            if(turns!=0){
                Matrix matrix=new Matrix();matrix.postRotate(90*turns);
                Bitmap rotated=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                if(rotated!=bitmap)bitmap.recycle();
                bitmap=rotated;
            }
        }
        return bitmap;
    }

    /** APPLIED may not carry a vivo block; the editor records the style the user last picked. */
    private static VivoOptions currentVivo(Context c){
        try{
            android.content.SharedPreferences editor=c.getSharedPreferences("vivo.editor",0);
            int family=editor.getInt("current_family",0);
            if(family>=1&&family<=3){
                VivoOptions picked=VivoOptions.parse(editor.getString("family."+family,""));
                if(picked!=null)return picked;
            }
            // Older installs predate current_family: take the first family that still has a photo.
            for(int i=1;i<=3;i++){
                VivoOptions candidate=VivoOptions.parse(editor.getString("family."+i,""));
                if(candidate!=null&&!candidate.photos.isEmpty())return candidate;
            }
            return null;
        }catch(Throwable ignored){return null;}
    }

    private Bitmap wallpaperSnapshot(Context c){
        File internal=new File(c.getFilesDir(),SceneOptions.WALLPAPER);
        if(internal.isFile())return decode(internal);
        File external=c.getExternalFilesDir(null);
        if(external!=null){
            File file=new File(external,SceneOptions.WALLPAPER);
            if(file.isFile())return decode(file);
        }
        return null;
    }

    private static Bitmap decode(File file){
        if(file==null||!file.isFile())return null;
        try{
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(file),(decoder,info,source)->{
                int width=info.getSize().getWidth(),height=info.getSize().getHeight();
                float ratio=Math.min(1f,1440f/Math.max(width,height));
                decoder.setTargetSize(Math.max(1,Math.round(width*ratio)),Math.max(1,Math.round(height*ratio)));
                decoder.setAllocator(ImageDecoder.ALLOCATOR_SOFTWARE);
            });
        }catch(Throwable error){
            android.util.Log.w("OpenAliveScene","AOD preview decode",error);
            return null;
        }
    }

    private static Bitmap scale(Bitmap source){
        int width=source.getWidth(),height=source.getHeight();
        float ratio=Math.min(1f,720f/Math.max(width,height));
        if(ratio>=1f)return source;
        return Bitmap.createScaledBitmap(source,Math.max(1,Math.round(width*ratio)),Math.max(1,Math.round(height*ratio)),true);
    }

    @Override public Cursor query(Uri uri,String[] projection,String selection,String[] args,String order){throw new UnsupportedOperationException();}
    @Override public String getType(Uri uri){return null;}
    @Override public Uri insert(Uri uri,ContentValues values){throw new UnsupportedOperationException();}
    @Override public int delete(Uri uri,String selection,String[] args){throw new UnsupportedOperationException();}
    @Override public int update(Uri uri,ContentValues values,String selection,String[] args){throw new UnsupportedOperationException();}
}
