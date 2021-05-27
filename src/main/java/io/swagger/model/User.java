package io.swagger.model;
import java.math.BigDecimal;
import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import javax.persistence.*;
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
//  private UserRoles roles;
  private BigDecimal dailyLimit = new BigDecimal(10000);
  private BigDecimal transactionLimit = new BigDecimal(2000);

  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = Role.class)
  private Collection<Role> roles;


  public User(String firstName, String lastName, String phoneNumber, String emailAddress, String password, Collection<Role> roles) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.emailAddress = emailAddress;
    this.password = password;
    this.roles = roles;
  }
}
