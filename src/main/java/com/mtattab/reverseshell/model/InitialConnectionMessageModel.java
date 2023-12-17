package com.mtattab.reverseshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class InitialConnectionMessageModel {

    private String sessionId;
    private String aes256Key;
    private String initialMessage;

    @JsonCreator
    public InitialConnectionMessageModel(
            @JsonProperty("sessionId") String sessionId,
            @JsonProperty("initialMessage") String initialMessage,
            @JsonProperty("aes256Key") String aes256Key
    ) {
        this.sessionId = sessionId;
        this.initialMessage = initialMessage;
        this.aes256Key = aes256Key;
    }

}
