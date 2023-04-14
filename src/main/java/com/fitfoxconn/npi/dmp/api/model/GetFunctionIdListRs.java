package com.fitfoxconn.npi.dmp.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetFunctionIdListRs {
    @Schema(description = "中文名稱", example = "1F裝配")
    @JsonProperty("Title")
    private String title;
    @Schema(description = "ID", example = "1130")
    @JsonProperty("ID")
    private long id;
}
