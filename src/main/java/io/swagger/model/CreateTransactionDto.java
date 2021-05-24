package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * CreateTransactionDto
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransactionDto   {
  @Schema(required = true, description = "")@NotNull
  @JsonProperty("fromIban")
  private String fromIban = null;

  @Schema(required = true, description = "")@NotNull
  @JsonProperty("toIban")
  private String toIban = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @Min(value=0, message = "Amount cannot be negative.")
  @Max(value=1000000, message = "Maximum amount per transaction is 1,000,000 EUR.")
  @Digits(fraction = 2, integer=10, message = "Amount can have at most 2 decimal places.")
  @NotNull(message = "Amount must be specified.")
  @JsonProperty("amount")
  private BigDecimal amount = null;

}
