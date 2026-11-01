package com.bumptech.glide.load.engine.executor;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
final class RuntimeCompat {

    /* JADX INFO: renamed from: com.bumptech.glide.load.engine.executor.RuntimeCompat$1, reason: invalid class name */
    class AnonymousClass1 implements FilenameFilter {
        public final /* synthetic */ Pattern val$cpuNamePattern;

        @Override // java.io.FilenameFilter
        public boolean accept(File file, String str) {
            return this.val$cpuNamePattern.matcher(str).matches();
        }
    }

    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}
