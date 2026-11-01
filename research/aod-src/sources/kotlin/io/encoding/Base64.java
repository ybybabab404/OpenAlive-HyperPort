package kotlin.io.encoding;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.AbstractList;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import miuix.animation.internal.TransitionInfo;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: Base64.kt */
/* JADX INFO: loaded from: classes2.dex */
@ExperimentalEncodingApi
@SinceKotlin
@Metadata
public class Base64 {
    public static final Base64 Mime;
    public static final Base64 UrlSafe;
    public final boolean isMimeScheme;
    public final boolean isUrlSafe;
    public final PaddingOption paddingOption;
    public static final Default Default = new Default(null);
    public static final byte[] mimeLineSeparatorSymbols = {13, 10};

    /* JADX INFO: compiled from: Base64.kt */
    @Metadata
    public static final class Default extends Base64 {
        /* JADX WARN: Illegal instructions before constructor call */
        public Default() {
            boolean z = false;
            super(z, z, PaddingOption.PRESENT, null);
        }

        public /* synthetic */ Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final byte[] getMimeLineSeparatorSymbols$kotlin_stdlib() {
            return Base64.mimeLineSeparatorSymbols;
        }
    }

    /* JADX INFO: compiled from: Base64.kt */
    @SinceKotlin
    @Metadata
    public enum PaddingOption {
        PRESENT,
        ABSENT,
        PRESENT_OPTIONAL,
        ABSENT_OPTIONAL;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    static {
        PaddingOption paddingOption = PaddingOption.PRESENT;
        UrlSafe = new Base64(true, false, paddingOption);
        Mime = new Base64(false, true, paddingOption);
    }

    public Base64(boolean z, boolean z2, PaddingOption paddingOption) {
        this.isUrlSafe = z;
        this.isMimeScheme = z2;
        this.paddingOption = paddingOption;
        if (z && z2) {
            throw new IllegalArgumentException("Failed requirement.");
        }
    }

    public /* synthetic */ Base64(boolean z, boolean z2, PaddingOption paddingOption, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, paddingOption);
    }

    public final void checkDestinationBounds(int i, int i2, int i3) {
        if (i2 < 0 || i2 > i) {
            throw new IndexOutOfBoundsException("destination offset: " + i2 + ", destination size: " + i);
        }
        int i4 = i2 + i3;
        if (i4 < 0 || i4 > i) {
            throw new IndexOutOfBoundsException("The destination array does not have enough capacity, destination offset: " + i2 + ", destination size: " + i + ", capacity needed: " + i3);
        }
    }

    public final void checkPaddingIsAllowed(int i) {
        if (this.paddingOption != PaddingOption.ABSENT) {
            return;
        }
        throw new IllegalArgumentException("The padding option is set to ABSENT, but the input has a pad character at index " + i);
    }

    public final void checkSourceBounds$kotlin_stdlib(int i, int i2, int i3) {
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(i2, i3, i);
    }

