package fr.arnaudguyon.xmltojsonlib;

import android.util.Xml;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class JsonToXml {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    public HashSet mForcedAttributes;
    public HashSet mForcedContent;
    public JSONObject mJson;

    public static class Builder {
    }

    public final boolean isAttribute(String str) {
        return this.mForcedAttributes.contains(str);
    }

    public final boolean isContent(String str) {
        return this.mForcedContent.contains(str);
    }

    public final String nodeToXML(Node node) {
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        StringWriter stringWriter = new StringWriter();
        try {
            xmlSerializerNewSerializer.setOutput(stringWriter);
            xmlSerializerNewSerializer.startDocument("UTF-8", Boolean.TRUE);
            nodeToXml(xmlSerializerNewSerializer, node);
            xmlSerializerNewSerializer.endDocument();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final void nodeToXml(XmlSerializer xmlSerializer, Node node) throws IOException {
        String name = node.getName();
        if (name != null) {
            xmlSerializer.startTag("", name);
            for (Node.Attribute attribute : node.getAttributes()) {
                xmlSerializer.attribute("", attribute.mKey, attribute.mValue);
            }
            String content = node.getContent();
            if (content != null) {
                xmlSerializer.text(content);
            }
        }
        Iterator it = node.getChildren().iterator();
        while (it.hasNext()) {
            nodeToXml(xmlSerializer, (Node) it.next());
        }
        if (name != null) {
            xmlSerializer.endTag("", name);
        }
    }

    public final void prepareArray(Node node, String str, JSONArray jSONArray) {
        int length = jSONArray.length();
        String str2 = node.getPath() + "/" + str;
        for (int i = 0; i < length; i++) {
            Node node2 = new Node(str, str2);
            Object objOpt = jSONArray.opt(i);
            if (objOpt != null) {
                if (objOpt instanceof JSONObject) {
                    prepareObject(node2, (JSONObject) objOpt);
                } else if (objOpt instanceof JSONArray) {
                    prepareArray(node2, str, (JSONArray) objOpt);
                } else {
                    String string = objOpt.toString();
                    node2.setName(str);
                    node2.setContent(string);
                }
            }
            node.addChild(node2);
        }
    }

    public final void prepareObject(Node node, JSONObject jSONObject) {
        String string;
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = jSONObject.opt(next);
            if (objOpt != null) {
                if (objOpt instanceof JSONObject) {
                    Node node2 = new Node(next, node.getPath() + "/" + next);
                    node.addChild(node2);
                    prepareObject(node2, (JSONObject) objOpt);
                } else if (objOpt instanceof JSONArray) {
                    prepareArray(node, next, (JSONArray) objOpt);
                } else {
                    String str = node.getPath() + "/" + next;
                    if (objOpt instanceof Double) {
                        double dDoubleValue = ((Double) objOpt).doubleValue();
                        if (dDoubleValue % 1.0d == BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
                            string = Long.toString((long) dDoubleValue);
                        } else {
                            DecimalFormat decimalFormat = DECIMAL_FORMAT;
                            if (decimalFormat.getMaximumFractionDigits() == 0) {
                                decimalFormat.setMaximumFractionDigits(20);
                            }
                            string = decimalFormat.format(dDoubleValue);
                        }
                    } else {
                        string = objOpt.toString();
                    }
                    if (isAttribute(str)) {
                        node.addAttribute(next, string);
                    } else if (isContent(str)) {
                        node.setContent(string);
                    } else {
                        Node node3 = new Node(next, node.getPath());
                        node3.setContent(string);
                        node.addChild(node3);
                    }
                }
            }
        }
    }

    public String toString() {
        Node node = new Node(null, "");
        prepareObject(node, this.mJson);
        return nodeToXML(node);
    }
}
