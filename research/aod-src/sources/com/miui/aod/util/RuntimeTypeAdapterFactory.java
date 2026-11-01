package com.miui.aod.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {
    public final Class baseType;
    public final boolean maintainType;
    public boolean recognizeSubtypes;
    public final String typeFieldName;
    public final Map labelToSubtype = new LinkedHashMap();
    public final Map subtypeToLabel = new LinkedHashMap();

    public RuntimeTypeAdapterFactory(Class cls, String str, boolean z) {
        if (str == null || cls == null) {
            throw null;
        }
        this.baseType = cls;
        this.typeFieldName = str;
        this.maintainType = z;
    }

    public static RuntimeTypeAdapterFactory of(Class cls, String str) {
        return new RuntimeTypeAdapterFactory(cls, str, false);
    }

    @Override // com.google.gson.TypeAdapterFactory
    public TypeAdapter create(Gson gson, TypeToken typeToken) {
        if (typeToken == null) {
            return null;
        }
        Class<? super T> rawType = typeToken.getRawType();
        if (!(this.recognizeSubtypes ? this.baseType.isAssignableFrom(rawType) : this.baseType.equals(rawType))) {
            return null;
        }
        final TypeAdapter<T> adapter = gson.getAdapter(JsonElement.class);
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Map.Entry entry : this.labelToSubtype.entrySet()) {
            TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, TypeToken.get((Class) entry.getValue()));
            linkedHashMap.put((String) entry.getKey(), delegateAdapter);
            linkedHashMap2.put((Class) entry.getValue(), delegateAdapter);
        }
        return new TypeAdapter<Object>() { // from class: com.miui.aod.util.RuntimeTypeAdapterFactory.1
            @Override // com.google.gson.TypeAdapter
            /* JADX INFO: renamed from: read */
            public Object read2(JsonReader jsonReader) {
                JsonElement jsonElement = (JsonElement) adapter.read2(jsonReader);
                JsonElement jsonElementRemove = RuntimeTypeAdapterFactory.this.maintainType ? jsonElement.getAsJsonObject().get(RuntimeTypeAdapterFactory.this.typeFieldName) : jsonElement.getAsJsonObject().remove(RuntimeTypeAdapterFactory.this.typeFieldName);
                if (jsonElementRemove == null) {
                    throw new JsonParseException("cannot deserialize " + RuntimeTypeAdapterFactory.this.baseType + " because it does not define a field named " + RuntimeTypeAdapterFactory.this.typeFieldName);
                }
                String asString = jsonElementRemove.getAsString();
                TypeAdapter typeAdapter = (TypeAdapter) linkedHashMap.get(asString);
                if (typeAdapter != null) {
                    return typeAdapter.fromJsonTree(jsonElement);
                }
                throw new JsonParseException("cannot deserialize " + RuntimeTypeAdapterFactory.this.baseType + " subtype named " + asString + "; did you forget to register a subtype?");
            }

            @Override // com.google.gson.TypeAdapter
            public void write(JsonWriter jsonWriter, Object obj) throws IOException {
                Class<?> cls = obj.getClass();
                String str = (String) RuntimeTypeAdapterFactory.this.subtypeToLabel.get(cls);
                TypeAdapter typeAdapter = (TypeAdapter) linkedHashMap2.get(cls);
                if (typeAdapter == null) {
                    throw new JsonParseException("cannot serialize " + cls.getName() + "; did you forget to register a subtype?");
                }
                JsonObject asJsonObject = typeAdapter.toJsonTree(obj).getAsJsonObject();
                if (RuntimeTypeAdapterFactory.this.maintainType) {
                    adapter.write(jsonWriter, asJsonObject);
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                if (asJsonObject.has(RuntimeTypeAdapterFactory.this.typeFieldName)) {
                    throw new JsonParseException("cannot serialize " + cls.getName() + " because it already defines a field named " + RuntimeTypeAdapterFactory.this.typeFieldName);
                }
                jsonObject.add(RuntimeTypeAdapterFactory.this.typeFieldName, new JsonPrimitive(str));
                for (Map.Entry<String, JsonElement> entry2 : asJsonObject.entrySet()) {
                    jsonObject.add(entry2.getKey(), entry2.getValue());
                }
                adapter.write(jsonWriter, jsonObject);
            }
        }.nullSafe();
    }

    public RuntimeTypeAdapterFactory registerSubtype(Class cls, String str) {
        if (cls == null || str == null) {
            throw null;
        }
        if (this.subtypeToLabel.containsKey(cls) || this.labelToSubtype.containsKey(str)) {
            throw new IllegalArgumentException("types and labels must be unique");
        }
        this.labelToSubtype.put(str, cls);
        this.subtypeToLabel.put(cls, str);
        return this;
    }
}
