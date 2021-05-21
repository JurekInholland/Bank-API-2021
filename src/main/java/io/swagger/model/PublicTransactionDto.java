package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.PublicAccountDto;
import io.swagger.model.PublicUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PublicTransactionDto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")


public class PublicTransactionDto   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("accountFrom")
  private PublicAccountDto accountFrom = null;

  @JsonProperty("accountTo")
  private PublicAccountDto accountTo = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("userPerforming")
  private PublicUserDto userPerforming = null;

  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

  public PublicTransactionDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PublicTransactionDto accountFrom(PublicAccountDto accountFrom) {
    this.accountFrom = accountFrom;
    return this;
  }

  /**
   * Get accountFrom
   * @return accountFrom
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PublicAccountDto getAccountFrom() {
    return accountFrom;
  }

  public void setAccountFrom(PublicAccountDto accountFrom) {
    this.accountFrom = accountFrom;
  }

  public PublicTransactionDto accountTo(PublicAccountDto accountTo) {
    this.accountTo = accountTo;
    return this;
  }

  /**
   * Get accountTo
   * @return accountTo
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PublicAccountDto getAccountTo() {
    return accountTo;
  }

  public void setAccountTo(PublicAccountDto accountTo) {
    this.accountTo = accountTo;
  }

  public PublicTransactionDto amount(BigDecimal amount) {
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

  public PublicTransactionDto userPerforming(PublicUserDto userPerforming) {
    this.userPerforming = userPerforming;
    return this;
  }

  /**
   * Get userPerforming
   * @return userPerforming
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PublicUserDto getUserPerforming() {
    return userPerforming;
  }

  public void setUserPerforming(PublicUserDto userPerforming) {
    this.userPerforming = userPerforming;
  }

  public PublicTransactionDto timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   **/
  @Schema(required = true, description = "")
      @NotNull

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
    PublicTransactionDto publicTransactionDto = (PublicTransactionDto) o;
    return Objects.equals(this.id, publicTransactionDto.id) &&
        Objects.equals(this.accountFrom, publicTransactionDto.accountFrom) &&
        Objects.equals(this.accountTo, publicTransactionDto.accountTo) &&
        Objects.equals(this.amount, publicTransactionDto.amount) &&
        Objects.equals(this.userPerforming, publicTransactionDto.userPerforming) &&
        Objects.equals(this.timestamp, publicTransactionDto.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountFrom, accountTo, amount, userPerforming, timestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PublicTransactionDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountFrom: ").append(toIndentedString(accountFrom)).append("\n");
    sb.append("    accountTo: ").append(toIndentedString(accountTo)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
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
