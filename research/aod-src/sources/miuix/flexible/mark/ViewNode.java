package miuix.flexible.mark;

import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class ViewNode {
    public int height;
    public int mark;
    public View view;
    public float weight = 0.0f;
    public int width;

    public int getHeight() {
        return this.height;
    }

    public int getMark() {
        return this.mark;
    }

    public View getView() {
        return this.view;
    }

    public float getWeight() {
        return this.weight;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setMark(int i) {
        this.mark = i;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setWeight(float f) {
        this.weight = f;
    }

    public void setWidth(int i) {
        this.width = i;
    }
}
