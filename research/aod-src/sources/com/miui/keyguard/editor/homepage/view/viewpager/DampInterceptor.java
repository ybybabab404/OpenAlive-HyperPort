package com.miui.keyguard.editor.homepage.view.viewpager;

import android.view.animation.Interpolator;
import miui.maml.data.Expression;

/* JADX INFO: loaded from: classes2.dex */
public class DampInterceptor implements Interpolator {
    public float c;
    public float c2;
    public float k;
    public float mDamping;
    public Expression mDampingExp;
    public float mResponse;
    public Expression mResponseExp;
    public float r;
    public float w;
    public float mInitial = -1.0f;
    public float m = 1.0f;
    public float c1 = -1.0f;
    public boolean mNeedEvaluate = true;

    public DampInterceptor(float f, float f2) {
        this.mDamping = f;
        this.mResponse = f2;
    }

    public final void evaluate() {
        if (this.mNeedEvaluate) {
            double dPow = Math.pow(6.283185307179586d / ((double) this.mResponse), 2.0d);
            float f = this.m;
            float f2 = (float) (dPow * ((double) f));
            this.k = f2;
            float f3 = (float) (((((double) this.mDamping) * 12.566370614359172d) * ((double) f)) / ((double) this.mResponse));
            this.c = f3;
            float fSqrt = (float) Math.sqrt(((f * 4.0f) * f2) - (f3 * f3));
            float f4 = this.m;
            float f5 = fSqrt / (f4 * 2.0f);
            this.w = f5;
            float f6 = -((this.c / 2.0f) * f4);
            this.r = f6;
            this.c2 = (0.0f - (f6 * this.mInitial)) / f5;
            this.mNeedEvaluate = false;
        }
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        Expression expression = this.mDampingExp;
        if (expression != null) {
            float fEvaluate = (float) expression.evaluate();
            if (this.mDamping != fEvaluate) {
                this.mDamping = fEvaluate;
                this.mNeedEvaluate = true;
            }
        }
        Expression expression2 = this.mResponseExp;
        if (expression2 != null) {
            float fEvaluate2 = (float) expression2.evaluate();
            if (this.mResponse != fEvaluate2) {
                this.mResponse = fEvaluate2;
                this.mNeedEvaluate = true;
            }
        }
        evaluate();
        return (float) ((Math.pow(2.718281828459045d, this.r * f) * ((((double) this.c1) * Math.cos(this.w * f)) + (((double) this.c2) * Math.sin(this.w * f)))) + 1.0d);
    }
}
