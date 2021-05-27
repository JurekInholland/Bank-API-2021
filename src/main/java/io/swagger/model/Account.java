package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AccountType;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Account
 */
@Data
@Validated
@Entity
@NoArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-20T14:30:32.476Z[GMT]")


public class Account   {
  @Schema(example = "NLxxINHO0xxxxxxxxx", required = true, description = "")@NotNull
  @Id
  @JsonProperty("iban")
  private String iban = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @OneToOne
  @JsonProperty("user")
  private User user = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("balance")
  private BigDecimal balance = null;

  @Schema(required = true, description = "")@NotNull@Valid
  @JsonProperty("accountType")
  private AccountType accountType = null;

  @Schema(description = "Balance cannot become lower than a certain number defined per account, referred to as absolute limit")@Valid
  @JsonProperty("absoluteLimit")
  private BigDecimal absoluteLimit = new BigDecimal(0);

  public Account ( String iban, User user, BigDecimal balance, AccountType accountType){
    this.iban = iban;
    this.user = user;
    this.balance = balance;
    this.accountType = accountType;

  }

}
