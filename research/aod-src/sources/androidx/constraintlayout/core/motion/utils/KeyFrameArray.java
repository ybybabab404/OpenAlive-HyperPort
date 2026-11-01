package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;

/* JADX INFO: loaded from: classes.dex */
public class KeyFrameArray {

    public static class CustomArray {
        public int count;
        public int[] keys;
        public CustomAttribute[] values;

        public int keyAt(int i) {
            return this.keys[i];
        }

        public int size() {
            return this.count;
        }

        public CustomAttribute valueAt(int i) {
            return this.values[this.keys[i]];
        }
    }

    public static class CustomVar {
        public int count;
        public int[] keys;
        public CustomVariable[] values;

        public int keyAt(int i) {
            return this.keys[i];
        }

        public int size() {
            return this.count;
        }

        public CustomVariable valueAt(int i) {
            return this.values[this.keys[i]];
        }
    }

    public static class FloatArray {
    }
}
