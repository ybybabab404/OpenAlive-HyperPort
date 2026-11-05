package org.aliveclean;

import android.graphics.*;
import android.os.Build;
import android.view.*;
import java.util.*;

/** Camera geometry is separate from the display's status-bar exclusion rectangle. */
final class CameraHoleGeometry {
    static List<RectF> resolve(Display display,DisplayCutout cutout,int width,int height,float density){
        // ColorOS OplusStatusBarUtils.loadScreenHoleData reads these same physical
        // rectangles. Choose the screen whose hole also matches this display's cutout.
        if(display!=null&&cutout!=null){
            Display.Mode largest=display.getMode();
            for(Display.Mode m:display.getSupportedModes())if((long)m.getPhysicalWidth()*m.getPhysicalHeight()>(long)largest.getPhysicalWidth()*largest.getPhysicalHeight())largest=m;
            for(String key:new String[]{"ro.oplus.display.screenhole.positon","ro.oplus.display.secondary.screenhole.position"}){
                List<RectF> candidates=mapProperty(property(key),largest.getPhysicalWidth(),largest.getPhysicalHeight(),width,height,display.getRotation());
                if(!candidates.isEmpty()&&matches(candidates,cutout.getBoundingRects()))return candidates;
            }
        }
        return holes(cutout,width,height,density);
    }
    static List<RectF> holes(DisplayCutout cutout,int width,int height,float density){
        ArrayList<RectF> result=new ArrayList<>();
        if(cutout!=null&&Build.VERSION.SDK_INT>=31&&cutout.getCutoutPath()!=null){
            PathMeasure measure=new PathMeasure(cutout.getCutoutPath(),false);
            do{
                Path contour=new Path();measure.getSegment(0,measure.getLength(),contour,true);
                RectF b=new RectF();contour.computeBounds(b,true);
                if(validHole(b,width,height)&&Math.abs(b.width()-b.height())<=Math.max(2,Math.min(b.width(),b.height())*.15f))result.add(b);
            }while(measure.nextContour());
        }
        if(result.isEmpty()&&cutout!=null)for(Rect b:cutout.getBoundingRects()){
            RectF f=new RectF(b);
            if(validHole(f,width,height)&&Math.abs(f.width()-f.height())<=2)result.add(f);
        }
        if(result.isEmpty()){
            float size=28*density,cy=24*density;
            result.add(new RectF(width/2f-size/2,cy-size/2,width/2f+size/2,cy+size/2));
        }
        return result;
    }
    private static boolean validHole(RectF b,int width,int height){
        return b.width()>2&&b.height()>2&&b.left>0&&b.top>0&&b.right<width&&b.bottom<height
                &&b.width()<Math.min(width,height)*.4f&&b.height()<Math.min(width,height)*.4f;
    }
    private static String property(String key){
        try{return (String)Class.forName("android.os.SystemProperties").getMethod("get",String.class,String.class).invoke(null,key,"");}
        catch(Exception unavailable){return "";}
    }
    static List<RectF> mapProperty(String value,int physicalWidth,int physicalHeight,int width,int height,int rotation){
        ArrayList<RectF> result=new ArrayList<>();
        if(value==null||value.isEmpty()||physicalWidth<=0||physicalHeight<=0)return result;
        String[] parts=value.trim().split("[:,;\\s]+");
        if(parts.length%4!=0||parts.length>16)return result;
        int naturalWidth=rotation%2==0?width:height,naturalHeight=rotation%2==0?height:width;
        float scale=(float)naturalWidth/physicalWidth;
        // Reject incompatible displays; never turn a circular mask into an ellipse.
        if(Math.abs(physicalHeight*scale-naturalHeight)>Math.max(2,naturalHeight*.01f))return result;
        try{
            for(int i=0;i<parts.length;i+=4){
                RectF r=new RectF(Float.parseFloat(parts[i]),Float.parseFloat(parts[i+1]),Float.parseFloat(parts[i+2]),Float.parseFloat(parts[i+3]));
                if(r.isEmpty()||r.left<0||r.top<0||r.right>physicalWidth||r.bottom>physicalHeight
                        ||!Float.isFinite(r.left+r.top+r.right+r.bottom)||r.width()>physicalWidth*.4f||r.height()>physicalHeight*.2f)return Collections.emptyList();
                r.set(r.left*scale,r.top*scale,r.right*scale,r.bottom*scale);
                RectF rotated;
                if(rotation==Surface.ROTATION_90)rotated=new RectF(r.top,naturalWidth-r.right,r.bottom,naturalWidth-r.left);
                else if(rotation==Surface.ROTATION_180)rotated=new RectF(naturalWidth-r.right,naturalHeight-r.bottom,naturalWidth-r.left,naturalHeight-r.top);
                else if(rotation==Surface.ROTATION_270)rotated=new RectF(naturalHeight-r.bottom,r.left,naturalHeight-r.top,r.right);
                else rotated=r;
                result.add(rotated);
            }
        }catch(NumberFormatException error){return Collections.emptyList();}
        return result;
    }
    private static boolean matches(List<RectF> holes,List<Rect> excluded){
        for(RectF hole:holes){boolean matched=false;
            for(Rect r:excluded)if(r.contains(Math.round(hole.centerX()),Math.round(hole.centerY()))){matched=true;break;}
            if(!matched)return false;
        }
        return true;
    }
}
