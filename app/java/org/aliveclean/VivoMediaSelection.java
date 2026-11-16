package org.aliveclean;

import android.content.Context;
import android.graphics.*;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import java.io.*;
import java.util.*;

/**
 * 素材导入：把用户从相册选中的图片/视频复制成应用私有文件，折算进 VivoOptions 的照片、视频与槽位字段，
 * 并记录本次新建的文件，放弃时统一删除。
 *
 * <p>图片优先原样复制而不重新编码——没有二次压缩损失，也只要几毫秒，解码交给引擎按需采样；
 * 只有复制过来的文件确实解不开时，才退回解码后重新压缩。</p>
 */
final class VivoMediaSelection {
    static final int WALLPAPER=10,VIDEO=11,COVER=12,ALBUM=13,COVER_VIDEO=14,ALBUM_VIDEO=15;
    static final int SLOT_IMAGE=30,SLOT_VIDEO=33;
    static final int BATCH_IMAGES=36;
    static boolean isSlot(int kind){return kind>=SLOT_IMAGE&&kind<SLOT_VIDEO+3;}
    static int slotIndex(int kind){return (kind-SLOT_IMAGE)%3;}
    final VivoOptions result;
    private final Context context;
    private final ArrayList<String> created=new ArrayList<>();
    VivoMediaSelection(Context context,VivoOptions current){this.context=context;result=VivoOptions.parse(current.json());}
    static VivoOptions defaults(VivoOptions current){
        VivoOptions result=new VivoOptions(current.family);
        result.sensitivity=current.sensitivity;result.wallpaperZoom=current.wallpaperZoom;
        return result;
    }
    void importUris(VivoEngineRuntime runtime,int kind,List<Uri> uris)throws Exception{
        if(kind==BATCH_IMAGES){
            if(uris.isEmpty()||uris.size()>3)throw new IOException("请选择 1 至 3 张图片，依次放入封面、光栅1、光栅2");
            for(int i=0;i<uris.size();i++)importSlot(runtime,i,false,Collections.singletonList(uris.get(i)));
            clearLegacyVideo();return;
        }
        if(isSlot(kind)){importSlot(runtime,slotIndex(kind),kind>=SLOT_VIDEO,uris);return;}
        int maximum=kind==ALBUM||kind==ALBUM_VIDEO?2:1;
        if(uris.isEmpty()||uris.size()>maximum)throw new IOException(kind==ALBUM?"请选择 1 至 2 张光栅图片（加上封面共 2 至 3 张）":"请选择一个素材");
        if(kind==VIDEO){ensureRasterPhotos(runtime);video(uris.get(0));for(int i=1;i<result.rasterVideos.size();i++)result.rasterVideos.set(i,"");return;}
        if(kind==COVER_VIDEO||kind==ALBUM_VIDEO){
            ensureRasterPhotos(runtime);
            if(kind==COVER_VIDEO){
                VivoOptions previous=VivoOptions.parse(result.json());video(uris.get(0));
                String poster=result.videoFirstFrame,clip=result.video;
                result.video=previous.video;result.videoFirstFrame=previous.videoFirstFrame;result.videoDuration=previous.videoDuration;
                result.videoFrames=previous.videoFrames;result.videoRate=previous.videoRate;
                result.photos.set(0,poster);result.rasterVideos.set(0,clip);
                result.x=result.y=.5f;result.zoom=1;return;
            }
            ArrayList<String> posters=new ArrayList<>(),clips=new ArrayList<>();
            for(Uri uri:uris){video(uri);posters.add(result.videoFirstFrame);clips.add(result.video);}
            clearLegacyVideo();
            replaceAlbum(posters,clips);
            result.x=result.y=.5f;result.zoom=1;return;
        }
        ArrayList<String> selected=new ArrayList<>();
        long importStart=System.currentTimeMillis();
        for(Uri uri:uris)selected.add(importPhoto(uri));
        if(kind==COVER){
            ensureRasterPhotos(runtime);
            result.photos.set(0,selected.get(0));
            result.rasterVideos.set(0,"");
        }else if(kind==ALBUM){
            ensureRasterPhotos(runtime);
            replaceAlbum(selected,Collections.nCopies(selected.size(),""));clearLegacyVideo();
        }else{
            result.photos.clear();result.photos.addAll(selected);result.video="";
            result.rasterVideos.clear();for(int i=0;i<selected.size();i++)result.rasterVideos.add("");
            result.subject="";result.paint="";result.area=4;
        }
        AliveLog.i("AliveClean","vivo import total "+(System.currentTimeMillis()-importStart)+"ms for "+uris.size()+" item(s)");
        result.x=result.y=.5f;result.zoom=1;
    }
    private void importSlot(VivoEngineRuntime runtime,int index,boolean movie,List<Uri> uris)throws Exception{
        if(result.family!=VivoOptions.RASTER||uris.size()!=1)throw new IOException("每个位置请选择一个图片或视频");
        ensureRasterPhotos(runtime);

        while(result.photos.size()<=index){
            Bitmap b=VivoImages.asset(runtime,"vivo/default/raster-"+result.photos.size()+".jpg");
            try{result.photos.add(write(b));result.rasterVideos.add("");}finally{b.recycle();}
        }
        VivoOptions previous=VivoOptions.parse(result.json());
        if(movie){
            video(uris.get(0));String poster=result.videoFirstFrame,clip=result.video;
            result.video=previous.video;result.videoFirstFrame=previous.videoFirstFrame;result.videoDuration=previous.videoDuration;
            result.videoFrames=previous.videoFrames;result.videoRate=previous.videoRate;
            result.photos.set(index,poster);result.rasterVideos.set(index,clip);
        }else{
            result.photos.set(index,importPhoto(uris.get(0)));result.rasterVideos.set(index,"");
        }
        result.slots[index]=new VivoOptions.Slot();
        if(index>0)clearLegacyVideo();
    }
    private void ensureRasterPhotos(VivoEngineRuntime runtime)throws IOException{
        if(!result.photos.isEmpty()){while(result.rasterVideos.size()<result.photos.size())result.rasterVideos.add("");return;}
        for(int i=0;i<3;i++){
            Bitmap bitmap=VivoImages.asset(runtime,"vivo/default/raster-"+i+".jpg");
            try{result.photos.add(write(bitmap));result.rasterVideos.add("");}finally{bitmap.recycle();}
        }
    }
    void swapSlots(VivoEngineRuntime runtime,int a,int b)throws IOException{
        if(a<0||a>2||b<0||b>2||!result.video.isEmpty())throw new IOException("当前模式无法交换位置");
        ensureRasterPhotos(runtime);
        if(a>=result.photos.size()||b>=result.photos.size())throw new IOException("请先添加该位置的素材");
        Collections.swap(result.photos,a,b);Collections.swap(result.rasterVideos,a,b);
        VivoOptions.Slot slot=result.slots[a];result.slots[a]=result.slots[b];result.slots[b]=slot;
    }
    private void replaceAlbum(List<String> posters,List<String> clips){
        String cover=result.photos.get(0),coverVideo=result.rasterVideos.get(0);
        result.photos.clear();result.photos.add(cover);result.photos.addAll(posters);
        result.rasterVideos.clear();result.rasterVideos.add(coverVideo);result.rasterVideos.addAll(clips);
    }
    private void clearLegacyVideo(){result.video="";result.videoFirstFrame="";result.videoDuration=0;result.videoFrames=0;}
    /**
     * 导入一张图片：优先"原封不动复制"，不重新编码——既没有二次压缩损失，也只要几毫秒。
     * 引擎加载时会按需采样解码（本来就要解一次），所以这里不需要预缩。
     * 只有复制过来的文件无法解码（少见的格式）时，才回退到解码 + JPEG(100) 重新编码。
     */
    private String importPhoto(Uri uri)throws Exception{
        long start=System.currentTimeMillis();
        String base="vivo-"+UUID.randomUUID();
        String extension=extension(uri);
        String name=base+extension;created.add(name);
        try(InputStream in=context.getContentResolver().openInputStream(uri);
            FileOutputStream out=context.openFileOutput(name,0)){
            if(in==null)throw new IOException("无法读取照片");
            byte[] buffer=new byte[65536];
            for(int n;(n=in.read(buffer))!=-1;)out.write(buffer,0,n);
            out.getFD().sync();
        }
        try{
            PhotoDecode.bounds(new File(context.getFilesDir(),name));
            AliveLog.i("AliveClean","vivo import copy "+name+" "+(System.currentTimeMillis()-start)+"ms");
            return name;
        }catch(IOException notDecodable){
            context.deleteFile(name);
            created.remove(name);
            Bitmap bitmap=PhotoDecode.decode(context.getContentResolver(),uri,2560);
            try{return write(bitmap);}finally{bitmap.recycle();}
        }
    }

