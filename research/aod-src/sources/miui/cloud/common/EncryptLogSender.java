package miui.cloud.common;

import android.util.Base64;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes2.dex */
public class EncryptLogSender implements XLogger.LogSender {
    public static final byte[] DEFAULT_SYM_ENCRYPT_ALGORITHM_IV = "\u0000\u0000\u0000\u0000\u0000\u0000xiaomi.com".getBytes();
    public String mEncryptedSecrectKey;
    public String mKeyLabel;
    public boolean mRandomIv;
    public SecureRandom mRandomIvGenerator;
    public SecretKey mSecretKey;
    public XLogger.LogSender mWrappedLogSender;

    public final String encryptString(String str, byte[] bArr) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            try {
                cipher.init(1, this.mSecretKey, new IvParameterSpec(bArr));
                try {
                    return Base64.encodeToString(cipher.doFinal(str.getBytes()), 2);
                } catch (BadPaddingException e) {
                    throw new RuntimeException("Should never happen. ", e);
                } catch (IllegalBlockSizeException e2) {
                    throw new RuntimeException("Should never happen. ", e2);
                }
            } catch (InvalidAlgorithmParameterException e3) {
                throw new RuntimeException(e3);
            } catch (InvalidKeyException e4) {
                throw new RuntimeException("Should never happen. ", e4);
            }
        } catch (NoSuchAlgorithmException e5) {
            throw new RuntimeException(e5);
        } catch (NoSuchPaddingException e6) {
            throw new RuntimeException(e6);
        }
    }

    public final byte[] generateRandomIv() {
        byte[] bArr = new byte[16];
        this.mRandomIvGenerator.nextBytes(bArr);
        return bArr;
    }

    @Override // miui.cloud.common.XLogger.LogSender
    public void sendLog(int i, String str, String str2) {
        try {
            if (this.mRandomIv) {
                byte[] bArrGenerateRandomIv = generateRandomIv();
                str2 = String.format("#&^%s@%s!%s!%s^&#", this.mEncryptedSecrectKey, Base64.encodeToString(bArrGenerateRandomIv, 2), this.mKeyLabel, encryptString(str2, bArrGenerateRandomIv));
            } else {
                str2 = String.format("#&^%s!%s!%s^&#", this.mEncryptedSecrectKey, this.mKeyLabel, encryptString(str2, DEFAULT_SYM_ENCRYPT_ALGORITHM_IV));
            }
        } catch (Exception e) {
            this.mWrappedLogSender.sendLog(6, getClass().getName(), String.format("Failed to encrypt the message: %s. ", e));
        }
        this.mWrappedLogSender.sendLog(i, str, str2);
    }
}
