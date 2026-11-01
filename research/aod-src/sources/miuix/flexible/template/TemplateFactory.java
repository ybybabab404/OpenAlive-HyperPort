package miuix.flexible.template;

import android.content.Context;
import android.text.TextUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class TemplateFactory {
    public static final HashMap TEMPLATE_MAP;

    static {
        HashMap map = new HashMap();
        TEMPLATE_MAP = map;
        map.put("mark", SimpleMarkTemplate.class);
        map.put("message", MessageTemplate.class);
        map.put("standard", StandardTemplate.class);
        map.put("standard_button", StandardButtonTemplate.class);
        map.put("notification", NotificationTemplate.class);
        map.put("head_button", HeadButtonTemplate.class);
        map.put("infoOperation", InfoOperationMarkTemplate.class);
        map.put("summary", SummaryMarkTemplate.class);
        map.put("simpleItem", SimpleItemMarkTemplate.class);
        map.put("settingInfo", SettingInfoMarkTemplate.class);
        map.put("ternary", TernaryLayoutTemplate.class);
    }

    public static IHyperCellTemplate get(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            Class clsAsSubclass = (Class) TEMPLATE_MAP.get(str);
            if (clsAsSubclass == null) {
                clsAsSubclass = context.getClassLoader().loadClass(str).asSubclass(IHyperCellTemplate.class);
            }
            Constructor constructor = clsAsSubclass.getConstructor(new Class[0]);
            constructor.setAccessible(true);
            return (IHyperCellTemplate) constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Can't find template: " + str, e);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Can't access non-public constructor " + str, e2);
        } catch (InstantiationException | InvocationTargetException e3) {
            throw new IllegalStateException("Could not instantiate the template: " + str, e3);
        } catch (NoSuchMethodException e4) {
            throw new IllegalStateException("Error creating template " + str, e4);
        }
    }

    public static void registerTemplate(String str, Class cls) {
        HashMap map = TEMPLATE_MAP;
        if (!map.containsKey(str)) {
            map.put(str, cls);
            return;
        }
        throw new IllegalArgumentException("Template name '" + str + "' has been registered! Please do not register repeatedly.");
    }
}
