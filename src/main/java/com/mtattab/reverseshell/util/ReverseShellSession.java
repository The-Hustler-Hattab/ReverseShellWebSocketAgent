package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.model.InitialConnectionMessageModel;
import lombok.Data;

@Data
public class ReverseShellSession {

    private static String sessionId;

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(InitialConnectionMessageModel initialConnectionMessageModel) {

        if (initialConnectionMessageModel != null) {
            ReverseShellSession.sessionId = initialConnectionMessageModel.getSessionId();

        }



    }
}
