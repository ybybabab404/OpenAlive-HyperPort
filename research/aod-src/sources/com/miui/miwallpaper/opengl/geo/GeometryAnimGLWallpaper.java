package com.miui.miwallpaper.opengl.geo;

import com.miui.miwallpaper.opengl.ImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;

/* JADX INFO: loaded from: classes2.dex */
public class GeometryAnimGLWallpaper extends AnimImageGLWallpaper {
    public int uCapsuleLens_Pos_cosSin;
    public int uCapsuleLens_Size_NB;
    public int uDistance;
    public int uGlassScale_uInnerProgress;
    public int uLodThres_Fac_Shadow_Highlight;
    public int uPosT;
    public int uRoundBoxLensRz_Pos_cosSin;
    public int uRoundBoxLens_Size_NB_R;
    public int uSphereLens2_Pos_Radius_NB;
    public int uSphereLens_Pos_Radius_NB;

    public GeometryAnimGLWallpaper(ImageGLProgram imageGLProgram) {
        super(imageGLProgram);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper, com.miui.miwallpaper.opengl.ImageGLWallpaper
    public void setupUniforms() {
        super.setupUniforms();
        this.uDistance = this.mProgram.getUniformHandle("uDistance");
        this.uLodThres_Fac_Shadow_Highlight = this.mProgram.getUniformHandle("uLodThres_Fac_Shadow_Highlight");
        this.uPosT = this.mProgram.getUniformHandle("uPosT");
        this.uSphereLens_Pos_Radius_NB = this.mProgram.getUniformHandle("uSphereLens_Pos_Radius_NB");
        this.uSphereLens2_Pos_Radius_NB = this.mProgram.getUniformHandle("uSphereLens2_Pos_Radius_NB");
        this.uCapsuleLens_Pos_cosSin = this.mProgram.getUniformHandle("uCapsuleLens_Pos_cosSin");
        this.uCapsuleLens_Size_NB = this.mProgram.getUniformHandle("uCapsuleLens_Size_NB");
        this.uRoundBoxLensRz_Pos_cosSin = this.mProgram.getUniformHandle("uRoundBoxLensRz_Pos_cosSin");
        this.uRoundBoxLens_Size_NB_R = this.mProgram.getUniformHandle("uRoundBoxLens_Size_NB_R");
        this.uGlassScale_uInnerProgress = this.mProgram.getUniformHandle("uGlassScale_uInnerProgress");
    }
}
