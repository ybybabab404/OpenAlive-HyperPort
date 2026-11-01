package com.miui.maml.util;

import com.miui.aod.components.BaseStyleSelectPresenter;
import com.xiaomi.onetrack.api.a;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.api.bf;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class Task {
    public static String TAG_ACTION = "action";
    public static String TAG_CATEGORY = "category";
    public static String TAG_CLASS = "class";
    public static String TAG_ID = "id";
    public static String TAG_NAME = "name";
    public static String TAG_PACKAGE = "package";
    public static String TAG_TYPE = "type";
    public String action;
    public String category;
    public String className;
    public String id;
    public String name;
    public String packageName;
    public String type;

    public static Task load(Element element) {
        if (element == null) {
            return null;
        }
        Task task = new Task();
        task.id = element.getAttribute("id");
        task.action = element.getAttribute(a.a);
        task.type = element.getAttribute("type");
        task.category = element.getAttribute(BaseStyleSelectPresenter.KEY_CATE);
        task.packageName = element.getAttribute("package");
        task.className = element.getAttribute(am.v);
        task.name = element.getAttribute(bf.a);
        return task;
    }
}
