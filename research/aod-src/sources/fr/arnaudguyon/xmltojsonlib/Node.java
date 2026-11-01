package fr.arnaudguyon.xmltojsonlib;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
class Node {
    public ArrayList mAttributes = new ArrayList();
    public ArrayList mChildren = new ArrayList();
    public String mContent;
    public String mName;
    public String mPath;

    public class Attribute {
        public String mKey;
        public String mValue;

        public Attribute(String str, String str2) {
            this.mKey = str;
            this.mValue = str2;
        }
    }

    public Node(String str, String str2) {
        this.mName = str;
        this.mPath = str2;
    }

    public void addAttribute(String str, String str2) {
        this.mAttributes.add(new Attribute(str, str2));
    }

    public void addChild(Node node) {
        this.mChildren.add(node);
    }

    public ArrayList getAttributes() {
        return this.mAttributes;
    }

    public ArrayList getChildren() {
        return this.mChildren;
    }

    public String getContent() {
        return this.mContent;
    }

    public String getName() {
        return this.mName;
    }

    public String getPath() {
        return this.mPath;
    }

    public void setContent(String str) {
        this.mContent = str;
    }

    public void setName(String str) {
        this.mName = str;
    }
}
