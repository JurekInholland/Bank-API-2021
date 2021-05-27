package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * ModifyAccountDto
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")


public class ModifyAccountDto   {

  @Schema(description = "")@Valid
  @JsonProperty("accountType")
  private AccountType accountType = null;

}
