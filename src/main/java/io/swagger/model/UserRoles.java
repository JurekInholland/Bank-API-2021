package io.swagger.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Objects;

/**
 * UserRoles
 */
@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-05-22T03:36:54.103Z[GMT]")


public class UserRoles extends ArrayList<Role>  {

  public UserRoles() {}
  public UserRoles(Role role) {
    this.add(role);
  }

  public void addRole(Role role) {
    if (!this.contains(role)) {
        this.add(role);
    }
  }


}
