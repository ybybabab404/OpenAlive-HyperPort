package com.miui.miwallpaper.opengl.moru;

import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class MoruGlassFolmeParam {

    public class CORRUGATION extends MoruGlassFolme {
        public CORRUGATION() {
            super();
            AnimConfig animConfig = new AnimConfig();
            this.lightScreenConfig = animConfig;
            animConfig.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 2.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            AnimConfig animConfig2 = new AnimConfig();
            this.showConfig = animConfig2;
            animConfig2.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.65d), 0.4f);
            AnimConfig animConfig3 = new AnimConfig();
            this.hideConfig = animConfig3;
            animConfig3.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 0.95d, 0.43d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 0.95d, 0.23d), new float[0]);
            AnimConfig animConfig4 = new AnimConfig();
            this.nextConfig = animConfig4;
            animConfig4.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]);
            AnimConfig animConfig5 = new AnimConfig();
            this.fastConfig = animConfig5;
            animConfig5.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.4d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            this.animScreenOnStartState = new AnimState().add("uNormalOffsetX", -0.6f).add("progress", 1.0f);
            this.animScreenOnEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(4).offsetX).add("progress", 1.0f);
            this.animShowStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(4).offsetX).add("progress", 0.0f);
            this.animShowEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(4).offsetX + 0.335f).add("progress", 1.0f);
            this.animHideEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(4).offsetX + 0.2f).add("progress", 0.0f);
            this.animNextEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(4).offsetX + 0.335f).add("progress", 1.0f);
            this.animFastStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(4).offsetX).add("progress", 1.0f);
            this.animFastEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(4).offsetX + 0.335f).add("progress", 1.0f);
        }
    }

    public abstract class MoruGlassFolme {
        public AnimState animFastEndState;
        public AnimState animFastStartState;
        public AnimState animHideEndState;
        public AnimState animNextEndState;
        public AnimState animScreenOnEndState;
        public AnimState animScreenOnStartState;
        public AnimState animShowEndState;
        public AnimState animShowStartState;
        public AnimConfig fastConfig;
        public AnimConfig hideConfig;
        public AnimConfig lightScreenConfig;
        public AnimConfig nextConfig;
        public AnimConfig showConfig;

        public MoruGlassFolme() {
        }
    }

    public class PINSTRIPE extends MoruGlassFolme {
        public PINSTRIPE() {
            super();
            AnimConfig animConfig = new AnimConfig();
            this.lightScreenConfig = animConfig;
            animConfig.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 2.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            AnimConfig animConfig2 = new AnimConfig();
            this.showConfig = animConfig2;
            animConfig2.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.65d), 0.4f);
            AnimConfig animConfig3 = new AnimConfig();
            this.hideConfig = animConfig3;
            animConfig3.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 0.95d, 0.43d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 0.95d, 0.23d), new float[0]);
            AnimConfig animConfig4 = new AnimConfig();
            this.nextConfig = animConfig4;
            animConfig4.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]);
            AnimConfig animConfig5 = new AnimConfig();
            this.fastConfig = animConfig5;
            animConfig5.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.4d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            this.animScreenOnStartState = new AnimState().add("uNormalOffsetX", -0.6f).add("progress", 1.0f);
            this.animScreenOnEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(0).offsetX).add("progress", 1.0f);
            this.animShowStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(0).offsetX).add("progress", 0.0f);
            this.animShowEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(0).offsetX + 0.4f).add("progress", 1.0f);
            this.animHideEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(0).offsetX + 0.2f).add("progress", 0.0f);
            this.animFastStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(0).offsetX).add("progress", 1.0f);
            this.animFastEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(0).offsetX + 0.4f).add("progress", 1.0f);
            this.animNextEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(0).offsetX + 0.4f).add("progress", 1.0f);
        }
    }

    public class PRISM extends MoruGlassFolme {
        public PRISM() {
            super();
            AnimConfig animConfig = new AnimConfig();
            this.lightScreenConfig = animConfig;
            animConfig.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.6d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            AnimConfig animConfig2 = new AnimConfig();
            this.showConfig = animConfig2;
            animConfig2.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.65d), 0.4f);
            AnimConfig animConfig3 = new AnimConfig();
            this.hideConfig = animConfig3;
            animConfig3.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 0.95d, 0.43d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 0.95d, 0.23d), new float[0]);
            AnimConfig animConfig4 = new AnimConfig();
            this.nextConfig = animConfig4;
            animConfig4.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]);
            AnimConfig animConfig5 = new AnimConfig();
            this.fastConfig = animConfig5;
            animConfig5.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.4d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            this.animScreenOnStartState = new AnimState().add("uNormalOffsetX", -0.5f).add("progress", 1.0f);
            this.animScreenOnEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(2).offsetX).add("progress", 1.0f);
            this.animShowStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(2).offsetX).add("progress", 0.0f);
            this.animShowEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(2).offsetX + 0.5f).add("progress", 1.0f);
            this.animHideEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(2).offsetX + 0.2f).add("progress", 0.0f);
            this.animNextEndState = new AnimState().add("uNormalOffsetX", ((double) MoruGlassParam.getValueByIndex(2).offsetX) + 0.5d).add("progress", 1.0f);
            this.animFastStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(2).offsetX).add("progress", 1.0f);
            this.animFastEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(2).offsetX + 0.5f).add("progress", 1.0f);
        }
    }

    public class THICK_STRIPE extends MoruGlassFolme {
        public THICK_STRIPE() {
            super();
            AnimConfig animConfig = new AnimConfig();
            this.lightScreenConfig = animConfig;
            animConfig.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 2.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            AnimConfig animConfig2 = new AnimConfig();
            this.showConfig = animConfig2;
            animConfig2.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.65d), 0.4f);
            AnimConfig animConfig3 = new AnimConfig();
            this.hideConfig = animConfig3;
            animConfig3.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 0.95d, 0.43d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 0.95d, 0.23d), new float[0]);
            AnimConfig animConfig4 = new AnimConfig();
            this.nextConfig = animConfig4;
            animConfig4.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]);
            AnimConfig animConfig5 = new AnimConfig();
            this.fastConfig = animConfig5;
            animConfig5.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.4d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            this.animScreenOnStartState = new AnimState().add("uNormalOffsetX", -0.67f).add("progress", 1.0f);
            this.animScreenOnEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(1).offsetX).add("progress", 1.0f);
            this.animShowStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(1).offsetX).add("progress", 0.0f);
            this.animShowEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(1).offsetX + 0.335f).add("progress", 1.0f);
            this.animHideEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(1).offsetX + 0.2f).add("progress", 0.0f);
            this.animNextEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(1).offsetX + 0.335f).add("progress", 1.0f);
            this.animFastStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(1).offsetX).add("progress", 1.0f);
            this.animFastEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(1).offsetX + 0.335f).add("progress", 1.0f);
        }
    }

    public class THIN_LINE extends MoruGlassFolme {
        public THIN_LINE() {
            super();
            AnimConfig animConfig = new AnimConfig();
            this.lightScreenConfig = animConfig;
            animConfig.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 2.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            AnimConfig animConfig2 = new AnimConfig();
            this.showConfig = animConfig2;
            animConfig2.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.65d), 0.4f);
            AnimConfig animConfig3 = new AnimConfig();
            this.hideConfig = animConfig3;
            animConfig3.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 0.95d, 0.43d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 0.95d, 0.23d), new float[0]);
            AnimConfig animConfig4 = new AnimConfig();
            this.nextConfig = animConfig4;
            animConfig4.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 1.0d), new float[0]);
            AnimConfig animConfig5 = new AnimConfig();
            this.fastConfig = animConfig5;
            animConfig5.setSpecial("uNormalOffsetX", EaseManager.getStyle(-2, 1.0d, 1.4d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0d, 0.35d), new float[0]);
            this.animScreenOnStartState = new AnimState().add("uNormalOffsetX", -0.6f).add("progress", 1.0f);
            this.animScreenOnEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(3).offsetX).add("progress", 1.0f);
            this.animShowStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(3).offsetX).add("progress", 0.0f);
            this.animShowEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(3).offsetX + 0.335f).add("progress", 1.0f);
            this.animHideEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(3).offsetX + 0.2f).add("progress", 0.0f);
            this.animNextEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(3).offsetX + 0.335f).add("progress", 1.0f);
            this.animFastStartState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(3).offsetX).add("progress", 1.0f);
            this.animFastEndState = new AnimState().add("uNormalOffsetX", MoruGlassParam.getValueByIndex(3).offsetX + 0.335f).add("progress", 1.0f);
        }
    }

    public MoruGlassFolme getMoruGlassFolmeParamByGlassId(int i) {
        if (i == 1) {
            return new THICK_STRIPE();
        }
        if (i == 2) {
            return new PRISM();
        }
        if (i != 3) {
            return i != 4 ? new PINSTRIPE() : new CORRUGATION();
        }
        return new THIN_LINE();
    }
}
