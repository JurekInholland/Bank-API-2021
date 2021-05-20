package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ModifyTransaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-20T15:39:24.948Z[GMT]")


public class ModifyTransaction   {
  @JsonProperty("fromIban")
  private String fromIban = null;

  @JsonProperty("toIban")
  private String toIban = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("performingUserId")
  private Integer performingUserId = null;

  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

  public ModifyTransaction fromIban(String fromIban) {
    this.fromIban = fromIban;
    return this;
  }

  /**
   * Get fromIban
   * @return fromIban
   **/
  @Schema(description = "")

  public String getFromIban() {
    return fromIban;
  }

  public void setFromIban(String fromIban) {
    this.fromIban = fromIban;
  }

  public ModifyTransaction toIban(String toIban) {
    this.toIban = toIban;
    return this;
  }

  /**
   * Get toIban
   * @return toIban
   **/
  @Schema(description = "")

  public String getToIban() {
    return toIban;
  }

  public void setToIban(String toIban) {
    this.toIban = toIban;
  }

  public ModifyTransaction amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(description = "")

  @Valid
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public ModifyTransaction performingUserId(Integer performingUserId) {
    this.performingUserId = performingUserId;
    return this;
  }

  /**
   * Get performingUserId
   * @return performingUserId
   **/
  @Schema(description = "")

  public Integer getPerformingUserId() {
    return performingUserId;
  }

  public void setPerformingUserId(Integer performingUserId) {
    this.performingUserId = performingUserId;
  }

  public ModifyTransaction timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   **/
  @Schema(description = "")

  @Valid
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModifyTransaction modifyTransaction = (ModifyTransaction) o;
    return Objects.equals(this.fromIban, modifyTransaction.fromIban) &&
            Objects.equals(this.toIban, modifyTransaction.toIban) &&
            Objects.equals(this.amount, modifyTransaction.amount) &&
            Objects.equals(this.performingUserId, modifyTransaction.performingUserId) &&
            Objects.equals(this.timestamp, modifyTransaction.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromIban, toIban, amount, performingUserId, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModifyTransaction {\n");

    sb.append("    fromIban: ").append(toIndentedString(fromIban)).append("\n");
    sb.append("    toIban: ").append(toIndentedString(toIban)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    performingUserId: ").append(toIndentedString(performingUserId)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
