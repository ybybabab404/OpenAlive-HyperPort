package org.aliveclean;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;

/** In-memory receipt that the SystemUI hook is alive. No clock capability. */
public final class ModuleActivationProvider extends ContentProvider {
    private IBinder module;
    @Override public boolean onCreate(){return true;}

    private boolean caller(String name){
        String[] packages=getContext().getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if(packages!=null)for(String value:packages)if(name.equals(value))return true;
        return false;
    }

    @Override public synchronized Bundle call(String method,String argument,Bundle extras){
        if("hookAnnounce".equals(method)){
            if(!caller("com.android.systemui"))throw new SecurityException("SystemUI module required");
            IBinder owner=extras==null?null:extras.getBinder("owner");
            if(owner==null||!owner.isBinderAlive())throw new IllegalArgumentException("Invalid module runtime");
            try{
                if(owner!=module)owner.linkToDeath(()->{
                    synchronized(ModuleActivationProvider.this){if(module==owner)module=null;}
                },0);
            }catch(android.os.RemoteException dead){
                throw new IllegalStateException("Module runtime exited",dead);
            }
            module=owner;return new Bundle();
        }
        if("hookStatus".equals(method)){
            if(Binder.getCallingUid()!=Process.myUid())throw new SecurityException("Module home required");
            Bundle reply=new Bundle();
            reply.putBoolean("active",module!=null&&module.isBinderAlive());
            return reply;
        }
        throw new IllegalArgumentException("Unknown activation operation");
    }

    @Override public Cursor query(Uri uri,String[] projection,String selection,String[] args,String order){throw new UnsupportedOperationException();}
    @Override public String getType(Uri uri){return null;}
    @Override public Uri insert(Uri uri,ContentValues values){throw new UnsupportedOperationException();}
    @Override public int delete(Uri uri,String selection,String[] args){throw new UnsupportedOperationException();}
    @Override public int update(Uri uri,ContentValues values,String selection,String[] args){throw new UnsupportedOperationException();}
}
