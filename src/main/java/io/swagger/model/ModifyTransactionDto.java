package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * ModifyTransactionDto
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModifyTransactionDto   {
  @Schema(description = "")
  @JsonProperty("fromIban")
  private String fromIban = null;

  @Schema(description = "")
  @JsonProperty("toIban")
  private String toIban = null;

  @Schema(description = "")@Valid
  @Min(value=0, message = "Amount cannot be negative.")
  @Max(value=1000000, message = "Maximum amount per transaction is 1,000,000 EUR.")
  @Digits(fraction = 2, integer=7, message = "Amount can have at most 2 decimal places.")
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @Schema(description = "")
  @JsonProperty("performingUserId")
  private Integer performingUserId = null;

  @Schema(description = "")@Valid
  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

  //  Check if model is empty
  public boolean isEmpty() {
    if (this.getTimestamp() != null || this.getAmount() != null || this.getFromIban() != null || this.getToIban() != null || this.getPerformingUserId() != null) {
      return false;
    }
    return true;
}
}
