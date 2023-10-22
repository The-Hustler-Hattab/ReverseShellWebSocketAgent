package com.mtattab.reverseshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class ManagerCommunicationModel {

    @JsonProperty("masterSessionId")
    private String masterSessionId;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("success")
    private boolean success;

    @JsonCreator
    public ManagerCommunicationModel(
            @JsonProperty("masterSessionId") String masterSessionId,
            @JsonProperty("msg") String msg,
            @JsonProperty("success") boolean success
    ) {
        this.masterSessionId = masterSessionId;
        this.msg = msg;
        this.success = success;
    }

}