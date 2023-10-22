package com.mtattab.reverseshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class InitialConnectionMessageModel {

    private String sessionId;
    private String initialMessage;

    @JsonCreator
    public InitialConnectionMessageModel(
            @JsonProperty("sessionId") String sessionId,
            @JsonProperty("initialMessage") String initialMessage
    ) {
        this.sessionId = sessionId;
        this.initialMessage = initialMessage;
    }

}
