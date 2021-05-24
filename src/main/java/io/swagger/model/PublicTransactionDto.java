package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * PublicTransactionDto
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")


@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicTransactionDto   {
  @Schema(required = true, description = "")@NotNull
  @JsonProperty("id")
  private Integer id = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("accountFrom")
  private PublicAccountDto accountFrom = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("accountTo")
  private PublicAccountDto accountTo = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("userPerforming")
  private PublicUserDto userPerforming = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

}
