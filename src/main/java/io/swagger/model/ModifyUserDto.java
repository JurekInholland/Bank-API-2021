package io.swagger.model;

import java.util.Collection;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ModifyUserDto
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")

@Data
public class ModifyUserDto   {
  @Id
  private Long id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String emailAddress;
  private String password;

  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = Role.class)
  private Collection<Role> roles;

  public ModifyUserDto( String firstName,  String lastName,  String phoneNumber, String emailAddress,  String password,  Collection<Role> roles) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.password = password;
    this.roles = roles;
  }
}
