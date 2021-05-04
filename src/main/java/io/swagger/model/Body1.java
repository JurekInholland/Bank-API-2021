package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Body1
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-04T07:44:48.337Z[GMT]")


public class Body1   {
  @JsonProperty("amount_withdraw")
  private Integer amountWithdraw = null;

  @JsonProperty("account_type")
  private String accountType = null;

  public Body1 amountWithdraw(Integer amountWithdraw) {
    this.amountWithdraw = amountWithdraw;
    return this;
  }

  /**
   * Get amountWithdraw
   * @return amountWithdraw
   **/
  @Schema(description = "")
  
    public Integer getAmountWithdraw() {
    return amountWithdraw;
  }

  public void setAmountWithdraw(Integer amountWithdraw) {
    this.amountWithdraw = amountWithdraw;
  }

  public Body1 accountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(description = "")
  
    public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body1 body1 = (Body1) o;
    return Objects.equals(this.amountWithdraw, body1.amountWithdraw) &&
        Objects.equals(this.accountType, body1.accountType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amountWithdraw, accountType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body1 {\n");
    
    sb.append("    amountWithdraw: ").append(toIndentedString(amountWithdraw)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
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
