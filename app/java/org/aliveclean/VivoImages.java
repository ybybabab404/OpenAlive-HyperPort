package org.aliveclean;

import android.content.Context;
import android.graphics.*;
import java.io.*;
import java.util.*;

final class VivoImages implements AutoCloseable {
    final ArrayList<Bitmap> photos=new ArrayList<>();
    Bitmap subject,paint;
    static VivoImages load(Context host,VivoEngineRuntime runtime,VivoOptions options)throws IOException{
        VivoImages images=new VivoImages();
        try{
            if(options.photos.isEmpty()){
                if(options.family==VivoOptions.RASTER)for(int i=0;i<3;i++)images.photos.add(asset(runtime,"vivo/default/raster-"+i+".jpg"));
                else {String category=options.family==VivoOptions.LIQUID?"liquid":"flash";
                    images.photos.add(asset(runtime,"vivo/default/"+category+".jpg"));images.subject=asset(runtime,"vivo/default/"+category+"-mask.png");}
                if(options.family==VivoOptions.FLASH&&options.paint.isEmpty()){

                    Bitmap canvas=asset(runtime,"vivo/default/flash-doodle.png");
                    try{
                        if(canvas.getWidth()!=1080||canvas.getHeight()!=3600)throw new IOException("Unexpected Vivo factory drawing canvas");
                        images.paint=Bitmap.createBitmap(canvas,0,1200,1080,2400);
                    }finally{canvas.recycle();}
                }
            }else for(String name:options.photos)images.photos.add(file(host,name));
            if(options.family==VivoOptions.RASTER)for(int i=0;i<images.photos.size();i++){
                Bitmap source=images.photos.get(i),rotated=rotate(source,options.slots[i].rotation);
                if(rotated!=source){images.photos.set(i,rotated);source.recycle();}
            }
            if(!options.subject.isEmpty()){if(images.subject!=null)images.subject.recycle();images.subject=file(host,options.subject);}
            if(!options.paint.isEmpty())images.paint=file(host,options.paint);
            return images;
        }catch(IOException|RuntimeException e){images.close();throw e;}
    }
    /**
     * 只读首张照片的尺寸（不解码像素）：调用方原来为了拿宽高把整套图片都解码了一遍。
     *
     * 注意：`photos` 为空是**正常状态**——液态/光栅/闪卡在没有导入照片时用的就是内置默认图
     * （`VivoImages.load()` 会回退到 `vivo/default/*.jpg`）。这里必须走同样的大小写回退，
     * 否则进入页面就会抛 "No photo selected"，用户看到 "照片加载失败" 的 toast，
     * 而画面上默认壁纸其实是正常显示的。
     */
    static int[] size(Context host,VivoEngineRuntime runtime,VivoOptions options)throws IOException{
        int[] edge;
        if(options.photos.isEmpty()){
            String name=options.family==VivoOptions.LIQUID?"vivo/default/liquid.jpg"
                    :options.family==VivoOptions.FLASH?"vivo/default/flash.jpg"
                    :"vivo/default/raster-0.jpg";
            edge=assetSize(runtime,name);
        }else{
            edge=PhotoDecode.bounds(new File(host.getFilesDir(),options.photos.get(0)));
        }
        // load() 对 RASTER 家族（无论图片是用户选的还是默认图）都会按 slots[0].rotation 旋转，
        // 所以这里也必须交换宽高，否则裁切框比例会错。
        boolean swap=options.family==VivoOptions.RASTER&&options.slots.length>0
                &&Math.floorMod(options.slots[0].rotation,2)==1;
        return swap?new int[]{edge[1],edge[0]}:edge;
    }
    private static int[] assetSize(VivoEngineRuntime runtime,String name)throws IOException{
        BitmapFactory.Options options=new BitmapFactory.Options();options.inJustDecodeBounds=true;
        try(InputStream in=runtime.getAssets().open(name)){BitmapFactory.decodeStream(in,null,options);}
        if(options.outWidth<=0||options.outHeight<=0)throw new IOException("默认图片不可用："+name);
        return new int[]{options.outWidth,options.outHeight};
    }
    static Bitmap asset(VivoEngineRuntime runtime,String name)throws IOException{
        try(InputStream in=runtime.getAssets().open(name)){Bitmap b=BitmapFactory.decodeStream(in);if(b==null)throw new IOException("Invalid Vivo image: "+name);return b;}
    }
    static Bitmap rotate(Bitmap source,int turns){
        if(Math.floorMod(turns,4)==0)return source;
        Matrix matrix=new Matrix();matrix.postRotate(90*Math.floorMod(turns,4));
        return Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
    }
    static Bitmap slot(Context host,VivoEngineRuntime runtime,VivoOptions options,int index)throws IOException{
        return index<options.photos.size()?file(host,options.photos.get(index)):asset(runtime,"vivo/default/raster-"+index+".jpg");
    }
    private static Bitmap file(Context host,String name)throws IOException{
        File f=new File(host.getFilesDir(),name);
        if(!name.matches("vivo-[A-Za-z0-9._-]+"))throw new IOException("Invalid image path");
        return PhotoDecode.decode(f,2560);
    }
    @Override public void close(){for(Bitmap b:photos)b.recycle();photos.clear();if(subject!=null)subject.recycle();if(paint!=null)paint.recycle();subject=paint=null;}
}