    public final int decodeImpl(byte[] bArr, byte[] bArr2, int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int[] iArr = this.isUrlSafe ? Base64Kt.base64UrlDecodeMap : Base64Kt.base64DecodeMap;
        int i7 = -8;
        int i8 = i;
        int iHandlePaddingSymbol = i2;
        int i9 = -8;
        int i10 = 0;
        while (true) {
            if (iHandlePaddingSymbol >= i3) {
                i4 = 8;
                i5 = 0;
                break;
            }
            if (i9 != i7 || iHandlePaddingSymbol + 3 >= i3) {
                i4 = 8;
                i6 = 1;
            } else {
                i4 = 8;
                i6 = 1;
                int i11 = iHandlePaddingSymbol + 4;
                int i12 = (iArr[bArr[iHandlePaddingSymbol + 1] & TransitionInfo.INIT] << 12) | (iArr[bArr[iHandlePaddingSymbol] & TransitionInfo.INIT] << 18) | (iArr[bArr[iHandlePaddingSymbol + 2] & TransitionInfo.INIT] << 6) | iArr[bArr[iHandlePaddingSymbol + 3] & TransitionInfo.INIT];
                if (i12 >= 0) {
                    bArr2[i8] = (byte) (i12 >> 16);
                    int i13 = i8 + 2;
                    bArr2[i8 + 1] = (byte) (i12 >> 8);
                    i8 += 3;
                    bArr2[i13] = (byte) i12;
                    iHandlePaddingSymbol = i11;
                }
                i7 = -8;
            }
            int i14 = bArr[iHandlePaddingSymbol] & TransitionInfo.INIT;
            int i15 = iArr[i14];
            if (i15 >= 0) {
                iHandlePaddingSymbol++;
                i10 = (i10 << 6) | i15;
                int i16 = i9 + 6;
                if (i16 >= 0) {
                    bArr2[i8] = (byte) (i10 >>> i16);
                    i10 &= (i6 << i16) - 1;
                    i9 -= 2;
                    i8++;
                } else {
                    i9 = i16;
                }
            } else {
                if (i15 == -2) {
                    iHandlePaddingSymbol = handlePaddingSymbol(bArr, iHandlePaddingSymbol, i3, i9);
                    i5 = i6;
                    break;
                }
                if (!this.isMimeScheme) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid symbol '");
                    sb.append((char) i14);
                    sb.append("'(");
                    String string = Integer.toString(i14, CharsKt.checkRadix(i4));
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    sb.append(string);
                    sb.append(") at index ");
                    sb.append(iHandlePaddingSymbol);
                    throw new IllegalArgumentException(sb.toString());
                }
                iHandlePaddingSymbol++;
            }
            i7 = -8;
        }
        if (i9 == -2) {
            throw new IllegalArgumentException("The last unit of input does not have enough bits");
        }
        if (i9 != -8 && i5 == 0 && this.paddingOption == PaddingOption.PRESENT) {
            throw new IllegalArgumentException("The padding option is set to PRESENT, but the input is not properly padded");
        }
        if (i10 != 0) {
            throw new IllegalArgumentException("The pad bits must be zeros");
        }
        int iSkipIllegalSymbolsIfMime = skipIllegalSymbolsIfMime(bArr, iHandlePaddingSymbol, i3);
        if (iSkipIllegalSymbolsIfMime >= i3) {
            return i8 - i;
        }
        int i17 = bArr[iSkipIllegalSymbolsIfMime] & TransitionInfo.INIT;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Symbol '");
        sb2.append((char) i17);
        sb2.append("'(");
        String string2 = Integer.toString(i17, CharsKt.checkRadix(i4));
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        sb2.append(string2);
        sb2.append(") at index ");
        sb2.append(iSkipIllegalSymbolsIfMime - 1);
        sb2.append(" is prohibited after the pad character");
        throw new IllegalArgumentException(sb2.toString());
    }

    public final int decodeIntoByteArray(byte[] source, byte[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        checkSourceBounds$kotlin_stdlib(source.length, i2, i3);
        checkDestinationBounds(destination.length, i, decodeSize$kotlin_stdlib(source, i2, i3));
        return decodeImpl(source, destination, i, i2, i3);
    }

    public final int decodeSize$kotlin_stdlib(byte[] source, int i, int i2) {
        Intrinsics.checkNotNullParameter(source, "source");
        int i3 = i2 - i;
        if (i3 == 0) {
            return 0;
        }
        if (i3 == 1) {
            throw new IllegalArgumentException("Input should have at least 2 symbols for Base64 decoding, startIndex: " + i + ", endIndex: " + i2);
        }
        if (this.isMimeScheme) {
            while (i < i2) {
                int i4 = Base64Kt.base64DecodeMap[source[i] & TransitionInfo.INIT];
                if (i4 < 0) {
                    if (i4 == -2) {
                        i3 -= i2 - i;
                        break;
                    }
                    i3--;
                }
                i++;
            }
        } else if (source[i2 - 1] == 61) {
            i3 = source[i2 + (-2)] == 61 ? i3 - 2 : i3 - 1;
        }
        return (int) ((((long) i3) * ((long) 6)) / ((long) 8));
    }

    public final int encodeIntoByteArray(byte[] source, byte[] destination, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        return encodeIntoByteArrayImpl$kotlin_stdlib(source, destination, i, i2, i3);
    }

    public final int encodeIntoByteArrayImpl$kotlin_stdlib(byte[] source, byte[] destination, int i, int i2, int i3) {
        int i4 = i2;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        checkSourceBounds$kotlin_stdlib(source.length, i4, i3);
        checkDestinationBounds(destination.length, i, encodeSize$kotlin_stdlib(i3 - i4));
        byte[] bArr = this.isUrlSafe ? Base64Kt.base64UrlEncodeMap : Base64Kt.base64EncodeMap;
        int i5 = this.isMimeScheme ? 19 : Integer.MAX_VALUE;
        int i6 = i;
        while (i4 + 2 < i3) {
            int iMin = Math.min((i3 - i4) / 3, i5);
            for (int i7 = 0; i7 < iMin; i7++) {
                int i8 = source[i4] & TransitionInfo.INIT;
                int i9 = i4 + 2;
                int i10 = source[i4 + 1] & TransitionInfo.INIT;
                i4 += 3;
                int i11 = (i10 << 8) | (i8 << 16) | (source[i9] & TransitionInfo.INIT);
                destination[i6] = bArr[i11 >>> 18];
                destination[i6 + 1] = bArr[(i11 >>> 12) & 63];
                int i12 = i6 + 3;
                destination[i6 + 2] = bArr[(i11 >>> 6) & 63];
                i6 += 4;
                destination[i12] = bArr[i11 & 63];
            }
            if (iMin == i5 && i4 != i3) {
                int i13 = i6 + 1;
                byte[] bArr2 = mimeLineSeparatorSymbols;
                destination[i6] = bArr2[0];
                i6 += 2;
                destination[i13] = bArr2[1];
            }
        }
        int i14 = i3 - i4;
        if (i14 == 1) {
            int i15 = i4 + 1;
            int i16 = (source[i4] & TransitionInfo.INIT) << 4;
            destination[i6] = bArr[i16 >>> 6];
            int i17 = i6 + 2;
            destination[i6 + 1] = bArr[i16 & 63];
            if (shouldPadOnEncode()) {
                int i18 = i6 + 3;
                destination[i17] = 61;
                i6 += 4;
                destination[i18] = 61;
                i4 = i15;
            } else {
                i4 = i15;
                i6 = i17;
            }
        } else if (i14 == 2) {
            int i19 = i4 + 1;
            int i20 = source[i4] & TransitionInfo.INIT;
            i4 += 2;
            int i21 = ((source[i19] & TransitionInfo.INIT) << 2) | (i20 << 10);
            destination[i6] = bArr[i21 >>> 12];
            destination[i6 + 1] = bArr[(i21 >>> 6) & 63];
            int i22 = i6 + 3;
            destination[i6 + 2] = bArr[i21 & 63];
            if (shouldPadOnEncode()) {
                i6 += 4;
                destination[i22] = 61;
            } else {
                i6 = i22;
            }
        }
        if (i4 == i3) {
            return i6 - i;
        }
        throw new IllegalStateException("Check failed.");
    }

    public final int encodeSize$kotlin_stdlib(int i) {
        int i2 = i / 3;
        int i3 = i % 3;
        int i4 = i2 * 4;
        if (i3 != 0) {
            i4 += shouldPadOnEncode() ? 4 : i3 + 1;
        }
        if (this.isMimeScheme) {
            i4 += ((i4 - 1) / 76) * 2;
        }
        if (i4 >= 0) {
            return i4;
        }
        throw new IllegalArgumentException("Input is too big");
    }

    public final int handlePaddingSymbol(byte[] bArr, int i, int i2, int i3) {
        if (i3 == -8) {
            throw new IllegalArgumentException("Redundant pad character at index " + i);
        }
        if (i3 == -6) {
            checkPaddingIsAllowed(i);
            return i + 1;
        }
        if (i3 != -4) {
            if (i3 == -2) {
                return i + 1;
            }
            throw new IllegalStateException("Unreachable");
        }
        checkPaddingIsAllowed(i);
        int iSkipIllegalSymbolsIfMime = skipIllegalSymbolsIfMime(bArr, i + 1, i2);
        if (iSkipIllegalSymbolsIfMime != i2 && bArr[iSkipIllegalSymbolsIfMime] == 61) {
            return iSkipIllegalSymbolsIfMime + 1;
        }
        throw new IllegalArgumentException("Missing one pad character at index " + iSkipIllegalSymbolsIfMime);
    }

    public final boolean isMimeScheme$kotlin_stdlib() {
        return this.isMimeScheme;
    }

    public final boolean shouldPadOnEncode() {
        PaddingOption paddingOption = this.paddingOption;
        return paddingOption == PaddingOption.PRESENT || paddingOption == PaddingOption.PRESENT_OPTIONAL;
    }

    public final int skipIllegalSymbolsIfMime(byte[] bArr, int i, int i2) {
        if (!this.isMimeScheme) {
            return i;
        }
        while (i < i2) {
            if (Base64Kt.base64DecodeMap[bArr[i] & TransitionInfo.INIT] != -1) {
                break;
            }
            i++;
        }
        return i;
    }
}
