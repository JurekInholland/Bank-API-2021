package io.swagger.model;

import java.math.BigDecimal;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateAccountDto
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")


public class CreateAccountDto {


  @Schema(description = "")
  @JsonProperty("iban")
  private String iban = null;

  @Schema(required = true, description = "")@NotNull
  @JsonProperty("userId")
  private Long userId = null;

  @Schema(required = true, description = "")@NotNull
  @JsonProperty("balance")
  private BigDecimal balance = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("accountType")
  private AccountType accountType = null;

}
