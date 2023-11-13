package com.mtattab.reverseshell.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class PublicIpJsonModel {


    @JsonProperty("origin")
    private String origin;

    @JsonCreator
    public PublicIpJsonModel(
            @JsonProperty("origin") String origin
    ) {
        this.origin = origin;

    }
}
