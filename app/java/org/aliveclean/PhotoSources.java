package org.aliveclean;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

final class PhotoSources {
    static final int MIUI=0,GALLERY=1,FILES=2;
    static final String[] LABELS={"小米相册","通用相册","Android 文件"};

    static Intent intent(Context context,int source){
        if(source==MIUI){
            Intent pick=new Intent(Intent.ACTION_PICK).setType("image/*").putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);

            Intent miui=installed(context,pick,new String[]{"com.miui.gallery"});
            if(miui!=null)return miui;
            return installed(context,pick,new String[]{"com.coloros.gallery3d","com.oplus.gallery3d"});
        }
        if(source==GALLERY){
            Intent pick=new Intent(Intent.ACTION_PICK).setType("image/*").putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);

            if(context.getPackageManager().queryIntentActivities(pick,PackageManager.MATCH_DEFAULT_ONLY).isEmpty())return null;
            return Intent.createChooser(pick,"选择相册");
        }
        if(source==FILES){
            Intent files=new Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE).putExtra(Intent.EXTRA_ALLOW_MULTIPLE,false);
            Intent nativeFiles=installed(context,files,new String[]{"com.android.documentsui","com.google.android.documentsui"});
            if(nativeFiles!=null)return nativeFiles;
            return files.resolveActivity(context.getPackageManager())!=null?files:null;
        }
        throw new IllegalArgumentException("Photo source: "+source);
    }
    static Intent vivoMedia(Context context,String mime,boolean multiple){
        Intent pick=new Intent(Intent.ACTION_PICK).setType(mime)
            .putExtra(Intent.EXTRA_ALLOW_MULTIPLE,multiple)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent gallery=installed(context,pick,new String[]{"com.miui.gallery","com.coloros.gallery3d","com.oplus.gallery3d"});
        if(gallery!=null)return gallery;
        return new Intent(Intent.ACTION_OPEN_DOCUMENT).setType(mime)
            .addCategory(Intent.CATEGORY_OPENABLE).putExtra(Intent.EXTRA_ALLOW_MULTIPLE,multiple)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
    private static Intent installed(Context context,Intent base,String[] packages){
        for(String name:packages){
            Intent intent=new Intent(base).setPackage(name);
            if(intent.resolveActivity(context.getPackageManager())!=null)return intent;
        }
        return null;
    }
}
