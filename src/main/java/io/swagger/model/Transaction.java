package io.swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Transaction
 */
@Data
@Validated
@Entity
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@SequenceGenerator(name = "t_seq", initialValue = 1)


public class Transaction {
  @Schema(required = true, description = "")
  @NotNull
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_seq")
  private Long id = null;

  @Schema(required = true, description = "")
  @NotNull
  @Valid
  @OneToOne
  private Account accountFrom = null;

  @Schema(required = true, description = "")
  @NotNull
  @Valid
  @OneToOne
  private Account accountTo = null;

  @Schema(required = true, description = "")
  @NotNull
  @Valid
  @OneToOne
  private User userPerforming = null;

  @Schema(required = true, description = "")
  @NotNull
  @Valid
  private OffsetDateTime timestamp = null;

  @Schema(required = true, description = "")
  @NotNull
  @Valid
  private BigDecimal amount = null;

  public void execute() {
    if (this.getTimestamp() != null) {
      System.out.println("TRANSACTION IS EXECUTED TWICE!");
      return;
    }
    this.getAccountFrom().setBalance(this.getAccountFrom().getBalance().subtract(this.amount));
    this.getAccountTo().setBalance(this.getAccountTo().getBalance().add(this.amount));
    this.setTimestamp(OffsetDateTime.now());
  }
}