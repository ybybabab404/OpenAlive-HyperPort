package androidx.vectordrawable.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.TypeEvaluator;
import android.content.Context;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.PathParser;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class AnimatorInflaterCompat {

    public static class PathDataEvaluator implements TypeEvaluator<PathParser.PathDataNode[]> {
        public PathParser.PathDataNode[] mNodeArray;

        @Override // android.animation.TypeEvaluator
        public PathParser.PathDataNode[] evaluate(float f, PathParser.PathDataNode[] pathDataNodeArr, PathParser.PathDataNode[] pathDataNodeArr2) {
            if (!PathParser.canMorph(pathDataNodeArr, pathDataNodeArr2)) {
                throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            if (!PathParser.canMorph(this.mNodeArray, pathDataNodeArr)) {
                this.mNodeArray = PathParser.deepCopyNodes(pathDataNodeArr);
            }
            for (int i = 0; i < pathDataNodeArr.length; i++) {
                this.mNodeArray[i].interpolatePathDataNode(pathDataNodeArr[i], pathDataNodeArr2[i], f);
            }
            return this.mNodeArray;
        }
    }

    public static Animator loadAnimator(Context context, int i) {
        return AnimatorInflater.loadAnimator(context, i);
    }
}
