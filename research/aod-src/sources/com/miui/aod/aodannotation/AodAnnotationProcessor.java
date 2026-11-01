package com.miui.aod.aodannotation;

import com.google.auto.service.AutoService;
import com.miui.aod.aodannotation.annotations.FrameRes;
import com.miui.aod.aodannotation.annotations.JsonString;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/* JADX INFO: loaded from: classes.dex */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService
public class AodAnnotationProcessor extends AbstractProcessor {
    public static final Set mSet;
    public static final Map toolsMap;

    public interface annotationProcessor {
    }

    static {
        HashMap map = new HashMap();
        toolsMap = map;
        HashSet hashSet = new HashSet();
        mSet = hashSet;
        map.put(JsonString.class, new annotationProcessor() { // from class: com.miui.aod.aodannotation.AodAnnotationProcessor$$ExternalSyntheticLambda0
        });
        map.put(FrameRes.class, new annotationProcessor() { // from class: com.miui.aod.aodannotation.AodAnnotationProcessor$$ExternalSyntheticLambda0
        });
        hashSet.add(JsonString.class.getCanonicalName());
        hashSet.add(FrameRes.class.getCanonicalName());
    }
}
