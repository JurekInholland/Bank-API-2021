package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * UserLogin
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")


public class UserLogin   {
  @Schema(required = true, description = "")@NotNull
  @JsonProperty("email_address")
  private String emailAddress = null;

  @Schema(required = true, description = "")@NotNull
  @JsonProperty("password")
  private String password = null;

}
