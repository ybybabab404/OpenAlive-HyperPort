package com.xiaomi.micloudsdk.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;
import miui.cloud.common.XLogger;

/* JADX INFO: loaded from: classes2.dex */
public class WebViewFileTransferUtils {

    public interface FileChooserCallback {
        void onFileChoose(ValueCallback valueCallback, String str);
    }

    public static class FileUpload {
        public final int fileChooserRequestCode;
        public ValueCallback valueCallback;

        /* JADX INFO: renamed from: com.xiaomi.micloudsdk.utils.WebViewFileTransferUtils$FileUpload$1, reason: invalid class name */
        class AnonymousClass1 implements FileChooserCallback {
            public final /* synthetic */ FileUpload this$0;
            public final /* synthetic */ Activity val$activity;

            @Override // com.xiaomi.micloudsdk.utils.WebViewFileTransferUtils.FileChooserCallback
            public void onFileChoose(ValueCallback valueCallback, String str) {
                ValueCallback valueCallback2 = this.this$0.valueCallback;
                if (valueCallback2 != null) {
                    valueCallback2.onReceiveValue(null);
                }
                this.this$0.valueCallback = valueCallback;
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType(str);
                this.val$activity.startActivityForResult(Intent.createChooser(intent, null), this.this$0.fileChooserRequestCode);
            }
        }
    }

    public static class MIUISysDownloadListener implements DownloadListener {
        public Context mContext;
        public String mDesc;

        @Override // android.webkit.DownloadListener
        public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
            request.setMimeType(str4);
            request.addRequestHeader("Cookie", CookieManager.getInstance().getCookie(str));
            request.addRequestHeader("User-Agent", str2);
            String strGuessFileName = URLUtil.guessFileName(str, str3, str4);
            String str5 = String.format(this.mDesc, strGuessFileName);
            request.setDescription(str5);
            request.setTitle(strGuessFileName);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(str, str3, str4));
            ((DownloadManager) this.mContext.getSystemService("download")).enqueue(request);
            Toast.makeText(this.mContext, str5, 1).show();
            XLogger.logi("WebViewFileTransferUtil", "download file onDownloadStart and url: " + str);
        }
    }

    public static class UploadWebChromeClient extends WebChromeClient {
        public FileChooserCallback mCallback;

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            String str;
            XLogger.logi("WebViewFileTransferUtil", "upload file and file chooser params: " + fileChooserParams.toString());
            if (fileChooserParams.getAcceptTypes() == null || fileChooserParams.getAcceptTypes().length <= 0) {
                str = "*/*";
            } else {
                str = fileChooserParams.getAcceptTypes()[0];
                for (int i = 1; i < fileChooserParams.getAcceptTypes().length; i++) {
                    str = str + " " + fileChooserParams.getAcceptTypes()[i];
                }
            }
            this.mCallback.onFileChoose(valueCallback, str);
            return true;
        }

        public void openFileChooser(ValueCallback valueCallback, String str, String str2) {
            XLogger.logi("WebViewFileTransferUtil", "upload file in openFile Uri Callback has accept Type" + str + "has capture" + str2);
            if (TextUtils.isEmpty(str)) {
                str = "*/*";
            }
            this.mCallback.onFileChoose(valueCallback, str);
        }
    }
}
