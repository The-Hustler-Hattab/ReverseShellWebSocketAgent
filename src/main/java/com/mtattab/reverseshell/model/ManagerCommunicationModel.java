package com.mtattab.reverseshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ManagerCommunicationModel {

    @JsonProperty("masterSessionId")
    private String masterSessionId;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("uuid")
    private String uuid ;

    @JsonCreator
    public ManagerCommunicationModel(
            @JsonProperty("masterSessionId") String masterSessionId,
            @JsonProperty("msg") String msg,
            @JsonProperty("uuid") String uuid,
            @JsonProperty("success") boolean success
    ) {
        this.masterSessionId = masterSessionId;
        this.msg = msg;
        this.success = success;
        this.uuid = uuid;
    }

}