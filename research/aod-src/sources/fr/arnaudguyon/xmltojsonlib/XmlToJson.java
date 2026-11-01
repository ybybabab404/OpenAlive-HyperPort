package fr.arnaudguyon.xmltojsonlib;

import android.util.Log;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.xiaomi.onetrack.util.a;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: loaded from: classes2.dex */
public class XmlToJson {
    public HashMap mAttributeNameReplacements;
    public HashMap mContentNameReplacements;
    public HashMap mForceClassForPath;
    public HashSet mForceListPaths;
    public HashSet mForceListPatterns;
    public String mIndentationPattern;
    public String mInputEncoding;
    public InputStream mInputStreamSource;
    public JSONObject mJsonObject;
    public HashSet mSkippedAttributes;
    public HashSet mSkippedTags;
    public StringReader mStringSource;

    public static class Builder {
        public InputStream mInputStreamSource;
        public StringReader mStringSource;
        public String mInputEncoding = "utf-8";
        public HashSet mForceListPaths = new HashSet();
        public HashSet mForceListPatterns = new HashSet();
        public HashMap mAttributeNameReplacements = new HashMap();
        public HashMap mContentNameReplacements = new HashMap();
        public HashMap mForceClassForPath = new HashMap();
        public HashSet mSkippedAttributes = new HashSet();
        public HashSet mSkippedTags = new HashSet();

        public Builder(String str) {
            this.mStringSource = new StringReader(str);
        }

        public XmlToJson build() {
            return new XmlToJson(this);
        }

        public Builder forceIntegerForPath(String str) {
            this.mForceClassForPath.put(str, Integer.class);
            return this;
        }
    }

    public XmlToJson(Builder builder) {
        this.mIndentationPattern = "   ";
        this.mForceListPatterns = new HashSet();
        this.mSkippedAttributes = new HashSet();
        this.mSkippedTags = new HashSet();
        this.mStringSource = builder.mStringSource;
        this.mInputStreamSource = builder.mInputStreamSource;
        this.mInputEncoding = builder.mInputEncoding;
        this.mForceListPaths = builder.mForceListPaths;
        this.mForceListPatterns = builder.mForceListPatterns;
        this.mAttributeNameReplacements = builder.mAttributeNameReplacements;
        this.mContentNameReplacements = builder.mContentNameReplacements;
        this.mForceClassForPath = builder.mForceClassForPath;
        this.mSkippedAttributes = builder.mSkippedAttributes;
        this.mSkippedTags = builder.mSkippedTags;
        this.mJsonObject = convertToJSONObject();
    }

