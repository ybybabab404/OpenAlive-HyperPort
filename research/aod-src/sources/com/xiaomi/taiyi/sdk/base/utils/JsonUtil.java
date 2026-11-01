package com.xiaomi.taiyi.sdk.base.utils;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class JsonUtil {
    public static final JsonUtil INSTANCE = new JsonUtil();
    public static final Gson gson = new Gson();
    public static final Gson printGson = new GsonBuilder().setPrettyPrinting().create();

    @JvmStatic
    public static final <T> T fromFile(String filePath, Type type) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(type, "type");
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return (T) fromJson(FilesKt.readText$default(file, null, 1, null), type);
            }
            SdkLog.e("JsonUtil", "fromFile " + filePath + " file not exist!");
            return null;
        } catch (IOException e) {
            SdkLog.eMask("JsonUtil", "fromFile " + filePath + " IOException! " + e.getMessage());
            return null;
        } catch (Exception e2) {
            SdkLog.eMask("JsonUtil", "fromFile " + filePath + " Exception! " + e2.getMessage());
            return null;
        }
    }

    @JvmStatic
    public static final <T> T fromJson(String json, Class<T> clazz) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        try {
            return (T) gson.fromJson(json, (Class) clazz);
        } catch (JsonIOException e) {
            SdkLog.e("JsonUtil", "fromJson " + json + " JsonIOException! " + e.getMessage());
            return null;
        } catch (JsonSyntaxException e2) {
            SdkLog.e("JsonUtil", "fromJson " + json + " JsonSyntaxException! " + e2.getMessage());
            return null;
        } catch (Exception e3) {
            SdkLog.e("JsonUtil", "fromJson " + json + " Exception! " + e3.getMessage());
            return null;
        }
    }

    @JvmStatic
    public static final <T> T fromJson(String json, Type type) {
        Intrinsics.checkNotNullParameter(json, "json");
        Intrinsics.checkNotNullParameter(type, "type");
        try {
            return (T) gson.fromJson(json, type);
        } catch (JsonIOException e) {
            SdkLog.e("JsonUtil", "fromJson " + json + " JsonIOException! " + e.getMessage());
            return null;
        } catch (JsonSyntaxException e2) {
            SdkLog.e("JsonUtil", "fromJson " + json + " JsonSyntaxException! " + e2.getMessage());
            return null;
        } catch (Exception e3) {
            SdkLog.e("JsonUtil", "fromJson " + json + " Exception! " + e3.getMessage());
            return null;
        }
    }

    @JvmStatic
    public static final <T> T fromStream(InputStream input, Type type) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(type, "type");
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(input, Charsets.UTF_8);
            try {
                Gson gson2 = gson;
                JsonReader jsonReaderNewJsonReader = gson2.newJsonReader(inputStreamReader);
                try {
                    T t = (T) gson2.fromJson(jsonReaderNewJsonReader, type);
                    CloseableKt.closeFinally(jsonReaderNewJsonReader, null);
                    CloseableKt.closeFinally(inputStreamReader, null);
                    return t;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(jsonReaderNewJsonReader, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(inputStreamReader, th3);
                    throw th4;
                }
            }
        } catch (JsonParseException e) {
            SdkLog.e("JsonUtil", "fromStream JsonParseException! " + e.getMessage());
            return null;
        } catch (Exception e2) {
            SdkLog.e("JsonUtil", "fromStream Exception! " + e2.getMessage());
            return null;
        }
    }

    @JvmStatic
    public static final String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return gson.toJson(obj);
        } catch (JsonIOException e) {
            SdkLog.e("JsonUtil", "toJson " + obj + " JsonIOException! " + e.getMessage());
            return "";
        }
    }

    @JvmStatic
    public static final String toPrettyJson(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return printGson.toJson(obj);
        } catch (JsonIOException e) {
            SdkLog.e("JsonUtil", "toPrettyJson " + obj + " JsonIOException! " + e.getMessage());
            return "";
        }
    }

    public final <T> T fromAssets(Context context, String name, Type type) throws IOException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        InputStream inputStreamOpen = context.getAssets().open(name);
        try {
            Intrinsics.checkNotNull(inputStreamOpen);
            T t = (T) fromStream(inputStreamOpen, type);
            CloseableKt.closeFinally(inputStreamOpen, null);
            return t;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(inputStreamOpen, th);
                throw th2;
            }
        }
    }

    public final boolean toFile(Object obj, String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        if (obj == null) {
            return false;
        }
        String prettyJson = toPrettyJson(obj);
        if (prettyJson.length() == 0) {
            SdkLog.e("JsonUtil", "toFile " + obj + " json is empty!");
            return false;
        }
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                try {
                    outputStreamWriter.write(prettyJson);
                    outputStreamWriter.flush();
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(outputStreamWriter, null);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    return true;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(outputStreamWriter, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(fileOutputStream, th3);
                    throw th4;
                }
            }
        } catch (IOException e) {
            SdkLog.eMask("JsonUtil", "toFile " + obj + " -> " + filePath + " IOException! " + e.getMessage());
            return false;
        } catch (SecurityException e2) {
            SdkLog.eMask("JsonUtil", "toFile " + obj + " -> " + filePath + " SecurityException! " + e2.getMessage());
            return false;
        } catch (Exception e3) {
            SdkLog.eMask("JsonUtil", "toFile " + obj + " -> " + filePath + " Exception! " + e3.getMessage());
            return false;
        }
    }
}
