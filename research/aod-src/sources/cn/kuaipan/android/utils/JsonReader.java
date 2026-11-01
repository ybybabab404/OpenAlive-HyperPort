package cn.kuaipan.android.utils;

import android.text.TextUtils;
import com.miui.maml.R;
import com.xiaomi.onetrack.util.a;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class JsonReader implements Closeable {
    public final Reader in;
    public String name;
    public boolean skipping;
    public JsonToken token;
    public String value;
    public int valueLength;
    public int valuePos;
    public final StringPool stringPool = new StringPool();
    public boolean lenient = false;
    public final char[] buffer = new char[1024];
    public int pos = 0;
    public int limit = 0;
    public int bufferStartLine = 1;
    public int bufferStartColumn = 1;
    public final List stack = new ArrayList();

    /* JADX INFO: renamed from: cn.kuaipan.android.utils.JsonReader$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$cn$kuaipan$android$utils$JsonScope;

        static {
            int[] iArr = new int[JsonScope.values().length];
            $SwitchMap$cn$kuaipan$android$utils$JsonScope = iArr;
            try {
                iArr[JsonScope.EMPTY_DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonScope[JsonScope.EMPTY_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonScope[JsonScope.NONEMPTY_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonScope[JsonScope.EMPTY_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonScope[JsonScope.DANGLING_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonScope[JsonScope.NONEMPTY_OBJECT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonScope[JsonScope.NONEMPTY_DOCUMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$cn$kuaipan$android$utils$JsonScope[JsonScope.CLOSED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public JsonReader(Reader reader) {
        push(JsonScope.EMPTY_DOCUMENT);
        this.skipping = false;
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.in = reader;
    }

    public final JsonToken advance() throws IOException {
        peek();
        JsonToken jsonToken = this.token;
        this.token = null;
        this.value = null;
        this.name = null;
        return jsonToken;
    }

    public void beginArray() throws IOException {
        expect(JsonToken.BEGIN_ARRAY);
    }

    public void beginObject() throws IOException {
        expect(JsonToken.BEGIN_OBJECT);
    }

    public final void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.value = null;
        this.token = null;
        this.stack.clear();
        this.stack.add(JsonScope.CLOSED);
        this.in.close();
    }

    public final JsonToken decodeLiteral() {
        char[] cArr;
        char c;
        char[] cArr2;
        char c2;
        char[] cArr3;
        char c3;
        int i = this.valuePos;
        if (i == -1) {
            return JsonToken.STRING;
        }
        int i2 = this.valueLength;
        if (i2 == 4 && (('n' == (c3 = (cArr3 = this.buffer)[i]) || 'N' == c3) && (('u' == cArr3[i + 1] || 'U' == cArr3[i + 1]) && (('l' == cArr3[i + 2] || 'L' == cArr3[i + 2]) && ('l' == cArr3[i + 3] || 'L' == cArr3[i + 3]))))) {
            this.value = "null";
            return JsonToken.NULL;
        }
        if (i2 == 4 && (('t' == (c2 = (cArr2 = this.buffer)[i]) || 'T' == c2) && (('r' == cArr2[i + 1] || 'R' == cArr2[i + 1]) && (('u' == cArr2[i + 2] || 'U' == cArr2[i + 2]) && ('e' == cArr2[i + 3] || 'E' == cArr2[i + 3]))))) {
            this.value = a.i;
            return JsonToken.BOOLEAN;
        }
        if (i2 == 5 && (('f' == (c = (cArr = this.buffer)[i]) || 'F' == c) && (('a' == cArr[i + 1] || 'A' == cArr[i + 1]) && (('l' == cArr[i + 2] || 'L' == cArr[i + 2]) && (('s' == cArr[i + 3] || 'S' == cArr[i + 3]) && ('e' == cArr[i + 4] || 'E' == cArr[i + 4])))))) {
            this.value = "false";
            return JsonToken.BOOLEAN;
        }
        this.value = this.stringPool.get(this.buffer, i, i2);
        return decodeNumber(this.buffer, this.valuePos, this.valueLength);
    }

    public final JsonToken decodeNumber(char[] cArr, int i, int i2) {
        int i3;
        int i4;
        char c;
        char c2 = cArr[i];
        if (c2 == '-') {
            int i5 = i + 1;
            i3 = i5;
            c2 = cArr[i5];
        } else {
            i3 = i;
        }
        if (c2 == '0') {
            i4 = i3 + 1;
            c = cArr[i4];
        } else {
            if (c2 < '1' || c2 > '9') {
                return JsonToken.STRING;
            }
            i4 = i3 + 1;
            c = cArr[i4];
            while (c >= '0' && c <= '9') {
                i4++;
                c = cArr[i4];
            }
        }
        if (c == '.') {
            i4++;
            c = cArr[i4];
            while (c >= '0' && c <= '9') {
                i4++;
                c = cArr[i4];
            }
        }
        if (c == 'e' || c == 'E') {
            int i6 = i4 + 1;
            char c3 = cArr[i6];
            if (c3 == '+' || c3 == '-') {
                i6 = i4 + 2;
                c3 = cArr[i6];
            }
            if (c3 < '0' || c3 > '9') {
                return JsonToken.STRING;
            }
            int i7 = i6 + 1;
            char c4 = cArr[i7];
            i4 = i7;
            while (c4 >= '0' && c4 <= '9') {
                i4++;
                c4 = cArr[i4];
            }
        }
        return i4 == i + i2 ? JsonToken.NUMBER : JsonToken.STRING;
    }

    public void endArray() throws IOException {
        expect(JsonToken.END_ARRAY);
    }

    public void endObject() throws IOException {
        expect(JsonToken.END_OBJECT);
    }

    public final void expect(JsonToken jsonToken) throws IOException {
        peek();
        if (this.token == jsonToken) {
            advance();
            return;
        }
        throw new IllegalStateException("Expected " + jsonToken + " but was " + peek());
    }

    public final boolean fillBuffer(int i) throws IOException {
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        while (true) {
            i2 = this.pos;
            if (i5 >= i2) {
                break;
            }
            if (this.buffer[i5] == '\n') {
                this.bufferStartLine++;
                this.bufferStartColumn = 1;
            } else {
                this.bufferStartColumn++;
            }
            i5++;
        }
        int i6 = this.limit;
        if (i6 != i2) {
            int i7 = i6 - i2;
            this.limit = i7;
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i2, cArr, 0, i7);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            Reader reader = this.in;
            char[] cArr2 = this.buffer;
            int i8 = this.limit;
            int i9 = reader.read(cArr2, i8, cArr2.length - i8);
            if (i9 == -1) {
                return false;
            }
            i3 = this.limit + i9;
            this.limit = i3;
            if (this.bufferStartLine == 1 && (i4 = this.bufferStartColumn) == 1 && i3 > 0 && this.buffer[0] == 65279) {
                this.pos++;
                this.bufferStartColumn = i4 - 1;
            }
        } while (i3 < i);
        return true;
    }

    public final int getColumnNumber() {
        int i = this.bufferStartColumn;
        for (int i2 = 0; i2 < this.pos; i2++) {
            i = this.buffer[i2] == '\n' ? 1 : i + 1;
        }
        return i;
    }

    public final int getLineNumber() {
        int i = this.bufferStartLine;
        for (int i2 = 0; i2 < this.pos; i2++) {
            if (this.buffer[i2] == '\n') {
                i++;
            }
        }
        return i;
    }

    public final CharSequence getSnippet() {
        StringBuilder sb = new StringBuilder();
        int iMin = Math.min(this.pos, 20);
        sb.append(this.buffer, this.pos - iMin, iMin);
        sb.append(this.buffer, this.pos, Math.min(this.limit - this.pos, 20));
        return sb;
    }

    public boolean nextBoolean() throws IOException {
        peek();
        if (this.token == JsonToken.BOOLEAN) {
            boolean z = this.value == a.i;
            advance();
            return z;
        }
        throw new IllegalStateException("Expected a boolean but was " + this.token);
    }

    public double nextDouble() throws IOException {
        peek();
        JsonToken jsonToken = this.token;
        if (jsonToken == JsonToken.STRING || jsonToken == JsonToken.NUMBER) {
            double d = Double.parseDouble(this.value);
            advance();
            return d;
        }
        throw new IllegalStateException("Expected a double but was " + this.token);
    }

    public final JsonToken nextInArray(boolean z) throws IOException {
        if (z) {
            replaceTop(JsonScope.NONEMPTY_ARRAY);
        } else {
            int iNextNonWhitespace = nextNonWhitespace();
            if (iNextNonWhitespace != 44) {
                if (iNextNonWhitespace != 59) {
                    if (iNextNonWhitespace != 93) {
                        throw syntaxError("Unterminated array");
                    }
                    pop();
                    JsonToken jsonToken = JsonToken.END_ARRAY;
                    this.token = jsonToken;
                    return jsonToken;
                }
                checkLenient();
            }
        }
        int iNextNonWhitespace2 = nextNonWhitespace();
        if (iNextNonWhitespace2 != 44 && iNextNonWhitespace2 != 59) {
            if (iNextNonWhitespace2 != 93) {
                this.pos--;
                return nextValue();
            }
            if (z) {
                pop();
                JsonToken jsonToken2 = JsonToken.END_ARRAY;
                this.token = jsonToken2;
                return jsonToken2;
            }
        }
        checkLenient();
        this.pos--;
        this.value = "null";
        JsonToken jsonToken3 = JsonToken.NULL;
        this.token = jsonToken3;
        return jsonToken3;
    }

    public final JsonToken nextInObject(boolean z) throws IOException {
        if (!z) {
            int iNextNonWhitespace = nextNonWhitespace();
            if (iNextNonWhitespace != 44 && iNextNonWhitespace != 59) {
                if (iNextNonWhitespace != 125) {
                    throw syntaxError("Unterminated object");
                }
                pop();
                JsonToken jsonToken = JsonToken.END_OBJECT;
                this.token = jsonToken;
                return jsonToken;
            }
        } else {
            if (nextNonWhitespace() == 125) {
                pop();
                JsonToken jsonToken2 = JsonToken.END_OBJECT;
                this.token = jsonToken2;
                return jsonToken2;
            }
            this.pos--;
        }
        int iNextNonWhitespace2 = nextNonWhitespace();
        if (iNextNonWhitespace2 == 34) {
            this.name = nextString((char) iNextNonWhitespace2);
        } else if (iNextNonWhitespace2 != 39) {
            checkLenient();
            this.pos--;
            String strNextLiteral = nextLiteral(false);
            this.name = strNextLiteral;
            if (TextUtils.isEmpty(strNextLiteral)) {
                throw syntaxError("Expected name");
            }
        } else {
            checkLenient();
            this.name = nextString((char) iNextNonWhitespace2);
        }
        replaceTop(JsonScope.DANGLING_NAME);
        JsonToken jsonToken3 = JsonToken.NAME;
        this.token = jsonToken3;
        return jsonToken3;
    }

    public int nextInt() throws IOException {
        int i;
        peek();
        JsonToken jsonToken = this.token;
        if (jsonToken != JsonToken.STRING && jsonToken != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected an int but was " + this.token);
        }
        try {
            i = Integer.parseInt(this.value);
        } catch (NumberFormatException unused) {
            double d = Double.parseDouble(this.value);
            int i2 = (int) d;
            if (i2 != d) {
                throw new NumberFormatException(this.value);
            }
            i = i2;
        }
        advance();
        return i;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:31:0x004a. Please report as an issue. */
    public final String nextLiteral(boolean z) throws IOException {
        this.valuePos = -1;
        int i = 0;
        this.valueLength = 0;
        String string = null;
        int i2 = 0;
        StringBuilder sb = null;
        while (true) {
            int i3 = this.pos;
            if (i3 + i2 < this.limit) {
                char c = this.buffer[i3 + i2];
                if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                    if (c != '#') {
                        if (c != ',') {
                            if (c != '/' && c != '=') {
                                if (c != '{' && c != '}' && c != ':') {
                                    if (c != ';') {
                                        switch (c) {
                                            case '[':
                                            case R.styleable.AppCompatTheme_ratingBarStyleIndicator /* 93 */:
                                                break;
                                            case R.styleable.AppCompatTheme_ratingBarStyle /* 92 */:
                                                break;
                                            default:
                                                i2++;
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    checkLenient();
                }
                i = i2;
            } else if (i2 >= this.buffer.length) {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(this.buffer, this.pos, i2);
                this.valueLength += i2;
                this.pos += i2;
                if (fillBuffer(1)) {
                    i2 = 0;
                }
            } else if (!fillBuffer(i2 + 1)) {
                this.buffer[this.limit] = 0;
                i = i2;
            }
            if (z && sb == null) {
                this.valuePos = this.pos;
            } else if (this.skipping) {
                string = "skipped!";
            } else if (sb == null) {
                string = this.stringPool.get(this.buffer, this.pos, i);
            } else {
                sb.append(this.buffer, this.pos, i);
                string = sb.toString();
            }
            this.valueLength += i;
            this.pos += i;
            return string;
        }
    }

    public long nextLong() throws IOException {
        long j;
        peek();
        JsonToken jsonToken = this.token;
        if (jsonToken != JsonToken.STRING && jsonToken != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected a long but was " + this.token);
        }
        try {
            j = Long.parseLong(this.value);
        } catch (NumberFormatException unused) {
            double d = Double.parseDouble(this.value);
            long j2 = (long) d;
            if (j2 != d) {
                throw new NumberFormatException(this.value);
            }
            j = j2;
        }
        advance();
        return j;
    }

    public String nextName() throws IOException {
        peek();
        if (this.token == JsonToken.NAME) {
            String str = this.name;
            advance();
            return str;
        }
        throw new IllegalStateException("Expected a name but was " + peek());
    }

    public final int nextNonWhitespace() throws IOException {
        while (true) {
            if (this.pos >= this.limit && !fillBuffer(1)) {
                throw new EOFException("End of input");
            }
            char[] cArr = this.buffer;
            int i = this.pos;
            int i2 = i + 1;
            this.pos = i2;
            char c = cArr[i];
            if (c != '\t' && c != '\n' && c != '\r' && c != ' ') {
                if (c != '#') {
                    if (c == '/' && (i2 != this.limit || fillBuffer(1))) {
                        checkLenient();
                        char[] cArr2 = this.buffer;
                        int i3 = this.pos;
                        char c2 = cArr2[i3];
                        if (c2 == '*') {
                            this.pos = i3 + 1;
                            if (!skipTo("*/")) {
                                throw syntaxError("Unterminated comment");
                            }
                            this.pos += 2;
                        } else if (c2 == '/') {
                            this.pos = i3 + 1;
                            skipToEndOfLine();
                        }
                    }
                    return c;
                }
                checkLenient();
                skipToEndOfLine();
            }
        }
    }

    public void nextNull() throws IOException {
        peek();
        if (this.token == JsonToken.NULL) {
            advance();
            return;
        }
        throw new IllegalStateException("Expected null but was " + this.token);
    }

    public String nextString() throws IOException {
        peek();
        JsonToken jsonToken = this.token;
        if (jsonToken == JsonToken.STRING || jsonToken == JsonToken.NUMBER) {
            String str = this.value;
            advance();
            return str;
        }
        throw new IllegalStateException("Expected a string but was " + peek());
    }

    public final String nextString(char c) throws IOException {
        StringBuilder sb = null;
        do {
            int i = this.pos;
            while (true) {
                int i2 = this.pos;
                if (i2 < this.limit) {
                    char[] cArr = this.buffer;
                    int i3 = i2 + 1;
                    this.pos = i3;
                    char c2 = cArr[i2];
                    if (c2 == c) {
                        if (this.skipping) {
                            return "skipped!";
                        }
                        if (sb == null) {
                            return this.stringPool.get(cArr, i, (i3 - i) - 1);
                        }
                        sb.append(cArr, i, (i3 - i) - 1);
                        return sb.toString();
                    }
                    if (c2 == '\\') {
                        if (sb == null) {
                            sb = new StringBuilder();
                        }
                        sb.append(this.buffer, i, (this.pos - i) - 1);
                        sb.append(readEscapeCharacter());
                        i = this.pos;
                    }
                }
            }
            if (sb == null) {
                sb = new StringBuilder();
            }
            sb.append(this.buffer, i, this.pos - i);
        } while (fillBuffer(1));
        throw syntaxError("Unterminated string");
    }

    public final JsonToken nextValue() throws IOException {
        int iNextNonWhitespace = nextNonWhitespace();
        if (iNextNonWhitespace != 34) {
            if (iNextNonWhitespace != 39) {
                if (iNextNonWhitespace == 91) {
                    push(JsonScope.EMPTY_ARRAY);
                    JsonToken jsonToken = JsonToken.BEGIN_ARRAY;
                    this.token = jsonToken;
                    return jsonToken;
                }
                if (iNextNonWhitespace != 123) {
                    this.pos--;
                    return readLiteral();
                }
                push(JsonScope.EMPTY_OBJECT);
                JsonToken jsonToken2 = JsonToken.BEGIN_OBJECT;
                this.token = jsonToken2;
                return jsonToken2;
            }
            checkLenient();
        }
        this.value = nextString((char) iNextNonWhitespace);
        JsonToken jsonToken3 = JsonToken.STRING;
        this.token = jsonToken3;
        return jsonToken3;
    }

    public final JsonToken objectValue() throws IOException {
        int iNextNonWhitespace = nextNonWhitespace();
        if (iNextNonWhitespace != 58) {
            if (iNextNonWhitespace != 61) {
                throw syntaxError("Expected ':'");
            }
            checkLenient();
            if (this.pos < this.limit || fillBuffer(1)) {
                char[] cArr = this.buffer;
                int i = this.pos;
                if (cArr[i] == '>') {
                    this.pos = i + 1;
                }
            }
        }
        replaceTop(JsonScope.NONEMPTY_OBJECT);
        return nextValue();
    }

    public JsonToken peek() throws IOException {
        JsonToken jsonToken;
        JsonToken jsonToken2 = this.token;
        if (jsonToken2 != null) {
            return jsonToken2;
        }
        switch (AnonymousClass1.$SwitchMap$cn$kuaipan$android$utils$JsonScope[peekStack().ordinal()]) {
            case 1:
                replaceTop(JsonScope.NONEMPTY_DOCUMENT);
                JsonToken jsonTokenNextValue = nextValue();
                if (this.lenient || (jsonToken = this.token) == JsonToken.BEGIN_ARRAY || jsonToken == JsonToken.BEGIN_OBJECT) {
                    return jsonTokenNextValue;
                }
                throw new IOException("Expected JSON document to start with '[' or '{' but was " + this.token);
            case 2:
                return nextInArray(true);
            case 3:
                return nextInArray(false);
            case 4:
                return nextInObject(true);
            case 5:
                return objectValue();
            case 6:
                return nextInObject(false);
            case 7:
                try {
                    JsonToken jsonTokenNextValue2 = nextValue();
                    if (this.lenient) {
                        return jsonTokenNextValue2;
                    }
                    throw syntaxError("Expected EOF");
                } catch (EOFException unused) {
                    JsonToken jsonToken3 = JsonToken.END_DOCUMENT;
                    this.token = jsonToken3;
                    return jsonToken3;
                }
            case 8:
                throw new IllegalStateException("JsonReader is closed");
            default:
                throw new AssertionError();
        }
    }

    public final JsonScope peekStack() {
        List list = this.stack;
        return (JsonScope) list.get(list.size() - 1);
    }

    public final JsonScope pop() {
        List list = this.stack;
        return (JsonScope) list.remove(list.size() - 1);
    }

    public final void push(JsonScope jsonScope) {
        this.stack.add(jsonScope);
    }

    public final char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i = this.pos;
        this.pos = i + 1;
        char c = cArr[i];
        if (c == 'b') {
            return '\b';
        }
        if (c == 'f') {
            return '\f';
        }
        if (c == 'n') {
            return '\n';
        }
        if (c == 'r') {
            return '\r';
        }
        if (c == 't') {
            return '\t';
        }
        if (c != 'u') {
            return c;
        }
        if (i + 5 > this.limit && !fillBuffer(4)) {
            throw syntaxError("Unterminated escape sequence");
        }
        String str = this.stringPool.get(this.buffer, this.pos, 4);
        this.pos += 4;
        return (char) Integer.parseInt(str, 16);
    }

    public final JsonToken readLiteral() throws IOException {
        this.value = nextLiteral(true);
        if (this.valueLength == 0) {
            throw syntaxError("Expected literal value");
        }
        JsonToken jsonTokenDecodeLiteral = decodeLiteral();
        this.token = jsonTokenDecodeLiteral;
        if (jsonTokenDecodeLiteral == JsonToken.STRING) {
            checkLenient();
        }
        return this.token;
    }

    public final void replaceTop(JsonScope jsonScope) {
        List list = this.stack;
        list.set(list.size() - 1, jsonScope);
    }

    public final boolean skipTo(String str) {
        while (true) {
            if (this.pos + str.length() > this.limit && !fillBuffer(str.length())) {
                return false;
            }
            for (int i = 0; i < str.length(); i++) {
                if (this.buffer[this.pos + i] != str.charAt(i)) {
                    this.pos++;
                }
            }
            return true;
        }
    }

    public final void skipToEndOfLine() {
        char c;
        do {
            if (this.pos >= this.limit && !fillBuffer(1)) {
                return;
            }
            char[] cArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            c = cArr[i];
            if (c == '\r') {
                return;
            }
        } while (c != '\n');
    }

    public final IOException syntaxError(String str) throws MalformedJsonException {
        throw new MalformedJsonException(str + " at line " + getLineNumber() + " column " + getColumnNumber());
    }

    public String toString() {
        return JsonReader.class.getSimpleName() + " near " + ((Object) getSnippet());
    }
}
