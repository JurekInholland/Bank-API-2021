package io.swagger.model;

import java.util.Collection;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateUserDto
 */
@Validated

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@Data
public class CreateUserDto   {

  @Id
  private BigDecimal id;
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  @NotEmpty
  private String phoneNumber;
  @NotEmpty
  private String emailAddress;
  @NotEmpty
  private String password;

  @NotEmpty
  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = Role.class)
  private Collection<Role> roles;

  public CreateUserDto(@NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String emailAddress, @NonNull String password, @NonNull Collection<Role> roles) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.password = password;
    this.roles = roles;
  }
}
