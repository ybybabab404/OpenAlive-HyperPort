package com.miui.aod;

import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class MemInfoReader {
    public static String getFieldFromMeminfo(String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"));
        Pattern patternCompile = Pattern.compile(str + "\\s*:\\s*(.*)");
        String str2 = "";
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return str2;
                }
                Matcher matcher = patternCompile.matcher(line);
                if (matcher.matches()) {
                    str2 = matcher.group(1).split("[ ]")[0];
                }
            } finally {
                bufferedReader.close();
            }
        }
    }

    public static String getMemTotal() {
        try {
            return getFieldFromMeminfo("MemTotal");
        } catch (IOException e) {
            Log.e("MemInfoReader", "error...", e);
            return null;
        }
    }
}
