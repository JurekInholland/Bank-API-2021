package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-21T13:43:31.154Z[GMT]")
@Entity
@SequenceGenerator(name = "user_seq", initialValue = 1000001)
@Data
@NoArgsConstructor
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  private Long id;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String emailAddress;
  private String password;
  private Role role;

  public User(String firstName, String lastName, String phoneNumber, String emailAddress, String password, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.password = password;
    this.role = role;
  }
}