    private String extension(Uri uri){
        String type=null;
        try{type=context.getContentResolver().getType(uri);}catch(RuntimeException ignored){}
        if(type!=null){
            if(type.contains("png"))return ".png";
            if(type.contains("webp"))return ".webp";
            if(type.contains("heic")||type.contains("heif"))return ".heic";
        }
        String path=uri.getPath();
        if(path!=null){int dot=path.lastIndexOf('.');
            if(dot>=0){String ext=path.substring(dot).toLowerCase(Locale.ROOT);
                if(ext.matches("\\.(jpg|jpeg|png|webp|heic|heif)"))return ext;}}
        return ".jpg";
    }

    private String write(Bitmap bitmap)throws IOException{
        // 照片没有透明通道时用 JPEG：同样 2560 边长，编码比 PNG 快一个数量级，文件也小得多。
        // 蒙版之类需要 alpha 的位图仍然走 PNG。
        boolean opaque=!bitmap.hasAlpha();
        String name="vivo-"+UUID.randomUUID()+(opaque?".jpg":".png");created.add(name);
        long start=System.currentTimeMillis();
        try(FileOutputStream out=context.openFileOutput(name,0)){
            boolean ok=bitmap.compress(opaque?Bitmap.CompressFormat.JPEG:Bitmap.CompressFormat.PNG,100,out);
            if(!ok)throw new IOException("照片编码失败");
            out.getFD().sync();
        }
        AliveLog.i("AliveClean","vivo import "+bitmap.getWidth()+"x"+bitmap.getHeight()
                +(opaque?" jpeg":" png")+" "+(System.currentTimeMillis()-start)+"ms");
        return name;
    }
    private void video(Uri uri)throws Exception{
        String name="vivo-"+UUID.randomUUID()+".mp4";created.add(name);
        try(InputStream in=context.getContentResolver().openInputStream(uri);OutputStream out=context.openFileOutput(name,0)){
            if(in==null)throw new IOException("无法读取视频");
            byte[] buffer=new byte[65536];long total=0;
            for(int n;(n=in.read(buffer))!=-1;){total+=n;if(total>512L*1024*1024)throw new IOException("请选择小于 512 MB 的视频");out.write(buffer,0,n);}
        }
        MediaMetadataRetriever media=new MediaMetadataRetriever();
        try{
            media.setDataSource(new File(context.getFilesDir(),name).getPath());
            long duration=Long.parseLong(media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            if(duration<=0)throw new IOException("视频时长无效");
            Bitmap first=media.getFrameAtTime(0,MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            if(first==null)throw new IOException("无法解码视频封面");
            try{
                if(result.photos.isEmpty()){result.photos.add(write(first));}
                result.videoFirstFrame=write(first);
            }finally{first.recycle();}
            String count=media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
            String rate=media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
            result.videoRate=rate==null?30:Math.max(1,Math.round(Float.parseFloat(rate)));
            result.videoFrames=count==null?Math.max(1,(int)(duration*result.videoRate/1000)):Integer.parseInt(count);
            result.videoDuration=duration;result.video=name;
        }finally{media.release();}
    }
    void discard(){for(String file:created)context.deleteFile(file);created.clear();}
}