    public final JSONObject convertTagToJson(Tag tag, boolean z) {
        JSONObject jSONObject = new JSONObject();
        if (tag.getContent() != null) {
            String path = tag.getPath();
            putContent(path, jSONObject, getContentNameReplacement(path, "content"), tag.getContent());
        }
        try {
            for (ArrayList arrayList : tag.getGroupedElements().values()) {
                if (arrayList.size() == 1) {
                    Tag tag2 = (Tag) arrayList.get(0);
                    if (isForcedList(tag2)) {
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.put(convertTagToJson(tag2, true));
                        jSONObject.put(tag2.getName(), jSONArray);
                    } else if (tag2.hasChildren()) {
                        jSONObject.put(tag2.getName(), convertTagToJson(tag2, false));
                    } else {
                        putContent(tag2.getPath(), jSONObject, tag2.getName(), tag2.getContent());
                    }
                } else {
                    JSONArray jSONArray2 = new JSONArray();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        jSONArray2.put(convertTagToJson((Tag) it.next(), true));
                    }
                    jSONObject.put(((Tag) arrayList.get(0)).getName(), jSONArray2);
                }
            }
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final JSONObject convertToJSONObject() {
        try {
            Tag tag = new Tag("", "xml");
            XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
            xmlPullParserFactoryNewInstance.setNamespaceAware(false);
            XmlPullParser xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
            setInput(xmlPullParserNewPullParser);
            for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 0; eventType = xmlPullParserNewPullParser.next()) {
            }
            readTags(tag, xmlPullParserNewPullParser);
            unsetInput();
            return convertTagToJson(tag, false);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String getAttributeNameReplacement(String str, String str2) {
        String str3 = (String) this.mAttributeNameReplacements.get(str);
        return str3 != null ? str3 : str2;
    }

    public final String getContentNameReplacement(String str, String str2) {
        String str3 = (String) this.mContentNameReplacements.get(str);
        return str3 != null ? str3 : str2;
    }

    public final boolean isForcedList(Tag tag) {
        String path = tag.getPath();
        if (this.mForceListPaths.contains(path)) {
            return true;
        }
        Iterator it = this.mForceListPatterns.iterator();
        while (it.hasNext()) {
            if (((Pattern) it.next()).matcher(path).find()) {
                return true;
            }
        }
        return false;
    }

    public final void putContent(String str, JSONObject jSONObject, String str2, String str3) {
        try {
            Class cls = (Class) this.mForceClassForPath.get(str);
            if (cls == null) {
                if (str3 == null) {
                    str3 = "";
                }
                jSONObject.put(str2, str3);
            } else if (cls == Integer.class) {
                try {
                    jSONObject.put(str2, Integer.valueOf(Integer.parseInt(str3)));
                } catch (NumberFormatException unused) {
                    jSONObject.put(str2, 0);
                }
            } else if (cls == Long.class) {
                try {
                    jSONObject.put(str2, Long.valueOf(Long.parseLong(str3)));
                } catch (NumberFormatException unused2) {
                    jSONObject.put(str2, 0L);
                }
            } else if (cls == Double.class) {
                try {
                    jSONObject.put(str2, Double.valueOf(Double.parseDouble(str3)));
                } catch (NumberFormatException unused3) {
                    jSONObject.put(str2, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                }
            } else if (cls == Boolean.class) {
                if (str3 == null) {
                    jSONObject.put(str2, false);
                } else if (str3.equalsIgnoreCase(a.i)) {
                    jSONObject.put(str2, true);
                } else if (str3.equalsIgnoreCase("false")) {
                    jSONObject.put(str2, false);
                } else {
                    jSONObject.put(str2, false);
                }
            }
        } catch (JSONException unused4) {
        }
    }

    public final void readTags(Tag tag, XmlPullParser xmlPullParser) {
        int next;
        do {
            try {
                next = xmlPullParser.next();
                if (next == 2) {
                    String name = xmlPullParser.getName();
                    String str = tag.getPath() + "/" + name;
                    boolean zContains = this.mSkippedTags.contains(str);
                    Tag tag2 = new Tag(str, name);
                    if (!zContains) {
                        tag.addChild(tag2);
                    }
                    int attributeCount = xmlPullParser.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        String attributeName = xmlPullParser.getAttributeName(i);
                        String attributeValue = xmlPullParser.getAttributeValue(i);
                        String str2 = tag.getPath() + "/" + tag2.getName() + "/" + attributeName;
                        if (!this.mSkippedAttributes.contains(str2)) {
                            Tag tag3 = new Tag(str2, getAttributeNameReplacement(str2, attributeName));
                            tag3.setContent(attributeValue);
                            tag2.addChild(tag3);
                        }
                    }
                    readTags(tag2, xmlPullParser);
                } else {
                    if (next != 4) {
                        if (next != 3 && next != 1) {
                            Log.i("XmlToJson", "unknown xml eventType " + next);
                        }
                        return;
                    }
                    tag.setContent(xmlPullParser.getText());
                }
            } catch (IOException | NullPointerException | XmlPullParserException e) {
                e.printStackTrace();
                return;
            }
        } while (next != 1);
    }

    public final void setInput(XmlPullParser xmlPullParser) {
        StringReader stringReader = this.mStringSource;
        if (stringReader != null) {
            try {
                xmlPullParser.setInput(stringReader);
                return;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            xmlPullParser.setInput(this.mInputStreamSource, this.mInputEncoding);
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    public JSONObject toJson() {
        return this.mJsonObject;
    }

    public String toString() {
        JSONObject jSONObject = this.mJsonObject;
        if (jSONObject != null) {
            return jSONObject.toString();
        }
        return null;
    }

    public final void unsetInput() {
        StringReader stringReader = this.mStringSource;
        if (stringReader != null) {
            stringReader.close();
        }
    }
}
