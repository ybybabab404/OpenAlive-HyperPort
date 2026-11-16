package org.aliveclean;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * 小米路径的失败收口：把最近一次失败（操作名 + 堆栈）写进 cache/xiaomi-last-failure.txt 留证据，
 * 并返回最内层 cause 的一句话给界面展示。
 *
 * <p>遍历 cause 链时用 IdentityHashMap 去重，防止自引用的异常链把这里变成死循环；
 * 写文件失败被静默吞掉 —— 诊断信息不能反过来变成新的失败。</p>
 */
final class XiaomiFailure {
    static String describe(Context context, String operation, Throwable error) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(
                new File(context.getCacheDir(), "xiaomi-last-failure.txt")))) {
            out.println(operation);
            error.printStackTrace(out);
        } catch (Exception ignored) {}
        Set<Throwable> visited = Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
        Throwable cause = error;
        while (cause.getCause() != null && visited.add(cause.getCause())) cause = cause.getCause();
        return cause.toString();
    }
}
