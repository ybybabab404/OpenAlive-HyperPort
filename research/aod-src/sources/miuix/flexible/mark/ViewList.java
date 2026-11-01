package miuix.flexible.mark;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class ViewList extends ViewNode {
    public int orientation = 1;
    public final List list = new ArrayList();

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public List getList() {
        return this.list;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }
}
