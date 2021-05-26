package io.swagger.model;

/**
 * Gets or Sets Role
 */
public enum Role {
    CUSTOMER("customer"),
    EMPLOYEE("employee");

  private String value;

  Role(String value) {
    this.value = value;
  }

}
