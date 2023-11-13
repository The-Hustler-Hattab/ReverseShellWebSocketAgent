package com.mtattab.reverseshell.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ReverseShellInfoInitialMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String osName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String osVersion;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String osArch;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userHome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userCurrentWorkingDir;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userLanguage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userPublicIp;

    private String reply;


}
