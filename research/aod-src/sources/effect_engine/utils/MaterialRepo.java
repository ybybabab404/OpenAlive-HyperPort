package effect_engine.utils;

import java.util.Objects;
import miuix.mgl.Shader;

/* JADX INFO: loaded from: classes2.dex */
public class MaterialRepo {
    public static final float[] VERTEX_POS = {-1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f};
    public static final float[] VERTEX_UV = {0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    public static final float[] VERTEX_UV_RE = {0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f};
    public static final byte[] INDEX = {0, 1, 2, 3, 0, 2};

    /* JADX INFO: renamed from: effect_engine.utils.MaterialRepo$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$miuix$mgl$Shader$ShaderType;

        static {
            int[] iArr = new int[Shader.ShaderType.values().length];
            $SwitchMap$miuix$mgl$Shader$ShaderType = iArr;
            try {
                iArr[Shader.ShaderType.VERTEX_FRAGMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miuix$mgl$Shader$ShaderType[Shader.ShaderType.COMPUTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class MKey {
        public int compId;
        public int fragId;
        public int glassId;
        public Shader.ShaderType type;
        public int vertId;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                MKey mKey = (MKey) obj;
                if (this.vertId == mKey.vertId && this.fragId == mKey.fragId && this.compId == mKey.compId && this.glassId == mKey.glassId && Objects.equals(this.type, mKey.type)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.vertId), Integer.valueOf(this.fragId), Integer.valueOf(this.compId), this.type);
        }
    }
}
