package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateTransactionDto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")


public class CreateTransactionDto   {
  @JsonProperty("fromIban")
  private String fromIban = null;

  @JsonProperty("toIban")
  private String toIban = null;

  @Min(value=0, message = "Amount cannot be negative.")
  @Max(value=1000000, message = "Maximum amount per transaction is 1,000,000 usd.")
  @Digits(fraction = 2, integer=10, message = "Amount can have at most 2 decimal places.")
  @NotNull(message = "Amount must be specified.")
  @JsonProperty("amount")
  private BigDecimal amount = null;

  public CreateTransactionDto fromIban(String fromIban) {
    this.fromIban = fromIban;
    return this;
  }

  /**
   * Get fromIban
   * @return fromIban
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getFromIban() {
    return fromIban;
  }

  public void setFromIban(String fromIban) {
    this.fromIban = fromIban;
  }

  public CreateTransactionDto toIban(String toIban) {
    this.toIban = toIban;
    return this;
  }

  /**
   * Get toIban
   * @return toIban
   **/
  @Schema(required = true, description = "")
      @NotNull

    public String getToIban() {
    return toIban;
  }

  public void setToIban(String toIban) {
    this.toIban = toIban;
  }

  public CreateTransactionDto amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTransactionDto createTransactionDto = (CreateTransactionDto) o;
    return Objects.equals(this.fromIban, createTransactionDto.fromIban) &&
        Objects.equals(this.toIban, createTransactionDto.toIban) &&
        Objects.equals(this.amount, createTransactionDto.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromIban, toIban, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTransactionDto {\n");
    
    sb.append("    fromIban: ").append(toIndentedString(fromIban)).append("\n");
    sb.append("    toIban: ").append(toIndentedString(toIban)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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
