package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.AccountType;
import io.swagger.model.PublicUserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PublicAccountDto
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")


public class PublicAccountDto   {
  @Schema(description = "")
  @JsonProperty("iban")
  private String iban = null;

  @Schema(description = "")@Valid
  @JsonProperty("accountType")
  private AccountType accountType = null;

  @JsonProperty("owner")
  private PublicUserDto user = null;

}
