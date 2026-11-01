package com.miui.miwallpaper.linkage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import miuix.animation.FolmeEase;

/* JADX INFO: loaded from: classes2.dex */
public class AnimationDeserializer implements JsonDeserializer<Animation> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.gson.JsonDeserializer
    public Animation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonElement2 = asJsonObject.get("type");
        if (jsonElement2 == null) {
            return null;
        }
        String asString = jsonElement2.getAsString();
        asString.getClass();
        if (asString.equals(FolmeEase.BEZIER)) {
            return (Animation) jsonDeserializationContext.deserialize(asJsonObject, BezierAnimation.class);
        }
        if (asString.equals(FolmeEase.SPRING)) {
            return (Animation) jsonDeserializationContext.deserialize(asJsonObject, SpringAnimation.class);
        }
        return null;
    }
}
