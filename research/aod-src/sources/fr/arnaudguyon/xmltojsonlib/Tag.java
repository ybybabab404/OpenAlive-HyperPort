package fr.arnaudguyon.xmltojsonlib;

import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class Tag {
    public ArrayList mChildren = new ArrayList();
    public String mContent;
    public String mName;
    public String mPath;

    public Tag(String str, String str2) {
        this.mPath = str;
        this.mName = str2;
    }

    public void addChild(Tag tag) {
        this.mChildren.add(tag);
    }

    public String getContent() {
        return this.mContent;
    }

    public HashMap getGroupedElements() {
        HashMap map = new HashMap();
        for (Tag tag : this.mChildren) {
            String name = tag.getName();
            ArrayList arrayList = (ArrayList) map.get(name);
            if (arrayList == null) {
                arrayList = new ArrayList();
                map.put(name, arrayList);
            }
            arrayList.add(tag);
        }
        return map;
    }

    public String getName() {
        return this.mName;
    }

    public String getPath() {
        return this.mPath;
    }

    public boolean hasChildren() {
        return this.mChildren.size() > 0;
    }

    public void setContent(String str) {
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char cCharAt = str.charAt(i);
                if (cCharAt != ' ' && cCharAt != '\n') {
                    this.mContent = str;
                    return;
                }
            }
        }
    }

    public String toString() {
        return "Tag: " + this.mName + ", " + this.mChildren.size() + " children, Content: " + this.mContent;
    }
}
