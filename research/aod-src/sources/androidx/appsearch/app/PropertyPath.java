package androidx.appsearch.app;

import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PropertyPath implements Iterable<PathSegment> {
    public final List mPathList;

    public static class PathSegment {
        public final int mPropertyIndex;
        public final String mPropertyName;

        public PathSegment(String str, int i) {
            this.mPropertyName = (String) ObjectsCompat.requireNonNull(str);
            this.mPropertyIndex = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof PathSegment)) {
                return false;
            }
            PathSegment pathSegment = (PathSegment) obj;
            return this.mPropertyIndex == pathSegment.mPropertyIndex && this.mPropertyName.equals(pathSegment.mPropertyName);
        }

        public int getPropertyIndex() {
            return this.mPropertyIndex;
        }

        public String getPropertyName() {
            return this.mPropertyName;
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mPropertyName, Integer.valueOf(this.mPropertyIndex));
        }

        public String toString() {
            if (this.mPropertyIndex == -1) {
                return this.mPropertyName;
            }
            return this.mPropertyName + "[" + this.mPropertyIndex + "]";
        }
    }

    public PropertyPath(String str) {
        Preconditions.checkNotNull(str);
        this.mPathList = new ArrayList();
        try {
            recursivePathScan(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ": " + str);
        }
    }

    public final String consumePropertyWithIndex(String str, int i) {
        Preconditions.checkNotNull(str);
        String strSubstring = str.substring(0, i);
        int iIndexOf = str.indexOf(93, i);
        if (iIndexOf == -1) {
            throw new IllegalArgumentException("Malformed path (no ending ']')");
        }
        int i2 = iIndexOf + 1;
        if (i2 < str.length() && str.charAt(i2) != '.') {
            throw new IllegalArgumentException("Malformed path (']' not followed by '.'): " + str);
        }
        String strSubstring2 = str.substring(i + 1, iIndexOf);
        try {
            int i3 = Integer.parseInt(strSubstring2);
            if (i3 < 0) {
                throw new IllegalArgumentException("Malformed path (path index less than 0)");
            }
            this.mPathList.add(new PathSegment(strSubstring, i3));
            if (i2 < str.length()) {
                return str.substring(iIndexOf + 2);
            }
            return null;
        } catch (NumberFormatException unused) {
            throw new IllegalArgumentException("Malformed path (\"" + strSubstring2 + "\" as path index)");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof PropertyPath)) {
            return ObjectsCompat.equals(this.mPathList, ((PropertyPath) obj).mPathList);
        }
        return false;
    }

    public PathSegment get(int i) {
        return (PathSegment) this.mPathList.get(i);
    }

    public int hashCode() {
        return ObjectsCompat.hashCode(this.mPathList);
    }

    @Override // java.lang.Iterable
    public Iterator<PathSegment> iterator() {
        return this.mPathList.iterator();
    }

    public final void recursivePathScan(String str) {
        boolean z;
        String strConsumePropertyWithIndex;
        int i = 0;
        while (true) {
            if (i >= str.length()) {
                z = false;
                i = -1;
                break;
            }
            char cCharAt = str.charAt(i);
            if (cCharAt == ']') {
                throw new IllegalArgumentException("Malformed path (no starting '[')");
            }
            if (cCharAt == '[' || cCharAt == '.') {
                if (cCharAt != '[') {
                    z = false;
                    break;
                } else {
                    z = true;
                    break;
                }
            }
            i++;
        }
        if (i == 0 || str.isEmpty()) {
            throw new IllegalArgumentException("Malformed path (blank property name)");
        }
        if (i == -1) {
            this.mPathList.add(new PathSegment(str, -1));
            return;
        }
        if (z) {
            strConsumePropertyWithIndex = consumePropertyWithIndex(str, i);
            if (strConsumePropertyWithIndex == null) {
                return;
            }
        } else {
            String strSubstring = str.substring(0, i);
            strConsumePropertyWithIndex = str.substring(i + 1);
            this.mPathList.add(new PathSegment(strSubstring, -1));
        }
        recursivePathScan(strConsumePropertyWithIndex);
    }

    public int size() {
        return this.mPathList.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mPathList.size(); i++) {
            sb.append(get(i).toString());
            if (i < this.mPathList.size() - 1) {
                sb.append('.');
            }
        }
        return sb.toString();
    }
}
