package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Transaction
 */
@Validated
@Entity
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@SequenceGenerator(name = "t_seq", initialValue = 1)


public class Transaction   {
  @Id
  @JsonProperty("id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_seq")
  private Integer id = null;

  @OneToOne
  @JsonProperty("accountFrom")
  private Account accountFrom = null;

  @OneToOne
  @JsonProperty("accountTo")
  private Account accountTo = null;

  @OneToOne
  @JsonProperty("userPerforming")
  private User userPerforming = null;

  @JsonProperty("timestamp")
  private OffsetDateTime timestamp = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  public Transaction id(Integer id) {
    this.id = id;
    return this;
  }

  public void execute() {
    if (this.getTimestamp() != null) {
      System.out.println("TRANSACTION IS EXECUTED TWICE!");
      return;
    }
    this.getAccountFrom().setBalance(this.getAccountFrom().getBalance().subtract(this.amount));
    this.getAccountTo().setBalance(this.getAccountTo().getBalance().add(this.amount));
    this.setTimestamp(OffsetDateTime.now());
  }

//  Check if balance is sufficient
  public Boolean hasSufficientBalance() {

    if (this.getAccountFrom().getBalance().compareTo(this.getAmount()) > 0) {
      return true;
    }
    return false;
  }

//  Check if amount is below transaction limit
  public Boolean isBelowTransactionLimit() {
     if (this.getAmount().compareTo(this.getUserPerforming().getTransactionLimit()) > 0) {
       return true;
     }
     return false;
  }

//  Returns false if one account is a savings account and the owners don't match
  public Boolean accountsValid() {
    if (this.getAccountFrom().getAccountType() == AccountType.SAVINGS || this.getAccountTo().getAccountType() == AccountType.SAVINGS) {
      if (this.getAccountFrom().getUser() != this.getAccountTo().getUser()) {
        return false;
      }
    }
    return true;
  }

  public BigDecimal getDailySum(List<Transaction> userTransactions) {

//    Sum up all transactions within last day
    BigDecimal dailySum = userTransactions.stream()
            .map(transaction -> {

              if (transaction.timestamp.isAfter(OffsetDateTime.now().minusDays(1))) {
                return transaction.getAmount();
              }
              return BigDecimal.ZERO;
            }).reduce(BigDecimal.ZERO,BigDecimal::add);

    return dailySum;
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

  public Transaction accountFrom(Account accountFrom) {
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
    public Account getAccountFrom() {
    return accountFrom;
  }

  public void setAccountFrom(Account accountFrom) {
    this.accountFrom = accountFrom;
  }

  public Transaction accountTo(Account accountTo) {
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
    public Account getAccountTo() {
    return accountTo;
  }

  public void setAccountTo(Account accountTo) {
    this.accountTo = accountTo;
  }

  public Transaction userPerforming(User userPerforming) {
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
    public User getUserPerforming() {
    return userPerforming;
  }

  public void setUserPerforming(User userPerforming) {
    this.userPerforming = userPerforming;
  }

  public Transaction timestamp(OffsetDateTime timestamp) {
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

  public Transaction amount(BigDecimal amount) {
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
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) &&
        Objects.equals(this.accountFrom, transaction.accountFrom) &&
        Objects.equals(this.accountTo, transaction.accountTo) &&
        Objects.equals(this.userPerforming, transaction.userPerforming) &&
        Objects.equals(this.timestamp, transaction.timestamp) &&
        Objects.equals(this.amount, transaction.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountFrom, accountTo, userPerforming, timestamp, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountFrom: ").append(toIndentedString(accountFrom)).append("\n");
    sb.append("    accountTo: ").append(toIndentedString(accountTo)).append("\n");
    sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
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
