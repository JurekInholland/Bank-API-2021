package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Error
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T16:08:07.736Z[GMT]")
public class Error   {
    @Schema(required = true, description = "")@NotNull
    @JsonProperty("code")
    private String code = null;

    @Schema(required = true, description = "")@NotNull
    @JsonProperty("message")
    private String message = null;

    public Error(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }
}
